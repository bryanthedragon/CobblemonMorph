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
 *  net.minecraft.network.FriendlyByteBuf
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.MoLang;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.NumberExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.codec.ExpressionCodecKt;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0010\u0018\u0000 !2\u00020\u0001:\u0001!B9\u0012\b\b\u0002\u0010\u000f\u001a\u00020\b\u0012\b\b\u0002\u0010\u001c\u001a\u00020\b\u0012\b\b\u0002\u0010\u0019\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0012\u00a2\u0006\u0004\b\u001f\u0010 J\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0015\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0007\u0010\u0006R\"\u0010\t\u001a\u00020\b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\"\u0010\u000f\u001a\u00020\b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u000f\u0010\n\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000eR\"\u0010\u0013\u001a\u00020\u00128\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\"\u0010\u0019\u001a\u00020\b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0019\u0010\n\u001a\u0004\b\u001a\u0010\f\"\u0004\b\u001b\u0010\u000eR\"\u0010\u001c\u001a\u00020\b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001c\u0010\n\u001a\u0004\b\u001d\u0010\f\"\u0004\b\u001e\u0010\u000e\u00a8\u0006\""}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/ParticleCollision;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "readFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "writeToBuffer", "Lcom/bedrockk/molang/Expression;", "bounciness", "Lcom/bedrockk/molang/Expression;", "getBounciness", "()Lcom/bedrockk/molang/Expression;", "setBounciness", "(Lcom/bedrockk/molang/Expression;)V", "enabled", "getEnabled", "setEnabled", "", "expiresOnContact", "Z", "getExpiresOnContact", "()Z", "setExpiresOnContact", "(Z)V", "friction", "getFriction", "setFriction", "radius", "getRadius", "setRadius", "<init>", "(Lcom/bedrockk/molang/Expression;Lcom/bedrockk/molang/Expression;Lcom/bedrockk/molang/Expression;Lcom/bedrockk/molang/Expression;Z)V", "Companion", "common"})
public final class ParticleCollision {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private Expression enabled;
    @NotNull
    private Expression radius;
    @NotNull
    private Expression friction;
    @NotNull
    private Expression bounciness;
    private boolean expiresOnContact;
    @NotNull
    private static final Codec<ParticleCollision> CODEC;

    public ParticleCollision(@NotNull Expression enabled, @NotNull Expression radius, @NotNull Expression friction, @NotNull Expression bounciness, boolean expiresOnContact) {
        Intrinsics.checkNotNullParameter((Object)enabled, (String)"enabled");
        Intrinsics.checkNotNullParameter((Object)radius, (String)"radius");
        Intrinsics.checkNotNullParameter((Object)friction, (String)"friction");
        Intrinsics.checkNotNullParameter((Object)bounciness, (String)"bounciness");
        this.enabled = enabled;
        this.radius = radius;
        this.friction = friction;
        this.bounciness = bounciness;
        this.expiresOnContact = expiresOnContact;
    }

    public /* synthetic */ ParticleCollision(Expression expression, Expression expression2, Expression expression3, Expression expression4, boolean bl, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            expression = new NumberExpression(0.0);
        }
        if ((n & 2) != 0) {
            expression2 = new NumberExpression(0.1);
        }
        if ((n & 4) != 0) {
            expression3 = new NumberExpression(10.0);
        }
        if ((n & 8) != 0) {
            expression4 = new NumberExpression(0.0);
        }
        if ((n & 0x10) != 0) {
            bl = false;
        }
        this(expression, expression2, expression3, expression4, bl);
    }

    @NotNull
    public final Expression getEnabled() {
        return this.enabled;
    }

    public final void setEnabled(@NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)expression, (String)"<set-?>");
        this.enabled = expression;
    }

    @NotNull
    public final Expression getRadius() {
        return this.radius;
    }

    public final void setRadius(@NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)expression, (String)"<set-?>");
        this.radius = expression;
    }

    @NotNull
    public final Expression getFriction() {
        return this.friction;
    }

    public final void setFriction(@NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)expression, (String)"<set-?>");
        this.friction = expression;
    }

    @NotNull
    public final Expression getBounciness() {
        return this.bounciness;
    }

    public final void setBounciness(@NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)expression, (String)"<set-?>");
        this.bounciness = expression;
    }

    public final boolean getExpiresOnContact() {
        return this.expiresOnContact;
    }

    public final void setExpiresOnContact(boolean bl) {
        this.expiresOnContact = bl;
    }

    public final void writeToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130070_(MoLangExtensionsKt.getString(this.enabled));
        buffer.m_130070_(MoLangExtensionsKt.getString(this.radius));
        buffer.m_130070_(MoLangExtensionsKt.getString(this.friction));
        buffer.m_130070_(MoLangExtensionsKt.getString(this.bounciness));
        buffer.writeBoolean(this.expiresOnContact);
    }

    public final void readFromBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        Expression expression = MoLang.createParser(buffer.m_130277_()).parseExpression();
        Intrinsics.checkNotNullExpressionValue((Object)expression, (String)"createParser(buffer.read\u2026ring()).parseExpression()");
        this.enabled = expression;
        Expression expression2 = MoLang.createParser(buffer.m_130277_()).parseExpression();
        Intrinsics.checkNotNullExpressionValue((Object)expression2, (String)"createParser(buffer.read\u2026ring()).parseExpression()");
        this.radius = expression2;
        Expression expression3 = MoLang.createParser(buffer.m_130277_()).parseExpression();
        Intrinsics.checkNotNullExpressionValue((Object)expression3, (String)"createParser(buffer.read\u2026ring()).parseExpression()");
        this.friction = expression3;
        Expression expression4 = MoLang.createParser(buffer.m_130277_()).parseExpression();
        Intrinsics.checkNotNullExpressionValue((Object)expression4, (String)"createParser(buffer.read\u2026ring()).parseExpression()");
        this.bounciness = expression4;
        this.expiresOnContact = buffer.readBoolean();
    }

    private static final Expression CODEC$lambda$5$lambda$0(ParticleCollision it) {
        return it.enabled;
    }

    private static final Expression CODEC$lambda$5$lambda$1(ParticleCollision it) {
        return it.radius;
    }

    private static final Expression CODEC$lambda$5$lambda$2(ParticleCollision it) {
        return it.friction;
    }

    private static final Expression CODEC$lambda$5$lambda$3(ParticleCollision it) {
        return it.bounciness;
    }

    private static final Boolean CODEC$lambda$5$lambda$4(ParticleCollision it) {
        return it.expiresOnContact;
    }

    private static final App CODEC$lambda$5(RecordCodecBuilder.Instance instance) {
        return instance.group((App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("enabled").forGetter(ParticleCollision::CODEC$lambda$5$lambda$0), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("radius").forGetter(ParticleCollision::CODEC$lambda$5$lambda$1), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("friction").forGetter(ParticleCollision::CODEC$lambda$5$lambda$2), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("bounciness").forGetter(ParticleCollision::CODEC$lambda$5$lambda$3), (App)PrimitiveCodec.BOOL.fieldOf("expiresOnContact").forGetter(ParticleCollision::CODEC$lambda$5$lambda$4)).apply((Applicative)instance, ParticleCollision::new);
    }

    public ParticleCollision() {
        this(null, null, null, null, false, 31, null);
    }

    static {
        Codec codec2 = RecordCodecBuilder.create(ParticleCollision::CODEC$lambda$5);
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"create { instance ->\n   \u2026ticleCollision)\n        }");
        CODEC = codec2;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/ParticleCollision$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleCollision;", "CODEC", "Lcom/mojang/serialization/Codec;", "getCODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Codec<ParticleCollision> getCODEC() {
            return CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

