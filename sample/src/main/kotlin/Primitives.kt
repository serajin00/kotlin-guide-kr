/**
 * Created by jean on 2017. 1. 28..
 */

fun main(args: Array<String>) {
    val MASK: Byte = 0b0001
    val a: Byte = 0b1001

    println(a and MASK == MASK) // true

    println(a.inv().toString(2)) // -1010


    val i = -100.toByte()

    println(i.toString(2))
    println((i shl 1).also(::println).toString(2))


}
