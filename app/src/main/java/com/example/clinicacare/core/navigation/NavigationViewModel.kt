package com.example.clinicacare.core.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clinicacare.core.data.repository.ClinicRepository
import com.example.clinicacare.core.data.repository.DatastoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.mongodb.kbson.BsonObjectId
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val datastoreRepository: DatastoreRepository,
    private val repository: ClinicRepository
) : ViewModel() {

    private val _startDestination = MutableStateFlow(Screen.LoginScreen.route)
    val startDestination = _startDestination.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            datastoreRepository.readKeepLoginFromDataStore().collect { keepLogged ->
                if (keepLogged) {
                    showScreen()
                }
            }
        }
    }

    private fun showScreen() {
        viewModelScope.launch(Dispatchers.IO) {
            datastoreRepository.readUserIdFromDataStore().collect {
                repository.getUser(BsonObjectId(it)).collect { role ->
                    when (role.role) {
                        "Paciente" -> _startDestination.value = Screen.PatientScreen.route

                        "Doutor" -> _startDestination.value = Screen.ProfessionalScreen.route

                        "Admin" -> _startDestination.value = Screen.DashboardScreen.route
                    }
                }
            }
        }
    }
}