package com.example.chatapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Adapter class for the RecyclerView to display a list of users
class UserAdapter(val context: Context, val userList: ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    // ViewHolder for a single user item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        // Inflate the layout for a user item
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.user_layout, parent, false)
        return UserViewHolder(view)
    }

    // Bind the user data to the ViewHolder
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        // Get the current user from the list
        val currentUser = userList[position]

        // Set the user's name to the TextView in the ViewHolder
        holder.textName.text = currentUser.name

        // Set a click listener for the entire item view
        holder.itemView.setOnClickListener {
            // Create an intent to start the ChatActivity
            val intent = Intent(context, ChatActivity::class.java)

            // Pass relevant data to the ChatActivity using extras
            intent.putExtra("name", currentUser.name)
            intent.putExtra("uid", currentUser.uid)

            // Start the ChatActivity
            context.startActivity(intent)
        }
    }

    // Get the total number of items in the user list
    override fun getItemCount(): Int {
        return userList.size
    }

    // ViewHolder class to hold references to the UI elements of a single user item
    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textName = itemView.findViewById<TextView>(R.id.txt_name)
    }
}


