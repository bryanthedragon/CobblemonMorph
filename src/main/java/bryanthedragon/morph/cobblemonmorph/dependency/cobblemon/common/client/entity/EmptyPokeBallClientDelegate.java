/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.Unit
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangEnvironment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoParams;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.DoubleValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.EntitySideDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SettableObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingTracker;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.pokeball.PokeBallPoseableState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B\u0007\u00a2\u0006\u0004\b&\u0010'J\u000f\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0017\u0010\b\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ\u001b\u0010\f\u001a\u00020\u00072\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\nH\u0016\u00a2\u0006\u0004\b\f\u0010\rJ\u0017\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u000e\u0010\tJ\u0017\u0010\u0011\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u000fH\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0012R\"\u0010\u0013\u001a\u00020\u00038\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0005\"\u0004\b\u0016\u0010\tR\u0014\u0010\u001a\u001a\u00020\u00178VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R \u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00070\u001b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001fR \u0010\"\u001a\b\u0012\u0004\u0012\u00020!0 8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%\u00a8\u0006("}, d2={"Lcom/cobblemon/mod/common/client/entity/EmptyPokeBallClientDelegate;", "Lcom/cobblemon/mod/common/client/render/pokeball/PokeBallPoseableState;", "Lcom/cobblemon/mod/common/api/entity/EntitySideDelegate;", "Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;", "getEntity", "()Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;", "entity", "", "initialize", "(Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;)V", "Lnet/minecraft/network/syncher/EntityDataAccessor;", "data", "onTrackedDataSet", "(Lnet/minecraft/network/syncher/EntityDataAccessor;)V", "tick", "", "partialTicks", "updatePartialTicks", "(F)V", "currentEntity", "Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;", "getCurrentEntity", "setCurrentEntity", "Lcom/cobblemon/mod/common/api/scheduling/SchedulingTracker;", "getSchedulingTracker", "()Lcom/cobblemon/mod/common/api/scheduling/SchedulingTracker;", "schedulingTracker", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "shakeEmitter", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "getShakeEmitter", "()Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "Lcom/cobblemon/mod/common/api/reactive/SettableObservable;", "Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity$CaptureState;", "stateEmitter", "Lcom/cobblemon/mod/common/api/reactive/SettableObservable;", "getStateEmitter", "()Lcom/cobblemon/mod/common/api/reactive/SettableObservable;", "<init>", "()V", "common"})
public final class EmptyPokeBallClientDelegate
extends PokeBallPoseableState
implements EntitySideDelegate<EmptyPokeBallEntity> {
    @NotNull
    private final SettableObservable<EmptyPokeBallEntity.CaptureState> stateEmitter = new SettableObservable<EmptyPokeBallEntity.CaptureState>(EmptyPokeBallEntity.CaptureState.NOT);
    @NotNull
    private final SimpleObservable<Unit> shakeEmitter = new SimpleObservable();
    public EmptyPokeBallEntity currentEntity;

    @Override
    @NotNull
    public SettableObservable<EmptyPokeBallEntity.CaptureState> getStateEmitter() {
        return this.stateEmitter;
    }

    @NotNull
    public SimpleObservable<Unit> getShakeEmitter() {
        return this.shakeEmitter;
    }

    @NotNull
    public final EmptyPokeBallEntity getCurrentEntity() {
        EmptyPokeBallEntity emptyPokeBallEntity = this.currentEntity;
        if (emptyPokeBallEntity != null) {
            return emptyPokeBallEntity;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"currentEntity");
        return null;
    }

    public final void setCurrentEntity(@NotNull EmptyPokeBallEntity emptyPokeBallEntity) {
        Intrinsics.checkNotNullParameter((Object)emptyPokeBallEntity, (String)"<set-?>");
        this.currentEntity = emptyPokeBallEntity;
    }

    @Override
    @NotNull
    public SchedulingTracker getSchedulingTracker() {
        return this.getEntity().getSchedulingTracker();
    }

    @Override
    @NotNull
    public EmptyPokeBallEntity getEntity() {
        return this.getCurrentEntity();
    }

    @Override
    public void updatePartialTicks(float partialTicks) {
        this.setCurrentPartialTicks(partialTicks);
    }

    @Override
    public void initialize(@NotNull EmptyPokeBallEntity entity2) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        this.setCurrentEntity(entity2);
        this.setAge(entity2.f_19797_);
        this.initSubscriptions();
        MoLangEnvironment moLangEnvironment = this.getRuntime().getEnvironment();
        Intrinsics.checkNotNullExpressionValue((Object)moLangEnvironment, (String)"this.runtime.environment");
        MoLangFunctions.INSTANCE.addFunctions(MoLangFunctions.getQueryStruct$default(MoLangFunctions.INSTANCE, moLangEnvironment, null, 1, null), MapsKt.mapOf((Pair)TuplesKt.to((Object)"pokeball_type", arg_0 -> EmptyPokeBallClientDelegate.initialize$lambda$0(this, arg_0))));
    }

    @Override
    public void tick(@NotNull EmptyPokeBallEntity entity2) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        EntitySideDelegate.DefaultImpls.tick(this, (Entity)entity2);
        Vec3 vec3 = entity2.m_20182_();
        Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"entity.pos");
        this.updateLocatorPosition(vec3);
        this.incrementAge((Entity)entity2);
    }

    @Override
    public void onTrackedDataSet(@NotNull EntityDataAccessor<?> data) {
        Intrinsics.checkNotNullParameter(data, (String)"data");
        EntitySideDelegate.DefaultImpls.onTrackedDataSet(this, data);
        EntityDataAccessor<?> entityDataAccessor = data;
        if (Intrinsics.areEqual(entityDataAccessor, EmptyPokeBallEntity.Companion.getCAPTURE_STATE())) {
            this.getStateEmitter().set(this.getCurrentEntity().getCaptureState());
        } else if (Intrinsics.areEqual(entityDataAccessor, EmptyPokeBallEntity.Companion.getSHAKE())) {
            Unit[] unitArray = new Unit[]{Unit.INSTANCE};
            ((SimpleObservable)this.getShakeEmitter()).emit(unitArray);
        }
    }

    private static final Object initialize$lambda$0(EmptyPokeBallClientDelegate this$0, MoParams it) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
        return new DoubleValue(this$0.getCurrentEntity().getPokeBall().getName().toString());
    }
}

