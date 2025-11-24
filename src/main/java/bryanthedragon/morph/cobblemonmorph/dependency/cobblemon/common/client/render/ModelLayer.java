/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Vector4f
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.ModelTextureSupplier;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector4f;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u001a\u0010\u001bR\u001a\u0010\u0003\u001a\u00020\u00028\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\u00020\u00028\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\u0007\u0010\u0004\u001a\u0004\b\b\u0010\u0006R\u001a\u0010\n\u001a\u00020\t8\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\rR\u0019\u0010\u000f\u001a\u0004\u0018\u00010\u000e8\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0014\u001a\u00020\u00138\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\u00020\u00028\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0004\u001a\u0004\b\u0019\u0010\u0006\u00a8\u0006\u001c"}, d2={"Lcom/cobblemon/mod/common/client/render/ModelLayer;", "", "", "emissive", "Z", "getEmissive", "()Z", "enabled", "getEnabled", "", "name", "Ljava/lang/String;", "getName", "()Ljava/lang/String;", "Lcom/cobblemon/mod/common/client/render/ModelTextureSupplier;", "texture", "Lcom/cobblemon/mod/common/client/render/ModelTextureSupplier;", "getTexture", "()Lcom/cobblemon/mod/common/client/render/ModelTextureSupplier;", "Lorg/joml/Vector4f;", "tint", "Lorg/joml/Vector4f;", "getTint", "()Lorg/joml/Vector4f;", "translucent", "getTranslucent", "<init>", "()V", "common"})
public final class ModelLayer {
    @NotNull
    private final String name;
    private final boolean enabled;
    @NotNull
    private final Vector4f tint = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
    @Nullable
    private final ModelTextureSupplier texture;
    private final boolean emissive;
    private final boolean translucent;

    public ModelLayer() {
        this.name = "";
        this.enabled = true;
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    public final boolean getEnabled() {
        return this.enabled;
    }

    @NotNull
    public final Vector4f getTint() {
        return this.tint;
    }

    @Nullable
    public final ModelTextureSupplier getTexture() {
        return this.texture;
    }

    public final boolean getEmissive() {
        return this.emissive;
    }

    public final boolean getTranslucent() {
        return this.translucent;
    }
}

