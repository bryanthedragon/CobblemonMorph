/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.StringTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientGamePacketListener
 *  net.minecraft.network.protocol.game.ClientboundAddEntityPacket
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.tags.FluidTags
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.vehicle.Boat
 *  net.minecraft.world.entity.vehicle.Boat$Status
 *  net.minecraft.world.entity.vehicle.Boat$Type
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.GameRules
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.WoodType
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.boat;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonEntities;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.boat.CobblemonBoatType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.accessor.BoatEntityAccessor;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0016\u0018\u0000 C2\u00020\u0001:\u0001CB\u0011\b\u0016\u0012\u0006\u00109\u001a\u000208\u00a2\u0006\u0004\b:\u0010;B)\b\u0016\u0012\u0006\u00109\u001a\u000208\u0012\u0006\u0010<\u001a\u00020\f\u0012\u0006\u0010=\u001a\u00020\f\u0012\u0006\u0010>\u001a\u00020\f\u00a2\u0006\u0004\b:\u0010?B\u001f\u0012\u000e\u0010A\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010@\u0012\u0006\u00109\u001a\u000208\u00a2\u0006\u0004\b:\u0010BJ\u000f\u0010\u0003\u001a\u00020\u0002H\u0004\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u000f\u0010\u0006\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0015\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ/\u0010\u0015\u001a\u00020\u00142\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u0012H\u0014\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u000f\u0010\u0018\u001a\u00020\u0017H\u0014\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u000f\u0010\u001a\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u000f\u0010\u001d\u001a\u00020\u001cH\u0016\u00a2\u0006\u0004\b\u001d\u0010\u001eJ\u000f\u0010\u001f\u001a\u00020\u0014H\u0014\u00a2\u0006\u0004\b\u001f\u0010 J\u0017\u0010#\u001a\u00020\u00142\u0006\u0010\"\u001a\u00020!H\u0014\u00a2\u0006\u0004\b#\u0010$J\u0017\u0010&\u001a\u00020\u00142\u0006\u0010%\u001a\u00020\u001cH\u0016\u00a2\u0006\u0004\b&\u0010'J\u0017\u0010(\u001a\u00020\u00142\u0006\u0010\"\u001a\u00020!H\u0014\u00a2\u0006\u0004\b(\u0010$R\u0011\u0010,\u001a\u00020)8F\u00a2\u0006\u0006\u001a\u0004\b*\u0010+R$\u00103\u001a\u00020-2\u0006\u0010.\u001a\u00020-8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b/\u00100\"\u0004\b1\u00102R\u0011\u00107\u001a\u0002048F\u00a2\u0006\u0006\u001a\u0004\b5\u00106\u00a8\u0006D"}, d2={"Lcom/cobblemon/mod/common/entity/boat/CobblemonBoatEntity;", "Lnet/minecraft/world/entity/vehicle/Boat;", "Lcom/cobblemon/mod/common/mixin/accessor/BoatEntityAccessor;", "accessor", "()Lcom/cobblemon/mod/common/mixin/accessor/BoatEntityAccessor;", "Lnet/minecraft/world/item/Item;", "asItem", "()Lnet/minecraft/world/item/Item;", "Lnet/minecraft/network/protocol/Packet;", "Lnet/minecraft/network/protocol/game/ClientGamePacketListener;", "createSpawnPacket", "()Lnet/minecraft/network/protocol/Packet;", "", "heightDifference", "", "onGround", "Lnet/minecraft/world/level/block/state/BlockState;", "state", "Lnet/minecraft/core/BlockPos;", "landedPosition", "", "fall", "(DZLnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;)V", "Lnet/minecraft/network/chat/Component;", "getDefaultName", "()Lnet/minecraft/network/chat/Component;", "getMountedHeightOffset", "()D", "Lnet/minecraft/entity/vehicle/BoatEntity$Type;", "getVariant", "()Lnet/minecraft/world/entity/vehicle/Boat$Type;", "initDataTracker", "()V", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "readCustomDataFromNbt", "(Lnet/minecraft/nbt/CompoundTag;)V", "type", "setVariant", "(Lnet/minecraft/world/entity/vehicle/Boat$Type;)V", "writeCustomDataToNbt", "Lnet/minecraft/world/level/block/Block;", "getBaseBlock", "()Lnet/minecraft/world/level/block/Block;", "baseBlock", "Lcom/cobblemon/mod/common/entity/boat/CobblemonBoatType;", "value", "getBoatType", "()Lcom/cobblemon/mod/common/entity/boat/CobblemonBoatType;", "setBoatType", "(Lcom/cobblemon/mod/common/entity/boat/CobblemonBoatType;)V", "boatType", "Lnet/minecraft/world/level/block/state/properties/WoodType;", "getWoodType", "()Lnet/minecraft/world/level/block/state/properties/WoodType;", "woodType", "Lnet/minecraft/world/level/Level;", "world", "<init>", "(Lnet/minecraft/world/level/Level;)V", "x", "y", "z", "(Lnet/minecraft/world/level/Level;DDD)V", "Lnet/minecraft/world/entity/EntityType;", "entityType", "(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/level/Level;)V", "Companion", "common"})
public class CobblemonBoatEntity
extends Boat {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private static final String TYPE_KEY = "type";
    private static final EntityDataAccessor<Integer> TYPE_TRACKED_DATA = SynchedEntityData.m_135353_(CobblemonBoatEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);

    public CobblemonBoatEntity(@NotNull EntityType<? extends Boat> entityType, @NotNull Level world) {
        Intrinsics.checkNotNullParameter(entityType, (String)"entityType");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        super(entityType, world);
    }

    public CobblemonBoatEntity(@NotNull Level world) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        this(CobblemonEntities.BOAT, world);
    }

    public CobblemonBoatEntity(@NotNull Level world, double x, double y, double z) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        this(CobblemonEntities.BOAT, world);
        this.m_6034_(x, y, z);
        this.f_19854_ = x;
        this.f_19855_ = y;
        this.f_19856_ = z;
    }

    @NotNull
    public final CobblemonBoatType getBoatType() {
        Object object = this.f_19804_.m_135370_(TYPE_TRACKED_DATA);
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"this.dataTracker.get(TYPE_TRACKED_DATA)");
        return CobblemonBoatType.Companion.ofOrdinal$common(((Number)object).intValue());
    }

    public final void setBoatType(@NotNull CobblemonBoatType value2) {
        Intrinsics.checkNotNullParameter((Object)((Object)value2), (String)"value");
        this.f_19804_.m_135381_(TYPE_TRACKED_DATA, (Object)value2.ordinal());
    }

    @NotNull
    public final WoodType getWoodType() {
        return this.getBoatType().getWoodType();
    }

    @NotNull
    public final Block getBaseBlock() {
        return this.getBoatType().getBaseBlock();
    }

    @NotNull
    public Item m_38369_() {
        return this.getBoatType().getBoatItem();
    }

    @NotNull
    public Packet<ClientGamePacketListener> m_5654_() {
        return (Packet)new ClientboundAddEntityPacket((Entity)this);
    }

    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(TYPE_TRACKED_DATA, (Object)CobblemonBoatType.APRICORN.ordinal());
    }

    @NotNull
    protected Component m_5677_() {
        Component component = EntityType.f_20552_.m_20676_();
        Intrinsics.checkNotNullExpressionValue((Object)component, (String)"BOAT.name");
        return component;
    }

    protected void m_7378_(@NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        if (nbt.m_128425_(TYPE_KEY, 8)) {
            String string = nbt.m_128461_(TYPE_KEY);
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"nbt.getString(TYPE_KEY)");
            this.setBoatType(CobblemonBoatType.valueOf(string));
        }
    }

    protected void m_7380_(@NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        nbt.m_128365_(TYPE_KEY, (Tag)StringTag.m_129297_((String)this.getBoatType().name()));
    }

    public void m_28464_(@NotNull Boat.Type type) {
        Intrinsics.checkNotNullParameter((Object)type, (String)TYPE_KEY);
        throw new UnsupportedOperationException("The vanilla boat type is not present in the Cobblemon implementation use the type property");
    }

    @NotNull
    public Boat.Type m_28554_() {
        throw new UnsupportedOperationException("The vanilla boat type is not present in the Cobblemon implementation use the type property");
    }

    public double m_6048_() {
        return this.getBoatType().getMountedOffset();
    }

    protected void m_7840_(double heightDifference, boolean onGround, @NotNull BlockState state, @NotNull BlockPos landedPosition) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)landedPosition, (String)"landedPosition");
        BoatEntityAccessor accessor = this.accessor();
        accessor.setFallVelocity(this.m_20184_().f_82480_);
        if (!this.m_20159_()) {
            return;
        }
        if (!this.m_9236_().m_6425_(this.m_20183_().m_7495_()).m_205070_(FluidTags.f_13131_) && heightDifference < 0.0) {
            this.f_19789_ -= (float)heightDifference;
        }
        if (!onGround) {
            return;
        }
        if (this.f_19789_ < 3.0f || accessor.getLocation() != Boat.Status.ON_LAND) {
            this.m_183634_();
            return;
        }
        this.m_142535_(this.f_19789_, 1.0f, this.m_269291_().m_268989_());
        if (this.m_9236_().f_46443_ || this.m_213877_()) {
            return;
        }
        this.m_6074_();
        if (this.m_9236_().m_46469_().m_46207_(GameRules.f_46137_)) {
            int it;
            int n = 3;
            int n2 = 0;
            while (n2 < n) {
                it = n2++;
                boolean bl = false;
                this.m_19998_((ItemLike)this.getBoatType().getBaseBlock());
            }
            n = 2;
            n2 = 0;
            while (n2 < n) {
                it = n2++;
                boolean bl = false;
                this.m_19998_((ItemLike)Items.f_42398_);
            }
        }
    }

    @NotNull
    protected final BoatEntityAccessor accessor() {
        Intrinsics.checkNotNull((Object)((Object)this), (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.accessor.BoatEntityAccessor");
        return (BoatEntityAccessor)((Object)this);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\n\u0010\u000bR\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R8\u0010\b\u001a&\u0012\f\u0012\n \u0007*\u0004\u0018\u00010\u00060\u0006 \u0007*\u0012\u0012\f\u0012\n \u0007*\u0004\u0018\u00010\u00060\u0006\u0018\u00010\u00050\u00058\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\b\u0010\t\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/entity/boat/CobblemonBoatEntity$Companion;", "", "", "TYPE_KEY", "Ljava/lang/String;", "Lnet/minecraft/network/syncher/EntityDataAccessor;", "", "kotlin.jvm.PlatformType", "TYPE_TRACKED_DATA", "Lnet/minecraft/network/syncher/EntityDataAccessor;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

