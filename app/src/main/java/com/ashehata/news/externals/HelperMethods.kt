package com.ashehata.news.externals

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showMessage(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}