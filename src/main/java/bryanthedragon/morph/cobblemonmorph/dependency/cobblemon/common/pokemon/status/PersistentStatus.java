package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Status
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import kotlin.random.Random
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer

public open class PersistentStatus(name: ResourceLocation,
   showdownName: String,
   applyMessage: String,
   removeMessage: String,
   defaultDuration: IntRange = new IntRange(0, 0)
) : Status(name, showdownName, applyMessage, removeMessage) {
   private final val defaultDuration: IntRange

   init {
      this.defaultDuration = defaultDuration;
   }

   public open fun onStatusExpire(player: ServerPlayer, pokemon: Pokemon, random: Random) {
      player.m_213846_(MiscUtilsKt.asTranslated(this.getRemoveMessage(), pokemon.getDisplayName()) as Component);
   }

   public open fun onSecondPassed(player: ServerPlayer, pokemon: Pokemon, random: Random) {
   }

   public fun statusPeriod(): IntRange {
      var var10000: IntRange = Cobblemon.INSTANCE.getConfig().getPassiveStatuses().get(this.getName().toString());
      if (var10000 == null) {
         var10000 = this.defaultDuration;
      }

      return var10000;
   }

   public fun configEntry(): Pair<String, IntRange> {
      val var10000: java.lang.String = this.getName().toString();
      return TuplesKt.to(var10000, this.defaultDuration);
   }
}
