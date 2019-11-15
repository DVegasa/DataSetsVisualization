package io.github.dvegasa.datasetsvisualization

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.FileNotFoundException

@SuppressLint("SetTextI18n")
class MainActivity : AppCompatActivity() {

    private val dataLoader = DataLoader()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        flVisualizationViewHolder.addView(VisualizationView(this))

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
                    setStatus("Датасет загружен. Элементов: ${dataset.lines().size}")
                }
            })
    }

    private fun visualizeDataset(dataset: String) {

    }

    fun setStatus(status: String) {
        tvStatus.setText(status)
    }
}
