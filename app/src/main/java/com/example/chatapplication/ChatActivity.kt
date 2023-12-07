package com.example.chatapplication

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

// Activity for handling chat functionality
class ChatActivity : AppCompatActivity() {
    private lateinit var ChatmessageRecyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendButton: ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var mDbRef: DatabaseReference

    var receiverRoom: String? = null
    var senderRoom: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // Get data passed from the intent
        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid

        // Initialize Firebase Database reference
        mDbRef = FirebaseDatabase.getInstance().reference

        // Set up room IDs for sender and receiver
        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid

        // Set the title of the action bar to the name of the chat recipient
        supportActionBar?.title = name

        // Initialize UI elements
        ChatmessageRecyclerView = findViewById(R.id.chatRecyclerView)
        messageBox = findViewById(R.id.messageBox)
        sendButton = findViewById(R.id.sentButton)
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this, messageList)

        // Set up RecyclerView with LinearLayoutManager and MessageAdapter
        ChatmessageRecyclerView.layoutManager = LinearLayoutManager(this)
        ChatmessageRecyclerView.adapter = messageAdapter

        // Listen for changes in the "messages" node in the sender's room
        mDbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // Clear existing messages
                    messageList.clear()

                    // Iterate through new messages and add them to the list
                    for (postSnapshot in snapshot.children) {
                        val message = postSnapshot.getValue(Message::class.java)
                        message?.let { messageList.add(it) }
                    }

                    // Notify the adapter that the data has changed
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle onCancelled event if needed
                }
            })

        // Set up click listener for the sendButton

        sendButton.setOnClickListener {
            // Get the message text from the EditText
            val message = messageBox.text.toString()

            // Check if the message is not empty
            if (message.isNotEmpty()) {
                // Create a Message object with the message and sender UID
                val messageObject = Message(message, senderUid)

                // Push the message to both sender's and receiver's rooms
                val senderRef = mDbRef.child("chats").child(senderRoom!!).child("messages").push()
                val receiverRef = mDbRef.child("chats").child(receiverRoom!!).child("messages").push()

                // Set the message object to both sender's and receiver's references
                senderRef.setValue(messageObject).addOnSuccessListener {
                    receiverRef.setValue(messageObject).addOnSuccessListener {
                        // Handle success if needed
                    }.addOnFailureListener {
                        // Handle failure if needed
                    }
                }

                // Clear the message box after sending
                messageBox.setText("")
            } else {
                // Show a toast message if the message is empty
                showToast("write some message!!")
            }
        }


    }

    private fun showToast(message: String) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
