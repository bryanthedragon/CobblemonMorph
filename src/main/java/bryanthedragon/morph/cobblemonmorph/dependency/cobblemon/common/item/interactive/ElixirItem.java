/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.Ref$BooleanRef
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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item.PokemonSelectingItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Move;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.CobblemonItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle.BagItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\u0012\u0006\u0010!\u001a\u00020\u0011\u00a2\u0006\u0004\b%\u0010&J'\u0010\n\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ-\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00050\u000e2\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0017\u0010\u0012\u001a\u00020\u00112\u0006\u0010\r\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b\u0012\u0010\u0013J-\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00050\u000e2\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u0018H\u0016\u00a2\u0006\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001d\u001a\u00020\u001c8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 R\u0017\u0010!\u001a\u00020\u00118\u0006\u00a2\u0006\f\n\u0004\b!\u0010\"\u001a\u0004\b#\u0010$\u00a8\u0006'"}, d2={"Lcom/cobblemon/mod/common/item/interactive/ElixirItem;", "Lcom/cobblemon/mod/common/item/CobblemonItem;", "Lcom/cobblemon/mod/common/api/item/PokemonSelectingItem;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "Lnet/minecraft/world/item/ItemStack;", "stack", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "battlePokemon", "", "applyToBattlePokemon", "(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/item/ItemStack;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;)V", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lnet/minecraft/world/InteractionResultHolder;", "applyToPokemon", "(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/item/ItemStack;Lcom/cobblemon/mod/common/pokemon/Pokemon;)Lnet/minecraft/world/InteractionResultHolder;", "", "canUseOnPokemon", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "Lnet/minecraft/world/level/Level;", "world", "Lnet/minecraft/world/entity/player/Player;", "user", "Lnet/minecraft/world/InteractionHand;", "hand", "use", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResultHolder;", "Lcom/cobblemon/mod/common/item/battle/BagItem;", "bagItem", "Lcom/cobblemon/mod/common/item/battle/BagItem;", "getBagItem", "()Lcom/cobblemon/mod/common/item/battle/BagItem;", "max", "Z", "getMax", "()Z", "<init>", "(Z)V", "common"})
@SourceDebugExtension(value={"SMAP\nElixirItem.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ElixirItem.kt\ncom/cobblemon/mod/common/item/interactive/ElixirItem\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,85:1\n1747#2,3:86\n*S KotlinDebug\n*F\n+ 1 ElixirItem.kt\ncom/cobblemon/mod/common/item/interactive/ElixirItem\n*L\n44#1:86,3\n*E\n"})
public final class ElixirItem
extends CobblemonItem
implements PokemonSelectingItem {
    private final boolean max;
    @NotNull
    private final BagItem bagItem;

    public ElixirItem(boolean max2) {
        super(new Item.Properties());
        this.max = max2;
        this.bagItem = new BagItem(this){
            @NotNull
            private final String itemName;
            final /* synthetic */ ElixirItem this$0;
            {
                this.this$0 = $receiver;
                this.itemName = "item.cobblemon." + ($receiver.getMax() ? "max_elixir" : "elixir");
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
                String string = "elixir";
                ElixirItem elixirItem = this.this$0;
                String it = string;
                boolean bl = false;
                return !elixirItem.getMax() ? it + " 10" : it;
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
                    Move it = (Move)element$iv;
                    boolean bl2 = false;
                    if (!(it.getCurrentPp() < it.getMaxPp())) continue;
                    bl = true;
                    break block3;
                }
                bl = false;
            }
        }
        return bl;
    }

    @Override
    @NotNull
    public InteractionResultHolder<ItemStack> applyToPokemon(@NotNull ServerPlayer player, @NotNull ItemStack stack, @NotNull Pokemon pokemon) {
        InteractionResultHolder interactionResultHolder;
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Ref.BooleanRef changed = new Ref.BooleanRef();
        pokemon.getMoveSet().doWithoutEmitting((Function0<Unit>)((Function0)new Function0<Unit>(pokemon, this, changed){
            final /* synthetic */ Pokemon $pokemon;
            final /* synthetic */ ElixirItem this$0;
            final /* synthetic */ Ref.BooleanRef $changed;
            {
                this.$pokemon = $pokemon;
                this.this$0 = $receiver;
                this.$changed = $changed;
                super(0);
            }

            /*
             * WARNING - void declaration
             */
            public final void invoke() {
                void $this$forEach$iv;
                Iterable iterable = this.$pokemon.getMoveSet().getMoves();
                ElixirItem elixirItem = this.this$0;
                Ref.BooleanRef booleanRef = this.$changed;
                boolean $i$f$forEach = false;
                for (T element$iv : $this$forEach$iv) {
                    Move it = (Move)element$iv;
                    boolean bl = false;
                    if (it.getCurrentPp() >= it.getMaxPp()) continue;
                    if (elixirItem.getMax()) {
                        it.setCurrentPp(it.getMaxPp());
                    } else {
                        it.setCurrentPp(Math.min(it.getCurrentPp() + 10, it.getMaxPp()));
                    }
                    booleanRef.element = true;
                }
            }
        }));
        if (changed.element) {
            pokemon.getMoveSet().update();
            if (!player.m_7500_()) {
                stack.m_41774_(1);
                PlayerExtensionsKt.giveOrDropItemStack$default((Player)player, new ItemStack((ItemLike)Items.f_42590_), false, 2, null);
            }
            player.m_6330_(CobblemonSounds.MEDICINE_LIQUID_USE, SoundSource.PLAYERS, 1.0f, 1.0f);
            InteractionResultHolder interactionResultHolder2 = InteractionResultHolder.m_19090_((Object)stack);
            interactionResultHolder = interactionResultHolder2;
            Intrinsics.checkNotNullExpressionValue((Object)interactionResultHolder2, (String)"{\n            pokemon.mo\u2026.success(stack)\n        }");
        } else {
            InteractionResultHolder interactionResultHolder3 = InteractionResultHolder.m_19100_((Object)stack);
            interactionResultHolder = interactionResultHolder3;
            Intrinsics.checkNotNullExpressionValue((Object)interactionResultHolder3, (String)"{\n            TypedActio\u2026ult.fail(stack)\n        }");
        }
        return interactionResultHolder;
    }

    @Override
    public void applyToBattlePokemon(@NotNull ServerPlayer player, @NotNull ItemStack stack, @NotNull BattlePokemon battlePokemon) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
        Intrinsics.checkNotNullParameter((Object)battlePokemon, (String)"battlePokemon");
        PokemonSelectingItem.DefaultImpls.applyToBattlePokemon(this, player, stack, battlePokemon);
        player.m_6330_(CobblemonSounds.MEDICINE_LIQUID_USE, SoundSource.PLAYERS, 1.0f, 1.0f);
    }

    @NotNull
    public InteractionResultHolder<ItemStack> m_7203_(@NotNull Level world, @NotNull Player user, @NotNull InteractionHand hand) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)user, (String)"user");
        Intrinsics.checkNotNullParameter((Object)hand, (String)"hand");
        if (world instanceof ServerLevel && user instanceof ServerPlayer) {
            ItemStack stack = user.m_21120_(hand);
            ServerPlayer serverPlayer = (ServerPlayer)user;
            Intrinsics.checkNotNullExpressionValue((Object)stack, (String)"stack");
            return this.use(serverPlayer, stack);
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

