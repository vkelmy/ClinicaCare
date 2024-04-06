package com.example.clinicacare.core.data.repository

import android.util.Log
import com.example.clinicacare.core.data.models.Appointment
import com.example.clinicacare.core.data.models.Client
import com.example.clinicacare.core.data.models.Professional
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId
import java.time.LocalDate

class ClinicRepositoryImpl(private val realm: Realm): ClinicRepository {
    override suspend fun insertClient(client: Client) {
        realm.write { copyToRealm(client, UpdatePolicy.ALL) }
    }

    override fun getClient(id: ObjectId): Flow<Client> {
        return realm.query<Client>(query = "_id == $0", id).asFlow().map { it.list.first() }
    }

    override fun getAllClients(): Flow<List<Client>> {
        return realm.query<Client>().asFlow().map { it.list }
    }

    override suspend fun insertProfessional(professional: Professional) {
        realm.write { copyToRealm(professional) }
    }

    override fun getProfessional(id: ObjectId): Flow<Professional> {
        return realm.query<Professional>(query = "_id == $0", id).asFlow().map { it.list.first() }
    }

    override fun getAllProfessionals(): Flow<List<Professional>> {
        return realm.query<Professional>().asFlow().map { it.list }
    }

    override suspend fun deleteProfessionalAccount(id: ObjectId) {
        realm.write {
            val professional = query<Professional>(query = "_id == $0", id).first().find()
            try {
                professional?.let { delete(it) }
            } catch (e: Exception) {
                Log.d("Error on deleting account:", "${e.message}")
            }
        }
    }

    override suspend fun deleteClientAccount(id: ObjectId) {
        realm.write {
            val client = query<Client>(query = "_id == $0", id).first().find()
            try {
                client?.let { delete(it) }
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