package com.gsm.phoneuse.screenTime

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gsm.phoneuse.persistence.ScreenTimeDatabaseDao

class ScreenTimeViewModelFactory(
    private val dataSource: ScreenTimeDatabaseDao,
    private val application: Application
)
    : ViewModelProvider.Factory
{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScreenTimeViewModel::class.java)) {
            return ScreenTimeViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}