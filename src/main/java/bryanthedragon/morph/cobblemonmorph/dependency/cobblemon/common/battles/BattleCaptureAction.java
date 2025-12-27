package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Transform
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleCaptureShakePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleCaptureStartPacket
import kotlin.jvm.functions.Function1
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.syncher.EntityDataAccessor
import org.jetbrains.annotations.NotNull

public class BattleCaptureAction(battle: PokemonBattle, targetPokemon: ActiveBattlePokemon, pokeBallEntity: EmptyPokeBallEntity) {
   public final val battle: PokemonBattle
   public final val pokeBallEntity: EmptyPokeBallEntity
   public final val pokemonName: MutableComponent
   public final val targetPokemon: ActiveBattlePokemon

   init {
      var var4: MutableComponent;
      label11: {
         super();
         this.battle = battle;
         this.targetPokemon = targetPokemon;
         this.pokeBallEntity = pokeBallEntity;
         val var10001: BattlePokemon = this.targetPokemon.getBattlePokemon();
         if (var10001 != null) {
            var4 = var10001.getName();
            if (var4 != null) {
               break label11;
            }
         }

         var4 = TextKt.red("error");
      }

      this.pokemonName = var4;
   }

   public fun attach() {
      this.battle
         .sendUpdate(new BattleCaptureStartPacket(this.pokeBallEntity.getPokeBall().getName(), this.pokeBallEntity.getAspects(), this.targetPokemon.getPNX()));
      Observable.DefaultImpls.subscribe$default(
         this.pokeBallEntity
            .getDataTrackerEmitter()
            .pipe(
               Observable.Companion.filter(<unrepresentable>.INSTANCE) as Transform<EntityDataAccessor<?>, EntityDataAccessor<?>>,
               Observable.Companion.emitWhile((new Function1<EntityDataAccessor<?>, java.lang.Boolean>(this) {
                  {
                     super(1);
                     this.this$0 = `$receiver`;
                  }

                  @NotNull
                  public final java.lang.Boolean invoke(@NotNull EntityDataAccessor<?> it) {
                     return this.this$0.getPokeBallEntity().m_6084_() && this.this$0.getBattle().getCaptureActions().contains(this.this$0);
                  }
               }) as (EntityDataAccessor<?>?) -> java.lang.Boolean) as Transform<EntityDataAccessor<?>, EntityDataAccessor<?>>
            ),
         null,
         (new Function1<EntityDataAccessor<?>, Unit>(this) {
            {
               super(1);
               this.this$0 = `$receiver`;
            }

            public final void invoke(@NotNull EntityDataAccessor<?> it) {
               val var10000: PokemonBattle = this.this$0.getBattle();
               val var10003: java.lang.String = this.this$0.getTargetPokemon().getPNX();
               val var10004: Any = this.this$0.getPokeBallEntity().m_20088_().m_135370_(EmptyPokeBallEntity.Companion.getSHAKE());
               var10000.sendUpdate(new BattleCaptureShakePacket(var10003, var10004 as java.lang.Boolean));
            }
         }) as Function1,
         1,
         null
      );
      this.pokeBallEntity.getCaptureFuture().thenAccept(BattleCaptureAction::attach$lambda$0);
   }

   @JvmStatic
   fun `attach$lambda$0`(`$tmp0`: Function1, p0: Any) {
      `$tmp0`.invoke(p0);
   }
}
