package com.capstone.ecoreport.data.ai

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.capstone.ecoreport.R
import org.tensorflow.lite.task.vision.detector.Detection
import java.util.LinkedList
import kotlin.math.max

// Kelas yang merepresentasikan tampilan lapisan overlay untuk menampilkan hasil deteksi objek
class OverlayTrashDetection(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    // Daftar hasil deteksi yang akan ditampilkan pada overlay
    private var results: List<Detection> = LinkedList<Detection>()

    // Paint untuk menggambar bounding box
    private var boxPaint = Paint()

    // Paint untuk latar belakang teks
    private var textBackgroundPaint = Paint()

    // Paint untuk teks
    private var textPaint = Paint()

    // Faktor skala untuk menyesuaikan ukuran bounding box dengan tampilan gambar yang ditangkap
    private var scaleFactor: Float = 1f

    // Batas teks
    private var bounds = Rect()

    // Inisialisasi paint pada konstruktor
    init {
        initPaints()
    }

    // Menghapus paint dan memulai ulang
    fun clear() {
        textPaint.reset()
        textBackgroundPaint.reset()
        boxPaint.reset()
        invalidate()
        initPaints()
    }

    // Inisialisasi paint untuk menggambar bounding box dan teks
    private fun initPaints() {
        textBackgroundPaint.color = Color.BLACK
        textBackgroundPaint.style = Paint.Style.FILL
        textBackgroundPaint.textSize = 50f

        textPaint.color = Color.WHITE
        textPaint.style = Paint.Style.FILL
        textPaint.textSize = 50f

        boxPaint.color = ContextCompat.getColor(context!!, R.color.bounding_box_color)
        boxPaint.strokeWidth = 8F
        boxPaint.style = Paint.Style.STROKE
    }

    // Menggambar overlay pada kanvas
    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        for (result in results) {
            val boundingBox = result.boundingBox

            val top = boundingBox.top * scaleFactor
            val bottom = boundingBox.bottom * scaleFactor
            val left = boundingBox.left * scaleFactor
            val right = boundingBox.right * scaleFactor

            // Menggambar bounding box di sekitar objek yang terdeteksi
            val drawableRect = RectF(left, top, right, bottom)
            canvas.drawRect(drawableRect, boxPaint)

            // Membuat teks untuk ditampilkan bersama objek yang terdeteksi
            val drawableText =
                result.categories[0].label + " " +
                        String.format("%.2f", result.categories[0].score)

            // Menggambar persegi panjang di belakang teks
            textBackgroundPaint.getTextBounds(drawableText, 0, drawableText.length, bounds)
            val textWidth = bounds.width()
            val textHeight = bounds.height()
            canvas.drawRect(
                left,
                top,
                left + textWidth + Companion.BOUNDING_RECT_TEXT_PADDING,
                top + textHeight + Companion.BOUNDING_RECT_TEXT_PADDING,
                textBackgroundPaint
            )

            // Menggambar teks untuk objek yang terdeteksi
            canvas.drawText(drawableText, left, top + bounds.height(), textPaint)
        }
    }

    // Mengatur hasil deteksi yang akan ditampilkan pada overlay
    fun setResults(
        detectionResults: MutableList<Detection>,
        imageHeight: Int,
        imageWidth: Int,
    ) {
        results = detectionResults

        // PreviewView berada dalam mode FILL_START. Jadi kita perlu memperbesar bounding box
        // untuk cocok dengan ukuran tampilan gambar yang akan ditampilkan.
        scaleFactor = max(width * 1f / imageWidth, height * 1f / imageHeight)
    }

    companion object {
        // Padding untuk persegi panjang di sekitar teks
        private const val BOUNDING_RECT_TEXT_PADDING = 8
    }
}
