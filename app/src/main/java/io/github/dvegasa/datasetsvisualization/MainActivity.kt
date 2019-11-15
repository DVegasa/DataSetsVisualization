package io.github.dvegasa.datasetsvisualization

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.FileNotFoundException

@SuppressLint("SetTextI18n")
class MainActivity : AppCompatActivity() {

    private val dataLoader = DataLoader()
    private var datasetSize = -1

    private val converter = DatasetConverter().ProtocolAlpha()

    private val visView by lazy {
        VisualizationView(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        flVisualizationViewHolder.addView(visView)

        btnGo.setOnClickListener {
            setStatus("Загрузка начата...")
            startDownloading()
        }
    }

    private fun startDownloading() {
        dataLoader.loadFromMyGithub(
            etFileNameInput.text.toString().trim(), object : DataLoader.Callback {

                override fun error(ex: Exception) {
                    when (ex) {

                        is FileNotFoundException -> setStatus("Файл не найден")

                        else -> {
                            setStatus("Неизвестная ошибка")
                            ex.printStackTrace()
                        }
                    }
                }

                override fun downloaded(dataset: String) {
                    datasetSize = dataset.lines().size
                    handleDataset(dataset)
                }
            })
    }

    private fun handleDataset(dataset: String) {
        val parsedList = ArrayList<PixelData>()

        converter.toPixelData(dataset, object : DatasetConverter.StatusCallback {
            override fun updateStatus(computed: Int) {
                setStatus("Переведено $computed / $datasetSize")
            }

            override fun done(list: List<PixelData>) {
                setStatus("Перевод закончен ($datasetSize элементов)")
            }
        })

        visView.visualize(parsedList)
    }

    fun setStatus(status: String) {
        tvStatus.setText(status)
    }
}