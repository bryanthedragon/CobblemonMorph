/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.advancements.critereon.ContextAwarePredicate
 *  net.minecraft.advancements.critereon.DeserializationContext
 *  net.minecraft.advancements.critereon.SimpleCriterionTrigger
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.SimpleCriterionCondition;

import com.google.gson.JsonObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import org.jetbrains.annotations.NotNull;

public class SimpleCriterionTrigger<T, C extends SimpleCriterionCondition<T>>
extends net.minecraft.advancements.critereon.SimpleCriterionTrigger<C> {
    @NotNull
    private final ResourceLocation id;

    @NotNull
    private final Class<C> criterionClass;

    public SimpleCriterionTrigger(@NotNull ResourceLocation id, @NotNull Class<C> criterionClass) {
        Intrinsics.checkNotNullParameter((Object) id, (String)"_id");
        Intrinsics.checkNotNullParameter(criterionClass, (String)"criterionClass");
        this.id = id;
        this.criterionClass = criterionClass;
    }

    @NotNull
    public final ResourceLocation get_id() {
        return this.id;
    }

    @NotNull
    public final Class<C> getCriterionClass() {
        return this.criterionClass;
    }

    @NotNull
    public ResourceLocation m_7295_() {
        return this.id;
    }

    @NotNull
    @SuppressWarnings({ "unchecked", "rawtypes", "null" })
    protected C conditionsFromJson(@NotNull JsonObject obj, @NotNull ContextAwarePredicate playerPredicate, @NotNull DeserializationContext predicateDeserializer) {
        Intrinsics.checkNotNullParameter((Object)obj, (String)"obj");
        Intrinsics.checkNotNullParameter((Object)playerPredicate, (String)"playerPredicate");
        Intrinsics.checkNotNullParameter((Object)predicateDeserializer, (String)"predicateDeserializer");
        Object[] objectArray = new Class[]{ResourceLocation.class, ContextAwarePredicate.class};
        Constructor<C> constructor = null;
        objectArray = new Object[]{this.m_7295_(), playerPredicate};
        SimpleCriterionCondition instance = null;

        try {
            constructor = this.criterionClass.getConstructor((Class<?>[])objectArray);
        } 
        catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        try {
            instance = (SimpleCriterionCondition)((Object)constructor.newInstance(objectArray));
        } 
        catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
             e.printStackTrace();
        }
        instance.fromJson(obj);
        Intrinsics.checkNotNullExpressionValue((Object)((Object)instance), (String)"instance");
        return (C)((Object)instance);
    }

    public final void trigger(@NotNull ServerPlayer player, T context) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        this.m_66234_(player, arg_0 -> SimpleCriterionTrigger.trigger$lambda$0(player, context, arg_0));
    }

    @SuppressWarnings({ "unchecked", "rawtypes", "unused" })
    private static final boolean trigger$lambda$0(ServerPlayer $player, Object $context, SimpleCriterionCondition it) {
        Intrinsics.checkNotNullParameter((Object)$player, (String)"$player");
        return it.matches($player, $context);
    }

    public ResourceLocation getId() {
        return this.id;
    }


    @SuppressWarnings("null")
    protected C createInstance(JsonObject pJson, ContextAwarePredicate pPredicate, DeserializationContext pDeserializationContext) {
            return this.conditionsFromJson(pJson, pPredicate, pDeserializationContext);
    }
}

