/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.arguments.StringArgumentType
 *  com.mojang.brigadier.builder.ArgumentBuilder
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.tree.LiteralCommandNode
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands
 *  net.minecraft.commands.arguments.DimensionArgument
 *  net.minecraft.commands.arguments.EntityArgument
 *  net.minecraft.commands.arguments.ResourceLocationArgument
 *  net.minecraft.commands.arguments.coordinates.Vec3Argument
 *  net.minecraft.core.BlockPos
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.effect.SpawnSnowstormEntityParticlePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.effect.SpawnSnowstormParticlePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CommandUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.EntityExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.Vec3ExtensionsKt;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.DimensionArgument;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0016\u0010\u0017J7\u0010\r\u001a\u00020\f2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\nH\u0002\u00a2\u0006\u0004\b\r\u0010\u000eJ/\u0010\r\u001a\u00020\f2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u000fH\u0002\u00a2\u0006\u0004\b\r\u0010\u0010J\u001b\u0010\u0014\u001a\u00020\u00132\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00020\u0011\u00a2\u0006\u0004\b\u0014\u0010\u0015\u00a8\u0006\u0018"}, d2={"Lcom/cobblemon/mod/common/command/BedrockParticleCommand;", "", "Lnet/minecraft/commands/CommandSourceStack;", "source", "Lnet/minecraft/resources/ResourceLocation;", "effectId", "Lnet/minecraft/server/level/ServerLevel;", "world", "Lnet/minecraft/world/entity/Entity;", "target", "", "locator", "", "execute", "(Lnet/minecraft/commands/CommandSourceStack;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/Entity;Ljava/lang/String;)I", "Lnet/minecraft/world/phys/Vec3;", "(Lnet/minecraft/commands/CommandSourceStack;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/phys/Vec3;)I", "Lcom/mojang/brigadier/CommandDispatcher;", "dispatcher", "", "register", "(Lcom/mojang/brigadier/CommandDispatcher;)V", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nBedrockParticleCommand.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BedrockParticleCommand.kt\ncom/cobblemon/mod/common/command/BedrockParticleCommand\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,88:1\n1855#2,2:89\n1855#2,2:91\n1#3:93\n*S KotlinDebug\n*F\n+ 1 BedrockParticleCommand.kt\ncom/cobblemon/mod/common/command/BedrockParticleCommand\n*L\n77#1:89,2\n84#1:91,2\n*E\n"})
public final class BedrockParticleCommand {
    @NotNull
    public static final BedrockParticleCommand INSTANCE = new BedrockParticleCommand();

    private BedrockParticleCommand() {
    }

    public final void register(@NotNull CommandDispatcher<CommandSourceStack> dispatcher) {
        Intrinsics.checkNotNullParameter(dispatcher, (String)"dispatcher");
        LiteralArgumentBuilder literalArgumentBuilder = Commands.m_82127_((String)"bedrockparticle");
        Intrinsics.checkNotNullExpressionValue((Object)literalArgumentBuilder, (String)"literal(\"bedrockparticle\")");
        LiteralCommandNode command = dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)PermissionUtilsKt.permission$default((ArgumentBuilder)literalArgumentBuilder, CobblemonPermissions.INSTANCE.getBEDROCK_PARTICLE(), false, 2, null)).then(((RequiredArgumentBuilder)Commands.m_82129_((String)"effect", (ArgumentType)((ArgumentType)ResourceLocationArgument.m_106984_())).then(((RequiredArgumentBuilder)Commands.m_82129_((String)"target", (ArgumentType)((ArgumentType)EntityArgument.m_91460_())).executes(BedrockParticleCommand::register$lambda$1)).then(Commands.m_82129_((String)"locator", (ArgumentType)((ArgumentType)StringArgumentType.word())).executes(BedrockParticleCommand::register$lambda$3)))).then(Commands.m_82129_((String)"world", (ArgumentType)((ArgumentType)DimensionArgument.m_88805_())).then(Commands.m_82129_((String)"pos", (ArgumentType)((ArgumentType)Vec3Argument.m_120841_())).executes(BedrockParticleCommand::register$lambda$4)))));
        Intrinsics.checkNotNullExpressionValue((Object)command, (String)"command");
        dispatcher.register(CommandUtilsKt.alias(command, "bedrockparticle"));
    }

    private final int execute(CommandSourceStack source, ResourceLocation effectId, ServerLevel world, Vec3 target) {
        BlockPos pos = Vec3ExtensionsKt.toBlockPos(target);
        List nearbyPlayers2 = world.m_8795_(arg_0 -> BedrockParticleCommand.execute$lambda$5((Function1)new Function1<ServerPlayer, Boolean>(pos){
            final /* synthetic */ BlockPos $pos;
            {
                this.$pos = $pos;
                super(1);
            }

            @NotNull
            public final Boolean invoke(ServerPlayer it) {
                Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
                return EntityExtensionsKt.distanceTo((Entity)it, this.$pos) < 1000.0;
            }
        }, arg_0));
        Intrinsics.checkNotNullExpressionValue((Object)nearbyPlayers2, (String)"nearbyPlayers");
        Iterable $this$forEach$iv = nearbyPlayers2;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            ServerPlayer player = (ServerPlayer)element$iv;
            boolean bl = false;
            Intrinsics.checkNotNullExpressionValue((Object)player, (String)"player");
            CobblemonNetwork.INSTANCE.sendPacket(player, new SpawnSnowstormParticlePacket(effectId, target));
        }
        return 1;
    }

    private final int execute(CommandSourceStack source, ResourceLocation effectId, ServerLevel world, Entity target, String locator) {
        BlockPos pos = target.m_20183_();
        List nearbyPlayers2 = world.m_8795_(arg_0 -> BedrockParticleCommand.execute$lambda$7((Function1)new Function1<ServerPlayer, Boolean>(pos){
            final /* synthetic */ BlockPos $pos;
            {
                this.$pos = $pos;
                super(1);
            }

            @NotNull
            public final Boolean invoke(ServerPlayer it) {
                Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
                Entity entity2 = (Entity)it;
                BlockPos blockPos2 = this.$pos;
                Intrinsics.checkNotNullExpressionValue((Object)blockPos2, (String)"pos");
                return EntityExtensionsKt.distanceTo(entity2, blockPos2) < 1000.0;
            }
        }, arg_0));
        Intrinsics.checkNotNullExpressionValue((Object)nearbyPlayers2, (String)"nearbyPlayers");
        Iterable $this$forEach$iv = nearbyPlayers2;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            ServerPlayer player = (ServerPlayer)element$iv;
            boolean bl = false;
            Intrinsics.checkNotNullExpressionValue((Object)player, (String)"player");
            CobblemonNetwork.INSTANCE.sendPacket(player, new SpawnSnowstormEntityParticlePacket(effectId, target.m_19879_(), locator));
        }
        return 1;
    }

    /*
     * WARNING - void declaration
     */
    private static final int register$lambda$1(CommandContext it) {
        ResourceLocation effectId = ResourceLocationArgument.m_107011_((CommandContext)it, (String)"effect");
        Collection entities2 = EntityArgument.m_91461_((CommandContext)it, (String)"target");
        Intrinsics.checkNotNullExpressionValue((Object)entities2, (String)"entities");
        Iterable iterable = entities2;
        int n = 0;
        for (Object t : iterable) {
            void entity2;
            Entity entity3 = (Entity)t;
            int n2 = n;
            boolean bl = false;
            Object object = it.getSource();
            Intrinsics.checkNotNullExpressionValue((Object)object, (String)"it.source");
            CommandSourceStack commandSourceStack = (CommandSourceStack)object;
            Intrinsics.checkNotNullExpressionValue((Object)effectId, (String)"effectId");
            Level level = entity2.m_9236_();
            Intrinsics.checkNotNull((Object)level, (String)"null cannot be cast to non-null type net.minecraft.server.world.ServerWorld");
            ServerLevel serverLevel = (ServerLevel)level;
            Vec3 vec3 = entity2.m_20182_();
            Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"entity.pos");
            int n3 = INSTANCE.execute(commandSourceStack, effectId, serverLevel, vec3);
            n = n2 + n3;
        }
        return n;
    }

    /*
     * WARNING - void declaration
     */
    private static final int register$lambda$3(CommandContext it) {
        ResourceLocation effectId = ResourceLocationArgument.m_107011_((CommandContext)it, (String)"effect");
        Collection entities2 = EntityArgument.m_91461_((CommandContext)it, (String)"target");
        String locator = StringArgumentType.getString((CommandContext)it, (String)"locator");
        Intrinsics.checkNotNullExpressionValue((Object)entities2, (String)"entities");
        Iterable iterable = entities2;
        int n = 0;
        for (Object t : iterable) {
            void entity2;
            Entity entity3 = (Entity)t;
            int n2 = n;
            boolean bl = false;
            Object object = it.getSource();
            Intrinsics.checkNotNullExpressionValue((Object)object, (String)"it.source");
            CommandSourceStack commandSourceStack = (CommandSourceStack)object;
            Intrinsics.checkNotNullExpressionValue((Object)effectId, (String)"effectId");
            Level level = entity2.m_9236_();
            Intrinsics.checkNotNull((Object)level, (String)"null cannot be cast to non-null type net.minecraft.server.world.ServerWorld");
            ServerLevel serverLevel = (ServerLevel)level;
            Intrinsics.checkNotNullExpressionValue((Object)entity2, (String)"entity");
            Intrinsics.checkNotNullExpressionValue((Object)locator, (String)"locator");
            int n3 = INSTANCE.execute(commandSourceStack, effectId, serverLevel, (Entity)entity2, locator);
            n = n2 + n3;
        }
        return n;
    }

    private static final int register$lambda$4(CommandContext it) {
        ResourceLocation effectId = ResourceLocationArgument.m_107011_((CommandContext)it, (String)"effect");
        ServerLevel world = DimensionArgument.m_88808_((CommandContext)it, (String)"world");
        Vec3 pos = Vec3Argument.m_120844_((CommandContext)it, (String)"pos");
        Object object = it.getSource();
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"it.source");
        CommandSourceStack commandSourceStack = (CommandSourceStack)object;
        Intrinsics.checkNotNullExpressionValue((Object)effectId, (String)"effectId");
        Intrinsics.checkNotNull((Object)world, (String)"null cannot be cast to non-null type net.minecraft.server.world.ServerWorld");
        Intrinsics.checkNotNullExpressionValue((Object)pos, (String)"pos");
        return INSTANCE.execute(commandSourceStack, effectId, world, pos);
    }

    private static final boolean execute$lambda$5(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Boolean)$tmp0.invoke(p0);
    }

    private static final boolean execute$lambda$7(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Boolean)$tmp0.invoke(p0);
    }
}

