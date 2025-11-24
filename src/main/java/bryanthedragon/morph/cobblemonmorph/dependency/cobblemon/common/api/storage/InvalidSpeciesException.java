/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00060\u0001j\u0002`\u0002B\u000f\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0004\b\b\u0010\tR\u0017\u0010\u0004\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/storage/InvalidSpeciesException;", "Ljava/lang/IllegalStateException;", "Lkotlin/IllegalStateException;", "Lnet/minecraft/resources/ResourceLocation;", "identifier", "Lnet/minecraft/resources/ResourceLocation;", "getIdentifier", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "(Lnet/minecraft/resources/ResourceLocation;)V", "common"})
public final class InvalidSpeciesException
extends IllegalStateException {
    @NotNull
    private final ResourceLocation identifier;

    public InvalidSpeciesException(@NotNull ResourceLocation identifier) {
        Intrinsics.checkNotNullParameter((Object)identifier, (String)"identifier");
        super("Invalid species: " + identifier);
        this.identifier = identifier;
    }

    @NotNull
    public final ResourceLocation getIdentifier() {
        return this.identifier;
    }
}

