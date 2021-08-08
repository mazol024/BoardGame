package rationals
import java.math.BigInteger
import java.util.NoSuchElementException

class Rational (numerator:BigInteger,denominator:BigInteger){
    val rational:Pair<BigInteger,BigInteger> = Pair(if (numerator<0.toBigInteger()) -numerator else numerator,
        if (denominator< 0.toBigInteger()) -denominator else denominator)
    val normalized: Pair<BigInteger,BigInteger> = normalizeIt()
    var minussign: String
    init {
        this.minussign = if((numerator > "0".toBigInteger() && denominator > "0".toBigInteger())
            or (numerator < "0".toBigInteger() && denominator < "0".toBigInteger() ) ) "" else "-"
    }
    private fun normalizeIt():Pair<BigInteger,BigInteger> {
        var gcd:BigInteger = this.rational.second.gcd(this.rational.first)
        return if (gcd != 0.toBigInteger())  Pair<BigInteger,BigInteger>(this.rational.first/gcd,this.rational.second/gcd) else this.rational
    }

    override fun toString(): String {
        return "${this.minussign}" +
                "${this.normalized.first}" +
                "${if (this.normalized.second != 1.toBigInteger()) "/".plus(this.normalized.second) else ""}"
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Rational) throw NoSuchElementException("What are you going compaire? ")
        return if ((this.normalized.first == other.normalized.first) && (this.normalized.second == other.normalized.second)
            && (this.minussign == other.minussign)) true else false
    }

}



infix  fun Int.divBy(b:Int):Rational = Rational(this.toBigInteger(),b.toBigInteger())
infix  fun Long.divBy(b:Long):Rational =Rational(this.toBigInteger(),b.toBigInteger())
infix  fun BigInteger.divBy(b:BigInteger):Rational =Rational(this,b)

fun String.toRational():Rational {
    var pair = this.split("/")
    var outp: Rational
    when {
        pair.size == 0 -> outp = Rational(0.toBigInteger(),0.toBigInteger())
        pair.size ==1 -> outp = Rational(pair[0].toBigInteger(),1.toBigInteger())
        else -> outp = Rational(pair[0].toBigInteger(),pair[1].toBigInteger())
    }
    return outp
}

operator  fun Rational.unaryMinus(): Rational {
    if (this.minussign == "") this.minussign= "-" else this.minussign = ""
    return this
}

infix operator fun Rational.plus(b:Rational): Rational = Rational(this.rational.first*b.rational.second +
        this.rational.second*b.rational.first,
    this.rational.second*b.rational.second)
infix operator fun Rational.minus(b:Rational): Rational = Rational(this.rational.first*b.rational.second -
        this.rational.second*b.rational.first,
    this.rational.second*b.rational.second)
infix operator fun Rational.times(b:Rational): Rational = Rational( if (this.minussign == b.minussign)
    this.rational.first*b.rational.first else -this.rational.first*b.rational.first,
    this.rational.second*b.rational.second)
infix operator fun Rational.div(b:Rational): Rational = Rational( if (this.minussign == b.minussign)
    this.rational.first*b.rational.second else -this.rational.first*b.rational.second,
    this.rational.second*b.rational.first)
operator fun BigInteger.rangeTo(b: BigInteger):ClosedRange<BigInteger> = this..b
operator fun Rational.rangeTo(b:Rational): ClosedRange<Float> = this.rational.first.toFloat()/this.rational.second.toFloat()..b.rational.first.toFloat()/b.rational.second.toFloat()
operator fun ClosedRange<Float>.contains(b: Rational): Boolean = if ((b.rational.first.toFloat()/b.rational.second.toFloat()>=this.start)
    && (b.rational.first.toFloat()/b.rational.second.toFloat()<=this.endInclusive) ) true else false

infix operator fun Rational.compareTo(b: Rational) :Int {
    return ( if (this.minussign == "") this.normalized.first*b.normalized.second else -this.normalized.first*b.normalized.second)
        .compareTo( if (b.minussign == "") b.normalized.first*this.normalized.second else -b.normalized.first*this.normalized.second )
}

fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(" half < twoThirds is :  ${half < twoThirds} ${half} < ${twoThirds}")
    println("87077/297895 < 40687/138970 true ${87077 divBy 297895 < 40687 divBy 138970}")


    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)
}