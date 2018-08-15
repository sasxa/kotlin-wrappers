package kotlinx.css

private const val ZERO = "0"

class LinearDimension(val value: String) {
    companion object {
        val auto = LinearDimension("auto")
        val initial = LinearDimension("initial")
        val available = LinearDimension("available")
        val borderBox = LinearDimension("border-box")
        val contentBox = LinearDimension("content-box")
        val maxContent = LinearDimension("max-content")
        val minContent = LinearDimension("min-content")
        val fitContent = LinearDimension("fit-content")
    }

    private val valueCalcSafe: String
        get() = if (value == ZERO) "0px" else value

    override fun toString() = value

    operator fun unaryMinus() = LinearDimension(when {
        value.startsWith('-') -> value.substring(1)
        value.startsWith("calc") -> "calc(0px - $value)"
        value == ZERO -> value
        else -> "-$value"
    })

    operator fun plus(other: LinearDimension) = LinearDimension("calc($valueCalcSafe + ${other.valueCalcSafe})")
    operator fun minus(other: LinearDimension) = LinearDimension("calc($valueCalcSafe - ${other.valueCalcSafe})")
    operator fun times(times: Number) = LinearDimension("calc($valueCalcSafe * $times)")
    operator fun div(times: Number) = LinearDimension("calc($valueCalcSafe / $times)")
}

private fun value(number: Number, unit: String): String {
    return if (number == 0)
        ZERO
    else
        number.toString() + unit
}

val Number.em: LinearDimension get() = LinearDimension(value(this, "em"))
val Number.rem: LinearDimension get() = LinearDimension(value(this, "rem"))
val Number.pct: LinearDimension get() = LinearDimension(value(this, "%"))
val Number.px: LinearDimension get() = LinearDimension(value(this, "px"))
val Number.vw: LinearDimension get() = LinearDimension(value(this, "vw"))
val Number.vh: LinearDimension get() = LinearDimension(value(this, "vh"))
