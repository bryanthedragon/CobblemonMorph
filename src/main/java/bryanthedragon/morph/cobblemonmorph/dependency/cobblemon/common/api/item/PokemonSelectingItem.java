/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.CobblemonCriteria;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.PokemonInteractContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.PokemonInteractCriterion;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.SimpleCriterionTrigger;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.PartySelectCallbacks;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BagItemActionResponse;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle.BagItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ItemStackExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J'\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ/\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\r2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u000bH&\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0017\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0017\u0010\u0013\u001a\u00020\u00102\u0006\u0010\f\u001a\u00020\u000bH&\u00a2\u0006\u0004\b\u0013\u0010\u0014J%\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00040\r2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0015\u0010\u0016J-\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00040\r2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0017H\u0016\u00a2\u0006\u0004\b\u0019\u0010\u001aJ-\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00040\r2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\u001b\u0010\u001cJ%\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00040\r2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u001d\u0010\u0016R\u0016\u0010!\u001a\u0004\u0018\u00010\u001e8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001f\u0010 \u00a8\u0006\""}, d2={"Lcom/cobblemon/mod/common/api/item/PokemonSelectingItem;", "", "Lnet/minecraft/server/level/ServerPlayer;", "player", "Lnet/minecraft/world/item/ItemStack;", "stack", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "battlePokemon", "", "applyToBattlePokemon", "(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/item/ItemStack;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;)V", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lnet/minecraft/world/InteractionResultHolder;", "applyToPokemon", "(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/item/ItemStack;Lcom/cobblemon/mod/common/pokemon/Pokemon;)Lnet/minecraft/world/InteractionResultHolder;", "", "canUseOnBattlePokemon", "(Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;)Z", "canUseOnPokemon", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "interactGeneral", "(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/InteractionResultHolder;", "Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "actor", "interactGeneralBattle", "(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/item/ItemStack;Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;)Lnet/minecraft/world/InteractionResultHolder;", "interactWithSpecificBattle", "(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/item/ItemStack;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;)Lnet/minecraft/world/InteractionResultHolder;", "use", "Lcom/cobblemon/mod/common/item/battle/BagItem;", "getBagItem", "()Lcom/cobblemon/mod/common/item/battle/BagItem;", "bagItem", "common"})
public interface PokemonSelectingItem {
    @NotNull
    public InteractionResultHolder<ItemStack> use(@NotNull ServerPlayer var1, @NotNull ItemStack var2);

    @Nullable
    public BagItem getBagItem();

    @Nullable
    public InteractionResultHolder<ItemStack> applyToPokemon(@NotNull ServerPlayer var1, @NotNull ItemStack var2, @NotNull Pokemon var3);

    public void applyToBattlePokemon(@NotNull ServerPlayer var1, @NotNull ItemStack var2, @NotNull BattlePokemon var3);

    public boolean canUseOnPokemon(@NotNull Pokemon var1);

    public boolean canUseOnBattlePokemon(@NotNull BattlePokemon var1);

    @NotNull
    public InteractionResultHolder<ItemStack> interactWithSpecificBattle(@NotNull ServerPlayer var1, @NotNull ItemStack var2, @NotNull BattlePokemon var3);

    @NotNull
    public InteractionResultHolder<ItemStack> interactGeneral(@NotNull ServerPlayer var1, @NotNull ItemStack var2);

    @NotNull
    public InteractionResultHolder<ItemStack> interactGeneralBattle(@NotNull ServerPlayer var1, @NotNull ItemStack var2, @NotNull BattleActor var3);

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    @SourceDebugExtension(value={"SMAP\nPokemonSelectingItem.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonSelectingItem.kt\ncom/cobblemon/mod/common/api/item/PokemonSelectingItem$DefaultImpls\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,153:1\n766#2:154\n857#2,2:155\n2333#2,14:157\n1#3:171\n*S KotlinDebug\n*F\n+ 1 PokemonSelectingItem.kt\ncom/cobblemon/mod/common/api/item/PokemonSelectingItem$DefaultImpls\n*L\n47#1:154\n47#1:155,2\n48#1:157,14\n*E\n"})
    public static final class DefaultImpls {
        /*
         * WARNING - void declaration
         */
        @NotNull
        public static InteractionResultHolder<ItemStack> use(@NotNull PokemonSelectingItem $this, @NotNull ServerPlayer player, @NotNull ItemStack stack) {
            Unit unit;
            Object v2;
            void $this$minByOrNull$iv;
            void $this$filterTo$iv$iv;
            Iterable $this$filter$iv;
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
            List list = player.m_9236_().m_45933_((Entity)player, AABB.m_165882_((Vec3)player.m_20182_(), (double)16.0, (double)16.0, (double)16.0));
            Intrinsics.checkNotNullExpressionValue((Object)list, (String)"player.world\n           \u2026r.pos, 16.0, 16.0, 16.0))");
            Iterable iterable = list;
            boolean $i$f$filter = false;
            void var7_6 = $this$filter$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv : $this$filterTo$iv$iv) {
                Entity it = (Entity)element$iv$iv;
                boolean bl = false;
                Entity entity2 = (Entity)player;
                Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
                if (!PlayerExtensionsKt.isLookingAt$default(entity2, it, 0.0f, 0.1f, 2, null)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            $this$filter$iv = (List)destination$iv$iv;
            boolean $i$f$minByOrNull = false;
            Iterator iterator$iv = $this$minByOrNull$iv.iterator();
            if (!iterator$iv.hasNext()) {
                v2 = null;
            } else {
                Object minElem$iv = iterator$iv.next();
                if (!iterator$iv.hasNext()) {
                    v2 = minElem$iv;
                } else {
                    Entity it = (Entity)minElem$iv;
                    boolean bl = false;
                    float minValue$iv = it.m_20270_((Entity)player);
                    do {
                        Object e$iv = iterator$iv.next();
                        Entity it2 = (Entity)e$iv;
                        $i$a$-minByOrNull-PokemonSelectingItem$use$entity$2 = false;
                        float v$iv = it2.m_20270_((Entity)player);
                        if (Float.compare(minValue$iv, v$iv) <= 0) continue;
                        minElem$iv = e$iv;
                        minValue$iv = v$iv;
                    } while (iterator$iv.hasNext());
                    v2 = minElem$iv;
                }
            }
            Object var4_27 = v2;
            PokemonEntity entity3 = var4_27 instanceof PokemonEntity ? (PokemonEntity)var4_27 : null;
            Pair<PokemonBattle, BattleActor> pair = PlayerExtensionsKt.getBattleState(player);
            if (pair != null) {
                Object v6;
                BattleActor actor;
                block21: {
                    iterator$iv = pair;
                    boolean bl = false;
                    actor = (BattleActor)iterator$iv.component2();
                    if ($this.getBagItem() == null) {
                        InteractionResultHolder interactionResultHolder = InteractionResultHolder.m_19100_((Object)stack);
                        Intrinsics.checkNotNullExpressionValue((Object)interactionResultHolder, (String)"fail(stack)");
                        return interactionResultHolder;
                    }
                    Iterable iterable2 = actor.getPokemonList();
                    Iterator iterator = iterable2.iterator();
                    while (iterator.hasNext()) {
                        Object t = iterator.next();
                        BattlePokemon it = (BattlePokemon)t;
                        boolean bl2 = false;
                        PokemonEntity pokemonEntity = entity3;
                        if (!Intrinsics.areEqual((Object)it.getEffectedPokemon(), (Object)(pokemonEntity != null ? pokemonEntity.getPokemon() : null))) continue;
                        v6 = t;
                        break block21;
                    }
                    v6 = null;
                }
                BattlePokemon battlePokemon = v6;
                if (!actor.canFitForcedAction()) {
                    MutableComponent mutableComponent = LocalizationUtilsKt.battleLang("bagitem.cannot", new Object[0]);
                    Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"battleLang(\"bagitem.cannot\")");
                    player.m_213846_((Component)TextKt.red(mutableComponent));
                    InteractionResultHolder interactionResultHolder = InteractionResultHolder.m_19100_((Object)stack);
                    Intrinsics.checkNotNullExpressionValue((Object)interactionResultHolder, (String)"fail(stack)");
                    return interactionResultHolder;
                }
                if (entity3 == null) {
                    return $this.interactGeneralBattle(player, stack, actor);
                }
                if (battlePokemon != null) {
                    return $this.interactWithSpecificBattle(player, stack, battlePokemon);
                }
                unit = Unit.INSTANCE;
            } else {
                unit = null;
            }
            if (unit == null) {
                PokemonSelectingItem $this$use_u24lambda_u244 = $this;
                boolean bl = false;
                if (!player.m_6144_()) {
                    InteractionResultHolder<ItemStack> interactionResultHolder;
                    if (entity3 != null) {
                        Pokemon pokemon = entity3.getPokemon();
                        if (Intrinsics.areEqual((Object)entity3.m_21805_(), (Object)player.m_20148_())) {
                            InteractionResultHolder<ItemStack> typedActionResult = $this$use_u24lambda_u244.applyToPokemon(player, stack, pokemon);
                            if (typedActionResult != null) {
                                SimpleCriterionTrigger<PokemonInteractContext, PokemonInteractCriterion> simpleCriterionTrigger = CobblemonCriteria.INSTANCE.getPOKEMON_INTERACT();
                                ResourceLocation resourceLocation = pokemon.getSpecies().getResourceIdentifier();
                                ResourceLocation resourceLocation2 = BuiltInRegistries.f_257033_.m_7981_((Object)stack.m_41720_());
                                Intrinsics.checkNotNullExpressionValue((Object)resourceLocation2, (String)"ITEM.getId(stack.item)");
                                simpleCriterionTrigger.trigger(player, new PokemonInteractContext(resourceLocation, resourceLocation2));
                                interactionResultHolder = typedActionResult;
                            } else {
                                InteractionResultHolder interactionResultHolder2 = InteractionResultHolder.m_19098_((Object)stack);
                                interactionResultHolder = interactionResultHolder2;
                                Intrinsics.checkNotNullExpressionValue((Object)interactionResultHolder2, (String)"{\n                      \u2026                        }");
                            }
                        } else {
                            InteractionResultHolder<ItemStack> interactionResultHolder3 = InteractionResultHolder.m_19100_((Object)stack);
                            interactionResultHolder = interactionResultHolder3;
                            Intrinsics.checkNotNullExpressionValue((Object)interactionResultHolder3, (String)"{\n                      \u2026ck)\n                    }");
                        }
                    } else {
                        interactionResultHolder = $this$use_u24lambda_u244.interactGeneral(player, stack);
                    }
                    return interactionResultHolder;
                }
            }
            InteractionResultHolder interactionResultHolder = InteractionResultHolder.m_19098_((Object)stack);
            Intrinsics.checkNotNullExpressionValue((Object)interactionResultHolder, (String)"pass(stack)");
            return interactionResultHolder;
        }

        public static void applyToBattlePokemon(@NotNull PokemonSelectingItem $this, @NotNull ServerPlayer player, @NotNull ItemStack stack, @NotNull BattlePokemon battlePokemon) {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
            Intrinsics.checkNotNullParameter((Object)battlePokemon, (String)"battlePokemon");
            PokemonBattle battle2 = battlePokemon.getActor().getBattle();
            BagItem bagItem2 = $this.getBagItem();
            if (!battlePokemon.getActor().canFitForcedAction()) {
                MutableComponent mutableComponent = LocalizationUtilsKt.battleLang("bagitem.cannot", new Object[0]);
                Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"battleLang(\"bagitem.cannot\")");
                player.m_213846_((Component)TextKt.red(mutableComponent));
            } else {
                BagItem bagItem3 = bagItem2;
                Intrinsics.checkNotNull((Object)bagItem3);
                if (!bagItem3.canUse(battle2, battlePokemon)) {
                    MutableComponent mutableComponent = LocalizationUtilsKt.battleLang("bagitem.invalid", new Object[0]);
                    Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"battleLang(\"bagitem.invalid\")");
                    player.m_213846_((Component)TextKt.red(mutableComponent));
                } else {
                    battlePokemon.getActor().forceChoose(new BagItemActionResponse(bagItem2, battlePokemon, null, 4, null));
                    if (!player.m_7500_()) {
                        stack.m_41774_(1);
                    }
                    SimpleCriterionTrigger<PokemonInteractContext, PokemonInteractCriterion> simpleCriterionTrigger = CobblemonCriteria.INSTANCE.getPOKEMON_INTERACT();
                    PokemonEntity pokemonEntity = battlePokemon.getEntity();
                    Intrinsics.checkNotNull((Object)pokemonEntity);
                    ResourceLocation resourceLocation = pokemonEntity.getPokemon().getSpecies().getResourceIdentifier();
                    ResourceLocation resourceLocation2 = BuiltInRegistries.f_257033_.m_7981_((Object)stack.m_41720_());
                    Intrinsics.checkNotNullExpressionValue((Object)resourceLocation2, (String)"ITEM.getId(stack.item)");
                    simpleCriterionTrigger.trigger(player, new PokemonInteractContext(resourceLocation, resourceLocation2));
                }
            }
        }

        public static boolean canUseOnBattlePokemon(@NotNull PokemonSelectingItem $this, @NotNull BattlePokemon battlePokemon) {
            Intrinsics.checkNotNullParameter((Object)battlePokemon, (String)"battlePokemon");
            BagItem bagItem2 = $this.getBagItem();
            Intrinsics.checkNotNull((Object)bagItem2);
            return bagItem2.canUse(battlePokemon.getActor().getBattle(), battlePokemon);
        }

        @NotNull
        public static InteractionResultHolder<ItemStack> interactWithSpecificBattle(@NotNull PokemonSelectingItem $this, @NotNull ServerPlayer player, @NotNull ItemStack stack, @NotNull BattlePokemon battlePokemon) {
            InteractionResultHolder interactionResultHolder;
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
            Intrinsics.checkNotNullParameter((Object)battlePokemon, (String)"battlePokemon");
            if ($this.canUseOnBattlePokemon(battlePokemon)) {
                $this.applyToBattlePokemon(player, stack, battlePokemon);
                InteractionResultHolder interactionResultHolder2 = InteractionResultHolder.m_19090_((Object)stack);
                interactionResultHolder = interactionResultHolder2;
                Intrinsics.checkNotNullExpressionValue((Object)interactionResultHolder2, (String)"{\n            applyToBat\u2026.success(stack)\n        }");
            } else {
                MutableComponent mutableComponent = LocalizationUtilsKt.battleLang("bagitem.invalid", new Object[0]);
                Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"battleLang(\"bagitem.invalid\")");
                player.m_213846_((Component)TextKt.red(mutableComponent));
                InteractionResultHolder interactionResultHolder3 = InteractionResultHolder.m_19100_((Object)stack);
                Intrinsics.checkNotNullExpressionValue((Object)interactionResultHolder3, (String)"{\n            player.sen\u2026ult.fail(stack)\n        }");
                interactionResultHolder = interactionResultHolder3;
            }
            return interactionResultHolder;
        }

        @NotNull
        public static InteractionResultHolder<ItemStack> interactGeneral(@NotNull PokemonSelectingItem $this, @NotNull ServerPlayer player, @NotNull ItemStack stack) {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
            List party = CollectionsKt.toList((Iterable)PlayerExtensionsKt.party(player));
            if (party.isEmpty()) {
                InteractionResultHolder interactionResultHolder = InteractionResultHolder.m_19100_((Object)stack);
                Intrinsics.checkNotNullExpressionValue((Object)interactionResultHolder, (String)"fail(stack)");
                return interactionResultHolder;
            }
            PartySelectCallbacks.createFromPokemon$default(PartySelectCallbacks.INSTANCE, player, null, party, (Function1)new Function1<Pokemon, Boolean>((Object)$this){

                @NotNull
                public final Boolean invoke(@NotNull Pokemon p0) {
                    Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                    return ((PokemonSelectingItem)this.receiver).canUseOnPokemon(p0);
                }
            }, null, (Function1)new Function1<Pokemon, Unit>(stack, player, $this){
                final /* synthetic */ ItemStack $stack;
                final /* synthetic */ ServerPlayer $player;
                final /* synthetic */ PokemonSelectingItem this$0;
                {
                    this.$stack = $stack;
                    this.$player = $player;
                    this.this$0 = $receiver;
                    super(1);
                }

                public final void invoke(@NotNull Pokemon pk) {
                    Intrinsics.checkNotNullParameter((Object)pk, (String)"pk");
                    if (ItemStackExtensionsKt.isHeld(this.$stack, this.$player)) {
                        this.this$0.applyToPokemon(this.$player, this.$stack, pk);
                        SimpleCriterionTrigger<PokemonInteractContext, PokemonInteractCriterion> simpleCriterionTrigger = CobblemonCriteria.INSTANCE.getPOKEMON_INTERACT();
                        ResourceLocation resourceLocation = pk.getSpecies().getResourceIdentifier();
                        ResourceLocation resourceLocation2 = BuiltInRegistries.f_257033_.m_7981_((Object)this.$stack.m_41720_());
                        Intrinsics.checkNotNullExpressionValue((Object)resourceLocation2, (String)"ITEM.getId(stack.item)");
                        simpleCriterionTrigger.trigger(this.$player, new PokemonInteractContext(resourceLocation, resourceLocation2));
                    }
                }
            }, 18, null);
            InteractionResultHolder interactionResultHolder = InteractionResultHolder.m_19090_((Object)stack);
            Intrinsics.checkNotNullExpressionValue((Object)interactionResultHolder, (String)"success(stack)");
            return interactionResultHolder;
        }

        @NotNull
        public static InteractionResultHolder<ItemStack> interactGeneralBattle(@NotNull PokemonSelectingItem $this, @NotNull ServerPlayer player, @NotNull ItemStack stack, @NotNull BattleActor actor) {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
            Intrinsics.checkNotNullParameter((Object)actor, (String)"actor");
            PartySelectCallbacks.createBattleSelect$default(PartySelectCallbacks.INSTANCE, player, null, actor.getPokemonList(), (Function1)new Function1<BattlePokemon, Boolean>($this, actor){
                final /* synthetic */ PokemonSelectingItem this$0;
                final /* synthetic */ BattleActor $actor;
                {
                    this.this$0 = $receiver;
                    this.$actor = $actor;
                    super(1);
                }

                @NotNull
                public final Boolean invoke(@NotNull BattlePokemon pk) {
                    Object v0;
                    PokemonSelectingItem pokemonSelectingItem;
                    block1: {
                        Intrinsics.checkNotNullParameter((Object)pk, (String)"pk");
                        Iterable iterable = this.$actor.getPokemonList();
                        pokemonSelectingItem = this.this$0;
                        Iterable iterable2 = iterable;
                        for (T t : iterable2) {
                            BattlePokemon it = (BattlePokemon)t;
                            boolean bl = false;
                            if (!Intrinsics.areEqual((Object)it.getEffectedPokemon(), (Object)pk.getEffectedPokemon())) continue;
                            v0 = t;
                            break block1;
                        }
                        v0 = null;
                    }
                    Intrinsics.checkNotNull(v0);
                    return pokemonSelectingItem.canUseOnBattlePokemon(v0);
                }
            }, null, (Function1)new Function1<BattlePokemon, Unit>($this, player, stack, actor){
                final /* synthetic */ PokemonSelectingItem this$0;
                final /* synthetic */ ServerPlayer $player;
                final /* synthetic */ ItemStack $stack;
                final /* synthetic */ BattleActor $actor;
                {
                    this.this$0 = $receiver;
                    this.$player = $player;
                    this.$stack = $stack;
                    this.$actor = $actor;
                    super(1);
                }

                public final void invoke(@NotNull BattlePokemon pk) {
                    Object var11_11;
                    Object v0;
                    PokemonSelectingItem pokemonSelectingItem;
                    ServerPlayer serverPlayer;
                    ItemStack itemStack;
                    block1: {
                        Intrinsics.checkNotNullParameter((Object)pk, (String)"pk");
                        Iterable iterable = this.$actor.getPokemonList();
                        itemStack = this.$stack;
                        serverPlayer = this.$player;
                        pokemonSelectingItem = this.this$0;
                        Iterable iterable2 = iterable;
                        for (T t : iterable2) {
                            BattlePokemon it = (BattlePokemon)t;
                            boolean bl = false;
                            if (!Intrinsics.areEqual((Object)it.getEffectedPokemon(), (Object)pk.getEffectedPokemon())) continue;
                            v0 = t;
                            break block1;
                        }
                        v0 = null;
                    }
                    Object v1 = var11_11 = v0;
                    Intrinsics.checkNotNull(v1);
                    pokemonSelectingItem.applyToBattlePokemon(serverPlayer, itemStack, v1);
                }
            }, 18, null);
            InteractionResultHolder interactionResultHolder = InteractionResultHolder.m_19090_((Object)stack);
            Intrinsics.checkNotNullExpressionValue((Object)interactionResultHolder, (String)"success(stack)");
            return interactionResultHolder;
        }
    }
}

