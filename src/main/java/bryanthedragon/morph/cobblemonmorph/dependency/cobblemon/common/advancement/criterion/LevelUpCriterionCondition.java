/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.advancements.critereon.ContextAwarePredicate
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import org.jetbrains.annotations.NotNull;

public final class LevelUpCriterionCondition extends SimpleCriterionCondition<LevelUpContext> {
    private int level;
    private boolean evolved;

    public LevelUpCriterionCondition(@NotNull ResourceLocation id, @NotNull ContextAwarePredicate entity2) {
        super(id, entity2);
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");

        this.evolved = true;
    }

    public final int getLevel() {
        return this.level;
    }

    public final void setLevel(int n) {
        this.level = n;
    }

    public final boolean getEvolved() {
        return this.evolved;
    }

    public final void setEvolved(boolean bl) {
        this.evolved = bl;
    }

    @Override
    public void toJson(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        json.addProperty("level", (Number)this.level);
        json.addProperty("has_evolved", Boolean.valueOf(this.evolved));
    }

    @Override
    public void fromJson(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        JsonElement jsonElement = json.get("level");
        this.level = jsonElement != null ? jsonElement.getAsInt() : 0;
        JsonElement jsonElement2 = json.get("has_evolved");
        this.evolved = jsonElement2 != null ? jsonElement2.getAsBoolean() : true;
    }

    @Override
    public boolean matches(@NotNull ServerPlayer player, @NotNull LevelUpContext context) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        boolean preEvo = context.getPokemon().getPreEvolution() == null;
        boolean hasEvolution = !CollectionsKt.none(context.getPokemon().getEvolutions());
        boolean evolutionCheck = true;
        if (preEvo || hasEvolution) {
            evolutionCheck = preEvo != hasEvolution;
        }
        return this.level == context.getLevel() && evolutionCheck == this.evolved;
    }
}

