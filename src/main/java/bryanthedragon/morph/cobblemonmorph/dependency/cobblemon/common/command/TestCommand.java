/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.SetsKt
 *  kotlin.io.FilesKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.Charsets
 *  kotlin.text.StringsKt
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.entity.EntityTypeTest
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonEntities;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.Abilities;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item.ability.AbilityChanger;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ClientTaskTracker;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ScheduledTask;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingFunctionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ServerTaskTracker;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleFormat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleSide;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor.PlayerBattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor.PokemonBattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.TestCommand;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.generic.GenericBedrockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.animation.PlayPoseableAnimationPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeStartedPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.ActiveTrade;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.DummyTradeParticipant;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.PlayerTradeParticipant;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.TradeParticipant;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.StringExtensionsKt;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\b\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b,\u0010\nJ\u001d\u0010\u0006\u001a\u00020\u00052\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007J\r\u0010\t\u001a\u00020\b\u00a2\u0006\u0004\b\t\u0010\nJ\u001b\u0010\r\u001a\u00020\b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00030\u000b\u00a2\u0006\u0004\b\r\u0010\u000eJ\u001d\u0010\u000f\u001a\u00020\b2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0002\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u000f\u0010\u0012\u001a\u00020\u0011H\u0002\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u000f\u0010\u0014\u001a\u00020\u0011H\u0002\u00a2\u0006\u0004\b\u0014\u0010\u0013J\u001d\u0010\u0015\u001a\u00020\b2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0002\u00a2\u0006\u0004\b\u0015\u0010\u0010J\u000f\u0010\u0016\u001a\u00020\u0011H\u0002\u00a2\u0006\u0004\b\u0016\u0010\u0013J\u000f\u0010\u0017\u001a\u00020\u0011H\u0002\u00a2\u0006\u0004\b\u0017\u0010\u0013J\u000f\u0010\u0018\u001a\u00020\u0011H\u0002\u00a2\u0006\u0004\b\u0018\u0010\u0013J\u000f\u0010\u0019\u001a\u00020\u0011H\u0002\u00a2\u0006\u0004\b\u0019\u0010\u0013J\u0017\u0010\u001c\u001a\u00020\b2\u0006\u0010\u001b\u001a\u00020\u001aH\u0002\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u000f\u0010\u001e\u001a\u00020\bH\u0002\u00a2\u0006\u0004\b\u001e\u0010\nR\"\u0010\u001f\u001a\u00020\u00058\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001f\u0010 \u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R$\u0010&\u001a\u0004\u0018\u00010%8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b&\u0010'\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+\u00a8\u0006-"}, d2={"Lcom/cobblemon/mod/common/command/TestCommand;", "", "Lcom/mojang/brigadier/context/CommandContext;", "Lnet/minecraft/commands/CommandSourceStack;", "context", "", "execute", "(Lcom/mojang/brigadier/context/CommandContext;)I", "", "readBerryDataFromCSV", "()V", "Lcom/mojang/brigadier/CommandDispatcher;", "dispatcher", "register", "(Lcom/mojang/brigadier/CommandDispatcher;)V", "testAbilitiesBetweenEvolution", "(Lcom/mojang/brigadier/context/CommandContext;)V", "Lnet/minecraft/network/chat/Component;", "testAbilityCapsule", "()Lnet/minecraft/network/chat/Component;", "testAbilityPatch", "testClosestBattle", "testForcedAbility", "testHiddenAbilityThroughoutEvolutions", "testIllegalAbilityNonForced", "testMiddleStageSingleAbility", "Lnet/minecraft/server/level/ServerPlayer;", "playerEntity", "testTrade", "(Lnet/minecraft/server/level/ServerPlayer;)V", "testUpdate", "lastDebugId", "I", "getLastDebugId", "()I", "setLastDebugId", "(I)V", "Lcom/cobblemon/mod/common/trade/ActiveTrade;", "trade", "Lcom/cobblemon/mod/common/trade/ActiveTrade;", "getTrade", "()Lcom/cobblemon/mod/common/trade/ActiveTrade;", "setTrade", "(Lcom/cobblemon/mod/common/trade/ActiveTrade;)V", "<init>", "common"})
@SourceDebugExtension(value={"SMAP\nTestCommand.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TestCommand.kt\ncom/cobblemon/mod/common/command/TestCommand\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,396:1\n1855#2,2:397\n*S KotlinDebug\n*F\n+ 1 TestCommand.kt\ncom/cobblemon/mod/common/command/TestCommand\n*L\n134#1:397,2\n*E\n"})
public final class TestCommand {
    @NotNull
    public static final TestCommand INSTANCE = new TestCommand();
    @Nullable
    private static ActiveTrade trade;
    private static int lastDebugId;

    private TestCommand() {
    }

    public final void register(@NotNull CommandDispatcher<CommandSourceStack> dispatcher) {
        Intrinsics.checkNotNullParameter(dispatcher, (String)"dispatcher");
        LiteralArgumentBuilder command = (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_((String)"testcommand").requires(TestCommand::register$lambda$0)).executes(this::execute);
        dispatcher.register(command);
    }

    private final int execute(CommandContext<CommandSourceStack> context) {
        if (!(((CommandSourceStack)context.getSource()).m_81373_() instanceof ServerPlayer)) {
            return 1;
        }
        try {
            GenericBedrockEntity evolutionEntity;
            GenericBedrockEntity genericBedrockEntity;
            Entity entity2 = ((CommandSourceStack)context.getSource()).m_81373_();
            Intrinsics.checkNotNull((Object)entity2, (String)"null cannot be cast to non-null type net.minecraft.server.network.ServerPlayerEntity");
            ServerPlayer player = (ServerPlayer)entity2;
            Level level = player.m_9236_();
            Intrinsics.checkNotNullExpressionValue((Object)level, (String)"player.world");
            GenericBedrockEntity $this$execute_u24lambda_u241 = genericBedrockEntity = (evolutionEntity = new GenericBedrockEntity(level));
            boolean bl = false;
            $this$execute_u24lambda_u241.setCategory(MiscUtilsKt.cobblemonResource("evolution"));
            $this$execute_u24lambda_u241.setColliderHeight(1.5f);
            $this$execute_u24lambda_u241.setColliderWidth(1.5f);
            $this$execute_u24lambda_u241.setScale(1.0f);
            $this$execute_u24lambda_u241.setSyncAge(true);
            $this$execute_u24lambda_u241.m_6034_(player.m_20185_(), player.m_20186_(), player.m_20189_() + (double)4);
            player.m_9236_().m_7967_((Entity)evolutionEntity);
            ClientTaskTracker.INSTANCE.after(0.5f, (Function0<Unit>)((Function0)new Function0<Unit>(player, evolutionEntity){
                final /* synthetic */ ServerPlayer $player;
                final /* synthetic */ GenericBedrockEntity $evolutionEntity;
                {
                    this.$player = $player;
                    this.$evolutionEntity = $evolutionEntity;
                    super(0);
                }

                public final void invoke() {
                    CobblemonNetwork.INSTANCE.sendPacket(this.$player, new PlayPoseableAnimationPacket(this.$evolutionEntity.m_19879_(), SetsKt.setOf((Object)"evolution:animation.evolution.evolution"), SetsKt.emptySet()));
                }
            }));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    @Nullable
    public final ActiveTrade getTrade() {
        return trade;
    }

    public final void setTrade(@Nullable ActiveTrade activeTrade) {
        trade = activeTrade;
    }

    public final int getLastDebugId() {
        return lastDebugId;
    }

    public final void setLastDebugId(int n) {
        lastDebugId = n;
    }

    private final void testClosestBattle(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = ((CommandSourceStack)context.getSource()).m_81375_();
        Intrinsics.checkNotNullExpressionValue((Object)player, (String)"player");
        List cloneTeam = PartyStore.toBattleTeam$default(PlayerExtensionsKt.party(player), true, false, null, 6, null);
        Iterable $this$forEach$iv = cloneTeam;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            BattlePokemon it = (BattlePokemon)element$iv;
            boolean bl = false;
            it.getEffectedPokemon().setLevel(100);
        }
        AABB scanBox = AABB.m_165882_((Vec3)player.m_20182_(), (double)9.0, (double)9.0, (double)9.0);
        List results2 = player.m_9236_().m_142425_((EntityTypeTest)CobblemonEntities.POKEMON, scanBox, arg_0 -> TestCommand.testClosestBattle$lambda$3(testClosestBattle.results.1.INSTANCE, arg_0));
        Intrinsics.checkNotNullExpressionValue((Object)results2, (String)"results");
        PokemonEntity pokemonEntity = (PokemonEntity)CollectionsKt.firstOrNull((List)results2);
        if (pokemonEntity == null) {
            ((CommandSourceStack)context.getSource()).m_81352_((Component)Component.m_237113_((String)"Cannot find any wild Pok\u00e9mon in a 9x9x9 area"));
            return;
        }
        BattleFormat battleFormat = BattleFormat.Companion.getGEN_9_SINGLES();
        BattleActor[] battleActorArray = new BattleActor[1];
        UUID uUID = player.m_20148_();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"player.uuid");
        battleActorArray[0] = new PlayerBattleActor(uUID, cloneTeam);
        BattleSide battleSide = new BattleSide(battleActorArray);
        battleActorArray = new BattleActor[1];
        UUID uUID2 = pokemonEntity.getPokemon().getUuid();
        Intrinsics.checkNotNullExpressionValue((Object)uUID2, (String)"pokemonEntity.pokemon.uuid");
        battleActorArray[0] = new PokemonBattleActor(uUID2, new BattlePokemon(pokemonEntity.getPokemon(), null, null, 6, null), Cobblemon.INSTANCE.getConfig().getDefaultFleeDistance(), null, 8, null);
        BattleRegistry.startBattle$default(BattleRegistry.INSTANCE, battleFormat, battleSide, new BattleSide(battleActorArray), false, 8, null);
    }

    private final void testTrade(ServerPlayer playerEntity) {
        ActiveTrade trade2;
        Object[] objectArray = new Pokemon[]{StringExtensionsKt.toPokemon("pikachu level=30 shiny"), StringExtensionsKt.toPokemon("machop level=15")};
        trade = trade2 = new ActiveTrade(new PlayerTradeParticipant(playerEntity), new DummyTradeParticipant(CollectionsKt.mutableListOf((Object[])objectArray)));
        UUID uUID = trade2.getPlayer2().getUuid();
        MutableComponent mutableComponent = trade2.getPlayer2().getName().m_6881_();
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"trade.player2.name.copy()");
        CobblemonNetwork.INSTANCE.sendPacket(playerEntity, new TradeStartedPacket(uUID, mutableComponent, trade2.getPlayer2().getParty().mapNullPreserving(testTrade.1.INSTANCE)));
        SchedulingFunctionsKt.taskBuilder().interval(0.5f).execute((Function1<? super ScheduledTask, Unit>)((Function1)new Function1<ScheduledTask, Unit>(this, trade2){
            final /* synthetic */ TestCommand this$0;
            final /* synthetic */ ActiveTrade $trade;
            {
                this.this$0 = $receiver;
                this.$trade = $trade;
                super(1);
            }

            public final void invoke(@NotNull ScheduledTask task) {
                Intrinsics.checkNotNullParameter((Object)task, (String)"task");
                if (!Intrinsics.areEqual((Object)this.this$0.getTrade(), (Object)this.$trade)) {
                    task.expire();
                    return;
                }
                TestCommand.access$testUpdate(TestCommand.INSTANCE);
            }
        })).tracker(ServerTaskTracker.INSTANCE).iterations(Integer.MAX_VALUE).build();
    }

    private final void testUpdate() {
        ActiveTrade activeTrade = trade;
        if (activeTrade == null) {
            return;
        }
        ActiveTrade trade2 = activeTrade;
        TradeParticipant tradeParticipant = trade2.getPlayer2();
        Intrinsics.checkNotNull((Object)tradeParticipant, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.DummyTradeParticipant");
        DummyTradeParticipant dummy2 = (DummyTradeParticipant)tradeParticipant;
        int currentDebugId = 0;
        if (lastDebugId != 0) {
            lastDebugId = currentDebugId;
        }
    }

    public final void readBerryDataFromCSV() {
        Gson gson2 = new GsonBuilder().setPrettyPrinting().create();
        List csv = FilesKt.readLines$default((File)new File("scripty/berries.csv"), null, (int)1, null);
        Iterator iterator = csv.iterator();
        iterator.next();
        iterator.next();
        Iterator iterator2 = iterator;
        while (iterator2.hasNext()) {
            String line = (String)iterator2.next();
            String[] stringArray = new String[]{","};
            List cols = StringsKt.split$default((CharSequence)line, (String[])stringArray, (boolean)false, (int)0, (int)6, null);
            String string = ((String)cols.get(1)).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
            String berryName = string + "_berry";
            File file = new File("scripty/old/" + berryName + ".json");
            Charset charset = Charsets.UTF_8;
            JsonObject json = (JsonObject)gson2.fromJson((Reader)new InputStreamReader((InputStream)new FileInputStream(file), charset), JsonObject.class);
            List growthPoints = new ArrayList();
            for (int index = 7; cols.size() > index && !StringsKt.isBlank((CharSequence)((CharSequence)cols.get(index))); index += 6) {
                float posX = Float.parseFloat((String)cols.get(index));
                float posY = Float.parseFloat((String)cols.get(index + 1));
                float posZ = Float.parseFloat((String)cols.get(index + 2));
                float rotX = Float.parseFloat((String)cols.get(index + 3));
                float rotY = Float.parseFloat((String)cols.get(index + 4));
                float rotZ = Float.parseFloat((String)cols.get(index + 5));
                JsonObject position = new JsonObject();
                position.addProperty("x", (Number)Float.valueOf(posX));
                position.addProperty("y", (Number)Float.valueOf(posY));
                position.addProperty("z", (Number)Float.valueOf(posZ));
                JsonObject rotation = new JsonObject();
                rotation.addProperty("x", (Number)Float.valueOf(rotX));
                rotation.addProperty("y", (Number)Float.valueOf(rotY));
                rotation.addProperty("z", (Number)Float.valueOf(rotZ));
                JsonObject obj = new JsonObject();
                obj.add("position", (JsonElement)position);
                obj.add("rotation", (JsonElement)rotation);
                growthPoints.add(obj);
            }
            JsonArray arr = json.getAsJsonArray("growthPoints");
            Intrinsics.checkNotNullExpressionValue((Object)arr, (String)"arr");
            CollectionsKt.removeAll((Iterable)((Iterable)arr), (Function1)readBerryDataFromCSV.1.INSTANCE);
            for (JsonObject point : growthPoints) {
                arr.add((JsonElement)point);
            }
            File file2 = new File("scripty/new/" + berryName + ".json");
            PrintWriter pw = new PrintWriter(file2);
            gson2.toJson((JsonElement)json, (Appendable)pw);
            pw.flush();
            pw.close();
        }
    }

    private final void testAbilitiesBetweenEvolution(CommandContext<CommandSourceStack> context) {
        MutableComponent results2 = Component.m_237113_((String)"Ability test results (Assumed default assets)").m_7220_((Component)Component.m_237113_((String)"\n")).m_7220_(this.testHiddenAbilityThroughoutEvolutions()).m_7220_((Component)Component.m_237113_((String)"\n")).m_7220_(this.testMiddleStageSingleAbility()).m_7220_((Component)Component.m_237113_((String)"\n")).m_7220_(this.testForcedAbility()).m_7220_((Component)Component.m_237113_((String)"\n")).m_7220_(this.testIllegalAbilityNonForced()).m_7220_((Component)Component.m_237113_((String)"\n")).m_7220_(this.testAbilityCapsule()).m_7220_((Component)Component.m_237113_((String)"\n")).m_7220_(this.testAbilityPatch());
        ((CommandSourceStack)context.getSource()).m_243053_((Component)results2);
    }

    private final Component testHiddenAbilityThroughoutEvolutions() {
        Component component;
        Pokemon pokemon = PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "dragonair level=" + Cobblemon.INSTANCE.getConfig().getMaxPokemonLevel() + " hiddenability=true", null, null, 6, null).create();
        Evolution evolution = (Evolution)CollectionsKt.firstOrNull(pokemon.getEvolutions());
        if (evolution == null) {
            MutableComponent mutableComponent = Component.m_237113_((String)"\u2716 Failed to find Dragonair \u00bb Dragonite evolution");
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"literal(\"\u2716 Failed to fin\u2026r \u00bb Dragonite evolution\")");
            return (Component)TextKt.red(mutableComponent);
        }
        Evolution dragonite = evolution;
        dragonite.evolutionMethod(pokemon);
        boolean failed = pokemon.getAbility().getIndex() != 0 || pokemon.getAbility().getPriority() != Priority.LOW || pokemon.getAbility().getForced();
        String symbol = failed ? "\u2716" : "\u2714";
        MutableComponent result = Component.m_237113_((String)(" " + symbol + " Dratini line final Ability(name=" + pokemon.getAbility().getName() + ", priority=" + pokemon.getAbility().getPriority() + ", index=" + pokemon.getAbility().getIndex() + ", forced=" + pokemon.getAbility().getForced() + ")"));
        if (failed) {
            Intrinsics.checkNotNullExpressionValue((Object)result, (String)"result");
            component = (Component)TextKt.red(result);
        } else {
            Intrinsics.checkNotNullExpressionValue((Object)result, (String)"result");
            component = (Component)TextKt.green(result);
        }
        return component;
    }

    private final Component testMiddleStageSingleAbility() {
        Component component;
        Pokemon pokemon = PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "scatterbug level=" + Cobblemon.INSTANCE.getConfig().getMaxPokemonLevel() + " ability=compoundeyes", null, null, 6, null).create();
        Evolution evolution = (Evolution)CollectionsKt.firstOrNull(pokemon.getEvolutions());
        if (evolution == null) {
            MutableComponent mutableComponent = Component.m_237113_((String)"\u2716 Failed to find Scatterbug \u00bb Spewpa evolution");
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"literal(\"\u2716 Failed to fin\u2026rbug \u00bb Spewpa evolution\")");
            return (Component)TextKt.red(mutableComponent);
        }
        Evolution spewpa = evolution;
        spewpa.evolutionMethod(pokemon);
        Evolution evolution2 = (Evolution)CollectionsKt.firstOrNull(pokemon.getEvolutions());
        if (evolution2 == null) {
            MutableComponent mutableComponent = Component.m_237113_((String)"\u2716 Failed to find Spewpa \u00bb Vivillon evolution");
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"literal(\"\u2716 Failed to fin\u2026pa \u00bb Vivillon evolution\")");
            return (Component)TextKt.red(mutableComponent);
        }
        Evolution vivillon = evolution2;
        vivillon.evolutionMethod(pokemon);
        boolean failed = pokemon.getAbility().getIndex() != 1 || pokemon.getAbility().getPriority() != Priority.LOWEST || pokemon.getAbility().getForced();
        String symbol = failed ? "\u2716" : "\u2714";
        MutableComponent result = Component.m_237113_((String)(" " + symbol + " Scatterbug line final Ability(name=" + pokemon.getAbility().getName() + ", priority=" + pokemon.getAbility().getPriority() + ", index=" + pokemon.getAbility().getIndex() + ", forced=" + pokemon.getAbility().getForced() + ")"));
        if (failed) {
            Intrinsics.checkNotNullExpressionValue((Object)result, (String)"result");
            component = (Component)TextKt.red(result);
        } else {
            Intrinsics.checkNotNullExpressionValue((Object)result, (String)"result");
            component = (Component)TextKt.green(result);
        }
        return component;
    }

    private final Component testForcedAbility() {
        Component component;
        Pokemon pokemon = PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "magikarp level=" + Cobblemon.INSTANCE.getConfig().getMaxPokemonLevel() + " ability=adaptability", null, null, 6, null).create();
        Evolution evolution = (Evolution)CollectionsKt.firstOrNull(pokemon.getEvolutions());
        if (evolution == null) {
            MutableComponent mutableComponent = Component.m_237113_((String)"\u2716 Failed to find Magikarp \u00bb Gyarados evolution");
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"literal(\"\u2716 Failed to fin\u2026rp \u00bb Gyarados evolution\")");
            return (Component)TextKt.red(mutableComponent);
        }
        Evolution gyarados = evolution;
        gyarados.evolutionMethod(pokemon);
        boolean failed = !pokemon.getAbility().getForced() || !Intrinsics.areEqual((Object)pokemon.getAbility().getTemplate().getName(), (Object)"adaptability");
        String symbol = failed ? "\u2716" : "\u2714";
        MutableComponent result = Component.m_237113_((String)(" " + symbol + " Magikarp line forced Ability(name=" + pokemon.getAbility().getName() + ", priority=" + pokemon.getAbility().getPriority() + ", index=" + pokemon.getAbility().getIndex() + ", forced=" + pokemon.getAbility().getForced() + ")"));
        if (failed) {
            Intrinsics.checkNotNullExpressionValue((Object)result, (String)"result");
            component = (Component)TextKt.red(result);
        } else {
            Intrinsics.checkNotNullExpressionValue((Object)result, (String)"result");
            component = (Component)TextKt.green(result);
        }
        return component;
    }

    private final Component testIllegalAbilityNonForced() {
        Component component;
        Pokemon pokemon = PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "rattata", null, null, 6, null).create();
        pokemon.updateAbility(Abilities.INSTANCE.getOrException("adaptability").create(false));
        boolean failed = !pokemon.getAbility().getForced();
        String symbol = failed ? "\u2716" : "\u2714";
        MutableComponent result = Component.m_237113_((String)(" " + symbol + " Rattata illegal non-forced (name=" + pokemon.getAbility().getName() + ", priority=" + pokemon.getAbility().getPriority() + ", index=" + pokemon.getAbility().getIndex() + ", forced=" + pokemon.getAbility().getForced() + ")"));
        if (failed) {
            Intrinsics.checkNotNullExpressionValue((Object)result, (String)"result");
            component = (Component)TextKt.red(result);
        } else {
            Intrinsics.checkNotNullExpressionValue((Object)result, (String)"result");
            component = (Component)TextKt.green(result);
        }
        return component;
    }

    private final Component testAbilityCapsule() {
        Component component;
        Pokemon pokemon = PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "rattata", null, null, 6, null).create();
        boolean failed = !AbilityChanger.Companion.getCOMMON_ABILITY().performChange(pokemon);
        String symbol = failed ? "\u2716" : "\u2714";
        MutableComponent result = Component.m_237113_((String)(" " + symbol + " Rattata capsule Ability(name=" + pokemon.getAbility().getName() + ", priority=" + pokemon.getAbility().getPriority() + ", index=" + pokemon.getAbility().getIndex() + ", forced=" + pokemon.getAbility().getForced() + ")"));
        if (failed) {
            Intrinsics.checkNotNullExpressionValue((Object)result, (String)"result");
            component = (Component)TextKt.red(result);
        } else {
            Intrinsics.checkNotNullExpressionValue((Object)result, (String)"result");
            component = (Component)TextKt.green(result);
        }
        return component;
    }

    private final Component testAbilityPatch() {
        Component component;
        Pokemon pokemon = PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "magikarp ha=true", null, null, 6, null).create();
        boolean failed = AbilityChanger.Companion.getHIDDEN_ABILITY().performChange(pokemon);
        String symbol = failed ? "\u2716" : "\u2714";
        MutableComponent result = Component.m_237113_((String)(" " + symbol + " Magikarp patch Ability(name=" + pokemon.getAbility().getName() + ", priority=" + pokemon.getAbility().getPriority() + ", index=" + pokemon.getAbility().getIndex() + ", forced=" + pokemon.getAbility().getForced() + ")"));
        if (failed) {
            Intrinsics.checkNotNullExpressionValue((Object)result, (String)"result");
            component = (Component)TextKt.red(result);
        } else {
            Intrinsics.checkNotNullExpressionValue((Object)result, (String)"result");
            component = (Component)TextKt.green(result);
        }
        return component;
    }

    private static final boolean register$lambda$0(CommandSourceStack it) {
        return it.m_6761_(4);
    }

    private static final boolean testClosestBattle$lambda$3(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Boolean)$tmp0.invoke(p0);
    }

    public static final /* synthetic */ void access$testUpdate(TestCommand $this) {
        $this.testUpdate();
    }
}

