package com.truongdc21.quickquotes.utils

import android.view.View
import android.view.animation.AlphaAnimation


fun View.setAnimationClick(){
    this.startAnimation(AlphaAnimation(9f, 0.1f))
}
