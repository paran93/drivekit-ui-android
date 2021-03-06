package com.drivequant.drivekit.vehicle.ui.picker.fragments

import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.drivequant.drivekit.common.ui.extension.button
import com.drivequant.drivekit.common.ui.extension.normalText
import com.drivequant.drivekit.common.ui.extension.setDKStyle
import com.drivequant.drivekit.common.ui.utils.DKResource
import com.drivequant.drivekit.vehicle.picker.VehicleVersion
import com.drivequant.drivekit.vehicle.ui.R
import com.drivequant.drivekit.vehicle.ui.picker.commons.VehiclePickerStep
import com.drivequant.drivekit.vehicle.ui.picker.viewmodel.VehiclePickerViewModel
import kotlinx.android.synthetic.main.fragment_vehicle_name_chooser.*

class VehicleNameChooserFragment : Fragment() {

    private lateinit var viewModel: VehiclePickerViewModel
    private lateinit var vehicleVersion: VehicleVersion

    companion object {
        fun newInstance(
            viewModel: VehiclePickerViewModel)
                : VehicleNameChooserFragment {
            val fragment = VehicleNameChooserFragment()
            fragment.viewModel = viewModel
            fragment.vehicleVersion = viewModel.selectedVersion
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_vehicle_name_chooser, container, false).setDKStyle()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        image_view.setImageDrawable(DKResource.convertToDrawable(requireContext(), "dk_vehicle_name_chooser"))
        text_view_description.normalText()
        text_view_description.text = DKResource.convertToString(requireContext(), "dk_vehicle_name_chooser_description")

        val editTextWrapper = view.findViewById(R.id.text_input_layout) as TextInputLayout
        editTextWrapper.editText?.setText(viewModel.getDefaultVehicleName())
        button_validate.button()
        button_validate.text = DKResource.convertToString(requireContext(), "dk_common_validate")
        button_validate.setOnClickListener {
            viewModel.name = editTextWrapper.editText?.editableText.toString()
            viewModel.computeNextScreen(requireContext(), VehiclePickerStep.NAME)
        }
    }
}