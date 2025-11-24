/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.PrimaryAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\b\u0016\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u00020\u0003B\u0007\u00a2\u0006\u0004\b \u0010!Je\u0010\u0011\u001a\u00020\u00102\b\u0010\u0004\u001a\u0004\u0018\u00018\u00002\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u00052\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u00072\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0012R'\u0010\u0015\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00028\u0000\u0012\u0002\b\u00030\u00140\u00138\u0006\u00a2\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018R*\u0010\u001a\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u00198\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001f\u00a8\u0006\""}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/quirk/QuirkData;", "Lnet/minecraft/world/entity/Entity;", "T", "", "entity", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;", "model", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "state", "", "limbSwing", "limbSwingAmount", "ageInTicks", "headYaw", "headPitch", "intensity", "", "run", "(Lnet/minecraft/world/entity/Entity;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;FFFFFF)V", "", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatefulAnimation;", "animations", "Ljava/util/List;", "getAnimations", "()Ljava/util/List;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/PrimaryAnimation;", "primaryAnimation", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/PrimaryAnimation;", "getPrimaryAnimation", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/PrimaryAnimation;", "setPrimaryAnimation", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/PrimaryAnimation;)V", "<init>", "()V", "common"})
public class QuirkData<T extends Entity> {
    @NotNull
    private final List<StatefulAnimation<T, ?>> animations = new ArrayList();
    @Nullable
    private PrimaryAnimation<T> primaryAnimation;

    @NotNull
    public final List<StatefulAnimation<T, ?>> getAnimations() {
        return this.animations;
    }

    @Nullable
    public final PrimaryAnimation<T> getPrimaryAnimation() {
        return this.primaryAnimation;
    }

    public final void setPrimaryAnimation(@Nullable PrimaryAnimation<T> primaryAnimation2) {
        this.primaryAnimation = primaryAnimation2;
    }

    public void run(@Nullable T entity2, @NotNull PoseableEntityModel<T> model, @NotNull PoseableEntityState<T> state, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch, float intensity) {
        Intrinsics.checkNotNullParameter(model, (String)"model");
        Intrinsics.checkNotNullParameter(state, (String)"state");
        if (this.primaryAnimation != null && !Intrinsics.areEqual(state.getPrimaryAnimation(), this.primaryAnimation)) {
            this.primaryAnimation = null;
        }
        this.animations.removeIf(arg_0 -> QuirkData.run$lambda$0((Function1)new Function1<StatefulAnimation<T, ?>, Boolean>(entity2, model, state, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch, intensity){
            final /* synthetic */ T $entity;
            final /* synthetic */ PoseableEntityModel<T> $model;
            final /* synthetic */ PoseableEntityState<T> $state;
            final /* synthetic */ float $limbSwing;
            final /* synthetic */ float $limbSwingAmount;
            final /* synthetic */ float $ageInTicks;
            final /* synthetic */ float $headYaw;
            final /* synthetic */ float $headPitch;
            final /* synthetic */ float $intensity;
            {
                this.$entity = $entity;
                this.$model = $model;
                this.$state = $state;
                this.$limbSwing = $limbSwing;
                this.$limbSwingAmount = $limbSwingAmount;
                this.$ageInTicks = $ageInTicks;
                this.$headYaw = $headYaw;
                this.$headPitch = $headPitch;
                this.$intensity = $intensity;
                super(1);
            }

            @NotNull
            public final Boolean invoke(@NotNull StatefulAnimation<T, ?> it) {
                Intrinsics.checkNotNullParameter(it, (String)"it");
                return !it.run(this.$entity, this.$model, this.$state, this.$limbSwing, this.$limbSwingAmount, this.$ageInTicks, this.$headYaw, this.$headPitch, this.$intensity);
            }
        }, arg_0));
    }

    private static final boolean run$lambda$0(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Boolean)$tmp0.invoke(p0);
    }
}

