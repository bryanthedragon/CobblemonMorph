/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.component.SpawnRuleComponent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u001b\u0010\u001cR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\t\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\fR\"\u0010\u000e\u001a\u00020\r8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\"\u0010\u0015\u001a\u00020\u00148\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001a\u00a8\u0006\u001d"}, d2={"Lcom/cobblemon/mod/common/api/spawning/rules/SpawnRule;", "", "", "Lcom/cobblemon/mod/common/api/spawning/rules/component/SpawnRuleComponent;", "components", "Ljava/util/List;", "getComponents", "()Ljava/util/List;", "Lnet/minecraft/network/chat/Component;", "displayName", "Lnet/minecraft/network/chat/Component;", "getDisplayName", "()Lnet/minecraft/network/chat/Component;", "", "enabled", "Z", "getEnabled", "()Z", "setEnabled", "(Z)V", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "setId", "(Lnet/minecraft/resources/ResourceLocation;)V", "<init>", "()V", "common"})
public final class SpawnRule {
    public ResourceLocation id;
    @NotNull
    private final Component displayName = (Component)TextKt.text("Spawn Rule");
    private boolean enabled = true;
    @NotNull
    private final List<SpawnRuleComponent> components = new ArrayList();

    @NotNull
    public final ResourceLocation getId() {
        ResourceLocation resourceLocation = this.id;
        if (resourceLocation != null) {
            return resourceLocation;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"id");
        return null;
    }

    public final void setId(@NotNull ResourceLocation resourceLocation) {
        Intrinsics.checkNotNullParameter((Object)resourceLocation, (String)"<set-?>");
        this.id = resourceLocation;
    }

    @NotNull
    public final Component getDisplayName() {
        return this.displayName;
    }

    public final boolean getEnabled() {
        return this.enabled;
    }

    public final void setEnabled(boolean bl) {
        this.enabled = bl;
    }

    @NotNull
    public final List<SpawnRuleComponent> getComponents() {
        return this.components;
    }
}

