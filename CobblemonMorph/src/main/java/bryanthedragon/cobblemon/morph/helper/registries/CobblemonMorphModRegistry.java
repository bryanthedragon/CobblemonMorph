package bryanthedragon.cobblemon.morph.helper.registries;

import bryanthedragon.cobblemon.morph.helper.CobblemonMorphHelper;
import bryanthedragon.cobblemon.morph.helper.registries.tabs.CobblemonMorphTabRegistry;
import bryanthedragon.cobblemon.morph.helper.registries.mod.CobblemonMorphCobblemonRegistry;

public class CobblemonMorphModRegistry extends CobblemonMorphHelper
{
    private static CobblemonMorphTabRegistry cobblemonmorphtabregistry;
    private static CobblemonMorphCobblemonRegistry cobblemonmorphcobblemonregistry;
    public static CobblemonMorphTabRegistry getCobblemonMorphTabRegistry()
    {
        return cobblemonmorphtabregistry;
    }
    public static CobblemonMorphCobblemonRegistry getCobblemonMorphCobblemonRegistry()
    {
        return cobblemonmorphcobblemonregistry;
    }
}
