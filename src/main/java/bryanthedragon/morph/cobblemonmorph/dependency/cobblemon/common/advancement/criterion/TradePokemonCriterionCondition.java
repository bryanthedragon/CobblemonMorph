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

public final class TradePokemonCriterionCondition extends SimpleCriterionCondition<TradePokemonContext> {
    @NotNull
    private String traded;
    @NotNull
    private String received;
    @NotNull
    private String tradedHeldItem;
    @NotNull
    private String receivedHeldItem;

    public TradePokemonCriterionCondition(@NotNull ResourceLocation id, @NotNull ContextAwarePredicate entity2) {
        super(id, entity2);
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
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

