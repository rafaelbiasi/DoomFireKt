package br.com.rafaelbiasi.doomfire

import processing.core.PApplet


class ProcessingRender(private val doomFire: DoomFire, private val pixelW: Int, private val pixelH: Int) : PApplet(),
    Render {

    private val palette = arrayOf(
        intArrayOf(0, 0, 0),
        intArrayOf(7, 7, 7),
        intArrayOf(31, 7, 7),
        intArrayOf(47, 15, 7),
        intArrayOf(71, 15, 7),
        intArrayOf(87, 23, 7),
        intArrayOf(103, 31, 7),
        intArrayOf(119, 31, 7),
        intArrayOf(143, 39, 7),
        intArrayOf(159, 47, 7),
        intArrayOf(175, 63, 7),
        intArrayOf(191, 71, 7),
        intArrayOf(199, 71, 7),
        intArrayOf(223, 79, 7),
        intArrayOf(223, 87, 7),
        intArrayOf(223, 87, 7),
        intArrayOf(215, 95, 7),
        intArrayOf(215, 95, 7),
        intArrayOf(215, 103, 15),
        intArrayOf(207, 111, 15),
        intArrayOf(207, 119, 15),
        intArrayOf(207, 127, 15),
        intArrayOf(207, 135, 23),
        intArrayOf(199, 135, 23),
        intArrayOf(199, 143, 23),
        intArrayOf(199, 151, 31),
        intArrayOf(191, 159, 31),
        intArrayOf(191, 159, 31),
        intArrayOf(191, 167, 39),
        intArrayOf(191, 167, 39),
        intArrayOf(191, 175, 47),
        intArrayOf(183, 175, 47),
        intArrayOf(183, 183, 47),
        intArrayOf(183, 183, 55),
        intArrayOf(207, 207, 111),
        intArrayOf(223, 223, 159),
        intArrayOf(239, 239, 199),
        intArrayOf(255, 255, 255)
    )

    @Volatile
    private var running = true

    fun run() {
        runSketch()
    }

    override fun settings() {
        size(doomFire.fireWidth * pixelW, doomFire.fireHeight * pixelH)
    }

    override fun setup() {
        doomFire.start()
    }

    override fun draw() {
        doomFire.doFire()
        loadPixels()
        doomFire.renderFire(this)
    }

    override fun setPixel(column: Int, row: Int, state: Int) {
        setPixel(column * width + row, state)
    }

    override fun setPixel(pixelIndex: Int, state: Int) {
        val rgb: IntArray = palette[state]
        val row: Int = pixelIndex / (doomFire.fireWidth)
        val col: Int = pixelIndex % (doomFire.fireWidth)
        for (x in 0 until pixelW) {
            for (y in 0 until pixelH) {
                val actualX: Int = col * pixelW + x
                val actualY: Int = row * pixelH + y
                if (actualX < width && actualY < height) {
                    pixels[actualY * width + actualX] = color(rgb[0], rgb[1], rgb[2])
                }
            }
        }
    }

    override fun render() {
        updatePixels()
    }

    override fun isRunning(): Boolean {
        return running
    }

    override fun close() {
        running = false
    }
}