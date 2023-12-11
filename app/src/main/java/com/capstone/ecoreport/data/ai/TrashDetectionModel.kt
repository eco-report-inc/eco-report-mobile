package com.capstone.ecoreport.data.ai

import android.content.Context
import android.graphics.Bitmap
import android.os.SystemClock
import android.util.Log
import org.tensorflow.lite.gpu.CompatibilityList
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.Rot90Op
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.vision.detector.Detection
import org.tensorflow.lite.task.vision.detector.ObjectDetector

class TrashDetectionModel(
    var threshold: Float = 0.5f,
    var numThreads: Int = 2,
    var maxResults: Int = 3,
    var currentDelegate: Int = 0,
    var currentModel: Int = 0,
    val context: Context,
    val objectDetectorListener: DetectorListener?
) {

    // Variabel untuk menyimpan instance ObjectDetector
    private var objectDetector: ObjectDetector? = null

    init {
        // Inisialisasi ObjectDetector saat objek TrashDetectionModel dibuat
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

        // Menggunakan perangkat keras tertentu untuk menjalankan model, default ke CPU
        when (currentDelegate) {
            DELEGATE_CPU -> {
                // Default, tidak perlu ditambahkan
            }
            DELEGATE_GPU -> {
                // Memeriksa apakah GPU didukung pada perangkat ini
                if (CompatibilityList().isDelegateSupportedOnThisDevice) {
                    baseOptionsBuilder.useGpu()
                } else {
                    // Memberikan pesan kesalahan jika GPU tidak didukung pada perangkat ini
                    objectDetectorListener?.onError("GPU tidak didukung pada perangkat ini")
                }
            }
            DELEGATE_NNAPI -> {
                // Menggunakan NNAPI untuk inferensi
                baseOptionsBuilder.useNnapi()
            }
        }

        // Menetapkan opsi dasar yang telah dibuat ke dalam opsi detektor
        optionsBuilder.setBaseOptions(baseOptionsBuilder.build())

        // Menentukan nama model berdasarkan pilihan pengguna
        val modelName =
            when (currentModel) {
                MODEL_MOBILENETV1 -> "mobilenetv1.tflite"
                MODEL_EFFICIENTDETV0 -> "efficientdet-lite0.tflite"
                MODEL_EFFICIENTDETV1 -> "efficientdet-lite1.tflite"
                MODEL_EFFICIENTDETV2 -> "efficientdet-lite2.tflite"
                else -> "mobilenetv1.tflite"
            }

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

    companion object {
        // Konstanta untuk delegate (perangkat keras) dan model
        const val DELEGATE_CPU = 0
        const val DELEGATE_GPU = 1
        const val DELEGATE_NNAPI = 2
        const val MODEL_MOBILENETV1 = 0
        const val MODEL_EFFICIENTDETV0 = 1
        const val MODEL_EFFICIENTDETV1 = 2
        const val MODEL_EFFICIENTDETV2 = 3
    }
}
