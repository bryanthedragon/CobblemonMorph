/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.joml.Matrix4f
 *  org.joml.Matrix4fc
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.geometry.Matrix4fExtensionsKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0012\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\u0015\u0010\u0004\u001a\n \u0003*\u0004\u0018\u00010\u00020\u0002\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u001d\u0010\u0007\u001a\n \u0003*\u0004\u0018\u00010\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0015\u0010\u000b\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\t\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0015\u0010\r\u001a\u00020\u00002\u0006\u0010\u0006\u001a\u00020\u0002\u00a2\u0006\u0004\b\r\u0010\u000eR\"\u0010\u000f\u001a\u00020\t8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\"\u0010\u0006\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0006\u0010\u0015\u001a\u0004\b\u0016\u0010\u0005\"\u0004\b\u0017\u0010\u0018\u00a8\u0006\u001b"}, d2={"Lcom/cobblemon/mod/common/client/render/MatrixWrapper;", "", "Lnet/minecraft/world/phys/Vec3;", "kotlin.jvm.PlatformType", "getOrigin", "()Lnet/minecraft/world/phys/Vec3;", "position", "transformPosition", "(Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;", "Lorg/joml/Matrix4f;", "rotationMatrix", "updateMatrix", "(Lorg/joml/Matrix4f;)Lcom/cobblemon/mod/common/client/render/MatrixWrapper;", "updatePosition", "(Lnet/minecraft/world/phys/Vec3;)Lcom/cobblemon/mod/common/client/render/MatrixWrapper;", "matrix", "Lorg/joml/Matrix4f;", "getMatrix", "()Lorg/joml/Matrix4f;", "setMatrix", "(Lorg/joml/Matrix4f;)V", "Lnet/minecraft/world/phys/Vec3;", "getPosition", "setPosition", "(Lnet/minecraft/world/phys/Vec3;)V", "<init>", "()V", "common"})
public final class MatrixWrapper {
    @NotNull
    private Vec3 position;
    @NotNull
    private Matrix4f matrix;

    public MatrixWrapper() {
        Vec3 vec3 = Vec3.f_82478_;
        Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"ZERO");
        this.position = vec3;
        this.matrix = new Matrix4f();
    }

    @NotNull
    public final Vec3 getPosition() {
        return this.position;
    }

    public final void setPosition(@NotNull Vec3 vec3) {
        Intrinsics.checkNotNullParameter((Object)vec3, (String)"<set-?>");
        this.position = vec3;
    }

    @NotNull
    public final Matrix4f getMatrix() {
        return this.matrix;
    }

    public final void setMatrix(@NotNull Matrix4f matrix4f) {
        Intrinsics.checkNotNullParameter((Object)matrix4f, (String)"<set-?>");
        this.matrix = matrix4f;
    }

    @NotNull
    public final MatrixWrapper updateMatrix(@NotNull Matrix4f rotationMatrix) {
        MatrixWrapper matrixWrapper;
        Intrinsics.checkNotNullParameter((Object)rotationMatrix, (String)"rotationMatrix");
        MatrixWrapper $this$updateMatrix_u24lambda_u240 = matrixWrapper = this;
        boolean bl = false;
        $this$updateMatrix_u24lambda_u240.matrix = new Matrix4f((Matrix4fc)rotationMatrix);
        return matrixWrapper;
    }

    @NotNull
    public final MatrixWrapper updatePosition(@NotNull Vec3 position) {
        MatrixWrapper matrixWrapper;
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        MatrixWrapper $this$updatePosition_u24lambda_u241 = matrixWrapper = this;
        boolean bl = false;
        $this$updatePosition_u24lambda_u241.position = position;
        return matrixWrapper;
    }

    public final Vec3 getOrigin() {
        return this.position.m_82549_(Matrix4fExtensionsKt.getOrigin(this.matrix));
    }

    public final Vec3 transformPosition(@NotNull Vec3 position) {
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        return this.position.m_82549_(Matrix4fExtensionsKt.transformPosition(this.matrix, position));
    }
}

