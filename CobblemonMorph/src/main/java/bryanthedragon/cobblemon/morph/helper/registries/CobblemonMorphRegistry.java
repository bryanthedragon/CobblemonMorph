package bryanthedragon.morph.cobblemonmorph.helper.registries;

import bryanthedragon.morph.cobblemonmorph.helper.CobblemonMorphHelper;
import bryanthedragon.morph.cobblemonmorph.helper.registries.tabs.CobblemonMorphTabRegistry;

public class CobblemonMorphRegistry extends CobblemonMorphHelper
{
    private static CobblemonMorphTabRegistry cobblemonmorphtabregistry;
    public static CobblemonMorphTabRegistry getCobblemonMorphTabRegistry()
    {
        return cobblemonmorphtabregistry;
    }
}
