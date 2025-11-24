/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.wheel;

import kotlin.Metadata;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007\u00a8\u0006\b"}, d2={"Lcom/cobblemon/mod/common/client/gui/interact/wheel/Orientation;", "", "<init>", "(Ljava/lang/String;I)V", "TOP_LEFT", "TOP_RIGHT", "BOTTOM_LEFT", "BOTTOM_RIGHT", "common"})
public final class Orientation
extends Enum<Orientation> {
    public static final /* enum */ Orientation TOP_LEFT = new Orientation();
    public static final /* enum */ Orientation TOP_RIGHT = new Orientation();
    public static final /* enum */ Orientation BOTTOM_LEFT = new Orientation();
    public static final /* enum */ Orientation BOTTOM_RIGHT = new Orientation();
    private static final /* synthetic */ Orientation[] $VALUES;

    public static Orientation[] values() {
        return (Orientation[])$VALUES.clone();
    }

    public static Orientation valueOf(String value2) {
        return Enum.valueOf(Orientation.class, value2);
    }

    static {
        $VALUES = orientationArray = new Orientation[]{Orientation.TOP_LEFT, Orientation.TOP_RIGHT, Orientation.BOTTOM_LEFT, Orientation.BOTTOM_RIGHT};
    }
}

