/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.builder.ArgumentBuilder
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  kotlin.Metadata
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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonPropertyExtractor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.PokemonPropertiesArgumentType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CommandContextExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\n\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u001d\u0010\u0006\u001a\u00020\u00052\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001b\u0010\u000b\u001a\u00020\n2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\b\u00a2\u0006\u0004\b\u000b\u0010\fR\u0014\u0010\u000e\u001a\u00020\r8\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\u00058\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\r8\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0012\u0010\u000fR\u0014\u0010\u0013\u001a\u00020\r8\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0013\u0010\u000fR\u0014\u0010\u0014\u001a\u00020\r8\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0014\u0010\u000f\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/command/TestPartySlotCommand;", "", "Lcom/mojang/brigadier/context/CommandContext;", "Lnet/minecraft/commands/CommandSourceStack;", "context", "", "execute", "(Lcom/mojang/brigadier/context/CommandContext;)I", "Lcom/mojang/brigadier/CommandDispatcher;", "dispatcher", "", "register", "(Lcom/mojang/brigadier/CommandDispatcher;)V", "", "NAME", "Ljava/lang/String;", "NO_SUCCESS", "I", "PLAYER", "PROPERTIES", "SLOT", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nTestPartySlotCommand.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TestPartySlotCommand.kt\ncom/cobblemon/mod/common/command/TestPartySlotCommand\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,51:1\n1#2:52\n*E\n"})
public final class TestPartySlotCommand {
    @NotNull
    public static final TestPartySlotCommand INSTANCE = new TestPartySlotCommand();
    @NotNull
    private static final String NAME = "testpartyslot";
    @NotNull
    private static final String PLAYER = "player";
    @NotNull
    private static final String SLOT = "slot";
    @NotNull
    private static final String PROPERTIES = "properties";
    private static final int NO_SUCCESS = 0;

    private TestPartySlotCommand() {
    }

    public final void register(@NotNull CommandDispatcher<CommandSourceStack> dispatcher) {
        Intrinsics.checkNotNullParameter(dispatcher, (String)"dispatcher");
        LiteralArgumentBuilder literalArgumentBuilder = Commands.m_82127_((String)NAME);
        Intrinsics.checkNotNullExpressionValue((Object)literalArgumentBuilder, (String)"literal(NAME)");
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)PermissionUtilsKt.permission$default((ArgumentBuilder)literalArgumentBuilder, CobblemonPermissions.INSTANCE.getTEST_PARTY_SLOT(), false, 2, null)).then(Commands.m_82129_((String)PLAYER, (ArgumentType)((ArgumentType)EntityArgument.m_91466_())).then(Commands.m_82129_((String)SLOT, (ArgumentType)((ArgumentType)IntegerArgumentType.integer((int)1, (int)6))).then(Commands.m_82129_((String)PROPERTIES, (ArgumentType)PokemonPropertiesArgumentType.Companion.properties()).executes(this::execute)))));
    }

    private final int execute(CommandContext<CommandSourceStack> context) {
        boolean bl;
        PokemonProperties pokemonProperties;
        ServerPlayer player = CommandContextExtensionsKt.player(context, PLAYER);
        int slot = IntegerArgumentType.getInteger(context, (String)SLOT);
        PokemonProperties properties2 = PokemonPropertiesArgumentType.Companion.getPokemonProperties(context, PROPERTIES);
        Intrinsics.checkNotNullExpressionValue((Object)player, (String)PLAYER);
        Pokemon pokemon = PlayerExtensionsKt.party(player).get(slot - 1);
        if (pokemon != null && (pokemonProperties = pokemon.createPokemonProperties(PokemonPropertyExtractor.ALL)) != null) {
            PokemonProperties it = pokemonProperties;
            boolean bl2 = false;
            bl = properties2.isSubSetOf(it);
        } else {
            bl = false;
        }
        return bl ? 1 : 0;
    }
}

