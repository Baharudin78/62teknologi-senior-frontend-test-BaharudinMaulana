package com.baharudin.enamduatest.core.util

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast

fun Context.showToast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun withDelay(delay: Long = 100, block: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed(Runnable(block), delay)
}