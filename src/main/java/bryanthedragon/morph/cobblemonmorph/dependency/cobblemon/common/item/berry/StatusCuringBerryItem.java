/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.ArraysKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
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

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item.PokemonSelectingItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Status;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.BerryBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.BerryItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle.BagItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.berry.StatusCuringBerryItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatusContainer;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u00012\u00020\u0002B#\u0012\u0006\u0010(\u001a\u00020'\u0012\u0012\u0010#\u001a\n\u0012\u0006\b\u0001\u0012\u00020\"0!\"\u00020\"\u00a2\u0006\u0004\b)\u0010*J'\u0010\n\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ/\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u000e2\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0017\u0010\u0012\u001a\u00020\u00112\u0006\u0010\r\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b\u0012\u0010\u0013J-\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00050\u000e2\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u0018H\u0016\u00a2\u0006\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001d\u001a\u00020\u001c8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 R\u001f\u0010#\u001a\n\u0012\u0006\b\u0001\u0012\u00020\"0!8\u0006\u00a2\u0006\f\n\u0004\b#\u0010$\u001a\u0004\b%\u0010&\u00a8\u0006+"}, d2={"Lcom/cobblemon/mod/common/item/berry/StatusCuringBerryItem;", "Lcom/cobblemon/mod/common/item/BerryItem;", "Lcom/cobblemon/mod/common/api/item/PokemonSelectingItem;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "Lnet/minecraft/world/item/ItemStack;", "stack", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "battlePokemon", "", "applyToBattlePokemon", "(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/item/ItemStack;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;)V", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lnet/minecraft/world/InteractionResultHolder;", "applyToPokemon", "(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/item/ItemStack;Lcom/cobblemon/mod/common/pokemon/Pokemon;)Lnet/minecraft/world/InteractionResultHolder;", "", "canUseOnPokemon", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "Lnet/minecraft/world/level/Level;", "world", "Lnet/minecraft/world/entity/player/Player;", "user", "Lnet/minecraft/world/InteractionHand;", "hand", "use", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResultHolder;", "Lcom/cobblemon/mod/common/item/battle/BagItem;", "bagItem", "Lcom/cobblemon/mod/common/item/battle/BagItem;", "getBagItem", "()Lcom/cobblemon/mod/common/item/battle/BagItem;", "", "Lcom/cobblemon/mod/common/api/pokemon/status/Status;", "status", "[Lcom/cobblemon/mod/common/api/pokemon/status/Status;", "getStatus", "()[Lcom/cobblemon/mod/common/api/pokemon/status/Status;", "Lcom/cobblemon/mod/common/block/BerryBlock;", "block", "<init>", "(Lcom/cobblemon/mod/common/block/BerryBlock;[Lcom/cobblemon/mod/common/api/pokemon/status/Status;)V", "common"})
@SourceDebugExtension(value={"SMAP\nStatusCuringBerryItem.kt\nKotlin\n*S Kotlin\n*F\n+ 1 StatusCuringBerryItem.kt\ncom/cobblemon/mod/common/item/berry/StatusCuringBerryItem\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,72:1\n1#2:73\n*E\n"})
public final class StatusCuringBerryItem
extends BerryItem
implements PokemonSelectingItem {
    @NotNull
    private final Status[] status;
    @NotNull
    private final BagItem bagItem;

    public StatusCuringBerryItem(@NotNull BerryBlock block, Status ... status) {
        Intrinsics.checkNotNullParameter((Object)block, (String)"block");
        Intrinsics.checkNotNullParameter((Object)status, (String)"status");
        super(block);
        this.status = status;
        this.bagItem = new BagItem(this){
            final /* synthetic */ StatusCuringBerryItem this$0;
            {
                this.this$0 = $receiver;
            }

            @NotNull
            public String getItemName() {
                Berry berry = this.this$0.berry();
                Intrinsics.checkNotNull((Object)berry);
                return "item.cobblemon." + berry.getIdentifier().m_135815_();
            }

            public boolean canUse(@NotNull PokemonBattle battle2, @NotNull BattlePokemon target) {
                Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
                Intrinsics.checkNotNullParameter((Object)target, (String)"target");
                return this.this$0.canUseOnPokemon(target.getEffectedPokemon());
            }

            @NotNull
            public String getShowdownInput(@NotNull BattleActor actor, @NotNull BattlePokemon battlePokemon, @Nullable String data) {
                Object object;
                block4: {
                    block3: {
                        Object[] objectArray;
                        Intrinsics.checkNotNullParameter((Object)actor, (String)"actor");
                        Intrinsics.checkNotNullParameter((Object)battlePokemon, (String)"battlePokemon");
                        Object[] it = objectArray = this.this$0.getStatus();
                        boolean bl = false;
                        object = !(it.length == 0) ? objectArray : null;
                        if (object == null) break block3;
                        Object[] it2 = object;
                        boolean bl2 = false;
                        String string = " " + ArraysKt.joinToString$default((Object[])it2, (CharSequence)" ", null, null, (int)0, null, (Function1)bagItem.getShowdownInput.2.1.INSTANCE, (int)30, null);
                        object = string;
                        if (string != null) break block4;
                    }
                    object = "";
                }
                return "cure_status" + (String)object;
            }

            public boolean canStillUse(@NotNull ServerPlayer player, @NotNull PokemonBattle battle2, @NotNull BattleActor actor, @NotNull BattlePokemon target, @NotNull ItemStack stack) {
                return BagItem.DefaultImpls.canStillUse(this, player, battle2, actor, target, stack);
            }
        };
    }

    @NotNull
    public final Status[] getStatus() {
        return this.status;
    }

    @Override
    @NotNull
    public BagItem getBagItem() {
        return this.bagItem;
    }

    @Override
    public boolean canUseOnPokemon(@NotNull Pokemon pokemon) {
        boolean bl;
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        PersistentStatusContainer persistentStatusContainer = pokemon.getStatus();
        if (persistentStatusContainer != null) {
            PersistentStatusContainer it = persistentStatusContainer;
            boolean bl2 = false;
            bl = ArraysKt.contains((Object[])this.status, (Object)it.getStatus()) || this.status.length == 0;
        } else {
            bl = false;
        }
        return bl && pokemon.getCurrentHealth() > 0;
    }

    @Override
    @Nullable
    public InteractionResultHolder<ItemStack> applyToPokemon(@NotNull ServerPlayer player, @NotNull ItemStack stack, @NotNull Pokemon pokemon) {
        InteractionResultHolder interactionResultHolder;
        PersistentStatus currentStatus;
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        PersistentStatusContainer persistentStatusContainer = pokemon.getStatus();
        PersistentStatus persistentStatus = currentStatus = persistentStatusContainer != null ? persistentStatusContainer.getStatus() : null;
        if (currentStatus != null && (this.status.length == 0 || ArraysKt.contains((Object[])this.status, (Object)currentStatus))) {
            pokemon.setStatus(null);
            player.m_6330_(CobblemonSounds.BERRY_EAT, SoundSource.PLAYERS, 1.0f, 1.0f);
            if (!player.m_7500_()) {
                stack.m_41774_(1);
            }
            interactionResultHolder = InteractionResultHolder.m_19090_((Object)stack);
        } else {
            interactionResultHolder = InteractionResultHolder.m_19100_((Object)stack);
        }
        return interactionResultHolder;
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
        if (user instanceof ServerPlayer) {
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

