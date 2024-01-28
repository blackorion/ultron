package dev.orion.ultron

import androidx.compose.ui.geometry.Offset
import dev.orion.ultron.canvas.HitBox

sealed interface Command {

    fun isIn(hitBox: HitBox): Boolean
    fun destination(): Point

    data class Point(val x: Float, val y: Float) : Command {
        override fun isIn(hitBox: HitBox): Boolean = hitBox.contains(toOffset())

        override fun destination(): Point = this

        fun toOffset(): Offset = Offset(x, y)

        override fun toString(): String = "x${x}y${y}"

        operator fun plus(other: Point): Point = Point(x + other.x, y + other.y)

        operator fun minus(other: Point): Point = Point(x - other.x, y - other.y)
    }

    data class CubicBezier(val a: Point, val b: Point, val d: Point) : Command {
        override fun isIn(hitBox: HitBox): Boolean = d.isIn(hitBox)

        override fun destination(): Point = d

        override fun toString(): String {
            return "C$a $b $d"
        }
    }

    data class Arc(val p: Point, val radius: Float, val startAngle: Float, val sweepAngle: Float) : Command {
        override fun isIn(hitBox: HitBox): Boolean = p.isIn(hitBox)

        override fun destination(): Point = p

        fun start(): Point {
            val x = p.x + radius * kotlin.math.cos(degToRad(startAngle))
            val y = p.y + radius * kotlin.math.sin(degToRad(startAngle))

            return Point(x, y)
        }

        fun end(): Point {
            val x = p.x + radius * kotlin.math.cos(degToRad(startAngle + sweepAngle))
            val y = p.y + radius * kotlin.math.sin(degToRad(startAngle + sweepAngle))

            return Point(x, y)
        }

        private fun degToRad(deg: Float): Float {
            return deg * kotlin.math.PI.toFloat() / 180f
        }

        override fun toString(): String = "${p}r${radius}u${startAngle}o${sweepAngle}"
    }

    companion object {

        private fun parsePoint(value: String): Point {
            val (_, x, y) = value.lowercase().replace("m", "").split("x", "y")

            return Point(x.toFloat(), y.toFloat())
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
            val floatValue = "-?\\d+(\\.\\d+)?"
            val coords = "x${floatValue}y${floatValue}"
            val arcRegexp = Regex("[aA]?${coords}r${floatValue}u${floatValue}o${floatValue}")
            val pointRegexp = Regex("[mM]?${coords}")
            val bezierRegexp = Regex("[cC]?${coords} $coords $coords")

            return when {
                arcRegexp.matches(value) -> parseArc(value)
                pointRegexp.matches(value) -> parsePoint(value)
                bezierRegexp.matches(value) -> parseCubicBezier(value)
                else -> null
            }
        }

        fun isValid(command: String): Boolean = parse(command) != null

    }
}