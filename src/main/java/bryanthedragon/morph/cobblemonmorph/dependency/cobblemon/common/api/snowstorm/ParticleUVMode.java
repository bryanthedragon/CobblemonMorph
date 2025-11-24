/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.NumberExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.codec.CodecMapped;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.ArbitrarilyMappedSerializableCompanion;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.AnimatedParticleUVMode;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleUVModeType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.StaticParticleUVMode;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.UVDetails;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\r\b&\u0018\u0000 +2\u00020\u0001:\u0001+B\u0007\u00a2\u0006\u0004\b)\u0010*J/\u0010\t\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0007H&\u00a2\u0006\u0004\b\t\u0010\nR\"\u0010\f\u001a\u00020\u000b8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\"\u0010\u0012\u001a\u00020\u000b8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u0012\u0010\r\u001a\u0004\b\u0013\u0010\u000f\"\u0004\b\u0014\u0010\u0011R\"\u0010\u0016\u001a\u00020\u00158\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\"\u0010\u001c\u001a\u00020\u00158\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u001c\u0010\u0017\u001a\u0004\b\u001d\u0010\u0019\"\u0004\b\u001e\u0010\u001bR\u0014\u0010\"\u001a\u00020\u001f8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b \u0010!R\"\u0010#\u001a\u00020\u000b8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b#\u0010\r\u001a\u0004\b$\u0010\u000f\"\u0004\b%\u0010\u0011R\"\u0010&\u001a\u00020\u000b8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b&\u0010\r\u001a\u0004\b'\u0010\u000f\"\u0004\b(\u0010\u0011\u00a8\u0006,"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/ParticleUVMode;", "Lcom/cobblemon/mod/common/api/codec/CodecMapped;", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "moLangRuntime", "", "age", "maxAge", "Lcom/cobblemon/mod/common/api/snowstorm/UVDetails;", "uvDetails", "get", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;DDLcom/cobblemon/mod/common/api/snowstorm/UVDetails;)Lcom/cobblemon/mod/common/api/snowstorm/UVDetails;", "Lcom/bedrockk/molang/Expression;", "startU", "Lcom/bedrockk/molang/Expression;", "getStartU", "()Lcom/bedrockk/molang/Expression;", "setStartU", "(Lcom/bedrockk/molang/Expression;)V", "startV", "getStartV", "setStartV", "", "textureSizeX", "I", "getTextureSizeX", "()I", "setTextureSizeX", "(I)V", "textureSizeY", "getTextureSizeY", "setTextureSizeY", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleUVModeType;", "getType", "()Lcom/cobblemon/mod/common/api/snowstorm/ParticleUVModeType;", "type", "uSize", "getUSize", "setUSize", "vSize", "getVSize", "setVSize", "<init>", "()V", "Companion", "common"})
public abstract class ParticleUVMode
implements CodecMapped {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private Expression startU = new NumberExpression(0.0);
    @NotNull
    private Expression startV = new NumberExpression(0.0);
    private int textureSizeX = 8;
    private int textureSizeY = 8;
    @NotNull
    private Expression uSize = new NumberExpression(8.0);
    @NotNull
    private Expression vSize = new NumberExpression(8.0);

    @NotNull
    public abstract ParticleUVModeType getType();

    @NotNull
    public Expression getStartU() {
        return this.startU;
    }

    public void setStartU(@NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)expression, (String)"<set-?>");
        this.startU = expression;
    }

    @NotNull
    public Expression getStartV() {
        return this.startV;
    }

    public void setStartV(@NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)expression, (String)"<set-?>");
        this.startV = expression;
    }

    public int getTextureSizeX() {
        return this.textureSizeX;
    }

    public void setTextureSizeX(int n) {
        this.textureSizeX = n;
    }

    public int getTextureSizeY() {
        return this.textureSizeY;
    }

    public void setTextureSizeY(int n) {
        this.textureSizeY = n;
    }

    @NotNull
    public Expression getUSize() {
        return this.uSize;
    }

    public void setUSize(@NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)expression, (String)"<set-?>");
        this.uSize = expression;
    }

    @NotNull
    public Expression getVSize() {
        return this.vSize;
    }

    public void setVSize(@NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)expression, (String)"<set-?>");
        this.vSize = expression;
    }

    @NotNull
    public abstract UVDetails get(@NotNull MoLangRuntime var1, double var2, double var4, @NotNull UVDetails var6);

    static {
        Companion.registerSubtype(ParticleUVModeType.ANIMATED, AnimatedParticleUVMode.class, AnimatedParticleUVMode.Companion.getCODEC());
        Companion.registerSubtype(ParticleUVModeType.STATIC, StaticParticleUVMode.class, StaticParticleUVMode.Companion.getCODEC());
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0004\u0010\u0005\u00a8\u0006\u0006"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/ParticleUVMode$Companion;", "Lcom/cobblemon/mod/common/api/data/ArbitrarilyMappedSerializableCompanion;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleUVMode;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleUVModeType;", "<init>", "()V", "common"})
    public static final class Companion
    extends ArbitrarilyMappedSerializableCompanion<ParticleUVMode, ParticleUVModeType> {
        private Companion() {
            super(1.INSTANCE, 2.INSTANCE, 3.INSTANCE);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

