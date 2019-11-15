package io.github.dvegasa.datasetsvisualization

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View

/**
 * 15.11.2019
 */
class VisualizationView(context: Context) : View(context) {

    private var bitmap: Bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.RGB_565)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawBitmap(bitmap, 0f, 0f, null)
    }

    fun visualize(bitmapData: Bitmap) {
        bitmap = bitmapData
        invalidate()
    }

    fun clear() {
        bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.RGB_565)
        invalidate()
    }
}