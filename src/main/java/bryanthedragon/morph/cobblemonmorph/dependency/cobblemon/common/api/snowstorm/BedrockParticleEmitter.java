/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.ListCodec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Instance
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.FriendlyByteBuf
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.MoLang;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.NumberExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.codec.CodecMapped;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.EventTriggerTimeline;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.InstantParticleEmitterRate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.LoopingTravelDistanceEventTrigger;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.OnceEmitterLifetime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleEmitterLifetime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleEmitterRate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleEmitterShape;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.SimpleEventTrigger;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.SphereParticleEmitterShape;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.codec.ExpressionCodecKt;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.ListCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u0000 ?2\u00020\u0001:\u0001?B\u0089\u0001\u0012\u000e\b\u0002\u00104\u001a\b\u0012\u0004\u0012\u0002030\b\u0012\u000e\b\u0002\u0010:\u001a\b\u0012\u0004\u0012\u0002030\b\u0012\b\b\u0002\u0010&\u001a\u00020%\u0012\b\b\u0002\u0010-\u001a\u00020,\u0012\b\b\u0002\u0010\u001b\u001a\u00020\u001a\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0010\u0012\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\b\u0012\u000e\b\u0002\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\t0\b\u0012\b\b\u0002\u00107\u001a\u00020\u0010\u0012\u000e\b\u0002\u0010\"\u001a\b\u0012\u0004\u0012\u00020!0\b\u00a2\u0006\u0004\b=\u0010>J\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0015\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0007\u0010\u0006R(\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\"\u0010\u0011\u001a\u00020\u00108\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R(\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\t0\b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0017\u0010\u000b\u001a\u0004\b\u0018\u0010\r\"\u0004\b\u0019\u0010\u000fR\"\u0010\u001b\u001a\u00020\u001a8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R(\u0010\"\u001a\b\u0012\u0004\u0012\u00020!0\b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\"\u0010\u000b\u001a\u0004\b#\u0010\r\"\u0004\b$\u0010\u000fR\"\u0010&\u001a\u00020%8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b&\u0010'\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\"\u0010-\u001a\u00020,8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b-\u0010.\u001a\u0004\b/\u00100\"\u0004\b1\u00102R(\u00104\u001a\b\u0012\u0004\u0012\u0002030\b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b4\u0010\u000b\u001a\u0004\b5\u0010\r\"\u0004\b6\u0010\u000fR\"\u00107\u001a\u00020\u00108\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b7\u0010\u0012\u001a\u0004\b8\u0010\u0014\"\u0004\b9\u0010\u0016R(\u0010:\u001a\b\u0012\u0004\u0012\u0002030\b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b:\u0010\u000b\u001a\u0004\b;\u0010\r\"\u0004\b<\u0010\u000f\u00a8\u0006@"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/BedrockParticleEmitter;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "readFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "writeToBuffer", "", "Lcom/cobblemon/mod/common/api/snowstorm/SimpleEventTrigger;", "creationEvents", "Ljava/util/List;", "getCreationEvents", "()Ljava/util/List;", "setCreationEvents", "(Ljava/util/List;)V", "Lcom/cobblemon/mod/common/api/snowstorm/EventTriggerTimeline;", "eventTimeline", "Lcom/cobblemon/mod/common/api/snowstorm/EventTriggerTimeline;", "getEventTimeline", "()Lcom/cobblemon/mod/common/api/snowstorm/EventTriggerTimeline;", "setEventTimeline", "(Lcom/cobblemon/mod/common/api/snowstorm/EventTriggerTimeline;)V", "expirationEvents", "getExpirationEvents", "setExpirationEvents", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterLifetime;", "lifetime", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterLifetime;", "getLifetime", "()Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterLifetime;", "setLifetime", "(Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterLifetime;)V", "Lcom/cobblemon/mod/common/api/snowstorm/LoopingTravelDistanceEventTrigger;", "loopingTravelDistanceEvents", "getLoopingTravelDistanceEvents", "setLoopingTravelDistanceEvents", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterRate;", "rate", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterRate;", "getRate", "()Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterRate;", "setRate", "(Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterRate;)V", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterShape;", "shape", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterShape;", "getShape", "()Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterShape;", "setShape", "(Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterShape;)V", "Lcom/bedrockk/molang/Expression;", "startExpressions", "getStartExpressions", "setStartExpressions", "travelDistanceEvents", "getTravelDistanceEvents", "setTravelDistanceEvents", "updateExpressions", "getUpdateExpressions", "setUpdateExpressions", "<init>", "(Ljava/util/List;Ljava/util/List;Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterRate;Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterShape;Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterLifetime;Lcom/cobblemon/mod/common/api/snowstorm/EventTriggerTimeline;Ljava/util/List;Ljava/util/List;Lcom/cobblemon/mod/common/api/snowstorm/EventTriggerTimeline;Ljava/util/List;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nBedrockParticleEmitter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BedrockParticleEmitter.kt\ncom/cobblemon/mod/common/api/snowstorm/BedrockParticleEmitter\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,94:1\n1#2:95\n*E\n"})
public final class BedrockParticleEmitter {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private List<Expression> startExpressions;
    @NotNull
    private List<Expression> updateExpressions;
    @NotNull
    private ParticleEmitterRate rate;
    @NotNull
    private ParticleEmitterShape shape;
    @NotNull
    private ParticleEmitterLifetime lifetime;
    @NotNull
    private EventTriggerTimeline eventTimeline;
    @NotNull
    private List<SimpleEventTrigger> creationEvents;
    @NotNull
    private List<SimpleEventTrigger> expirationEvents;
    @NotNull
    private EventTriggerTimeline travelDistanceEvents;
    @NotNull
    private List<LoopingTravelDistanceEventTrigger> loopingTravelDistanceEvents;
    @NotNull
    private static final Codec<BedrockParticleEmitter> CODEC;

    public BedrockParticleEmitter(@NotNull List<Expression> startExpressions, @NotNull List<Expression> updateExpressions, @NotNull ParticleEmitterRate rate, @NotNull ParticleEmitterShape shape, @NotNull ParticleEmitterLifetime lifetime, @NotNull EventTriggerTimeline eventTimeline, @NotNull List<SimpleEventTrigger> creationEvents, @NotNull List<SimpleEventTrigger> expirationEvents, @NotNull EventTriggerTimeline travelDistanceEvents, @NotNull List<LoopingTravelDistanceEventTrigger> loopingTravelDistanceEvents) {
        Intrinsics.checkNotNullParameter(startExpressions, (String)"startExpressions");
        Intrinsics.checkNotNullParameter(updateExpressions, (String)"updateExpressions");
        Intrinsics.checkNotNullParameter((Object)rate, (String)"rate");
        Intrinsics.checkNotNullParameter((Object)shape, (String)"shape");
        Intrinsics.checkNotNullParameter((Object)lifetime, (String)"lifetime");
        Intrinsics.checkNotNullParameter((Object)eventTimeline, (String)"eventTimeline");
        Intrinsics.checkNotNullParameter(creationEvents, (String)"creationEvents");
        Intrinsics.checkNotNullParameter(expirationEvents, (String)"expirationEvents");
        Intrinsics.checkNotNullParameter((Object)travelDistanceEvents, (String)"travelDistanceEvents");
        Intrinsics.checkNotNullParameter(loopingTravelDistanceEvents, (String)"loopingTravelDistanceEvents");
        this.startExpressions = startExpressions;
        this.updateExpressions = updateExpressions;
        this.rate = rate;
        this.shape = shape;
        this.lifetime = lifetime;
        this.eventTimeline = eventTimeline;
        this.creationEvents = creationEvents;
        this.expirationEvents = expirationEvents;
        this.travelDistanceEvents = travelDistanceEvents;
        this.loopingTravelDistanceEvents = loopingTravelDistanceEvents;
    }

    public /* synthetic */ BedrockParticleEmitter(List list, List list2, ParticleEmitterRate particleEmitterRate, ParticleEmitterShape particleEmitterShape, ParticleEmitterLifetime particleEmitterLifetime, EventTriggerTimeline eventTriggerTimeline, List list3, List list4, EventTriggerTimeline eventTriggerTimeline2, List list5, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            list = new ArrayList();
        }
        if ((n & 2) != 0) {
            list2 = new ArrayList();
        }
        if ((n & 4) != 0) {
            particleEmitterRate = new InstantParticleEmitterRate(null, 1, null);
        }
        if ((n & 8) != 0) {
            particleEmitterShape = new SphereParticleEmitterShape(null, null, false, 7, null);
        }
        if ((n & 0x10) != 0) {
            particleEmitterLifetime = new OnceEmitterLifetime(new NumberExpression(1.0));
        }
        if ((n & 0x20) != 0) {
            eventTriggerTimeline = new EventTriggerTimeline(new LinkedHashMap());
        }
        if ((n & 0x40) != 0) {
            list3 = new ArrayList();
        }
        if ((n & 0x80) != 0) {
            list4 = new ArrayList();
        }
        if ((n & 0x100) != 0) {
            eventTriggerTimeline2 = new EventTriggerTimeline(new LinkedHashMap());
        }
        if ((n & 0x200) != 0) {
            list5 = new ArrayList();
        }
        this(list, list2, particleEmitterRate, particleEmitterShape, particleEmitterLifetime, eventTriggerTimeline, list3, list4, eventTriggerTimeline2, list5);
    }

    @NotNull
    public final List<Expression> getStartExpressions() {
        return this.startExpressions;
    }

    public final void setStartExpressions(@NotNull List<Expression> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.startExpressions = list;
    }

    @NotNull
    public final List<Expression> getUpdateExpressions() {
        return this.updateExpressions;
    }

    public final void setUpdateExpressions(@NotNull List<Expression> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.updateExpressions = list;
    }

    @NotNull
    public final ParticleEmitterRate getRate() {
        return this.rate;
    }

    public final void setRate(@NotNull ParticleEmitterRate particleEmitterRate) {
        Intrinsics.checkNotNullParameter((Object)particleEmitterRate, (String)"<set-?>");
        this.rate = particleEmitterRate;
    }

    @NotNull
    public final ParticleEmitterShape getShape() {
        return this.shape;
    }

    public final void setShape(@NotNull ParticleEmitterShape particleEmitterShape) {
        Intrinsics.checkNotNullParameter((Object)particleEmitterShape, (String)"<set-?>");
        this.shape = particleEmitterShape;
    }

    @NotNull
    public final ParticleEmitterLifetime getLifetime() {
        return this.lifetime;
    }

    public final void setLifetime(@NotNull ParticleEmitterLifetime particleEmitterLifetime) {
        Intrinsics.checkNotNullParameter((Object)particleEmitterLifetime, (String)"<set-?>");
        this.lifetime = particleEmitterLifetime;
    }

    @NotNull
    public final EventTriggerTimeline getEventTimeline() {
        return this.eventTimeline;
    }

    public final void setEventTimeline(@NotNull EventTriggerTimeline eventTriggerTimeline) {
        Intrinsics.checkNotNullParameter((Object)eventTriggerTimeline, (String)"<set-?>");
        this.eventTimeline = eventTriggerTimeline;
    }

    @NotNull
    public final List<SimpleEventTrigger> getCreationEvents() {
        return this.creationEvents;
    }

    public final void setCreationEvents(@NotNull List<SimpleEventTrigger> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.creationEvents = list;
    }

    @NotNull
    public final List<SimpleEventTrigger> getExpirationEvents() {
        return this.expirationEvents;
    }

    public final void setExpirationEvents(@NotNull List<SimpleEventTrigger> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.expirationEvents = list;
    }

    @NotNull
    public final EventTriggerTimeline getTravelDistanceEvents() {
        return this.travelDistanceEvents;
    }

    public final void setTravelDistanceEvents(@NotNull EventTriggerTimeline eventTriggerTimeline) {
        Intrinsics.checkNotNullParameter((Object)eventTriggerTimeline, (String)"<set-?>");
        this.travelDistanceEvents = eventTriggerTimeline;
    }

    @NotNull
    public final List<LoopingTravelDistanceEventTrigger> getLoopingTravelDistanceEvents() {
        return this.loopingTravelDistanceEvents;
    }

    public final void setLoopingTravelDistanceEvents(@NotNull List<LoopingTravelDistanceEventTrigger> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.loopingTravelDistanceEvents = list;
    }

    public final void writeToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_236828_((Collection)this.startExpressions, BedrockParticleEmitter::writeToBuffer$lambda$0);
        buffer.m_236828_((Collection)this.updateExpressions, BedrockParticleEmitter::writeToBuffer$lambda$1);
        ParticleEmitterRate.Companion.writeToBuffer(buffer, (CodecMapped)this.rate);
        ParticleEmitterShape.Companion.writeToBuffer(buffer, (CodecMapped)this.shape);
        ParticleEmitterLifetime.Companion.writeToBuffer(buffer, (CodecMapped)this.lifetime);
        this.eventTimeline.encode(buffer);
        buffer.m_236828_((Collection)this.creationEvents, BedrockParticleEmitter::writeToBuffer$lambda$2);
        buffer.m_236828_((Collection)this.expirationEvents, BedrockParticleEmitter::writeToBuffer$lambda$3);
        this.travelDistanceEvents.encode(buffer);
        buffer.m_236828_((Collection)this.loopingTravelDistanceEvents, BedrockParticleEmitter::writeToBuffer$lambda$4);
    }

    public final void readFromBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        List list = buffer.m_236845_(arg_0 -> BedrockParticleEmitter.readFromBuffer$lambda$5(buffer, arg_0));
        Intrinsics.checkNotNullExpressionValue((Object)list, (String)"buffer.readList { MoLang\u2026ng()).parseExpression() }");
        this.startExpressions = list;
        List list2 = buffer.m_236845_(arg_0 -> BedrockParticleEmitter.readFromBuffer$lambda$6(buffer, arg_0));
        Intrinsics.checkNotNullExpressionValue((Object)list2, (String)"buffer.readList { MoLang\u2026ng()).parseExpression() }");
        this.updateExpressions = list2;
        this.rate = (ParticleEmitterRate)ParticleEmitterRate.Companion.readFromBuffer(buffer);
        this.shape = (ParticleEmitterShape)ParticleEmitterShape.Companion.readFromBuffer(buffer);
        this.lifetime = (ParticleEmitterLifetime)ParticleEmitterLifetime.Companion.readFromBuffer(buffer);
        this.eventTimeline.decode(buffer);
        List list3 = buffer.m_236845_(arg_0 -> BedrockParticleEmitter.readFromBuffer$lambda$8(buffer, arg_0));
        Intrinsics.checkNotNullExpressionValue((Object)list3, (String)"buffer.readList { Simple\u2026o { it.decode(buffer) } }");
        this.creationEvents = list3;
        List list4 = buffer.m_236845_(arg_0 -> BedrockParticleEmitter.readFromBuffer$lambda$10(buffer, arg_0));
        Intrinsics.checkNotNullExpressionValue((Object)list4, (String)"buffer.readList { Simple\u2026o { it.decode(buffer) } }");
        this.expirationEvents = list4;
        this.travelDistanceEvents.decode(buffer);
        List list5 = buffer.m_236845_(arg_0 -> BedrockParticleEmitter.readFromBuffer$lambda$12(buffer, arg_0));
        Intrinsics.checkNotNullExpressionValue((Object)list5, (String)"buffer.readList { Loopin\u2026o { it.decode(buffer) } }");
        this.loopingTravelDistanceEvents = list5;
    }

    private static final void writeToBuffer$lambda$0(FriendlyByteBuf pb, Expression expression) {
        Intrinsics.checkNotNullExpressionValue((Object)expression, (String)"expression");
        pb.m_130070_(MoLangExtensionsKt.getString(expression));
    }

    private static final void writeToBuffer$lambda$1(FriendlyByteBuf pb, Expression expression) {
        Intrinsics.checkNotNullExpressionValue((Object)expression, (String)"expression");
        pb.m_130070_(MoLangExtensionsKt.getString(expression));
    }

    private static final void writeToBuffer$lambda$2(FriendlyByteBuf pb, SimpleEventTrigger event) {
        Intrinsics.checkNotNullExpressionValue((Object)pb, (String)"pb");
        event.encode(pb);
    }

    private static final void writeToBuffer$lambda$3(FriendlyByteBuf pb, SimpleEventTrigger event) {
        Intrinsics.checkNotNullExpressionValue((Object)pb, (String)"pb");
        event.encode(pb);
    }

    private static final void writeToBuffer$lambda$4(FriendlyByteBuf pb, LoopingTravelDistanceEventTrigger event) {
        Intrinsics.checkNotNullExpressionValue((Object)pb, (String)"pb");
        event.encode(pb);
    }

    private static final Expression readFromBuffer$lambda$5(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        return MoLang.createParser($buffer.m_130277_()).parseExpression();
    }

    private static final Expression readFromBuffer$lambda$6(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        return MoLang.createParser($buffer.m_130277_()).parseExpression();
    }

    private static final SimpleEventTrigger readFromBuffer$lambda$8(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
        SimpleEventTrigger simpleEventTrigger;
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        SimpleEventTrigger it2 = simpleEventTrigger = new SimpleEventTrigger("");
        boolean bl = false;
        it2.decode($buffer);
        return simpleEventTrigger;
    }

    private static final SimpleEventTrigger readFromBuffer$lambda$10(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
        SimpleEventTrigger simpleEventTrigger;
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        SimpleEventTrigger it2 = simpleEventTrigger = new SimpleEventTrigger("");
        boolean bl = false;
        it2.decode($buffer);
        return simpleEventTrigger;
    }

    private static final LoopingTravelDistanceEventTrigger readFromBuffer$lambda$12(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
        LoopingTravelDistanceEventTrigger loopingTravelDistanceEventTrigger;
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        LoopingTravelDistanceEventTrigger it2 = loopingTravelDistanceEventTrigger = new LoopingTravelDistanceEventTrigger(0.0, new ArrayList());
        boolean bl = false;
        it2.decode($buffer);
        return loopingTravelDistanceEventTrigger;
    }

    private static final List CODEC$lambda$24$lambda$13(BedrockParticleEmitter it) {
        return it.startExpressions;
    }

    private static final List CODEC$lambda$24$lambda$14(BedrockParticleEmitter it) {
        return it.updateExpressions;
    }

    private static final ParticleEmitterRate CODEC$lambda$24$lambda$15(BedrockParticleEmitter it) {
        return it.rate;
    }

    private static final ParticleEmitterShape CODEC$lambda$24$lambda$16(BedrockParticleEmitter it) {
        return it.shape;
    }

    private static final ParticleEmitterLifetime CODEC$lambda$24$lambda$17(BedrockParticleEmitter it) {
        return it.lifetime;
    }

    private static final EventTriggerTimeline CODEC$lambda$24$lambda$18(BedrockParticleEmitter it) {
        return it.eventTimeline;
    }

    private static final List CODEC$lambda$24$lambda$19(BedrockParticleEmitter it) {
        return it.creationEvents;
    }

    private static final List CODEC$lambda$24$lambda$20(BedrockParticleEmitter it) {
        return it.expirationEvents;
    }

    private static final EventTriggerTimeline CODEC$lambda$24$lambda$21(BedrockParticleEmitter it) {
        return it.travelDistanceEvents;
    }

    private static final List CODEC$lambda$24$lambda$22(BedrockParticleEmitter it) {
        return it.loopingTravelDistanceEvents;
    }

    private static final BedrockParticleEmitter CODEC$lambda$24$lambda$23(List startExpressions, List updateExpressions, ParticleEmitterRate rate, ParticleEmitterShape shape, ParticleEmitterLifetime lifetime, EventTriggerTimeline eventTimeline, List creationEvents, List expirationEvents, EventTriggerTimeline travelDistanceEvents, List loopingTravelDistanceEvents) {
        Intrinsics.checkNotNullExpressionValue((Object)startExpressions, (String)"startExpressions");
        Intrinsics.checkNotNullExpressionValue((Object)updateExpressions, (String)"updateExpressions");
        Intrinsics.checkNotNullExpressionValue((Object)rate, (String)"rate");
        Intrinsics.checkNotNullExpressionValue((Object)shape, (String)"shape");
        Intrinsics.checkNotNullExpressionValue((Object)lifetime, (String)"lifetime");
        Intrinsics.checkNotNullExpressionValue((Object)eventTimeline, (String)"eventTimeline");
        Intrinsics.checkNotNullExpressionValue((Object)creationEvents, (String)"creationEvents");
        Intrinsics.checkNotNullExpressionValue((Object)expirationEvents, (String)"expirationEvents");
        Intrinsics.checkNotNullExpressionValue((Object)travelDistanceEvents, (String)"travelDistanceEvents");
        Intrinsics.checkNotNullExpressionValue((Object)loopingTravelDistanceEvents, (String)"loopingTravelDistanceEvents");
        return new BedrockParticleEmitter(startExpressions, updateExpressions, rate, shape, lifetime, eventTimeline, creationEvents, expirationEvents, travelDistanceEvents, loopingTravelDistanceEvents);
    }

    private static final App CODEC$lambda$24(RecordCodecBuilder.Instance instance) {
        return instance.group((App)new ListCodec((Codec)ExpressionCodecKt.getEXPRESSION_CODEC()).fieldOf("startExpressions").forGetter(BedrockParticleEmitter::CODEC$lambda$24$lambda$13), (App)new ListCodec((Codec)ExpressionCodecKt.getEXPRESSION_CODEC()).fieldOf("updateExpressions").forGetter(BedrockParticleEmitter::CODEC$lambda$24$lambda$14), (App)ParticleEmitterRate.Companion.getCodec().fieldOf("rate").forGetter(BedrockParticleEmitter::CODEC$lambda$24$lambda$15), (App)ParticleEmitterShape.Companion.getCodec().fieldOf("shape").forGetter(BedrockParticleEmitter::CODEC$lambda$24$lambda$16), (App)ParticleEmitterLifetime.Companion.getCodec().fieldOf("lifetime").forGetter(BedrockParticleEmitter::CODEC$lambda$24$lambda$17), (App)EventTriggerTimeline.Companion.getCODEC().fieldOf("eventTimeline").forGetter(BedrockParticleEmitter::CODEC$lambda$24$lambda$18), (App)new ListCodec(SimpleEventTrigger.Companion.getCODEC()).fieldOf("creationEvents").forGetter(BedrockParticleEmitter::CODEC$lambda$24$lambda$19), (App)new ListCodec(SimpleEventTrigger.Companion.getCODEC()).fieldOf("expirationEvents").forGetter(BedrockParticleEmitter::CODEC$lambda$24$lambda$20), (App)EventTriggerTimeline.Companion.getCODEC().fieldOf("travelDistanceEvents").forGetter(BedrockParticleEmitter::CODEC$lambda$24$lambda$21), (App)new ListCodec(LoopingTravelDistanceEventTrigger.Companion.getCODEC()).fieldOf("loopingTravelDistanceEvents").forGetter(BedrockParticleEmitter::CODEC$lambda$24$lambda$22)).apply((Applicative)instance, BedrockParticleEmitter::CODEC$lambda$24$lambda$23);
    }

    public BedrockParticleEmitter() {
        this(null, null, null, null, null, null, null, null, null, null, 1023, null);
    }

    static {
        Codec codec2 = RecordCodecBuilder.create(BedrockParticleEmitter::CODEC$lambda$24);
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"create { instance ->\n   \u2026)\n            }\n        }");
        CODEC = codec2;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/BedrockParticleEmitter$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/api/snowstorm/BedrockParticleEmitter;", "CODEC", "Lcom/mojang/serialization/Codec;", "getCODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Codec<BedrockParticleEmitter> getCODEC() {
            return CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

