package com.example.chatapplication
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity() {

    private lateinit var editname: EditText
    private lateinit var editemail: EditText
    private lateinit var editpassword: EditText
    private lateinit var btnsignup: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var databse : DatabaseReference


     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        databse = FirebaseDatabase.getInstance().getReference()
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
                    addusertodatabase(name , email , mAuth.currentUser?.uid!!)
                    val intent = Intent(this , MainActivity::class.java)
                    finish()
                    startActivity(intent)
                }
                else {
                    Toast.makeText(this ,"some error occurred " , Toast.LENGTH_SHORT ).show()
                }
            }
        }
     }
    private fun addusertodatabase(name:String,email:String,uid:String){
        databse = FirebaseDatabase.getInstance().getReference()
        databse.child("user").child(uid).setValue(user(name,email,uid))
    }

}