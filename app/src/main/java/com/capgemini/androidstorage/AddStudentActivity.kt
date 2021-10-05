package com.capgemini.androidstorage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

data class Student(val id: Int, var name: String, val marks: Int)

class AddStudentActivity : AppCompatActivity() {
    lateinit var idEditText: EditText
    lateinit var nameEditText: EditText
    lateinit var marksEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        idEditText = findViewById(R.id.stdIdE)
        nameEditText = findViewById(R.id.nameE)
        marksEditText = findViewById(R.id.marksE)
    }

    fun addClick(view: View) {
        val stdId = idEditText.text.toString()
        val name = nameEditText.text.toString()
        val marks = marksEditText.text.toString()

        if(stdId.isNotEmpty() && name.isNotEmpty() && marks.isNotEmpty()){

            val std = Student(stdId.toInt(), name, marks.toInt())
            // add to database


            val wrapper = DBWrapper(this)
            val rowid = wrapper.addStudent(std)
            if(rowid.toInt() != -1){
                Toast.makeText(this, "Details saved: $rowid",
                    Toast.LENGTH_LONG).show()
                finish()
            }
            else
                Toast.makeText(this, "Error saving details..",
                    Toast.LENGTH_LONG).show()

        }else
            Toast.makeText(this, "Pls provide all info", Toast.LENGTH_LONG).show()
    }
}