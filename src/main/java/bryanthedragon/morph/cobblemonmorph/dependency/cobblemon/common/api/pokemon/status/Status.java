/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectTimeline;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0016\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u000f\u001a\u00020\u0005\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u000f\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004R\u0017\u0010\u0006\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\u0006\u0010\u0007\u001a\u0004\b\b\u0010\tR\u0017\u0010\u000b\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\u000f\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0007\u001a\u0004\b\u0010\u0010\tR\u0017\u0010\u0011\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0007\u001a\u0004\b\u0012\u0010\t\u00a8\u0006\u0015"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/status/Status;", "", "Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectTimeline;", "getActionEffect", "()Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectTimeline;", "", "applyMessage", "Ljava/lang/String;", "getApplyMessage", "()Ljava/lang/String;", "Lnet/minecraft/resources/ResourceLocation;", "name", "Lnet/minecraft/resources/ResourceLocation;", "getName", "()Lnet/minecraft/resources/ResourceLocation;", "removeMessage", "getRemoveMessage", "showdownName", "getShowdownName", "<init>", "(Lnet/minecraft/resources/ResourceLocation;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "common"})
public class Status {
    @NotNull
    private final ResourceLocation name;
    @NotNull
    private final String showdownName;
    @NotNull
    private final String applyMessage;
    @NotNull
    private final String removeMessage;

    public Status(@NotNull ResourceLocation name, @NotNull String showdownName, @NotNull String applyMessage, @NotNull String removeMessage) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)showdownName, (String)"showdownName");
        Intrinsics.checkNotNullParameter((Object)applyMessage, (String)"applyMessage");
        Intrinsics.checkNotNullParameter((Object)removeMessage, (String)"removeMessage");
        this.name = name;
        this.showdownName = showdownName;
        this.applyMessage = applyMessage;
        this.removeMessage = removeMessage;
    }

    public /* synthetic */ Status(ResourceLocation resourceLocation, String string, String string2, String string3, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            string = "";
        }
        this(resourceLocation, string, string2, string3);
    }

    @NotNull
    public final ResourceLocation getName() {
        return this.name;
    }

    @NotNull
    public final String getShowdownName() {
        return this.showdownName;
    }

    @NotNull
    public final String getApplyMessage() {
        return this.applyMessage;
    }

    @NotNull
    public final String getRemoveMessage() {
        return this.removeMessage;
    }

    @Nullable
    public final ActionEffectTimeline getActionEffect() {
        return ActionEffects.INSTANCE.getActionEffects().get(this.name);
    }
}

