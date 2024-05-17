package com.example.procam

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner


class MainActivity : AppCompatActivity() {
    lateinit var previewView: PreviewView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        get_permission()

        previewView = findViewById(R.id.previewView)
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener(Runnable {
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            bindCameraUseCases(cameraProvider)
        }, ContextCompat.getMainExecutor(this))
    }

    fun bindCameraUseCases(cameraProvider: ProcessCameraProvider) {
        val cameraSelector = CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()

        var preview : Preview = Preview.Builder().build()

        preview.setSurfaceProvider(previewView.getSurfaceProvider())
        var camera  = cameraProvider.bindToLifecycle(this as LifecycleOwner, cameraSelector, preview)
    }

    fun get_permission() {
        val permissionList = mutableListOf<String>()

        if(checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            permissionList.add(android.Manifest.permission.CAMERA)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.MANAGE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                permissionList.add(android.Manifest.permission.MANAGE_EXTERNAL_STORAGE)
        }
        else {
            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                permissionList.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)

            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                permissionList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if(permissionList.size > 0) {
            requestPermissions(permissionList.toTypedArray(), 101)
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        for (it in grantResults) {
            if(it != PackageManager.PERMISSION_GRANTED)
                get_permission()
        }
    }

}