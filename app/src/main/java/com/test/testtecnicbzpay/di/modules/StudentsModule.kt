package com.test.testtecnicbzpay.di.modules

import android.content.Context
import androidx.room.Room
import com.test.testtecnicbzpay.features.featuresinlogin.abc.data.database.MainDataBase
// import com.test.testtecnicbzpay.features.featuresinlogin.abc.data.database.MainDataBase
import com.test.testtecnicbzpay.features.featuresinlogin.abc.data.database.daos.StudentDto
import com.test.testtecnicbzpay.features.featuresinlogin.abc.data.repository.StudentsRepository
import com.test.testtecnicbzpay.features.featuresinlogin.abc.data.repository.StudentsRepositoryImpl
import com.test.testtecnicbzpay.features.login.data.repository.LoginRepository
import com.test.testtecnicbzpay.features.login.data.repository.LoginRepositoryImpl
import com.test.testtecnicbzpay.features.login.data.repository.configRequest
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StudentsModule {

    @Provides
    @Singleton
    fun provideStudentDatabase(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            MainDataBase::class.java, "main-database"
        ).build()

    @Provides
    @Singleton
    fun provideDao(db: MainDataBase): StudentDto = db.studentDao()

    @Provides
    @Singleton
    fun provideStudentRepository(dao: StudentDto): StudentsRepository =
        StudentsRepositoryImpl(dao)
}