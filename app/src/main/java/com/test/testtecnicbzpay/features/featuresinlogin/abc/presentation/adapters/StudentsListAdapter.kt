package com.test.testtecnicbzpay.features.featuresinlogin.abc.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.test.testtecnicbzpay.databinding.LayoutStudentHolderBinding
import com.test.testtecnicbzpay.features.featuresinlogin.abc.domain.dtos.StudentEntityDto

class StudentsListAdapter : RecyclerView.Adapter<StudentsListAdapter.Holder>() {
    private var students: List<StudentEntityDto> = arrayListOf()

    fun setStudents(newStudents: List<StudentEntityDto>) {
        this.students = newStudents
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutStudentHolderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = students.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.view.apply {
            this@StudentsListAdapter.students[position].let {
                studentNameTextView.text = it.studentName
                val age = it.studentAge
                studentAgeTextView.text =
                    if (age > 1) age.toString().plus(" años") else age.toString().plus(" año")
                subjectStudentTextView.text = it.subject
            }
        }
    }

    class Holder(val view: LayoutStudentHolderBinding) :
        RecyclerView.ViewHolder(view.root)
}