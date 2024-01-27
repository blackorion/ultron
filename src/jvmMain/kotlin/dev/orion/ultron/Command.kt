package dev.orion.ultron

import androidx.compose.ui.geometry.Offset
import dev.orion.ultron.canvas.HitBox

sealed interface Command {

    fun isIn(hitBox: HitBox): Boolean
    fun isValid(value: String): Boolean

    data class Point(val x: Float, val y: Float) : Command {
        override fun isIn(hitBox: HitBox): Boolean = hitBox.contains(toOffset())

        override fun isValid(value: String): Boolean = value.matches(Regex("x\\d+y\\d+"))

        fun toOffset(): Offset = Offset(x, y)

        override fun toString(): String = "x${x.toInt()}y${y.toInt()}"
    }

    data class CubicBezier(val a: Point, val b: Point, val d: Point) : Command {
        override fun isIn(hitBox: HitBox): Boolean = d.isIn(hitBox)

        override fun isValid(value: String): Boolean {
            return value.matches(Regex("x\\d+y\\d+x\\d+y\\d+x\\d+y\\d+"))
        }

        override fun toString(): String {
            return "C$a $b $d"
        }
    }

    companion object {

        fun parse(value: String): Command? {
            val parts = value.split("x", "y")
            return when (parts.size) {
                3 -> Point(parts[1].toFloat(), parts[2].toFloat())
                7 -> CubicBezier(
                    Point(parts[1].toFloat(), parts[2].toFloat()),
                    Point(parts[3].toFloat(), parts[4].toFloat()),
                    Point(parts[5].toFloat(), parts[6].toFloat())
                )

                else -> null
            }
        }

    }
}