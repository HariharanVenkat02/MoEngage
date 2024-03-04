package com.example.moengagearticles.Utils

import android.content.Context
import android.widget.Toast

    fun handleApiError(context: Context, errorCode: Int) {
        // Handle API error based on the HTTP status code
        when (errorCode) {
            404 -> {
                showToast(context, "Resource not found. Please try again later.")
            }
            500 -> {
                showToast(context, "Internal server error. Please try again later.")
            }
            else -> {
                showToast(context, "An unexpected error occurred. Please try again later.")
            }
        }
    }
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
