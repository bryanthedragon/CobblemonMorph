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

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.PartyCheckContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.SimpleCriterionCondition;
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

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;

import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0017\u0012\u0006\u0010\u0015\u001a\u00020\u0010\u0012\u0006\u0010\u0017\u001a\u00020\u0016\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u0017\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001f\u0010\f\u001a\u00020\u000b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\f\u0010\rJ\u0017\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u000e\u0010\u0007R\u001d\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f8\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006\u001a"}, d2={"Lcom/cobblemon/mod/common/advancement/criterion/PartyCheckCriterion;", "Lcom/cobblemon/mod/common/advancement/criterion/SimpleCriterionCondition;", "Lcom/cobblemon/mod/common/advancement/criterion/PartyCheckContext;", "Lcom/google/gson/JsonObject;", "json", "", "fromJson", "(Lcom/google/gson/JsonObject;)V", "Lnet/minecraft/server/level/ServerPlayer;", "player", "context", "", "matches", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/advancement/criterion/PartyCheckContext;)Z", "toJson", "", "Lnet/minecraft/resources/ResourceLocation;", "party", "Ljava/util/List;", "getParty", "()Ljava/util/List;", "id", "Lnet/minecraft/advancements/critereon/ContextAwarePredicate;", "entity", "<init>", "(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/advancements/critereon/ContextAwarePredicate;)V", "common"})
@SourceDebugExtension(value={"SMAP\nPartyCheckCriterion.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PartyCheckCriterion.kt\ncom/cobblemon/mod/common/advancement/criterion/PartyCheckCriterion\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 Iterators.kt\nkotlin/collections/CollectionsKt__IteratorsKt\n*L\n1#1,54:1\n1855#2,2:55\n1855#2,2:57\n1855#2,2:59\n32#3,2:61\n*S KotlinDebug\n*F\n+ 1 PartyCheckCriterion.kt\ncom/cobblemon/mod/common/advancement/criterion/PartyCheckCriterion\n*L\n24#1:55,2\n30#1:57,2\n38#1:59,2\n45#1:61,2\n*E\n"})
public final class PartyCheckCriterion
extends SimpleCriterionCondition<PartyCheckContext> {
    @NotNull
    private final List<ResourceLocation> party;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public PartyCheckCriterion(@NotNull ResourceLocation id, @NotNull ContextAwarePredicate entity2) {
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        super(id, entity2);
        this.party = new ArrayList();
    }

    @NotNull
    public final List<ResourceLocation> getParty() {
        return this.party;
    }

    /*
     * WARNING - void declaration
     */
    @SuppressWarnings({ "rawtypes", "unchecked", "unused" })
    public void toJson(@NotNull JsonObject json) {
        JsonArray jsonArray;
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        JsonArray jsonArray2 = jsonArray = new JsonArray(this.party.size());
        String string = "party";
        JsonObject jsonObject = json;
        boolean bl = false;
        Iterable $this$forEach$iv = this.party;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            void it;
            ResourceLocation pokemon = (ResourceLocation)element$iv;
            boolean bl2 = false;
            it.add(pokemon.toString());
        }
        Unit unit = Unit.INSTANCE;
        jsonObject.add(string, (JsonElement)jsonArray);
    }

    @SuppressWarnings({ "rawtypes", "unchecked", "unused" })
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

