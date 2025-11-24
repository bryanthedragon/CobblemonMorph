/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item.PokemonAndMoveSelectingItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Move;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.BerryBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.BerryItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle.BagItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\u001d\u0012\u0006\u0010,\u001a\u00020+\u0012\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020!0 \u00a2\u0006\u0004\b-\u0010.J/\u0010\f\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\f\u0010\rJ/\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u0017\u0010\u0013\u001a\u00020\u00122\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0017\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u000f\u001a\u00020\u000eH\u0016\u00a2\u0006\u0004\b\u0015\u0010\u0016J-\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00050\u001d2\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001c\u001a\u00020\u001bH\u0016\u00a2\u0006\u0004\b\u001e\u0010\u001fR\u001d\u0010\"\u001a\b\u0012\u0004\u0012\u00020!0 8\u0006\u00a2\u0006\f\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%R\u001a\u0010'\u001a\u00020&8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b'\u0010(\u001a\u0004\b)\u0010*\u00a8\u0006/"}, d2={"Lcom/cobblemon/mod/common/item/berry/PPRestoringBerryItem;", "Lcom/cobblemon/mod/common/item/BerryItem;", "Lcom/cobblemon/mod/common/api/item/PokemonAndMoveSelectingItem;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "Lnet/minecraft/world/item/ItemStack;", "stack", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "battlePokemon", "Lcom/cobblemon/mod/common/api/moves/Move;", "move", "", "applyToBattlePokemon", "(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/item/ItemStack;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;Lcom/cobblemon/mod/common/api/moves/Move;)V", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "applyToPokemon", "(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/item/ItemStack;Lcom/cobblemon/mod/common/pokemon/Pokemon;Lcom/cobblemon/mod/common/api/moves/Move;)V", "", "canUseOnMove", "(Lcom/cobblemon/mod/common/api/moves/Move;)Z", "canUseOnPokemon", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "Lnet/minecraft/world/level/Level;", "world", "Lnet/minecraft/world/entity/player/Player;", "user", "Lnet/minecraft/world/InteractionHand;", "hand", "Lnet/minecraft/world/InteractionResultHolder;", "use", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResultHolder;", "Lkotlin/Function0;", "Lcom/bedrockk/molang/Expression;", "amount", "Lkotlin/jvm/functions/Function0;", "getAmount", "()Lkotlin/jvm/functions/Function0;", "Lcom/cobblemon/mod/common/item/battle/BagItem;", "bagItem", "Lcom/cobblemon/mod/common/item/battle/BagItem;", "getBagItem", "()Lcom/cobblemon/mod/common/item/battle/BagItem;", "Lcom/cobblemon/mod/common/block/BerryBlock;", "block", "<init>", "(Lcom/cobblemon/mod/common/block/BerryBlock;Lkotlin/jvm/functions/Function0;)V", "common"})
@SourceDebugExtension(value={"SMAP\nPPRestoringBerryItem.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PPRestoringBerryItem.kt\ncom/cobblemon/mod/common/item/berry/PPRestoringBerryItem\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,71:1\n1747#2,3:72\n1#3:75\n*S KotlinDebug\n*F\n+ 1 PPRestoringBerryItem.kt\ncom/cobblemon/mod/common/item/berry/PPRestoringBerryItem\n*L\n48#1:72,3\n*E\n"})
public final class PPRestoringBerryItem
extends BerryItem
implements PokemonAndMoveSelectingItem {
    @NotNull
    private final Function0<Expression> amount;
    @NotNull
    private final BagItem bagItem;

    public PPRestoringBerryItem(@NotNull BerryBlock block, @NotNull Function0<? extends Expression> amount) {
        Intrinsics.checkNotNullParameter((Object)block, (String)"block");
        Intrinsics.checkNotNullParameter(amount, (String)"amount");
        super(block);
        this.amount = amount;
        this.bagItem = new BagItem(this){
            final /* synthetic */ PPRestoringBerryItem this$0;
            {
                this.this$0 = $receiver;
            }

            @NotNull
            public String getItemName() {
                Berry berry = this.this$0.berry();
                Intrinsics.checkNotNull((Object)berry);
                return "item.cobblemon." + berry.getIdentifier().m_135815_();
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
                return "ether " + data + " " + MoLangExtensionsKt.resolveInt(MoLangExtensionsKt.getGenericRuntime(), (Expression)this.this$0.getAmount().invoke(), battlePokemon);
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
            moveToRecover.setCurrentPp(Math.min(moveToRecover.getMaxPp(), moveToRecover.getCurrentPp() + MoLangExtensionsKt.resolveInt(MoLangExtensionsKt.getGenericRuntime(), (Expression)this.amount.invoke(), pokemon)));
            player.m_6330_(CobblemonSounds.BERRY_EAT, SoundSource.PLAYERS, 1.0f, 1.0f);
            if (!player.m_7500_()) {
                stack.m_41774_(1);
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
            InteractionResultHolder interactionResultHolder = this.use(serverPlayer, itemStack);
            if (interactionResultHolder == null) {
                InteractionResultHolder interactionResultHolder2 = InteractionResultHolder.m_19098_((Object)user.m_21120_(hand));
                interactionResultHolder = interactionResultHolder2;
                Intrinsics.checkNotNullExpressionValue((Object)interactionResultHolder2, (String)"pass(user.getStackInHand(hand))");
            }
            return interactionResultHolder;
        }
        InteractionResultHolder interactionResultHolder = super.m_7203_(world, user, hand);
        Intrinsics.checkNotNullExpressionValue((Object)interactionResultHolder, (String)"super<BerryItem>.use(world, user, hand)");
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

