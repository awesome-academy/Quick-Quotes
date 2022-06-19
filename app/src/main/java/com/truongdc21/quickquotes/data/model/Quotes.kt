package com.truongdc21.quickquotes.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Quotes(
    val id : Int,
    val mQuotes : String ,
    val Author : String,
    val Tag : String,
    val urlImage : String
): Parcelable
