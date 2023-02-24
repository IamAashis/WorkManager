package com.android.workmanager

import android.content.Context
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.android.workmanager.Utils.makeStatusNotification
import java.lang.Thread.sleep


/**
 * Created by Aashis on 24,February,2023
 */


class NotificationWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    override fun doWork(): Result {
        val appContext = applicationContext

        makeStatusNotification("Worker started work.", appContext)
        sleep(10)
        makeStatusNotification("Worker finshed work.", appContext)

        return Result.success()
    }

}

/*class NotificationWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    *//**
     * Override this method to do your actual background processing.  This method is called on a
     * background thread - you are required to **synchronously** do your work and return the
     * [Result] from this method.  Once you return from this
     * method, the Worker is considered to have finished what its doing and will be destroyed.
     *
     *
     * A Worker is given a maximum of ten minutes to finish its execution and return a
     * [Result].  After this time has expired, the Worker will
     * be signalled to stop.
     *
     * @return The [Result] of the computation; note that
     * dependent work will not execute if you use
     * [Result.failure] or
     *//*
    override fun doWork(): Result {
        val context: Context = ApplicationProvider.getApplicationContext()
        return try {
            Log.d(NotificationWorker.Companion.TAG, "doWork Called")
            Utils.sendNotification(context)
            Result.success()
        } catch (throwable: Throwable) {
            Log.d(NotificationWorker.Companion.TAG, "Error Sending Notification" + throwable.message)
            Result.failure()
        }
    }

    companion object {
        private val TAG = NotificationWorker::class.java.name
    }
}*/
