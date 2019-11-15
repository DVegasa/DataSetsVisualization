package io.github.dvegasa.datasetsvisualization

import android.os.Handler
import java.util.*
import kotlin.collections.ArrayList

/**
 * 15.11.2019
 */
class DatasetConverter {

    private val handler = Handler()

    inner class ProtocolAlpha {
        fun toPixelData(dataset: String, cb: StatusCallback) {
            Thread(Runnable {
                val list = ArrayList<PixelData>()
                val sc = Scanner(dataset)
                var computed = 0

                while (sc.hasNextLine()) {
                    val x = sc.nextShort()
                    val y = sc.nextShort()
                    val gen = sc.nextShort()
                    list.add(PixelData(x, y, gen))

                    computed += 3
                    handler.post { cb.updateStatus(computed) }
                }
                handler.post {cb.done(list)}
            }).start()
        }
    }

    interface StatusCallback {
        fun updateStatus(computed: Int)
        fun done(list: List<PixelData>)
    }
}