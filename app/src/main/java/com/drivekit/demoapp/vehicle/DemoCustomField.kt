package com.drivekit.demoapp.vehicle

import android.content.Context
import android.text.InputType
import android.text.TextUtils
import com.drivequant.drivekit.databaseutils.entity.Vehicle
import com.drivequant.drivekit.vehicle.ui.vehicledetail.viewmodel.Field

class DemoCustomField : Field {

    override fun getTitle(context: Context, vehicle: Vehicle): String? {
        return "DemoCustomField title"
    }

    override fun getValue(context: Context, vehicle: Vehicle, allVehicles: List<Vehicle>): String? {
        return "Only numeric characters"
    }

    override fun isEditable(): Boolean {
        return true
    }

    override fun getKeyboardType(): Int? {
        return InputType.TYPE_CLASS_NUMBER
    }

    override fun alwaysDisplayable(vehicle: Vehicle): Boolean {
        return true
    }

    override fun isValid(value: String): Boolean {
        return value.toLongOrNull() != null
    }

    override fun onFieldUpdated(vehicle: Vehicle) {
        // TODO:
    }
}