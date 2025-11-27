/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.effect.ShoulderEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate.ActivePokemonState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate.PokemonState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CompoundTagExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.WorldExtensionsKt;
import com.google.gson.JsonObject;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0010\u0018\u00002\u00020\u0001B!\b\u0016\u0012\u0006\u00101\u001a\u000200\u0012\u0006\u0010+\u001a\u00020\t\u0012\u0006\u00107\u001a\u000200\u00a2\u0006\u0004\b>\u0010?B\u0007\u00a2\u0006\u0004\b>\u0010\u001dJ\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\n\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\u0007H\u0002\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0015\u0010\u000e\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\f\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0017\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0011\u001a\u00020\u0010H\u0016\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0017\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0015H\u0016\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u0017\u0010\u0019\u001a\u00020\u00122\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\u000f\u0010\u001c\u001a\u00020\u001bH\u0016\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u0017\u0010\u001e\u001a\u00020\u001b2\u0006\u0010\r\u001a\u00020\fH\u0002\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u0017\u0010 \u001a\u00020\u001b2\u0006\u0010\u0011\u001a\u00020\u0010H\u0016\u00a2\u0006\u0004\b \u0010!J\u0019\u0010\"\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u0015H\u0016\u00a2\u0006\u0004\b\"\u0010#J\u0017\u0010$\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b$\u0010%R\u001c\u0010'\u001a\u0004\u0018\u00010&8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b'\u0010(\u001a\u0004\b)\u0010*R\"\u0010+\u001a\u00020\t8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b+\u0010,\u001a\u0004\b+\u0010-\"\u0004\b.\u0010/R\"\u00101\u001a\u0002008\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b1\u00102\u001a\u0004\b3\u00104\"\u0004\b5\u00106R\"\u00107\u001a\u0002008\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b7\u00102\u001a\u0004\b8\u00104\"\u0004\b9\u00106R*\u0010;\u001a\n :*\u0004\u0018\u000100008\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b;\u00102\u001a\u0004\b<\u00104\"\u0004\b=\u00106\u00a8\u0006@"}, d2={"Lcom/cobblemon/mod/common/pokemon/activestate/ShoulderedState;", "Lcom/cobblemon/mod/common/pokemon/activestate/ActivePokemonState;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lnet/minecraft/resources/ResourceLocation;", "getIcon", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Lnet/minecraft/resources/ResourceLocation;", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "", "isShoulderedPokemon", "(Lnet/minecraft/nbt/CompoundTag;)Z", "Lnet/minecraft/server/level/ServerPlayer;", "player", "isStillShouldered", "(Lnet/minecraft/server/level/ServerPlayer;)Z", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lcom/cobblemon/mod/common/pokemon/activestate/PokemonState;", "readFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/pokemon/activestate/PokemonState;", "Lcom/google/gson/JsonObject;", "json", "readFromJSON", "(Lcom/google/gson/JsonObject;)Lcom/cobblemon/mod/common/pokemon/activestate/PokemonState;", "readFromNBT", "(Lnet/minecraft/nbt/CompoundTag;)Lcom/cobblemon/mod/common/pokemon/activestate/PokemonState;", "", "recall", "()V", "removeShoulderEffects", "(Lnet/minecraft/server/level/ServerPlayer;)V", "writeToBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "writeToJSON", "(Lcom/google/gson/JsonObject;)Lcom/google/gson/JsonObject;", "writeToNBT", "(Lnet/minecraft/nbt/CompoundTag;)Lnet/minecraft/nbt/CompoundTag;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "entity", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "getEntity", "()Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "isLeftShoulder", "Z", "()Z", "setLeftShoulder", "(Z)V", "Ljava/util/UUID;", "playerUUID", "Ljava/util/UUID;", "getPlayerUUID", "()Ljava/util/UUID;", "setPlayerUUID", "(Ljava/util/UUID;)V", "pokemonUUID", "getPokemonUUID", "setPokemonUUID", "kotlin.jvm.PlatformType", "stateId", "getStateId", "setStateId", "<init>", "(Ljava/util/UUID;ZLjava/util/UUID;)V", "common"})
@SourceDebugExtension(value={"SMAP\nPokemonState.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonState.kt\ncom/cobblemon/mod/common/pokemon/activestate/ShoulderedState\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,202:1\n1#2:203\n1855#3,2:204\n*S KotlinDebug\n*F\n+ 1 PokemonState.kt\ncom/cobblemon/mod/common/pokemon/activestate/ShoulderedState\n*L\n193#1:204,2\n*E\n"})
public final class ShoulderedState
extends ActivePokemonState {
    private boolean isLeftShoulder;
    public UUID playerUUID;
    public UUID pokemonUUID;
    private UUID stateId;
    @Nullable
    private final PokemonEntity entity;

    public ShoulderedState() {
        super(null);
        this.stateId = UUID.randomUUID();
    }

    public final boolean isLeftShoulder() {
        return this.isLeftShoulder;
    }

    public final void setLeftShoulder(boolean bl) {
        this.isLeftShoulder = bl;
    }

    @NotNull
    public final UUID getPlayerUUID() {
        UUID uUID = this.playerUUID;
        if (uUID != null) {
            return uUID;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"playerUUID");
        return null;
    }

    public final void setPlayerUUID(@NotNull UUID uUID) {
        Intrinsics.checkNotNullParameter((Object)uUID, (String)"<set-?>");
        this.playerUUID = uUID;
    }

    @NotNull
    public final UUID getPokemonUUID() {
        UUID uUID = this.pokemonUUID;
        if (uUID != null) {
            return uUID;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"pokemonUUID");
        return null;
    }

    public final void setPokemonUUID(@NotNull UUID uUID) {
        Intrinsics.checkNotNullParameter((Object)uUID, (String)"<set-?>");
        this.pokemonUUID = uUID;
    }

    public final UUID getStateId() {
        return this.stateId;
    }

    public final void setStateId(UUID uUID) {
        this.stateId = uUID;
    }

    public ShoulderedState(@NotNull UUID playerUUID, boolean isLeftShoulder, @NotNull UUID pokemonUUID) {
        Intrinsics.checkNotNullParameter((Object)playerUUID, (String)"playerUUID");
        Intrinsics.checkNotNullParameter((Object)pokemonUUID, (String)"pokemonUUID");
        this();
        this.isLeftShoulder = isLeftShoulder;
        this.setPlayerUUID(playerUUID);
        this.setPokemonUUID(pokemonUUID);
    }

    @Override
    @Nullable
    public PokemonEntity getEntity() {
        return this.entity;
    }

    @Override
    @NotNull
    public ResourceLocation getIcon(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        String suffix = this.isLeftShoulder ? "left" : "right";
        return MiscUtils.cobblemonResource("textures/gui/party/party_icon_shoulder_" + suffix + ".png");
    }

    @Override
    @NotNull
    public CompoundTag writeToNBT(@NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        super.writeToNBT(nbt);
        nbt.m_128379_("StateShoulder", this.isLeftShoulder);
        nbt.m_128362_("PlayerUUID", this.getPlayerUUID());
        nbt.m_128362_("StateId", this.stateId);
        nbt.m_128362_("PokemonUUID", this.getPokemonUUID());
        return nbt;
    }

    @Override
    @NotNull
    public PokemonState readFromNBT(@NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        super.readFromNBT(nbt);
        this.isLeftShoulder = nbt.m_128471_("StateShoulder");
        UUID uUID = nbt.m_128342_("PlayerUUID");
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"nbt.getUuid(DataKeys.POKEMON_STATE_PLAYER_UUID)");
        this.setPlayerUUID(uUID);
        this.stateId = nbt.m_128342_("StateId");
        UUID uUID2 = nbt.m_128342_("PokemonUUID");
        Intrinsics.checkNotNullExpressionValue((Object)uUID2, (String)"nbt.getUuid(DataKeys.POKEMON_STATE_POKEMON_UUID)");
        this.setPokemonUUID(uUID2);
        return this;
    }

    @Override
    @Nullable
    public JsonObject writeToJSON(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        super.writeToJSON(json);
        json.addProperty("StateShoulder", Boolean.valueOf(this.isLeftShoulder));
        json.addProperty("PlayerUUID", this.getPlayerUUID().toString());
        json.addProperty("StateId", this.stateId.toString());
        json.addProperty("PokemonUUID", this.getPokemonUUID().toString());
        return json;
    }

    @Override
    @NotNull
    public PokemonState readFromJSON(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        super.readFromJSON(json);
        this.isLeftShoulder = json.get("StateShoulder").getAsBoolean();
        UUID uUID = UUID.fromString(json.get("PlayerUUID").getAsString());
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"fromString(json.get(Data\u2026TE_PLAYER_UUID).asString)");
        this.setPlayerUUID(uUID);
        this.stateId = UUID.fromString(json.get("StateId").getAsString());
        UUID uUID2 = UUID.fromString(json.get("PokemonUUID").getAsString());
        Intrinsics.checkNotNullExpressionValue((Object)uUID2, (String)"fromString(json.get(Data\u2026E_POKEMON_UUID).asString)");
        this.setPokemonUUID(uUID2);
        return this;
    }

    @Override
    public void writeToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        super.writeToBuffer(buffer);
        buffer.writeBoolean(this.isLeftShoulder);
        buffer.m_130077_(this.getPlayerUUID());
        buffer.m_130077_(this.stateId);
        buffer.m_130077_(this.getPokemonUUID());
    }

    @Override
    @NotNull
    public PokemonState readFromBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        super.readFromBuffer(buffer);
        this.isLeftShoulder = buffer.readBoolean();
        UUID uUID = buffer.m_130259_();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"buffer.readUuid()");
        this.setPlayerUUID(uUID);
        this.stateId = buffer.m_130259_();
        UUID uUID2 = buffer.m_130259_();
        Intrinsics.checkNotNullExpressionValue((Object)uUID2, (String)"buffer.readUuid()");
        this.setPokemonUUID(uUID2);
        return this;
    }

    @Override
    public void recall() {
        ServerPlayer serverPlayer = PlayerExtensionsKt.getPlayer(this.getPlayerUUID());
        if (serverPlayer == null) {
            return;
        }
        ServerPlayer player = serverPlayer;
        CompoundTag nbt = this.isLeftShoulder ? player.m_36331_() : player.m_36332_();
        Intrinsics.checkNotNullExpressionValue((Object)nbt, (String)"nbt");
        if (this.isShoulderedPokemon(nbt)) {
            Level level = player.m_9236_();
            Intrinsics.checkNotNullExpressionValue((Object)level, (String)"player.world");
            Vec3 vec3 = player.m_20182_();
            Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"player.pos");
            SoundEvent soundEvent = SoundEvents.f_144099_;
            Intrinsics.checkNotNullExpressionValue((Object)soundEvent, (String)"BLOCK_CANDLE_FALL");
            WorldExtensionsKt.playSoundServer$default(level, vec3, soundEvent, null, 0.0f, 0.0f, 28, null);
            if (this.isLeftShoulder) {
                player.m_36362_(new CompoundTag());
            } else {
                player.m_36364_(new CompoundTag());
            }
            this.removeShoulderEffects(player);
        }
    }

    private final void removeShoulderEffects(ServerPlayer player) {
        block3: {
            Pokemon partyPokemon;
            Object object;
            Object v0;
            block2: {
                Iterable iterable = PlayerExtensionsKt.party(player);
                for (Object t : iterable) {
                    Pokemon pokemon = (Pokemon)t;
                    boolean bl = false;
                    if (!Intrinsics.areEqual((Object)pokemon.getUuid(), (Object)this.getPokemonUUID())) continue;
                    v0 = t;
                    break block2;
                }
                v0 = null;
            }
            if ((object = (partyPokemon = (Pokemon)v0)) == null || (object = ((Pokemon)object).getForm()) == null || (object = ((FormData)object).getShoulderEffects()) == null) break block3;
            Iterable $this$forEach$iv = (Iterable)object;
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                ShoulderEffect effect = (ShoulderEffect)element$iv;
                boolean bl = false;
                effect.removeEffect(partyPokemon, player, this.isLeftShoulder);
            }
        }
    }

    private final boolean isShoulderedPokemon(CompoundTag nbt) {
        return CompoundTagExtensionsKt.isPokemonEntity(nbt) && Intrinsics.areEqual((Object)nbt.m_128469_("Pokemon").m_128469_("State").m_128342_("StateId"), (Object)this.stateId);
    }

    public final boolean isStillShouldered(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        CompoundTag compoundTag = this.isLeftShoulder ? player.m_36331_() : player.m_36332_();
        Intrinsics.checkNotNullExpressionValue((Object)compoundTag, (String)"if (isLeftShoulder) play\u2026layer.shoulderEntityRight");
        return this.isShoulderedPokemon(compoundTag);
    }
}

