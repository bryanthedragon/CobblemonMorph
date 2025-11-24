/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.QuirkData;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0007\b&\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u0001*\u000e\b\u0001\u0010\u0004*\b\u0012\u0004\u0012\u00028\u00000\u00032\u00020\u0005B\u0007\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\u000f\u0010\u0006\u001a\u00028\u0001H&\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001b\u0010\n\u001a\u00028\u00012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\b\u00a2\u0006\u0004\b\n\u0010\u000bJc\u0010\u0017\u001a\u00020\u00162\b\u0010\f\u001a\u0004\u0018\u00018\u00002\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\r2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\b2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u000f\u00a2\u0006\u0004\b\u0017\u0010\u0018J%\u0010\u0017\u001a\u00020\u00162\f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\b2\u0006\u0010\u0019\u001a\u00028\u0001H$\u00a2\u0006\u0004\b\u0017\u0010\u001a\u00a8\u0006\u001d"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/quirk/ModelQuirk;", "Lnet/minecraft/world/entity/Entity;", "T", "Lcom/cobblemon/mod/common/client/render/models/blockbench/quirk/QuirkData;", "D", "", "createData", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/quirk/QuirkData;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "state", "getOrCreateData", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;)Lcom/cobblemon/mod/common/client/render/models/blockbench/quirk/QuirkData;", "entity", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;", "model", "", "limbSwing", "limbSwingAmount", "ageInTicks", "headYaw", "headPitch", "intensity", "", "tick", "(Lnet/minecraft/world/entity/Entity;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;FFFFFF)V", "data", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;Lcom/cobblemon/mod/common/client/render/models/blockbench/quirk/QuirkData;)V", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nModelQuirk.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ModelQuirk.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/quirk/ModelQuirk\n+ 2 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n*L\n1#1,26:1\n361#2,7:27\n*S KotlinDebug\n*F\n+ 1 ModelQuirk.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/quirk/ModelQuirk\n*L\n24#1:27,7\n*E\n"})
public abstract class ModelQuirk<T extends Entity, D extends QuirkData<T>> {
    @NotNull
    public abstract D createData();

    protected abstract void tick(@NotNull PoseableEntityState<T> var1, @NotNull D var2);

    public final void tick(@Nullable T entity2, @NotNull PoseableEntityModel<T> model, @NotNull PoseableEntityState<T> state, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch, float intensity) {
        Intrinsics.checkNotNullParameter(model, (String)"model");
        Intrinsics.checkNotNullParameter(state, (String)"state");
        D data = this.getOrCreateData(state);
        this.tick(state, data);
        ((QuirkData)data).run(entity2, model, state, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch, intensity);
    }

    @NotNull
    public final D getOrCreateData(@NotNull PoseableEntityState<T> state) {
        QuirkData<Object> quirkData;
        Intrinsics.checkNotNullParameter(state, (String)"state");
        Map<ModelQuirk<T, ?>, QuirkData<T>> $this$getOrPut$iv = state.getQuirks();
        boolean $i$f$getOrPut = false;
        QuirkData<T> value$iv = $this$getOrPut$iv.get(this);
        if (value$iv == null) {
            boolean bl = false;
            D answer$iv = this.createData();
            $this$getOrPut$iv.put(this, (QuirkData<T>)answer$iv);
            quirkData = answer$iv;
        } else {
            quirkData = value$iv;
        }
        Intrinsics.checkNotNull(quirkData, (String)"null cannot be cast to non-null type D of bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.ModelQuirk");
        return (D)quirkData;
    }
}

