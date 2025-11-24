/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.ModelTextureSupplier;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0007\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0011\u001a\u00020\u0010\u0012\u0006\u0010\u0007\u001a\u00020\u0002\u0012\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u000b\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0018\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0096\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\t\u0010\nR\u001d\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u000b8\u0006\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0011\u001a\u00020\u00108\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/client/render/AnimatedModelTextureSupplier;", "Lcom/cobblemon/mod/common/client/render/ModelTextureSupplier;", "", "animationSeconds", "Lnet/minecraft/resources/ResourceLocation;", "invoke", "(F)Lnet/minecraft/resources/ResourceLocation;", "fps", "F", "getFps", "()F", "", "frames", "Ljava/util/List;", "getFrames", "()Ljava/util/List;", "", "loop", "Z", "getLoop", "()Z", "<init>", "(ZFLjava/util/List;)V", "common"})
public final class AnimatedModelTextureSupplier
implements ModelTextureSupplier {
    private final boolean loop;
    private final float fps;
    @NotNull
    private final List<ResourceLocation> frames;

    public AnimatedModelTextureSupplier(boolean loop, float fps, @NotNull List<? extends ResourceLocation> frames) {
        Intrinsics.checkNotNullParameter(frames, (String)"frames");
        this.loop = loop;
        this.fps = fps;
        this.frames = frames;
    }

    public final boolean getLoop() {
        return this.loop;
    }

    public final float getFps() {
        return this.fps;
    }

    @NotNull
    public final List<ResourceLocation> getFrames() {
        return this.frames;
    }

    @Override
    @NotNull
    public ResourceLocation invoke(float animationSeconds) {
        int frameIndex = (int)Math.floor(animationSeconds * this.fps);
        if (frameIndex >= this.frames.size() && !this.loop) {
            return (ResourceLocation)CollectionsKt.last(this.frames);
        }
        return this.frames.get(frameIndex % this.frames.size());
    }
}

