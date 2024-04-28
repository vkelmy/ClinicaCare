package com.example.clinicacare.presentation.doctor

import androidx.lifecycle.ViewModel
import com.example.clinicacare.core.data.repository.ClinicRepository
import com.example.clinicacare.core.data.repository.DatastoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DoctorViewModel @Inject constructor(
    private val repo: ClinicRepository,
    private val dataRepo: DatastoreRepository
): ViewModel() {

}