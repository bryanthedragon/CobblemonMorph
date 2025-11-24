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

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.CountableContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.SimpleCriterionCondition;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\b\u0012\u0004\u0012\u00028\u00000\u0003B\u0017\u0012\u0006\u0010\u0018\u001a\u00020\u0017\u0012\u0006\u0010\u001a\u001a\u00020\u0019\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\u0017\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ\u001f\u0010\r\u001a\u00020\f2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00028\u0000H\u0016\u00a2\u0006\u0004\b\r\u0010\u000eJ\u0017\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u000f\u0010\bR\"\u0010\u0011\u001a\u00020\u00108\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016\u00a8\u0006\u001d"}, d2={"Lcom/cobblemon/mod/common/advancement/criterion/CountableCriterionCondition;", "Lcom/cobblemon/mod/common/advancement/criterion/CountableContext;", "T", "Lcom/cobblemon/mod/common/advancement/criterion/SimpleCriterionCondition;", "Lcom/google/gson/JsonObject;", "json", "", "fromJson", "(Lcom/google/gson/JsonObject;)V", "Lnet/minecraft/server/level/ServerPlayer;", "player", "context", "", "matches", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/advancement/criterion/CountableContext;)Z", "toJson", "", "count", "I", "getCount", "()I", "setCount", "(I)V", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/advancements/critereon/ContextAwarePredicate;", "predicate", "<init>", "(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/advancements/critereon/ContextAwarePredicate;)V", "common"})
public abstract class CountableCriterionCondition<T extends CountableContext>
extends SimpleCriterionCondition<T> {
    private int count;

    public CountableCriterionCondition(@NotNull ResourceLocation id, @NotNull ContextAwarePredicate predicate) {
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter((Object)predicate, (String)"predicate");
        super(id, predicate);
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

