package com.example.clinicacare.core.presentation

import androidx.lifecycle.ViewModel
import com.example.clinicacare.core.data.models.User
import com.example.clinicacare.core.data.repository.ClinicRepository
import com.example.clinicacare.core.data.repository.DatastoreRepository
import com.example.clinicacare.core.presentation.event.ResetPassEvent
import com.example.clinicacare.core.presentation.state.ResetPassState
import dagger.hilt.android.lifecycle.HiltViewModel
import de.nycode.bcrypt.hash
import de.nycode.bcrypt.verify
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val repository: ClinicRepository,
    private val datastoreRepository: DatastoreRepository
): ViewModel() {

    private val _resetPassState = MutableStateFlow(ResetPassState())
    val resetPassState = _resetPassState.asStateFlow()

    fun onEvent(event: ResetPassEvent) {
        when (event) {
            is ResetPassEvent.OnNewPassChange -> {
                _resetPassState.update { it.copy(newPass = event.newPass) }
            }
            is ResetPassEvent.OnOldPassChange -> {
                _resetPassState.update { it.copy(oldPass = event.oldPass) }
            }
        }
    }

    suspend fun changePassword(): Boolean {
        coroutineScope {
            getUserIdFromDatastore()
        }
        coroutineScope {
            getUser()
        }
        checkPass()
        if (_resetPassState.value.isPassCorrect) {
            coroutineScope {
                updateUserPass()
            }
        }

        return _resetPassState.value.isPassCorrect
    }

    private suspend fun getUserIdFromDatastore() {
        val id = datastoreRepository.readUserIdFromDataStore().first()
        _resetPassState.update { it.copy(userId = id) }
    }
    private suspend fun getUser() {
        val user = repository.getUser(ObjectId(_resetPassState.value.userId)).first()
        _resetPassState.update { it.copy(savedOldPass = user.password) }
    }
    private fun checkPass() {
        val savedOldPass = _resetPassState.value.savedOldPass
        val oldPass = _resetPassState.value.oldPass
        val checkPass = verify(oldPass, savedOldPass)
        _resetPassState.update {
            it.copy(isPassCorrect = checkPass)
        }
    }
    private suspend fun updateUserPass() {
        val user = User().apply {
            _id = ObjectId(_resetPassState.value.userId)
            password = hash(_resetPassState.value.newPass, 12)
        }
        repository.updateUser(user)
    }
}