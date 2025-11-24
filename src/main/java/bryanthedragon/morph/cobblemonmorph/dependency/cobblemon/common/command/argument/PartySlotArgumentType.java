/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.ImmutableStringReader
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 *  com.mojang.brigadier.suggestion.Suggestions
 *  com.mojang.brigadier.suggestion.SuggestionsBuilder
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.IntIterator
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.IntRange
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.SharedSuggestionProvider
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import com.mojang.brigadier.ImmutableStringReader;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.IntRange;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 \u00172\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0017B\u0007\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0015\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J5\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r\"\b\b\u0000\u0010\b*\u00020\u00072\f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\t2\u0006\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0017\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u0011H\u0016\u00a2\u0006\u0004\b\u0013\u0010\u0014\u00a8\u0006\u0018"}, d2={"Lcom/cobblemon/mod/common/command/argument/PartySlotArgumentType;", "Lcom/mojang/brigadier/arguments/ArgumentType;", "", "", "", "getExamples", "()Ljava/util/List;", "", "S", "Lcom/mojang/brigadier/context/CommandContext;", "context", "Lcom/mojang/brigadier/suggestion/SuggestionsBuilder;", "builder", "Ljava/util/concurrent/CompletableFuture;", "Lcom/mojang/brigadier/suggestion/Suggestions;", "listSuggestions", "(Lcom/mojang/brigadier/context/CommandContext;Lcom/mojang/brigadier/suggestion/SuggestionsBuilder;)Ljava/util/concurrent/CompletableFuture;", "Lcom/mojang/brigadier/StringReader;", "reader", "parse", "(Lcom/mojang/brigadier/StringReader;)Ljava/lang/Integer;", "<init>", "()V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nPartySlotArgumentType.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PartySlotArgumentType.kt\ncom/cobblemon/mod/common/command/argument/PartySlotArgumentType\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,66:1\n1549#2:67\n1620#2,3:68\n*S KotlinDebug\n*F\n+ 1 PartySlotArgumentType.kt\ncom/cobblemon/mod/common/command/argument/PartySlotArgumentType\n*L\n46#1:67\n46#1:68,3\n*E\n"})
public final class PartySlotArgumentType
implements ArgumentType<Integer> {
    @NotNull
    public static final Companion Companion;
    private static final int MIN = 1;
    private static final int MAX = 6;
    @NotNull
    private static final List<String> EXAMPLES;
    @NotNull
    private static final DynamicCommandExceptionType INVALID_SLOT;

    @NotNull
    public Integer parse(@NotNull StringReader reader) {
        Intrinsics.checkNotNullParameter((Object)reader, (String)"reader");
        int slot = reader.readInt();
        if (slot < 1) {
            CommandSyntaxException commandSyntaxException = CommandSyntaxException.BUILT_IN_EXCEPTIONS.integerTooLow().createWithContext((ImmutableStringReader)reader, (Object)slot, (Object)1);
            Intrinsics.checkNotNullExpressionValue((Object)commandSyntaxException, (String)"BUILT_IN_EXCEPTIONS.inte\u2026ontext(reader, slot, MIN)");
            throw (Throwable)commandSyntaxException;
        }
        if (slot > 6) {
            CommandSyntaxException commandSyntaxException = CommandSyntaxException.BUILT_IN_EXCEPTIONS.integerTooHigh().createWithContext((ImmutableStringReader)reader, (Object)slot, (Object)6);
            Intrinsics.checkNotNullExpressionValue((Object)commandSyntaxException, (String)"BUILT_IN_EXCEPTIONS.inte\u2026ontext(reader, slot, MAX)");
            throw (Throwable)commandSyntaxException;
        }
        return slot;
    }

    @NotNull
    public <S> CompletableFuture<Suggestions> listSuggestions(@NotNull CommandContext<S> context, @NotNull SuggestionsBuilder builder) {
        Intrinsics.checkNotNullParameter(context, (String)"context");
        Intrinsics.checkNotNullParameter((Object)builder, (String)"builder");
        CompletableFuture completableFuture = SharedSuggestionProvider.m_82970_((Iterable)EXAMPLES, (SuggestionsBuilder)builder);
        Intrinsics.checkNotNullExpressionValue((Object)completableFuture, (String)"suggestMatching(EXAMPLES, builder)");
        return completableFuture;
    }

    @NotNull
    public List<String> getExamples() {
        return EXAMPLES;
    }

    private static final Message INVALID_SLOT$lambda$1(Object slot) {
        Object[] objectArray = new Object[1];
        Intrinsics.checkNotNullExpressionValue((Object)slot, (String)"slot");
        objectArray[0] = slot;
        MutableComponent mutableComponent = LocalizationUtilsKt.commandLang("general.invalid-party-slot", objectArray);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"commandLang(\"general.invalid-party-slot\", slot)");
        return (Message)TextKt.red(mutableComponent);
    }

    /*
     * WARNING - void declaration
     */
    static {
        void var3_3;
        void $this$mapTo$iv$iv;
        Companion = new Companion(null);
        Iterable $this$map$iv = (Iterable)new IntRange(1, 6);
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        Iterator iterator = $this$mapTo$iv$iv.iterator();
        while (iterator.hasNext()) {
            void it;
            int item$iv$iv;
            int n = item$iv$iv = ((IntIterator)iterator).nextInt();
            Collection collection = destination$iv$iv;
            boolean bl = false;
            collection.add(String.valueOf((int)it));
        }
        EXAMPLES = (List)var3_3;
        INVALID_SLOT = new DynamicCommandExceptionType(PartySlotArgumentType::INVALID_SLOT$lambda$1);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u001b\u0010\u001cJ)\u0010\b\u001a\u00020\u0007\"\u0004\b\u0000\u0010\u00022\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u00032\u0006\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0004\b\b\u0010\tJ1\u0010\f\u001a\u00020\u0007\"\u0004\b\u0000\u0010\u00022\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\n\u00a2\u0006\u0004\b\f\u0010\rJ\r\u0010\u000f\u001a\u00020\u000e\u00a2\u0006\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00050\u00118\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0015\u001a\u00020\u00148\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0018\u001a\u00020\u00178\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0018\u0010\u0019R\u0014\u0010\u001a\u001a\u00020\u00178\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u001a\u0010\u0019\u00a8\u0006\u001d"}, d2={"Lcom/cobblemon/mod/common/command/argument/PartySlotArgumentType$Companion;", "", "S", "Lcom/mojang/brigadier/context/CommandContext;", "context", "", "name", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getPokemon", "(Lcom/mojang/brigadier/context/CommandContext;Ljava/lang/String;)Lcom/cobblemon/mod/common/pokemon/Pokemon;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "getPokemonOf", "(Lcom/mojang/brigadier/context/CommandContext;Ljava/lang/String;Lnet/minecraft/server/level/ServerPlayer;)Lcom/cobblemon/mod/common/pokemon/Pokemon;", "Lcom/cobblemon/mod/common/command/argument/PartySlotArgumentType;", "partySlot", "()Lcom/cobblemon/mod/common/command/argument/PartySlotArgumentType;", "", "EXAMPLES", "Ljava/util/List;", "Lcom/mojang/brigadier/exceptions/DynamicCommandExceptionType;", "INVALID_SLOT", "Lcom/mojang/brigadier/exceptions/DynamicCommandExceptionType;", "", "MAX", "I", "MIN", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final PartySlotArgumentType partySlot() {
            return new PartySlotArgumentType();
        }

        @NotNull
        public final <S> Pokemon getPokemon(@NotNull CommandContext<S> context, @NotNull String name) {
            Intrinsics.checkNotNullParameter(context, (String)"context");
            Intrinsics.checkNotNullParameter((Object)name, (String)"name");
            Integer slot = (Integer)context.getArgument(name, Integer.TYPE);
            Object object = context.getSource();
            CommandSourceStack commandSourceStack = object instanceof CommandSourceStack ? (CommandSourceStack)object : null;
            if (commandSourceStack == null) {
                CommandSyntaxException commandSyntaxException = CommandSourceStack.f_81286_.create();
                Intrinsics.checkNotNullExpressionValue((Object)commandSyntaxException, (String)"REQUIRES_PLAYER_EXCEPTION.create()");
                throw (Throwable)commandSyntaxException;
            }
            CommandSourceStack source = commandSourceStack;
            Entity entity2 = source.m_81373_();
            ServerPlayer serverPlayer = entity2 instanceof ServerPlayer ? (ServerPlayer)entity2 : null;
            if (serverPlayer == null) {
                CommandSyntaxException commandSyntaxException = CommandSourceStack.f_81286_.create();
                Intrinsics.checkNotNullExpressionValue((Object)commandSyntaxException, (String)"REQUIRES_PLAYER_EXCEPTION.create()");
                throw (Throwable)commandSyntaxException;
            }
            ServerPlayer player = serverPlayer;
            PlayerPartyStore party = Cobblemon.INSTANCE.getStorage().getParty(player);
            Pokemon pokemon = party.get(slot - 1);
            if (pokemon == null) {
                CommandSyntaxException commandSyntaxException = INVALID_SLOT.create((Object)slot);
                Intrinsics.checkNotNullExpressionValue((Object)commandSyntaxException, (String)"INVALID_SLOT.create(slot)");
                throw (Throwable)commandSyntaxException;
            }
            return pokemon;
        }

        @NotNull
        public final <S> Pokemon getPokemonOf(@NotNull CommandContext<S> context, @NotNull String name, @NotNull ServerPlayer player) {
            Intrinsics.checkNotNullParameter(context, (String)"context");
            Intrinsics.checkNotNullParameter((Object)name, (String)"name");
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            Integer slot = (Integer)context.getArgument(name, Integer.TYPE);
            PlayerPartyStore party = Cobblemon.INSTANCE.getStorage().getParty(player);
            Pokemon pokemon = party.get(slot - 1);
            if (pokemon == null) {
                CommandSyntaxException commandSyntaxException = INVALID_SLOT.create((Object)slot);
                Intrinsics.checkNotNullExpressionValue((Object)commandSyntaxException, (String)"INVALID_SLOT.create(slot)");
                throw (Throwable)commandSyntaxException;
            }
            return pokemon;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

