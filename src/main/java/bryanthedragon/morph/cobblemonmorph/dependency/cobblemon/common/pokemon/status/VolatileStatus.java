/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Status;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0016\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0004\u0012\u0006\u0010\u0007\u001a\u00020\u0004\u00a2\u0006\u0004\b\b\u0010\t\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/pokemon/status/VolatileStatus;", "Lcom/cobblemon/mod/common/api/pokemon/status/Status;", "Lnet/minecraft/resources/ResourceLocation;", "name", "", "showdownName", "applyMessage", "removeMessage", "<init>", "(Lnet/minecraft/resources/ResourceLocation;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "common"})
public class VolatileStatus
extends Status {
    public VolatileStatus(@NotNull ResourceLocation name, @NotNull String showdownName, @NotNull String applyMessage, @NotNull String removeMessage) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)showdownName, (String)"showdownName");
        Intrinsics.checkNotNullParameter((Object)applyMessage, (String)"applyMessage");
        Intrinsics.checkNotNullParameter((Object)removeMessage, (String)"removeMessage");
        super(name, showdownName, applyMessage, removeMessage);
    }
}

