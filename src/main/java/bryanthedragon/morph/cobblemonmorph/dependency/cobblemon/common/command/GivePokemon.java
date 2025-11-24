/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.builder.ArgumentBuilder
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.tree.LiteralCommandNode
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands
 *  net.minecraft.commands.arguments.EntityArgument
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.PokemonPropertiesArgumentType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CommandContextExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CommandUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\n\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0017\u0010\u0018J%\u0010\b\u001a\u00020\u00072\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\u0006\u001a\u00020\u0005H\u0002\u00a2\u0006\u0004\b\b\u0010\tJ\u001b\u0010\r\u001a\u00020\f2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\n\u00a2\u0006\u0004\b\r\u0010\u000eR\u0014\u0010\u0010\u001a\u00020\u000f8\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u000f8\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0012\u0010\u0011R\u0014\u0010\u0013\u001a\u00020\u000f8\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0013\u0010\u0011R\u0014\u0010\u0014\u001a\u00020\u000f8\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0014\u0010\u0011R\u0014\u0010\u0015\u001a\u00020\u000f8\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0015\u0010\u0011R\u0014\u0010\u0016\u001a\u00020\u000f8\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0016\u0010\u0011\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/command/GivePokemon;", "", "Lcom/mojang/brigadier/context/CommandContext;", "Lnet/minecraft/commands/CommandSourceStack;", "context", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "execute", "(Lcom/mojang/brigadier/context/CommandContext;Lnet/minecraft/server/level/ServerPlayer;)I", "Lcom/mojang/brigadier/CommandDispatcher;", "dispatcher", "", "register", "(Lcom/mojang/brigadier/CommandDispatcher;)V", "", "ALIAS", "Ljava/lang/String;", "ALIAS_OTHER", "NAME", "NAME_OTHER", "PLAYER", "PROPERTIES", "<init>", "()V", "common"})
public final class GivePokemon {
    @NotNull
    public static final GivePokemon INSTANCE = new GivePokemon();
    @NotNull
    private static final String NAME = "givepokemon";
    @NotNull
    private static final String ALIAS = "pokegive";
    @NotNull
    private static final String NAME_OTHER = "givepokemonother";
    @NotNull
    private static final String ALIAS_OTHER = "pokegiveother";
    @NotNull
    private static final String PLAYER = "player";
    @NotNull
    private static final String PROPERTIES = "properties";

    private GivePokemon() {
    }

    public final void register(@NotNull CommandDispatcher<CommandSourceStack> dispatcher) {
        Intrinsics.checkNotNullParameter(dispatcher, (String)"dispatcher");
        LiteralArgumentBuilder literalArgumentBuilder = Commands.m_82127_((String)NAME);
        Intrinsics.checkNotNullExpressionValue((Object)literalArgumentBuilder, (String)"literal(NAME)");
        LiteralCommandNode selfCommand = dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)PermissionUtilsKt.permission$default((ArgumentBuilder)literalArgumentBuilder, CobblemonPermissions.INSTANCE.getGIVE_POKEMON_SELF(), false, 2, null)).then(Commands.m_82129_((String)PROPERTIES, (ArgumentType)PokemonPropertiesArgumentType.Companion.properties()).executes(GivePokemon::register$lambda$0)));
        Intrinsics.checkNotNullExpressionValue((Object)selfCommand, (String)"selfCommand");
        dispatcher.register(CommandUtilsKt.alias(selfCommand, ALIAS));
        LiteralArgumentBuilder literalArgumentBuilder2 = Commands.m_82127_((String)NAME_OTHER);
        Intrinsics.checkNotNullExpressionValue((Object)literalArgumentBuilder2, (String)"literal(NAME_OTHER)");
        LiteralCommandNode otherCommand = dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)PermissionUtilsKt.permission$default((ArgumentBuilder)literalArgumentBuilder2, CobblemonPermissions.INSTANCE.getGIVE_POKEMON_OTHER(), false, 2, null)).then(Commands.m_82129_((String)PLAYER, (ArgumentType)((ArgumentType)EntityArgument.m_91466_())).then(Commands.m_82129_((String)PROPERTIES, (ArgumentType)PokemonPropertiesArgumentType.Companion.properties()).executes(GivePokemon::register$lambda$1))));
        Intrinsics.checkNotNullExpressionValue((Object)otherCommand, (String)"otherCommand");
        dispatcher.register(CommandUtilsKt.alias(otherCommand, ALIAS_OTHER));
    }

    private final int execute(CommandContext<CommandSourceStack> context, ServerPlayer player) {
        try {
            PokemonProperties pokemonProperties = PokemonPropertiesArgumentType.Companion.getPokemonProperties(context, PROPERTIES);
            if (pokemonProperties.getSpecies() == null) {
                MutableComponent mutableComponent = LocalizationUtilsKt.commandLang("givepokemon.nospecies", new Object[0]);
                Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"commandLang(\"${NAME}.nospecies\")");
                player.m_213846_((Component)TextKt.red(mutableComponent));
                return 1;
            }
            Pokemon pokemon = pokemonProperties.create();
            PlayerPartyStore party = Cobblemon.INSTANCE.getStorage().getParty(player);
            party.add(pokemon);
            ((CommandSourceStack)context.getSource()).m_288197_(() -> GivePokemon.execute$lambda$2(pokemon, player), true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    private static final int register$lambda$0(CommandContext it) {
        Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
        ServerPlayer serverPlayer = ((CommandSourceStack)it.getSource()).m_81375_();
        Intrinsics.checkNotNullExpressionValue((Object)serverPlayer, (String)"it.source.playerOrThrow");
        return INSTANCE.execute((CommandContext<CommandSourceStack>)it, serverPlayer);
    }

    private static final int register$lambda$1(CommandContext it) {
        Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
        ServerPlayer serverPlayer = CommandContextExtensionsKt.player$default(it, null, 1, null);
        Intrinsics.checkNotNullExpressionValue((Object)serverPlayer, (String)"it.player()");
        return INSTANCE.execute((CommandContext<CommandSourceStack>)it, serverPlayer);
    }

    private static final Component execute$lambda$2(Pokemon $pokemon, ServerPlayer $player) {
        Intrinsics.checkNotNullParameter((Object)$pokemon, (String)"$pokemon");
        Intrinsics.checkNotNullParameter((Object)$player, (String)"$player");
        Object[] objectArray = new Object[2];
        objectArray[0] = $pokemon.getSpecies().getTranslatedName();
        Intrinsics.checkNotNullExpressionValue((Object)$player.m_7755_(), (String)"player.name");
        return (Component)LocalizationUtilsKt.commandLang("givepokemon.give", objectArray);
    }
}

