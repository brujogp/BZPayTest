package com.test.testtecnicbzpay.features.featuresinlogin.abc.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.testtecnicbzpay.commons.domain.ResponseState
import com.test.testtecnicbzpay.features.featuresinlogin.abc.domain.GetStudentsUseCase
import com.test.testtecnicbzpay.features.featuresinlogin.abc.presentation.states.StudentsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentsViewModel @Inject constructor(
    private val getStudentsUseCase: GetStudentsUseCase
) : ViewModel() {

    private val _getStudentsState = MutableLiveData<StudentsState>()
    val getStudentsState: LiveData<StudentsState> = _getStudentsState

    fun getStudentsList() {
        viewModelScope.launch {
            /*
            getStudentsUseCase.invoke().collect { responseStudentsState ->
                when (responseStudentsState) {
                    is ResponseState.Loading -> {
                        _getStudentsState.value = StudentsState(isLoading = true)
                    }

                    is ResponseState.Success -> {
                        _getStudentsState.value =
                            StudentsState(studentsList = responseStudentsState.data?.studentsList)
                    }

                    is ResponseState.Error -> {
                        _getStudentsState.value =
                            StudentsState(error = responseStudentsState.message)
                    }
                }
            }
            */
        }
    }
}