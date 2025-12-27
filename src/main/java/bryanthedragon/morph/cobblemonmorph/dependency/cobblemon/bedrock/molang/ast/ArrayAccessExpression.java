package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast;

import com.bedrockk.molang.Expression;
import com.bedrockk.molang.StringHolder;
import com.bedrockk.molang.runtime.MoLangEnvironment;
import com.bedrockk.molang.runtime.MoScope;
import com.bedrockk.molang.runtime.value.MoValue;

import java.util.ArrayList;
import java.util.Collections;

public final class ArrayAccessExpression extends StringHolder implements Expression {
   private final Expression array;
   private final Expression index;

   @Override
   public MoValue evaluate(MoScope scope, MoLangEnvironment environment) {
      ArrayList<String> names = new ArrayList<>();
      if (this.array instanceof NameExpression) {
         names = ((NameExpression)this.array).getNames();
      } else {
         Collections.addAll(names, this.array.evaluate(scope, environment).asString().split("\\."));
      }

      names.add(String.valueOf((int)this.index.evaluate(scope, environment).asDouble()));
      return environment.getValue(names.iterator());
   }

   @Override
   public void assign(MoScope scope, MoLangEnvironment environment, MoValue value) {
      ArrayList<String> names = new ArrayList<>();
      if (this.array instanceof NameExpression) {
         names = ((NameExpression)this.array).getNames();
      } else {
         Collections.addAll(names, this.array.evaluate(scope, environment).asString().split("\\."));
      }

      names.add(String.valueOf((int)this.index.evaluate(scope, environment).asDouble()));
      environment.setValue(names.iterator(), value);
   }

   public ArrayAccessExpression(Expression array, Expression index) {
      this.array = array;
      this.index = index;
   }

   public Expression getArray() {
      return this.array;
   }

   public Expression getIndex() {
      return this.index;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } 
      else if (!(o instanceof ArrayAccessExpression other)) {
         return false;
      } 
      else if (!other.canEqual(this)) {
         return false;
      } 
      else {
         Object this$array = this.getArray();
         Object other$array = other.getArray();
         if (this$array == null ? other$array == null : this$array.equals(other$array)) {
            Object this$index = this.getIndex();
            Object other$index = other.getIndex();
            return this$index == null ? other$index == null : this$index.equals(other$index);
         } 
         else {
            return false;
         }
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof ArrayAccessExpression;
   }

   @Override
   @SuppressWarnings("unused")
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $array = this.getArray();
      result = result * 59 + ($array == null ? 43 : $array.hashCode());
      Object $index = this.getIndex();
      return result * 59 + ($index == null ? 43 : $index.hashCode());
   }

   @Override
   public String toString() {
      return "ArrayAccessExpression(array=" + this.getArray() + ", index=" + this.getIndex() + ")";
   }
}
