package com.example.clinicacare.core.data.repository

import com.example.clinicacare.core.data.models.Appointment
import com.example.clinicacare.core.data.models.User
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId


interface ClinicRepository {

    suspend fun login(email: String, password: String): String

    suspend fun register(user: User)

    suspend fun updateUser(user: User)

    fun getUser(id: ObjectId): Flow<User>

    suspend fun getUsers(userType: String?, query: String?): Flow<List<User>>

    suspend fun checkUserEmail(email: String): Boolean

    suspend fun deleteAccount(id: ObjectId)

    suspend fun insertAppointment(appointment: Appointment, doctor: String, patient: String)

    fun getAppointment(id: ObjectId): Flow<Appointment>

    suspend fun getDailyAppointments(date: String? = null): Flow<List<Appointment>>

    suspend fun getMonthlyAppointments(): Flow<List<Appointment>>

}