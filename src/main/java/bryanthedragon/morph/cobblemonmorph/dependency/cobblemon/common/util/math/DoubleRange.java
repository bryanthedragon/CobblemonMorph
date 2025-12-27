package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math

public class DoubleRange(start: Double, endInclusive: Double) : ClosedFloatingPointRange<java.lang.Double> {
   public open val endInclusive: Double
   public open val start: Double

   init {
      this.start = start;
      this.endInclusive = endInclusive;
   }

   public open operator fun contains(value: Double): Boolean {
      return value <= this.getEndInclusive() && this.getStart() <= value;
   }

   public open fun isEmpty(): Boolean {
      return this.getStart() > this.getEndInclusive();
   }

   public open fun lessThanOrEquals(a: Double, b: Double): Boolean {
      return a <= b;
   }

   public override operator fun equals(other: Any?): Boolean {
      return other is DoubleRange && this.getStart() == (other as DoubleRange).getStart() && this.getEndInclusive() == (other as DoubleRange).getEndInclusive();
   }

   public override fun hashCode(): Int {
      return 31 * java.lang.Double.hashCode(this.getStart()) + java.lang.Double.hashCode(this.getEndInclusive());
   }

   public override fun toString(): String {
      return "${this.getStart()}..${this.getEndInclusive()}";
   }
}
