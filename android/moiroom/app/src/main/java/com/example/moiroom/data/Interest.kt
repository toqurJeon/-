package com.example.moiroom.data

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.Serializable

@Serializable
data class Interest(
    val interestName: String,
    val interestPercent: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(interestName)
        parcel.writeInt(interestPercent)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Interest> {
        override fun createFromParcel(parcel: Parcel): Interest {
            return Interest(parcel)
        }

        override fun newArray(size: Int): Array<Interest?> {
            return arrayOfNulls(size)
        }
    }
}
