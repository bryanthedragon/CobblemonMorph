/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0006\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\b\u0082\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0006\u001a\u00020\u0002\u0012\u0006\u0010\u0007\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0005\u0010\u0004J$\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0006\u001a\u00020\u00022\b\b\u0002\u0010\u0007\u001a\u00020\u0002H\u00c6\u0001\u00a2\u0006\u0004\b\b\u0010\tJ\u001a\u0010\f\u001a\u00020\u000b2\b\u0010\n\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\f\u0010\rJ\u0010\u0010\u000f\u001a\u00020\u000eH\u00d6\u0001\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0010\u0010\u0012\u001a\u00020\u0011H\u00d6\u0001\u00a2\u0006\u0004\b\u0012\u0010\u0013R\u0017\u0010\u0006\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0006\u0010\u0014\u001a\u0004\b\u0015\u0010\u0004R\u0017\u0010\u0007\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\u0014\u001a\u0004\b\u0016\u0010\u0004\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/Vector2d;", "", "", "component1", "()D", "component2", "a", "b", "copy", "(DD)Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/Vector2d;", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "D", "getA", "getB", "<init>", "(DD)V", "common"})
final class Vector2d {
    private final double a;
    private final double b;

    public Vector2d(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public final double getA() {
        return this.a;
    }

    public final double getB() {
        return this.b;
    }

    public final double component1() {
        return this.a;
    }

    public final double component2() {
        return this.b;
    }

    @NotNull
    public final Vector2d copy(double a, double b) {
        return new Vector2d(a, b);
    }

    public static /* synthetic */ Vector2d copy$default(Vector2d vector2d, double d, double d2, int n, Object object) {
        if ((n & 1) != 0) {
            d = vector2d.a;
        }
        if ((n & 2) != 0) {
            d2 = vector2d.b;
        }
        return vector2d.copy(d, d2);
    }

    @NotNull
    public String toString() {
        return "Vector2d(a=" + this.a + ", b=" + this.b + ")";
    }

    public int hashCode() {
        int result = Double.hashCode(this.a);
        result = result * 31 + Double.hashCode(this.b);
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Vector2d)) {
            return false;
        }
        Vector2d vector2d = (Vector2d)other;
        if (Double.compare(this.a, vector2d.a) != 0) {
            return false;
        }
        return Double.compare(this.b, vector2d.b) == 0;
    }
}

