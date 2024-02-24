package com.jbarros.permissionanalysis.services

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent

object SchedulerRiskAnalysisService {

    private const val JOB_ID = 123

    fun scheduleJob(context: Context) {
        val serviceComponent = ComponentName(context, RiskAnalysisService::class.java)
        val builder = JobInfo.Builder(JOB_ID, serviceComponent)

        // Establece la hora para las 3 a.m.
        builder.setPersisted(true)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .setRequiresCharging(false)
            .setRequiresDeviceIdle(false)
            .setMinimumLatency(getTimeUntil3AM())
            .setOverrideDeadline(getTimeUntil3AM() + 1000) // Le da un margen de 1 segundo

        val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler.schedule(builder.build())
    }

    private fun getTimeUntil3AM(): Long {
        val currentTime = System.currentTimeMillis()
        val calendar = java.util.Calendar.getInstance()
        calendar.timeInMillis = currentTime
        calendar.set(java.util.Calendar.HOUR_OF_DAY, 5)
        calendar.set(java.util.Calendar.MINUTE, 14)
        calendar.set(java.util.Calendar.SECOND, 0)

        if (calendar.timeInMillis <= currentTime) {
            calendar.add(java.util.Calendar.DAY_OF_MONTH, 1)
        }

        return calendar.timeInMillis - currentTime
    }
}