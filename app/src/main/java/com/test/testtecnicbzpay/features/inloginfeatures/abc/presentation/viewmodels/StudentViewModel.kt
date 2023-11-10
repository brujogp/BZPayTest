package com.test.testtecnicbzpay.features.inloginfeatures.abc.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.testtecnicbzpay.commons.domain.ResponseState
import com.test.testtecnicbzpay.features.inloginfeatures.abc.domain.use_cases.GetStudentsUseCase
import com.test.testtecnicbzpay.features.inloginfeatures.abc.domain.dtos.StudentEntityDto
import com.test.testtecnicbzpay.features.inloginfeatures.abc.domain.use_cases.DeleteStudentUseCase
import com.test.testtecnicbzpay.features.inloginfeatures.abc.domain.use_cases.ModifyStudentUseCase
import com.test.testtecnicbzpay.features.inloginfeatures.abc.domain.use_cases.RegisterNewStudentUseCase
import com.test.testtecnicbzpay.features.inloginfeatures.abc.presentation.states.GetStudentsState
import com.test.testtecnicbzpay.features.inloginfeatures.abc.presentation.states.ActionWithStudentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StudentViewModel @Inject constructor(
    private val getStudentsUseCase: GetStudentsUseCase,
    private val registerNewStudentUseCase: RegisterNewStudentUseCase,
    private val modifyStudentUseCase: ModifyStudentUseCase,
    private val deleteStudentUseCase: DeleteStudentUseCase,
) : ViewModel() {
    private val _getStudentsState = MutableLiveData<GetStudentsState>()
    val getStudentsState: LiveData<GetStudentsState> = _getStudentsState

    private val _registerNewStudent = MutableLiveData<ActionWithStudentState>()
    val registerNewStudent: LiveData<ActionWithStudentState> = _registerNewStudent

    private val _modifyStudent = MutableLiveData<ActionWithStudentState>()
    val modifyStudent: LiveData<ActionWithStudentState> = _modifyStudent

    private val _deleteStudent = MutableLiveData<ActionWithStudentState>()
    val deleteStudent: LiveData<ActionWithStudentState> = _deleteStudent

    fun registerNewStudentAction(newStudent: StudentEntityDto) {
        viewModelScope.launch(Dispatchers.IO) {
            registerNewStudentUseCase.invoke(newStudent).collect { responseStudentsState ->
                when (responseStudentsState) {
                    is ResponseState.Loading -> {
                        _registerNewStudent.postValue(ActionWithStudentState(isLoading = true))
                    }

                    is ResponseState.Success -> {
                        _registerNewStudent.postValue(
                            ActionWithStudentState(isSuccess = true)
                        )
                    }

                    is ResponseState.Error -> {
                        _registerNewStudent.postValue(
                            ActionWithStudentState(error = responseStudentsState.message)
                        )
                    }
                }
            }
        }
    }

    fun getStudentsListAction() {
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

    fun modifyStudentAction(student: StudentEntityDto) {
        viewModelScope.launch(Dispatchers.IO) {
            modifyStudentUseCase.invoke(student).collect { responseStudentsState ->
                when (responseStudentsState) {
                    is ResponseState.Loading -> {
                        _modifyStudent.postValue(ActionWithStudentState(isLoading = true))
                    }

                    is ResponseState.Success -> {
                        _modifyStudent.postValue(
                            ActionWithStudentState(isSuccess = true)
                        )
                    }

                    is ResponseState.Error -> {
                        _modifyStudent.postValue(
                            ActionWithStudentState(error = responseStudentsState.message)
                        )
                    }
                }
            }
        }
    }

    fun deleteStudentAction(student: StudentEntityDto) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteStudentUseCase.invoke(student).collect { responseStudentsState ->
                when (responseStudentsState) {
                    is ResponseState.Loading -> {
                        _deleteStudent.postValue(ActionWithStudentState(isLoading = true))
                    }

                    is ResponseState.Success -> {
                        _deleteStudent.postValue(
                            ActionWithStudentState(isSuccess = true)
                        )
                    }

                    is ResponseState.Error -> {
                        _deleteStudent.postValue(
                            ActionWithStudentState(error = responseStudentsState.message)
                        )
                    }
                }
            }
        }
    }
}
