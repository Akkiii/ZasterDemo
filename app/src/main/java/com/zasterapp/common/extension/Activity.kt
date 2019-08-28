package co.asisten.android.common.extension

import android.Manifest
import android.app.Activity
import androidx.core.app.ActivityCompat


fun Activity.requestLocationPermission(requestCode: Int, showRationale: () -> Unit) {
    requestPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION, requestCode, showRationale)
}

private fun requestPermissions(activity: Activity, permission: String, requestCode: Int, showRationale: () -> Unit) {
    if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
        showRationale.invoke()
    }
    ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
}
