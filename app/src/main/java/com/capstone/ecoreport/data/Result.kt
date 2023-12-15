package com.capstone.ecoreport.data

import com.capstone.ecoreport.data.models.LoginResponse
import com.capstone.ecoreport.data.models.RegisterResponse
import com.capstone.ecoreport.data.models.ReportResponse

sealed class RegisterResult {
    object Loading : RegisterResult()
    data class Success(val response: RegisterResponse) : RegisterResult()
    data class Error(val message: String) : RegisterResult()
}
sealed class LoginResult {
    object loading : LoginResult()
    data class Success(val response: LoginResponse) : LoginResult()
    data class Error(val message: String) : LoginResult()
}
sealed class ReportResult {
    object Loading : ReportResult()
    data class Success(val response: ReportResponse) : ReportResult()
    data class Error(val message: String) : ReportResult()
}