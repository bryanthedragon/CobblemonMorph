/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.protocol.game.ClientboundAddEntityPacket
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.spawn;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.PokeBalls;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.spawn.SpawnExtraDataEntityPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.PokeBall;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0005\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 =2\u000e\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00020\u0001:\u0001=B\u0019\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u00109\u001a\u000208\u00a2\u0006\u0004\b:\u0010;B\u009b\u0001\u0012\b\u0010+\u001a\u0004\u0018\u00010\u0013\u0012\u0006\u00101\u001a\u000200\u0012\u0006\u00105\u001a\u000204\u0012\u0006\u0010\u001d\u001a\u00020\u001c\u0012\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f\u0012\b\u0010\u0014\u001a\u0004\u0018\u00010\u0013\u0012\u0006\u0010,\u001a\u00020\u001f\u0012\u0006\u0010\u0017\u001a\u00020\u0016\u0012\b\u0010)\u001a\u0004\u0018\u00010(\u0012\u0006\u0010'\u001a\u00020\u001f\u0012\u0006\u0010.\u001a\u00020-\u0012\u0006\u00107\u001a\u00020\b\u0012\u0006\u0010\"\u001a\u00020\b\u0012\u0006\u0010\u001a\u001a\u00020\u0019\u0012\u0006\u00103\u001a\u000200\u0012\u0006\u0010 \u001a\u00020\u001f\u0012\u0006\u00109\u001a\u000208\u00a2\u0006\u0004\b:\u0010<J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ\u0017\u0010\r\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\r\u0010\u000eR\u001a\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0011\u0010\u0012R\u0016\u0010\u0014\u001a\u0004\u0018\u00010\u00138\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0017\u001a\u00020\u00168\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0017\u0010\u0018R\u0014\u0010\u001a\u001a\u00020\u00198\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001a\u0010\u001bR\u0014\u0010\u001d\u001a\u00020\u001c8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001d\u0010\u001eR\u0014\u0010 \u001a\u00020\u001f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b \u0010!R\u0014\u0010\"\u001a\u00020\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\"\u0010#R\u001a\u0010$\u001a\u00020\u00198\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b$\u0010\u001b\u001a\u0004\b%\u0010&R\u0014\u0010'\u001a\u00020\u001f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b'\u0010!R\u0016\u0010)\u001a\u0004\u0018\u00010(8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b)\u0010*R\u0016\u0010+\u001a\u0004\u0018\u00010\u00138\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b+\u0010\u0015R\u0014\u0010,\u001a\u00020\u001f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b,\u0010!R\u0014\u0010.\u001a\u00020-8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b.\u0010/R\u0014\u00101\u001a\u0002008\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b1\u00102R\u0014\u00103\u001a\u0002008\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b3\u00102R\u0014\u00105\u001a\u0002048\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b5\u00106R\u0014\u00107\u001a\u00020\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b7\u0010#\u00a8\u0006>"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/spawn/SpawnPokemonPacket;", "Lcom/cobblemon/mod/common/net/messages/client/spawn/SpawnExtraDataEntityPacket;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "entity", "", "applyData", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)V", "Lnet/minecraft/world/entity/Entity;", "", "checkType", "(Lnet/minecraft/world/entity/Entity;)Z", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "encodeEntityData", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "", "", "aspects", "Ljava/util/Set;", "Ljava/util/UUID;", "battleId", "Ljava/util/UUID;", "", "beamMode", "B", "Lnet/minecraft/resources/ResourceLocation;", "caughtBall", "Lnet/minecraft/resources/ResourceLocation;", "Lcom/cobblemon/mod/common/pokemon/FormData;", "form", "Lcom/cobblemon/mod/common/pokemon/FormData;", "", "friendship", "I", "hideLabel", "Z", "id", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "labelLevel", "Lnet/minecraft/network/chat/MutableComponent;", "nickname", "Lnet/minecraft/network/chat/MutableComponent;", "ownerId", "phasingTargetId", "Lcom/cobblemon/mod/common/entity/PoseType;", "poseType", "Lcom/cobblemon/mod/common/entity/PoseType;", "", "scaleModifier", "F", "spawnYaw", "Lcom/cobblemon/mod/common/pokemon/Species;", "species", "Lcom/cobblemon/mod/common/pokemon/Species;", "unbattlable", "Lnet/minecraft/network/protocol/game/ClientboundAddEntityPacket;", "vanillaSpawnPacket", "<init>", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;Lnet/minecraft/network/protocol/game/ClientboundAddEntityPacket;)V", "(Ljava/util/UUID;FLcom/cobblemon/mod/common/pokemon/Species;Lcom/cobblemon/mod/common/pokemon/FormData;Ljava/util/Set;Ljava/util/UUID;IBLnet/minecraft/network/chat/MutableComponent;ILcom/cobblemon/mod/common/entity/PoseType;ZZLnet/minecraft/resources/ResourceLocation;FILnet/minecraft/network/protocol/game/ClientboundAddEntityPacket;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nSpawnPokemonPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpawnPokemonPacket.kt\ncom/cobblemon/mod/common/net/messages/client/spawn/SpawnPokemonPacket\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,138:1\n1#2:139\n*E\n"})
public final class SpawnPokemonPacket
extends SpawnExtraDataEntityPacket<SpawnPokemonPacket, PokemonEntity> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @Nullable
    private final UUID ownerId;
    private final float scaleModifier;
    @NotNull
    private final Species species;
    @NotNull
    private final FormData form;
    @NotNull
    private final Set<String> aspects;
    @Nullable
    private final UUID battleId;
    private final int phasingTargetId;
    private final byte beamMode;
    @Nullable
    private final MutableComponent nickname;
    private final int labelLevel;
    @NotNull
    private final PoseType poseType;
    private final boolean unbattlable;
    private final boolean hideLabel;
    @NotNull
    private final ResourceLocation caughtBall;
    private final float spawnYaw;
    private final int friendship;
    @NotNull
    private final ResourceLocation id;
    @NotNull
    private static final ResourceLocation ID = MiscUtils.cobblemonResource("spawn_pokemon_entity");

    public SpawnPokemonPacket(@Nullable UUID ownerId, float scaleModifier, @NotNull Species species, @NotNull FormData form2, @NotNull Set<String> aspects, @Nullable UUID battleId, int phasingTargetId, byte beamMode, @Nullable MutableComponent nickname, int labelLevel, @NotNull PoseType poseType, boolean unbattlable, boolean hideLabel, @NotNull ResourceLocation caughtBall, float spawnYaw, int friendship, @NotNull ClientboundAddEntityPacket vanillaSpawnPacket) {
        Intrinsics.checkNotNullParameter((Object)species, (String)"species");
        Intrinsics.checkNotNullParameter((Object)form2, (String)"form");
        Intrinsics.checkNotNullParameter(aspects, (String)"aspects");
        Intrinsics.checkNotNullParameter((Object)((Object)poseType), (String)"poseType");
        Intrinsics.checkNotNullParameter((Object)caughtBall, (String)"caughtBall");
        Intrinsics.checkNotNullParameter((Object)vanillaSpawnPacket, (String)"vanillaSpawnPacket");
        super(vanillaSpawnPacket);
        this.ownerId = ownerId;
        this.scaleModifier = scaleModifier;
        this.species = species;
        this.form = form2;
        this.aspects = aspects;
        this.battleId = battleId;
        this.phasingTargetId = phasingTargetId;
        this.beamMode = beamMode;
        this.nickname = nickname;
        this.labelLevel = labelLevel;
        this.poseType = poseType;
        this.unbattlable = unbattlable;
        this.hideLabel = hideLabel;
        this.caughtBall = caughtBall;
        this.spawnYaw = spawnYaw;
        this.friendship = friendship;
        this.id = ID;
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return this.id;
    }

    public SpawnPokemonPacket(@NotNull PokemonEntity entity2, @NotNull ClientboundAddEntityPacket vanillaSpawnPacket) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        Intrinsics.checkNotNullParameter((Object)vanillaSpawnPacket, (String)"vanillaSpawnPacket");
        UUID uUID = entity2.m_21805_();
        float f = entity2.getPokemon().getScaleModifier();
        Species species = entity2.getExposedSpecies();
        FormData formData = entity2.getPokemon().getForm();
        Set<String> set2 = entity2.getPokemon().getAspects();
        UUID uUID2 = entity2.getBattleId();
        int n = entity2.getPhasingTargetId();
        byte by = (byte)entity2.getBeamMode();
        MutableComponent mutableComponent = entity2.getPokemon().getNickname();
        Integer n2 = Cobblemon.INSTANCE.getConfig().getDisplayEntityLevelLabel() ? (Integer)entity2.m_20088_().m_135370_(PokemonEntity.Companion.getLABEL_LEVEL()) : Integer.valueOf(-1);
        Intrinsics.checkNotNullExpressionValue((Object)n2, (String)"if (Cobblemon.config.dis\u2026tity.LABEL_LEVEL) else -1");
        int n3 = ((Number)n2).intValue();
        Object object = entity2.m_20088_().m_135370_(PokemonEntity.Companion.getPOSE_TYPE());
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"entity.dataTracker.get(PokemonEntity.POSE_TYPE)");
        PoseType poseType = (PoseType)((Object)object);
        Object object2 = entity2.m_20088_().m_135370_(PokemonEntity.Companion.getUNBATTLEABLE());
        Intrinsics.checkNotNullExpressionValue((Object)object2, (String)"entity.dataTracker.get(PokemonEntity.UNBATTLEABLE)");
        boolean bl = (Boolean)object2;
        Object object3 = entity2.m_20088_().m_135370_(PokemonEntity.Companion.getHIDE_LABEL());
        Intrinsics.checkNotNullExpressionValue((Object)object3, (String)"entity.dataTracker.get(PokemonEntity.HIDE_LABEL)");
        boolean bl2 = (Boolean)object3;
        ResourceLocation resourceLocation = entity2.getPokemon().getCaughtBall().getName();
        Object object4 = entity2.m_20088_().m_135370_(PokemonEntity.Companion.getSPAWN_DIRECTION());
        Intrinsics.checkNotNullExpressionValue((Object)object4, (String)"entity.dataTracker.get(P\u2026onEntity.SPAWN_DIRECTION)");
        float f2 = ((Number)object4).floatValue();
        Object object5 = entity2.m_20088_().m_135370_(PokemonEntity.Companion.getFRIENDSHIP());
        Intrinsics.checkNotNullExpressionValue((Object)object5, (String)"entity.dataTracker.get(PokemonEntity.FRIENDSHIP)");
        this(uUID, f, species, formData, set2, uUID2, n, by, mutableComponent, n3, poseType, bl, bl2, resourceLocation, f2, ((Number)object5).intValue(), vanillaSpawnPacket);
    }

    @Override
    public void encodeEntityData(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_236821_((Object)this.ownerId, (arg_0, arg_1) -> SpawnPokemonPacket.encodeEntityData$lambda$0(buffer, arg_0, arg_1));
        buffer.writeFloat(this.scaleModifier);
        buffer.m_130085_(this.species.getResourceIdentifier());
        buffer.m_130070_(this.form.formOnlyShowdownId());
        buffer.m_236828_((Collection)this.aspects, SpawnPokemonPacket::encodeEntityData$lambda$1);
        buffer.m_236821_((Object)this.battleId, SpawnPokemonPacket::encodeEntityData$lambda$2);
        buffer.writeInt(this.phasingTargetId);
        buffer.writeByte((int)this.beamMode);
        buffer.m_236821_((Object)this.nickname, (arg_0, arg_1) -> SpawnPokemonPacket.encodeEntityData$lambda$3(buffer, arg_0, arg_1));
        buffer.writeInt(this.labelLevel);
        buffer.m_130068_((Enum)this.poseType);
        buffer.writeBoolean(this.unbattlable);
        buffer.writeBoolean(this.hideLabel);
        buffer.m_130085_(this.caughtBall);
        buffer.writeFloat(this.spawnYaw);
        buffer.writeInt(this.friendship);
    }

    @Override
    public void applyData(@NotNull PokemonEntity entity2) {
        Pokemon pokemon;
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        entity2.m_21816_(this.ownerId);
        Pokemon $this$applyData_u24lambda_u245 = pokemon = entity2.getPokemon();
        boolean bl = false;
        $this$applyData_u24lambda_u245.setScaleModifier(this.scaleModifier);
        $this$applyData_u24lambda_u245.setSpecies(this.species);
        $this$applyData_u24lambda_u245.setForm(this.form);
        $this$applyData_u24lambda_u245.setAspects(this.aspects);
        $this$applyData_u24lambda_u245.setNickname(this.nickname);
        PokeBall pokeBall = PokeBalls.INSTANCE.getPokeBall(this.caughtBall);
        if (pokeBall != null) {
            PokeBall it = pokeBall;
            boolean bl2 = false;
            $this$applyData_u24lambda_u245.setCaughtBall(it);
        }
        entity2.setPhasingTargetId(this.phasingTargetId);
        entity2.setBeamMode(this.beamMode);
        entity2.setBattleId(this.battleId);
        entity2.m_20088_().m_135381_(PokemonEntity.Companion.getLABEL_LEVEL(), (Object)this.labelLevel);
        entity2.m_20088_().m_135381_(PokemonEntity.Companion.getSPECIES(), (Object)entity2.getPokemon().getSpecies().getResourceIdentifier().toString());
        entity2.m_20088_().m_135381_(PokemonEntity.Companion.getASPECTS(), this.aspects);
        entity2.m_20088_().m_135381_(PokemonEntity.Companion.getPOSE_TYPE(), (Object)this.poseType);
        entity2.m_20088_().m_135381_(PokemonEntity.Companion.getUNBATTLEABLE(), (Object)this.unbattlable);
        entity2.m_20088_().m_135381_(PokemonEntity.Companion.getHIDE_LABEL(), (Object)this.hideLabel);
        entity2.m_20088_().m_135381_(PokemonEntity.Companion.getSPAWN_DIRECTION(), (Object)Float.valueOf(this.spawnYaw));
        entity2.m_20088_().m_135381_(PokemonEntity.Companion.getFRIENDSHIP(), (Object)this.friendship);
    }

    @Override
    public boolean checkType(@NotNull Entity entity2) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        return entity2 instanceof PokemonEntity;
    }

    private static final void encodeEntityData$lambda$0(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, UUID v) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        $buffer.m_130077_(v);
    }

    private static final void encodeEntityData$lambda$1(FriendlyByteBuf pb, String value2) {
        pb.m_130070_(value2);
    }

    private static final void encodeEntityData$lambda$2(FriendlyByteBuf pb, UUID value2) {
        pb.m_130077_(value2);
    }

    private static final void encodeEntityData$lambda$3(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, MutableComponent v) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        $buffer.m_130083_((Component)v);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/spawn/SpawnPokemonPacket$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lcom/cobblemon/mod/common/net/messages/client/spawn/SpawnPokemonPacket;", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/spawn/SpawnPokemonPacket;", "Lnet/minecraft/resources/ResourceLocation;", "ID", "Lnet/minecraft/resources/ResourceLocation;", "getID", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    @SourceDebugExtension(value={"SMAP\nSpawnPokemonPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpawnPokemonPacket.kt\ncom/cobblemon/mod/common/net/messages/client/spawn/SpawnPokemonPacket$Companion\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,138:1\n288#2,2:139\n*S KotlinDebug\n*F\n+ 1 SpawnPokemonPacket.kt\ncom/cobblemon/mod/common/net/messages/client/spawn/SpawnPokemonPacket$Companion\n*L\n119#1:139,2\n*E\n"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getID() {
            return ID;
        }

        @NotNull
        public final SpawnPokemonPacket decode(@NotNull FriendlyByteBuf buffer) {
            FormData formData;
            Object v2;
            Species species;
            float scaleModifier;
            UUID ownerId;
            block2: {
                Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
                ownerId = (UUID)buffer.m_236868_(arg_0 -> Companion.decode$lambda$0(buffer, arg_0));
                scaleModifier = buffer.readFloat();
                ResourceLocation resourceLocation = buffer.m_130281_();
                Intrinsics.checkNotNullExpressionValue((Object)resourceLocation, (String)"buffer.readIdentifier()");
                Species species2 = PokemonSpecies.INSTANCE.getByIdentifier(resourceLocation);
                Intrinsics.checkNotNull((Object)species2);
                species = species2;
                String showdownId = buffer.m_130277_();
                Iterable $this$firstOrNull$iv = species.getForms();
                boolean $i$f$firstOrNull = false;
                for (Object element$iv : $this$firstOrNull$iv) {
                    FormData it = (FormData)element$iv;
                    boolean bl = false;
                    if (!Intrinsics.areEqual((Object)it.formOnlyShowdownId(), (Object)showdownId)) continue;
                    v2 = element$iv;
                    break block2;
                }
                v2 = null;
            }
            if ((formData = (FormData)v2) == null) {
                formData = species.getStandardForm();
            }
            FormData form2 = formData;
            List list = buffer.m_236845_(FriendlyByteBuf::m_130277_);
            Intrinsics.checkNotNullExpressionValue((Object)list, (String)"buffer.readList(PacketByteBuf::readString)");
            Set aspects = CollectionsKt.toSet((Iterable)list);
            UUID battleId = (UUID)buffer.m_236868_(arg_0 -> Companion.decode$lambda$2(buffer, arg_0));
            int phasingTargetId = buffer.readInt();
            byte beamModeEmitter = buffer.readByte();
            MutableComponent nickname = (MutableComponent)buffer.m_236868_(arg_0 -> Companion.decode$lambda$3(buffer, arg_0));
            int labelLevel = buffer.readInt();
            PoseType poseType = (PoseType)buffer.m_130066_(PoseType.class);
            boolean unbattlable = buffer.readBoolean();
            boolean hideLabel = buffer.readBoolean();
            ResourceLocation caughtBall = buffer.m_130281_();
            float spawnAngle = buffer.readFloat();
            int friendship = buffer.readInt();
            ClientboundAddEntityPacket vanillaPacket = SpawnExtraDataEntityPacket.Companion.decodeVanillaPacket(buffer);
            Intrinsics.checkNotNullExpressionValue((Object)((Object)poseType), (String)"poseType");
            Intrinsics.checkNotNullExpressionValue((Object)caughtBall, (String)"caughtBall");
            return new SpawnPokemonPacket(ownerId, scaleModifier, species, form2, aspects, battleId, phasingTargetId, beamModeEmitter, nickname, labelLevel, poseType, unbattlable, hideLabel, caughtBall, spawnAngle, friendship, vanillaPacket);
        }

        private static final UUID decode$lambda$0(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
            Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
            return $buffer.m_130259_();
        }

        private static final UUID decode$lambda$2(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
            Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
            return $buffer.m_130259_();
        }

        private static final MutableComponent decode$lambda$3(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
            Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
            return $buffer.m_130238_().m_6881_();
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

