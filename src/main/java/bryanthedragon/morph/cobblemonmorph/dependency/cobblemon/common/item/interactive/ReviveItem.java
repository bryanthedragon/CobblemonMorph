/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.CobblemonCriteria;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.PokemonInteractContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.PokemonInteractCriterion;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.SimpleCriterionTrigger;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.PartySelectCallbacks;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BagItemActionResponse;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.CobblemonItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle.BagItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.ReviveItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ItemStackExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0012\u001a\u00020\u0011\u00a2\u0006\u0004\b\u0016\u0010\u0017J-\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\n\u0010\u000bR\u0017\u0010\r\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0012\u001a\u00020\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015\u00a8\u0006\u0018"}, d2={"Lcom/cobblemon/mod/common/item/interactive/ReviveItem;", "Lcom/cobblemon/mod/common/item/CobblemonItem;", "Lnet/minecraft/world/level/Level;", "world", "Lnet/minecraft/world/entity/player/Player;", "user", "Lnet/minecraft/world/InteractionHand;", "hand", "Lnet/minecraft/world/InteractionResultHolder;", "Lnet/minecraft/world/item/ItemStack;", "use", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResultHolder;", "Lcom/cobblemon/mod/common/item/battle/BagItem;", "bagItem", "Lcom/cobblemon/mod/common/item/battle/BagItem;", "getBagItem", "()Lcom/cobblemon/mod/common/item/battle/BagItem;", "", "max", "Z", "getMax", "()Z", "<init>", "(Z)V", "common"})
public final class ReviveItem
extends CobblemonItem {
    private final boolean max;
    @NotNull
    private final BagItem bagItem;

    public ReviveItem(boolean max2) {
        super(new Item.Properties());
        this.max = max2;
        this.bagItem = new BagItem(this){
            @NotNull
            private final String itemName;
            final /* synthetic */ ReviveItem this$0;
            {
                this.this$0 = $receiver;
                this.itemName = "item.cobblemon." + ($receiver.getMax() ? "max_revive" : "revive");
            }

            @NotNull
            public String getItemName() {
                return this.itemName;
            }

            public boolean canUse(@NotNull PokemonBattle battle2, @NotNull BattlePokemon target) {
                Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
                Intrinsics.checkNotNullParameter((Object)target, (String)"target");
                return target.getHealth() <= 0;
            }

            @NotNull
            public String getShowdownInput(@NotNull BattleActor actor, @NotNull BattlePokemon battlePokemon, @Nullable String data) {
                Intrinsics.checkNotNullParameter((Object)actor, (String)"actor");
                Intrinsics.checkNotNullParameter((Object)battlePokemon, (String)"battlePokemon");
                return "revive " + (this.this$0.getMax() ? "1" : "0.5");
            }

            public boolean canStillUse(@NotNull ServerPlayer player, @NotNull PokemonBattle battle2, @NotNull BattleActor actor, @NotNull BattlePokemon target, @NotNull ItemStack stack) {
                return BagItem.DefaultImpls.canStillUse(this, player, battle2, actor, target, stack);
            }
        };
    }

    public final boolean getMax() {
        return this.max;
    }

    @NotNull
    public final BagItem getBagItem() {
        return this.bagItem;
    }

    @NotNull
    public InteractionResultHolder<ItemStack> m_7203_(@NotNull Level world, @NotNull Player user, @NotNull InteractionHand hand) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)user, (String)"user");
        Intrinsics.checkNotNullParameter((Object)hand, (String)"hand");
        if (!(world instanceof ServerLevel)) {
            InteractionResultHolder interactionResultHolder = InteractionResultHolder.m_19090_((Object)user.m_21120_(hand));
            Intrinsics.checkNotNullExpressionValue((Object)interactionResultHolder, (String)"success(user.getStackInHand(hand))");
            return interactionResultHolder;
        }
        ServerPlayer player = (ServerPlayer)user;
        ItemStack stack = user.m_21120_(hand);
        PokemonBattle battle2 = BattleRegistry.INSTANCE.getBattleByParticipatingPlayer(player);
        if (battle2 != null) {
            BattleActor battleActor = battle2.getActor(player);
            Intrinsics.checkNotNull((Object)battleActor);
            BattleActor actor = battleActor;
            List<BattlePokemon> battlePokemon = actor.getPokemonList();
            if (!actor.canFitForcedAction()) {
                MutableComponent mutableComponent = LocalizationUtilsKt.battleLang("bagitem.cannot", new Object[0]);
                Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"battleLang(\"bagitem.cannot\")");
                player.m_5661_((Component)TextKt.red(mutableComponent), true);
                InteractionResultHolder interactionResultHolder = InteractionResultHolder.m_19096_((Object)stack);
                Intrinsics.checkNotNullExpressionValue((Object)interactionResultHolder, (String)"consume(stack)");
                return interactionResultHolder;
            }
            int turn = battle2.getTurn();
            PartySelectCallbacks.createBattleSelect$default(PartySelectCallbacks.INSTANCE, player, null, battlePokemon, (Function1)new Function1<BattlePokemon, Boolean>(this, battle2){
                final /* synthetic */ ReviveItem this$0;
                final /* synthetic */ PokemonBattle $battle;
                {
                    this.this$0 = $receiver;
                    this.$battle = $battle;
                    super(1);
                }

                @NotNull
                public final Boolean invoke(@NotNull BattlePokemon it) {
                    Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                    return this.this$0.getBagItem().canUse(this.$battle, it);
                }
            }, null, (Function1)new Function1<BattlePokemon, Unit>(actor, battle2, turn, stack, player, this){
                final /* synthetic */ BattleActor $actor;
                final /* synthetic */ PokemonBattle $battle;
                final /* synthetic */ int $turn;
                final /* synthetic */ ItemStack $stack;
                final /* synthetic */ ServerPlayer $player;
                final /* synthetic */ ReviveItem this$0;
                {
                    this.$actor = $actor;
                    this.$battle = $battle;
                    this.$turn = $turn;
                    this.$stack = $stack;
                    this.$player = $player;
                    this.this$0 = $receiver;
                    super(1);
                }

                public final void invoke(@NotNull BattlePokemon bp) {
                    Intrinsics.checkNotNullParameter((Object)bp, (String)"bp");
                    if (this.$actor.canFitForcedAction() && bp.getHealth() <= 0 && this.$battle.getTurn() == this.$turn) {
                        ItemStack itemStack = this.$stack;
                        Intrinsics.checkNotNullExpressionValue((Object)itemStack, (String)"stack");
                        if (ItemStackExtensionsKt.isHeld(itemStack, this.$player)) {
                            this.$player.m_6330_(CobblemonSounds.ITEM_USE, SoundSource.PLAYERS, 1.0f, 1.0f);
                            this.$actor.forceChoose(new BagItemActionResponse(this.this$0.getBagItem(), bp, bp.getUuid().toString()));
                            if (!this.$player.m_7500_()) {
                                this.$stack.m_41774_(1);
                            }
                            SimpleCriterionTrigger<PokemonInteractContext, PokemonInteractCriterion> simpleCriterionTrigger = CobblemonCriteria.INSTANCE.getPOKEMON_INTERACT();
                            Object object = bp.getEntity();
                            Species species = object != null && (object = ((PokemonEntity)object).getPokemon()) != null ? ((Pokemon)object).getSpecies() : null;
                            Intrinsics.checkNotNull((Object)species);
                            ResourceLocation resourceLocation = species.getResourceIdentifier();
                            ResourceLocation resourceLocation2 = BuiltInRegistries.f_257033_.m_7981_((Object)this.$stack.m_41720_());
                            Intrinsics.checkNotNullExpressionValue((Object)resourceLocation2, (String)"ITEM.getId(stack.item)");
                            simpleCriterionTrigger.trigger(this.$player, new PokemonInteractContext(resourceLocation, resourceLocation2));
                        }
                    }
                }
            }, 18, null);
        } else {
            List pokemon = CollectionsKt.toList((Iterable)PlayerExtensionsKt.party(player));
            PartySelectCallbacks.createFromPokemon$default(PartySelectCallbacks.INSTANCE, player, null, pokemon, use.3.INSTANCE, null, (Function1)new Function1<Pokemon, Unit>(player, stack, this){
                final /* synthetic */ ServerPlayer $player;
                final /* synthetic */ ItemStack $stack;
                final /* synthetic */ ReviveItem this$0;
                {
                    this.$player = $player;
                    this.$stack = $stack;
                    this.this$0 = $receiver;
                    super(1);
                }

                public final void invoke(@NotNull Pokemon pk) {
                    Intrinsics.checkNotNullParameter((Object)pk, (String)"pk");
                    if (pk.isFainted() && !PlayerExtensionsKt.isInBattle(this.$player)) {
                        ItemStack itemStack = this.$stack;
                        Intrinsics.checkNotNullExpressionValue((Object)itemStack, (String)"stack");
                        if (ItemStackExtensionsKt.isHeld(itemStack, this.$player)) {
                            pk.setCurrentHealth(this.this$0.getMax() ? pk.getHp() : (int)Math.ceil((float)pk.getHp() / 2.0f));
                            if (!this.$player.m_7500_()) {
                                this.$stack.m_41774_(1);
                            }
                            SimpleCriterionTrigger<PokemonInteractContext, PokemonInteractCriterion> simpleCriterionTrigger = CobblemonCriteria.INSTANCE.getPOKEMON_INTERACT();
                            ResourceLocation resourceLocation = pk.getSpecies().getResourceIdentifier();
                            ResourceLocation resourceLocation2 = BuiltInRegistries.f_257033_.m_7981_((Object)this.$stack.m_41720_());
                            Intrinsics.checkNotNullExpressionValue((Object)resourceLocation2, (String)"ITEM.getId(stack.item)");
                            simpleCriterionTrigger.trigger(this.$player, new PokemonInteractContext(resourceLocation, resourceLocation2));
                        }
                    }
                }
            }, 18, null);
        }
        InteractionResultHolder interactionResultHolder = InteractionResultHolder.m_19090_((Object)stack);
        Intrinsics.checkNotNullExpressionValue((Object)interactionResultHolder, (String)"success(stack)");
        return interactionResultHolder;
    }
}

