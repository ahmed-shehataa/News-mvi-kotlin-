package com.ashehata.news.externals

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.ashehata.news.R
import com.bumptech.glide.load.HttpException
import com.google.android.material.snackbar.Snackbar
import com.itextpdf.text.Document
import com.itextpdf.text.Font
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

fun Activity.allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
    ContextCompat.checkSelfPermission(
        baseContext, it
    ) == PackageManager.PERMISSION_GRANTED
}

private fun Context.getOutputDirectory(): File {
    val mediaDir = externalMediaDirs.firstOrNull()?.let {
        File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
    }
    return if (mediaDir != null && mediaDir.exists())
        mediaDir else filesDir
}

fun Context.stringToPDF(string: String) {
    val doc = Document()
    try {
        val outputFile = File(
            getOutputDirectory(), SimpleDateFormat(
                FILENAME_FORMAT, Locale.US
            ).format(System.currentTimeMillis()) + ".pdf"
        )

        val fos = FileOutputStream(outputFile)
        PdfWriter.getInstance(doc, fos)

        //open the document
        doc.open()

        /* Create Paragraph and S`enter code here`et Font */
        val p1 = Paragraph(string)

        /* Create Set Font and its Size */
        val paraFont = Font()
        //paraFont.setFamily(Font.BOLD)
        paraFont.size = 16F
        p1.alignment = Paragraph.ALIGN_CENTER
        p1.font = paraFont

        //add paragraph to document
        doc.add(p1)

        Toast.makeText(this, "Created successfully", Toast.LENGTH_SHORT).show()
        Log.i("bytesToPDF: ", outputFile.path)

    } catch (e: java.lang.Exception) {
        Log.e("bytesToPDF: ", e.localizedMessage.toString())
        Toast.makeText(this, "Failed to create", Toast.LENGTH_SHORT).show()

    } finally {
        doc.close()
    }
}

fun View.showMessage(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> T
): Result<T> {
    return withContext(dispatcher) {
        try {
            Result.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> Result.NetworkError
                is HttpException -> {
                    val code = throwable.statusCode
                    val errorResponse = convertErrorBody(throwable)
                    Result.GenericError(code, errorResponse)
                }
                else -> {
                    Result.GenericError(null)
                }
            }
        }
    }
}

private fun convertErrorBody(throwable: HttpException): ErrorResponse {
    return try {
        throwable.message?.let {
            val moshiAdapter = Moshi.Builder().build().adapter(ErrorResponse::class.java)
            moshiAdapter.fromJson(it)
        }!!
    } catch (exception: Exception) {
        ErrorResponse("")
    }
}