/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.VertexBuffer
 *  com.mojang.blaze3d.vertex.VertexBuffer$Usage
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.block;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.BerryBlockEntity;
import com.mojang.blaze3d.vertex.VertexBuffer;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u001a\u0010\u0004J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004R\u001a\u0010\u0006\u001a\u00020\u00058\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\u0006\u0010\u0007\u001a\u0004\b\b\u0010\tR\"\u0010\u000b\u001a\u00020\n8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0012\u001a\u00020\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\"\u0010\u0016\u001a\u00020\u00058\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0016\u0010\u0007\u001a\u0004\b\u0017\u0010\t\"\u0004\b\u0018\u0010\u0019\u00a8\u0006\u001b"}, d2={"Lcom/cobblemon/mod/common/client/render/block/BerryBlockEntityRenderState;", "Lcom/cobblemon/mod/common/block/entity/BerryBlockEntity$RenderState;", "", "close", "()V", "", "lastRenderFrame", "I", "getLastRenderFrame", "()I", "", "needsRebuild", "Z", "getNeedsRebuild", "()Z", "setNeedsRebuild", "(Z)V", "Lcom/mojang/blaze3d/vertex/VertexBuffer;", "vbo", "Lcom/mojang/blaze3d/vertex/VertexBuffer;", "getVbo", "()Lcom/mojang/blaze3d/vertex/VertexBuffer;", "vboLightLevel", "getVboLightLevel", "setVboLightLevel", "(I)V", "<init>", "common"})
public final class BerryBlockEntityRenderState
implements BerryBlockEntity.RenderState {
    private final int lastRenderFrame;
    private boolean needsRebuild = true;
    @NotNull
    private final VertexBuffer vbo = new VertexBuffer(VertexBuffer.Usage.STATIC);
    private int vboLightLevel;

    public BerryBlockEntityRenderState() {
        this.lastRenderFrame = -1;
    }

    public final int getLastRenderFrame() {
        return this.lastRenderFrame;
    }

    @Override
    public boolean getNeedsRebuild() {
        return this.needsRebuild;
    }

    @Override
    public void setNeedsRebuild(boolean bl) {
        this.needsRebuild = bl;
    }

    @NotNull
    public final VertexBuffer getVbo() {
        return this.vbo;
    }

    public final int getVboLightLevel() {
        return this.vboLightLevel;
    }

    public final void setVboLightLevel(int n) {
        this.vboLightLevel = n;
    }

    @Override
    public void close() {
        this.vbo.close();
    }
}

