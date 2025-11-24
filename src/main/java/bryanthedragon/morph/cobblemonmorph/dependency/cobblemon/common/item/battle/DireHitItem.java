/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.CobblemonItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle.BagItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle.SimpleBagItemConvertible;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0007\u00a2\u0006\u0004\b\b\u0010\tR\u001a\u0010\u0004\u001a\u00020\u00038\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/item/battle/DireHitItem;", "Lcom/cobblemon/mod/common/item/CobblemonItem;", "Lcom/cobblemon/mod/common/item/battle/SimpleBagItemConvertible;", "Lcom/cobblemon/mod/common/item/battle/BagItem;", "bagItem", "Lcom/cobblemon/mod/common/item/battle/BagItem;", "getBagItem", "()Lcom/cobblemon/mod/common/item/battle/BagItem;", "<init>", "()V", "common"})
public final class DireHitItem
extends CobblemonItem
implements SimpleBagItemConvertible {
    @NotNull
    private final BagItem bagItem = new BagItem(){
        @NotNull
        private final String itemName;
        {
            this.itemName = "item.cobblemon.dire_hit";
        }

        @NotNull
        public String getItemName() {
            return this.itemName;
        }

        public boolean canUse(@NotNull PokemonBattle battle2, @NotNull BattlePokemon target) {
            Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
            Intrinsics.checkNotNullParameter((Object)target, (String)"target");
            return target.getHealth() > 0;
        }

        @NotNull
        public String getShowdownInput(@NotNull BattleActor actor, @NotNull BattlePokemon battlePokemon, @Nullable String data) {
            Intrinsics.checkNotNullParameter((Object)actor, (String)"actor");
            Intrinsics.checkNotNullParameter((Object)battlePokemon, (String)"battlePokemon");
            Pokemon.incrementFriendship$default(battlePokemon.getEffectedPokemon(), 1, false, 2, null);
            return "dire_hit";
        }

        public boolean canStillUse(@NotNull ServerPlayer player, @NotNull PokemonBattle battle2, @NotNull BattleActor actor, @NotNull BattlePokemon target, @NotNull ItemStack stack) {
            return BagItem.DefaultImpls.canStillUse(this, player, battle2, actor, target, stack);
        }
    };

    public DireHitItem() {
        super(new Item.Properties());
    }

    @Override
    @NotNull
    public BagItem getBagItem() {
        return this.bagItem;
    }

    @Override
    @Nullable
    public BagItem getBagItem(@NotNull ItemStack stack) {
        return SimpleBagItemConvertible.DefaultImpls.getBagItem(this, stack);
    }

    @Override
    public boolean handleInteraction(@NotNull ServerPlayer player, @NotNull BattlePokemon battlePokemon, @NotNull ItemStack stack) {
        return SimpleBagItemConvertible.DefaultImpls.handleInteraction(this, player, battlePokemon, stack);
    }
}

