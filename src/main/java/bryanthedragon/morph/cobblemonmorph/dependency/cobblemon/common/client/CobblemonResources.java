/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import kotlin.Metadata;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\r\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\r\u0010\u000eR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\u0004\u001a\u0004\b\b\u0010\u0006R\u0017\u0010\t\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\u0004\u001a\u0004\b\n\u0010\u0006R\u0017\u0010\u000b\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\u0004\u001a\u0004\b\f\u0010\u0006\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/client/CobblemonResources;", "", "Lnet/minecraft/resources/ResourceLocation;", "DEFAULT_LARGE", "Lnet/minecraft/resources/ResourceLocation;", "getDEFAULT_LARGE", "()Lnet/minecraft/resources/ResourceLocation;", "PHASE_BEAM", "getPHASE_BEAM", "RED", "getRED", "WHITE", "getWHITE", "<init>", "()V", "common"})
public final class CobblemonResources {
    @NotNull
    public static final CobblemonResources INSTANCE = new CobblemonResources();
    @NotNull
    private static final ResourceLocation RED = MiscUtils.cobblemonResource("textures/red.png");
    @NotNull
    private static final ResourceLocation WHITE = MiscUtils.cobblemonResource("textures/white.png");
    @NotNull
    private static final ResourceLocation PHASE_BEAM = MiscUtils.cobblemonResource("textures/phase_beam.png");
    @NotNull
    private static final ResourceLocation DEFAULT_LARGE = new ResourceLocation("uniform");

    private CobblemonResources() {
    }

    @NotNull
    public final ResourceLocation getRED() {
        return RED;
    }

    @NotNull
    public final ResourceLocation getWHITE() {
        return WHITE;
    }

    @NotNull
    public final ResourceLocation getPHASE_BEAM() {
        return PHASE_BEAM;
    }

    @NotNull
    public final ResourceLocation getDEFAULT_LARGE() {
        return DEFAULT_LARGE;
    }
}

