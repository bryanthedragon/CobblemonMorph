/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Deprecated
 *  kotlin.Metadata
 *  kotlin.ReplaceWith
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.ClosedFloatingPointRange
 *  kotlin.ranges.ClosedRange
 *  kotlin.ranges.IntRange
 *  kotlin.ranges.RangesKt
 *  kotlin.text.StringsKt
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityTicker
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlockEntities;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.PokeBalls;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.HealingMachineBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.PokeBall;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.BlockPosExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.WorldExtensionsKt;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.ClosedFloatingPointRange;
import kotlin.ranges.ClosedRange;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0010\n\u0002\u0010%\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 W2\u00020\u0001:\u0002WXB\u0017\u0012\u0006\u0010R\u001a\u00020Q\u0012\u0006\u0010T\u001a\u00020S\u00a2\u0006\u0004\bU\u0010VJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0015\u0010\b\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\b\u0010\tJ\u000f\u0010\n\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ\u000f\u0010\f\u001a\u00020\u0004H\u0002\u00a2\u0006\u0004\b\f\u0010\u000bJ\r\u0010\r\u001a\u00020\u0004\u00a2\u0006\u0004\b\r\u0010\u000bJ\u000f\u0010\u000e\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000bJ\u000f\u0010\u000f\u001a\u00020\u0004H\u0002\u00a2\u0006\u0004\b\u000f\u0010\u000bJ\u0019\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\u0010\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0017\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u0015H\u0016\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u000f\u0010\u0019\u001a\u00020\u0004H\u0002\u00a2\u0006\u0004\b\u0019\u0010\u000bJ\u0015\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u001a\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u000f\u0010\u001e\u001a\u00020\u0004H\u0002\u00a2\u0006\u0004\b\u001e\u0010\u000bJ\u000f\u0010\u001f\u001a\u00020\u0015H\u0016\u00a2\u0006\u0004\b\u001f\u0010 J\u000f\u0010\"\u001a\u00020!H\u0016\u00a2\u0006\u0004\b\"\u0010#J\u001b\u0010%\u001a\u00020\u00042\n\b\u0002\u0010$\u001a\u0004\u0018\u00010\u0011H\u0002\u00a2\u0006\u0004\b%\u0010&J\u000f\u0010'\u001a\u00020\u0004H\u0002\u00a2\u0006\u0004\b'\u0010\u000bJ\u0017\u0010(\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u0015H\u0014\u00a2\u0006\u0004\b(\u0010\u0018R$\u0010*\u001a\u00020\u00112\u0006\u0010)\u001a\u00020\u00118\u0006@BX\u0086\u000e\u00a2\u0006\f\n\u0004\b*\u0010+\u001a\u0004\b,\u0010-R(\u0010.\u001a\u0004\u0018\u00010\u001a2\b\u0010)\u001a\u0004\u0018\u00010\u001a8\u0006@BX\u0086\u000e\u00a2\u0006\f\n\u0004\b.\u0010/\u001a\u0004\b0\u00101R\u0018\u00103\u001a\u0004\u0018\u0001028\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b3\u00104R\"\u00105\u001a\u00020\u00118\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b5\u0010+\u001a\u0004\b6\u0010-\"\u0004\b7\u00108R\"\u0010:\u001a\u0002098\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b:\u0010;\u001a\u0004\b<\u0010=\"\u0004\b>\u0010?R\"\u0010@\u001a\u00020\u00078\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b@\u0010A\u001a\u0004\bB\u0010C\"\u0004\bD\u0010ER\u0011\u0010F\u001a\u00020\u00078F\u00a2\u0006\u0006\u001a\u0004\bF\u0010CR\"\u0010G\u001a\u0002098\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bG\u0010;\u001a\u0004\bH\u0010=\"\u0004\bI\u0010?R \u0010K\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120J8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\bK\u0010LR \u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00120M8FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\bP\u0010\u000b\u001a\u0004\bN\u0010O\u00a8\u0006Y"}, d2={"Lcom/cobblemon/mod/common/block/entity/HealingMachineBlockEntity;", "Lnet/minecraft/world/level/block/entity/BlockEntity;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "activate", "(Lnet/minecraft/server/level/ServerPlayer;)V", "", "canHeal", "(Lnet/minecraft/server/level/ServerPlayer;)Z", "cancelRemoval", "()V", "clearData", "completeHealing", "markRemoved", "markUpdated", "", "", "Lcom/cobblemon/mod/common/pokeball/PokeBall;", "pokeBalls", "()Ljava/util/Map;", "Lnet/minecraft/nbt/CompoundTag;", "compoundTag", "readNbt", "(Lnet/minecraft/nbt/CompoundTag;)V", "restoreSnapshot", "Ljava/util/UUID;", "user", "setUser", "(Ljava/util/UUID;)V", "snapshotAndClearData", "toInitialChunkDataNbt", "()Lnet/minecraft/nbt/CompoundTag;", "Lnet/minecraft/network/protocol/game/ClientboundBlockEntityDataPacket;", "toUpdatePacket", "()Lnet/minecraft/network/protocol/game/ClientboundBlockEntityDataPacket;", "level", "updateBlockChargeLevel", "(Ljava/lang/Integer;)V", "updateRedstoneSignal", "writeNbt", "<set-?>", "currentSignal", "I", "getCurrentSignal", "()I", "currentUser", "Ljava/util/UUID;", "getCurrentUser", "()Ljava/util/UUID;", "Lcom/cobblemon/mod/common/block/entity/HealingMachineBlockEntity$DataSnapshot;", "dataSnapshot", "Lcom/cobblemon/mod/common/block/entity/HealingMachineBlockEntity$DataSnapshot;", "healTimeLeft", "getHealTimeLeft", "setHealTimeLeft", "(I)V", "", "healingCharge", "F", "getHealingCharge", "()F", "setHealingCharge", "(F)V", "infinite", "Z", "getInfinite", "()Z", "setInfinite", "(Z)V", "isInUse", "maxCharge", "getMaxCharge", "setMaxCharge", "", "pokeBallMap", "Ljava/util/Map;", "", "getPokeBalls", "()Ljava/util/List;", "getPokeBalls$annotations", "Lnet/minecraft/core/BlockPos;", "blockPos", "Lnet/minecraft/world/level/block/state/BlockState;", "blockState", "<init>", "(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V", "Companion", "DataSnapshot", "common"})
@SourceDebugExtension(value={"SMAP\nHealingMachineBlockEntity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HealingMachineBlockEntity.kt\ncom/cobblemon/mod/common/block/entity/HealingMachineBlockEntity\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,294:1\n1864#2,3:295\n215#3,2:298\n1#4:300\n*S KotlinDebug\n*F\n+ 1 HealingMachineBlockEntity.kt\ncom/cobblemon/mod/common/block/entity/HealingMachineBlockEntity\n*L\n89#1:295,3\n177#1:298,2\n*E\n"})
public final class HealingMachineBlockEntity
extends BlockEntity {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @Nullable
    private UUID currentUser;
    private int healTimeLeft;
    private float healingCharge;
    private boolean infinite;
    private int currentSignal;
    private float maxCharge;
    @Nullable
    private DataSnapshot dataSnapshot;
    @NotNull
    private final Map<Integer, PokeBall> pokeBallMap;
    @NotNull
    private static final HashSet<UUID> alreadyHealing = new HashSet();
    public static final int MAX_REDSTONE_SIGNAL = 10;
    @NotNull
    private static final BlockEntityTicker<HealingMachineBlockEntity> TICKER = HealingMachineBlockEntity::TICKER$lambda$3;

    public HealingMachineBlockEntity(@NotNull BlockPos blockPos2, @NotNull BlockState blockState) {
        Intrinsics.checkNotNullParameter((Object)blockPos2, (String)"blockPos");
        Intrinsics.checkNotNullParameter((Object)blockState, (String)"blockState");
        super(CobblemonBlockEntities.HEALING_MACHINE, blockPos2, blockState);
        this.maxCharge = 6.0f;
        this.pokeBallMap = new HashMap();
        this.maxCharge = RangesKt.coerceAtLeast((float)Cobblemon.INSTANCE.getConfig().getMaxHealerCharge(), (float)6.0f);
        this.updateRedstoneSignal();
        HealingMachineBlockEntity.updateBlockChargeLevel$default(this, null, 1, null);
    }

    @Nullable
    public final UUID getCurrentUser() {
        return this.currentUser;
    }

    @NotNull
    public final List<PokeBall> getPokeBalls() {
        return CollectionsKt.toMutableList(this.pokeBalls().values());
    }

    @Deprecated(message="This property will be removed in the future", replaceWith=@ReplaceWith(expression="pokeBalls()", imports={}))
    public static /* synthetic */ void getPokeBalls$annotations() {
    }

    public final int getHealTimeLeft() {
        return this.healTimeLeft;
    }

    public final void setHealTimeLeft(int n) {
        this.healTimeLeft = n;
    }

    public final float getHealingCharge() {
        return this.healingCharge;
    }

    public final void setHealingCharge(float f) {
        this.healingCharge = f;
    }

    public final boolean isInUse() {
        return this.currentUser != null;
    }

    public final boolean getInfinite() {
        return this.infinite;
    }

    public final void setInfinite(boolean bl) {
        this.infinite = bl;
    }

    public final int getCurrentSignal() {
        return this.currentSignal;
    }

    public final float getMaxCharge() {
        return this.maxCharge;
    }

    public final void setMaxCharge(float f) {
        this.maxCharge = f;
    }

    @NotNull
    public final Map<Integer, PokeBall> pokeBalls() {
        return this.pokeBallMap;
    }

    /*
     * WARNING - void declaration
     */
    public final void setUser(@NotNull UUID user) {
        Intrinsics.checkNotNullParameter((Object)user, (String)"user");
        this.clearData();
        ServerPlayer serverPlayer = PlayerExtensionsKt.getPlayer(user);
        if (serverPlayer == null) {
            return;
        }
        ServerPlayer player = serverPlayer;
        PlayerPartyStore party = PlayerExtensionsKt.party(player);
        this.pokeBallMap.clear();
        Iterable $this$forEachIndexed$iv = party.toGappyList();
        boolean $i$f$forEachIndexed = false;
        int index$iv = 0;
        for (Object item$iv : $this$forEachIndexed$iv) {
            void pokemon;
            int n;
            if ((n = index$iv++) < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            Pokemon pokemon2 = (Pokemon)item$iv;
            int index = n;
            boolean bl = false;
            if (pokemon == null) continue;
            Integer n2 = index;
            this.pokeBallMap.put(n2, pokemon.getCaughtBall());
        }
        this.currentUser = user;
        this.healTimeLeft = 24;
        this.markUpdated();
    }

    public final boolean canHeal(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        if (Cobblemon.INSTANCE.getConfig().getInfiniteHealerCharge() || this.infinite) {
            return true;
        }
        float neededHealthPercent = PlayerExtensionsKt.party(player).getHealingRemainderPercent();
        return this.healingCharge >= neededHealthPercent;
    }

    public final void activate(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        if (!Cobblemon.INSTANCE.getConfig().getInfiniteHealerCharge() && !(this.healingCharge == this.maxCharge)) {
            float neededHealthPercent = PlayerExtensionsKt.party(player).getHealingRemainderPercent();
            this.healingCharge = ((Number)((Object)RangesKt.coerceIn((Comparable)Float.valueOf(this.healingCharge - neededHealthPercent), (ClosedFloatingPointRange)RangesKt.rangeTo((float)0.0f, (float)this.maxCharge)))).floatValue();
            this.updateRedstoneSignal();
        }
        UUID uUID = player.m_20148_();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"player.uuid");
        this.setUser(uUID);
        alreadyHealing.add(player.m_20148_());
        this.updateBlockChargeLevel(6);
        if (this.f_58857_ != null) {
            Level level = this.f_58857_;
            Intrinsics.checkNotNull((Object)level);
            if (!level.f_46443_) {
                Level level2 = this.f_58857_;
                Intrinsics.checkNotNull((Object)level2);
                BlockPos blockPos2 = this.f_58858_;
                Intrinsics.checkNotNullExpressionValue((Object)blockPos2, (String)"pos");
                WorldExtensionsKt.playSoundServer$default(level2, BlockPosExtensionsKt.toVec3d(blockPos2), CobblemonSounds.HEALING_MACHINE_ACTIVE, null, 1.0f, 1.0f, 4, null);
            }
        }
    }

    public final void completeHealing() {
        UUID uUID = this.currentUser;
        if (uUID == null || (uUID = PlayerExtensionsKt.getPlayer(uUID)) == null) {
            this.clearData();
            return;
        }
        UUID player = uUID;
        PlayerPartyStore party = PlayerExtensionsKt.party((ServerPlayer)player);
        party.heal();
        MutableComponent mutableComponent = LocalizationUtilsKt.lang("healingmachine.healed", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"healingmachine.healed\")");
        player.m_5661_((Component)TextKt.green(mutableComponent), true);
        HealingMachineBlockEntity.updateBlockChargeLevel$default(this, null, 1, null);
        this.clearData();
    }

    public void m_142466_(@NotNull CompoundTag compoundTag) {
        Intrinsics.checkNotNullParameter((Object)compoundTag, (String)"compoundTag");
        super.m_142466_(compoundTag);
        this.pokeBallMap.clear();
        if (compoundTag.m_128403_("MachineUser")) {
            this.currentUser = compoundTag.m_128342_("MachineUser");
        }
        if (compoundTag.m_128441_("MachinePokeBalls")) {
            CompoundTag pokeBallsTag = compoundTag.m_128469_("MachinePokeBalls");
            int index = 0;
            for (String key : pokeBallsTag.m_128431_()) {
                String pokeBallId = pokeBallsTag.m_128461_(key);
                Intrinsics.checkNotNullExpressionValue((Object)pokeBallId, (String)"pokeBallId");
                if (((CharSequence)pokeBallId).length() == 0) continue;
                Intrinsics.checkNotNullExpressionValue((Object)key, (String)"key");
                Integer n = StringsKt.toIntOrNull((String)key);
                int actualIndex = n != null ? n : index;
                PokeBall pokeBall = PokeBalls.INSTANCE.getPokeBall(new ResourceLocation(pokeBallId));
                if (pokeBall != null) {
                    Integer n2 = actualIndex;
                    this.pokeBallMap.put(n2, pokeBall);
                }
                ++index;
            }
        }
        if (compoundTag.m_128441_("MachineTimeLeft")) {
            this.healTimeLeft = compoundTag.m_128451_("MachineTimeLeft");
        }
        if (compoundTag.m_128441_("MachineCharge")) {
            this.healingCharge = ((Number)((Object)RangesKt.coerceIn((Comparable)Float.valueOf(compoundTag.m_128457_("MachineCharge")), (ClosedFloatingPointRange)RangesKt.rangeTo((float)0.0f, (float)this.maxCharge)))).floatValue();
        }
        if (compoundTag.m_128441_("MachineInfinite")) {
            this.infinite = compoundTag.m_128471_("MachineInfinite");
        }
    }

    protected void m_183515_(@NotNull CompoundTag compoundTag) {
        Intrinsics.checkNotNullParameter((Object)compoundTag, (String)"compoundTag");
        super.m_183515_(compoundTag);
        if (this.currentUser != null) {
            UUID uUID = this.currentUser;
            Intrinsics.checkNotNull((Object)uUID);
            compoundTag.m_128362_("MachineUser", uUID);
        } else {
            compoundTag.m_128473_("MachineUser");
        }
        if (!this.pokeBalls().isEmpty()) {
            CompoundTag pokeBallsTag = new CompoundTag();
            Map<Integer, PokeBall> $this$forEach$iv = this.pokeBalls();
            boolean $i$f$forEach = false;
            Iterator<Map.Entry<Integer, PokeBall>> iterator = $this$forEach$iv.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Integer, PokeBall> element$iv;
                Map.Entry<Integer, PokeBall> entry = element$iv = iterator.next();
                boolean bl = false;
                int index = ((Number)entry.getKey()).intValue();
                PokeBall pokeBall = entry.getValue();
                pokeBallsTag.m_128359_(String.valueOf(index), pokeBall.getName().toString());
            }
            compoundTag.m_128365_("MachinePokeBalls", (Tag)pokeBallsTag);
        } else {
            compoundTag.m_128473_("MachinePokeBalls");
        }
        compoundTag.m_128405_("MachineTimeLeft", this.healTimeLeft);
        compoundTag.m_128350_("MachineCharge", this.healingCharge);
        compoundTag.m_128379_("MachineInfinite", this.infinite);
    }

    @NotNull
    public ClientboundBlockEntityDataPacket toUpdatePacket() {
        ClientboundBlockEntityDataPacket clientboundBlockEntityDataPacket = ClientboundBlockEntityDataPacket.m_195640_((BlockEntity)this);
        Intrinsics.checkNotNullExpressionValue((Object)clientboundBlockEntityDataPacket, (String)"create(this)");
        return clientboundBlockEntityDataPacket;
    }

    @NotNull
    public CompoundTag m_5995_() {
        CompoundTag compoundTag = super.m_187480_();
        Intrinsics.checkNotNullExpressionValue((Object)compoundTag, (String)"super.createNbtWithIdentifyingData()");
        return compoundTag;
    }

    public void m_7651_() {
        this.snapshotAndClearData();
        super.m_7651_();
    }

    public void m_6339_() {
        this.restoreSnapshot();
        super.m_6339_();
    }

    private final void updateRedstoneSignal() {
        if (Cobblemon.INSTANCE.getConfig().getInfiniteHealerCharge() || this.infinite) {
            this.currentSignal = 10;
        }
        int remainder = (int)(this.healingCharge / this.maxCharge * (float)100) / 10;
        this.currentSignal = RangesKt.coerceAtMost((int)remainder, (int)10);
    }

    private final void updateBlockChargeLevel(Integer level) {
        if (this.f_58857_ != null) {
            Level level2 = this.f_58857_;
            Intrinsics.checkNotNull((Object)level2);
            if (!level2.f_46443_) {
                int currentCharge;
                Integer n = level;
                int chargeLevel = RangesKt.coerceIn((int)(n != null ? n : (Cobblemon.INSTANCE.getConfig().getInfiniteHealerCharge() || this.infinite ? 5 : (int)Math.floor(this.healingCharge / this.maxCharge * (float)5))), (ClosedRange)((ClosedRange)new IntRange(0, 6)));
                Level level3 = this.f_58857_;
                Intrinsics.checkNotNull((Object)level3);
                BlockState state = level3.m_8055_(this.f_58858_);
                if (state != null && state.m_60734_() instanceof HealingMachineBlock && chargeLevel != (currentCharge = ((Number)((Object)state.m_61143_((Property)HealingMachineBlock.Companion.getCHARGE_LEVEL()))).intValue())) {
                    Level level4 = this.f_58857_;
                    Intrinsics.checkNotNull((Object)level4);
                    level4.m_46597_(this.f_58858_, (BlockState)state.m_61124_((Property)HealingMachineBlock.Companion.getCHARGE_LEVEL(), (Comparable)Integer.valueOf(chargeLevel)));
                }
            }
        }
    }

    static /* synthetic */ void updateBlockChargeLevel$default(HealingMachineBlockEntity healingMachineBlockEntity, Integer n, int n2, Object object) {
        if ((n2 & 1) != 0) {
            n = null;
        }
        healingMachineBlockEntity.updateBlockChargeLevel(n);
    }

    private final void markUpdated() {
        this.m_6596_();
        Level level = this.f_58857_;
        Intrinsics.checkNotNull((Object)level);
        level.m_7260_(this.f_58858_, this.m_58900_(), this.m_58900_(), 3);
    }

    private final void snapshotAndClearData() {
        this.dataSnapshot = new DataSnapshot(this.currentUser, this.pokeBalls(), this.healTimeLeft);
        this.clearData();
    }

    private final void clearData() {
        UUID uUID = this.currentUser;
        if (uUID != null) {
            UUID uUID2 = uUID;
            HashSet<UUID> hashSet = alreadyHealing;
            UUID p0 = uUID2;
            boolean bl = false;
            hashSet.remove(p0);
        }
        this.currentUser = null;
        this.pokeBallMap.clear();
        this.healTimeLeft = 0;
        this.markUpdated();
    }

    private final void restoreSnapshot() {
        block0: {
            DataSnapshot dataSnapshot = this.dataSnapshot;
            if (dataSnapshot == null) break block0;
            DataSnapshot it = dataSnapshot;
            boolean bl = false;
            this.pokeBallMap.clear();
            this.currentUser = it.getCurrentUser();
            this.pokeBallMap.putAll(it.getPokeBalls());
            this.healTimeLeft = it.getHealTimeLeft();
        }
    }

    private static final void TICKER$lambda$3(Level world, BlockPos blockPos2, BlockState blockState, HealingMachineBlockEntity blockEntity) {
        if (world.f_46443_) {
            return;
        }
        if (blockEntity.isInUse()) {
            if (blockEntity.healTimeLeft > 0) {
                int n = blockEntity.healTimeLeft;
                blockEntity.healTimeLeft = n + -1;
            } else {
                blockEntity.completeHealing();
            }
        } else if (blockEntity.healingCharge < blockEntity.maxCharge) {
            float chargePerTick = RangesKt.coerceAtLeast((float)Cobblemon.INSTANCE.getConfig().getChargeGainedPerTick(), (float)0.0f);
            blockEntity.healingCharge = ((Number)((Object)RangesKt.coerceIn((Comparable)Float.valueOf(blockEntity.healingCharge + chargePerTick), (ClosedFloatingPointRange)RangesKt.rangeTo((float)0.0f, (float)blockEntity.maxCharge)))).floatValue();
            Intrinsics.checkNotNullExpressionValue((Object)((Object)blockEntity), (String)"blockEntity");
            HealingMachineBlockEntity.updateBlockChargeLevel$default(blockEntity, null, 1, null);
            blockEntity.updateRedstoneSignal();
            blockEntity.markUpdated();
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0014\u0010\b\u001a\u00020\u00078\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\b\u0010\tR \u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\n8\u0000X\u0080\u0004\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000fR$\u0010\u0013\u001a\u0012\u0012\u0004\u0012\u00020\u00110\u0010j\b\u0012\u0004\u0012\u00020\u0011`\u00128\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0013\u0010\u0014\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/block/entity/HealingMachineBlockEntity$Companion;", "", "Lnet/minecraft/world/entity/player/Player;", "player", "", "isUsingHealer", "(Lnet/minecraft/world/entity/player/Player;)Z", "", "MAX_REDSTONE_SIGNAL", "I", "Lnet/minecraft/world/level/block/entity/BlockEntityTicker;", "Lcom/cobblemon/mod/common/block/entity/HealingMachineBlockEntity;", "TICKER", "Lnet/minecraft/world/level/block/entity/BlockEntityTicker;", "getTICKER$common", "()Lnet/minecraft/world/level/block/entity/BlockEntityTicker;", "Ljava/util/HashSet;", "Ljava/util/UUID;", "Lkotlin/collections/HashSet;", "alreadyHealing", "Ljava/util/HashSet;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final BlockEntityTicker<HealingMachineBlockEntity> getTICKER$common() {
            return TICKER;
        }

        public final boolean isUsingHealer(@NotNull Player player) {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            return alreadyHealing.contains(player.m_20148_());
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u000b\b\u0082\b\u0018\u00002\u00020\u0001B-\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0002\u0012\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005\u0012\u0006\u0010\u000e\u001a\u00020\u0006\u00a2\u0006\u0004\b\u001f\u0010 J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u001c\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\b\u0010\tJ\u0010\u0010\n\u001a\u00020\u0006H\u00c6\u0003\u00a2\u0006\u0004\b\n\u0010\u000bJ<\u0010\u000f\u001a\u00020\u00002\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00022\u0014\b\u0002\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u00052\b\b\u0002\u0010\u000e\u001a\u00020\u0006H\u00c6\u0001\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u001a\u0010\u0013\u001a\u00020\u00122\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0010\u0010\u0015\u001a\u00020\u0006H\u00d6\u0001\u00a2\u0006\u0004\b\u0015\u0010\u000bJ\u0010\u0010\u0017\u001a\u00020\u0016H\u00d6\u0001\u00a2\u0006\u0004\b\u0017\u0010\u0018R\u0019\u0010\f\u001a\u0004\u0018\u00010\u00028\u0006\u00a2\u0006\f\n\u0004\b\f\u0010\u0019\u001a\u0004\b\u001a\u0010\u0004R\u0017\u0010\u000e\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u001b\u001a\u0004\b\u001c\u0010\u000bR#\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u00058\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u001d\u001a\u0004\b\u001e\u0010\t\u00a8\u0006!"}, d2={"Lcom/cobblemon/mod/common/block/entity/HealingMachineBlockEntity$DataSnapshot;", "", "Ljava/util/UUID;", "component1", "()Ljava/util/UUID;", "", "", "Lcom/cobblemon/mod/common/pokeball/PokeBall;", "component2", "()Ljava/util/Map;", "component3", "()I", "currentUser", "pokeBalls", "healTimeLeft", "copy", "(Ljava/util/UUID;Ljava/util/Map;I)Lcom/cobblemon/mod/common/block/entity/HealingMachineBlockEntity$DataSnapshot;", "other", "", "equals", "(Ljava/lang/Object;)Z", "hashCode", "", "toString", "()Ljava/lang/String;", "Ljava/util/UUID;", "getCurrentUser", "I", "getHealTimeLeft", "Ljava/util/Map;", "getPokeBalls", "<init>", "(Ljava/util/UUID;Ljava/util/Map;I)V", "common"})
    private static final class DataSnapshot {
        @Nullable
        private final UUID currentUser;
        @NotNull
        private final Map<Integer, PokeBall> pokeBalls;
        private final int healTimeLeft;

        public DataSnapshot(@Nullable UUID currentUser, @NotNull Map<Integer, ? extends PokeBall> pokeBalls, int healTimeLeft) {
            Intrinsics.checkNotNullParameter(pokeBalls, (String)"pokeBalls");
            this.currentUser = currentUser;
            this.pokeBalls = pokeBalls;
            this.healTimeLeft = healTimeLeft;
        }

        @Nullable
        public final UUID getCurrentUser() {
            return this.currentUser;
        }

        @NotNull
        public final Map<Integer, PokeBall> getPokeBalls() {
            return this.pokeBalls;
        }

        public final int getHealTimeLeft() {
            return this.healTimeLeft;
        }

        @Nullable
        public final UUID component1() {
            return this.currentUser;
        }

        @NotNull
        public final Map<Integer, PokeBall> component2() {
            return this.pokeBalls;
        }

        public final int component3() {
            return this.healTimeLeft;
        }

        @NotNull
        public final DataSnapshot copy(@Nullable UUID currentUser, @NotNull Map<Integer, ? extends PokeBall> pokeBalls, int healTimeLeft) {
            Intrinsics.checkNotNullParameter(pokeBalls, (String)"pokeBalls");
            return new DataSnapshot(currentUser, pokeBalls, healTimeLeft);
        }

        public static /* synthetic */ DataSnapshot copy$default(DataSnapshot dataSnapshot, UUID uUID, Map map, int n, int n2, Object object) {
            if ((n2 & 1) != 0) {
                uUID = dataSnapshot.currentUser;
            }
            if ((n2 & 2) != 0) {
                map = dataSnapshot.pokeBalls;
            }
            if ((n2 & 4) != 0) {
                n = dataSnapshot.healTimeLeft;
            }
            return dataSnapshot.copy(uUID, map, n);
        }

        @NotNull
        public String toString() {
            return "DataSnapshot(currentUser=" + this.currentUser + ", pokeBalls=" + this.pokeBalls + ", healTimeLeft=" + this.healTimeLeft + ")";
        }

        public int hashCode() {
            int result = this.currentUser == null ? 0 : this.currentUser.hashCode();
            result = result * 31 + ((Object)this.pokeBalls).hashCode();
            result = result * 31 + Integer.hashCode(this.healTimeLeft);
            return result;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof DataSnapshot)) {
                return false;
            }
            DataSnapshot dataSnapshot = (DataSnapshot)other;
            if (!Intrinsics.areEqual((Object)this.currentUser, (Object)dataSnapshot.currentUser)) {
                return false;
            }
            if (!Intrinsics.areEqual(this.pokeBalls, dataSnapshot.pokeBalls)) {
                return false;
            }
            return this.healTimeLeft == dataSnapshot.healTimeLeft;
        }
    }
}

