package com.example.clinicacare.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clinicacare.core.data.models.User
import com.example.clinicacare.core.data.repository.ClinicRepository
import com.example.clinicacare.core.data.repository.DatastoreRepository
import com.example.clinicacare.core.navigation.Screen
import com.example.clinicacare.presentation.auth.event.AuthEvent
import com.example.clinicacare.presentation.auth.state.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import de.nycode.bcrypt.hash
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: ClinicRepository,
    private val datastoreRepository: DatastoreRepository
) : ViewModel() {
    private val _authState = MutableStateFlow(AuthState())
    val authState = _authState.asStateFlow()

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.OnEmailChange -> {
                _authState.update { it.copy(email = event.email) }
            }
            is AuthEvent.OnPasswordChange -> {
                _authState.update { it.copy(password = event.password) }
            }
            is AuthEvent.OnNameChange -> {
                _authState.update { it.copy(name = event.name) }
            }
            is AuthEvent.OnBirthdateChange -> {
                _authState.update { it.copy(birthdate = event.birthdate) }
            }
            is AuthEvent.OnSexChange -> {
                _authState.update { it.copy(sex = event.sex) }
            }
            is AuthEvent.OnNumberChange -> {
                _authState.update { it.copy(number = event.number) }
            }
            is AuthEvent.OnCpfChange -> {
                _authState.update { it.copy(cpf = event.cpf) }
            }
            is AuthEvent.OnCheckRememberMe -> {
                _authState.update { it.copy(isChecked = event.checked) }
            }
        }
    }

    suspend fun registerUser(): Boolean {
        val checkUserEmail = repository.checkUserEmail(_authState.value.email)
        _authState.update { it.copy(isEmailTaken = checkUserEmail) }
        if (!checkUserEmail) {
            val newUser = User().apply {
                email = _authState.value.email
                password = hash(_authState.value.password, 12)
                name = _authState.value.name
                birthdate = _authState.value.birthdate
                sex = _authState.value.sex
                number = _authState.value.number
                cpf = _authState.value.cpf
            }
            val userId = splitUserId(newUser._id.toString())
            writeOnDatastore(userId)
            repository.register(user = newUser)
        }
        return checkUserEmail
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    fun login(): String {
        val result = viewModelScope.async {
            repository.login(email = _authState.value.email, password = _authState.value.password)
        }

        result.invokeOnCompletion {
            if (it == null) {
                if (result.getCompleted().isNotEmpty()) {
                    val userId = result.getCompleted().split("-")[0]
                    updateUserCredentials(userId)
                } else {
                    _authState.update { credentials -> credentials.copy(emailOrPasswordIncorrect = true) }
                }
            }
        }
        if (_authState.value.userId.isNotEmpty()) {
            if (_authState.value.isChecked) {
                keepLogged()
            }
            writeOnDatastore(_authState.value.userId)

            val userRole = result.getCompleted().split("-")[1]

            selectRoutePath(userRole)
        }

        return if (_authState.value.isUserLogged) _authState.value.routePath else ""
    }

    private fun splitUserId(id: String): String {
        return id.split("BsonObjectId(", ")")[1]
    }

    private fun updateUserCredentials(id: String) {
        _authState.update { it.copy(userId = id) }
        _authState.update { logged -> logged.copy(isUserLogged = true) }
    }

    private fun writeOnDatastore(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            datastoreRepository.writeUserIdToDataStore(id)
        }
    }

    private fun keepLogged() {
        viewModelScope.launch(Dispatchers.IO) {
            datastoreRepository.writeKeepLoginToDataStore(_authState.value.isChecked)
        }
    }

    private fun selectRoutePath(route: String) {
        when (route) {
            "Paciente" -> _authState.update { it.copy(routePath = Screen.PatientScreen.route) }
            "Doutor" -> _authState.update { it.copy(routePath = Screen.ProfessionalScreen.route) }
            "Admin" -> _authState.update { it.copy(routePath = Screen.DashboardScreen.route) }
        }
    }
}