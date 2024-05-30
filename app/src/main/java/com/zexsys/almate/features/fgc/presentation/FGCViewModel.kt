package com.zexsys.almate.features.fgc.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class FGCViewModel() : ViewModel() {

    var targetPercentage by mutableStateOf("")

    fun calculateNeeded(
        current: String,
        goal:  String
    ): Double {
        return -4 * current.toDouble() + 5 * goal.toDouble()
    }

}