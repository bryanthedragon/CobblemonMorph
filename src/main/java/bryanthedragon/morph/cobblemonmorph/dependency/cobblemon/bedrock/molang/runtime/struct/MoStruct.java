package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct;

import com.bedrockk.molang.runtime.MoParams;
import com.bedrockk.molang.runtime.value.MoValue;

import java.util.Iterator;

public interface MoStruct extends MoValue {
   void set(Iterator<String> var1, MoValue var2);

   MoValue get(Iterator<String> var1, MoParams var2);

   void clear();

   default MoStruct value() {
      return this;
   }
}
