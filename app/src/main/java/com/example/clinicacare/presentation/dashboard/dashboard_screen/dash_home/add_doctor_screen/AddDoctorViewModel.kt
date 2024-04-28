package com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_home.add_doctor_screen

import androidx.lifecycle.ViewModel
import com.example.clinicacare.core.data.models.User
import com.example.clinicacare.core.data.repository.ClinicRepository
import com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_home.add_doctor_screen.event.AddDoctorEvent
import com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_home.add_doctor_screen.state.AddDoctorState
import dagger.hilt.android.lifecycle.HiltViewModel
import de.nycode.bcrypt.hash
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AddDoctorViewModel @Inject constructor(
    private val repository: ClinicRepository
) : ViewModel() {

    private val _addDoctorState = MutableStateFlow(AddDoctorState())
    val addDoctorState = _addDoctorState.asStateFlow()

    fun onEvent(event: AddDoctorEvent) {
        when (event) {
            is AddDoctorEvent.OnBirthdateChange -> {
                _addDoctorState.update { it.copy(birthdate = event.birthdate) }
            }
            is AddDoctorEvent.OnCrmChange -> {
                _addDoctorState.update { it.copy(crm = event.crm) }
            }
            is AddDoctorEvent.OnCpfChange -> {
                _addDoctorState.update { it.copy(cpf = event.cpf) }
            }
            is AddDoctorEvent.OnNameChange -> {
                _addDoctorState.update { it.copy(name = event.name) }
            }
            is AddDoctorEvent.OnNumberChange -> {
                _addDoctorState.update { it.copy(number = event.number) }
            }
            is AddDoctorEvent.OnProfessionChange -> {
                _addDoctorState.update { it.copy(profession = event.profession) }
            }
            is AddDoctorEvent.OnSexChange -> {
                _addDoctorState.update { it.copy(sex = event.sex) }
            }
            is AddDoctorEvent.OnEmailChange -> {
                _addDoctorState.update { it.copy(email = event.email) }
            }
        }
    }

    suspend fun addDoctor(): Boolean {
        val checkUserEmail = repository.checkUserEmail(_addDoctorState.value.email)
        _addDoctorState.update { it.copy(isEmailTaken = checkUserEmail) }
        if (!checkUserEmail) {
            val doctor = User().apply {
                email = _addDoctorState.value.email
                password = hash("${_addDoctorState.value.email}-${_addDoctorState.value.crm}", 12)
                role = "Doutor"
                name = _addDoctorState.value.name
                birthdate = _addDoctorState.value.birthdate
                sex = _addDoctorState.value.sex
                number = _addDoctorState.value.number
                profession = _addDoctorState.value.profession
                crm = _addDoctorState.value.crm
                cpf = _addDoctorState.value.cpf
            }
            repository.register(user = doctor)
        }
        return checkUserEmail
    }
}