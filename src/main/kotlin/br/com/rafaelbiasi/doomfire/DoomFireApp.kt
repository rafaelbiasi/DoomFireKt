package br.com.rafaelbiasi.doomfire

fun main() {
//    processing()
    java2D()
}

private fun processing() {
    val fireWidth = 800
    val fireHeight = 800
    val pixelWidth = 4
    val pixelHeight = 4
    val doomFire = DoomFire(fireWidth/pixelWidth, fireHeight/pixelHeight)
    ProcessingRender(doomFire, pixelWidth, pixelHeight).run()
}

private fun java2D() {
    val pixelWidth = 4
    val pixelHeight = 4
    val width = 800
    val height = 800

    val fireWidth: Int = (width / pixelWidth)
    val fireHeight: Int = (height / pixelHeight)
    val doomFire = DoomFire(fireWidth, fireHeight)
    val render = Java2DRender(fireWidth, fireHeight, pixelWidth, pixelHeight)

    GameLoop(doomFire::start, doomFire::doFire, { doomFire.renderFire(render) }, render::isRunning).start()
}
