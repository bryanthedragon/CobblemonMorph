package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pasture

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.PrioritizedList
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.PokemonPastureBlockEntity
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.server.level.ServerPlayer

@SourceDebugExtension(["SMAP\nPasturePermissionControllers.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PasturePermissionControllers.kt\ncom/cobblemon/mod/common/api/pasture/PasturePermissionControllers\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,39:1\n1#2:40\n*E\n"])
public object PasturePermissionControllers {
   public final val controllers: PrioritizedList<PasturePermissionController> = new PrioritizedList()

   public fun permit(player: ServerPlayer, pastureBlockEntity: PokemonPastureBlockEntity): PasturePermissions {
      val var3: java.util.Iterator = controllers.iterator();

      var var10000: PasturePermissions;
      while (true) {
         if (var3.hasNext()) {
            val var6: PasturePermissions = (var3.next() as PasturePermissionController).permit(player, pastureBlockEntity);
            if (var6 == null) {
               continue;
            }

            var10000 = var6;
            break;
         }

         var10000 = null;
         break;
      }

      if (var10000 == null) {
         var10000 = new PasturePermissions(true, true, pastureBlockEntity.getMaxTethered());
      }

      return var10000;
   }
}
