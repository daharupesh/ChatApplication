package com.example.chatapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

// Adapter for displaying messages in a RecyclerView
class MessageAdapter(
    private val context: Context,
    private val messageList: List<Message>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // Constants to identify message types
    private val ITEM_RECEIVE = 1
    private val ITEM_SENT = 2

    // Creates a new ViewHolder when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            // For received messages
            ITEM_RECEIVE -> {
                // Inflate the layout for received messages
                val view = LayoutInflater.from(context).inflate(R.layout.receive, parent, false)
                ReceiveViewHolder(view)
            }
            // For sent messages
            ITEM_SENT -> {
                // Inflate the layout for sent messages
                val view = LayoutInflater.from(context).inflate(R.layout.sent, parent, false)
                SentViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    // Binds data to the ViewHolder
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]

        // Based on the type of message, bind data to the appropriate ViewHolder
        when (holder) {
            is SentViewHolder -> {
                // Display sent message
                holder.sentMessage.text = currentMessage.message
            }
            is ReceiveViewHolder -> {
                // Display received message
                holder.receiveMessage.text = currentMessage.message
            }
        }
    }

    // Returns the total number of messages
    override fun getItemCount(): Int {
        return messageList.size
    }

    // Returns the type of message (sent or received) at a given position
    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
        return if (FirebaseAuth.getInstance().currentUser?.uid == currentMessage.senderId) {
            ITEM_SENT
        } else {
            ITEM_RECEIVE
        }
    }

    // ViewHolder for displaying sent messages
    class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sentMessage = itemView.findViewById<TextView>(R.id.txt_sent_message)
    }

    // ViewHolder for displaying received messages
    class ReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val receiveMessage = itemView.findViewById<TextView>(R.id.txt_receive_message)
    }
}

