package com.example.aftersunset.ui.screens.reviews

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AddReviewViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    var comment by mutableStateOf("")
    var rating by mutableIntStateOf(5)
    var isSubmitting by mutableStateOf(false)

    fun saveReview(venueId: String, onSuccess: () -> Unit) {
        val currentUserId = auth.currentUser?.uid ?: return
        if (comment.isEmpty()) return

        isSubmitting = true

        val newReview = hashMapOf(
            "venueId" to venueId,
            "userId" to currentUserId,
            "rating" to rating,
            "comment" to comment,
            "user" to ""
        )

        db.collection("resenas")
            .add(newReview)
            .addOnSuccessListener {
                isSubmitting = false
                onSuccess()
            }
            .addOnFailureListener { e ->
                isSubmitting = false
                Log.e("AddReview", "Error al guardar en Firebase", e)
            }
    }
}