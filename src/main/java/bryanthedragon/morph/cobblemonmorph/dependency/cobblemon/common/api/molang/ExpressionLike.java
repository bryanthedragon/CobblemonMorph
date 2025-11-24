/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.MoValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ObjectValue;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\b\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ\u0017\u0010\u000b\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0017\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0017\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u001b\u0010\u0014\u001a\u0006\u0012\u0002\b\u00030\u00132\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u001f\u0010\u0018\u001a\n \u0017*\u0004\u0018\u00010\u00160\u00162\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0018\u0010\u0019\u00a8\u0006\u001a"}, d2={"Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "Lcom/bedrockk/molang/runtime/value/MoValue;", "resolve", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;)Lcom/bedrockk/molang/runtime/value/MoValue;", "", "resolveBoolean", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;)Z", "", "resolveDouble", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;)D", "", "resolveFloat", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;)F", "", "resolveInt", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;)I", "Lcom/cobblemon/mod/common/api/molang/ObjectValue;", "resolveObject", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;)Lcom/cobblemon/mod/common/api/molang/ObjectValue;", "", "kotlin.jvm.PlatformType", "resolveString", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;)Ljava/lang/String;", "common"})
public interface ExpressionLike {
    @NotNull
    public MoValue resolve(@NotNull MoLangRuntime var1);

    public double resolveDouble(@NotNull MoLangRuntime var1);

    public float resolveFloat(@NotNull MoLangRuntime var1);

    public String resolveString(@NotNull MoLangRuntime var1);

    public int resolveInt(@NotNull MoLangRuntime var1);

    public boolean resolveBoolean(@NotNull MoLangRuntime var1);

    @NotNull
    public ObjectValue<?> resolveObject(@NotNull MoLangRuntime var1);

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        public static double resolveDouble(@NotNull ExpressionLike $this, @NotNull MoLangRuntime runtime2) {
            Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
            return $this.resolve(runtime2).asDouble();
        }

        public static float resolveFloat(@NotNull ExpressionLike $this, @NotNull MoLangRuntime runtime2) {
            Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
            return (float)$this.resolveDouble(runtime2);
        }

        public static String resolveString(@NotNull ExpressionLike $this, @NotNull MoLangRuntime runtime2) {
            Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
            return $this.resolve(runtime2).asString();
        }

        public static int resolveInt(@NotNull ExpressionLike $this, @NotNull MoLangRuntime runtime2) {
            Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
            return (int)$this.resolveDouble(runtime2);
        }

        public static boolean resolveBoolean(@NotNull ExpressionLike $this, @NotNull MoLangRuntime runtime2) {
            Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
            return $this.resolveDouble(runtime2) == 1.0;
        }

        @NotNull
        public static ObjectValue<?> resolveObject(@NotNull ExpressionLike $this, @NotNull MoLangRuntime runtime2) {
            Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
            MoValue moValue = $this.resolve(runtime2);
            Intrinsics.checkNotNull((Object)moValue, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ObjectValue<*>");
            return (ObjectValue)moValue;
        }
    }
}

