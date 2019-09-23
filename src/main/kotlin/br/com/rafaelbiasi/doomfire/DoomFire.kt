package br.com.rafaelbiasi.doomfire

import java.lang.StrictMath.round
import kotlin.random.Random.Default.nextInt


class DoomFire(
    private val fireWidth: Int = 50, fireHeight: Int = 50
) {

    private val fireIntensityPixels = IntArray(fireWidth * fireHeight)

    fun start() {
        createFireSource()
    }

    fun renderFire(render: Render) {
        fireIntensityPixels.indices.forEach { setPixelIntensity(render, it) }
        render.render()
    }

    private fun setPixelIntensity(render: Render, pixelIndex: Int) {
        val row = pixelIndex / fireWidth
        val column = pixelIndex - (fireWidth * row)
        val fireIntensity = fireIntensityPixels[pixelIndex]
        render.setPixel(column, row, fireIntensity)
    }

    fun doFire() {
        fireIntensityPixels.indices.forEach(::spreadFire)
    }

    private fun spreadFire(currentPixelIndex: Int) {
        val belowPixelIndex = currentPixelIndex + fireWidth
        val lastPixelIndex = fireIntensityPixels.indices.last

        if (belowPixelIndex >= lastPixelIndex) return

        val decay = nextInt(round(3f * 2f))
        val windForce = -decay / 3

        val belowPixelFireIntensity = fireIntensityPixels[belowPixelIndex]
        val newFireIntensity = if (belowPixelFireIntensity - decay >= 0) belowPixelFireIntensity - decay else 0

        if (currentPixelIndex + windForce >= 0 && currentPixelIndex - windForce <= lastPixelIndex)
            fireIntensityPixels[currentPixelIndex + windForce] = newFireIntensity
    }

    private fun createFireSource() {
        val lastPixelIndex = fireIntensityPixels.indices.last
        val fistPixelLastRow = lastPixelIndex - fireWidth

        (0..fireWidth).forEach { column ->
            val sourcePixelIndex = fistPixelLastRow + column

            fireIntensityPixels[sourcePixelIndex] = 37
        }
    }

    private fun destroyFireSource() {
        val lastPixelIndex = fireIntensityPixels.indices.last
        val fistPixelLastRow = lastPixelIndex - fireWidth

        (0..fireWidth).forEach { column ->
            val pixelIndex = fistPixelLastRow + column
            fireIntensityPixels[pixelIndex] = 0
        }
    }

    private fun increaseFireSource() {
        val lastPixelIndex = fireIntensityPixels.indices.last
        val fistPixelLastRow = lastPixelIndex - fireWidth

        (0..fireWidth).forEach { column ->
            val pixelIndex = fistPixelLastRow + column
            val currentFireIntensity = fireIntensityPixels[pixelIndex]

            if (currentFireIntensity < 37) {
                val increase = nextInt(14)
                val newFireIntensity =
                    if (currentFireIntensity + increase >= 37) 37 else currentFireIntensity + increase

                fireIntensityPixels[pixelIndex] = newFireIntensity
            }
        }
    }

    private fun decreaseFireSource() {
        val lastPixelIndex = fireIntensityPixels.indices.last
        val fistPixelLastRow = lastPixelIndex - fireWidth

        (0..fireWidth).forEach { column ->
            val pixelIndex = fistPixelLastRow + column
            val currentFireIntensity = fireIntensityPixels[pixelIndex]

            if (currentFireIntensity > 0) {
                val decay = nextInt(14)
                val newFireIntensity = if (currentFireIntensity - decay >= 0) currentFireIntensity - decay else 0

                fireIntensityPixels[pixelIndex] = newFireIntensity
            }
        }
    }
}
