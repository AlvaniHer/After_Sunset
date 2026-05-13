package com.example.aftersunset.data.repository

import com.example.aftersunset.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    /**
     * Creacion del username unico
     */
    suspend fun isUsernameAvailable(username: String): Boolean {
        return try {
            val query = db.collection("usuarios")
                .whereEqualTo("username", username)
                .get()
                .await()
            query.isEmpty
        } catch (e: Exception) {
            false
        }
    }
    /**
     * REGISTRO: Crea el usuario en Auth y luego su perfil en Firestore.
     */
    suspend fun registerUser(user: User, password: String): Result<Unit> {
        return try {

            val authResult = auth.createUserWithEmailAndPassword(user.email, password).await()
            val uid = authResult.user?.uid ?: throw Exception("No se pudo obtener el UID")

            db.collection("usuarios")
                .document(uid)
                .set(user.copy(id = uid))
                .await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * LOGIN: Acceso por email y contraseña.
     */
    suspend fun loginUser(email: String, password: String): Result<Unit> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * CERRAR SESIÓN
     */
    fun logout() {
        auth.signOut()
    }

    /**
     * OBTENER PERFIL: Para saber quién está logueado actualmente.
     */
    suspend fun getCurrentUserProfile(): User? {
        val uid = auth.currentUser?.uid ?: return null
        return try {
            val snapshot = db.collection("usuarios").document(uid).get().await()
            snapshot.toObject(User::class.java)
        } catch (e: Exception) {
            null
        }
    }
}