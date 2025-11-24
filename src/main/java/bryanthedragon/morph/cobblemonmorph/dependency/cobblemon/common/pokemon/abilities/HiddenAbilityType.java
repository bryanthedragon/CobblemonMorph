/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.abilities;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.Abilities;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.AbilityTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.PotentialAbilityType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.abilities.HiddenAbility;
import com.google.gson.JsonElement;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0019\u0010\u0005\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/pokemon/abilities/HiddenAbilityType;", "Lcom/cobblemon/mod/common/api/abilities/PotentialAbilityType;", "Lcom/cobblemon/mod/common/pokemon/abilities/HiddenAbility;", "Lcom/google/gson/JsonElement;", "element", "parseFromJSON", "(Lcom/google/gson/JsonElement;)Lcom/cobblemon/mod/common/pokemon/abilities/HiddenAbility;", "<init>", "()V", "common"})
public final class HiddenAbilityType
implements PotentialAbilityType<HiddenAbility> {
    @NotNull
    public static final HiddenAbilityType INSTANCE = new HiddenAbilityType();

    private HiddenAbilityType() {
    }

    @Override
    @Nullable
    public HiddenAbility parseFromJSON(@NotNull JsonElement element) {
        HiddenAbility hiddenAbility;
        String str;
        Intrinsics.checkNotNullParameter((Object)element, (String)"element");
        String string = str = element.isJsonPrimitive() ? element.getAsString() : null;
        boolean bl = string != null ? StringsKt.startsWith$default((String)string, (String)"h:", (boolean)false, (int)2, null) : false;
        if (bl) {
            String abilityString = StringsKt.substringAfter$default((String)str, (String)"h:", null, (int)2, null);
            AbilityTemplate ability = Abilities.INSTANCE.get(abilityString);
            if (ability != null) {
                hiddenAbility = new HiddenAbility(ability);
            } else {
                Cobblemon.INSTANCE.getLOGGER().error("Hidden ability referred to unknown ability: " + abilityString);
                hiddenAbility = null;
            }
        } else {
            hiddenAbility = null;
        }
        return hiddenAbility;
    }
}

