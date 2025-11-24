/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.JvmStatic
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item.ability;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.AbilityTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.CommonAbility;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.CommonAbilityType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.PotentialAbility;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.PotentialAbilityType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item.ability.AbilityChanger;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.ability.AbilityTypeChanger;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.abilities.HiddenAbility;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.abilities.HiddenAbilityType;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u0000 \u0013*\b\b\u0000\u0010\u0002*\u00020\u00012\u00020\u0003:\u0001\u0013J\u001d\u0010\u0007\u001a\u00020\u00062\f\u0010\u0005\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0004H&\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0017\u0010\u000b\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\tH&\u00a2\u0006\u0004\b\u000b\u0010\fJ\u001d\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010\n\u001a\u00020\tH&\u00a2\u0006\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u00048&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/api/item/ability/AbilityChanger;", "Lcom/cobblemon/mod/common/api/abilities/PotentialAbility;", "T", "", "Lcom/cobblemon/mod/common/api/abilities/PotentialAbilityType;", "type", "", "canChangeFrom", "(Lcom/cobblemon/mod/common/api/abilities/PotentialAbilityType;)Z", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "performChange", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "", "Lcom/cobblemon/mod/common/api/abilities/AbilityTemplate;", "queryPossible", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Ljava/util/Set;", "getType", "()Lcom/cobblemon/mod/common/api/abilities/PotentialAbilityType;", "Companion", "common"})
public interface AbilityChanger<T extends PotentialAbility> {
    @NotNull
    public static final Companion Companion = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item.ability.AbilityChanger$Companion.$$INSTANCE;

    @NotNull
    public PotentialAbilityType<T> getType();

    @NotNull
    public Set<AbilityTemplate> queryPossible(@NotNull Pokemon var1);

    public boolean performChange(@NotNull Pokemon var1);

    public boolean canChangeFrom(@Nullable PotentialAbilityType<?> var1);

    @NotNull
    public static AbilityChanger<CommonAbility> getCOMMON_ABILITY() {
        return Companion.getCOMMON_ABILITY();
    }

    @NotNull
    public static AbilityChanger<HiddenAbility> getHIDDEN_ABILITY() {
        return Companion.getHIDDEN_ABILITY();
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000e\u0010\tR&\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\b\u0004\u0010\u0005\u0012\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007R&\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\u00028\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\b\u000b\u0010\u0005\u0012\u0004\b\r\u0010\t\u001a\u0004\b\f\u0010\u0007\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/api/item/ability/AbilityChanger$Companion;", "", "Lcom/cobblemon/mod/common/api/item/ability/AbilityChanger;", "Lcom/cobblemon/mod/common/api/abilities/CommonAbility;", "COMMON_ABILITY", "Lcom/cobblemon/mod/common/api/item/ability/AbilityChanger;", "getCOMMON_ABILITY", "()Lcom/cobblemon/mod/common/api/item/ability/AbilityChanger;", "getCOMMON_ABILITY$annotations", "()V", "Lcom/cobblemon/mod/common/pokemon/abilities/HiddenAbility;", "HIDDEN_ABILITY", "getHIDDEN_ABILITY", "getHIDDEN_ABILITY$annotations", "<init>", "common"})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;
        @NotNull
        private static final AbilityChanger<CommonAbility> COMMON_ABILITY;
        @NotNull
        private static final AbilityChanger<HiddenAbility> HIDDEN_ABILITY;

        private Companion() {
        }

        @NotNull
        public final AbilityChanger<CommonAbility> getCOMMON_ABILITY() {
            return COMMON_ABILITY;
        }

        @JvmStatic
        public static /* synthetic */ void getCOMMON_ABILITY$annotations() {
        }

        @NotNull
        public final AbilityChanger<HiddenAbility> getHIDDEN_ABILITY() {
            return HIDDEN_ABILITY;
        }

        @JvmStatic
        public static /* synthetic */ void getHIDDEN_ABILITY$annotations() {
        }

        static {
            $$INSTANCE = new Companion();
            COMMON_ABILITY = new AbilityTypeChanger(CommonAbilityType.INSTANCE, COMMON_ABILITY.1.INSTANCE);
            HIDDEN_ABILITY = new AbilityTypeChanger(HiddenAbilityType.INSTANCE, HIDDEN_ABILITY.1.INSTANCE);
        }
    }
}

