package bryanthedragon.morph.cobblemonmorph.helper.dependency.walkers;

import net.minecraftforge.fml.ModList;

public class CobblemonMorphWalkersHelper {
    public static void GetHelper() {
        if (!ModList.get().isLoaded("walkers")) {
            throw new IllegalStateException("walkers is required but not loaded!");
        }
    }
}
