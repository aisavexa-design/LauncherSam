package com.launchersam

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.LruCache
import androidx.core.graphics.createBitmap
import androidx.core.graphics.drawable.toBitmap

object IconCache {

    // About 20 MB limit, suits 4GB RAM phones
    private val cache = object : LruCache<String, Bitmap>(20 * 1024 * 1024) {
        override fun sizeOf(key: String, value: Bitmap): Int = value.byteCount
    }

    fun get(context: Context, packageName: String): Bitmap? {
        cache[packageName]?.let { return it }
        return try {
            val drawable = context.packageManager.getApplicationIcon(packageName)
            val bmp = drawable.toBitmap(128, 128)
            cache.put(packageName, bmp)
            bmp
        } catch (e: Exception) {
            null
        }
    }
}
