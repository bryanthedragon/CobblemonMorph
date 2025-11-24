/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResult;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResultKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.UntilDispatch;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.effects.EffectTracker;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.effects.TransformEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleTransformPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0010\u001a\u00020\u000f\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0018\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0096\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0007\u001a\u0004\b\b\u0010\tR\u0019\u0010\u000b\u001a\u0004\u0018\u00010\n8\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\u0010\u001a\u00020\u000f8\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0016"}, d2={"Lcom/cobblemon/mod/common/battles/interpreter/instructions/TransformInstruction;", "Lcom/cobblemon/mod/common/battles/dispatch/InterpreterInstruction;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "", "invoke", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)V", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "getBattle", "()Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "expectedTarget", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "getExpectedTarget", "()Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "message", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "getMessage", "()Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "<init>", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;)V", "common"})
public final class TransformInstruction
implements InterpreterInstruction {
    @NotNull
    private final PokemonBattle battle;
    @NotNull
    private final BattleMessage message;
    @Nullable
    private final BattlePokemon expectedTarget;

    public TransformInstruction(@NotNull PokemonBattle battle2, @NotNull BattleMessage message) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        Intrinsics.checkNotNullParameter((Object)message, (String)"message");
        this.battle = battle2;
        this.message = message;
        this.expectedTarget = this.message.battlePokemon(0, this.battle);
    }

    @NotNull
    public final PokemonBattle getBattle() {
        return this.battle;
    }

    @NotNull
    public final BattleMessage getMessage() {
        return this.message;
    }

    @Nullable
    public final BattlePokemon getExpectedTarget() {
        return this.expectedTarget;
    }

    @Override
    public void invoke(@NotNull PokemonBattle battle2) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        Pair<String, String> pair = this.message.pnxAndUuid(0);
        if (pair == null) {
            return;
        }
        String pnx = (String)pair.component1();
        BattleActor actor = (BattleActor)battle2.getActorAndActiveSlotFromPNX(pnx).component1();
        BattlePokemon battlePokemon = this.message.battlePokemon(0, battle2);
        if (battlePokemon == null) {
            return;
        }
        BattlePokemon pokemon = battlePokemon;
        BattlePokemon battlePokemon2 = this.message.battlePokemon(1, battle2);
        if (battlePokemon2 == null) {
            return;
        }
        BattlePokemon targetPokemon = battlePokemon2;
        Effect effect = BattleMessage.effect$default(this.message, null, 1, null);
        ShowdownInterpreter.INSTANCE.broadcastOptionalAbility(battle2, effect, pokemon);
        battle2.dispatch((Function0<? extends DispatchResult>)((Function0)new Function0<DispatchResult>(pokemon, targetPokemon, battle2){
            final /* synthetic */ BattlePokemon $pokemon;
            final /* synthetic */ BattlePokemon $targetPokemon;
            final /* synthetic */ PokemonBattle $battle;
            {
                this.$pokemon = $pokemon;
                this.$targetPokemon = $targetPokemon;
                this.$battle = $battle;
                super(0);
            }

            @NotNull
            public final DispatchResult invoke() {
                PokemonEntity pokemonEntity = this.$pokemon.getEntity();
                if (pokemonEntity == null) {
                    return DispatchResultKt.getGO();
                }
                PokemonEntity entity2 = pokemonEntity;
                CompletableFuture<PokemonEntity> future2 = new TransformEffect(this.$targetPokemon.getEffectedPokemon(), this.$battle.getStarted()).start(entity2);
                return new UntilDispatch((Function0<Boolean>)((Function0)new Function0<Boolean>(future2){
                    final /* synthetic */ CompletableFuture<PokemonEntity> $future;
                    {
                        this.$future = $future;
                        super(0);
                    }

                    @NotNull
                    public final Boolean invoke() {
                        CompletableFuture<PokemonEntity> completableFuture = this.$future;
                        return !(completableFuture != null ? !completableFuture.isDone() : false);
                    }
                }));
            }
        }));
        PokemonBattle.dispatchWaiting$default(battle2, 0.0f, (Function0)new Function0<Unit>(pokemon, targetPokemon, battle2, this, actor, pnx){
            final /* synthetic */ BattlePokemon $pokemon;
            final /* synthetic */ BattlePokemon $targetPokemon;
            final /* synthetic */ PokemonBattle $battle;
            final /* synthetic */ TransformInstruction this$0;
            final /* synthetic */ BattleActor $actor;
            final /* synthetic */ String $pnx;
            {
                this.$pokemon = $pokemon;
                this.$targetPokemon = $targetPokemon;
                this.$battle = $battle;
                this.this$0 = $receiver;
                this.$actor = $actor;
                this.$pnx = $pnx;
                super(0);
            }

            public final void invoke() {
                Object[] objectArray;
                Object object = this.$pokemon.getEntity();
                PokemonProperties mock = object != null && (object = ((PokemonEntity)object).getEffects()) != null && (object = ((EffectTracker)object).getMockEffect()) != null ? object.getMock() : null;
                MutableComponent pokemonName = this.$pokemon.getName();
                MutableComponent targetPokemonName = this.$targetPokemon.getName();
                PokemonProperties pokemonProperties = mock;
                if (pokemonProperties != null) {
                    PokemonProperties pokemonProperties2 = pokemonProperties;
                    objectArray = this.$battle;
                    BattleActor battleActor = this.$actor;
                    String string = this.$pnx;
                    BattlePokemon battlePokemon = this.$pokemon;
                    PokemonProperties it = pokemonProperties2;
                    boolean bl = false;
                    PokemonBattle.sendSidedUpdate$default((PokemonBattle)objectArray, battleActor, new BattleTransformPokemonPacket(string, battlePokemon, it, true), new BattleTransformPokemonPacket(string, battlePokemon, it, false), false, 8, null);
                }
                objectArray = new Object[]{pokemonName, targetPokemonName};
                MutableComponent lang = LocalizationUtilsKt.battleLang("transform", objectArray);
                Intrinsics.checkNotNullExpressionValue((Object)lang, (String)"lang");
                this.$battle.broadcastChatMessage((Component)lang);
                ((Map)this.$battle.getMinorBattleActions()).put(this.$pokemon.getUuid(), this.this$0.getMessage());
            }
        }, 1, null);
    }
}

