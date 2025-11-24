/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0010\u000e\n\u0002\b\n\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0011\b\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\tj\u0002\b\nj\u0002\b\u000b\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/pokemon/Gender;", "", "", "showdownName", "Ljava/lang/String;", "getShowdownName", "()Ljava/lang/String;", "<init>", "(Ljava/lang/String;ILjava/lang/String;)V", "MALE", "FEMALE", "GENDERLESS", "common"})
public final class Gender
extends Enum<Gender> {
    @NotNull
    private final String showdownName;
    public static final /* enum */ Gender MALE = new Gender("M");
    public static final /* enum */ Gender FEMALE = new Gender("F");
    public static final /* enum */ Gender GENDERLESS = new Gender("N");
    private static final /* synthetic */ Gender[] $VALUES;

    private Gender(String showdownName) {
        this.showdownName = showdownName;
    }

    @NotNull
    public final String getShowdownName() {
        return this.showdownName;
    }

    public static Gender[] values() {
        return (Gender[])$VALUES.clone();
    }

    public static Gender valueOf(String value2) {
        return Enum.valueOf(Gender.class, value2);
    }

    static {
        $VALUES = genderArray = new Gender[]{Gender.MALE, Gender.FEMALE, Gender.GENDERLESS};
    }
}

