package bryanthedragon.cobblemon.morph.helper.dependency.cobblemon;

import net.minecraftforge.fml.ModList;

public class CobblemonMorphCobblemonHelper {
    public static void GetHelper() {
        if (!ModList.get().isLoaded("cobblemon")) {
            throw new IllegalStateException("cobblemon is required but not loaded!");
        }
    }
}
