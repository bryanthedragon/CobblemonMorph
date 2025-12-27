package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue

public class ReferenceDialogueFaceProvider(entityId: Int) : DialogueFaceProvider {
   public final val entityId: Int

   init {
      this.entityId = entityId;
   }
}
