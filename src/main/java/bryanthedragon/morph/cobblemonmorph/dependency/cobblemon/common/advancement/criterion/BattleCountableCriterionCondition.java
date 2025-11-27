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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerAdvancementData;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.JsonExtensionsKt;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Collection;
import java.util.List;

import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import org.jetbrains.annotations.NotNull;

public final class BattleCountableCriterionCondition extends CountableCriterionCondition<BattleCountableContext> {
    @NotNull
    private List<String> battleTypes;

    public BattleCountableCriterionCondition(@NotNull ResourceLocation id, @NotNull ContextAwarePredicate predicate) {
        super(id, predicate);
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter((Object)predicate, (String)"predicate");
        this.battleTypes = CollectionsKt.mutableListOf(new String[]{"any"});
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

