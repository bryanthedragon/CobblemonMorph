package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ai

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.ai.BattleAI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.DefaultActionResponse
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.InBattleMove
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.MoveActionResponse
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.PassActionResponse
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionResponse
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownMoveset
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.SwitchActionResponse
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.Targetable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.random.Random

@SourceDebugExtension(["SMAP\nRandomBattleAI.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RandomBattleAI.kt\ncom/cobblemon/mod/common/battles/ai/RandomBattleAI\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,57:1\n766#2:58\n857#2,2:59\n766#2:61\n857#2,2:62\n766#2:64\n857#2,2:65\n766#2:67\n857#2,2:68\n*S KotlinDebug\n*F\n+ 1 RandomBattleAI.kt\ncom/cobblemon/mod/common/battles/ai/RandomBattleAI\n*L\n33#1:58\n33#1:59,2\n43#1:61\n43#1:62,2\n44#1:64\n44#1:65,2\n53#1:67\n53#1:68,2\n*E\n"])
public class RandomBattleAI : BattleAI {
   public override fun choose(activeBattlePokemon: ActiveBattlePokemon, moveset: ShowdownMoveset?, forceSwitch: Boolean): ShowdownActionResponse {
      if (!forceSwitch && !activeBattlePokemon.isGone()) {
         if (moveset == null) {
            return PassActionResponse.INSTANCE;
         } else {
            var var18: java.lang.Iterable = moveset.getMoves();
            var var23: java.util.Collection = new ArrayList();

            for (Object element$iv$iv : var18) {
               if ((var31 as InBattleMove).canBeUsed()) {
                  var23.add(var31);
               }
            }

            var18 = var23 as java.util.List;
            var23 = new ArrayList();

            for (Object element$iv$ivx : var18) {
               var var41: Boolean;
               label86: {
                  val var35: InBattleMove = `element$iv$ivx` as InBattleMove;
                  if (!(`element$iv$ivx` as InBattleMove).mustBeUsed()) {
                     val var40: java.util.List = var35.getTarget().getTargetList().invoke(activeBattlePokemon) as java.util.List;
                     if (var40 != null && var40.isEmpty()) {
                        var41 = false;
                        break label86;
                     }
                  }

                  var41 = true;
               }

               if (var41) {
                  var23.add(`element$iv$ivx`);
               }
            }

            val var42: InBattleMove = CollectionsKt.randomOrNull(var23 as java.util.List, Random.Default as Random) as InBattleMove;
            if (var42 == null) {
               return new MoveActionResponse("struggle", null, null, 6, null);
            } else {
               val target: java.util.List = if (var42.mustBeUsed()) null else var42.getTarget().getTargetList().invoke(activeBattlePokemon) as java.util.List;
               val var43: ShowdownActionResponse;
               if (target == null) {
                  var43 = new MoveActionResponse(var42.getId(), null, null, 6, null);
               } else {
                  val `$this$filter$iv`: java.lang.Iterable = target;
                  val `destination$iv$ivx`: java.util.Collection = new ArrayList();

                  for (Object element$iv$ivx : $this$filter$iv) {
                     if (!(`element$iv$ivx` as Targetable).isAllied(activeBattlePokemon)) {
                        `destination$iv$ivx`.add(`element$iv$ivx`);
                     }
                  }

                  var var44: Targetable = CollectionsKt.randomOrNull(`destination$iv$ivx` as java.util.List, Random.Default as Random) as Targetable;
                  if (var44 == null) {
                     var44 = CollectionsKt.random(target, Random.Default as Random) as Targetable;
                  }

                  val var10002: java.lang.String = var42.getId();
                  var43 = new MoveActionResponse(var10002, (var44 as ActiveBattlePokemon).getPNX(), null, 4, null);
               }

               return var43;
            }
         }
      } else {
         val chosenTarget: java.lang.Iterable = activeBattlePokemon.getActor().getPokemonList();
         val `$i$f$filter`: java.util.Collection = new ArrayList();

         for (Object element$iv$ivxx : $this$filter$iv) {
            if ((`element$iv$ivxx` as BattlePokemon).canBeSentOut()) {
               `$i$f$filter`.add(`element$iv$ivxx`);
            }
         }

         val var10000: BattlePokemon = CollectionsKt.randomOrNull(`$i$f$filter` as java.util.List, Random.Default as Random) as BattlePokemon;
         if (var10000 == null) {
            return new DefaultActionResponse();
         } else {
            var10000.setWillBeSwitchedIn(true);
            return new SwitchActionResponse(var10000.getUuid());
         }
      }
   }
}
