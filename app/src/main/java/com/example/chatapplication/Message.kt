package com.example.chatapplication

// Model class for representing a chat message
class Message {

    // Properties to store message information
    var message: String? = null
    var senderId: String? = null

    // Default constructor (required for Firebase)
    constructor()

    // Parameterized constructor for creating a message with content and sender ID
    constructor(message: String?, senderId: String?) {
        this.message = message
        this.senderId = senderId
    }
}
