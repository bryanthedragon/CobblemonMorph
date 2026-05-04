package bryanthedragon.cobblemon.morph.helper.registries;

import bryanthedragon.cobblemon.morph.helper.CobblemonMorphHelper;
import bryanthedragon.cobblemon.morph.helper.registries.tabs.CobblemonMorphTabRegistry;

public class CobblemonMorphRegistry extends CobblemonMorphHelper
{
    private static CobblemonMorphTabRegistry cobblemonmorphtabregistry;
    public static CobblemonMorphTabRegistry getCobblemonMorphTabRegistry()
    {
        return cobblemonmorphtabregistry;
    }
}
