/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.GlStateManager$DestFactor
 *  com.mojang.blaze3d.platform.GlStateManager$SourceFactor
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.BufferBuilder
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.Camera
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.particle.Particle
 *  net.minecraft.client.particle.ParticleRenderType
 *  net.minecraft.client.renderer.texture.TextureAtlas
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.Shapes
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.joml.AxisAngle4d
 *  org.joml.Quaterniond
 *  org.joml.Quaternionf
 *  org.joml.Quaternionfc
 *  org.joml.Vector3d
 *  org.joml.Vector3dc
 *  org.joml.Vector3f
 *  org.joml.Vector4f
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.MoStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.VariableStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.DoubleValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.MoValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.ModAPI;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.MoLangCurve;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleCameraMode;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleCollision;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleMaterial;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleViewDirection;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.SimpleEventTrigger;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.UVDetails;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.ParticleStorm;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.MatrixWrapper;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.VertexConsumer;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.AxisAngle4d;
import org.joml.Quaterniond;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.joml.Vector3f;
import org.joml.Vector4f;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u008c\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b)\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 \u009b\u00012\u00020\u0001:\u0002\u009b\u0001BI\u0012\u0006\u0010x\u001a\u00020w\u0012\b\u0010\u0094\u0001\u001a\u00030\u0093\u0001\u0012\u0007\u0010\u0095\u0001\u001a\u00020\u0017\u0012\u0007\u0010\u0096\u0001\u001a\u00020\u0017\u0012\u0007\u0010\u0097\u0001\u001a\u00020\u0017\u0012\u0007\u0010\u0098\u0001\u001a\u00020\r\u0012\b\b\u0002\u0010>\u001a\u000205\u00a2\u0006\u0006\b\u0099\u0001\u0010\u009a\u0001J\u000f\u0010\u0003\u001a\u00020\u0002H\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004J'\u0010\u000b\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0017\u0010\u000f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rH\u0002\u00a2\u0006\u0004\b\u000f\u0010\u0010J\r\u0010\u0012\u001a\u00020\u0011\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u000f\u0010\u0015\u001a\u00020\u0014H\u0016\u00a2\u0006\u0004\b\u0015\u0010\u0016J\r\u0010\u0018\u001a\u00020\u0017\u00a2\u0006\u0004\b\u0018\u0010\u0019J\r\u0010\u001a\u001a\u00020\u0017\u00a2\u0006\u0004\b\u001a\u0010\u0019J\r\u0010\u001b\u001a\u00020\u0017\u00a2\u0006\u0004\b\u001b\u0010\u0019J\r\u0010\u001c\u001a\u00020\u0017\u00a2\u0006\u0004\b\u001c\u0010\u0019J\r\u0010\u001d\u001a\u00020\u0017\u00a2\u0006\u0004\b\u001d\u0010\u0019J\r\u0010\u001e\u001a\u00020\u0017\u00a2\u0006\u0004\b\u001e\u0010\u0019J\u000f\u0010\u001f\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u001f\u0010\u0004J'\u0010#\u001a\u00020\u00022\u0006\u0010 \u001a\u00020\u00172\u0006\u0010!\u001a\u00020\u00172\u0006\u0010\"\u001a\u00020\u0017H\u0016\u00a2\u0006\u0004\b#\u0010$J\r\u0010%\u001a\u00020\u0002\u00a2\u0006\u0004\b%\u0010\u0004J\u000f\u0010&\u001a\u00020\u0002H\u0002\u00a2\u0006\u0004\b&\u0010\u0004J\u000f\u0010'\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b'\u0010\u0004J\r\u0010(\u001a\u00020\u0002\u00a2\u0006\u0004\b(\u0010\u0004R\"\u0010)\u001a\u00020\u00178\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b)\u0010*\u001a\u0004\b+\u0010\u0019\"\u0004\b,\u0010-R\"\u0010/\u001a\u00020.8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b/\u00100\u001a\u0004\b1\u00102\"\u0004\b3\u00104R\"\u00106\u001a\u0002058\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b6\u00107\u001a\u0004\b8\u00109\"\u0004\b:\u0010;R\u0017\u0010<\u001a\u00020.8\u0006\u00a2\u0006\f\n\u0004\b<\u00100\u001a\u0004\b=\u00102R\"\u0010>\u001a\u0002058\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b>\u00107\u001a\u0004\b?\u00109\"\u0004\b@\u0010;R\"\u0010A\u001a\u00020\u00178\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bA\u0010*\u001a\u0004\bB\u0010\u0019\"\u0004\bC\u0010-R\"\u0010D\u001a\u00020\u00178\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bD\u0010*\u001a\u0004\bE\u0010\u0019\"\u0004\bF\u0010-R\"\u0010G\u001a\u00020\u00178\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bG\u0010*\u001a\u0004\bH\u0010\u0019\"\u0004\bI\u0010-R\"\u0010J\u001a\u00020.8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bJ\u00100\u001a\u0004\bK\u00102\"\u0004\bL\u00104R\"\u0010M\u001a\u00020\r8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bM\u0010N\u001a\u0004\bO\u0010P\"\u0004\bQ\u0010RR\u0017\u0010S\u001a\u00020\u00148\u0006\u00a2\u0006\f\n\u0004\bS\u0010T\u001a\u0004\bU\u0010\u0016R\"\u0010V\u001a\u00020\u00178\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bV\u0010*\u001a\u0004\bW\u0010\u0019\"\u0004\bX\u0010-R\"\u0010Y\u001a\u00020\u00178\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bY\u0010*\u001a\u0004\bZ\u0010\u0019\"\u0004\b[\u0010-R\"\u0010\\\u001a\u00020\u00178\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\\\u0010*\u001a\u0004\b]\u0010\u0019\"\u0004\b^\u0010-R\"\u0010`\u001a\u00020_8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b`\u0010a\u001a\u0004\bb\u0010c\"\u0004\bd\u0010eR\u0019\u0010g\u001a\u0004\u0018\u00010f8\u0006\u00a2\u0006\f\n\u0004\bg\u0010h\u001a\u0004\bi\u0010jR\u0019\u0010k\u001a\u0004\u0018\u00010f8\u0006\u00a2\u0006\f\n\u0004\bk\u0010h\u001a\u0004\bl\u0010jR\u0019\u0010m\u001a\u0004\u0018\u00010f8\u0006\u00a2\u0006\f\n\u0004\bm\u0010h\u001a\u0004\bn\u0010jR\u0019\u0010o\u001a\u0004\u0018\u00010f8\u0006\u00a2\u0006\f\n\u0004\bo\u0010h\u001a\u0004\bp\u0010jR\"\u0010q\u001a\u00020_8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bq\u0010a\u001a\u0004\br\u0010c\"\u0004\bs\u0010eR\u0017\u0010t\u001a\u00020\u00118\u0006\u00a2\u0006\f\n\u0004\bt\u0010u\u001a\u0004\bv\u0010\u0013R\u0017\u0010x\u001a\u00020w8\u0006\u00a2\u0006\f\n\u0004\bx\u0010y\u001a\u0004\bz\u0010{R%\u0010}\u001a\u00020|8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0015\n\u0004\b}\u0010~\u001a\u0005\b\u007f\u0010\u0080\u0001\"\u0006\b\u0081\u0001\u0010\u0082\u0001R\u001d\u0010\u0084\u0001\u001a\u00030\u0083\u00018\u0006\u00a2\u0006\u0010\n\u0006\b\u0084\u0001\u0010\u0085\u0001\u001a\u0006\b\u0086\u0001\u0010\u0087\u0001R*\u0010\u0089\u0001\u001a\u00030\u0088\u00018\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0018\n\u0006\b\u0089\u0001\u0010\u008a\u0001\u001a\u0006\b\u008b\u0001\u0010\u008c\u0001\"\u0006\b\u008d\u0001\u0010\u008e\u0001R/\u0010\u0090\u0001\u001a\u000b \u008f\u0001*\u0004\u0018\u00010\r0\r8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0015\n\u0005\b\u0090\u0001\u0010N\u001a\u0005\b\u0091\u0001\u0010P\"\u0005\b\u0092\u0001\u0010R\u00a8\u0006\u009c\u0001"}, d2={"Lcom/cobblemon/mod/common/client/render/SnowstormParticle;", "Lnet/minecraft/client/particle/Particle;", "", "applyRandoms", "()V", "Lcom/mojang/blaze3d/vertex/VertexConsumer;", "vertexConsumer", "Lnet/minecraft/client/Camera;", "camera", "", "tickDelta", "buildGeometry", "(Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/client/Camera;F)V", "Lnet/minecraft/world/phys/Vec3;", "movement", "checkCollision", "(Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;", "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "getSpriteFromAtlas", "()Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "Lnet/minecraft/client/particle/ParticleRenderType;", "getType", "()Lnet/minecraft/client/particle/ParticleRenderType;", "", "getVelocityX", "()D", "getVelocityY", "getVelocityZ", "getX", "getY", "getZ", "markDead", "dx", "dy", "dz", "move", "(DDD)V", "runExpirationEvents", "setParticleAgeInRuntime", "tick", "updatePosition", "angularVelocity", "D", "getAngularVelocity", "setAngularVelocity", "(D)V", "Lorg/joml/AxisAngle4d;", "axisRotation", "Lorg/joml/AxisAngle4d;", "getAxisRotation", "()Lorg/joml/AxisAngle4d;", "setAxisRotation", "(Lorg/joml/AxisAngle4d;)V", "", "colliding", "Z", "getColliding", "()Z", "setColliding", "(Z)V", "currentRotation", "getCurrentRotation", "invisible", "getInvisible", "setInvisible", "localX", "getLocalX", "setLocalX", "localY", "getLocalY", "setLocalY", "localZ", "getLocalZ", "setLocalZ", "oldAxisRotation", "getOldAxisRotation", "setOldAxisRotation", "originPos", "Lnet/minecraft/world/phys/Vec3;", "getOriginPos", "()Lnet/minecraft/world/phys/Vec3;", "setOriginPos", "(Lnet/minecraft/world/phys/Vec3;)V", "particleTextureSheet", "Lnet/minecraft/client/particle/ParticleRenderType;", "getParticleTextureSheet", "prevLocalX", "getPrevLocalX", "setPrevLocalX", "prevLocalY", "getPrevLocalY", "setPrevLocalY", "prevLocalZ", "getPrevLocalZ", "setPrevLocalZ", "Lorg/joml/Vector3d;", "prevRotatedLocal", "Lorg/joml/Vector3d;", "getPrevRotatedLocal", "()Lorg/joml/Vector3d;", "setPrevRotatedLocal", "(Lorg/joml/Vector3d;)V", "Lcom/bedrockk/molang/runtime/value/MoValue;", "random1", "Lcom/bedrockk/molang/runtime/value/MoValue;", "getRandom1", "()Lcom/bedrockk/molang/runtime/value/MoValue;", "random2", "getRandom2", "random3", "getRandom3", "random4", "getRandom4", "rotatedLocal", "getRotatedLocal", "setRotatedLocal", "sprite", "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "getSprite", "Lcom/cobblemon/mod/common/client/particle/ParticleStorm;", "storm", "Lcom/cobblemon/mod/common/client/particle/ParticleStorm;", "getStorm", "()Lcom/cobblemon/mod/common/client/particle/ParticleStorm;", "Lnet/minecraft/resources/ResourceLocation;", "texture", "Lnet/minecraft/resources/ResourceLocation;", "getTexture", "()Lnet/minecraft/resources/ResourceLocation;", "setTexture", "(Lnet/minecraft/resources/ResourceLocation;)V", "Lcom/cobblemon/mod/common/api/snowstorm/UVDetails;", "uvDetails", "Lcom/cobblemon/mod/common/api/snowstorm/UVDetails;", "getUvDetails", "()Lcom/cobblemon/mod/common/api/snowstorm/UVDetails;", "Lcom/bedrockk/molang/runtime/struct/VariableStruct;", "variableStruct", "Lcom/bedrockk/molang/runtime/struct/VariableStruct;", "getVariableStruct", "()Lcom/bedrockk/molang/runtime/struct/VariableStruct;", "setVariableStruct", "(Lcom/bedrockk/molang/runtime/struct/VariableStruct;)V", "kotlin.jvm.PlatformType", "viewDirection", "getViewDirection", "setViewDirection", "Lnet/minecraft/client/multiplayer/ClientLevel;", "world", "x", "y", "z", "initialVelocity", "<init>", "(Lcom/cobblemon/mod/common/client/particle/ParticleStorm;Lnet/minecraft/client/multiplayer/ClientLevel;DDDLnet/minecraft/world/phys/Vec3;Z)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nSnowstormParticle.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SnowstormParticle.kt\ncom/cobblemon/mod/common/client/render/SnowstormParticle\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,527:1\n1855#2,2:528\n1855#2,2:530\n1855#2,2:533\n1855#2,2:535\n1#3:532\n*S KotlinDebug\n*F\n+ 1 SnowstormParticle.kt\ncom/cobblemon/mod/common/client/render/SnowstormParticle\n*L\n129#1:528,2\n147#1:530,2\n247#1:533,2\n257#1:535,2\n*E\n"})
public final class SnowstormParticle
extends Particle {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final ParticleStorm storm;
    private boolean invisible;
    @NotNull
    private final TextureAtlasSprite sprite;
    @NotNull
    private final ParticleRenderType particleTextureSheet;
    private double angularVelocity;
    private boolean colliding;
    @NotNull
    private ResourceLocation texture;
    @NotNull
    private VariableStruct variableStruct;
    @Nullable
    private final MoValue random1;
    @Nullable
    private final MoValue random2;
    @Nullable
    private final MoValue random3;
    @Nullable
    private final MoValue random4;
    private double localX;
    private double localY;
    private double localZ;
    @NotNull
    private Vector3d rotatedLocal;
    private double prevLocalX;
    private double prevLocalY;
    private double prevLocalZ;
    @NotNull
    private Vector3d prevRotatedLocal;
    @NotNull
    private final AxisAngle4d currentRotation;
    @NotNull
    private AxisAngle4d oldAxisRotation;
    @NotNull
    private AxisAngle4d axisRotation;
    @NotNull
    private final UVDetails uvDetails;
    private Vec3 viewDirection;
    @NotNull
    private Vec3 originPos;
    public static final double MAXIMUM_DISTANCE_CHANGE_PER_TICK_FOR_FRICTION = 0.005;

    public SnowstormParticle(@NotNull ParticleStorm storm2, @NotNull ClientLevel world, double x, double y, double z, @NotNull Vec3 initialVelocity, boolean invisible) {
        ParticleRenderType particleRenderType;
        Intrinsics.checkNotNullParameter((Object)((Object)storm2), (String)"storm");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)initialVelocity, (String)"initialVelocity");
        super(world, x, y, z);
        this.storm = storm2;
        this.invisible = invisible;
        this.sprite = this.getSpriteFromAtlas();
        this.texture = this.storm.getEffect().getParticle().getTexture();
        MoStruct moStruct = this.storm.getRuntime().getEnvironment().getStructs().get("variable");
        Intrinsics.checkNotNull((Object)moStruct, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.VariableStruct");
        this.variableStruct = (VariableStruct)moStruct;
        this.random1 = this.variableStruct.getMap().get("particle_random_1");
        this.random2 = this.variableStruct.getMap().get("particle_random_2");
        this.random3 = this.variableStruct.getMap().get("particle_random_3");
        this.random4 = this.variableStruct.getMap().get("particle_random_4");
        this.localX = x - this.storm.getX();
        this.localY = y - this.storm.getY();
        this.localZ = z - this.storm.getZ();
        this.rotatedLocal = new Vector3d(this.localX, this.localY, this.localZ);
        this.prevLocalX = this.localX;
        this.prevLocalY = this.localY;
        this.prevLocalZ = this.localZ;
        this.prevRotatedLocal = new Vector3d(this.localX, this.localY, this.localZ);
        this.currentRotation = new AxisAngle4d(0.0, 0.0, 1.0, 0.0);
        this.oldAxisRotation = new AxisAngle4d(0.0, 0.0, 1.0, 0.0);
        this.axisRotation = new AxisAngle4d(0.0, 0.0, 1.0, 0.0);
        this.uvDetails = new UVDetails();
        this.viewDirection = Vec3.f_82478_;
        this.originPos = new Vec3(this.storm.getX(), this.storm.getY(), this.storm.getZ());
        this.m_172260_(initialVelocity.f_82479_, initialVelocity.f_82480_, initialVelocity.f_82481_);
        this.f_107204_ = this.f_107231_ = -((float)this.storm.getEffect().getParticle().getRotation().getInitialRotation(this.storm.getRuntime()));
        this.angularVelocity = this.storm.getEffect().getParticle().getRotation().getInitialAngularVelocity(this.storm.getRuntime());
        this.f_172258_ = 1.0f;
        this.f_107225_ = (int)(MoLangExtensionsKt.resolveDouble(this.storm.getRuntime(), this.storm.getEffect().getParticle().getMaxAge()) * (double)20);
        this.storm.getParticles().add(this);
        this.f_107226_ = 0.0f;
        if (this.invisible) {
            ParticleRenderType particleRenderType2 = ParticleRenderType.f_107434_;
            particleRenderType = particleRenderType2;
            Intrinsics.checkNotNullExpressionValue((Object)particleRenderType2, (String)"NO_RENDER");
        } else {
            ParticleRenderType particleRenderType3 = ParticleRenderType.f_107431_;
            particleRenderType = particleRenderType3;
            Intrinsics.checkNotNullExpressionValue((Object)particleRenderType3, (String)"PARTICLE_SHEET_TRANSLUCENT");
        }
        this.particleTextureSheet = particleRenderType;
        Iterable $this$forEach$iv = this.storm.getEffect().getParticle().getCreationEvents();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            SimpleEventTrigger it = (SimpleEventTrigger)element$iv;
            boolean bl = false;
            it.trigger(this.storm, this);
        }
    }

    public /* synthetic */ SnowstormParticle(ParticleStorm particleStorm, ClientLevel clientLevel, double d, double d2, double d3, Vec3 vec3, boolean bl, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 0x40) != 0) {
            bl = false;
        }
        this(particleStorm, clientLevel, d, d2, d3, vec3, bl);
    }

    @NotNull
    public final ParticleStorm getStorm() {
        return this.storm;
    }

    public final boolean getInvisible() {
        return this.invisible;
    }

    public final void setInvisible(boolean bl) {
        this.invisible = bl;
    }

    @NotNull
    public final TextureAtlasSprite getSprite() {
        return this.sprite;
    }

    @NotNull
    public final ParticleRenderType getParticleTextureSheet() {
        return this.particleTextureSheet;
    }

    public final double getAngularVelocity() {
        return this.angularVelocity;
    }

    public final void setAngularVelocity(double d) {
        this.angularVelocity = d;
    }

    public final boolean getColliding() {
        return this.colliding;
    }

    public final void setColliding(boolean bl) {
        this.colliding = bl;
    }

    @NotNull
    public final ResourceLocation getTexture() {
        return this.texture;
    }

    public final void setTexture(@NotNull ResourceLocation resourceLocation) {
        Intrinsics.checkNotNullParameter((Object)resourceLocation, (String)"<set-?>");
        this.texture = resourceLocation;
    }

    @NotNull
    public final VariableStruct getVariableStruct() {
        return this.variableStruct;
    }

    public final void setVariableStruct(@NotNull VariableStruct variableStruct) {
        Intrinsics.checkNotNullParameter((Object)variableStruct, (String)"<set-?>");
        this.variableStruct = variableStruct;
    }

    @Nullable
    public final MoValue getRandom1() {
        return this.random1;
    }

    @Nullable
    public final MoValue getRandom2() {
        return this.random2;
    }

    @Nullable
    public final MoValue getRandom3() {
        return this.random3;
    }

    @Nullable
    public final MoValue getRandom4() {
        return this.random4;
    }

    public final double getLocalX() {
        return this.localX;
    }

    public final void setLocalX(double d) {
        this.localX = d;
    }

    public final double getLocalY() {
        return this.localY;
    }

    public final void setLocalY(double d) {
        this.localY = d;
    }

    public final double getLocalZ() {
        return this.localZ;
    }

    public final void setLocalZ(double d) {
        this.localZ = d;
    }

    @NotNull
    public final Vector3d getRotatedLocal() {
        return this.rotatedLocal;
    }

    public final void setRotatedLocal(@NotNull Vector3d vector3d) {
        Intrinsics.checkNotNullParameter((Object)vector3d, (String)"<set-?>");
        this.rotatedLocal = vector3d;
    }

    public final double getPrevLocalX() {
        return this.prevLocalX;
    }

    public final void setPrevLocalX(double d) {
        this.prevLocalX = d;
    }

    public final double getPrevLocalY() {
        return this.prevLocalY;
    }

    public final void setPrevLocalY(double d) {
        this.prevLocalY = d;
    }

    public final double getPrevLocalZ() {
        return this.prevLocalZ;
    }

    public final void setPrevLocalZ(double d) {
        this.prevLocalZ = d;
    }

    @NotNull
    public final Vector3d getPrevRotatedLocal() {
        return this.prevRotatedLocal;
    }

    public final void setPrevRotatedLocal(@NotNull Vector3d vector3d) {
        Intrinsics.checkNotNullParameter((Object)vector3d, (String)"<set-?>");
        this.prevRotatedLocal = vector3d;
    }

    @NotNull
    public final AxisAngle4d getCurrentRotation() {
        return this.currentRotation;
    }

    @NotNull
    public final AxisAngle4d getOldAxisRotation() {
        return this.oldAxisRotation;
    }

    public final void setOldAxisRotation(@NotNull AxisAngle4d axisAngle4d) {
        Intrinsics.checkNotNullParameter((Object)axisAngle4d, (String)"<set-?>");
        this.oldAxisRotation = axisAngle4d;
    }

    @NotNull
    public final AxisAngle4d getAxisRotation() {
        return this.axisRotation;
    }

    public final void setAxisRotation(@NotNull AxisAngle4d axisAngle4d) {
        Intrinsics.checkNotNullParameter((Object)axisAngle4d, (String)"<set-?>");
        this.axisRotation = axisAngle4d;
    }

    @NotNull
    public final UVDetails getUvDetails() {
        return this.uvDetails;
    }

    public final Vec3 getViewDirection() {
        return this.viewDirection;
    }

    public final void setViewDirection(Vec3 vec3) {
        this.viewDirection = vec3;
    }

    @NotNull
    public final Vec3 getOriginPos() {
        return this.originPos;
    }

    public final void setOriginPos(@NotNull Vec3 vec3) {
        Intrinsics.checkNotNullParameter((Object)vec3, (String)"<set-?>");
        this.originPos = vec3;
    }

    public final double getX() {
        return this.f_107212_;
    }

    public final double getY() {
        return this.f_107213_;
    }

    public final double getZ() {
        return this.f_107214_;
    }

    public final double getVelocityX() {
        return this.f_107215_;
    }

    public final double getVelocityY() {
        return this.f_107216_;
    }

    public final double getVelocityZ() {
        return this.f_107217_;
    }

    @NotNull
    public final TextureAtlasSprite getSpriteFromAtlas() {
        TextureAtlas atlas = Minecraft.m_91087_().f_91061_.f_107296_;
        TextureAtlasSprite sprite = atlas.m_118316_(this.storm.getEffect().getParticle().getTexture());
        Intrinsics.checkNotNullExpressionValue((Object)sprite, (String)"sprite");
        return sprite;
    }

    private final void applyRandoms() {
        this.variableStruct.setDirectly("particle_random_1", this.random1);
        this.variableStruct.setDirectly("particle_random_2", this.random2);
        this.variableStruct.setDirectly("particle_random_3", this.random3);
        this.variableStruct.setDirectly("particle_random_4", this.random4);
    }

    public void m_5744_(@NotNull VertexConsumer vertexConsumer, @NotNull Camera camera, float tickDelta) {
        Vector3d vector3d;
        Intrinsics.checkNotNullParameter((Object)vertexConsumer, (String)"vertexConsumer");
        Intrinsics.checkNotNullParameter((Object)camera, (String)"camera");
        if (Cobblemon.INSTANCE.getImplementation().getModAPI() != ModAPI.FORGE && !Minecraft.m_91087_().f_91060_.f_172938_.m_113029_(this.m_107277_())) {
            return;
        }
        this.applyRandoms();
        this.setParticleAgeInRuntime();
        Iterable $this$forEach$iv = this.storm.getEffect().getCurves();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            MoLangCurve it = (MoLangCurve)element$iv;
            boolean bl = false;
            it.apply(this.storm.getRuntime());
        }
        this.storm.getRuntime().execute(this.storm.getEffect().getParticle().getRenderExpressions());
        switch (WhenMappings.$EnumSwitchMapping$0[this.storm.getEffect().getParticle().getMaterial().ordinal()]) {
            case 1: {
                RenderSystem.blendFunc((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
                break;
            }
            case 2: {
                RenderSystem.blendFunc((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_COLOR, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
                break;
            }
            case 3: {
                RenderSystem.blendFunc((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
                break;
            }
            case 4: {
                RenderSystem.blendFunc((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE);
            }
        }
        BufferBuilder cfr_ignored_0 = (BufferBuilder)vertexConsumer;
        Vec3 vec3d = camera.m_90583_();
        double interpLocalX = Mth.m_14139_((double)tickDelta, (double)this.prevLocalX, (double)this.localX);
        double interpLocalY = Mth.m_14139_((double)tickDelta, (double)this.prevLocalY, (double)this.localY);
        double interpLocalZ = Mth.m_14139_((double)tickDelta, (double)this.prevLocalZ, (double)this.localZ);
        if (this.storm.getEffect().getSpace().getLocalRotation()) {
            AxisAngle4d axisAngle4d;
            double interpRotation = Mth.m_14139_((double)tickDelta, (double)0.0, (double)this.currentRotation.angle);
            Vector3d vec = new Vector3d(interpLocalX, interpLocalY, interpLocalZ);
            this.oldAxisRotation.transform(vec);
            AxisAngle4d it = axisAngle4d = this.currentRotation.get(new AxisAngle4d());
            boolean bl = false;
            it.angle = interpRotation;
            vector3d = axisAngle4d.transform(vec);
        } else {
            vector3d = new Vector3d(interpLocalX, interpLocalY, interpLocalZ);
        }
        Vector3d pos = vector3d;
        float f = (float)(pos.x + this.originPos.f_82479_ - vec3d.m_7096_());
        float g = (float)(pos.y + this.originPos.f_82480_ - vec3d.m_7098_());
        float h = (float)(pos.z + this.originPos.f_82481_ - vec3d.m_7094_());
        ParticleCameraMode particleCameraMode = this.storm.getEffect().getParticle().getCameraMode();
        MatrixWrapper matrixWrapper = this.storm.getMatrixWrapper();
        Vec3 vec3 = new Vec3(this.f_107212_, this.f_107213_, this.f_107214_);
        Vec3 vec32 = camera.m_90583_();
        Intrinsics.checkNotNullExpressionValue((Object)vec32, (String)"camera.pos");
        Quaternionf quaternionf = camera.m_253121_();
        Intrinsics.checkNotNullExpressionValue((Object)quaternionf, (String)"camera.rotation");
        float f2 = camera.m_90590_();
        float f3 = camera.m_90589_();
        Vec3 vec33 = this.viewDirection;
        Intrinsics.checkNotNullExpressionValue((Object)vec33, (String)"viewDirection");
        Quaternionf quaternion = particleCameraMode.getRotation(matrixWrapper, this.f_107204_, this.f_107231_, tickDelta, vec3, vec32, quaternionf, f2, f3, vec33);
        float xSize = (float)MoLangExtensionsKt.resolveDouble(this.storm.getRuntime(), this.storm.getEffect().getParticle().getSizeX()) / 1.5f;
        float ySize = (float)MoLangExtensionsKt.resolveDouble(this.storm.getRuntime(), this.storm.getEffect().getParticle().getSizeY()) / 1.5f;
        Vector3f[] vector3fArray = new Vector3f[]{new Vector3f(-xSize, -ySize, 0.0f), new Vector3f(-xSize, ySize, 0.0f), new Vector3f(xSize, ySize, 0.0f), new Vector3f(xSize, -ySize, 0.0f)};
        Vector3f[] particleVertices = vector3fArray;
        for (int k = 0; k < 4; ++k) {
            Vector3f vertex = particleVertices[k];
            vertex.rotate((Quaternionfc)quaternion);
            vertex.add(f, g, h);
        }
        UVDetails uvs = this.storm.getEffect().getParticle().getUvMode().get(this.storm.getRuntime(), (double)this.f_107224_ / 20.0, (double)this.f_107225_ / 20.0, this.uvDetails);
        Vector4f colour = this.storm.getEffect().getParticle().getTinting().getTint(this.storm.getRuntime());
        float spriteURange = this.sprite.m_118410_() - this.sprite.m_118409_();
        float spriteVRange = this.sprite.m_118412_() - this.sprite.m_118411_();
        float minU = uvs.getStartU() * spriteURange + this.sprite.m_118409_();
        float maxU = uvs.getEndU() * spriteURange + this.sprite.m_118409_();
        float minV = uvs.getStartV() * spriteVRange + this.sprite.m_118411_();
        float maxV = uvs.getEndV() * spriteVRange + this.sprite.m_118411_();
        int p = this.storm.getEffect().getParticle().getEnvironmentLighting() ? this.m_6355_(tickDelta) : 0xF000F0;
        vertexConsumer.m_5483_((double)particleVertices[0].x, (double)particleVertices[0].y, (double)particleVertices[0].z).m_7421_(maxU, maxV).m_85950_(colour.x, colour.y, colour.z, colour.w).m_85969_(p).m_5752_();
        vertexConsumer.m_5483_((double)particleVertices[1].x, (double)particleVertices[1].y, (double)particleVertices[1].z).m_7421_(maxU, minV).m_85950_(colour.x, colour.y, colour.z, colour.w).m_85969_(p).m_5752_();
        vertexConsumer.m_5483_((double)particleVertices[2].x, (double)particleVertices[2].y, (double)particleVertices[2].z).m_7421_(minU, minV).m_85950_(colour.x, colour.y, colour.z, colour.w).m_85969_(p).m_5752_();
        vertexConsumer.m_5483_((double)particleVertices[3].x, (double)particleVertices[3].y, (double)particleVertices[3].z).m_7421_(minU, maxV).m_85950_(colour.x, colour.y, colour.z, colour.w).m_85969_(p).m_5752_();
    }

    public final void runExpirationEvents() {
        Iterable $this$forEach$iv = this.storm.getEffect().getParticle().getExpirationEvents();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            SimpleEventTrigger it = (SimpleEventTrigger)element$iv;
            boolean bl = false;
            it.trigger(this.storm, this);
        }
    }

    public void m_5989_() {
        if (this.storm.getEffect().getSpace().getLocalPosition()) {
            Vec3 vec3 = this.storm.getMatrixWrapper().getOrigin();
            Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"storm.matrixWrapper.getOrigin()");
            this.originPos = vec3;
        }
        this.applyRandoms();
        this.setParticleAgeInRuntime();
        Iterable $this$forEach$iv = this.storm.getEffect().getCurves();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            MoLangCurve it = (MoLangCurve)element$iv;
            boolean bl = false;
            it.apply(this.storm.getRuntime());
        }
        this.storm.getRuntime().execute(this.storm.getEffect().getParticle().getUpdateExpressions());
        this.angularVelocity = this.storm.getEffect().getParticle().getRotation().getAngularVelocity(this.storm.getRuntime(), -((double)this.f_107231_), this.angularVelocity) / (double)20;
        if (this.f_107224_ >= this.f_107225_ || MoLangExtensionsKt.resolveBoolean(this.storm.getRuntime(), this.storm.getEffect().getParticle().getKillExpression())) {
            this.runExpirationEvents();
            this.m_107274_();
            return;
        }
        Vec3 velocity = this.storm.getEffect().getParticle().getMotion().getVelocity(this.storm.getRuntime(), this, new Vec3(this.f_107215_, this.f_107216_, this.f_107217_));
        this.f_107215_ = velocity.f_82479_;
        this.f_107216_ = velocity.f_82480_;
        this.f_107217_ = velocity.f_82481_;
        this.f_107204_ = this.f_107231_;
        this.f_107231_ = this.f_107204_ - (float)this.angularVelocity;
        ParticleViewDirection particleViewDirection = this.storm.getEffect().getParticle().getViewDirection();
        MoLangRuntime moLangRuntime = this.storm.getRuntime();
        Vec3 vec3 = this.viewDirection;
        Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"viewDirection");
        this.viewDirection = particleViewDirection.getDirection(moLangRuntime, vec3, new Vec3(this.f_107215_, this.f_107216_, this.f_107217_)).m_82541_();
        this.f_107209_ = this.f_107212_;
        this.f_107210_ = this.f_107213_;
        this.f_107211_ = this.f_107214_;
        this.prevLocalX = this.localX;
        this.prevLocalY = this.localY;
        this.prevLocalZ = this.localZ;
        this.oldAxisRotation = this.axisRotation;
        Vector3d vector3d = this.oldAxisRotation.transform(new Vector3d(this.prevLocalX, this.prevLocalY, this.prevLocalZ));
        Intrinsics.checkNotNullExpressionValue((Object)vector3d, (String)"oldAxisRotation.transfor\u2026 prevLocalY, prevLocalZ))");
        this.prevRotatedLocal = vector3d;
        this.storm.getMatrixWrapper().getMatrix().getRotation(this.axisRotation);
        Vector3d vector3d2 = this.axisRotation.transform(new Vector3d(this.prevLocalX, this.prevLocalY, this.prevLocalZ));
        Intrinsics.checkNotNullExpressionValue((Object)vector3d2, (String)"axisRotation.transform(V\u2026 prevLocalY, prevLocalZ))");
        this.rotatedLocal = vector3d2;
        new Quaterniond().rotateTo((Vector3dc)this.prevRotatedLocal, (Vector3dc)this.rotatedLocal).get(this.currentRotation);
        int n = this.f_107224_;
        this.f_107224_ = n + 1;
        this.m_6257_(this.f_107215_, this.f_107216_, this.f_107217_);
        this.storm.getEffect().getParticle().getTimeline().check(this.storm, this, (double)(this.f_107224_ - 1) / 20.0, (double)this.f_107224_ / 20.0);
    }

    public void m_6257_(double dx, double dy, double dz) {
        ParticleCollision collision = this.storm.getEffect().getParticle().getCollision();
        double radius = MoLangExtensionsKt.resolveDouble(this.storm.getRuntime(), collision.getRadius());
        this.m_107259_(AABB.m_165882_((Vec3)new Vec3(this.f_107212_, this.f_107213_, this.f_107214_), (double)radius, (double)radius, (double)radius));
        if (dx == 0.0 && dy == 0.0 && dz == 0.0) {
            this.updatePosition();
            return;
        }
        double dx2 = dx;
        double dy2 = dy;
        double dz2 = dz;
        if (MoLangExtensionsKt.resolveBoolean(this.storm.getRuntime(), collision.getEnabled()) && radius > 0.0 && !this.storm.getEffect().getSpace().isLocalSpace()) {
            this.f_107219_ = true;
            Vec3 newMovement = this.checkCollision(new Vec3(dx2, dy2, dz2));
            if (this.f_107220_) {
                return;
            }
            dx2 = newMovement.f_82479_;
            dy2 = newMovement.f_82480_;
            dz2 = newMovement.f_82481_;
            if (!(dx2 == 0.0 && dy2 == 0.0 && dz2 == 0.0)) {
                this.m_107259_(this.m_107277_().m_82386_(dx2, dy2, dz2));
                this.localX += dx2;
                this.localY += dy2;
                this.localZ += dz2;
            }
        } else {
            this.f_107219_ = false;
            if (!(dx2 == 0.0 && dy2 == 0.0 && dz2 == 0.0)) {
                this.localX += dx2;
                this.localY += dy2;
                this.localZ += dz2;
            }
        }
        this.updatePosition();
    }

    public final void updatePosition() {
        Vec3 localVector = this.storm.getEffect().getSpace().getLocalRotation() ? this.storm.transformDirection(new Vec3(this.localX, this.localY, this.localZ)) : new Vec3(this.localX, this.localY, this.localZ);
        this.f_107212_ = localVector.f_82479_ + this.originPos.f_82479_;
        this.f_107213_ = localVector.f_82480_ + this.originPos.f_82480_;
        this.f_107214_ = localVector.f_82481_ + this.originPos.f_82481_;
    }

    private final Vec3 checkCollision(Vec3 movement) {
        boolean mostlyIsZMovement;
        ParticleCollision collision = this.storm.getEffect().getParticle().getCollision();
        AABB box = this.m_107277_();
        double bounciness = MoLangExtensionsKt.resolveDouble(this.storm.getRuntime(), collision.getBounciness());
        double friction = MoLangExtensionsKt.resolveDouble(this.storm.getRuntime(), collision.getFriction());
        boolean expiresOnContact = collision.getExpiresOnContact();
        Iterable collisions = this.f_107208_.m_186434_(null, box.m_82369_(movement));
        Intrinsics.checkNotNullExpressionValue((Object)collisions, (String)"collisions");
        if (CollectionsKt.none((Iterable)collisions)) {
            this.colliding = false;
            return movement;
        }
        if (expiresOnContact) {
            this.runExpirationEvents();
            this.m_107274_();
            return movement;
        }
        double xMovement = movement.f_82479_;
        double yMovement = movement.f_82480_;
        double zMovement = movement.f_82481_;
        boolean bouncing = false;
        boolean sliding = false;
        if (!(yMovement == 0.0)) {
            if (!((yMovement = Shapes.m_193135_((Direction.Axis)Direction.Axis.Y, (AABB)box, (Iterable)collisions, (double)yMovement)) == 0.0)) {
                box = box.m_82386_(0.0, 0.0, zMovement);
            } else if (bounciness > 0.0 && Math.abs(movement.f_82480_) > 0.005) {
                this.f_107216_ *= (double)-1 * bounciness;
                yMovement = (double)-1 * bounciness * movement.f_82480_;
                bouncing = true;
            } else if (friction > 0.0) {
                sliding = true;
                this.f_107216_ = 0.0;
            } else {
                this.f_107216_ = 0.0;
            }
        }
        boolean bl = mostlyIsZMovement = Math.abs(xMovement) < Math.abs(zMovement);
        if (mostlyIsZMovement && !(zMovement == 0.0)) {
            if (!((zMovement = Shapes.m_193135_((Direction.Axis)Direction.Axis.Z, (AABB)box, (Iterable)collisions, (double)zMovement)) == 0.0)) {
                box = box.m_82386_(0.0, 0.0, zMovement);
            } else if (bounciness > 0.0 && Math.abs(movement.f_82481_) > 0.005) {
                this.f_107217_ *= (double)-1 * bounciness;
                zMovement = (double)-1 * bounciness * movement.f_82481_;
                bouncing = true;
            } else if (friction > 0.0) {
                sliding = true;
                this.f_107217_ = 0.0;
            } else {
                this.f_107217_ = 0.0;
            }
        }
        if (!(xMovement == 0.0)) {
            xMovement = Shapes.m_193135_((Direction.Axis)Direction.Axis.X, (AABB)box, (Iterable)collisions, (double)xMovement);
            if (!mostlyIsZMovement && !(xMovement == 0.0)) {
                box = box.m_82386_(xMovement, 0.0, 0.0);
            } else if (bounciness > 0.0 && Math.abs(movement.f_82479_) > 0.005) {
                this.f_107215_ *= (double)-1 * bounciness;
                xMovement = (double)-1 * bounciness * movement.f_82479_;
                bouncing = true;
            } else if (friction > 0.0) {
                sliding = true;
                this.f_107217_ = 0.0;
            } else {
                this.f_107217_ = 0.0;
            }
        }
        if (!mostlyIsZMovement && !(zMovement == 0.0) && (zMovement = Shapes.m_193135_((Direction.Axis)Direction.Axis.Z, (AABB)box, (Iterable)collisions, (double)zMovement)) == 0.0) {
            if (bounciness > 0.0 && Math.abs(movement.f_82481_) > 0.005) {
                this.f_107217_ *= (double)-1 * bounciness;
                zMovement = (double)-1 * bounciness * movement.f_82481_;
                bouncing = true;
            } else if (friction > 0.0) {
                sliding = true;
                this.f_107217_ = 0.0;
            } else {
                this.f_107217_ = 0.0;
            }
        }
        Vec3 newMovement = new Vec3(xMovement, yMovement, zMovement);
        if (sliding && !bouncing) {
            Vec3 vec3;
            if (newMovement.m_82553_() * (double)20 < friction) {
                Vec3 vec32 = Vec3.f_82478_;
                vec3 = vec32;
                Intrinsics.checkNotNullExpressionValue((Object)vec32, (String)"{\n                Vec3d.ZERO\n            }");
            } else {
                Vec3 vec33 = newMovement.m_82546_(newMovement.m_82541_().m_82490_(friction / (double)20));
                vec3 = vec33;
                Intrinsics.checkNotNullExpressionValue((Object)vec33, (String)"{\n                newMov\u2026tion / 20))\n            }");
            }
            newMovement = vec3;
            Vec3 velocity = new Vec3(this.f_107215_, this.f_107216_, this.f_107217_);
            if (velocity.m_82553_() * (double)20 < friction) {
                this.m_172260_(0.0, 0.0, 0.0);
            } else {
                Vec3 vec34 = velocity.m_82546_(velocity.m_82541_().m_82490_(friction / (double)20));
                Intrinsics.checkNotNullExpressionValue((Object)vec34, (String)"velocity.subtract(veloci\u2026.multiply(friction / 20))");
                velocity = vec34;
                this.m_172260_(velocity.f_82479_, velocity.f_82480_, velocity.f_82481_);
            }
        }
        return newMovement;
    }

    private final void setParticleAgeInRuntime() {
        this.variableStruct.setDirectly("particle_age", new DoubleValue((double)this.f_107224_ / 20.0));
        this.variableStruct.setDirectly("particle_lifetime", new DoubleValue((double)this.f_107225_ / 20.0));
    }

    @NotNull
    public ParticleRenderType m_7556_() {
        return this.particleTextureSheet;
    }

    public void m_107274_() {
        super.m_107274_();
        this.storm.getParticles().remove((Object)this);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0006\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0007"}, d2={"Lcom/cobblemon/mod/common/client/render/SnowstormParticle$Companion;", "", "", "MAXIMUM_DISTANCE_CHANGE_PER_TICK_FOR_FRICTION", "D", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[ParticleMaterial.values().length];
            try {
                nArray[ParticleMaterial.ALPHA.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[ParticleMaterial.OPAQUE.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[ParticleMaterial.BLEND.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[ParticleMaterial.ADD.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

