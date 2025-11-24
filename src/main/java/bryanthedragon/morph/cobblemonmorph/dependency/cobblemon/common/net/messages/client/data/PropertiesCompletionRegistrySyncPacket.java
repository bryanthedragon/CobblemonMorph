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
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.DataRegistrySyncPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties.PropertiesCompletionProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\b\u0000\u0018\u0000 \u00162\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0016B\u0015\u0012\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00020\u000b\u00a2\u0006\u0004\b\u0015\u0010\u000eJ\u0017\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u001f\u0010\t\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ\u001d\u0010\r\u001a\u00020\b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00020\u000bH\u0016\u00a2\u0006\u0004\b\r\u0010\u000eR\u001a\u0010\u0010\u001a\u00020\u000f8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/data/PropertiesCompletionRegistrySyncPacket;", "Lcom/cobblemon/mod/common/net/messages/client/data/DataRegistrySyncPacket;", "Lcom/cobblemon/mod/common/pokemon/properties/PropertiesCompletionProvider$SuggestionHolder;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "decodeEntry", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/pokemon/properties/PropertiesCompletionProvider$SuggestionHolder;", "entry", "", "encodeEntry", "(Lnet/minecraft/network/FriendlyByteBuf;Lcom/cobblemon/mod/common/pokemon/properties/PropertiesCompletionProvider$SuggestionHolder;)V", "", "entries", "synchronizeDecoded", "(Ljava/util/Collection;)V", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "suggestions", "<init>", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nPropertiesCompletionRegistrySyncPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PropertiesCompletionRegistrySyncPacket.kt\ncom/cobblemon/mod/common/net/messages/client/data/PropertiesCompletionRegistrySyncPacket\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,41:1\n1855#2,2:42\n*S KotlinDebug\n*F\n+ 1 PropertiesCompletionRegistrySyncPacket.kt\ncom/cobblemon/mod/common/net/messages/client/data/PropertiesCompletionRegistrySyncPacket\n*L\n31#1:42,2\n*E\n"})
public final class PropertiesCompletionRegistrySyncPacket
extends DataRegistrySyncPacket<PropertiesCompletionProvider.SuggestionHolder, PropertiesCompletionRegistrySyncPacket> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final ResourceLocation id;
    @NotNull
    private static final ResourceLocation ID = MiscUtilsKt.cobblemonResource("properties_completion_sync");

    public PropertiesCompletionRegistrySyncPacket(@NotNull Collection<PropertiesCompletionProvider.SuggestionHolder> suggestions) {
        Intrinsics.checkNotNullParameter(suggestions, (String)"suggestions");
        super(suggestions);
        this.id = ID;
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public void encodeEntry(@NotNull FriendlyByteBuf buffer, @NotNull PropertiesCompletionProvider.SuggestionHolder entry) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        Intrinsics.checkNotNullParameter((Object)entry, (String)"entry");
        buffer.m_236828_(entry.getKeys(), PropertiesCompletionRegistrySyncPacket::encodeEntry$lambda$0);
        buffer.m_236828_(entry.getSuggestions(), PropertiesCompletionRegistrySyncPacket::encodeEntry$lambda$1);
    }

    @Override
    @NotNull
    public PropertiesCompletionProvider.SuggestionHolder decodeEntry(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        List keys = buffer.m_236845_(PropertiesCompletionRegistrySyncPacket::decodeEntry$lambda$2);
        List suggestions = buffer.m_236845_(PropertiesCompletionRegistrySyncPacket::decodeEntry$lambda$3);
        Intrinsics.checkNotNullExpressionValue((Object)keys, (String)"keys");
        Collection collection = keys;
        Intrinsics.checkNotNullExpressionValue((Object)suggestions, (String)"suggestions");
        return new PropertiesCompletionProvider.SuggestionHolder(collection, suggestions);
    }

    @Override
    public void synchronizeDecoded(@NotNull Collection<PropertiesCompletionProvider.SuggestionHolder> entries) {
        Intrinsics.checkNotNullParameter(entries, (String)"entries");
        Iterable $this$forEach$iv = entries;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            PropertiesCompletionProvider.SuggestionHolder suggestionHolder = (PropertiesCompletionProvider.SuggestionHolder)element$iv;
            boolean bl = false;
            PropertiesCompletionProvider.INSTANCE.inject((Iterable<String>)suggestionHolder.getKeys(), suggestionHolder.getSuggestions());
        }
    }

    private static final void encodeEntry$lambda$0(FriendlyByteBuf pb, String value2) {
        pb.m_130070_(value2);
    }

    private static final void encodeEntry$lambda$1(FriendlyByteBuf pb, String value2) {
        pb.m_130070_(value2);
    }

    private static final String decodeEntry$lambda$2(FriendlyByteBuf pb) {
        return pb.m_130277_();
    }

    private static final String decodeEntry$lambda$3(FriendlyByteBuf pb) {
        return pb.m_130277_();
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/data/PropertiesCompletionRegistrySyncPacket$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lcom/cobblemon/mod/common/net/messages/client/data/PropertiesCompletionRegistrySyncPacket;", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/data/PropertiesCompletionRegistrySyncPacket;", "Lnet/minecraft/resources/ResourceLocation;", "ID", "Lnet/minecraft/resources/ResourceLocation;", "getID", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    @SourceDebugExtension(value={"SMAP\nPropertiesCompletionRegistrySyncPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PropertiesCompletionRegistrySyncPacket.kt\ncom/cobblemon/mod/common/net/messages/client/data/PropertiesCompletionRegistrySyncPacket$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,41:1\n1#2:42\n*E\n"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getID() {
            return ID;
        }

        @NotNull
        public final PropertiesCompletionRegistrySyncPacket decode(@NotNull FriendlyByteBuf buffer) {
            PropertiesCompletionRegistrySyncPacket propertiesCompletionRegistrySyncPacket;
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            PropertiesCompletionRegistrySyncPacket $this$decode_u24lambda_u240 = propertiesCompletionRegistrySyncPacket = new PropertiesCompletionRegistrySyncPacket(CollectionsKt.emptyList());
            boolean bl = false;
            $this$decode_u24lambda_u240.decodeBuffer$common(buffer);
            return propertiesCompletionRegistrySyncPacket;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

