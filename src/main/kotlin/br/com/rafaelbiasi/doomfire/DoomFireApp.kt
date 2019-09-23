package br.com.rafaelbiasi.doomfire

fun main() {
    val fireWidth = 25
    val fireHeight = 25
    val render = ZirconRender(fireWidth, fireHeight)
    val doomFire = DoomFire(fireWidth, fireHeight)

    GameLoop(doomFire::start, doomFire::doFire, { doomFire.renderFire(render) }, render::isRunning).start()
}
