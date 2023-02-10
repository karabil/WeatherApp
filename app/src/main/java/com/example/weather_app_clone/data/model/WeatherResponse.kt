package com.example.weather_app_clone.data.model

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi

data class WeatherResponse(
    val name: String?,
    val main: Main?
) : Parcelable {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readParcelable(Main::class.java.classLoader, Main::class.java)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeParcelable(main, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WeatherResponse> {
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        override fun createFromParcel(parcel: Parcel): WeatherResponse {
            return WeatherResponse(parcel)
        }

        override fun newArray(size: Int): Array<WeatherResponse?> {
            return arrayOfNulls(size)
        }
    }


}
