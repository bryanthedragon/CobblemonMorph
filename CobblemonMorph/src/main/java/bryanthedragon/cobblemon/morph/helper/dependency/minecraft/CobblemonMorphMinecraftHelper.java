package bryanthedragon.morph.cobblemonmorph.helper.dependency.minecraft;

import net.minecraftforge.fml.ModList;

public class CobblemonMorphMinecraftHelper {
    public static void GetHelper() {
        if (!ModList.get().isLoaded("Minecraft")) {
            throw new IllegalStateException("Minecraft is required but not loaded!");
        }
    }
}
