package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math

public class FloatRange(start: Float, endInclusive: Float) : ClosedFloatingPointRange<java.lang.Float> {
   public open val endInclusive: Float
   public open val start: Float

   init {
      this.start = start;
      this.endInclusive = endInclusive;
   }

   public open operator fun contains(value: Float): Boolean {
      return value <= this.getEndInclusive() && this.getStart() <= value;
   }

   public open fun isEmpty(): Boolean {
      return this.getStart() > this.getEndInclusive();
   }

   public open fun lessThanOrEquals(a: Float, b: Float): Boolean {
      return a <= b;
   }

   public override operator fun equals(other: Any?): Boolean {
      return other is FloatRange && this.getStart() == (other as FloatRange).getStart() && this.getEndInclusive() == (other as FloatRange).getEndInclusive();
   }

   public override fun hashCode(): Int {
      return 31 * java.lang.Float.hashCode(this.getStart()) + java.lang.Float.hashCode(this.getEndInclusive());
   }

   public override fun toString(): String {
      return "${this.getStart()}..${this.getEndInclusive()}";
   }
}
