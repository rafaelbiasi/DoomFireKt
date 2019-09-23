package br.com.rafaelbiasi.doomfire

import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.TextCharacter
import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import java.awt.Window
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent


class LanternaRender(fireWidth: Int, fireHeight: Int) : Render {

    private val screen = DefaultTerminalFactory()
        .setInitialTerminalSize(TerminalSize(fireWidth, fireHeight))
        .createScreen()

    private var running: Boolean = true

    private val fireColorsPalette = arrayOf(
        RGB(7, 7, 7), RGB(31, 7, 7), RGB(47, 15, 7), RGB(71, 15, 7)
        , RGB(87, 23, 7), RGB(103, 31, 7), RGB(119, 31, 7), RGB(143, 39, 7)
        , RGB(159, 47, 7), RGB(175, 63, 7), RGB(191, 71, 7), RGB(199, 71, 7)
        , RGB(223, 79, 7), RGB(223, 87, 7), RGB(223, 87, 7), RGB(215, 95, 7)
        , RGB(215, 95, 7), RGB(215, 103, 15), RGB(207, 111, 15), RGB(207, 119, 15)
        , RGB(207, 127, 15), RGB(207, 135, 23), RGB(199, 135, 23), RGB(199, 143, 23)
        , RGB(199, 151, 31), RGB(191, 159, 31), RGB(191, 159, 31), RGB(191, 167, 39)
        , RGB(191, 167, 39), RGB(191, 175, 47), RGB(183, 175, 47), RGB(183, 183, 47)
        , RGB(183, 183, 55), RGB(207, 207, 111), RGB(223, 223, 159), RGB(239, 239, 199)
        , RGB(255, 255, 255)
    )

    init {
        screen.cursorPosition = null
        screen.startScreen()

        when (val window = screen.terminal) {
            is Window -> window.addWindowListener(object : WindowAdapter() {
                override fun windowClosed(e: WindowEvent) {
                    running = false
                }
            })
        }
    }

    override fun setPixel(column: Int, row: Int, fireIntensity: Int) {
        val color = fireColorsPalette[fireIntensity]

        screen.setCharacter(
            column,
            row,
            TextCharacter('*').withForegroundColor(TextColor.RGB(color.r, color.g, color.b))
        )
    }

    override fun render() {
        screen.refresh()
    }

    override fun close() {
        screen.close()
    }

    override fun isRunning(): Boolean {
        return running
    }
}
