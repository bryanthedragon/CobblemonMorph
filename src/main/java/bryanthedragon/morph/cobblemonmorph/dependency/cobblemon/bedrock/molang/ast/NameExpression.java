package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast;

import com.bedrockk.molang.Expression;
import com.bedrockk.molang.StringHolder;
import com.bedrockk.molang.runtime.MoLangEnvironment;
import com.bedrockk.molang.runtime.MoScope;
import com.bedrockk.molang.runtime.value.MoValue;
import java.util.ArrayList;

public final class NameExpression extends StringHolder implements Expression {
   private final ArrayList<String> names;

   @Override
   public MoValue evaluate(MoScope scope, MoLangEnvironment environment) {
      return environment.getValue(this.getNames().iterator());
   }

   @Override
   public void assign(MoScope scope, MoLangEnvironment environment, MoValue value) {
      environment.setValue(this.getNames().iterator(), value);
   }

   public NameExpression(ArrayList<String> names) {
      this.names = names;
   }

   public ArrayList<String> getNames() {
      return this.names;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof NameExpression other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$names = this.getNames();
         Object other$names = other.getNames();
         return this$names == null ? other$names == null : this$names.equals(other$names);
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof NameExpression;
   }

   @Override
   @SuppressWarnings("unused")
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $names = this.getNames();
      return result * 59 + ($names == null ? 43 : $names.hashCode());
   }

   @Override
   public String toString() {
      return "NameExpression(names=" + this.getNames() + ")";
   }
}
