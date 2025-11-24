/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.model.geom.ModelPart
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.RangeOfMotion;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.model.geom.ModelPart;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\r\u001a\u00020\f\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\r\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/EarJoint;", "", "", "axis", "I", "getAxis", "()I", "Lnet/minecraft/client/model/geom/ModelPart;", "modelPart", "Lnet/minecraft/client/model/geom/ModelPart;", "getModelPart", "()Lnet/minecraft/client/model/geom/ModelPart;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/RangeOfMotion;", "rangeOfMotion", "Lcom/cobblemon/mod/common/client/render/models/blockbench/RangeOfMotion;", "getRangeOfMotion", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/RangeOfMotion;", "<init>", "(Lnet/minecraft/client/model/geom/ModelPart;ILcom/cobblemon/mod/common/client/render/models/blockbench/RangeOfMotion;)V", "common"})
public final class EarJoint {
    @NotNull
    private final ModelPart modelPart;
    private final int axis;
    @NotNull
    private final RangeOfMotion rangeOfMotion;

    public EarJoint(@NotNull ModelPart modelPart, int axis, @NotNull RangeOfMotion rangeOfMotion) {
        Intrinsics.checkNotNullParameter((Object)modelPart, (String)"modelPart");
        Intrinsics.checkNotNullParameter((Object)rangeOfMotion, (String)"rangeOfMotion");
        this.modelPart = modelPart;
        this.axis = axis;
        this.rangeOfMotion = rangeOfMotion;
    }

    @NotNull
    public final ModelPart getModelPart() {
        return this.modelPart;
    }

    public final int getAxis() {
        return this.axis;
    }

    @NotNull
    public final RangeOfMotion getRangeOfMotion() {
        return this.rangeOfMotion;
    }
}

