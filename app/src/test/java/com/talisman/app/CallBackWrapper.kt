package com.talisman.app

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.subscribers.DisposableSubscriber
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.lang.ref.WeakReference
import java.net.SocketTimeoutException

/**
 * Created by Tarun on 11/17/17.
 */
abstract class CallbackWrapper<T : BaseResponse>(view: BaseView) : DisposableSubscriber<T>() {
    //BaseView is just a reference of a View in MVP
    private val weakReference: WeakReference<BaseView> = WeakReference(view)
    private var error =""

    protected abstract fun onSuccess(t: T)

    override fun onNext(t: T) {
        //You can return StatusCodes of different cases from your API and handle it here. I usually include these cases on BaseResponse and iherit it from every NotificationApiResponse
        onSuccess(t)
    }

    override fun onError(e: Throwable) {
        val view = weakReference.get()
        if (view != null) {
            view.hideProgress()
            when (e) {
                is HttpException -> {
                    if (e.response() != null && e.response().errorBody() != null) {
                        val responseBody = e.response().errorBody()
                        view.onUnknownError(getErrorMessage(responseBody!!)!!, error)
                    } else {
                        view.onUnknownError(e.message!!,"")
                    }
                }
                is SocketTimeoutException -> view.onTimeout()
                is IOException -> view.onNetworkError()
                else -> if(e!=null && e.message!=null) view.onUnknownError(e.message!!,"")
            }
        }
    }


    override fun onComplete() {

    }

    private fun getErrorMessage(responseBody: ResponseBody): String? {
        return try {
            val jsonObject = JSONObject(responseBody.string())
            if(jsonObject.has("error"))
                error= jsonObject.getString("error")
            when {
                jsonObject.has("errors") -> {
                    val iter = jsonObject.getJSONObject("errors").keys()
                    var key: String? = null
                    while (iter.hasNext()) {
                        key = iter.next()
                        break
                    }

                    return JSONArray(jsonObject.getJSONObject("errors").get(key).toString())[0].toString()

                }
            //   jsonObject.getString("errors") != null -> return jsonObject.getString("errors")
                jsonObject.has("message") -> jsonObject.getString("message")
                else -> jsonObject.getString("error")

            }
        } catch (e: Exception) {
            e.message
        }

    }


}