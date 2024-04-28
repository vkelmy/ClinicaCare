package com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_admin.add_admin_screen

import androidx.lifecycle.ViewModel
import com.example.clinicacare.core.data.models.User
import com.example.clinicacare.core.data.repository.ClinicRepository
import com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_admin.add_admin_screen.event.AddAdminEvent
import com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_admin.add_admin_screen.state.AddAdminState
import dagger.hilt.android.lifecycle.HiltViewModel
import de.nycode.bcrypt.hash
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DashAddAdminViewModel @Inject constructor(
    private val repository: ClinicRepository
) : ViewModel() {

    private val _addAdminState = MutableStateFlow(AddAdminState())
    val addAdminState = _addAdminState.asStateFlow()

    fun onEvent(event: AddAdminEvent) {
        when (event) {
            is AddAdminEvent.OnBirthdateChange -> {
                _addAdminState.update { it.copy(birthdate = event.birthdate) }
            }
            is AddAdminEvent.OnCpfChange -> {
                _addAdminState.update { it.copy(cpf = event.cpf) }
            }
            is AddAdminEvent.OnNameChange -> {
                _addAdminState.update { it.copy(name = event.name) }
            }
            is AddAdminEvent.OnNumberChange -> {
                _addAdminState.update { it.copy(number = event.number) }
            }
            is AddAdminEvent.OnSexChange -> {
                _addAdminState.update { it.copy(sex = event.sex) }
            }
            is AddAdminEvent.OnEmailChange -> {
                _addAdminState.update { it.copy(email = event.email) }
            }
        }
    }

    suspend fun addProfessional(): Boolean {
        val checkUserEmail = repository.checkUserEmail(_addAdminState.value.email)
        _addAdminState.update { it.copy(isEmailTaken = checkUserEmail) }
        if (!checkUserEmail) {
            val admin = User().apply {
                email = _addAdminState.value.email
                password = hash(_addAdminState.value.email, 12)
                role = "Admin"
                name = _addAdminState.value.name
                birthdate = _addAdminState.value.birthdate
                sex = _addAdminState.value.sex
                number = _addAdminState.value.number
                profession = _addAdminState.value.profession
                cpf = _addAdminState.value.cpf
            }
            repository.register(user = admin)
        }
        return checkUserEmail
    }
}