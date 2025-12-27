package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.keybinds

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.BattleGUI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.CobblemonBlockingKeyBinding
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.BattleChallengePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.RequestPlayerInteractionsPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.SendOutPokemonPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import com.mojang.blaze3d.platform.InputConstants.Type
import java.util.UUID
import net.minecraft.client.Minecraft
import net.minecraft.client.player.LocalPlayer
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player

public object PartySendBinding : CobblemonBlockingKeyBinding("key.cobblemon.throwpartypokemon", Type.KEYSYM, 82, "key.cobblemon.categories.cobblemon") {
   public final var canApplyChange: Boolean = true
   public final var secondsSinceActioned: Float

   public fun actioned() {
      canApplyChange = false;
      secondsSinceActioned = 0.0F;
      this.setWasDown(false);
   }

   public fun canAction(): Boolean {
      return canApplyChange;
   }

   public override fun onTick() {
      if (secondsSinceActioned < 100.0F) {
         secondsSinceActioned = secondsSinceActioned + Minecraft.m_91087_().m_91296_();
      }

      super.onTick();
   }

   public override fun onRelease() {
      this.setWasDown(false);
      if (!this.canAction()) {
         canApplyChange = true;
      } else {
         canApplyChange = true;
         val var10000: LocalPlayer = Minecraft.m_91087_().f_91074_;
         if (var10000 != null) {
            if (!var10000.m_5833_()) {
               val battle: ClientBattle = CobblemonClient.INSTANCE.getBattle();
               if (battle != null) {
                  battle.setMinimised(!battle.getMinimised());
                  if (!battle.getMinimised()) {
                     Minecraft.m_91087_().m_91152_(new BattleGUI());
                  }
               } else {
                  if (CobblemonClient.INSTANCE.getStorage().getSelectedSlot() != -1 && Minecraft.m_91087_().f_91080_ == null) {
                     val pokemon: Pokemon = CobblemonClient.INSTANCE.getStorage().getMyParty().get(CobblemonClient.INSTANCE.getStorage().getSelectedSlot());
                     if (pokemon != null && pokemon.getCurrentHealth() > 0) {
                        val targetEntity: LivingEntity = PlayerExtensionsKt.traceFirstEntityCollision$default(
                           var10000 as Player, 0.0F, 0.0F, LivingEntity.class, var10000 as Entity, 3, null
                        ) as LivingEntity;
                        if (targetEntity != null && (targetEntity !is PokemonEntity || !((targetEntity as PokemonEntity).m_21805_() == var10000.m_20148_()))) {
                           this.processEntityTarget(var10000, pokemon, targetEntity);
                        } else {
                           CobblemonNetwork.INSTANCE.sendPacketToServer(new SendOutPokemonPacket(CobblemonClient.INSTANCE.getStorage().getSelectedSlot()));
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private fun processEntityTarget(player: LocalPlayer, pokemon: Pokemon, entity: LivingEntity) {
      if (entity is Player) {
         val var10000: CobblemonNetwork = CobblemonNetwork.INSTANCE;
         val var10003: UUID = (entity as Player).m_20148_();
         val var10004: Int = (entity as Player).m_19879_();
         val var10005: UUID = pokemon.getUuid();
         var10000.sendPacketToServer(new RequestPlayerInteractionsPacket(var10003, var10004, var10005));
      } else if (entity is PokemonEntity) {
         if (!(entity as PokemonEntity).canBattle(player as Player)) {
            return;
         }

         val var5: CobblemonNetwork = CobblemonNetwork.INSTANCE;
         val var6: Int = (entity as PokemonEntity).m_19879_();
         val var7: UUID = pokemon.getUuid();
         var5.sendPacketToServer(new BattleChallengePacket(var6, var7));
      }
   }

   public override fun onPress() {
   }
}
