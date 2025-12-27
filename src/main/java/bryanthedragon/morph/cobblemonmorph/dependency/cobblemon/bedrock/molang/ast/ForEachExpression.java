package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast;

import com.bedrockk.molang.Expression;
import com.bedrockk.molang.StringHolder;
import com.bedrockk.molang.runtime.MoLangEnvironment;
import com.bedrockk.molang.runtime.MoScope;
import com.bedrockk.molang.runtime.struct.VariableStruct;
import com.bedrockk.molang.runtime.value.DoubleValue;
import com.bedrockk.molang.runtime.value.MoValue;

import java.util.ArrayList;

public final class ForEachExpression extends StringHolder implements Expression {
   private final Expression variable;
   private final Expression array;
   private final Expression body;

   @Override
   public MoValue evaluate(MoScope scope, MoLangEnvironment environment) {
      if (this.array.evaluate(scope, environment) instanceof VariableStruct struct) {
         MoScope scope2 = new MoScope();

         for (MoValue value : new ArrayList<>(struct.getMap().values())) {
            this.variable.assign(scope2, environment, value);
            this.body.evaluate(scope2, environment);
            if (scope2.getReturnValue() != null) {
               return scope2.getReturnValue();
            }

            if (scope2.isBreak()) {
               break;
            }
         }
      }

      return DoubleValue.ZERO;
   }

   public ForEachExpression(Expression variable, Expression array, Expression body) {
      this.variable = variable;
      this.array = array;
      this.body = body;
   }

   public Expression getVariable() {
      return this.variable;
   }

   public Expression getArray() {
      return this.array;
   }

   public Expression getBody() {
      return this.body;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } 
      else if (!(o instanceof ForEachExpression other)) {
         return false;
      } 
      else if (!other.canEqual(this)) {
         return false;
      } 
      else {
         Object this$variable = this.getVariable();
         Object other$variable = other.getVariable();
         if (this$variable == null ? other$variable == null : this$variable.equals(other$variable)) {
            Object this$array = this.getArray();
            Object other$array = other.getArray();
            if (this$array == null ? other$array == null : this$array.equals(other$array)) {
               Object this$body = this.getBody();
               Object other$body = other.getBody();
               return this$body == null ? other$body == null : this$body.equals(other$body);
            } 
            else {
               return false;
            }
         } 
         else {
            return false;
         }
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof ForEachExpression;
   }

   @Override
   @SuppressWarnings("unused")
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $variable = this.getVariable();
      result = result * 59 + ($variable == null ? 43 : $variable.hashCode());
      Object $array = this.getArray();
      result = result * 59 + ($array == null ? 43 : $array.hashCode());
      Object $body = this.getBody();
      return result * 59 + ($body == null ? 43 : $body.hashCode());
   }

   @Override
   public String toString() {
      return "ForEachExpression(variable=" + this.getVariable() + ", array=" + this.getArray() + ", body=" + this.getBody() + ")";
   }
}
