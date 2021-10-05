package com.capgemini.androidstorage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import java.io.FileNotFoundException
import java.util.*

class MainActivity : AppCompatActivity() {
    val studentList = mutableListOf<Student>()
    lateinit var studentListView: ListView
    lateinit var stdAdapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        studentListView = findViewById(R.id.lv)
        registerForContextMenu(studentListView)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val name = studentList[info.position].name

        menu?.setHeaderTitle(name)
        menu?.add("EDIT")
        menu?.add("DELETE")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val std = studentList[info.position]
        val wrapper = DBWrapper(this)
        when(item.title){
            "EDIT" -> {
                std.name = std.name.uppercase()
                wrapper.editStudent(std)
            }
            "DELETE" -> {
                wrapper.deleteStudent(std)
                studentList.remove(std)
            }
        }
        stdAdapter.notifyDataSetChanged()

        return super.onContextItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        populateList()

        stdAdapter = StudentAdapter(this, studentList)
        studentListView.adapter = stdAdapter
    }

    private fun populateList() {
        val wrapper = DBWrapper(this)
        val resultC = wrapper.getStudents()

        studentList.clear()
        if(resultC.count > 0){
            resultC.moveToFirst()

            val idx_id = resultC.getColumnIndex(DBHelper.CLM_STD_ID)
            val idx_name = resultC.getColumnIndex(DBHelper.CLM_STD_NAME)
            val idx_marks = resultC.getColumnIndex(DBHelper.CLM_STD_MARKS)

            do{

                val id = resultC.getInt(idx_id)
                val name = resultC.getString(idx_name)
                val marks = resultC.getInt(idx_marks)
                val std = Student(id, name, marks)
                studentList.add(std)

            }while (resultC.moveToNext())

            Log.d("MainActivity", "StudentList : $studentList")
            Toast.makeText(this, "No of students: ${studentList.size}",
                Toast.LENGTH_LONG).show()
        }
        else
            Toast.makeText(this, "No students added yet",
                Toast.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add("Save")
        menu?.add("Read")
        menu?.add("Delete")
        menu?.add("Login")
        menu?.add("Logout")

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.title){
            "Delete" -> {
                deleteFile("test.txt")
            }
            "Read" -> readFromFile()
            "Save" -> {
                writeToFile()
            }
            "Logout" -> {
                removeCredentials()
            }
            "Login" -> {
                // read the credentials
                val pref = getSharedPreferences("credentials", MODE_PRIVATE)
                val userid = pref.getString("userid", null)
                val password = pref.getString("passwd", null)

                if(userid == null || password == null) {
                    // launch AuthActivity
                    val i = Intent(this, AuthActivity::class.java)
                    startActivity(i)
                }
                else
                    Toast.makeText(this, "Already logged in: $userid, $password",
                        Toast.LENGTH_LONG).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun readFromFile() {
        try {
            val fis = openFileInput("test.txt")
            val data = String(fis.readBytes())
            fis.close()

            Toast.makeText(this, "File content: $data", Toast.LENGTH_LONG).show()
        }catch (e: Exception){
            Toast.makeText(this,
                "File reading issue: File might have been deleted", Toast.LENGTH_LONG).show()
        }

    }

    private fun writeToFile() {

        val data = "${Calendar.getInstance().time} This is test data\n"
        val fos = openFileOutput("test.txt", MODE_APPEND)
        fos.write(data.toByteArray())
        fos.close()
        Toast.makeText(this, "Data saved in file", Toast.LENGTH_LONG).show()
    }

    private fun removeCredentials() {
        val pref = getSharedPreferences("credentials", MODE_PRIVATE)
        if(pref.all.isEmpty())
        {
            Toast.makeText(this, "Already Logged out", Toast.LENGTH_LONG).show()
        }
        else {
            val editor = pref.edit()
            editor.clear()
            editor.commit()
            Toast.makeText(this, "Logged out", Toast.LENGTH_LONG).show()
        }
    }

    fun fabClick(view: View) {
        val i = Intent(this, AddStudentActivity::class.java)
        startActivity(i)
    }
}