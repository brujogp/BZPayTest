package com.test.testtecnicbzpay.features.featuresinlogin.abc.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.test.testtecnicbzpay.features.featuresinlogin.abc.data.database.entities.Student

@Dao
interface StudentDto {
    @Query("SELECT * FROM student")
    fun getAll(): List<Student>

    @Insert
    fun insert(users: Student)
}