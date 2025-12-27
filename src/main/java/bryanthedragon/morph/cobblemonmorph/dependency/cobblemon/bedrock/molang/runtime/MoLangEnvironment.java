package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime;

import com.bedrockk.molang.runtime.struct.MoStruct;
import com.bedrockk.molang.runtime.struct.VariableStruct;
import com.bedrockk.molang.runtime.value.DoubleValue;
import com.bedrockk.molang.runtime.value.MoValue;

import java.util.HashMap;
import java.util.Iterator;

public final class MoLangEnvironment implements MoValue {
   private final HashMap<String, MoStruct> structs = new HashMap<>();

   public MoValue getValue(Iterator<String> names) {
      return this.getParamValue(names, MoParams.EMPTY);
   }

   public MoValue getParamValue(Iterator<String> names, com.bedrockk.molang.runtime.MoParams empty) {
      String main = names.next();
      MoStruct struct = this.structs.get(main);
      return (MoValue)(struct != null ? struct.get(names, empty) : new DoubleValue(0.0));
   }

   public void setValue(Iterator<String> names, MoValue value) {
      String main = names.next();
      MoStruct struct = this.structs.get(main);
      if (struct != null) {
         struct.set(names, value);
      }
   }

   public void setSimpleVariable(String name, MoValue value) {
      ((VariableStruct)this.structs.get("variable")).setDirectly(name, value);
   }

   public MoValue getSimpleVariable(String name) {
      return ((VariableStruct)this.structs.get("variable")).getMap().get(name);
   }

   @Override
   public Object value() {
      return this;
   }

   public HashMap<String, MoStruct> getStructs() {
      return this.structs;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } 
      else if (!(o instanceof MoLangEnvironment other)) {
         return false;
      } 
      else {
         Object this$structs = this.getStructs();
         Object other$structs = other.getStructs();
         return this$structs == null ? other$structs == null : this$structs.equals(other$structs);
      }
   }


   @Override
   @SuppressWarnings("unused")
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $structs = this.getStructs();
      return result * 59 + ($structs == null ? 43 : $structs.hashCode());
   }

   @Override
   public String toString() {
      return "MoLangEnvironment(structs=" + this.getStructs() + ")";
   }
}
