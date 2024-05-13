package com.example.clinicacare.presentation.patient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clinicacare.core.data.models.Appointment
import com.example.clinicacare.core.data.repository.ClinicRepository
import com.example.clinicacare.core.data.repository.DatastoreRepository
import com.example.clinicacare.presentation.patient.event.PatientEvent
import com.example.clinicacare.presentation.patient.state.PatientState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

@HiltViewModel
class PatientViewModel @Inject constructor(
    private val repo: ClinicRepository,
    private val dataRepo: DatastoreRepository
): ViewModel() {

    private val _patientState = MutableStateFlow(PatientState())
    val patientState = _patientState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getDoctors()
        }

        getUserLogged()
    }

    fun onEvent(event: PatientEvent) {
        when (event) {
            is PatientEvent.ChangeAppointmentDate -> {
                _patientState.update { it.copy(date = event.date) }
            }
            is PatientEvent.ChangeAppointmentMotive -> {
                _patientState.update { it.copy(motive = event.motive) }
            }
            is PatientEvent.ChangeAppointmentTime -> {
                _patientState.update { it.copy(time = event.time) }
            }
            is PatientEvent.ChangeDoctor -> {
                _patientState.update { it.copy(doctor = event.doctor) }
            }
            is PatientEvent.ChangeDoctorExpertise -> {
                _patientState.update { it.copy(expertise = event.expertise) }
            }
            PatientEvent.SaveAppointment -> {
                createAppointment()
            }
        }
    }

    private fun getDoctors() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getUsers("Doutor", null).collect { users ->
                _patientState.update { it.copy(doctors = users) }
            }
        }
    }

    private fun createAppointment() {
        viewModelScope.launch(Dispatchers.IO) {
            val appointment = Appointment().apply {
                date = _patientState.value.date
                time = _patientState.value.time
                motive = _patientState.value.motive
            }
            repo.insertAppointment(
                appointment,
                _patientState.value.doctor,
                _patientState.value.userLoggedId
            )
        }
    }

    private fun getUserLogged() {
        viewModelScope.launch(Dispatchers.IO) {
            dataRepo.readUserIdFromDataStore().collect { id ->
                _patientState.update { it.copy(userLoggedId = id) }

                repo.getUser(ObjectId(id)).collect {
                    it.enrolledAppointments.forEach { appointment ->
                        _patientState.value.userLoggedAppointments.add(appointment)
                    }
                }
            }
        }
    }

    fun getDoctorAppointments() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getUser(ObjectId(_patientState.value.doctor)).collect {
                it.enrolledAppointments.forEach { appointment ->
                    if (appointment.date == _patientState.value.date) {
                        _patientState.value.appointments.add(appointment.time)
                    }
                }
            }
        }
    }

//    fun getUserLoggedAppointments() {
//        viewModelScope.launch(Dispatchers.IO) {
//            repo.getUser(ObjectId(_patientState.value.userLoggedId)).collect {
//                it.enrolledAppointments.forEach { appointment ->
//                    _patientState.value.userLoggedAppointments.add(appointment)
//                }
//            }
//        }
//    }

//    private suspend fun getUserLoggedAppointments() {
//        repo.getUser(ObjectId(_patientState.value.userLoggedId)).collect {
//            it.enrolledAppointments.forEach { appointment ->
//                _patientState.value.userLoggedAppointments.add(appointment)
//            }
//        }
//    }

}