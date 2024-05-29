package com.example.almate.features.fgc.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.almate.features.dashboard.domain.GradeInfoResponse

class FGCViewModel() : ViewModel() {

    var isExpanded by mutableStateOf(false)

}