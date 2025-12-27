package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.ContextManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.chat.Component

@SourceDebugExtension(["SMAP\nBattleSide.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleSide.kt\ncom/cobblemon/mod/common/battles/BattleSide\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,42:1\n10242#2:43\n10664#2,5:44\n13579#2,2:49\n12744#2,2:51\n1855#3,2:53\n*S KotlinDebug\n*F\n+ 1 BattleSide.kt\ncom/cobblemon/mod/common/battles/BattleSide\n*L\n24#1:43\n24#1:44,5\n31#1:49,2\n34#1:51,2\n37#1:53,2\n*E\n"])
public class BattleSide(vararg actors: BattleActor) {
   public final val activePokemon: List<ActiveBattlePokemon>
      public final get() {
         val `$this$flatMap$iv`: Array<Any> = this.actors;
         val `destination$iv$iv`: java.util.Collection = new ArrayList();

         for (Object element$iv$iv : $this$flatMap$iv) {
            CollectionsKt.addAll(`destination$iv$iv`, ((BattleActor)`element$iv$iv`).getActivePokemon());
         }

         return `destination$iv$iv` as MutableList<ActiveBattlePokemon>;
      }


   public final val actors: Array<out BattleActor>
   public final lateinit var battle: PokemonBattle
   public final val contextManager: ContextManager

   init {
      this.actors = actors;
      this.contextManager = new ContextManager();
   }

   public fun getOppositeSide(): BattleSide {
      return if (this == this.getBattle().getSide1()) this.getBattle().getSide2() else this.getBattle().getSide1();
   }

   public fun broadcastChatMessage(component: Component) {
      val `$this$forEach$iv`: Any;
      for (Object element$iv : $this$forEach$iv) {
         ((BattleActor)`element$iv`).sendMessage(component);
      }
   }

   public fun stillSendingOut(): Boolean {
      val `$this$any$iv`: Array<Any> = this.actors;
      var var3: Int = 0;
      val var4: Int = this.actors.length;

      var var10000: Boolean;
      while (true) {
         if (var3 >= var4) {
            var10000 = false;
            break;
         }

         if (((BattleActor)`$this$any$iv`[var3]).getStillSendingOutCount() > 0) {
            var10000 = true;
            break;
         }

         var3++;
      }

      return var10000;
   }

   public fun playCries() {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         val var10000: BattlePokemon = (`element$iv` as ActiveBattlePokemon).getBattlePokemon();
         if (var10000 != null) {
            val var8: PokemonEntity = var10000.getEntity();
            if (var8 != null) {
               var8.cry();
            }
         }
      }
   }
}
