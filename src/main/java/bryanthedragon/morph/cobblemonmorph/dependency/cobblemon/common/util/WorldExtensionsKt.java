/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.Triple
 *  kotlin.TuplesKt
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.Ref$BooleanRef
 *  kotlin.ranges.IntRange
 *  kotlin.ranges.RangesKt
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Registry
 *  net.minecraft.core.SectionPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.tags.FluidTags
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.border.WorldBorder
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.BlockPosExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.TraceResult;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.Vec3ExtensionsKt;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\u00a6\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u001c\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0019\u0010\u0004\u001a\u00020\u0003*\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u0001\u00a2\u0006\u0004\b\u0004\u0010\u0005\u001a\u0019\u0010\u0004\u001a\u00020\u0003*\u00020\u00002\u0006\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\u0004\b\u0004\u0010\b\u001a;\u0010\u0011\u001a\u00020\u000f*\u00020\t2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u00032\u0018\u0010\u0010\u001a\u0014\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u000f0\r\u00a2\u0006\u0004\b\u0011\u0010\u0012\u001a\u001f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0013*\u00020\t2\u0006\u0010\u000b\u001a\u00020\n\u00a2\u0006\u0004\b\u0014\u0010\u0015\u001a+\u0010\u0017\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00010\u00160\u0013*\u00020\t2\u0006\u0010\u000b\u001a\u00020\n\u00a2\u0006\u0004\b\u0017\u0010\u0015\u001a#\u0010\u001a\u001a\u0014\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u00190\u0018*\u00020\n\u00a2\u0006\u0004\b\u001a\u0010\u001b\u001a%\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0016*\u00020\t2\u0006\u0010\u000b\u001a\u00020\n\u00a2\u0006\u0004\b\u001c\u0010\u001d\u001a\u0019\u0010\u001f\u001a\u00020\u0003*\u00020\u001e2\u0006\u0010\u000b\u001a\u00020\n\u00a2\u0006\u0004\b\u001f\u0010 \u001a?\u0010*\u001a\u00020\u000f*\u00020!2\u0006\u0010\"\u001a\u00020\u00062\u0006\u0010$\u001a\u00020#2\b\b\u0002\u0010&\u001a\u00020%2\b\b\u0002\u0010(\u001a\u00020'2\b\b\u0002\u0010)\u001a\u00020'\u00a2\u0006\u0004\b*\u0010+\u001aC\u00104\u001a\u00020/\"\b\b\u0000\u0010-*\u00020,*\u00020!2\u0006\u0010.\u001a\u00028\u00002\u0006\u0010\"\u001a\u00020\u00062\u0006\u00100\u001a\u00020/2\u0006\u00101\u001a\u00020\u00062\u0006\u00103\u001a\u000202\u00a2\u0006\u0004\b4\u00105\u001a\u0019\u00106\u001a\u00020\u0001*\u00020!2\u0006\u0010\u0002\u001a\u00020\u0001\u00a2\u0006\u0004\b6\u00107\u001a/\u0010<\u001a\u0004\u0018\u00010;*\u00020\u00062\u0006\u00108\u001a\u00020!2\b\b\u0002\u00109\u001a\u00020'2\b\b\u0002\u0010:\u001a\u00020'\u00a2\u0006\u0004\b<\u0010=\"\u001b\u0010B\u001a\b\u0012\u0004\u0012\u00020?0>*\u00020!8F\u00a2\u0006\u0006\u001a\u0004\b@\u0010A\"\u001b\u0010E\u001a\b\u0012\u0004\u0012\u00020C0>*\u00020!8F\u00a2\u0006\u0006\u001a\u0004\bD\u0010A\u00a8\u0006F"}, d2={"Lnet/minecraft/world/entity/Entity;", "Lnet/minecraft/core/BlockPos;", "pos", "", "canFit", "(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/core/BlockPos;)Z", "Lnet/minecraft/world/phys/Vec3;", "vec", "(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/Vec3;)Z", "Lnet/minecraft/world/level/BlockGetter;", "Lnet/minecraft/world/phys/AABB;", "box", "useMutablePos", "Lkotlin/Function2;", "Lnet/minecraft/world/level/block/state/BlockState;", "", "action", "doForAllBlocksIn", "(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/world/phys/AABB;ZLkotlin/jvm/functions/Function2;)V", "", "getBlockStates", "(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/world/phys/AABB;)Ljava/lang/Iterable;", "Lkotlin/Pair;", "getBlockStatesWithPos", "Lkotlin/Triple;", "Lkotlin/ranges/IntRange;", "getRanges", "(Lnet/minecraft/world/phys/AABB;)Lkotlin/Triple;", "getWaterAndLavaIn", "(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/world/phys/AABB;)Lkotlin/Pair;", "Lnet/minecraft/server/level/ServerLevel;", "isBoxLoaded", "(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/phys/AABB;)Z", "Lnet/minecraft/world/level/Level;", "position", "Lnet/minecraft/sounds/SoundEvent;", "sound", "Lnet/minecraft/sounds/SoundSource;", "category", "", "volume", "pitch", "playSoundServer", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V", "Lnet/minecraft/core/particles/ParticleOptions;", "T", "particleType", "", "particles", "offset", "", "speed", "sendParticlesServer", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/particles/ParticleOptions;Lnet/minecraft/world/phys/Vec3;ILnet/minecraft/world/phys/Vec3;D)I", "squeezeWithinBounds", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/core/BlockPos;", "world", "maxDistance", "stepDistance", "Lcom/cobblemon/mod/common/util/TraceResult;", "traceDownwards", "(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/level/Level;FF)Lcom/cobblemon/mod/common/util/TraceResult;", "Lnet/minecraft/core/Registry;", "Lnet/minecraft/world/level/biome/Biome;", "getBiomeRegistry", "(Lnet/minecraft/world/level/Level;)Lnet/minecraft/core/Registry;", "biomeRegistry", "Lnet/minecraft/world/item/Item;", "getItemRegistry", "itemRegistry", "common"})
public final class WorldExtensionsKt {
    public static final void playSoundServer(@NotNull Level $this$playSoundServer, @NotNull Vec3 position, @NotNull SoundEvent sound2, @NotNull SoundSource category, float volume, float pitch) {
        Intrinsics.checkNotNullParameter((Object)$this$playSoundServer, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        Intrinsics.checkNotNullParameter((Object)sound2, (String)"sound");
        Intrinsics.checkNotNullParameter((Object)category, (String)"category");
        ((ServerLevel)$this$playSoundServer).m_6263_(null, position.f_82479_, position.f_82480_, position.f_82481_, sound2, category, volume, pitch);
    }

    public static /* synthetic */ void playSoundServer$default(Level level, Vec3 vec3, SoundEvent soundEvent, SoundSource soundSource, float f, float f2, int n, Object object) {
        if ((n & 4) != 0) {
            soundSource = SoundSource.NEUTRAL;
        }
        if ((n & 8) != 0) {
            f = 1.0f;
        }
        if ((n & 0x10) != 0) {
            f2 = 1.0f;
        }
        WorldExtensionsKt.playSoundServer(level, vec3, soundEvent, soundSource, f, f2);
    }

    public static final <T extends ParticleOptions> int sendParticlesServer(@NotNull Level $this$sendParticlesServer, @NotNull T particleType, @NotNull Vec3 position, int particles, @NotNull Vec3 offset, double speed) {
        Intrinsics.checkNotNullParameter((Object)$this$sendParticlesServer, (String)"<this>");
        Intrinsics.checkNotNullParameter(particleType, (String)"particleType");
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        Intrinsics.checkNotNullParameter((Object)offset, (String)"offset");
        return ((ServerLevel)$this$sendParticlesServer).m_8767_(particleType, position.f_82479_, position.f_82480_, position.f_82481_, particles, offset.f_82479_, offset.f_82480_, offset.f_82481_, speed);
    }

    @NotNull
    public static final BlockPos squeezeWithinBounds(@NotNull Level $this$squeezeWithinBounds, @NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)$this$squeezeWithinBounds, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        WorldBorder border = $this$squeezeWithinBounds.m_6857_();
        return new BlockPos(RangesKt.coerceIn((int)pos.m_123341_(), (int)((int)border.m_61955_()), (int)((int)border.m_61957_())), RangesKt.coerceIn((int)pos.m_123342_(), (int)$this$squeezeWithinBounds.m_141937_(), (int)$this$squeezeWithinBounds.m_151558_()), RangesKt.coerceIn((int)pos.m_123343_(), (int)((int)border.m_61956_()), (int)((int)border.m_61958_())));
    }

    public static final boolean isBoxLoaded(@NotNull ServerLevel $this$isBoxLoaded, @NotNull AABB box) {
        Intrinsics.checkNotNullParameter((Object)$this$isBoxLoaded, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)box, (String)"box");
        int startChunkX = SectionPos.m_175552_((double)box.f_82288_);
        int startChunkZ = SectionPos.m_175552_((double)box.f_82290_);
        int endChunkX = SectionPos.m_175552_((double)box.f_82291_);
        int endChunkZ = SectionPos.m_175552_((double)box.f_82293_);
        int chunkX = startChunkX;
        if (chunkX <= endChunkX) {
            while (true) {
                int chunkZ;
                if ((chunkZ = startChunkZ) <= endChunkZ) {
                    while (true) {
                        if (!$this$isBoxLoaded.m_143319_(ChunkPos.m_45589_((int)chunkX, (int)chunkZ))) {
                            return false;
                        }
                        if (chunkZ == endChunkZ) break;
                        ++chunkZ;
                    }
                }
                if (chunkX == endChunkX) break;
                ++chunkX;
            }
        }
        return true;
    }

    @NotNull
    public static final Triple<IntRange, IntRange, IntRange> getRanges(@NotNull AABB $this$getRanges) {
        Intrinsics.checkNotNullParameter((Object)$this$getRanges, (String)"<this>");
        return new Triple((Object)new IntRange(Mth.m_14107_((double)$this$getRanges.f_82288_), Mth.m_14165_((double)$this$getRanges.f_82291_)), (Object)new IntRange((int)$this$getRanges.f_82289_, Mth.m_14165_((double)$this$getRanges.f_82292_)), (Object)new IntRange((int)$this$getRanges.f_82290_, Mth.m_14165_((double)$this$getRanges.f_82293_)));
    }

    public static final void doForAllBlocksIn(@NotNull BlockGetter $this$doForAllBlocksIn, @NotNull AABB box, boolean useMutablePos, @NotNull Function2<? super BlockState, ? super BlockPos, Unit> action2) {
        Intrinsics.checkNotNullParameter((Object)$this$doForAllBlocksIn, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)box, (String)"box");
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        Triple<IntRange, IntRange, IntRange> triple = WorldExtensionsKt.getRanges(box);
        IntRange xRange = (IntRange)triple.component1();
        IntRange yRange = (IntRange)triple.component2();
        IntRange zRange = (IntRange)triple.component3();
        int x = xRange.getFirst();
        int n = xRange.getLast();
        if (x <= n) {
            while (true) {
                int n2;
                int y;
                if ((y = yRange.getFirst()) <= (n2 = yRange.getLast())) {
                    while (true) {
                        int n3;
                        int z;
                        if ((z = zRange.getFirst()) <= (n3 = zRange.getLast())) {
                            while (true) {
                                BlockPos pos = useMutablePos ? (BlockPos)mutable.m_122178_(x, y, z) : new BlockPos(x, y, z);
                                BlockState state = $this$doForAllBlocksIn.m_8055_(pos);
                                Intrinsics.checkNotNullExpressionValue((Object)state, (String)"state");
                                Intrinsics.checkNotNullExpressionValue((Object)pos, (String)"pos");
                                action2.invoke((Object)state, (Object)pos);
                                if (z == n3) break;
                                ++z;
                            }
                        }
                        if (y == n2) break;
                        ++y;
                    }
                }
                if (x == n) break;
                ++x;
            }
        }
    }

    @NotNull
    public static final Iterable<BlockState> getBlockStates(@NotNull BlockGetter $this$getBlockStates, @NotNull AABB box) {
        Intrinsics.checkNotNullParameter((Object)$this$getBlockStates, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)box, (String)"box");
        List states = new ArrayList();
        WorldExtensionsKt.doForAllBlocksIn($this$getBlockStates, box, true, (Function2<? super BlockState, ? super BlockPos, Unit>)((Function2)new Function2<BlockState, BlockPos, Unit>((List<BlockState>)states){
            final /* synthetic */ List<BlockState> $states;
            {
                this.$states = $states;
                super(2);
            }

            public final void invoke(@NotNull BlockState state, @NotNull BlockPos blockPos2) {
                Intrinsics.checkNotNullParameter((Object)state, (String)"state");
                Intrinsics.checkNotNullParameter((Object)blockPos2, (String)"<anonymous parameter 1>");
                this.$states.add(state);
            }
        }));
        return states;
    }

    @NotNull
    public static final Iterable<Pair<BlockState, BlockPos>> getBlockStatesWithPos(@NotNull BlockGetter $this$getBlockStatesWithPos, @NotNull AABB box) {
        Intrinsics.checkNotNullParameter((Object)$this$getBlockStatesWithPos, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)box, (String)"box");
        List states = new ArrayList();
        WorldExtensionsKt.doForAllBlocksIn($this$getBlockStatesWithPos, box, true, (Function2<? super BlockState, ? super BlockPos, Unit>)((Function2)new Function2<BlockState, BlockPos, Unit>((List<Pair<BlockState, BlockPos>>)states){
            final /* synthetic */ List<Pair<BlockState, BlockPos>> $states;
            {
                this.$states = $states;
                super(2);
            }

            public final void invoke(@NotNull BlockState state, @NotNull BlockPos pos) {
                Intrinsics.checkNotNullParameter((Object)state, (String)"state");
                Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
                this.$states.add((Pair<BlockState, BlockPos>)TuplesKt.to((Object)state, (Object)pos));
            }
        }));
        return states;
    }

    @NotNull
    public static final Pair<Boolean, Boolean> getWaterAndLavaIn(@NotNull BlockGetter $this$getWaterAndLavaIn, @NotNull AABB box) {
        Intrinsics.checkNotNullParameter((Object)$this$getWaterAndLavaIn, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)box, (String)"box");
        Ref.BooleanRef hasWater = new Ref.BooleanRef();
        Ref.BooleanRef hasLava = new Ref.BooleanRef();
        WorldExtensionsKt.doForAllBlocksIn($this$getWaterAndLavaIn, box, true, (Function2<? super BlockState, ? super BlockPos, Unit>)((Function2)new Function2<BlockState, BlockPos, Unit>(hasWater, hasLava){
            final /* synthetic */ Ref.BooleanRef $hasWater;
            final /* synthetic */ Ref.BooleanRef $hasLava;
            {
                this.$hasWater = $hasWater;
                this.$hasLava = $hasLava;
                super(2);
            }

            public final void invoke(@NotNull BlockState state, @NotNull BlockPos blockPos2) {
                Intrinsics.checkNotNullParameter((Object)state, (String)"state");
                Intrinsics.checkNotNullParameter((Object)blockPos2, (String)"<anonymous parameter 1>");
                if (!this.$hasWater.element && state.m_60819_().m_205070_(FluidTags.f_13131_)) {
                    this.$hasWater.element = true;
                }
                if (!this.$hasLava.element && state.m_60819_().m_205070_(FluidTags.f_13132_)) {
                    this.$hasLava.element = true;
                }
            }
        }));
        return TuplesKt.to((Object)hasWater.element, (Object)hasLava.element);
    }

    public static final boolean canFit(@NotNull Entity $this$canFit, @NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)$this$canFit, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        return WorldExtensionsKt.canFit($this$canFit, BlockPosExtensionsKt.toVec3d(pos));
    }

    public static final boolean canFit(@NotNull Entity $this$canFit, @NotNull Vec3 vec) {
        Intrinsics.checkNotNullParameter((Object)$this$canFit, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)vec, (String)"vec");
        AABB box = $this$canFit.m_20191_().m_82383_(vec.m_82546_($this$canFit.m_20182_()));
        return $this$canFit.m_9236_().m_45772_(box);
    }

    @NotNull
    public static final Registry<Item> getItemRegistry(@NotNull Level $this$itemRegistry) {
        Intrinsics.checkNotNullParameter((Object)$this$itemRegistry, (String)"<this>");
        Registry registry = $this$itemRegistry.m_9598_().m_175515_(Registries.f_256913_);
        Intrinsics.checkNotNullExpressionValue((Object)registry, (String)"registryManager.get(RegistryKeys.ITEM)");
        return registry;
    }

    @NotNull
    public static final Registry<Biome> getBiomeRegistry(@NotNull Level $this$biomeRegistry) {
        Intrinsics.checkNotNullParameter((Object)$this$biomeRegistry, (String)"<this>");
        Registry registry = $this$biomeRegistry.m_9598_().m_175515_(Registries.f_256952_);
        Intrinsics.checkNotNullExpressionValue((Object)registry, (String)"registryManager.get(RegistryKeys.BIOME)");
        return registry;
    }

    @Nullable
    public static final TraceResult traceDownwards(@NotNull Vec3 $this$traceDownwards, @NotNull Level world, float maxDistance, float stepDistance) {
        Intrinsics.checkNotNullParameter((Object)$this$traceDownwards, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Vec3 startPos = new Vec3($this$traceDownwards.f_82479_, $this$traceDownwards.f_82480_, $this$traceDownwards.f_82481_);
        Vec3 direction = new Vec3(0.0, -1.0, 0.0);
        BlockPos lastBlockPos = Vec3ExtensionsKt.toBlockPos(startPos);
        for (float step = stepDistance; step <= maxDistance; step += stepDistance) {
            Vec3 location = startPos.m_82549_(direction.m_82490_((double)step));
            Intrinsics.checkNotNullExpressionValue((Object)location, (String)"location");
            BlockPos blockPos2 = Vec3ExtensionsKt.toBlockPos(location);
            if (Intrinsics.areEqual((Object)blockPos2, (Object)lastBlockPos)) continue;
            lastBlockPos = blockPos2;
            BlockState block = world.m_8055_(blockPos2);
            if (block.m_60795_()) continue;
            Direction dir = PlayerExtensionsKt.findDirectionForIntercept(startPos, location, blockPos2);
            return new TraceResult(location, blockPos2, dir);
        }
        return null;
    }

    public static /* synthetic */ TraceResult traceDownwards$default(Vec3 vec3, Level level, float f, float f2, int n, Object object) {
        if ((n & 2) != 0) {
            f = 10.0f;
        }
        if ((n & 4) != 0) {
            f2 = 0.5f;
        }
        return WorldExtensionsKt.traceDownwards(vec3, level, f, f2);
    }
}

