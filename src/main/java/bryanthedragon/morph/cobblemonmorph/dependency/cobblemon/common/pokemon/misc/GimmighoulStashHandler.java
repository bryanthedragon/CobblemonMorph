/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.misc;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.HeldItemEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.IntSpeciesFeature;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0011\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b#\u0010$J\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u001d\u0010\f\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\t\u00a2\u0006\u0004\b\f\u0010\rJ%\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0015\u001a\u00020\u00148\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0019\u001a\u00020\u00148\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\u0019\u0010\u0016\u001a\u0004\b\u001a\u0010\u0018R\u001a\u0010\u001b\u001a\u00020\u00148\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\u001b\u0010\u0016\u001a\u0004\b\u001c\u0010\u0018R\u001a\u0010\u001d\u001a\u00020\u00148\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\u001d\u0010\u0016\u001a\u0004\b\u001e\u0010\u0018R\u001a\u0010\u001f\u001a\u00020\u00148\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\u001f\u0010\u0016\u001a\u0004\b \u0010\u0018R\u001a\u0010!\u001a\u00020\u00148\u0006X\u0086D\u00a2\u0006\f\n\u0004\b!\u0010\u0016\u001a\u0004\b\"\u0010\u0018\u00a8\u0006%"}, d2={"Lcom/cobblemon/mod/common/pokemon/misc/GimmighoulStashHandler;", "", "Lcom/cobblemon/mod/common/api/events/pokemon/HeldItemEvent$Post;", "event", "", "giveHeldItem", "(Lcom/cobblemon/mod/common/api/events/pokemon/HeldItemEvent$Post;)V", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lnet/minecraft/world/item/Item;", "item", "", "handleItem", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lnet/minecraft/world/item/Item;)Z", "Lnet/minecraft/world/entity/player/Player;", "player", "Lnet/minecraft/world/InteractionHand;", "hand", "interactMob", "(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "", "BLOCK_VALUE", "I", "getBLOCK_VALUE", "()I", "COIN_VALUE", "getCOIN_VALUE", "INGOT_VALUE", "getINGOT_VALUE", "POUCH_VALUE", "getPOUCH_VALUE", "SACK_VALUE", "getSACK_VALUE", "SCRAP_VALUE", "getSCRAP_VALUE", "<init>", "()V", "common"})
public final class GimmighoulStashHandler {
    @NotNull
    public static final GimmighoulStashHandler INSTANCE = new GimmighoulStashHandler();
    private static final int COIN_VALUE = 1;
    private static final int POUCH_VALUE = COIN_VALUE * 9;
    private static final int SACK_VALUE = POUCH_VALUE * 9;
    private static final int SCRAP_VALUE = 1;
    private static final int INGOT_VALUE = SCRAP_VALUE * 4;
    private static final int BLOCK_VALUE = INGOT_VALUE * 9;

    private GimmighoulStashHandler() {
    }

    public final int getCOIN_VALUE() {
        return COIN_VALUE;
    }

    public final int getPOUCH_VALUE() {
        return POUCH_VALUE;
    }

    public final int getSACK_VALUE() {
        return SACK_VALUE;
    }

    public final int getSCRAP_VALUE() {
        return SCRAP_VALUE;
    }

    public final int getINGOT_VALUE() {
        return INGOT_VALUE;
    }

    public final int getBLOCK_VALUE() {
        return BLOCK_VALUE;
    }

    public final boolean interactMob(@NotNull Player player, @NotNull InteractionHand hand, @NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)hand, (String)"hand");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        ItemStack itemStack = player.m_21120_(hand);
        boolean success = false;
        if (player instanceof ServerPlayer && Intrinsics.areEqual((Object)pokemon.getOwnerPlayer(), (Object)player)) {
            Item item = itemStack.m_41720_();
            Intrinsics.checkNotNullExpressionValue((Object)item, (String)"itemStack.item");
            success = this.handleItem(pokemon, item);
            if (success) {
                itemStack.m_41774_(1);
            }
        }
        return success;
    }

    public final void giveHeldItem(@NotNull HeldItemEvent.Post event) {
        Intrinsics.checkNotNullParameter((Object)event, (String)"event");
        Pokemon pokemon = event.getPokemon();
        Item item = event.getReceived().m_41720_();
        Intrinsics.checkNotNullExpressionValue((Object)item, (String)"item");
        if (this.handleItem(pokemon, item)) {
            pokemon.removeHeldItem();
        }
    }

    public final boolean handleItem(@NotNull Pokemon pokemon, @NotNull Item item) {
        int increase;
        Item item2;
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)item, (String)"item");
        IntSpeciesFeature goldHoard = (IntSpeciesFeature)pokemon.getFeature("gimmighoul_coins");
        IntSpeciesFeature netheriteHoard = (IntSpeciesFeature)pokemon.getFeature("gimmighoul_netherite");
        if (goldHoard != null && goldHoard.getValue() < 999) {
            item2 = item;
            int n = Intrinsics.areEqual((Object)item2, (Object)((Object)CobblemonItems.RELIC_COIN)) ? COIN_VALUE : (Intrinsics.areEqual((Object)item2, (Object)CobblemonItems.RELIC_COIN_POUCH) ? POUCH_VALUE : (increase = Intrinsics.areEqual((Object)item2, (Object)CobblemonItems.RELIC_COIN_SACK) ? SACK_VALUE : 0));
            if (increase != 0) {
                goldHoard.setValue(goldHoard.getValue() + increase);
                if (goldHoard.getValue() > 999) {
                    goldHoard.setValue(999);
                }
                if (pokemon.getEntity() != null) {
                    PokemonEntity pokemonEntity = pokemon.getEntity();
                    Intrinsics.checkNotNull((Object)pokemonEntity);
                    pokemonEntity.m_5496_(CobblemonSounds.GIMMIGHOUL_GIVE_ITEM_SMALL, 1.0f, 1.0f);
                }
                pokemon.markFeatureDirty(goldHoard);
                return true;
            }
        }
        if (netheriteHoard != null && netheriteHoard.getValue() < 256) {
            item2 = item;
            int n = Intrinsics.areEqual((Object)item2, (Object)Items.f_42419_) ? SCRAP_VALUE : (Intrinsics.areEqual((Object)item2, (Object)Items.f_42418_) ? INGOT_VALUE : (increase = Intrinsics.areEqual((Object)item2, (Object)Items.f_42791_) ? BLOCK_VALUE : 0));
            if (increase != 0) {
                netheriteHoard.setValue(netheriteHoard.getValue() + increase);
                if (netheriteHoard.getValue() > 256) {
                    netheriteHoard.setValue(256);
                }
                if (pokemon.getEntity() != null) {
                    PokemonEntity pokemonEntity = pokemon.getEntity();
                    Intrinsics.checkNotNull((Object)pokemonEntity);
                    pokemonEntity.m_5496_(CobblemonSounds.GIMMIGHOUL_GIVE_ITEM_SMALL, 1.0f, 1.0f);
                }
                pokemon.markFeatureDirty(netheriteHoard);
                return true;
            }
        }
        return false;
    }
}

