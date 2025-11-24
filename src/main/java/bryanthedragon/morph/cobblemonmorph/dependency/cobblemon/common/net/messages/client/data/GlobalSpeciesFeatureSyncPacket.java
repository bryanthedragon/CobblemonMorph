/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.GlobalSpeciesFeatures;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatureProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SynchronizedSpeciesFeatureProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.SpeciesFeatureSyncPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import java.util.Collection;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0010&\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u00142\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0014B\u001f\u0012\u0016\u0010\u0011\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00100\u000f\u00a2\u0006\u0004\b\u0012\u0010\u0013J-\u0010\b\u001a\u00020\u00072\u001c\u0010\u0006\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u0004\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00050\u00030\u0002H\u0016\u00a2\u0006\u0004\b\b\u0010\tR\u001a\u0010\u000b\u001a\u00020\n8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0015"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/data/GlobalSpeciesFeatureSyncPacket;", "Lcom/cobblemon/mod/common/net/messages/client/data/SpeciesFeatureSyncPacket;", "", "", "", "Lcom/cobblemon/mod/common/api/pokemon/feature/SynchronizedSpeciesFeatureProvider;", "entries", "", "synchronizeDecoded", "(Ljava/util/Collection;)V", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "", "Lcom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeatureProvider;", "speciesFeatures", "<init>", "(Ljava/util/Map;)V", "Companion", "common"})
public final class GlobalSpeciesFeatureSyncPacket
extends SpeciesFeatureSyncPacket<GlobalSpeciesFeatureSyncPacket> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final ResourceLocation id;
    @NotNull
    private static final ResourceLocation ID = MiscUtilsKt.cobblemonResource("global_species_feature_sync");

    public GlobalSpeciesFeatureSyncPacket(@NotNull Map<String, ? extends SpeciesFeatureProvider<?>> speciesFeatures) {
        Intrinsics.checkNotNullParameter(speciesFeatures, (String)"speciesFeatures");
        super(speciesFeatures);
        this.id = ID;
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public void synchronizeDecoded(@NotNull Collection<? extends Map.Entry<String, ? extends SynchronizedSpeciesFeatureProvider<?>>> entries) {
        Intrinsics.checkNotNullParameter(entries, (String)"entries");
        GlobalSpeciesFeatures.INSTANCE.loadOnClient(entries);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/data/GlobalSpeciesFeatureSyncPacket$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lcom/cobblemon/mod/common/net/messages/client/data/GlobalSpeciesFeatureSyncPacket;", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/data/GlobalSpeciesFeatureSyncPacket;", "Lnet/minecraft/resources/ResourceLocation;", "ID", "Lnet/minecraft/resources/ResourceLocation;", "getID", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    @SourceDebugExtension(value={"SMAP\nGlobalSpeciesFeatureSyncPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 GlobalSpeciesFeatureSyncPacket.kt\ncom/cobblemon/mod/common/net/messages/client/data/GlobalSpeciesFeatureSyncPacket$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,30:1\n1#2:31\n*E\n"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getID() {
            return ID;
        }

        @NotNull
        public final GlobalSpeciesFeatureSyncPacket decode(@NotNull FriendlyByteBuf buffer) {
            GlobalSpeciesFeatureSyncPacket globalSpeciesFeatureSyncPacket;
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            GlobalSpeciesFeatureSyncPacket $this$decode_u24lambda_u240 = globalSpeciesFeatureSyncPacket = new GlobalSpeciesFeatureSyncPacket(MapsKt.emptyMap());
            boolean bl = false;
            $this$decode_u24lambda_u240.decodeBuffer$common(buffer);
            return globalSpeciesFeatureSyncPacket;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

