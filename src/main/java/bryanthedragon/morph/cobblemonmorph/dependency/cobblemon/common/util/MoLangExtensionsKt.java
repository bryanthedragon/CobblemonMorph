/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Triple
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.MoLang;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.NumberExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangEnvironment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoParams;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoScope;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.VariableStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.MoValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ListExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ObjectValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.SingleExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.List;
import kotlin.Metadata;
import kotlin.Triple;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\u0088\u0001\n\u0002\u0010\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0007\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\b\u001a\u0011\u0010\u0002\u001a\u00020\u0001*\u00020\u0000\u00a2\u0006\u0004\b\u0002\u0010\u0003\u001a\u0019\u0010\u0002\u001a\n \u0006*\u0004\u0018\u00010\u00050\u0005*\u00020\u0004\u00a2\u0006\u0004\b\u0002\u0010\u0007\u001a\u0011\u0010\t\u001a\u00020\b*\u00020\u0004\u00a2\u0006\u0004\b\t\u0010\n\u001a\u0017\u0010\t\u001a\u00020\b*\b\u0012\u0004\u0012\u00020\u00040\u000b\u00a2\u0006\u0004\b\t\u0010\f\u001a5\u0010\u000e\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u000b0\r*\u00020\u0004\u00a2\u0006\u0004\b\u000e\u0010\u000f\u001a\u001b\u0010\u0014\u001a\u0004\u0018\u00010\u0013*\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0011\u00a2\u0006\u0004\b\u0014\u0010\u0015\u001a\u001b\u0010\u0016\u001a\u0004\u0018\u00010\u0000*\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0011\u00a2\u0006\u0004\b\u0016\u0010\u0017\u001a\u0011\u0010\u0018\u001a\u00020\u0004*\u00020\u0005\u00a2\u0006\u0004\b\u0018\u0010\u0019\u001a\u001b\u0010\u001a\u001a\u0004\u0018\u00010\u0004*\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0011\u00a2\u0006\u0004\b\u001a\u0010\u001b\u001a\u0019\u0010\u001f\u001a\u00020\u001e*\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0005\u00a2\u0006\u0004\b\u001f\u0010 \u001a\u0019\u0010\u001f\u001a\u00020\u001e*\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\b\u00a2\u0006\u0004\b\u001f\u0010!\u001a'\u0010\u001f\u001a\n \u0006*\u0004\u0018\u00010\u001e0\u001e*\b\u0012\u0004\u0012\u00020\u00050\u000b2\u0006\u0010\"\u001a\u00020\u001c\u00a2\u0006\u0004\b\u001f\u0010#\u001a\u0019\u0010$\u001a\u00020\u0013*\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0005\u00a2\u0006\u0004\b$\u0010%\u001a!\u0010$\u001a\u00020\u0013*\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u00052\u0006\u0010'\u001a\u00020&\u00a2\u0006\u0004\b$\u0010(\u001a!\u0010$\u001a\u00020\u0013*\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u00052\u0006\u0010'\u001a\u00020)\u00a2\u0006\u0004\b$\u0010*\u001a\u0019\u0010$\u001a\u00020\u0013*\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\b\u00a2\u0006\u0004\b$\u0010+\u001a\u001f\u0010$\u001a\u00020\u0013*\b\u0012\u0004\u0012\u00020\u00050\u000b2\u0006\u0010\"\u001a\u00020\u001c\u00a2\u0006\u0004\b$\u0010,\u001a\u0019\u0010-\u001a\u00020\u0000*\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0005\u00a2\u0006\u0004\b-\u0010.\u001a!\u0010-\u001a\u00020\u0000*\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u00052\u0006\u0010'\u001a\u00020&\u00a2\u0006\u0004\b-\u0010/\u001a!\u0010-\u001a\u00020\u0000*\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u00052\u0006\u0010'\u001a\u00020)\u00a2\u0006\u0004\b-\u00100\u001a\u0019\u0010-\u001a\u00020\u0000*\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\b\u00a2\u0006\u0004\b-\u00101\u001a\u001f\u0010-\u001a\u00020\u0000*\b\u0012\u0004\u0012\u00020\u00050\u000b2\u0006\u0010\"\u001a\u00020\u001c\u00a2\u0006\u0004\b-\u00102\u001a\u0019\u00104\u001a\u000203*\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0005\u00a2\u0006\u0004\b4\u00105\u001a!\u00104\u001a\u000203*\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u00052\u0006\u0010'\u001a\u00020&\u00a2\u0006\u0004\b4\u00106\u001a!\u00104\u001a\u000203*\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u00052\u0006\u0010'\u001a\u00020)\u00a2\u0006\u0004\b4\u00107\u001a\u0019\u00104\u001a\u000203*\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\b\u00a2\u0006\u0004\b4\u00108\u001a\u0019\u00109\u001a\u00020\u0011*\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0005\u00a2\u0006\u0004\b9\u0010:\u001a!\u00109\u001a\u00020\u0011*\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u00052\u0006\u0010'\u001a\u00020&\u00a2\u0006\u0004\b9\u0010;\u001a!\u00109\u001a\u00020\u0011*\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u00052\u0006\u0010'\u001a\u00020)\u00a2\u0006\u0004\b9\u0010<\u001a\u0019\u00109\u001a\u00020\u0011*\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\b\u00a2\u0006\u0004\b9\u0010=\u001a\u001f\u00109\u001a\u00020\u0011*\b\u0012\u0004\u0012\u00020\u00050\u000b2\u0006\u0010\"\u001a\u00020\u001c\u00a2\u0006\u0004\b9\u0010>\u001a\u001d\u0010@\u001a\u0006\u0012\u0002\b\u00030?*\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0005\u00a2\u0006\u0004\b@\u0010A\u001a\u001d\u0010@\u001a\u0006\u0012\u0002\b\u00030?*\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\b\u00a2\u0006\u0004\b@\u0010B\u001a#\u0010@\u001a\u0006\u0012\u0002\b\u00030?*\b\u0012\u0004\u0012\u00020\u00050\u000b2\u0006\u0010\"\u001a\u00020\u001c\u00a2\u0006\u0004\b@\u0010C\u001a\u0019\u0010D\u001a\u00020\u0004*\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0005\u00a2\u0006\u0004\bD\u0010E\u001a\u0019\u0010D\u001a\u00020\u0004*\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\b\u00a2\u0006\u0004\bD\u0010F\u001a+\u0010J\u001a\u00020I*\u00020\u001c2\u0018\u0010H\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050G\u00a2\u0006\u0004\bJ\u0010K\u001a\u0019\u0010N\u001a\u00020M*\u00020L2\u0006\u0010'\u001a\u00020&\u00a2\u0006\u0004\bN\u0010O\u001a\u0019\u0010N\u001a\u00020M*\u00020L2\u0006\u0010'\u001a\u00020)\u00a2\u0006\u0004\bN\u0010P\"\u0017\u0010Q\u001a\u00020\u001c8\u0006\u00a2\u0006\f\n\u0004\bQ\u0010R\u001a\u0004\bS\u0010T\u00a8\u0006U"}, d2={"", "Lcom/bedrockk/molang/ast/NumberExpression;", "asExpression", "(D)Lcom/bedrockk/molang/ast/NumberExpression;", "", "Lcom/bedrockk/molang/Expression;", "kotlin.jvm.PlatformType", "(Ljava/lang/String;)Lcom/bedrockk/molang/Expression;", "Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "asExpressionLike", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "", "(Ljava/util/List;)Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "", "asExpressions", "(Ljava/lang/String;)Ljava/util/List;", "Lcom/bedrockk/molang/runtime/MoParams;", "", "index", "", "getBooleanOrNull", "(Lcom/bedrockk/molang/runtime/MoParams;I)Ljava/lang/Boolean;", "getDoubleOrNull", "(Lcom/bedrockk/molang/runtime/MoParams;I)Ljava/lang/Double;", "getString", "(Lcom/bedrockk/molang/Expression;)Ljava/lang/String;", "getStringOrNull", "(Lcom/bedrockk/molang/runtime/MoParams;I)Ljava/lang/String;", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "expression", "Lcom/bedrockk/molang/runtime/value/MoValue;", "resolve", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lcom/bedrockk/molang/Expression;)Lcom/bedrockk/molang/runtime/value/MoValue;", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lcom/cobblemon/mod/common/api/molang/ExpressionLike;)Lcom/bedrockk/molang/runtime/value/MoValue;", "runtime", "(Ljava/util/List;Lcom/bedrockk/molang/runtime/MoLangRuntime;)Lcom/bedrockk/molang/runtime/value/MoValue;", "resolveBoolean", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lcom/bedrockk/molang/Expression;)Z", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "pokemon", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lcom/bedrockk/molang/Expression;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;)Z", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lcom/bedrockk/molang/Expression;Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lcom/cobblemon/mod/common/api/molang/ExpressionLike;)Z", "(Ljava/util/List;Lcom/bedrockk/molang/runtime/MoLangRuntime;)Z", "resolveDouble", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lcom/bedrockk/molang/Expression;)D", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lcom/bedrockk/molang/Expression;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;)D", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lcom/bedrockk/molang/Expression;Lcom/cobblemon/mod/common/pokemon/Pokemon;)D", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lcom/cobblemon/mod/common/api/molang/ExpressionLike;)D", "(Ljava/util/List;Lcom/bedrockk/molang/runtime/MoLangRuntime;)D", "", "resolveFloat", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lcom/bedrockk/molang/Expression;)F", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lcom/bedrockk/molang/Expression;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;)F", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lcom/bedrockk/molang/Expression;Lcom/cobblemon/mod/common/pokemon/Pokemon;)F", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lcom/cobblemon/mod/common/api/molang/ExpressionLike;)F", "resolveInt", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lcom/bedrockk/molang/Expression;)I", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lcom/bedrockk/molang/Expression;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;)I", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lcom/bedrockk/molang/Expression;Lcom/cobblemon/mod/common/pokemon/Pokemon;)I", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lcom/cobblemon/mod/common/api/molang/ExpressionLike;)I", "(Ljava/util/List;Lcom/bedrockk/molang/runtime/MoLangRuntime;)I", "Lcom/cobblemon/mod/common/api/molang/ObjectValue;", "resolveObject", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lcom/bedrockk/molang/Expression;)Lcom/cobblemon/mod/common/api/molang/ObjectValue;", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lcom/cobblemon/mod/common/api/molang/ExpressionLike;)Lcom/cobblemon/mod/common/api/molang/ObjectValue;", "(Ljava/util/List;Lcom/bedrockk/molang/runtime/MoLangRuntime;)Lcom/cobblemon/mod/common/api/molang/ObjectValue;", "resolveString", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lcom/bedrockk/molang/Expression;)Ljava/lang/String;", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lcom/cobblemon/mod/common/api/molang/ExpressionLike;)Ljava/lang/String;", "Lkotlin/Triple;", "triple", "Lnet/minecraft/world/phys/Vec3;", "resolveVec3d", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lkotlin/Triple;)Lnet/minecraft/world/phys/Vec3;", "Lcom/bedrockk/molang/runtime/MoLangEnvironment;", "", "writePokemon", "(Lcom/bedrockk/molang/runtime/MoLangEnvironment;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;)V", "(Lcom/bedrockk/molang/runtime/MoLangEnvironment;Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "genericRuntime", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "getGenericRuntime", "()Lcom/bedrockk/molang/runtime/MoLangRuntime;", "common"})
public final class MoLangExtensionsKt {
    @NotNull
    private static final MoLangRuntime genericRuntime = MoLangFunctions.INSTANCE.setup(new MoLangRuntime());

    @NotNull
    public static final MoLangRuntime getGenericRuntime() {
        return genericRuntime;
    }

    @NotNull
    public static final MoValue resolve(@NotNull MoLangRuntime $this$resolve, @NotNull Expression expression) {
        MoValue moValue;
        Intrinsics.checkNotNullParameter((Object)$this$resolve, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)expression, (String)"expression");
        try {
            MoValue moValue2 = expression.evaluate(new MoScope(), $this$resolve.getEnvironment());
            Intrinsics.checkNotNullExpressionValue((Object)moValue2, (String)"{\n    expression.evaluat\u2026MoScope(), environment)\n}");
            moValue = moValue2;
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Unable to parse expression: " + MoLangExtensionsKt.getString(expression), e);
        }
        return moValue;
    }

    public static final double resolveDouble(@NotNull MoLangRuntime $this$resolveDouble, @NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)$this$resolveDouble, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)expression, (String)"expression");
        return MoLangExtensionsKt.resolve($this$resolveDouble, expression).asDouble();
    }

    public static final float resolveFloat(@NotNull MoLangRuntime $this$resolveFloat, @NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)$this$resolveFloat, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)expression, (String)"expression");
        return (float)MoLangExtensionsKt.resolve($this$resolveFloat, expression).asDouble();
    }

    public static final int resolveInt(@NotNull MoLangRuntime $this$resolveInt, @NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)$this$resolveInt, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)expression, (String)"expression");
        return (int)MoLangExtensionsKt.resolveDouble($this$resolveInt, expression);
    }

    @NotNull
    public static final String resolveString(@NotNull MoLangRuntime $this$resolveString, @NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)$this$resolveString, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)expression, (String)"expression");
        String string = MoLangExtensionsKt.resolve($this$resolveString, expression).asString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"resolve(expression).asString()");
        return string;
    }

    @NotNull
    public static final ObjectValue<?> resolveObject(@NotNull MoLangRuntime $this$resolveObject, @NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)$this$resolveObject, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)expression, (String)"expression");
        MoValue moValue = MoLangExtensionsKt.resolve($this$resolveObject, expression);
        Intrinsics.checkNotNull((Object)moValue, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ObjectValue<*>");
        return (ObjectValue)moValue;
    }

    public static final boolean resolveBoolean(@NotNull MoLangRuntime $this$resolveBoolean, @NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)$this$resolveBoolean, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)expression, (String)"expression");
        return !(MoLangExtensionsKt.resolve($this$resolveBoolean, expression).asDouble() == 0.0);
    }

    @NotNull
    public static final MoValue resolve(@NotNull MoLangRuntime $this$resolve, @NotNull ExpressionLike expression) {
        Intrinsics.checkNotNullParameter((Object)$this$resolve, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)expression, (String)"expression");
        return expression.resolve($this$resolve);
    }

    public static final double resolveDouble(@NotNull MoLangRuntime $this$resolveDouble, @NotNull ExpressionLike expression) {
        Intrinsics.checkNotNullParameter((Object)$this$resolveDouble, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)expression, (String)"expression");
        return MoLangExtensionsKt.resolve($this$resolveDouble, expression).asDouble();
    }

    public static final float resolveFloat(@NotNull MoLangRuntime $this$resolveFloat, @NotNull ExpressionLike expression) {
        Intrinsics.checkNotNullParameter((Object)$this$resolveFloat, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)expression, (String)"expression");
        return (float)MoLangExtensionsKt.resolve($this$resolveFloat, expression).asDouble();
    }

    public static final int resolveInt(@NotNull MoLangRuntime $this$resolveInt, @NotNull ExpressionLike expression) {
        Intrinsics.checkNotNullParameter((Object)$this$resolveInt, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)expression, (String)"expression");
        return (int)MoLangExtensionsKt.resolveDouble($this$resolveInt, expression);
    }

    @NotNull
    public static final String resolveString(@NotNull MoLangRuntime $this$resolveString, @NotNull ExpressionLike expression) {
        Intrinsics.checkNotNullParameter((Object)$this$resolveString, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)expression, (String)"expression");
        String string = MoLangExtensionsKt.resolve($this$resolveString, expression).asString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"resolve(expression).asString()");
        return string;
    }

    @NotNull
    public static final ObjectValue<?> resolveObject(@NotNull MoLangRuntime $this$resolveObject, @NotNull ExpressionLike expression) {
        Intrinsics.checkNotNullParameter((Object)$this$resolveObject, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)expression, (String)"expression");
        MoValue moValue = MoLangExtensionsKt.resolve($this$resolveObject, expression);
        Intrinsics.checkNotNull((Object)moValue, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ObjectValue<*>");
        return (ObjectValue)moValue;
    }

    public static final boolean resolveBoolean(@NotNull MoLangRuntime $this$resolveBoolean, @NotNull ExpressionLike expression) {
        Intrinsics.checkNotNullParameter((Object)$this$resolveBoolean, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)expression, (String)"expression");
        return !(MoLangExtensionsKt.resolve($this$resolveBoolean, expression).asDouble() == 0.0);
    }

    @NotNull
    public static final Vec3 resolveVec3d(@NotNull MoLangRuntime $this$resolveVec3d, @NotNull Triple<? extends Expression, ? extends Expression, ? extends Expression> triple) {
        Intrinsics.checkNotNullParameter((Object)$this$resolveVec3d, (String)"<this>");
        Intrinsics.checkNotNullParameter(triple, (String)"triple");
        return new Vec3(MoLangExtensionsKt.resolveDouble($this$resolveVec3d, (Expression)triple.getFirst()), MoLangExtensionsKt.resolveDouble($this$resolveVec3d, (Expression)triple.getSecond()), MoLangExtensionsKt.resolveDouble($this$resolveVec3d, (Expression)triple.getThird()));
    }

    public static final boolean resolveBoolean(@NotNull MoLangRuntime $this$resolveBoolean, @NotNull Expression expression, @NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)$this$resolveBoolean, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)expression, (String)"expression");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        MoLangEnvironment moLangEnvironment = $this$resolveBoolean.getEnvironment();
        Intrinsics.checkNotNullExpressionValue((Object)moLangEnvironment, (String)"environment");
        MoLangExtensionsKt.writePokemon(moLangEnvironment, pokemon);
        return MoLangExtensionsKt.resolveBoolean($this$resolveBoolean, expression);
    }

    public static final double resolveDouble(@NotNull MoLangRuntime $this$resolveDouble, @NotNull Expression expression, @NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)$this$resolveDouble, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)expression, (String)"expression");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        MoLangEnvironment moLangEnvironment = $this$resolveDouble.getEnvironment();
        Intrinsics.checkNotNullExpressionValue((Object)moLangEnvironment, (String)"environment");
        MoLangExtensionsKt.writePokemon(moLangEnvironment, pokemon);
        return MoLangExtensionsKt.resolveDouble($this$resolveDouble, expression);
    }

    public static final int resolveInt(@NotNull MoLangRuntime $this$resolveInt, @NotNull Expression expression, @NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)$this$resolveInt, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)expression, (String)"expression");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        MoLangEnvironment moLangEnvironment = $this$resolveInt.getEnvironment();
        Intrinsics.checkNotNullExpressionValue((Object)moLangEnvironment, (String)"environment");
        MoLangExtensionsKt.writePokemon(moLangEnvironment, pokemon);
        return MoLangExtensionsKt.resolveInt($this$resolveInt, expression);
    }

    public static final float resolveFloat(@NotNull MoLangRuntime $this$resolveFloat, @NotNull Expression expression, @NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)$this$resolveFloat, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)expression, (String)"expression");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        MoLangEnvironment moLangEnvironment = $this$resolveFloat.getEnvironment();
        Intrinsics.checkNotNullExpressionValue((Object)moLangEnvironment, (String)"environment");
        MoLangExtensionsKt.writePokemon(moLangEnvironment, pokemon);
        return MoLangExtensionsKt.resolveFloat($this$resolveFloat, expression);
    }

    public static final boolean resolveBoolean(@NotNull MoLangRuntime $this$resolveBoolean, @NotNull Expression expression, @NotNull BattlePokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)$this$resolveBoolean, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)expression, (String)"expression");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        MoLangEnvironment moLangEnvironment = $this$resolveBoolean.getEnvironment();
        Intrinsics.checkNotNullExpressionValue((Object)moLangEnvironment, (String)"environment");
        MoLangExtensionsKt.writePokemon(moLangEnvironment, pokemon);
        return MoLangExtensionsKt.resolveBoolean($this$resolveBoolean, expression);
    }

    public static final double resolveDouble(@NotNull MoLangRuntime $this$resolveDouble, @NotNull Expression expression, @NotNull BattlePokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)$this$resolveDouble, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)expression, (String)"expression");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        MoLangEnvironment moLangEnvironment = $this$resolveDouble.getEnvironment();
        Intrinsics.checkNotNullExpressionValue((Object)moLangEnvironment, (String)"environment");
        MoLangExtensionsKt.writePokemon(moLangEnvironment, pokemon);
        return MoLangExtensionsKt.resolveDouble($this$resolveDouble, expression);
    }

    public static final int resolveInt(@NotNull MoLangRuntime $this$resolveInt, @NotNull Expression expression, @NotNull BattlePokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)$this$resolveInt, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)expression, (String)"expression");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        MoLangEnvironment moLangEnvironment = $this$resolveInt.getEnvironment();
        Intrinsics.checkNotNullExpressionValue((Object)moLangEnvironment, (String)"environment");
        MoLangExtensionsKt.writePokemon(moLangEnvironment, pokemon);
        return MoLangExtensionsKt.resolveInt($this$resolveInt, expression);
    }

    public static final float resolveFloat(@NotNull MoLangRuntime $this$resolveFloat, @NotNull Expression expression, @NotNull BattlePokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)$this$resolveFloat, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)expression, (String)"expression");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        MoLangEnvironment moLangEnvironment = $this$resolveFloat.getEnvironment();
        Intrinsics.checkNotNullExpressionValue((Object)moLangEnvironment, (String)"environment");
        MoLangExtensionsKt.writePokemon(moLangEnvironment, pokemon);
        return MoLangExtensionsKt.resolveFloat($this$resolveFloat, expression);
    }

    @NotNull
    public static final String getString(@NotNull Expression $this$getString) {
        Intrinsics.checkNotNullParameter((Object)$this$getString, (String)"<this>");
        String string = $this$getString.getOriginalString();
        if (string == null) {
            string = "0";
        }
        return string;
    }

    @NotNull
    public static final NumberExpression asExpression(double $this$asExpression) {
        return new NumberExpression($this$asExpression);
    }

    public static final Expression asExpression(@NotNull String $this$asExpression) {
        Expression expression;
        Intrinsics.checkNotNullParameter((Object)$this$asExpression, (String)"<this>");
        try {
            expression = MoLang.createParser(Intrinsics.areEqual((Object)$this$asExpression, (Object)"") ? "0.0" : $this$asExpression).parseExpression();
        }
        catch (Exception exc) {
            Cobblemon.INSTANCE.getLOGGER().error("Failed to parse MoLang expression: " + $this$asExpression);
            throw exc;
        }
        return expression;
    }

    public static final List<Expression> asExpressions(@NotNull String $this$asExpressions) {
        List<Expression> list;
        Intrinsics.checkNotNullParameter((Object)$this$asExpressions, (String)"<this>");
        try {
            list = MoLang.createParser(Intrinsics.areEqual((Object)$this$asExpressions, (Object)"") ? "0.0" : $this$asExpressions).parse();
        }
        catch (Exception exc) {
            Cobblemon.INSTANCE.getLOGGER().error("Failed to parse MoLang expressions: " + $this$asExpressions);
            throw exc;
        }
        return list;
    }

    @NotNull
    public static final ExpressionLike asExpressionLike(@NotNull String $this$asExpressionLike) {
        ExpressionLike expressionLike;
        Intrinsics.checkNotNullParameter((Object)$this$asExpressionLike, (String)"<this>");
        try {
            ExpressionLike expressionLike2;
            List<Expression> ls = MoLang.createParser(Intrinsics.areEqual((Object)$this$asExpressionLike, (Object)"") ? "0.0" : $this$asExpressionLike).parse();
            if (ls.size() == 1) {
                Expression expression = ls.get(0);
                Intrinsics.checkNotNullExpressionValue((Object)expression, (String)"ls[0]");
                expressionLike2 = new SingleExpression(expression);
            } else {
                Intrinsics.checkNotNullExpressionValue(ls, (String)"ls");
                expressionLike2 = new ListExpression(ls);
            }
            expressionLike = expressionLike2;
        }
        catch (Exception exc) {
            Cobblemon.INSTANCE.getLOGGER().error("Failed to parse MoLang expressions: " + $this$asExpressionLike);
            throw exc;
        }
        return expressionLike;
    }

    public static final void writePokemon(@NotNull MoLangEnvironment $this$writePokemon, @NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)$this$writePokemon, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        VariableStruct pokemonStruct = new VariableStruct();
        pokemon.writeVariables(pokemonStruct);
        $this$writePokemon.setSimpleVariable("pokemon", pokemonStruct);
    }

    public static final void writePokemon(@NotNull MoLangEnvironment $this$writePokemon, @NotNull BattlePokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)$this$writePokemon, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        VariableStruct pokemonStruct = new VariableStruct();
        pokemon.writeVariables(pokemonStruct);
        $this$writePokemon.setSimpleVariable("pokemon", pokemonStruct);
    }

    @NotNull
    public static final ExpressionLike asExpressionLike(@NotNull List<String> $this$asExpressionLike) {
        Intrinsics.checkNotNullParameter($this$asExpressionLike, (String)"<this>");
        return MoLangExtensionsKt.asExpressionLike(CollectionsKt.joinToString$default((Iterable)$this$asExpressionLike, (CharSequence)"\n", null, null, (int)0, null, null, (int)62, null));
    }

    public static final MoValue resolve(@NotNull List<? extends Expression> $this$resolve, @NotNull MoLangRuntime runtime2) {
        Intrinsics.checkNotNullParameter($this$resolve, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        return runtime2.execute($this$resolve);
    }

    public static final double resolveDouble(@NotNull List<? extends Expression> $this$resolveDouble, @NotNull MoLangRuntime runtime2) {
        Intrinsics.checkNotNullParameter($this$resolveDouble, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        return MoLangExtensionsKt.resolve($this$resolveDouble, runtime2).asDouble();
    }

    public static final int resolveInt(@NotNull List<? extends Expression> $this$resolveInt, @NotNull MoLangRuntime runtime2) {
        Intrinsics.checkNotNullParameter($this$resolveInt, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        return (int)MoLangExtensionsKt.resolveDouble($this$resolveInt, runtime2);
    }

    public static final boolean resolveBoolean(@NotNull List<? extends Expression> $this$resolveBoolean, @NotNull MoLangRuntime runtime2) {
        Intrinsics.checkNotNullParameter($this$resolveBoolean, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        return MoLangExtensionsKt.resolveDouble($this$resolveBoolean, runtime2) == 1.0;
    }

    @NotNull
    public static final ObjectValue<?> resolveObject(@NotNull List<? extends Expression> $this$resolveObject, @NotNull MoLangRuntime runtime2) {
        Intrinsics.checkNotNullParameter($this$resolveObject, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        MoValue moValue = MoLangExtensionsKt.resolve($this$resolveObject, runtime2);
        Intrinsics.checkNotNull((Object)moValue, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ObjectValue<*>");
        return (ObjectValue)moValue;
    }

    @Nullable
    public static final String getStringOrNull(@NotNull MoParams $this$getStringOrNull, int index) {
        Intrinsics.checkNotNullParameter((Object)$this$getStringOrNull, (String)"<this>");
        return $this$getStringOrNull.getParams().size() > index ? $this$getStringOrNull.getString(index) : null;
    }

    @Nullable
    public static final Double getDoubleOrNull(@NotNull MoParams $this$getDoubleOrNull, int index) {
        Intrinsics.checkNotNullParameter((Object)$this$getDoubleOrNull, (String)"<this>");
        return $this$getDoubleOrNull.getParams().size() > index ? Double.valueOf($this$getDoubleOrNull.getDouble(index)) : null;
    }

    @Nullable
    public static final Boolean getBooleanOrNull(@NotNull MoParams $this$getBooleanOrNull, int index) {
        Intrinsics.checkNotNullParameter((Object)$this$getBooleanOrNull, (String)"<this>");
        return $this$getBooleanOrNull.getParams().size() > index ? Boolean.valueOf($this$getBooleanOrNull.getDouble(index) == 1.0) : null;
    }
}

