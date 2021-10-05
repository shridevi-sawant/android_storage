package com.capgemini.androidstorage

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "students.db",
    null, 1) {

    companion object {
        val TABLE_NAME = "student_table"
        val CLM_STD_ID = "student_id"
        val CLM_STD_NAME = "student_name"
        val CLM_STD_MARKS = "student_marks"
    }

    val QUERY_CREATE_TABLE = "create table $TABLE_NAME ( $CLM_STD_ID number, " +
            "$CLM_STD_NAME text, $CLM_STD_MARKS number)"

    override fun onCreate(db: SQLiteDatabase?) {
        // executed first time when db is created
        db?.execSQL(QUERY_CREATE_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, oldV: Int, newV: Int) {
        // upgrade db
    }


}