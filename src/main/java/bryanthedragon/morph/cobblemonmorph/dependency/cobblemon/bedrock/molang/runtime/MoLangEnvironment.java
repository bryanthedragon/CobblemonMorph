package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.MoStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.VariableStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.DoubleValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.MoValue;

import java.util.HashMap;
import java.util.Iterator;

public final class MoLangEnvironment
implements MoValue {
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private final HashMap<String, MoStruct> structs = new HashMap();

    public MoValue getValue(Iterator<String> names) {
        return this.getValue(names, MoParams.EMPTY);
    }

    public MoValue getValue(Iterator<String> names, MoParams params) {
        String main = names.next();
        MoStruct struct2 = this.structs.get(main);
        if (struct2 != null) {
            return struct2.get(names, params);
        }
        return new DoubleValue(0.0);
    }

    public void setValue(Iterator<String> names, MoValue value2) {
        String main = names.next();
        MoStruct struct2 = this.structs.get(main);
        if (struct2 != null) {
            struct2.set(names, value2);
        }
    }

    public void setSimpleVariable(String name, MoValue value2) {
        ((VariableStruct)this.structs.get("variable")).setDirectly(name, value2);
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

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof MoLangEnvironment)) {
            return false;
        }
        MoLangEnvironment other = (MoLangEnvironment)o;
        HashMap<String, MoStruct> this$structs = this.getStructs();
        HashMap<String, MoStruct> other$structs = other.getStructs();
        return !(this$structs == null ? other$structs != null : !((Object)this$structs).equals(other$structs));
    }

    @SuppressWarnings("unused")
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        HashMap<String, MoStruct> $structs = this.getStructs();
        result = result * 59 + ($structs == null ? 43 : ((Object)$structs).hashCode());
        return result;
    }

    public String toString() {
        return "MoLangEnvironment(structs=" + this.getStructs() + ")";
    }
}

