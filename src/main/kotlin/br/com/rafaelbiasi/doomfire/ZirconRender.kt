package br.com.rafaelbiasi.doomfire

import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.DrawSurfaces
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Screens
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.TileColors
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.extensions.onShutdown


class ZirconRender(fireWidth: Int, fireHeight: Int) : Render {

    private val tileGrid = SwingApplications.startTileGrid(
        AppConfigs.newConfig()
            .withSize(Sizes.create(fireWidth, fireHeight))
            .withDefaultTileset(CP437TilesetResources.rexPaint8x8())
            .build()
    )

    private val image = DrawSurfaces.tileGraphicsBuilder().withSize(tileGrid.size)

    private val screen = Screens.createScreenFor(tileGrid)

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
            Positions.create(column, row), Tiles.newBuilder()
                .withForegroundColor(TileColors.create(color.r, color.g, color.b))
                .withBackgroundColor(TileColors.create(color.r, color.g, color.b))
                .withCharacter(' ')
                .build()
        )
    }

    override fun render() {
        screen.also { it.draw(image.build(), Positions.zero()) }.also { it.display() }
    }

    override fun close() {
        tileGrid.close()
    }

    override fun isRunning(): Boolean {
        return running
    }
}
