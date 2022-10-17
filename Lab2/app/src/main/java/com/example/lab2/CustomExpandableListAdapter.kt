package com.example.lab2

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView

class CustomExpandableListAdapter(
    private var context: Context?,
    private var groupArray: Array<String>?,
    private var groupsWithChildren: HashMap<String, Array<String>>?
) : BaseExpandableListAdapter() {

    var selectedPosition: ExpandableListViewItemPosition? = null

    override fun getChild(listPosition: Int, expandedListPosition: Int): Any {
        return groupsWithChildren!![groupArray!![listPosition]]!![expandedListPosition]
    }

    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long {
        return expandedListPosition.toLong()
    }

    override fun getChildView(
        listPosition: Int, expandedListPosition: Int,
        isLastChild: Boolean, convertView: View?, parent: ViewGroup?
    ): View {
        var convertViewCopy = convertView
        val expandedListText = getChild(listPosition, expandedListPosition).toString()
        if (convertViewCopy == null) {
            val layoutInflater =
                context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertViewCopy = layoutInflater.inflate(R.layout.child_item, null)
        }
        preserveColor(listPosition, expandedListPosition, convertViewCopy!!)
        val expandedListTextView = convertViewCopy.findViewById(R.id.item) as TextView
        expandedListTextView.text = expandedListText
        return convertViewCopy
    }

    private fun preserveColor(listPosition: Int, expandedListPosition: Int, view: View) {
        if (selectedPosition != null
            && selectedPosition!!.groupIndex == listPosition
            && selectedPosition!!.childIndex == expandedListPosition
        )
            highlight(view)
        else
            makeTransparent(view)
    }

    override fun getChildrenCount(listPosition: Int): Int {
        return groupsWithChildren!![groupArray!![listPosition]]!!.size
    }

    override fun getGroup(listPosition: Int): Any {
        return groupArray!![listPosition]
    }

    override fun getGroupCount(): Int {
        return groupArray!!.size
    }

    override fun getGroupId(listPosition: Int): Long {
        return listPosition.toLong()
    }

    override fun getGroupView(
        listPosition: Int, isExpanded: Boolean,
        convertView: View?, parent: ViewGroup?
    ): View {
        var convertViewCopy = convertView
        val listTitle = getGroup(listPosition).toString()
        if (convertViewCopy == null) {
            val layoutInflater =
                context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertViewCopy = layoutInflater.inflate(R.layout.group_item, null)
        }
        val listTitleTextView = convertViewCopy!!.findViewById(R.id.group) as TextView
        listTitleTextView.setTypeface(null, Typeface.BOLD)
        listTitleTextView.text = listTitle
        return convertViewCopy
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean {
        return true
    }
}
