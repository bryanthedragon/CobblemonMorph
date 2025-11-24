/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.ListCodec
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
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.codec.CodecMapped;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BedrockParticle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BedrockParticleEmitter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.MoLangCurve;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleSpace;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.ListCodec;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.UnboundedMapCodec;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 72\u00020\u0001:\u00017BU\u0012\b\b\u0002\u0010!\u001a\u00020 \u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0010\u0012\b\b\u0002\u0010(\u001a\u00020'\u0012\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\b\u0012\b\b\u0002\u0010/\u001a\u00020.\u0012\u0014\b\u0002\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00190\u0017\u00a2\u0006\u0004\b5\u00106J\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0015\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0007\u0010\u0006R(\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\"\u0010\u0011\u001a\u00020\u00108\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R.\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00190\u00178\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\"\u0010!\u001a\u00020 8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b!\u0010\"\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\"\u0010(\u001a\u00020'8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b(\u0010)\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-R\"\u0010/\u001a\u00020.8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b/\u00100\u001a\u0004\b1\u00102\"\u0004\b3\u00104\u00a8\u00068"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/BedrockParticleEffect;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "readFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "writeToBuffer", "", "Lcom/cobblemon/mod/common/api/snowstorm/MoLangCurve;", "curves", "Ljava/util/List;", "getCurves", "()Ljava/util/List;", "setCurves", "(Ljava/util/List;)V", "Lcom/cobblemon/mod/common/api/snowstorm/BedrockParticleEmitter;", "emitter", "Lcom/cobblemon/mod/common/api/snowstorm/BedrockParticleEmitter;", "getEmitter", "()Lcom/cobblemon/mod/common/api/snowstorm/BedrockParticleEmitter;", "setEmitter", "(Lcom/cobblemon/mod/common/api/snowstorm/BedrockParticleEmitter;)V", "", "", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleEvent;", "events", "Ljava/util/Map;", "getEvents", "()Ljava/util/Map;", "setEvents", "(Ljava/util/Map;)V", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "setId", "(Lnet/minecraft/resources/ResourceLocation;)V", "Lcom/cobblemon/mod/common/api/snowstorm/BedrockParticle;", "particle", "Lcom/cobblemon/mod/common/api/snowstorm/BedrockParticle;", "getParticle", "()Lcom/cobblemon/mod/common/api/snowstorm/BedrockParticle;", "setParticle", "(Lcom/cobblemon/mod/common/api/snowstorm/BedrockParticle;)V", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleSpace;", "space", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleSpace;", "getSpace", "()Lcom/cobblemon/mod/common/api/snowstorm/ParticleSpace;", "setSpace", "(Lcom/cobblemon/mod/common/api/snowstorm/ParticleSpace;)V", "<init>", "(Lnet/minecraft/resources/ResourceLocation;Lcom/cobblemon/mod/common/api/snowstorm/BedrockParticleEmitter;Lcom/cobblemon/mod/common/api/snowstorm/BedrockParticle;Ljava/util/List;Lcom/cobblemon/mod/common/api/snowstorm/ParticleSpace;Ljava/util/Map;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nBedrockParticleEffect.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BedrockParticleEffect.kt\ncom/cobblemon/mod/common/api/snowstorm/BedrockParticleEffect\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,74:1\n1#2:75\n*E\n"})
public final class BedrockParticleEffect {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private ResourceLocation id;
    @NotNull
    private BedrockParticleEmitter emitter;
    @NotNull
    private BedrockParticle particle;
    @NotNull
    private List<MoLangCurve> curves;
    @NotNull
    private ParticleSpace space;
    @NotNull
    private Map<String, ParticleEvent> events;
    @NotNull
    private static final Codec<BedrockParticleEffect> CODEC;

    public BedrockParticleEffect(@NotNull ResourceLocation id, @NotNull BedrockParticleEmitter emitter, @NotNull BedrockParticle particle, @NotNull List<MoLangCurve> curves, @NotNull ParticleSpace space, @NotNull Map<String, ParticleEvent> events) {
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter((Object)emitter, (String)"emitter");
        Intrinsics.checkNotNullParameter((Object)particle, (String)"particle");
        Intrinsics.checkNotNullParameter(curves, (String)"curves");
        Intrinsics.checkNotNullParameter((Object)space, (String)"space");
        Intrinsics.checkNotNullParameter(events, (String)"events");
        this.id = id;
        this.emitter = emitter;
        this.particle = particle;
        this.curves = curves;
        this.space = space;
        this.events = events;
    }

    public /* synthetic */ BedrockParticleEffect(ResourceLocation resourceLocation, BedrockParticleEmitter bedrockParticleEmitter, BedrockParticle bedrockParticle, List list, ParticleSpace particleSpace, Map map, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            resourceLocation = new ResourceLocation("effect");
        }
        if ((n & 2) != 0) {
            bedrockParticleEmitter = new BedrockParticleEmitter(null, null, null, null, null, null, null, null, null, null, 1023, null);
        }
        if ((n & 4) != 0) {
            bedrockParticle = new BedrockParticle(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, false, null, null, null, 524287, null);
        }
        if ((n & 8) != 0) {
            list = new ArrayList();
        }
        if ((n & 0x10) != 0) {
            particleSpace = new ParticleSpace(false, false, false, 7, null);
        }
        if ((n & 0x20) != 0) {
            map = new LinkedHashMap();
        }
        this(resourceLocation, bedrockParticleEmitter, bedrockParticle, list, particleSpace, map);
    }

    @NotNull
    public final ResourceLocation getId() {
        return this.id;
    }

    public final void setId(@NotNull ResourceLocation resourceLocation) {
        Intrinsics.checkNotNullParameter((Object)resourceLocation, (String)"<set-?>");
        this.id = resourceLocation;
    }

    @NotNull
    public final BedrockParticleEmitter getEmitter() {
        return this.emitter;
    }

    public final void setEmitter(@NotNull BedrockParticleEmitter bedrockParticleEmitter) {
        Intrinsics.checkNotNullParameter((Object)bedrockParticleEmitter, (String)"<set-?>");
        this.emitter = bedrockParticleEmitter;
    }

    @NotNull
    public final BedrockParticle getParticle() {
        return this.particle;
    }

    public final void setParticle(@NotNull BedrockParticle bedrockParticle) {
        Intrinsics.checkNotNullParameter((Object)bedrockParticle, (String)"<set-?>");
        this.particle = bedrockParticle;
    }

    @NotNull
    public final List<MoLangCurve> getCurves() {
        return this.curves;
    }

    public final void setCurves(@NotNull List<MoLangCurve> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.curves = list;
    }

    @NotNull
    public final ParticleSpace getSpace() {
        return this.space;
    }

    public final void setSpace(@NotNull ParticleSpace particleSpace) {
        Intrinsics.checkNotNullParameter((Object)particleSpace, (String)"<set-?>");
        this.space = particleSpace;
    }

    @NotNull
    public final Map<String, ParticleEvent> getEvents() {
        return this.events;
    }

    public final void setEvents(@NotNull Map<String, ParticleEvent> map) {
        Intrinsics.checkNotNullParameter(map, (String)"<set-?>");
        this.events = map;
    }

    public final void writeToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130085_(this.id);
        this.emitter.writeToBuffer(buffer);
        this.particle.writeToBuffer(buffer);
        buffer.m_236828_((Collection)this.curves, (arg_0, arg_1) -> BedrockParticleEffect.writeToBuffer$lambda$0(buffer, arg_0, arg_1));
        this.space.writeToBuffer(buffer);
        buffer.m_236831_(this.events, (arg_0, arg_1) -> BedrockParticleEffect.writeToBuffer$lambda$1(buffer, arg_0, arg_1), (arg_0, arg_1) -> BedrockParticleEffect.writeToBuffer$lambda$2(buffer, arg_0, arg_1));
    }

    public final void readFromBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        ResourceLocation resourceLocation = buffer.m_130281_();
        Intrinsics.checkNotNullExpressionValue((Object)resourceLocation, (String)"buffer.readIdentifier()");
        this.id = resourceLocation;
        this.emitter.readFromBuffer(buffer);
        this.particle.readFromBuffer(buffer);
        List list = buffer.m_236845_(arg_0 -> BedrockParticleEffect.readFromBuffer$lambda$3(buffer, arg_0));
        Intrinsics.checkNotNullExpressionValue((Object)list, (String)"buffer.readList { MoLang\u2026.readFromBuffer(buffer) }");
        this.curves = list;
        this.space.readFromBuffer(buffer);
        Map map = buffer.m_236847_(arg_0 -> BedrockParticleEffect.readFromBuffer$lambda$4(buffer, arg_0), arg_0 -> BedrockParticleEffect.readFromBuffer$lambda$6(buffer, arg_0));
        Intrinsics.checkNotNullExpressionValue((Object)map, (String)"buffer.readMap({ buffer.\u2026o { it.decode(buffer) } }");
        this.events = map;
    }

    private static final void writeToBuffer$lambda$0(FriendlyByteBuf $buffer, FriendlyByteBuf pb, MoLangCurve curve2) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        Intrinsics.checkNotNullExpressionValue((Object)curve2, (String)"curve");
        MoLangCurve.Companion.writeToBuffer($buffer, (CodecMapped)curve2);
    }

    private static final void writeToBuffer$lambda$1(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, String v) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        $buffer.m_130070_(v);
    }

    private static final void writeToBuffer$lambda$2(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, ParticleEvent event) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        event.encode($buffer);
    }

    private static final MoLangCurve readFromBuffer$lambda$3(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        return (MoLangCurve)MoLangCurve.Companion.readFromBuffer($buffer);
    }

    private static final String readFromBuffer$lambda$4(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        return $buffer.m_130277_();
    }

    private static final ParticleEvent readFromBuffer$lambda$6(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
        ParticleEvent particleEvent;
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        ParticleEvent it2 = particleEvent = new ParticleEvent(null, null, null, 7, null);
        boolean bl = false;
        it2.decode($buffer);
        return particleEvent;
    }

    private static final ResourceLocation CODEC$lambda$14$lambda$7(BedrockParticleEffect it) {
        return it.id;
    }

    private static final BedrockParticleEmitter CODEC$lambda$14$lambda$8(BedrockParticleEffect it) {
        return it.emitter;
    }

    private static final BedrockParticle CODEC$lambda$14$lambda$9(BedrockParticleEffect it) {
        return it.particle;
    }

    private static final List CODEC$lambda$14$lambda$10(BedrockParticleEffect it) {
        return it.curves;
    }

    private static final ParticleSpace CODEC$lambda$14$lambda$11(BedrockParticleEffect it) {
        return it.space;
    }

    private static final Map CODEC$lambda$14$lambda$12(BedrockParticleEffect it) {
        return it.events;
    }

    private static final BedrockParticleEffect CODEC$lambda$14$lambda$13(ResourceLocation id, BedrockParticleEmitter emitter, BedrockParticle particle, List curves, ParticleSpace space, Map events) {
        Intrinsics.checkNotNullExpressionValue((Object)id, (String)"id");
        Intrinsics.checkNotNullExpressionValue((Object)emitter, (String)"emitter");
        Intrinsics.checkNotNullExpressionValue((Object)particle, (String)"particle");
        Intrinsics.checkNotNullExpressionValue((Object)curves, (String)"curves");
        List list = CollectionsKt.toMutableList((Collection)curves);
        Intrinsics.checkNotNullExpressionValue((Object)space, (String)"space");
        Intrinsics.checkNotNullExpressionValue((Object)events, (String)"events");
        return new BedrockParticleEffect(id, emitter, particle, list, space, events);
    }

    private static final App CODEC$lambda$14(RecordCodecBuilder.Instance instance) {
        return instance.group((App)ResourceLocation.f_135803_.fieldOf("id").forGetter(BedrockParticleEffect::CODEC$lambda$14$lambda$7), (App)BedrockParticleEmitter.Companion.getCODEC().fieldOf("emitter").forGetter(BedrockParticleEffect::CODEC$lambda$14$lambda$8), (App)BedrockParticle.Companion.getCODEC().fieldOf("particle").forGetter(BedrockParticleEffect::CODEC$lambda$14$lambda$9), (App)new ListCodec((Codec)MoLangCurve.Companion.getCodec()).fieldOf("curves").forGetter(BedrockParticleEffect::CODEC$lambda$14$lambda$10), (App)ParticleSpace.Companion.getCODEC().fieldOf("space").forGetter(BedrockParticleEffect::CODEC$lambda$14$lambda$11), (App)new UnboundedMapCodec((Codec)PrimitiveCodec.STRING, ParticleEvent.Companion.getCODEC()).fieldOf("events").forGetter(BedrockParticleEffect::CODEC$lambda$14$lambda$12)).apply((Applicative)instance, BedrockParticleEffect::CODEC$lambda$14$lambda$13);
    }

    public BedrockParticleEffect() {
        this(null, null, null, null, null, null, 63, null);
    }

    static {
        Codec codec2 = RecordCodecBuilder.create(BedrockParticleEffect::CODEC$lambda$14);
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"create { instance ->\n   \u2026)\n            }\n        }");
        CODEC = codec2;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/BedrockParticleEffect$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/api/snowstorm/BedrockParticleEffect;", "CODEC", "Lcom/mojang/serialization/Codec;", "getCODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Codec<BedrockParticleEffect> getCODEC() {
            return CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

