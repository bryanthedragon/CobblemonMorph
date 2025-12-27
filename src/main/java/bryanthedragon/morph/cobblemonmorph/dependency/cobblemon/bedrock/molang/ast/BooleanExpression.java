package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast;

import com.bedrockk.molang.Expression;
import com.bedrockk.molang.StringHolder;
import com.bedrockk.molang.runtime.MoLangEnvironment;
import com.bedrockk.molang.runtime.MoScope;
import com.bedrockk.molang.runtime.value.DoubleValue;
import com.bedrockk.molang.runtime.value.MoValue;

public final class BooleanExpression extends StringHolder implements Expression {
   private final boolean value;

   @Override
   public MoValue evaluate(MoScope scope, MoLangEnvironment environment) {
      return new DoubleValue(this.value);
   }

   public BooleanExpression(boolean value) {
      this.value = value;
   }

   public boolean isValue() {
      return this.value;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof BooleanExpression other)) {
         return false;
      } else {
         return !other.canEqual(this) ? false : this.isValue() == other.isValue();
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof BooleanExpression;
   }

   @Override
   @SuppressWarnings("unused")
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      return result * 59 + (this.isValue() ? 79 : 97);
   }

   @Override
   public String toString() {
      return "BooleanExpression(value=" + this.isValue() + ")";
   }
}
