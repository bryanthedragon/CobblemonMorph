package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast;

import com.bedrockk.molang.Expression;
import com.bedrockk.molang.StringHolder;
import com.bedrockk.molang.runtime.MoLangEnvironment;
import com.bedrockk.molang.runtime.MoParams;
import com.bedrockk.molang.runtime.MoScope;
import com.bedrockk.molang.runtime.value.MoValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class FuncCallExpression extends StringHolder implements Expression {
   private final Expression name;
   private final Expression[] args;

   @Override
   public MoValue evaluate(MoScope scope, MoLangEnvironment environment) {
      List<Expression> params = Arrays.asList(this.args);
      ArrayList<String> names = new ArrayList<>();
      if (this.name instanceof NameExpression) {
         names.addAll(((NameExpression)this.name).getNames());
      } 
      else {
         Collections.addAll(names, this.name.evaluate(scope, environment).asString().split("\\."));
      }

      ArrayList<MoValue> paramsParsed = new ArrayList<>(params.size());

      for (Expression param : params) {
         paramsParsed.add(param.evaluate(scope, environment));
      }

      return environment.getValue(names.iterator(), new MoParams(paramsParsed));
   }

   public FuncCallExpression(Expression name, Expression[] args) {
      this.name = name;
      this.args = args;
   }

   public Expression getName() {
      return this.name;
   }

   public Expression[] getArgs() {
      return this.args;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } 
      else if (!(o instanceof FuncCallExpression other)) {
         return false;
      } 
      else if (!other.canEqual(this)) {
         return false;
      } 
      else {
         Object this$name = this.getName();
         Object other$name = other.getName();
         return (this$name == null ? other$name == null : this$name.equals(other$name)) ? Arrays.deepEquals(this.getArgs(), other.getArgs()) : false;
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof FuncCallExpression;
   }

   @Override
   @SuppressWarnings("unused")
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $name = this.getName();
      result = result * 59 + ($name == null ? 43 : $name.hashCode());
      return result * 59 + Arrays.deepHashCode(this.getArgs());
   }

   @Override
   public String toString() {
      return "FuncCallExpression(name=" + this.getName() + ", args=" + Arrays.deepToString(this.getArgs()) + ")";
   }
}
