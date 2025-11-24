/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u00002\u00020\u0001B7\u0012\u0006\u0010\n\u001a\u00020\t\u0012\u0006\u0010\u0010\u001a\u00020\u0002\u0012\u0006\u0010\u0007\u001a\u00020\u0002\u0012\u0006\u0010\u0012\u001a\u00020\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u000e\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\u0004\u001a\u0004\b\b\u0010\u0006R\u0017\u0010\n\u001a\u00020\t8\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\rR\u0017\u0010\u000e\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u0004\u001a\u0004\b\u000f\u0010\u0006R\u0017\u0010\u0010\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0004\u001a\u0004\b\u0011\u0010\u0006R\u0017\u0010\u0012\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0004\u001a\u0004\b\u0013\u0010\u0006\u00a8\u0006\u0016"}, d2={"Lcom/cobblemon/mod/common/api/item/Berry;", "", "", "bitter", "I", "getBitter", "()I", "dry", "getDry", "Lnet/minecraft/resources/ResourceLocation;", "name", "Lnet/minecraft/resources/ResourceLocation;", "getName", "()Lnet/minecraft/resources/ResourceLocation;", "sour", "getSour", "spicy", "getSpicy", "sweet", "getSweet", "<init>", "(Lnet/minecraft/resources/ResourceLocation;IIIII)V", "common"})
public final class Berry {
    @NotNull
    private final ResourceLocation name;
    private final int spicy;
    private final int dry;
    private final int sweet;
    private final int bitter;
    private final int sour;

    public Berry(@NotNull ResourceLocation name, int spicy, int dry, int sweet, int bitter, int sour) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        this.name = name;
        this.spicy = spicy;
        this.dry = dry;
        this.sweet = sweet;
        this.bitter = bitter;
        this.sour = sour;
    }

    @NotNull
    public final ResourceLocation getName() {
        return this.name;
    }

    public final int getSpicy() {
        return this.spicy;
    }

    public final int getDry() {
        return this.dry;
    }

    public final int getSweet() {
        return this.sweet;
    }

    public final int getBitter() {
        return this.bitter;
    }

    public final int getSour() {
        return this.sour;
    }
}

