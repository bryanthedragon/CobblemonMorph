package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import net.minecraft.resources.ResourceLocation

public class ArtificialDialogueFaceProvider(modelType: String = "",
      identifier: ResourceLocation = MiscUtilsKt.cobblemonResource("bulbasaur"),
      aspects: Set<String> = SetsKt.emptySet()
   ) :
   DialogueFaceProvider {
   public final val aspects: Set<String>
   public final val identifier: ResourceLocation
   public final val modelType: String

   init {
      this.modelType = modelType;
      this.identifier = identifier;
      this.aspects = aspects;
   }

   fun ArtificialDialogueFaceProvider() {
      this(null, null, null, 7, null);
   }
}
