package com.example.clinicacare.presentation.doctor.doctor_setting_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clinicacare.core.data.models.User
import com.example.clinicacare.core.data.repository.ClinicRepository
import com.example.clinicacare.core.data.repository.DatastoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId
import javax.inject.Inject


@HiltViewModel
class DoctorSettingViewModel @Inject constructor(
    private val datastoreRepository: DatastoreRepository,
    private val repo: ClinicRepository
): ViewModel() {

    val user = MutableStateFlow(User())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val id = datastoreRepository.readUserIdFromDataStore().first()
            val getUser = repo.getUser(ObjectId(id)).first()
            user.value = getUser
        }
    }


    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            datastoreRepository.eraseDatastore()
        }
    }
}