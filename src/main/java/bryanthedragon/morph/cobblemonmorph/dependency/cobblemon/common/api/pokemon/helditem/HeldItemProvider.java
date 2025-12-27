package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.helditem

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.PrioritizedList
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nHeldItemProvider.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HeldItemProvider.kt\ncom/cobblemon/mod/common/api/pokemon/helditem/HeldItemProvider\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,73:1\n288#2,2:74\n1#3:76\n*S KotlinDebug\n*F\n+ 1 HeldItemProvider.kt\ncom/cobblemon/mod/common/api/pokemon/helditem/HeldItemProvider\n*L\n32#1:74,2\n*E\n"])
public object HeldItemProvider {
   private final val managers: PrioritizedList<HeldItemManager> = new PrioritizedList()

   public fun provide(pokemon: BattlePokemon): HeldItemManager {
      val var4: java.util.Iterator = managers.iterator();

      var var10000: Any;
      while (true) {
         if (var4.hasNext()) {
            val `element$iv`: Any = var4.next();
            if ((`element$iv` as HeldItemManager).showdownId(pokemon) == null) {
               continue;
            }

            var10000 = (HeldItemManager)`element$iv`;
            break;
         }

         var10000 = null;
         break;
      }

      var10000 = var10000;
      if (var10000 == null) {
         var10000 = HeldItemManager.Companion.getEMPTY();
      }

      return var10000;
   }

   public fun provideShowdownId(pokemon: BattlePokemon): String? {
      val var2: java.util.Iterator = managers.iterator();

      var var10000: java.lang.String;
      while (true) {
         if (var2.hasNext()) {
            val var5: java.lang.String = (var2.next() as HeldItemManager).showdownId(pokemon);
            if (var5 == null) {
               continue;
            }

            var10000 = var5;
            break;
         }

         var10000 = null;
         break;
      }

      return var10000;
   }

   public fun register(manager: HeldItemManager, priority: Priority = Priority.NORMAL) {
      managers.add(priority, manager);
   }

   public fun unregister(manager: HeldItemManager, priority: Priority? = null) {
      if (priority != null) {
         managers.remove(priority, manager);
      } else {
         managers.remove(manager);
      }
   }

   public fun managers(): List<HeldItemManager> {
      return CollectionsKt.toList(managers);
   }
}
