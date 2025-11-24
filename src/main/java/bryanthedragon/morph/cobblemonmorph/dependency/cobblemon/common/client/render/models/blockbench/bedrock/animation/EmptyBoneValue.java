/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockBoneValue;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\r\u0010\u000eJ\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J'\u0010\u000b\u001a\n \n*\u0004\u0018\u00010\t0\t2\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\u000b\u0010\f\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/EmptyBoneValue;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockBoneValue;", "", "isEmpty", "()Z", "", "time", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "Lnet/minecraft/world/phys/Vec3;", "kotlin.jvm.PlatformType", "resolve", "(DLcom/bedrockk/molang/runtime/MoLangRuntime;)Lnet/minecraft/world/phys/Vec3;", "<init>", "()V", "common"})
public final class EmptyBoneValue
implements BedrockBoneValue {
    @NotNull
    public static final EmptyBoneValue INSTANCE = new EmptyBoneValue();

    private EmptyBoneValue() {
    }

    @Override
    public Vec3 resolve(double time, @NotNull MoLangRuntime runtime2) {
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        return Vec3.f_82478_;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }
}

