package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player

import java.util.LinkedHashMap

public object PlayerDataExtensionRegistry {
   private final val allExtensions: MutableMap<String, Class<out PlayerDataExtension>> = (new LinkedHashMap()) as java.util.Map

   public fun register(name: String, extension: Class<out PlayerDataExtension>, overwrite: Boolean = false): Boolean {
      if (allExtensions.containsKey(name) && !overwrite) {
         return false;
      } else {
         allExtensions.put(name, extension);
         return true;
      }
   }

   public fun get(name: String): Class<out PlayerDataExtension>? {
      return allExtensions.get(name);
   }

   public fun getOrException(name: String): Class<out PlayerDataExtension> {
      val var10000: Class = this.get(name);
      if (var10000 == null) {
         throw new IllegalStateException("PlayerDataExtension with name $name was not found.");
      } else {
         return var10000;
      }
   }

   public fun count(): Int {
      return allExtensions.size();
   }

   public fun remove(name: String): Class<out PlayerDataExtension>? {
      return allExtensions.remove(name);
   }

   public fun contains(name: String): Boolean {
      return allExtensions.containsKey(name);
   }
}
