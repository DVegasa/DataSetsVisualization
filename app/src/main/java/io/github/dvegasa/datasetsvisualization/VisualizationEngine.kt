package io.github.dvegasa.datasetsvisualization

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

/**
 * 15.11.2019
 */
class VisualizationEngine(
    private val visView: VisualizationView
) {
    fun visualize(dataset: ArrayList<PixelData>) {
        val bitmap = datasetToBitmap(dataset)
        visView.visualize(bitmap)
    }

    fun clear() {
        visView.clear()
    }

    private fun datasetToBitmap(dataset: ArrayList<PixelData>): Bitmap {
        val width = visView.width
        val height = visView.height
        val paint = Paint().apply {
            color = Color.RED
            style = Paint.Style.FILL
        }

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        bitmap.eraseColor(Color.WHITE)
        val canvas = Canvas(bitmap)

        for (pixelData in dataset) {
            val x = pixelData.x + width / 2
            val y = pixelData.y + height / 2
            val gen = pixelData.gen
            canvas.drawPoint(x.toFloat(), y.toFloat(), paint)
        }
        return bitmap
    }

}