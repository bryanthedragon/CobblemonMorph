/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.pokemon.MocKEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleReplacePokemonPacket;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\f\u0010\rJ\u0018\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0096\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/battles/interpreter/instructions/ReplaceInstruction;", "Lcom/cobblemon/mod/common/battles/dispatch/InterpreterInstruction;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "", "invoke", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)V", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "message", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "getMessage", "()Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "<init>", "(Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;)V", "common"})
public final class ReplaceInstruction
implements InterpreterInstruction {
    @NotNull
    private final BattleMessage message;

    public ReplaceInstruction(@NotNull BattleMessage message) {
        Intrinsics.checkNotNullParameter((Object)message, (String)"message");
        this.message = message;
    }

    @NotNull
    public final BattleMessage getMessage() {
        return this.message;
    }

    @Override
    public void invoke(@NotNull PokemonBattle battle2) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        Pair<String, String> pair = this.message.pnxAndUuid(0);
        if (pair == null) {
            return;
        }
        String pnx = (String)pair.component1();
        Pair<BattleActor, ActiveBattlePokemon> pair2 = battle2.getActorAndActiveSlotFromPNX(pnx);
        BattleActor actor = (BattleActor)pair2.component1();
        ActiveBattlePokemon activePokemon = (ActiveBattlePokemon)pair2.component2();
        BattlePokemon battlePokemon = this.message.battlePokemon(0, battle2);
        if (battlePokemon == null) {
            return;
        }
        BattlePokemon pokemon = battlePokemon;
        battle2.dispatchGo((Function0<Unit>)((Function0)new Function0<Unit>(pokemon, battle2, actor, pnx, activePokemon){
            final /* synthetic */ BattlePokemon $pokemon;
            final /* synthetic */ PokemonBattle $battle;
            final /* synthetic */ BattleActor $actor;
            final /* synthetic */ String $pnx;
            final /* synthetic */ ActiveBattlePokemon $activePokemon;
            {
                this.$pokemon = $pokemon;
                this.$battle = $battle;
                this.$actor = $actor;
                this.$pnx = $pnx;
                this.$activePokemon = $activePokemon;
                super(0);
            }

            public final void invoke() {
                PokemonEntity entity2;
                PokemonEntity pokemonEntity = entity2 = this.$pokemon.getEntity();
                if (pokemonEntity != null) {
                    PokemonEntity it = pokemonEntity;
                    boolean bl = false;
                    MocKEffect mocKEffect = it.getEffects().getMockEffect();
                    if (mocKEffect != null) {
                        mocKEffect.end(it);
                    }
                }
                PokemonBattle.sendSidedUpdate$default(this.$battle, this.$actor, new BattleReplacePokemonPacket(this.$pnx, this.$pokemon, true), new BattleReplacePokemonPacket(this.$pnx, this.$pokemon, false), false, 8, null);
                this.$activePokemon.setIllusion(null);
            }
        }));
    }
}

