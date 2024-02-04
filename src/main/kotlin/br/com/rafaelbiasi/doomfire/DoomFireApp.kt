package br.com.rafaelbiasi.doomfire

fun main() {
    val pixelWidth = 8
    val pixelHeight = 8
    val widthRatio = 16
    val heightRatio = 9
    val sizeMultiplication = 100

    val fireWidth: Int = (widthRatio * sizeMultiplication / pixelWidth).toInt()
    val fireHeight: Int = (heightRatio * sizeMultiplication / pixelHeight).toInt()
    val render = Java2DRender(fireWidth, fireHeight, pixelWidth, pixelHeight)
    val doomFire = DoomFire(fireWidth, fireHeight)

    GameLoop(doomFire::start, doomFire::doFire, { doomFire.renderFire(render) }, render::isRunning).start()
}
