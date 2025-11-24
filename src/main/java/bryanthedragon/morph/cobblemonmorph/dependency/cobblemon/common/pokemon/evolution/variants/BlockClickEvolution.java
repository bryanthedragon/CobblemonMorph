/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.StringsKt
 *  net.minecraft.core.Registry
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.registry.BlockIdentifierCondition;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\b\u0016\u0018\u0000 52\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001:\u000265B\t\b\u0016\u00a2\u0006\u0004\b2\u00103BQ\u0012\u0006\u0010\u0019\u001a\u00020\u0018\u0012\u0006\u0010.\u001a\u00020-\u0012\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010#\u001a\u00020\u0007\u0012\u0006\u0010\u0012\u001a\u00020\u0007\u0012\f\u0010+\u001a\b\u0012\u0004\u0012\u00020*0\u001d\u0012\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001d\u00a2\u0006\u0004\b2\u00104J\u001a\u0010\b\u001a\u00020\u00072\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005H\u0096\u0002\u00a2\u0006\u0004\b\b\u0010\tJ\u000f\u0010\u000b\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fJ\u001f\u0010\u0010\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011R\"\u0010\u0012\u001a\u00020\u00078\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0019\u001a\u00020\u00188\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u001b\u0010\u001cR \u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001d8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001f\u0010 \u001a\u0004\b!\u0010\"R\"\u0010#\u001a\u00020\u00078\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b#\u0010\u0013\u001a\u0004\b$\u0010\u0015\"\u0004\b%\u0010\u0017R \u0010&\u001a\b\u0012\u0004\u0012\u00020\u00040\u00038\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b&\u0010'\u001a\u0004\b(\u0010)R \u0010+\u001a\b\u0012\u0004\u0012\u00020*0\u001d8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b+\u0010 \u001a\u0004\b,\u0010\"R\u001a\u0010.\u001a\u00020-8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b.\u0010/\u001a\u0004\b0\u00101\u00a8\u00067"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/variants/BlockClickEvolution;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/ContextEvolution;", "Lcom/cobblemon/mod/common/pokemon/evolution/variants/BlockClickEvolution$BlockInteractionContext;", "Lcom/cobblemon/mod/common/api/conditional/RegistryLikeCondition;", "Lnet/minecraft/world/level/block/Block;", "", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "context", "testContext", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lcom/cobblemon/mod/common/pokemon/evolution/variants/BlockClickEvolution$BlockInteractionContext;)Z", "consumeHeldItem", "Z", "getConsumeHeldItem", "()Z", "setConsumeHeldItem", "(Z)V", "", "id", "Ljava/lang/String;", "getId", "()Ljava/lang/String;", "", "Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "learnableMoves", "Ljava/util/Set;", "getLearnableMoves", "()Ljava/util/Set;", "optional", "getOptional", "setOptional", "requiredContext", "Lcom/cobblemon/mod/common/api/conditional/RegistryLikeCondition;", "getRequiredContext", "()Lcom/cobblemon/mod/common/api/conditional/RegistryLikeCondition;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/requirement/EvolutionRequirement;", "requirements", "getRequirements", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "result", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "getResult", "()Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "<init>", "()V", "(Ljava/lang/String;Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;Lcom/cobblemon/mod/common/api/conditional/RegistryLikeCondition;ZZLjava/util/Set;Ljava/util/Set;)V", "Companion", "BlockInteractionContext", "common"})
public class BlockClickEvolution
implements ContextEvolution<BlockInteractionContext, RegistryLikeCondition<Block>> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final String id;
    @NotNull
    private final PokemonProperties result;
    @NotNull
    private final RegistryLikeCondition<Block> requiredContext;
    private boolean optional;
    private boolean consumeHeldItem;
    @NotNull
    private final Set<EvolutionRequirement> requirements;
    @NotNull
    private final Set<MoveTemplate> learnableMoves;
    @NotNull
    public static final String ADAPTER_VARIANT = "block_click";

    public BlockClickEvolution(@NotNull String id, @NotNull PokemonProperties result, @NotNull RegistryLikeCondition<Block> requiredContext, boolean optional, boolean consumeHeldItem, @NotNull Set<EvolutionRequirement> requirements, @NotNull Set<MoveTemplate> learnableMoves) {
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter((Object)result, (String)"result");
        Intrinsics.checkNotNullParameter(requiredContext, (String)"requiredContext");
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
    public RegistryLikeCondition<Block> getRequiredContext() {
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

    public BlockClickEvolution() {
        this("id", new PokemonProperties(), new BlockIdentifierCondition(new ResourceLocation("minecraft", "dirt")), true, true, new LinkedHashSet(), new LinkedHashSet());
    }

    @Override
    public boolean testContext(@NotNull Pokemon pokemon, @NotNull BlockInteractionContext context) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        Object object = this.getRequiredContext();
        Block block = context.getBlock();
        Registry registry = context.getWorld().m_9598_().m_175515_(Registries.f_256747_);
        Intrinsics.checkNotNullExpressionValue((Object)registry, (String)"context.world.registryMa\u2026r.get(RegistryKeys.BLOCK)");
        return object.fits(block, registry);
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof BlockClickEvolution && StringsKt.equals((String)((BlockClickEvolution)other).getId(), (String)this.getId(), (boolean)true);
    }

    public int hashCode() {
        int result = this.getId().hashCode();
        result = 31 * result + ADAPTER_VARIANT.hashCode();
        return result;
    }

    @Override
    public boolean attemptEvolution(@NotNull Pokemon pokemon, @NotNull BlockInteractionContext context) {
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

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\b\u001a\u00020\u0002\u0012\u0006\u0010\t\u001a\u00020\u0005\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J$\u0010\n\u001a\u00020\u00002\b\b\u0002\u0010\b\u001a\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\u0005H\u00c6\u0001\u00a2\u0006\u0004\b\n\u0010\u000bJ\u001a\u0010\u000e\u001a\u00020\r2\b\u0010\f\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0010\u0010\u0011\u001a\u00020\u0010H\u00d6\u0001\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0010\u0010\u0014\u001a\u00020\u0013H\u00d6\u0001\u00a2\u0006\u0004\b\u0014\u0010\u0015R\u0017\u0010\b\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\u0016\u001a\u0004\b\u0017\u0010\u0004R\u0017\u0010\t\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\u0018\u001a\u0004\b\u0019\u0010\u0007\u00a8\u0006\u001c"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/variants/BlockClickEvolution$BlockInteractionContext;", "", "Lnet/minecraft/world/level/block/Block;", "component1", "()Lnet/minecraft/world/level/block/Block;", "Lnet/minecraft/world/level/Level;", "component2", "()Lnet/minecraft/world/level/Level;", "block", "world", "copy", "(Lnet/minecraft/world/level/block/Block;Lnet/minecraft/world/level/Level;)Lcom/cobblemon/mod/common/pokemon/evolution/variants/BlockClickEvolution$BlockInteractionContext;", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lnet/minecraft/world/level/block/Block;", "getBlock", "Lnet/minecraft/world/level/Level;", "getWorld", "<init>", "(Lnet/minecraft/world/level/block/Block;Lnet/minecraft/world/level/Level;)V", "common"})
    public static final class BlockInteractionContext {
        @NotNull
        private final Block block;
        @NotNull
        private final Level world;

        public BlockInteractionContext(@NotNull Block block, @NotNull Level world) {
            Intrinsics.checkNotNullParameter((Object)block, (String)"block");
            Intrinsics.checkNotNullParameter((Object)world, (String)"world");
            this.block = block;
            this.world = world;
        }

        @NotNull
        public final Block getBlock() {
            return this.block;
        }

        @NotNull
        public final Level getWorld() {
            return this.world;
        }

        @NotNull
        public final Block component1() {
            return this.block;
        }

        @NotNull
        public final Level component2() {
            return this.world;
        }

        @NotNull
        public final BlockInteractionContext copy(@NotNull Block block, @NotNull Level world) {
            Intrinsics.checkNotNullParameter((Object)block, (String)"block");
            Intrinsics.checkNotNullParameter((Object)world, (String)"world");
            return new BlockInteractionContext(block, world);
        }

        public static /* synthetic */ BlockInteractionContext copy$default(BlockInteractionContext blockInteractionContext, Block block, Level level, int n, Object object) {
            if ((n & 1) != 0) {
                block = blockInteractionContext.block;
            }
            if ((n & 2) != 0) {
                level = blockInteractionContext.world;
            }
            return blockInteractionContext.copy(block, level);
        }

        @NotNull
        public String toString() {
            return "BlockInteractionContext(block=" + this.block + ", world=" + this.world + ")";
        }

        public int hashCode() {
            int result = this.block.hashCode();
            result = result * 31 + this.world.hashCode();
            return result;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof BlockInteractionContext)) {
                return false;
            }
            BlockInteractionContext blockInteractionContext = (BlockInteractionContext)other;
            if (!Intrinsics.areEqual((Object)this.block, (Object)blockInteractionContext.block)) {
                return false;
            }
            return Intrinsics.areEqual((Object)this.world, (Object)blockInteractionContext.world);
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0007"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/variants/BlockClickEvolution$Companion;", "", "", "ADAPTER_VARIANT", "Ljava/lang/String;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

