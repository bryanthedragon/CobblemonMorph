package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BasicContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.MissingContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext.Type
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InstructionSet
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.ContextManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions.UnknownInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.DistributionUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import java.util.ArrayList;
import java.util.Arrays
import java.util.LinkedHashMap
import java.util.UUID
import kotlin.jvm.functions.Function0
import kotlin.jvm.functions.Function4
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

@SourceDebugExtension(["SMAP\nShowdownInterpreter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ShowdownInterpreter.kt\ncom/cobblemon/mod/common/battles/ShowdownInterpreter\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 4 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n*L\n1#1,289:1\n1855#2,2:290\n1549#2:295\n1620#2,3:296\n1855#2,2:301\n3792#3:292\n4307#3,2:293\n37#4,2:299\n*S KotlinDebug\n*F\n+ 1 ShowdownInterpreter.kt\ncom/cobblemon/mod/common/battles/ShowdownInterpreter\n*L\n171#1:290,2\n232#1:295\n232#1:296,3\n60#1:301,2\n231#1:292\n231#1:293,2\n234#1:299,2\n*E\n"])
public object ShowdownInterpreter {
   public final val lastCauser: MutableMap<UUID, BattleMessage> = (new LinkedHashMap()) as java.util.Map
   private final val sideInstructionParser: MutableMap<String, (PokemonBattle, BattleActor, InstructionSet, BattleMessage) -> InterpreterInstruction> =
      (new LinkedHashMap()) as java.util.Map
      private final val splitInstructionParser: MutableMap<
      String,
      (PokemonBattle, BattleActor, InstructionSet, BattleMessage, BattleMessage, Iterator<BattleMessage>) -> InterpreterInstruction
   > = (new LinkedHashMap()) as java.util.Map
   private final val updateInstructionParser: MutableMap<
      String,
      (PokemonBattle, InstructionSet, BattleMessage, Iterator<BattleMessage>) -> InterpreterInstruction
   > = (new LinkedHashMap()) as java.util.Map

   public fun interpretMessage(battleId: UUID, message: String) {
      if (!StringsKt.startsWith$default(message, "{\"winner\":\"", false, 2, null)) {
         val battle: PokemonBattle = BattleRegistry.INSTANCE.getBattle(battleId);
         if (battle == null) {
            Cobblemon.INSTANCE.getLOGGER().info("No battle could be found with the id: $battleId");
         } else {
            DistributionUtilsKt.runOnServer((new Function0<Unit>(battle, message) {
               {
                  super(0);
                  this.$battle = `$battle`;
                  this.$message = `$message`;
               }

               public final void invoke() {
                  this.$battle.getShowdownMessages().add(this.$message);
                  ShowdownInterpreter.INSTANCE.interpret(this.$battle, this.$message);
               }
            }) as Function0);
         }
      }
   }

   public fun interpret(battle: PokemonBattle, rawMessage: String) {
      PokemonBattle.log$default(battle, null, 1, null);
      battle.log(rawMessage);
      PokemonBattle.log$default(battle, null, 1, null);
      val instructionSet: InstructionSet = new InstructionSet();
      val battleMessages: java.util.List = new ArrayList();

      try {
         val e: java.util.List = CollectionsKt.toMutableList(StringsKt.split$default(rawMessage, new java.lang.String[]{"\n"}, false, 0, 6, null));
         if (e.get(0) == "update") {
            e.remove(0);

            val var14: java.lang.Iterable;
            for (Object element$iv : var14) {
               battleMessages.add(new BattleMessage(var20 as java.lang.String));
            }

            val var15: java.util.Iterator = battleMessages.iterator();

            while (iterator.hasNext()) {
               var var26: InterpreterInstruction;
               label33: {
                  val var17: BattleMessage = var15.next() as BattleMessage;
                  val var25: Function4 = updateInstructionParser.get(StringsKt.replace$default(var17.getId(), "|", "", false, 4, null));
                  if (var25 != null) {
                     var26 = var25.invoke(battle, instructionSet, var17, var15) as InterpreterInstruction;
                     if (var26 != null) {
                        break label33;
                     }
                  }

                  var26 = new UnknownInstruction(var17);
               }

               instructionSet.getInstructions().add(var26);
            }
         } else if (e.get(0) == "sideupdate") {
            val var13: java.lang.String = e.get(1) as java.lang.String;
            val targetActor: BattleActor = battle.getActor(var13);
            val message: BattleMessage = new BattleMessage(e.get(2) as java.lang.String);
            val id: java.lang.String = StringsKt.replace$default(message.getId(), "|", "", false, 4, null);
            if (targetActor == null) {
               battle.log("No actor could be found with the showdown id: $var13");
               return;
            }

            var var24: InterpreterInstruction;
            label51: {
               val var23: Function4 = sideInstructionParser.get(id);
               if (var23 != null) {
                  var24 = var23.invoke(battle, targetActor, instructionSet, message) as InterpreterInstruction;
                  if (var24 != null) {
                     break label51;
                  }
               }

               var24 = new UnknownInstruction(message);
            }

            instructionSet.getInstructions().add(var24);
         }

         instructionSet.execute(battle);
      } catch (var12: Exception) {
         Cobblemon.INSTANCE.getLOGGER().error("Caught exception interpreting {}", var12);
      }
   }

   public fun broadcastOptionalAbility(battle: PokemonBattle, effect: Effect?, pokemon: BattlePokemon) {
      if (effect != null && effect.getType() === Effect.Type.ABILITY) {
         this.broadcastAbility(battle, effect, pokemon);
      }
   }

   public fun broadcastAbility(battle: PokemonBattle, effect: Effect, pokemon: BattlePokemon) {
      battle.dispatchGo((new Function0<Unit>(pokemon, effect, battle) {
         {
            super(0);
            this.$pokemon = `$pokemon`;
            this.$effect = `$effect`;
            this.$battle = `$battle`;
         }

         public final void invoke() {
            val var10000: MutableComponent = LocalizationUtilsKt.battleLang("ability.generic", this.$pokemon.getName(), this.$effect.getTypelessData());
            this.$battle.broadcastChatMessage(TextKt.yellow(var10000) as Component);
         }
      }) as () -> Unit);
   }

   public fun getContextFromFaint(pokemon: BattlePokemon, battle: PokemonBattle): BattleContext {
      var var10000: BattleMessage = battle.getMinorBattleActions().get(pokemon.getUuid());
      if (var10000 == null) {
         var10000 = lastCauser.get(battle.getBattleId());
         if (var10000 == null) {
            return new MissingContext(null, 0, null, null, 15, null);
         }
      }

      val side: BattleSide = pokemon.getActor().getSide();
      val var5: java.lang.String = var10000.getId();
      switch (var5.hashCode()) {
         case -1387046880:
            if (var5.equals("-activate")) {
               val var62: Effect = var10000.effectAt(1);
               return if (var62 != null)
                  new BasicContext(var62.getId(), battle.getTurn(), BattleContext.Type.FAINT, var10000.battlePokemon(0, battle))
                  else
                  new MissingContext(null, 0, null, null, 15, null);
            }

            return new MissingContext(null, 0, null, null, 15, null);
         case -56166948:
            if (!var5.equals("-damage")) {
               return new MissingContext(null, 0, null, null, 15, null);
            }
            break;
         case 3357649:
            if (!var5.equals("move")) {
               return new MissingContext(null, 0, null, null, 15, null);
            }
            break;
         case 1398069333:
            if (var5.equals("-start")) {
               val var48: Effect = var10000.effectAt(1);
               if (var48 != null) {
                  val var50: BattleContext = ContextManager.Companion
                     .scoop(
                        if (StringsKt.contains$default(var48.getId(), "perish", false, 2, null)) "perishsong" else var48.getId(),
                        pokemon.getContextManager().get(BattleContext.Type.VOLATILE)
                     );
                  if (var50 != null) {
                     return var50;
                  }
               }

               return new MissingContext(null, 0, null, null, 15, null);
            }

            return new MissingContext(null, 0, null, null, 15, null);
         default:
            return new MissingContext(null, 0, null, null, 15, null);
      }

      val var51: Effect = var10000.effect("of");
      var var63: BattleContext;
      if (var51 != null) {
         label89: {
            val var52: Effect = BattleMessage.effect$default(var10000, null, 1, null);
            if (var52 != null) {
               var53 = var52.getId();
               if (var53 != null) {
                  break label89;
               }
            }

            var53 = var51.getId();
         }

         val var54: java.lang.String = var10000.optionalArgument("of");
         val var29: java.lang.String = StringsKt.substringBefore$default(var54, ':', null, 2, null);
         val var55: java.lang.String = var10000.optionalArgument("of");
         var63 = new BasicContext(
            var53,
            battle.getTurn(),
            BattleContext.Type.FAINT,
            battle.getBattlePokemon(var29, StringsKt.trim(StringsKt.substringAfter$default(var55, ':', null, 2, null)).toString())
         );
      } else {
         val var56: Effect = BattleMessage.effect$default(var10000, null, 1, null);
         if (var56 == null) {
            var63 = null;
         } else {
            var var32: Array<Any> = BattleContext.Type.values();
            val `$i$f$toTypedArray`: java.util.Collection = new ArrayList();

            for (Object element$iv$iv : $this$filter$iv) {
               if (((BattleContext.Type)`item$iv$iv`).getDamaging()) {
                  `$i$f$toTypedArray`.add(`item$iv$iv`);
               }
            }

            val var36: java.lang.Iterable = `$i$f$toTypedArray` as java.util.List;
            val `destination$iv$ivx`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(`$i$f$toTypedArray` as java.util.List, 10));

            for (Object item$iv$iv : $this$map$iv) {
               val it: BattleContext.Type = var46 as BattleContext.Type;
               var var57: java.util.Collection = pokemon.getContextManager().get(var46 as BattleContext.Type);
               if (var57 == null) {
                  var57 = side.getContextManager().get(it);
                  if (var57 == null) {
                     var57 = battle.getContextManager().get(it);
                  }
               }

               `destination$iv$ivx`.add(var57);
            }

            var32 = `destination$iv$ivx` as java.util.List;
            val var58: ContextManager.Companion = ContextManager.Companion;
            val var10001: java.lang.String = var56.getId();
            val var37: Array<java.util.Collection> = (var32 as java.util.Collection).toArray(new java.util.Collection[0]);
            var63 = var58.scoop(var10001, Arrays.copyOf(var37, var37.length));
         }

         if (var63 == null) {
            var10000 = lastCauser.get(battle.getBattleId());
            val var61: BasicContext;
            if (var10000 != null) {
               val var60: Effect = var10000.effectAt(1);
               var61 = new BasicContext(var60.getId(), battle.getTurn(), BattleContext.Type.FAINT, var10000.battlePokemon(0, battle));
            } else {
               var61 = null;
            }

            var63 = if (var61 != null) var61 else new MissingContext(null, 0, null, null, 15, null);
         }
      }

      return var63;
   }

   public fun getContextFromAction(message: BattleMessage, type: Type, battle: PokemonBattle): BattleContext {
      val var10000: Pair = BattleMessage.actorAndActivePokemonFromOptional$default(message, battle, null, 2, null);
      var var16: BattleContext;
      if (var10000 != null) {
         label67: {
            val var13: Effect = message.effectAt(1);
            if (var13 != null) {
               var14 = var13.getId();
               if (var14 != null) {
                  break label67;
               }
            }

            val var15: Effect = message.effectAt(0);
            if (var15 == null) {
               return new MissingContext(null, 0, null, null, 15, null);
            }

            var14 = var15.getId();
         }

         var16 = new BasicContext(var14, battle.getTurn(), type, (var10000.getSecond() as ActiveBattlePokemon).getBattlePokemon());
      } else if (message.actorAndActivePokemon(0, battle) != null) {
         val var17: Effect = message.effectAt(1);
         if (var17 != null) {
            val var18: java.lang.String = var17.getId();
            if (var18 != null) {
               val var19: BattleMessage = lastCauser.get(battle.getBattleId());
               if (var19 != null) {
                  val var20: BattlePokemon = var19.battlePokemon(0, battle);
                  if (var20 != null) {
                     return new BasicContext(var18, battle.getTurn(), type, var20);
                  }
               }

               return new MissingContext(null, 0, null, null, 15, null);
            }
         }

         var16 = new MissingContext(null, 0, null, null, 15, null);
      } else {
         val var21: BattleMessage = lastCauser.get(battle.getBattleId());
         if (var21 == null) {
            var16 = null;
         } else {
            label71: {
               label69: {
                  val var22: Effect = message.effectAt(1);
                  if (var22 != null) {
                     var23 = var22.getId();
                     if (var23 != null) {
                        break label69;
                     }
                  }

                  val var24: Effect = message.effectAt(0);
                  if (var24 == null) {
                     var16 = new MissingContext(null, 0, null, null, 15, null);
                     break label71;
                  }

                  var23 = var24.getId();
               }

               var16 = new BasicContext(var23, battle.getTurn(), type, var21.battlePokemon(0, battle));
            }
         }

         if (var16 == null) {
            var16 = new MissingContext(null, 0, null, null, 15, null);
         }
      }

      return var16;
   }

   @JvmStatic
   fun {
      updateInstructionParser.put("split", <unrepresentable>.INSTANCE);

      val var6: java.lang.Iterable;
      for (Object element$iv : var6) {
         updateInstructionParser.put(`element$iv` as java.lang.String, <unrepresentable>.INSTANCE);
      }

      updateInstructionParser.put("-ability", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("-activate", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("bagitem", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("-boost", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("-block", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("cant", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("-clearallboost", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("-clearnegativeboost", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("-copyboost", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("-crit", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("-curestatus", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("detailschange", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("-endability", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("-end", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("-enditem", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("-fail", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("faint", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("-fieldactivate", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("-fieldend", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("-fieldstart", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("-hitcount", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("-immune", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("-invertboost", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("-item", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("-mega", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("-miss", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("move", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("-nothing", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("pp_update", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("-prepare", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("-mustrecharge", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("replace", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("-resisted", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("-resisted", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("-setboost", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("-sideend", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("-sidestart", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("-singlemove", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("-singleturn", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("-start", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("-status", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("-supereffective", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("-swapboost", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("-swapsideconditions", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("-terastallize", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("-transform", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("turn", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("-unboost", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("upkeep", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("-weather", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("win", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("-zbroken", <unrepresentable>.INSTANCE);
      updateInstructionParser.put("-zpower", <unrepresentable>.INSTANCE);
      sideInstructionParser.put("error", <unrepresentable>.INSTANCE);
      sideInstructionParser.put("request", <unrepresentable>.INSTANCE);
      splitInstructionParser.put("-damage", <unrepresentable>.INSTANCE);
      splitInstructionParser.put("drag", <unrepresentable>.INSTANCE);
      splitInstructionParser.put("-heal", <unrepresentable>.INSTANCE);
      splitInstructionParser.put("-sethp", <unrepresentable>.INSTANCE);
      splitInstructionParser.put("switch", <unrepresentable>.INSTANCE);
   }
}
