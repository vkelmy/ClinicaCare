package com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clinicacare.core.data.repository.ClinicRepository
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
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: ClinicRepository
) : ViewModel() {

    private val _dashState = MutableStateFlow(DashboardState())
    val dashState = _dashState.asStateFlow()

    private var searchJob: Job? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
            showDoctors()
        }
    }

    fun onEvent(event: DashboardEvent) {
        when (event) {
            is DashboardEvent.OnDeleteProfessional -> {
                deleteProfessional(event.id)
            }
            is DashboardEvent.OnFilterDoctors -> {
                _dashState.update { it.copy(searchQuery = event.query) }
                searchJob?.cancel()
                searchJob = viewModelScope.launch(Dispatchers.IO) {
                    delay(500L)
                    filterDoctors()
                }
            }
            is DashboardEvent.OnShowDoctorInfo -> {
                displayUserInfo(event.id)
            }
        }
    }

    private fun deleteProfessional(id: ObjectId) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAccount(id)
        }
    }

    private fun showDoctors() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUsers("Doutor", null).collect { userList ->
                _dashState.update { it.copy(users = userList) }
            }
        }
    }

    private fun filterDoctors(query: String = _dashState.value.searchQuery.lowercase()) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUsers(userType = "Doutor", query = query).collect { userList ->
                _dashState.update { it.copy(users = userList) }
            }
        }
    }

    private fun displayUserInfo(itemIndex: String?) {
        if (itemIndex != "-1") {
            viewModelScope.launch(Dispatchers.IO) {
                repository.getUser(ObjectId(itemIndex!!)).collect { user ->
                    _dashState.update { it.copy(user = user) }
                }
            }
        }
    }
}