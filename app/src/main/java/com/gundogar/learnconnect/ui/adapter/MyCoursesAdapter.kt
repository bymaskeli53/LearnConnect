package com.gundogar.learnconnect.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gundogar.learnconnect.model.RemoteCourseModel
import com.gundogar.learnconnect.databinding.ItemCourseBinding

class MyCoursesAdapter(
    private val courses: List<RemoteCourseModel>,
    val itemClickListener: (RemoteCourseModel) -> Unit
) : RecyclerView.Adapter<MyCoursesAdapter.MyCoursesViewHolder>() {



    inner class MyCoursesViewHolder(val binding: ItemCourseBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyCoursesViewHolder {
        val binding = ItemCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyCoursesViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MyCoursesViewHolder,
        position: Int
    ) {
        val course = courses[position]
        holder.binding.courseName.text = course.courseId
        holder.binding.courseDescription.text = course.description

        holder.binding.root.setOnClickListener {
            itemClickListener(course)
        }

    }

    override fun getItemCount(): Int {
        return courses.size
    }


}