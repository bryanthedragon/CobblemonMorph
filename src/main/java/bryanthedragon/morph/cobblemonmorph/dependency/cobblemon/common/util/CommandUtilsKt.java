/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.builder.ArgumentBuilder
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.tree.CommandNode
 *  com.mojang.brigadier.tree.LiteralCommandNode
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.commands.CommandSourceStack
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collection;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.commands.CommandSourceStack;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u00004\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\u001a/\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\"\b\b\u0000\u0010\u0001*\u00020\u0000*\b\u0012\u0004\u0012\u00028\u00000\u00022\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0006\u001aV\u0010\u0010\u001a\u00028\u0000\"\u0014\b\u0000\u0010\t*\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00028\u00000\u0007*\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00028\u00000\u00072!\u0010\u000f\u001a\u001d\u0012\u0013\u0012\u00110\b\u00a2\u0006\f\b\u000b\u0012\b\b\f\u0012\u0004\b\b(\r\u0012\u0004\u0012\u00020\u000e0\n\u00a2\u0006\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0012"}, d2={"", "S", "Lcom/mojang/brigadier/tree/LiteralCommandNode;", "", "alias", "Lcom/mojang/brigadier/builder/LiteralArgumentBuilder;", "(Lcom/mojang/brigadier/tree/LiteralCommandNode;Ljava/lang/String;)Lcom/mojang/brigadier/builder/LiteralArgumentBuilder;", "Lcom/mojang/brigadier/builder/ArgumentBuilder;", "Lnet/minecraft/commands/CommandSourceStack;", "T", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "src", "", "requirement", "appendRequirement", "(Lcom/mojang/brigadier/builder/ArgumentBuilder;Lkotlin/jvm/functions/Function1;)Lcom/mojang/brigadier/builder/ArgumentBuilder;", "common"})
@SourceDebugExtension(value={"SMAP\nCommandUtils.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CommandUtils.kt\ncom/cobblemon/mod/common/util/CommandUtilsKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,35:1\n1855#2,2:36\n*S KotlinDebug\n*F\n+ 1 CommandUtils.kt\ncom/cobblemon/mod/common/util/CommandUtilsKt\n*L\n31#1:36,2\n*E\n"})
public final class CommandUtilsKt {
    @NotNull
    public static final <T extends ArgumentBuilder<CommandSourceStack, T>> T appendRequirement(@NotNull ArgumentBuilder<CommandSourceStack, T> $this$appendRequirement, @NotNull Function1<? super CommandSourceStack, Boolean> requirement) {
        Intrinsics.checkNotNullParameter($this$appendRequirement, (String)"<this>");
        Intrinsics.checkNotNullParameter(requirement, (String)"requirement");
        ArgumentBuilder argumentBuilder = $this$appendRequirement.requires($this$appendRequirement.getRequirement().and(arg_0 -> CommandUtilsKt.appendRequirement$lambda$0(requirement, arg_0)));
        Intrinsics.checkNotNullExpressionValue((Object)argumentBuilder, (String)"this.requires(this.requirement.and(requirement))");
        return (T)argumentBuilder;
    }

    @NotNull
    public static final <S> LiteralArgumentBuilder<S> alias(@NotNull LiteralCommandNode<S> $this$alias, @NotNull String alias) {
        Intrinsics.checkNotNullParameter($this$alias, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)alias, (String)"alias");
        String string = alias.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
        LiteralArgumentBuilder builder = (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)LiteralArgumentBuilder.literal((String)string).requires($this$alias.getRequirement())).forward($this$alias.getRedirect(), $this$alias.getRedirectModifier(), $this$alias.isFork())).executes($this$alias.getCommand());
        Collection collection = $this$alias.getChildren();
        Intrinsics.checkNotNullExpressionValue((Object)collection, (String)"this.children");
        Iterable $this$forEach$iv = collection;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            CommandNode child = (CommandNode)element$iv;
            boolean bl = false;
            builder.then(child);
        }
        Intrinsics.checkNotNullExpressionValue((Object)builder, (String)"builder");
        return builder;
    }

    private static final boolean appendRequirement$lambda$0(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Boolean)$tmp0.invoke(p0);
    }
}

