package br.com.rafaelbiasi.doomfire

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.DrawSurfaces
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.builder.screen.ScreenBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile


class ZirconRender(fireWidth: Int, fireHeight: Int) : Render {

    private val tileGrid = SwingApplications.startTileGrid(
        AppConfig.newBuilder()
            .withSize(Size.create(fireWidth, fireHeight))
            .withDefaultTileset(CP437TilesetResources.rexPaint8x8())
            .build()
    )

    private val image = DrawSurfaces.tileGraphicsBuilder().withSize(tileGrid.size)

    private val screen = ScreenBuilder.createScreenFor(tileGrid)

    private val fireColorsPalette = arrayOf(
        RGB(0, 0, 0), RGB(7, 7, 7)
        , RGB(31, 7, 7), RGB(47, 15, 7), RGB(71, 15, 7)
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

    private var running: Boolean = true

    init {
        tileGrid.onShutdown {
            running = false
        }
    }

    override fun setPixel(column: Int, row: Int, fireIntensity: Int) {
        val color = fireColorsPalette[fireIntensity]

        image.withTile(
            Position.create(column, row), Tile.newBuilder()
                .withForegroundColor(TileColor.create(color.r, color.g, color.b))
                .withBackgroundColor(TileColor.create(color.r, color.g, color.b))
                .withCharacter(' ')
                .build()
        )
    }

    override fun render() {
        screen.also { it.draw(image.build(), Position.zero()) }.also { it.display() }
    }

    override fun close() {
        tileGrid.close()
    }

    override fun isRunning(): Boolean {
        return running
    }
}
