package com.example.tarun.kotlin

/**
 * Created by Tarun on 11/16/17.
 */
import android.animation.Animator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.text.DecimalFormat
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import java.util.regex.Pattern

/**
 * Created by aditlal on 04/07/17.
 */

private val c = AtomicInteger(0)


fun getID(): Int {
    return c.incrementAndGet()
}


fun Int.isOdd() = this.rem(2) == 1

fun Int.isEven() = this.rem(2) == 0
fun Int.format(digits: Int) = String.format("%0${digits}d%n", this)

fun Boolean.asInt(): Int {
    return if (this) 1 else 0
}

fun Int.asBoolean(): Boolean {
    return (this == 1)
}

fun Long.toMinutes() = TimeUnit.MILLISECONDS.toMinutes(this)
fun Long.toSeconds() = TimeUnit.MILLISECONDS.toSeconds(this) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(this))

inline fun supportsLollipop(func: () -> Unit) =
        supportsVersion(Build.VERSION_CODES.LOLLIPOP, func)

fun isLollipopOrBellow(): Boolean = (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.LOLLIPOP)

inline fun supportsVersion(ver: Int, func: () -> Unit) {
    if (Build.VERSION.SDK_INT >= ver) {
        func.invoke()
    }
}

inline fun inReleaseMode(func: () -> Unit) {
    if (BuildConfig.BUILD_TYPE.equals("release")) {
        func()
    }
}

inline fun inDebugMode(func: () -> Unit) {
    if (BuildConfig.BUILD_TYPE.equals("debug")) {
        func()
    }
}


inline fun ObjectAnimator.onStart(crossinline func: () -> Unit) {
    addListener(object : Animator.AnimatorListener {
        override fun onAnimationRepeat(animation: Animator?) {}
        override fun onAnimationEnd(animation: Animator?) {}
        override fun onAnimationCancel(animation: Animator?) {}
        override fun onAnimationStart(animation: Animator?) {
            func()
        }
    })
}

inline fun ObjectAnimator.onEnd(crossinline func: () -> Unit) {
    addListener(object : Animator.AnimatorListener {
        override fun onAnimationRepeat(animation: Animator?) {}
        override fun onAnimationEnd(animation: Animator?) {
            func()
        }

        override fun onAnimationCancel(animation: Animator?) {}
        override fun onAnimationStart(animation: Animator?) {}
    })
}

fun Intent.hasExtras(extras: List<String>): Boolean = extras.all { hasExtra(it) }

fun formatDate(modelDateTime: DateTime, today: DateTime, yesterday: DateTime): String {
    val builder = DateTimeFormat.forPattern("h:mma")
    if (modelDateTime.toLocalDate() == today.toLocalDate()) {
        return "Today" + ", " +builder.print(modelDateTime).replace(".","")
    } else if (modelDateTime.toLocalDate() == yesterday.toLocalDate()) {
        return "Yest" + ", " +builder.print(modelDateTime).replace(".","")
    } else {

        return modelDateTime.toString("dd") + " " + modelDateTime.monthOfYear().asText.substring(0, 3) + ", " +builder.print(modelDateTime).replace(".","")
    }
}

fun getHrsDuration(seconds: Int): String {
    return if(seconds > 60){
        val minutes = seconds / 60
        if(minutes > 60) {
            val hours = minutes / 60
            val decimalMinutes = minutes % 60
            if(decimalMinutes>10) {
                hours.toString() + ":" + minutes + " HOUR"
            }else{
                hours.toString() + ":0" + decimalMinutes + " HOUR"
            }
        }else{
            "00" + ":0" + minutes.toString() + " HOUR"
        }

    }else{
        "00:01 HOUR"
    }
}

fun calculateDuration(start: DateTime, end: DateTime): Long { //should return secs
    val millis = end.millis - start.millis
    return TimeUnit.MILLISECONDS.toSeconds(millis)
}

fun getFormattedDateTime(dateString: String): DateTime {
    return DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(dateString)
}

fun isEmailValid(email: String): Boolean {
    val sb = StringBuilder()
    val regExpn = sb.append("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@")
            .append("((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?")
            .append("[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.")
            .append("([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?")
            .append("[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|")
            .append("([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$")

    val inputStr = email

    @SuppressLint("WrongConstant") val pattern = Pattern.compile(regExpn.toString(), Pattern.CASE_INSENSITIVE)
    val matcher = pattern.matcher(inputStr)

    return matcher.matches()
}

fun getKeyFromValue(hm: Map<*, *>, value: Any): Any? {
    return hm.keys.firstOrNull { hm[it] == value }
}

var df = DecimalFormat("#")

fun getSingleDecimalFormatValue(): DecimalFormat {
    val df_value = DecimalFormat("#.#")
    df_value.minimumFractionDigits = 1
    df_value.maximumFractionDigits = 1
    return df_value

}
fun getDoubleDecimalFormatValue(): DecimalFormat {
    val df_value_two_decimal = DecimalFormat("#.##")
    df_value_two_decimal.minimumFractionDigits = 2
    df_value_two_decimal.maximumFractionDigits = 2
    return df_value_two_decimal

}


fun isOnline(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = connectivityManager.activeNetworkInfo
    return netInfo != null && netInfo.isConnected
}


fun convertToKiloMetres(distance : Float) : Float{
    return distance / 1000
}

fun convertToLitres(fuel : Float) : Float{
    return fuel / 1000
}





