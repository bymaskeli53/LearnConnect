package com.gundogar.learnconnect.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gundogar.learnconnect.model.RemoteCourseModel
import com.gundogar.learnconnect.databinding.ItemCourseBinding

class CoursesAdapter(private val courses: List<RemoteCourseModel>, val itemClickListener: (RemoteCourseModel) -> Unit) : RecyclerView.Adapter<CoursesAdapter.CoursesViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CoursesViewHolder {
        val binding = ItemCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoursesViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CoursesViewHolder,
        position: Int
    ) {
        val course = courses[position]
        holder.binding.courseName.text = course.courseId
        holder.binding.courseDescription.text = course.description

        holder.binding.btnBuy.setOnClickListener{
            itemClickListener(course)
        }

    }

    override fun getItemCount(): Int {
        return courses.size
    }

    inner class CoursesViewHolder(val binding: ItemCourseBinding) : RecyclerView.ViewHolder(binding.root)





}