package com.example.clinicacare.core.presentation.state

data class ResetPassState (
    var userId: String = "",
    var oldPass: String = "",
    var savedOldPass: ByteArray = byteArrayOf(),
    val newPass: String = "",
    var isPassCorrect: Boolean = false
)