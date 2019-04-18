package com.dev.hare.firebasepushmoduletest

import android.app.*
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import com.dev.hare.firebasepushmodule.model.abstracts.AbstractDefaultNotificationModel

class FCMModel(context: Context, data: Map<String, String>, channelID: String, channelName: String) :
    AbstractDefaultNotificationModel(context, data, channelID, channelName) {

    private var _contents_type: String? = null

    init {
        title = data[AbstractDefaultNotificationModel.KEY_TITLE]
        content = data[AbstractDefaultNotificationModel.KEY_CONTENT]
        imageUrl = data[AbstractDefaultNotificationModel.KEY_IMAGE_URL]
        link = data[AbstractDefaultNotificationModel.KEY_LINK]
        _contents_type = data["type"]
    }

    override fun applyNotificationCompatBuilder(notificationCompatBuilder: NotificationCompat.Builder): NotificationCompat.Builder {
        when (_contents_type) {
            "txt" -> {
                NotificationCompat.BigTextStyle(notificationCompatBuilder).run {
                    setBigContentTitle(title)
                    bigText(content)
                    notificationCompatBuilder.setStyle(this)
                }
            }
            "img", "noti_img" -> {
                NotificationCompat.BigPictureStyle(notificationCompatBuilder).run {
                    setBigContentTitle(title)
                    bigPicture(img)
                    if (_contents_type.equals("noti_img"))
                        setSummaryText(content).bigPicture(img)
                    notificationCompatBuilder.setStyle(this)
                }
            }
        }

        return notificationCompatBuilder
    }

    override fun applyNotificationBuilder(notificationBuilder: Notification.Builder): Notification.Builder {
        when (_contents_type) {
            "txt" -> {
                var textStyle = Notification.BigTextStyle(notificationBuilder).apply {
                    setBigContentTitle(title)
                    bigText(content)
                }
                notificationBuilder.style = textStyle
            }
            "img", "noti_img" -> {
                var pictureStyle = Notification.BigPictureStyle(notificationBuilder).apply {
                    setBigContentTitle(title)
                    bigPicture(img)
                    if (_contents_type.equals("noti_img"))
                        setSummaryText(content).bigPicture(img)
                }
                notificationBuilder.style = pictureStyle
            }
        }

        return notificationBuilder
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun createOwnNotification(): Notification {
        return createDefaultOwnNotification()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun createNotificationChannel(notificationManager: NotificationManager): NotificationChannel {
        return createDefaultNotificationChannel(notificationManager)
    }

    override fun createNotificationCompatBuilder(): NotificationCompat.Builder {
        var builder = NotificationCompat.Builder(context, channelID).apply {
            setContentTitle(title)
            setSmallIcon(R.mipmap.ic_launcher)
            setAutoCancel(true)
            setContentIntent(pendingIntent)
            priority = Notification.PRIORITY_MAX
        }
        when (_contents_type) {
            "txt", "noti_img" -> builder.setContentText(content)
        }
        return builder
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun createNotificationBuilder(): Notification.Builder {
        var builder = Notification.Builder(context, channelID).apply {
            setContentTitle(title)
            setSmallIcon(R.mipmap.ic_launcher)
            setAutoCancel(true)
            setContentIntent(pendingIntent)
        }
        when (_contents_type) {
            "txt", "noti_img" -> builder.setContentText(content)
        }
        return builder
    }

    override fun createPendingIntent(activity: Class<out Activity>): PendingIntent {
        return createDefaultPendingIntent(activity)
    }
}