package com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_patients

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clinicacare.core.data.models.User
import com.example.clinicacare.core.data.repository.ClinicRepository
import com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_patients.event.PatientEvent
import com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_patients.state.PatientState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

@HiltViewModel
class DashPatientViewModel @Inject constructor(
    private val repository: ClinicRepository
): ViewModel() {

    private val _patientState = MutableStateFlow(PatientState())
    val patientState = _patientState.asStateFlow()

    private var searchJob: Job? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
            showPatients()
        }
    }

    fun onEvent(event: PatientEvent) {
        when (event) {
            is PatientEvent.OnDeleteProfessional -> {
            }
            is PatientEvent.OnFilterPatients -> {
                _patientState.update { it.copy(searchQuery = event.query) }
                searchJob?.cancel()
                searchJob = viewModelScope.launch(Dispatchers.IO) {
                    delay(500L)
                    filterPatients()
                }
            }
            is PatientEvent.OnShowPatientInfo -> {
                displayPatientInfo(event.id)
            }
        }
    }

    private fun showPatients() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUsers("Paciente", null).collect { userList ->
                _patientState.update { it.copy(patients = userList) }
            }
        }
    }

    private fun filterPatients(query: String = _patientState.value.searchQuery.lowercase()) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUsers(userType = "Paciente", query = query).collect { user ->
                _patientState.update { it.copy(patients = user) }
            }
        }
    }

    private fun displayPatientInfo(itemIndex: String?) {
        if (itemIndex != "-1") {
            viewModelScope.launch(Dispatchers.IO) {
                repository.getUser(ObjectId(itemIndex!!)).collect { user ->
                    _patientState.update { it.copy(patient = user) }
                }
            }
        }
    }

}