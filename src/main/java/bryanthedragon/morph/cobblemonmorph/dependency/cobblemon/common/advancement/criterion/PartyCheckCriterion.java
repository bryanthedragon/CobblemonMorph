/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.advancements.critereon.ContextAwarePredicate
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;


import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import org.jetbrains.annotations.NotNull;

public final class PartyCheckCriterion extends SimpleCriterionCondition<PartyCheckContext> {
    @NotNull
    private final List<ResourceLocation> party;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public PartyCheckCriterion(@NotNull ResourceLocation id, @NotNull ContextAwarePredicate entity2) {
        super(id, entity2);
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        this.party = new ArrayList();
    }


    @NotNull
    public final List<ResourceLocation> getParty() {
        return this.party;
    }

    /*
     * WARNING - void declaration
     */
    @SuppressWarnings({ "unused" })
    @Override
    public void toJson(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter(json, "json");
        
        JsonArray jsonArray = new JsonArray(this.party.size());
        
        for (ResourceLocation pokemon : this.party) {
            jsonArray.add(pokemon.toString());
        }
        
        json.add("party", jsonArray);
    }


    @SuppressWarnings({ "rawtypes", "unused" })
    public void fromJson(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        this.party.clear();
        JsonArray jsonArray = json.getAsJsonArray("party");
        Intrinsics.checkNotNullExpressionValue((Object)jsonArray, (String)"json.getAsJsonArray(\"party\")");
        Iterable $this$forEach$iv = (Iterable)jsonArray;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            JsonElement element = (JsonElement)element$iv;
            boolean bl = false;
            String string = element.getAsString();
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"element.asString");
            this.party.add(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(string, null, 1, null));
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked", "unused" })
    public boolean matches(@NotNull ServerPlayer player, @NotNull PartyCheckContext context) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        PlayerPartyStore playerParty = PlayerExtensionsKt.party(player);
        List matches2 = new ArrayList();
        Iterable $this$forEach$iv = this.party;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            ResourceLocation it = (ResourceLocation)element$iv;
            boolean bl = false;
            if (!Intrinsics.areEqual((Object)it, (Object)ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default("any", null, 1, null))) continue;
            matches2.add(it);
        }
        int partyCount = CollectionsKt.count((Iterable)playerParty);
        if (matches2.containsAll((Collection)this.party) && this.party.size() == partyCount && matches2.size() == partyCount) {
            return true;
        }
        Iterator<Pokemon> $this$forEach$iv2 = playerParty.iterator();
        boolean $i$f$forEach2 = false;
        Iterator<Pokemon> iterator = $this$forEach$iv2;
        while (iterator.hasNext()) {
            Pokemon element$iv;
            Pokemon it = element$iv = iterator.next();
            boolean bl = false;
            if (!this.party.contains(it.getSpecies().getResourceIdentifier())) continue;
            matches2.add(it.getSpecies().getResourceIdentifier());
        }
        return matches2.containsAll((Collection)this.party) && matches2.size() == partyCount;
    }
}

