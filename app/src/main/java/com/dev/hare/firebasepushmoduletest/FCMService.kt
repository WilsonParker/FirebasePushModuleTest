package com.dev.hare.firebasepushmoduletest

import android.graphics.Bitmap
import com.dev.hare.firebasepushmodule.model.abstracts.AbstractDefaultNotificationModel
import com.dev.hare.firebasepushmodule.service.abstracts.AbstractImageDownloadService
import com.dev.hare.firebasepushmodule.util.ImageUtilUsingThread

class FCMService : AbstractImageDownloadService() {

    override fun createNotificationModel(data: Map<String, String>?): AbstractDefaultNotificationModel {
        return FCMModel(this, data!!, "FirebasePushModule_ID", "FirebasePushModule_Name")
    }

    override fun createOnImageLoadCompleteListener(): ImageUtilUsingThread.OnImageLoadCompleteListener {
        return object : ImageUtilUsingThread.OnImageLoadCompleteListener {
            override fun onComplete(bitmap: Bitmap?) {
                model?.run {
                    img = bitmap
                    runNotification()
                    notifyStop()
                }
            }
        }
    }
}