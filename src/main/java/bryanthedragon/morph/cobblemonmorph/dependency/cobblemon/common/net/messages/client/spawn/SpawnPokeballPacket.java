/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
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

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.PokeBalls;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.spawn.SpawnExtraDataEntityPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.PokeBall;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 #2\u000e\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00020\u0001:\u0001#B%\u0012\u0006\u0010\u001b\u001a\u00020\u001a\u0012\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f\u0012\u0006\u0010 \u001a\u00020\u001f\u00a2\u0006\u0004\b!\u0010\"J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ\u0017\u0010\r\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\r\u0010\u000eR\u001d\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f8\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0016\u001a\u00020\u00158\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019R\u0017\u0010\u001b\u001a\u00020\u001a8\u0006\u00a2\u0006\f\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u001d\u0010\u001e\u00a8\u0006$"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/spawn/SpawnPokeballPacket;", "Lcom/cobblemon/mod/common/net/messages/client/spawn/SpawnExtraDataEntityPacket;", "Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;", "entity", "", "applyData", "(Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;)V", "Lnet/minecraft/world/entity/Entity;", "", "checkType", "(Lnet/minecraft/world/entity/Entity;)Z", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "encodeEntityData", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "", "", "aspects", "Ljava/util/Set;", "getAspects", "()Ljava/util/Set;", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "Lcom/cobblemon/mod/common/pokeball/PokeBall;", "pokeBall", "Lcom/cobblemon/mod/common/pokeball/PokeBall;", "getPokeBall", "()Lcom/cobblemon/mod/common/pokeball/PokeBall;", "Lnet/minecraft/network/protocol/game/ClientboundAddEntityPacket;", "vanillaSpawnPacket", "<init>", "(Lcom/cobblemon/mod/common/pokeball/PokeBall;Ljava/util/Set;Lnet/minecraft/network/protocol/game/ClientboundAddEntityPacket;)V", "Companion", "common"})
public final class SpawnPokeballPacket
extends SpawnExtraDataEntityPacket<SpawnPokeballPacket, EmptyPokeBallEntity> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final PokeBall pokeBall;
    @NotNull
    private final Set<String> aspects;
    @NotNull
    private final ResourceLocation id;
    @NotNull
    private static final ResourceLocation ID = MiscUtils.cobblemonResource("spawn_empty_pokeball_entity");

    public SpawnPokeballPacket(@NotNull PokeBall pokeBall, @NotNull Set<String> aspects, @NotNull ClientboundAddEntityPacket vanillaSpawnPacket) {
        Intrinsics.checkNotNullParameter((Object)pokeBall, (String)"pokeBall");
        Intrinsics.checkNotNullParameter(aspects, (String)"aspects");
        Intrinsics.checkNotNullParameter((Object)vanillaSpawnPacket, (String)"vanillaSpawnPacket");
        super(vanillaSpawnPacket);
        this.pokeBall = pokeBall;
        this.aspects = aspects;
        this.id = ID;
    }

    @NotNull
    public final PokeBall getPokeBall() {
        return this.pokeBall;
    }

    @NotNull
    public final Set<String> getAspects() {
        return this.aspects;
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public void encodeEntityData(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130085_(this.pokeBall.getName());
        buffer.m_236828_((Collection)this.aspects, (arg_0, arg_1) -> SpawnPokeballPacket.encodeEntityData$lambda$0(buffer, arg_0, arg_1));
    }

    @Override
    public void applyData(@NotNull EmptyPokeBallEntity entity2) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        entity2.setPokeBall(this.pokeBall);
        entity2.setAspects(this.aspects);
    }

    @Override
    public boolean checkType(@NotNull Entity entity2) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        return entity2 instanceof EmptyPokeBallEntity;
    }

    private static final void encodeEntityData$lambda$0(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, String aspect) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        $buffer.m_130070_(aspect);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/spawn/SpawnPokeballPacket$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lcom/cobblemon/mod/common/net/messages/client/spawn/SpawnPokeballPacket;", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/spawn/SpawnPokeballPacket;", "Lnet/minecraft/resources/ResourceLocation;", "ID", "Lnet/minecraft/resources/ResourceLocation;", "getID", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getID() {
            return ID;
        }

        @NotNull
        public final SpawnPokeballPacket decode(@NotNull FriendlyByteBuf buffer) {
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            ResourceLocation resourceLocation = buffer.m_130281_();
            Intrinsics.checkNotNullExpressionValue((Object)resourceLocation, (String)"buffer.readIdentifier()");
            PokeBall pokeBall = PokeBalls.INSTANCE.getPokeBall(resourceLocation);
            Intrinsics.checkNotNull((Object)pokeBall);
            PokeBall pokeBall2 = pokeBall;
            List list = buffer.m_236845_(Companion::decode$lambda$0);
            Intrinsics.checkNotNullExpressionValue((Object)list, (String)"buffer.readList { it.readString() }");
            Set aspects = CollectionsKt.toSet((Iterable)list);
            ClientboundAddEntityPacket vanillaPacket = SpawnExtraDataEntityPacket.Companion.decodeVanillaPacket(buffer);
            return new SpawnPokeballPacket(pokeBall2, aspects, vanillaPacket);
        }

        private static final String decode$lambda$0(FriendlyByteBuf it) {
            return it.m_130277_();
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

