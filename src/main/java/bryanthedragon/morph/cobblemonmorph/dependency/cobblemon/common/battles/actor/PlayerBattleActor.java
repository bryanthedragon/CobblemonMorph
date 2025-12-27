package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.ActorType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.EntityBackedBattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.BattleExperienceSource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMusicPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import java.util.ArrayList;
import java.util.UUID
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvent

@SourceDebugExtension(["SMAP\nPlayerBattleActor.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PlayerBattleActor.kt\ncom/cobblemon/mod/common/battles/actor/PlayerBattleActor\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,65:1\n1#2:66\n1#2:77\n1603#3,9:67\n1855#3:76\n1856#3:78\n1612#3:79\n*S KotlinDebug\n*F\n+ 1 PlayerBattleActor.kt\ncom/cobblemon/mod/common/battles/actor/PlayerBattleActor\n*L\n62#1:77\n62#1:67,9\n62#1:76\n62#1:78\n62#1:79\n*E\n"])
public class PlayerBattleActor(uuid: UUID, pokemonList: List<BattlePokemon>) : BattleActor(uuid, CollectionsKt.toMutableList(pokemonList)),
   EntityBackedBattleActor<ServerPlayer> {
   public final var battleTheme: SoundEvent?
      public final set(value) {
         if (!(this.battleTheme == value) && this.getBattle().getStarted()) {
            this.sendUpdate(new BattleMusicPacket(value, 0.0F, 0.0F, 6, null));
         }

         this.battleTheme = value;
      }


   public open val entity: ServerPlayer?
      public open get() {
         return PlayerExtensionsKt.getPlayer(this.getUuid());
      }


   public open val type: ActorType = ActorType.PLAYER

   public override fun getName(): MutableComponent {
      var var2: MutableComponent;
      label16: {
         val var10000: ServerPlayer = this.getEntity();
         if (var10000 != null) {
            val var1: Component = var10000.m_7755_();
            if (var1 != null) {
               var2 = var1.m_6881_();
               break label16;
            }
         }

         var2 = null;
      }

      if (var2 == null) {
         var2 = TextKt.red("Offline Player");
      }

      return var2;
   }

   public override fun nameOwned(name: String): MutableComponent {
      val var10000: MutableComponent = LocalizationUtilsKt.battleLang("owned_pokemon", this.getName(), name);
      return var10000;
   }

   public open fun getPlayerUUIDs(): Set<UUID> {
      return SetsKt.setOf(this.getUuid());
   }

   public override fun awardExperience(battlePokemon: BattlePokemon, experience: Int) {
      if (!this.getBattle().isPvP() || Cobblemon.INSTANCE.getConfig().getAllowExperienceFromPvP()) {
         val source: BattleExperienceSource = new BattleExperienceSource(this.getBattle(), CollectionsKt.toList(battlePokemon.getFacedOpponents()));
         if (battlePokemon.getEffectedPokemon() == battlePokemon.getOriginalPokemon() && experience > 0) {
            val var4: ServerPlayer = PlayerExtensionsKt.getPlayer(this.getUuid());
            if (var4 != null && battlePokemon.getEffectedPokemon().addExperienceWithPlayer(var4, source, experience) != null) {
               return;
            }

            val `$this$awardExperience_u24lambda_u241`: PlayerBattleActor = this;
            battlePokemon.getEffectedPokemon().addExperience(source, experience);
         }
      }
   }

   public override fun sendUpdate(packet: NetworkPacket<*>) {
      val var10000: CobblemonNetwork = CobblemonNetwork.INSTANCE;
      val `$this$mapNotNull$iv`: java.lang.Iterable = this.getPlayerUUIDs();
      val `destination$iv$iv`: java.util.Collection = new ArrayList();

      for (Object element$iv$iv$iv : $this$mapNotNull$iv) {
         val var18: ServerPlayer = PlayerExtensionsKt.getPlayer(`element$iv$iv$iv` as UUID);
         if (var18 != null) {
            `destination$iv$iv`.add(var18);
         }
      }

      var10000.sendPacketToPlayers(`destination$iv$iv`, packet);
   }
}
