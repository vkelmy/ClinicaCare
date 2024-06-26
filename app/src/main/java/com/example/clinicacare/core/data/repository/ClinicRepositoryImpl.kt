package com.example.clinicacare.core.data.repository

import android.util.Log
import com.example.clinicacare.core.data.models.Appointment
import com.example.clinicacare.core.data.models.User
import de.nycode.bcrypt.verify
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.Sort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId
import java.time.LocalDate


class ClinicRepositoryImpl(private val realm: Realm): ClinicRepository {

    override suspend fun login(email: String, password: String): String {
        val getUser = realm.query<User>(query = "email == $0", email).first().find()
        var checkUserPass = false
        var response = ""
        if (getUser != null) {
            checkUserPass = verify(password, getUser.password)
            val userId = getUser._id.toString().split("BsonObjectId(", ")")[1]
            val userRole = getUser.role
            response = "$userId-$userRole"
        }

        return if (checkUserPass) {
            response
        } else {
            ""
        }
    }

    override suspend fun register(user: User) {
        realm.write { copyToRealm(user, UpdatePolicy.ALL) }
    }

    override suspend fun updateUser(user: User) {
        realm.write {
            val queriedUser = query<User>(query = "_id == $0", user._id).first().find()
            queriedUser?.password = user.password
        }
    }

    override fun getUser(id: ObjectId): Flow<User> {
        return realm.query<User>(query = "_id == $0", id).asFlow().map { it.list.first() }
    }

    override suspend fun checkUserEmail(email: String): Boolean {
        val checkEmail = realm.query<User>(query = "email == $0", email).first().find()
        return checkEmail?.email != null
    }

    override suspend fun getUsers(userType: String?, query: String?): Flow<List<User>> {
        var users = emptyFlow<List<User>>()
        realm.write {
            users = if (query?.isNotEmpty() == true) {
                realm.query<User>(query = "role == $0 AND name CONTAINS[c] $1", userType, query)
                    .sort("_id", Sort.DESCENDING).asFlow().map { it.list }
            } else {
                realm.query<User>(query = "role == $0", userType)
                    .sort("_id", Sort.DESCENDING).asFlow().map { it.list }
            }
        }
        return users
    }

    override suspend fun deleteAccount(id: ObjectId) {
        realm.write {
            val user = query<User>(query = "_id == $0", id).first().find()
            try {
                user?.let { delete(it) }
            } catch (e: Exception) {
                Log.d("Error on deleting account:", "${e.message}")
            }
        }
    }

    override suspend fun insertAppointment(appointment: Appointment, doctor: String, patient: String) {
        realm.write {
            val doctorId = query<User>(query = "_id == $0", ObjectId(doctor)).first().find()
            val patientId = query<User>(query = "_id == $0", ObjectId(patient)).first().find()
            appointment.enrolledUsers.addAll(listOf(doctorId!!, patientId!!))
            copyToRealm(appointment, UpdatePolicy.ALL)
        }
    }

    override fun getAppointment(id: ObjectId): Flow<Appointment> {
        return realm.query<Appointment>(query = "_id == $0", id).asFlow().map { it.list.first() }
    }

    override suspend fun getDailyAppointments(date: String?): Flow<List<Appointment>> {
        var dailyAppointments = emptyFlow<List<Appointment>>()
        val formattedDate = if (date == null) {
            val day = LocalDate.now().dayOfMonth
            val formatDay = String.format("%02d", day)
            val month = LocalDate.now().monthValue
            val formatMonth = String.format("%02d", month)
            val year = LocalDate.now().year
            "$formatDay/$formatMonth/$year"
        } else {
            date
        }
        realm.write {
            dailyAppointments = realm.query<Appointment>(query = "date == $0", formattedDate).asFlow().map { it.list }
        }
        return dailyAppointments
    }

    override suspend fun getMonthlyAppointments(): Flow<List<Appointment>> {
        val month = LocalDate.now().monthValue
        val formatMonth = String.format("%02d", month)
        val year = LocalDate.now().year
        val formattedDate = "/$formatMonth/$year"
        var monthlyAppointments = emptyFlow<List<Appointment>>()
        realm.write {
            monthlyAppointments = realm.query<Appointment>(query = "date CONTAINS $0", formattedDate).asFlow().map { it.list }
        }
        return monthlyAppointments
    }

}