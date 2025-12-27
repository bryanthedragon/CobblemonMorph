package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.phys.Vec3

public class ActiveBattlePokemon(actor: BattleActor, battlePokemon: BattlePokemon? = null) : Targetable {
   public final val actor: BattleActor
   public final val battle: PokemonBattle
   public final var battlePokemon: BattlePokemon?
   public final var illusion: BattlePokemon?
   public final var position: Pair<ServerLevel, Vec3>?

   init {
      this.actor = actor;
      this.battlePokemon = battlePokemon;
      this.battle = this.actor.getBattle();
   }

   public fun getSide(): BattleSide {
      return this.actor.getSide();
   }

   public override fun getAllActivePokemon(): Iterable<ActiveBattlePokemon> {
      return this.battle.getActivePokemon();
   }

   public override fun getActorShowdownId(): String {
      return this.actor.getShowdownId();
   }

   public open fun getActorPokemon(): MutableList<ActiveBattlePokemon> {
      return this.actor.getActivePokemon();
   }

   public open fun getSidePokemon(): List<ActiveBattlePokemon> {
      return this.getSide().getActivePokemon();
   }

   public override fun getFormat(): BattleFormat {
      return this.battle.getFormat();
   }

   public override fun isAllied(other: Targetable): Boolean {
      return this.getSide() == (other as ActiveBattlePokemon).getSide();
   }

   public override fun hasPokemon(): Boolean {
      return this.battlePokemon != null;
   }

   public fun isGone(): Boolean {
      return this.battlePokemon == null || this.battlePokemon.getGone();
   }

   public fun isAlive(): Boolean {
      return (if (this.battlePokemon != null) this.battlePokemon.getHealth() else 0) > 0;
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
