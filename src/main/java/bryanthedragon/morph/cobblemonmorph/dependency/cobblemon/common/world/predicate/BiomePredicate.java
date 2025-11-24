/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Instance
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.jvm.optionals.OptionalsKt
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Holder
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.level.WorldGenLevel
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate
 *  net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.predicate;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.predicate.CobblemonBlockPredicates;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.optionals.OptionalsKt;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B;\u0012\u0018\u0010\u0014\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\r0\f\u0012\u0018\u0010\u0010\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\r0\f\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0015\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00000\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u001f\u0010\n\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\n\u0010\u000bR)\u0010\u0010\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\r0\f8\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013R)\u0010\u0014\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\r0\f8\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0011\u001a\u0004\b\u0015\u0010\u0013\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/world/predicate/BiomePredicate;", "Lnet/minecraft/world/level/levelgen/blockpredicates/BlockPredicate;", "Lnet/minecraft/world/level/levelgen/blockpredicates/BlockPredicateType;", "getType", "()Lnet/minecraft/world/level/levelgen/blockpredicates/BlockPredicateType;", "Lnet/minecraft/world/level/WorldGenLevel;", "world", "Lnet/minecraft/core/BlockPos;", "block", "", "test", "(Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/core/BlockPos;)Z", "Ljava/util/Optional;", "", "Lnet/minecraft/tags/TagKey;", "Lnet/minecraft/world/level/biome/Biome;", "excludedBiomes", "Ljava/util/Optional;", "getExcludedBiomes", "()Ljava/util/Optional;", "includedBiomes", "getIncludedBiomes", "<init>", "(Ljava/util/Optional;Ljava/util/Optional;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nBiomePredicate.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BiomePredicate.kt\ncom/cobblemon/mod/common/world/predicate/BiomePredicate\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,50:1\n1747#2,3:51\n1747#2,3:54\n*S KotlinDebug\n*F\n+ 1 BiomePredicate.kt\ncom/cobblemon/mod/common/world/predicate/BiomePredicate\n*L\n37#1:51,3\n38#1:54,3\n*E\n"})
public final class BiomePredicate
implements BlockPredicate {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final Optional<List<TagKey<Biome>>> includedBiomes;
    @NotNull
    private final Optional<List<TagKey<Biome>>> excludedBiomes;
    @NotNull
    private static final Codec<BiomePredicate> CODEC;

    public BiomePredicate(@NotNull Optional<List<TagKey<Biome>>> includedBiomes, @NotNull Optional<List<TagKey<Biome>>> excludedBiomes) {
        Intrinsics.checkNotNullParameter(includedBiomes, (String)"includedBiomes");
        Intrinsics.checkNotNullParameter(excludedBiomes, (String)"excludedBiomes");
        this.includedBiomes = includedBiomes;
        this.excludedBiomes = excludedBiomes;
    }

    @NotNull
    public final Optional<List<TagKey<Biome>>> getIncludedBiomes() {
        return this.includedBiomes;
    }

    @NotNull
    public final Optional<List<TagKey<Biome>>> getExcludedBiomes() {
        return this.excludedBiomes;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean test(@NotNull WorldGenLevel world, @NotNull BlockPos block) {
        boolean bl;
        Object element$iv;
        TagKey it;
        Iterator iterator;
        boolean $i$f$any;
        Iterable $this$any$iv;
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)block, (String)"block");
        Holder biome2 = world.m_204166_(block);
        List list = (List)OptionalsKt.getOrNull(this.includedBiomes);
        if (list != null) {
            $this$any$iv = list;
            $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                return false;
            }
            iterator = $this$any$iv.iterator();
            do {
                if (!iterator.hasNext()) return false;
                element$iv = iterator.next();
                it = (TagKey)element$iv;
                boolean bl2 = false;
            } while (!biome2.m_203656_(it));
            bl = true;
        } else {
            bl = true;
        }
        if (!bl) return false;
        List list2 = (List)OptionalsKt.getOrNull(this.excludedBiomes);
        if (list2 == null) return true;
        $this$any$iv = list2;
        $i$f$any = false;
        if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
            return true;
        }
        iterator = $this$any$iv.iterator();
        do {
            if (!iterator.hasNext()) return true;
            element$iv = iterator.next();
            it = (TagKey)element$iv;
            boolean bl3 = false;
        } while (!biome2.m_203656_(it));
        return false;
    }

    @NotNull
    public BlockPredicateType<BiomePredicate> m_183575_() {
        return CobblemonBlockPredicates.INSTANCE.getBIOME();
    }

    private static final Optional CODEC$lambda$4$lambda$2(BiomePredicate it) {
        return it.includedBiomes;
    }

    private static final Optional CODEC$lambda$4$lambda$3(BiomePredicate it) {
        return it.excludedBiomes;
    }

    private static final App CODEC$lambda$4(RecordCodecBuilder.Instance instance) {
        return instance.group((App)TagKey.m_203886_((ResourceKey)Registries.f_256952_).listOf().optionalFieldOf("includedBiomes").forGetter(BiomePredicate::CODEC$lambda$4$lambda$2), (App)TagKey.m_203886_((ResourceKey)Registries.f_256952_).listOf().optionalFieldOf("excludedBiomes").forGetter(BiomePredicate::CODEC$lambda$4$lambda$3)).apply((Applicative)instance, BiomePredicate::new);
    }

    static {
        Codec codec2 = RecordCodecBuilder.create(BiomePredicate::CODEC$lambda$4);
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"create { instance ->\n   \u2026BiomePredicate)\n        }");
        CODEC = codec2;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/world/predicate/BiomePredicate$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/world/predicate/BiomePredicate;", "CODEC", "Lcom/mojang/serialization/Codec;", "getCODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Codec<BiomePredicate> getCODEC() {
            return CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

