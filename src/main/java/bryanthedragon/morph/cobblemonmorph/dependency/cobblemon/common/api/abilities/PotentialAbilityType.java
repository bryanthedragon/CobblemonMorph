/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.PotentialAbility;
import com.google.gson.JsonElement;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u00020\u0003J\u0019\u0010\u0006\u001a\u0004\u0018\u00018\u00002\u0006\u0010\u0005\u001a\u00020\u0004H&\u00a2\u0006\u0004\b\u0006\u0010\u0007\u00a8\u0006\b"}, d2={"Lcom/cobblemon/mod/common/api/abilities/PotentialAbilityType;", "Lcom/cobblemon/mod/common/api/abilities/PotentialAbility;", "T", "", "Lcom/google/gson/JsonElement;", "element", "parseFromJSON", "(Lcom/google/gson/JsonElement;)Lcom/cobblemon/mod/common/api/abilities/PotentialAbility;", "common"})
public interface PotentialAbilityType<T extends PotentialAbility> {
    @Nullable
    public T parseFromJSON(@NotNull JsonElement var1);
}

