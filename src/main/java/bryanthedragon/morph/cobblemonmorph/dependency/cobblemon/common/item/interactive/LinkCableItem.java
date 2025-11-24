/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.interaction.EntityInteraction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.interaction.PokemonEntityInteraction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.CobblemonItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.variants.TradeEvolution;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0007\u00a2\u0006\u0004\b\u0012\u0010\u0013J'\u0010\n\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\n\u0010\u000bR \u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\f8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/item/interactive/LinkCableItem;", "Lcom/cobblemon/mod/common/item/CobblemonItem;", "Lcom/cobblemon/mod/common/api/interaction/PokemonEntityInteraction;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "entity", "Lnet/minecraft/world/item/ItemStack;", "stack", "", "processInteraction", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;Lnet/minecraft/world/item/ItemStack;)Z", "", "Lcom/cobblemon/mod/common/api/interaction/PokemonEntityInteraction$Ownership;", "accepted", "Ljava/util/Set;", "getAccepted", "()Ljava/util/Set;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nLinkCableItem.kt\nKotlin\n*S Kotlin\n*F\n+ 1 LinkCableItem.kt\ncom/cobblemon/mod/common/item/interactive/LinkCableItem\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,33:1\n800#2,11:34\n1855#2:45\n1726#2,3:46\n1856#2:49\n*S KotlinDebug\n*F\n+ 1 LinkCableItem.kt\ncom/cobblemon/mod/common/item/interactive/LinkCableItem\n*L\n22#1:34,11\n22#1:45\n25#1:46,3\n22#1:49\n*E\n"})
public final class LinkCableItem
extends CobblemonItem
implements PokemonEntityInteraction {
    @NotNull
    private final Set<PokemonEntityInteraction.Ownership> accepted = SetsKt.setOf((Object)((Object)PokemonEntityInteraction.Ownership.OWNER));

    public LinkCableItem() {
        super(new Item.Properties());
    }

    @Override
    @NotNull
    public Set<PokemonEntityInteraction.Ownership> getAccepted() {
        return this.accepted;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public boolean processInteraction(@NotNull ServerPlayer player, @NotNull PokemonEntity entity2, @NotNull ItemStack stack) {
        void $this$forEach$iv;
        void $this$filterIsInstanceTo$iv$iv;
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
        Pokemon pokemon = entity2.getPokemon();
        Iterable $this$filterIsInstance$iv = pokemon.getLockedEvolutions();
        boolean $i$f$filterIsInstance = false;
        Iterable iterable = $this$filterIsInstance$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterIsInstanceTo = false;
        for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
            if (!(element$iv$iv instanceof TradeEvolution)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        $this$filterIsInstance$iv = (List)destination$iv$iv;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            boolean bl;
            TradeEvolution evolution;
            block5: {
                evolution = (TradeEvolution)element$iv;
                boolean bl2 = false;
                Iterable $this$all$iv = evolution.getRequirements();
                boolean $i$f$all = false;
                if ($this$all$iv instanceof Collection && ((Collection)$this$all$iv).isEmpty()) {
                    bl = true;
                } else {
                    for (Object element$iv2 : $this$all$iv) {
                        EvolutionRequirement it = (EvolutionRequirement)element$iv2;
                        boolean bl3 = false;
                        if (it.check(pokemon)) continue;
                        bl = false;
                        break block5;
                    }
                    bl = true;
                }
            }
            if (!bl || !evolution.evolve(pokemon)) continue;
            EntityInteraction.DefaultImpls.consumeItem$default(this, player, stack, 0, 4, null);
            return true;
        }
        return false;
    }

    @Override
    @Nullable
    public SoundEvent getSound() {
        return PokemonEntityInteraction.DefaultImpls.getSound(this);
    }

    @Override
    public boolean onInteraction(@NotNull ServerPlayer player, @NotNull PokemonEntity entity2, @NotNull ItemStack stack) {
        return PokemonEntityInteraction.DefaultImpls.onInteraction(this, player, entity2, stack);
    }

    @Override
    public void consumeItem(@NotNull ServerPlayer player, @NotNull ItemStack stack, int amount) {
        PokemonEntityInteraction.DefaultImpls.consumeItem(this, player, stack, amount);
    }
}

