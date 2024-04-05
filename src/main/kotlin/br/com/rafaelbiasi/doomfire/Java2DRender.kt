package br.com.rafaelbiasi.doomfire

import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import javax.swing.JFrame
import javax.swing.JPanel

class Java2DRender(
    private val width: Int,
    private val height: Int,
    private val pixelWidth: Int = 2,
    private val pixelHeight: Int = 2
) : JPanel(), Render {
    private val canvas = BufferedImage(width * pixelWidth, height * pixelHeight, BufferedImage.TYPE_INT_RGB)
    private val frame = JFrame("Java2D Render")
    private val pixelStates = Array(height) { IntArray(width) }

    private val fireColorsPalette = arrayOf(
        Color(0, 0, 0),
        Color(7, 7, 7),
        Color(31, 7, 7),
        Color(47, 15, 7),
        Color(71, 15, 7),
        Color(87, 23, 7),
        Color(103, 31, 7),
        Color(119, 31, 7),
        Color(143, 39, 7),
        Color(159, 47, 7),
        Color(175, 63, 7),
        Color(191, 71, 7),
        Color(199, 71, 7),
        Color(223, 79, 7),
        Color(223, 87, 7),
        Color(223, 87, 7),
        Color(215, 95, 7),
        Color(215, 95, 7),
        Color(215, 103, 15),
        Color(207, 111, 15),
        Color(207, 119, 15),
        Color(207, 127, 15),
        Color(207, 135, 23),
        Color(199, 135, 23),
        Color(199, 143, 23),
        Color(199, 151, 31),
        Color(191, 159, 31),
        Color(191, 159, 31),
        Color(191, 167, 39),
        Color(191, 167, 39),
        Color(191, 175, 47),
        Color(183, 175, 47),
        Color(183, 183, 47),
        Color(183, 183, 55),
        Color(207, 207, 111),
        Color(223, 223, 159),
        Color(239, 239, 199),
        Color(255, 255, 255)
    )

    @Volatile
    private var running = true

    init {
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.add(this)
        frame.pack()
        frame.isVisible = true
    }

    override fun setPixel(row: Int, column: Int, state: Int) {
        if (column < 0 || column >= width || row < 0 || row >= height) return

        if (pixelStates[row][column] != state) {
            pixelStates[row][column] = state
            val color = fireColorsPalette[state]
            val graphics = canvas.graphics
            graphics.color = color
            graphics.fillRect(row * pixelHeight, column * pixelWidth, pixelWidth, pixelHeight)
        }
    }

    override fun setPixel(pixelIndex: Int, state: Int) {
        val column = pixelIndex % width
        val row = pixelIndex / width
        setPixel(column, row, state)
    }

    override fun render() {
        repaint()
    }

    override fun isRunning(): Boolean {
        return running
    }

    override fun close() {
        frame.dispose()
        running = false
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g2d = g as Graphics2D
        g2d.drawImage(canvas, 0, 0, this)
    }

    override fun getPreferredSize(): Dimension {
        return Dimension(width * pixelWidth, height * pixelHeight)
    }
}
