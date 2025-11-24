/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockAnimation;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\n\b\u0086\b\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\t\u001a\u00020\u0002\u0012\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u001c\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0007\u0010\bJ0\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\t\u001a\u00020\u00022\u0014\b\u0002\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0001\u00a2\u0006\u0004\b\u000b\u0010\fJ\u001a\u0010\u000f\u001a\u00020\u000e2\b\u0010\r\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0010\u0010\u0012\u001a\u00020\u0011H\u00d6\u0001\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0010\u0010\u0014\u001a\u00020\u0002H\u00d6\u0001\u00a2\u0006\u0004\b\u0014\u0010\u0004R#\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u00058\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u0015\u001a\u0004\b\u0016\u0010\bR\u0017\u0010\t\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\u0017\u001a\u0004\b\u0018\u0010\u0004\u00a8\u0006\u001b"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimationGroup;", "", "", "component1", "()Ljava/lang/String;", "", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimation;", "component2", "()Ljava/util/Map;", "formatVersion", "animations", "copy", "(Ljava/lang/String;Ljava/util/Map;)Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimationGroup;", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "toString", "Ljava/util/Map;", "getAnimations", "Ljava/lang/String;", "getFormatVersion", "<init>", "(Ljava/lang/String;Ljava/util/Map;)V", "common"})
public final class BedrockAnimationGroup {
    @NotNull
    private final String formatVersion;
    @NotNull
    private final Map<String, BedrockAnimation> animations;

    public BedrockAnimationGroup(@NotNull String formatVersion, @NotNull Map<String, BedrockAnimation> animations2) {
        Intrinsics.checkNotNullParameter((Object)formatVersion, (String)"formatVersion");
        Intrinsics.checkNotNullParameter(animations2, (String)"animations");
        this.formatVersion = formatVersion;
        this.animations = animations2;
    }

    @NotNull
    public final String getFormatVersion() {
        return this.formatVersion;
    }

    @NotNull
    public final Map<String, BedrockAnimation> getAnimations() {
        return this.animations;
    }

    @NotNull
    public final String component1() {
        return this.formatVersion;
    }

    @NotNull
    public final Map<String, BedrockAnimation> component2() {
        return this.animations;
    }

    @NotNull
    public final BedrockAnimationGroup copy(@NotNull String formatVersion, @NotNull Map<String, BedrockAnimation> animations2) {
        Intrinsics.checkNotNullParameter((Object)formatVersion, (String)"formatVersion");
        Intrinsics.checkNotNullParameter(animations2, (String)"animations");
        return new BedrockAnimationGroup(formatVersion, animations2);
    }

    public static /* synthetic */ BedrockAnimationGroup copy$default(BedrockAnimationGroup bedrockAnimationGroup, String string, Map map, int n, Object object) {
        if ((n & 1) != 0) {
            string = bedrockAnimationGroup.formatVersion;
        }
        if ((n & 2) != 0) {
            map = bedrockAnimationGroup.animations;
        }
        return bedrockAnimationGroup.copy(string, map);
    }

    @NotNull
    public String toString() {
        return "BedrockAnimationGroup(formatVersion=" + this.formatVersion + ", animations=" + this.animations + ")";
    }

    public int hashCode() {
        int result = this.formatVersion.hashCode();
        result = result * 31 + ((Object)this.animations).hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BedrockAnimationGroup)) {
            return false;
        }
        BedrockAnimationGroup bedrockAnimationGroup = (BedrockAnimationGroup)other;
        if (!Intrinsics.areEqual((Object)this.formatVersion, (Object)bedrockAnimationGroup.formatVersion)) {
            return false;
        }
        return Intrinsics.areEqual(this.animations, bedrockAnimationGroup.animations);
    }
}

