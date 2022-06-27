package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class loginActivity : AppCompatActivity() {

    private lateinit var editemail: EditText
    private lateinit var editpassword: EditText
    private lateinit var btnlogin: Button
    private lateinit var btnsignup: Button
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()
        editemail = findViewById(R.id.edt_Email)
        editpassword = findViewById(R.id.edt_Password)
        btnlogin = findViewById(R.id.l_button)
        btnsignup = findViewById(R.id.S_button)

        btnsignup.setOnClickListener{
            val intent = Intent(this,signup::class.java)
            startActivity(intent)
            finish()
        }

        btnlogin.setOnClickListener{
            val email = editemail.text.toString()
            val password = editpassword.text.toString()
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                 if (task.isSuccessful) {
                     val intent = Intent(this , MainActivity::class.java)
                     startActivity(intent)
                 }
                 else {
                     Toast.makeText(this , "User does not exist " , Toast.LENGTH_SHORT).show()
                 }
            }
        }

    }
}
