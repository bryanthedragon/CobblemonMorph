/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.builder.ArgumentBuilder
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands
 *  net.minecraft.commands.arguments.EntityArgument
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCBox;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCPosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.settings.ServerSettings;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.PokeboxCommand;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.PartySlotArgumentType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.RemoveClientPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CommandContextExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u001c\u0010\u001dJ?\u0010\f\u001a\u00020\n2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\u0006\u001a\u00020\u00052\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\nH\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\u001b\u0010\u0011\u001a\u00020\u00102\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00030\u000e\u00a2\u0006\u0004\b\u0011\u0010\u0012R(\u0010\u0016\u001a\u0016\u0012\u0004\u0012\u00020\n\u0012\f\u0012\n \u0015*\u0004\u0018\u00010\u00140\u00140\u00138\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0016\u0010\u0017R(\u0010\u0018\u001a\u0016\u0012\u0004\u0012\u00020\n\u0012\f\u0012\n \u0015*\u0004\u0018\u00010\u00140\u00140\u00138\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0018\u0010\u0017R\u001c\u0010\u0019\u001a\n \u0015*\u0004\u0018\u00010\u00140\u00148\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0019\u0010\u001aR\u001c\u0010\u001b\u001a\n \u0015*\u0004\u0018\u00010\u00140\u00148\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001b\u0010\u001a\u00a8\u0006\u001e"}, d2={"Lcom/cobblemon/mod/common/command/PokeboxCommand;", "", "Lcom/mojang/brigadier/context/CommandContext;", "Lnet/minecraft/commands/CommandSourceStack;", "context", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemons", "", "box", "execute", "(Lcom/mojang/brigadier/context/CommandContext;Lnet/minecraft/server/level/ServerPlayer;Ljava/util/Collection;Ljava/lang/Integer;)I", "Lcom/mojang/brigadier/CommandDispatcher;", "dispatcher", "", "register", "(Lcom/mojang/brigadier/CommandDispatcher;)V", "Lkotlin/Function1;", "Lnet/minecraft/network/chat/MutableComponent;", "kotlin.jvm.PlatformType", "BOX_DOES_NOT_EXIST", "Lkotlin/jvm/functions/Function1;", "BOX_IS_FULL_EXCEPTION", "LAST_POKE_MESSAGE", "Lnet/minecraft/network/chat/MutableComponent;", "STORAGE_IS_FULL_EXCEPTION", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nPokeboxCommand.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokeboxCommand.kt\ncom/cobblemon/mod/common/command/PokeboxCommand\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,134:1\n1855#2,2:135\n*S KotlinDebug\n*F\n+ 1 PokeboxCommand.kt\ncom/cobblemon/mod/common/command/PokeboxCommand\n*L\n103#1:135,2\n*E\n"})
public final class PokeboxCommand {
    @NotNull
    public static final PokeboxCommand INSTANCE = new PokeboxCommand();
    @NotNull
    private static final Function1<Integer, MutableComponent> BOX_DOES_NOT_EXIST = BOX_DOES_NOT_EXIST.1.INSTANCE;
    @NotNull
    private static final Function1<Integer, MutableComponent> BOX_IS_FULL_EXCEPTION = BOX_IS_FULL_EXCEPTION.1.INSTANCE;
    private static final MutableComponent STORAGE_IS_FULL_EXCEPTION = LocalizationUtilsKt.commandLang("pokebox.storage_is_full", new Object[0]);
    private static final MutableComponent LAST_POKE_MESSAGE = LocalizationUtilsKt.commandLang("pokebox.last_pokemon", new Object[0]);

    private PokeboxCommand() {
    }

    public final void register(@NotNull CommandDispatcher<CommandSourceStack> dispatcher) {
        Intrinsics.checkNotNullParameter(dispatcher, (String)"dispatcher");
        LiteralArgumentBuilder literalArgumentBuilder = Commands.m_82127_((String)"pokebox");
        Intrinsics.checkNotNullExpressionValue((Object)literalArgumentBuilder, (String)"literal(\"pokebox\")");
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)PermissionUtilsKt.permission$default((ArgumentBuilder)literalArgumentBuilder, CobblemonPermissions.INSTANCE.getPOKEBOX(), false, 2, null)).then(Commands.m_82129_((String)"player", (ArgumentType)((ArgumentType)EntityArgument.m_91466_())).then(((RequiredArgumentBuilder)Commands.m_82129_((String)"slot", (ArgumentType)PartySlotArgumentType.Companion.partySlot()).then(Commands.m_82129_((String)"box", (ArgumentType)((ArgumentType)IntegerArgumentType.integer((int)1))).executes(PokeboxCommand::register$lambda$0))).executes(PokeboxCommand::register$lambda$1))));
        LiteralArgumentBuilder literalArgumentBuilder2 = Commands.m_82127_((String)"pokeboxall");
        Intrinsics.checkNotNullExpressionValue((Object)literalArgumentBuilder2, (String)"literal(\"pokeboxall\")");
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)PermissionUtilsKt.permission$default((ArgumentBuilder)literalArgumentBuilder2, CobblemonPermissions.INSTANCE.getPOKEBOX(), false, 2, null)).then(((RequiredArgumentBuilder)Commands.m_82129_((String)"player", (ArgumentType)((ArgumentType)EntityArgument.m_91466_())).then(Commands.m_82129_((String)"box", (ArgumentType)((ArgumentType)IntegerArgumentType.integer((int)1))).executes(PokeboxCommand::register$lambda$2))).executes(PokeboxCommand::register$lambda$3)));
    }

    private final int execute(CommandContext<CommandSourceStack> context, ServerPlayer player, Collection<? extends Pokemon> pokemons, Integer box) {
        PCStore playerPc = PlayerExtensionsKt.pc(player);
        PlayerPartyStore playerParty = PlayerExtensionsKt.party(player);
        if (box != null) {
            if (playerPc.getBoxes().size() < box) {
                Object object = BOX_DOES_NOT_EXIST.invoke((Object)box);
                Intrinsics.checkNotNullExpressionValue((Object)object, (String)"BOX_DOES_NOT_EXIST(box)");
                CommandSyntaxException commandSyntaxException = new SimpleCommandExceptionType((Message)TextKt.red((MutableComponent)object)).create();
                Intrinsics.checkNotNullExpressionValue((Object)commandSyntaxException, (String)"SimpleCommandExceptionTy\u2026XIST(box).red()).create()");
                throw (Throwable)commandSyntaxException;
            }
            PCBox pcBox = playerPc.getBoxes().get(box - 1);
            if (pcBox.getUnoccupiedSlots() < pokemons.size()) {
                Object object = BOX_IS_FULL_EXCEPTION.invoke((Object)box);
                Intrinsics.checkNotNullExpressionValue((Object)object, (String)"BOX_IS_FULL_EXCEPTION(box)");
                CommandSyntaxException commandSyntaxException = new SimpleCommandExceptionType((Message)TextKt.red((MutableComponent)object)).create();
                Intrinsics.checkNotNullExpressionValue((Object)commandSyntaxException, (String)"SimpleCommandExceptionTy\u2026TION(box).red()).create()");
                throw (Throwable)commandSyntaxException;
            }
        }
        Iterable $this$forEach$iv = CollectionsKt.reversed((Iterable)pokemons);
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            PCPosition pCPosition;
            Pokemon pokemon = (Pokemon)element$iv;
            boolean bl = false;
            if (ServerSettings.INSTANCE.getPreventCompletePartyDeposit() && playerParty.occupied() == 1) {
                ((CommandSourceStack)context.getSource()).m_288197_(PokeboxCommand::execute$lambda$5$lambda$4, false);
                return pokemons.size() - 1;
            }
            if (box == null) {
                pCPosition = playerPc.getFirstAvailablePosition();
                if (pCPosition == null) {
                    MutableComponent mutableComponent = STORAGE_IS_FULL_EXCEPTION;
                    Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"STORAGE_IS_FULL_EXCEPTION");
                    CommandSyntaxException commandSyntaxException = new SimpleCommandExceptionType((Message)TextKt.red(mutableComponent)).create();
                    Intrinsics.checkNotNullExpressionValue((Object)commandSyntaxException, (String)"SimpleCommandExceptionTy\u2026EXCEPTION.red()).create()");
                    throw (Throwable)commandSyntaxException;
                }
            } else {
                PCBox pcBox = playerPc.getBoxes().get(box - 1);
                pCPosition = pcBox.getFirstAvailablePosition();
                if (pCPosition == null) {
                    Object object = BOX_IS_FULL_EXCEPTION.invoke((Object)box);
                    Intrinsics.checkNotNullExpressionValue((Object)object, (String)"BOX_IS_FULL_EXCEPTION(box)");
                    CommandSyntaxException commandSyntaxException = new SimpleCommandExceptionType((Message)TextKt.red((MutableComponent)object)).create();
                    Intrinsics.checkNotNullExpressionValue((Object)commandSyntaxException, (String)"SimpleCommandExceptionTy\u2026TION(box).red()).create()");
                    throw (Throwable)commandSyntaxException;
                }
            }
            PCPosition pcPosition = pCPosition;
            playerParty.remove(pokemon);
            playerPc.set(pcPosition, pokemon);
            PokemonStore pokemonStore = PlayerExtensionsKt.party(player);
            UUID uUID = pokemon.getUuid();
            Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"pokemon.uuid");
            playerParty.sendPacketToObservers(new RemoveClientPokemonPacket(pokemonStore, uUID));
        }
        return pokemons.size();
    }

    static /* synthetic */ int execute$default(PokeboxCommand pokeboxCommand, CommandContext commandContext, ServerPlayer serverPlayer, Collection collection, Integer n, int n2, Object object) {
        if ((n2 & 8) != 0) {
            n = null;
        }
        return pokeboxCommand.execute((CommandContext<CommandSourceStack>)commandContext, serverPlayer, collection, n);
    }

    private static final int register$lambda$0(CommandContext context) {
        Intrinsics.checkNotNullExpressionValue((Object)context, (String)"context");
        ServerPlayer player = CommandContextExtensionsKt.player$default(context, null, 1, null);
        Intrinsics.checkNotNullExpressionValue((Object)player, (String)"player");
        Pokemon pokemon = PartySlotArgumentType.Companion.getPokemonOf(context, "slot", player);
        int box = IntegerArgumentType.getInteger((CommandContext)context, (String)"box");
        return INSTANCE.execute((CommandContext<CommandSourceStack>)context, player, CollectionsKt.listOf((Object)pokemon), box);
    }

    private static final int register$lambda$1(CommandContext context) {
        Intrinsics.checkNotNullExpressionValue((Object)context, (String)"context");
        ServerPlayer player = CommandContextExtensionsKt.player$default(context, null, 1, null);
        Intrinsics.checkNotNullExpressionValue((Object)player, (String)"player");
        Pokemon pokemon = PartySlotArgumentType.Companion.getPokemonOf(context, "slot", player);
        return PokeboxCommand.execute$default(INSTANCE, context, player, CollectionsKt.listOf((Object)pokemon), null, 8, null);
    }

    private static final int register$lambda$2(CommandContext context) {
        Intrinsics.checkNotNullExpressionValue((Object)context, (String)"context");
        ServerPlayer player = CommandContextExtensionsKt.player$default(context, null, 1, null);
        int box = IntegerArgumentType.getInteger((CommandContext)context, (String)"box");
        Intrinsics.checkNotNullExpressionValue((Object)player, (String)"player");
        return INSTANCE.execute((CommandContext<CommandSourceStack>)context, player, CollectionsKt.toList((Iterable)PlayerExtensionsKt.party(player)), box);
    }

    private static final int register$lambda$3(CommandContext context) {
        Intrinsics.checkNotNullExpressionValue((Object)context, (String)"context");
        ServerPlayer player = CommandContextExtensionsKt.player$default(context, null, 1, null);
        Intrinsics.checkNotNullExpressionValue((Object)player, (String)"player");
        return PokeboxCommand.execute$default(INSTANCE, context, player, CollectionsKt.toList((Iterable)PlayerExtensionsKt.party(player)), null, 8, null);
    }

    private static final Component execute$lambda$5$lambda$4() {
        MutableComponent mutableComponent = LAST_POKE_MESSAGE;
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"LAST_POKE_MESSAGE");
        return (Component)TextKt.red(mutableComponent);
    }
}

