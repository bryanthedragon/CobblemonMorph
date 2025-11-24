/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.FriendlyByteBuf
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownPokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u001f\u0010 J\u0015\u0010\u0004\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0015\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR\"\u0010\n\u001a\u00020\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\"\u0010\u0011\u001a\u00020\u00108\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R(\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00180\u00178\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001e\u00a8\u0006!"}, d2={"Lcom/cobblemon/mod/common/battles/ShowdownSide;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "loadFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/battles/ShowdownSide;", "", "saveToBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "", "id", "Ljava/lang/String;", "getId", "()Ljava/lang/String;", "setId", "(Ljava/lang/String;)V", "Ljava/util/UUID;", "name", "Ljava/util/UUID;", "getName", "()Ljava/util/UUID;", "setName", "(Ljava/util/UUID;)V", "", "Lcom/cobblemon/mod/common/battles/ShowdownPokemon;", "pokemon", "Ljava/util/List;", "getPokemon", "()Ljava/util/List;", "setPokemon", "(Ljava/util/List;)V", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nShowdownActionRequest.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ShowdownActionRequest.kt\ncom/cobblemon/mod/common/battles/ShowdownSide\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,452:1\n1855#2,2:453\n*S KotlinDebug\n*F\n+ 1 ShowdownActionRequest.kt\ncom/cobblemon/mod/common/battles/ShowdownSide\n*L\n374#1:453,2\n*E\n"})
public final class ShowdownSide {
    public UUID name;
    public String id;
    public List<ShowdownPokemon> pokemon;

    @NotNull
    public final UUID getName() {
        UUID uUID = this.name;
        if (uUID != null) {
            return uUID;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"name");
        return null;
    }

    public final void setName(@NotNull UUID uUID) {
        Intrinsics.checkNotNullParameter((Object)uUID, (String)"<set-?>");
        this.name = uUID;
    }

    @NotNull
    public final String getId() {
        String string = this.id;
        if (string != null) {
            return string;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"id");
        return null;
    }

    public final void setId(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.id = string;
    }

    @NotNull
    public final List<ShowdownPokemon> getPokemon() {
        List<ShowdownPokemon> list = this.pokemon;
        if (list != null) {
            return list;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"pokemon");
        return null;
    }

    public final void setPokemon(@NotNull List<ShowdownPokemon> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.pokemon = list;
    }

    public final void saveToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130077_(this.getName());
        buffer.m_130070_(this.getId());
        NetExtensionsKt.writeSizedInt((ByteBuf)buffer, IntSize.U_BYTE, this.getPokemon().size());
        Iterable $this$forEach$iv = this.getPokemon();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            ShowdownPokemon it = (ShowdownPokemon)element$iv;
            boolean bl = false;
            it.saveToBuffer(buffer);
        }
    }

    @NotNull
    public final ShowdownSide loadFromBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        UUID uUID = buffer.m_130259_();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"buffer.readUuid()");
        this.setName(uUID);
        String string = buffer.m_130277_();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"buffer.readString()");
        this.setId(string);
        List pokemon = new ArrayList();
        int n = NetExtensionsKt.readSizedInt((ByteBuf)buffer, IntSize.U_BYTE);
        int n2 = 0;
        while (n2 < n) {
            int it = n2++;
            boolean bl = false;
            pokemon.add(new ShowdownPokemon().loadFromBuffer(buffer));
        }
        this.setPokemon(pokemon);
        return this;
    }
}

