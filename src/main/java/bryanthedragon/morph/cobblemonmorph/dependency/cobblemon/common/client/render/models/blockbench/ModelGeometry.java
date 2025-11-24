/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelBone;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelDataDescription;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u000f\u0010\u0010R\u001f\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007R\"\u0010\t\u001a\u00020\b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e\u00a8\u0006\u0011"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/ModelGeometry;", "", "", "Lcom/cobblemon/mod/common/client/render/models/blockbench/ModelBone;", "bones", "Ljava/util/List;", "getBones", "()Ljava/util/List;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/ModelDataDescription;", "description", "Lcom/cobblemon/mod/common/client/render/models/blockbench/ModelDataDescription;", "getDescription", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/ModelDataDescription;", "setDescription", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/ModelDataDescription;)V", "<init>", "()V", "common"})
public final class ModelGeometry {
    public ModelDataDescription description;
    @Nullable
    private final List<ModelBone> bones;

    @NotNull
    public final ModelDataDescription getDescription() {
        ModelDataDescription modelDataDescription = this.description;
        if (modelDataDescription != null) {
            return modelDataDescription;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"description");
        return null;
    }

    public final void setDescription(@NotNull ModelDataDescription modelDataDescription) {
        Intrinsics.checkNotNullParameter((Object)modelDataDescription, (String)"<set-?>");
        this.description = modelDataDescription;
    }

    @Nullable
    public final List<ModelBone> getBones() {
        return this.bones;
    }
}

