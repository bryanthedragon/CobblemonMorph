/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.PrimitiveCodec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Instance
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.random.Random
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate$StructureBlockInfo
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.structureprocessors;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.codec.PairCodecKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.placementmodifier.BlockStateTransformer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.structureprocessors.CobblemonProcessorTypes;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 '2\u00020\u0001:\u0001'BA\u0012\u0018\u0010\u001e\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u001d0\u001c0\u0016\u0012\u0006\u0010!\u001a\u00020 \u0012\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0011\u00a2\u0006\u0004\b%\u0010&J\u0015\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00000\u0002H\u0014\u00a2\u0006\u0004\b\u0003\u0010\u0004J?\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0012\u001a\u00020\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\u001d\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00170\u00168\u0006\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001bR)\u0010\u001e\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u001d0\u001c0\u00168\u0006\u00a2\u0006\f\n\u0004\b\u001e\u0010\u0019\u001a\u0004\b\u001f\u0010\u001bR\u0017\u0010!\u001a\u00020 8\u0006\u00a2\u0006\f\n\u0004\b!\u0010\"\u001a\u0004\b#\u0010$\u00a8\u0006("}, d2={"Lcom/cobblemon/mod/common/world/structureprocessors/RandomizedStructureMappedBlockStatePairProcessor;", "Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureProcessor;", "Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureProcessorType;", "getType", "()Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureProcessorType;", "Lnet/minecraft/world/level/LevelReader;", "world", "Lnet/minecraft/core/BlockPos;", "pos", "pivot", "Lnet/minecraft/structure/StructureTemplate$StructureBlockInfo;", "originalBlockInfo", "currentBlockInfo", "Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructurePlaceSettings;", "data", "process", "(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplate$StructureBlockInfo;Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplate$StructureBlockInfo;Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructurePlaceSettings;)Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplate$StructureBlockInfo;", "", "probability", "F", "getProbability", "()F", "", "Lnet/minecraft/world/level/levelgen/structure/templatesystem/RuleTest;", "rules", "Ljava/util/List;", "getRules", "()Ljava/util/List;", "Lkotlin/Pair;", "Lnet/minecraft/world/level/block/Block;", "targetBlockPairs", "getTargetBlockPairs", "Lcom/cobblemon/mod/common/world/placementmodifier/BlockStateTransformer;", "transformer", "Lcom/cobblemon/mod/common/world/placementmodifier/BlockStateTransformer;", "getTransformer", "()Lcom/cobblemon/mod/common/world/placementmodifier/BlockStateTransformer;", "<init>", "(Ljava/util/List;Lcom/cobblemon/mod/common/world/placementmodifier/BlockStateTransformer;Ljava/util/List;F)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nRandomizedStructureMappedBlockStatePairProcessor.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RandomizedStructureMappedBlockStatePairProcessor.kt\ncom/cobblemon/mod/common/world/structureprocessors/RandomizedStructureMappedBlockStatePairProcessor\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,94:1\n1#2:95\n*E\n"})
public final class RandomizedStructureMappedBlockStatePairProcessor
extends StructureProcessor {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final List<Pair<Block, Block>> targetBlockPairs;
    @NotNull
    private final BlockStateTransformer transformer;
    @NotNull
    private final List<RuleTest> rules;
    private final float probability;
    @NotNull
    private static final Codec<RandomizedStructureMappedBlockStatePairProcessor> CODEC;

    public RandomizedStructureMappedBlockStatePairProcessor(@NotNull List<? extends Pair<? extends Block, ? extends Block>> targetBlockPairs, @NotNull BlockStateTransformer transformer, @NotNull List<? extends RuleTest> rules, float probability) {
        Intrinsics.checkNotNullParameter(targetBlockPairs, (String)"targetBlockPairs");
        Intrinsics.checkNotNullParameter((Object)transformer, (String)"transformer");
        Intrinsics.checkNotNullParameter(rules, (String)"rules");
        this.targetBlockPairs = targetBlockPairs;
        this.transformer = transformer;
        this.rules = rules;
        this.probability = probability;
    }

    public /* synthetic */ RandomizedStructureMappedBlockStatePairProcessor(List list, BlockStateTransformer blockStateTransformer, List list2, float f, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 8) != 0) {
            f = 1.0f;
        }
        this(list, blockStateTransformer, list2, f);
    }

    @NotNull
    public final List<Pair<Block, Block>> getTargetBlockPairs() {
        return this.targetBlockPairs;
    }

    @NotNull
    public final BlockStateTransformer getTransformer() {
        return this.transformer;
    }

    @NotNull
    public final List<RuleTest> getRules() {
        return this.rules;
    }

    public final float getProbability() {
        return this.probability;
    }

    @NotNull
    public StructureTemplate.StructureBlockInfo m_7382_(@NotNull LevelReader world, @NotNull BlockPos pos, @NotNull BlockPos pivot, @NotNull StructureTemplate.StructureBlockInfo originalBlockInfo, @NotNull StructureTemplate.StructureBlockInfo currentBlockInfo, @NotNull StructurePlaceSettings data) {
        boolean active;
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)pivot, (String)"pivot");
        Intrinsics.checkNotNullParameter((Object)originalBlockInfo, (String)"originalBlockInfo");
        Intrinsics.checkNotNullParameter((Object)currentBlockInfo, (String)"currentBlockInfo");
        Intrinsics.checkNotNullParameter((Object)data, (String)"data");
        RandomSource random = RandomSource.m_216335_((long)pivot.hashCode());
        boolean bl = active = random.m_188501_() < this.probability;
        if (!active) {
            return currentBlockInfo;
        }
        for (RuleTest rule : this.rules) {
            if (!rule.m_213865_(currentBlockInfo.f_74676_(), data.m_230326_(pos))) continue;
            Pair<Block, Block> pair = this.targetBlockPairs.get(random.m_188503_(this.targetBlockPairs.size()));
            Block block1 = (Block)pair.component1();
            Block block2 = (Block)pair.component2();
            Block[] blockArray = new Block[]{block1, block2};
            blockArray = ((Block)CollectionsKt.random((Collection)SetsKt.setOf((Object[])blockArray), (Random)((Random)Random.Default))).m_49966_();
            BlockStateTransformer blockStateTransformer = this.transformer;
            Block[] p0 = blockArray;
            boolean bl2 = false;
            BlockState targetState = blockStateTransformer.transform((BlockState)p0);
            return new StructureTemplate.StructureBlockInfo(currentBlockInfo.f_74675_(), targetState, currentBlockInfo.f_74677_());
        }
        return currentBlockInfo;
    }

    @NotNull
    protected StructureProcessorType<RandomizedStructureMappedBlockStatePairProcessor> m_6953_() {
        return CobblemonProcessorTypes.RANDOM_POOLED_STATES;
    }

    private static final List CODEC$lambda$5$lambda$0(RandomizedStructureMappedBlockStatePairProcessor it) {
        return it.targetBlockPairs;
    }

    private static final BlockStateTransformer CODEC$lambda$5$lambda$1(RandomizedStructureMappedBlockStatePairProcessor it) {
        return it.transformer;
    }

    private static final List CODEC$lambda$5$lambda$2(RandomizedStructureMappedBlockStatePairProcessor it) {
        return it.rules;
    }

    private static final Float CODEC$lambda$5$lambda$3(RandomizedStructureMappedBlockStatePairProcessor it) {
        return Float.valueOf(it.probability);
    }

    private static final RandomizedStructureMappedBlockStatePairProcessor CODEC$lambda$5$lambda$4(List targetBlockPairs, BlockStateTransformer transformer, List rules, Float probability) {
        Intrinsics.checkNotNullExpressionValue((Object)targetBlockPairs, (String)"targetBlockPairs");
        Intrinsics.checkNotNullExpressionValue((Object)transformer, (String)"transformer");
        Intrinsics.checkNotNullExpressionValue((Object)rules, (String)"rules");
        Intrinsics.checkNotNullExpressionValue((Object)probability, (String)"probability");
        return new RandomizedStructureMappedBlockStatePairProcessor(targetBlockPairs, transformer, rules, probability.floatValue());
    }

    private static final App CODEC$lambda$5(RecordCodecBuilder.Instance instance) {
        Codec codec2 = BuiltInRegistries.f_256975_.m_194605_();
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"BLOCK.codec");
        Codec codec3 = BuiltInRegistries.f_256975_.m_194605_();
        Intrinsics.checkNotNullExpressionValue((Object)codec3, (String)"BLOCK.codec");
        return instance.group((App)PairCodecKt.pairCodec(codec2, codec3).listOf().fieldOf("targetBlockPairs").forGetter(RandomizedStructureMappedBlockStatePairProcessor::CODEC$lambda$5$lambda$0), (App)BlockStateTransformer.Companion.getCodec().fieldOf("transformer").forGetter(RandomizedStructureMappedBlockStatePairProcessor::CODEC$lambda$5$lambda$1), (App)RuleTest.f_74307_.listOf().fieldOf("rules").forGetter(RandomizedStructureMappedBlockStatePairProcessor::CODEC$lambda$5$lambda$2), (App)PrimitiveCodec.FLOAT.fieldOf("probability").forGetter(RandomizedStructureMappedBlockStatePairProcessor::CODEC$lambda$5$lambda$3)).apply((Applicative)instance, RandomizedStructureMappedBlockStatePairProcessor::CODEC$lambda$5$lambda$4);
    }

    static {
        Codec codec2 = RecordCodecBuilder.create(RandomizedStructureMappedBlockStatePairProcessor::CODEC$lambda$5);
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"create { instance ->\n   \u2026              }\n        }");
        CODEC = codec2;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/world/structureprocessors/RandomizedStructureMappedBlockStatePairProcessor$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/world/structureprocessors/RandomizedStructureMappedBlockStatePairProcessor;", "CODEC", "Lcom/mojang/serialization/Codec;", "getCODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Codec<RandomizedStructureMappedBlockStatePairProcessor> getCODEC() {
            return CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

