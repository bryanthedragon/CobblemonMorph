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

public final class PokemonInteractCriterion extends SimpleCriterionCondition<PokemonInteractContext> {
    @NotNull
    private String type;
    @NotNull
    private String item;

    public PokemonInteractCriterion(@NotNull ResourceLocation id, @NotNull ContextAwarePredicate entity2) {
        super(id, entity2);
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        this.type = "any";
        this.item = "any";
    }

    @NotNull
    public final String getType() {
        return this.type;
    }

    public final void setType(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.type = string;
    }

    @NotNull
    public final String getItem() {
        return this.item;
    }

    public final void setItem(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.item = string;
    }

    @Override
    public void toJson(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        json.addProperty("type", this.type);
        json.addProperty("item", this.item);
    }

    @Override
    public void fromJson(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        JsonElement jsonElement = json.get("type");
        String string = jsonElement != null ? jsonElement.getAsString() : null;
        if (string == null) {
            string = "any";
        }
        this.type = string;
        JsonElement jsonElement2 = json.get("item");
        String string2 = jsonElement2 != null ? jsonElement2.getAsString() : null;
        if (string2 == null) {
            string2 = "any";
        }
        this.item = string2;
    }

    @Override
    public boolean matches(@NotNull ServerPlayer player, @NotNull PokemonInteractContext context) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        return !(!Intrinsics.areEqual((Object)context.getType(), (Object)ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(this.type, null, 1, null)) && !Intrinsics.areEqual((Object)this.type, (Object)"any") || !Intrinsics.areEqual((Object)context.getItem(), (Object)ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(this.item, null, 1, null)) && !Intrinsics.areEqual((Object)this.item, (Object)"any"));
    }
}

