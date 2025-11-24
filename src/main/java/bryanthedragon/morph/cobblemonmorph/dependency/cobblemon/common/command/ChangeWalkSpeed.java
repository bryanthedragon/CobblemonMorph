/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.arguments.FloatArgumentType
 *  com.mojang.brigadier.builder.ArgumentBuilder
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.PokemonArgumentType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\r\u0010\u000eJ\u001d\u0010\u0006\u001a\u00020\u00052\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001b\u0010\u000b\u001a\u00020\n2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\b\u00a2\u0006\u0004\b\u000b\u0010\f\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/command/ChangeWalkSpeed;", "", "Lcom/mojang/brigadier/context/CommandContext;", "Lnet/minecraft/commands/CommandSourceStack;", "context", "", "execute", "(Lcom/mojang/brigadier/context/CommandContext;)I", "Lcom/mojang/brigadier/CommandDispatcher;", "dispatcher", "", "register", "(Lcom/mojang/brigadier/CommandDispatcher;)V", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nChangeWalkSpeed.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ChangeWalkSpeed.kt\ncom/cobblemon/mod/common/command/ChangeWalkSpeed\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,48:1\n1#2:49\n*E\n"})
public final class ChangeWalkSpeed {
    @NotNull
    public static final ChangeWalkSpeed INSTANCE = new ChangeWalkSpeed();

    private ChangeWalkSpeed() {
    }

    public final void register(@NotNull CommandDispatcher<CommandSourceStack> dispatcher) {
        Intrinsics.checkNotNullParameter(dispatcher, (String)"dispatcher");
        LiteralArgumentBuilder literalArgumentBuilder = Commands.m_82127_((String)"changewalkspeed");
        Intrinsics.checkNotNullExpressionValue((Object)literalArgumentBuilder, (String)"literal(\"changewalkspeed\")");
        LiteralArgumentBuilder command = (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)PermissionUtilsKt.permission$default((ArgumentBuilder)literalArgumentBuilder, CobblemonPermissions.INSTANCE.getCHANGE_WALK_SPEED(), false, 2, null)).then(Commands.m_82129_((String)"pokemon", (ArgumentType)PokemonArgumentType.Companion.pokemon()).then(Commands.m_82129_((String)"walkSpeed", (ArgumentType)((ArgumentType)FloatArgumentType.floatArg())).executes(this::execute)))).executes(this::execute);
        dispatcher.register(command);
    }

    /*
     * WARNING - void declaration
     */
    private final int execute(CommandContext<CommandSourceStack> context) {
        void it;
        FormData formData;
        Species pkm = PokemonArgumentType.Companion.getPokemon(context, "pokemon");
        float walkSpeed = FloatArgumentType.getFloat(context, (String)"walkSpeed");
        pkm.getBehaviour().getMoving().getWalk().setWalkSpeed(walkSpeed);
        pkm.getForms().clear();
        FormData formData2 = formData = new FormData(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, -1, 3, null);
        List<FormData> list = pkm.getForms();
        boolean bl = false;
        it.initialize(pkm);
        list.add(formData);
        return 1;
    }
}

