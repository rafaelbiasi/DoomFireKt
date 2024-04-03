package br.com.rafaelbiasi.doomfire

import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

class DoomFire(private val fireWidth: Int = 50, private val fireHeight: Int = 50) {
    private var maxFireIntensity = 37
    private val windForceDirection = -1
    private val fireIntensityPixels = IntArray(fireWidth * fireHeight) { 0 }

    fun start() = createFireSource()

    fun renderFire(render: Render) {
        for (pixelIndex in fireIntensityPixels.indices) {
            val column = pixelIndex % fireWidth
            val row = pixelIndex / fireWidth
            render.setPixel(column, row, fireIntensityPixels[pixelIndex])
        }
        render.render()
    }


    fun doFire() {
        for (pixelIndex in 0 until fireWidth * (fireHeight - 1)) {
            spreadFire(pixelIndex)
        }
    }


    private fun spreadFire(pixelIndex: Int) {
        val belowPixelIndex: Int = pixelIndex + fireWidth
        if (belowPixelIndex >= fireIntensityPixels.size) {
            return
        }
        val decay: Int = Random.nextInt(3) and 3
        val targetIndex = min(
            max((pixelIndex + decay + windForceDirection), 0),
            fireIntensityPixels.size - 1
        )
        val newIntensity = min(
            max((fireIntensityPixels[belowPixelIndex] - (decay and 1)), 0),
            maxFireIntensity
        )
        fireIntensityPixels[targetIndex] = newIntensity
    }

    private fun createFireSource() {
        val startIndex = fireWidth * (fireHeight - 1)
        for (i in startIndex until startIndex + fireWidth) {
            fireIntensityPixels[i] = maxFireIntensity
        }
    }
}
