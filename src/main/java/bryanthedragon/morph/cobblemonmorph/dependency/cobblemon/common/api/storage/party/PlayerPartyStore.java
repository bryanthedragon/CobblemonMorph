package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.CobblemonCriteria
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.PartyCheckContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.SimpleCriterionTrigger
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.PassiveEvolution
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.OriginalTrainerType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate.PokemonState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate.ShoulderedState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.variants.LevelUpEvolution
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatusContainer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CompoundTagExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import java.util.ArrayList;
import java.util.UUID
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.random.Random
import kotlin.random.Random.Default
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer

@SourceDebugExtension(["SMAP\nPlayerPartyStore.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PlayerPartyStore.kt\ncom/cobblemon/mod/common/api/storage/party/PlayerPartyStore\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,199:1\n1#2:200\n800#3,11:201\n1855#3,2:212\n1855#3,2:214\n1855#3,2:216\n1855#3,2:218\n1855#3,2:220\n*S KotlinDebug\n*F\n+ 1 PlayerPartyStore.kt\ncom/cobblemon/mod/common/api/storage/party/PlayerPartyStore\n*L\n127#1:201,11\n127#1:212,2\n129#1:214,2\n133#1:216,2\n139#1:218,2\n157#1:220,2\n*E\n"])
public open class PlayerPartyStore(playerUUID: UUID, storageUUID: UUID = playerUUID) : PartyStore(storageUUID) {
   public final val playerUUID: UUID
   private final var secondsSinceFriendshipUpdate: Int

   init {
      this.playerUUID = playerUUID;
   }

   public constructor(playerUUID: UUID) : this(playerUUID, playerUUID)
   public override fun initialize() {
      super.initialize();
      this.getObserverUUIDs().add(this.playerUUID);
   }

   public open fun getOverflowPC(): PCStore? {
      return Cobblemon.INSTANCE.getStorage().getPC(this.playerUUID);
   }

   public override fun add(pokemon: Pokemon): Boolean {
      if (pokemon.getOriginalTrainerType() === OriginalTrainerType.NONE) {
         pokemon.setOriginalTrainer(this.playerUUID);
      }

      pokemon.refreshOriginalTrainer();
      val var8: Boolean;
      if (super.add(pokemon)) {
         val var10000: ServerPlayer = pokemon.getOwnerPlayer();
         if (var10000 != null) {
            CobblemonCriteria.INSTANCE.getPARTY_CHECK().trigger(var10000, new PartyCheckContext(this));
         }

         var8 = true;
      } else {
         val player: ServerPlayer = PlayerExtensionsKt.getPlayer(this.playerUUID);
         val pc: PCStore = this.getOverflowPC();
         if (pc != null && pc.add(pokemon)) {
            if (player != null) {
               player.m_213846_(LocalizationUtilsKt.lang("overflow_to_pc", pokemon.getSpecies().getTranslatedName(), pc.getName()) as Component);
            }

            var8 = true;
         } else {
            if (pc == null) {
               if (player != null) {
                  player.m_213846_(LocalizationUtilsKt.lang("overflow_no_pc") as Component);
               }
            } else if (player != null) {
               player.m_213846_(LocalizationUtilsKt.lang("overflow_no_space", pc.getName()) as Component);
            }

            var8 = false;
         }
      }

      return var8;
   }

   public fun onSecondPassed(player: ServerPlayer) {
      if (BattleRegistry.INSTANCE.getBattleByParticipatingPlayer(player) == null) {
         val `$this$forEach$iv`: Default = Random.Default;

         for (Pokemon pokemon : this) {
            if (`$i$f$forEach`.isFainted()) {
               `$i$f$forEach`.setFaintedTimer(`$i$f$forEach`.getFaintedTimer() - 1);
               if (`$i$f$forEach`.getFaintedTimer() <= -1) {
                  `$i$f$forEach`.setCurrentHealth(
                     (int)((float)Math.ceil((double)((float)`$i$f$forEach`.getHp() * Cobblemon.INSTANCE.getConfig().getFaintAwakenHealthPercent())))
                  );
                  player.m_213846_(Component.m_237110_("cobblemon.party.faintRecover", new Object[]{`$i$f$forEach`.getDisplayName()}) as Component);
               }
            } else if (`$i$f$forEach`.getCurrentHealth() < `$i$f$forEach`.getHp()) {
               `$i$f$forEach`.setHealTimer(`$i$f$forEach`.getHealTimer() + -1);
               if (`$i$f$forEach`.getHealTimer() <= -1) {
                  `$i$f$forEach`.setHealTimer(Cobblemon.INSTANCE.getConfig().getHealTimer());
                  `$i$f$forEach`.setCurrentHealth(
                     `$i$f$forEach`.getCurrentHealth()
                        + (int)Math.rint(RangesKt.coerceAtLeast(1.0, (double)`$i$f$forEach`.getHp() * Cobblemon.INSTANCE.getConfig().getHealPercent()))
                  );
               }
            }

            val var20: PersistentStatusContainer = `$i$f$forEach`.getStatus();
            if (var20 != null && !player.m_5803_()) {
               if (var20.isExpired()) {
                  var20.getStatus().onStatusExpire(player, `$i$f$forEach`, `$this$forEach$iv` as Random);
                  `$i$f$forEach`.setStatus(null);
               } else {
                  var20.getStatus().onSecondPassed(player, `$i$f$forEach`, `$this$forEach$iv` as Random);
                  var20.tickTimer();
               }
            }

            val var23: java.lang.Iterable = `$i$f$forEach`.getLockedEvolutions();
            val `element$iv`: java.util.Collection = new ArrayList();

            for (Object element$iv$iv : $this$filterIsInstance$iv) {
               if (var12 is PassiveEvolution) {
                  `element$iv`.add(var12);
               }
            }

            for (Object element$ivx : $this$filterIsInstance$iv) {
               (`element$ivx` as PassiveEvolution).attemptEvolution(`$i$f$forEach`);
            }

            val var25: java.util.List = new ArrayList();

            val var29: java.lang.Iterable;
            for (Object element$ivx : var29) {
               val var44: Evolution = `element$ivx` as Evolution;
               if (!(`element$ivx` as Evolution).test(`$i$f$forEach`)
                  && `element$ivx` as Evolution is LevelUpEvolution
                  && !((`element$ivx` as Evolution) as LevelUpEvolution).getPermanent()) {
                  var25.add(var44);
               }
            }

            for (Object element$ivxx : var29) {
               `$i$f$forEach`.getEvolutionProxy().server().remove(`element$ivxx` as Evolution);
            }
         }

         this.secondsSinceFriendshipUpdate++;
         if (this.secondsSinceFriendshipUpdate == 120) {
            this.secondsSinceFriendshipUpdate = 0;

            val var14: java.lang.Iterable;
            for (Object element$ivx : var14) {
               val var31: Pokemon = `element$ivx` as Pokemon;
               if ((`element$ivx` as Pokemon).getFriendship() < 160
                  && ((`element$ivx` as Pokemon).getEntity() != null || (`element$ivx` as Pokemon).getState() is ShoulderedState)) {
                  Pokemon.incrementFriendship$default(var31, 1, false, 2, null);
               }
            }
         }
      }

      var var10000: CompoundTag = player.m_36331_();
      if (CompoundTagExtensionsKt.isPokemonEntity(var10000)) {
         val var10001: CompoundTag = player.m_36331_();
         if (!this.validateShoulder(var10001, true)) {
            player.m_36370_(player.m_36331_());
         }
      }

      var10000 = player.m_36332_();
      if (CompoundTagExtensionsKt.isPokemonEntity(var10000)) {
         val var49: CompoundTag = player.m_36332_();
         if (!this.validateShoulder(var49, false)) {
            player.m_36370_(player.m_36332_());
         }
      }

      val var13: java.lang.Iterable;
      for (Object element$ivxx : var13) {
         val var27: Pokemon = `element$ivxx` as Pokemon;
         val var36: PokemonState = (`element$ivxx` as Pokemon).getState();
         if (var36 is ShoulderedState && !(var36 as ShoulderedState).isStillShouldered(player)) {
            var27.recall();
         }
      }
   }

   public fun validateShoulder(shoulderEntity: CompoundTag, isLeft: Boolean): Boolean {
      val var5: java.util.Iterator = this.iterator();

      var var10000: Any;
      while (true) {
         if (var5.hasNext()) {
            val var6: Any = var5.next();
            if (!((var6 as Pokemon).getUuid() == shoulderEntity.m_128469_("Pokemon").m_128342_("UUID"))) {
               continue;
            }

            var10000 = var6;
            break;
         }

         var10000 = null;
         break;
      }

      val pokemon: Pokemon = var10000 as Pokemon;
      if (var10000 as Pokemon != null) {
         val var9: PokemonState = pokemon.getState();
         if ((var9 as? ShoulderedState) != null && (var9 as? ShoulderedState).isLeftShoulder() == isLeft) {
            return true;
         }
      }

      return false;
   }

   public override fun swap(position1: PartyPosition, position2: PartyPosition) {
      super.swap(position1, position2);
      val pokemon1: Pokemon = this.get(position1);
      val pokemon2: Pokemon = this.get(position2);
      if (pokemon1 != null && pokemon2 != null) {
         val var7: ServerPlayer = pokemon1.getOwnerPlayer();
         if (var7 != null) {
            CobblemonCriteria.INSTANCE.getPARTY_CHECK().trigger(var7, new PartyCheckContext(this));
         }
      } else if (pokemon1 != null || pokemon2 != null) {
         var player: ServerPlayer = if (pokemon1 != null) pokemon1.getOwnerPlayer() else null;
         if (player != null) {
            CobblemonCriteria.INSTANCE.getPARTY_CHECK().trigger(player, new PartyCheckContext(this));
         } else {
            player = pokemon2.getOwnerPlayer();
            val var10000: SimpleCriterionTrigger = CobblemonCriteria.INSTANCE.getPARTY_CHECK();
            var10000.trigger(player, new PartyCheckContext(this));
         }
      }
   }

   public override operator fun set(position: PartyPosition, pokemon: Pokemon) {
      super.set(position, pokemon);
      val var10000: ServerPlayer = pokemon.getOwnerPlayer();
      if (var10000 != null) {
         CobblemonCriteria.INSTANCE.getPARTY_CHECK().trigger(var10000, new PartyCheckContext(this));
      }
   }
}
