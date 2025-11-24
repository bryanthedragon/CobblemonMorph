/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.FriendlyByteBuf
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.EvolutionController;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.EvolutionDisplay;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.EvolutionLike;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.EvolutionProxy;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.controller.ClientEvolutionController;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.controller.ServerEvolutionController;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0017\u0012\u0006\u0010&\u001a\u00020%\u0012\u0006\u0010!\u001a\u00020\t\u00a2\u0006\u0004\b(\u0010)J\u0015\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00070\u0004H\u0016\u00a2\u0006\u0004\b\b\u0010\u0006J\u000f\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0017\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\r\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0017\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u0011H\u0016\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0017\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\u0015H\u0016\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u001f\u0010\u001a\u001a\u00020\u000e2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u0019\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u000f\u0010\u001c\u001a\u00020\u0011H\u0016\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u000f\u0010\u001e\u001a\u00020\u0015H\u0016\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u0015\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00030\u0004H\u0016\u00a2\u0006\u0004\b \u0010\u0006R\u0014\u0010!\u001a\u00020\t8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b!\u0010\"R\u001c\u0010#\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00070\u00048\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b#\u0010$R\u0014\u0010&\u001a\u00020%8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b&\u0010'\u00a8\u0006*"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/CobblemonEvolutionProxy;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/EvolutionProxy;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/EvolutionDisplay;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/Evolution;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/EvolutionController;", "client", "()Lcom/cobblemon/mod/common/api/pokemon/evolution/EvolutionController;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/EvolutionLike;", "current", "", "isClient", "()Z", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "loadFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "Lcom/google/gson/JsonElement;", "json", "loadFromJson", "(Lcom/google/gson/JsonElement;)V", "Lnet/minecraft/nbt/Tag;", "nbt", "loadFromNBT", "(Lnet/minecraft/nbt/Tag;)V", "toClient", "saveToBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;Z)V", "saveToJson", "()Lcom/google/gson/JsonElement;", "saveToNBT", "()Lnet/minecraft/nbt/Tag;", "server", "clientSide", "Z", "controller", "Lcom/cobblemon/mod/common/api/pokemon/evolution/EvolutionController;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "<init>", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Z)V", "common"})
public final class CobblemonEvolutionProxy
implements EvolutionProxy<EvolutionDisplay, Evolution> {
    @NotNull
    private final Pokemon pokemon;
    private final boolean clientSide;
    @NotNull
    private final EvolutionController<? extends EvolutionLike> controller;

    public CobblemonEvolutionProxy(@NotNull Pokemon pokemon, boolean clientSide) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        this.pokemon = pokemon;
        this.clientSide = clientSide;
        this.controller = this.clientSide ? (EvolutionController)new ClientEvolutionController(this.pokemon) : (EvolutionController)new ServerEvolutionController(this.pokemon);
    }

    @Override
    public boolean isClient() {
        return this.clientSide;
    }

    @Override
    @NotNull
    public EvolutionController<? extends EvolutionLike> current() {
        return this.controller;
    }

    @Override
    @NotNull
    public EvolutionController<EvolutionDisplay> client() {
        EvolutionController<? extends EvolutionLike> evolutionController = this.controller;
        EvolutionController<? extends EvolutionLike> evolutionController2 = evolutionController instanceof EvolutionController ? evolutionController : null;
        if (evolutionController2 == null) {
            throw new ClassCastException("Cannot use the client implementation from the server side");
        }
        return evolutionController2;
    }

    @Override
    @NotNull
    public EvolutionController<Evolution> server() {
        EvolutionController<? extends EvolutionLike> evolutionController = this.controller;
        EvolutionController<? extends EvolutionLike> evolutionController2 = evolutionController instanceof EvolutionController ? evolutionController : null;
        if (evolutionController2 == null) {
            throw new ClassCastException("Cannot use the server implementation from the client side");
        }
        return evolutionController2;
    }

    @Override
    @NotNull
    public Tag saveToNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.m_128365_("Pending", this.current().saveToNBT());
        return (Tag)nbt;
    }

    @Override
    public void loadFromNBT(@NotNull Tag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        CompoundTag compoundTag = nbt instanceof CompoundTag ? (CompoundTag)nbt : null;
        if (compoundTag == null) {
            return;
        }
        CompoundTag compound = compoundTag;
        Tag tag = compound.m_128423_("Pending");
        if (tag == null) {
            return;
        }
        this.current().loadFromNBT(tag);
    }

    @Override
    @NotNull
    public JsonElement saveToJson() {
        JsonObject json = new JsonObject();
        json.add("Pending", this.current().saveToJson());
        return (JsonElement)json;
    }

    @Override
    public void loadFromJson(@NotNull JsonElement json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        JsonObject jsonObject = json instanceof JsonObject ? (JsonObject)json : null;
        if (jsonObject == null) {
            return;
        }
        JsonObject jObject = jsonObject;
        EvolutionController<? extends EvolutionLike> evolutionController = this.current();
        JsonElement jsonElement = jObject.get("Pending");
        if (jsonElement == null) {
            jsonElement = (JsonElement)new JsonObject();
        }
        evolutionController.loadFromJson(jsonElement);
    }

    @Override
    public void saveToBuffer(@NotNull FriendlyByteBuf buffer, boolean toClient) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        this.current().saveToBuffer(buffer, toClient);
    }

    @Override
    public void loadFromBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        this.current().loadFromBuffer(buffer);
    }
}

