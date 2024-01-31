package com.example.moiroom.data

import android.os.Parcel
import android.os.Parcelable

data class Member(
    val memberId: Long,
    val memberProfileImageUrl: String,
    val memberNickname: String,
    val memberGender: String,
    val memberName: String,
    val memberBirthYear: Int,
    val metropolitanName: String,
    val cityName: String,
    val memberIntroduction: String,
    var memberRoomateSearchStatus: Int,
    val socialbility: Int,
    val positivity: Int,
    val activity: Int,
    val communion: Int,
    val altruism: Int,
    val empathy: Int,
    val humor: Int,
    val generous: Int,
    val interest: List<Interest>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.createTypedArrayList(Interest.CREATOR)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(memberId)
        parcel.writeString(memberProfileImageUrl)
        parcel.writeString(memberNickname)
        parcel.writeString(memberGender)
        parcel.writeString(memberName)
        parcel.writeInt(memberBirthYear)
        parcel.writeString(metropolitanName)
        parcel.writeString(cityName)
        parcel.writeString(memberIntroduction)
        parcel.writeInt(memberRoomateSearchStatus)
        parcel.writeInt(socialbility)
        parcel.writeInt(positivity)
        parcel.writeInt(activity)
        parcel.writeInt(communion)
        parcel.writeInt(altruism)
        parcel.writeInt(empathy)
        parcel.writeInt(humor)
        parcel.writeInt(generous)
        parcel.writeTypedList(interest)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Member> {
        override fun createFromParcel(parcel: Parcel): Member {
            return Member(parcel)
        }

        override fun newArray(size: Int): Array<Member?> {
            return arrayOfNulls(size)
        }
    }
}