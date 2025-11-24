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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.NumberExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleUVMode;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleUVModeType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.UVDetails;
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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u000e\u0018\u0000 82\u00020\u0001:\u00018BC\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u0017\u0012\b\b\u0002\u0010\u001e\u001a\u00020\u0017\u0012\b\b\u0002\u0010\"\u001a\u00020!\u0012\b\b\u0002\u0010(\u001a\u00020!\u0012\b\b\u0002\u00100\u001a\u00020\u0017\u0012\b\b\u0002\u00103\u001a\u00020\u0017\u00a2\u0006\u0004\b6\u00107J)\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\"\u0004\b\u0000\u0010\u00022\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J/\u0010\u000f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0017\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0012\u001a\u00020\u0011H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0017\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0012\u001a\u00020\u0011H\u0016\u00a2\u0006\u0004\b\u0016\u0010\u0015R\"\u0010\u0018\u001a\u00020\u00178\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\"\u0010\u001e\u001a\u00020\u00178\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u001e\u0010\u0019\u001a\u0004\b\u001f\u0010\u001b\"\u0004\b \u0010\u001dR\"\u0010\"\u001a\u00020!8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R\"\u0010(\u001a\u00020!8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b(\u0010#\u001a\u0004\b)\u0010%\"\u0004\b*\u0010'R\u001a\u0010,\u001a\u00020+8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b,\u0010-\u001a\u0004\b.\u0010/R\"\u00100\u001a\u00020\u00178\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b0\u0010\u0019\u001a\u0004\b1\u0010\u001b\"\u0004\b2\u0010\u001dR\"\u00103\u001a\u00020\u00178\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b3\u0010\u0019\u001a\u0004\b4\u0010\u001b\"\u0004\b5\u0010\u001d\u00a8\u00069"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/StaticParticleUVMode;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleUVMode;", "T", "Lcom/mojang/serialization/DynamicOps;", "ops", "Lcom/mojang/serialization/DataResult;", "encode", "(Lcom/mojang/serialization/DynamicOps;)Lcom/mojang/serialization/DataResult;", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "moLangRuntime", "", "age", "maxAge", "Lcom/cobblemon/mod/common/api/snowstorm/UVDetails;", "uvDetails", "get", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;DDLcom/cobblemon/mod/common/api/snowstorm/UVDetails;)Lcom/cobblemon/mod/common/api/snowstorm/UVDetails;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "readFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "writeToBuffer", "Lcom/bedrockk/molang/Expression;", "startU", "Lcom/bedrockk/molang/Expression;", "getStartU", "()Lcom/bedrockk/molang/Expression;", "setStartU", "(Lcom/bedrockk/molang/Expression;)V", "startV", "getStartV", "setStartV", "", "textureSizeX", "I", "getTextureSizeX", "()I", "setTextureSizeX", "(I)V", "textureSizeY", "getTextureSizeY", "setTextureSizeY", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleUVModeType;", "type", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleUVModeType;", "getType", "()Lcom/cobblemon/mod/common/api/snowstorm/ParticleUVModeType;", "uSize", "getUSize", "setUSize", "vSize", "getVSize", "setVSize", "<init>", "(Lcom/bedrockk/molang/Expression;Lcom/bedrockk/molang/Expression;IILcom/bedrockk/molang/Expression;Lcom/bedrockk/molang/Expression;)V", "Companion", "common"})
public final class StaticParticleUVMode
extends ParticleUVMode {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private Expression startU;
    @NotNull
    private Expression startV;
    private int textureSizeX;
    private int textureSizeY;
    @NotNull
    private Expression uSize;
    @NotNull
    private Expression vSize;
    @NotNull
    private final ParticleUVModeType type;
    @NotNull
    private static final Codec<StaticParticleUVMode> CODEC;

    public StaticParticleUVMode(@NotNull Expression startU, @NotNull Expression startV, int textureSizeX, int textureSizeY, @NotNull Expression uSize, @NotNull Expression vSize) {
        Intrinsics.checkNotNullParameter((Object)startU, (String)"startU");
        Intrinsics.checkNotNullParameter((Object)startV, (String)"startV");
        Intrinsics.checkNotNullParameter((Object)uSize, (String)"uSize");
        Intrinsics.checkNotNullParameter((Object)vSize, (String)"vSize");
        this.startU = startU;
        this.startV = startV;
        this.textureSizeX = textureSizeX;
        this.textureSizeY = textureSizeY;
        this.uSize = uSize;
        this.vSize = vSize;
        this.type = ParticleUVModeType.STATIC;
    }

    public /* synthetic */ StaticParticleUVMode(Expression expression, Expression expression2, int n, int n2, Expression expression3, Expression expression4, int n3, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n3 & 1) != 0) {
            expression = new NumberExpression(0.0);
        }
        if ((n3 & 2) != 0) {
            expression2 = new NumberExpression(0.0);
        }
        if ((n3 & 4) != 0) {
            n = 8;
        }
        if ((n3 & 8) != 0) {
            n2 = 8;
        }
        if ((n3 & 0x10) != 0) {
            expression3 = new NumberExpression(8.0);
        }
        if ((n3 & 0x20) != 0) {
            expression4 = new NumberExpression(8.0);
        }
        this(expression, expression2, n, n2, expression3, expression4);
    }

    @Override
    @NotNull
    public Expression getStartU() {
        return this.startU;
    }

    @Override
    public void setStartU(@NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)expression, (String)"<set-?>");
        this.startU = expression;
    }

    @Override
    @NotNull
    public Expression getStartV() {
        return this.startV;
    }

    @Override
    public void setStartV(@NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)expression, (String)"<set-?>");
        this.startV = expression;
    }

    @Override
    public int getTextureSizeX() {
        return this.textureSizeX;
    }

    @Override
    public void setTextureSizeX(int n) {
        this.textureSizeX = n;
    }

    @Override
    public int getTextureSizeY() {
        return this.textureSizeY;
    }

    @Override
    public void setTextureSizeY(int n) {
        this.textureSizeY = n;
    }

    @Override
    @NotNull
    public Expression getUSize() {
        return this.uSize;
    }

    @Override
    public void setUSize(@NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)expression, (String)"<set-?>");
        this.uSize = expression;
    }

    @Override
    @NotNull
    public Expression getVSize() {
        return this.vSize;
    }

    @Override
    public void setVSize(@NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)expression, (String)"<set-?>");
        this.vSize = expression;
    }

    @Override
    @NotNull
    public ParticleUVModeType getType() {
        return this.type;
    }

    @Override
    @NotNull
    public UVDetails get(@NotNull MoLangRuntime moLangRuntime, double age, double maxAge, @NotNull UVDetails uvDetails) {
        Intrinsics.checkNotNullParameter((Object)moLangRuntime, (String)"moLangRuntime");
        Intrinsics.checkNotNullParameter((Object)uvDetails, (String)"uvDetails");
        return uvDetails.set(MoLangExtensionsKt.resolveDouble(moLangRuntime, this.getStartU()) / (double)this.getTextureSizeX(), MoLangExtensionsKt.resolveDouble(moLangRuntime, this.getStartV()) / (double)this.getTextureSizeY(), (MoLangExtensionsKt.resolveDouble(moLangRuntime, this.getStartU()) + MoLangExtensionsKt.resolveDouble(moLangRuntime, this.getUSize())) / (double)this.getTextureSizeX(), (MoLangExtensionsKt.resolveDouble(moLangRuntime, this.getStartV()) + MoLangExtensionsKt.resolveDouble(moLangRuntime, this.getVSize())) / (double)this.getTextureSizeY());
    }

    @Override
    @NotNull
    public <T> DataResult<T> encode(@NotNull DynamicOps<T> ops) {
        Intrinsics.checkNotNullParameter(ops, (String)"ops");
        DataResult dataResult = CODEC.encodeStart(ops, (Object)this);
        Intrinsics.checkNotNullExpressionValue((Object)dataResult, (String)"CODEC.encodeStart(ops, this)");
        return dataResult;
    }

    @Override
    public void readFromBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        Expression expression = MoLang.createParser(buffer.m_130277_()).parseExpression();
        Intrinsics.checkNotNullExpressionValue((Object)expression, (String)"createParser(buffer.read\u2026ring()).parseExpression()");
        this.setStartU(expression);
        Expression expression2 = MoLang.createParser(buffer.m_130277_()).parseExpression();
        Intrinsics.checkNotNullExpressionValue((Object)expression2, (String)"createParser(buffer.read\u2026ring()).parseExpression()");
        this.setStartV(expression2);
        this.setTextureSizeX(buffer.readInt());
        this.setTextureSizeY(buffer.readInt());
        Expression expression3 = MoLang.createParser(buffer.m_130277_()).parseExpression();
        Intrinsics.checkNotNullExpressionValue((Object)expression3, (String)"createParser(buffer.read\u2026ring()).parseExpression()");
        this.setUSize(expression3);
        Expression expression4 = MoLang.createParser(buffer.m_130277_()).parseExpression();
        Intrinsics.checkNotNullExpressionValue((Object)expression4, (String)"createParser(buffer.read\u2026ring()).parseExpression()");
        this.setVSize(expression4);
    }

    @Override
    public void writeToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130070_(MoLangExtensionsKt.getString(this.getStartU()));
        buffer.m_130070_(MoLangExtensionsKt.getString(this.getStartV()));
        buffer.writeInt(this.getTextureSizeX());
        buffer.writeInt(this.getTextureSizeY());
        buffer.m_130070_(MoLangExtensionsKt.getString(this.getUSize()));
        buffer.m_130070_(MoLangExtensionsKt.getString(this.getVSize()));
    }

    private static final String CODEC$lambda$8$lambda$0(StaticParticleUVMode it) {
        return it.getType().name();
    }

    private static final Expression CODEC$lambda$8$lambda$1(StaticParticleUVMode it) {
        return it.getStartU();
    }

    private static final Expression CODEC$lambda$8$lambda$2(StaticParticleUVMode it) {
        return it.getStartV();
    }

    private static final Integer CODEC$lambda$8$lambda$3(StaticParticleUVMode it) {
        return it.getTextureSizeX();
    }

    private static final Integer CODEC$lambda$8$lambda$4(StaticParticleUVMode it) {
        return it.getTextureSizeY();
    }

    private static final Expression CODEC$lambda$8$lambda$5(StaticParticleUVMode it) {
        return it.getUSize();
    }

    private static final Expression CODEC$lambda$8$lambda$6(StaticParticleUVMode it) {
        return it.getVSize();
    }

    private static final StaticParticleUVMode CODEC$lambda$8$lambda$7(String string, Expression startU, Expression startV, Integer textureSizeX, Integer textureSizeY, Expression uSize, Expression vSize) {
        Intrinsics.checkNotNullExpressionValue((Object)startU, (String)"startU");
        Intrinsics.checkNotNullExpressionValue((Object)startV, (String)"startV");
        Intrinsics.checkNotNullExpressionValue((Object)textureSizeX, (String)"textureSizeX");
        int n = textureSizeX;
        Intrinsics.checkNotNullExpressionValue((Object)textureSizeY, (String)"textureSizeY");
        int n2 = textureSizeY;
        Intrinsics.checkNotNullExpressionValue((Object)uSize, (String)"uSize");
        Intrinsics.checkNotNullExpressionValue((Object)vSize, (String)"vSize");
        return new StaticParticleUVMode(startU, startV, n, n2, uSize, vSize);
    }

    private static final App CODEC$lambda$8(RecordCodecBuilder.Instance instance) {
        return instance.group((App)PrimitiveCodec.STRING.fieldOf("type").forGetter(StaticParticleUVMode::CODEC$lambda$8$lambda$0), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("startU").forGetter(StaticParticleUVMode::CODEC$lambda$8$lambda$1), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("startV").forGetter(StaticParticleUVMode::CODEC$lambda$8$lambda$2), (App)PrimitiveCodec.INT.fieldOf("textureSizeX").forGetter(StaticParticleUVMode::CODEC$lambda$8$lambda$3), (App)PrimitiveCodec.INT.fieldOf("textureSizeY").forGetter(StaticParticleUVMode::CODEC$lambda$8$lambda$4), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("uSize").forGetter(StaticParticleUVMode::CODEC$lambda$8$lambda$5), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("vSize").forGetter(StaticParticleUVMode::CODEC$lambda$8$lambda$6)).apply((Applicative)instance, StaticParticleUVMode::CODEC$lambda$8$lambda$7);
    }

    public StaticParticleUVMode() {
        this(null, null, 0, 0, null, null, 63, null);
    }

    static {
        Codec codec2 = RecordCodecBuilder.create(StaticParticleUVMode::CODEC$lambda$8);
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"create { instance ->\n   \u2026)\n            }\n        }");
        CODEC = codec2;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/StaticParticleUVMode$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/api/snowstorm/StaticParticleUVMode;", "CODEC", "Lcom/mojang/serialization/Codec;", "getCODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Codec<StaticParticleUVMode> getCODEC() {
            return CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

