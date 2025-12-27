package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast;

import com.bedrockk.molang.Expression;
import com.bedrockk.molang.StringHolder;
import com.bedrockk.molang.runtime.MoLangEnvironment;
import com.bedrockk.molang.runtime.MoScope;
import com.bedrockk.molang.runtime.value.DoubleValue;
import com.bedrockk.molang.runtime.value.MoValue;

public final class BreakExpression extends StringHolder implements Expression {
   @Override
   public MoValue evaluate(MoScope scope, MoLangEnvironment environment) {
      scope.setBreak(true);
      return DoubleValue.ZERO;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else {
         return !(o instanceof BreakExpression other) ? false : other.canEqual(this);
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof BreakExpression;
   }

   @Override
   @SuppressWarnings("unused")
   public int hashCode() {
      int result = 1;
      return 1;
   }

   @Override
   public String toString() {
      return "BreakExpression()";
   }
}
