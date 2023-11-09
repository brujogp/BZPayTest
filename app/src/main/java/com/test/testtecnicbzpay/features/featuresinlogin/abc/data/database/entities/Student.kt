package com.test.testtecnicbzpay.features.featuresinlogin.abc.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Student(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "student_name") val name: String,
    @ColumnInfo val subject: String
)