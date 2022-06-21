package com.truongdc21.quickquotes.utils

import android.widget.TextView
import com.mannan.translateapi.Language
import com.mannan.translateapi.TranslateAPI

fun TextView.translateToVietnam(text : String) {

    val translateAPI = TranslateAPI(
        Language.AUTO_DETECT,
        Language.VIETNAMESE,
        text
    )
    translateAPI.setTranslateListener(object : TranslateAPI.TranslateListener{
        override fun onSuccess(translatedText: String?) {
            this@translateToVietnam.text = translatedText
        }
        override fun onFailure(ErrorText: String?) {
            this@translateToVietnam.text = text
        }
    })
}

fun TextView.translateToEnglish(text : String) {

    val translateAPI = TranslateAPI(
        Language.AUTO_DETECT,
        Language.ENGLISH,
        text
    )
    translateAPI.setTranslateListener(object : TranslateAPI.TranslateListener{
        override fun onSuccess(translatedText: String?) {
            this@translateToEnglish.text = translatedText
        }
        override fun onFailure(ErrorText: String?) {
            this@translateToEnglish.text = text
        }
    })
}