package com.drivequant.drivekit.permissionsutils.commons.views

import android.content.Context
import android.content.DialogInterface
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.drivequant.drivekit.common.ui.DriveKitUI
import com.drivequant.drivekit.common.ui.extension.headLine1
import com.drivequant.drivekit.common.ui.extension.normalText
import com.drivequant.drivekit.common.ui.utils.DKAlertDialog
import com.drivequant.drivekit.common.ui.utils.DKResource
import com.drivequant.drivekit.permissionsutils.R

/**
 * Created by Mohamed on 2020-04-16.
 */
// Copyright (c) 2020 DriveQuant. All rights reserved.

class DiagnosisItemView : LinearLayout {

    private var textViewDiagnosisTitle: TextView? = null
    private var textViewDiagnosisSubTitle: TextView? = null
    private var imageViewDiagnosis: ImageView? = null

    private lateinit var diagnosisTitle: String
    private lateinit var diagnosisSubtitle: String
    private lateinit var diagnosisTextOK: String
    private lateinit var diagnosisTextKO: String
    private lateinit var diagnosisLink: String

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        val view = View.inflate(context, R.layout.diagnosis_item_view, null)
        textViewDiagnosisTitle = view.findViewById(R.id.text_view_diagnosis_title)
        textViewDiagnosisSubTitle = view.findViewById(R.id.text_view_diagnosis_subtitle)
        imageViewDiagnosis = view.findViewById(R.id.image_view_diagnosis)

        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.DiagnosisItemView, 0, 0)
            try {

                a.getString(R.styleable.DiagnosisItemView_diagnosisTitle)?.let {
                    diagnosisTitle = it
                }

                a.getString(R.styleable.DiagnosisItemView_diagnosisSubtitle)?.let {
                    diagnosisSubtitle = it
                }

                a.getString(R.styleable.DiagnosisItemView_diagnosisTextOK)?.let {
                    diagnosisTextOK = it
                }

                a.getString(R.styleable.DiagnosisItemView_diagnosisTextKO)?.let {
                    diagnosisTextKO = it
                }

                a.getString(R.styleable.DiagnosisItemView_diagnosisLink)?.let {
                    diagnosisLink = it
                }

                textViewDiagnosisTitle?.let {
                    it.text = diagnosisTitle
                    it.normalText()
                }

                textViewDiagnosisSubTitle?.let {
                    it.text = diagnosisSubtitle
                    it.normalText(DriveKitUI.colors.secondaryColor())
                }

            } finally {
                a.recycle()
            }
        }

        addView(
            view, ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
    }

    fun getDiagnosisTitle(): String = diagnosisTitle

    private fun getDiagnosticTextOK(): String = diagnosisTextOK

    fun getDiagnosticTextKO(): String = diagnosisTextKO

    fun getDiagnosisLink(): String = diagnosisLink

    fun setDiagnosisDrawable(hasProblem: Boolean) {
        val drawableItem = if (hasProblem) {
            DKResource.convertToDrawable(context, "dk_perm_utils_high_priority_generic")
        } else {
            DKResource.convertToDrawable(context, "dk_perm_utils_checked_generic")
        }

        val color = if (hasProblem) {
            DriveKitUI.colors.criticalColor()
        } else {
            ContextCompat.getColor(context, R.color.dkValid)
        }

        val wrapped = DrawableCompat.wrap(drawableItem!!)
        DrawableCompat.setTint(wrapped, color)
        imageViewDiagnosis?.setImageDrawable(wrapped)
    }

    fun setNormalState() {
        setDiagnosisDrawable(false)
        this.setOnClickListener {
            val infoDiagnosis = DKAlertDialog.LayoutBuilder()
                .init(context)
                .layout(R.layout.template_alert_dialog_layout)
                .cancelable(false)
                .positiveButton(context.getString(R.string.dk_common_ok),
                    DialogInterface.OnClickListener { dialog, _ ->
                        dialog.dismiss()
                    }).show()

            val titleTextView = infoDiagnosis.findViewById<TextView>(R.id.text_view_alert_title)
            val descriptionTextView =
                infoDiagnosis.findViewById<TextView>(R.id.text_view_alert_description)

            titleTextView?.text = this.getDiagnosisTitle()
            descriptionTextView?.text = this.getDiagnosticTextOK()
            titleTextView?.headLine1()
            descriptionTextView?.normalText()
        }
    }
}