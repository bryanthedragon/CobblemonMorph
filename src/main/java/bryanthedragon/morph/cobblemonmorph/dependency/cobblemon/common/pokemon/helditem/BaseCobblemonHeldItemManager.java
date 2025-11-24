/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.HashBiMap
 *  com.google.gson.JsonArray
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.StringsKt
 *  net.minecraft.core.DefaultedRegistry
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.helditem;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.helditem.HeldItemManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.runner.ShowdownService;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import com.google.common.collect.HashBiMap;
import com.google.gson.JsonArray;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u001c\u0010\nJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ\u000f\u0010\u000b\u001a\u00020\u0006H\u0010\u00a2\u0006\u0004\b\t\u0010\nJ\u000f\u0010\r\u001a\u00020\fH\u0004\u00a2\u0006\u0004\b\r\u0010\u000eJ\u0017\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u0019\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0012J\u0019\u0010\u0015\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0014\u001a\u00020\u0013H\u0002\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u001f\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0017\u0010\bRT\u0010\u001a\u001aB\u0012\f\u0012\n \u0019*\u0004\u0018\u00010\u00040\u0004\u0012\f\u0012\n \u0019*\u0004\u0018\u00010\u00130\u0013 \u0019* \u0012\f\u0012\n \u0019*\u0004\u0018\u00010\u00040\u0004\u0012\f\u0012\n \u0019*\u0004\u0018\u00010\u00130\u0013\u0018\u00010\u00180\u00188\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001a\u0010\u001b\u00a8\u0006\u001d"}, d2={"Lcom/cobblemon/mod/common/pokemon/helditem/BaseCobblemonHeldItemManager;", "Lcom/cobblemon/mod/common/api/pokemon/helditem/HeldItemManager;", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "pokemon", "", "showdownId", "", "give", "(Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;Ljava/lang/String;)V", "load$common", "()V", "load", "", "loadedItemCount", "()I", "Lnet/minecraft/network/chat/Component;", "nameOf", "(Ljava/lang/String;)Lnet/minecraft/network/chat/Component;", "(Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;)Ljava/lang/String;", "Lnet/minecraft/world/item/Item;", "item", "showdownIdOf", "(Lnet/minecraft/world/item/Item;)Ljava/lang/String;", "take", "Lcom/google/common/collect/HashBiMap;", "kotlin.jvm.PlatformType", "itemIds", "Lcom/google/common/collect/HashBiMap;", "<init>", "common"})
@SourceDebugExtension(value={"SMAP\nBaseCobblemonHeldItemManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BaseCobblemonHeldItemManager.kt\ncom/cobblemon/mod/common/pokemon/helditem/BaseCobblemonHeldItemManager\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,88:1\n1855#2,2:89\n1#3:91\n*S KotlinDebug\n*F\n+ 1 BaseCobblemonHeldItemManager.kt\ncom/cobblemon/mod/common/pokemon/helditem/BaseCobblemonHeldItemManager\n*L\n39#1:89,2\n*E\n"})
public abstract class BaseCobblemonHeldItemManager
implements HeldItemManager {
    private final HashBiMap<String, Item> itemIds = HashBiMap.create();

    public void load$common() {
        this.itemIds.clear();
        JsonArray itemsJson = ShowdownService.Companion.getService().getItemIds();
        HashSet showdownIds = new HashSet();
        int n = itemsJson.size();
        for (int i = 0; i < n; ++i) {
            ((Collection)showdownIds).add(itemsJson.get(i).getAsString());
        }
        DefaultedRegistry defaultedRegistry = BuiltInRegistries.f_257033_;
        Intrinsics.checkNotNullExpressionValue((Object)defaultedRegistry, (String)"ITEM");
        Iterable $this$forEach$iv = (Iterable)defaultedRegistry;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            ResourceLocation identifier;
            Item item = (Item)element$iv;
            boolean bl = false;
            Intrinsics.checkNotNullExpressionValue((Object)BuiltInRegistries.f_257033_.m_7981_((Object)item), (String)"ITEM.getId(item)");
            if (!Intrinsics.areEqual((Object)identifier.m_135827_(), (Object)"cobblemon")) continue;
            String string = identifier.m_135815_();
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"identifier.path");
            String formattedPath = StringsKt.replace$default((String)string, (String)"_", (String)"", (boolean)false, (int)4, null);
            if (!showdownIds.contains(formattedPath)) continue;
            HashBiMap<String, Item> hashBiMap = this.itemIds;
            Intrinsics.checkNotNullExpressionValue(hashBiMap, (String)"this.itemIds");
            ((Map)hashBiMap).put(formattedPath, item);
        }
    }

    @Override
    @Nullable
    public String showdownId(@NotNull BattlePokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Item item = pokemon.getEffectedPokemon().heldItemNoCopy$common().m_41720_();
        Intrinsics.checkNotNullExpressionValue((Object)item, (String)"pokemon.effectedPokemon.heldItemNoCopy().item");
        return this.showdownIdOf(item);
    }

    @Override
    @NotNull
    public Component nameOf(@NotNull String showdownId) {
        Intrinsics.checkNotNullParameter((Object)showdownId, (String)"showdownId");
        Item item = (Item)this.itemIds.get((Object)showdownId);
        Object object = item != null ? item.m_41466_() : null;
        if (object == null) {
            Component component = Component.m_130674_((String)showdownId);
            object = component;
            Intrinsics.checkNotNullExpressionValue((Object)component, (String)"of(showdownId)");
        }
        return object;
    }

    @Override
    public void give(@NotNull BattlePokemon pokemon, @NotNull String showdownId) {
        ItemStack itemStack;
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)showdownId, (String)"showdownId");
        Item item = (Item)this.itemIds.get((Object)showdownId);
        if (item != null) {
            Item it = item;
            boolean bl = false;
            itemStack = new ItemStack((ItemLike)it);
        } else {
            itemStack = ItemStack.f_41583_;
        }
        ItemStack stack = itemStack;
        Pokemon pokemon2 = pokemon.getEffectedPokemon();
        Intrinsics.checkNotNullExpressionValue((Object)stack, (String)"stack");
        pokemon2.swapHeldItem(stack, false);
    }

    @Override
    public void take(@NotNull BattlePokemon pokemon, @NotNull String showdownId) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)showdownId, (String)"showdownId");
        pokemon.getEffectedPokemon().removeHeldItem();
    }

    protected final int loadedItemCount() {
        return this.itemIds.size();
    }

    private final String showdownIdOf(Item item) {
        ResourceLocation resourceLocation = BuiltInRegistries.f_257033_.m_7981_((Object)item);
        Intrinsics.checkNotNullExpressionValue((Object)resourceLocation, (String)"ITEM.getId(item)");
        ResourceLocation identifier = resourceLocation;
        String string = identifier.m_135815_();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"identifier.path");
        String formattedPath = StringsKt.replace$default((String)string, (String)"_", (String)"", (boolean)false, (int)4, null);
        if (this.itemIds.containsKey((Object)formattedPath)) {
            return formattedPath;
        }
        return null;
    }

    @Override
    public boolean shouldConsumeItem(@NotNull BattlePokemon pokemon, @NotNull PokemonBattle battle2, @NotNull String showdownId) {
        return HeldItemManager.DefaultImpls.shouldConsumeItem(this, pokemon, battle2, showdownId);
    }
}

