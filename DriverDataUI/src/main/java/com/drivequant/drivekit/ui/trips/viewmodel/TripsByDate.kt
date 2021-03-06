package com.drivequant.drivekit.ui.trips.viewmodel

import com.drivequant.drivekit.databaseutils.entity.Trip
import java.util.*

data class TripsByDate(
    val date: Date,
    val trips: List<Trip>)