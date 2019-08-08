package com.gsm.phoneuse.screenTime

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.gsm.phoneuse.persistence.ScreenTimeDatabaseDao
import kotlinx.coroutines.*

class ScreenTimeViewModel(
    private val database: ScreenTimeDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private val time = database.getLiveTime()

    val timeString: LiveData<String> = Transformations.map(time){time->
        formatTime(time?:0)
    }
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun onResetTime(){
        uiScope.launch {
            clear()
        }

    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private fun formatTime(time: Long) : String{
        val seconds = (time / 1000).rem(60)
        val minutes = (time / (1000 * 60)).rem(60)
        val hours = (time / (1000 * 60 * 60)) .rem(24)

        var finalTime = ""

        if(hours > 0L){
            finalTime += hours.toString(10) + "h "
        }

        if(minutes > 0L){
            finalTime += minutes.toString(10) + "' "
        }

        finalTime += seconds.toString(10) + "\" "

        return finalTime
    }
}