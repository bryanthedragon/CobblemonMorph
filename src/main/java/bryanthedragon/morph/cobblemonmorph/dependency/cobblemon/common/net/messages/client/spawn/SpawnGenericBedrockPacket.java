/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.protocol.game.ClientboundAddEntityPacket
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.spawn;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.EntitySideDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity.GenericBedrockClientDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.generic.GenericBedrockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.spawn.SpawnExtraDataEntityPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt;
import io.netty.buffer.ByteBuf;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 32\u000e\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00020\u0001:\u00013BM\u0012\u0006\u0010\u0016\u001a\u00020\u0015\u0012\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f\u0012\u0006\u0010\"\u001a\u00020!\u0012\u0006\u0010&\u001a\u00020\u001a\u0012\u0006\u0010-\u001a\u00020\u001a\u0012\u0006\u0010\u001b\u001a\u00020\u001a\u0012\u0006\u0010)\u001a\u00020(\u0012\u0006\u00100\u001a\u00020/\u00a2\u0006\u0004\b1\u00102J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ\u0017\u0010\r\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\r\u0010\u000eR\u001d\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f8\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014R\u0017\u0010\u0016\u001a\u00020\u00158\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019R\u0017\u0010\u001b\u001a\u00020\u001a8\u0006\u00a2\u0006\f\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u001d\u0010\u001eR\u001a\u0010\u001f\u001a\u00020\u00158\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001f\u0010\u0017\u001a\u0004\b \u0010\u0019R\u0017\u0010\"\u001a\u00020!8\u0006\u00a2\u0006\f\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%R\u0017\u0010&\u001a\u00020\u001a8\u0006\u00a2\u0006\f\n\u0004\b&\u0010\u001c\u001a\u0004\b'\u0010\u001eR\u0017\u0010)\u001a\u00020(8\u0006\u00a2\u0006\f\n\u0004\b)\u0010*\u001a\u0004\b+\u0010,R\u0017\u0010-\u001a\u00020\u001a8\u0006\u00a2\u0006\f\n\u0004\b-\u0010\u001c\u001a\u0004\b.\u0010\u001e\u00a8\u00064"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/spawn/SpawnGenericBedrockPacket;", "Lcom/cobblemon/mod/common/net/messages/client/spawn/SpawnExtraDataEntityPacket;", "Lcom/cobblemon/mod/common/entity/generic/GenericBedrockEntity;", "entity", "", "applyData", "(Lcom/cobblemon/mod/common/entity/generic/GenericBedrockEntity;)V", "Lnet/minecraft/world/entity/Entity;", "", "checkType", "(Lnet/minecraft/world/entity/Entity;)Z", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "encodeEntityData", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "", "", "aspects", "Ljava/util/Set;", "getAspects", "()Ljava/util/Set;", "Lnet/minecraft/resources/ResourceLocation;", "category", "Lnet/minecraft/resources/ResourceLocation;", "getCategory", "()Lnet/minecraft/resources/ResourceLocation;", "", "height", "F", "getHeight", "()F", "id", "getId", "Lcom/cobblemon/mod/common/entity/PoseType;", "poseType", "Lcom/cobblemon/mod/common/entity/PoseType;", "getPoseType", "()Lcom/cobblemon/mod/common/entity/PoseType;", "scale", "getScale", "", "startAge", "I", "getStartAge", "()I", "width", "getWidth", "Lnet/minecraft/network/protocol/game/ClientboundAddEntityPacket;", "vanillaSpawnPacket", "<init>", "(Lnet/minecraft/resources/ResourceLocation;Ljava/util/Set;Lcom/cobblemon/mod/common/entity/PoseType;FFFILnet/minecraft/network/protocol/game/ClientboundAddEntityPacket;)V", "Companion", "common"})
public final class SpawnGenericBedrockPacket
extends SpawnExtraDataEntityPacket<SpawnGenericBedrockPacket, GenericBedrockEntity> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final ResourceLocation category;
    @NotNull
    private final Set<String> aspects;
    @NotNull
    private final PoseType poseType;
    private final float scale;
    private final float width;
    private final float height;
    private final int startAge;
    @NotNull
    private final ResourceLocation id;
    @NotNull
    private static final ResourceLocation ID = MiscUtilsKt.cobblemonResource("spawn_generic_bedrock_entity");

    public SpawnGenericBedrockPacket(@NotNull ResourceLocation category, @NotNull Set<String> aspects, @NotNull PoseType poseType, float scale, float width, float height, int startAge, @NotNull ClientboundAddEntityPacket vanillaSpawnPacket) {
        Intrinsics.checkNotNullParameter((Object)category, (String)"category");
        Intrinsics.checkNotNullParameter(aspects, (String)"aspects");
        Intrinsics.checkNotNullParameter((Object)((Object)poseType), (String)"poseType");
        Intrinsics.checkNotNullParameter((Object)vanillaSpawnPacket, (String)"vanillaSpawnPacket");
        super(vanillaSpawnPacket);
        this.category = category;
        this.aspects = aspects;
        this.poseType = poseType;
        this.scale = scale;
        this.width = width;
        this.height = height;
        this.startAge = startAge;
        this.id = ID;
    }

    @NotNull
    public final ResourceLocation getCategory() {
        return this.category;
    }

    @NotNull
    public final Set<String> getAspects() {
        return this.aspects;
    }

    @NotNull
    public final PoseType getPoseType() {
        return this.poseType;
    }

    public final float getScale() {
        return this.scale;
    }

    public final float getWidth() {
        return this.width;
    }

    public final float getHeight() {
        return this.height;
    }

    public final int getStartAge() {
        return this.startAge;
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public void encodeEntityData(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130085_(this.category);
        buffer.m_236828_((Collection)this.aspects, (arg_0, arg_1) -> SpawnGenericBedrockPacket.encodeEntityData$lambda$0(buffer, arg_0, arg_1));
        NetExtensionsKt.writeSizedInt((ByteBuf)buffer, IntSize.U_BYTE, this.poseType.ordinal());
        buffer.writeFloat(this.scale);
        buffer.writeFloat(this.width);
        buffer.writeFloat(this.height);
        buffer.writeInt(this.startAge);
    }

    @Override
    public void applyData(@NotNull GenericBedrockEntity entity2) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        entity2.setCategory(this.category);
        entity2.setAspects(this.aspects);
        entity2.m_20088_().m_135381_(GenericBedrockEntity.Companion.getPOSE_TYPE(), (Object)this.poseType);
        entity2.setScale(this.scale);
        entity2.setColliderWidth(this.width);
        entity2.setColliderHeight(this.height);
        entity2.getDelegate().initialize((GenericBedrockEntity)((Entity)entity2));
        entity2.f_19797_ = this.startAge;
        EntitySideDelegate<GenericBedrockEntity> entitySideDelegate = entity2.getDelegate();
        Intrinsics.checkNotNull(entitySideDelegate, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity.GenericBedrockClientDelegate");
        ((GenericBedrockClientDelegate)entitySideDelegate).updateAge(this.startAge);
    }

    @Override
    public boolean checkType(@NotNull Entity entity2) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        return entity2 instanceof GenericBedrockEntity;
    }

    private static final void encodeEntityData$lambda$0(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, String aspect) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        $buffer.m_130070_(aspect);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/spawn/SpawnGenericBedrockPacket$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lcom/cobblemon/mod/common/net/messages/client/spawn/SpawnGenericBedrockPacket;", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/spawn/SpawnGenericBedrockPacket;", "Lnet/minecraft/resources/ResourceLocation;", "ID", "Lnet/minecraft/resources/ResourceLocation;", "getID", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getID() {
            return ID;
        }

        @NotNull
        public final SpawnGenericBedrockPacket decode(@NotNull FriendlyByteBuf buffer) {
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            ResourceLocation category = buffer.m_130281_();
            List list = buffer.m_236845_(Companion::decode$lambda$0);
            Intrinsics.checkNotNullExpressionValue((Object)list, (String)"buffer.readList { it.readString() }");
            Set aspects = CollectionsKt.toSet((Iterable)list);
            PoseType poseType = (PoseType)buffer.m_130066_(PoseType.class);
            float scale = buffer.readFloat();
            float width = buffer.readFloat();
            float height = buffer.readFloat();
            int startAge = buffer.readInt();
            ClientboundAddEntityPacket vanillaPacket = SpawnExtraDataEntityPacket.Companion.decodeVanillaPacket(buffer);
            Intrinsics.checkNotNullExpressionValue((Object)category, (String)"category");
            Intrinsics.checkNotNullExpressionValue((Object)((Object)poseType), (String)"poseType");
            return new SpawnGenericBedrockPacket(category, aspects, poseType, scale, width, height, startAge, vanillaPacket);
        }

        private static final String decode$lambda$0(FriendlyByteBuf it) {
            return it.m_130277_();
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

