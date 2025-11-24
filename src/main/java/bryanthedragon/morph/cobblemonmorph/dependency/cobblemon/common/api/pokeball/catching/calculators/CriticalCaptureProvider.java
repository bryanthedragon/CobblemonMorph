/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.math.MathKt
 *  kotlin.random.Random
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.calculators;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.random.Random;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\b\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/api/pokeball/catching/calculators/CriticalCaptureProvider;", "", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "modifiedCatchRate", "", "shouldHaveCriticalCapture", "(Lnet/minecraft/server/level/ServerPlayer;F)Z", "common"})
public interface CriticalCaptureProvider {
    public boolean shouldHaveCriticalCapture(@NotNull ServerPlayer var1, float var2);

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        public static boolean shouldHaveCriticalCapture(@NotNull CriticalCaptureProvider $this, @NotNull ServerPlayer player, float modifiedCatchRate) {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            boolean caughtCount = false;
            float caughtMultiplier = 0.0f;
            float b = modifiedCatchRate * caughtMultiplier;
            int c = MathKt.roundToInt((float)(b * 1.0f / 6.0f));
            return Random.Default.nextInt(256) < c;
        }
    }
}

