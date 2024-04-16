package com.example.clinicacare.core.data.repository

import android.util.Log
import com.example.clinicacare.core.data.models.Appointment
import com.example.clinicacare.core.data.models.User
import de.nycode.bcrypt.verify
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId
import java.time.LocalDate


class ClinicRepositoryImpl(private val realm: Realm): ClinicRepository {

    override suspend fun login(email: String, password: String): String {
        val getUser = realm.query<User>(query = "email == $0", email).first().find()
        val checkUserPass = getUser?.password?.let { verify(password, it) }

        val userId = getUser?._id.toString().split("BsonObjectId(", ")")[1]
        return if (checkUserPass == true) {
            userId
        } else {
            ""
        }
    }

    override suspend fun register(user: User) {
        realm.write { copyToRealm(user, UpdatePolicy.ALL) }
    }

    override fun getUser(id: ObjectId): Flow<User> {
        return realm.query<User>(query = "_id == $0", id).asFlow().map { it.list.first() }
    }

    override fun getPatients(): Flow<List<User>> {
        val patient = "patient"
        return realm.query<User>(query = "role == $0", patient).asFlow().map { it.list }
    }

    override fun getProfessionals(): Flow<List<User>> {
        val professional = "professional"
        return realm.query<User>(query = "role == $0", professional).asFlow().map { it.list }
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

    override suspend fun insertAppointment(appointment: Appointment) {
        realm.write { copyToRealm(appointment) }
    }

    override fun getAppointment(id: ObjectId): Flow<Appointment> {
        return realm.query<Appointment>(query = "_id == $0", id).asFlow().map { it.list.first() }
    }

    override fun getDailyAppointments(): Flow<List<Appointment>> {
        val day = LocalDate.now().dayOfMonth
        val formatDay = String.format("%02d", day)
        val month = LocalDate.now().monthValue
        val formatMonth = String.format("%02d", month)
        val year = LocalDate.now().year
        val formattedDate = "$formatDay/$formatMonth/$year"

        return realm.query<Appointment>(query = "date CONTAINS $0", formattedDate).asFlow().map { it.list }
    }

    override fun getMonthlyAppointments(): Flow<List<Appointment>> {
        val month = LocalDate.now().monthValue
        val formatMonth = String.format("%02d", month)
        val year = LocalDate.now().year
        val formattedDate = "/$formatMonth/$year"

        return realm.query<Appointment>(query = "date CONTAINS $0", formattedDate).asFlow().map { it.list }
    }

}