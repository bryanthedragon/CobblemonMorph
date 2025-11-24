/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.PrimitiveCodec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Instance
 *  com.mojang.serialization.codecs.UnboundedMapCodec
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.FriendlyByteBuf
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Decodable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Encodable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.ParticleStorm;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.SnowstormParticle;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.UnboundedMapCodec;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010%\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0002\b\t\u0018\u0000 \u001c2\u00020\u00012\u00020\u0002:\u0001\u001cB!\u0012\u0018\u0010\u0015\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u00130\u0012\u00a2\u0006\u0004\b\u001b\u0010\u001aJ/\u0010\u000b\u001a\u00020\n2\u0006\u0010\u0004\u001a\u00020\u00032\b\u0010\u0006\u001a\u0004\u0018\u00010\u00052\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0007\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0017\u0010\u000f\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0017\u0010\u0011\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0010R4\u0010\u0015\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u00130\u00128\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001a\u00a8\u0006\u001d"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/EventTriggerTimeline;", "Lcom/cobblemon/mod/common/api/net/Encodable;", "Lcom/cobblemon/mod/common/api/net/Decodable;", "Lcom/cobblemon/mod/common/client/particle/ParticleStorm;", "storm", "Lcom/cobblemon/mod/common/client/render/SnowstormParticle;", "particle", "", "previousTime", "newTime", "", "check", "(Lcom/cobblemon/mod/common/client/particle/ParticleStorm;Lcom/cobblemon/mod/common/client/render/SnowstormParticle;DD)V", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "encode", "", "", "", "map", "Ljava/util/Map;", "getMap", "()Ljava/util/Map;", "setMap", "(Ljava/util/Map;)V", "<init>", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nParticleEventTrigger.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ParticleEventTrigger.kt\ncom/cobblemon/mod/common/api/snowstorm/EventTriggerTimeline\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,118:1\n766#2:119\n857#2,2:120\n1360#2:122\n1446#2,5:123\n1855#2,2:128\n*S KotlinDebug\n*F\n+ 1 ParticleEventTrigger.kt\ncom/cobblemon/mod/common/api/snowstorm/EventTriggerTimeline\n*L\n78#1:119\n78#1:120,2\n78#1:122\n78#1:123,5\n79#1:128,2\n*E\n"})
public final class EventTriggerTimeline
implements Encodable,
Decodable {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private Map<Double, List<String>> map;
    private static final Codec<EventTriggerTimeline> CODEC = RecordCodecBuilder.create(EventTriggerTimeline::CODEC$lambda$10);

    public EventTriggerTimeline(@NotNull Map<Double, List<String>> map) {
        Intrinsics.checkNotNullParameter(map, (String)"map");
        this.map = map;
    }

    @NotNull
    public final Map<Double, List<String>> getMap() {
        return this.map;
    }

    public final void setMap(@NotNull Map<Double, List<String>> map) {
        Intrinsics.checkNotNullParameter(map, (String)"<set-?>");
        this.map = map;
    }

    @Override
    public void encode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_236831_(this.map, EventTriggerTimeline::encode$lambda$0, EventTriggerTimeline::encode$lambda$2);
    }

    @Override
    public void decode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        Map map = buffer.m_236847_(EventTriggerTimeline::decode$lambda$3, EventTriggerTimeline::decode$lambda$5);
        Intrinsics.checkNotNullExpressionValue((Object)map, (String)"buffer.readMap({ pb -> p\u2026st { pb.readString() } })");
        this.map = map;
    }

    /*
     * WARNING - void declaration
     */
    public final void check(@NotNull ParticleStorm storm2, @Nullable SnowstormParticle particle, double previousTime, double newTime) {
        void $this$flatMapTo$iv$iv;
        void $this$flatMap$iv;
        Map.Entry it;
        void $this$filterTo$iv$iv;
        Intrinsics.checkNotNullParameter((Object)((Object)storm2), (String)"storm");
        Iterable $this$filter$iv = this.map.entrySet();
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            it = (Map.Entry)element$iv$iv;
            boolean bl = false;
            double d = ((Number)it.getKey()).doubleValue();
            boolean bl2 = previousTime <= d ? d <= newTime : false;
            if (!bl2) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        $this$filter$iv = (List)destination$iv$iv;
        boolean $i$f$flatMap = false;
        $this$filterTo$iv$iv = $this$flatMap$iv;
        destination$iv$iv = new ArrayList();
        boolean $i$f$flatMapTo = false;
        for (Object element$iv$iv : $this$flatMapTo$iv$iv) {
            it = (Map.Entry)element$iv$iv;
            boolean bl = false;
            Iterable list$iv$iv = (List)it.getValue();
            CollectionsKt.addAll((Collection)destination$iv$iv, (Iterable)list$iv$iv);
        }
        List events = (List)destination$iv$iv;
        Iterable $this$forEach$iv = events;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            ParticleEvent event;
            String event2 = (String)element$iv;
            boolean bl = false;
            if (storm2.getEffect().getEvents().get(event2) == null) {
                return;
            }
            event.run(storm2, particle);
        }
    }

    private static final void encode$lambda$0(FriendlyByteBuf pb, Double k) {
        Intrinsics.checkNotNullExpressionValue((Object)k, (String)"k");
        pb.writeDouble(k.doubleValue());
    }

    private static final void encode$lambda$2$lambda$1(FriendlyByteBuf $pb, FriendlyByteBuf friendlyByteBuf, String s) {
        $pb.m_130070_(s);
    }

    private static final void encode$lambda$2(FriendlyByteBuf pb, List v) {
        pb.m_236828_((Collection)v, (arg_0, arg_1) -> EventTriggerTimeline.encode$lambda$2$lambda$1(pb, arg_0, arg_1));
    }

    private static final Double decode$lambda$3(FriendlyByteBuf pb) {
        return pb.readDouble();
    }

    private static final String decode$lambda$5$lambda$4(FriendlyByteBuf $pb, FriendlyByteBuf it) {
        return $pb.m_130277_();
    }

    private static final List decode$lambda$5(FriendlyByteBuf pb) {
        return pb.m_236845_(arg_0 -> EventTriggerTimeline.decode$lambda$5$lambda$4(pb, arg_0));
    }

    private static final Map CODEC$lambda$10$lambda$9(EventTriggerTimeline it) {
        return it.map;
    }

    private static final App CODEC$lambda$10(RecordCodecBuilder.Instance instance) {
        return instance.group((App)new UnboundedMapCodec((Codec)PrimitiveCodec.DOUBLE, PrimitiveCodec.STRING.listOf()).fieldOf("map").forGetter(EventTriggerTimeline::CODEC$lambda$10$lambda$9)).apply((Applicative)instance, EventTriggerTimeline::new);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nR;\u0010\u0005\u001a&\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u0003 \u0004*\u0012\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u0003\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/EventTriggerTimeline$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/api/snowstorm/EventTriggerTimeline;", "kotlin.jvm.PlatformType", "CODEC", "Lcom/mojang/serialization/Codec;", "getCODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public final Codec<EventTriggerTimeline> getCODEC() {
            return CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

