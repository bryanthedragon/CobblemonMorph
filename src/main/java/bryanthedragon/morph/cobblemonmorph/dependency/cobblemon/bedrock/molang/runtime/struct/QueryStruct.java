package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct;

import com.bedrockk.molang.runtime.MoParams;
import com.bedrockk.molang.runtime.value.MoValue;

import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Function;

public class QueryStruct implements MoStruct {
   public final HashMap<String, Function<MoParams, Object>> functions;

   @Override
   public MoValue get(Iterator<String> names, MoParams params) {
      String key = names.next();
      Function<MoParams, Object> func = this.functions.get(key);
      MoParams currentParams = names.hasNext() ? MoParams.EMPTY : params;
      if (func != null) {
         Object result = func.apply(currentParams);
         return result instanceof MoStruct && names.hasNext() ? ((MoStruct)result).get(names, params) : MoValue.of(result);
      } else {
         return null;
      }
   }

   @Override
   public void set(Iterator<String> names, MoValue value) {
      throw new RuntimeException("Cannot set a value in query struct");
   }

   @Override
   public void clear() {
      this.functions.clear();
   }

   public QueryStruct addFunction(String name, Function<MoParams, Object> func) {
      this.functions.put(name, func);
      return this;
   }

   public HashMap<String, Function<MoParams, Object>> getFunctions() {
      return this.functions;
   }

   public QueryStruct(HashMap<String, Function<MoParams, Object>> functions) {
      this.functions = functions;
   }
}
