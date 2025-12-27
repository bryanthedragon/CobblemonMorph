package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct;

import com.bedrockk.molang.runtime.MoParams;
import com.bedrockk.molang.runtime.value.DoubleValue;
import com.bedrockk.molang.runtime.value.MoValue;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class VariableStruct implements MoStruct {
   private final Map<String, MoValue> map;

   public VariableStruct() {
      this.map = new HashMap<>();
   }

   @Override
   public void set(Iterator<String> names, MoValue value) {
      String main = names.next();
      if (names.hasNext() && main != null) {
         Object struct = this.map.get(main);
         if (!(struct instanceof MoStruct)) {
            struct = new VariableStruct();
         }

         ((MoStruct)struct).set(names, value);
         this.map.put(main, (MoStruct)struct);
      } else {
         this.map.put(main, value);
      }
   }

   public void setDirectly(String name, MoValue value) {
      this.map.put(name, value);
   }

   @Override
   public MoValue get(Iterator<String> names, MoParams params) {
      String main = names.next();
      if (names.hasNext() && main != null) {
         Object struct = this.map.get(main);
         if (struct instanceof MoStruct) {
            return ((MoStruct)struct).get(names, params);
         }
      }

      return this.map.getOrDefault(main, new DoubleValue(0.0));
   }

   @Override
   public void clear() {
      this.map.clear();
   }

   public Map<String, MoValue> getMap() {
      return this.map;
   }

   public VariableStruct(Map<String, MoValue> map) {
      this.map = map;
   }
}
