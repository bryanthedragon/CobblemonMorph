/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.Level
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate.ActivePokemonState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import com.google.gson.JsonObject;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0011\b\u0016\u0012\u0006\u0010\u000f\u001a\u00020\u000e\u00a2\u0006\u0004\b'\u0010\u0011B\u0007\u00a2\u0006\u0004\b'\u0010\rJ\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\t\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ\u000f\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\f\u0010\rJ\u0015\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u000e\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u0017\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0019\u0010\u0017\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0015\u001a\u00020\u0014H\u0016\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u0019\u0010\u001b\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u001a\u001a\u00020\u0019H\u0016\u00a2\u0006\u0004\b\u001b\u0010\u001cR:\u0010 \u001a&\u0012\f\u0012\n \u001f*\u0004\u0018\u00010\u001e0\u001e \u001f*\u0012\u0012\f\u0012\n \u001f*\u0004\u0018\u00010\u001e0\u001e\u0018\u00010\u001d0\u001d8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b \u0010!R\u0016\u0010\u000f\u001a\u0004\u0018\u00010\u000e8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\"\u0010#R\u0016\u0010%\u001a\u00020$8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b%\u0010&\u00a8\u0006("}, d2={"Lcom/cobblemon/mod/common/pokemon/activestate/SentOutState;", "Lcom/cobblemon/mod/common/pokemon/activestate/ActivePokemonState;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lnet/minecraft/resources/ResourceLocation;", "getIcon", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Lnet/minecraft/resources/ResourceLocation;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "readFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/pokemon/activestate/SentOutState;", "", "recall", "()V", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "entity", "update", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)V", "writeToBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "Lcom/google/gson/JsonObject;", "json", "", "writeToJSON", "(Lcom/google/gson/JsonObject;)Ljava/lang/Void;", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "writeToNBT", "(Lnet/minecraft/nbt/CompoundTag;)Ljava/lang/Void;", "Lnet/minecraft/resources/ResourceKey;", "Lnet/minecraft/world/level/Level;", "kotlin.jvm.PlatformType", "dimension", "Lnet/minecraft/resources/ResourceKey;", "getEntity", "()Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "", "entityId", "I", "<init>", "common"})
public final class SentOutState
extends ActivePokemonState {
    private int entityId;
    private ResourceKey<Level> dimension;

    public SentOutState() {
        super(null);
        this.entityId = -1;
        this.dimension = Level.f_46428_;
    }

    @Override
    @Nullable
    public PokemonEntity getEntity() {
        ResourceKey<Level> resourceKey = this.dimension;
        Intrinsics.checkNotNullExpressionValue(resourceKey, (String)"dimension");
        Level level = Cobblemon.INSTANCE.getLevel(resourceKey);
        Entity entity2 = level != null ? level.m_6815_(this.entityId) : null;
        return entity2 instanceof PokemonEntity ? (PokemonEntity)entity2 : null;
    }

    public SentOutState(@NotNull PokemonEntity entity2) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        this();
        this.entityId = entity2.m_19879_();
        this.dimension = entity2.m_9236_().m_46472_();
    }

    @Override
    @NotNull
    public ResourceLocation getIcon(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        return MiscUtils.cobblemonResource("textures/gui/party/party_icon_released.png");
    }

    @Nullable
    public Void writeToNBT(@NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        return null;
    }

    @Nullable
    public Void writeToJSON(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        return null;
    }

    @Override
    public void writeToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        super.writeToBuffer(buffer);
        buffer.writeInt(this.entityId);
        buffer.m_130070_(this.dimension.m_135782_().toString());
    }

    @Override
    @NotNull
    public SentOutState readFromBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        super.readFromBuffer(buffer);
        this.entityId = buffer.readInt();
        this.dimension = ResourceKey.m_135785_((ResourceKey)ResourceKey.m_135788_((ResourceLocation)this.dimension.m_135782_()), (ResourceLocation)new ResourceLocation(buffer.m_130277_()));
        return this;
    }

    public final void update(@NotNull PokemonEntity entity2) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        this.entityId = entity2.m_19879_();
        this.dimension = entity2.m_9236_().m_46472_();
    }

    @Override
    public void recall() {
        block0: {
            PokemonEntity pokemonEntity = this.getEntity();
            if (pokemonEntity == null) break block0;
            pokemonEntity.m_146870_();
        }
    }
}

