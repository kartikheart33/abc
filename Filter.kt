package com.alexios.android.models

import android.os.Parcelable
import androidx.exifinterface.media.ExifInterface.IfdType
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize

@Entity(tableName = "Filter")
data class Filter(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "veriety")
    var veriety:List<String>?=null,
    @ColumnInfo(name = "bytheglass")
    var bytheglass:List<String>?=null,
    @ColumnInfo(name = "country")
    var country:List<String>?=null,
    @ColumnInfo(name = "price")
    var price:List<String>?=null,
): Parcelable
