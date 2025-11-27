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

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import kotlin.jvm.internal.Intrinsics;

import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import org.jetbrains.annotations.NotNull;

public final class EvolvePokemonCriterionCondition extends CountableCriterionCondition<EvolvePokemonContext> {
    @NotNull
    private String species;
    @NotNull
    private String evolution;

    public EvolvePokemonCriterionCondition(@NotNull ResourceLocation id, @NotNull ContextAwarePredicate entity2) {
        super(id, entity2);
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        this.species = "any";
        this.evolution = "any";
    }

    @NotNull
    public final String getSpecies() {
        return this.species;
    }

    public final void setSpecies(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.species = string;
    }

    @NotNull
    public final String getEvolution() {
        return this.evolution;
    }

    public final void setEvolution(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.evolution = string;
    }

    @Override
    public void toJson(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        super.toJson(json);
        json.addProperty("species", this.species);
        json.addProperty("evolution", this.evolution);
    }

    @Override
    public void fromJson(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        super.fromJson(json);
        JsonElement jsonElement = json.get("species");
        String string = jsonElement != null ? jsonElement.getAsString() : null;
        if (string == null) {
            string = "any";
        }
        this.species = string;
        JsonElement jsonElement2 = json.get("evolution");
        String string2 = jsonElement2 != null ? jsonElement2.getAsString() : null;
        if (string2 == null) {
            string2 = "any";
        }
        this.evolution = string2;
    }

    @Override
    public boolean matches(@NotNull ServerPlayer player, @NotNull EvolvePokemonContext context) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        return !(context.getTimes() < this.getCount() || !Intrinsics.areEqual((Object)context.getSpecies(), (Object)ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(this.species, null, 1, null)) && !Intrinsics.areEqual((Object)this.species, (Object)"any") || !Intrinsics.areEqual((Object)context.getEvolution(), (Object)ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(this.evolution, null, 1, null)) && !Intrinsics.areEqual((Object)this.evolution, (Object)"any"));
    }
}

