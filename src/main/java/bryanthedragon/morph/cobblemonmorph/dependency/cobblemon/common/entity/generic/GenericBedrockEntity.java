/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.StringTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientGamePacketListener
 *  net.minecraft.network.protocol.game.ClientboundAddEntityPacket
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityDimensions
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.level.Level
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.generic;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonEntities;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.EntitySideDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.serializers.IdentifierDataSerializer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.serializers.PoseTypeDataSerializer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.serializers.StringSetDataSerializer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.Schedulable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ScheduledTask;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingTracker;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity.GenericBedrockClientDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.Poseable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.generic.GenericBedrockServerDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.spawn.SpawnGenericBedrockPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000~\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 P2\u00020\u00012\u00020\u00022\u00020\u0003:\u0001PB\u000f\u0012\u0006\u0010M\u001a\u00020L\u00a2\u0006\u0004\bN\u0010OJ\u000f\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0015\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ\u000f\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\f\u0010\rJ\u001f\u0010\u0012\u001a\n \u0011*\u0004\u0018\u00010\u00100\u00102\u0006\u0010\u000f\u001a\u00020\u000eH\u0016\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u000f\u0010\u0015\u001a\u00020\u0014H\u0014\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u000f\u0010\u0017\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0017\u0010\u0006J\u0017\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u0018H\u0014\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u000f\u0010\u001c\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u001c\u0010\u0006J\u000f\u0010\u001d\u001a\u00020\u0014H\u0016\u00a2\u0006\u0004\b\u001d\u0010\u0016J\u0017\u0010\u001e\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u0018H\u0014\u00a2\u0006\u0004\b\u001e\u0010\u001bR0\u0010&\u001a\b\u0012\u0004\u0012\u00020 0\u001f2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020 0\u001f8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R$\u0010,\u001a\u00020'2\u0006\u0010!\u001a\u00020'8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R*\u0010.\u001a\u00020-2\u0006\u0010!\u001a\u00020-8\u0006@FX\u0086\u000e\u00a2\u0006\u0012\n\u0004\b.\u0010/\u001a\u0004\b0\u00101\"\u0004\b2\u00103R*\u00104\u001a\u00020-2\u0006\u0010!\u001a\u00020-8\u0006@FX\u0086\u000e\u00a2\u0006\u0012\n\u0004\b4\u0010/\u001a\u0004\b5\u00101\"\u0004\b6\u00103R \u00108\u001a\b\u0012\u0004\u0012\u00020\u0000078\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b8\u00109\u001a\u0004\b:\u0010;R\"\u0010<\u001a\u00020\u00048\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b<\u0010=\u001a\u0004\b>\u0010\u0006\"\u0004\b?\u0010@R$\u0010C\u001a\u00020-2\u0006\u0010!\u001a\u00020-8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\bA\u00101\"\u0004\bB\u00103R\u001a\u0010E\u001a\u00020D8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\bE\u0010F\u001a\u0004\bG\u0010HR\"\u0010I\u001a\u00020\u00048\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bI\u0010=\u001a\u0004\bJ\u0010\u0006\"\u0004\bK\u0010@\u00a8\u0006Q"}, d2={"Lcom/cobblemon/mod/common/entity/generic/GenericBedrockEntity;", "Lnet/minecraft/world/entity/Entity;", "Lcom/cobblemon/mod/common/entity/Poseable;", "Lcom/cobblemon/mod/common/api/scheduling/Schedulable;", "", "canHit", "()Z", "Lnet/minecraft/network/protocol/Packet;", "Lnet/minecraft/network/protocol/game/ClientGamePacketListener;", "createSpawnPacket", "()Lnet/minecraft/network/protocol/Packet;", "Lcom/cobblemon/mod/common/entity/PoseType;", "getCurrentPoseType", "()Lcom/cobblemon/mod/common/entity/PoseType;", "Lnet/minecraft/world/entity/Pose;", "pose", "Lnet/minecraft/world/entity/EntityDimensions;", "kotlin.jvm.PlatformType", "getDimensions", "(Lnet/minecraft/world/entity/Pose;)Lnet/minecraft/world/entity/EntityDimensions;", "", "initDataTracker", "()V", "isCollidable", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "readCustomDataFromNbt", "(Lnet/minecraft/nbt/CompoundTag;)V", "shouldSave", "tick", "writeCustomDataToNbt", "", "", "value", "getAspects", "()Ljava/util/Set;", "setAspects", "(Ljava/util/Set;)V", "aspects", "Lnet/minecraft/resources/ResourceLocation;", "getCategory", "()Lnet/minecraft/resources/ResourceLocation;", "setCategory", "(Lnet/minecraft/resources/ResourceLocation;)V", "category", "", "colliderHeight", "F", "getColliderHeight", "()F", "setColliderHeight", "(F)V", "colliderWidth", "getColliderWidth", "setColliderWidth", "Lcom/cobblemon/mod/common/api/entity/EntitySideDelegate;", "delegate", "Lcom/cobblemon/mod/common/api/entity/EntitySideDelegate;", "getDelegate", "()Lcom/cobblemon/mod/common/api/entity/EntitySideDelegate;", "savesToWorld", "Z", "getSavesToWorld", "setSavesToWorld", "(Z)V", "getScale", "setScale", "scale", "Lcom/cobblemon/mod/common/api/scheduling/SchedulingTracker;", "schedulingTracker", "Lcom/cobblemon/mod/common/api/scheduling/SchedulingTracker;", "getSchedulingTracker", "()Lcom/cobblemon/mod/common/api/scheduling/SchedulingTracker;", "syncAge", "getSyncAge", "setSyncAge", "Lnet/minecraft/world/level/Level;", "world", "<init>", "(Lnet/minecraft/world/level/Level;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nGenericBedrockEntity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 GenericBedrockEntity.kt\ncom/cobblemon/mod/common/entity/generic/GenericBedrockEntity\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,138:1\n1549#2:139\n1620#2,3:140\n1549#2:144\n1620#2,3:145\n1#3:143\n*S KotlinDebug\n*F\n+ 1 GenericBedrockEntity.kt\ncom/cobblemon/mod/common/entity/generic/GenericBedrockEntity\n*L\n95#1:139\n95#1:140,3\n105#1:144\n105#1:145,3\n*E\n"})
public final class GenericBedrockEntity
extends Entity
implements Poseable,
Schedulable {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private boolean savesToWorld;
    @NotNull
    private final SchedulingTracker schedulingTracker;
    @NotNull
    private final EntitySideDelegate<GenericBedrockEntity> delegate;
    private float colliderWidth;
    private float colliderHeight;
    private boolean syncAge;
    private static final EntityDataAccessor<ResourceLocation> CATEGORY = SynchedEntityData.m_135353_(GenericBedrockEntity.class, (EntityDataSerializer)IdentifierDataSerializer.INSTANCE);
    private static final EntityDataAccessor<Set<String>> ASPECTS = SynchedEntityData.m_135353_(GenericBedrockEntity.class, (EntityDataSerializer)StringSetDataSerializer.INSTANCE);
    private static final EntityDataAccessor<PoseType> POSE_TYPE = SynchedEntityData.m_135353_(GenericBedrockEntity.class, (EntityDataSerializer)PoseTypeDataSerializer.INSTANCE);
    private static final EntityDataAccessor<Float> SCALE = SynchedEntityData.m_135353_(GenericBedrockEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135029_);

    public GenericBedrockEntity(@NotNull Level world) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        super(CobblemonEntities.GENERIC_BEDROCK_ENTITY, world);
        this.schedulingTracker = new SchedulingTracker();
        this.delegate = world.f_46443_ ? (EntitySideDelegate)new GenericBedrockClientDelegate() : (EntitySideDelegate)new GenericBedrockServerDelegate();
        this.colliderWidth = 1.0f;
        this.colliderHeight = 1.0f;
    }

    public final boolean getSavesToWorld() {
        return this.savesToWorld;
    }

    public final void setSavesToWorld(boolean bl) {
        this.savesToWorld = bl;
    }

    @Override
    @NotNull
    public SchedulingTracker getSchedulingTracker() {
        return this.schedulingTracker;
    }

    @NotNull
    public EntitySideDelegate<GenericBedrockEntity> getDelegate() {
        return this.delegate;
    }

    @NotNull
    public final ResourceLocation getCategory() {
        Object object = this.f_19804_.m_135370_(CATEGORY);
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"this.dataTracker.get(CATEGORY)");
        return (ResourceLocation)object;
    }

    public final void setCategory(@NotNull ResourceLocation value2) {
        Intrinsics.checkNotNullParameter((Object)value2, (String)"value");
        this.f_19804_.m_135381_(CATEGORY, (Object)value2);
    }

    @NotNull
    public final Set<String> getAspects() {
        Object object = this.f_19804_.m_135370_(ASPECTS);
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"this.dataTracker.get(ASPECTS)");
        return (Set)object;
    }

    public final void setAspects(@NotNull Set<String> value2) {
        Intrinsics.checkNotNullParameter(value2, (String)"value");
        this.f_19804_.m_135381_(ASPECTS, value2);
    }

    public final float getScale() {
        Object object = this.f_19804_.m_135370_(SCALE);
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"this.dataTracker.get(SCALE)");
        return ((Number)object).floatValue();
    }

    public final void setScale(float value2) {
        this.f_19804_.m_135381_(SCALE, (Object)Float.valueOf(value2));
    }

    public final float getColliderWidth() {
        return this.colliderWidth;
    }

    public final void setColliderWidth(float value2) {
        super.m_20205_();
        this.colliderWidth = value2;
        this.m_6210_();
    }

    public final float getColliderHeight() {
        return this.colliderHeight;
    }

    public final void setColliderHeight(float value2) {
        this.colliderHeight = value2;
        this.m_6210_();
    }

    public final boolean getSyncAge() {
        return this.syncAge;
    }

    public final void setSyncAge(boolean bl) {
        this.syncAge = bl;
    }

    protected void m_8097_() {
        this.f_19804_.m_135372_(CATEGORY, (Object)MiscUtils.cobblemonResource("generic"));
        this.f_19804_.m_135372_(ASPECTS, (Object)SetsKt.emptySet());
        this.f_19804_.m_135372_(SCALE, (Object)Float.valueOf(1.0f));
        this.f_19804_.m_135372_(POSE_TYPE, (Object)PoseType.NONE);
    }

    /*
     * WARNING - void declaration
     */
    protected void m_7378_(@NotNull CompoundTag nbt) {
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        this.setCategory(new ResourceLocation(nbt.m_128461_("Category")));
        ListTag listTag = nbt.m_128437_("Aspects", 8);
        Intrinsics.checkNotNullExpressionValue((Object)listTag, (String)"nbt.getList(DataKeys.GEN\u2026ring.STRING_TYPE.toInt())");
        Iterable iterable = (Iterable)listTag;
        GenericBedrockEntity genericBedrockEntity = this;
        boolean $i$f$map = false;
        void var4_5 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            Tag tag = (Tag)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            collection.add(it.m_7916_());
        }
        genericBedrockEntity.setAspects(CollectionsKt.toSet((Iterable)((List)destination$iv$iv)));
        this.f_19804_.m_135381_(POSE_TYPE, (Object)PoseType.values()[nbt.m_128445_("PoseType")]);
        this.setScale(nbt.m_128457_("Scale"));
        this.setColliderWidth(nbt.m_128457_("Width"));
        this.setColliderHeight(nbt.m_128457_("Height"));
        this.syncAge = nbt.m_128471_("SyncAge");
    }

    /*
     * WARNING - void declaration
     */
    protected void m_7380_(@NotNull CompoundTag nbt) {
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        void it;
        ListTag listTag;
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        nbt.m_128359_("Category", this.getCategory().toString());
        ListTag listTag2 = listTag = new ListTag();
        String string = "Aspects";
        CompoundTag compoundTag = nbt;
        boolean bl = false;
        Iterable iterable = this.getAspects();
        void var6_8 = it;
        boolean $i$f$map = false;
        void var8_10 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void p0;
            String string2 = (String)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl2 = false;
            collection.add(StringTag.m_129297_((String)p0));
        }
        var6_8.addAll((Collection)((List)destination$iv$iv));
        Unit unit = Unit.INSTANCE;
        compoundTag.m_128365_(string, (Tag)listTag);
        nbt.m_128344_("PoseType", (byte)this.getCurrentPoseType().ordinal());
        nbt.m_128350_("Scale", this.getScale());
        nbt.m_128350_("Width", this.colliderWidth);
        nbt.m_128350_("Height", this.colliderHeight);
        nbt.m_128379_("SyncAge", this.syncAge);
    }

    public boolean m_6087_() {
        return true;
    }

    public boolean m_5829_() {
        return true;
    }

    public boolean m_142391_() {
        return super.m_142391_() && this.savesToWorld;
    }

    public EntityDimensions m_6972_(@NotNull Pose pose) {
        Intrinsics.checkNotNullParameter((Object)pose, (String)"pose");
        return EntityDimensions.m_20395_((float)this.colliderWidth, (float)this.colliderHeight).m_20388_(this.getScale());
    }

    @Override
    @NotNull
    public PoseType getCurrentPoseType() {
        Object object = this.f_19804_.m_135370_(POSE_TYPE);
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"this.dataTracker.get(POSE_TYPE)");
        return (PoseType)((Object)object);
    }

    @NotNull
    public Packet<ClientGamePacketListener> m_5654_() {
        ResourceLocation resourceLocation = this.getCategory();
        Set<String> set2 = this.getAspects();
        PoseType poseType = this.getCurrentPoseType();
        float f = this.getScale();
        int n = this.syncAge ? this.f_19797_ : 0;
        Packet packet = super.m_5654_();
        Intrinsics.checkNotNull((Object)packet, (String)"null cannot be cast to non-null type net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket");
        return CobblemonNetwork.INSTANCE.asVanillaClientBound((NetworkPacket)new SpawnGenericBedrockPacket(resourceLocation, set2, poseType, f, this.colliderWidth, this.colliderHeight, n, (ClientboundAddEntityPacket)packet));
    }

    public void m_8119_() {
        super.m_8119_();
        this.getDelegate().tick((GenericBedrockEntity)((Entity)this));
        this.getSchedulingTracker().update(0.05f);
    }

    @Override
    @NotNull
    public ScheduledTask momentarily(@NotNull Function0<Unit> action2) {
        return Schedulable.DefaultImpls.momentarily(this, action2);
    }

    @Override
    @NotNull
    public ScheduledTask after(float seconds, @NotNull Function0<Unit> action2) {
        return Schedulable.DefaultImpls.after(this, seconds, action2);
    }

    @Override
    @NotNull
    public ScheduledTask lerp(float seconds, @NotNull Function1<? super Float, Unit> action2) {
        return Schedulable.DefaultImpls.lerp(this, seconds, action2);
    }

    @Override
    @NotNull
    public ScheduledTask.Builder taskBuilder() {
        return Schedulable.DefaultImpls.taskBuilder(this);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0013\u0010\u0014RS\u0010\u0006\u001a>\u0012\u0018\u0012\u0016\u0012\u0004\u0012\u00020\u0004 \u0005*\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00030\u0003 \u0005*\u001e\u0012\u0018\u0012\u0016\u0012\u0004\u0012\u00020\u0004 \u0005*\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00030\u0003\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0006\u0010\u0007\u001a\u0004\b\b\u0010\tR;\u0010\u000b\u001a&\u0012\f\u0012\n \u0005*\u0004\u0018\u00010\n0\n \u0005*\u0012\u0012\f\u0012\n \u0005*\u0004\u0018\u00010\n0\n\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\u0007\u001a\u0004\b\f\u0010\tR;\u0010\u000e\u001a&\u0012\f\u0012\n \u0005*\u0004\u0018\u00010\r0\r \u0005*\u0012\u0012\f\u0012\n \u0005*\u0004\u0018\u00010\r0\r\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u0007\u001a\u0004\b\u000f\u0010\tR;\u0010\u0011\u001a&\u0012\f\u0012\n \u0005*\u0004\u0018\u00010\u00100\u0010 \u0005*\u0012\u0012\f\u0012\n \u0005*\u0004\u0018\u00010\u00100\u0010\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0007\u001a\u0004\b\u0012\u0010\t\u00a8\u0006\u0015"}, d2={"Lcom/cobblemon/mod/common/entity/generic/GenericBedrockEntity$Companion;", "", "Lnet/minecraft/network/syncher/EntityDataAccessor;", "", "", "kotlin.jvm.PlatformType", "ASPECTS", "Lnet/minecraft/network/syncher/EntityDataAccessor;", "getASPECTS", "()Lnet/minecraft/network/syncher/EntityDataAccessor;", "Lnet/minecraft/resources/ResourceLocation;", "CATEGORY", "getCATEGORY", "Lcom/cobblemon/mod/common/entity/PoseType;", "POSE_TYPE", "getPOSE_TYPE", "", "SCALE", "getSCALE", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public final EntityDataAccessor<ResourceLocation> getCATEGORY() {
            return CATEGORY;
        }

        public final EntityDataAccessor<Set<String>> getASPECTS() {
            return ASPECTS;
        }

        public final EntityDataAccessor<PoseType> getPOSE_TYPE() {
            return POSE_TYPE;
        }

        public final EntityDataAccessor<Float> getSCALE() {
            return SCALE;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

