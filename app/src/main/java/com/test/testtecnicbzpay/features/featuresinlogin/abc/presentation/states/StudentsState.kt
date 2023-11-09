package com.test.testtecnicbzpay.features.featuresinlogin.abc.presentation.states

import com.test.testtecnicbzpay.features.featuresinlogin.abc.data.database.entities.Student

data class StudentsState(
    var isLoading: Boolean = false,
    var studentsList: List<Student>? = null,
    var error: String? = null
)