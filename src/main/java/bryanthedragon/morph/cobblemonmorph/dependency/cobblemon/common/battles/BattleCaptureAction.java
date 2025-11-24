/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleCaptureAction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleCaptureEndPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleCaptureShakePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleCaptureStartPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0015\u001a\u00020\u0014\u0012\u0006\u0010\u000b\u001a\u00020\n\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004R\u0017\u0010\u0006\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\u0006\u0010\u0007\u001a\u0004\b\b\u0010\tR\u0017\u0010\u000b\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\u0010\u001a\u00020\u000f8\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013R\u0017\u0010\u0015\u001a\u00020\u00148\u0006\u00a2\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018\u00a8\u0006\u001b"}, d2={"Lcom/cobblemon/mod/common/battles/BattleCaptureAction;", "", "", "attach", "()V", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "getBattle", "()Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;", "pokeBallEntity", "Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;", "getPokeBallEntity", "()Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;", "Lnet/minecraft/network/chat/MutableComponent;", "pokemonName", "Lnet/minecraft/network/chat/MutableComponent;", "getPokemonName", "()Lnet/minecraft/network/chat/MutableComponent;", "Lcom/cobblemon/mod/common/battles/ActiveBattlePokemon;", "targetPokemon", "Lcom/cobblemon/mod/common/battles/ActiveBattlePokemon;", "getTargetPokemon", "()Lcom/cobblemon/mod/common/battles/ActiveBattlePokemon;", "<init>", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;Lcom/cobblemon/mod/common/battles/ActiveBattlePokemon;Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;)V", "common"})
public final class BattleCaptureAction {
    @NotNull
    private final PokemonBattle battle;
    @NotNull
    private final ActiveBattlePokemon targetPokemon;
    @NotNull
    private final EmptyPokeBallEntity pokeBallEntity;
    @NotNull
    private final MutableComponent pokemonName;

    public BattleCaptureAction(@NotNull PokemonBattle battle2, @NotNull ActiveBattlePokemon targetPokemon, @NotNull EmptyPokeBallEntity pokeBallEntity) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        Intrinsics.checkNotNullParameter((Object)targetPokemon, (String)"targetPokemon");
        Intrinsics.checkNotNullParameter((Object)pokeBallEntity, (String)"pokeBallEntity");
        this.battle = battle2;
        this.targetPokemon = targetPokemon;
        this.pokeBallEntity = pokeBallEntity;
        BattlePokemon battlePokemon = this.targetPokemon.getBattlePokemon();
        if (battlePokemon == null || (battlePokemon = battlePokemon.getName()) == null) {
            battlePokemon = TextKt.red("error");
        }
        this.pokemonName = battlePokemon;
    }

    @NotNull
    public final PokemonBattle getBattle() {
        return this.battle;
    }

    @NotNull
    public final ActiveBattlePokemon getTargetPokemon() {
        return this.targetPokemon;
    }

    @NotNull
    public final EmptyPokeBallEntity getPokeBallEntity() {
        return this.pokeBallEntity;
    }

    @NotNull
    public final MutableComponent getPokemonName() {
        return this.pokemonName;
    }

    public final void attach() {
        this.battle.sendUpdate(new BattleCaptureStartPacket(this.pokeBallEntity.getPokeBall().getName(), this.pokeBallEntity.getAspects(), this.targetPokemon.getPNX()));
        Observable.DefaultImpls.subscribe$default(this.pokeBallEntity.getDataTrackerEmitter().pipe(Observable.Companion.filter(attach.1.INSTANCE), Observable.Companion.emitWhile((Function1)new Function1<EntityDataAccessor<?>, Boolean>(this){
            final /* synthetic */ BattleCaptureAction this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @NotNull
            public final Boolean invoke(@NotNull EntityDataAccessor<?> it) {
                Intrinsics.checkNotNullParameter(it, (String)"it");
                return this.this$0.getPokeBallEntity().m_6084_() && this.this$0.getBattle().getCaptureActions().contains(this.this$0);
            }
        })), null, new Function1<EntityDataAccessor<?>, Unit>(this){
            final /* synthetic */ BattleCaptureAction this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            public final void invoke(@NotNull EntityDataAccessor<?> it) {
                Intrinsics.checkNotNullParameter(it, (String)"it");
                PokemonBattle pokemonBattle = this.this$0.getBattle();
                String string = this.this$0.getTargetPokemon().getPNX();
                Object object = this.this$0.getPokeBallEntity().m_20088_().m_135370_(EmptyPokeBallEntity.Companion.getSHAKE());
                Intrinsics.checkNotNullExpressionValue((Object)object, (String)"pokeBallEntity.dataTrack\u2026mptyPokeBallEntity.SHAKE)");
                pokemonBattle.sendUpdate(new BattleCaptureShakePacket(string, (Boolean)object));
            }
        }, 1, null);
        this.pokeBallEntity.getCaptureFuture().thenAccept(arg_0 -> BattleCaptureAction.attach$lambda$0((Function1)new Function1<Boolean, Unit>(this){
            final /* synthetic */ BattleCaptureAction this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            public final void invoke(Boolean successful) {
                Intrinsics.checkNotNullExpressionValue((Object)successful, (String)"successful");
                if (successful.booleanValue()) {
                    BattlePokemon battlePokemon = this.this$0.getTargetPokemon().getBattlePokemon();
                    if (battlePokemon != null) {
                        battlePokemon.setGone(true);
                    }
                    this.this$0.getBattle().dispatchWaiting(2.0f, (Function0<Unit>)((Function0)new Function0<Unit>(this.this$0){
                        final /* synthetic */ BattleCaptureAction this$0;
                        {
                            this.this$0 = $receiver;
                            super(0);
                        }

                        public final void invoke() {
                            PokemonBattle pokemonBattle = this.this$0.getBattle();
                            Object[] objectArray = new Object[]{this.this$0.getPokemonName()};
                            MutableComponent mutableComponent = LocalizationUtilsKt.lang("capture.succeeded", objectArray);
                            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"capture.succeeded\", pokemonName)");
                            pokemonBattle.broadcastChatMessage((Component)TextKt.green(mutableComponent));
                        }
                    }));
                    String[] stringArray = new String[]{">capture " + this.this$0.getTargetPokemon().getPNX()};
                    this.this$0.getBattle().writeShowdownAction(stringArray);
                } else {
                    this.this$0.getBattle().dispatchWaiting(2.0f, (Function0<Unit>)((Function0)new Function0<Unit>(this.this$0){
                        final /* synthetic */ BattleCaptureAction this$0;
                        {
                            this.this$0 = $receiver;
                            super(0);
                        }

                        public final void invoke() {
                            PokemonBattle pokemonBattle = this.this$0.getBattle();
                            Object[] objectArray = new Object[]{this.this$0.getPokemonName()};
                            MutableComponent mutableComponent = LocalizationUtilsKt.lang("capture.broke_free", objectArray);
                            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"capture.broke_free\", pokemonName)");
                            pokemonBattle.broadcastChatMessage((Component)TextKt.red(mutableComponent));
                        }
                    }));
                }
                this.this$0.getBattle().sendUpdate(new BattleCaptureEndPacket(this.this$0.getTargetPokemon().getPNX(), successful));
                this.this$0.getBattle().finishCaptureAction(this.this$0);
            }
        }, arg_0));
    }

    private static final void attach$lambda$0(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke(p0);
    }
}

