package com.capgemini.androidstorage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class StudentAdapter(context: Context, studentData: MutableList<Student>) :
    ArrayAdapter<Student>(context, R.layout.student_item, studentData) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // layout rendering only if it is not done
        var view: View
        if(convertView == null){
            view = LayoutInflater.from(context).inflate(R.layout.student_item, null)
        }
        else
            view = convertView

        // data binding
        val nameTextView = view.findViewById<TextView>(R.id.nameT)
        val idTextView = view.findViewById<TextView>(R.id.idT)
        val marksTextView = view.findViewById<TextView>(R.id.marksT)

        val std = getItem(position)
        nameTextView.text = std?.name
        idTextView.text = "${std?.id}"
        marksTextView.text = "${std?.marks}"

        return view
    }

}