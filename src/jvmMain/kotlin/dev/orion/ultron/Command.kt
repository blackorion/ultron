package dev.orion.ultron

import androidx.compose.ui.geometry.Offset
import dev.orion.ultron.canvas.HitBox

sealed interface Command {

    fun isIn(hitBox: HitBox): Boolean
    fun destination(): Point

    data class Point(val x: Float, val y: Float) : Command {
        override fun isIn(hitBox: HitBox): Boolean = hitBox.contains(toOffset())

        override fun destination(): Point {
            return this
        }

        private fun toOffset(): Offset = Offset(x, y)

        override fun toString(): String = "x${x.toInt()}y${y.toInt()}"
    }

    data class CubicBezier(val a: Point, val b: Point, val d: Point) : Command {
        override fun isIn(hitBox: HitBox): Boolean = d.isIn(hitBox)

        override fun destination(): Point {
            return d
        }

        override fun toString(): String {
            return "C$a $b $d"
        }
    }

    data class Arc(val p: Point, val radius: Float, val startAngle: Float, val sweepAngle: Float) : Command {
        override fun isIn(hitBox: HitBox): Boolean = p.isIn(hitBox)

        override fun destination(): Point {
            return p
        }

        override fun toString(): String {
            return "${p}r${radius}u${startAngle}o${sweepAngle}"
        }
    }

    companion object {

        private fun parsePoint(value: String): Point {
            val parts = value.lowercase().replace("m", "").split("x", "y")

            return Point(parts[1].toFloat(), parts[2].toFloat())
        }

        private fun parseCubicBezier(value: String): CubicBezier {
            val (a, b, d) = value.lowercase().replace("c", "").split(" ")

            return CubicBezier(
                parsePoint(a),
                parsePoint(b),
                parsePoint(d)
            )
        }

        private fun parseArc(value: String): Arc {
            val parts = value.lowercase().replace("a", "").split("x", "y", "r", "u", "o")

            return Arc(
                Point(parts[1].toFloat(), parts[2].toFloat()),
                parts[3].toFloat(),
                parts[4].toFloat(),
                parts[5].toFloat()
            )
        }

        fun parse(value: String): Command? {
            return when {
                Regex("[aA]?x\\d+y\\d+r\\d+u\\d+o\\d+").matches(value) -> parseArc(value)
                Regex("[mM]?x\\d+y\\d+").matches(value) -> parsePoint(value)
                Regex("[cC]x\\d+y\\d+ x\\d+y\\d+ x\\d+y\\d+").matches(value) -> parseCubicBezier(value)
                else -> null
            }
        }

        fun isValid(command: String): Boolean = parse(command) != null

    }
}