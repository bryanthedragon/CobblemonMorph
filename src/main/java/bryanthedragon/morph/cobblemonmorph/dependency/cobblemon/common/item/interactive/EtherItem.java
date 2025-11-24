/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item.PokemonAndMoveSelectingItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Move;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.CobblemonItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle.BagItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\u0012\u0006\u0010%\u001a\u00020\u0012\u00a2\u0006\u0004\b)\u0010*J/\u0010\f\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\f\u0010\rJ/\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u0017\u0010\u0013\u001a\u00020\u00122\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0017\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u000f\u001a\u00020\u000eH\u0016\u00a2\u0006\u0004\b\u0015\u0010\u0016J-\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00050\u001d2\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001c\u001a\u00020\u001bH\u0016\u00a2\u0006\u0004\b\u001e\u0010\u001fR\u001a\u0010!\u001a\u00020 8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b!\u0010\"\u001a\u0004\b#\u0010$R\u0017\u0010%\u001a\u00020\u00128\u0006\u00a2\u0006\f\n\u0004\b%\u0010&\u001a\u0004\b'\u0010(\u00a8\u0006+"}, d2={"Lcom/cobblemon/mod/common/item/interactive/EtherItem;", "Lcom/cobblemon/mod/common/item/CobblemonItem;", "Lcom/cobblemon/mod/common/api/item/PokemonAndMoveSelectingItem;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "Lnet/minecraft/world/item/ItemStack;", "stack", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "battlePokemon", "Lcom/cobblemon/mod/common/api/moves/Move;", "move", "", "applyToBattlePokemon", "(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/item/ItemStack;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;Lcom/cobblemon/mod/common/api/moves/Move;)V", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "applyToPokemon", "(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/item/ItemStack;Lcom/cobblemon/mod/common/pokemon/Pokemon;Lcom/cobblemon/mod/common/api/moves/Move;)V", "", "canUseOnMove", "(Lcom/cobblemon/mod/common/api/moves/Move;)Z", "canUseOnPokemon", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "Lnet/minecraft/world/level/Level;", "world", "Lnet/minecraft/world/entity/player/Player;", "user", "Lnet/minecraft/world/InteractionHand;", "hand", "Lnet/minecraft/world/InteractionResultHolder;", "use", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResultHolder;", "Lcom/cobblemon/mod/common/item/battle/BagItem;", "bagItem", "Lcom/cobblemon/mod/common/item/battle/BagItem;", "getBagItem", "()Lcom/cobblemon/mod/common/item/battle/BagItem;", "max", "Z", "getMax", "()Z", "<init>", "(Z)V", "common"})
@SourceDebugExtension(value={"SMAP\nEtherItem.kt\nKotlin\n*S Kotlin\n*F\n+ 1 EtherItem.kt\ncom/cobblemon/mod/common/item/interactive/EtherItem\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,70:1\n1747#2,3:71\n1#3:74\n*S KotlinDebug\n*F\n+ 1 EtherItem.kt\ncom/cobblemon/mod/common/item/interactive/EtherItem\n*L\n46#1:71,3\n*E\n"})
public final class EtherItem
extends CobblemonItem
implements PokemonAndMoveSelectingItem {
    private final boolean max;
    @NotNull
    private final BagItem bagItem;

    public EtherItem(boolean max2) {
        super(new Item.Properties());
        this.max = max2;
        this.bagItem = new BagItem(this){
            @NotNull
            private final String itemName;
            final /* synthetic */ EtherItem this$0;
            {
                this.this$0 = $receiver;
                this.itemName = "item.cobblemon." + ($receiver.getMax() ? "max_ether" : "ether");
            }

            @NotNull
            public String getItemName() {
                return this.itemName;
            }

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            public boolean canUse(@NotNull PokemonBattle battle2, @NotNull BattlePokemon target) {
                boolean bl;
                Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
                Intrinsics.checkNotNullParameter((Object)target, (String)"target");
                if (target.getHealth() <= 0) return false;
                Iterable $this$any$iv = target.getMoveSet();
                boolean $i$f$any = false;
                if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                    return false;
                }
                Iterator<T> iterator = $this$any$iv.iterator();
                do {
                    if (!iterator.hasNext()) return false;
                    T element$iv = iterator.next();
                    Move it = (Move)element$iv;
                    boolean bl2 = false;
                    if (it.getCurrentPp() < it.getMaxPp()) {
                        return true;
                    }
                    bl = false;
                } while (!bl);
                return true;
            }

            @NotNull
            public String getShowdownInput(@NotNull BattleActor actor, @NotNull BattlePokemon battlePokemon, @Nullable String data) {
                Intrinsics.checkNotNullParameter((Object)actor, (String)"actor");
                Intrinsics.checkNotNullParameter((Object)battlePokemon, (String)"battlePokemon");
                return "ether " + data + (this.this$0.getMax() ? "" : " 10");
            }

            public boolean canStillUse(@NotNull ServerPlayer player, @NotNull PokemonBattle battle2, @NotNull BattleActor actor, @NotNull BattlePokemon target, @NotNull ItemStack stack) {
                return BagItem.DefaultImpls.canStillUse(this, player, battle2, actor, target, stack);
            }
        };
    }

    public final boolean getMax() {
        return this.max;
    }

    @Override
    @NotNull
    public BagItem getBagItem() {
        return this.bagItem;
    }

    @Override
    public boolean canUseOnMove(@NotNull Move move) {
        Intrinsics.checkNotNullParameter((Object)move, (String)"move");
        return move.getCurrentPp() < move.getMaxPp();
    }

    @Override
    public boolean canUseOnPokemon(@NotNull Pokemon pokemon) {
        boolean bl;
        block3: {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Iterable $this$any$iv = pokemon.getMoveSet();
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    Move p0 = (Move)element$iv;
                    boolean bl2 = false;
                    if (!this.canUseOnMove(p0)) continue;
                    bl = true;
                    break block3;
                }
                bl = false;
            }
        }
        return bl;
    }

    @Override
    public void applyToPokemon(@NotNull ServerPlayer player, @NotNull ItemStack stack, @NotNull Pokemon pokemon, @NotNull Move move) {
        Object v0;
        block3: {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Intrinsics.checkNotNullParameter((Object)move, (String)"move");
            Iterable iterable = pokemon.getMoveSet();
            for (Object t : iterable) {
                Move it = (Move)t;
                boolean bl = false;
                if (!Intrinsics.areEqual((Object)it.getTemplate(), (Object)move.getTemplate())) continue;
                v0 = t;
                break block3;
            }
            v0 = null;
        }
        Move moveToRecover = v0;
        if (moveToRecover != null && moveToRecover.getCurrentPp() < moveToRecover.getMaxPp()) {
            moveToRecover.setCurrentPp(this.max ? moveToRecover.getMaxPp() : Math.min(moveToRecover.getMaxPp(), moveToRecover.getCurrentPp() + 10));
            player.m_6330_(CobblemonSounds.MEDICINE_LIQUID_USE, SoundSource.PLAYERS, 1.0f, 1.0f);
            if (!player.m_7500_()) {
                stack.m_41774_(1);
                PlayerExtensionsKt.giveOrDropItemStack$default((Player)player, new ItemStack((ItemLike)Items.f_42590_), false, 2, null);
            }
        }
    }

    @Override
    public void applyToBattlePokemon(@NotNull ServerPlayer player, @NotNull ItemStack stack, @NotNull BattlePokemon battlePokemon, @NotNull Move move) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
        Intrinsics.checkNotNullParameter((Object)battlePokemon, (String)"battlePokemon");
        Intrinsics.checkNotNullParameter((Object)move, (String)"move");
        PokemonAndMoveSelectingItem.DefaultImpls.applyToBattlePokemon(this, player, stack, battlePokemon, move);
        player.m_6330_(CobblemonSounds.MEDICINE_LIQUID_USE, SoundSource.PLAYERS, 1.0f, 1.0f);
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
            InteractionResultHolder interactionResultHolder = this.use(serverPlayer, itemStack);
            if (interactionResultHolder == null) {
                InteractionResultHolder interactionResultHolder2 = InteractionResultHolder.m_19098_((Object)user.m_21120_(hand));
                interactionResultHolder = interactionResultHolder2;
                Intrinsics.checkNotNullExpressionValue((Object)interactionResultHolder2, (String)"pass(user.getStackInHand(hand))");
            }
            return interactionResultHolder;
        }
        InteractionResultHolder interactionResultHolder = InteractionResultHolder.m_19090_((Object)user.m_21120_(hand));
        Intrinsics.checkNotNullExpressionValue((Object)interactionResultHolder, (String)"success(user.getStackInHand(hand))");
        return interactionResultHolder;
    }

    @Override
    public boolean canUseOnMove(@NotNull Pokemon pokemon, @NotNull Move move) {
        return PokemonAndMoveSelectingItem.DefaultImpls.canUseOnMove(this, pokemon, move);
    }

    @Override
    @Nullable
    public InteractionResultHolder<ItemStack> use(@NotNull ServerPlayer player, @NotNull ItemStack stack) {
        return PokemonAndMoveSelectingItem.DefaultImpls.use(this, player, stack);
    }

    @Override
    public boolean canUseOnBattlePokemon(@NotNull BattlePokemon battlePokemon) {
        return PokemonAndMoveSelectingItem.DefaultImpls.canUseOnBattlePokemon(this, battlePokemon);
    }

    @Override
    @Nullable
    public InteractionResultHolder<ItemStack> interactWithSpecific(@NotNull ServerPlayer player, @NotNull ItemStack stack, @NotNull Pokemon pokemon) {
        return PokemonAndMoveSelectingItem.DefaultImpls.interactWithSpecific(this, player, stack, pokemon);
    }

    @Override
    @Nullable
    public InteractionResultHolder<ItemStack> interactWithSpecificBattle(@NotNull ServerPlayer player, @NotNull ItemStack stack, @NotNull BattlePokemon battlePokemon) {
        return PokemonAndMoveSelectingItem.DefaultImpls.interactWithSpecificBattle(this, player, stack, battlePokemon);
    }

    @Override
    @Nullable
    public InteractionResultHolder<ItemStack> interactGeneral(@NotNull ServerPlayer player, @NotNull ItemStack stack) {
        return PokemonAndMoveSelectingItem.DefaultImpls.interactGeneral(this, player, stack);
    }

    @Override
    @Nullable
    public InteractionResultHolder<ItemStack> interactGeneralBattle(@NotNull ServerPlayer player, @NotNull ItemStack stack, @NotNull BattleActor actor) {
        return PokemonAndMoveSelectingItem.DefaultImpls.interactGeneralBattle(this, player, stack, actor);
    }
}

