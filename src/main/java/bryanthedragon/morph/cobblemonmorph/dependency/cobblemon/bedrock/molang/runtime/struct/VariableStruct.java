package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoParams;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.DoubleValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.MoValue;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class VariableStruct
implements MoStruct {
    private final Map<String, MoValue> map;

    public VariableStruct() {
        this.map = new HashMap<String, MoValue>();
    }

    @Override
    public void set(Iterator<String> names, MoValue value2) {
        String main = names.next();
        if (names.hasNext() && main != null) {
            MoValue struct2 = this.map.get(main);
            if (!(struct2 instanceof MoStruct)) {
                struct2 = new VariableStruct();
            }
            ((MoStruct)struct2).set(names, value2);
            this.map.put(main, (MoStruct)struct2);
        } else {
            this.map.put(main, value2);
        }
    }

    public void setDirectly(String name, MoValue value2) {
        this.map.put(name, value2);
    }

    @Override
    public MoValue get(Iterator<String> names, MoParams params) {
        MoValue struct2;
        String main = names.next();
        if (names.hasNext() && main != null && (struct2 = this.map.get(main)) instanceof MoStruct) {
            return ((MoStruct)struct2).get(names, params);
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

