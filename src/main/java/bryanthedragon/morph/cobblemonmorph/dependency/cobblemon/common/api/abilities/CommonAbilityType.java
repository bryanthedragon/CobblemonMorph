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

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.Abilities;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.AbilityTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.CommonAbility;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.PotentialAbilityType;
import com.google.gson.JsonElement;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0019\u0010\u0005\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/api/abilities/CommonAbilityType;", "Lcom/cobblemon/mod/common/api/abilities/PotentialAbilityType;", "Lcom/cobblemon/mod/common/api/abilities/CommonAbility;", "Lcom/google/gson/JsonElement;", "element", "parseFromJSON", "(Lcom/google/gson/JsonElement;)Lcom/cobblemon/mod/common/api/abilities/CommonAbility;", "<init>", "()V", "common"})
public final class CommonAbilityType
implements PotentialAbilityType<CommonAbility> {
    @NotNull
    public static final CommonAbilityType INSTANCE = new CommonAbilityType();

    private CommonAbilityType() {
    }

    @Override
    @Nullable
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

