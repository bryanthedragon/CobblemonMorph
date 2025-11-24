/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.NoWhenBranchMatchedException
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 \u000e2\u00020\u0001:\u0002\u000f\u000eB\u0007\u00a2\u0006\u0004\b\f\u0010\rJ\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0010"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/requirements/AttackDefenceRatioRequirement;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/requirement/EvolutionRequirement;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "", "check", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "Lcom/cobblemon/mod/common/pokemon/evolution/requirements/AttackDefenceRatioRequirement$AttackDefenceRatio;", "ratio", "Lcom/cobblemon/mod/common/pokemon/evolution/requirements/AttackDefenceRatioRequirement$AttackDefenceRatio;", "getRatio", "()Lcom/cobblemon/mod/common/pokemon/evolution/requirements/AttackDefenceRatioRequirement$AttackDefenceRatio;", "<init>", "()V", "Companion", "AttackDefenceRatio", "common"})
public final class AttackDefenceRatioRequirement
implements EvolutionRequirement {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final AttackDefenceRatio ratio = AttackDefenceRatio.ATTACK_HIGHER;
    @NotNull
    public static final String ADAPTER_VARIANT = "attack_defence_ratio";

    @NotNull
    public final AttackDefenceRatio getRatio() {
        return this.ratio;
    }

    @Override
    public boolean check(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        return switch (WhenMappings.$EnumSwitchMapping$0[this.ratio.ordinal()]) {
            case 1 -> {
                if (pokemon.getAttack() > pokemon.getDefence()) {
                    yield true;
                }
                yield false;
            }
            case 2 -> {
                if (pokemon.getDefence() > pokemon.getAttack()) {
                    yield true;
                }
                yield false;
            }
            case 3 -> {
                if (pokemon.getAttack() == pokemon.getDefence()) {
                    yield true;
                }
                yield false;
            }
            default -> throw new NoWhenBranchMatchedException();
        };
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006\u00a8\u0006\u0007"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/requirements/AttackDefenceRatioRequirement$AttackDefenceRatio;", "", "<init>", "(Ljava/lang/String;I)V", "ATTACK_HIGHER", "DEFENCE_HIGHER", "EQUAL", "common"})
    public static final class AttackDefenceRatio
    extends Enum<AttackDefenceRatio> {
        public static final /* enum */ AttackDefenceRatio ATTACK_HIGHER = new AttackDefenceRatio();
        public static final /* enum */ AttackDefenceRatio DEFENCE_HIGHER = new AttackDefenceRatio();
        public static final /* enum */ AttackDefenceRatio EQUAL = new AttackDefenceRatio();
        private static final /* synthetic */ AttackDefenceRatio[] $VALUES;

        public static AttackDefenceRatio[] values() {
            return (AttackDefenceRatio[])$VALUES.clone();
        }

        public static AttackDefenceRatio valueOf(String value2) {
            return Enum.valueOf(AttackDefenceRatio.class, value2);
        }

        static {
            $VALUES = attackDefenceRatioArray = new AttackDefenceRatio[]{AttackDefenceRatio.ATTACK_HIGHER, AttackDefenceRatio.DEFENCE_HIGHER, AttackDefenceRatio.EQUAL};
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0007"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/requirements/AttackDefenceRatioRequirement$Companion;", "", "", "ADAPTER_VARIANT", "Ljava/lang/String;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[AttackDefenceRatio.values().length];
            try {
                nArray[AttackDefenceRatio.ATTACK_HIGHER.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[AttackDefenceRatio.DEFENCE_HIGHER.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[AttackDefenceRatio.EQUAL.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

