package com.example.chatappforhasan

class User {
    var name :String? =null
    var email :String? =null
    var uid :String? =null

    constructor(){ // المنشء الاول//
    // هذا يعرِّف كيف يمكن إنشاء الفئة في الحالة الافتراضية.
    }
    constructor(name:String?,email:String?,uid :String?){
        this.name = name
        this.email=email
        this.uid=uid

    }
}