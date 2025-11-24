/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.EntitySideDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\u000f\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\u0003\u0010\u0004R\u0018\u0010\b\u001a\u0006\u0012\u0002\b\u00030\u00058&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/entity/Poseable;", "", "Lcom/cobblemon/mod/common/entity/PoseType;", "getCurrentPoseType", "()Lcom/cobblemon/mod/common/entity/PoseType;", "Lcom/cobblemon/mod/common/api/entity/EntitySideDelegate;", "getDelegate", "()Lcom/cobblemon/mod/common/api/entity/EntitySideDelegate;", "delegate", "common"})
public interface Poseable {
    @NotNull
    public PoseType getCurrentPoseType();

    @NotNull
    public EntitySideDelegate<?> getDelegate();
}

