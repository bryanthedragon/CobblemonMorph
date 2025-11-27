/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance
 *  net.minecraft.advancements.critereon.ContextAwarePredicate
 *  net.minecraft.advancements.critereon.SerializationContext
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion;

import com.google.gson.JsonObject;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import org.jetbrains.annotations.NotNull;

public abstract class SimpleCriterionCondition<T> extends AbstractCriterionTriggerInstance {
    public SimpleCriterionCondition(@NotNull ResourceLocation id, @NotNull ContextAwarePredicate playerPredicate) {
        super(id, playerPredicate);
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter((Object)playerPredicate, (String)"playerPredicate");
    }

    @NotNull
    public JsonObject m_7683_(@NotNull SerializationContext predicateSerializer) {
        Intrinsics.checkNotNullParameter((Object)predicateSerializer, (String)"predicateSerializer");
        JsonObject json = super.m_7683_(predicateSerializer);
        Intrinsics.checkNotNullExpressionValue((Object)json, (String)"json");
        this.toJson(json);
        return json;
    }

    public abstract void toJson(@NotNull JsonObject var1);

    public abstract void fromJson(@NotNull JsonObject var1);

    public abstract boolean matches(@NotNull ServerPlayer var1, T var2);
}

