/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.feature;

import java.util.EnumSet;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\b\b\u0086\u0001\u0018\u0000 \u00042\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0004B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\b\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/pokemon/feature/CobblemonSeason;", "", "<init>", "(Ljava/lang/String;I)V", "Companion", "SPRING", "AUTUMN", "SUMMER", "WINTER", "common"})
public final class CobblemonSeason
extends Enum<CobblemonSeason> {
    @NotNull
    public static final Companion Companion;
    private static final EnumSet<CobblemonSeason> ALL_VALUES;
    public static final /* enum */ CobblemonSeason SPRING;
    public static final /* enum */ CobblemonSeason AUTUMN;
    public static final /* enum */ CobblemonSeason SUMMER;
    public static final /* enum */ CobblemonSeason WINTER;
    private static final /* synthetic */ CobblemonSeason[] $VALUES;

    public static CobblemonSeason[] values() {
        return (CobblemonSeason[])$VALUES.clone();
    }

    public static CobblemonSeason valueOf(String value2) {
        return Enum.valueOf(CobblemonSeason.class, value2);
    }

    static {
        SPRING = new CobblemonSeason();
        AUTUMN = new CobblemonSeason();
        SUMMER = new CobblemonSeason();
        WINTER = new CobblemonSeason();
        $VALUES = cobblemonSeasonArray = new CobblemonSeason[]{CobblemonSeason.SPRING, CobblemonSeason.AUTUMN, CobblemonSeason.SUMMER, CobblemonSeason.WINTER};
        Companion = new Companion(null);
        ALL_VALUES = EnumSet.allOf(CobblemonSeason.class);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nR;\u0010\u0005\u001a&\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u0003 \u0004*\u0012\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u0003\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/pokemon/feature/CobblemonSeason$Companion;", "", "Ljava/util/EnumSet;", "Lcom/cobblemon/mod/common/pokemon/feature/CobblemonSeason;", "kotlin.jvm.PlatformType", "ALL_VALUES", "Ljava/util/EnumSet;", "getALL_VALUES", "()Ljava/util/EnumSet;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public final EnumSet<CobblemonSeason> getALL_VALUES() {
            return ALL_VALUES;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

