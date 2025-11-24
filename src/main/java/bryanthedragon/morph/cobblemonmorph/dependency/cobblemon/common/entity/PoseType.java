/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity;

import java.util.EnumSet;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0011\b\u0086\u0001\u0018\u0000 \u00042\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0004B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011\u00a8\u0006\u0012"}, d2={"Lcom/cobblemon/mod/common/entity/PoseType;", "", "<init>", "(Ljava/lang/String;I)V", "Companion", "STAND", "WALK", "SLEEP", "HOVER", "FLY", "FLOAT", "SWIM", "SHOULDER_LEFT", "SHOULDER_RIGHT", "PROFILE", "PORTRAIT", "OPEN", "NONE", "common"})
public final class PoseType
extends Enum<PoseType> {
    @NotNull
    public static final Companion Companion;
    private static final EnumSet<PoseType> ALL_POSES;
    private static final EnumSet<PoseType> FLYING_POSES;
    private static final EnumSet<PoseType> SWIMMING_POSES;
    private static final EnumSet<PoseType> STANDING_POSES;
    private static final EnumSet<PoseType> SHOULDER_POSES;
    private static final EnumSet<PoseType> UI_POSES;
    private static final EnumSet<PoseType> MOVING_POSES;
    private static final EnumSet<PoseType> STATIONARY_POSES;
    public static final /* enum */ PoseType STAND;
    public static final /* enum */ PoseType WALK;
    public static final /* enum */ PoseType SLEEP;
    public static final /* enum */ PoseType HOVER;
    public static final /* enum */ PoseType FLY;
    public static final /* enum */ PoseType FLOAT;
    public static final /* enum */ PoseType SWIM;
    public static final /* enum */ PoseType SHOULDER_LEFT;
    public static final /* enum */ PoseType SHOULDER_RIGHT;
    public static final /* enum */ PoseType PROFILE;
    public static final /* enum */ PoseType PORTRAIT;
    public static final /* enum */ PoseType OPEN;
    public static final /* enum */ PoseType NONE;
    private static final /* synthetic */ PoseType[] $VALUES;

    public static PoseType[] values() {
        return (PoseType[])$VALUES.clone();
    }

    public static PoseType valueOf(String value2) {
        return Enum.valueOf(PoseType.class, value2);
    }

    static {
        STAND = new PoseType();
        WALK = new PoseType();
        SLEEP = new PoseType();
        HOVER = new PoseType();
        FLY = new PoseType();
        FLOAT = new PoseType();
        SWIM = new PoseType();
        SHOULDER_LEFT = new PoseType();
        SHOULDER_RIGHT = new PoseType();
        PROFILE = new PoseType();
        PORTRAIT = new PoseType();
        OPEN = new PoseType();
        NONE = new PoseType();
        $VALUES = poseTypeArray = new PoseType[]{PoseType.STAND, PoseType.WALK, PoseType.SLEEP, PoseType.HOVER, PoseType.FLY, PoseType.FLOAT, PoseType.SWIM, PoseType.SHOULDER_LEFT, PoseType.SHOULDER_RIGHT, PoseType.PROFILE, PoseType.PORTRAIT, PoseType.OPEN, PoseType.NONE};
        Companion = new Companion(null);
        ALL_POSES = EnumSet.allOf(PoseType.class);
        FLYING_POSES = EnumSet.of((Enum)FLY, (Enum)HOVER);
        SWIMMING_POSES = EnumSet.of((Enum)SWIM, (Enum)FLOAT);
        STANDING_POSES = EnumSet.of((Enum)STAND, (Enum)WALK);
        SHOULDER_POSES = EnumSet.of((Enum)SHOULDER_LEFT, (Enum)SHOULDER_RIGHT);
        UI_POSES = EnumSet.of((Enum)PROFILE, (Enum)PORTRAIT);
        MOVING_POSES = EnumSet.of((Enum)WALK, (Enum)SWIM, (Enum)FLY);
        STATIONARY_POSES = EnumSet.of((Enum)STAND, (Enum)FLOAT, (Enum)HOVER);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0016\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0017\u0010\u0018R;\u0010\u0005\u001a&\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u0003 \u0004*\u0012\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u0003\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0007\u0010\bR;\u0010\t\u001a&\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u0003 \u0004*\u0012\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u0003\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\u0006\u001a\u0004\b\n\u0010\bR;\u0010\u000b\u001a&\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u0003 \u0004*\u0012\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u0003\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\u0006\u001a\u0004\b\f\u0010\bR;\u0010\r\u001a&\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u0003 \u0004*\u0012\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u0003\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u0006\u001a\u0004\b\u000e\u0010\bR;\u0010\u000f\u001a&\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u0003 \u0004*\u0012\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u0003\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0006\u001a\u0004\b\u0010\u0010\bR;\u0010\u0011\u001a&\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u0003 \u0004*\u0012\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u0003\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0006\u001a\u0004\b\u0012\u0010\bR;\u0010\u0013\u001a&\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u0003 \u0004*\u0012\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u0003\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0006\u001a\u0004\b\u0014\u0010\bR;\u0010\u0015\u001a&\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u0003 \u0004*\u0012\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u0003\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0015\u0010\u0006\u001a\u0004\b\u0016\u0010\b\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/entity/PoseType$Companion;", "", "Ljava/util/EnumSet;", "Lcom/cobblemon/mod/common/entity/PoseType;", "kotlin.jvm.PlatformType", "ALL_POSES", "Ljava/util/EnumSet;", "getALL_POSES", "()Ljava/util/EnumSet;", "FLYING_POSES", "getFLYING_POSES", "MOVING_POSES", "getMOVING_POSES", "SHOULDER_POSES", "getSHOULDER_POSES", "STANDING_POSES", "getSTANDING_POSES", "STATIONARY_POSES", "getSTATIONARY_POSES", "SWIMMING_POSES", "getSWIMMING_POSES", "UI_POSES", "getUI_POSES", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public final EnumSet<PoseType> getALL_POSES() {
            return ALL_POSES;
        }

        public final EnumSet<PoseType> getFLYING_POSES() {
            return FLYING_POSES;
        }

        public final EnumSet<PoseType> getSWIMMING_POSES() {
            return SWIMMING_POSES;
        }

        public final EnumSet<PoseType> getSTANDING_POSES() {
            return STANDING_POSES;
        }

        public final EnumSet<PoseType> getSHOULDER_POSES() {
            return SHOULDER_POSES;
        }

        public final EnumSet<PoseType> getUI_POSES() {
            return UI_POSES;
        }

        public final EnumSet<PoseType> getMOVING_POSES() {
            return MOVING_POSES;
        }

        public final EnumSet<PoseType> getSTATIONARY_POSES() {
            return STATIONARY_POSES;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

