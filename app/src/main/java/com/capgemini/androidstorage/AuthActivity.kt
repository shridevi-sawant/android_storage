package com.capgemini.androidstorage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

class AuthActivity : AppCompatActivity() {
    lateinit var uidEditText: EditText
    lateinit var pwdEditText: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        uidEditText = findViewById(R.id.useridE)
        pwdEditText = findViewById(R.id.passwdE)
    }

    fun submitClick(view: View) {
        val username = uidEditText.text.toString()
        val password = pwdEditText.text.toString()

        if(username.isNotEmpty() && password.isNotEmpty()){
            // save data
            val pref = getSharedPreferences("credentials", MODE_PRIVATE)
            val editor = pref.edit()
            editor.putString("userid", username)
            editor.putString("passwd", password)
            editor.commit()

            Toast.makeText(this, "Credentials are saved!!", Toast.LENGTH_LONG).show()
        }
        else
            Toast.makeText(this, "Pls enter all data",
                Toast.LENGTH_LONG).show()
    }
}