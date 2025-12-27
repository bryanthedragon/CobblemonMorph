package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang;

import com.bedrockk.molang.runtime.MoLangEnvironment;
import com.bedrockk.molang.runtime.MoScope;
import com.bedrockk.molang.runtime.value.MoValue;

import java.util.HashMap;
import java.util.Map;

public interface Expression {
   Map<String, Object> attributes = new HashMap<>();

   String getOriginalString();

   void setOriginalString(String var1);

   default Map<String, Object> getAttributes() {
      return attributes;
   }

   MoValue evaluate(MoScope var1, MoLangEnvironment var2);

   default void assign(MoScope scope, MoLangEnvironment environment, MoValue value) {
      throw new RuntimeException("Cannot assign a value to " + this.getClass());
   }
}
