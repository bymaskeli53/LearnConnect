package com.gundogar.learnconnect

import androidx.annotation.Keep

@Keep
data class RemoteCourseModel(
    val courseId: String = "",
    val name: String = "",
    val description: String = "",
    val instructor: String = "",
    val duration: Int = 0,
    val category: String = "",
    val rating: Double = 0.0,
    val price: Double = 0.0,
)