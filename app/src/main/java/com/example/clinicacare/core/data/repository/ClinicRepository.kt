package com.example.clinicacare.core.data.repository

import com.example.clinicacare.core.data.models.Appointment
import com.example.clinicacare.core.data.models.User
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId


interface ClinicRepository {

    suspend fun login(email: String, password: String): String

    suspend fun register(user: User)

    fun getUser(id: ObjectId): Flow<User>

    fun getPatients(): Flow<List<User>>

    fun getProfessionals(): Flow<List<User>>

    suspend fun deleteAccount(id: ObjectId)

    suspend fun insertAppointment(appointment: Appointment)

    fun getAppointment(id: ObjectId): Flow<Appointment>

    fun getDailyAppointments(): Flow<List<Appointment>>

    fun getMonthlyAppointments(): Flow<List<Appointment>>

}