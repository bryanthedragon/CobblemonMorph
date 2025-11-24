/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.StringsKt
 *  net.minecraft.network.FriendlyByteBuf
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatureProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatures;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SynchronizedSpeciesFeatureProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.DataRegistrySyncPacket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010&\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u0000*\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u00028\u00000\u00002\u001e\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u0004\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00050\u0003\u0012\u0004\u0012\u00028\u00000\u0002B\u001f\u0012\u0016\u0010\u0010\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000f0\u000e\u00a2\u0006\u0004\b\u0011\u0010\u0012J)\u0010\b\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0005\u0018\u00010\u00032\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ/\u0010\f\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\u00062\u0016\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00050\u0003H\u0016\u00a2\u0006\u0004\b\f\u0010\r\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/data/SpeciesFeatureSyncPacket;", "T", "Lcom/cobblemon/mod/common/net/messages/client/data/DataRegistrySyncPacket;", "", "", "Lcom/cobblemon/mod/common/api/pokemon/feature/SynchronizedSpeciesFeatureProvider;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "decodeEntry", "(Lnet/minecraft/network/FriendlyByteBuf;)Ljava/util/Map$Entry;", "entry", "", "encodeEntry", "(Lnet/minecraft/network/FriendlyByteBuf;Ljava/util/Map$Entry;)V", "", "Lcom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeatureProvider;", "speciesFeatureProviders", "<init>", "(Ljava/util/Map;)V", "common"})
@SourceDebugExtension(value={"SMAP\nSpeciesFeatureSyncPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpeciesFeatureSyncPacket.kt\ncom/cobblemon/mod/common/net/messages/client/data/SpeciesFeatureSyncPacket\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,68:1\n800#2,11:69\n766#2:80\n857#2,2:81\n1#3:83\n*S KotlinDebug\n*F\n+ 1 SpeciesFeatureSyncPacket.kt\ncom/cobblemon/mod/common/net/messages/client/data/SpeciesFeatureSyncPacket\n*L\n25#1:69,11\n25#1:80\n25#1:81,2\n*E\n"})
public abstract class SpeciesFeatureSyncPacket<T extends SpeciesFeatureSyncPacket<T>>
extends DataRegistrySyncPacket<Map.Entry<? extends String, ? extends SynchronizedSpeciesFeatureProvider<?>>, T> {
    /*
     * WARNING - void declaration
     */
    public SpeciesFeatureSyncPacket(@NotNull Map<String, ? extends SpeciesFeatureProvider<?>> speciesFeatureProviders) {
        void $this$filterTo$iv$iv;
        void $this$filter$iv;
        void $this$filterIsInstanceTo$iv$iv;
        Iterable $this$filterIsInstance$iv;
        Intrinsics.checkNotNullParameter(speciesFeatureProviders, (String)"speciesFeatureProviders");
        Iterable iterable = speciesFeatureProviders.entrySet();
        SpeciesFeatureSyncPacket speciesFeatureSyncPacket = this;
        boolean $i$f$filterIsInstance = false;
        void var4_5 = $this$filterIsInstance$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterIsInstanceTo = false;
        for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
            if (!(element$iv$iv instanceof Map.Entry)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        $this$filterIsInstance$iv = (List)destination$iv$iv;
        boolean $i$f$filter = false;
        $this$filterIsInstanceTo$iv$iv = $this$filter$iv;
        destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            Map.Entry it = (Map.Entry)element$iv$iv;
            boolean bl = false;
            if (!((SynchronizedSpeciesFeatureProvider)it.getValue()).getVisible()) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        super((List)destination$iv$iv);
    }

    @Override
    public void encodeEntry(@NotNull FriendlyByteBuf buffer, @NotNull Map.Entry<String, ? extends SynchronizedSpeciesFeatureProvider<?>> entry) {
        Object v0;
        block2: {
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            Intrinsics.checkNotNullParameter(entry, (String)"entry");
            Iterable iterable = SpeciesFeatures.INSTANCE.getTypes().entrySet();
            for (Object t : iterable) {
                Map.Entry it = (Map.Entry)t;
                boolean bl = false;
                if (!((Class)it.getValue()).isInstance(entry.getValue())) continue;
                v0 = t;
                break block2;
            }
            v0 = null;
        }
        Map.Entry entry2 = v0;
        String typeName = entry2 != null ? (String)entry2.getKey() : null;
        SynchronizedSpeciesFeatureProvider<?> value2 = entry.getValue();
        if (typeName == null) {
            buffer.writeBoolean(false);
            return;
        }
        buffer.writeBoolean(true);
        buffer.m_130070_(entry.getKey());
        buffer.m_130070_(typeName);
        value2.encode(buffer);
    }

    @Override
    @Nullable
    public Map.Entry<String, SynchronizedSpeciesFeatureProvider<?>> decodeEntry(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        if (!buffer.readBoolean()) {
            return null;
        }
        String name = buffer.m_130277_();
        String typeName = buffer.m_130277_();
        Class<SpeciesFeatureProvider<?>> clazz = SpeciesFeatures.INSTANCE.getTypes().get(typeName);
        if (clazz == null) {
            throw new IllegalStateException(StringsKt.trimIndent((String)("\n                    A custom species feature provider, " + typeName + " with encoding implementations was registered on the server and \n                    not the client, and therefore cannot be synced. Remove the implementation or install it \n                    on the client.\n                ")));
        }
        Class<SpeciesFeatureProvider<?>> typeClass = clazz;
        SpeciesFeatureProvider<?> instance = typeClass.getConstructor(new Class[0]).newInstance(new Object[0]);
        if (!(instance instanceof SynchronizedSpeciesFeatureProvider)) {
            throw new IllegalStateException("Somehow a non-SynchronizedSpeciesFeatureProvider was sent to the client. Version mismatch?");
        }
        ((SynchronizedSpeciesFeatureProvider)instance).decode(buffer);
        return new Map.Entry<String, SynchronizedSpeciesFeatureProvider<?>>(name, instance){
            @NotNull
            private final String key;
            @NotNull
            private final SynchronizedSpeciesFeatureProvider<?> value;
            {
                Intrinsics.checkNotNullExpressionValue((Object)$name, (String)"name");
                this.key = $name;
                Intrinsics.checkNotNullExpressionValue($instance, (String)"instance");
                this.value = (SynchronizedSpeciesFeatureProvider)$instance;
            }

            @NotNull
            public String getKey() {
                return this.key;
            }

            @NotNull
            public SynchronizedSpeciesFeatureProvider<?> getValue() {
                return this.value;
            }

            public SynchronizedSpeciesFeatureProvider<?> setValue(SynchronizedSpeciesFeatureProvider<?> newValue) {
                throw new UnsupportedOperationException("Operation is not supported for read-only collection");
            }
        };
    }
}

