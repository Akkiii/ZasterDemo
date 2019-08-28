package com.zasterapp.common.extension

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService

fun Context.dismissKeyboard(view: View) {
    val imm: InputMethodManager? = getSystemService()
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}

//fun Context.copyToClipBoard(text: String) {
//    var clipboard: ClipboardManager? = getSystemService()
//    val clip = ClipData.newPlainText(text, text)
//    clipboard?.primaryClip = clip
//}

fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.isLocationPermissionGranted(): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}


fun Context.callPhoneNumber(number: String) {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:$number")
    startActivity(intent)
}

fun Context.sendSms(number: String) {
    startActivity(Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)))
}
