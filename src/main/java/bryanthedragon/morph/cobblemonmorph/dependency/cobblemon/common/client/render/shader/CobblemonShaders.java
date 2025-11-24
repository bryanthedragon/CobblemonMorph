/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.renderer.ShaderInstance
 *  net.minecraft.server.packs.resources.ResourceProvider
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.shader;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.shader.CobblemonShaders;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ShaderRegistryData;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.server.packs.resources.ResourceProvider;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u001d\u0010\u0004J\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004J1\u0010\f\u001a\u00020\u00022\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u00052\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0002\u00a2\u0006\u0004\b\f\u0010\rR\"\u0010\u000e\u001a\u00020\n8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\"\u0010\u0014\u001a\u00020\n8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u0014\u0010\u000f\u001a\u0004\b\u0015\u0010\u0011\"\u0004\b\u0016\u0010\u0013R;\u0010\u0019\u001a&\u0012\"\u0012 \u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u00180\u00178\u0006\u00a2\u0006\f\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u001b\u0010\u001c\u00a8\u0006\u001e"}, d2={"Lcom/cobblemon/mod/common/client/render/shader/CobblemonShaders;", "", "", "init", "()V", "Lkotlin/Function1;", "Lnet/minecraft/server/packs/resources/ResourceProvider;", "Lcom/cobblemon/mod/common/util/ShaderRegistryData;", "shader", "Ljava/util/function/Consumer;", "Lnet/minecraft/client/renderer/ShaderInstance;", "callback", "registerShader", "(Lkotlin/jvm/functions/Function1;Ljava/util/function/Consumer;)V", "PARTICLE_BLEND", "Lnet/minecraft/client/renderer/ShaderInstance;", "getPARTICLE_BLEND", "()Lnet/minecraft/client/renderer/ShaderInstance;", "setPARTICLE_BLEND", "(Lnet/minecraft/client/renderer/ShaderInstance;)V", "PARTICLE_CUTOUT", "getPARTICLE_CUTOUT", "setPARTICLE_CUTOUT", "", "Lkotlin/Pair;", "SHADERS_TO_REGISTER", "Ljava/util/List;", "getSHADERS_TO_REGISTER", "()Ljava/util/List;", "<init>", "common"})
public final class CobblemonShaders {
    @NotNull
    public static final CobblemonShaders INSTANCE = new CobblemonShaders();
    @NotNull
    private static final List<Pair<Function1<ResourceProvider, ShaderRegistryData>, Consumer<ShaderInstance>>> SHADERS_TO_REGISTER = new ArrayList();
    public static ShaderInstance PARTICLE_BLEND;
    public static ShaderInstance PARTICLE_CUTOUT;

    private CobblemonShaders() {
    }

    @NotNull
    public final List<Pair<Function1<ResourceProvider, ShaderRegistryData>, Consumer<ShaderInstance>>> getSHADERS_TO_REGISTER() {
        return SHADERS_TO_REGISTER;
    }

    @NotNull
    public final ShaderInstance getPARTICLE_BLEND() {
        ShaderInstance shaderInstance = PARTICLE_BLEND;
        if (shaderInstance != null) {
            return shaderInstance;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"PARTICLE_BLEND");
        return null;
    }

    public final void setPARTICLE_BLEND(@NotNull ShaderInstance shaderInstance) {
        Intrinsics.checkNotNullParameter((Object)shaderInstance, (String)"<set-?>");
        PARTICLE_BLEND = shaderInstance;
    }

    @NotNull
    public final ShaderInstance getPARTICLE_CUTOUT() {
        ShaderInstance shaderInstance = PARTICLE_CUTOUT;
        if (shaderInstance != null) {
            return shaderInstance;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"PARTICLE_CUTOUT");
        return null;
    }

    public final void setPARTICLE_CUTOUT(@NotNull ShaderInstance shaderInstance) {
        Intrinsics.checkNotNullParameter((Object)shaderInstance, (String)"<set-?>");
        PARTICLE_CUTOUT = shaderInstance;
    }

    private final void registerShader(Function1<? super ResourceProvider, ShaderRegistryData> shader, Consumer<ShaderInstance> callback) {
        SHADERS_TO_REGISTER.add((Pair<Function1<ResourceProvider, ShaderRegistryData>, Consumer<ShaderInstance>>)new Pair(shader, callback));
    }

    public final void init() {
        this.registerShader((Function1<? super ResourceProvider, ShaderRegistryData>)((Function1)init.1.INSTANCE), CobblemonShaders::init$lambda$0);
        this.registerShader((Function1<? super ResourceProvider, ShaderRegistryData>)((Function1)init.3.INSTANCE), CobblemonShaders::init$lambda$1);
    }

    private static final void init$lambda$0(ShaderInstance it) {
        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
        INSTANCE.setPARTICLE_BLEND(it);
    }

    private static final void init$lambda$1(ShaderInstance it) {
        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
        INSTANCE.setPARTICLE_CUTOUT(it);
    }
}

