package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast;

import com.bedrockk.molang.Expression;
import com.bedrockk.molang.StringHolder;
import com.bedrockk.molang.runtime.MoLangEnvironment;
import com.bedrockk.molang.runtime.MoScope;
import com.bedrockk.molang.runtime.value.MoValue;
import com.bedrockk.molang.runtime.value.StringValue;

public final class StringExpression extends StringHolder implements Expression {
   private final String string;

   @Override
   public MoValue evaluate(MoScope scope, MoLangEnvironment environment) {
      return new StringValue(this.string);
   }

   public StringExpression(String string) {
      this.string = string;
   }

   public String getString() {
      return this.string;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } 
      else if (!(o instanceof StringExpression other)) {
         return false;
      } 
      else if (!other.canEqual(this)) {
         return false;
      } 
      else {
         Object this$string = this.getString();
         Object other$string = other.getString();
         return this$string == null ? other$string == null : this$string.equals(other$string);
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof StringExpression;
   }

   @Override
   @SuppressWarnings("unused")
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $string = this.getString();
      return result * 59 + ($string == null ? 43 : $string.hashCode());
   }

   @Override
   public String toString() {
      return "StringExpression(string=" + this.getString() + ")";
   }
}
