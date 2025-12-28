package bryanthedragon.morph.cobblemonmorph.helper.dependency.kotlin;

import net.minecraftforge.fml.ModList;

public class CobblemonMorphKotlinHelper {
    public static void GetHelper() {
        if (!ModList.get().isLoaded("kotlinforforge")) {
            throw new IllegalStateException("KotlinForForge is required but not loaded!");
        }
    }
}
