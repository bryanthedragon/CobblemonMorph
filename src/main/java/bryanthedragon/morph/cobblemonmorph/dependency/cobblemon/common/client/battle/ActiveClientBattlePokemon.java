package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleFormat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.Targetable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.animations.TileAnimation;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

public class ActiveClientBattlePokemon(actor: ClientBattleActor, battlePokemon: ClientBattlePokemon?) : Targetable {
   public final val actor: ClientBattleActor
   public final var animations: ConcurrentLinkedQueue<TileAnimation>
   public final var ballCapturing: ClientBallDisplay?
   public final var battlePokemon: ClientBattlePokemon?
   public final var invisibleX: Float
   public final var xDisplacement: Float

   init {
      this.actor = actor;
      this.battlePokemon = battlePokemon;
      this.animations = new ConcurrentLinkedQueue<>();
      this.invisibleX = -1.0F;
   }

   public open fun getAllActivePokemon(): List<ActiveClientBattlePokemon> {
      val `$this$flatMap$iv`: Array<Any> = this.actor.getSide().getBattle().getSides();
      val `destination$iv$iv`: java.util.Collection = new ArrayList();

      for (Object element$iv$iv : $this$flatMap$iv) {
         CollectionsKt.addAll(`destination$iv$iv`, ((ClientBattleSide)`element$iv$iv`).getActiveClientBattlePokemon());
      }

      return `destination$iv$iv` as MutableList<ActiveClientBattlePokemon>;
   }

   public open fun getActorPokemon(): MutableList<ActiveClientBattlePokemon> {
      return this.actor.getActivePokemon();
   }

   public override fun getSidePokemon(): Iterable<ActiveClientBattlePokemon> {
      return this.actor.getSide().getActiveClientBattlePokemon();
   }

   public override fun getActorShowdownId(): String {
      return this.actor.getShowdownId();
   }

   public override fun getFormat(): BattleFormat {
      return this.actor.getSide().getBattle().getBattleFormat();
   }

   public override fun isAllied(other: Targetable): Boolean {
      return this.actor.getSide() == (other as ActiveClientBattlePokemon).actor.getSide();
   }

   public override fun hasPokemon(): Boolean {
      return this.battlePokemon != null;
   }

   public fun getHue(): Int {
      val var10000: LocalPlayer = Minecraft.m_91087_().f_91074_;
      val var21: UUID = if (var10000 != null) var10000.m_20148_() else null;
      if (var21 == null) {
         return 16448250;
      } else {
         val playerUUID: UUID = var21;
         if (this.battlePokemon != null) {
            val var22: ClientBattleActor = this.battlePokemon.getActor();
            if (var22 != null) {
               val side: ClientBattleSide = var22.getSide();
               val battle: ClientBattle = var22.getSide().getBattle();
               val `$this$flatMap$iv`: Array<Any> = battle.getSides();
               var `destination$iv$iv`: java.util.Collection = new ArrayList();

               for (Object element$iv$iv : $this$flatMap$iv) {
                  CollectionsKt.addAll(`destination$iv$iv`, ((ClientBattleSide)`element$iv$iv`).getActors());
               }

               val var17: java.util.Iterator = (`destination$iv$iv` as java.util.List).iterator();

               while (true) {
                  if (var17.hasNext()) {
                     `destination$iv$iv` = (java.util.Collection)var17.next();
                     if (!((`destination$iv$iv` as ClientBattleActor).getUuid() == playerUUID)) {
                        continue;
                     }

                     var23 = `destination$iv$iv`;
                     break;
                  }

                  var23 = null;
                  break;
               }

               return if (var23 as ClientBattleActor != null)
                  (
                     if (!((var23 as ClientBattleActor).getSide() == side))
                        (if (side.getActors().indexOf(var22) == 0) 14956600 else 13982394)
                        else
                        (if (var22 == var23 as ClientBattleActor) 2724857 else 3457708)
                  )
                  else
                  (if (side == battle.getSide1()) 2724857 else 14956600);
            }
         }

         return 16448250;
      }
   }

   public fun animate(deltaTicks: Float) {
      val var10000: TileAnimation = this.animations.peek();
      if (var10000 != null) {
         if (var10000.invoke(this, deltaTicks) && (!var10000.shouldHoldUntilNextAnimation() || this.animations.size() > 1)) {
            this.animations.remove();
         }
      }
   }

   override fun getPNX(): java.lang.String {
      return Targetable.DefaultImpls.getPNX(this);
   }

   override fun getAdjacent(): MutableList<Targetable> {
      return Targetable.DefaultImpls.getAdjacent(this);
   }

   override fun getAdjacentAllies(): MutableList<Targetable> {
      return Targetable.DefaultImpls.getAdjacentAllies(this);
   }

   override fun getAdjacentOpponents(): MutableList<Targetable> {
      return Targetable.DefaultImpls.getAdjacentOpponents(this);
   }

   override fun getSignedDigitRelativeTo(other: Targetable): java.lang.String {
      return Targetable.DefaultImpls.getSignedDigitRelativeTo(this, other);
   }

   override fun getDigitRelativeTo(other: Targetable): Int {
      return Targetable.DefaultImpls.getDigitRelativeTo(this, other);
   }

   override fun getDigit(asAlly: Boolean): Int {
      return Targetable.DefaultImpls.getDigit(this, asAlly);
   }

   override fun getLetter(): Char {
      return Targetable.DefaultImpls.getLetter(this);
   }
}
