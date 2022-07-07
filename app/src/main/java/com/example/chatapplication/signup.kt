package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth



class signup : AppCompatActivity() {

    private lateinit var editname: EditText
    private lateinit var editemail: EditText
    private lateinit var editpassword: EditText
    private lateinit var btnsignup: Button
    private lateinit var mAuth: FirebaseAuth


     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        mAuth = FirebaseAuth.getInstance()
        editname = findViewById(R.id.edt_name)
        editemail = findViewById(R.id.edt_Email)
        editpassword = findViewById(R.id.edt_Password)
        btnsignup = findViewById(R.id.S_button)

        btnsignup.setOnClickListener {
            val name = editname.text.toString()
            val email = editemail.text.toString()
            val password = editpassword.text.toString()

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this , MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else {
                    Toast.makeText(this ,"some error occurred " , Toast.LENGTH_SHORT ).show()

                }
            }
        }
     }

}