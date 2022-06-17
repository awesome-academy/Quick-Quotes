package com.truongdc21.quickquotes.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.showImageGlideWithURL (mContext: Context, url: String){
        Glide.with(mContext).load(url).into(this)
}
