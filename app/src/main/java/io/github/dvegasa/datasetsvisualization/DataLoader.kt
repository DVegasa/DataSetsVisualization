package io.github.dvegasa.datasetsvisualization

import android.os.Handler
import java.io.FileNotFoundException
import java.net.URL

/**
 * 15.11.2019
 */
class DataLoader {

    val handler = Handler()

    fun loadFromMyGithub(fileName: String, cb: Callback) {
        val url = "https://raw.githubusercontent.com/DVegasa/sandbox_datasets/master/$fileName"
        var dataset: String
        Thread(Runnable {
            try {
                dataset = URL(url).readText()
            } catch (ex: FileNotFoundException) {
                cb.error(ex)
                return@Runnable
            }

            handler.post {
                cb.downloaded(dataset)
            }

        }).start()
    }

    interface Callback {
        fun downloaded(dataset: String)
        fun error(ex: Exception)
    }
}