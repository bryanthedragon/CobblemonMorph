/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.serialization.Codec
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.arguments.StringRepresentableArgument
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.StoreType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.serialization.Codec;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.StringRepresentableArgument;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u00052\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0005B\u0007\u00a2\u0006\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0006"}, d2={"Lcom/cobblemon/mod/common/command/argument/PokemonStoreArgumentType;", "Lnet/minecraft/commands/arguments/StringRepresentableArgument;", "Lcom/cobblemon/mod/common/command/argument/StoreType;", "<init>", "()V", "Companion", "common"})
public final class PokemonStoreArgumentType
extends StringRepresentableArgument<StoreType> {
    @NotNull
    public static final Companion Companion = new Companion(null);

    public PokemonStoreArgumentType() {
        super((Codec)StoreType.Companion.getCODEC(), StoreType::values);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\r\u0010\u000eJ\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004J#\u0010\u000b\u001a\u00020\n2\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\t\u001a\u00020\b\u00a2\u0006\u0004\b\u000b\u0010\f\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/command/argument/PokemonStoreArgumentType$Companion;", "", "Lcom/cobblemon/mod/common/command/argument/PokemonStoreArgumentType;", "pokemonStore", "()Lcom/cobblemon/mod/common/command/argument/PokemonStoreArgumentType;", "Lcom/mojang/brigadier/context/CommandContext;", "Lnet/minecraft/commands/CommandSourceStack;", "context", "", "id", "Lcom/cobblemon/mod/common/command/argument/StoreType;", "pokemonStoreFrom", "(Lcom/mojang/brigadier/context/CommandContext;Ljava/lang/String;)Lcom/cobblemon/mod/common/command/argument/StoreType;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final PokemonStoreArgumentType pokemonStore() {
            return new PokemonStoreArgumentType();
        }

        @NotNull
        public final StoreType pokemonStoreFrom(@NotNull CommandContext<CommandSourceStack> context, @NotNull String id) {
            Intrinsics.checkNotNullParameter(context, (String)"context");
            Intrinsics.checkNotNullParameter((Object)id, (String)"id");
            Object object = context.getArgument(id, StoreType.class);
            Intrinsics.checkNotNullExpressionValue((Object)object, (String)"context.getArgument(id, StoreType::class.java)");
            return (StoreType)((Object)object);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

