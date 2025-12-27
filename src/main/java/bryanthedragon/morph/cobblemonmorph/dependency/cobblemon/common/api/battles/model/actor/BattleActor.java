package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleSide
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ForcePassActionResponse
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionRequest
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionResponse
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownMoveset
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.exception.IllegalActionChoiceException
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleApplyPassResponsePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMakeChoicePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMessagePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.ArrayList;
import java.util.UUID
import kotlin.jvm.functions.Function3
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.jvm.internal.Ref.IntRef
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.level.ServerPlayer
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

@SourceDebugExtension(["SMAP\nBattleActor.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleActor.kt\ncom/cobblemon/mod/common/api/battles/model/actor/BattleActor\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,148:1\n1855#2,2:149\n1774#2,4:151\n1747#2,3:155\n1569#2,11:158\n1864#2,2:169\n1866#2:172\n1580#2:173\n1864#2,2:174\n1866#2:177\n1#3:171\n1#3:176\n*S KotlinDebug\n*F\n+ 1 BattleActor.kt\ncom/cobblemon/mod/common/api/battles/model/actor/BattleActor\n*L\n33#1:149,2\n53#1:151,4\n66#1:155,3\n84#1:158,11\n84#1:169,2\n84#1:172\n84#1:173\n96#1:174,2\n96#1:177\n84#1:171\n*E\n"])
public abstract class BattleActor {
   public final val activePokemon: MutableList<ActiveBattlePokemon>
   public final lateinit var battle: PokemonBattle
   public final var canDynamax: Boolean
   public final val expectingPassActions: MutableList<ShowdownActionResponse>
   public final var mustChoose: Boolean
   public final val pokemonList: MutableList<BattlePokemon>
   public final var request: ShowdownActionRequest?
   public final var responses: MutableList<ShowdownActionResponse>
   public final lateinit var showdownId: String
   public final var stillSendingOutCount: Int
   public abstract val type: ActorType
   public final val uuid: UUID

   open fun BattleActor(uuid: UUID, pokemonList: MutableList<BattlePokemon>) {
      this.uuid = uuid;
      this.pokemonList = pokemonList;

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         (`element$iv` as BattlePokemon).setActor(this);
      }

      this.activePokemon = new ArrayList<>();
      this.responses = new ArrayList<>();
      this.expectingPassActions = new ArrayList<>();
   }

   public fun canFitForcedAction(): Boolean {
      if (this.mustChoose) {
         var var10000: Boolean;
         if (this.request != null) {
            val request: ShowdownActionRequest = this.request;
            val var13: java.util.List = this.request.getActive();
            var10000 = if (var13 != null) var13.size() else 0;
            val `$this$count$iv`: java.lang.Iterable = request.getForceSwitch();
            if (`$this$count$iv` is java.util.Collection && (`$this$count$iv` as java.util.Collection).isEmpty()) {
               var10000 = 0;
            } else {
               val `count$iv`: Int = 0;

               for (Object element$iv : $this$count$iv) {
                  if (`element$iv` as java.lang.Boolean) {
                     if (++`count$iv` < 0) {
                        CollectionsKt.throwCountOverflow();
                     }
                  }
               }

               var10000 = `count$iv`;
            }

            var10000 = var10000 - var10000 > this.expectingPassActions.size() && !this.getBattle().getEnded();
         } else {
            var10000 = 0;
         }

         if (var10000) {
            return true;
         }
      }

      return false;
   }

   public fun forceChoose(response: ShowdownActionResponse) {
      this.expectingPassActions.add(response);
      this.sendUpdate(new BattleApplyPassResponsePacket());
   }

   public fun getSide(): BattleSide {
      return if (ArraysKt.contains(this.getBattle().getSide1().getActors(), this)) this.getBattle().getSide1() else this.getBattle().getSide2();
   }

   public open fun getPlayerUUIDs(): Iterable<UUID> {
      return CollectionsKt.emptyList();
   }

   public open fun isForPlayer(serverPlayerEntity: ServerPlayer): Boolean {
      return CollectionsKt.contains(this.getPlayerUUIDs(), serverPlayerEntity.m_20148_());
   }

   public open fun isForPokemon(pokemonEntity: PokemonEntity): Boolean {
      val `$this$any$iv`: java.lang.Iterable = this.activePokemon;
      var var10: Boolean;
      if (this.activePokemon is java.util.Collection && this.activePokemon.isEmpty()) {
         var10 = false;
      } else {
         val var4: java.util.Iterator = `$this$any$iv`.iterator();

         while (true) {
            if (!var4.hasNext()) {
               var10 = false;
               break;
            }

            label24: {
               val var10000: BattlePokemon = (var4.next() as ActiveBattlePokemon).getBattlePokemon();
               if (var10000 != null) {
                  val var8: Pokemon = var10000.getEffectedPokemon();
                  if (var8 != null) {
                     var9 = var8.getEntity();
                     break label24;
                  }
               }

               var9 = null;
            }

            if (var9 == pokemonEntity) {
               var10 = true;
               break;
            }
         }
      }

      return var10;
   }

   public fun turn() {
      if (this.request != null) {
         val request: ShowdownActionRequest = this.request;
         this.responses.clear();
         this.mustChoose = true;
         this.sendUpdate(new BattleMakeChoicePacket());
         val requestActive: java.util.List = request.getActive();
         if (requestActive == null || requestActive.isEmpty() || request.getWait()) {
            this.request = null;
            this.expectingPassActions.clear();
         }
      }
   }

   public fun upkeep() {
      if (this.request != null) {
         val `$this$mapIndexedNotNull$iv`: java.lang.Iterable = this.request.getForceSwitch();
         val `destination$iv$iv`: java.util.Collection = new ArrayList();
         var `index$iv$iv$iv`: Int = 0;

         for (Object item$iv$iv$iv : $this$mapIndexedNotNull$iv) {
            val var13: Int = `index$iv$iv$iv`++;
            if (var13 < 0) {
               CollectionsKt.throwIndexOverflow();
            }

            val var22: ActiveBattlePokemon = if (`item$iv$iv$iv` as java.lang.Boolean) this.activePokemon.get(var13) else null;
            if (var22 != null) {
               `destination$iv$iv`.add(var22);
            }
         }

         if (!(`destination$iv$iv` as java.util.List).isEmpty()) {
            this.sendUpdate(new BattleMakeChoicePacket());
            this.mustChoose = true;
         }
      }
   }

   public fun setActionResponses(responses: List<ShowdownActionResponse>) {
      if (this.request != null) {
         val request: ShowdownActionRequest = this.request;
         val originalPassActions: java.util.List = CollectionsKt.toList(this.expectingPassActions);
         val `$this$forEachIndexed$iv`: java.lang.Iterable = responses;
         var `index$iv`: Int = 0;

         for (Object item$iv : $this$forEachIndexed$iv) {
            val var9: Int = `index$iv`++;
            if (var9 < 0) {
               CollectionsKt.throwIndexOverflow();
            }

            val response: ShowdownActionResponse = `item$iv` as ShowdownActionResponse;
            val forceSwitch: java.util.List = this.activePokemon;
            if (this.activePokemon.size() <= var9) {
               return;
            }

            val activeBattlePokemon: ActiveBattlePokemon = forceSwitch.get(var9) as ActiveBattlePokemon;
            val var22: java.util.List = request.getActive();
            val var23: ShowdownMoveset = if (var22 != null) (if (var22.size() > var9) var22.get(var9) as ShowdownMoveset else null) else null;
            val itx: java.util.List = request.getForceSwitch();
            if (!response.isValid(activeBattlePokemon, var23, itx.size() > var9 && itx.get(var9) as java.lang.Boolean)) {
               this.expectingPassActions.clear();
               this.expectingPassActions.addAll(originalPassActions);
               val var10003: BattlePokemon = activeBattlePokemon.getBattlePokemon();
               throw new IllegalActionChoiceException(this, "Invalid action choice for ${var10003.getName().getString()}: $response");
            }

            if (response is ForcePassActionResponse) {
               this.responses.add(this.expectingPassActions.remove(0));
            } else {
               this.responses.add(response);
            }
         }

         if (this.expectingPassActions.size() > 0) {
            throw new IllegalActionChoiceException(this, "Invalid action choice: a capture was expected. Are you hacking me?");
         } else {
            this.mustChoose = false;
            this.getBattle().checkForInputDispatch();
         }
      }
   }

   public fun writeShowdownResponse() {
      val showdownMessages: java.util.List = new ArrayList();
      val index: IntRef = new IntRef();
      val var10000: ShowdownActionRequest = this.request;
      var10000.iterate(this.activePokemon, (new Function3<ActiveBattlePokemon, ShowdownMoveset, java.lang.Boolean, Integer>(showdownMessages, this, index) {
         {
            super(3);
            this.$showdownMessages = `$showdownMessages`;
            this.this$0 = `$receiver`;
            this.$index = `$index`;
         }

         @NotNull
         public final Integer invoke(@NotNull ActiveBattlePokemon activeBattlePokemon, @Nullable ShowdownMoveset showdownMoveSet, boolean forceSwitch) {
            this.$showdownMessages.add(this.this$0.getResponses().get(this.$index.element).toShowdownString(activeBattlePokemon, showdownMoveSet));
            return this.$index.element++;
         }
      }) as Function3);
      this.responses.clear();
      this.request = null;
      this.expectingPassActions.clear();
      this.getBattle()
         .writeShowdownAction(">${this.getShowdownId()} ${CollectionsKt.joinToString$default(showdownMessages, null, null, null, 0, null, null, 63, null)}");
   }

   public abstract fun getName(): MutableComponent {
   }

   public abstract fun nameOwned(name: String): MutableComponent {
   }

   public open fun sendMessage(component: Component) {
      this.sendUpdate(new BattleMessagePacket(component));
   }

   public open fun awardExperience(battlePokemon: BattlePokemon, experience: Int) {
   }

   public open fun sendUpdate(packet: NetworkPacket<*>) {
   }
}
