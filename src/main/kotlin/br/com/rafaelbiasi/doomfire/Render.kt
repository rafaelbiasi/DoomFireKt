package br.com.rafaelbiasi.doomfire

interface Render {

    fun setPixel(column: Int, row: Int, fireIntensity: Int)
    fun render()
    fun isRunning(): Boolean
    fun close()
}
