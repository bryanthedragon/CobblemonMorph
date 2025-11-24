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

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.SimpleCriterionCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.TradePokemonContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0017\u0012\u0006\u0010 \u001a\u00020\u001f\u0012\u0006\u0010\"\u001a\u00020!\u00a2\u0006\u0004\b#\u0010$J\u0017\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001f\u0010\f\u001a\u00020\u000b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\f\u0010\rJ\u0017\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u000e\u0010\u0007R\"\u0010\u0010\u001a\u00020\u000f8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\"\u0010\u0016\u001a\u00020\u000f8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0016\u0010\u0011\u001a\u0004\b\u0017\u0010\u0013\"\u0004\b\u0018\u0010\u0015R\"\u0010\u0019\u001a\u00020\u000f8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0019\u0010\u0011\u001a\u0004\b\u001a\u0010\u0013\"\u0004\b\u001b\u0010\u0015R\"\u0010\u001c\u001a\u00020\u000f8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001c\u0010\u0011\u001a\u0004\b\u001d\u0010\u0013\"\u0004\b\u001e\u0010\u0015\u00a8\u0006%"}, d2={"Lcom/cobblemon/mod/common/advancement/criterion/TradePokemonCriterionCondition;", "Lcom/cobblemon/mod/common/advancement/criterion/SimpleCriterionCondition;", "Lcom/cobblemon/mod/common/advancement/criterion/TradePokemonContext;", "Lcom/google/gson/JsonObject;", "json", "", "fromJson", "(Lcom/google/gson/JsonObject;)V", "Lnet/minecraft/server/level/ServerPlayer;", "player", "context", "", "matches", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/advancement/criterion/TradePokemonContext;)Z", "toJson", "", "received", "Ljava/lang/String;", "getReceived", "()Ljava/lang/String;", "setReceived", "(Ljava/lang/String;)V", "receivedHeldItem", "getReceivedHeldItem", "setReceivedHeldItem", "traded", "getTraded", "setTraded", "tradedHeldItem", "getTradedHeldItem", "setTradedHeldItem", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/advancements/critereon/ContextAwarePredicate;", "entity", "<init>", "(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/advancements/critereon/ContextAwarePredicate;)V", "common"})
public final class TradePokemonCriterionCondition
extends SimpleCriterionCondition<TradePokemonContext> {
    @NotNull
    private String traded;
    @NotNull
    private String received;
    @NotNull
    private String tradedHeldItem;
    @NotNull
    private String receivedHeldItem;

    public TradePokemonCriterionCondition(@NotNull ResourceLocation id, @NotNull ContextAwarePredicate entity2) {
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        super(id, entity2);
        this.traded = "any";
        this.received = "any";
        this.tradedHeldItem = "any";
        this.receivedHeldItem = "any";
    }

    @NotNull
    public final String getTraded() {
        return this.traded;
    }

    public final void setTraded(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.traded = string;
    }

    @NotNull
    public final String getReceived() {
        return this.received;
    }

    public final void setReceived(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.received = string;
    }

    @NotNull
    public final String getTradedHeldItem() {
        return this.tradedHeldItem;
    }

    public final void setTradedHeldItem(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.tradedHeldItem = string;
    }

    @NotNull
    public final String getReceivedHeldItem() {
        return this.receivedHeldItem;
    }

    public final void setReceivedHeldItem(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.receivedHeldItem = string;
    }

    @Override
    public void toJson(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        json.addProperty("traded", this.traded);
        json.addProperty("received", this.received);
        json.addProperty("traded_held_item", this.tradedHeldItem);
        json.addProperty("received_held_item", this.receivedHeldItem);
    }

    @Override
    public void fromJson(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        JsonElement jsonElement = json.get("traded");
        String string = jsonElement != null ? jsonElement.getAsString() : null;
        if (string == null) {
            string = "any";
        }
        this.traded = string;
        JsonElement jsonElement2 = json.get("received");
        String string2 = jsonElement2 != null ? jsonElement2.getAsString() : null;
        if (string2 == null) {
            string2 = "any";
        }
        this.received = string2;
        JsonElement jsonElement3 = json.get("traded_held_item");
        String string3 = jsonElement3 != null ? jsonElement3.getAsString() : null;
        if (string3 == null) {
            string3 = "minecraft:air";
        }
        this.tradedHeldItem = string3;
        JsonElement jsonElement4 = json.get("received_held_item");
        String string4 = jsonElement4 != null ? jsonElement4.getAsString() : null;
        if (string4 == null) {
            string4 = "minecraft:air";
        }
        this.receivedHeldItem = string4;
    }

    @Override
    public boolean matches(@NotNull ServerPlayer player, @NotNull TradePokemonContext context) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        ResourceLocation heldItem1 = context.getTraded().heldItem().m_41720_().m_204114_().m_205785_().m_135782_();
        ResourceLocation heldItem2 = context.getReceived().heldItem().m_41720_().m_204114_().m_205785_().m_135782_();
        return !(!Intrinsics.areEqual((Object)context.getTraded().getSpecies().getResourceIdentifier(), (Object)ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(this.traded, null, 1, null)) && !Intrinsics.areEqual((Object)this.traded, (Object)"any") || !Intrinsics.areEqual((Object)context.getReceived().getSpecies().getResourceIdentifier(), (Object)ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(this.received, null, 1, null)) && !Intrinsics.areEqual((Object)this.received, (Object)"any") || !Intrinsics.areEqual((Object)heldItem1, (Object)ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(this.tradedHeldItem, null, 1, null)) && !Intrinsics.areEqual((Object)heldItem1, (Object)ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default("minecraft:air", null, 1, null)) || !Intrinsics.areEqual((Object)heldItem2, (Object)ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(this.receivedHeldItem, null, 1, null)) && !Intrinsics.areEqual((Object)heldItem2, (Object)ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default("minecraft:air", null, 1, null)));
    }
}

