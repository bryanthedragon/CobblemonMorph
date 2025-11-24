/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle.BagItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle.BagItemConvertible;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J\u0019\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0014\u0010\b\u001a\u00020\u00048&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0007\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/item/battle/SimpleBagItemConvertible;", "Lcom/cobblemon/mod/common/item/battle/BagItemConvertible;", "Lnet/minecraft/world/item/ItemStack;", "stack", "Lcom/cobblemon/mod/common/item/battle/BagItem;", "getBagItem", "(Lnet/minecraft/world/item/ItemStack;)Lcom/cobblemon/mod/common/item/battle/BagItem;", "()Lcom/cobblemon/mod/common/item/battle/BagItem;", "bagItem", "common"})
public interface SimpleBagItemConvertible
extends BagItemConvertible {
    @NotNull
    public BagItem getBagItem();

    @Override
    @Nullable
    public BagItem getBagItem(@NotNull ItemStack var1);

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    @SourceDebugExtension(value={"SMAP\nSimpleBagItemConvertible.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SimpleBagItemConvertible.kt\ncom/cobblemon/mod/common/item/battle/SimpleBagItemConvertible$DefaultImpls\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,24:1\n1#2:25\n*E\n"})
    public static final class DefaultImpls {
        @Nullable
        public static BagItem getBagItem(@NotNull SimpleBagItemConvertible $this, @NotNull ItemStack stack) {
            BagItem bagItem2;
            Item item;
            Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
            Item it = item = stack.m_41720_();
            boolean bl = false;
            Object object = Intrinsics.areEqual((Object)it, (Object)$this) ? item : null;
            if (object != null) {
                it = object;
                boolean bl2 = false;
                bagItem2 = $this.getBagItem();
            } else {
                bagItem2 = null;
            }
            return bagItem2;
        }

        public static boolean handleInteraction(@NotNull SimpleBagItemConvertible $this, @NotNull ServerPlayer player, @NotNull BattlePokemon battlePokemon, @NotNull ItemStack stack) {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            Intrinsics.checkNotNullParameter((Object)battlePokemon, (String)"battlePokemon");
            Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
            return BagItemConvertible.DefaultImpls.handleInteraction($this, player, battlePokemon, stack);
        }
    }
}

