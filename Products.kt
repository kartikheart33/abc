package com.alexios.android.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize

@Entity(tableName = "Products")
data class Products(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "name")
    var name: String = "",
    @ColumnInfo(name = "price")
    var price: Int = 0,
    @ColumnInfo(name = "description")
    var description: String = "",
    @ColumnInfo(name = "glass")
    var glass:String = "",
    @ColumnInfo(name = "grapetype")
    var grapetype:String = "",
    @ColumnInfo(name = "image")
    var image:String = "",
    @ColumnInfo(name = "veriety")
    var veriety:List<String>?=null,
    @ColumnInfo(name = "country")
    var country:String = "",
    @ColumnInfo(name = "winery")
    var winery:String = "",
    @ColumnInfo(name = "region")
    var region:String = "",
    @ColumnInfo(name = "catid")
    var catid : Int = -1
): Parcelable
