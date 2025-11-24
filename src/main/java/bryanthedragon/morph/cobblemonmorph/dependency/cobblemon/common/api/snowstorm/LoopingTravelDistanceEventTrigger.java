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
 *  kotlin.Metadata
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0002\b\n\u0018\u0000 \"2\u00020\u00012\u00020\u0002:\u0001\"B\u001d\u0012\u0006\u0010\u0012\u001a\u00020\u0007\u0012\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018\u00a2\u0006\u0004\b \u0010!J/\u0010\u000b\u001a\u00020\n2\u0006\u0010\u0004\u001a\u00020\u00032\b\u0010\u0006\u001a\u0004\u0018\u00010\u00052\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0007\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0017\u0010\u000f\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0017\u0010\u0011\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0010R\"\u0010\u0012\u001a\u00020\u00078\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R(\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00190\u00188\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001f\u00a8\u0006#"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/LoopingTravelDistanceEventTrigger;", "Lcom/cobblemon/mod/common/api/net/Encodable;", "Lcom/cobblemon/mod/common/api/net/Decodable;", "Lcom/cobblemon/mod/common/client/particle/ParticleStorm;", "storm", "Lcom/cobblemon/mod/common/client/render/SnowstormParticle;", "particle", "", "previousDistance", "currentDistance", "", "check", "(Lcom/cobblemon/mod/common/client/particle/ParticleStorm;Lcom/cobblemon/mod/common/client/render/SnowstormParticle;DD)V", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "encode", "distance", "D", "getDistance", "()D", "setDistance", "(D)V", "", "", "events", "Ljava/util/List;", "getEvents", "()Ljava/util/List;", "setEvents", "(Ljava/util/List;)V", "<init>", "(DLjava/util/List;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nParticleEventTrigger.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ParticleEventTrigger.kt\ncom/cobblemon/mod/common/api/snowstorm/LoopingTravelDistanceEventTrigger\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,118:1\n1603#2,9:119\n1855#2:128\n1856#2:130\n1612#2:131\n1855#2,2:132\n1#3:129\n*S KotlinDebug\n*F\n+ 1 ParticleEventTrigger.kt\ncom/cobblemon/mod/common/api/snowstorm/LoopingTravelDistanceEventTrigger\n*L\n115#1:119,9\n115#1:128\n115#1:130\n115#1:131\n115#1:132,2\n115#1:129\n*E\n"})
public final class LoopingTravelDistanceEventTrigger
implements Encodable,
Decodable {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private double distance;
    @NotNull
    private List<String> events;
    private static final Codec<LoopingTravelDistanceEventTrigger> CODEC = RecordCodecBuilder.create(LoopingTravelDistanceEventTrigger::CODEC$lambda$6);

    public LoopingTravelDistanceEventTrigger(double distance, @NotNull List<String> events) {
        Intrinsics.checkNotNullParameter(events, (String)"events");
        this.distance = distance;
        this.events = events;
    }

    public final double getDistance() {
        return this.distance;
    }

    public final void setDistance(double d) {
        this.distance = d;
    }

    @NotNull
    public final List<String> getEvents() {
        return this.events;
    }

    public final void setEvents(@NotNull List<String> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.events = list;
    }

    @Override
    public void encode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.writeDouble(this.distance);
        buffer.m_236828_((Collection)this.events, (arg_0, arg_1) -> LoopingTravelDistanceEventTrigger.encode$lambda$0(buffer, arg_0, arg_1));
    }

    @Override
    public void decode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        this.distance = buffer.readDouble();
        List list = buffer.m_236845_(arg_0 -> LoopingTravelDistanceEventTrigger.decode$lambda$1(buffer, arg_0));
        Intrinsics.checkNotNullExpressionValue((Object)list, (String)"buffer.readList { buffer.readString() }");
        this.events = list;
    }

    /*
     * WARNING - void declaration
     */
    public final void check(@NotNull ParticleStorm storm2, @Nullable SnowstormParticle particle, double previousDistance, double currentDistance) {
        Intrinsics.checkNotNullParameter((Object)((Object)storm2), (String)"storm");
        if (previousDistance < this.distance && currentDistance >= this.distance) {
            void $this$forEach$iv;
            void $this$mapNotNullTo$iv$iv;
            Iterable $this$mapNotNull$iv = this.events;
            boolean $i$f$mapNotNull = false;
            Iterable iterable = $this$mapNotNull$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$mapNotNullTo = false;
            void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
            boolean $i$f$forEach = false;
            Iterator iterator = $this$forEach$iv$iv$iv.iterator();
            while (iterator.hasNext()) {
                ParticleEvent it$iv$iv;
                Object element$iv$iv$iv;
                Object element$iv$iv = element$iv$iv$iv = iterator.next();
                boolean bl = false;
                String it = (String)element$iv$iv;
                boolean bl2 = false;
                if (storm2.getEffect().getEvents().get(it) == null) continue;
                boolean bl3 = false;
                destination$iv$iv.add(it$iv$iv);
            }
            $this$mapNotNull$iv = (List)destination$iv$iv;
            boolean $i$f$forEach2 = false;
            for (Object element$iv : $this$forEach$iv) {
                ParticleEvent it = (ParticleEvent)element$iv;
                boolean bl = false;
                it.run(storm2, particle);
            }
        }
    }

    private static final void encode$lambda$0(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, String s) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        $buffer.m_130070_(s);
    }

    private static final String decode$lambda$1(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        return $buffer.m_130277_();
    }

    private static final Double CODEC$lambda$6$lambda$4(LoopingTravelDistanceEventTrigger it) {
        return it.distance;
    }

    private static final List CODEC$lambda$6$lambda$5(LoopingTravelDistanceEventTrigger it) {
        return it.events;
    }

    private static final App CODEC$lambda$6(RecordCodecBuilder.Instance instance) {
        return instance.group((App)PrimitiveCodec.DOUBLE.fieldOf("distance").forGetter(LoopingTravelDistanceEventTrigger::CODEC$lambda$6$lambda$4), (App)PrimitiveCodec.STRING.listOf().fieldOf("events").forGetter(LoopingTravelDistanceEventTrigger::CODEC$lambda$6$lambda$5)).apply((Applicative)instance, LoopingTravelDistanceEventTrigger::new);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nR;\u0010\u0005\u001a&\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u0003 \u0004*\u0012\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u0003\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/LoopingTravelDistanceEventTrigger$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/api/snowstorm/LoopingTravelDistanceEventTrigger;", "kotlin.jvm.PlatformType", "CODEC", "Lcom/mojang/serialization/Codec;", "getCODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public final Codec<LoopingTravelDistanceEventTrigger> getCODEC() {
            return CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

