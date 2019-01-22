package com.example.grzegorz.androidmvvm.helpers

import android.content.res.Resources
import androidx.annotation.StringRes
import com.example.grzegorz.androidmvvm.R

class ErrorMessage {

    @StringRes
    private val resource: Int?
    private val message: String?

    fun getMessage(resources: Resources): String? {
        return message ?: if (resource != null) resources.getString(resource)
        else null
    }

    constructor(@StringRes res: Int = R.string.default_error) {
        resource = res
        message = null
    }

    constructor(text: String, code: Int? = null) {
        this.message = text
        resource = null
    }
}