package bryanthedragon.morph.cobblemonmorph.helper.dependency;

import java.net.URL;
import java.util.Optional;

import bryanthedragon.morph.cobblemonmorph.CobblemonMorph;
import bryanthedragon.morph.cobblemonmorph.helper.dependency.cobblemon.CobblemonMorphCobblemonHelper;
import bryanthedragon.morph.cobblemonmorph.helper.dependency.craftedcore.CobblemonMorphCraftedcoreHelper;
import bryanthedragon.morph.cobblemonmorph.helper.dependency.minecraft.CobblemonMorphMinecraftHelper;
import bryanthedragon.morph.cobblemonmorph.helper.dependency.remorph.CobblemonMorphRemorphHelper;
import bryanthedragon.morph.cobblemonmorph.helper.dependency.kotlin.CobblemonMorphKotlinHelper;
import bryanthedragon.morph.cobblemonmorph.helper.dependency.walkers.CobblemonMorphWalkersHelper;

import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class CobblemonMorphDependencyHelper extends CobblemonMorph
{
    public CobblemonMorphDependencyHelper(FMLJavaModLoadingContext context) 
    {
        super(context);
    }

    /**
     * Returns whether or not a mod with the given id is loaded.
     */
    public static boolean isModLoaded(String modid)
    {
        return net.minecraftforge.fml.ModList.get().isLoaded(modid);
    }
    
    /**
     * Returns the version of the given mod, or "N/A" if the mod is not found.
     */
    public static String getModVersion(String modid)
    {
        return net.minecraftforge.fml.ModList.get().getModContainerById(modid).map(modContainer -> modContainer.getModInfo().getVersion().toString()).orElse("N/A");
    }

    /**
     * Returns the name of the given mod, or "N/A" if the mod is not found.
     */
    public static String getModDisplayName(String modid)
    {
        return net.minecraftforge.fml.ModList.get().getModContainerById(modid).map(modContainer -> modContainer.getModInfo().getDisplayName()).orElse("N/A");
    }
    
    /**
     * * Generates the doc string for the given mod
     */
    public static String getModDescription(String modid)
    {
        return net.minecraftforge.fml.ModList.get().getModContainerById(modid).map(modContainer -> modContainer.getModInfo().getDescription()).orElse("N/A");
    }

    /**
     * Returns the URL of the mod with the given id if it is loaded, or throws an exception if it is not.
     * @param modid the id of the mod to get the URL of
     * @return the URL of the mod if it is loaded
     * @throws IllegalStateException if the mod is not loaded
     */
    public static Optional<URL> getModURL(String modid)
    {
        return net.minecraftforge.fml.ModList.get().getModContainerById(modid).map(modContainer -> modContainer.getModInfo().getModURL()).orElseThrow();
    }

    /**
     * Checks if all dependencies of this mod are loaded, and throws an exception if they are not.
     * This method should be called at the start of the mod's life cycle, as it will throw an exception if any dependencies are missing.
     * @throws IllegalStateException if any dependencies are missing
     */
    public static void dependencyCheck() 
    {
        if (!isModLoaded("kotlin") || !isModLoaded("craftedcore") || !isModLoaded("remorphed") || !isModLoaded("cobblemon") || !isModLoaded("minecraft") || !isModLoaded("walkers")) 
        {
            CobblemonMorphKotlinHelper.GetHelper();
            CobblemonMorphCraftedcoreHelper.GetHelper();
            CobblemonMorphRemorphHelper.GetHelper();
            CobblemonMorphCobblemonHelper.GetHelper();
            CobblemonMorphMinecraftHelper.GetHelper();
            CobblemonMorphWalkersHelper.GetHelper();
            try {
                throw new IllegalStateException("One or more dependencies are missing!");
            } 
            catch (IllegalStateException e) {
                CobblemonMorph.LOGGER.error("Dependency check failed: " + e.getMessage());
                throw e;
            }
        }
        else 
        {
            CobblemonMorph.LOGGER.info("All dependencies are loaded!");
        }
//         public static boolean isModVersionAtLeast(String modid, String version)
//         {
//             return net.minecraftforge.fml.ModList.get().getModContainerById(modid).map(modContainer -> modContainer.getModInfo().getVersion().compareTo(VersionChecker.parseVersionReference(version)) >= 0).orElse(false);
//         }
//
//         public static boolean isModVersionAtMost(String modid, String version)
//         {
//             return net.minecraftforge.fml.ModList.get().getModContainerById(modid).map(modContainer -> modContainer.getModInfo().getVersion().compareTo(VersionChecker.parseVersionReference(version)) <= 0).orElse(false);
//         }

//         public static boolean isModVersionExact(String modid, String version)
//         {
//             return net.minecraftforge.fml.ModList.get().getModContainerById(modid).map(modContainer -> modContainer.getModInfo().getVersion().compareTo(VersionChecker.parseVersionReference(version)) == 0).orElse(false);
//         }
//
//         public static String getModAuthors(String modid)
//         {
//             return net.minecraftforge.fml.ModList.get().getModContainerById(modid).map(modContainer -> String.join(", ", modContainer.getModInfo().getAuthors())).orElse("N/A");
//         }
    }
}
