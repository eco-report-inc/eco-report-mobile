package com.capstone.ecoreport.data.ai

import android.content.Context
import android.graphics.Bitmap
import android.os.SystemClock
import android.util.Log
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.Rot90Op
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.vision.detector.Detection
import org.tensorflow.lite.task.vision.detector.ObjectDetector

class TrashDetectionHelper(
    var threshold: Float = 0.5f,
    var numThreads: Int = 2,
    var maxResults: Int = 3,
    val context: Context,
    val objectDetectorListener: DetectorListener?
) {

    // Variabel untuk menyimpan instance ObjectDetector
    private var objectDetector: ObjectDetector? = null

    init {
        // Inisialisasi ObjectDetector saat objek TrashDetectionHelper dibuat
        setupObjectDetector()
    }

    // Fungsi untuk menghapus instance ObjectDetector
    fun clearObjectDetector() {
        objectDetector = null
    }

    // Fungsi untuk mengatur ObjectDetector berdasarkan pengaturan saat ini
    fun setupObjectDetector() {
        // Membuat opsi dasar untuk detektor dengan menentukan jumlah maksimum hasil dan ambang skor
        val optionsBuilder =
            ObjectDetector.ObjectDetectorOptions.builder()
                .setScoreThreshold(threshold)
                .setMaxResults(maxResults)

        // Membuat opsi deteksi umum, termasuk jumlah thread yang digunakan
        val baseOptionsBuilder = BaseOptions.builder().setNumThreads(numThreads)

        // Menetapkan opsi dasar yang telah dibuat ke dalam opsi detektor
        optionsBuilder.setBaseOptions(baseOptionsBuilder.build())

        // Menentukan nama model berdasarkan pilihan pengguna
        val modelName = "best_float16.tflite"

        try {
            // Membuat instance ObjectDetector dari model yang telah ditentukan
            objectDetector =
                ObjectDetector.createFromFileAndOptions(context, modelName, optionsBuilder.build())
        } catch (e: IllegalStateException) {
            // Menangani kesalahan jika detektor objek gagal diinisialisasi
            objectDetectorListener?.onError(
                "Object detector gagal diinisialisasi. Lihat log kesalahan untuk detail"
            )
            Log.e("Test", "TFLite gagal memuat model dengan kesalahan: " + e.message)
        }
    }

    // Fungsi untuk mendeteksi objek pada gambar
    fun detect(image: Bitmap, imageRotation: Int) {
        if (objectDetector == null) {
            // Inisialisasi ObjectDetector jika belum diinisialisasi
            setupObjectDetector()
        }

        // Menghitung waktu inferensi
        var inferenceTime = SystemClock.uptimeMillis()

        // Membuat preprocessor untuk gambar
        val imageProcessor =
            ImageProcessor.Builder()
                .add(Rot90Op(-imageRotation / 90))
                .build()

        // Memproses gambar dan mengonversinya menjadi TensorImage untuk deteksi
        val tensorImage = imageProcessor.process(TensorImage.fromBitmap(image))

        // Melakukan deteksi menggunakan ObjectDetector
        val results = objectDetector?.detect(tensorImage)

        // Menghitung waktu inferensi
        inferenceTime = SystemClock.uptimeMillis() - inferenceTime

        // Memberikan hasil deteksi ke listener
        objectDetectorListener?.onResults(
            results,
            inferenceTime,
            tensorImage.height,
            tensorImage.width
        )
    }

    // Interface untuk mendengarkan kesalahan atau hasil deteksi
    interface DetectorListener {
        fun onError(error: String)
        fun onResults(
            results: MutableList<Detection>?,
            inferenceTime: Long,
            imageHeight: Int,
            imageWidth: Int
        )
    }
}
