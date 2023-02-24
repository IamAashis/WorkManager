package com.android.workmanager

import android.content.Context
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

