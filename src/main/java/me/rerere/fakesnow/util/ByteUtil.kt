package me.rerere.fakesnow.util

/**
 * Get the bit value at a position of Byte
 *
 * @receiver the Byte
 * @param position the bit position (in 0..7)
 */
fun Byte.getBit(position: Int): Int {
    return (this.toInt() shr position) and 1
}