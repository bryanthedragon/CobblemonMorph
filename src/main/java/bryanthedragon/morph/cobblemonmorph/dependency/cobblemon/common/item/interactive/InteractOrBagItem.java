/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function3
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.item.ItemStack
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.MoveSelectCallbacks;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.MoveSelectDTO;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Move;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BagItemActionResponse;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle.BagItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\t\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0007H&\u00a2\u0006\u0004\b\t\u0010\nJ?\u0010\u0015\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u0013H\u0016\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0019\u0010\u0018\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0012\u001a\u00020\u0011H&\u00a2\u0006\u0004\b\u0018\u0010\u0019J'\u0010\u001a\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u0011H\u0016\u00a2\u0006\u0004\b\u001a\u0010\u001bJ-\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00110\u001f2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u0013H\u0016\u00a2\u0006\u0004\b \u0010!\u00a8\u0006\""}, d2={"Lcom/cobblemon/mod/common/item/interactive/InteractOrBagItem;", "", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "battlePokemon", "", "canUseBattle", "(Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;)Z", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "canUseOverworld", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "Lnet/minecraft/server/level/ServerPlayer;", "player", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "actor", "Lnet/minecraft/world/item/ItemStack;", "stack", "Lnet/minecraft/world/InteractionHand;", "hand", "checkBattleItem", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/InteractionHand;)Z", "Lcom/cobblemon/mod/common/item/battle/BagItem;", "getBagItem", "(Lnet/minecraft/world/item/ItemStack;)Lcom/cobblemon/mod/common/item/battle/BagItem;", "onBattleUse", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;Lnet/minecraft/world/item/ItemStack;)Z", "Lnet/minecraft/server/level/ServerLevel;", "world", "user", "Lnet/minecraft/world/InteractionResultHolder;", "onRegularUse", "(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResultHolder;", "common"})
public interface InteractOrBagItem {
    public boolean canUseOverworld(@NotNull Pokemon var1);

    public boolean canUseBattle(@NotNull BattlePokemon var1);

    @Nullable
    public BagItem getBagItem(@NotNull ItemStack var1);

    @NotNull
    public InteractionResultHolder<ItemStack> onRegularUse(@NotNull ServerLevel var1, @NotNull ServerPlayer var2, @NotNull InteractionHand var3);

    public boolean onBattleUse(@NotNull ServerPlayer var1, @NotNull BattlePokemon var2, @NotNull ItemStack var3);

    public boolean checkBattleItem(@NotNull ServerPlayer var1, @NotNull PokemonBattle var2, @NotNull BattleActor var3, @NotNull BattlePokemon var4, @NotNull ItemStack var5, @NotNull InteractionHand var6);

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    @SourceDebugExtension(value={"SMAP\nInteractOrBagItem.kt\nKotlin\n*S Kotlin\n*F\n+ 1 InteractOrBagItem.kt\ncom/cobblemon/mod/common/item/interactive/InteractOrBagItem$DefaultImpls\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,90:1\n1549#2:91\n1620#2,2:92\n1622#2:95\n1#3:94\n*S KotlinDebug\n*F\n+ 1 InteractOrBagItem.kt\ncom/cobblemon/mod/common/item/interactive/InteractOrBagItem$DefaultImpls\n*L\n58#1:91\n58#1:92,2\n58#1:95\n*E\n"})
    public static final class DefaultImpls {
        @NotNull
        public static InteractionResultHolder<ItemStack> onRegularUse(@NotNull InteractOrBagItem $this, @NotNull ServerLevel world, @NotNull ServerPlayer user, @NotNull InteractionHand hand) {
            Intrinsics.checkNotNullParameter((Object)world, (String)"world");
            Intrinsics.checkNotNullParameter((Object)user, (String)"user");
            Intrinsics.checkNotNullParameter((Object)hand, (String)"hand");
            InteractionResultHolder interactionResultHolder = InteractionResultHolder.m_19090_((Object)user.m_21120_(hand));
            Intrinsics.checkNotNullExpressionValue((Object)interactionResultHolder, (String)"success(user.getStackInHand(hand))");
            return interactionResultHolder;
        }

        /*
         * WARNING - void declaration
         */
        public static boolean onBattleUse(@NotNull InteractOrBagItem $this, @NotNull ServerPlayer player, @NotNull BattlePokemon battlePokemon, @NotNull ItemStack stack) {
            Collection<MoveSelectDTO> collection;
            void $this$mapTo$iv$iv;
            void $this$map$iv;
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            Intrinsics.checkNotNullParameter((Object)battlePokemon, (String)"battlePokemon");
            Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
            PokemonBattle battle2 = battlePokemon.getActor().getBattle();
            BagItem bagItem2 = $this.getBagItem(stack);
            if (bagItem2 == null) {
                return false;
            }
            BagItem bagItem3 = bagItem2;
            if (!battlePokemon.getActor().canFitForcedAction()) {
                MutableComponent mutableComponent = LocalizationUtilsKt.battleLang("bagitem.cannot", new Object[0]);
                Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"battleLang(\"bagitem.cannot\")");
                player.m_213846_((Component)TextKt.red(mutableComponent));
                return false;
            }
            if (!bagItem3.canUse(battle2, battlePokemon)) {
                MutableComponent mutableComponent = LocalizationUtilsKt.battleLang("bagitem.invalid", new Object[0]);
                Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"battleLang(\"bagitem.invalid\")");
                player.m_213846_((Component)TextKt.red(mutableComponent));
                return false;
            }
            int turn = battle2.getTurn();
            Iterable iterable = battlePokemon.getMoveSet();
            Component component = null;
            ServerPlayer serverPlayer = player;
            MoveSelectCallbacks moveSelectCallbacks = MoveSelectCallbacks.INSTANCE;
            boolean $i$f$map = false;
            void var9_12 = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                MoveSelectDTO moveSelectDTO;
                void move;
                Move move2 = (Move)item$iv$iv;
                collection = destination$iv$iv;
                boolean bl = false;
                boolean enabled = move.getCurrentPp() < move.getMaxPp();
                MoveSelectDTO it = moveSelectDTO = new MoveSelectDTO((Move)move, false, 2, null);
                boolean bl2 = false;
                it.setEnabled(enabled);
                collection.add(moveSelectDTO);
            }
            collection = (List)destination$iv$iv;
            MoveSelectCallbacks.create$default(moveSelectCallbacks, serverPlayer, component, (List)collection, null, (Function3)new Function3<ServerPlayer, Integer, MoveSelectDTO, Unit>(player, stack, battlePokemon, battle2, turn, bagItem3){
                final /* synthetic */ ServerPlayer $player;
                final /* synthetic */ ItemStack $stack;
                final /* synthetic */ BattlePokemon $battlePokemon;
                final /* synthetic */ PokemonBattle $battle;
                final /* synthetic */ int $turn;
                final /* synthetic */ BagItem $bagItem;
                {
                    this.$player = $player;
                    this.$stack = $stack;
                    this.$battlePokemon = $battlePokemon;
                    this.$battle = $battle;
                    this.$turn = $turn;
                    this.$bagItem = $bagItem;
                    super(3);
                }

                public final void invoke(@NotNull ServerPlayer serverPlayer, int n, @NotNull MoveSelectDTO move) {
                    Intrinsics.checkNotNullParameter((Object)serverPlayer, (String)"<anonymous parameter 0>");
                    Intrinsics.checkNotNullParameter((Object)move, (String)"move");
                    Iterable iterable = this.$player.m_6167_();
                    Intrinsics.checkNotNullExpressionValue((Object)iterable, (String)"player.handItems");
                    if (CollectionsKt.contains((Iterable)iterable, (Object)this.$stack) && !this.$stack.m_41619_() && this.$battlePokemon.getActor().canFitForcedAction() && this.$battle.getTurn() == this.$turn) {
                        this.$battlePokemon.getActor().forceChoose(new BagItemActionResponse(this.$bagItem, this.$battlePokemon, move.getMoveTemplate().getName()));
                        if (!this.$player.m_7500_()) {
                            this.$stack.m_41774_(1);
                        }
                    }
                }
            }, 10, null);
            return true;
        }

        public static boolean checkBattleItem(@NotNull InteractOrBagItem $this, @NotNull ServerPlayer player, @NotNull PokemonBattle battle2, @NotNull BattleActor actor, @NotNull BattlePokemon battlePokemon, @NotNull ItemStack stack, @NotNull InteractionHand hand) {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
            Intrinsics.checkNotNullParameter((Object)actor, (String)"actor");
            Intrinsics.checkNotNullParameter((Object)battlePokemon, (String)"battlePokemon");
            Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
            Intrinsics.checkNotNullParameter((Object)hand, (String)"hand");
            BagItem bagItem2 = $this.getBagItem(stack);
            if (bagItem2 == null) {
                return false;
            }
            BagItem bagItem3 = bagItem2;
            if (!actor.canFitForcedAction()) {
                MutableComponent mutableComponent = LocalizationUtilsKt.battleLang("bagitem.cannot", new Object[0]);
                Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"battleLang(\"bagitem.cannot\")");
                player.m_213846_((Component)TextKt.red(mutableComponent));
                return false;
            }
            if (!bagItem3.canUse(battle2, battlePokemon)) {
                MutableComponent mutableComponent = LocalizationUtilsKt.battleLang("bagitem.invalid", new Object[0]);
                Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"battleLang(\"bagitem.invalid\")");
                player.m_213846_((Component)TextKt.red(mutableComponent));
                return false;
            }
            return player.m_21120_(hand) == stack;
        }
    }
}

