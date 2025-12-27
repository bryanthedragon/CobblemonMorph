package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime;

import com.bedrockk.molang.runtime.struct.MoStruct;
import com.bedrockk.molang.runtime.value.DoubleValue;
import com.bedrockk.molang.runtime.value.MoValue;
import com.bedrockk.molang.runtime.value.StringValue;
import java.util.ArrayList;
import java.util.List;

public class MoParams {
   public static final com.bedrockk.molang.runtime.MoParams EMPTY = new com.bedrockk.molang.runtime.MoParams(new ArrayList<>());
   private final List<MoValue> params;

   public MoParams(List<MoValue> params) {
      this.params = params;
   }

   @SuppressWarnings("unchecked")
   public <T extends MoValue> T get(int index) {
      return (T)this.params.get(index);
   }

   public boolean contains(int index) {
      return this.params.size() >= index + 1;
   }

   public int getInt(int index) {
      return (int)this.getDouble(index);
   }

   public double getDouble(int index) {
      return this.<DoubleValue>get(index).asDouble();
   }

   public MoStruct getStruct(int index) {
      return this.get(index);
   }

   public String getString(int index) {
      return this.<StringValue>get(index).asString();
   }

   public MoLangEnvironment getEnv(int index) {
      return this.get(index);
   }

   public List<MoValue> getParams() {
      return this.params;
   }
}
