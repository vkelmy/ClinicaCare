package com.example.clinicacare.presentation.doctor.patient_history_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clinicacare.core.data.repository.ClinicRepository
import com.example.clinicacare.core.data.repository.DatastoreRepository
import com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_home.event.DashboardEvent
import com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_home.state.DashboardState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

@HiltViewModel
class PatientHistoryViewModel @Inject constructor(
    private val repo: ClinicRepository,
    private val dataRepo: DatastoreRepository
): ViewModel() {

    private val _historyState = MutableStateFlow(PatientHistoryState())
    val historyState = _historyState.asStateFlow()

    private var searchJob: Job? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
            showPatients()
        }
    }

    fun onEvent(event: PatientHistoryEvent) {
        when (event) {
            is PatientHistoryEvent.OnFilterPatients -> {
                _historyState.update { it.copy(searchQuery = event.query) }
                searchJob?.cancel()
                searchJob = viewModelScope.launch(Dispatchers.IO) {
                    delay(500L)
                    filterPatients()
                }
            }
            is PatientHistoryEvent.OnShowPatientAppointments -> {
                displayPatientAppointments(event.id)
            }
        }
    }


    private fun showPatients() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getUsers("Paciente", null).collect { userList ->
                _historyState.update { it.copy(patients = userList) }
            }
        }
    }

    private fun filterPatients(query: String = _historyState.value.searchQuery.lowercase()) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getUsers(userType = "Paciente", query = query).collect { userList ->
                _historyState.update { it.copy(patients = userList) }
            }
        }
    }

    private fun displayPatientAppointments(itemIndex: String?) {
        if (itemIndex != "-1") {
            viewModelScope.launch(Dispatchers.IO) {
                repo.getUser(ObjectId(itemIndex!!)).collect { patientAppointments ->
                    _historyState.update { it.copy(patientName = patientAppointments.name) }
                    _historyState.update { it.copy(appointments = patientAppointments.enrolledAppointments) }
                }
            }
        }
    }
}