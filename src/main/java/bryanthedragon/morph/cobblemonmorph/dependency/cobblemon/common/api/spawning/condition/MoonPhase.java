/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.level.Level
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\f\b\u0086\u0001\u0018\u0000 \u00042\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0004B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\f\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/api/spawning/condition/MoonPhase;", "", "<init>", "(Ljava/lang/String;I)V", "Companion", "FULL_MOON", "WANING_GIBBOUS", "THIRD_QUARTER", "WANING_CRESCENT", "NEW_MOON", "WAXING_CRESCENT", "FIRST_QUARTER", "WAXING_GIBBOUS", "common"})
public final class MoonPhase
extends Enum<MoonPhase> {
    @NotNull
    public static final Companion Companion;
    @NotNull
    private static final MoonPhase[] VALUES;
    public static final /* enum */ MoonPhase FULL_MOON;
    public static final /* enum */ MoonPhase WANING_GIBBOUS;
    public static final /* enum */ MoonPhase THIRD_QUARTER;
    public static final /* enum */ MoonPhase WANING_CRESCENT;
    public static final /* enum */ MoonPhase NEW_MOON;
    public static final /* enum */ MoonPhase WAXING_CRESCENT;
    public static final /* enum */ MoonPhase FIRST_QUARTER;
    public static final /* enum */ MoonPhase WAXING_GIBBOUS;
    private static final /* synthetic */ MoonPhase[] $VALUES;

    public static MoonPhase[] values() {
        return (MoonPhase[])$VALUES.clone();
    }

    public static MoonPhase valueOf(String value2) {
        return Enum.valueOf(MoonPhase.class, value2);
    }

    static {
        FULL_MOON = new MoonPhase();
        WANING_GIBBOUS = new MoonPhase();
        THIRD_QUARTER = new MoonPhase();
        WANING_CRESCENT = new MoonPhase();
        NEW_MOON = new MoonPhase();
        WAXING_CRESCENT = new MoonPhase();
        FIRST_QUARTER = new MoonPhase();
        WAXING_GIBBOUS = new MoonPhase();
        $VALUES = moonPhaseArray = new MoonPhase[]{MoonPhase.FULL_MOON, MoonPhase.WANING_GIBBOUS, MoonPhase.THIRD_QUARTER, MoonPhase.WANING_CRESCENT, MoonPhase.NEW_MOON, MoonPhase.WAXING_CRESCENT, MoonPhase.FIRST_QUARTER, MoonPhase.WAXING_GIBBOUS};
        Companion = new Companion(null);
        VALUES = MoonPhase.values();
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u00078\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\b\u0010\t\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/api/spawning/condition/MoonPhase$Companion;", "", "Lnet/minecraft/world/level/Level;", "world", "Lcom/cobblemon/mod/common/api/spawning/condition/MoonPhase;", "ofWorld", "(Lnet/minecraft/world/level/Level;)Lcom/cobblemon/mod/common/api/spawning/condition/MoonPhase;", "", "VALUES", "[Lcom/cobblemon/mod/common/api/spawning/condition/MoonPhase;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final MoonPhase ofWorld(@NotNull Level world) {
            Intrinsics.checkNotNullParameter((Object)world, (String)"world");
            return VALUES[world.m_46941_()];
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

