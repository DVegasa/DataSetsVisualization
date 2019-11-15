package io.github.dvegasa.datasetsvisualization

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator

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

        val c1 = 0x00FFFFL // outside color
        val c2 = 0xFF0000L // inside color

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        bitmap.eraseColor(Color.WHITE)
        val canvas = Canvas(bitmap)

        val maxGen = dataset[0].gen

        for (pixelData in dataset) {
            val x = pixelData.x + width / 2
            val y = pixelData.y + height / 2
            val gen = pixelData.gen

            paint.color = getGradientColor(c1, c2, n = gen.toFloat() / maxGen.toFloat()).toInt()

            canvas.drawPoint(x.toFloat(), y.toFloat(), paint)
        }
        return bitmap
    }

    private fun getGradientColor(c1: Long, c2: Long, n: Float): Long {
        val result = (ArgbEvaluator.getInstance().evaluate(n, c1.toInt(), c2.toInt()) as Int).toLong()
        return result + 0xFF_000000 // adding alpha channel
    }
}