package com.example.weather_app_clone.data.model

import android.os.Parcel
import android.os.Parcelable

data class Main(
    val temp: Double
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readDouble())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(temp)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Main> {
        override fun createFromParcel(parcel: Parcel): Main {
            return Main(parcel)
        }

        override fun newArray(size: Int): Array<Main?> {
            return arrayOfNulls(size)
        }
    }
}
