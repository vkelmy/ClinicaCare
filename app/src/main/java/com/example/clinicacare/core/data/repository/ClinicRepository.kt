package com.example.clinicacare.core.data.repository

import com.example.clinicacare.core.data.models.Appointment
import com.example.clinicacare.core.data.models.Client
import com.example.clinicacare.core.data.models.Professional
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId


interface ClinicRepository {

    suspend fun insertClient(client: Client)

    fun getClient(id: ObjectId): Flow<Client>

    fun getAllClients(): Flow<List<Client>>

    suspend fun insertProfessional(professional: Professional)

    fun getProfessional(id: ObjectId): Flow<Professional>

    fun getAllProfessionals(): Flow<List<Professional>>

    suspend fun deleteProfessionalAccount(id: ObjectId)

    suspend fun deleteClientAccount(id: ObjectId)

    suspend fun insertAppointment(appointment: Appointment)

    fun getAppointment(id: ObjectId): Flow<Appointment>

    fun getDailyAppointments(): Flow<List<Appointment>>

    fun getMonthlyAppointments(): Flow<List<Appointment>>

}