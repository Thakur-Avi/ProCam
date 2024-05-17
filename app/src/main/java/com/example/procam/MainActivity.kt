package com.example.procam

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        get_permission()
    }

    fun get_permission() {
        var permissionList = mutableListOf<String>()

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