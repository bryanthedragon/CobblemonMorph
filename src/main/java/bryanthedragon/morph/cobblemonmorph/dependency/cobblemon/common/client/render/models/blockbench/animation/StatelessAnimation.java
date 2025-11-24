/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\b&\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u0001*\b\b\u0001\u0010\u0004*\u00020\u00032\u00020\u0005B\u000f\u0012\u0006\u0010\u001a\u001a\u00028\u0001\u00a2\u0006\u0004\b*\u0010+Je\u0010\u0013\u001a\u00020\u00122\b\u0010\u0006\u001a\u0004\u0018\u00018\u00002\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u00072\u000e\u0010\n\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\t2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u000b\u00a2\u0006\u0004\b\u0013\u0010\u0014J5\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0006\u001a\u00028\u00002\f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\t2\u0006\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\u0017\u0010\u0018Jg\u0010\u0019\u001a\u00020\u00122\b\u0010\u0006\u001a\u0004\u0018\u00018\u00002\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u00072\u000e\u0010\n\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\t2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u000bH$\u00a2\u0006\u0004\b\u0019\u0010\u0014R\u0017\u0010\u001a\u001a\u00028\u00018\u0006\u00a2\u0006\f\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u001c\u0010\u001dR(\u0010 \u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b \u0010!\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\u001a\u0010)\u001a\b\u0012\u0004\u0012\u00028\u00010&8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b'\u0010(\u00a8\u0006,"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatelessAnimation;", "Lnet/minecraft/world/entity/Entity;", "T", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "F", "", "entity", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;", "model", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "state", "", "limbSwing", "limbSwingAmount", "ageInTicks", "headYaw", "headPitch", "intensity", "", "apply", "(Lnet/minecraft/world/entity/Entity;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;FFFFFF)V", "previousSeconds", "newSeconds", "applyEffects", "(Lnet/minecraft/world/entity/Entity;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;FF)V", "setAngles", "frame", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "getFrame", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "", "", "labels", "Ljava/util/Set;", "getLabels", "()Ljava/util/Set;", "setLabels", "(Ljava/util/Set;)V", "Ljava/lang/Class;", "getTargetFrame", "()Ljava/lang/Class;", "targetFrame", "<init>", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;)V", "common"})
public abstract class StatelessAnimation<T extends Entity, F extends ModelFrame> {
    @NotNull
    private final F frame;
    @NotNull
    private Set<String> labels;

    public StatelessAnimation(@NotNull F frame) {
        Intrinsics.checkNotNullParameter(frame, (String)"frame");
        this.frame = frame;
        this.labels = SetsKt.emptySet();
    }

    @NotNull
    public final F getFrame() {
        return this.frame;
    }

    @NotNull
    public abstract Class<F> getTargetFrame();

    @NotNull
    public Set<String> getLabels() {
        return this.labels;
    }

    public void setLabels(@NotNull Set<String> set2) {
        Intrinsics.checkNotNullParameter(set2, (String)"<set-?>");
        this.labels = set2;
    }

    protected abstract void setAngles(@Nullable T var1, @NotNull PoseableEntityModel<T> var2, @Nullable PoseableEntityState<T> var3, float var4, float var5, float var6, float var7, float var8, float var9);

    public final void apply(@Nullable T entity2, @NotNull PoseableEntityModel<T> model, @Nullable PoseableEntityState<T> state, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch, float intensity) {
        Intrinsics.checkNotNullParameter(model, (String)"model");
        this.setAngles(entity2, model, state, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch, intensity);
    }

    public void applyEffects(@NotNull T entity2, @NotNull PoseableEntityState<T> state, float previousSeconds, float newSeconds) {
        Intrinsics.checkNotNullParameter(entity2, (String)"entity");
        Intrinsics.checkNotNullParameter(state, (String)"state");
    }
}

