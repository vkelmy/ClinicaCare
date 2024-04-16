package com.example.clinicacare.core.data.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Appointment : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId.invoke()
    var date: String = ""
    var time: String = ""
    var motive: String = ""
    var isReturn: Boolean = false
    var enrolledUsers: RealmList<User> = realmListOf()
}