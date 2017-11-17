package com.talisman.app.utils

import android.content.Context
import android.os.AsyncTask
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * Created by Tarun on 11/16/17.
 */
object KeyboardUtils {
    /**
     * Hide keyboard.

     * <pre>
     * `KeyboardUtils.hideKeyboard(getActivity(), searchField);`
    </pre> *
     */
    fun hideKeyboard(context: Context, field: EditText) {
        val imm = context.getSystemService(
                Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(field.windowToken, 0)
    }


    /**
     * Show keyboard with a custom delay.

     * <pre>
     * `KeyboardUtils.showDelayedKeyboard(getActivity(), searchField, 500);`
    </pre> *
     */
    @JvmOverloads fun showDelayedKeyboard(context: Context, view: View, delay: Int = 100) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg params: Void): Void? {
                try {
                    Thread.sleep(delay.toLong())
                } catch (e: InterruptedException) {
                }
                return null
            }

            override fun onPostExecute(result: Void) {
                val imm = context.getSystemService(
                        Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
            }
        }.execute()
    }
}
/**
 * Show keyboard with a 100ms delay.

 * <pre>
 * `KeyboardUtils.showDelayedKeyboard(getActivity(), searchField);`
</pre> *
 */
