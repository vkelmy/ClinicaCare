package com.example.clinicacare.presentation.doctor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clinicacare.core.data.repository.ClinicRepository
import com.example.clinicacare.core.data.repository.DatastoreRepository
import com.example.clinicacare.presentation.doctor.state.DoctorState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

@HiltViewModel
class DoctorViewModel @Inject constructor(
    private val repo: ClinicRepository,
    private val dataRepo: DatastoreRepository
): ViewModel() {

    private val _doctorState = MutableStateFlow(DoctorState())
    val doctorState = _doctorState.asStateFlow()


    init {
        viewModelScope.launch(Dispatchers.IO) {
            getFilteredAppointments()
        }

        viewModelScope.launch(Dispatchers.IO) {
            coroutineScope {
                getDoctorId()
            }
            coroutineScope {
                getDoctor()
            }
        }
    }

    fun getFilteredAppointments(filterTimeFrame: Int = 0) {
        viewModelScope.launch(Dispatchers.IO) {
            when (filterTimeFrame) {
                0 -> {
                    repo.getDailyAppointments().collect { list ->
                        _doctorState.update { it.copy(dailyAppointments = list) }
                    }
                }

                1 -> {
                    repo.getMonthlyAppointments().collect { list ->
                        _doctorState.update { it.copy(monthlyAppointments = list) }
                    }
                }
            }
        }
    }

    fun displayAppointmentInfo(itemIndex: Int?) {
        if (itemIndex != -1) {
            viewModelScope.launch(Dispatchers.IO) {
                val appointment = _doctorState.value.dailyAppointments[itemIndex!!]
                val patient = appointment.enrolledUsers.last()
                _doctorState.update { it.copy(appointment = appointment) }
                _doctorState.update { it.copy(patient = patient) }
            }
        }
    }

    private suspend fun getDoctorId() {
        dataRepo.readUserIdFromDataStore().collect { _doctorState.value.doctorId = it }
    }

    private suspend fun getDoctor() {
        repo.getUser(ObjectId(_doctorState.value.doctorId)).collect {
            _doctorState.value.doctor = it
        }
    }

}