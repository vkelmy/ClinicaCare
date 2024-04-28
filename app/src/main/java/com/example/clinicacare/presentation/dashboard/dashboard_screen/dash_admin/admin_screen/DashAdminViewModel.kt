package com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_admin.admin_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clinicacare.core.data.models.User
import com.example.clinicacare.core.data.repository.ClinicRepository
import com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_admin.event.AdminEvent
import com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_admin.state.AdminState
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
class DashAdminViewModel @Inject constructor(
    private val repository: ClinicRepository
) : ViewModel() {

    private val _adminState = MutableStateFlow(AdminState())
    val adminState = _adminState.asStateFlow()

    private var searchJob: Job? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
            showUsers()
        }
    }

    fun onEvent(event: AdminEvent) {
        when (event) {
            is AdminEvent.OnFilterAdmins -> {
                _adminState.update { it.copy(searchQuery = event.query) }
                searchJob?.cancel()
                searchJob = viewModelScope.launch(Dispatchers.IO) {
                    delay(500L)
                    filterAdmins()
                }
            }
        }
    }

    private fun showUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUsers("Admin", null).collect { adminList ->
                _adminState.update { it.copy(admins = adminList) }
            }
        }
    }

    private fun filterAdmins(query: String = _adminState.value.searchQuery.lowercase()) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUsers(userType = "Admin", query = query).collect { user ->
                _adminState.update { it.copy(admins = user) }
            }
        }
    }

    fun displayAdminInfo(itemIndex: String?) {
        if (itemIndex != "-1") {
            viewModelScope.launch(Dispatchers.IO) {
                repository.getUser(ObjectId(itemIndex!!)).collect { user ->
                    _adminState.update { it.copy(admin = user) }
                }
            }
        }
    }
}