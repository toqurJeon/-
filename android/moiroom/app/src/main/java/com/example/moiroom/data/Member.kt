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
    val characteristic: Characteristic,
    val interests: List<Interest>
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
        parcel.readParcelable(Characteristic::class.java.classLoader)!!,
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
        parcel.writeParcelable(characteristic, flags)
        parcel.writeTypedList(interests)
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