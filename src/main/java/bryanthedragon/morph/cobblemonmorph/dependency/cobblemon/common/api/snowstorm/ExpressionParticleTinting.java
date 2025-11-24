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
 *  org.joml.Vector4f
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.MoLang;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.NumberExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleTinting;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleTintingType;
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
import org.joml.Vector4f;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 +2\u00020\u0001:\u0001+B/\u0012\b\b\u0002\u0010!\u001a\u00020\u0014\u0012\b\b\u0002\u0010\u001e\u001a\u00020\u0014\u0012\b\b\u0002\u0010\u001b\u001a\u00020\u0014\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0014\u00a2\u0006\u0004\b)\u0010*JG\u0010\u0007\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00018\u00008\u0000 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00018\u00008\u0000\u0018\u00010\u00050\u0005\"\u0004\b\u0000\u0010\u00022\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0017\u0010\f\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\f\u0010\rJ\u0017\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u000eH\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0017\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u000eH\u0016\u00a2\u0006\u0004\b\u0013\u0010\u0012R\"\u0010\u0015\u001a\u00020\u00148\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\"\u0010\u001b\u001a\u00020\u00148\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001b\u0010\u0016\u001a\u0004\b\u001c\u0010\u0018\"\u0004\b\u001d\u0010\u001aR\"\u0010\u001e\u001a\u00020\u00148\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001e\u0010\u0016\u001a\u0004\b\u001f\u0010\u0018\"\u0004\b \u0010\u001aR\"\u0010!\u001a\u00020\u00148\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b!\u0010\u0016\u001a\u0004\b\"\u0010\u0018\"\u0004\b#\u0010\u001aR\u001a\u0010%\u001a\u00020$8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b%\u0010&\u001a\u0004\b'\u0010(\u00a8\u0006,"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/ExpressionParticleTinting;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleTinting;", "T", "Lcom/mojang/serialization/DynamicOps;", "ops", "Lcom/mojang/serialization/DataResult;", "kotlin.jvm.PlatformType", "encode", "(Lcom/mojang/serialization/DynamicOps;)Lcom/mojang/serialization/DataResult;", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "Lorg/joml/Vector4f;", "getTint", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;)Lorg/joml/Vector4f;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "readFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "writeToBuffer", "Lcom/bedrockk/molang/Expression;", "alpha", "Lcom/bedrockk/molang/Expression;", "getAlpha", "()Lcom/bedrockk/molang/Expression;", "setAlpha", "(Lcom/bedrockk/molang/Expression;)V", "blue", "getBlue", "setBlue", "green", "getGreen", "setGreen", "red", "getRed", "setRed", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleTintingType;", "type", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleTintingType;", "getType", "()Lcom/cobblemon/mod/common/api/snowstorm/ParticleTintingType;", "<init>", "(Lcom/bedrockk/molang/Expression;Lcom/bedrockk/molang/Expression;Lcom/bedrockk/molang/Expression;Lcom/bedrockk/molang/Expression;)V", "Companion", "common"})
public final class ExpressionParticleTinting
implements ParticleTinting {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private Expression red;
    @NotNull
    private Expression green;
    @NotNull
    private Expression blue;
    @NotNull
    private Expression alpha;
    @NotNull
    private final ParticleTintingType type;
    @NotNull
    private static final Codec<ExpressionParticleTinting> CODEC;

    public ExpressionParticleTinting(@NotNull Expression red, @NotNull Expression green, @NotNull Expression blue, @NotNull Expression alpha) {
        Intrinsics.checkNotNullParameter((Object)red, (String)"red");
        Intrinsics.checkNotNullParameter((Object)green, (String)"green");
        Intrinsics.checkNotNullParameter((Object)blue, (String)"blue");
        Intrinsics.checkNotNullParameter((Object)alpha, (String)"alpha");
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
        this.type = ParticleTintingType.EXPRESSION;
    }

    public /* synthetic */ ExpressionParticleTinting(Expression expression, Expression expression2, Expression expression3, Expression expression4, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            expression = new NumberExpression(1.0);
        }
        if ((n & 2) != 0) {
            expression2 = new NumberExpression(1.0);
        }
        if ((n & 4) != 0) {
            expression3 = new NumberExpression(1.0);
        }
        if ((n & 8) != 0) {
            expression4 = new NumberExpression(1.0);
        }
        this(expression, expression2, expression3, expression4);
    }

    @NotNull
    public final Expression getRed() {
        return this.red;
    }

    public final void setRed(@NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)expression, (String)"<set-?>");
        this.red = expression;
    }

    @NotNull
    public final Expression getGreen() {
        return this.green;
    }

    public final void setGreen(@NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)expression, (String)"<set-?>");
        this.green = expression;
    }

    @NotNull
    public final Expression getBlue() {
        return this.blue;
    }

    public final void setBlue(@NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)expression, (String)"<set-?>");
        this.blue = expression;
    }

    @NotNull
    public final Expression getAlpha() {
        return this.alpha;
    }

    public final void setAlpha(@NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)expression, (String)"<set-?>");
        this.alpha = expression;
    }

    @Override
    @NotNull
    public ParticleTintingType getType() {
        return this.type;
    }

    @Override
    @NotNull
    public Vector4f getTint(@NotNull MoLangRuntime runtime2) {
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        return new Vector4f((float)MoLangExtensionsKt.resolveDouble(runtime2, this.red), (float)MoLangExtensionsKt.resolveDouble(runtime2, this.green), (float)MoLangExtensionsKt.resolveDouble(runtime2, this.blue), (float)MoLangExtensionsKt.resolveDouble(runtime2, this.alpha));
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
        this.red = expression;
        Expression expression2 = MoLang.createParser(buffer.m_130277_()).parseExpression();
        Intrinsics.checkNotNullExpressionValue((Object)expression2, (String)"createParser(buffer.read\u2026ring()).parseExpression()");
        this.green = expression2;
        Expression expression3 = MoLang.createParser(buffer.m_130277_()).parseExpression();
        Intrinsics.checkNotNullExpressionValue((Object)expression3, (String)"createParser(buffer.read\u2026ring()).parseExpression()");
        this.blue = expression3;
        Expression expression4 = MoLang.createParser(buffer.m_130277_()).parseExpression();
        Intrinsics.checkNotNullExpressionValue((Object)expression4, (String)"createParser(buffer.read\u2026ring()).parseExpression()");
        this.alpha = expression4;
    }

    @Override
    public void writeToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130070_(MoLangExtensionsKt.getString(this.red));
        buffer.m_130070_(MoLangExtensionsKt.getString(this.green));
        buffer.m_130070_(MoLangExtensionsKt.getString(this.blue));
        buffer.m_130070_(MoLangExtensionsKt.getString(this.alpha));
    }

    private static final String CODEC$lambda$6$lambda$0(ExpressionParticleTinting it) {
        return it.getType().name();
    }

    private static final Expression CODEC$lambda$6$lambda$1(ExpressionParticleTinting it) {
        return it.red;
    }

    private static final Expression CODEC$lambda$6$lambda$2(ExpressionParticleTinting it) {
        return it.green;
    }

    private static final Expression CODEC$lambda$6$lambda$3(ExpressionParticleTinting it) {
        return it.blue;
    }

    private static final Expression CODEC$lambda$6$lambda$4(ExpressionParticleTinting it) {
        return it.alpha;
    }

    private static final ExpressionParticleTinting CODEC$lambda$6$lambda$5(String string, Expression red, Expression green, Expression blue, Expression alpha) {
        Intrinsics.checkNotNullExpressionValue((Object)red, (String)"red");
        Intrinsics.checkNotNullExpressionValue((Object)green, (String)"green");
        Intrinsics.checkNotNullExpressionValue((Object)blue, (String)"blue");
        Intrinsics.checkNotNullExpressionValue((Object)alpha, (String)"alpha");
        return new ExpressionParticleTinting(red, green, blue, alpha);
    }

    private static final App CODEC$lambda$6(RecordCodecBuilder.Instance instance) {
        return instance.group((App)PrimitiveCodec.STRING.fieldOf("type").forGetter(ExpressionParticleTinting::CODEC$lambda$6$lambda$0), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("red").forGetter(ExpressionParticleTinting::CODEC$lambda$6$lambda$1), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("green").forGetter(ExpressionParticleTinting::CODEC$lambda$6$lambda$2), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("blue").forGetter(ExpressionParticleTinting::CODEC$lambda$6$lambda$3), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("alpha").forGetter(ExpressionParticleTinting::CODEC$lambda$6$lambda$4)).apply((Applicative)instance, ExpressionParticleTinting::CODEC$lambda$6$lambda$5);
    }

    public ExpressionParticleTinting() {
        this(null, null, null, null, 15, null);
    }

    static {
        Codec codec2 = RecordCodecBuilder.create(ExpressionParticleTinting::CODEC$lambda$6);
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"create { instance ->\n   \u2026 blue, alpha) }\n        }");
        CODEC = codec2;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/ExpressionParticleTinting$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/api/snowstorm/ExpressionParticleTinting;", "CODEC", "Lcom/mojang/serialization/Codec;", "getCODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Codec<ExpressionParticleTinting> getCODEC() {
            return CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

