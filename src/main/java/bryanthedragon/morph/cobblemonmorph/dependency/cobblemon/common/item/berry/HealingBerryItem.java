/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.berry;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item.PokemonSelectingItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.BerryBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.BerryItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle.BagItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\u001d\u0012\u0006\u0010(\u001a\u00020'\u0012\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c\u00a2\u0006\u0004\b)\u0010*J'\u0010\n\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ/\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u000e2\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0017\u0010\u0012\u001a\u00020\u00112\u0006\u0010\r\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b\u0012\u0010\u0013J-\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00050\u000e2\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u0018H\u0016\u00a2\u0006\u0004\b\u001a\u0010\u001bR\u001d\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c8\u0006\u00a2\u0006\f\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b \u0010!R\u001a\u0010#\u001a\u00020\"8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b#\u0010$\u001a\u0004\b%\u0010&\u00a8\u0006+"}, d2={"Lcom/cobblemon/mod/common/item/berry/HealingBerryItem;", "Lcom/cobblemon/mod/common/item/BerryItem;", "Lcom/cobblemon/mod/common/api/item/PokemonSelectingItem;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "Lnet/minecraft/world/item/ItemStack;", "stack", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "battlePokemon", "", "applyToBattlePokemon", "(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/item/ItemStack;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;)V", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lnet/minecraft/world/InteractionResultHolder;", "applyToPokemon", "(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/item/ItemStack;Lcom/cobblemon/mod/common/pokemon/Pokemon;)Lnet/minecraft/world/InteractionResultHolder;", "", "canUseOnPokemon", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "Lnet/minecraft/world/level/Level;", "world", "Lnet/minecraft/world/entity/player/Player;", "user", "Lnet/minecraft/world/InteractionHand;", "hand", "use", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResultHolder;", "Lkotlin/Function0;", "Lcom/bedrockk/molang/Expression;", "amount", "Lkotlin/jvm/functions/Function0;", "getAmount", "()Lkotlin/jvm/functions/Function0;", "Lcom/cobblemon/mod/common/item/battle/BagItem;", "bagItem", "Lcom/cobblemon/mod/common/item/battle/BagItem;", "getBagItem", "()Lcom/cobblemon/mod/common/item/battle/BagItem;", "Lcom/cobblemon/mod/common/block/BerryBlock;", "block", "<init>", "(Lcom/cobblemon/mod/common/block/BerryBlock;Lkotlin/jvm/functions/Function0;)V", "common"})
public final class HealingBerryItem
extends BerryItem
implements PokemonSelectingItem {
    @NotNull
    private final Function0<Expression> amount;
    @NotNull
    private final BagItem bagItem;

    public HealingBerryItem(@NotNull BerryBlock block, @NotNull Function0<? extends Expression> amount) {
        Intrinsics.checkNotNullParameter((Object)block, (String)"block");
        Intrinsics.checkNotNullParameter(amount, (String)"amount");
        super(block);
        this.amount = amount;
        this.bagItem = new BagItem(this){
            final /* synthetic */ HealingBerryItem this$0;
            {
                this.this$0 = $receiver;
            }

            @NotNull
            public String getItemName() {
                Berry berry = this.this$0.berry();
                Intrinsics.checkNotNull((Object)berry);
                return "item.cobblemon." + berry.getIdentifier().m_135815_();
            }

            @NotNull
            public String getShowdownInput(@NotNull BattleActor actor, @NotNull BattlePokemon battlePokemon, @Nullable String data) {
                Intrinsics.checkNotNullParameter((Object)actor, (String)"actor");
                Intrinsics.checkNotNullParameter((Object)battlePokemon, (String)"battlePokemon");
                return "potion " + MoLangExtensionsKt.resolveInt(MoLangExtensionsKt.getGenericRuntime(), (Expression)this.this$0.getAmount().invoke(), battlePokemon);
            }

            public boolean canUse(@NotNull PokemonBattle battle2, @NotNull BattlePokemon target) {
                Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
                Intrinsics.checkNotNullParameter((Object)target, (String)"target");
                return target.getHealth() < target.getMaxHealth() && target.getHealth() > 0;
            }

            public boolean canStillUse(@NotNull ServerPlayer player, @NotNull PokemonBattle battle2, @NotNull BattleActor actor, @NotNull BattlePokemon target, @NotNull ItemStack stack) {
                return BagItem.DefaultImpls.canStillUse(this, player, battle2, actor, target, stack);
            }
        };
    }

    @NotNull
    public final Function0<Expression> getAmount() {
        return this.amount;
    }

    @Override
    @NotNull
    public BagItem getBagItem() {
        return this.bagItem;
    }

    @Override
    public boolean canUseOnPokemon(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        return !pokemon.isFainted() && !pokemon.isFullHealth();
    }

    @Override
    @Nullable
    public InteractionResultHolder<ItemStack> applyToPokemon(@NotNull ServerPlayer player, @NotNull ItemStack stack, @NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        if (pokemon.isFullHealth() || pokemon.isFainted()) {
            return InteractionResultHolder.m_19100_((Object)stack);
        }
        pokemon.setCurrentHealth(Integer.min(pokemon.getCurrentHealth() + MoLangExtensionsKt.resolveInt(MoLangExtensionsKt.getGenericRuntime(), (Expression)this.amount.invoke(), pokemon), pokemon.getHp()));
        player.m_6330_(CobblemonSounds.BERRY_EAT, SoundSource.PLAYERS, 1.0f, 1.0f);
        if (!player.m_7500_()) {
            stack.m_41774_(1);
        }
        return InteractionResultHolder.m_19090_((Object)stack);
    }

    @Override
    public void applyToBattlePokemon(@NotNull ServerPlayer player, @NotNull ItemStack stack, @NotNull BattlePokemon battlePokemon) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
        Intrinsics.checkNotNullParameter((Object)battlePokemon, (String)"battlePokemon");
        PokemonSelectingItem.DefaultImpls.applyToBattlePokemon(this, player, stack, battlePokemon);
        player.m_6330_(CobblemonSounds.BERRY_EAT, SoundSource.PLAYERS, 1.0f, 1.0f);
    }

    @NotNull
    public InteractionResultHolder<ItemStack> m_7203_(@NotNull Level world, @NotNull Player user, @NotNull InteractionHand hand) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)user, (String)"user");
        Intrinsics.checkNotNullParameter((Object)hand, (String)"hand");
        if (world instanceof ServerLevel && user instanceof ServerPlayer) {
            ServerPlayer serverPlayer = (ServerPlayer)user;
            ItemStack itemStack = user.m_21120_(hand);
            Intrinsics.checkNotNullExpressionValue((Object)itemStack, (String)"user.getStackInHand(hand)");
            return this.use(serverPlayer, itemStack);
        }
        InteractionResultHolder interactionResultHolder = super.m_7203_(world, user, hand);
        Intrinsics.checkNotNullExpressionValue((Object)interactionResultHolder, (String)"super<BerryItem>.use(world, user, hand)");
        return interactionResultHolder;
    }

    @Override
    @NotNull
    public InteractionResultHolder<ItemStack> use(@NotNull ServerPlayer player, @NotNull ItemStack stack) {
        return PokemonSelectingItem.DefaultImpls.use(this, player, stack);
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
}

