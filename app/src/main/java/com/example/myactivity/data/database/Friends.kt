package com.example.myactivity.data.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Friends(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "friend_id")
    var friendId: Int? = null,

    @ColumnInfo(name = "friend_name")
    var name: String,

    @ColumnInfo(name = "friend_email")
    var email: String
) : Parcelable