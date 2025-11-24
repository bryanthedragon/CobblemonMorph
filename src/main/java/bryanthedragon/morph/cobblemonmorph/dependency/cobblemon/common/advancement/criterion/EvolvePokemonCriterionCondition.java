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

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.CountableCriterionCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.EvolvePokemonContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0017\u0012\u0006\u0010\u001a\u001a\u00020\u0019\u0012\u0006\u0010\u001c\u001a\u00020\u001b\u00a2\u0006\u0004\b\u001d\u0010\u001eJ\u0017\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001f\u0010\f\u001a\u00020\u000b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\f\u0010\rJ\u0017\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u000e\u0010\u0007R\"\u0010\u0010\u001a\u00020\u000f8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\"\u0010\u0016\u001a\u00020\u000f8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0016\u0010\u0011\u001a\u0004\b\u0017\u0010\u0013\"\u0004\b\u0018\u0010\u0015\u00a8\u0006\u001f"}, d2={"Lcom/cobblemon/mod/common/advancement/criterion/EvolvePokemonCriterionCondition;", "Lcom/cobblemon/mod/common/advancement/criterion/CountableCriterionCondition;", "Lcom/cobblemon/mod/common/advancement/criterion/EvolvePokemonContext;", "Lcom/google/gson/JsonObject;", "json", "", "fromJson", "(Lcom/google/gson/JsonObject;)V", "Lnet/minecraft/server/level/ServerPlayer;", "player", "context", "", "matches", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/advancement/criterion/EvolvePokemonContext;)Z", "toJson", "", "evolution", "Ljava/lang/String;", "getEvolution", "()Ljava/lang/String;", "setEvolution", "(Ljava/lang/String;)V", "species", "getSpecies", "setSpecies", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/advancements/critereon/ContextAwarePredicate;", "entity", "<init>", "(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/advancements/critereon/ContextAwarePredicate;)V", "common"})
public final class EvolvePokemonCriterionCondition
extends CountableCriterionCondition<EvolvePokemonContext> {
    @NotNull
    private String species;
    @NotNull
    private String evolution;

    public EvolvePokemonCriterionCondition(@NotNull ResourceLocation id, @NotNull ContextAwarePredicate entity2) {
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        super(id, entity2);
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

