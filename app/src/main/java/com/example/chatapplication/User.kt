package com.example.chatapplication

// User class for representing user data
class User {

    // Properties
    var name: String? = null
    var email: String? = null
    var uid: String? = null

    // Default constructor
    constructor()

    // Parameterized constructor
    constructor(name: String?, email: String?, uid: String?) {
        this.name = name
        this.email = email
        this.uid = uid
    }
}
