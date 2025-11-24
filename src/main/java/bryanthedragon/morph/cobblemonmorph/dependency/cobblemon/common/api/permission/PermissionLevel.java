/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission;

import kotlin.Metadata;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0010\b\n\u0002\b\f\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0011\b\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\r\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/api/permission/PermissionLevel;", "", "", "numericalValue", "I", "getNumericalValue", "()I", "<init>", "(Ljava/lang/String;II)V", "NONE", "SPAWN_PROTECTION_BYPASS", "CHEAT_COMMANDS_AND_COMMAND_BLOCKS", "MULTIPLAYER_MANAGEMENT", "ALL_COMMANDS", "common"})
public final class PermissionLevel
extends Enum<PermissionLevel> {
    private final int numericalValue;
    public static final /* enum */ PermissionLevel NONE = new PermissionLevel(0);
    public static final /* enum */ PermissionLevel SPAWN_PROTECTION_BYPASS = new PermissionLevel(1);
    public static final /* enum */ PermissionLevel CHEAT_COMMANDS_AND_COMMAND_BLOCKS = new PermissionLevel(2);
    public static final /* enum */ PermissionLevel MULTIPLAYER_MANAGEMENT = new PermissionLevel(3);
    public static final /* enum */ PermissionLevel ALL_COMMANDS = new PermissionLevel(4);
    private static final /* synthetic */ PermissionLevel[] $VALUES;

    private PermissionLevel(int numericalValue) {
        this.numericalValue = numericalValue;
    }

    public final int getNumericalValue() {
        return this.numericalValue;
    }

    public static PermissionLevel[] values() {
        return (PermissionLevel[])$VALUES.clone();
    }

    public static PermissionLevel valueOf(String value2) {
        return Enum.valueOf(PermissionLevel.class, value2);
    }

    static {
        $VALUES = permissionLevelArray = new PermissionLevel[]{PermissionLevel.NONE, PermissionLevel.SPAWN_PROTECTION_BYPASS, PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS, PermissionLevel.MULTIPLAYER_MANAGEMENT, PermissionLevel.ALL_COMMANDS};
    }
}

