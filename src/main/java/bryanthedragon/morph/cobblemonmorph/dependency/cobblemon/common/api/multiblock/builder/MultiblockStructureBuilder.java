/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.builder;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.condition.MultiblockCondition;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\b\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\b\u0010\tR\u0014\u0010\r\u001a\u00020\n8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/api/multiblock/builder/MultiblockStructureBuilder;", "", "Lnet/minecraft/server/level/ServerLevel;", "world", "", "form", "(Lnet/minecraft/server/level/ServerLevel;)V", "", "validate", "(Lnet/minecraft/server/level/ServerLevel;)Z", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "getBoundingBox", "()Lnet/minecraft/world/phys/shapes/VoxelShape;", "boundingBox", "", "Lcom/cobblemon/mod/common/api/multiblock/condition/MultiblockCondition;", "getConditions", "()Ljava/util/List;", "conditions", "common"})
public interface MultiblockStructureBuilder {
    @NotNull
    public VoxelShape getBoundingBox();

    @NotNull
    public List<MultiblockCondition> getConditions();

    public boolean validate(@NotNull ServerLevel var1);

    public void form(@NotNull ServerLevel var1);

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    @SourceDebugExtension(value={"SMAP\nMultiblockStructureBuilder.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MultiblockStructureBuilder.kt\ncom/cobblemon/mod/common/api/multiblock/builder/MultiblockStructureBuilder$DefaultImpls\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,40:1\n1855#2,2:41\n*S KotlinDebug\n*F\n+ 1 MultiblockStructureBuilder.kt\ncom/cobblemon/mod/common/api/multiblock/builder/MultiblockStructureBuilder$DefaultImpls\n*L\n29#1:41,2\n*E\n"})
    public static final class DefaultImpls {
        public static boolean validate(@NotNull MultiblockStructureBuilder $this, @NotNull ServerLevel world) {
            Intrinsics.checkNotNullParameter((Object)world, (String)"world");
            Iterable $this$forEach$iv = $this.getConditions();
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                MultiblockCondition it = (MultiblockCondition)element$iv;
                boolean bl = false;
                if (it.test(world, $this.getBoundingBox())) continue;
                return false;
            }
            $this.form(world);
            return true;
        }
    }
}

