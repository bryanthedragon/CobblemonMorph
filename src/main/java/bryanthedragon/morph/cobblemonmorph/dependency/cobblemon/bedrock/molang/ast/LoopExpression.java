package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast;

import com.bedrockk.molang.Expression;
import com.bedrockk.molang.StringHolder;
import com.bedrockk.molang.runtime.MoLangEnvironment;
import com.bedrockk.molang.runtime.MoScope;
import com.bedrockk.molang.runtime.value.DoubleValue;
import com.bedrockk.molang.runtime.value.MoValue;

public final class LoopExpression extends StringHolder implements Expression {
   private final Expression count;
   private final Expression body;

   @Override
   public MoValue evaluate(MoScope scope, MoLangEnvironment environment) {
      int loop = (int)this.count.evaluate(scope, environment).asDouble();
      MoScope subScope = new MoScope();

      while (loop > 0) {
         this.body.evaluate(subScope, environment);
         loop--;
         if (subScope.getReturnValue() != null) {
            return subScope.getReturnValue();
         }

         if (subScope.isBreak()) {
            break;
         }
      }

      return DoubleValue.ZERO;
   }

   public LoopExpression(Expression count, Expression body) {
      this.count = count;
      this.body = body;
   }

   public Expression getCount() {
      return this.count;
   }

   public Expression getBody() {
      return this.body;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof LoopExpression other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$count = this.getCount();
         Object other$count = other.getCount();
         if (this$count == null ? other$count == null : this$count.equals(other$count)) {
            Object this$body = this.getBody();
            Object other$body = other.getBody();
            return this$body == null ? other$body == null : this$body.equals(other$body);
         } else {
            return false;
         }
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof LoopExpression;
   }

   @Override
   @SuppressWarnings("unused")
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $count = this.getCount();
      result = result * 59 + ($count == null ? 43 : $count.hashCode());
      Object $body = this.getBody();
      return result * 59 + ($body == null ? 43 : $body.hashCode());
   }

   @Override
   public String toString() {
      return "LoopExpression(count=" + this.getCount() + ", body=" + this.getBody() + ")";
   }
}
