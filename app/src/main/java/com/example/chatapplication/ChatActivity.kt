package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {
    private lateinit var messageRecyclerView: RecyclerView
    private lateinit var messagebox : EditText
    private lateinit var sentButton : ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messagelist : ArrayList<Message>
    private lateinit var database: DatabaseReference

    var receiverRoom : String? = null
    var senderRoom : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)


        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")
        val senderuid = FirebaseAuth.getInstance().currentUser?.uid
        database = FirebaseDatabase.getInstance().getReference()

        senderRoom = receiverUid + senderuid
        receiverRoom = senderuid + receiverUid
        supportActionBar?.title = name

        messagebox = findViewById(R.id.messagebox)
        sentButton = findViewById(R.id.sentbtn)
        messagelist = ArrayList()
        messageAdapter = MessageAdapter(this , messagelist)
        messageRecyclerView = findViewById(R.id.chatrecyclerView)
        messageRecyclerView.layoutManager = LinearLayoutManager(this)
        messageRecyclerView.adapter = messageAdapter


        // receiving message from database
        database.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    messagelist.clear()
                    for (postSnapshot in snapshot.children){
                        val message = postSnapshot.getValue(Message::class.java)
                        messagelist.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })

        // adding the message to database
        sentButton.setOnClickListener{
            val message = messagebox.text.toString()
            val messageobject = Message(message , senderuid)

            database.child("chats").child(senderRoom!!).child("messages").push().
            setValue(messageobject).addOnSuccessListener {

                database.child("chats").child(receiverRoom!!).child("messages").push().
                setValue(messageobject)
            }
            messagebox.setText("")

        }

    }
}