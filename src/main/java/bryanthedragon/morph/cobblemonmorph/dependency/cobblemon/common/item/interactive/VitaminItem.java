/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item.PokemonSelectingItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.CobblemonItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0001\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 #2\u00020\u00012\u00020\u0002:\u0001#B\u000f\u0012\u0006\u0010\u001d\u001a\u00020\u001c\u00a2\u0006\u0004\b!\u0010\"J-\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\t2\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0017\u0010\r\u001a\u00020\f2\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\r\u0010\u000eJ-\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00050\t2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u0013H\u0016\u00a2\u0006\u0004\b\u0015\u0010\u0016R\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u00178\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001bR\u0017\u0010\u001d\u001a\u00020\u001c8\u0006\u00a2\u0006\f\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 \u00a8\u0006$"}, d2={"Lcom/cobblemon/mod/common/item/interactive/VitaminItem;", "Lcom/cobblemon/mod/common/item/CobblemonItem;", "Lcom/cobblemon/mod/common/api/item/PokemonSelectingItem;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "Lnet/minecraft/world/item/ItemStack;", "stack", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lnet/minecraft/world/InteractionResultHolder;", "applyToPokemon", "(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/item/ItemStack;Lcom/cobblemon/mod/common/pokemon/Pokemon;)Lnet/minecraft/world/InteractionResultHolder;", "", "canUseOnPokemon", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "Lnet/minecraft/world/level/Level;", "world", "Lnet/minecraft/world/entity/player/Player;", "user", "Lnet/minecraft/world/InteractionHand;", "hand", "use", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResultHolder;", "", "bagItem", "Ljava/lang/Void;", "getBagItem", "()Ljava/lang/Void;", "Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;", "stat", "Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;", "getStat", "()Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;", "<init>", "(Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;)V", "Companion", "common"})
public final class VitaminItem
extends CobblemonItem
implements PokemonSelectingItem {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final Stat stat;
    @Nullable
    private final Void bagItem;
    public static final int EV_YIELD = 10;

    public VitaminItem(@NotNull Stat stat) {
        Intrinsics.checkNotNullParameter((Object)stat, (String)"stat");
        super(new Item.Properties());
        this.stat = stat;
    }

    @NotNull
    public final Stat getStat() {
        return this.stat;
    }

    @Nullable
    public Void getBagItem() {
        return this.bagItem;
    }

    @Override
    public boolean canUseOnPokemon(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        return pokemon.getEvs().getOrDefault(this.stat) < 252;
    }

    @Override
    @NotNull
    public InteractionResultHolder<ItemStack> applyToPokemon(@NotNull ServerPlayer player, @NotNull ItemStack stack, @NotNull Pokemon pokemon) {
        InteractionResultHolder interactionResultHolder;
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        int evsGained = pokemon.getEvs().add(this.stat, 10);
        if (evsGained > 0) {
            player.m_6330_(CobblemonSounds.MEDICINE_PILLS_USE, SoundSource.PLAYERS, 1.0f, 1.0f);
            if (!player.m_7500_()) {
                stack.m_41774_(1);
            }
            InteractionResultHolder interactionResultHolder2 = InteractionResultHolder.m_19090_((Object)stack);
            interactionResultHolder = interactionResultHolder2;
            Intrinsics.checkNotNullExpressionValue((Object)interactionResultHolder2, (String)"{\n            player.pla\u2026.success(stack)\n        }");
        } else {
            InteractionResultHolder interactionResultHolder3 = InteractionResultHolder.m_19100_((Object)stack);
            interactionResultHolder = interactionResultHolder3;
            Intrinsics.checkNotNullExpressionValue((Object)interactionResultHolder3, (String)"{\n            TypedActio\u2026ult.fail(stack)\n        }");
        }
        return interactionResultHolder;
    }

    @NotNull
    public InteractionResultHolder<ItemStack> m_7203_(@NotNull Level world, @NotNull Player user, @NotNull InteractionHand hand) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)user, (String)"user");
        Intrinsics.checkNotNullParameter((Object)hand, (String)"hand");
        if (user instanceof ServerPlayer) {
            ServerPlayer serverPlayer = (ServerPlayer)user;
            ItemStack itemStack = user.m_21120_(hand);
            Intrinsics.checkNotNullExpressionValue((Object)itemStack, (String)"user.getStackInHand(hand)");
            return this.use(serverPlayer, itemStack);
        }
        InteractionResultHolder interactionResultHolder = InteractionResultHolder.m_19090_((Object)user.m_21120_(hand));
        Intrinsics.checkNotNullExpressionValue((Object)interactionResultHolder, (String)"success(user.getStackInHand(hand))");
        return interactionResultHolder;
    }

    @Override
    @NotNull
    public InteractionResultHolder<ItemStack> use(@NotNull ServerPlayer player, @NotNull ItemStack stack) {
        return PokemonSelectingItem.DefaultImpls.use(this, player, stack);
    }

    @Override
    public void applyToBattlePokemon(@NotNull ServerPlayer player, @NotNull ItemStack stack, @NotNull BattlePokemon battlePokemon) {
        PokemonSelectingItem.DefaultImpls.applyToBattlePokemon(this, player, stack, battlePokemon);
    }

    @Override
    public boolean canUseOnBattlePokemon(@NotNull BattlePokemon battlePokemon) {
        return PokemonSelectingItem.DefaultImpls.canUseOnBattlePokemon(this, battlePokemon);
    }

    @Override
    @NotNull
    public InteractionResultHolder<ItemStack> interactWithSpecificBattle(@NotNull ServerPlayer player, @NotNull ItemStack stack, @NotNull BattlePokemon battlePokemon) {
        return PokemonSelectingItem.DefaultImpls.interactWithSpecificBattle(this, player, stack, battlePokemon);
    }

    @Override
    @NotNull
    public InteractionResultHolder<ItemStack> interactGeneral(@NotNull ServerPlayer player, @NotNull ItemStack stack) {
        return PokemonSelectingItem.DefaultImpls.interactGeneral(this, player, stack);
    }

    @Override
    @NotNull
    public InteractionResultHolder<ItemStack> interactGeneralBattle(@NotNull ServerPlayer player, @NotNull ItemStack stack, @NotNull BattleActor actor) {
        return PokemonSelectingItem.DefaultImpls.interactGeneralBattle(this, player, stack, actor);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0007"}, d2={"Lcom/cobblemon/mod/common/item/interactive/VitaminItem$Companion;", "", "", "EV_YIELD", "I", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

