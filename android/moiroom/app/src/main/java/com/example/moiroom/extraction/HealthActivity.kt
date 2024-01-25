package com.example.moiroom.extraction

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.health.connect.client.records.HeartRateSeries
import androidx.health.connect.client.records.Steps
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthDataRequestPermissions
import androidx.lifecycle.lifecycleScope
import com.example.moiroom.R
import com.example.moiroom.databinding.ActivityJaeeontestBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.request.OnDataPointListener
import com.google.android.gms.fitness.request.SensorRequest
import com.google.android.gms.fitness.result.DataReadResponse
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.gms.fitness.data.Field
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class HealthActivity: AppCompatActivity() {
//    val PERMISSIONS =
//        setOf(
//            Permission.createReadPermission(HeartRateSeries::class),
//            Permission.createWritePermission(HeartRateSeries::class),
//            Permission.createReadPermission(Steps::class),
//            Permission.createWritePermission(Steps::class)
//        )
    // Create the permissions launcher.
//    val requestPermissions =
//        registerForActivityResult(HealthDataRequestPermissions()) { granted ->
//            if (granted.containsAll(PERMISSIONS)) {
//                // Permissions successfully granted
//            } else {
//                // Lack of required permissions
//            }
//        }

//    fun checkPermissionsAndRun(client: HealthConnectClient) {
//        lifecycleScope.launch {
//            val granted = client.permissionController.getGrantedPermissions(PERMISSIONS)
//            if (granted.containsAll(PERMISSIONS)) {
//                // Permissions already granted
//            } else {
//                requestPermissions.launch(PERMISSIONS)
//            }
//        }
//    }

}

