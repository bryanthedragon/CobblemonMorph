package bryanthedragon.morph.cobblemonmorph;

import java.net.URL;
import java.util.Optional;

public class CobblemonMorphDependencyHelper
{
        /**
         * Returns whether or not a mod with the given id is loaded.
         */
        public static boolean isModLoaded(String modid)
        {
            return net.minecraftforge.fml.ModList.get().isLoaded(modid);
        }
        
        public static String getModVersion(String modid)
        {
            return net.minecraftforge.fml.ModList.get().getModContainerById(modid).map(modContainer -> modContainer.getModInfo().getVersion().toString()).orElse("N/A");
        }

        public static String getModDisplayName(String modid)
        {
            return net.minecraftforge.fml.ModList.get().getModContainerById(modid).map(modContainer -> modContainer.getModInfo().getDisplayName()).orElse("N/A");
        }
        
        public static String getModDescription(String modid)
        {
            return net.minecraftforge.fml.ModList.get().getModContainerById(modid).map(modContainer -> modContainer.getModInfo().getDescription()).orElse("N/A");
        }

        public static Optional<URL> getModURL(String modid)
        {
            return net.minecraftforge.fml.ModList.get().getModContainerById(modid).map(modContainer -> modContainer.getModInfo().getModURL()).orElseThrow();
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
