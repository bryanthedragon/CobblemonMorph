/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.advancements.critereon.ContextAwarePredicate
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.player.Player
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.BattleCountableContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.CountableCriterionCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerAdvancementData;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.JsonExtensionsKt;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Collection;
import java.util.List;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;

import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0017\u0012\u0006\u0010\u0014\u001a\u00020\u0013\u0012\u0006\u0010\u0016\u001a\u00020\u0015\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u0017\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001f\u0010\f\u001a\u00020\u000b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\f\u0010\rJ\u0017\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u000e\u0010\u0007R\u001c\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\u0011\u0010\u0012\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/advancement/criterion/BattleCountableCriterionCondition;", "Lcom/cobblemon/mod/common/advancement/criterion/CountableCriterionCondition;", "Lcom/cobblemon/mod/common/advancement/criterion/BattleCountableContext;", "Lcom/google/gson/JsonObject;", "json", "", "fromJson", "(Lcom/google/gson/JsonObject;)V", "Lnet/minecraft/server/level/ServerPlayer;", "player", "context", "", "matches", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/advancement/criterion/BattleCountableContext;)Z", "toJson", "", "", "battleTypes", "Ljava/util/List;", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/advancements/critereon/ContextAwarePredicate;", "predicate", "<init>", "(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/advancements/critereon/ContextAwarePredicate;)V", "common"})
@SourceDebugExtension(value={"SMAP\nBattleCountableCriterion.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleCountableCriterion.kt\ncom/cobblemon/mod/common/advancement/criterion/BattleCountableCriterionCondition\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,67:1\n1855#2,2:68\n*S KotlinDebug\n*F\n+ 1 BattleCountableCriterion.kt\ncom/cobblemon/mod/common/advancement/criterion/BattleCountableCriterionCondition\n*L\n31#1:68,2\n*E\n"})
public final class BattleCountableCriterionCondition
extends CountableCriterionCondition<BattleCountableContext> {
    @NotNull
    private List<String> battleTypes;

    public BattleCountableCriterionCondition(@NotNull ResourceLocation id, @NotNull ContextAwarePredicate predicate) {
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter((Object)predicate, (String)"predicate");
        super(id, predicate);
        Object[] objectArray = new String[]{"any"};
        this.battleTypes = CollectionsKt.mutableListOf((Object[])objectArray);
    }

    @SuppressWarnings({"unused", "rawtypes"})
    @Override
    public void fromJson(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        super.fromJson(json);
        if (!json.get("battle_types").isJsonNull()) {
            this.battleTypes.clear();
            List list = json.get("battle_types").getAsJsonArray().asList();
            Intrinsics.checkNotNullExpressionValue((Object)list, (String)"json.get(\"battle_types\").asJsonArray.asList()");
            Iterable $this$forEach$iv = list;
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                JsonElement it = (JsonElement)element$iv;
                boolean bl = false;
                String string = it.getAsString();
                Intrinsics.checkNotNullExpressionValue((Object)string, (String)"it.asString");
                this.battleTypes.add(string);
            }
        }
    }

    @Override
    public void toJson(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        super.toJson(json);
        json.add("battle_types", (JsonElement)JsonExtensionsKt.toJsonArrayString((Collection<String>)this.battleTypes));
    }

    @Override
    public boolean matches(@NotNull ServerPlayer player, @NotNull BattleCountableContext context) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        boolean typeCheck = false;
        PlayerAdvancementData advancementData = Cobblemon.INSTANCE.getPlayerData().get((Player)player).getAdvancementData();
        if (this.battleTypes.isEmpty() || this.battleTypes.contains("any")) {
            typeCheck = true;
        }
        if (this.battleTypes.contains("pvp")) {
            typeCheck = context.getBattle().isPvP();
            context.setTimes(advancementData.getTotalPvPBattleVictoryCount());
        }
        if (this.battleTypes.contains("pvw")) {
            typeCheck = context.getBattle().isPvW();
            context.setTimes(advancementData.getTotalPvWBattleVictoryCount());
        }
        if (this.battleTypes.contains("pvn")) {
            typeCheck = context.getBattle().isPvN();
            context.setTimes(advancementData.getTotalPvWBattleVictoryCount());
        }
        if (this.battleTypes.size() > 1) {
            context.setTimes(advancementData.getTotalBattleVictoryCount());
        }
        return context.getTimes() >= this.getCount() && typeCheck;
    }
}

