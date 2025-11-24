/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Position
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.WorldSlice;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.AreaSpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.AreaSpawningContextCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.AreaSpawningInput;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.SpawningContextInput;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence.SpawningInfluence;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.Spawner;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.BlockPosExtensionsKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J7\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u00042\u0006\u0010\u0003\u001a\u00020\u00022\u0010\u0010\u0006\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00050\u00042\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\n\u0010\u000b\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/api/spawning/context/AreaContextResolver;", "", "Lcom/cobblemon/mod/common/api/spawning/spawner/Spawner;", "spawner", "", "Lcom/cobblemon/mod/common/api/spawning/context/calculators/AreaSpawningContextCalculator;", "contextCalculators", "Lcom/cobblemon/mod/common/api/spawning/WorldSlice;", "slice", "Lcom/cobblemon/mod/common/api/spawning/context/AreaSpawningContext;", "resolve", "(Lcom/cobblemon/mod/common/api/spawning/spawner/Spawner;Ljava/util/List;Lcom/cobblemon/mod/common/api/spawning/WorldSlice;)Ljava/util/List;", "common"})
public interface AreaContextResolver {
    @NotNull
    public List<AreaSpawningContext> resolve(@NotNull Spawner var1, @NotNull List<? extends AreaSpawningContextCalculator<?>> var2, @NotNull WorldSlice var3);

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    @SourceDebugExtension(value={"SMAP\nAreaContextResolver.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AreaContextResolver.kt\ncom/cobblemon/mod/common/api/spawning/context/AreaContextResolver$DefaultImpls\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,79:1\n2624#2,3:80\n288#2:83\n2624#2,3:84\n289#2:87\n*S KotlinDebug\n*F\n+ 1 AreaContextResolver.kt\ncom/cobblemon/mod/common/api/spawning/context/AreaContextResolver$DefaultImpls\n*L\n51#1:80,3\n53#1:83\n53#1:84,3\n53#1:87\n*E\n"})
    public static final class DefaultImpls {
        /*
         * Unable to fully structure code
         */
        @NotNull
        public static List<AreaSpawningContext> resolve(@NotNull AreaContextResolver $this, @NotNull Spawner spawner, @NotNull List<? extends AreaSpawningContextCalculator<?>> contextCalculators, @NotNull WorldSlice slice) {
            Intrinsics.checkNotNullParameter((Object)spawner, (String)"spawner");
            Intrinsics.checkNotNullParameter(contextCalculators, (String)"contextCalculators");
            Intrinsics.checkNotNullParameter((Object)slice, (String)"slice");
            pos = new BlockPos.MutableBlockPos(1, 2, 3);
            input = new AreaSpawningInput(spawner, (BlockPos)pos, slice);
            contexts = new ArrayList<E>();
            y = slice.getBaseY();
            z = slice.getBaseZ();
            for (x = slice.getBaseX(); x < slice.getBaseX() + slice.getLength(); ++x) {
                while (y < slice.getBaseY() + slice.getHeight()) {
                    while (z < slice.getBaseZ() + slice.getWidth()) {
                        block16: {
                            block15: {
                                block13: {
                                    pos.m_122178_(x, y, z);
                                    vec = BlockPosExtensionsKt.toVec3d((BlockPos)pos);
                                    $this$none$iv = slice.getNearbyEntityPositions();
                                    $i$f$none = false;
                                    if ($this$none$iv instanceof Collection && ((Collection)$this$none$iv).isEmpty()) {
                                        v0 = true;
                                    } else {
                                        for (T element$iv : $this$none$iv) {
                                            it = (Vec3)element$iv;
                                            $i$a$-none-AreaContextResolver$resolve$1 = false;
                                            if (!(it.m_82509_((Position)vec, Cobblemon.INSTANCE.getConfig().getMinimumDistanceBetweenEntities()) != false && Intrinsics.areEqual((Object)it, (Object)slice.getCause().getEntity()) == false)) continue;
                                            v0 = false;
                                            break block13;
                                        }
                                        v0 = true;
                                    }
                                }
                                if (!v0) break block16;
                                $i$f$none = contextCalculators;
                                $i$f$firstOrNull = false;
                                for (T element$iv : $this$firstOrNull$iv) {
                                    block14: {
                                        calc = (AreaSpawningContextCalculator)element$iv;
                                        $i$a$-firstOrNull-AreaContextResolver$resolve$fittedContextCalculator$1 = false;
                                        if (!calc.fits(input)) ** GOTO lbl-1000
                                        $this$none$iv = input.getSpawner().getInfluences();
                                        $i$f$none = false;
                                        if ($this$none$iv instanceof Collection && ((Collection)$this$none$iv).isEmpty()) {
                                            v1 = true;
                                        } else {
                                            for (T element$iv : $this$none$iv) {
                                                it = (SpawningInfluence)element$iv;
                                                $i$a$-none-AreaContextResolver$resolve$fittedContextCalculator$1$1 = false;
                                                if (!(it.isAllowedPosition(input.getWorld(), input.getPosition(), calc) == false)) continue;
                                                v1 = false;
                                                break block14;
                                            }
                                            v1 = true;
                                        }
                                    }
                                    if (v1) {
                                        v2 = true;
                                    } else lbl-1000:
                                    // 2 sources

                                    {
                                        v2 = false;
                                    }
                                    if (!v2) continue;
                                    v3 = element$iv;
                                    break block15;
                                }
                                v3 = null;
                            }
                            fittedContextCalculator = v3;
                            if (fittedContextCalculator != null && (context = (AreaSpawningContext)fittedContextCalculator.calculate((SpawningContextInput)input)) != null) {
                                contexts.add(context);
                                pos = new BlockPos.MutableBlockPos(1, 2, 3);
                                input.setPosition((BlockPos)pos);
                            }
                        }
                        ++z;
                    }
                    ++y;
                    z = slice.getBaseZ();
                }
                y = slice.getBaseY();
                z = slice.getBaseZ();
            }
            return contexts;
        }
    }
}

