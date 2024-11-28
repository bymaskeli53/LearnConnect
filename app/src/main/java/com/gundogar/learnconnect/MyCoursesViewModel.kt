package com.gundogar.learnconnect

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.ui.PlayerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyCoursesViewModel @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val exoplayerRepository: ExoplayerRepository
) : ViewModel() {

    init {
        getMyCourses()
    }

    private val _myCourses = MutableStateFlow<List<RemoteCourseModel>>(emptyList())
    val myCourses: StateFlow<List<RemoteCourseModel>> = _myCourses.asStateFlow()


    private fun getMyCourses() {
        val userId = auth.currentUser?.uid
        val userCoursesRef = firestore
            .collection("Users") // Ana koleksiyon
            .document(userId!!) // Kullanıcının UID'sine göre doküman
            .collection("PurchasedCourses") // Alt koleksiyon
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("Firestore", "Error listening to changes: ${error.message}")
                    return@addSnapshotListener
                }
                if (snapshot != null && !snapshot.isEmpty) {
                    val myCourseList =
                        snapshot.documents.mapNotNull { it.toObject(RemoteCourseModel::class.java) }
                    _myCourses.value = myCourseList
                }
            }
    }

    fun watchVideo(playerView: PlayerView,videoId: String) {
        viewModelScope.launch {
            exoplayerRepository.watchVideo(playerView = playerView,videoId = videoId)

        }


    }

    fun saveVideoProgress(videoId: String) {
        viewModelScope.launch {
            exoplayerRepository.saveVideoProgress(videoId)
        }
    }
}