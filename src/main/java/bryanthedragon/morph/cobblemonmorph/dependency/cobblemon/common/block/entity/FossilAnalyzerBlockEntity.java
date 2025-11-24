/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.WorldlyContainer
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlockEntities;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fossil.Fossils;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.MultiblockStructure;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.builder.MultiblockStructureBuilder;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.FossilMultiblockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.multiblock.FossilMultiblockStructure;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u0001\u000fB\u001f\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\n\u001a\u00020\t\u0012\u0006\u0010\f\u001a\u00020\u000b\u00a2\u0006\u0004\b\r\u0010\u000eR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0010"}, d2={"Lcom/cobblemon/mod/common/block/entity/FossilAnalyzerBlockEntity;", "Lcom/cobblemon/mod/common/block/entity/FossilMultiblockEntity;", "Lcom/cobblemon/mod/common/block/entity/FossilAnalyzerBlockEntity$FossilAnalyzerInventory;", "inv", "Lcom/cobblemon/mod/common/block/entity/FossilAnalyzerBlockEntity$FossilAnalyzerInventory;", "getInv", "()Lcom/cobblemon/mod/common/block/entity/FossilAnalyzerBlockEntity$FossilAnalyzerInventory;", "Lnet/minecraft/core/BlockPos;", "pos", "Lnet/minecraft/world/level/block/state/BlockState;", "state", "Lcom/cobblemon/mod/common/api/multiblock/builder/MultiblockStructureBuilder;", "multiblockBuilder", "<init>", "(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lcom/cobblemon/mod/common/api/multiblock/builder/MultiblockStructureBuilder;)V", "FossilAnalyzerInventory", "common"})
public final class FossilAnalyzerBlockEntity
extends FossilMultiblockEntity {
    @NotNull
    private final FossilAnalyzerInventory inv;

    public FossilAnalyzerBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state, @NotNull MultiblockStructureBuilder multiblockBuilder) {
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)multiblockBuilder, (String)"multiblockBuilder");
        super(pos, state, multiblockBuilder, CobblemonBlockEntities.FOSSIL_ANALYZER);
        this.inv = new FossilAnalyzerInventory(this);
    }

    @NotNull
    public final FossilAnalyzerInventory getInv() {
        return this.inv;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0015\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010$\u001a\u00020#\u00a2\u0006\u0004\b(\u0010)J+\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\b\u0010\u0005\u001a\u0004\u0018\u00010\u00042\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ+\u0010\u000b\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\b\u0010\u0005\u001a\u0004\u0018\u00010\u00042\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0016\u00a2\u0006\u0004\b\u000b\u0010\nJ\u0019\u0010\u000e\u001a\u00020\b2\b\u0010\r\u001a\u0004\u0018\u00010\fH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u000f\u0010\u0011\u001a\u00020\u0010H\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0019\u0010\u0015\u001a\u00020\u00142\b\u0010\u0013\u001a\u0004\u0018\u00010\u0006H\u0016\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0017\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u000f\u0010\u0019\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\u000f\u0010\u001b\u001a\u00020\u0010H\u0016\u00a2\u0006\u0004\b\u001b\u0010\u0012J\u0017\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u001c\u0010\u0018J\u001f\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u001d\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u001c\u0010\u001eJ\u001f\u0010\u001f\u001a\u00020\u00102\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u001f\u0010 J\u000f\u0010!\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b!\u0010\"R\u0017\u0010$\u001a\u00020#8\u0006\u00a2\u0006\f\n\u0004\b$\u0010%\u001a\u0004\b&\u0010'\u00a8\u0006*"}, d2={"Lcom/cobblemon/mod/common/block/entity/FossilAnalyzerBlockEntity$FossilAnalyzerInventory;", "Lnet/minecraft/world/WorldlyContainer;", "", "slot", "Lnet/minecraft/world/item/ItemStack;", "stack", "Lnet/minecraft/core/Direction;", "dir", "", "canExtract", "(ILnet/minecraft/world/item/ItemStack;Lnet/minecraft/core/Direction;)Z", "canInsert", "Lnet/minecraft/world/entity/player/Player;", "player", "canPlayerUse", "(Lnet/minecraft/world/entity/player/Player;)Z", "", "clear", "()V", "side", "", "getAvailableSlots", "(Lnet/minecraft/core/Direction;)[I", "getStack", "(I)Lnet/minecraft/world/item/ItemStack;", "isEmpty", "()Z", "markDirty", "removeStack", "amount", "(II)Lnet/minecraft/world/item/ItemStack;", "setStack", "(ILnet/minecraft/world/item/ItemStack;)V", "size", "()I", "Lcom/cobblemon/mod/common/block/entity/FossilAnalyzerBlockEntity;", "analyzerEntity", "Lcom/cobblemon/mod/common/block/entity/FossilAnalyzerBlockEntity;", "getAnalyzerEntity", "()Lcom/cobblemon/mod/common/block/entity/FossilAnalyzerBlockEntity;", "<init>", "(Lcom/cobblemon/mod/common/block/entity/FossilAnalyzerBlockEntity;)V", "common"})
    @SourceDebugExtension(value={"SMAP\nFossilAnalyzerBlockEntity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FossilAnalyzerBlockEntity.kt\ncom/cobblemon/mod/common/block/entity/FossilAnalyzerBlockEntity$FossilAnalyzerInventory\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,109:1\n1#2:110\n*E\n"})
    public static final class FossilAnalyzerInventory
    implements WorldlyContainer {
        @NotNull
        private final FossilAnalyzerBlockEntity analyzerEntity;

        public FossilAnalyzerInventory(@NotNull FossilAnalyzerBlockEntity analyzerEntity) {
            Intrinsics.checkNotNullParameter((Object)((Object)analyzerEntity), (String)"analyzerEntity");
            this.analyzerEntity = analyzerEntity;
        }

        @NotNull
        public final FossilAnalyzerBlockEntity getAnalyzerEntity() {
            return this.analyzerEntity;
        }

        public void m_6211_() {
            if (this.analyzerEntity.getMultiblockStructure() != null && this.analyzerEntity.getMultiblockStructure() instanceof FossilMultiblockStructure) {
                MultiblockStructure multiblockStructure = this.analyzerEntity.getMultiblockStructure();
                Intrinsics.checkNotNull((Object)multiblockStructure, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.multiblock.FossilMultiblockStructure");
                FossilMultiblockStructure fossilMultiblockStructure = (FossilMultiblockStructure)multiblockStructure;
                fossilMultiblockStructure.getFossilInventory().clear();
            }
        }

        public int m_6643_() {
            if (this.analyzerEntity.getMultiblockStructure() != null) {
                return 3;
            }
            return 0;
        }

        public boolean m_7983_() {
            return false;
        }

        @NotNull
        public ItemStack m_8020_(int slot) {
            if (this.analyzerEntity.getMultiblockStructure() != null && this.analyzerEntity.getMultiblockStructure() instanceof FossilMultiblockStructure) {
                MultiblockStructure multiblockStructure = this.analyzerEntity.getMultiblockStructure();
                Intrinsics.checkNotNull((Object)multiblockStructure, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.multiblock.FossilMultiblockStructure");
                FossilMultiblockStructure fossilMultiblockStructure = (FossilMultiblockStructure)multiblockStructure;
                if (fossilMultiblockStructure.getFossilInventory().size() > slot) {
                    return fossilMultiblockStructure.getFossilInventory().get(slot);
                }
            }
            ItemStack itemStack = ItemStack.f_41583_;
            Intrinsics.checkNotNullExpressionValue((Object)itemStack, (String)"EMPTY");
            return itemStack;
        }

        @NotNull
        public ItemStack m_7407_(int slot, int amount) {
            return this.m_8016_(slot);
        }

        @NotNull
        public ItemStack m_8016_(int slot) {
            ItemStack itemStack = ItemStack.f_41583_;
            Intrinsics.checkNotNullExpressionValue((Object)itemStack, (String)"EMPTY");
            return itemStack;
        }

        public void m_6836_(int slot, @NotNull ItemStack stack) {
            block1: {
                Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
                if (this.analyzerEntity.getMultiblockStructure() == null || !(this.analyzerEntity.getMultiblockStructure() instanceof FossilMultiblockStructure)) break block1;
                MultiblockStructure multiblockStructure = this.analyzerEntity.getMultiblockStructure();
                Intrinsics.checkNotNull((Object)multiblockStructure, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.multiblock.FossilMultiblockStructure");
                FossilMultiblockStructure struct2 = (FossilMultiblockStructure)multiblockStructure;
                Level level = this.analyzerEntity.f_58857_;
                if (level != null) {
                    Level it = level;
                    boolean bl = false;
                    struct2.insertFossil(stack, it);
                }
            }
        }

        public void m_6596_() {
            block1: {
                if (this.analyzerEntity.f_58857_ == null) break block1;
                MultiblockStructure multiblockStructure = this.analyzerEntity.getMultiblockStructure();
                if (multiblockStructure != null) {
                    Level level = this.analyzerEntity.f_58857_;
                    Intrinsics.checkNotNull((Object)level);
                    multiblockStructure.markDirty(level);
                }
            }
        }

        public boolean m_6542_(@Nullable Player player) {
            return false;
        }

        @NotNull
        public int[] m_7071_(@Nullable Direction side) {
            if (this.analyzerEntity.getMultiblockStructure() != null && this.analyzerEntity.getMultiblockStructure() instanceof FossilMultiblockStructure) {
                int[] nArray = new int[]{0, 1, 2};
                return nArray;
            }
            return new int[0];
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public boolean m_7155_(int slot, @Nullable ItemStack stack, @Nullable Direction dir) {
            if (!(this.analyzerEntity.getMultiblockStructure() instanceof FossilMultiblockStructure)) return false;
            MultiblockStructure multiblockStructure = this.analyzerEntity.getMultiblockStructure();
            Intrinsics.checkNotNull((Object)multiblockStructure, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.multiblock.FossilMultiblockStructure");
            FossilMultiblockStructure structure = (FossilMultiblockStructure)multiblockStructure;
            ItemStack itemStack = stack;
            if (itemStack == null) return false;
            ItemStack it = itemStack;
            boolean bl = false;
            if (!Fossils.INSTANCE.isFossilIngredient(it)) return false;
            boolean bl2 = true;
            if (!bl2) return false;
            if (structure.getFossilInventory().size() >= Cobblemon.INSTANCE.getConfig().getMaxInsertedFossilItems()) return false;
            if (structure.isRunning()) return false;
            if (structure.getResultingFossil() != null) return false;
            Object[] objectArray = new ItemStack[]{stack};
            if (Fossils.INSTANCE.getSubFossilByItemStacks(CollectionsKt.plus((Collection)structure.getFossilInventory(), (Iterable)CollectionsKt.mutableListOf((Object[])objectArray))) == null) return false;
            return true;
        }

        public boolean m_7157_(int slot, @Nullable ItemStack stack, @Nullable Direction dir) {
            return false;
        }
    }
}

