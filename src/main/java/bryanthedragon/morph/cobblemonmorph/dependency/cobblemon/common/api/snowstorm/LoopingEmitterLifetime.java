/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.codecs.PrimitiveCodec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Instance
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.FriendlyByteBuf
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.MoLang;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.MoValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleEmitterAction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleEmitterLifetime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleEmitterLifetimeType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.codec.ExpressionCodecKt;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 )2\u00020\u0001:\u0001)B\u001b\u0012\b\b\u0002\u0010\u0019\u001a\u00020\u0018\u0012\b\b\u0002\u0010\u001f\u001a\u00020\u0018\u00a2\u0006\u0004\b'\u0010(JG\u0010\u0007\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00018\u00008\u0000 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00018\u00008\u0000\u0018\u00010\u00050\u0005\"\u0004\b\u0000\u0010\u00022\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ'\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u0017\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0013\u001a\u00020\u0012H\u0016\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0017\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u0013\u001a\u00020\u0012H\u0016\u00a2\u0006\u0004\b\u0017\u0010\u0016R\"\u0010\u0019\u001a\u00020\u00188\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\"\u0010\u001f\u001a\u00020\u00188\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001f\u0010\u001a\u001a\u0004\b \u0010\u001c\"\u0004\b!\u0010\u001eR\u001a\u0010#\u001a\u00020\"8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b#\u0010$\u001a\u0004\b%\u0010&\u00a8\u0006*"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/LoopingEmitterLifetime;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterLifetime;", "T", "Lcom/mojang/serialization/DynamicOps;", "ops", "Lcom/mojang/serialization/DataResult;", "kotlin.jvm.PlatformType", "encode", "(Lcom/mojang/serialization/DynamicOps;)Lcom/mojang/serialization/DataResult;", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "", "started", "", "emitterAge", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterAction;", "getAction", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;ZD)Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterAction;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "readFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "writeToBuffer", "Lcom/bedrockk/molang/Expression;", "activeTime", "Lcom/bedrockk/molang/Expression;", "getActiveTime", "()Lcom/bedrockk/molang/Expression;", "setActiveTime", "(Lcom/bedrockk/molang/Expression;)V", "sleepTime", "getSleepTime", "setSleepTime", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterLifetimeType;", "type", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterLifetimeType;", "getType", "()Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterLifetimeType;", "<init>", "(Lcom/bedrockk/molang/Expression;Lcom/bedrockk/molang/Expression;)V", "Companion", "common"})
public final class LoopingEmitterLifetime
implements ParticleEmitterLifetime {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private Expression activeTime;
    @NotNull
    private Expression sleepTime;
    @NotNull
    private final ParticleEmitterLifetimeType type;
    @NotNull
    private static final Codec<LoopingEmitterLifetime> CODEC;

    public LoopingEmitterLifetime(@NotNull Expression activeTime, @NotNull Expression sleepTime) {
        Intrinsics.checkNotNullParameter((Object)activeTime, (String)"activeTime");
        Intrinsics.checkNotNullParameter((Object)sleepTime, (String)"sleepTime");
        this.activeTime = activeTime;
        this.sleepTime = sleepTime;
        this.type = ParticleEmitterLifetimeType.LOOPING;
    }

    public /* synthetic */ LoopingEmitterLifetime(Expression expression, Expression expression2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            expression = MoLangExtensionsKt.asExpression(1.0);
        }
        if ((n & 2) != 0) {
            expression2 = MoLangExtensionsKt.asExpression(1.0);
        }
        this(expression, expression2);
    }

    @NotNull
    public final Expression getActiveTime() {
        return this.activeTime;
    }

    public final void setActiveTime(@NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)expression, (String)"<set-?>");
        this.activeTime = expression;
    }

    @NotNull
    public final Expression getSleepTime() {
        return this.sleepTime;
    }

    public final void setSleepTime(@NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)expression, (String)"<set-?>");
        this.sleepTime = expression;
    }

    @Override
    @NotNull
    public ParticleEmitterLifetimeType getType() {
        return this.type;
    }

    @Override
    @NotNull
    public ParticleEmitterAction getAction(@NotNull MoLangRuntime runtime2, boolean started, double emitterAge) {
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        MoValue activeTime = MoLangExtensionsKt.resolve(runtime2, this.activeTime);
        double activeTimeValue = activeTime.asDouble();
        double sleepTime = MoLangExtensionsKt.resolveDouble(runtime2, this.sleepTime);
        double interval = activeTimeValue + sleepTime;
        double displacement = emitterAge % interval;
        runtime2.getEnvironment().setSimpleVariable("emitter_lifetime", activeTime);
        if (emitterAge > activeTimeValue && sleepTime == 0.0) {
            return ParticleEmitterAction.STOP;
        }
        return displacement < activeTimeValue ? ParticleEmitterAction.GO : ParticleEmitterAction.RESET;
    }

    @Override
    public <T> DataResult<T> encode(@NotNull DynamicOps<T> ops) {
        Intrinsics.checkNotNullParameter(ops, (String)"ops");
        return CODEC.encodeStart(ops, (Object)this);
    }

    @Override
    public void readFromBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        Expression expression = MoLang.createParser(buffer.m_130277_()).parseExpression();
        Intrinsics.checkNotNullExpressionValue((Object)expression, (String)"createParser(buffer.read\u2026ring()).parseExpression()");
        this.activeTime = expression;
        Expression expression2 = MoLang.createParser(buffer.m_130277_()).parseExpression();
        Intrinsics.checkNotNullExpressionValue((Object)expression2, (String)"createParser(buffer.read\u2026ring()).parseExpression()");
        this.sleepTime = expression2;
    }

    @Override
    public void writeToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130070_(MoLangExtensionsKt.getString(this.activeTime));
        buffer.m_130070_(MoLangExtensionsKt.getString(this.sleepTime));
    }

    private static final String CODEC$lambda$4$lambda$0(LoopingEmitterLifetime it) {
        return it.getType().name();
    }

    private static final Expression CODEC$lambda$4$lambda$1(LoopingEmitterLifetime it) {
        return it.activeTime;
    }

    private static final Expression CODEC$lambda$4$lambda$2(LoopingEmitterLifetime it) {
        return it.sleepTime;
    }

    private static final LoopingEmitterLifetime CODEC$lambda$4$lambda$3(String string, Expression activeTime, Expression sleepTime) {
        Intrinsics.checkNotNullExpressionValue((Object)activeTime, (String)"activeTime");
        Intrinsics.checkNotNullExpressionValue((Object)sleepTime, (String)"sleepTime");
        return new LoopingEmitterLifetime(activeTime, sleepTime);
    }

    private static final App CODEC$lambda$4(RecordCodecBuilder.Instance instance) {
        return instance.group((App)PrimitiveCodec.STRING.fieldOf("type").forGetter(LoopingEmitterLifetime::CODEC$lambda$4$lambda$0), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("activeTime").forGetter(LoopingEmitterLifetime::CODEC$lambda$4$lambda$1), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("sleepTime").forGetter(LoopingEmitterLifetime::CODEC$lambda$4$lambda$2)).apply((Applicative)instance, LoopingEmitterLifetime::CODEC$lambda$4$lambda$3);
    }

    public LoopingEmitterLifetime() {
        this(null, null, 3, null);
    }

    static {
        Codec codec2 = RecordCodecBuilder.create(LoopingEmitterLifetime::CODEC$lambda$4);
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"create { instance ->\n   \u2026e, sleepTime) }\n        }");
        CODEC = codec2;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/LoopingEmitterLifetime$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/api/snowstorm/LoopingEmitterLifetime;", "CODEC", "Lcom/mojang/serialization/Codec;", "getCODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Codec<LoopingEmitterLifetime> getCODEC() {
            return CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

