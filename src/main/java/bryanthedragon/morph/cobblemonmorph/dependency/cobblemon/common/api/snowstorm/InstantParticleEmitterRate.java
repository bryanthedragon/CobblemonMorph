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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleEmitterRate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleEmitterRateType;
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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 $2\u00020\u0001:\u0001$B\u0011\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u0017\u00a2\u0006\u0004\b#\u0010\u001dJG\u0010\u0007\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00018\u00008\u0000 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00018\u00008\u0000\u0018\u00010\u00050\u0005\"\u0004\b\u0000\u0010\u00022\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ'\u0010\u000f\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0017\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0012\u001a\u00020\u0011H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0017\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0012\u001a\u00020\u0011H\u0016\u00a2\u0006\u0004\b\u0016\u0010\u0015R\"\u0010\u0018\u001a\u00020\u00178\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001a\u0010\u001f\u001a\u00020\u001e8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001f\u0010 \u001a\u0004\b!\u0010\"\u00a8\u0006%"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/InstantParticleEmitterRate;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterRate;", "T", "Lcom/mojang/serialization/DynamicOps;", "ops", "Lcom/mojang/serialization/DataResult;", "kotlin.jvm.PlatformType", "encode", "(Lcom/mojang/serialization/DynamicOps;)Lcom/mojang/serialization/DataResult;", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "", "started", "", "currentlyActive", "getEmitCount", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;ZI)I", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "readFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "writeToBuffer", "Lcom/bedrockk/molang/Expression;", "amount", "Lcom/bedrockk/molang/Expression;", "getAmount", "()Lcom/bedrockk/molang/Expression;", "setAmount", "(Lcom/bedrockk/molang/Expression;)V", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterRateType;", "type", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterRateType;", "getType", "()Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterRateType;", "<init>", "Companion", "common"})
public final class InstantParticleEmitterRate
implements ParticleEmitterRate {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private Expression amount;
    @NotNull
    private final ParticleEmitterRateType type;
    @NotNull
    private static final Codec<InstantParticleEmitterRate> CODEC;

    public InstantParticleEmitterRate(@NotNull Expression amount) {
        Intrinsics.checkNotNullParameter((Object)amount, (String)"amount");
        this.amount = amount;
        this.type = ParticleEmitterRateType.INSTANT;
    }

    public /* synthetic */ InstantParticleEmitterRate(Expression expression, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            Expression expression2 = MoLangExtensionsKt.asExpression("1");
            Intrinsics.checkNotNullExpressionValue((Object)expression2, (String)"1\".asExpression()");
            expression = expression2;
        }
        this(expression);
    }

    @NotNull
    public final Expression getAmount() {
        return this.amount;
    }

    public final void setAmount(@NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)expression, (String)"<set-?>");
        this.amount = expression;
    }

    @Override
    @NotNull
    public ParticleEmitterRateType getType() {
        return this.type;
    }

    @Override
    public int getEmitCount(@NotNull MoLangRuntime runtime2, boolean started, int currentlyActive) {
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        if (started) {
            return 0;
        }
        return MoLangExtensionsKt.resolveInt(runtime2, this.amount);
    }

    @Override
    public <T> DataResult<T> encode(@NotNull DynamicOps<T> ops) {
        Intrinsics.checkNotNullParameter(ops, (String)"ops");
        return CODEC.encodeStart(ops, (Object)this);
    }

    @Override
    public void readFromBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        String string = buffer.m_130277_();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"buffer.readString()");
        Expression expression = MoLangExtensionsKt.asExpression(string);
        Intrinsics.checkNotNullExpressionValue((Object)expression, (String)"buffer.readString().asExpression()");
        this.amount = expression;
    }

    @Override
    public void writeToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130070_(MoLangExtensionsKt.getString(this.amount));
    }

    private static final String CODEC$lambda$3$lambda$0(InstantParticleEmitterRate it) {
        return it.getType().name();
    }

    private static final Expression CODEC$lambda$3$lambda$1(InstantParticleEmitterRate it) {
        return it.amount;
    }

    private static final InstantParticleEmitterRate CODEC$lambda$3$lambda$2(String string, Expression amount) {
        Intrinsics.checkNotNullExpressionValue((Object)amount, (String)"amount");
        return new InstantParticleEmitterRate(amount);
    }

    private static final App CODEC$lambda$3(RecordCodecBuilder.Instance instance) {
        return instance.group((App)PrimitiveCodec.STRING.fieldOf("type").forGetter(InstantParticleEmitterRate::CODEC$lambda$3$lambda$0), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("amount").forGetter(InstantParticleEmitterRate::CODEC$lambda$3$lambda$1)).apply((Applicative)instance, InstantParticleEmitterRate::CODEC$lambda$3$lambda$2);
    }

    public InstantParticleEmitterRate() {
        this(null, 1, null);
    }

    static {
        Codec codec2 = RecordCodecBuilder.create(InstantParticleEmitterRate::CODEC$lambda$3);
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"create { instance ->\n   \u2026rRate(amount) }\n        }");
        CODEC = codec2;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/InstantParticleEmitterRate$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/api/snowstorm/InstantParticleEmitterRate;", "CODEC", "Lcom/mojang/serialization/Codec;", "getCODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Codec<InstantParticleEmitterRate> getCODEC() {
            return CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

