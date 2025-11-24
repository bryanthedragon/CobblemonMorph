/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.server.level.ServerPlayer
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

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.interaction.ExperienceCandyUseEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item.PokemonSelectingItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.CandyExperienceSource;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.CancelableObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.CobblemonItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.AddExperienceResult;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0001\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 #2\u00020\u00012\u00020\u0002:\u0002$#B\u000f\u0012\u0006\u0010\u001d\u001a\u00020\u001c\u00a2\u0006\u0004\b!\u0010\"J/\u0010\n\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\t2\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0017\u0010\r\u001a\u00020\f2\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\r\u0010\u000eJ-\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00050\t2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u0013H\u0016\u00a2\u0006\u0004\b\u0015\u0010\u0016R\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u00178\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001bR\u0017\u0010\u001d\u001a\u00020\u001c8\u0006\u00a2\u0006\f\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 \u00a8\u0006%"}, d2={"Lcom/cobblemon/mod/common/item/interactive/CandyItem;", "Lcom/cobblemon/mod/common/item/CobblemonItem;", "Lcom/cobblemon/mod/common/api/item/PokemonSelectingItem;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "Lnet/minecraft/world/item/ItemStack;", "stack", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lnet/minecraft/world/InteractionResultHolder;", "applyToPokemon", "(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/item/ItemStack;Lcom/cobblemon/mod/common/pokemon/Pokemon;)Lnet/minecraft/world/InteractionResultHolder;", "", "canUseOnPokemon", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "Lnet/minecraft/world/level/Level;", "world", "Lnet/minecraft/world/entity/player/Player;", "user", "Lnet/minecraft/world/InteractionHand;", "hand", "use", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResultHolder;", "", "bagItem", "Ljava/lang/Void;", "getBagItem", "()Ljava/lang/Void;", "Lcom/cobblemon/mod/common/item/interactive/CandyItem$Calculator;", "calculator", "Lcom/cobblemon/mod/common/item/interactive/CandyItem$Calculator;", "getCalculator", "()Lcom/cobblemon/mod/common/item/interactive/CandyItem$Calculator;", "<init>", "(Lcom/cobblemon/mod/common/item/interactive/CandyItem$Calculator;)V", "Companion", "Calculator", "common"})
@SourceDebugExtension(value={"SMAP\nCandyItem.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CandyItem.kt\ncom/cobblemon/mod/common/item/interactive/CandyItem\n+ 2 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/CancelableObservable\n+ 3 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 4 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 5 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/CancelableObservable$postThen$1\n+ 6 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable$post$1\n*L\n1#1,101:1\n39#2,2:102\n41#2,2:107\n44#2:110\n46#2:120\n47#2:123\n17#3,2:104\n14#3,5:111\n19#3:119\n19#3:122\n13579#4:106\n13579#4:116\n13580#4:118\n13580#4:121\n39#5:109\n14#6:117\n*S KotlinDebug\n*F\n+ 1 CandyItem.kt\ncom/cobblemon/mod/common/item/interactive/CandyItem\n*L\n46#1:102,2\n46#1:107,2\n46#1:110\n46#1:120\n46#1:123\n46#1:104,2\n60#1:111,5\n60#1:119\n46#1:122\n46#1:106\n60#1:116\n60#1:118\n46#1:121\n46#1:109\n60#1:117\n*E\n"})
public final class CandyItem
extends CobblemonItem
implements PokemonSelectingItem {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final Calculator calculator;
    @Nullable
    private final Void bagItem;
    public static final int DEFAULT_XS_CANDY_YIELD = 100;
    public static final int DEFAULT_S_CANDY_YIELD = 800;
    public static final int DEFAULT_M_CANDY_YIELD = 3000;
    public static final int DEFAULT_L_CANDY_YIELD = 10000;
    public static final int DEFAULT_XL_CANDY_YIELD = 30000;

    public CandyItem(@NotNull Calculator calculator) {
        Intrinsics.checkNotNullParameter((Object)calculator, (String)"calculator");
        super(new Item.Properties());
        this.calculator = calculator;
    }

    @NotNull
    public final Calculator getCalculator() {
        return this.calculator;
    }

    @Nullable
    public Void getBagItem() {
        return this.bagItem;
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

    /*
     * WARNING - void declaration
     */
    @Override
    @Nullable
    public InteractionResultHolder<ItemStack> applyToPokemon(@NotNull ServerPlayer player, @NotNull ItemStack stack, @NotNull Pokemon pokemon) {
        void this_$iv$iv;
        void event$iv;
        void $this$iv;
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        int experience = this.calculator.calculate(player, pokemon);
        CancelableObservable<ExperienceCandyUseEvent.Pre> cancelableObservable = CobblemonEvents.EXPERIENCE_CANDY_USE_PRE;
        Cancelable cancelable = new ExperienceCandyUseEvent.Pre(player, pokemon, this, experience, experience);
        boolean $i$f$postThen = false;
        EventObservable eventObservable = (EventObservable)$this$iv;
        Cancelable[] cancelableArray = new Cancelable[]{event$iv};
        Cancelable[] events$iv$iv = cancelableArray;
        boolean $i$f$post = false;
        this_$iv$iv.emit(Arrays.copyOf(events$iv$iv, events$iv$iv.length));
        Cancelable[] $this$forEach$iv$iv$iv = events$iv$iv;
        boolean $i$f$forEach = false;
        int n = $this$forEach$iv$iv$iv.length;
        for (int i = 0; i < n; ++i) {
            Cancelable element$iv$iv$iv;
            Cancelable it$iv = element$iv$iv$iv = $this$forEach$iv$iv$iv[i];
            boolean bl = false;
            if (!it$iv.isCanceled()) {
                void $this$iv2;
                ExperienceCandyUseEvent.Pre preEvent = (ExperienceCandyUseEvent.Pre)it$iv;
                boolean bl2 = false;
                int finalExperience = preEvent.getExperienceYield();
                CandyExperienceSource source = new CandyExperienceSource(player, stack);
                AddExperienceResult result = pokemon.addExperienceWithPlayer(player, source, finalExperience);
                boolean returnValue = false;
                if (result.getExperienceAdded() > 0) {
                    if (!player.m_7500_()) {
                        stack.m_41774_(1);
                    }
                    returnValue = true;
                }
                EventObservable<ExperienceCandyUseEvent.Post> eventObservable2 = CobblemonEvents.EXPERIENCE_CANDY_USE_POST;
                ExperienceCandyUseEvent.Post[] postArray = new ExperienceCandyUseEvent.Post[]{new ExperienceCandyUseEvent.Post(player, pokemon, this, result)};
                ExperienceCandyUseEvent.Post[] events$iv = postArray;
                boolean $i$f$post2 = false;
                $this$iv2.emit(Arrays.copyOf(events$iv, events$iv.length));
                ExperienceCandyUseEvent.Post[] $this$forEach$iv$iv = events$iv;
                boolean $i$f$forEach2 = false;
                int n2 = $this$forEach$iv$iv.length;
                for (int j = 0; j < n2; ++j) {
                    ExperienceCandyUseEvent.Post element$iv$iv;
                    ExperienceCandyUseEvent.Post post2 = element$iv$iv = $this$forEach$iv$iv[j];
                    boolean bl3 = false;
                    ExperienceCandyUseEvent.Post it = post2;
                }
                return returnValue ? InteractionResultHolder.m_19090_((Object)stack) : InteractionResultHolder.m_19100_((Object)stack);
            }
            Cancelable cancelable2 = it$iv;
            boolean bl4 = false;
            Cancelable it = cancelable2;
        }
        return InteractionResultHolder.m_19100_((Object)stack);
    }

    @Override
    public boolean canUseOnPokemon(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        return pokemon.isPlayerOwned();
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

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\b\u00e6\u0080\u0001\u0018\u00002\u00020\u0001J\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H&\u00a2\u0006\u0004\b\u0007\u0010\b\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/item/interactive/CandyItem$Calculator;", "", "Lnet/minecraft/server/level/ServerPlayer;", "player", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "", "calculate", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/pokemon/Pokemon;)I", "common"})
    public static interface Calculator {
        public int calculate(@NotNull ServerPlayer var1, @NotNull Pokemon var2);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\t\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nR\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0004R\u0014\u0010\u0006\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0004R\u0014\u0010\u0007\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0007\u0010\u0004R\u0014\u0010\b\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\b\u0010\u0004\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/item/interactive/CandyItem$Companion;", "", "", "DEFAULT_L_CANDY_YIELD", "I", "DEFAULT_M_CANDY_YIELD", "DEFAULT_S_CANDY_YIELD", "DEFAULT_XL_CANDY_YIELD", "DEFAULT_XS_CANDY_YIELD", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

