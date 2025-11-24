/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.util.StringRepresentable
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.mulch;

import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0010\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u00012\u00020\u0002B\u0013\b\u0002\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\u0004\b\u000b\u0010\fJ\u000f\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005R\u0017\u0010\u0007\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\t\u0010\nj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015\u00a8\u0006\u0016"}, d2={"Lcom/cobblemon/mod/common/api/mulch/MulchVariant;", "", "Lnet/minecraft/util/StringRepresentable;", "", "asString", "()Ljava/lang/String;", "", "duration", "I", "getDuration", "()I", "<init>", "(Ljava/lang/String;II)V", "COARSE", "GROWTH", "HUMID", "LOAMY", "PEAT", "RICH", "SANDY", "SURPRISE", "NONE", "common"})
public final class MulchVariant
extends Enum<MulchVariant>
implements StringRepresentable {
    private final int duration;
    public static final /* enum */ MulchVariant COARSE = new MulchVariant("COARSE", 0, 0, 1, null);
    public static final /* enum */ MulchVariant GROWTH = new MulchVariant(3);
    public static final /* enum */ MulchVariant HUMID = new MulchVariant("HUMID", 2, 0, 1, null);
    public static final /* enum */ MulchVariant LOAMY = new MulchVariant("LOAMY", 3, 0, 1, null);
    public static final /* enum */ MulchVariant PEAT = new MulchVariant("PEAT", 4, 0, 1, null);
    public static final /* enum */ MulchVariant RICH = new MulchVariant(3);
    public static final /* enum */ MulchVariant SANDY = new MulchVariant("SANDY", 6, 0, 1, null);
    public static final /* enum */ MulchVariant SURPRISE = new MulchVariant(3);
    public static final /* enum */ MulchVariant NONE = new MulchVariant("NONE", 8, 0, 1, null);
    private static final /* synthetic */ MulchVariant[] $VALUES;

    private MulchVariant(int duration) {
        this.duration = duration;
    }

    /* synthetic */ MulchVariant(String string, int n, int n2, int n3, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n3 & 1) != 0) {
            n2 = -1;
        }
        this(n2);
    }

    public final int getDuration() {
        return this.duration;
    }

    @NotNull
    public String m_7912_() {
        String string = this.name().toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
        return string;
    }

    public static MulchVariant[] values() {
        return (MulchVariant[])$VALUES.clone();
    }

    public static MulchVariant valueOf(String value2) {
        return Enum.valueOf(MulchVariant.class, value2);
    }

    static {
        $VALUES = mulchVariantArray = new MulchVariant[]{MulchVariant.COARSE, MulchVariant.GROWTH, MulchVariant.HUMID, MulchVariant.LOAMY, MulchVariant.PEAT, MulchVariant.RICH, MulchVariant.SANDY, MulchVariant.SURPRISE, MulchVariant.NONE};
    }
}

