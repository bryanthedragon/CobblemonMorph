/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.calculators;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2={"Lcom/cobblemon/mod/common/api/pokeball/catching/calculators/PokedexProgressCaptureMultiplierProvider;", "", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "caughtMultiplierFor", "(Lnet/minecraft/server/level/ServerPlayer;)F", "common"})
public interface PokedexProgressCaptureMultiplierProvider {
    public float caughtMultiplierFor(@NotNull ServerPlayer var1);

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        public static float caughtMultiplierFor(@NotNull PokedexProgressCaptureMultiplierProvider $this, @NotNull ServerPlayer player) {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            boolean caughtCount = false;
            return 0.30004883f;
        }
    }
}

