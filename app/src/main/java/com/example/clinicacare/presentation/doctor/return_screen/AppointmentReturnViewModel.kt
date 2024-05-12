package com.example.clinicacare.presentation.doctor.return_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clinicacare.core.data.models.Appointment
import com.example.clinicacare.core.data.repository.ClinicRepository
import com.example.clinicacare.presentation.doctor.return_screen.event.ReturnEvent
import com.example.clinicacare.presentation.doctor.return_screen.state.ReturnState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

@HiltViewModel
class AppointmentReturnViewModel @Inject constructor(
    private val repo: ClinicRepository
): ViewModel() {

    private val _returnState = MutableStateFlow(ReturnState())
    val returnState = _returnState.asStateFlow()


    fun onEvent(event: ReturnEvent) {
        when (event) {
            is ReturnEvent.OnDateChange -> {
                _returnState.update { it.copy(date = event.date) }
            }
            is ReturnEvent.OnMotiveChange -> {
                _returnState.update { it.copy(motive = event.motive) }
            }
            is ReturnEvent.OnTimeChange -> {
                _returnState.update { it.copy(time = event.time) }
            }
            ReturnEvent.SaveReturn -> {
                insertReturn()
            }
        }
    }

    fun getAppointment(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAppointment(ObjectId(id)).collect { appointment ->
                _returnState.update { it.copy(appointment = appointment) }
            }
        }
    }

    private fun insertReturn() {
        viewModelScope.launch(Dispatchers.IO) {
            val returnAppointment = Appointment().apply {
                date = _returnState.value.date
                time = _returnState.value.time
                motive = _returnState.value.motive
                isReturn = true
            }

            val doctorId = _returnState.value.appointment.enrolledUsers.first()._id.toString()
            val patientId = _returnState.value.appointment.enrolledUsers.last()._id.toString()
            val splitDoctorId = doctorId.split("BsonObjectId(", ")")[1]
            val splitPatientId = patientId.split("BsonObjectId(", ")")[1]

            repo.insertAppointment(
                appointment = returnAppointment,
                doctor = splitDoctorId,
                patient = splitPatientId
            )
        }

    }

    fun getDailyAppointments() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getDailyAppointments(_returnState.value.date).collect { list ->
                list.map {
                    _returnState.value.dailyAppointmentsTime.add(it.time)
                }
            }
        }
    }

}