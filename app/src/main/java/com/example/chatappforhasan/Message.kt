package com.example.chatappforhasan

class Message {
    // message modul

    var message: String? = null
    var senderId: String? = null

    constructor(){
    }

    constructor(message: String?,senderId: String?){
        this.message=message
        this.senderId= senderId

    }
    override fun toString(): String {
        return " $message"
    }
}