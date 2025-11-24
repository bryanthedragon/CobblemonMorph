/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleStartError;
import kotlin.Metadata;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u00012\u00020\u0002B\t\b\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0005"}, d2={"Lcom/cobblemon/mod/common/battles/CommonBattleStartError;", "", "Lcom/cobblemon/mod/common/battles/BattleStartError;", "<init>", "(Ljava/lang/String;I)V", "common"})
public abstract class CommonBattleStartError
extends Enum<CommonBattleStartError>
implements BattleStartError {
    private static final /* synthetic */ CommonBattleStartError[] $VALUES;

    public static CommonBattleStartError[] values() {
        return (CommonBattleStartError[])$VALUES.clone();
    }

    public static CommonBattleStartError valueOf(String value2) {
        return Enum.valueOf(CommonBattleStartError.class, value2);
    }

    private static final /* synthetic */ CommonBattleStartError[] $values() {
        return new CommonBattleStartError[0];
    }

    static {
        $VALUES = CommonBattleStartError.$values();
    }
}

