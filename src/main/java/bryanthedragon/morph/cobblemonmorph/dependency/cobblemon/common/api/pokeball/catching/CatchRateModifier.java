/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.entity.LivingEntity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\b\bf\u0018\u0000 \u00142\u00020\u0001:\u0002\u0015\u0014J\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H&\u00a2\u0006\u0004\b\u0007\u0010\bJ\u000f\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ\u001f\u0010\f\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H&\u00a2\u0006\u0004\b\f\u0010\rJ'\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H&\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u001f\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H&\u00a2\u0006\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0016"}, d2={"Lcom/cobblemon/mod/common/api/pokeball/catching/CatchRateModifier;", "", "Lnet/minecraft/world/entity/LivingEntity;", "thrower", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lcom/cobblemon/mod/common/api/pokeball/catching/CatchRateModifier$Behavior;", "behavior", "(Lnet/minecraft/world/entity/LivingEntity;Lcom/cobblemon/mod/common/pokemon/Pokemon;)Lcom/cobblemon/mod/common/api/pokeball/catching/CatchRateModifier$Behavior;", "", "isGuaranteed", "()Z", "isValid", "(Lnet/minecraft/world/entity/LivingEntity;Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "", "currentCatchRate", "modifyCatchRate", "(FLnet/minecraft/world/entity/LivingEntity;Lcom/cobblemon/mod/common/pokemon/Pokemon;)F", "value", "(Lnet/minecraft/world/entity/LivingEntity;Lcom/cobblemon/mod/common/pokemon/Pokemon;)F", "Companion", "Behavior", "common"})
public interface CatchRateModifier {
    @NotNull
    public static final Companion Companion = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CatchRateModifier$Companion.$$INSTANCE;

    public boolean isGuaranteed();

    public float value(@NotNull LivingEntity var1, @NotNull Pokemon var2);

    @NotNull
    public Behavior behavior(@NotNull LivingEntity var1, @NotNull Pokemon var2);

    public boolean isValid(@NotNull LivingEntity var1, @NotNull Pokemon var2);

    public float modifyCatchRate(float var1, @NotNull LivingEntity var2, @NotNull Pokemon var3);

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\u0018\u0002\n\u0002\b\u000e\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001BA\b\u0002\u00126\u0010\b\u001a2\u0012\u0013\u0012\u00110\u0003\u00a2\u0006\f\b\u0004\u0012\b\b\u0005\u0012\u0004\b\b(\u0006\u0012\u0013\u0012\u00110\u0003\u00a2\u0006\f\b\u0004\u0012\b\b\u0005\u0012\u0004\b\b(\u0007\u0012\u0004\u0012\u00020\u00030\u0002\u00a2\u0006\u0004\b\f\u0010\rRG\u0010\b\u001a2\u0012\u0013\u0012\u00110\u0003\u00a2\u0006\f\b\u0004\u0012\b\b\u0005\u0012\u0004\b\b(\u0006\u0012\u0013\u0012\u00110\u0003\u00a2\u0006\f\b\u0004\u0012\b\b\u0005\u0012\u0004\b\b(\u0007\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011\u00a8\u0006\u0012"}, d2={"Lcom/cobblemon/mod/common/api/pokeball/catching/CatchRateModifier$Behavior;", "", "Lkotlin/Function2;", "", "Lkotlin/ParameterName;", "name", "input", "value", "mutator", "Lkotlin/jvm/functions/Function2;", "getMutator", "()Lkotlin/jvm/functions/Function2;", "<init>", "(Ljava/lang/String;ILkotlin/jvm/functions/Function2;)V", "ADD", "SUBTRACT", "MULTIPLY", "DIVIDE", "common"})
    public static final class Behavior
    extends Enum<Behavior> {
        @NotNull
        private final Function2<Float, Float, Float> mutator;
        public static final /* enum */ Behavior ADD = new Behavior((Function2<? super Float, ? super Float, Float>)((Function2)1.INSTANCE));
        public static final /* enum */ Behavior SUBTRACT = new Behavior((Function2<? super Float, ? super Float, Float>)((Function2)2.INSTANCE));
        public static final /* enum */ Behavior MULTIPLY = new Behavior((Function2<? super Float, ? super Float, Float>)((Function2)3.INSTANCE));
        public static final /* enum */ Behavior DIVIDE = new Behavior((Function2<? super Float, ? super Float, Float>)((Function2)4.INSTANCE));
        private static final /* synthetic */ Behavior[] $VALUES;

        private Behavior(Function2<? super Float, ? super Float, Float> mutator) {
            this.mutator = mutator;
        }

        @NotNull
        public final Function2<Float, Float, Float> getMutator() {
            return this.mutator;
        }

        public static Behavior[] values() {
            return (Behavior[])$VALUES.clone();
        }

        public static Behavior valueOf(String value2) {
            return Enum.valueOf(Behavior.class, value2);
        }

        static {
            $VALUES = behaviorArray = new Behavior[]{Behavior.ADD, Behavior.SUBTRACT, Behavior.MULTIPLY, Behavior.DIVIDE};
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR\u001a\u0010\u0003\u001a\u00020\u00028\u0000X\u0080\u0004\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/api/pokeball/catching/CatchRateModifier$Companion;", "", "Lcom/cobblemon/mod/common/api/pokeball/catching/CatchRateModifier;", "DUMMY", "Lcom/cobblemon/mod/common/api/pokeball/catching/CatchRateModifier;", "getDUMMY$common", "()Lcom/cobblemon/mod/common/api/pokeball/catching/CatchRateModifier;", "<init>", "()V", "common"})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;
        @NotNull
        private static final CatchRateModifier DUMMY;

        private Companion() {
        }

        @NotNull
        public final CatchRateModifier getDUMMY$common() {
            return DUMMY;
        }

        static {
            $$INSTANCE = new Companion();
            DUMMY = new CatchRateModifier(){

                public float value(@NotNull LivingEntity thrower, @NotNull Pokemon pokemon) {
                    Intrinsics.checkNotNullParameter((Object)thrower, (String)"thrower");
                    Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
                    return 1.0f;
                }

                @NotNull
                public Behavior behavior(@NotNull LivingEntity thrower, @NotNull Pokemon pokemon) {
                    Intrinsics.checkNotNullParameter((Object)thrower, (String)"thrower");
                    Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
                    return Behavior.ADD;
                }

                public boolean isValid(@NotNull LivingEntity thrower, @NotNull Pokemon pokemon) {
                    Intrinsics.checkNotNullParameter((Object)thrower, (String)"thrower");
                    Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
                    return false;
                }

                public float modifyCatchRate(float currentCatchRate, @NotNull LivingEntity thrower, @NotNull Pokemon pokemon) {
                    Intrinsics.checkNotNullParameter((Object)thrower, (String)"thrower");
                    Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
                    return 1.0f;
                }

                public boolean isGuaranteed() {
                    return DefaultImpls.isGuaranteed(this);
                }
            };
        }
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        public static boolean isGuaranteed(@NotNull CatchRateModifier $this) {
            return false;
        }
    }
}

