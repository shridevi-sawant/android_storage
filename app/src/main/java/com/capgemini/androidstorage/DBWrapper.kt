package com.capgemini.androidstorage

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class DBWrapper(val context: Context) {

    val helper: DBHelper = DBHelper(context)
    val db: SQLiteDatabase = helper.writableDatabase

    fun addStudent(std: Student): Long{
        // insert
        val rowData = ContentValues()
        rowData.put(DBHelper.CLM_STD_ID, std.id)
        rowData.put(DBHelper.CLM_STD_NAME, std.name)
        rowData.put(DBHelper.CLM_STD_MARKS, std.marks)

        return db.insert(DBHelper.TABLE_NAME, null, rowData)
    }

    fun getStudents() : Cursor{
        // select query
        val clms = arrayOf(DBHelper.CLM_STD_ID, DBHelper.CLM_STD_NAME, DBHelper.CLM_STD_MARKS)

        return db.query(DBHelper.TABLE_NAME, clms, null,
            null, null, null, "${DBHelper.CLM_STD_MARKS} desc")
    }

    fun editStudent(std: Student): Int{
        // update
        val rowData = ContentValues()
        rowData.put(DBHelper.CLM_STD_ID, std.id)
        rowData.put(DBHelper.CLM_STD_NAME, std.name)
        rowData.put(DBHelper.CLM_STD_MARKS, std.marks)

        val args = arrayOf("${std.id}")
        return db.update(DBHelper.TABLE_NAME, rowData,
            "${DBHelper.CLM_STD_ID} = ?", args )
    }

    fun deleteStudent(std: Student): Int{
        //delete
        val args = arrayOf("${std.id}")
        return db.delete(DBHelper.TABLE_NAME, "${DBHelper.CLM_STD_ID} = ?", args)

    }
}