package com.gundogar.learnconnect

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.Log.e
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CoursesViewModel @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _courses = MutableStateFlow<List<RemoteCourseModel>>(emptyList())
    val courses: StateFlow<List<RemoteCourseModel>> = _courses.asStateFlow()


    init {
        listenToCourses()
    }

    private fun listenToCourses() {
        firestore.collection("Videos")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("Firestore", "Error listening to changes: ${error.message}")
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    val courseList =
                        snapshot.documents.mapNotNull { it.toObject(RemoteCourseModel::class.java) }
                    _courses.value = courseList
                }
            }
    }

    fun purchaseCourse(courseModel: RemoteCourseModel) {
        viewModelScope.launch {
            // Firestore referansı
            val userId = auth.currentUser?.uid
            val userCoursesRef = FirebaseFirestore.getInstance()
                .collection("Users") // Ana koleksiyon
                .document(userId!!) // Kullanıcının UID'sine göre doküman
                .collection("PurchasedCourses") // Alt koleksiyon
                .document(courseModel.courseId)

            userCoursesRef.set(courseModel)
                .addOnSuccessListener {
                    Log.d("Firestore", "Kurs başarıyla satın alındı ve kaydedildi!")
                }
                .addOnFailureListener {
                    Log.e("Firestore", "Kurs satın alma başarısız:")

                }
        }
    }


}