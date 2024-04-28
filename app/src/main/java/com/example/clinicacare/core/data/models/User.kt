package com.example.clinicacare.core.data.models

import io.realm.kotlin.ext.backlinks
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class User : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId.invoke()
    var email: String = ""
    var password: ByteArray = ByteArray(1)
    var role: String = "Paciente"
    var name: String = ""
    var birthdate: String = ""
    var sex: String = ""
    var number: String = ""
    var cpf: String? = null
    var profession: String? = null
    var crm: String? = null
    val enrolledAppointments: RealmResults<Appointment> by backlinks(Appointment::enrolledUsers)
}