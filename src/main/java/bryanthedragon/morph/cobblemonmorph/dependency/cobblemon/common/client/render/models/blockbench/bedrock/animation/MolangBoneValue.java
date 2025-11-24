/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.Minecraft
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangEnvironment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.DoubleValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockBoneValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.Transformation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\r\u001a\u00020\f\u0012\u0006\u0010\u0011\u001a\u00020\f\u0012\u0006\u0010\u0018\u001a\u00020\f\u0012\u0006\u0010\u001b\u001a\u00020\u001a\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u001f\u0010\n\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\n\u0010\u000bR\u0017\u0010\r\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0011\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\u000e\u001a\u0004\b\u0012\u0010\u0010R\u0017\u0010\u0014\u001a\u00020\u00138\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R\u0017\u0010\u0018\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\u0018\u0010\u000e\u001a\u0004\b\u0019\u0010\u0010\u00a8\u0006\u001e"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/MolangBoneValue;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockBoneValue;", "", "isEmpty", "()Z", "", "time", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "Lnet/minecraft/world/phys/Vec3;", "resolve", "(DLcom/bedrockk/molang/runtime/MoLangRuntime;)Lnet/minecraft/world/phys/Vec3;", "Lcom/bedrockk/molang/Expression;", "x", "Lcom/bedrockk/molang/Expression;", "getX", "()Lcom/bedrockk/molang/Expression;", "y", "getY", "", "yMul", "I", "getYMul", "()I", "z", "getZ", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/Transformation;", "transformation", "<init>", "(Lcom/bedrockk/molang/Expression;Lcom/bedrockk/molang/Expression;Lcom/bedrockk/molang/Expression;Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/Transformation;)V", "common"})
public final class MolangBoneValue
implements BedrockBoneValue {
    @NotNull
    private final Expression x;
    @NotNull
    private final Expression y;
    @NotNull
    private final Expression z;
    private final int yMul;

    public MolangBoneValue(@NotNull Expression x, @NotNull Expression y, @NotNull Expression z, @NotNull Transformation transformation) {
        Intrinsics.checkNotNullParameter((Object)x, (String)"x");
        Intrinsics.checkNotNullParameter((Object)y, (String)"y");
        Intrinsics.checkNotNullParameter((Object)z, (String)"z");
        Intrinsics.checkNotNullParameter((Object)((Object)transformation), (String)"transformation");
        this.x = x;
        this.y = y;
        this.z = z;
        this.yMul = transformation == Transformation.POSITION ? -1 : 1;
    }

    @NotNull
    public final Expression getX() {
        return this.x;
    }

    @NotNull
    public final Expression getY() {
        return this.y;
    }

    @NotNull
    public final Expression getZ() {
        return this.z;
    }

    public final int getYMul() {
        return this.yMul;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    @NotNull
    public Vec3 resolve(double time, @NotNull MoLangRuntime runtime2) {
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        MoLangEnvironment environment = runtime2.getEnvironment();
        environment.setSimpleVariable("anim_time", new DoubleValue(time));
        environment.setSimpleVariable("camera_rotation_x", new DoubleValue(Minecraft.m_91087_().f_91063_.m_109153_().m_253121_().x));
        environment.setSimpleVariable("camera_rotation_y", new DoubleValue(Minecraft.m_91087_().f_91063_.m_109153_().m_253121_().y));
        return new Vec3(MoLangExtensionsKt.resolveDouble(runtime2, this.x), MoLangExtensionsKt.resolveDouble(runtime2, this.y) * (double)this.yMul, MoLangExtensionsKt.resolveDouble(runtime2, this.z));
    }
}

