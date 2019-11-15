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

    private var visEngine: VisualizationEngine? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        flVisualizationViewHolder.addView(visView)
        visEngine = VisualizationEngine(visView)

        btnGo.setOnClickListener {
            btnGo.isEnabled = false
            btnGo.isClickable = false
            setStatus("Загрузка начата...")
            startDownloading()
        }

        btnClear.setOnClickListener {
            visEngine?.clear()
        }
    }

    private fun startDownloading() {
        dataLoader.loadFromMyGithub(
            etFileNameInput.text.toString().trim(),
            object : DataLoader.Callback {

                override fun error(ex: Exception) {
                    btnGo.isEnabled = true
                    btnGo.isClickable = true
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
        var parsedList: ArrayList<PixelData>

        converter.toPixelData(dataset, object : DatasetConverter.StatusCallback {
            override fun updateStatus(computed: Int) {
                setStatus("Переведено $computed / $datasetSize")
            }

            override fun done(list: List<PixelData>) {
                btnGo.isEnabled = true
                btnGo.isClickable = true
                setStatus("Перевод закончен ($datasetSize элементов)")
                parsedList = list as ArrayList<PixelData>
                visEngine?.visualize(parsedList)
            }
        })

    }

    fun setStatus(status: String) {
        tvStatus.setText(status)
    }
}