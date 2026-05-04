package bryanthedragon.cobblemon.morph.helper.dependency.craftedcore;

import net.minecraftforge.fml.ModList;

public class CobblemonMorphCraftedcoreHelper {
    public static void GetHelper() {
        if (!ModList.get().isLoaded("craftedcore")) {
            throw new IllegalStateException("CraftedCore is required but not loaded!");
        }
    }
}
