package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonEntities
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.Abilities
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item.ability.AbilityChanger
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ClientTaskTracker
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ScheduledTask
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingFunctionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ServerTaskTracker
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleFormat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleSide
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor.PlayerBattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor.PokemonBattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.generic.GenericBedrockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.animation.PlayPoseableAnimationPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeStartedPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.ActiveTrade
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.DummyTradeParticipant
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.PlayerTradeParticipant
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.TradeParticipant
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.StringExtensionsKt
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.io.PrintWriter
import java.nio.charset.Charset
import java.util.ArrayList;
import java.util.Locale
import java.util.UUID
import kotlin.jvm.functions.Function0
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import net.minecraft.world.level.entity.EntityTypeTest
import net.minecraft.world.phys.AABB
import org.jetbrains.annotations.NotNull

@SourceDebugExtension(["SMAP\nTestCommand.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TestCommand.kt\ncom/cobblemon/mod/common/command/TestCommand\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,396:1\n1855#2,2:397\n*S KotlinDebug\n*F\n+ 1 TestCommand.kt\ncom/cobblemon/mod/common/command/TestCommand\n*L\n134#1:397,2\n*E\n"])
public object TestCommand {
   public final var lastDebugId: Int
   public final var trade: ActiveTrade?

   public fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
      dispatcher.register(
         (Commands.m_82127_("testcommand").requires(TestCommand::register$lambda$0) as LiteralArgumentBuilder).executes(this::execute) as LiteralArgumentBuilder
      );
   }

   private fun execute(context: CommandContext<CommandSourceStack>): Int {
      if ((context.getSource() as CommandSourceStack).m_81373_() !is ServerPlayer) {
         return 1;
      } else {
         try {
            val var10000: Entity = (context.getSource() as CommandSourceStack).m_81373_();
            val e: ServerPlayer = var10000 as ServerPlayer;
            val var10002: Level = (var10000 as ServerPlayer).m_9236_();
            val evolutionEntity: GenericBedrockEntity = new GenericBedrockEntity(var10002);
            evolutionEntity.setCategory(MiscUtilsKt.cobblemonResource("evolution"));
            evolutionEntity.setColliderHeight(1.5F);
            evolutionEntity.setColliderWidth(1.5F);
            evolutionEntity.setScale(1.0F);
            evolutionEntity.setSyncAge(true);
            evolutionEntity.m_6034_(e.m_20185_(), e.m_20186_(), e.m_20189_() + (double)4);
            e.m_9236_().m_7967_(evolutionEntity);
            ClientTaskTracker.INSTANCE
               .after(
                  0.5F,
                  (
                     new Function0<Unit>(e, evolutionEntity) {
                        {
                           super(0);
                           this.$player = `$player`;
                           this.$evolutionEntity = `$evolutionEntity`;
                        }

                        public final void invoke() {
                           CobblemonNetwork.INSTANCE
                              .sendPacket(
                                 this.$player,
                                 new PlayPoseableAnimationPacket(
                                    this.$evolutionEntity.m_19879_(), SetsKt.setOf("evolution:animation.evolution.evolution"), SetsKt.emptySet()
                                 )
                              );
                        }
                     }
                  ) as () -> Unit
               );
         } catch (var7: Exception) {
            var7.printStackTrace();
         }

         return 1;
      }
   }

   private fun testClosestBattle(context: CommandContext<CommandSourceStack>) {
      val player: ServerPlayer = (context.getSource() as CommandSourceStack).m_81375_();

      val scanBox: java.lang.Iterable;
      for (Object element$iv : scanBox) {
         (`element$iv` as BattlePokemon).getEffectedPokemon().setLevel(100);
      }

      val var11: java.util.List = player.m_9236_()
         .m_142425_(CobblemonEntities.POKEMON as EntityTypeTest, AABB.m_165882_(player.m_20182_(), 9.0, 9.0, 9.0), TestCommand::testClosestBattle$lambda$3);
      val var12: PokemonEntity = CollectionsKt.firstOrNull(var11) as PokemonEntity;
      if (var12 == null) {
         (context.getSource() as CommandSourceStack).m_81352_(Component.m_237113_("Cannot find any wild Pokémon in a 9x9x9 area") as Component);
      } else {
         val var10000: BattleRegistry = BattleRegistry.INSTANCE;
         val var10001: BattleFormat = BattleFormat.Companion.getGEN_9_SINGLES();
         var var13: Array<BattleActor> = new BattleActor[1];
         val var10008: UUID = player.m_20148_();
         var13[0] = new PlayerBattleActor(var10008, PartyStore.toBattleTeam$default(PlayerExtensionsKt.party(player), true, false, null, 6, null));
         val var10002: BattleSide = new BattleSide(var13);
         var13 = new BattleActor[1];
         val var10009: UUID = var12.getPokemon().getUuid();
         var13[0] = new PokemonBattleActor(
            var10009, new BattlePokemon(var12.getPokemon(), null, null, 6, null), Cobblemon.INSTANCE.getConfig().getDefaultFleeDistance(), null, 8, null
         );
         BattleRegistry.startBattle$default(var10000, var10001, var10002, new BattleSide(var13), false, 8, null);
      }
   }

   private fun testTrade(playerEntity: ServerPlayer) {
      val trade: ActiveTrade = new ActiveTrade(
         new PlayerTradeParticipant(playerEntity),
         new DummyTradeParticipant(
            CollectionsKt.mutableListOf(new Pokemon[]{StringExtensionsKt.toPokemon("pikachu level=30 shiny"), StringExtensionsKt.toPokemon("machop level=15")})
         )
      );
      trade = trade;
      val var10000: CobblemonNetwork = CobblemonNetwork.INSTANCE;
      val var10004: UUID = trade.getPlayer2().getUuid();
      val var10005: MutableComponent = trade.getPlayer2().getName().m_6881_();
      var10000.sendPacket(playerEntity, new TradeStartedPacket(var10004, var10005, trade.getPlayer2().getParty().mapNullPreserving(<unrepresentable>.INSTANCE)));
      SchedulingFunctionsKt.taskBuilder().interval(0.5F).execute((new Function1<ScheduledTask, Unit>(this, trade) {
         {
            super(1);
            this.this$0 = `$receiver`;
            this.$trade = `$trade`;
         }

         public final void invoke(@NotNull ScheduledTask task) {
            if (!(this.this$0.getTrade() == this.$trade)) {
               task.expire();
            } else {
               TestCommand.access$testUpdate(TestCommand.INSTANCE);
            }
         }
      }) as (ScheduledTask?) -> Unit).tracker(ServerTaskTracker.INSTANCE).iterations(Integer.MAX_VALUE).build();
   }

   private fun testUpdate() {
      if (trade != null) {
         val var10000: TradeParticipant = trade.getPlayer2();
         val dummy: DummyTradeParticipant = var10000 as DummyTradeParticipant;
         if (lastDebugId != 0) {
            lastDebugId = 0;
         }
      }
   }

   public fun readBerryDataFromCSV() {
      val gson: Gson = new GsonBuilder().setPrettyPrinting().create();
      val iterator: java.util.Iterator = FilesKt.readLines$default(new File("scripty/berries.csv"), null, 1, null).iterator();
      iterator.next();
      iterator.next();
      val var4: java.util.Iterator = iterator;

      while (var4.hasNext()) {
         val cols: java.util.List = StringsKt.split$default(var4.next() as java.lang.String, new java.lang.String[]{","}, false, 0, 6, null);
         val var28: java.lang.String = (cols.get(1) as java.lang.String).toLowerCase(Locale.ROOT);
         val var20: java.lang.String = "$var28_berry";
         val growthPoints: File = new File("scripty/old/$var20.json");
         val index: Charset = Charsets.UTF_8;
         val json: JsonObject = gson.fromJson(new InputStreamReader(new FileInputStream(growthPoints), index), JsonObject.class) as JsonObject;
         val var21: java.util.List = new ArrayList();

         for (int indexx = 7; cols.size() > indexx && !StringsKt.isBlank((java.lang.CharSequence)cols.get(indexx)); indexx += 6) {
            val arr: Float = java.lang.Float.parseFloat(cols.get(indexx) as java.lang.String);
            val var12: Float = java.lang.Float.parseFloat(cols.get(indexx + 1) as java.lang.String);
            val pw: Float = java.lang.Float.parseFloat(cols.get(indexx + 2) as java.lang.String);
            val rotX: Float = java.lang.Float.parseFloat(cols.get(indexx + 3) as java.lang.String);
            val rotY: Float = java.lang.Float.parseFloat(cols.get(indexx + 4) as java.lang.String);
            val rotZ: Float = java.lang.Float.parseFloat(cols.get(indexx + 5) as java.lang.String);
            val position: JsonObject = new JsonObject();
            position.addProperty("x", arr);
            position.addProperty("y", var12);
            position.addProperty("z", pw);
            val rotation: JsonObject = new JsonObject();
            rotation.addProperty("x", rotX);
            rotation.addProperty("y", rotY);
            rotation.addProperty("z", rotZ);
            val obj: JsonObject = new JsonObject();
            obj.add("position", position as JsonElement);
            obj.add("rotation", rotation as JsonElement);
            var21.add(obj);
         }

         val var23: JsonArray = json.getAsJsonArray("growthPoints");
         CollectionsKt.removeAll(var23 as java.lang.Iterable, <unrepresentable>.INSTANCE);

         for (JsonObject point : growthPoints) {
            var23.add(var26 as JsonElement);
         }

         val var27: PrintWriter = new PrintWriter(new File("scripty/new/$var20.json"));
         gson.toJson(json as JsonElement, var27);
         var27.flush();
         var27.close();
      }
   }

   private fun testAbilitiesBetweenEvolution(context: CommandContext<CommandSourceStack>) {
      (context.getSource() as CommandSourceStack)
         .m_243053_(
            Component.m_237113_("Ability test results (Assumed default assets)")
               .m_7220_(Component.m_237113_("\n") as Component)
               .m_7220_(this.testHiddenAbilityThroughoutEvolutions())
               .m_7220_(Component.m_237113_("\n") as Component)
               .m_7220_(this.testMiddleStageSingleAbility())
               .m_7220_(Component.m_237113_("\n") as Component)
               .m_7220_(this.testForcedAbility())
               .m_7220_(Component.m_237113_("\n") as Component)
               .m_7220_(this.testIllegalAbilityNonForced())
               .m_7220_(Component.m_237113_("\n") as Component)
               .m_7220_(this.testAbilityCapsule())
               .m_7220_(Component.m_237113_("\n") as Component)
               .m_7220_(this.testAbilityPatch()) as Component
         );
   }

   private fun testHiddenAbilityThroughoutEvolutions(): Component {
      val pokemon: Pokemon = PokemonProperties.Companion.parse$default(
            PokemonProperties.Companion, "dragonair level=${Cobblemon.INSTANCE.getConfig().getMaxPokemonLevel()} hiddenability=true", null, null, 6, null
         )
         .create();
      val var10000: Evolution = CollectionsKt.firstOrNull(pokemon.getEvolutions()) as Evolution;
      if (var10000 == null) {
         val var7: MutableComponent = Component.m_237113_("✖ Failed to find Dragonair » Dragonite evolution");
         return TextKt.red(var7) as Component;
      } else {
         var10000.evolutionMethod(pokemon);
         val failed: Boolean = pokemon.getAbility().getIndex() != 0 || pokemon.getAbility().getPriority() != Priority.LOW || pokemon.getAbility().getForced();
         val result: MutableComponent = Component.m_237113_(
            " ${if (failed) "✖" else "✔"} Dratini line final Ability(name=${pokemon.getAbility().getName()}, priority=${pokemon.getAbility().getPriority()}, index=${pokemon.getAbility()
               .getIndex()}, forced=${pokemon.getAbility().getForced()})"
         );
         val var6: Component;
         if (failed) {
            var6 = TextKt.red(result) as Component;
         } else {
            var6 = TextKt.green(result) as Component;
         }

         return var6;
      }
   }

   private fun testMiddleStageSingleAbility(): Component {
      val pokemon: Pokemon = PokemonProperties.Companion.parse$default(
            PokemonProperties.Companion, "scatterbug level=${Cobblemon.INSTANCE.getConfig().getMaxPokemonLevel()} ability=compoundeyes", null, null, 6, null
         )
         .create();
      var var10000: Evolution = CollectionsKt.firstOrNull(pokemon.getEvolutions()) as Evolution;
      if (var10000 == null) {
         val var10: MutableComponent = Component.m_237113_("✖ Failed to find Scatterbug » Spewpa evolution");
         return TextKt.red(var10) as Component;
      } else {
         var10000.evolutionMethod(pokemon);
         var10000 = CollectionsKt.firstOrNull(pokemon.getEvolutions()) as Evolution;
         if (var10000 == null) {
            val var9: MutableComponent = Component.m_237113_("✖ Failed to find Spewpa » Vivillon evolution");
            return TextKt.red(var9) as Component;
         } else {
            var10000.evolutionMethod(pokemon);
            val failed: Boolean = pokemon.getAbility().getIndex() != 1
               || pokemon.getAbility().getPriority() != Priority.LOWEST
               || pokemon.getAbility().getForced();
            val result: MutableComponent = Component.m_237113_(
               " ${if (failed) "✖" else "✔"} Scatterbug line final Ability(name=${pokemon.getAbility().getName()}, priority=${pokemon.getAbility()
                  .getPriority()}, index=${pokemon.getAbility().getIndex()}, forced=${pokemon.getAbility().getForced()})"
            );
            val var8: Component;
            if (failed) {
               var8 = TextKt.red(result) as Component;
            } else {
               var8 = TextKt.green(result) as Component;
            }

            return var8;
         }
      }
   }

   private fun testForcedAbility(): Component {
      val pokemon: Pokemon = PokemonProperties.Companion.parse$default(
            PokemonProperties.Companion, "magikarp level=${Cobblemon.INSTANCE.getConfig().getMaxPokemonLevel()} ability=adaptability", null, null, 6, null
         )
         .create();
      val var10000: Evolution = CollectionsKt.firstOrNull(pokemon.getEvolutions()) as Evolution;
      if (var10000 == null) {
         val var7: MutableComponent = Component.m_237113_("✖ Failed to find Magikarp » Gyarados evolution");
         return TextKt.red(var7) as Component;
      } else {
         var10000.evolutionMethod(pokemon);
         val failed: Boolean = !pokemon.getAbility().getForced() || !(pokemon.getAbility().getTemplate().getName() == "adaptability");
         val result: MutableComponent = Component.m_237113_(
            " ${if (failed) "✖" else "✔"} Magikarp line forced Ability(name=${pokemon.getAbility().getName()}, priority=${pokemon.getAbility().getPriority()}, index=${pokemon.getAbility()
               .getIndex()}, forced=${pokemon.getAbility().getForced()})"
         );
         val var6: Component;
         if (failed) {
            var6 = TextKt.red(result) as Component;
         } else {
            var6 = TextKt.green(result) as Component;
         }

         return var6;
      }
   }

   private fun testIllegalAbilityNonForced(): Component {
      val pokemon: Pokemon = PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "rattata", null, null, 6, null).create();
      pokemon.updateAbility(Abilities.INSTANCE.getOrException("adaptability").create(false));
      val failed: Boolean = !pokemon.getAbility().getForced();
      val result: MutableComponent = Component.m_237113_(
         " ${if (failed) "✖" else "✔"} Rattata illegal non-forced (name=${pokemon.getAbility().getName()}, priority=${pokemon.getAbility().getPriority()}, index=${pokemon.getAbility()
            .getIndex()}, forced=${pokemon.getAbility().getForced()})"
      );
      val var10000: Component;
      if (failed) {
         var10000 = TextKt.red(result) as Component;
      } else {
         var10000 = TextKt.green(result) as Component;
      }

      return var10000;
   }

   private fun testAbilityCapsule(): Component {
      val pokemon: Pokemon = PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "rattata", null, null, 6, null).create();
      val failed: Boolean = !AbilityChanger.Companion.getCOMMON_ABILITY().performChange(pokemon);
      val result: MutableComponent = Component.m_237113_(
         " ${if (failed) "✖" else "✔"} Rattata capsule Ability(name=${pokemon.getAbility().getName()}, priority=${pokemon.getAbility().getPriority()}, index=${pokemon.getAbility()
            .getIndex()}, forced=${pokemon.getAbility().getForced()})"
      );
      val var10000: Component;
      if (failed) {
         var10000 = TextKt.red(result) as Component;
      } else {
         var10000 = TextKt.green(result) as Component;
      }

      return var10000;
   }

   private fun testAbilityPatch(): Component {
      val pokemon: Pokemon = PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "magikarp ha=true", null, null, 6, null).create();
      val failed: Boolean = AbilityChanger.Companion.getHIDDEN_ABILITY().performChange(pokemon);
      val result: MutableComponent = Component.m_237113_(
         " ${if (failed) "✖" else "✔"} Magikarp patch Ability(name=${pokemon.getAbility().getName()}, priority=${pokemon.getAbility().getPriority()}, index=${pokemon.getAbility()
            .getIndex()}, forced=${pokemon.getAbility().getForced()})"
      );
      val var10000: Component;
      if (failed) {
         var10000 = TextKt.red(result) as Component;
      } else {
         var10000 = TextKt.green(result) as Component;
      }

      return var10000;
   }

   @JvmStatic
   fun `register$lambda$0`(it: CommandSourceStack): Boolean {
      return it.m_6761_(4);
   }

   @JvmStatic
   fun `testClosestBattle$lambda$3`(`$tmp0`: Function1, p0: Any): Boolean {
      return `$tmp0`.invoke(p0) as java.lang.Boolean;
   }
}
