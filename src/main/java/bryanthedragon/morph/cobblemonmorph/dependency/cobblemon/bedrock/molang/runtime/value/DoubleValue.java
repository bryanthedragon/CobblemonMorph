package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value;

public class DoubleValue implements MoValue {
   public static final DoubleValue ZERO = new DoubleValue(0.0);
   public static final DoubleValue ONE = new DoubleValue(1.0);
   public double value;

   public DoubleValue(Object value) {
      if (value instanceof Boolean) {
         this.value = (Boolean)value ? 1.0 : 0.0;
      } else if (value instanceof Number) {
         this.value = ((Number)value).doubleValue();
      } else {
         this.value = 1.0;
      }
   }

   public Double value() {
      return this.value;
   }

   @Override
   public String asString() {
      return Double.toString(this.value);
   }

   @Override
   public double asDouble() {
      return this.value;
   }

   @Override
   public boolean equals(Object obj) {
      return super.equals(obj)
         || obj instanceof DoubleValue && ((DoubleValue)obj).asDouble() == this.value
         || obj instanceof StringValue && ((StringValue)obj).asString().equals(this.asString());
   }
}
