/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
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
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatureAssignments;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.DataRegistrySyncPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010&\n\u0002\u0018\u0002\n\u0002\u0010#\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0007\n\u0002\u0010$\n\u0002\b\u0005\u0018\u0000 \u001a2 \u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u0003\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0002\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u001aB!\u0012\u0018\u0010\u0017\u001a\u0014\u0012\u0004\u0012\u00020\u0003\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0016\u00a2\u0006\u0004\b\u0018\u0010\u0019J)\u0010\b\u001a\u0014\u0012\u0004\u0012\u00020\u0003\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00022\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ1\u0010\f\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\u00062\u0018\u0010\n\u001a\u0014\u0012\u0004\u0012\u00020\u0003\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0002H\u0016\u00a2\u0006\u0004\b\f\u0010\rJ/\u0010\u0010\u001a\u00020\u000b2\u001e\u0010\u000f\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u0003\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00020\u000eH\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u00038\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015\u00a8\u0006\u001b"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/data/SpeciesFeatureAssignmentSyncPacket;", "Lcom/cobblemon/mod/common/net/messages/client/data/DataRegistrySyncPacket;", "", "Lnet/minecraft/resources/ResourceLocation;", "", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "decodeEntry", "(Lnet/minecraft/network/FriendlyByteBuf;)Ljava/util/Map$Entry;", "entry", "", "encodeEntry", "(Lnet/minecraft/network/FriendlyByteBuf;Ljava/util/Map$Entry;)V", "", "entries", "synchronizeDecoded", "(Ljava/util/Collection;)V", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "", "data", "<init>", "(Ljava/util/Map;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nSpeciesFeatureAssignmentSyncPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpeciesFeatureAssignmentSyncPacket.kt\ncom/cobblemon/mod/common/net/messages/client/data/SpeciesFeatureAssignmentSyncPacket\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,48:1\n1179#2,2:49\n1253#2,4:51\n*S KotlinDebug\n*F\n+ 1 SpeciesFeatureAssignmentSyncPacket.kt\ncom/cobblemon/mod/common/net/messages/client/data/SpeciesFeatureAssignmentSyncPacket\n*L\n41#1:49,2\n41#1:51,4\n*E\n"})
public final class SpeciesFeatureAssignmentSyncPacket
extends DataRegistrySyncPacket<Map.Entry<? extends ResourceLocation, ? extends Set<String>>, SpeciesFeatureAssignmentSyncPacket> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final ResourceLocation id;
    @NotNull
    private static final ResourceLocation ID = MiscUtilsKt.cobblemonResource("species_feature_assignment_sync");

    public SpeciesFeatureAssignmentSyncPacket(@NotNull Map<ResourceLocation, ? extends Set<String>> data) {
        Intrinsics.checkNotNullParameter(data, (String)"data");
        super((Collection)data.entrySet());
        this.id = ID;
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    @NotNull
    public Map.Entry<ResourceLocation, Set<String>> decodeEntry(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        ResourceLocation key = buffer.m_130281_();
        List list = buffer.m_236845_(arg_0 -> SpeciesFeatureAssignmentSyncPacket.decodeEntry$lambda$0(buffer, arg_0));
        Intrinsics.checkNotNullExpressionValue((Object)list, (String)"buffer.readList { buffer.readString() }");
        Set assignments = CollectionsKt.toMutableSet((Iterable)list);
        return new Map.Entry<ResourceLocation, Set<String>>(key, (Set<String>)assignments){
            private final ResourceLocation key;
            @NotNull
            private final Set<String> value;
            {
                this.key = $key;
                this.value = $assignments;
            }

            public ResourceLocation getKey() {
                return this.key;
            }

            @NotNull
            public Set<String> getValue() {
                return this.value;
            }

            public Set<String> setValue(Set<String> newValue) {
                throw new UnsupportedOperationException("Operation is not supported for read-only collection");
            }
        };
    }

    @Override
    public void encodeEntry(@NotNull FriendlyByteBuf buffer, @NotNull Map.Entry<? extends ResourceLocation, ? extends Set<String>> entry) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        Intrinsics.checkNotNullParameter(entry, (String)"entry");
        buffer.m_130085_(entry.getKey());
        buffer.m_236828_((Collection)entry.getValue(), (arg_0, arg_1) -> SpeciesFeatureAssignmentSyncPacket.encodeEntry$lambda$1(buffer, arg_0, arg_1));
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void synchronizeDecoded(@NotNull Collection<? extends Map.Entry<? extends ResourceLocation, ? extends Set<String>>> entries) {
        void $this$associateTo$iv$iv;
        void $this$associate$iv;
        Intrinsics.checkNotNullParameter(entries, (String)"entries");
        Iterable iterable = entries;
        SpeciesFeatureAssignments speciesFeatureAssignments = SpeciesFeatureAssignments.INSTANCE;
        boolean $i$f$associate = false;
        int capacity$iv = RangesKt.coerceAtLeast((int)MapsKt.mapCapacity((int)CollectionsKt.collectionSizeOrDefault((Iterable)$this$associate$iv, (int)10)), (int)16);
        void var5_6 = $this$associate$iv;
        Map destination$iv$iv = new LinkedHashMap(capacity$iv);
        boolean $i$f$associateTo = false;
        for (Object element$iv$iv : $this$associateTo$iv$iv) {
            Map map = destination$iv$iv;
            Map.Entry it = (Map.Entry)element$iv$iv;
            boolean bl = false;
            Map.Entry entry = it;
            Pair pair = new Pair(entry.getKey(), entry.getValue());
            map.put(pair.getFirst(), pair.getSecond());
        }
        speciesFeatureAssignments.loadOnClient(destination$iv$iv);
    }

    private static final String decodeEntry$lambda$0(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        return $buffer.m_130277_();
    }

    private static final void encodeEntry$lambda$1(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, String value2) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        $buffer.m_130070_(value2);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/data/SpeciesFeatureAssignmentSyncPacket$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lcom/cobblemon/mod/common/net/messages/client/data/SpeciesFeatureAssignmentSyncPacket;", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/data/SpeciesFeatureAssignmentSyncPacket;", "Lnet/minecraft/resources/ResourceLocation;", "ID", "Lnet/minecraft/resources/ResourceLocation;", "getID", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    @SourceDebugExtension(value={"SMAP\nSpeciesFeatureAssignmentSyncPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpeciesFeatureAssignmentSyncPacket.kt\ncom/cobblemon/mod/common/net/messages/client/data/SpeciesFeatureAssignmentSyncPacket$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,48:1\n1#2:49\n*E\n"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getID() {
            return ID;
        }

        @NotNull
        public final SpeciesFeatureAssignmentSyncPacket decode(@NotNull FriendlyByteBuf buffer) {
            SpeciesFeatureAssignmentSyncPacket speciesFeatureAssignmentSyncPacket;
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            SpeciesFeatureAssignmentSyncPacket $this$decode_u24lambda_u240 = speciesFeatureAssignmentSyncPacket = new SpeciesFeatureAssignmentSyncPacket(MapsKt.emptyMap());
            boolean bl = false;
            $this$decode_u24lambda_u240.decodeBuffer$common(buffer);
            return speciesFeatureAssignmentSyncPacket;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

