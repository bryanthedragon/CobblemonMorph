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

public abstract class CountableCriterionCondition<T extends CountableContext>
extends SimpleCriterionCondition<T> {
    private int count;

    public CountableCriterionCondition(@NotNull ResourceLocation id, @NotNull ContextAwarePredicate predicate) {
        super(id, predicate);
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter((Object)predicate, (String)"predicate");
    }

    public final int getCount() {
        return this.count;
    }

    public final void setCount(int n) {
        this.count = n;
    }

    @Override
    public void fromJson(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        JsonElement jsonElement = json.get("count");
        this.count = jsonElement != null ? jsonElement.getAsInt() : 0;
    }

    @Override
    public void toJson(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        json.addProperty("count", (Number)this.count);
    }

    @Override
    public boolean matches(@NotNull ServerPlayer player, @NotNull T context) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter(context, (String)"context");
        return ((CountableContext)context).getTimes() >= this.count;
    }
}

