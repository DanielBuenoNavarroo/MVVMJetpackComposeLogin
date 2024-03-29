package com.example.mvvmjetpackcomposelogin.screens.register.ui

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.mvvmjetpackcomposelogin.data.model.User
import com.example.mvvmjetpackcomposelogin.navegacion.NavigationScreens
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterViewModel {

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _confirmPassword = MutableLiveData<String>()
    val confirmPassword: LiveData<String> = _confirmPassword

    private val _registerEnable = MutableLiveData<Boolean>()
    val registerEnable: LiveData<Boolean> = _registerEnable

    fun onEmailChanged(email: String) {
        _email.value = email
        _registerEnable.value = isValidEmail(email) &&
                isValidPassword(password.value ?: "") &&
                isSamePassword(password.value ?: "", confirmPassword.value ?: "")
    }

    fun onPasswordChanged(password: String) {
        _password.value = password
        _registerEnable.value = isValidEmail(email.value ?: "") &&
                isValidPassword(password) &&
                isSamePassword(password, confirmPassword.value ?: "")
    }

    fun onConfirmPasswordChanged(confirmPassword: String) {
        _confirmPassword.value = confirmPassword
        _registerEnable.value = isValidEmail(email.value ?: "") &&
                isValidPassword(password.value ?: "") &&
                isSamePassword(password.value ?: "", confirmPassword)
    }

    fun onRegisterSelected(navController: NavController) {
        createUserEmailPassword { navController.navigate(NavigationScreens.ProfileCreationScreen.route) }
    }

    private fun isValidEmail(email: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isValidPassword(password: String): Boolean = password.length in 6..50

    private fun isSamePassword(password: String, confirmPassword: String): Boolean =
        password == confirmPassword

    // PASSWORD VISIBILITY

    private val _passwordVisibility = MutableLiveData<Boolean>()
    val passwordVisibility: LiveData<Boolean> = _passwordVisibility

    fun togglePasswordVisibility() {
        _passwordVisibility.value = _passwordVisibility.value?.not() ?: false
    }

    // FIREBASE

    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun clearError() {
        _errorMessage.value = ""
    }

    private fun createUserEmailPassword(register: () -> Unit) {

        val email = _email.value.toString()
        val password = _password.value.toString()

        if (_loading.value == false) {
            _loading.value = true
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.i("aplicacion", "logeado correctamente")
                    createUser()
                    register()
                } else {
                    Log.i("aplicacion", "registrado incorrectamente: ${it.exception}")
                    _errorMessage.value = "Error al registrarse"
                    clearError()
                }
            }
            _loading.value = false
        }
    }

    private fun createUser() {

        val userId = auth.currentUser?.uid
        val email = auth.currentUser?.email
        val nombre = email?.split('@')?.first()

        val user = User(
            userId = userId.toString(),
            mail = email.toString(),
            nombre = nombre.toString(),
            imagen = "",
            id = null
        ).toMap()

        FirebaseFirestore.getInstance().collection("usuarios")
            .add(user)
            .addOnSuccessListener {
                Log.i("aplicacion", "${it.id} añadido a la base de datos")
            }.addOnFailureListener {
                Log.i("aplicacion", "Error: $it")
            }
    }

}