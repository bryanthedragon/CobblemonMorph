/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.egg;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0010\u000e\n\u0002\b\u0016\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0011\b\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR\u001a\u0010\u0003\u001a\u00020\u00028\u0000X\u0080\u0004\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017\u00a8\u0006\u0018"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/egg/EggGroup;", "", "", "showdownID", "Ljava/lang/String;", "getShowdownID$common", "()Ljava/lang/String;", "<init>", "(Ljava/lang/String;ILjava/lang/String;)V", "MONSTER", "WATER_1", "BUG", "FLYING", "FIELD", "FAIRY", "GRASS", "HUMAN_LIKE", "WATER_3", "MINERAL", "AMORPHOUS", "WATER_2", "DITTO", "DRAGON", "UNDISCOVERED", "common"})
public final class EggGroup
extends Enum<EggGroup> {
    @NotNull
    private final String showdownID;
    public static final /* enum */ EggGroup MONSTER = new EggGroup("Monster");
    public static final /* enum */ EggGroup WATER_1 = new EggGroup("Water 1");
    public static final /* enum */ EggGroup BUG = new EggGroup("Bug");
    public static final /* enum */ EggGroup FLYING = new EggGroup("Flying");
    public static final /* enum */ EggGroup FIELD = new EggGroup("Field");
    public static final /* enum */ EggGroup FAIRY = new EggGroup("Fairy");
    public static final /* enum */ EggGroup GRASS = new EggGroup("Grass");
    public static final /* enum */ EggGroup HUMAN_LIKE = new EggGroup("Human-Like");
    public static final /* enum */ EggGroup WATER_3 = new EggGroup("Water 3");
    public static final /* enum */ EggGroup MINERAL = new EggGroup("Mineral");
    public static final /* enum */ EggGroup AMORPHOUS = new EggGroup("Amorphous");
    public static final /* enum */ EggGroup WATER_2 = new EggGroup("Water 2");
    public static final /* enum */ EggGroup DITTO = new EggGroup("Ditto");
    public static final /* enum */ EggGroup DRAGON = new EggGroup("Dragon");
    public static final /* enum */ EggGroup UNDISCOVERED = new EggGroup("Undiscovered");
    private static final /* synthetic */ EggGroup[] $VALUES;

    private EggGroup(String showdownID) {
        this.showdownID = showdownID;
    }

    @NotNull
    public final String getShowdownID$common() {
        return this.showdownID;
    }

    public static EggGroup[] values() {
        return (EggGroup[])$VALUES.clone();
    }

    public static EggGroup valueOf(String value2) {
        return Enum.valueOf(EggGroup.class, value2);
    }

    static {
        $VALUES = eggGroupArray = new EggGroup[]{EggGroup.MONSTER, EggGroup.WATER_1, EggGroup.BUG, EggGroup.FLYING, EggGroup.FIELD, EggGroup.FAIRY, EggGroup.GRASS, EggGroup.HUMAN_LIKE, EggGroup.WATER_3, EggGroup.MINERAL, EggGroup.AMORPHOUS, EggGroup.WATER_2, EggGroup.DITTO, EggGroup.DRAGON, EggGroup.UNDISCOVERED};
    }
}

