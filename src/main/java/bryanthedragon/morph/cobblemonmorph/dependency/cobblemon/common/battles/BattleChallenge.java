package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles.BattleStartedPostEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles.BattleStartedPreEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.helditem.HeldItemProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stats
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.runner.ShowdownService
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleChallengeExpiredPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatusContainer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.time.Instant
import java.util.ArrayList;
import java.util.Arrays
import java.util.LinkedHashMap
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.server.level.ServerPlayer

@SourceDebugExtension(["SMAP\nBattleRegistry.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleRegistry.kt\ncom/cobblemon/mod/common/battles/BattleRegistry\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n+ 5 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/CancelableObservable\n+ 6 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 7 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 8 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/CancelableObservable$postThen$1\n+ 9 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable$post$1\n*L\n1#1,240:1\n1549#2:241\n1620#2,3:242\n1549#2:245\n1620#2,3:246\n1603#2,9:249\n1855#2:258\n1856#2:260\n1612#2:261\n1855#2,2:262\n1045#2:264\n1045#2:265\n1#3:259\n1#3:290\n37#4,2:266\n39#5,2:268\n41#5,2:273\n44#5:276\n46#5:286\n47#5:289\n17#6,2:270\n14#6,5:277\n19#6:285\n19#6:288\n13579#7:272\n13579#7:282\n13580#7:284\n13580#7:287\n39#8:275\n14#9:283\n*S KotlinDebug\n*F\n+ 1 BattleRegistry.kt\ncom/cobblemon/mod/common/battles/BattleRegistry\n*L\n114#1:241\n114#1:242,3\n119#1:245\n119#1:246,3\n184#1:249,9\n184#1:258\n184#1:260\n184#1:261\n185#1:262,2\n189#1:264\n194#1:265\n184#1:259\n199#1:266,2\n212#1:268,2\n212#1:273,2\n212#1:276\n212#1:286\n212#1:289\n212#1:270,2\n215#1:277,5\n215#1:285\n212#1:288\n212#1:272\n215#1:282\n215#1:284\n212#1:287\n212#1:275\n215#1:283\n*E\n"])
public object BattleRegistry {
   private final val battleMap: ConcurrentHashMap<UUID, PokemonBattle> = new ConcurrentHashMap()
   public final val gson: Gson =
      new GsonBuilder().disableHtmlEscaping().registerTypeAdapter(ShowdownMoveset::class.java, ShowdownMovesetAdapter.INSTANCE).create()
      public final val pvpChallenges: MutableMap<UUID, bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry.BattleChallenge> =
      (new LinkedHashMap()) as java.util.Map

   public fun onServerStarted() {
      battleMap.clear();
      pvpChallenges.clear();
   }

   public fun removeChallenge(challengerId: UUID, challengeId: UUID? = null) {
      val var10000: BattleRegistry.BattleChallenge = pvpChallenges.get(challengerId);
      if (var10000 != null) {
         if (var10000.getChallengeId() == challengeId) {
            pvpChallenges.remove(challengerId);
            val var4: ServerPlayer = PlayerExtensionsKt.getPlayer(var10000.getChallengedPlayerUUID());
            if (var4 != null) {
               CobblemonNetwork.INSTANCE.sendPacket(var4, new BattleChallengeExpiredPacket(var10000.getChallengeId()));
            }
         }
      }
   }

   public fun List<BattlePokemon>.packTeam(): String {
      val team: java.util.List = new ArrayList();

      for (BattlePokemon pokemon : $this$packTeam) {
         val pk: Pokemon = pokemon.getEffectedPokemon();
         val packedTeamBuilder: StringBuilder = new StringBuilder();
         packedTeamBuilder.append("${pk.showdownId()}|");
         packedTeamBuilder.append("|");
         packedTeamBuilder.append("${pk.getUuid()}|");
         packedTeamBuilder.append("${pk.getCurrentHealth()}|");
         var var31: java.lang.String;
         if (pk.getStatus() != null) {
            val var10000: PersistentStatusContainer = pk.getStatus();
            var31 = var10000.getStatus().getShowdownName();
         } else {
            var31 = "";
         }

         packedTeamBuilder.append("$var31|");
         val var32: java.lang.Iterable = CollectionsKt.listOf(new PersistentStatus[]{Statuses.INSTANCE.getSLEEP(), Statuses.INSTANCE.getFROZEN()});
         val var10001: PersistentStatusContainer = pk.getStatus();
         if (CollectionsKt.contains(var32, if (var10001 != null) var10001.getStatus() else null)) {
            packedTeamBuilder.append("2|");
         } else {
            packedTeamBuilder.append("-1|");
         }

         var31 = HeldItemProvider.INSTANCE.provideShowdownId(pokemon);
         if (var31 == null) {
            var31 = "";
         }

         packedTeamBuilder.append("$var31|");
         packedTeamBuilder.append("${StringsKt.replace$default(pk.getAbility().getName(), "_", "", false, 4, null)}|");
         packedTeamBuilder.append(
            "${CollectionsKt.joinToString$default(pk.getMoveSet().getMoves(), ",", null, null, 0, null, <unrepresentable>.INSTANCE, 30, null)}|"
         );
         packedTeamBuilder.append(
            "${CollectionsKt.joinToString$default(pk.getMoveSet().getMoves(), ",", null, null, 0, null, <unrepresentable>.INSTANCE, 30, null)}|"
         );
         packedTeamBuilder.append("${pk.getEffectiveNature().getName().m_135815_()}|");
         val ivsInOrder: java.lang.Iterable = Stats.Companion.getPERMANENT();
         val `$this$mapTo$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(ivsInOrder, 10));

         for (Object item$iv$iv : $this$map$iv) {
            `$this$mapTo$iv$iv`.add(pk.getEvs().getOrDefault(`item$iv$iv` as Stat));
         }

         packedTeamBuilder.append("${CollectionsKt.joinToString$default(`$this$mapTo$iv$iv` as java.util.List, ",", null, null, 0, null, null, 62, null)}|");
         packedTeamBuilder.append("${pk.getGender().getShowdownName()}|");
         val `$this$map$ivx`: java.lang.Iterable = Stats.Companion.getPERMANENT();
         val `destination$iv$ivx`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(`$this$map$ivx`, 10));

         for (Object item$iv$iv : $this$map$ivx) {
            `destination$iv$ivx`.add(pk.getIvs().getOrDefault(var29 as Stat));
         }

         packedTeamBuilder.append("${CollectionsKt.joinToString$default(`destination$iv$ivx` as java.util.List, ",", null, null, 0, null, null, 62, null)}|");
         packedTeamBuilder.append("${if (pk.getShiny()) "S" else ""}|");
         packedTeamBuilder.append("${pk.getLevel()}|");
         packedTeamBuilder.append("${pk.getFriendship()},");
         var31 = pokemon.getEffectedPokemon().getCaughtBall().getName().m_135815_();
         packedTeamBuilder.append("${StringsKt.replace$default(var31, "_", "", false, 4, null)},");
         packedTeamBuilder.append(",");
         packedTeamBuilder.append("${if (pk.getGmaxFactor()) "G" else ""},");
         packedTeamBuilder.append("${if (pk.getDmaxLevel() < 10) pk.getDmaxLevel() else ""},");
         packedTeamBuilder.append("${pokemon.getEffectedPokemon().getTeraType().showdownId()},");
         val var35: java.lang.String = packedTeamBuilder.toString();
         team.add(var35);
      }

      return CollectionsKt.joinToString$default(team, "]", null, null, 0, null, null, 62, null);
   }

   private fun startShowdown(battle: PokemonBattle) {
      val messages: java.util.List = new ArrayList();
      messages.add(">start { \"format\": ${battle.getFormat().toFormatJSON()} }");
      var actorIndex: Int = 1;

      for (BattleActor actor : battle.getSide1().getActors()) {
         `$this$forEach$iv`.setShowdownId("p$actorIndex");
         actorIndex += 2;
      }

      actorIndex = 2;

      for (BattleActor actor : battle.getSide2().getActors()) {
         var40.setShowdownId("p$actorIndex");
         actorIndex += 2;
      }

      for (BattleActor actor : battle.getActors()) {
         val var36: Int = battle.getFormat().getBattleType().getSlotsPerActor();

         for (int var41 = 0; var41 < var36; var41++) {
            var29.getActivePokemon().add(new ActiveBattlePokemon(var29, null, 2, null));
         }

         val var42: java.lang.Iterable = var29.getPokemonList();
         val `element$iv`: java.util.Collection = new ArrayList();

         for (Object element$iv$iv$iv : $this$mapNotNull$iv) {
            val var10000: PokemonEntity = (`element$iv$iv$iv` as BattlePokemon).getEntity();
            if (var10000 != null) {
               `element$iv`.add(var10000);
            }
         }

         for (Object element$ivx : $this$mapNotNull$iv) {
            (`element$ivx` as PokemonEntity).setBattleId(battle.getBattleId());
         }
      }

      for (BattleActor actor : CollectionsKt.sortedWith(battle.getActors(), new BattleRegistry$startShowdown$$inlined$sortedBy$1())) {
         messages.add(">player ${var31.getShowdownId()} {\"name\":\"${var31.getUuid()}\",\"team\":\"${this.packTeam(var31.getPokemonList())}\"}");
      }

      for (BattleActor actor : CollectionsKt.sortedWith(battle.getActors(), new BattleRegistry$startShowdown$$inlined$sortedBy$2())) {
         messages.add(">${var33.getShowdownId()} team ${var33.getPokemonList().size()}");
      }

      ShowdownService.Companion.getService().startBattle(battle, messages.toArray(new java.lang.String[0]));
   }

   public fun startBattle(battleFormat: BattleFormat, side1: BattleSide, side2: BattleSide, silent: Boolean = false): BattleStartResult {
      val battle: PokemonBattle = new PokemonBattle(battleFormat, side1, side2);
      if (silent) {
         return new SuccessfulBattleStart(battle);
      } else {
         val preBattleEvent: BattleStartedPreEvent = new BattleStartedPreEvent(battle, null, 2, null);
         val `this_$iv$iv`: EventObservable = CobblemonEvents.BATTLE_STARTED_PRE;
         val `events$iv$iv`: Array<Cancelable> = new Cancelable[]{preBattleEvent};
         `this_$iv$iv`.emit(Arrays.copyOf(`events$iv$iv`, `events$iv$iv`.length));

         for (Object element$iv$iv$iv : events$iv$iv) {
            if (!((Cancelable)`element$iv$iv$iv`).isCanceled()) {
               val it: BattleStartedPreEvent = `element$iv$iv$iv` as BattleStartedPreEvent;
               val `$this$iv`: java.util.Map = battleMap;
               val var10000: UUID = battle.getBattleId();
               `$this$iv`.put(var10000, battle);
               INSTANCE.startShowdown(battle);
               val var33: EventObservable = CobblemonEvents.BATTLE_STARTED_POST;
               val `events$iv`: Array<BattleStartedPostEvent> = new BattleStartedPostEvent[]{new BattleStartedPostEvent(battle)};
               var33.emit(Arrays.copyOf(`events$iv`, `events$iv`.length));

               for (Object element$iv$iv : events$iv) {
                  ;
               }

               return new SuccessfulBattleStart(battle);
            }
         }

         return new ErroredBattleStart(
            SetsKt.mutableSetOf(new BattleStartError[]{BattleStartError.Companion.canceledByEvent(preBattleEvent.getReason())}), null, 2, null
         );
      }
   }

   public fun closeBattle(battle: PokemonBattle) {
      battleMap.remove(battle.getBattleId());
   }

   public fun getBattle(id: UUID): PokemonBattle? {
      return battleMap.get(id);
   }

   public fun getBattleByParticipatingPlayer(serverPlayerEntity: ServerPlayer): PokemonBattle? {
      var var10000: java.util.Collection = battleMap.values();
      val var3: java.util.Iterator = var10000.iterator();

      while (true) {
         if (var3.hasNext()) {
            val var4: Any = var3.next();
            if ((var4 as PokemonBattle).getActor(serverPlayerEntity) == null) {
               continue;
            }

            var10000 = (java.util.Collection)var4;
            break;
         }

         var10000 = null;
         break;
      }

      return var10000 as PokemonBattle;
   }

   public fun getBattleByParticipatingPlayerId(playerId: UUID): PokemonBattle? {
      var var10000: java.util.Collection = battleMap.values();
      val var3: java.util.Iterator = var10000.iterator();

      while (true) {
         if (var3.hasNext()) {
            val var4: Any = var3.next();
            if (!CollectionsKt.contains((var4 as PokemonBattle).getPlayerUUIDs(), playerId)) {
               continue;
            }

            var10000 = (java.util.Collection)var4;
            break;
         }

         var10000 = null;
         break;
      }

      return var10000 as PokemonBattle;
   }

   public fun tick() {
      battleMap.forEachValue(java.lang.Long.MAX_VALUE, BattleRegistry::tick$lambda$10);
   }

   @JvmStatic
   fun `tick$lambda$10`(`$tmp0`: Function1, p0: Any) {
      `$tmp0`.invoke(p0);
   }

   public class BattleChallenge(challengeId: UUID, challengedPlayerUUID: UUID, selectedPokemonId: UUID, expiryTimeSeconds: Int = 60) {
      public final val challengeId: UUID
      public final val challengedPlayerUUID: UUID
      public final val challengedTime: Instant
      public final var expiryTimeSeconds: Int
      public final val selectedPokemonId: UUID

      init {
         this.challengeId = challengeId;
         this.challengedPlayerUUID = challengedPlayerUUID;
         this.selectedPokemonId = selectedPokemonId;
         this.expiryTimeSeconds = expiryTimeSeconds;
         this.challengedTime = Instant.now();
      }

      public fun isExpired(): Boolean {
         return Instant.now().isAfter(this.challengedTime.plusSeconds((long)this.expiryTimeSeconds));
      }
   }
}
