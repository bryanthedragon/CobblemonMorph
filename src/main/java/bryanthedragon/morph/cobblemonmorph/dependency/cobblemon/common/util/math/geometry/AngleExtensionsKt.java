/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.geometry;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\f\n\u0002\u0010\u0004\n\u0002\u0010\u0007\n\u0002\b\u0006\u001a\u0011\u0010\u0002\u001a\u00020\u0001*\u00020\u0000\u00a2\u0006\u0004\b\u0002\u0010\u0003\u001a\u0011\u0010\u0004\u001a\u00020\u0001*\u00020\u0000\u00a2\u0006\u0004\b\u0004\u0010\u0003\"\u0014\u0010\u0005\u001a\u00020\u00018\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2={"", "", "toDegrees", "(Ljava/lang/Number;)F", "toRadians", "RADIAN_IN_DEGREES", "F", "common"})
public final class AngleExtensionsKt {
    private static final float RADIAN_IN_DEGREES = 57.2958f;

    public static final float toRadians(@NotNull Number $this$toRadians) {
        Intrinsics.checkNotNullParameter((Object)$this$toRadians, (String)"<this>");
        return $this$toRadians.floatValue() / 57.2958f;
    }

    public static final float toDegrees(@NotNull Number $this$toDegrees) {
        Intrinsics.checkNotNullParameter((Object)$this$toDegrees, (String)"<this>");
        return $this$toDegrees.floatValue() * 57.2958f;
    }
}

