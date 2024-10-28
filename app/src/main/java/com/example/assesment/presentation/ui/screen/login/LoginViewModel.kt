package com.example.assesment.presentation.ui.screen.login

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assesment.R
import com.example.assesment.data.repository.AuthRepository
import com.example.assesment.utils.preference.PreferencesManager
import kotlinx.coroutines.launch

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {
    val loginResult = MutableLiveData<Result<String>>()
    fun login(username: String, password: String,context: Context) {

        viewModelScope.launch {
          val  preferencesManager = PreferencesManager(context)

            val result = authRepository.login(username, password)
            loginResult.value = result
            preferencesManager.saveData(R.string.key_token.toString(),loginResult.value.toString())
        }
    }


}