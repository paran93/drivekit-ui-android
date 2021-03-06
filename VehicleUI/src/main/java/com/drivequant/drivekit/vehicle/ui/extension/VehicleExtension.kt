package com.drivequant.drivekit.vehicle.ui.extension

import android.content.Context
import android.text.TextUtils
import com.drivequant.drivekit.common.ui.utils.DKResource
import com.drivequant.drivekit.databaseutils.entity.DetectionMode.*
import com.drivequant.drivekit.databaseutils.entity.Vehicle
import com.drivequant.drivekit.vehicle.enums.VehicleCategory
import com.drivequant.drivekit.vehicle.enums.VehicleEngineIndex
import com.drivequant.drivekit.vehicle.ui.picker.viewmodel.VehicleTypeItem
import com.drivequant.drivekit.vehicle.ui.vehicles.utils.VehicleUtils
import com.drivequant.drivekit.vehicle.ui.vehicles.viewmodel.DetectionModeType

fun Vehicle.buildFormattedName(context: Context) : String {
    val sortedVehicles = VehicleUtils().fetchVehiclesOrderedByDisplayName(context)
    return if (!TextUtils.isEmpty(name) && !VehicleUtils().isNameEqualsDefaultName(this)) {
        name?.let { it }?:run { " " }
    } else {
        val vehiclePositionInList = sortedVehicles.indexOf(this) + 1
        val myVehicleString = DKResource.convertToString(context, "dk_vehicle_my_vehicle")
        "$myVehicleString - $vehiclePositionInList"
    }
}

fun Vehicle.computeSubtitle(context: Context): String? {
    val title = this.buildFormattedName(context)
    var subtitle: String? = "$brand $model $version"

    if (liteConfig){
        VehicleTypeItem.CAR.getCategories(context).first { it.liteConfigDqIndex == dqIndex}.title?.let { categoryName ->
            subtitle = if (categoryName.equals(title, true)){
                null
            } else {
                categoryName
            }
        }
    }
    return subtitle
}

fun Vehicle.isConfigured(): Boolean {
    return when (detectionMode){
        DISABLED, GPS -> true
        BEACON -> beacon != null
        BLUETOOTH -> bluetooth != null
    }
}

fun Vehicle.getDeviceDisplayIdentifier(): String {
    return when (detectionMode){
        DISABLED, GPS -> ""
        BEACON -> beacon?.code ?: run { "" }
        BLUETOOTH -> bluetooth?.name ?: run { "" }
    }
}

fun Vehicle.getDetectionModeName(context: Context): String {
    return DetectionModeType.getEnumByDetectionMode(detectionMode).getTitle(context)
}

fun Vehicle.getCategoryName(context: Context): String? {
    return VehicleCategory.getEnumByTypeIndex(typeIndex)?.let { vehicleCategory ->
        val categories = VehicleTypeItem.CAR.getCategories(context)
        val matchedCategory = categories.first {
            vehicleCategory .name == it.category
        }
        matchedCategory.title
    } ?: run { "-" }
}

fun Vehicle.getEngineTypeName(context: Context): String? {
    val engineIndexes = VehicleTypeItem.CAR.getEngineIndexes(context)
    val matchedEngineIndex = engineIndexes.first {
        VehicleEngineIndex.getEnumByValue(engineIndex) == it.engine
    }
    return matchedEngineIndex.title
}

fun Vehicle.getGearBoxName(context: Context): String? {
    val identifier = when (gearboxIndex){
        1 -> "dk_gearbox_automatic"
        2 -> "dk_gearbox_manual_5"
        3 -> "dk_gearbox_manual_6"
        4 -> "dk_gearbox_manual_7"
        5 -> "dk_gearbox_manual_8"
        else -> null
    }
    return if (identifier == null){
        "-"
    } else {
        DKResource.convertToString(context, identifier)
    }
}