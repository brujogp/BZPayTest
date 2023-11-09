package com.test.testtecnicbzpay.features.featuresinlogin.abc.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.testtecnicbzpay.commons.domain.ResponseState
import com.test.testtecnicbzpay.features.featuresinlogin.abc.domain.use_cases.GetStudentsUseCase
import com.test.testtecnicbzpay.features.featuresinlogin.abc.domain.dtos.StudentEntityDto
import com.test.testtecnicbzpay.features.featuresinlogin.abc.domain.use_cases.RegisterNewStudentUseCase
import com.test.testtecnicbzpay.features.featuresinlogin.abc.presentation.states.GetStudentsState
import com.test.testtecnicbzpay.features.featuresinlogin.abc.presentation.states.RegisterNewStudentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StudentViewModel @Inject constructor(
    private val getStudentsUseCase: GetStudentsUseCase,
    private val registerNewStudentUseCase: RegisterNewStudentUseCase,
) : ViewModel() {
    private val _getStudentsState = MutableLiveData<GetStudentsState>()
    val getStudentsState: LiveData<GetStudentsState> = _getStudentsState

    private val _registerNewStudent = MutableLiveData<RegisterNewStudentState>()
    val registerNewStudent: LiveData<RegisterNewStudentState> = _registerNewStudent

    fun registerNewStudent(newStudent: StudentEntityDto) {
        viewModelScope.launch(Dispatchers.IO) {
            registerNewStudentUseCase.invoke(newStudent).collect { responseStudentsState ->
                when (responseStudentsState) {
                    is ResponseState.Loading -> {
                        _registerNewStudent.postValue(RegisterNewStudentState(isLoading = true))
                    }

                    is ResponseState.Success -> {
                        _registerNewStudent.postValue(
                            RegisterNewStudentState(isSuccess = true)
                        )
                    }

                    is ResponseState.Error -> {
                        _registerNewStudent.postValue(
                            RegisterNewStudentState(error = responseStudentsState.message)
                        )
                    }
                }
            }
        }
    }

    fun getStudentsList() {
        viewModelScope.launch(Dispatchers.IO) {
            getStudentsUseCase.invoke().collect { responseStudentsState ->
                when (responseStudentsState) {
                    is ResponseState.Loading -> {
                        _getStudentsState.postValue(GetStudentsState(isLoading = true))
                    }

                    is ResponseState.Success -> {
                        _getStudentsState.postValue(
                            GetStudentsState(studentsList = responseStudentsState.data?.studentsList)
                        )
                    }

                    is ResponseState.Error -> {
                        _getStudentsState.postValue(
                            GetStudentsState(error = responseStudentsState.message)
                        )
                    }
                }
            }
        }
    }
}
