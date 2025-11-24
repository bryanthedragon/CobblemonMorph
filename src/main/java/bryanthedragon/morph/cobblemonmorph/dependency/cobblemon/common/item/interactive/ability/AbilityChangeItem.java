/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.ability;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.PotentialAbility;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.interaction.EntityInteraction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.interaction.PokemonEntityInteraction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item.ability.AbilityChanger;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.CobblemonItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0016\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u00020\u00032\u00020\u0004B\u0015\u0012\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00028\u00000\u0014\u00a2\u0006\u0004\b\u0019\u0010\u001aJ'\u0010\f\u001a\u00020\u000b2\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\f\u0010\rR \u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013R\u001d\u0010\u0015\u001a\b\u0012\u0004\u0012\u00028\u00000\u00148\u0006\u00a2\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018\u00a8\u0006\u001b"}, d2={"Lcom/cobblemon/mod/common/item/interactive/ability/AbilityChangeItem;", "Lcom/cobblemon/mod/common/api/abilities/PotentialAbility;", "T", "Lcom/cobblemon/mod/common/item/CobblemonItem;", "Lcom/cobblemon/mod/common/api/interaction/PokemonEntityInteraction;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "entity", "Lnet/minecraft/world/item/ItemStack;", "stack", "", "processInteraction", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;Lnet/minecraft/world/item/ItemStack;)Z", "", "Lcom/cobblemon/mod/common/api/interaction/PokemonEntityInteraction$Ownership;", "accepted", "Ljava/util/Set;", "getAccepted", "()Ljava/util/Set;", "Lcom/cobblemon/mod/common/api/item/ability/AbilityChanger;", "changer", "Lcom/cobblemon/mod/common/api/item/ability/AbilityChanger;", "getChanger", "()Lcom/cobblemon/mod/common/api/item/ability/AbilityChanger;", "<init>", "(Lcom/cobblemon/mod/common/api/item/ability/AbilityChanger;)V", "common"})
public class AbilityChangeItem<T extends PotentialAbility>
extends CobblemonItem
implements PokemonEntityInteraction {
    @NotNull
    private final AbilityChanger<T> changer;
    @NotNull
    private final Set<PokemonEntityInteraction.Ownership> accepted;

    public AbilityChangeItem(@NotNull AbilityChanger<T> changer) {
        Intrinsics.checkNotNullParameter(changer, (String)"changer");
        super(new Item.Properties());
        this.changer = changer;
        this.accepted = SetsKt.setOf((Object)((Object)PokemonEntityInteraction.Ownership.OWNER));
    }

    @NotNull
    public final AbilityChanger<T> getChanger() {
        return this.changer;
    }

    @Override
    @NotNull
    public Set<PokemonEntityInteraction.Ownership> getAccepted() {
        return this.accepted;
    }

    @Override
    public boolean processInteraction(@NotNull ServerPlayer player, @NotNull PokemonEntity entity2, @NotNull ItemStack stack) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
        if (this.changer.performChange(entity2.getPokemon())) {
            EntityInteraction.DefaultImpls.consumeItem$default(this, player, stack, 0, 4, null);
            Object[] objectArray = new Object[2];
            objectArray[0] = entity2.getPokemon().getDisplayName();
            Intrinsics.checkNotNullExpressionValue((Object)MiscUtilsKt.asTranslated(entity2.getPokemon().getAbility().getDisplayName()), (String)"entity.pokemon.ability.displayName.asTranslated()");
            MutableComponent feedback = LocalizationUtilsKt.lang("ability_changer.changed", objectArray);
            player.m_213846_((Component)feedback);
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

