/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.lighthing;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\n\b\u0086\b\u0018\u00002\u00020\u0001:\u0001\u001aB\u0017\u0012\u0006\u0010\b\u001a\u00020\u0002\u0012\u0006\u0010\t\u001a\u00020\u0005\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J$\u0010\n\u001a\u00020\u00002\b\b\u0002\u0010\b\u001a\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\u0005H\u00c6\u0001\u00a2\u0006\u0004\b\n\u0010\u000bJ\u001a\u0010\u000e\u001a\u00020\r2\b\u0010\f\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0010\u0010\u0010\u001a\u00020\u0002H\u00d6\u0001\u00a2\u0006\u0004\b\u0010\u0010\u0004J\u0010\u0010\u0012\u001a\u00020\u0011H\u00d6\u0001\u00a2\u0006\u0004\b\u0012\u0010\u0013R\u0017\u0010\b\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\u0014\u001a\u0004\b\u0015\u0010\u0004R\u0017\u0010\t\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\u0016\u001a\u0004\b\u0017\u0010\u0007\u00a8\u0006\u001b"}, d2={"Lcom/cobblemon/mod/common/pokemon/lighthing/LightingData;", "", "", "component1", "()I", "Lcom/cobblemon/mod/common/pokemon/lighthing/LightingData$LiquidGlowMode;", "component2", "()Lcom/cobblemon/mod/common/pokemon/lighthing/LightingData$LiquidGlowMode;", "lightLevel", "liquidGlowMode", "copy", "(ILcom/cobblemon/mod/common/pokemon/lighthing/LightingData$LiquidGlowMode;)Lcom/cobblemon/mod/common/pokemon/lighthing/LightingData;", "other", "", "equals", "(Ljava/lang/Object;)Z", "hashCode", "", "toString", "()Ljava/lang/String;", "I", "getLightLevel", "Lcom/cobblemon/mod/common/pokemon/lighthing/LightingData$LiquidGlowMode;", "getLiquidGlowMode", "<init>", "(ILcom/cobblemon/mod/common/pokemon/lighthing/LightingData$LiquidGlowMode;)V", "LiquidGlowMode", "common"})
public final class LightingData {
    private final int lightLevel;
    @NotNull
    private final LiquidGlowMode liquidGlowMode;

    public LightingData(int lightLevel, @NotNull LiquidGlowMode liquidGlowMode) {
        Intrinsics.checkNotNullParameter((Object)((Object)liquidGlowMode), (String)"liquidGlowMode");
        this.lightLevel = lightLevel;
        this.liquidGlowMode = liquidGlowMode;
    }

    public final int getLightLevel() {
        return this.lightLevel;
    }

    @NotNull
    public final LiquidGlowMode getLiquidGlowMode() {
        return this.liquidGlowMode;
    }

    public final int component1() {
        return this.lightLevel;
    }

    @NotNull
    public final LiquidGlowMode component2() {
        return this.liquidGlowMode;
    }

    @NotNull
    public final LightingData copy(int lightLevel, @NotNull LiquidGlowMode liquidGlowMode) {
        Intrinsics.checkNotNullParameter((Object)((Object)liquidGlowMode), (String)"liquidGlowMode");
        return new LightingData(lightLevel, liquidGlowMode);
    }

    public static /* synthetic */ LightingData copy$default(LightingData lightingData, int n, LiquidGlowMode liquidGlowMode, int n2, Object object) {
        if ((n2 & 1) != 0) {
            n = lightingData.lightLevel;
        }
        if ((n2 & 2) != 0) {
            liquidGlowMode = lightingData.liquidGlowMode;
        }
        return lightingData.copy(n, liquidGlowMode);
    }

    @NotNull
    public String toString() {
        return "LightingData(lightLevel=" + this.lightLevel + ", liquidGlowMode=" + this.liquidGlowMode + ")";
    }

    public int hashCode() {
        int result = Integer.hashCode(this.lightLevel);
        result = result * 31 + this.liquidGlowMode.hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LightingData)) {
            return false;
        }
        LightingData lightingData = (LightingData)other;
        if (this.lightLevel != lightingData.lightLevel) {
            return false;
        }
        return this.liquidGlowMode == lightingData.liquidGlowMode;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0010\u000b\n\u0002\b\f\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0019\b\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0007\u001a\u00020\u0002\u00a2\u0006\u0004\b\t\u0010\nR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\u0004\u001a\u0004\b\b\u0010\u0006j\u0002\b\u000bj\u0002\b\fj\u0002\b\r\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/pokemon/lighthing/LightingData$LiquidGlowMode;", "", "", "glowsInLand", "Z", "getGlowsInLand", "()Z", "glowsUnderwater", "getGlowsUnderwater", "<init>", "(Ljava/lang/String;IZZ)V", "LAND", "UNDERWATER", "BOTH", "common"})
    public static final class LiquidGlowMode
    extends Enum<LiquidGlowMode> {
        private final boolean glowsInLand;
        private final boolean glowsUnderwater;
        public static final /* enum */ LiquidGlowMode LAND = new LiquidGlowMode(true, false);
        public static final /* enum */ LiquidGlowMode UNDERWATER = new LiquidGlowMode(false, true);
        public static final /* enum */ LiquidGlowMode BOTH = new LiquidGlowMode(true, true);
        private static final /* synthetic */ LiquidGlowMode[] $VALUES;

        private LiquidGlowMode(boolean glowsInLand, boolean glowsUnderwater) {
            this.glowsInLand = glowsInLand;
            this.glowsUnderwater = glowsUnderwater;
        }

        public final boolean getGlowsInLand() {
            return this.glowsInLand;
        }

        public final boolean getGlowsUnderwater() {
            return this.glowsUnderwater;
        }

        public static LiquidGlowMode[] values() {
            return (LiquidGlowMode[])$VALUES.clone();
        }

        public static LiquidGlowMode valueOf(String value2) {
            return Enum.valueOf(LiquidGlowMode.class, value2);
        }

        static {
            $VALUES = liquidGlowModeArray = new LiquidGlowMode[]{LiquidGlowMode.LAND, LiquidGlowMode.UNDERWATER, LiquidGlowMode.BOTH};
        }
    }
}

