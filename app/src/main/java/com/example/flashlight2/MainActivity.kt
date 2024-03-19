package com.example.flashlight2

import android.content.Context
import android.hardware.camera2.CameraCharacteristics.FLASH_INFO_AVAILABLE
import android.hardware.camera2.CameraManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.flashlight2.ui.theme.Flashlight2Theme

class MainActivity : ComponentActivity() {
    val camMan by lazy { getSystemService(Context.CAMERA_SERVICE) as CameraManager }
    var camIdWithFlash: String = "0"

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Flashlight2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                }
            }
        }
        val camList = camMan.cameraIdList
        camList.forEach {
            val characteristics = camMan.getCameraCharacteristics(it)
            val doesCameHasFlash: Boolean? = characteristics.get(FLASH_INFO_AVAILABLE)
            if (camIdWithFlash == "0" && doesCameHasFlash == true) {
                camIdWithFlash = it
            }
        }
        camMan.setTorchMode(camIdWithFlash, true)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onPause() {
        super.onPause()
        camMan.setTorchMode(camIdWithFlash, false)
    }
}
