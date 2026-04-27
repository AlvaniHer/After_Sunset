package com.example.aftersunset.data.auth

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class AuthRepository {

    private val auth: FirebaseAuth = Firebase.auth

    fun login(
        email: String,
        password: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, null)
                } else {
                    onResult(false, task.exception?.localizedMessage ?: "Error al iniciar sesión")
                }
            }
    }

    fun isUserLogged(): Boolean {
        return auth.currentUser != null
    }

    fun logout() {
        auth.signOut()
    }
}