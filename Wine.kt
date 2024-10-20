package com.alexios.android.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "WinesCategory")
data class Wine (
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int = -1,
    @ColumnInfo(name = "name")
    var name: String = "",
    @ColumnInfo(name = "image")
    var image: String = ""
) : Parcelable
