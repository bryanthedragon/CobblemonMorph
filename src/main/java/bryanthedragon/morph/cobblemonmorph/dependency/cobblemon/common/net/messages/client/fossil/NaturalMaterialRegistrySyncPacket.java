/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.TagKey
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.fossil;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fossil.NaturalMaterial;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fossil.NaturalMaterials;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.DataRegistrySyncPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.registry.ItemTagCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0005\u0018\u0000 \u00182\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0018B\u0015\u0012\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00020\u0014\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0017\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u001f\u0010\t\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ\u001d\u0010\r\u001a\u00020\b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00020\u000bH\u0016\u00a2\u0006\u0004\b\r\u0010\u000eR\u001a\u0010\u0010\u001a\u00020\u000f8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/fossil/NaturalMaterialRegistrySyncPacket;", "Lcom/cobblemon/mod/common/net/messages/client/data/DataRegistrySyncPacket;", "Lcom/cobblemon/mod/common/api/fossil/NaturalMaterial;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "decodeEntry", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/api/fossil/NaturalMaterial;", "entry", "", "encodeEntry", "(Lnet/minecraft/network/FriendlyByteBuf;Lcom/cobblemon/mod/common/api/fossil/NaturalMaterial;)V", "", "entries", "synchronizeDecoded", "(Ljava/util/Collection;)V", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "", "naturalMaterials", "<init>", "(Ljava/util/List;)V", "Companion", "common"})
public final class NaturalMaterialRegistrySyncPacket
extends DataRegistrySyncPacket<NaturalMaterial, NaturalMaterialRegistrySyncPacket> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final ResourceLocation id;
    @NotNull
    private static final ResourceLocation ID = MiscUtilsKt.cobblemonResource("natural_materials");

    public NaturalMaterialRegistrySyncPacket(@NotNull List<NaturalMaterial> naturalMaterials) {
        Intrinsics.checkNotNullParameter(naturalMaterials, (String)"naturalMaterials");
        super((Collection)naturalMaterials);
        this.id = ID;
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public void encodeEntry(@NotNull FriendlyByteBuf buffer, @NotNull NaturalMaterial entry) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        Intrinsics.checkNotNullParameter((Object)entry, (String)"entry");
        buffer.m_236821_((Object)entry.getItem(), (arg_0, arg_1) -> NaturalMaterialRegistrySyncPacket.encodeEntry$lambda$0(entry, arg_0, arg_1));
        buffer.m_236821_((Object)entry.getTag(), (arg_0, arg_1) -> NaturalMaterialRegistrySyncPacket.encodeEntry$lambda$1(entry, arg_0, arg_1));
        buffer.m_236821_((Object)entry.getReturnItem(), (arg_0, arg_1) -> NaturalMaterialRegistrySyncPacket.encodeEntry$lambda$2(entry, arg_0, arg_1));
    }

    @Override
    @NotNull
    public NaturalMaterial decodeEntry(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        return new NaturalMaterial(0, (ResourceLocation)buffer.m_236868_(NaturalMaterialRegistrySyncPacket::decodeEntry$lambda$3), (ItemTagCondition)buffer.m_236868_(arg_0 -> NaturalMaterialRegistrySyncPacket.decodeEntry$lambda$4(buffer, arg_0)), (ResourceLocation)buffer.m_236868_(NaturalMaterialRegistrySyncPacket::decodeEntry$lambda$5));
    }

    @Override
    public void synchronizeDecoded(@NotNull Collection<NaturalMaterial> entries) {
        Intrinsics.checkNotNullParameter(entries, (String)"entries");
        NaturalMaterials.INSTANCE.reload(MapsKt.mapOf((Pair)TuplesKt.to((Object)MiscUtilsKt.cobblemonResource("natural_materials"), (Object)CollectionsKt.toList((Iterable)entries))));
    }

    private static final void encodeEntry$lambda$0(NaturalMaterial $entry, FriendlyByteBuf pb, ResourceLocation type) {
        Intrinsics.checkNotNullParameter((Object)$entry, (String)"$entry");
        pb.m_130085_($entry.getItem());
    }

    private static final void encodeEntry$lambda$1(NaturalMaterial $entry, FriendlyByteBuf pb, ItemTagCondition type) {
        TagKey tagKey;
        Intrinsics.checkNotNullParameter((Object)$entry, (String)"$entry");
        pb.m_130070_(NaturalMaterials.INSTANCE.getGson().toJson((Object)("#" + ((tagKey = $entry.getTag()) != null && (tagKey = tagKey.getTag()) != null ? tagKey.f_203868_() : null))));
    }

    private static final void encodeEntry$lambda$2(NaturalMaterial $entry, FriendlyByteBuf pb, ResourceLocation type) {
        Intrinsics.checkNotNullParameter((Object)$entry, (String)"$entry");
        pb.m_130085_($entry.getReturnItem());
    }

    private static final ResourceLocation decodeEntry$lambda$3(FriendlyByteBuf pb) {
        return pb.m_130281_();
    }

    private static final ItemTagCondition decodeEntry$lambda$4(FriendlyByteBuf $buffer, FriendlyByteBuf pb) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        return (ItemTagCondition)NaturalMaterials.INSTANCE.getGson().fromJson($buffer.m_130277_(), ItemTagCondition.class);
    }

    private static final ResourceLocation decodeEntry$lambda$5(FriendlyByteBuf pb) {
        return pb.m_130281_();
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/fossil/NaturalMaterialRegistrySyncPacket$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lcom/cobblemon/mod/common/net/messages/client/fossil/NaturalMaterialRegistrySyncPacket;", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/fossil/NaturalMaterialRegistrySyncPacket;", "Lnet/minecraft/resources/ResourceLocation;", "ID", "Lnet/minecraft/resources/ResourceLocation;", "getID", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    @SourceDebugExtension(value={"SMAP\nNaturalMaterialRegistrySyncPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NaturalMaterialRegistrySyncPacket.kt\ncom/cobblemon/mod/common/net/messages/client/fossil/NaturalMaterialRegistrySyncPacket$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,49:1\n1#2:50\n*E\n"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getID() {
            return ID;
        }

        @NotNull
        public final NaturalMaterialRegistrySyncPacket decode(@NotNull FriendlyByteBuf buffer) {
            NaturalMaterialRegistrySyncPacket naturalMaterialRegistrySyncPacket;
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            NaturalMaterialRegistrySyncPacket $this$decode_u24lambda_u240 = naturalMaterialRegistrySyncPacket = new NaturalMaterialRegistrySyncPacket(CollectionsKt.emptyList());
            boolean bl = false;
            $this$decode_u24lambda_u240.decodeBuffer$common(buffer);
            return naturalMaterialRegistrySyncPacket;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

