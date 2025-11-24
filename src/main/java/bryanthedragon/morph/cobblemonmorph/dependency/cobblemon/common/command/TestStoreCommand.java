/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.builder.ArgumentBuilder
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands
 *  net.minecraft.commands.arguments.EntityArgument
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.PokemonPropertiesArgumentType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.PokemonStoreArgumentType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.StoreType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CommandContextExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u001d\u0010\u0006\u001a\u00020\u00052\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001b\u0010\u000b\u001a\u00020\n2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\b\u00a2\u0006\u0004\b\u000b\u0010\fR\u0014\u0010\u000e\u001a\u00020\r8\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\r8\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0010\u0010\u000fR\u0014\u0010\u0011\u001a\u00020\r8\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0011\u0010\u000fR\u0014\u0010\u0012\u001a\u00020\r8\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0012\u0010\u000f\u00a8\u0006\u0015"}, d2={"Lcom/cobblemon/mod/common/command/TestStoreCommand;", "", "Lcom/mojang/brigadier/context/CommandContext;", "Lnet/minecraft/commands/CommandSourceStack;", "context", "", "execute", "(Lcom/mojang/brigadier/context/CommandContext;)I", "Lcom/mojang/brigadier/CommandDispatcher;", "dispatcher", "", "register", "(Lcom/mojang/brigadier/CommandDispatcher;)V", "", "NAME", "Ljava/lang/String;", "PLAYER", "PROPERTIES", "STORE", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nTestStoreCommand.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TestStoreCommand.kt\ncom/cobblemon/mod/common/command/TestStoreCommand\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,49:1\n1774#2,4:50\n*S KotlinDebug\n*F\n+ 1 TestStoreCommand.kt\ncom/cobblemon/mod/common/command/TestStoreCommand\n*L\n46#1:50,4\n*E\n"})
public final class TestStoreCommand {
    @NotNull
    public static final TestStoreCommand INSTANCE = new TestStoreCommand();
    @NotNull
    private static final String NAME = "teststore";
    @NotNull
    private static final String PLAYER = "player";
    @NotNull
    private static final String STORE = "store";
    @NotNull
    private static final String PROPERTIES = "properties";

    private TestStoreCommand() {
    }

    public final void register(@NotNull CommandDispatcher<CommandSourceStack> dispatcher) {
        Intrinsics.checkNotNullParameter(dispatcher, (String)"dispatcher");
        LiteralArgumentBuilder literalArgumentBuilder = Commands.m_82127_((String)NAME);
        Intrinsics.checkNotNullExpressionValue((Object)literalArgumentBuilder, (String)"literal(NAME)");
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)PermissionUtilsKt.permission$default((ArgumentBuilder)literalArgumentBuilder, CobblemonPermissions.INSTANCE.getTEST_STORE(), false, 2, null)).then(Commands.m_82129_((String)PLAYER, (ArgumentType)((ArgumentType)EntityArgument.m_91466_())).then(Commands.m_82129_((String)STORE, (ArgumentType)((ArgumentType)PokemonStoreArgumentType.Companion.pokemonStore())).then(Commands.m_82129_((String)PROPERTIES, (ArgumentType)PokemonPropertiesArgumentType.Companion.properties()).executes(this::execute)))));
    }

    /*
     * WARNING - void declaration
     */
    private final int execute(CommandContext<CommandSourceStack> context) {
        int n;
        void $this$count$iv;
        ServerPlayer player = CommandContextExtensionsKt.player(context, PLAYER);
        StoreType storeType = PokemonStoreArgumentType.Companion.pokemonStoreFrom(context, STORE);
        PokemonProperties properties2 = PokemonPropertiesArgumentType.Companion.getPokemonProperties(context, PROPERTIES);
        Function1<ServerPlayer, Collection<Pokemon>> function1 = storeType.getStoreFetcher();
        Intrinsics.checkNotNullExpressionValue((Object)player, (String)PLAYER);
        Iterable iterable = (Iterable)function1.invoke((Object)player);
        boolean $i$f$count = false;
        if ($this$count$iv instanceof Collection && ((Collection)$this$count$iv).isEmpty()) {
            n = 0;
        } else {
            int count$iv = 0;
            for (Object element$iv : $this$count$iv) {
                Pokemon p0 = (Pokemon)element$iv;
                boolean bl = false;
                if (!properties2.matches(p0) || ++count$iv >= 0) continue;
                CollectionsKt.throwCountOverflow();
            }
            n = count$iv;
        }
        return n;
    }
}

