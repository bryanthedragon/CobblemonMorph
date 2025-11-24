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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0018\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u000e\u0018\u0000 N2\u00020\u0001:\u0001NB\u007f\u0012\b\b\u0002\u0010(\u001a\u00020\u0017\u0012\b\b\u0002\u0010+\u001a\u00020\u0017\u0012\b\b\u0002\u00108\u001a\u000207\u0012\b\b\u0002\u0010>\u001a\u000207\u0012\b\b\u0002\u0010F\u001a\u00020\u0017\u0012\b\b\u0002\u0010I\u001a\u00020\u0017\u0012\b\b\u0002\u0010.\u001a\u00020\u0017\u0012\b\b\u0002\u00101\u001a\u00020\u0017\u0012\b\b\u0002\u0010%\u001a\u00020\u0017\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u0017\u0012\b\b\u0002\u00104\u001a\u00020\u001e\u0012\b\b\u0002\u0010\u001f\u001a\u00020\u001e\u00a2\u0006\u0004\bL\u0010MJ)\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\"\u0004\b\u0000\u0010\u00022\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J/\u0010\u000f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0017\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0012\u001a\u00020\u0011H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0017\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0012\u001a\u00020\u0011H\u0016\u00a2\u0006\u0004\b\u0016\u0010\u0015R\"\u0010\u0018\u001a\u00020\u00178\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\"\u0010\u001f\u001a\u00020\u001e8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001f\u0010 \u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\"\u0010%\u001a\u00020\u00178\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b%\u0010\u0019\u001a\u0004\b&\u0010\u001b\"\u0004\b'\u0010\u001dR\"\u0010(\u001a\u00020\u00178\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b(\u0010\u0019\u001a\u0004\b)\u0010\u001b\"\u0004\b*\u0010\u001dR\"\u0010+\u001a\u00020\u00178\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b+\u0010\u0019\u001a\u0004\b,\u0010\u001b\"\u0004\b-\u0010\u001dR\"\u0010.\u001a\u00020\u00178\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b.\u0010\u0019\u001a\u0004\b/\u0010\u001b\"\u0004\b0\u0010\u001dR\"\u00101\u001a\u00020\u00178\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b1\u0010\u0019\u001a\u0004\b2\u0010\u001b\"\u0004\b3\u0010\u001dR\"\u00104\u001a\u00020\u001e8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b4\u0010 \u001a\u0004\b5\u0010\"\"\u0004\b6\u0010$R\"\u00108\u001a\u0002078\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b8\u00109\u001a\u0004\b:\u0010;\"\u0004\b<\u0010=R\"\u0010>\u001a\u0002078\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b>\u00109\u001a\u0004\b?\u0010;\"\u0004\b@\u0010=R\u001a\u0010B\u001a\u00020A8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\bB\u0010C\u001a\u0004\bD\u0010ER\"\u0010F\u001a\u00020\u00178\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\bF\u0010\u0019\u001a\u0004\bG\u0010\u001b\"\u0004\bH\u0010\u001dR\"\u0010I\u001a\u00020\u00178\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\bI\u0010\u0019\u001a\u0004\bJ\u0010\u001b\"\u0004\bK\u0010\u001d\u00a8\u0006O"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/AnimatedParticleUVMode;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleUVMode;", "T", "Lcom/mojang/serialization/DynamicOps;", "ops", "Lcom/mojang/serialization/DataResult;", "encode", "(Lcom/mojang/serialization/DynamicOps;)Lcom/mojang/serialization/DataResult;", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "", "age", "maxAge", "Lcom/cobblemon/mod/common/api/snowstorm/UVDetails;", "uvDetails", "get", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;DDLcom/cobblemon/mod/common/api/snowstorm/UVDetails;)Lcom/cobblemon/mod/common/api/snowstorm/UVDetails;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "readFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "writeToBuffer", "Lcom/bedrockk/molang/Expression;", "fps", "Lcom/bedrockk/molang/Expression;", "getFps", "()Lcom/bedrockk/molang/Expression;", "setFps", "(Lcom/bedrockk/molang/Expression;)V", "", "loop", "Z", "getLoop", "()Z", "setLoop", "(Z)V", "maxFrame", "getMaxFrame", "setMaxFrame", "startU", "getStartU", "setStartU", "startV", "getStartV", "setStartV", "stepU", "getStepU", "setStepU", "stepV", "getStepV", "setStepV", "stretchToLifetime", "getStretchToLifetime", "setStretchToLifetime", "", "textureSizeX", "I", "getTextureSizeX", "()I", "setTextureSizeX", "(I)V", "textureSizeY", "getTextureSizeY", "setTextureSizeY", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleUVModeType;", "type", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleUVModeType;", "getType", "()Lcom/cobblemon/mod/common/api/snowstorm/ParticleUVModeType;", "uSize", "getUSize", "setUSize", "vSize", "getVSize", "setVSize", "<init>", "(Lcom/bedrockk/molang/Expression;Lcom/bedrockk/molang/Expression;IILcom/bedrockk/molang/Expression;Lcom/bedrockk/molang/Expression;Lcom/bedrockk/molang/Expression;Lcom/bedrockk/molang/Expression;Lcom/bedrockk/molang/Expression;Lcom/bedrockk/molang/Expression;ZZ)V", "Companion", "common"})
public final class AnimatedParticleUVMode
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
    private Expression stepU;
    @NotNull
    private Expression stepV;
    @NotNull
    private Expression maxFrame;
    @NotNull
    private Expression fps;
    private boolean stretchToLifetime;
    private boolean loop;
    @NotNull
    private final ParticleUVModeType type;
    @NotNull
    private static final Codec<AnimatedParticleUVMode> CODEC;

    public AnimatedParticleUVMode(@NotNull Expression startU, @NotNull Expression startV, int textureSizeX, int textureSizeY, @NotNull Expression uSize, @NotNull Expression vSize, @NotNull Expression stepU, @NotNull Expression stepV, @NotNull Expression maxFrame, @NotNull Expression fps, boolean stretchToLifetime, boolean loop) {
        Intrinsics.checkNotNullParameter((Object)startU, (String)"startU");
        Intrinsics.checkNotNullParameter((Object)startV, (String)"startV");
        Intrinsics.checkNotNullParameter((Object)uSize, (String)"uSize");
        Intrinsics.checkNotNullParameter((Object)vSize, (String)"vSize");
        Intrinsics.checkNotNullParameter((Object)stepU, (String)"stepU");
        Intrinsics.checkNotNullParameter((Object)stepV, (String)"stepV");
        Intrinsics.checkNotNullParameter((Object)maxFrame, (String)"maxFrame");
        Intrinsics.checkNotNullParameter((Object)fps, (String)"fps");
        this.startU = startU;
        this.startV = startV;
        this.textureSizeX = textureSizeX;
        this.textureSizeY = textureSizeY;
        this.uSize = uSize;
        this.vSize = vSize;
        this.stepU = stepU;
        this.stepV = stepV;
        this.maxFrame = maxFrame;
        this.fps = fps;
        this.stretchToLifetime = stretchToLifetime;
        this.loop = loop;
        this.type = ParticleUVModeType.ANIMATED;
    }

    public /* synthetic */ AnimatedParticleUVMode(Expression expression, Expression expression2, int n, int n2, Expression expression3, Expression expression4, Expression expression5, Expression expression6, Expression expression7, Expression expression8, boolean bl, boolean bl2, int n3, DefaultConstructorMarker defaultConstructorMarker) {
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
        if ((n3 & 0x40) != 0) {
            expression5 = new NumberExpression(8.0);
        }
        if ((n3 & 0x80) != 0) {
            expression6 = new NumberExpression(0.0);
        }
        if ((n3 & 0x100) != 0) {
            expression7 = new NumberExpression(0.0);
        }
        if ((n3 & 0x200) != 0) {
            expression8 = new NumberExpression(1.0);
        }
        if ((n3 & 0x400) != 0) {
            bl = false;
        }
        if ((n3 & 0x800) != 0) {
            bl2 = false;
        }
        this(expression, expression2, n, n2, expression3, expression4, expression5, expression6, expression7, expression8, bl, bl2);
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

    @NotNull
    public final Expression getStepU() {
        return this.stepU;
    }

    public final void setStepU(@NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)expression, (String)"<set-?>");
        this.stepU = expression;
    }

    @NotNull
    public final Expression getStepV() {
        return this.stepV;
    }

    public final void setStepV(@NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)expression, (String)"<set-?>");
        this.stepV = expression;
    }

    @NotNull
    public final Expression getMaxFrame() {
        return this.maxFrame;
    }

    public final void setMaxFrame(@NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)expression, (String)"<set-?>");
        this.maxFrame = expression;
    }

    @NotNull
    public final Expression getFps() {
        return this.fps;
    }

    public final void setFps(@NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)expression, (String)"<set-?>");
        this.fps = expression;
    }

    public final boolean getStretchToLifetime() {
        return this.stretchToLifetime;
    }

    public final void setStretchToLifetime(boolean bl) {
        this.stretchToLifetime = bl;
    }

    public final boolean getLoop() {
        return this.loop;
    }

    public final void setLoop(boolean bl) {
        this.loop = bl;
    }

    @Override
    @NotNull
    public ParticleUVModeType getType() {
        return this.type;
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
        Expression expression5 = MoLang.createParser(buffer.m_130277_()).parseExpression();
        Intrinsics.checkNotNullExpressionValue((Object)expression5, (String)"createParser(buffer.read\u2026ring()).parseExpression()");
        this.stepU = expression5;
        Expression expression6 = MoLang.createParser(buffer.m_130277_()).parseExpression();
        Intrinsics.checkNotNullExpressionValue((Object)expression6, (String)"createParser(buffer.read\u2026ring()).parseExpression()");
        this.stepV = expression6;
        Expression expression7 = MoLang.createParser(buffer.m_130277_()).parseExpression();
        Intrinsics.checkNotNullExpressionValue((Object)expression7, (String)"createParser(buffer.read\u2026ring()).parseExpression()");
        this.maxFrame = expression7;
        Expression expression8 = MoLang.createParser(buffer.m_130277_()).parseExpression();
        Intrinsics.checkNotNullExpressionValue((Object)expression8, (String)"createParser(buffer.read\u2026ring()).parseExpression()");
        this.fps = expression8;
        this.stretchToLifetime = buffer.readBoolean();
        this.loop = buffer.readBoolean();
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
        buffer.m_130070_(MoLangExtensionsKt.getString(this.stepU));
        buffer.m_130070_(MoLangExtensionsKt.getString(this.stepV));
        buffer.m_130070_(MoLangExtensionsKt.getString(this.maxFrame));
        buffer.m_130070_(MoLangExtensionsKt.getString(this.fps));
        buffer.writeBoolean(this.stretchToLifetime);
        buffer.writeBoolean(this.loop);
    }

    @Override
    @NotNull
    public UVDetails get(@NotNull MoLangRuntime runtime2, double age, double maxAge, @NotNull UVDetails uvDetails) {
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        Intrinsics.checkNotNullParameter((Object)uvDetails, (String)"uvDetails");
        int maxFrame = MoLangExtensionsKt.resolveInt(runtime2, this.maxFrame) - 1;
        double stepU = MoLangExtensionsKt.resolveDouble(runtime2, this.stepU);
        double stepV = MoLangExtensionsKt.resolveDouble(runtime2, this.stepV);
        double uSize = MoLangExtensionsKt.resolveDouble(runtime2, this.getUSize());
        double vSize = MoLangExtensionsKt.resolveDouble(runtime2, this.getVSize());
        if (this.stretchToLifetime) {
            int frame = (int)(age / maxAge * (double)maxFrame);
            double startU = MoLangExtensionsKt.resolveDouble(runtime2, this.getStartU()) + (double)frame * stepU;
            double startV = MoLangExtensionsKt.resolveDouble(runtime2, this.getStartV()) + (double)frame * stepV;
            return uvDetails.set(startU / (double)this.getTextureSizeX(), startV / (double)this.getTextureSizeY(), (startU + uSize) / (double)this.getTextureSizeX(), (startV + vSize) / (double)this.getTextureSizeY());
        }
        double fps = MoLangExtensionsKt.resolveDouble(runtime2, this.fps);
        int effectiveFrame = (int)(age * fps % (double)maxFrame);
        int frame = !this.loop && age * fps >= (double)maxFrame ? maxFrame : effectiveFrame;
        double startU = MoLangExtensionsKt.resolveDouble(runtime2, this.getStartU()) + (double)frame * stepU;
        double startV = MoLangExtensionsKt.resolveDouble(runtime2, this.getStartV()) + (double)frame * stepV;
        return uvDetails.set(startU / (double)this.getTextureSizeX(), startV / (double)this.getTextureSizeY(), (startU + uSize) / (double)this.getTextureSizeX(), (startV + vSize) / (double)this.getTextureSizeY());
    }

    private static final String CODEC$lambda$14$lambda$0(AnimatedParticleUVMode it) {
        return it.getType().name();
    }

    private static final Expression CODEC$lambda$14$lambda$1(AnimatedParticleUVMode it) {
        return it.getStartU();
    }

    private static final Expression CODEC$lambda$14$lambda$2(AnimatedParticleUVMode it) {
        return it.getStartV();
    }

    private static final Integer CODEC$lambda$14$lambda$3(AnimatedParticleUVMode it) {
        return it.getTextureSizeX();
    }

    private static final Integer CODEC$lambda$14$lambda$4(AnimatedParticleUVMode it) {
        return it.getTextureSizeY();
    }

    private static final Expression CODEC$lambda$14$lambda$5(AnimatedParticleUVMode it) {
        return it.getUSize();
    }

    private static final Expression CODEC$lambda$14$lambda$6(AnimatedParticleUVMode it) {
        return it.getVSize();
    }

    private static final Expression CODEC$lambda$14$lambda$7(AnimatedParticleUVMode it) {
        return it.stepU;
    }

    private static final Expression CODEC$lambda$14$lambda$8(AnimatedParticleUVMode it) {
        return it.stepV;
    }

    private static final Expression CODEC$lambda$14$lambda$9(AnimatedParticleUVMode it) {
        return it.maxFrame;
    }

    private static final Expression CODEC$lambda$14$lambda$10(AnimatedParticleUVMode it) {
        return it.fps;
    }

    private static final Boolean CODEC$lambda$14$lambda$11(AnimatedParticleUVMode it) {
        return it.stretchToLifetime;
    }

    private static final Boolean CODEC$lambda$14$lambda$12(AnimatedParticleUVMode it) {
        return it.loop;
    }

    private static final AnimatedParticleUVMode CODEC$lambda$14$lambda$13(String string, Expression startU, Expression startV, Integer textureSizeX, Integer textureSizeY, Expression uSize, Expression vSize, Expression stepU, Expression stepV, Expression maxFrame, Expression fps, Boolean stretchToLifetime, Boolean loop) {
        Intrinsics.checkNotNullExpressionValue((Object)startU, (String)"startU");
        Intrinsics.checkNotNullExpressionValue((Object)startV, (String)"startV");
        Intrinsics.checkNotNullExpressionValue((Object)textureSizeX, (String)"textureSizeX");
        int n = textureSizeX;
        Intrinsics.checkNotNullExpressionValue((Object)textureSizeY, (String)"textureSizeY");
        int n2 = textureSizeY;
        Intrinsics.checkNotNullExpressionValue((Object)uSize, (String)"uSize");
        Intrinsics.checkNotNullExpressionValue((Object)vSize, (String)"vSize");
        Intrinsics.checkNotNullExpressionValue((Object)stepU, (String)"stepU");
        Intrinsics.checkNotNullExpressionValue((Object)stepV, (String)"stepV");
        Intrinsics.checkNotNullExpressionValue((Object)maxFrame, (String)"maxFrame");
        Intrinsics.checkNotNullExpressionValue((Object)fps, (String)"fps");
        Intrinsics.checkNotNullExpressionValue((Object)stretchToLifetime, (String)"stretchToLifetime");
        boolean bl = stretchToLifetime;
        Intrinsics.checkNotNullExpressionValue((Object)loop, (String)"loop");
        return new AnimatedParticleUVMode(startU, startV, n, n2, uSize, vSize, stepU, stepV, maxFrame, fps, bl, loop);
    }

    private static final App CODEC$lambda$14(RecordCodecBuilder.Instance instance) {
        return instance.group((App)PrimitiveCodec.STRING.fieldOf("type").forGetter(AnimatedParticleUVMode::CODEC$lambda$14$lambda$0), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("startU").forGetter(AnimatedParticleUVMode::CODEC$lambda$14$lambda$1), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("startV").forGetter(AnimatedParticleUVMode::CODEC$lambda$14$lambda$2), (App)PrimitiveCodec.INT.fieldOf("textureSizeX").forGetter(AnimatedParticleUVMode::CODEC$lambda$14$lambda$3), (App)PrimitiveCodec.INT.fieldOf("textureSizeY").forGetter(AnimatedParticleUVMode::CODEC$lambda$14$lambda$4), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("uSize").forGetter(AnimatedParticleUVMode::CODEC$lambda$14$lambda$5), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("vSize").forGetter(AnimatedParticleUVMode::CODEC$lambda$14$lambda$6), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("stepU").forGetter(AnimatedParticleUVMode::CODEC$lambda$14$lambda$7), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("stepV").forGetter(AnimatedParticleUVMode::CODEC$lambda$14$lambda$8), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("maxFrame").forGetter(AnimatedParticleUVMode::CODEC$lambda$14$lambda$9), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("fps").forGetter(AnimatedParticleUVMode::CODEC$lambda$14$lambda$10), (App)PrimitiveCodec.BOOL.fieldOf("stretchToLifetime").forGetter(AnimatedParticleUVMode::CODEC$lambda$14$lambda$11), (App)PrimitiveCodec.BOOL.fieldOf("loop").forGetter(AnimatedParticleUVMode::CODEC$lambda$14$lambda$12)).apply((Applicative)instance, AnimatedParticleUVMode::CODEC$lambda$14$lambda$13);
    }

    public AnimatedParticleUVMode() {
        this(null, null, 0, 0, null, null, null, null, null, null, false, false, 4095, null);
    }

    static {
        Codec codec2 = RecordCodecBuilder.create(AnimatedParticleUVMode::CODEC$lambda$14);
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"create { instance ->\n   \u2026)\n            }\n        }");
        CODEC = codec2;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/AnimatedParticleUVMode$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/api/snowstorm/AnimatedParticleUVMode;", "CODEC", "Lcom/mojang/serialization/Codec;", "getCODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Codec<AnimatedParticleUVMode> getCODEC() {
            return CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

