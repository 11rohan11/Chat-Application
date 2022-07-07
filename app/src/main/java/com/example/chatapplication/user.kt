package com.example.chatapplication

import android.provider.ContactsContract

class user {

    var name : String? = null
    var email : String? = null
    var Uid :String? = null

    constructor(){}

    constructor(name:String? , email:String? , Uid:String?){
        this.name = name
        this.email = email
        this.Uid = Uid
    }

}