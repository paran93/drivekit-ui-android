package com.drivequant.drivekit.vehicle.ui.vehicles.viewholder

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.*
import com.drivequant.drivekit.databaseutils.entity.DetectionMode
import com.drivequant.drivekit.databaseutils.entity.Vehicle
import com.drivequant.drivekit.vehicle.ui.R
import com.drivequant.drivekit.vehicle.ui.vehicles.viewmodel.DetectionModeType
import com.drivequant.drivekit.vehicle.ui.vehicles.viewmodel.VehiclesListViewModel

class VehicleViewHolder(itemView: View, var viewModel: VehiclesListViewModel) : RecyclerView.ViewHolder(itemView) {
    private val textViewTitle: TextView = itemView.findViewById(R.id.text_view_title)
    private val textViewSubtitle: TextView = itemView.findViewById(R.id.text_view_subtitle)
    private val linearLayoutDetectionMode: LinearLayout = itemView.findViewById(R.id.detection_mode_container)
    private val spinerDetectionMode: Spinner = itemView.findViewById(R.id.spinner_vehicle_detection_mode)
    private val textViewDetectionModeTitle: TextView = itemView.findViewById(R.id.text_view_detection_mode_title)
    private val textViewDetectionModeDescription: TextView = itemView.findViewById(R.id.text_view_detection_mode_description)
    private val buttonSetup: Button = itemView.findViewById(R.id.text_view_setup_button)

    fun bind(vehicle: Vehicle){
        val context = itemView.context
        textViewTitle.text = viewModel.getTitle(context, vehicle)
        textViewSubtitle.text = viewModel.getSubtitle(context, vehicle)
        setupDetectionModeContainer(context, vehicle)
        setupConfigureButton(context, vehicle)
    }

    private fun setupDetectionModeContainer(context: Context, vehicle: Vehicle){
        // TODO via singleton, if detection mode size == 1 then View.GONE else View.VISIBLE
        linearLayoutDetectionMode.visibility = View.VISIBLE

        textViewDetectionModeTitle.text = context.getString(R.string.dk_vehicle_detection_mode_title)
        textViewDetectionModeDescription.text = viewModel.getDetectionModeDescription(context, vehicle)

        // TODO retrieve listOf from Singleton
        val detectionModes = mutableListOf<DetectionModeSpinnerItem>()
        detectionModes.add(buildItem(context, DetectionModeType.DISABLED))
        detectionModes.add(buildItem(context, DetectionModeType.GPS))
        detectionModes.add(buildItem(context, DetectionModeType.BEACON))
        detectionModes.add(buildItem(context, DetectionModeType.BLUETOOTH))

        val adapter: ArrayAdapter<DetectionModeSpinnerItem> = ArrayAdapter(context, R.layout.simple_list_item_spinner, detectionModes)
        spinerDetectionMode.adapter = adapter
    }

    private fun setupConfigureButton(context: Context, vehicle: Vehicle){
         when (vehicle.detectionMode){
             DetectionMode.DISABLED, DetectionMode.GPS -> {
                 buttonSetup.visibility = View.GONE
             }
             DetectionMode.BEACON -> {
                 buttonSetup.visibility = View.VISIBLE
                 buttonSetup.text = context.getString(R.string.dk_vehicle_configure_beacon)
             }
             DetectionMode.BLUETOOTH -> {
                 buttonSetup.visibility = View.VISIBLE
                 buttonSetup.text = context.getString(R.string.dk_vehicle_configure_bluetooth)
             }
         }
    }
}