package dev.orion.ultron

import androidx.compose.ui.geometry.Offset
import dev.orion.ultron.canvas.HitBox

sealed interface Command {

    fun isIn(hitBox: HitBox): Boolean

    data class Point(val x: Float, val y: Float) : Command {
        override fun isIn(hitBox: HitBox): Boolean = hitBox.contains(toOffset())

        private fun toOffset(): Offset = Offset(x, y)

        override fun toString(): String = "x${x.toInt()}y${y.toInt()}"
    }

    data class CubicBezier(val a: Point, val b: Point, val d: Point) : Command {
        override fun isIn(hitBox: HitBox): Boolean = d.isIn(hitBox)

        override fun toString(): String {
            return "C$a $b $d"
        }
    }

    companion object {

        private fun parsePoint(value: String): Point {
            val parts = value.split("x", "y")

            return Point(parts[1].toFloat(), parts[2].toFloat())
        }

        fun parseCubicBezier(value: String): CubicBezier {
            val parts = value.split("x", "y")

            return CubicBezier(
                Point(parts[1].toFloat(), parts[2].toFloat()),
                Point(parts[3].toFloat(), parts[4].toFloat()),
                Point(parts[5].toFloat(), parts[6].toFloat())
            )
        }

        fun parse(value: String): Command? {
            return when {
                Regex("x\\d+y\\d+").matches(value) -> parsePoint(value)
                Regex("x\\d+y\\d+x\\d+y\\d+x\\d+y\\d+").matches(value) -> parseCubicBezier(value)
                else -> null
            }
        }

        fun isValid(command: String): Boolean = parse(command) != null

    }
}