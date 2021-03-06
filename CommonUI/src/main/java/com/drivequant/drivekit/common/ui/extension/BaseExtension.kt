package com.drivequant.drivekit.common.ui.extension

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.v4.graphics.drawable.DrawableCompat
import com.drivequant.drivekit.common.ui.DriveKitUI
import android.view.View
import com.drivequant.drivekit.common.ui.utils.FontUtils
import com.drivequant.drivekit.common.ui.utils.ResSpans

inline fun Context.resSpans(options: ResSpans.() -> Unit) = ResSpans(this).apply(options)

fun Drawable.tintDrawable(color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        DrawableCompat.setTint(this, color)
    } else {
        this.mutate().setColorFilter(color, PorterDuff.Mode.SRC_IN)
    }
}

fun View.setDKStyle(): View {
    FontUtils.overrideFonts(this.context, this)
    this.setBackgroundColor(DriveKitUI.colors.backgroundViewColor())
    return this
}