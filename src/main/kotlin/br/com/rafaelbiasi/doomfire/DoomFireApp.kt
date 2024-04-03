package br.com.rafaelbiasi.doomfire

fun main() {
    java2D()
}

private fun java2D() {
    val pixelWidth = 4
    val pixelHeight = 4
    val widthRatio = 10
    val heightRatio = 10
    val sizeMultiplication = 100

    val fireWidth: Int = (widthRatio * sizeMultiplication / pixelWidth)
    val fireHeight: Int = (heightRatio * sizeMultiplication / pixelHeight)
    val render = Java2DRender(fireWidth, fireHeight, pixelWidth, pixelHeight)
    val doomFire = DoomFire(fireWidth, fireHeight)

    GameLoop(doomFire::start, doomFire::doFire, { doomFire.renderFire(render) }, render::isRunning).start()
}
