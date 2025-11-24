/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SettableObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ClientTaskTracker;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.pokeball.PokeBallPoseableState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.PokeBall;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0013\u001a\u00020\u0012\u0012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\f\u00a2\u0006\u0004\b,\u0010-J\u0011\u0010\u0003\u001a\u0004\u0018\u00010\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J\r\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0017\u0010\n\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\n\u0010\u000bR\u001d\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\f8\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0013\u001a\u00020\u00128\u0006\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016R\"\u0010\u0017\u001a\u00020\b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u000bR\u001a\u0010\u001d\u001a\u00020\u001c8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 R \u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00050!8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%R \u0010(\u001a\b\u0012\u0004\u0012\u00020'0&8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b(\u0010)\u001a\u0004\b*\u0010+\u00a8\u0006."}, d2={"Lcom/cobblemon/mod/common/client/battle/ClientBallDisplay;", "Lcom/cobblemon/mod/common/client/render/pokeball/PokeBallPoseableState;", "", "getEntity", "()Ljava/lang/Void;", "", "start", "()V", "", "partialTicks", "updatePartialTicks", "(F)V", "", "", "aspects", "Ljava/util/Set;", "getAspects", "()Ljava/util/Set;", "Lcom/cobblemon/mod/common/pokeball/PokeBall;", "pokeBall", "Lcom/cobblemon/mod/common/pokeball/PokeBall;", "getPokeBall", "()Lcom/cobblemon/mod/common/pokeball/PokeBall;", "scale", "F", "getScale", "()F", "setScale", "Lcom/cobblemon/mod/common/api/scheduling/ClientTaskTracker;", "schedulingTracker", "Lcom/cobblemon/mod/common/api/scheduling/ClientTaskTracker;", "getSchedulingTracker", "()Lcom/cobblemon/mod/common/api/scheduling/ClientTaskTracker;", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "shakeEmitter", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "getShakeEmitter", "()Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "Lcom/cobblemon/mod/common/api/reactive/SettableObservable;", "Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity$CaptureState;", "stateEmitter", "Lcom/cobblemon/mod/common/api/reactive/SettableObservable;", "getStateEmitter", "()Lcom/cobblemon/mod/common/api/reactive/SettableObservable;", "<init>", "(Lcom/cobblemon/mod/common/pokeball/PokeBall;Ljava/util/Set;)V", "common"})
public final class ClientBallDisplay
extends PokeBallPoseableState {
    @NotNull
    private final PokeBall pokeBall;
    @NotNull
    private final Set<String> aspects;
    @NotNull
    private final SettableObservable<EmptyPokeBallEntity.CaptureState> stateEmitter;
    @NotNull
    private final SimpleObservable<Unit> shakeEmitter;
    @NotNull
    private final ClientTaskTracker schedulingTracker;
    private float scale;

    public ClientBallDisplay(@NotNull PokeBall pokeBall, @NotNull Set<String> aspects) {
        Intrinsics.checkNotNullParameter((Object)pokeBall, (String)"pokeBall");
        Intrinsics.checkNotNullParameter(aspects, (String)"aspects");
        this.pokeBall = pokeBall;
        this.aspects = aspects;
        this.stateEmitter = new SettableObservable<EmptyPokeBallEntity.CaptureState>(EmptyPokeBallEntity.CaptureState.FALL);
        this.shakeEmitter = new SimpleObservable();
        this.schedulingTracker = ClientTaskTracker.INSTANCE;
        this.scale = 1.0f;
    }

    @NotNull
    public final PokeBall getPokeBall() {
        return this.pokeBall;
    }

    @NotNull
    public final Set<String> getAspects() {
        return this.aspects;
    }

    @Override
    @NotNull
    public SettableObservable<EmptyPokeBallEntity.CaptureState> getStateEmitter() {
        return this.stateEmitter;
    }

    @NotNull
    public SimpleObservable<Unit> getShakeEmitter() {
        return this.shakeEmitter;
    }

    @Override
    @NotNull
    public ClientTaskTracker getSchedulingTracker() {
        return this.schedulingTracker;
    }

    @Override
    @Nullable
    public Void getEntity() {
        return null;
    }

    @Override
    public void updatePartialTicks(float partialTicks) {
        this.setCurrentPartialTicks(this.getCurrentPartialTicks() + partialTicks);
    }

    public final float getScale() {
        return this.scale;
    }

    public final void setScale(float f) {
        this.scale = f;
    }

    public final void start() {
        this.initSubscriptions();
        this.after(1.0f, (Function0<Unit>)((Function0)new Function0<Unit>(this){
            final /* synthetic */ ClientBallDisplay this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            public final void invoke() {
                this.this$0.lerp(0.3f, (Function1<Float, Unit>)((Function1)new Function1<Float, Unit>(this.this$0){
                    final /* synthetic */ ClientBallDisplay this$0;
                    {
                        this.this$0 = $receiver;
                        super(1);
                    }

                    public final void invoke(float it) {
                        this.this$0.setScale(1.0f - it);
                    }
                }));
                this.this$0.after(0.3f, (Function0<Unit>)((Function0)new Function0<Unit>(this.this$0){
                    final /* synthetic */ ClientBallDisplay this$0;
                    {
                        this.this$0 = $receiver;
                        super(0);
                    }

                    public final void invoke() {
                        this.this$0.getStateEmitter().set(EmptyPokeBallEntity.CaptureState.SHAKE);
                        this.this$0.lerp(0.3f, (Function1<Float, Unit>)((Function1)new Function1<Float, Unit>(this.this$0){
                            final /* synthetic */ ClientBallDisplay this$0;
                            {
                                this.this$0 = $receiver;
                                super(1);
                            }

                            public final void invoke(float it) {
                                this.this$0.setScale(it);
                            }
                        }));
                    }
                }));
            }
        }));
    }
}

