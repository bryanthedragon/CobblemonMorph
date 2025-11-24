/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.builder.ArgumentBuilder
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.comparisons.ComparisonsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.Level
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.CobblemonWorldSpawnerManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnBucket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnCause;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.WorldSlice;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.AreaSpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.prospecting.SpawningProspector;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.PlayerSpawner;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.Spawner;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.SpawningArea;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.SpawnBucketArgumentType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PermissionUtilsKt;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u001d\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0006\u0010\u0007J%\u0010\u000e\u001a\u00020\r2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\u000bH\u0002\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u001b\u0010\u0013\u001a\u00020\u00122\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\t0\u0010\u00a2\u0006\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0015\u001a\u00020\u00048\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0017\u001a\u00020\u00048\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0017\u0010\u0016R\u0014\u0010\u0018\u001a\u00020\u00048\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0018\u0010\u0016R\u0017\u0010\u001a\u001a\u00020\u00198\u0006\u00a2\u0006\f\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u001c\u0010\u001d\u00a8\u0006 "}, d2={"Lcom/cobblemon/mod/common/command/CheckSpawnsCommand;", "", "Lnet/minecraft/network/chat/MutableComponent;", "name", "", "percentage", "applyColour", "(Lnet/minecraft/network/chat/MutableComponent;F)Lnet/minecraft/network/chat/MutableComponent;", "Lcom/mojang/brigadier/context/CommandContext;", "Lnet/minecraft/commands/CommandSourceStack;", "context", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "execute", "(Lcom/mojang/brigadier/context/CommandContext;Lnet/minecraft/server/level/ServerPlayer;)I", "Lcom/mojang/brigadier/CommandDispatcher;", "dispatcher", "", "register", "(Lcom/mojang/brigadier/CommandDispatcher;)V", "PURPLE_THRESHOLD", "F", "RED_THRESHOLD", "YELLOW_THRESHOLD", "Ljava/text/DecimalFormat;", "df", "Ljava/text/DecimalFormat;", "getDf", "()Ljava/text/DecimalFormat;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nCheckSpawnsCommand.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CheckSpawnsCommand.kt\ncom/cobblemon/mod/common/command/CheckSpawnsCommand\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,128:1\n1855#2,2:129\n1054#2:131\n1855#2,2:132\n*S KotlinDebug\n*F\n+ 1 CheckSpawnsCommand.kt\ncom/cobblemon/mod/common/command/CheckSpawnsCommand\n*L\n84#1:129,2\n95#1:131\n97#1:132,2\n*E\n"})
public final class CheckSpawnsCommand {
    @NotNull
    public static final CheckSpawnsCommand INSTANCE = new CheckSpawnsCommand();
    public static final float PURPLE_THRESHOLD = 0.01f;
    public static final float RED_THRESHOLD = 0.1f;
    public static final float YELLOW_THRESHOLD = 5.0f;
    @NotNull
    private static final DecimalFormat df = new DecimalFormat("#.##");

    private CheckSpawnsCommand() {
    }

    @NotNull
    public final DecimalFormat getDf() {
        return df;
    }

    public final void register(@NotNull CommandDispatcher<CommandSourceStack> dispatcher) {
        Intrinsics.checkNotNullParameter(dispatcher, (String)"dispatcher");
        LiteralArgumentBuilder literalArgumentBuilder = Commands.m_82127_((String)"checkspawn");
        Intrinsics.checkNotNullExpressionValue((Object)literalArgumentBuilder, (String)"literal(\"checkspawn\")");
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)PermissionUtilsKt.permission$default((ArgumentBuilder)literalArgumentBuilder, CobblemonPermissions.INSTANCE.getCHECKSPAWNS(), false, 2, null)).then(((RequiredArgumentBuilder)Commands.m_82129_((String)"bucket", (ArgumentType)SpawnBucketArgumentType.Companion.spawnBucket()).requires(CheckSpawnsCommand::register$lambda$0)).executes(CheckSpawnsCommand::register$lambda$1)));
    }

    private final int execute(CommandContext<CommandSourceStack> context, ServerPlayer player) {
        if (!Cobblemon.INSTANCE.getConfig().getEnableSpawning()) {
            return 0;
        }
        PlayerSpawner playerSpawner = CobblemonWorldSpawnerManager.INSTANCE.getSpawnersForPlayers().get(player.m_20148_());
        if (playerSpawner == null) {
            return 1;
        }
        PlayerSpawner spawner = playerSpawner;
        SpawnBucket bucket = SpawnBucketArgumentType.Companion.getSpawnBucket(context, "bucket");
        SpawnCause cause = new SpawnCause(spawner, bucket, (Entity)player);
        SpawningProspector spawningProspector = spawner.getProspector();
        Spawner spawner2 = spawner;
        Level level = player.m_9236_();
        Intrinsics.checkNotNull((Object)level, (String)"null cannot be cast to non-null type net.minecraft.server.world.ServerWorld");
        WorldSlice slice = spawningProspector.prospect(spawner2, new SpawningArea(cause, (ServerLevel)level, Mth.m_14165_((double)(player.m_20185_() - (double)((float)Cobblemon.INSTANCE.getConfig().getWorldSliceDiameter() / 2.0f))), Mth.m_14165_((double)(player.m_20186_() - (double)((float)Cobblemon.INSTANCE.getConfig().getWorldSliceHeight() / 2.0f))), Mth.m_14165_((double)(player.m_20189_() - (double)((float)Cobblemon.INSTANCE.getConfig().getWorldSliceDiameter() / 2.0f))), Cobblemon.INSTANCE.getConfig().getWorldSliceDiameter(), Cobblemon.INSTANCE.getConfig().getWorldSliceHeight(), Cobblemon.INSTANCE.getConfig().getWorldSliceDiameter()));
        List<AreaSpawningContext> contexts = spawner.getResolver().resolve(spawner, spawner.getContextCalculators(), slice);
        Map<SpawnDetail, Float> spawnProbabilities = spawner.getSpawningSelector().getProbabilities(spawner, contexts);
        Map spawnNames = new LinkedHashMap();
        Map namedProbabilities = new LinkedHashMap();
        Iterable $this$forEach$iv = spawnProbabilities.entrySet();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Map.Entry it = (Map.Entry)element$iv;
            boolean bl = false;
            MutableComponent nameText = ((SpawnDetail)it.getKey()).getName();
            String nameString = nameText.getString();
            if (!spawnNames.containsKey(nameString)) {
                Intrinsics.checkNotNullExpressionValue((Object)nameString, (String)"nameString");
                spawnNames.put(nameString, ((SpawnDetail)it.getKey()).getName());
            }
            Object v = spawnNames.get(nameString);
            Intrinsics.checkNotNull(v);
            MutableComponent standardizedNameText = (MutableComponent)v;
            Map map = namedProbabilities;
            Float f = (Float)namedProbabilities.get(standardizedNameText);
            Float f2 = Float.valueOf((f != null ? f.floatValue() : 0.0f) + ((Number)it.getValue()).floatValue());
            map.put(standardizedNameText, f2);
        }
        Iterable $this$sortedByDescending$iv = namedProbabilities.entrySet();
        boolean $i$f$sortedByDescending = false;
        List sortedEntries = CollectionsKt.sortedWith((Iterable)$this$sortedByDescending$iv, (Comparator)new Comparator(){

            public final int compare(T a, T b) {
                Map.Entry it = (Map.Entry)b;
                boolean bl = false;
                Comparable comparable = (Float)it.getValue();
                it = (Map.Entry)a;
                Comparable comparable2 = comparable;
                bl = false;
                return ComparisonsKt.compareValues((Comparable)comparable2, (Comparable)((Float)it.getValue()));
            }
        });
        List messages = new ArrayList();
        Iterable $this$forEach$iv2 = sortedEntries;
        boolean $i$f$forEach2 = false;
        for (Object element$iv : $this$forEach$iv2) {
            Map.Entry entry = (Map.Entry)element$iv;
            boolean bl = false;
            MutableComponent name = (MutableComponent)entry.getKey();
            float percentage = ((Number)entry.getValue()).floatValue();
            MutableComponent message = TextKt.plus(TextKt.plus(name, ": "), (Component)INSTANCE.applyColour(TextKt.text(df.format(Float.valueOf(percentage)) + "%"), percentage));
            messages.add(message);
        }
        if (messages.isEmpty()) {
            MutableComponent mutableComponent = LocalizationUtilsKt.lang("command.checkspawns.nothing", new Object[0]);
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"command.checkspawns.nothing\")");
            player.m_213846_((Component)TextKt.red(mutableComponent));
        } else {
            MutableComponent mutableComponent = LocalizationUtilsKt.lang("command.checkspawns.spawns", new Object[0]);
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"command.checkspawns.spawns\")");
            player.m_213846_((Component)TextKt.underline(mutableComponent));
            MutableComponent msg = (MutableComponent)messages.get(0);
            for (MutableComponent nextMessage : messages.subList(1, messages.size())) {
                TextKt.add(msg, (Component)TextKt.plus(TextKt.text(", "), (Component)nextMessage));
            }
            player.m_213846_((Component)msg);
        }
        return 1;
    }

    @NotNull
    public final MutableComponent applyColour(@NotNull MutableComponent name, float percentage) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        return percentage < 0.01f ? TextKt.lightPurple(name) : (percentage < 0.1f ? TextKt.red(name) : (percentage < 5.0f ? TextKt.yellow(name) : TextKt.green(name)));
    }

    private static final boolean register$lambda$0(CommandSourceStack it) {
        return it.m_230896_() != null;
    }

    private static final int register$lambda$1(CommandContext it) {
        Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
        ServerPlayer serverPlayer = ((CommandSourceStack)it.getSource()).m_81375_();
        Intrinsics.checkNotNullExpressionValue((Object)serverPlayer, (String)"it.source.playerOrThrow");
        return INSTANCE.execute((CommandContext<CommandSourceStack>)it, serverPlayer);
    }
}

