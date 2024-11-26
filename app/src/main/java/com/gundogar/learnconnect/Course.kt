package com.gundogar.learnconnect

data class Course(
    val name: String,
    val description: String,
    val imageResId: Int
)


    fun getDummyCourses(): List<Course> {
        return listOf(
            Course("Android Development", "Learn the basics of Android", R.drawable.ic_courses),
            Course("Kotlin for Beginners", "A complete guide to Kotlin", R.drawable.ic_coding),
            Course("Advanced Jetpack Compose", "Build modern UIs with Compose", R.drawable.ic_art),
            Course("Data Structures", "Master data structures", R.drawable.ic_art),
            Course("Machine Learning", "Introduction to ML", R.drawable.ic_coding)
        )
    }
