@file:SourceDebugExtension(["SMAP\nCommandUtils.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CommandUtils.kt\ncom/cobblemon/mod/common/util/CommandUtilsKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,35:1\n1855#2,2:36\n*S KotlinDebug\n*F\n+ 1 CommandUtils.kt\ncom/cobblemon/mod/common/util/CommandUtilsKt\n*L\n31#1:36,2\n*E\n"])

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util

import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.tree.CommandNode
import com.mojang.brigadier.tree.LiteralCommandNode
import java.util.Locale
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.commands.CommandSourceStack

public fun <T : ArgumentBuilder<CommandSourceStack, Any>> ArgumentBuilder<CommandSourceStack, Any>.appendRequirement(
   requirement: (CommandSourceStack) -> Boolean
): Any {
   val var10000: ArgumentBuilder = `$this$appendRequirement`.requires(
      `$this$appendRequirement`.getRequirement().and(CommandUtilsKt::appendRequirement$lambda$0)
   );
   return (T)var10000;
}

public fun <S : Any> LiteralCommandNode<Any>.alias(alias: String): LiteralArgumentBuilder<Any> {
   val var10000: java.lang.String = alias.toLowerCase(Locale.ROOT);
   val builder: LiteralArgumentBuilder = ((LiteralArgumentBuilder.literal(var10000).requires(`$this$alias`.getRequirement()) as LiteralArgumentBuilder)
         .forward(`$this$alias`.getRedirect(), `$this$alias`.getRedirectModifier(), `$this$alias`.isFork()) as LiteralArgumentBuilder)
      .executes(`$this$alias`.getCommand()) as LiteralArgumentBuilder;

   val `$this$forEach$iv`: java.lang.Iterable;
   for (Object element$iv : $this$forEach$iv) {
      builder.then(`element$iv` as CommandNode);
   }

   return builder;
}

fun `appendRequirement$lambda$0`(`$tmp0`: Function1, p0: Any): Boolean {
   return `$tmp0`.invoke(p0) as java.lang.Boolean;
}
