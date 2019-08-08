package com.gsm.phoneuse.utils

class StopWatch {
    private var initTime = 0L

    fun start(){
        initTime = System.currentTimeMillis()
    }

    fun stop() : Long{
        if (initTime != 0L) {
            val endTime = System.currentTimeMillis()
            return endTime - initTime
        }
        return 0


    }
}