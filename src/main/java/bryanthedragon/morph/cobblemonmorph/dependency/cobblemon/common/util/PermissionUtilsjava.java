package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.Permission
import com.mojang.brigadier.builder.ArgumentBuilder
import kotlin.jvm.functions.Function1
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.SharedSuggestionProvider
import org.jetbrains.annotations.NotNull

public fun <T : ArgumentBuilder<CommandSourceStack, Any>> ArgumentBuilder<CommandSourceStack, Any>.permission(
   permission: Permission,
   appendRequirement: Boolean = ...
): Any {
   val permissionPredicate: Function1 = (new Function1<CommandSourceStack, java.lang.Boolean>(permission) {
      {
         super(1);
         this.$permission = `$permission`;
      }

      @NotNull
      public final java.lang.Boolean invoke(@NotNull CommandSourceStack src) {
         return Cobblemon.INSTANCE.getPermissionValidator().hasPermission(src as SharedSuggestionProvider, this.$permission);
      }
   }) as Function1;
   val var10000: ArgumentBuilder;
   if (appendRequirement) {
      var10000 = `$this$permission`.requires(`$this$permission`.getRequirement().and(PermissionUtilsKt::permission$lambda$0));
   } else {
      var10000 = `$this$permission`.requires(PermissionUtilsKt::permission$lambda$1);
   }

   return (T)var10000;
}

@JvmSynthetic
fun `permission$default`(var0: ArgumentBuilder, var1: Permission, var2: Boolean, var3: Int, var4: Any): ArgumentBuilder {
   if ((var3 and 2) != 0) {
      var2 = true;
   }

   return permission(var0, var1, var2);
}

public fun <T : ArgumentBuilder<CommandSourceStack, Any>> ArgumentBuilder<CommandSourceStack, Any>.requiresWithPermission(
   permission: Permission,
   predicate: (CommandSourceStack) -> Boolean
): Any {
   `$this$requiresWithPermission`.requires(PermissionUtilsKt::requiresWithPermission$lambda$2);
   return (T)permission$default(`$this$requiresWithPermission`, permission, false, 2, null);
}

fun `permission$lambda$0`(`$tmp0`: Function1, p0: Any): Boolean {
   return `$tmp0`.invoke(p0) as java.lang.Boolean;
}

fun `permission$lambda$1`(`$tmp0`: Function1, p0: CommandSourceStack): Boolean {
   return `$tmp0`.invoke(p0) as java.lang.Boolean;
}

fun `requiresWithPermission$lambda$2`(`$tmp0`: Function1, p0: CommandSourceStack): Boolean {
   return `$tmp0`.invoke(p0) as java.lang.Boolean;
}
