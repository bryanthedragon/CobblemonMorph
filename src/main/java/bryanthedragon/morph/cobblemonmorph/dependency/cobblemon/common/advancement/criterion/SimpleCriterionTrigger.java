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

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0016\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u000e\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u00028\u00000\u00022\b\u0012\u0004\u0012\u00028\u00010\u0004B\u001d\u0012\u0006\u0010\u0016\u001a\u00020\r\u0012\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00028\u00010\u0019\u00a2\u0006\u0004\b\u001e\u0010\u001fJ'\u0010\u000b\u001a\u00028\u00012\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\tH\u0014\u00a2\u0006\u0004\b\u000b\u0010\fJ\u000f\u0010\u000e\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u001d\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00028\u0000\u00a2\u0006\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0016\u001a\u00020\r8\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u000fR\u001d\u0010\u001a\u001a\b\u0012\u0004\u0012\u00028\u00010\u00198\u0006\u00a2\u0006\f\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u001c\u0010\u001d\u00a8\u0006 "}, d2={"Lcom/cobblemon/mod/common/advancement/criterion/SimpleCriterionTrigger;", "T", "Lcom/cobblemon/mod/common/advancement/criterion/SimpleCriterionCondition;", "C", "Lnet/minecraft/advancements/critereon/SimpleCriterionTrigger;", "Lcom/google/gson/JsonObject;", "obj", "Lnet/minecraft/advancements/critereon/ContextAwarePredicate;", "playerPredicate", "Lnet/minecraft/advancements/critereon/DeserializationContext;", "predicateDeserializer", "conditionsFromJson", "(Lcom/google/gson/JsonObject;Lnet/minecraft/advancements/critereon/ContextAwarePredicate;Lnet/minecraft/advancements/critereon/DeserializationContext;)Lcom/cobblemon/mod/common/advancement/criterion/SimpleCriterionCondition;", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "context", "", "trigger", "(Lnet/minecraft/server/level/ServerPlayer;Ljava/lang/Object;)V", "_id", "Lnet/minecraft/resources/ResourceLocation;", "get_id", "Ljava/lang/Class;", "criterionClass", "Ljava/lang/Class;", "getCriterionClass", "()Ljava/lang/Class;", "<init>", "(Lnet/minecraft/resources/ResourceLocation;Ljava/lang/Class;)V", "common"})
public class SimpleCriterionTrigger<T, C extends SimpleCriterionCondition<T>>
extends net.minecraft.advancements.critereon.SimpleCriterionTrigger<C> {
    @NotNull
    private final ResourceLocation _id;
    @NotNull
    private final Class<C> criterionClass;

    public SimpleCriterionTrigger(@NotNull ResourceLocation _id, @NotNull Class<C> criterionClass) {
        Intrinsics.checkNotNullParameter((Object)_id, (String)"_id");
        Intrinsics.checkNotNullParameter(criterionClass, (String)"criterionClass");
        this._id = _id;
        this.criterionClass = criterionClass;
    }

    @NotNull
    public final ResourceLocation get_id() {
        return this._id;
    }

    @NotNull
    public final Class<C> getCriterionClass() {
        return this.criterionClass;
    }

    @NotNull
    public ResourceLocation m_7295_() {
        return this._id;
    }

    @NotNull
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected C conditionsFromJson(@NotNull JsonObject obj, @NotNull ContextAwarePredicate playerPredicate, @NotNull DeserializationContext predicateDeserializer) {
        Intrinsics.checkNotNullParameter((Object)obj, (String)"obj");
        Intrinsics.checkNotNullParameter((Object)playerPredicate, (String)"playerPredicate");
        Intrinsics.checkNotNullParameter((Object)predicateDeserializer, (String)"predicateDeserializer");
        Object[] objectArray = new Class[]{ResourceLocation.class, ContextAwarePredicate.class};
        Constructor<C> constructor = this.criterionClass.getConstructor((Class<?>[])objectArray);
        objectArray = new Object[]{this.m_7295_(), playerPredicate};
        SimpleCriterionCondition instance = (SimpleCriterionCondition)((Object)constructor.newInstance(objectArray));
        instance.fromJson(obj);
        Intrinsics.checkNotNullExpressionValue((Object)((Object)instance), (String)"instance");
        return (C)((Object)instance);
    }

    public final void trigger(@NotNull ServerPlayer player, T context) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        this.m_66234_(player, arg_0 -> SimpleCriterionTrigger.trigger$lambda$0(player, context, arg_0));
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static final boolean trigger$lambda$0(ServerPlayer $player, Object $context, SimpleCriterionCondition it) {
        Intrinsics.checkNotNullParameter((Object)$player, (String)"$player");
        return it.matches($player, $context);
    }
}

