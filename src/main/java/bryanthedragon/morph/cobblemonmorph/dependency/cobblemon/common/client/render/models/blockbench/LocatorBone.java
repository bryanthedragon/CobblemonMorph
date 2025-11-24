/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010 \n\u0002\u0010\u0007\n\u0002\b\f\u0018\u00002\u00020\u0001B'\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u0012\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u00a2\u0006\u0004\b\r\u0010\u000eR(\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR(\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\n\u0010\u0005\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\t\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/LocatorBone;", "", "", "", "offset", "Ljava/util/List;", "getOffset", "()Ljava/util/List;", "setOffset", "(Ljava/util/List;)V", "rotation", "getRotation", "setRotation", "<init>", "(Ljava/util/List;Ljava/util/List;)V", "common"})
public final class LocatorBone {
    @NotNull
    private List<Float> offset;
    @NotNull
    private List<Float> rotation;

    public LocatorBone(@NotNull List<Float> offset, @NotNull List<Float> rotation) {
        Intrinsics.checkNotNullParameter(offset, (String)"offset");
        Intrinsics.checkNotNullParameter(rotation, (String)"rotation");
        this.offset = offset;
        this.rotation = rotation;
    }

    public /* synthetic */ LocatorBone(List list, List list2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        Object[] objectArray;
        if ((n & 1) != 0) {
            objectArray = new Float[]{Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(0.0f)};
            list = CollectionsKt.listOf((Object[])objectArray);
        }
        if ((n & 2) != 0) {
            objectArray = new Float[]{Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(0.0f)};
            list2 = CollectionsKt.listOf((Object[])objectArray);
        }
        this(list, list2);
    }

    @NotNull
    public final List<Float> getOffset() {
        return this.offset;
    }

    public final void setOffset(@NotNull List<Float> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.offset = list;
    }

    @NotNull
    public final List<Float> getRotation() {
        return this.rotation;
    }

    public final void setRotation(@NotNull List<Float> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.rotation = list;
    }

    public LocatorBone() {
        this(null, null, 3, null);
    }
}

