/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.StringsKt
 *  net.minecraft.advancements.critereon.NbtPredicate
 *  net.minecraft.core.Registry
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.variants;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.ContextEvolution;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.predicate.NbtItemPredicate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.registry.ItemIdentifierCondition;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.minecraft.advancements.critereon.NbtPredicate;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\b\u0016\u0018\u0000 42\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u000245B\t\b\u0016\u00a2\u0006\u0004\b1\u00102BK\u0012\u0006\u0010\u0018\u001a\u00020\u0017\u0012\u0006\u0010-\u001a\u00020,\u0012\u0006\u0010%\u001a\u00020\u0003\u0012\u0006\u0010\"\u001a\u00020\u0006\u0012\u0006\u0010\u0011\u001a\u00020\u0006\u0012\f\u0010*\u001a\b\u0012\u0004\u0012\u00020)0\u001c\u0012\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c\u00a2\u0006\u0004\b1\u00103J\u001a\u0010\u0007\u001a\u00020\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H\u0096\u0002\u00a2\u0006\u0004\b\u0007\u0010\bJ\u000f\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ\u001f\u0010\u000f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010R\"\u0010\u0011\u001a\u00020\u00068\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0018\u001a\u00020\u00178\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001bR \u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b \u0010!R\"\u0010\"\u001a\u00020\u00068\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\"\u0010\u0012\u001a\u0004\b#\u0010\u0014\"\u0004\b$\u0010\u0016R\u001a\u0010%\u001a\u00020\u00038\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b%\u0010&\u001a\u0004\b'\u0010(R \u0010*\u001a\b\u0012\u0004\u0012\u00020)0\u001c8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b*\u0010\u001f\u001a\u0004\b+\u0010!R\u001a\u0010-\u001a\u00020,8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b-\u0010.\u001a\u0004\b/\u00100\u00a8\u00066"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/variants/ItemInteractionEvolution;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/ContextEvolution;", "Lcom/cobblemon/mod/common/pokemon/evolution/variants/ItemInteractionEvolution$ItemInteractionContext;", "Lcom/cobblemon/mod/common/pokemon/evolution/predicate/NbtItemPredicate;", "", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "context", "testContext", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lcom/cobblemon/mod/common/pokemon/evolution/variants/ItemInteractionEvolution$ItemInteractionContext;)Z", "consumeHeldItem", "Z", "getConsumeHeldItem", "()Z", "setConsumeHeldItem", "(Z)V", "", "id", "Ljava/lang/String;", "getId", "()Ljava/lang/String;", "", "Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "learnableMoves", "Ljava/util/Set;", "getLearnableMoves", "()Ljava/util/Set;", "optional", "getOptional", "setOptional", "requiredContext", "Lcom/cobblemon/mod/common/pokemon/evolution/predicate/NbtItemPredicate;", "getRequiredContext", "()Lcom/cobblemon/mod/common/pokemon/evolution/predicate/NbtItemPredicate;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/requirement/EvolutionRequirement;", "requirements", "getRequirements", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "result", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "getResult", "()Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "<init>", "()V", "(Ljava/lang/String;Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;Lcom/cobblemon/mod/common/pokemon/evolution/predicate/NbtItemPredicate;ZZLjava/util/Set;Ljava/util/Set;)V", "Companion", "ItemInteractionContext", "common"})
public class ItemInteractionEvolution
implements ContextEvolution<ItemInteractionContext, NbtItemPredicate> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final String id;
    @NotNull
    private final PokemonProperties result;
    @NotNull
    private final NbtItemPredicate requiredContext;
    private boolean optional;
    private boolean consumeHeldItem;
    @NotNull
    private final Set<EvolutionRequirement> requirements;
    @NotNull
    private final Set<MoveTemplate> learnableMoves;
    @NotNull
    public static final String ADAPTER_VARIANT = "item_interact";

    public ItemInteractionEvolution(@NotNull String id, @NotNull PokemonProperties result, @NotNull NbtItemPredicate requiredContext, boolean optional, boolean consumeHeldItem, @NotNull Set<EvolutionRequirement> requirements, @NotNull Set<MoveTemplate> learnableMoves) {
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter((Object)result, (String)"result");
        Intrinsics.checkNotNullParameter((Object)requiredContext, (String)"requiredContext");
        Intrinsics.checkNotNullParameter(requirements, (String)"requirements");
        Intrinsics.checkNotNullParameter(learnableMoves, (String)"learnableMoves");
        this.id = id;
        this.result = result;
        this.requiredContext = requiredContext;
        this.optional = optional;
        this.consumeHeldItem = consumeHeldItem;
        this.requirements = requirements;
        this.learnableMoves = learnableMoves;
    }

    @Override
    @NotNull
    public String getId() {
        return this.id;
    }

    @Override
    @NotNull
    public PokemonProperties getResult() {
        return this.result;
    }

    @Override
    @NotNull
    public NbtItemPredicate getRequiredContext() {
        return this.requiredContext;
    }

    @Override
    public boolean getOptional() {
        return this.optional;
    }

    @Override
    public void setOptional(boolean bl) {
        this.optional = bl;
    }

    @Override
    public boolean getConsumeHeldItem() {
        return this.consumeHeldItem;
    }

    @Override
    public void setConsumeHeldItem(boolean bl) {
        this.consumeHeldItem = bl;
    }

    @Override
    @NotNull
    public Set<EvolutionRequirement> getRequirements() {
        return this.requirements;
    }

    @Override
    @NotNull
    public Set<MoveTemplate> getLearnableMoves() {
        return this.learnableMoves;
    }

    public ItemInteractionEvolution() {
        PokemonProperties pokemonProperties = new PokemonProperties();
        RegistryLikeCondition registryLikeCondition = new ItemIdentifierCondition(new ResourceLocation("minecraft", "fish"));
        NbtPredicate nbtPredicate = NbtPredicate.f_57471_;
        Intrinsics.checkNotNullExpressionValue((Object)nbtPredicate, (String)"ANY");
        this("id", pokemonProperties, new NbtItemPredicate(registryLikeCondition, nbtPredicate), true, true, new LinkedHashSet(), new LinkedHashSet());
    }

    @Override
    public boolean testContext(@NotNull Pokemon pokemon, @NotNull ItemInteractionContext context) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        RegistryLikeCondition<Item> registryLikeCondition = this.getRequiredContext().getItem();
        Item item = context.getStack().m_41720_();
        Intrinsics.checkNotNullExpressionValue((Object)item, (String)"context.stack.item");
        Registry registry = context.getWorld().m_9598_().m_175515_(Registries.f_256913_);
        Intrinsics.checkNotNullExpressionValue((Object)registry, (String)"context.world.registryMa\u2026er.get(RegistryKeys.ITEM)");
        return registryLikeCondition.fits(item, (Registry<Item>)registry) && this.getRequiredContext().getNbt().m_57479_(context.getStack());
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof ItemInteractionEvolution && StringsKt.equals((String)((ItemInteractionEvolution)other).getId(), (String)this.getId(), (boolean)true);
    }

    public int hashCode() {
        int result = this.getId().hashCode();
        result = 31 * result + ADAPTER_VARIANT.hashCode();
        return result;
    }

    @Override
    public boolean attemptEvolution(@NotNull Pokemon pokemon, @NotNull ItemInteractionContext context) {
        return ContextEvolution.DefaultImpls.attemptEvolution(this, pokemon, context);
    }

    @Override
    public boolean test(@NotNull Pokemon pokemon) {
        return ContextEvolution.DefaultImpls.test(this, pokemon);
    }

    @Override
    public boolean evolve(@NotNull Pokemon pokemon) {
        return ContextEvolution.DefaultImpls.evolve(this, pokemon);
    }

    @Override
    public void forceEvolve(@NotNull Pokemon pokemon) {
        ContextEvolution.DefaultImpls.forceEvolve(this, pokemon);
    }

    @Override
    public void evolutionMethod(@NotNull Pokemon pokemon) {
        ContextEvolution.DefaultImpls.evolutionMethod(this, pokemon);
    }

    @Override
    public void applyTo(@NotNull Pokemon pokemon) {
        ContextEvolution.DefaultImpls.applyTo(this, pokemon);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0007"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/variants/ItemInteractionEvolution$Companion;", "", "", "ADAPTER_VARIANT", "Ljava/lang/String;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\b\u001a\u00020\u0002\u0012\u0006\u0010\t\u001a\u00020\u0005\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J$\u0010\n\u001a\u00020\u00002\b\b\u0002\u0010\b\u001a\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\u0005H\u00c6\u0001\u00a2\u0006\u0004\b\n\u0010\u000bJ\u001a\u0010\u000e\u001a\u00020\r2\b\u0010\f\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0010\u0010\u0011\u001a\u00020\u0010H\u00d6\u0001\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0010\u0010\u0014\u001a\u00020\u0013H\u00d6\u0001\u00a2\u0006\u0004\b\u0014\u0010\u0015R\u0017\u0010\b\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\u0016\u001a\u0004\b\u0017\u0010\u0004R\u0017\u0010\t\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\u0018\u001a\u0004\b\u0019\u0010\u0007\u00a8\u0006\u001c"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/variants/ItemInteractionEvolution$ItemInteractionContext;", "", "Lnet/minecraft/world/item/ItemStack;", "component1", "()Lnet/minecraft/world/item/ItemStack;", "Lnet/minecraft/world/level/Level;", "component2", "()Lnet/minecraft/world/level/Level;", "stack", "world", "copy", "(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;)Lcom/cobblemon/mod/common/pokemon/evolution/variants/ItemInteractionEvolution$ItemInteractionContext;", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lnet/minecraft/world/item/ItemStack;", "getStack", "Lnet/minecraft/world/level/Level;", "getWorld", "<init>", "(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;)V", "common"})
    public static final class ItemInteractionContext {
        @NotNull
        private final ItemStack stack;
        @NotNull
        private final Level world;

        public ItemInteractionContext(@NotNull ItemStack stack, @NotNull Level world) {
            Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
            Intrinsics.checkNotNullParameter((Object)world, (String)"world");
            this.stack = stack;
            this.world = world;
        }

        @NotNull
        public final ItemStack getStack() {
            return this.stack;
        }

        @NotNull
        public final Level getWorld() {
            return this.world;
        }

        @NotNull
        public final ItemStack component1() {
            return this.stack;
        }

        @NotNull
        public final Level component2() {
            return this.world;
        }

        @NotNull
        public final ItemInteractionContext copy(@NotNull ItemStack stack, @NotNull Level world) {
            Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
            Intrinsics.checkNotNullParameter((Object)world, (String)"world");
            return new ItemInteractionContext(stack, world);
        }

        public static /* synthetic */ ItemInteractionContext copy$default(ItemInteractionContext itemInteractionContext, ItemStack itemStack, Level level, int n, Object object) {
            if ((n & 1) != 0) {
                itemStack = itemInteractionContext.stack;
            }
            if ((n & 2) != 0) {
                level = itemInteractionContext.world;
            }
            return itemInteractionContext.copy(itemStack, level);
        }

        @NotNull
        public String toString() {
            return "ItemInteractionContext(stack=" + this.stack + ", world=" + this.world + ")";
        }

        public int hashCode() {
            int result = this.stack.hashCode();
            result = result * 31 + this.world.hashCode();
            return result;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ItemInteractionContext)) {
                return false;
            }
            ItemInteractionContext itemInteractionContext = (ItemInteractionContext)other;
            if (!Intrinsics.areEqual((Object)this.stack, (Object)itemInteractionContext.stack)) {
                return false;
            }
            return Intrinsics.areEqual((Object)this.world, (Object)itemInteractionContext.world);
        }
    }
}

