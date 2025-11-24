/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.RangesKt
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.fossil;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fossil.Fossil;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fossil.Fossils;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.DataRegistrySyncPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0005\u0018\u0000 \u00182\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0018B\u0015\u0012\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00020\u0014\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0017\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u001f\u0010\t\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ\u001d\u0010\r\u001a\u00020\b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00020\u000bH\u0016\u00a2\u0006\u0004\b\r\u0010\u000eR\u001a\u0010\u0010\u001a\u00020\u000f8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/fossil/FossilRegistrySyncPacket;", "Lcom/cobblemon/mod/common/net/messages/client/data/DataRegistrySyncPacket;", "Lcom/cobblemon/mod/common/api/fossil/Fossil;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "decodeEntry", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/api/fossil/Fossil;", "entry", "", "encodeEntry", "(Lnet/minecraft/network/FriendlyByteBuf;Lcom/cobblemon/mod/common/api/fossil/Fossil;)V", "", "entries", "synchronizeDecoded", "(Ljava/util/Collection;)V", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "", "fossils", "<init>", "(Ljava/util/List;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nFossilRegistrySyncPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FossilRegistrySyncPacket.kt\ncom/cobblemon/mod/common/net/messages/client/fossil/FossilRegistrySyncPacket\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,44:1\n1194#2,2:45\n1222#2,4:47\n*S KotlinDebug\n*F\n+ 1 FossilRegistrySyncPacket.kt\ncom/cobblemon/mod/common/net/messages/client/fossil/FossilRegistrySyncPacket\n*L\n42#1:45,2\n42#1:47,4\n*E\n"})
public final class FossilRegistrySyncPacket
extends DataRegistrySyncPacket<Fossil, FossilRegistrySyncPacket> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final ResourceLocation id;
    @NotNull
    private static final ResourceLocation ID = MiscUtilsKt.cobblemonResource("fossils");

    public FossilRegistrySyncPacket(@NotNull List<Fossil> fossils) {
        Intrinsics.checkNotNullParameter(fossils, (String)"fossils");
        super((Collection)fossils);
        this.id = ID;
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public void encodeEntry(@NotNull FriendlyByteBuf buffer, @NotNull Fossil entry) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        Intrinsics.checkNotNullParameter((Object)entry, (String)"entry");
        buffer.m_130085_(entry.getIdentifier());
        buffer.m_130070_(Fossils.INSTANCE.getGson().toJson((Object)entry.getResult(), (Type)((Object)PokemonProperties.class)));
    }

    @Override
    @NotNull
    public Fossil decodeEntry(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        ResourceLocation resourceLocation = buffer.m_130281_();
        Intrinsics.checkNotNullExpressionValue((Object)resourceLocation, (String)"buffer.readIdentifier()");
        Object object = Fossils.INSTANCE.getGson().fromJson(buffer.m_130277_(), PokemonProperties.class);
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"Fossils.gson.fromJson(bu\u2026onProperties::class.java)");
        return new Fossil(resourceLocation, (PokemonProperties)object, CollectionsKt.emptyList());
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void synchronizeDecoded(@NotNull Collection<Fossil> entries) {
        void $this$associateByTo$iv$iv;
        void $this$associateBy$iv;
        Intrinsics.checkNotNullParameter(entries, (String)"entries");
        Iterable iterable = entries;
        Fossils fossils = Fossils.INSTANCE;
        boolean $i$f$associateBy = false;
        int capacity$iv = RangesKt.coerceAtLeast((int)MapsKt.mapCapacity((int)CollectionsKt.collectionSizeOrDefault((Iterable)$this$associateBy$iv, (int)10)), (int)16);
        void var5_6 = $this$associateBy$iv;
        Map destination$iv$iv = new LinkedHashMap(capacity$iv);
        boolean $i$f$associateByTo = false;
        for (Object element$iv$iv : $this$associateByTo$iv$iv) {
            void it;
            Fossil fossil = (Fossil)element$iv$iv;
            Map map = destination$iv$iv;
            boolean bl = false;
            map.put(it.getIdentifier(), element$iv$iv);
        }
        fossils.reload(destination$iv$iv);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/fossil/FossilRegistrySyncPacket$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lcom/cobblemon/mod/common/net/messages/client/fossil/FossilRegistrySyncPacket;", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/fossil/FossilRegistrySyncPacket;", "Lnet/minecraft/resources/ResourceLocation;", "ID", "Lnet/minecraft/resources/ResourceLocation;", "getID", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    @SourceDebugExtension(value={"SMAP\nFossilRegistrySyncPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FossilRegistrySyncPacket.kt\ncom/cobblemon/mod/common/net/messages/client/fossil/FossilRegistrySyncPacket$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,44:1\n1#2:45\n*E\n"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getID() {
            return ID;
        }

        @NotNull
        public final FossilRegistrySyncPacket decode(@NotNull FriendlyByteBuf buffer) {
            FossilRegistrySyncPacket fossilRegistrySyncPacket;
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            FossilRegistrySyncPacket $this$decode_u24lambda_u240 = fossilRegistrySyncPacket = new FossilRegistrySyncPacket(CollectionsKt.emptyList());
            boolean bl = false;
            $this$decode_u24lambda_u240.decodeBuffer$common(buffer);
            return fossilRegistrySyncPacket;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

