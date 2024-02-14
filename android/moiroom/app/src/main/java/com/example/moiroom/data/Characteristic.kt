package com.example.moiroom.data

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.Serializable

@Serializable
data class Characteristic(
    val sociability: Int,
    val positivity: Int,
    val activity: Int,
    val communion: Int,
    val altruism: Int,
    val empathy: Int,
    val humor: Int,
    val generous: Int,
    val sleepAt: String?,
    val wakeUpAt: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(sociability)
        parcel.writeInt(positivity)
        parcel.writeInt(activity)
        parcel.writeInt(communion)
        parcel.writeInt(altruism)
        parcel.writeInt(empathy)
        parcel.writeInt(humor)
        parcel.writeInt(generous)
        parcel.writeString(sleepAt)
        parcel.writeString(wakeUpAt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Characteristic> {
        override fun createFromParcel(parcel: Parcel): Characteristic {
            return Characteristic(parcel)
        }

        override fun newArray(size: Int): Array<Characteristic?> {
            return arrayOfNulls(size)
        }
    }

    fun getNonNullSleepAt(): String {
        return sleepAt ?: "11:30"
    }
    fun getNonNullWakeUpAt(): String {
        return wakeUpAt ?: "06:30"
    }
}

