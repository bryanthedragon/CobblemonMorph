/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.advancements.critereon.ContextAwarePredicate
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import kotlin.jvm.internal.Intrinsics;

import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import org.jetbrains.annotations.NotNull;

public final class PickStarterCriterionCondition extends SimpleCriterionCondition<Pokemon> {
    @NotNull
    private PokemonProperties properties;

    public PickStarterCriterionCondition(@NotNull ResourceLocation id, @NotNull ContextAwarePredicate predicate) {
        super(id, predicate);
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter((Object)predicate, (String)"predicate");
        this.properties = new PokemonProperties();
    }

    @NotNull
    public final PokemonProperties getProperties() {
        return this.properties;
    }

    public final void setProperties(@NotNull PokemonProperties pokemonProperties) {
        Intrinsics.checkNotNullParameter((Object)pokemonProperties, (String)"<set-?>");
        this.properties = pokemonProperties;
    }

    @Override
    public void toJson(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        json.addProperty("properties", this.properties.getOriginalString());
    }

    @Override
    public void fromJson(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        JsonElement jsonElement = json.get("properties");
        String string = jsonElement != null ? jsonElement.getAsString() : null;
        if (string == null) {
            string = "";
        }
        this.properties = PokemonProperties.Companion.parse$default(PokemonProperties.Companion, string, null, null, 6, null);
    }

    @Override
    public boolean matches(@NotNull ServerPlayer player, @NotNull Pokemon context) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        return this.properties.matches(context);
    }
}

