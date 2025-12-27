package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.Entity

public class InsufficientPokemonError(player: ServerPlayer, requiredCount: Int, hadCount: Int) : BattleStartError {
   public final val hadCount: Int
   public final val player: ServerPlayer
   public final val requiredCount: Int

   init {
      this.player = player;
      this.requiredCount = requiredCount;
      this.hadCount = hadCount;
   }

   public override fun getMessageFor(entity: Entity): MutableComponent {
      val var6: MutableComponent;
      if (this.player == entity) {
         var6 = LocalizationUtilsKt.battleLang(
            "error.${if (this.hadCount == 0) "no_pokemon" else "insufficient_pokemon.personal"}", this.requiredCount, this.hadCount
         );
      } else {
         val var5: Array<Any> = new Object[3];
         val var10003: Component = this.player.m_5446_();
         var5[0] = var10003;
         var5[1] = this.requiredCount;
         var5[2] = this.hadCount;
         val var2: MutableComponent = LocalizationUtilsKt.battleLang("error.insufficient_pokemon", var5);
         var6 = var2;
      }

      return var6;
   }
}
