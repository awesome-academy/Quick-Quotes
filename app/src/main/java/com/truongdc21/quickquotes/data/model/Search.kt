package com.truongdc21.quickquotes.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Search (
    val id : Int?,
    val key : String?,
    val text : String?,
    val type : String?
    ): Parcelable
