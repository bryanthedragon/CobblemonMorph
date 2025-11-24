/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.model.geom.ModelPart
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.model.geom.ModelPart;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\f\u0010\rR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/WaveSegment;", "", "", "length", "F", "getLength", "()F", "Lnet/minecraft/client/model/geom/ModelPart;", "modelPart", "Lnet/minecraft/client/model/geom/ModelPart;", "getModelPart", "()Lnet/minecraft/client/model/geom/ModelPart;", "<init>", "(Lnet/minecraft/client/model/geom/ModelPart;F)V", "common"})
public final class WaveSegment {
    @NotNull
    private final ModelPart modelPart;
    private final float length;

    public WaveSegment(@NotNull ModelPart modelPart, float length) {
        Intrinsics.checkNotNullParameter((Object)modelPart, (String)"modelPart");
        this.modelPart = modelPart;
        this.length = length;
    }

    @NotNull
    public final ModelPart getModelPart() {
        return this.modelPart;
    }

    public final float getLength() {
        return this.length;
    }
}

