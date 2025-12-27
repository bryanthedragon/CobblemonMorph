package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles

import net.minecraft.network.FriendlyByteBuf

public enum ShowdownActionResponseType(loader: (FriendlyByteBuf) -> ShowdownActionResponse) {
   SWITCH(<unrepresentable>.INSTANCE),
   MOVE(<unrepresentable>.INSTANCE),
   DEFAULT(<unrepresentable>.INSTANCE),
   FORCE_PASS(<unrepresentable>.INSTANCE),
   PASS(<unrepresentable>.INSTANCE),
   HEAL_ITEM(<unrepresentable>.INSTANCE),
   FORFEIT(<unrepresentable>.INSTANCE)
   public final val loader: (FriendlyByteBuf) -> ShowdownActionResponse

   init {
      this.loader = loader;
   }
}
