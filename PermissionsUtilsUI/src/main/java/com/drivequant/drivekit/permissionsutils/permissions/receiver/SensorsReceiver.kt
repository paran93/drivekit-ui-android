package com.drivequant.drivekit.permissionsutils.permissions.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.drivequant.drivekit.permissionsutils.diagnosis.DiagnosisHelper
import com.drivequant.drivekit.permissionsutils.diagnosis.model.SensorType

/**
 * Created by Mohamed on 2020-04-16.
 */
// Copyright (c) 2020 DriveQuant. All rights reserved.

abstract class SensorsReceiver : BroadcastReceiver() {

    companion object {
        const val LOCATION_INTENT_ACTION = "android.location.PROVIDERS_CHANGED"
        const val BLUETOOTH_INTENT_ACTION = "android.bluetooth.adapter.action.STATE_CHANGED"
        const val CONNECTIVITY_INTENT_ACTION = "android.net.conn.CONNECTIVITY_CHANGE"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            init(it)
        }

        intent?.let { it ->
            context?.let { context ->
                when (it.action) {
                    LOCATION_INTENT_ACTION  -> getGpsState(DiagnosisHelper.isSensorActivated(context, SensorType.GPS))
                    CONNECTIVITY_INTENT_ACTION -> getConnectivityState(DiagnosisHelper.isNetworkReachable(context))
                    BLUETOOTH_INTENT_ACTION -> getBluetoothState(DiagnosisHelper.isSensorActivated(context, SensorType.BLUETOOTH))
                }
            }
        }
        onSensorBroadcastReceived()
    }

    abstract fun getGpsState(gpsState:Boolean)

    abstract fun getBluetoothState(bluetoothState: Boolean)

    abstract fun getConnectivityState(connectivityState: Boolean)

    abstract fun onSensorBroadcastReceived()

    private fun init(context: Context) {
        getGpsState(DiagnosisHelper.isSensorActivated(context, SensorType.GPS))
        getConnectivityState(DiagnosisHelper.isNetworkReachable(context))
        getBluetoothState(DiagnosisHelper.isSensorActivated(context, SensorType.BLUETOOTH))
    }
}
