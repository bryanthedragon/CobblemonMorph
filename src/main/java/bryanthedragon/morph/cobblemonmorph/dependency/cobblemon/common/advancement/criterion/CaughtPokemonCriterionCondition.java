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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import kotlin.jvm.internal.Intrinsics;

import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import org.jetbrains.annotations.NotNull;

public final class CaughtPokemonCriterionCondition extends CountableCriterionCondition<CountablePokemonTypeContext> {
    @NotNull
    private String type;

    public CaughtPokemonCriterionCondition(@NotNull ResourceLocation id, @NotNull ContextAwarePredicate predicate) {
        super(id, predicate);
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter((Object)predicate, (String)"predicate");
        this.type = "any";
    }

    @NotNull
    public final String getType() {
        return this.type;
    }

    public final void setType(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.type = string;
    }

    @Override
    public void toJson(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        super.toJson(json);
        json.addProperty("type", this.type);
    }

    @Override
    public void fromJson(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        super.fromJson(json);
        JsonElement jsonElement = json.get("type");
        String string = jsonElement != null ? jsonElement.getAsString() : null;
        if (string == null) {
            string = "any";
        }
        this.type = string;
    }

    @Override
    public boolean matches(@NotNull ServerPlayer player, @NotNull CountablePokemonTypeContext context) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        return super.matches(player, (@NotNull CountablePokemonTypeContext)context) && (Intrinsics.areEqual((Object)context.getType(), (Object)this.type) || Intrinsics.areEqual((Object)this.type, (Object)"any"));
    }
}

