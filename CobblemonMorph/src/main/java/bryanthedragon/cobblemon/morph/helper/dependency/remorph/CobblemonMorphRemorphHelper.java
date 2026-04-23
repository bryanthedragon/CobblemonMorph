package bryanthedragon.morph.cobblemonmorph.helper.dependency.remorph;

import net.minecraftforge.fml.ModList;

public class CobblemonMorphRemorphHelper {
    public static void GetHelper() {
        if (!ModList.get().isLoaded("remorphed")) {
            throw new IllegalStateException("Remorphed is required but not loaded!");
        }
    }
}
