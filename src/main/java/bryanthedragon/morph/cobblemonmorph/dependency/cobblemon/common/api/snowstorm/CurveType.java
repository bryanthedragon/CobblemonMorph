/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm;

import kotlin.Metadata;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007\u00a8\u0006\b"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/CurveType;", "", "<init>", "(Ljava/lang/String;I)V", "LINEAR", "BEZIER", "CATMULL_ROM", "BEZIER_CHAIN", "common"})
public final class CurveType
extends Enum<CurveType> {
    public static final /* enum */ CurveType LINEAR = new CurveType();
    public static final /* enum */ CurveType BEZIER = new CurveType();
    public static final /* enum */ CurveType CATMULL_ROM = new CurveType();
    public static final /* enum */ CurveType BEZIER_CHAIN = new CurveType();
    private static final /* synthetic */ CurveType[] $VALUES;

    public static CurveType[] values() {
        return (CurveType[])$VALUES.clone();
    }

    public static CurveType valueOf(String value2) {
        return Enum.valueOf(CurveType.class, value2);
    }

    static {
        $VALUES = curveTypeArray = new CurveType[]{CurveType.LINEAR, CurveType.BEZIER, CurveType.CATMULL_ROM, CurveType.BEZIER_CHAIN};
    }
}

