/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities;

import com.google.gson.JsonElement;

import kotlin.jvm.internal.Intrinsics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class CommonAbilityType implements PotentialAbilityType<CommonAbility> {
    @NotNull
    public static final CommonAbilityType INSTANCE = new CommonAbilityType();

    private CommonAbilityType() {
    }


    @Override
    @Nullable
    @SuppressWarnings("unused")
    public CommonAbility parseFromJSON(@NotNull JsonElement element) {
        CommonAbility commonAbility;
        String str;
        Intrinsics.checkNotNullParameter((Object)element, (String)"element");
        String string = str = element.isJsonPrimitive() ? element.getAsString() : null;
        if (string != null) {
            String it = string;
            boolean bl = false;
            AbilityTemplate ability = Abilities.INSTANCE.get(it);
            commonAbility = ability != null ? new CommonAbility(ability) : null;
        } else {
            commonAbility = null;
        }
        return commonAbility;
    }
}

