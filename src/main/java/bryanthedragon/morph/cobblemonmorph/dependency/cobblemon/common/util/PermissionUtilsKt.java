/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.builder.ArgumentBuilder
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.SharedSuggestionProvider
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.Permission;
import com.mojang.brigadier.builder.ArgumentBuilder;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u001aE\u0010\u0004\u001a\u00028\u0000\"\u0014\b\u0000\u0010\u0002*\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00028\u00000\u0000*\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0004\b\u0004\u0010\u0007\u001a^\u0010\r\u001a\u00028\u0000\"\u0014\b\u0000\u0010\u0002*\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00028\u00000\u0000*\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u0004\u001a\u00020\u00032!\u0010\f\u001a\u001d\u0012\u0013\u0012\u00110\u0001\u00a2\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00020\u00050\b\u00a2\u0006\u0004\b\r\u0010\u000e\u00a8\u0006\u000f"}, d2={"Lcom/mojang/brigadier/builder/ArgumentBuilder;", "Lnet/minecraft/commands/CommandSourceStack;", "T", "Lcom/cobblemon/mod/common/api/permission/Permission;", "permission", "", "appendRequirement", "(Lcom/mojang/brigadier/builder/ArgumentBuilder;Lcom/cobblemon/mod/common/api/permission/Permission;Z)Lcom/mojang/brigadier/builder/ArgumentBuilder;", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "src", "predicate", "requiresWithPermission", "(Lcom/mojang/brigadier/builder/ArgumentBuilder;Lcom/cobblemon/mod/common/api/permission/Permission;Lkotlin/jvm/functions/Function1;)Lcom/mojang/brigadier/builder/ArgumentBuilder;", "common"})
public final class PermissionUtilsKt {
    @NotNull
    public static final <T extends ArgumentBuilder<CommandSourceStack, T>> T permission(@NotNull ArgumentBuilder<CommandSourceStack, T> $this$permission, @NotNull Permission permission2, boolean appendRequirement) {
        ArgumentBuilder argumentBuilder;
        Intrinsics.checkNotNullParameter($this$permission, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)permission2, (String)"permission");
        Function1 permissionPredicate2 = (Function1)new Function1<CommandSourceStack, Boolean>(permission2){
            final /* synthetic */ Permission $permission;
            {
                this.$permission = $permission;
                super(1);
            }

            @NotNull
            public final Boolean invoke(@NotNull CommandSourceStack src) {
                Intrinsics.checkNotNullParameter((Object)src, (String)"src");
                return Cobblemon.INSTANCE.getPermissionValidator().hasPermission((SharedSuggestionProvider)src, this.$permission);
            }
        };
        if (appendRequirement) {
            ArgumentBuilder argumentBuilder2 = $this$permission.requires($this$permission.getRequirement().and(arg_0 -> PermissionUtilsKt.permission$lambda$0(permissionPredicate2, arg_0)));
            argumentBuilder = argumentBuilder2;
            Intrinsics.checkNotNullExpressionValue((Object)argumentBuilder2, (String)"this.requires(this.requi\u2026and(permissionPredicate))");
        } else {
            ArgumentBuilder argumentBuilder3 = $this$permission.requires(arg_0 -> PermissionUtilsKt.permission$lambda$1(permissionPredicate2, arg_0));
            argumentBuilder = argumentBuilder3;
            Intrinsics.checkNotNullExpressionValue((Object)argumentBuilder3, (String)"this.requires(permissionPredicate)");
        }
        return (T)argumentBuilder;
    }

    public static /* synthetic */ ArgumentBuilder permission$default(ArgumentBuilder argumentBuilder, Permission permission2, boolean bl, int n, Object object) {
        if ((n & 2) != 0) {
            bl = true;
        }
        return PermissionUtilsKt.permission(argumentBuilder, permission2, bl);
    }

    @NotNull
    public static final <T extends ArgumentBuilder<CommandSourceStack, T>> T requiresWithPermission(@NotNull ArgumentBuilder<CommandSourceStack, T> $this$requiresWithPermission, @NotNull Permission permission2, @NotNull Function1<? super CommandSourceStack, Boolean> predicate) {
        Intrinsics.checkNotNullParameter($this$requiresWithPermission, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)permission2, (String)"permission");
        Intrinsics.checkNotNullParameter(predicate, (String)"predicate");
        $this$requiresWithPermission.requires(arg_0 -> PermissionUtilsKt.requiresWithPermission$lambda$2(predicate, arg_0));
        return (T)PermissionUtilsKt.permission$default($this$requiresWithPermission, permission2, false, 2, null);
    }

    private static final boolean permission$lambda$0(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Boolean)$tmp0.invoke(p0);
    }

    private static final boolean permission$lambda$1(Function1 $tmp0, CommandSourceStack p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Boolean)$tmp0.invoke((Object)p0);
    }

    private static final boolean requiresWithPermission$lambda$2(Function1 $tmp0, CommandSourceStack p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Boolean)$tmp0.invoke((Object)p0);
    }
}

