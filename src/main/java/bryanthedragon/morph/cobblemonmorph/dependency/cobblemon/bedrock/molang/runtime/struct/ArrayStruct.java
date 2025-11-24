package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.MoValue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class ArrayStruct
extends VariableStruct {
    public ArrayStruct() {
    }

    public ArrayStruct(Map<String, MoValue> map) {
        super(map);
    }

    @Override
    public void set(Iterator<String> names, MoValue value2) {
        ArrayList<String> namesList = new ArrayList<String>();
        while (names.hasNext()) {
            String name = names.next();
            if (!names.hasNext()) {
                namesList.add(String.valueOf(Integer.parseInt(name)));
                continue;
            }
            namesList.add(name);
        }
        super.set(namesList.iterator(), value2);
    }
}

