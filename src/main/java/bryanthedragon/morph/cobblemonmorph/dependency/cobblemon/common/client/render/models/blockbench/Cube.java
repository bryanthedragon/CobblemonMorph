/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench;

import java.util.List;
import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u001a\u0010\u001bR\u0019\u0010\u0003\u001a\u0004\u0018\u00010\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\b\u001a\u00020\u00078\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u001f\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\f8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u001f\u0010\u0011\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\f8\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\u000e\u001a\u0004\b\u0012\u0010\u0010R\u001f\u0010\u0013\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\f8\u0006\u00a2\u0006\f\n\u0004\b\u0013\u0010\u000e\u001a\u0004\b\u0014\u0010\u0010R\u001f\u0010\u0015\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\f8\u0006\u00a2\u0006\f\n\u0004\b\u0015\u0010\u000e\u001a\u0004\b\u0016\u0010\u0010R\u001f\u0010\u0018\u001a\n\u0012\u0004\u0012\u00020\u0017\u0018\u00010\f8\u0006\u00a2\u0006\f\n\u0004\b\u0018\u0010\u000e\u001a\u0004\b\u0019\u0010\u0010\u00a8\u0006\u001c"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/Cube;", "", "", "inflate", "Ljava/lang/Float;", "getInflate", "()Ljava/lang/Float;", "", "mirror", "Z", "getMirror", "()Z", "", "origin", "Ljava/util/List;", "getOrigin", "()Ljava/util/List;", "pivot", "getPivot", "rotation", "getRotation", "size", "getSize", "", "uv", "getUv", "<init>", "()V", "common"})
public final class Cube {
    @Nullable
    private final List<Float> origin;
    @Nullable
    private final List<Float> size;
    @Nullable
    private final List<Float> pivot;
    @Nullable
    private final List<Float> rotation;
    @Nullable
    private final List<Integer> uv;
    @Nullable
    private final Float inflate;
    private final boolean mirror;

    @Nullable
    public final List<Float> getOrigin() {
        return this.origin;
    }

    @Nullable
    public final List<Float> getSize() {
        return this.size;
    }

    @Nullable
    public final List<Float> getPivot() {
        return this.pivot;
    }

    @Nullable
    public final List<Float> getRotation() {
        return this.rotation;
    }

    @Nullable
    public final List<Integer> getUv() {
        return this.uv;
    }

    @Nullable
    public final Float getInflate() {
        return this.inflate;
    }

    public final boolean getMirror() {
        return this.mirror;
    }
}

