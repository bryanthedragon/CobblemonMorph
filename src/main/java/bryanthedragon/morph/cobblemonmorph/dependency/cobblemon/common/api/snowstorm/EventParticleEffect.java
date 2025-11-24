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
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 \u00132\u00020\u0001:\u0002\u0013\u0014B#\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\r\u001a\u00020\f\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0019\u0010\b\u001a\u0004\u0018\u00010\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\r\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0015"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/EventParticleEffect;", "", "Lnet/minecraft/resources/ResourceLocation;", "effect", "Lnet/minecraft/resources/ResourceLocation;", "getEffect", "()Lnet/minecraft/resources/ResourceLocation;", "Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "expression", "Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "getExpression", "()Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "Lcom/cobblemon/mod/common/api/snowstorm/EventParticleEffect$EventParticleType;", "type", "Lcom/cobblemon/mod/common/api/snowstorm/EventParticleEffect$EventParticleType;", "getType", "()Lcom/cobblemon/mod/common/api/snowstorm/EventParticleEffect$EventParticleType;", "<init>", "(Lnet/minecraft/resources/ResourceLocation;Lcom/cobblemon/mod/common/api/snowstorm/EventParticleEffect$EventParticleType;Lcom/cobblemon/mod/common/api/molang/ExpressionLike;)V", "Companion", "EventParticleType", "common"})
public final class EventParticleEffect {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final ResourceLocation effect;
    @NotNull
    private final EventParticleType type;
    @Nullable
    private final ExpressionLike expression;
    private static final Codec<EventParticleEffect> CODEC = RecordCodecBuilder.create(EventParticleEffect::CODEC$lambda$4);

    public EventParticleEffect(@NotNull ResourceLocation effect, @NotNull EventParticleType type, @Nullable ExpressionLike expression) {
        Intrinsics.checkNotNullParameter((Object)effect, (String)"effect");
        Intrinsics.checkNotNullParameter((Object)((Object)type), (String)"type");
        this.effect = effect;
        this.type = type;
        this.expression = expression;
    }

    public /* synthetic */ EventParticleEffect(ResourceLocation resourceLocation, EventParticleType eventParticleType, ExpressionLike expressionLike, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 4) != 0) {
            expressionLike = null;
        }
        this(resourceLocation, eventParticleType, expressionLike);
    }

    @NotNull
    public final ResourceLocation getEffect() {
        return this.effect;
    }

    @NotNull
    public final EventParticleType getType() {
        return this.type;
    }

    @Nullable
    public final ExpressionLike getExpression() {
        return this.expression;
    }

    private static final ResourceLocation CODEC$lambda$4$lambda$0(EventParticleEffect it) {
        return it.effect;
    }

    private static final String CODEC$lambda$4$lambda$1(EventParticleEffect it) {
        return it.type.name();
    }

    private static final String CODEC$lambda$4$lambda$2(EventParticleEffect it) {
        ExpressionLike expressionLike = it.expression;
        return expressionLike != null ? expressionLike.toString() : null;
    }

    private static final EventParticleEffect CODEC$lambda$4$lambda$3(ResourceLocation effect, String type, String expression) {
        Intrinsics.checkNotNullExpressionValue((Object)effect, (String)"effect");
        Intrinsics.checkNotNullExpressionValue((Object)type, (String)"type");
        String string = expression;
        return new EventParticleEffect(effect, EventParticleType.valueOf(type), string != null ? MoLangExtensionsKt.asExpressionLike(string) : null);
    }

    private static final App CODEC$lambda$4(RecordCodecBuilder.Instance instance) {
        return instance.group((App)ResourceLocation.f_135803_.fieldOf("effect").forGetter(EventParticleEffect::CODEC$lambda$4$lambda$0), (App)PrimitiveCodec.STRING.fieldOf("type").forGetter(EventParticleEffect::CODEC$lambda$4$lambda$1), (App)PrimitiveCodec.STRING.optionalFieldOf("expression", null).forGetter(EventParticleEffect::CODEC$lambda$4$lambda$2)).apply((Applicative)instance, EventParticleEffect::CODEC$lambda$4$lambda$3);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nR;\u0010\u0005\u001a&\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u0003 \u0004*\u0012\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u0003\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/EventParticleEffect$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/api/snowstorm/EventParticleEffect;", "kotlin.jvm.PlatformType", "CODEC", "Lcom/mojang/serialization/Codec;", "getCODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public final Codec<EventParticleEffect> getCODEC() {
            return CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007\u00a8\u0006\b"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/EventParticleEffect$EventParticleType;", "", "<init>", "(Ljava/lang/String;I)V", "EMITTER", "EMITTER_BOUND", "PARTICLE", "PARTICLE_WITH_VELOCITY", "common"})
    public static final class EventParticleType
    extends Enum<EventParticleType> {
        public static final /* enum */ EventParticleType EMITTER = new EventParticleType();
        public static final /* enum */ EventParticleType EMITTER_BOUND = new EventParticleType();
        public static final /* enum */ EventParticleType PARTICLE = new EventParticleType();
        public static final /* enum */ EventParticleType PARTICLE_WITH_VELOCITY = new EventParticleType();
        private static final /* synthetic */ EventParticleType[] $VALUES;

        public static EventParticleType[] values() {
            return (EventParticleType[])$VALUES.clone();
        }

        public static EventParticleType valueOf(String value2) {
            return Enum.valueOf(EventParticleType.class, value2);
        }

        static {
            $VALUES = eventParticleTypeArray = new EventParticleType[]{EventParticleType.EMITTER, EventParticleType.EMITTER_BOUND, EventParticleType.PARTICLE, EventParticleType.PARTICLE_WITH_VELOCITY};
        }
    }
}

