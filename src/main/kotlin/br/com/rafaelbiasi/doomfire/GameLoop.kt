package br.com.rafaelbiasi.doomfire

class GameLoop(
    private val startGame: () -> Unit,
    private val updateGame: () -> Unit,
    private val displayGame: () -> Unit,
    private val isRunning: () -> Boolean
) {

    private val ticksPerSecond = 60
    private val skipTicks = 1000 / ticksPerSecond
    private val maxFrameSkip = 10

    fun start() {
        startGame.invoke()
        var nextGameTick = System.currentTimeMillis()

        var loops: Int

        while (isRunning.invoke()) {

            loops = 0

            while (System.currentTimeMillis() > nextGameTick && loops < maxFrameSkip) {
                updateGame.invoke()

                nextGameTick += skipTicks
                loops++
            }

            displayGame.invoke()
        }
    }
}
