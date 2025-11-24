/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.random.Random
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.pokeball;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SettableObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Transform;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.Schedulable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokeball.AncientPokeBallModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokeball.PokeBallModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.RenderContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.pokeball.PokeBallPoseableState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u0007\u00a2\u0006\u0004\b\u0014\u0010\u0006J\u000f\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0014\u0010\n\u001a\u00020\u00078BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\tR\u001a\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00040\u000b8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\rR\u001a\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006\u0015"}, d2={"Lcom/cobblemon/mod/common/client/render/pokeball/PokeBallPoseableState;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;", "Lcom/cobblemon/mod/common/api/scheduling/Schedulable;", "", "initSubscriptions", "()V", "", "getGroup", "()Ljava/lang/String;", "group", "Lcom/cobblemon/mod/common/api/reactive/Observable;", "getShakeEmitter", "()Lcom/cobblemon/mod/common/api/reactive/Observable;", "shakeEmitter", "Lcom/cobblemon/mod/common/api/reactive/SettableObservable;", "Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity$CaptureState;", "getStateEmitter", "()Lcom/cobblemon/mod/common/api/reactive/SettableObservable;", "stateEmitter", "<init>", "common"})
public abstract class PokeBallPoseableState
extends PoseableEntityState<EmptyPokeBallEntity>
implements Schedulable {
    @NotNull
    public abstract SettableObservable<EmptyPokeBallEntity.CaptureState> getStateEmitter();

    @NotNull
    public abstract Observable<Unit> getShakeEmitter();

    private final String getGroup() {
        return this.getCurrentModel() instanceof AncientPokeBallModel ? "ancient_poke_ball" : "poke_ball";
    }

    public void initSubscriptions() {
        Observable.DefaultImpls.subscribe$default(this.getStateEmitter(), null, (Function1)new Function1<EmptyPokeBallEntity.CaptureState, Unit>(this){
            final /* synthetic */ PokeBallPoseableState this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            public final void invoke(@NotNull EmptyPokeBallEntity.CaptureState state) {
                Intrinsics.checkNotNullParameter((Object)((Object)state), (String)"state");
                switch (initSubscriptions.WhenMappings.$EnumSwitchMapping$0[state.ordinal()]) {
                    case 1: {
                        this.this$0.doLater((Function0<Unit>)((Function0)new Function0<Unit>(this.this$0){
                            final /* synthetic */ PokeBallPoseableState this$0;
                            {
                                this.this$0 = $receiver;
                                super(0);
                            }

                            public final void invoke() {
                                PoseableEntityModel<EmptyPokeBallEntity> poseableEntityModel = this.this$0.getCurrentModel();
                                Intrinsics.checkNotNull(poseableEntityModel);
                                PoseableEntityModel<EmptyPokeBallEntity> model = poseableEntityModel;
                                this.this$0.after(0.2f, (Function0<Unit>)((Function0)new Function0<Unit>(model, this.this$0){
                                    final /* synthetic */ PoseableEntityModel<EmptyPokeBallEntity> $model;
                                    final /* synthetic */ PokeBallPoseableState this$0;
                                    {
                                        this.$model = $model;
                                        this.this$0 = $receiver;
                                        super(0);
                                    }

                                    public final void invoke() {
                                        if (this.$model instanceof PokeBallModel && this.this$0.getStateEmitter().get() == EmptyPokeBallEntity.CaptureState.HIT) {
                                            this.this$0.doLater((Function0<Unit>)((Function0)new Function0<Unit>(this.$model, this.this$0){
                                                final /* synthetic */ PoseableEntityModel<EmptyPokeBallEntity> $model;
                                                final /* synthetic */ PokeBallPoseableState this$0;
                                                {
                                                    this.$model = $model;
                                                    this.this$0 = $receiver;
                                                    super(0);
                                                }

                                                public final void invoke() {
                                                    EmptyPokeBallEntity emptyPokeBallEntity = (EmptyPokeBallEntity)this.$model.getContext().request(RenderContext.Companion.getENTITY());
                                                    if (emptyPokeBallEntity == null) {
                                                        return;
                                                    }
                                                    EmptyPokeBallEntity entity2 = emptyPokeBallEntity;
                                                    this.$model.moveToPose((EmptyPokeBallEntity)((Entity)entity2), this.this$0, ((PokeBallModel)this.$model).getOpen());
                                                    this.this$0.after(1.75f, (Function0<Unit>)((Function0)new Function0<Unit>(this.$model, entity2, this.this$0){
                                                        final /* synthetic */ PoseableEntityModel<EmptyPokeBallEntity> $model;
                                                        final /* synthetic */ EmptyPokeBallEntity $entity;
                                                        final /* synthetic */ PokeBallPoseableState this$0;
                                                        {
                                                            this.$model = $model;
                                                            this.$entity = $entity;
                                                            this.this$0 = $receiver;
                                                            super(0);
                                                        }

                                                        public final void invoke() {
                                                            this.$model.moveToPose((EmptyPokeBallEntity)((Entity)this.$entity), this.this$0, ((PokeBallModel)this.$model).getShut());
                                                        }
                                                    }));
                                                }
                                            }));
                                        }
                                    }
                                }));
                            }
                        }));
                        break;
                    }
                    case 2: {
                        break;
                    }
                    case 3: {
                        this.this$0.doLater((Function0<Unit>)((Function0)new Function0<Unit>(this.this$0){
                            final /* synthetic */ PokeBallPoseableState this$0;
                            {
                                this.this$0 = $receiver;
                                super(0);
                            }

                            public final void invoke() {
                                StatefulAnimation[] statefulAnimationArray = new StatefulAnimation[1];
                                PoseableEntityModel<T> poseableEntityModel = this.this$0.getCurrentModel();
                                Intrinsics.checkNotNull(poseableEntityModel);
                                statefulAnimationArray[0] = PoseableEntityModel.bedrockStateful$default(poseableEntityModel, PokeBallPoseableState.access$getGroup(this.this$0), "bounce", null, 4, null);
                                this.this$0.setStatefulAnimations(statefulAnimationArray);
                            }
                        }));
                        Observable.DefaultImpls.subscribe$default(this.this$0.getShakeEmitter().pipe((Transform)Observable.Companion.emitWhile((Function1)new Function1<Unit, Boolean>(this.this$0){
                            final /* synthetic */ PokeBallPoseableState this$0;
                            {
                                this.this$0 = $receiver;
                                super(1);
                            }

                            @NotNull
                            public final Boolean invoke(@NotNull Unit it) {
                                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                                return this.this$0.getStateEmitter().get() == EmptyPokeBallEntity.CaptureState.SHAKE;
                            }
                        })), null, (Function1)new Function1<Unit, Unit>(this.this$0){
                            final /* synthetic */ PokeBallPoseableState this$0;
                            {
                                this.this$0 = $receiver;
                                super(1);
                            }

                            public final void invoke(@NotNull Unit it) {
                                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                                String bob = "bob" + (Random.Default.nextInt(6) + 1);
                                this.this$0.doLater((Function0<Unit>)((Function0)new Function0<Unit>(this.this$0, bob){
                                    final /* synthetic */ PokeBallPoseableState this$0;
                                    final /* synthetic */ String $bob;
                                    {
                                        this.this$0 = $receiver;
                                        this.$bob = $bob;
                                        super(0);
                                    }

                                    public final void invoke() {
                                        StatefulAnimation[] statefulAnimationArray = new StatefulAnimation[1];
                                        PoseableEntityModel<T> poseableEntityModel = this.this$0.getCurrentModel();
                                        Intrinsics.checkNotNull(poseableEntityModel);
                                        statefulAnimationArray[0] = PoseableEntityModel.bedrockStateful$default(poseableEntityModel, PokeBallPoseableState.access$getGroup(this.this$0), this.$bob, null, 4, null);
                                        this.this$0.setStatefulAnimations(statefulAnimationArray);
                                    }
                                }));
                            }
                        }, 1, null);
                        break;
                    }
                    case 4: {
                        this.this$0.doLater((Function0<Unit>)((Function0)new Function0<Unit>(this.this$0){
                            final /* synthetic */ PokeBallPoseableState this$0;
                            {
                                this.this$0 = $receiver;
                                super(0);
                            }

                            public final void invoke() {
                                StatefulAnimation[] statefulAnimationArray = new StatefulAnimation[1];
                                PoseableEntityModel<T> poseableEntityModel = this.this$0.getCurrentModel();
                                Intrinsics.checkNotNull(poseableEntityModel);
                                statefulAnimationArray[0] = PoseableEntityModel.bedrockStateful$default(poseableEntityModel, PokeBallPoseableState.access$getGroup(this.this$0), "capture", null, 4, null);
                                this.this$0.setStatefulAnimations(statefulAnimationArray);
                            }
                        }));
                        break;
                    }
                    case 5: {
                        this.this$0.doLater((Function0<Unit>)((Function0)new Function0<Unit>(this.this$0){
                            final /* synthetic */ PokeBallPoseableState this$0;
                            {
                                this.this$0 = $receiver;
                                super(0);
                            }

                            public final void invoke() {
                                StatefulAnimation[] statefulAnimationArray = new StatefulAnimation[1];
                                PoseableEntityModel<T> poseableEntityModel = this.this$0.getCurrentModel();
                                Intrinsics.checkNotNull(poseableEntityModel);
                                statefulAnimationArray[0] = PoseableEntityModel.bedrockStateful$default(poseableEntityModel, PokeBallPoseableState.access$getGroup(this.this$0), "critical", null, 4, null);
                                this.this$0.setStatefulAnimations(statefulAnimationArray);
                            }
                        }));
                        break;
                    }
                    case 6: {
                        this.this$0.doLater((Function0<Unit>)((Function0)new Function0<Unit>(this.this$0){
                            final /* synthetic */ PokeBallPoseableState this$0;
                            {
                                this.this$0 = $receiver;
                                super(0);
                            }

                            public final void invoke() {
                                StatefulAnimation[] statefulAnimationArray = new StatefulAnimation[1];
                                PoseableEntityModel<T> poseableEntityModel = this.this$0.getCurrentModel();
                                Intrinsics.checkNotNull(poseableEntityModel);
                                statefulAnimationArray[0] = PoseableEntityModel.bedrockStateful$default(poseableEntityModel, PokeBallPoseableState.access$getGroup(this.this$0), "break", null, 4, null);
                                this.this$0.setStatefulAnimations(statefulAnimationArray);
                            }
                        }));
                    }
                }
            }
        }, 1, null);
    }

    public static final /* synthetic */ String access$getGroup(PokeBallPoseableState $this) {
        return $this.getGroup();
    }
}

