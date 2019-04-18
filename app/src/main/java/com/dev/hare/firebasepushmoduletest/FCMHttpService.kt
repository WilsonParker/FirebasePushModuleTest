package com.dev.hare.firebasepushmoduletest

import com.dev.hare.firebasepushmodule.http.abstracts.AbstractCallService
import com.dev.hare.firebasepushmodule.http.model.HttpResultModel
import com.dev.hare.firebasepushmodule.util.Logger

object FCMHttpService: AbstractCallService() {
    override val baseUrl: String
        get() = "http://enter-api.virtual.localhost:8001"

    override fun onInsertTokenSuccess(result: HttpResultModel?) {
        Logger.log(Logger.LogType.INFO, "token sequence : ${result.toString()}")
    }

    override fun onUpdateTokenWithUserCodeSuccess(result: HttpResultModel?) {
        Logger.log(Logger.LogType.INFO, "update is success? : ${result.toString()}")
    }

    override fun onUpdateTokenWithAgreementSuccess(result: HttpResultModel?) {
        Logger.log(Logger.LogType.INFO, "update is success? : ${result.toString()}")
    }
}