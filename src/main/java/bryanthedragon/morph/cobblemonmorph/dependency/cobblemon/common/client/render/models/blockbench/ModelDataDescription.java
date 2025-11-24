/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.SerializedName
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\t\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\f\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\u0018\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u000e\u0012\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0013\u00a2\u0006\u0004\b\u001a\u0010\u001bR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\b\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\f\n\u0004\b\f\u0010\t\u001a\u0004\b\r\u0010\u000bR\u001a\u0010\u000f\u001a\u00020\u000e8\u0006X\u0087\u0004\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012R \u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00138\u0006X\u0087\u0004\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\u00020\u000e8\u0006X\u0087\u0004\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0010\u001a\u0004\b\u0019\u0010\u0012\u00a8\u0006\u001c"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/ModelDataDescription;", "", "", "identifier", "Ljava/lang/String;", "getIdentifier", "()Ljava/lang/String;", "", "textureHeight", "I", "getTextureHeight", "()I", "textureWidth", "getTextureWidth", "", "visibleBoundsHeight", "F", "getVisibleBoundsHeight", "()F", "", "visibleBoundsOffset", "Ljava/util/List;", "getVisibleBoundsOffset", "()Ljava/util/List;", "visibleBoundsWidth", "getVisibleBoundsWidth", "<init>", "(Ljava/lang/String;IIFFLjava/util/List;)V", "common"})
public final class ModelDataDescription {
    @NotNull
    private final String identifier;
    @SerializedName(value="texture_width")
    private final int textureWidth;
    @SerializedName(value="texture_height")
    private final int textureHeight;
    @SerializedName(value="visible_bounds_width")
    private final float visibleBoundsWidth;
    @SerializedName(value="visible_bounds_height")
    private final float visibleBoundsHeight;
    @SerializedName(value="visible_bounds_offset")
    @NotNull
    private final List<Float> visibleBoundsOffset;

    public ModelDataDescription(@NotNull String identifier, int textureWidth, int textureHeight, float visibleBoundsWidth, float visibleBoundsHeight, @NotNull List<Float> visibleBoundsOffset) {
        Intrinsics.checkNotNullParameter((Object)identifier, (String)"identifier");
        Intrinsics.checkNotNullParameter(visibleBoundsOffset, (String)"visibleBoundsOffset");
        this.identifier = identifier;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.visibleBoundsWidth = visibleBoundsWidth;
        this.visibleBoundsHeight = visibleBoundsHeight;
        this.visibleBoundsOffset = visibleBoundsOffset;
    }

    @NotNull
    public final String getIdentifier() {
        return this.identifier;
    }

    public final int getTextureWidth() {
        return this.textureWidth;
    }

    public final int getTextureHeight() {
        return this.textureHeight;
    }

    public final float getVisibleBoundsWidth() {
        return this.visibleBoundsWidth;
    }

    public final float getVisibleBoundsHeight() {
        return this.visibleBoundsHeight;
    }

    @NotNull
    public final List<Float> getVisibleBoundsOffset() {
        return this.visibleBoundsOffset;
    }
}

