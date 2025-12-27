package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.serialization.StringIdentifiedObjectAdapter

public enum ItemDropMethod(methodName: String) {
   ON_ENTITY("on-entity"),
   ON_PLAYER("on-player"),
   TO_INVENTORY("to-inventory")
   public final val methodName: String
   @JvmStatic
   public ItemDropMethod.Companion Companion = new ItemDropMethod.Companion(null);
   @JvmStatic
   private StringIdentifiedObjectAdapter<ItemDropMethod> adapter = new StringIdentifiedObjectAdapter<>(<unrepresentable>.INSTANCE);

   init {
      this.methodName = methodName;
   }

   public companion object {
      public final val adapter: StringIdentifiedObjectAdapter<ItemDropMethod?>
   }
}
