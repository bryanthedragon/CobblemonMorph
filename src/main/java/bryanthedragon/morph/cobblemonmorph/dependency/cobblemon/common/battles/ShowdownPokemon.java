/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  kotlin.Lazy
 *  kotlin.LazyKt
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.StringsKt
 *  net.minecraft.network.FriendlyByteBuf
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0012\n\u0002\u0010!\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b4\u00105J\u0015\u0010\u0004\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0015\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR\"\u0010\n\u001a\u00020\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\"\u0010\u0011\u001a\u00020\u00108\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\"\u0010\u0017\u001a\u00020\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u0017\u0010\u000b\u001a\u0004\b\u0018\u0010\r\"\u0004\b\u0019\u0010\u000fR\"\u0010\u001a\u001a\u00020\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u001a\u0010\u000b\u001a\u0004\b\u001b\u0010\r\"\u0004\b\u001c\u0010\u000fR\"\u0010\u001d\u001a\u00020\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u001d\u0010\u000b\u001a\u0004\b\u001e\u0010\r\"\u0004\b\u001f\u0010\u000fR\"\u0010 \u001a\u00020\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b \u0010\u000b\u001a\u0004\b!\u0010\r\"\u0004\b\"\u0010\u000fR\u001d\u0010$\u001a\b\u0012\u0004\u0012\u00020\t0#8\u0006\u00a2\u0006\f\n\u0004\b$\u0010%\u001a\u0004\b&\u0010'R\"\u0010(\u001a\u00020\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b(\u0010\u000b\u001a\u0004\b)\u0010\r\"\u0004\b*\u0010\u000fR\"\u0010+\u001a\u00020\u00108\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b+\u0010\u0012\u001a\u0004\b,\u0010\u0014\"\u0004\b-\u0010\u0016R\u001b\u00103\u001a\u00020.8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b/\u00100\u001a\u0004\b1\u00102\u00a8\u00066"}, d2={"Lcom/cobblemon/mod/common/battles/ShowdownPokemon;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "loadFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/battles/ShowdownPokemon;", "", "saveToBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "", "ability", "Ljava/lang/String;", "getAbility", "()Ljava/lang/String;", "setAbility", "(Ljava/lang/String;)V", "", "active", "Z", "getActive", "()Z", "setActive", "(Z)V", "baseAbility", "getBaseAbility", "setBaseAbility", "condition", "getCondition", "setCondition", "details", "getDetails", "setDetails", "ident", "getIdent", "setIdent", "", "moves", "Ljava/util/List;", "getMoves", "()Ljava/util/List;", "pokeball", "getPokeball", "setPokeball", "reviving", "getReviving", "setReviving", "Ljava/util/UUID;", "uuid$delegate", "Lkotlin/Lazy;", "getUuid", "()Ljava/util/UUID;", "uuid", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nShowdownActionRequest.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ShowdownActionRequest.kt\ncom/cobblemon/mod/common/battles/ShowdownPokemon\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,452:1\n1855#2,2:453\n*S KotlinDebug\n*F\n+ 1 ShowdownActionRequest.kt\ncom/cobblemon/mod/common/battles/ShowdownPokemon\n*L\n406#1:453,2\n*E\n"})
public final class ShowdownPokemon {
    public String ident;
    public String details;
    public String condition;
    private boolean active;
    @NotNull
    private final List<String> moves = new ArrayList();
    public String baseAbility;
    public String pokeball;
    public String ability;
    private boolean reviving;
    @NotNull
    private final Lazy uuid$delegate = LazyKt.lazy((Function0)((Function0)new Function0<UUID>(this){
        final /* synthetic */ ShowdownPokemon this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        public final UUID invoke() {
            String[] stringArray = new String[]{","};
            return UUID.fromString(((Object)StringsKt.trim((CharSequence)((String)StringsKt.split$default((CharSequence)this.this$0.getDetails(), (String[])stringArray, (boolean)false, (int)0, (int)6, null).get(1)))).toString());
        }
    }));

    @NotNull
    public final String getIdent() {
        String string = this.ident;
        if (string != null) {
            return string;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"ident");
        return null;
    }

    public final void setIdent(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.ident = string;
    }

    @NotNull
    public final String getDetails() {
        String string = this.details;
        if (string != null) {
            return string;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"details");
        return null;
    }

    public final void setDetails(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.details = string;
    }

    @NotNull
    public final String getCondition() {
        String string = this.condition;
        if (string != null) {
            return string;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"condition");
        return null;
    }

    public final void setCondition(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.condition = string;
    }

    public final boolean getActive() {
        return this.active;
    }

    public final void setActive(boolean bl) {
        this.active = bl;
    }

    @NotNull
    public final List<String> getMoves() {
        return this.moves;
    }

    @NotNull
    public final String getBaseAbility() {
        String string = this.baseAbility;
        if (string != null) {
            return string;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"baseAbility");
        return null;
    }

    public final void setBaseAbility(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.baseAbility = string;
    }

    @NotNull
    public final String getPokeball() {
        String string = this.pokeball;
        if (string != null) {
            return string;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"pokeball");
        return null;
    }

    public final void setPokeball(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.pokeball = string;
    }

    @NotNull
    public final String getAbility() {
        String string = this.ability;
        if (string != null) {
            return string;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"ability");
        return null;
    }

    public final void setAbility(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.ability = string;
    }

    public final boolean getReviving() {
        return this.reviving;
    }

    public final void setReviving(boolean bl) {
        this.reviving = bl;
    }

    @NotNull
    public final UUID getUuid() {
        Lazy lazy = this.uuid$delegate;
        Object object = lazy.getValue();
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"<get-uuid>(...)");
        return (UUID)object;
    }

    public final void saveToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130070_(this.getIdent());
        buffer.m_130070_(this.getDetails());
        buffer.m_130070_(this.getCondition());
        buffer.writeBoolean(this.active);
        buffer.writeBoolean(this.reviving);
        NetExtensionsKt.writeSizedInt((ByteBuf)buffer, IntSize.U_BYTE, this.moves.size());
        Iterable $this$forEach$iv = this.moves;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            String p0 = (String)element$iv;
            boolean bl = false;
            buffer.m_130070_(p0);
        }
        buffer.m_130070_(this.getBaseAbility());
        buffer.m_130070_(this.getPokeball());
        buffer.m_130070_(this.getAbility());
    }

    @NotNull
    public final ShowdownPokemon loadFromBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        String string = buffer.m_130277_();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"buffer.readString()");
        this.setIdent(string);
        String string2 = buffer.m_130277_();
        Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"buffer.readString()");
        this.setDetails(string2);
        String string3 = buffer.m_130277_();
        Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"buffer.readString()");
        this.setCondition(string3);
        this.active = buffer.readBoolean();
        this.reviving = buffer.readBoolean();
        int n = NetExtensionsKt.readSizedInt((ByteBuf)buffer, IntSize.U_BYTE);
        int n2 = 0;
        while (n2 < n) {
            int it = n2++;
            boolean bl = false;
            String string4 = buffer.m_130277_();
            Intrinsics.checkNotNullExpressionValue((Object)string4, (String)"buffer.readString()");
            this.moves.add(string4);
        }
        String string5 = buffer.m_130277_();
        Intrinsics.checkNotNullExpressionValue((Object)string5, (String)"buffer.readString()");
        this.setBaseAbility(string5);
        String string6 = buffer.m_130277_();
        Intrinsics.checkNotNullExpressionValue((Object)string6, (String)"buffer.readString()");
        this.setPokeball(string6);
        String string7 = buffer.m_130277_();
        Intrinsics.checkNotNullExpressionValue((Object)string7, (String)"buffer.readString()");
        this.setAbility(string7);
        return this;
    }
}

