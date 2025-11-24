/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.EntitySideDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingTracker;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.GenericBedrockEntityModelRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.RenderContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.generic.GenericBedrockEntity;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\b\u0012\u0004\u0012\u00020\u00020\u0003B\u0007\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u000f\u0010\u0004\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0017\u0010\b\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ\u0017\u0010\n\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\n\u0010\tJ\u0017\u0010\r\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\r\u0010\u000eR\"\u0010\u000f\u001a\u00020\u00028\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0005\"\u0004\b\u0012\u0010\tR\u0014\u0010\u0016\u001a\u00020\u00138VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/client/entity/GenericBedrockClientDelegate;", "Lcom/cobblemon/mod/common/api/entity/EntitySideDelegate;", "Lcom/cobblemon/mod/common/entity/generic/GenericBedrockEntity;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "getEntity", "()Lcom/cobblemon/mod/common/entity/generic/GenericBedrockEntity;", "entity", "", "initialize", "(Lcom/cobblemon/mod/common/entity/generic/GenericBedrockEntity;)V", "tick", "", "partialTicks", "updatePartialTicks", "(F)V", "currentEntity", "Lcom/cobblemon/mod/common/entity/generic/GenericBedrockEntity;", "getCurrentEntity", "setCurrentEntity", "Lcom/cobblemon/mod/common/api/scheduling/SchedulingTracker;", "getSchedulingTracker", "()Lcom/cobblemon/mod/common/api/scheduling/SchedulingTracker;", "schedulingTracker", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nGenericBedrockClientDelegate.kt\nKotlin\n*S Kotlin\n*F\n+ 1 GenericBedrockClientDelegate.kt\ncom/cobblemon/mod/common/client/entity/GenericBedrockClientDelegate\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,50:1\n288#2,2:51\n*S KotlinDebug\n*F\n+ 1 GenericBedrockClientDelegate.kt\ncom/cobblemon/mod/common/client/entity/GenericBedrockClientDelegate\n*L\n35#1:51,2\n*E\n"})
public final class GenericBedrockClientDelegate
extends PoseableEntityState<GenericBedrockEntity>
implements EntitySideDelegate<GenericBedrockEntity> {
    public GenericBedrockEntity currentEntity;

    @NotNull
    public final GenericBedrockEntity getCurrentEntity() {
        GenericBedrockEntity genericBedrockEntity = this.currentEntity;
        if (genericBedrockEntity != null) {
            return genericBedrockEntity;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"currentEntity");
        return null;
    }

    public final void setCurrentEntity(@NotNull GenericBedrockEntity genericBedrockEntity) {
        Intrinsics.checkNotNullParameter((Object)genericBedrockEntity, (String)"<set-?>");
        this.currentEntity = genericBedrockEntity;
    }

    @Override
    @NotNull
    public SchedulingTracker getSchedulingTracker() {
        return this.getEntity().getSchedulingTracker();
    }

    @Override
    @NotNull
    public GenericBedrockEntity getEntity() {
        return this.getCurrentEntity();
    }

    @Override
    public void initialize(@NotNull GenericBedrockEntity entity2) {
        Object v4;
        block2: {
            Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
            EntitySideDelegate.DefaultImpls.initialize(this, (Entity)entity2);
            this.setCurrentEntity(entity2);
            this.setAge(entity2.f_19797_);
            this.setCurrentModel(GenericBedrockEntityModelRepository.INSTANCE.getPoser(entity2.getCategory(), entity2.getAspects()));
            PoseableEntityModel poseableEntityModel = this.getCurrentModel();
            Intrinsics.checkNotNull(poseableEntityModel);
            PoseableEntityModel model = poseableEntityModel;
            model.getContext().put(RenderContext.Companion.getENTITY(), entity2);
            PoseableEntityModel poseableEntityModel2 = this.getCurrentModel();
            Intrinsics.checkNotNull(poseableEntityModel2);
            poseableEntityModel2.updateLocators(this);
            Vec3 vec3 = entity2.m_20182_();
            Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"entity.pos");
            this.updateLocatorPosition(vec3);
            PoseType currentPoseType = entity2.getCurrentPoseType();
            PoseableEntityModel poseableEntityModel3 = this.getCurrentModel();
            Intrinsics.checkNotNull(poseableEntityModel3);
            Iterable $this$firstOrNull$iv = poseableEntityModel3.getPoses().values();
            boolean $i$f$firstOrNull = false;
            for (Object element$iv : $this$firstOrNull$iv) {
                Pose it = (Pose)element$iv;
                boolean bl = false;
                if (!(it.getPoseTypes().contains((Object)currentPoseType) && (it.getCondition() == null || (Boolean)it.getCondition().invoke((Object)entity2) != false))) continue;
                v4 = element$iv;
                break block2;
            }
            v4 = null;
        }
        Pose pose = v4;
        if (pose != null) {
            this.doLater((Function0<Unit>)((Function0)new Function0<Unit>(this, (Pose<GenericBedrockEntity, ? extends ModelFrame>)pose){
                final /* synthetic */ GenericBedrockClientDelegate this$0;
                final /* synthetic */ Pose<GenericBedrockEntity, ? extends ModelFrame> $pose;
                {
                    this.this$0 = $receiver;
                    this.$pose = $pose;
                    super(0);
                }

                public final void invoke() {
                    this.this$0.setPose(this.$pose.getPoseName());
                }
            }));
        }
    }

    @Override
    public void tick(@NotNull GenericBedrockEntity entity2) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        EntitySideDelegate.DefaultImpls.tick(this, (Entity)entity2);
        Vec3 vec3 = entity2.m_20182_();
        Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"entity.pos");
        this.updateLocatorPosition(vec3);
        this.incrementAge((Entity)entity2);
    }

    @Override
    public void updatePartialTicks(float partialTicks) {
        this.setCurrentPartialTicks(partialTicks);
    }

    @Override
    public void onTrackedDataSet(@NotNull EntityDataAccessor<?> data) {
        EntitySideDelegate.DefaultImpls.onTrackedDataSet(this, data);
    }
}

