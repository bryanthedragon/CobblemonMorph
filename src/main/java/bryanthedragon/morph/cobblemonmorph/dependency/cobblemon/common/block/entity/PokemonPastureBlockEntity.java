/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.NbtUtils
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.tags.FluidTags
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityTicker
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.entity.EntityTypeTest
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlockEntities;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlocks;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.CobblemonCriteria;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pasture.PastureLink;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pasture.PastureLinkManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingFunctionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStoreManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.PastureBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.PokemonPastureBlockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture.ClosePasturePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture.OpenPasturePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture.PokemonPasturedPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.BlockPosExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 [2\u00020\u0001:\u0002[\\B\u0017\u0012\u0006\u0010\u0011\u001a\u00020\u0010\u0012\u0006\u0010X\u001a\u00020W\u00a2\u0006\u0004\bY\u0010ZJ%\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\u0004\b\t\u0010\nJ\r\u0010\f\u001a\u00020\u000b\u00a2\u0006\u0004\b\f\u0010\rJ)\u0010\u0014\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u00102\b\b\u0002\u0010\u0013\u001a\u00020\u0012H\u0002\u00a2\u0006\u0004\b\u0014\u0010\u0015J\r\u0010\u0016\u001a\u00020\u0006\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0017\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u0002H\u0002\u00a2\u0006\u0004\b\u0018\u0010\u0019J%\u0010\u001c\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u001a\u00a2\u0006\u0004\b\u001c\u0010\u001dJ/\u0010 \u001a\u0004\u0018\u00010\u00102\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u001f\u001a\u00020\u001e\u00a2\u0006\u0004\b \u0010!J\r\u0010\"\u001a\u00020\u000b\u00a2\u0006\u0004\b\"\u0010\rJ\u0017\u0010%\u001a\u00020\u000b2\u0006\u0010$\u001a\u00020#H\u0016\u00a2\u0006\u0004\b%\u0010&J\u001b\u0010*\u001a\b\u0012\u0004\u0012\u00020'0)2\u0006\u0010(\u001a\u00020'\u00a2\u0006\u0004\b*\u0010+J\u0015\u0010-\u001a\u00020\u000b2\u0006\u0010,\u001a\u00020'\u00a2\u0006\u0004\b-\u0010.J%\u00101\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u00100\u001a\u00020/\u00a2\u0006\u0004\b1\u00102J\u0017\u00104\u001a\u00020\u000b2\u0006\u00103\u001a\u00020\bH\u0002\u00a2\u0006\u0004\b4\u00105J\u0017\u00106\u001a\u00020\u000b2\u0006\u0010$\u001a\u00020#H\u0014\u00a2\u0006\u0004\b6\u0010&R\"\u00107\u001a\u00020\u00108\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b7\u00108\u001a\u0004\b9\u0010:\"\u0004\b;\u0010<R\"\u0010=\u001a\u00020\u00108\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b=\u00108\u001a\u0004\b>\u0010:\"\u0004\b?\u0010<R$\u0010@\u001a\u0004\u0018\u00010'8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b@\u0010A\u001a\u0004\bB\u0010C\"\u0004\bD\u0010.R\"\u0010F\u001a\u00020E8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bF\u0010G\u001a\u0004\bH\u0010I\"\u0004\bJ\u0010KR\u001d\u0010N\u001a\b\u0012\u0004\u0012\u00020M0L8\u0006\u00a2\u0006\f\n\u0004\bN\u0010O\u001a\u0004\bP\u0010QR\"\u0010R\u001a\u00020\u00068\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bR\u0010S\u001a\u0004\bT\u0010\u0017\"\u0004\bU\u0010V\u00a8\u0006]"}, d2={"Lcom/cobblemon/mod/common/block/entity/PokemonPastureBlockEntity;", "Lnet/minecraft/world/level/block/entity/BlockEntity;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "", "maxPerPlayer", "", "canAddPokemon", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/pokemon/Pokemon;I)Z", "", "checkPokemon", "()V", "Lnet/minecraft/world/level/Level;", "world", "Lnet/minecraft/core/BlockPos;", "pos", "", "range", "getInRangeViewerCount", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;D)I", "getMaxTethered", "()I", "isPlayerViewing", "(Lnet/minecraft/server/level/ServerPlayer;)Z", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "entity", "isSafeFloor", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)Z", "Lnet/minecraft/world/phys/AABB;", "box", "makeSuitableY", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;Lnet/minecraft/world/phys/AABB;)Lnet/minecraft/core/BlockPos;", "onBroken", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "readNbt", "(Lnet/minecraft/nbt/CompoundTag;)V", "Ljava/util/UUID;", "playerId", "", "releaseAllPokemon", "(Ljava/util/UUID;)Ljava/util/List;", "pokemonId", "releasePokemon", "(Ljava/util/UUID;)V", "Lnet/minecraft/core/Direction;", "directionToBehind", "tether", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/pokemon/Pokemon;Lnet/minecraft/core/Direction;)Z", "on", "togglePastureOn", "(Z)V", "writeNbt", "maxRoamPos", "Lnet/minecraft/core/BlockPos;", "getMaxRoamPos", "()Lnet/minecraft/core/BlockPos;", "setMaxRoamPos", "(Lnet/minecraft/core/BlockPos;)V", "minRoamPos", "getMinRoamPos", "setMinRoamPos", "ownerId", "Ljava/util/UUID;", "getOwnerId", "()Ljava/util/UUID;", "setOwnerId", "", "ownerName", "Ljava/lang/String;", "getOwnerName", "()Ljava/lang/String;", "setOwnerName", "(Ljava/lang/String;)V", "", "Lcom/cobblemon/mod/common/block/entity/PokemonPastureBlockEntity$Tethering;", "tetheredPokemon", "Ljava/util/List;", "getTetheredPokemon", "()Ljava/util/List;", "ticksUntilCheck", "I", "getTicksUntilCheck", "setTicksUntilCheck", "(I)V", "Lnet/minecraft/world/level/block/state/BlockState;", "state", "<init>", "(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V", "Companion", "Tethering", "common"})
@SourceDebugExtension(value={"SMAP\nPokemonPastureBlockEntity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonPastureBlockEntity.kt\ncom/cobblemon/mod/common/block/entity/PokemonPastureBlockEntity\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,334:1\n1774#2,4:335\n1855#2,2:340\n1855#2,2:342\n1855#2,2:344\n766#2:346\n857#2,2:347\n1855#2,2:349\n1#3:339\n*S KotlinDebug\n*F\n+ 1 PokemonPastureBlockEntity.kt\ncom/cobblemon/mod/common/block/entity/PokemonPastureBlockEntity\n*L\n108#1:335,4\n242#1:340,2\n250#1:342,2\n257#1:344,2\n271#1:346\n271#1:347,2\n271#1:349,2\n*E\n"})
public final class PokemonPastureBlockEntity
extends BlockEntity {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private int ticksUntilCheck;
    @NotNull
    private final List<Tethering> tetheredPokemon;
    @NotNull
    private BlockPos minRoamPos;
    @NotNull
    private BlockPos maxRoamPos;
    @Nullable
    private UUID ownerId;
    @NotNull
    private String ownerName;
    @NotNull
    private static final BlockEntityTicker<PokemonPastureBlockEntity> TICKER = PokemonPastureBlockEntity::TICKER$lambda$11;

    public PokemonPastureBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        super(CobblemonBlockEntities.PASTURE, pos, state);
        this.ticksUntilCheck = Cobblemon.INSTANCE.getConfig().getPastureBlockUpdateTicks();
        this.tetheredPokemon = new ArrayList();
        this.ownerName = "";
        int radius = Cobblemon.INSTANCE.getConfig().getPastureMaxWanderDistance();
        BlockPos blockPos2 = pos.m_121996_(new Vec3i(radius, radius, radius));
        Intrinsics.checkNotNullExpressionValue((Object)blockPos2, (String)"pos.subtract(Vec3i(radius, radius, radius))");
        this.minRoamPos = blockPos2;
        BlockPos blockPos3 = pos.m_121955_(new Vec3i(radius, radius, radius));
        Intrinsics.checkNotNullExpressionValue((Object)blockPos3, (String)"pos.add(Vec3i(radius, radius, radius))");
        this.maxRoamPos = blockPos3;
    }

    public final int getTicksUntilCheck() {
        return this.ticksUntilCheck;
    }

    public final void setTicksUntilCheck(int n) {
        this.ticksUntilCheck = n;
    }

    @NotNull
    public final List<Tethering> getTetheredPokemon() {
        return this.tetheredPokemon;
    }

    @NotNull
    public final BlockPos getMinRoamPos() {
        return this.minRoamPos;
    }

    public final void setMinRoamPos(@NotNull BlockPos blockPos2) {
        Intrinsics.checkNotNullParameter((Object)blockPos2, (String)"<set-?>");
        this.minRoamPos = blockPos2;
    }

    @NotNull
    public final BlockPos getMaxRoamPos() {
        return this.maxRoamPos;
    }

    public final void setMaxRoamPos(@NotNull BlockPos blockPos2) {
        Intrinsics.checkNotNullParameter((Object)blockPos2, (String)"<set-?>");
        this.maxRoamPos = blockPos2;
    }

    @Nullable
    public final UUID getOwnerId() {
        return this.ownerId;
    }

    public final void setOwnerId(@Nullable UUID uUID) {
        this.ownerId = uUID;
    }

    @NotNull
    public final String getOwnerName() {
        return this.ownerName;
    }

    public final void setOwnerName(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.ownerName = string;
    }

    public final int getMaxTethered() {
        return Cobblemon.INSTANCE.getConfig().getDefaultPasturedPokemonLimit();
    }

    public final boolean canAddPokemon(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, int maxPerPlayer) {
        int forThisPlayer;
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Iterable $this$count$iv = this.tetheredPokemon;
        boolean $i$f$count = false;
        if ($this$count$iv instanceof Collection && ((Collection)$this$count$iv).isEmpty()) {
            v0 = 0;
        } else {
            int count$iv = 0;
            for (Object element$iv : $this$count$iv) {
                Tethering it = (Tethering)element$iv;
                boolean bl = false;
                if (!Intrinsics.areEqual((Object)it.getPlayerId(), (Object)player.m_20148_()) || ++count$iv >= 0) continue;
                CollectionsKt.throwCountOverflow();
            }
            v0 = forThisPlayer = count$iv;
        }
        if (forThisPlayer >= maxPerPlayer || this.tetheredPokemon.size() >= this.getMaxTethered() || pokemon.isFainted()) {
            return false;
        }
        double radius = Cobblemon.INSTANCE.getConfig().getPastureMaxWanderDistance();
        BlockPos blockPos2 = this.f_58858_;
        Intrinsics.checkNotNullExpressionValue((Object)blockPos2, (String)"pos");
        Vec3 bottom = BlockPosExtensionsKt.toVec3d(blockPos2).m_82542_(1.0, 0.0, 1.0);
        List list = player.m_9236_().m_6443_(PokemonEntity.class, AABB.m_165882_((Vec3)bottom, (double)radius, (double)99999.0, (double)radius), arg_0 -> PokemonPastureBlockEntity.canAddPokemon$lambda$1(canAddPokemon.pokemonWithinPastureWander.1.INSTANCE, arg_0));
        Intrinsics.checkNotNullExpressionValue((Object)list, (String)"player.world.getEntities\u20269999.0, radius)) { true }");
        int pokemonWithinPastureWander2 = ((Collection)list).size();
        double chunkDiameter = radius / (double)16 * (double)2;
        if ((double)pokemonWithinPastureWander2 >= (double)Cobblemon.INSTANCE.getConfig().getPastureMaxPerChunk() * chunkDiameter * chunkDiameter) {
            CobblemonNetwork.INSTANCE.sendPacket(player, new ClosePasturePacket());
            MutableComponent mutableComponent = LocalizationUtilsKt.lang("pasture.too_many_nearby", new Object[0]);
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"pasture.too_many_nearby\")");
            player.m_5661_((Component)TextKt.red(mutableComponent), true);
            return false;
        }
        return true;
    }

    public final boolean tether(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull Direction directionToBehind) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)directionToBehind, (String)"directionToBehind");
        Level level = this.f_58857_;
        if (level == null) {
            return false;
        }
        Level world = level;
        PokemonEntity entity2 = new PokemonEntity(world, pokemon, null, 4, null);
        entity2.m_6210_();
        double width = entity2.m_20191_().m_82362_();
        BlockPos idealPlace = this.f_58858_.m_121955_(directionToBehind.m_122436_().m_142393_((int)Math.ceil(width) + 1));
        AABB box = entity2.m_6972_(Pose.STANDING).m_20393_(idealPlace.m_252807_().m_82492_(0.0, 0.5, 0.0));
        for (int i = 0; i < 6; ++i) {
            box = box.m_82386_((double)directionToBehind.m_122436_().m_123341_(), 0.0, (double)directionToBehind.m_122436_().m_123343_());
            BlockPos blockPos2 = idealPlace.m_121955_(directionToBehind.m_122436_());
            Intrinsics.checkNotNullExpressionValue((Object)blockPos2, (String)"idealPlace.add(directionToBehind.vector)");
            AABB aABB = box;
            Intrinsics.checkNotNullExpressionValue((Object)aABB, (String)"box");
            BlockPos fixedPosition = this.makeSuitableY(world, blockPos2, entity2, aABB);
            if (fixedPosition == null) continue;
            entity2.m_146884_(fixedPosition.m_252807_().m_82492_(0.0, 0.5, 0.0));
            PokemonStoreManager pokemonStoreManager = Cobblemon.INSTANCE.getStorage();
            UUID uUID = player.m_20148_();
            Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"player.uuid");
            PCStore pc = pokemonStoreManager.getPC(uUID);
            entity2.setBeamMode(2);
            SchedulingFunctionsKt.afterOnServer$default(0, 1.5f, (Function0)new Function0<Unit>(entity2){
                final /* synthetic */ PokemonEntity $entity;
                {
                    this.$entity = $entity;
                    super(0);
                }

                public final void invoke() {
                    this.$entity.setBeamMode(0);
                }
            }, 1, null);
            if (world.m_7967_((Entity)entity2)) {
                UUID uUID2 = player.m_20148_();
                Intrinsics.checkNotNullExpressionValue((Object)uUID2, (String)"player.uuid");
                String string = player.m_36316_().getName();
                Intrinsics.checkNotNullExpressionValue((Object)string, (String)"player.gameProfile.name");
                UUID uUID3 = UUID.randomUUID();
                Intrinsics.checkNotNullExpressionValue((Object)uUID3, (String)"randomUUID()");
                UUID uUID4 = pokemon.getUuid();
                Intrinsics.checkNotNullExpressionValue((Object)uUID4, (String)"pokemon.uuid");
                Tethering tethering = new Tethering(this.minRoamPos, this.maxRoamPos, uUID2, string, uUID3, uUID4, pc.getUuid(), entity2.m_19879_());
                pokemon.setTetheringId(tethering.getTetheringId());
                this.tetheredPokemon.add(tethering);
                entity2.setTethering(tethering);
                OpenPasturePacket.PasturePokemonDataDTO pasturePokemonDataDTO = tethering.toDTO(player);
                if (pasturePokemonDataDTO != null) {
                    OpenPasturePacket.PasturePokemonDataDTO it = pasturePokemonDataDTO;
                    boolean bl = false;
                    CobblemonNetwork.INSTANCE.sendPacket(player, new PokemonPasturedPacket(it));
                }
                this.m_6596_();
                CobblemonCriteria.INSTANCE.getPASTURE_USE().trigger(player, pokemon);
                return true;
            }
            Cobblemon.INSTANCE.getLOGGER().warn("Couldn't spawn pastured Pok\u00e9mon for some reason");
            break;
        }
        return false;
    }

    private final void togglePastureOn(boolean on) {
        Block block = this.m_58900_().m_60734_();
        Intrinsics.checkNotNull((Object)block, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.PastureBlock");
        PastureBlock pastureBlock = (PastureBlock)block;
        if (this.f_58857_ != null) {
            Level level = this.f_58857_;
            Intrinsics.checkNotNull((Object)level);
            if (!level.f_46443_) {
                Level level2 = this.f_58857_;
                Intrinsics.checkNotNull((Object)level2);
                Level world = level2;
                BlockState blockState = this.m_58900_();
                Intrinsics.checkNotNullExpressionValue((Object)blockState, (String)"cachedState");
                BlockPos blockPos2 = this.f_58858_;
                Intrinsics.checkNotNullExpressionValue((Object)blockPos2, (String)"pos");
                BlockPos posBottom = pastureBlock.getBasePosition(blockState, blockPos2);
                BlockState stateBottom = world.m_8055_(posBottom);
                Intrinsics.checkNotNullExpressionValue((Object)stateBottom, (String)"stateBottom");
                BlockPos posTop = pastureBlock.getPositionOfOtherPart(stateBottom, posBottom);
                BlockState stateTop = world.m_8055_(posTop);
                try {
                    if (!Intrinsics.areEqual((Object)stateBottom.m_61143_((Property)PastureBlock.Companion.getON()), (Object)on)) {
                        world.m_46597_(posTop, (BlockState)stateTop.m_61124_((Property)PastureBlock.Companion.getON(), (Comparable)Boolean.valueOf(on)));
                        world.m_46597_(posBottom, (BlockState)stateBottom.m_61124_((Property)PastureBlock.Companion.getON(), (Comparable)Boolean.valueOf(on)));
                    }
                }
                catch (IllegalArgumentException exception) {
                    if (world.m_8055_(this.f_58858_.m_7494_()).m_60734_() instanceof PastureBlock) {
                        world.m_46597_(this.f_58858_.m_7494_(), Blocks.f_50016_.m_49966_());
                    } else {
                        world.m_46597_(this.f_58858_.m_7495_(), Blocks.f_50016_.m_49966_());
                    }
                    world.m_46597_(this.f_58858_, Blocks.f_50016_.m_49966_());
                    world.m_7967_((Entity)new ItemEntity(world, (double)this.f_58858_.m_123341_() + 0.5, (double)this.f_58858_.m_123342_() + 1.0, (double)this.f_58858_.m_123343_() + 0.5, new ItemStack((ItemLike)CobblemonBlocks.PASTURE)));
                }
            }
        }
    }

    public final boolean isSafeFloor(@NotNull Level world, @NotNull BlockPos pos, @NotNull PokemonEntity entity2) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        BlockState state = world.m_8055_(pos);
        return state.m_60795_() ? false : (state.m_60634_((BlockGetter)world, pos, (Entity)entity2) || state.m_60638_((BlockGetter)world, pos, (Entity)entity2, Direction.DOWN) ? true : ((entity2.getBehaviour().getMoving().getSwim().getCanWalkOnWater() || entity2.getBehaviour().getMoving().getSwim().getCanSwimInWater()) && state.m_60819_().m_205070_(FluidTags.f_13131_) ? true : (entity2.getBehaviour().getMoving().getSwim().getCanWalkOnLava() || entity2.getBehaviour().getMoving().getSwim().getCanSwimInLava()) && state.m_60819_().m_205070_(FluidTags.f_13132_)));
    }

    @Nullable
    public final BlockPos makeSuitableY(@NotNull Level world, @NotNull BlockPos pos, @NotNull PokemonEntity entity2, @NotNull AABB box) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        Intrinsics.checkNotNullParameter((Object)box, (String)"box");
        if (world.m_186437_((Entity)entity2, box)) {
            for (int i = 1; i < 16; ++i) {
                AABB newBox = box.m_82386_(0.5, (double)i, 0.5);
                if (world.m_186437_((Entity)entity2, newBox)) continue;
                BlockPos blockPos2 = pos.m_7918_(0, i - 1, 0);
                Intrinsics.checkNotNullExpressionValue((Object)blockPos2, (String)"pos.add(0, i - 1, 0)");
                if (!this.isSafeFloor(world, blockPos2, entity2)) continue;
                return pos.m_7918_(0, i, 0);
            }
        } else {
            for (int i = 1; i < 16; ++i) {
                AABB newBox = box.m_82386_(0.5, -((double)i), 0.5);
                if (!world.m_186437_((Entity)entity2, newBox)) continue;
                BlockPos blockPos3 = pos.m_7918_(0, -i, 0);
                Intrinsics.checkNotNullExpressionValue((Object)blockPos3, (String)"pos.add(0, -i, 0)");
                if (!this.isSafeFloor(world, blockPos3, entity2)) continue;
                return pos.m_7918_(0, -i + 1, 0);
            }
        }
        return null;
    }

    public final void checkPokemon() {
        List deadLinks = new ArrayList();
        Iterable $this$forEach$iv = this.tetheredPokemon;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Tethering it = (Tethering)element$iv;
            boolean bl = false;
            Pokemon pokemon = it.getPokemon();
            if (pokemon == null) {
                deadLinks.add(it.getPokemonId());
                continue;
            }
            if (pokemon.getTetheringId() != null && Intrinsics.areEqual((Object)pokemon.getTetheringId(), (Object)it.getTetheringId())) continue;
            deadLinks.add(it.getPokemonId());
        }
        $this$forEach$iv = deadLinks;
        $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            UUID p0 = (UUID)element$iv;
            boolean bl = false;
            this.releasePokemon(p0);
        }
        this.ticksUntilCheck = Cobblemon.INSTANCE.getConfig().getPastureBlockUpdateTicks();
        this.m_6596_();
    }

    public final void onBroken() {
        if (this.f_58857_ instanceof ServerLevel) {
            Iterable $this$forEach$iv = CollectionsKt.toList((Iterable)this.tetheredPokemon);
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                Tethering it = (Tethering)element$iv;
                boolean bl = false;
                this.releasePokemon(it.getPokemonId());
            }
            Level level = this.f_58857_;
            Intrinsics.checkNotNull((Object)level, (String)"null cannot be cast to non-null type net.minecraft.server.world.ServerWorld");
            ServerLevel serverLevel = (ServerLevel)level;
            BlockPos blockPos2 = this.f_58858_;
            Intrinsics.checkNotNullExpressionValue((Object)blockPos2, (String)"pos");
            PastureLinkManager.INSTANCE.removeAt(serverLevel, blockPos2);
        }
    }

    public final void releasePokemon(@NotNull UUID pokemonId) {
        Object v0;
        block3: {
            Intrinsics.checkNotNullParameter((Object)pokemonId, (String)"pokemonId");
            Iterable iterable = this.tetheredPokemon;
            for (Object t : iterable) {
                Tethering it = (Tethering)t;
                boolean bl = false;
                if (!Intrinsics.areEqual((Object)it.getPokemonId(), (Object)pokemonId)) continue;
                v0 = t;
                break block3;
            }
            v0 = null;
        }
        Tethering tethering = v0;
        if (tethering == null) {
            return;
        }
        Tethering tethering2 = tethering;
        Pokemon pokemon = tethering2.getPokemon();
        if (pokemon != null) {
            pokemon.setTetheringId(null);
        }
        this.tetheredPokemon.remove(tethering2);
        this.m_6596_();
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final List<UUID> releaseAllPokemon(@NotNull UUID playerId) {
        void $this$filterTo$iv$iv;
        Intrinsics.checkNotNullParameter((Object)playerId, (String)"playerId");
        List unpastured = new ArrayList();
        Iterable $this$filter$iv = this.tetheredPokemon;
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            Tethering it = (Tethering)element$iv$iv;
            boolean bl = false;
            if (!Intrinsics.areEqual((Object)it.getPlayerId(), (Object)playerId)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        Iterable $this$forEach$iv = (List)destination$iv$iv;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Tethering it = (Tethering)element$iv;
            boolean bl = false;
            Pokemon pokemon = it.getPokemon();
            if (pokemon != null) {
                pokemon.setTetheringId(null);
            }
            this.tetheredPokemon.remove(it);
            unpastured.add(it.getPokemonId());
        }
        this.m_6596_();
        return unpastured;
    }

    private final int getInRangeViewerCount(Level world, BlockPos pos, double range) {
        AABB box = new AABB((double)pos.m_123341_() - range, (double)pos.m_123342_() - range, (double)pos.m_123343_() - range, (double)(pos.m_123341_() + 1) + range, (double)(pos.m_123342_() + 1) + range, (double)(pos.m_123343_() + 1) + range);
        return world.m_142425_(EntityTypeTest.m_156916_(ServerPlayer.class), box, arg_0 -> PokemonPastureBlockEntity.getInRangeViewerCount$lambda$8((Function1)new Function1<ServerPlayer, Boolean>((Object)this){

            @NotNull
            public final Boolean invoke(@NotNull ServerPlayer p0) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                return PokemonPastureBlockEntity.access$isPlayerViewing((PokemonPastureBlockEntity)((Object)this.receiver), p0);
            }
        }, arg_0)).size();
    }

    static /* synthetic */ int getInRangeViewerCount$default(PokemonPastureBlockEntity pokemonPastureBlockEntity, Level level, BlockPos blockPos2, double d, int n, Object object) {
        if ((n & 4) != 0) {
            d = 5.0;
        }
        return pokemonPastureBlockEntity.getInRangeViewerCount(level, blockPos2, d);
    }

    private final boolean isPlayerViewing(ServerPlayer player) {
        PastureLink pastureLink = PastureLinkManager.INSTANCE.getLinkByPlayer(player);
        return pastureLink != null && Intrinsics.areEqual((Object)pastureLink.getPos(), (Object)this.f_58858_) && Intrinsics.areEqual((Object)pastureLink.getDimension(), (Object)player.m_9236_().m_220362_().m_135782_());
    }

    /*
     * WARNING - void declaration
     */
    public void m_142466_(@NotNull CompoundTag nbt) {
        void it;
        String string;
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        super.m_142466_(nbt);
        ListTag list = nbt.m_128437_("TetherPokemon", 10);
        this.ownerId = nbt.m_128403_("TetherOwnerId") ? nbt.m_128342_("TetherOwnerId") : null;
        String string2 = string = nbt.m_128461_("TetherOwnerName");
        PokemonPastureBlockEntity pokemonPastureBlockEntity = this;
        boolean bl = false;
        Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
        boolean bl2 = ((CharSequence)it).length() > 0;
        String string3 = bl2 ? string : null;
        if (string3 == null) {
            string3 = "";
        }
        pokemonPastureBlockEntity.ownerName = string3;
        for (Tag tetheringNBT : list) {
            Intrinsics.checkNotNull((Object)tetheringNBT, (String)"null cannot be cast to non-null type net.minecraft.nbt.NbtCompound");
            CompoundTag cfr_ignored_0 = (CompoundTag)tetheringNBT;
            UUID tetheringId = ((CompoundTag)tetheringNBT).m_128342_("TetheringId");
            UUID pokemonId = ((CompoundTag)tetheringNBT).m_128342_("UUID");
            UUID pcId = ((CompoundTag)tetheringNBT).m_128342_("PCId");
            UUID playerId = ((CompoundTag)tetheringNBT).m_128342_("PlayerId");
            int entityId = ((CompoundTag)tetheringNBT).m_128451_("EntityId");
            Intrinsics.checkNotNullExpressionValue((Object)playerId, (String)"playerId");
            Intrinsics.checkNotNullExpressionValue((Object)tetheringId, (String)"tetheringId");
            Intrinsics.checkNotNullExpressionValue((Object)pokemonId, (String)"pokemonId");
            Intrinsics.checkNotNullExpressionValue((Object)pcId, (String)"pcId");
            this.tetheredPokemon.add(new Tethering(this.minRoamPos, this.maxRoamPos, playerId, this.ownerName, tetheringId, pokemonId, pcId, entityId));
        }
        BlockPos blockPos2 = NbtUtils.m_129239_((CompoundTag)nbt.m_128469_("TetherMinRoamPos"));
        Intrinsics.checkNotNullExpressionValue((Object)blockPos2, (String)"toBlockPos(nbt.getCompou\u2026eys.TETHER_MIN_ROAM_POS))");
        this.minRoamPos = blockPos2;
        BlockPos blockPos3 = NbtUtils.m_129239_((CompoundTag)nbt.m_128469_("TetherMaxRoamPos"));
        Intrinsics.checkNotNullExpressionValue((Object)blockPos3, (String)"toBlockPos(nbt.getCompou\u2026eys.TETHER_MAX_ROAM_POS))");
        this.maxRoamPos = blockPos3;
    }

    protected void m_183515_(@NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        super.m_183515_(nbt);
        ListTag list = new ListTag();
        for (Tethering tethering : this.tetheredPokemon) {
            CompoundTag tetheringNBT = new CompoundTag();
            tetheringNBT.m_128362_("TetheringId", tethering.getTetheringId());
            tetheringNBT.m_128362_("PlayerId", tethering.getPlayerId());
            tetheringNBT.m_128362_("UUID", tethering.getPokemonId());
            tetheringNBT.m_128362_("PCId", tethering.getPcId());
            tetheringNBT.m_128405_("EntityId", tethering.getEntityId());
            list.add((Object)tetheringNBT);
        }
        nbt.m_128365_("TetherPokemon", (Tag)list);
        nbt.m_128365_("TetherMinRoamPos", (Tag)NbtUtils.m_129224_((BlockPos)this.minRoamPos));
        nbt.m_128365_("TetherMaxRoamPos", (Tag)NbtUtils.m_129224_((BlockPos)this.maxRoamPos));
        UUID uUID = this.ownerId;
        if (uUID != null) {
            UUID it = uUID;
            boolean bl = false;
            nbt.m_128362_("TetherOwnerId", it);
        }
        nbt.m_128359_("TetherOwnerName", this.ownerName);
    }

    private static final boolean canAddPokemon$lambda$1(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Boolean)$tmp0.invoke(p0);
    }

    private static final boolean getInRangeViewerCount$lambda$8(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Boolean)$tmp0.invoke(p0);
    }

    private static final void TICKER$lambda$11(Level world, BlockPos blockPos2, BlockState blockState, PokemonPastureBlockEntity blockEntity) {
        if (world.f_46443_) {
            return;
        }
        int n = blockEntity.ticksUntilCheck;
        blockEntity.ticksUntilCheck = n + -1;
        if (blockEntity.ticksUntilCheck <= 0) {
            blockEntity.checkPokemon();
        }
        Intrinsics.checkNotNullExpressionValue((Object)((Object)blockEntity), (String)"blockEntity");
        Intrinsics.checkNotNullExpressionValue((Object)world, (String)"world");
        BlockPos blockPos3 = blockEntity.f_58858_;
        Intrinsics.checkNotNullExpressionValue((Object)blockPos3, (String)"blockEntity.pos");
        blockEntity.togglePastureOn(PokemonPastureBlockEntity.getInRangeViewerCount$default(blockEntity, world, blockPos3, 0.0, 4, null) > 0);
    }

    public static final /* synthetic */ boolean access$isPlayerViewing(PokemonPastureBlockEntity $this, ServerPlayer player) {
        return $this.isPlayerViewing(player);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR \u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0000X\u0080\u0004\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/block/entity/PokemonPastureBlockEntity$Companion;", "", "Lnet/minecraft/world/level/block/entity/BlockEntityTicker;", "Lcom/cobblemon/mod/common/block/entity/PokemonPastureBlockEntity;", "TICKER", "Lnet/minecraft/world/level/block/entity/BlockEntityTicker;", "getTICKER$common", "()Lnet/minecraft/world/level/block/entity/BlockEntityTicker;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final BlockEntityTicker<PokemonPastureBlockEntity> getTICKER$common() {
            return TICKER;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u000b\b\u0016\u0018\u00002\u00020\u0001BG\u0012\u0006\u0010\u001d\u001a\u00020\u0002\u0012\u0006\u0010\u0019\u001a\u00020\u0002\u0012\u0006\u0010$\u001a\u00020\u001f\u0012\u0006\u0010'\u001a\u00020&\u0012\u0006\u0010-\u001a\u00020\u001f\u0012\u0006\u0010+\u001a\u00020\u001f\u0012\u0006\u0010 \u001a\u00020\u001f\u0012\u0006\u0010\u0015\u001a\u00020\u0014\u00a2\u0006\u0004\b/\u00100J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u000f\u0010\b\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\u0004\b\b\u0010\tJ\u0017\u0010\r\u001a\u0004\u0018\u00010\f2\u0006\u0010\u000b\u001a\u00020\n\u00a2\u0006\u0004\b\r\u0010\u000eR\u0017\u0010\u0010\u001a\u00020\u000f8\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013R\u0017\u0010\u0015\u001a\u00020\u00148\u0006\u00a2\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018R\u0017\u0010\u0019\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u001b\u0010\u001cR\u0017\u0010\u001d\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u001d\u0010\u001a\u001a\u0004\b\u001e\u0010\u001cR\u0017\u0010 \u001a\u00020\u001f8\u0006\u00a2\u0006\f\n\u0004\b \u0010!\u001a\u0004\b\"\u0010#R\u0017\u0010$\u001a\u00020\u001f8\u0006\u00a2\u0006\f\n\u0004\b$\u0010!\u001a\u0004\b%\u0010#R\u0017\u0010'\u001a\u00020&8\u0006\u00a2\u0006\f\n\u0004\b'\u0010(\u001a\u0004\b)\u0010*R\u0017\u0010+\u001a\u00020\u001f8\u0006\u00a2\u0006\f\n\u0004\b+\u0010!\u001a\u0004\b,\u0010#R\u0017\u0010-\u001a\u00020\u001f8\u0006\u00a2\u0006\f\n\u0004\b-\u0010!\u001a\u0004\b.\u0010#\u00a8\u00061"}, d2={"Lcom/cobblemon/mod/common/block/entity/PokemonPastureBlockEntity$Tethering;", "", "Lnet/minecraft/core/BlockPos;", "pos", "", "canRoamTo", "(Lnet/minecraft/core/BlockPos;)Z", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getPokemon", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "Lcom/cobblemon/mod/common/net/messages/client/pasture/OpenPasturePacket$PasturePokemonDataDTO;", "toDTO", "(Lnet/minecraft/server/level/ServerPlayer;)Lcom/cobblemon/mod/common/net/messages/client/pasture/OpenPasturePacket$PasturePokemonDataDTO;", "Lnet/minecraft/world/phys/AABB;", "box", "Lnet/minecraft/world/phys/AABB;", "getBox", "()Lnet/minecraft/world/phys/AABB;", "", "entityId", "I", "getEntityId", "()I", "maxRoamPos", "Lnet/minecraft/core/BlockPos;", "getMaxRoamPos", "()Lnet/minecraft/core/BlockPos;", "minRoamPos", "getMinRoamPos", "Ljava/util/UUID;", "pcId", "Ljava/util/UUID;", "getPcId", "()Ljava/util/UUID;", "playerId", "getPlayerId", "", "playerName", "Ljava/lang/String;", "getPlayerName", "()Ljava/lang/String;", "pokemonId", "getPokemonId", "tetheringId", "getTetheringId", "<init>", "(Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;Ljava/util/UUID;Ljava/lang/String;Ljava/util/UUID;Ljava/util/UUID;Ljava/util/UUID;I)V", "common"})
    public static class Tethering {
        @NotNull
        private final BlockPos minRoamPos;
        @NotNull
        private final BlockPos maxRoamPos;
        @NotNull
        private final UUID playerId;
        @NotNull
        private final String playerName;
        @NotNull
        private final UUID tetheringId;
        @NotNull
        private final UUID pokemonId;
        @NotNull
        private final UUID pcId;
        private final int entityId;
        @NotNull
        private final AABB box;

        public Tethering(@NotNull BlockPos minRoamPos, @NotNull BlockPos maxRoamPos, @NotNull UUID playerId, @NotNull String playerName, @NotNull UUID tetheringId, @NotNull UUID pokemonId, @NotNull UUID pcId, int entityId) {
            Intrinsics.checkNotNullParameter((Object)minRoamPos, (String)"minRoamPos");
            Intrinsics.checkNotNullParameter((Object)maxRoamPos, (String)"maxRoamPos");
            Intrinsics.checkNotNullParameter((Object)playerId, (String)"playerId");
            Intrinsics.checkNotNullParameter((Object)playerName, (String)"playerName");
            Intrinsics.checkNotNullParameter((Object)tetheringId, (String)"tetheringId");
            Intrinsics.checkNotNullParameter((Object)pokemonId, (String)"pokemonId");
            Intrinsics.checkNotNullParameter((Object)pcId, (String)"pcId");
            this.minRoamPos = minRoamPos;
            this.maxRoamPos = maxRoamPos;
            this.playerId = playerId;
            this.playerName = playerName;
            this.tetheringId = tetheringId;
            this.pokemonId = pokemonId;
            this.pcId = pcId;
            this.entityId = entityId;
            this.box = new AABB(this.minRoamPos, this.maxRoamPos);
        }

        @NotNull
        public final BlockPos getMinRoamPos() {
            return this.minRoamPos;
        }

        @NotNull
        public final BlockPos getMaxRoamPos() {
            return this.maxRoamPos;
        }

        @NotNull
        public final UUID getPlayerId() {
            return this.playerId;
        }

        @NotNull
        public final String getPlayerName() {
            return this.playerName;
        }

        @NotNull
        public final UUID getTetheringId() {
            return this.tetheringId;
        }

        @NotNull
        public final UUID getPokemonId() {
            return this.pokemonId;
        }

        @NotNull
        public final UUID getPcId() {
            return this.pcId;
        }

        public final int getEntityId() {
            return this.entityId;
        }

        @Nullable
        public final Pokemon getPokemon() {
            return Cobblemon.INSTANCE.getStorage().getPC(this.pcId).get(this.pokemonId);
        }

        @NotNull
        public final AABB getBox() {
            return this.box;
        }

        public boolean canRoamTo(@NotNull BlockPos pos) {
            Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
            return this.box.m_82390_(pos.m_252807_());
        }

        @Nullable
        public final OpenPasturePacket.PasturePokemonDataDTO toDTO(@NotNull ServerPlayer player) {
            Object object;
            Object[] objectArray;
            MutableComponent mutableComponent;
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            Pokemon pokemon = this.getPokemon();
            if (pokemon == null) {
                return null;
            }
            Pokemon pokemon2 = pokemon;
            if (Intrinsics.areEqual((Object)this.playerId, (Object)player.m_20148_())) {
                mutableComponent = pokemon2.getDisplayName();
            } else {
                objectArray = new Object[]{pokemon2.getDisplayName(), this.playerName};
                mutableComponent = LocalizationUtilsKt.lang("ui.pasture.owned_name", objectArray);
            }
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"if (playerId == player.u\u2026isplayName(), playerName)");
            objectArray = player.m_9236_().m_6815_(this.entityId);
            return new OpenPasturePacket.PasturePokemonDataDTO(this.pokemonId, this.playerId, (Component)mutableComponent, pokemon2.getSpecies().getResourceIdentifier(), pokemon2.getAspects(), pokemon2.heldItem(), pokemon2.getLevel(), Intrinsics.areEqual((Object)((object = objectArray instanceof PokemonEntity ? (PokemonEntity)objectArray : null) != null && (object = ((PokemonEntity)object).getTethering()) != null ? ((Tethering)object).tetheringId : null), (Object)this.tetheringId));
        }
    }
}

