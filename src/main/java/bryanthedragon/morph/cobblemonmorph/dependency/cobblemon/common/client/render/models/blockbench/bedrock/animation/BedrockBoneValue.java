/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import kotlin.Metadata;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u000f\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u001f\u0010\n\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H&\u00a2\u0006\u0004\b\n\u0010\u000b\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockBoneValue;", "", "", "isEmpty", "()Z", "", "time", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "Lnet/minecraft/world/phys/Vec3;", "resolve", "(DLcom/bedrockk/molang/runtime/MoLangRuntime;)Lnet/minecraft/world/phys/Vec3;", "common"})
public interface BedrockBoneValue {
    @NotNull
    public Vec3 resolve(double var1, @NotNull MoLangRuntime var3);

    public boolean isEmpty();
}

