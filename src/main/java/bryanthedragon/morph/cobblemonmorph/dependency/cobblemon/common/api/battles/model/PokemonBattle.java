/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.Unit
 *  kotlin.collections.ArraysKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.io.CloseableKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.RangesKt
 *  kotlin.sequences.Sequence
 *  kotlin.sequences.SequencesKt
 *  kotlin.text.Charsets
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoParams;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.QueryStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.DoubleValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle$checkFlee$;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle$checkFlee$lambda$59$;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.ActorType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.EntityBackedBattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.FleeableBattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles.BattleFledEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.progress.EvolutionProgress;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags.CobblemonItemTags;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleCaptureAction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleFormat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleSide;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ForfeitActionResponse;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionResponse;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor.PlayerBattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.BattleDispatch;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResult;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResultKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.WaitDispatch;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.ContextManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.runner.ShowdownService;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleEndPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMessagePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.DefeatEvolutionProgress;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.LastBattleCriticalHitsEvolutionProgress;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.DefeatRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.Charsets;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u00e8\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\b\n\u0002\u0010!\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010#\n\u0002\b\u0013\b\u0016\u0018\u00002\u00020\u0001B'\u0012\b\u0010\u0091\u0001\u001a\u00030\u0090\u0001\u0012\b\u0010\u00ab\u0001\u001a\u00030\u00aa\u0001\u0012\b\u0010\u00af\u0001\u001a\u00030\u00aa\u0001\u00a2\u0006\u0006\b\u00c4\u0001\u0010\u00c5\u0001J\u0015\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0015\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\u0004\b\t\u0010\nJ\u000f\u0010\u000b\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fJ\r\u0010\r\u001a\u00020\b\u00a2\u0006\u0004\b\r\u0010\fJ\u000f\u0010\u000f\u001a\u00020\u000eH\u0002\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0017\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u0011H\u0000\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u001f\u0010\u001a\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u00112\u0006\u0010\u0017\u001a\u00020\u0011H\u0000\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u001b\u0010\u001e\u001a\u00020\b2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001b\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u0015\u0010\u001e\u001a\u00020\b2\u0006\u0010\u001d\u001a\u00020 \u00a2\u0006\u0004\b\u001e\u0010!J\u001b\u0010\"\u001a\u00020\b2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\b0\u001b\u00a2\u0006\u0004\b\"\u0010\u001fJ!\u0010$\u001a\u00020\b2\u0012\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020 0#0\u001b\u00a2\u0006\u0004\b$\u0010\u001fJ\u001b\u0010%\u001a\u00020\b2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001b\u00a2\u0006\u0004\b%\u0010\u001fJ\u0015\u0010%\u001a\u00020\b2\u0006\u0010\u001d\u001a\u00020 \u00a2\u0006\u0004\b%\u0010!J%\u0010(\u001a\u00020\b2\b\b\u0002\u0010'\u001a\u00020&2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\b0\u001b\u00a2\u0006\u0004\b(\u0010)J\u001b\u0010+\u001a\u00020\b2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020\b0\u001b\u00a2\u0006\u0004\b+\u0010\u001fJ\r\u0010,\u001a\u00020\b\u00a2\u0006\u0004\b,\u0010\fJ\u0015\u0010/\u001a\u00020\b2\u0006\u0010.\u001a\u00020-\u00a2\u0006\u0004\b/\u00100J\u0017\u00104\u001a\u0004\u0018\u0001032\u0006\u00102\u001a\u000201\u00a2\u0006\u0004\b4\u00105J\u0017\u00104\u001a\u0004\u0018\u0001032\u0006\u00107\u001a\u000206\u00a2\u0006\u0004\b4\u00108J\u0017\u00104\u001a\u0004\u0018\u0001032\u0006\u0010:\u001a\u000209\u00a2\u0006\u0004\b4\u0010;J!\u0010?\u001a\u000e\u0012\u0004\u0012\u000203\u0012\u0004\u0012\u00020>0=2\u0006\u0010<\u001a\u000206\u00a2\u0006\u0004\b?\u0010@J\u001d\u0010C\u001a\u00020B2\u0006\u0010<\u001a\u0002062\u0006\u0010A\u001a\u000206\u00a2\u0006\u0004\bC\u0010DJ\u0017\u0010E\u001a\u00020\b2\b\b\u0002\u0010\u0012\u001a\u000206\u00a2\u0006\u0004\bE\u0010FJ\r\u0010G\u001a\u00020\b\u00a2\u0006\u0004\bG\u0010\fJ7\u0010M\u001a\u00020\b2\u0006\u0010H\u001a\u0002032\n\u0010J\u001a\u0006\u0012\u0002\b\u00030I2\n\u0010K\u001a\u0006\u0012\u0002\b\u00030I2\b\b\u0002\u0010L\u001a\u00020\u000e\u00a2\u0006\u0004\bM\u0010NJ\u0019\u0010P\u001a\u00020\b2\n\u0010O\u001a\u0006\u0012\u0002\b\u00030I\u00a2\u0006\u0004\bP\u0010QJ-\u0010U\u001a\u00020\b2\u0006\u0010R\u001a\u0002032\n\u0010S\u001a\u0006\u0012\u0002\b\u00030I2\n\u0010T\u001a\u0006\u0012\u0002\b\u00030I\u00a2\u0006\u0004\bU\u0010VJ\u0019\u0010W\u001a\u00020\b2\n\u0010O\u001a\u0006\u0012\u0002\b\u00030I\u00a2\u0006\u0004\bW\u0010QJ\u0019\u0010X\u001a\u00020\b2\n\u0010O\u001a\u0006\u0012\u0002\b\u00030I\u00a2\u0006\u0004\bX\u0010QJ\r\u0010Y\u001a\u00020\b\u00a2\u0006\u0004\bY\u0010\fJ\r\u0010Z\u001a\u00020\b\u00a2\u0006\u0004\bZ\u0010\fJ\u0015\u0010]\u001a\u00020\b2\u0006\u0010\\\u001a\u00020[\u00a2\u0006\u0004\b]\u0010^J!\u0010a\u001a\u00020\b2\u0012\u0010`\u001a\n\u0012\u0006\b\u0001\u0012\u0002060_\"\u000206\u00a2\u0006\u0004\ba\u0010bR\u0017\u0010e\u001a\b\u0012\u0004\u0012\u00020>0#8F\u00a2\u0006\u0006\u001a\u0004\bc\u0010dR\u0017\u0010g\u001a\b\u0012\u0004\u0012\u0002030#8F\u00a2\u0006\u0006\u001a\u0004\bf\u0010dR#\u0010i\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u001b0h8\u0006\u00a2\u0006\f\n\u0004\bi\u0010j\u001a\u0004\bk\u0010lR\"\u0010m\u001a\u00020\u000e8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bm\u0010n\u001a\u0004\bo\u0010\u0010\"\u0004\bp\u0010qR\u001f\u0010s\u001a\n r*\u0004\u0018\u000101018\u0006\u00a2\u0006\f\n\u0004\bs\u0010t\u001a\u0004\bu\u0010vR\u001d\u0010w\u001a\b\u0012\u0004\u0012\u0002060h8\u0006\u00a2\u0006\f\n\u0004\bw\u0010j\u001a\u0004\bx\u0010lR\u001d\u0010y\u001a\b\u0012\u0004\u0012\u00020-0h8\u0006\u00a2\u0006\f\n\u0004\by\u0010j\u001a\u0004\bz\u0010lR\u001d\u0010{\u001a\b\u0012\u0004\u0012\u00020\u00060h8\u0006\u00a2\u0006\f\n\u0004\b{\u0010j\u001a\u0004\b|\u0010lR\u0019\u0010~\u001a\u00020}8\u0006\u00a2\u0006\u000e\n\u0004\b~\u0010\u007f\u001a\u0006\b\u0080\u0001\u0010\u0081\u0001R)\u0010\u0082\u0001\u001a\u00020\u001c8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0018\n\u0006\b\u0082\u0001\u0010\u0083\u0001\u001a\u0006\b\u0084\u0001\u0010\u0085\u0001\"\u0006\b\u0086\u0001\u0010\u0087\u0001R#\u0010\u0089\u0001\u001a\t\u0012\u0004\u0012\u00020 0\u0088\u00018\u0006\u00a2\u0006\u0010\n\u0006\b\u0089\u0001\u0010\u008a\u0001\u001a\u0006\b\u008b\u0001\u0010\u008c\u0001R&\u0010\u008d\u0001\u001a\u00020\u000e8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0015\n\u0005\b\u008d\u0001\u0010n\u001a\u0005\b\u008e\u0001\u0010\u0010\"\u0005\b\u008f\u0001\u0010qR\u001d\u0010\u0091\u0001\u001a\u00030\u0090\u00018\u0006\u00a2\u0006\u0010\n\u0006\b\u0091\u0001\u0010\u0092\u0001\u001a\u0006\b\u0093\u0001\u0010\u0094\u0001R\u0013\u0010\u0095\u0001\u001a\u00020\u000e8F\u00a2\u0006\u0007\u001a\u0005\b\u0095\u0001\u0010\u0010R\u0013\u0010\u0096\u0001\u001a\u00020\u000e8F\u00a2\u0006\u0007\u001a\u0005\b\u0096\u0001\u0010\u0010R\u0013\u0010\u0097\u0001\u001a\u00020\u000e8F\u00a2\u0006\u0007\u001a\u0005\b\u0097\u0001\u0010\u0010R:\u0010\u009a\u0001\u001a \u0012\u0004\u0012\u000201\u0012\u0004\u0012\u00020\u00110\u0098\u0001j\u000f\u0012\u0004\u0012\u000201\u0012\u0004\u0012\u00020\u0011`\u0099\u00018\u0006\u00a2\u0006\u0010\n\u0006\b\u009a\u0001\u0010\u009b\u0001\u001a\u0006\b\u009c\u0001\u0010\u009d\u0001R:\u0010\u009e\u0001\u001a \u0012\u0004\u0012\u000201\u0012\u0004\u0012\u00020\u00110\u0098\u0001j\u000f\u0012\u0004\u0012\u000201\u0012\u0004\u0012\u00020\u0011`\u0099\u00018\u0006\u00a2\u0006\u0010\n\u0006\b\u009e\u0001\u0010\u009b\u0001\u001a\u0006\b\u009f\u0001\u0010\u009d\u0001R&\u0010\u00a0\u0001\u001a\u00020\u000e8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0015\n\u0005\b\u00a0\u0001\u0010n\u001a\u0005\b\u00a1\u0001\u0010\u0010\"\u0005\b\u00a2\u0001\u0010qR\u0019\u0010\u00a4\u0001\u001a\b\u0012\u0004\u0012\u0002010#8F\u00a2\u0006\u0007\u001a\u0005\b\u00a3\u0001\u0010dR!\u0010\u00a6\u0001\u001a\t\u0012\u0004\u0012\u0002090\u00a5\u00018\u0006\u00a2\u0006\u000e\n\u0005\b\u00a6\u0001\u0010j\u001a\u0005\b\u00a7\u0001\u0010lR \u0010\u00a8\u0001\u001a\b\u0012\u0004\u0012\u0002060h8\u0006\u00a2\u0006\u000e\n\u0005\b\u00a8\u0001\u0010j\u001a\u0005\b\u00a9\u0001\u0010lR\u001d\u0010\u00ab\u0001\u001a\u00030\u00aa\u00018\u0006\u00a2\u0006\u0010\n\u0006\b\u00ab\u0001\u0010\u00ac\u0001\u001a\u0006\b\u00ad\u0001\u0010\u00ae\u0001R\u001d\u0010\u00af\u0001\u001a\u00030\u00aa\u00018\u0006\u00a2\u0006\u0010\n\u0006\b\u00af\u0001\u0010\u00ac\u0001\u001a\u0006\b\u00b0\u0001\u0010\u00ae\u0001R\u001a\u0010\u00b2\u0001\u001a\t\u0012\u0005\u0012\u00030\u00aa\u00010#8F\u00a2\u0006\u0007\u001a\u0005\b\u00b1\u0001\u0010dR#\u0010\u00b4\u0001\u001a\t\u0012\u0004\u0012\u0002010\u00b3\u00018\u0006\u00a2\u0006\u0010\n\u0006\b\u00b4\u0001\u0010\u00b5\u0001\u001a\u0006\b\u00b6\u0001\u0010\u00b7\u0001R&\u0010\u00b8\u0001\u001a\u00020\u000e8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0015\n\u0005\b\u00b8\u0001\u0010n\u001a\u0005\b\u00b9\u0001\u0010\u0010\"\u0005\b\u00ba\u0001\u0010qR)\u0010\u00bc\u0001\u001a\u00020[2\u0007\u0010\u00bb\u0001\u001a\u00020[8\u0002@BX\u0082\u000e\u00a2\u0006\u000f\n\u0006\b\u00bc\u0001\u0010\u00bd\u0001\"\u0005\b\u00be\u0001\u0010^R\u0014\u0010\u00c1\u0001\u001a\u00020[8F\u00a2\u0006\b\u001a\u0006\b\u00bf\u0001\u0010\u00c0\u0001R(\u0010]\u001a\u00020[2\u0007\u0010\u00c2\u0001\u001a\u00020[8\u0006@BX\u0086\u000e\u00a2\u0006\u000f\n\u0005\b]\u0010\u00bd\u0001\u001a\u0006\b\u00c3\u0001\u0010\u00c0\u0001\u00a8\u0006\u00c6\u0001"}, d2={"Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "", "Lcom/bedrockk/molang/runtime/struct/QueryStruct;", "queryStruct", "addQueryFunctions", "(Lcom/bedrockk/molang/runtime/struct/QueryStruct;)Lcom/bedrockk/molang/runtime/struct/QueryStruct;", "Lnet/minecraft/network/chat/Component;", "component", "", "broadcastChatMessage", "(Lnet/minecraft/network/chat/Component;)V", "checkFlee", "()V", "checkForInputDispatch", "", "checkForfeit", "()Z", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "message", "createUnimplemented$common", "(Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;)Lnet/minecraft/network/chat/Component;", "createUnimplemented", "publicMessage", "privateMessage", "createUnimplementedSplit$common", "(Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;)Lnet/minecraft/network/chat/Component;", "createUnimplementedSplit", "Lkotlin/Function0;", "Lcom/cobblemon/mod/common/battles/dispatch/DispatchResult;", "dispatcher", "dispatch", "(Lkotlin/jvm/functions/Function0;)V", "Lcom/cobblemon/mod/common/battles/dispatch/BattleDispatch;", "(Lcom/cobblemon/mod/common/battles/dispatch/BattleDispatch;)V", "dispatchGo", "", "dispatchInsert", "dispatchToFront", "", "delaySeconds", "dispatchWaiting", "(FLkotlin/jvm/functions/Function0;)V", "action", "doWhenClear", "end", "Lcom/cobblemon/mod/common/battles/BattleCaptureAction;", "captureAction", "finishCaptureAction", "(Lcom/cobblemon/mod/common/battles/BattleCaptureAction;)V", "Ljava/util/UUID;", "actorId", "Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "getActor", "(Ljava/util/UUID;)Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "", "showdownId", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "(Lnet/minecraft/server/level/ServerPlayer;)Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "pnx", "Lkotlin/Pair;", "Lcom/cobblemon/mod/common/battles/ActiveBattlePokemon;", "getActorAndActiveSlotFromPNX", "(Ljava/lang/String;)Lkotlin/Pair;", "pokemonID", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "getBattlePokemon", "(Ljava/lang/String;Ljava/lang/String;)Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "log", "(Ljava/lang/String;)V", "saveBattleLog", "source", "Lcom/cobblemon/mod/common/api/net/NetworkPacket;", "allyPacket", "opponentPacket", "spectatorsAsAlly", "sendSidedUpdate", "(Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;Lcom/cobblemon/mod/common/api/net/NetworkPacket;Lcom/cobblemon/mod/common/api/net/NetworkPacket;Z)V", "packet", "sendSpectatorUpdate", "(Lcom/cobblemon/mod/common/api/net/NetworkPacket;)V", "privateActor", "publicPacket", "privatePacket", "sendSplitUpdate", "(Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;Lcom/cobblemon/mod/common/api/net/NetworkPacket;Lcom/cobblemon/mod/common/api/net/NetworkPacket;)V", "sendToActors", "sendUpdate", "stop", "tick", "", "newTurnNumber", "turn", "(I)V", "", "messages", "writeShowdownAction", "([Ljava/lang/String;)V", "getActivePokemon", "()Ljava/lang/Iterable;", "activePokemon", "getActors", "actors", "", "afterDispatches", "Ljava/util/List;", "getAfterDispatches", "()Ljava/util/List;", "announcingRules", "Z", "getAnnouncingRules", "setAnnouncingRules", "(Z)V", "kotlin.jvm.PlatformType", "battleId", "Ljava/util/UUID;", "getBattleId", "()Ljava/util/UUID;", "battleLog", "getBattleLog", "captureActions", "getCaptureActions", "chatLog", "getChatLog", "Lcom/cobblemon/mod/common/battles/interpreter/ContextManager;", "contextManager", "Lcom/cobblemon/mod/common/battles/interpreter/ContextManager;", "getContextManager", "()Lcom/cobblemon/mod/common/battles/interpreter/ContextManager;", "dispatchResult", "Lcom/cobblemon/mod/common/battles/dispatch/DispatchResult;", "getDispatchResult", "()Lcom/cobblemon/mod/common/battles/dispatch/DispatchResult;", "setDispatchResult", "(Lcom/cobblemon/mod/common/battles/dispatch/DispatchResult;)V", "Ljava/util/concurrent/ConcurrentLinkedDeque;", "dispatches", "Ljava/util/concurrent/ConcurrentLinkedDeque;", "getDispatches", "()Ljava/util/concurrent/ConcurrentLinkedDeque;", "ended", "getEnded", "setEnded", "Lcom/cobblemon/mod/common/battles/BattleFormat;", "format", "Lcom/cobblemon/mod/common/battles/BattleFormat;", "getFormat", "()Lcom/cobblemon/mod/common/battles/BattleFormat;", "isPvN", "isPvP", "isPvW", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "majorBattleActions", "Ljava/util/HashMap;", "getMajorBattleActions", "()Ljava/util/HashMap;", "minorBattleActions", "getMinorBattleActions", "mute", "getMute", "setMute", "getPlayerUUIDs", "playerUUIDs", "", "players", "getPlayers", "showdownMessages", "getShowdownMessages", "Lcom/cobblemon/mod/common/battles/BattleSide;", "side1", "Lcom/cobblemon/mod/common/battles/BattleSide;", "getSide1", "()Lcom/cobblemon/mod/common/battles/BattleSide;", "side2", "getSide2", "getSides", "sides", "", "spectators", "Ljava/util/Set;", "getSpectators", "()Ljava/util/Set;", "started", "getStarted", "setStarted", "value", "ticks", "I", "setTicks", "getTime", "()I", "time", "<set-?>", "getTurn", "<init>", "(Lcom/cobblemon/mod/common/battles/BattleFormat;Lcom/cobblemon/mod/common/battles/BattleSide;Lcom/cobblemon/mod/common/battles/BattleSide;)V", "common"})
@SourceDebugExtension(value={"SMAP\nPokemonBattle.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonBattle.kt\ncom/cobblemon/mod/common/api/battles/model/PokemonBattle\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 5 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n+ 6 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 7 _Sequences.kt\nkotlin/sequences/SequencesKt___SequencesKt\n+ 8 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 9 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable$post$1\n*L\n1#1,501:1\n1855#2:502\n1855#2:503\n800#2,11:504\n1855#2,2:515\n1856#2:517\n1856#2:518\n1603#2,9:519\n1855#2:528\n1856#2:530\n1612#2:531\n1360#2:532\n1446#2,5:533\n1360#2:538\n1446#2,5:539\n1360#2:544\n1446#2,5:545\n1726#2,2:557\n1728#2:561\n288#2,2:568\n1855#2,2:570\n1855#2,2:574\n1855#2:576\n1603#2,9:577\n1855#2:586\n1856#2:588\n1612#2:589\n1856#2:590\n1855#2:591\n766#2:592\n857#2,2:593\n766#2:596\n857#2,2:597\n1855#2:599\n1855#2:600\n800#2,11:601\n1855#2,2:612\n1856#2:614\n1856#2:617\n1856#2:619\n766#2:620\n857#2,2:621\n800#2,11:623\n1603#2,9:634\n1855#2:643\n1856#2:645\n1612#2:646\n800#2,11:647\n1855#2,2:658\n1855#2:660\n1855#2:661\n1856#2:663\n1856#2:664\n1855#2,2:665\n1855#2,2:667\n1360#2:673\n1446#2,2:674\n1603#2,9:676\n1855#2:685\n1856#2:687\n1612#2:688\n1448#2,3:689\n1855#2,2:692\n1603#2,9:694\n1855#2:703\n1856#2:705\n1612#2:706\n1855#2,2:707\n800#2,11:709\n1855#2,2:720\n800#2,11:722\n766#2:733\n857#2,2:734\n2624#2,2:736\n2626#2:740\n766#2:741\n857#2,2:742\n800#2,11:744\n1603#2,9:755\n1855#2:764\n1856#2:766\n1612#2:767\n800#2,11:768\n1855#2,2:779\n800#2,11:791\n1603#2,9:802\n1855#2:811\n1856#2:813\n1612#2:814\n1855#2,2:815\n1747#2,3:817\n2624#2,3:820\n766#2:823\n857#2,2:824\n1855#2,2:826\n1855#2,2:828\n1747#2,3:831\n1#3:529\n1#3:550\n1#3:587\n1#3:644\n1#3:662\n1#3:686\n1#3:704\n1#3:739\n1#3:765\n1#3:812\n1#3:830\n12744#4,2:551\n12744#4,2:553\n12541#4,2:555\n12744#4,2:559\n12744#4,2:562\n12744#4,2:564\n12541#4,2:566\n13579#4:595\n13580#4:618\n13579#4,2:669\n13579#4,2:671\n13579#4:787\n13580#4:789\n37#5,2:572\n215#6,2:615\n473#7:738\n473#7:781\n14#8,5:782\n19#8:790\n14#9:788\n*S KotlinDebug\n*F\n+ 1 PokemonBattle.kt\ncom/cobblemon/mod/common/api/battles/model/PokemonBattle\n*L\n73#1:502\n75#1:503\n77#1:504,11\n78#1:515,2\n75#1:517\n73#1:518\n91#1:519,9\n91#1:528\n91#1:530\n91#1:531\n86#1:532\n86#1:533,5\n88#1:538\n88#1:539,5\n90#1:544\n90#1:545,5\n135#1:557,2\n135#1:561\n167#1:568,2\n198#1:570,2\n207#1:574,2\n210#1:576\n212#1:577,9\n212#1:586\n212#1:588\n212#1:589\n210#1:590\n220#1:591\n221#1:592\n221#1:593,2\n223#1:596\n223#1:597,2\n224#1:599\n229#1:600\n230#1:601,11\n230#1:612,2\n229#1:614\n224#1:617\n220#1:619\n257#1:620\n257#1:621,2\n258#1:623,11\n259#1:634,9\n259#1:643\n259#1:645\n259#1:646\n260#1:647,11\n261#1:658,2\n262#1:660\n263#1:661\n263#1:663\n262#1:664\n291#1:665,2\n301#1:667,2\n320#1:673\n320#1:674,2\n320#1:676,9\n320#1:685\n320#1:687\n320#1:688\n320#1:689,3\n324#1:692,2\n329#1:694,9\n329#1:703\n329#1:705\n329#1:706\n387#1:707,2\n393#1:709,11\n393#1:720,2\n408#1:722,11\n409#1:733\n409#1:734,2\n410#1:736,2\n410#1:740\n426#1:741\n426#1:742,2\n427#1:744,11\n428#1:755,9\n428#1:764\n428#1:766\n428#1:767\n429#1:768,11\n430#1:779,2\n432#1:791,11\n432#1:802,9\n432#1:811\n432#1:813\n432#1:814\n432#1:815,2\n444#1:817,3\n444#1:820,3\n446#1:823\n446#1:824,2\n446#1:826,2\n447#1:828,2\n453#1:831,3\n91#1:529\n212#1:587\n259#1:644\n320#1:686\n329#1:704\n428#1:765\n432#1:812\n125#1:551,2\n126#1:553,2\n130#1:555,2\n135#1:559,2\n140#1:562,2\n141#1:564,2\n145#1:566,2\n222#1:595\n222#1:618\n314#1:669,2\n315#1:671,2\n431#1:787\n431#1:789\n203#1:572,2\n249#1:615,2\n416#1:738\n431#1:781\n431#1:782,5\n431#1:790\n431#1:788\n*E\n"})
public class PokemonBattle {
    @NotNull
    private final BattleFormat format;
    @NotNull
    private final BattleSide side1;
    @NotNull
    private final BattleSide side2;
    private boolean mute;
    @NotNull
    private final List<ServerPlayer> players;
    @NotNull
    private final Set<UUID> spectators;
    private final UUID battleId;
    @NotNull
    private final List<String> showdownMessages;
    @NotNull
    private final List<String> battleLog;
    @NotNull
    private final List<Component> chatLog;
    private boolean started;
    private boolean ended;
    private boolean announcingRules;
    private int turn;
    private int ticks;
    @NotNull
    private DispatchResult dispatchResult;
    @NotNull
    private final ConcurrentLinkedDeque<BattleDispatch> dispatches;
    @NotNull
    private final List<Function0<Unit>> afterDispatches;
    @NotNull
    private final List<BattleCaptureAction> captureActions;
    @NotNull
    private final HashMap<UUID, BattleMessage> majorBattleActions;
    @NotNull
    private final HashMap<UUID, BattleMessage> minorBattleActions;
    @NotNull
    private final ContextManager contextManager;

    /*
     * WARNING - void declaration
     */
    public PokemonBattle(@NotNull BattleFormat format, @NotNull BattleSide side1, @NotNull BattleSide side2) {
        void $this$mapNotNullTo$iv$iv;
        void $this$mapNotNull$iv;
        Intrinsics.checkNotNullParameter((Object)format, (String)"format");
        Intrinsics.checkNotNullParameter((Object)side1, (String)"side1");
        Intrinsics.checkNotNullParameter((Object)side2, (String)"side2");
        this.format = format;
        this.side1 = side1;
        this.side2 = side2;
        this.mute = true;
        this.side1.setBattle(this);
        this.side2.setBattle(this);
        Iterable<Object> $this$forEach$iv = this.getActors();
        boolean $i$f$forEach = false;
        Iterator<BattleActor> iterator = $this$forEach$iv.iterator();
        while (iterator.hasNext()) {
            BattleActor element$iv;
            BattleActor actor = element$iv = iterator.next();
            boolean bl = false;
            actor.setBattle(this);
            Iterable $this$forEach$iv2 = actor.getPokemonList();
            boolean $i$f$forEach2 = false;
            for (Object element$iv2 : $this$forEach$iv2) {
                void $this$forEach$iv3;
                Iterator $this$filterIsInstanceTo$iv$iv;
                Iterable $this$filterIsInstance$iv;
                BattlePokemon battlePokemon = (BattlePokemon)element$iv2;
                boolean bl2 = false;
                Iterable iterable = battlePokemon.getEffectedPokemon().getEvolutionProxy().current().progress();
                boolean $i$f$filterIsInstance = false;
                void var18_28 = $this$filterIsInstance$iv;
                Collection destination$iv$iv = new ArrayList();
                boolean $i$f$filterIsInstanceTo = false;
                Iterator iterator2 = $this$filterIsInstanceTo$iv$iv.iterator();
                while (iterator2.hasNext()) {
                    Object element$iv$iv = iterator2.next();
                    if (!(element$iv$iv instanceof LastBattleCriticalHitsEvolutionProgress)) continue;
                    destination$iv$iv.add(element$iv$iv);
                }
                $this$filterIsInstance$iv = (List)destination$iv$iv;
                boolean $i$f$forEach3 = false;
                $this$filterIsInstanceTo$iv$iv = $this$forEach$iv3.iterator();
                while ($this$filterIsInstanceTo$iv$iv.hasNext()) {
                    Object element$iv3 = $this$filterIsInstanceTo$iv$iv.next();
                    LastBattleCriticalHitsEvolutionProgress it = (LastBattleCriticalHitsEvolutionProgress)element$iv3;
                    boolean bl3 = false;
                    it.reset();
                }
            }
        }
        $this$forEach$iv = this.getPlayerUUIDs();
        PokemonBattle pokemonBattle = this;
        boolean $i$f$mapNotNull = false;
        iterator = $this$mapNotNull$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$mapNotNullTo = false;
        void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
        boolean $i$f$forEach4 = false;
        Iterator iterator3 = $this$forEach$iv$iv$iv.iterator();
        while (iterator3.hasNext()) {
            ServerPlayer it$iv$iv;
            Object element$iv$iv$iv;
            Object element$iv$iv = element$iv$iv$iv = iterator3.next();
            boolean bl = false;
            UUID it = (UUID)element$iv$iv;
            boolean bl4 = false;
            if (PlayerExtensionsKt.getPlayer(it) == null) continue;
            boolean bl5 = false;
            destination$iv$iv.add(it$iv$iv);
        }
        pokemonBattle.players = (List)destination$iv$iv;
        this.spectators = new LinkedHashSet();
        this.battleId = UUID.randomUUID();
        this.showdownMessages = new ArrayList();
        this.battleLog = new ArrayList();
        this.chatLog = new ArrayList();
        this.turn = 1;
        this.dispatchResult = DispatchResultKt.getGO();
        this.dispatches = new ConcurrentLinkedDeque();
        this.afterDispatches = new ArrayList();
        this.captureActions = new ArrayList();
        this.majorBattleActions = new HashMap();
        this.minorBattleActions = new HashMap();
        this.contextManager = new ContextManager();
    }

    @NotNull
    public final BattleFormat getFormat() {
        return this.format;
    }

    @NotNull
    public final BattleSide getSide1() {
        return this.side1;
    }

    @NotNull
    public final BattleSide getSide2() {
        return this.side2;
    }

    public final boolean getMute() {
        return this.mute;
    }

    public final void setMute(boolean bl) {
        this.mute = bl;
    }

    @NotNull
    public final Iterable<BattleSide> getSides() {
        Object[] objectArray = new BattleSide[]{this.side1, this.side2};
        return CollectionsKt.listOf((Object[])objectArray);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final Iterable<BattleActor> getActors() {
        void $this$flatMapTo$iv$iv;
        Iterable<BattleSide> $this$flatMap$iv = this.getSides();
        boolean $i$f$flatMap = false;
        Iterable<BattleSide> iterable = $this$flatMap$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$flatMapTo = false;
        for (Object element$iv$iv : $this$flatMapTo$iv$iv) {
            BattleSide it = (BattleSide)element$iv$iv;
            boolean bl = false;
            Iterable list$iv$iv = ArraysKt.toList((Object[])it.getActors());
            CollectionsKt.addAll((Collection)destination$iv$iv, (Iterable)list$iv$iv);
        }
        return (List)destination$iv$iv;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final Iterable<ActiveBattlePokemon> getActivePokemon() {
        void $this$flatMapTo$iv$iv;
        Iterable<BattleActor> $this$flatMap$iv = this.getActors();
        boolean $i$f$flatMap = false;
        Iterable<BattleActor> iterable = $this$flatMap$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$flatMapTo = false;
        for (Object element$iv$iv : $this$flatMapTo$iv$iv) {
            BattleActor it = (BattleActor)element$iv$iv;
            boolean bl = false;
            Iterable list$iv$iv = it.getActivePokemon();
            CollectionsKt.addAll((Collection)destination$iv$iv, (Iterable)list$iv$iv);
        }
        return (List)destination$iv$iv;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final Iterable<UUID> getPlayerUUIDs() {
        void $this$flatMapTo$iv$iv;
        Iterable<BattleActor> $this$flatMap$iv = this.getActors();
        boolean $i$f$flatMap = false;
        Iterable<BattleActor> iterable = $this$flatMap$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$flatMapTo = false;
        for (Object element$iv$iv : $this$flatMapTo$iv$iv) {
            BattleActor it = (BattleActor)element$iv$iv;
            boolean bl = false;
            Iterable<UUID> list$iv$iv = it.getPlayerUUIDs();
            CollectionsKt.addAll((Collection)destination$iv$iv, list$iv$iv);
        }
        return (List)destination$iv$iv;
    }

    @NotNull
    public final List<ServerPlayer> getPlayers() {
        return this.players;
    }

    @NotNull
    public final Set<UUID> getSpectators() {
        return this.spectators;
    }

    public final UUID getBattleId() {
        return this.battleId;
    }

    @NotNull
    public final List<String> getShowdownMessages() {
        return this.showdownMessages;
    }

    @NotNull
    public final List<String> getBattleLog() {
        return this.battleLog;
    }

    @NotNull
    public final List<Component> getChatLog() {
        return this.chatLog;
    }

    public final boolean getStarted() {
        return this.started;
    }

    public final void setStarted(boolean bl) {
        this.started = bl;
    }

    public final boolean getEnded() {
        return this.ended;
    }

    public final void setEnded(boolean bl) {
        this.ended = bl;
    }

    public final boolean getAnnouncingRules() {
        return this.announcingRules;
    }

    public final void setAnnouncingRules(boolean bl) {
        this.announcingRules = bl;
    }

    public final int getTurn() {
        return this.turn;
    }

    private final void setTicks(int value2) {
        this.ticks = RangesKt.coerceAtMost((int)value2, (int)Integer.MAX_VALUE);
    }

    public final int getTime() {
        return this.ticks % 20;
    }

    @NotNull
    public final DispatchResult getDispatchResult() {
        return this.dispatchResult;
    }

    public final void setDispatchResult(@NotNull DispatchResult dispatchResult) {
        Intrinsics.checkNotNullParameter((Object)dispatchResult, (String)"<set-?>");
        this.dispatchResult = dispatchResult;
    }

    @NotNull
    public final ConcurrentLinkedDeque<BattleDispatch> getDispatches() {
        return this.dispatches;
    }

    @NotNull
    public final List<Function0<Unit>> getAfterDispatches() {
        return this.afterDispatches;
    }

    @NotNull
    public final List<BattleCaptureAction> getCaptureActions() {
        return this.captureActions;
    }

    @NotNull
    public final HashMap<UUID, BattleMessage> getMajorBattleActions() {
        return this.majorBattleActions;
    }

    @NotNull
    public final HashMap<UUID, BattleMessage> getMinorBattleActions() {
        return this.minorBattleActions;
    }

    @NotNull
    public final ContextManager getContextManager() {
        return this.contextManager;
    }

    public final boolean isPvW() {
        boolean bl;
        block11: {
            BattleSide battleSide;
            block10: {
                boolean bl2;
                BattleSide playerSide;
                Object it;
                block9: {
                    BattleSide battleSide2;
                    block8: {
                        Iterable<BattleSide> iterable = this.getSides();
                        for (BattleSide battleSide3 : iterable) {
                            boolean bl3;
                            block7: {
                                it = battleSide3;
                                boolean bl4 = false;
                                BattleActor[] $this$any$iv = ((BattleSide)it).getActors();
                                boolean $i$f$any = false;
                                int n = $this$any$iv.length;
                                for (int i = 0; i < n; ++i) {
                                    BattleActor element$iv;
                                    BattleActor it2 = element$iv = $this$any$iv[i];
                                    boolean bl5 = false;
                                    if (!(it2.getType() == ActorType.PLAYER)) continue;
                                    bl3 = true;
                                    break block7;
                                }
                                bl3 = false;
                            }
                            if (!bl3) continue;
                            battleSide2 = battleSide3;
                            break block8;
                        }
                        battleSide2 = null;
                    }
                    BattleSide battleSide4 = battleSide2;
                    if (battleSide4 == null) {
                        return false;
                    }
                    playerSide = battleSide4;
                    BattleActor[] $this$any$iv = playerSide.getActors();
                    boolean $i$f$any = false;
                    for (BattleActor element$iv : $this$any$iv) {
                        it = element$iv;
                        boolean bl6 = false;
                        if (!(((BattleActor)it).getType() != ActorType.PLAYER)) continue;
                        bl2 = true;
                        break block9;
                    }
                    bl2 = false;
                }
                if (bl2) {
                    return false;
                }
                Iterable<BattleSide> iterable = this.getSides();
                for (BattleSide battleSide3 : iterable) {
                    it = battleSide3;
                    boolean bl7 = false;
                    if (!(!Intrinsics.areEqual((Object)it, (Object)playerSide))) continue;
                    battleSide = battleSide3;
                    break block10;
                }
                battleSide = null;
            }
            Intrinsics.checkNotNull(battleSide);
            BattleSide otherSide = battleSide;
            BattleActor[] $this$all$iv = otherSide.getActors();
            boolean $i$f$all = false;
            int n = $this$all$iv.length;
            for (int i = 0; i < n; ++i) {
                BattleActor element$iv;
                BattleActor it = element$iv = $this$all$iv[i];
                boolean bl8 = false;
                if (it.getType() == ActorType.WILD) continue;
                bl = false;
                break block11;
            }
            bl = true;
        }
        return bl;
    }

    public final boolean isPvP() {
        boolean bl;
        block5: {
            Iterable<BattleSide> $this$all$iv = this.getSides();
            boolean $i$f$all = false;
            if ($this$all$iv instanceof Collection && ((Collection)$this$all$iv).isEmpty()) {
                bl = true;
            } else {
                Iterator<BattleSide> iterator = $this$all$iv.iterator();
                while (iterator.hasNext()) {
                    boolean bl2;
                    block4: {
                        BattleSide element$iv;
                        BattleSide it = element$iv = iterator.next();
                        boolean bl3 = false;
                        BattleActor[] $this$any$iv = it.getActors();
                        boolean $i$f$any = false;
                        int n = $this$any$iv.length;
                        for (int i = 0; i < n; ++i) {
                            BattleActor element$iv2;
                            BattleActor it2 = element$iv2 = $this$any$iv[i];
                            boolean bl4 = false;
                            if (!(it2.getType() == ActorType.PLAYER)) continue;
                            bl2 = true;
                            break block4;
                        }
                        bl2 = false;
                    }
                    if (bl2) continue;
                    bl = false;
                    break block5;
                }
                bl = true;
            }
        }
        return bl;
    }

    public final boolean isPvN() {
        boolean bl;
        block11: {
            BattleSide battleSide;
            block10: {
                boolean bl2;
                BattleSide playerSide;
                Object it;
                block9: {
                    BattleSide battleSide2;
                    block8: {
                        Iterable<BattleSide> iterable = this.getSides();
                        for (BattleSide battleSide3 : iterable) {
                            boolean bl3;
                            block7: {
                                it = battleSide3;
                                boolean bl4 = false;
                                BattleActor[] $this$any$iv = ((BattleSide)it).getActors();
                                boolean $i$f$any = false;
                                int n = $this$any$iv.length;
                                for (int i = 0; i < n; ++i) {
                                    BattleActor element$iv;
                                    BattleActor it2 = element$iv = $this$any$iv[i];
                                    boolean bl5 = false;
                                    if (!(it2.getType() == ActorType.PLAYER)) continue;
                                    bl3 = true;
                                    break block7;
                                }
                                bl3 = false;
                            }
                            if (!bl3) continue;
                            battleSide2 = battleSide3;
                            break block8;
                        }
                        battleSide2 = null;
                    }
                    BattleSide battleSide4 = battleSide2;
                    if (battleSide4 == null) {
                        return false;
                    }
                    playerSide = battleSide4;
                    BattleActor[] $this$any$iv = playerSide.getActors();
                    boolean $i$f$any = false;
                    for (BattleActor element$iv : $this$any$iv) {
                        it = element$iv;
                        boolean bl6 = false;
                        if (!(((BattleActor)it).getType() != ActorType.PLAYER)) continue;
                        bl2 = true;
                        break block9;
                    }
                    bl2 = false;
                }
                if (bl2) {
                    return false;
                }
                Iterable<BattleSide> iterable = this.getSides();
                for (BattleSide battleSide3 : iterable) {
                    it = battleSide3;
                    boolean bl7 = false;
                    if (!(!Intrinsics.areEqual((Object)it, (Object)playerSide))) continue;
                    battleSide = battleSide3;
                    break block10;
                }
                battleSide = null;
            }
            Intrinsics.checkNotNull(battleSide);
            BattleSide otherSide = battleSide;
            BattleActor[] $this$all$iv = otherSide.getActors();
            boolean $i$f$all = false;
            int n = $this$all$iv.length;
            for (int i = 0; i < n; ++i) {
                BattleActor element$iv;
                BattleActor it = element$iv = $this$all$iv[i];
                boolean bl8 = false;
                if (it.getType() == ActorType.NPC) continue;
                bl = false;
                break block11;
            }
            bl = true;
        }
        return bl;
    }

    @Nullable
    public final BattleActor getActor(@NotNull String showdownId) {
        BattleActor battleActor;
        block1: {
            Intrinsics.checkNotNullParameter((Object)showdownId, (String)"showdownId");
            Iterable<BattleActor> iterable = this.getActors();
            Iterator<BattleActor> iterator = iterable.iterator();
            while (iterator.hasNext()) {
                BattleActor battleActor2;
                BattleActor actor = battleActor2 = iterator.next();
                boolean bl = false;
                if (!Intrinsics.areEqual((Object)actor.getShowdownId(), (Object)showdownId)) continue;
                battleActor = battleActor2;
                break block1;
            }
            battleActor = null;
        }
        return battleActor;
    }

    @Nullable
    public final BattleActor getActor(@NotNull UUID actorId) {
        BattleActor battleActor;
        block1: {
            Intrinsics.checkNotNullParameter((Object)actorId, (String)"actorId");
            Iterable<BattleActor> iterable = this.getActors();
            Iterator<BattleActor> iterator = iterable.iterator();
            while (iterator.hasNext()) {
                BattleActor battleActor2;
                BattleActor actor = battleActor2 = iterator.next();
                boolean bl = false;
                if (!Intrinsics.areEqual((Object)actor.getUuid(), (Object)actorId)) continue;
                battleActor = battleActor2;
                break block1;
            }
            battleActor = null;
        }
        return battleActor;
    }

    @Nullable
    public final BattleActor getActor(@NotNull ServerPlayer player) {
        BattleActor battleActor;
        block1: {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            Iterable<BattleActor> $this$firstOrNull$iv = this.getActors();
            boolean $i$f$firstOrNull = false;
            Iterator<BattleActor> iterator = $this$firstOrNull$iv.iterator();
            while (iterator.hasNext()) {
                BattleActor element$iv;
                BattleActor it = element$iv = iterator.next();
                boolean bl = false;
                if (!it.isForPlayer(player)) continue;
                battleActor = element$iv;
                break block1;
            }
            battleActor = null;
        }
        return battleActor;
    }

    @NotNull
    public final Pair<BattleActor, ActiveBattlePokemon> getActorAndActiveSlotFromPNX(@NotNull String pnx) {
        Object v4;
        BattleActor actor;
        block5: {
            BattleActor battleActor;
            block4: {
                Intrinsics.checkNotNullParameter((Object)pnx, (String)"pnx");
                Iterable<BattleActor> iterable = this.getActors();
                Iterator<BattleActor> iterator = iterable.iterator();
                while (iterator.hasNext()) {
                    BattleActor object22;
                    BattleActor it = object22 = iterator.next();
                    boolean bl = false;
                    String string = it.getShowdownId();
                    String string2 = pnx.substring(0, 2);
                    Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"this as java.lang.String\u2026ing(startIndex, endIndex)");
                    if (!Intrinsics.areEqual((Object)string, (Object)string2)) continue;
                    battleActor = object22;
                    break block4;
                }
                battleActor = null;
            }
            BattleActor battleActor2 = battleActor;
            if (battleActor2 == null) {
                throw new IllegalStateException("Invalid pnx: " + pnx + " - unknown actor");
            }
            actor = battleActor2;
            char letter = pnx.charAt(2);
            Iterable iterable = actor.getSide().getActivePokemon();
            for (Object t : iterable) {
                ActiveBattlePokemon it = (ActiveBattlePokemon)t;
                boolean bl = false;
                if (!(it.getLetter() == letter)) continue;
                v4 = t;
                break block5;
            }
            v4 = null;
        }
        ActiveBattlePokemon activeBattlePokemon = v4;
        if (activeBattlePokemon == null) {
            throw new IllegalStateException("Invalid pnx: " + pnx + " - unknown pokemon");
        }
        ActiveBattlePokemon pokemon = activeBattlePokemon;
        return TuplesKt.to((Object)actor, (Object)pokemon);
    }

    @NotNull
    public final BattlePokemon getBattlePokemon(@NotNull String pnx, @NotNull String pokemonID) {
        BattleActor battleActor;
        block5: {
            BattleActor battleActor2;
            Object it;
            Iterable iterable;
            block4: {
                Intrinsics.checkNotNullParameter((Object)pnx, (String)"pnx");
                Intrinsics.checkNotNullParameter((Object)pokemonID, (String)"pokemonID");
                iterable = this.getActors();
                for (BattleActor battleActor3 : iterable) {
                    it = battleActor3;
                    boolean bl = false;
                    String string = ((BattleActor)it).getShowdownId();
                    String string2 = pnx.substring(0, 2);
                    Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"this as java.lang.String\u2026ing(startIndex, endIndex)");
                    if (!Intrinsics.areEqual((Object)string, (Object)string2)) continue;
                    battleActor2 = battleActor3;
                    break block4;
                }
                battleActor2 = null;
            }
            BattleActor battleActor4 = battleActor2;
            if (battleActor4 == null) {
                throw new IllegalStateException("Invalid pnx: " + pnx + " - unknown actor");
            }
            BattleActor actor = battleActor4;
            iterable = actor.getPokemonList();
            for (BattleActor battleActor3 : iterable) {
                it = (BattlePokemon)((Object)battleActor3);
                boolean bl = false;
                if (!Intrinsics.areEqual((Object)((BattlePokemon)it).getUuid().toString(), (Object)pokemonID)) continue;
                battleActor = battleActor3;
                break block5;
            }
            battleActor = null;
        }
        BattlePokemon battlePokemon = (BattlePokemon)((Object)battleActor);
        if (battlePokemon == null) {
            throw new IllegalStateException("Invalid pnx: " + pnx + " - unknown pokemon");
        }
        return battlePokemon;
    }

    public final void broadcastChatMessage(@NotNull Component component) {
        Intrinsics.checkNotNullParameter((Object)component, (String)"component");
        this.chatLog.add(component);
        Component[] componentArray = new Component[]{component};
        this.sendSpectatorUpdate(new BattleMessagePacket(componentArray));
        Iterable<BattleActor> $this$forEach$iv = this.getActors();
        boolean $i$f$forEach = false;
        Iterator<BattleActor> iterator = $this$forEach$iv.iterator();
        while (iterator.hasNext()) {
            BattleActor element$iv;
            BattleActor it = element$iv = iterator.next();
            boolean bl = false;
            it.sendMessage(component);
        }
    }

    public final void writeShowdownAction(String ... messages) {
        Intrinsics.checkNotNullParameter((Object)messages, (String)"messages");
        this.log(ArraysKt.joinToString$default((Object[])messages, (CharSequence)"\n", null, null, (int)0, null, null, (int)62, null));
        ShowdownService showdownService = ShowdownService.Companion.getService();
        UUID uUID = this.battleId;
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"battleId");
        Collection $this$toTypedArray$iv = ArraysKt.toList((Object[])messages);
        boolean $i$f$toTypedArray = false;
        Collection thisCollection$iv = $this$toTypedArray$iv;
        showdownService.send(uUID, thisCollection$iv.toArray(new String[0]));
    }

    /*
     * WARNING - void declaration
     */
    public final void turn(int newTurnNumber) {
        Iterable<BattleActor> $this$forEach$iv = this.getActors();
        boolean $i$f$forEach = false;
        Iterator<BattleActor> iterator = $this$forEach$iv.iterator();
        while (iterator.hasNext()) {
            BattleActor element$iv;
            BattleActor it = element$iv = iterator.next();
            boolean bl = false;
            it.turn();
        }
        for (BattleSide side : this.getSides()) {
            BattleSide opposite = side.getOppositeSide();
            Iterable $this$forEach$iv2 = side.getActivePokemon();
            boolean $i$f$forEach2 = false;
            for (Object element$iv : $this$forEach$iv2) {
                void $this$mapNotNullTo$iv$iv;
                void $this$mapNotNull$iv;
                BattlePokemon battlePokemon;
                ActiveBattlePokemon it = (ActiveBattlePokemon)element$iv;
                boolean bl = false;
                if (it.getBattlePokemon() == null) continue;
                Iterable iterable = opposite.getActivePokemon();
                Set<BattlePokemon> set2 = battlePokemon.getFacedOpponents();
                boolean $i$f$mapNotNull = false;
                void var15_18 = $this$mapNotNull$iv;
                Collection destination$iv$iv = new ArrayList();
                boolean $i$f$mapNotNullTo = false;
                void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
                boolean $i$f$forEach3 = false;
                Iterator iterator2 = $this$forEach$iv$iv$iv.iterator();
                while (iterator2.hasNext()) {
                    BattlePokemon it$iv$iv;
                    Object element$iv$iv$iv;
                    Object element$iv$iv = element$iv$iv$iv = iterator2.next();
                    boolean bl2 = false;
                    ActiveBattlePokemon it2 = (ActiveBattlePokemon)element$iv$iv;
                    boolean bl3 = false;
                    if (it2.getBattlePokemon() == null) continue;
                    boolean bl4 = false;
                    destination$iv$iv.add(it$iv$iv);
                }
                set2.addAll((List)destination$iv$iv);
            }
        }
        this.turn = newTurnNumber;
    }

    /*
     * WARNING - void declaration
     */
    public final void end() {
        Iterable $this$mapNotNullTo$iv$iv;
        Iterable $this$filterIsInstanceTo$iv$iv;
        Iterable $this$filterTo$iv$iv;
        this.ended = true;
        Iterable<BattleActor> $this$forEach$iv = this.getActors();
        boolean $i$f$forEach = false;
        Iterator<Object> iterator = $this$forEach$iv.iterator();
        while (iterator.hasNext()) {
            void $this$filterTo$iv$iv222;
            BattleActor battleActor;
            BattleActor actor = battleActor = iterator.next();
            boolean bl = false;
            Iterable $this$filter$iv = actor.getPokemonList();
            boolean $i$f$filter = false;
            Iterable iterable = $this$filter$iv;
            Collection destination$iv$iv232 = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv : $this$filterTo$iv$iv222) {
                BattlePokemon it = (BattlePokemon)element$iv$iv;
                boolean bl2 = false;
                if (!(it.getHealth() <= 0)) continue;
                destination$iv$iv232.add(element$iv$iv);
            }
            List faintedPokemons = (List)destination$iv$iv232;
            BattleActor[] $this$forEach$iv2 = actor.getSide().getOppositeSide().getActors();
            boolean $i$f$forEach2 = false;
            int destination$iv$iv232 = $this$forEach$iv2.length;
            for (int $this$filterTo$iv$iv222 = 0; $this$filterTo$iv$iv222 < destination$iv$iv232; ++$this$filterTo$iv$iv222) {
                void $this$filterTo$iv$iv3;
                BattleActor element$iv2;
                BattleActor opponent = element$iv2 = $this$forEach$iv2[$this$filterTo$iv$iv222];
                boolean bl3 = false;
                Iterable $this$filter$iv2 = opponent.getPokemonList();
                boolean $i$f$filter2 = false;
                Iterable iterable2 = $this$filter$iv2;
                Collection destination$iv$iv = new ArrayList();
                boolean $i$f$filterTo2 = false;
                for (Object element$iv$iv : $this$filterTo$iv$iv3) {
                    BattlePokemon it = (BattlePokemon)element$iv$iv;
                    boolean bl4 = false;
                    if (!(it.getHealth() > 0)) continue;
                    destination$iv$iv.add(element$iv$iv);
                }
                List opponentNonFaintedPokemons = (List)destination$iv$iv;
                Iterable $this$forEach$iv3 = faintedPokemons;
                boolean $i$f$forEach3 = false;
                for (Object element$iv3 : $this$forEach$iv3) {
                    BattlePokemon faintedPokemon = (BattlePokemon)element$iv3;
                    boolean bl5 = false;
                    for (BattlePokemon opponentPokemon : opponentNonFaintedPokemons) {
                        double d;
                        boolean facedFainted = opponentPokemon.getFacedOpponents().contains(faintedPokemon);
                        Pokemon pokemon = opponentPokemon.getEffectedPokemon();
                        if (facedFainted) {
                            Iterable<Evolution> $this$forEach$iv4 = pokemon.getLockedEvolutions();
                            boolean $i$f$forEach4 = false;
                            Iterator<Evolution> iterator2 = $this$forEach$iv4.iterator();
                            while (iterator2.hasNext()) {
                                void $this$forEach$iv5;
                                Iterator $this$filterIsInstanceTo$iv$iv2;
                                Evolution element$iv4;
                                Evolution evolution = element$iv4 = iterator2.next();
                                boolean bl6 = false;
                                Iterable $this$filterIsInstance$iv = evolution.getRequirements();
                                boolean $i$f$filterIsInstance = false;
                                Iterable iterable3 = $this$filterIsInstance$iv;
                                Collection destination$iv$iv3 = new ArrayList();
                                boolean $i$f$filterIsInstanceTo = false;
                                Iterator iterator3 = $this$filterIsInstanceTo$iv$iv2.iterator();
                                while (iterator3.hasNext()) {
                                    Object element$iv$iv = iterator3.next();
                                    if (!(element$iv$iv instanceof DefeatRequirement)) continue;
                                    destination$iv$iv3.add(element$iv$iv);
                                }
                                $this$filterIsInstance$iv = (List)destination$iv$iv3;
                                boolean $i$f$forEach5 = false;
                                $this$filterIsInstanceTo$iv$iv2 = $this$forEach$iv5.iterator();
                                while ($this$filterIsInstanceTo$iv$iv2.hasNext()) {
                                    Object element$iv5 = $this$filterIsInstanceTo$iv$iv2.next();
                                    DefeatRequirement defeatRequirement = (DefeatRequirement)element$iv5;
                                    boolean bl7 = false;
                                    if (!defeatRequirement.getTarget().matches(faintedPokemon.getEffectedPokemon())) continue;
                                    DefeatEvolutionProgress progress2 = (DefeatEvolutionProgress)pokemon.getEvolutionProxy().current().progressFirstOrCreate((Function1)new Function1<EvolutionProgress<?>, Boolean>(defeatRequirement){
                                        final /* synthetic */ DefeatRequirement $defeatRequirement;
                                        {
                                            this.$defeatRequirement = $defeatRequirement;
                                            super(1);
                                        }

                                        @NotNull
                                        public final Boolean invoke(@NotNull EvolutionProgress<?> it) {
                                            Intrinsics.checkNotNullParameter(it, (String)"it");
                                            return it instanceof DefeatEvolutionProgress && Intrinsics.areEqual((Object)((DefeatEvolutionProgress)it).currentProgress().getTarget(), (Object)this.$defeatRequirement.getTarget());
                                        }
                                    }, end.1.1.1.1.1.progress.2.INSTANCE);
                                    progress2.updateProgress(new DefeatEvolutionProgress.Progress(defeatRequirement.getTarget(), progress2.currentProgress().getAmount() + 1));
                                }
                            }
                        }
                        if (!facedFainted && pokemon.heldItemNoCopy$common().m_204117_(CobblemonItemTags.EXPERIENCE_SHARE)) {
                            d = Cobblemon.INSTANCE.getConfig().getExperienceShareMultiplier();
                        } else {
                            if (!facedFainted) continue;
                            d = 1.0;
                        }
                        double multiplier = d;
                        int experience = Cobblemon.INSTANCE.getExperienceCalculator().calculate(opponentPokemon, faintedPokemon, multiplier);
                        if (experience > 0) {
                            opponent.awardExperience(opponentPokemon, experience);
                        }
                        Map<Stat, Integer> $this$forEach$iv6 = Cobblemon.INSTANCE.getEvYieldCalculator().calculate(opponentPokemon, faintedPokemon);
                        boolean $i$f$forEach6 = false;
                        Iterator<Map.Entry<Stat, Integer>> iterator4 = $this$forEach$iv6.entrySet().iterator();
                        while (iterator4.hasNext()) {
                            Map.Entry<Stat, Integer> element$iv6;
                            Map.Entry<Stat, Integer> entry = element$iv6 = iterator4.next();
                            boolean bl8 = false;
                            Stat stat = entry.getKey();
                            int amount = ((Number)entry.getValue()).intValue();
                            pokemon.getEvs().add(stat, amount);
                        }
                    }
                }
            }
        }
        Iterable<BattleActor> $this$filter$iv = this.getActors();
        boolean $i$f$filter = false;
        iterator = $this$filter$iv;
        Collection collection = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            BattleActor it = (BattleActor)element$iv$iv;
            boolean bl = false;
            if (!(it.getType() == ActorType.WILD)) continue;
            collection.add(element$iv$iv);
        }
        Iterable $this$filterIsInstance$iv = (List)collection;
        boolean $i$f$filterIsInstance = false;
        $this$filterTo$iv$iv = $this$filterIsInstance$iv;
        Collection collection2 = new ArrayList();
        boolean $i$f$filterIsInstanceTo = false;
        for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
            if (!(element$iv$iv instanceof EntityBackedBattleActor)) continue;
            collection2.add(element$iv$iv);
        }
        Iterable $this$mapNotNull$iv = (List)collection2;
        boolean $i$f$mapNotNull = false;
        $this$filterIsInstanceTo$iv$iv = $this$mapNotNull$iv;
        Collection collection3 = new ArrayList();
        boolean $i$f$mapNotNullTo = false;
        Iterator $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
        boolean $i$f$forEach7 = false;
        Iterator it = $this$forEach$iv$iv$iv.iterator();
        while (it.hasNext()) {
            Object it$iv$iv;
            Object element$iv$iv$iv;
            Object element$iv$iv = element$iv$iv$iv = it.next();
            boolean bl = false;
            EntityBackedBattleActor it2 = (EntityBackedBattleActor)element$iv$iv;
            boolean bl9 = false;
            if (it2.getEntity() == null) continue;
            boolean bl10 = false;
            collection3.add(it$iv$iv);
        }
        $this$filterIsInstance$iv = (List)collection3;
        $i$f$filterIsInstance = false;
        $this$mapNotNullTo$iv$iv = $this$filterIsInstance$iv;
        Collection collection4 = new ArrayList();
        $i$f$filterIsInstanceTo = false;
        for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
            if (!(element$iv$iv instanceof PokemonEntity)) continue;
            collection4.add(element$iv$iv);
        }
        $this$forEach$iv = (List)collection4;
        $i$f$forEach = false;
        for (Object t : $this$forEach$iv) {
            PokemonEntity it3 = (PokemonEntity)t;
            boolean bl = false;
            it3.getPokemon().heal();
        }
        $this$forEach$iv = this.getActors();
        $i$f$forEach = false;
        for (Object object : $this$forEach$iv) {
            BattleActor actor = (BattleActor)object;
            boolean bl = false;
            Iterable $this$forEach$iv7 = actor.getPokemonList();
            boolean $i$f$forEach8 = false;
            for (Object element$iv7 : $this$forEach$iv7) {
                PokemonEntity entity2;
                BattlePokemon battlePokemon = (BattlePokemon)element$iv7;
                boolean bl11 = false;
                if (battlePokemon.getEntity() == null) continue;
                boolean bl12 = false;
                battlePokemon.getPostBattleEntityOperation().invoke((Object)entity2);
            }
        }
        this.sendUpdate(new BattleEndPacket());
        BattleRegistry.INSTANCE.closeBattle(this);
    }

    public final void finishCaptureAction(@NotNull BattleCaptureAction captureAction) {
        Intrinsics.checkNotNullParameter((Object)captureAction, (String)"captureAction");
        this.captureActions.remove(captureAction);
        this.checkForInputDispatch();
    }

    public final void log(@NotNull String message) {
        Intrinsics.checkNotNullParameter((Object)message, (String)"message");
        if (!this.mute) {
            Cobblemon.INSTANCE.getLOGGER().info(message);
        }
        this.battleLog.add(message);
    }

    public static /* synthetic */ void log$default(PokemonBattle pokemonBattle, String string, int n, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: log");
        }
        if ((n & 1) != 0) {
            string = "";
        }
        pokemonBattle.log(string);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void saveBattleLog() {
        File battleLogsDir = new File("./battle_logs/");
        if (!battleLogsDir.exists()) {
            battleLogsDir.mkdirs();
        }
        File logFile = new File(battleLogsDir, this.battleId + ".txt");
        Object object = logFile;
        Object object2 = Charsets.UTF_8;
        int n = 8192;
        Object object3 = object;
        object = (object3 = (Writer)new OutputStreamWriter((OutputStream)new FileOutputStream((File)object3), (Charset)object2)) instanceof BufferedWriter ? (BufferedWriter)object3 : new BufferedWriter((Writer)object3, n);
        object2 = null;
        try {
            BufferedWriter out = (BufferedWriter)object;
            boolean bl = false;
            Iterable $this$forEach$iv = this.battleLog;
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                String it = (String)element$iv;
                boolean bl2 = false;
                out.write(it);
                out.newLine();
            }
            Unit unit = Unit.INSTANCE;
        }
        catch (Throwable throwable) {
            object2 = throwable;
            throw throwable;
        }
        finally {
            CloseableKt.closeFinally((Closeable)object, (Throwable)object2);
        }
        Cobblemon.INSTANCE.getLOGGER().info("Saved battle log as " + this.battleId + ".txt");
    }

    public final void sendUpdate(@NotNull NetworkPacket<?> packet) {
        Intrinsics.checkNotNullParameter(packet, (String)"packet");
        Iterable<BattleActor> $this$forEach$iv = this.getActors();
        boolean $i$f$forEach = false;
        Iterator<BattleActor> iterator = $this$forEach$iv.iterator();
        while (iterator.hasNext()) {
            BattleActor element$iv;
            BattleActor it = element$iv = iterator.next();
            boolean bl = false;
            it.sendUpdate(packet);
        }
        this.sendSpectatorUpdate(packet);
    }

    public final void sendSidedUpdate(@NotNull BattleActor source, @NotNull NetworkPacket<?> allyPacket, @NotNull NetworkPacket<?> opponentPacket, boolean spectatorsAsAlly) {
        BattleActor it;
        BattleActor element$iv;
        int n;
        Intrinsics.checkNotNullParameter((Object)source, (String)"source");
        Intrinsics.checkNotNullParameter(allyPacket, (String)"allyPacket");
        Intrinsics.checkNotNullParameter(opponentPacket, (String)"opponentPacket");
        BattleActor[] $this$forEach$iv = source.getSide().getActors();
        boolean $i$f$forEach = false;
        int n2 = $this$forEach$iv.length;
        for (n = 0; n < n2; ++n) {
            it = element$iv = $this$forEach$iv[n];
            boolean bl = false;
            it.sendUpdate(allyPacket);
        }
        $this$forEach$iv = source.getSide().getOppositeSide().getActors();
        $i$f$forEach = false;
        n2 = $this$forEach$iv.length;
        for (n = 0; n < n2; ++n) {
            it = element$iv = $this$forEach$iv[n];
            boolean bl = false;
            it.sendUpdate(opponentPacket);
        }
        this.sendSpectatorUpdate(spectatorsAsAlly ? allyPacket : opponentPacket);
    }

    public static /* synthetic */ void sendSidedUpdate$default(PokemonBattle pokemonBattle, BattleActor battleActor, NetworkPacket networkPacket, NetworkPacket networkPacket2, boolean bl, int n, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: sendSidedUpdate");
        }
        if ((n & 8) != 0) {
            bl = false;
        }
        pokemonBattle.sendSidedUpdate(battleActor, networkPacket, networkPacket2, bl);
    }

    /*
     * WARNING - void declaration
     */
    public final void sendToActors(@NotNull NetworkPacket<?> packet) {
        void $this$flatMapTo$iv$iv;
        void $this$flatMap$iv;
        Intrinsics.checkNotNullParameter(packet, (String)"packet");
        Iterable<BattleActor> iterable = this.getActors();
        CobblemonNetwork cobblemonNetwork = CobblemonNetwork.INSTANCE;
        boolean $i$f$flatMap = false;
        void var4_5 = $this$flatMap$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$flatMapTo = false;
        for (Object element$iv$iv : $this$flatMapTo$iv$iv) {
            void $this$mapNotNullTo$iv$iv;
            BattleActor it = (BattleActor)element$iv$iv;
            boolean bl = false;
            Iterable<UUID> $this$mapNotNull$iv = it.getPlayerUUIDs();
            boolean $i$f$mapNotNull = false;
            Iterable<UUID> iterable2 = $this$mapNotNull$iv;
            Collection destination$iv$iv2 = new ArrayList();
            boolean $i$f$mapNotNullTo = false;
            void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
            boolean $i$f$forEach = false;
            Iterator iterator = $this$forEach$iv$iv$iv.iterator();
            while (iterator.hasNext()) {
                ServerPlayer it$iv$iv;
                Object element$iv$iv$iv;
                Object element$iv$iv2 = element$iv$iv$iv = iterator.next();
                boolean bl2 = false;
                UUID it2 = (UUID)element$iv$iv2;
                boolean bl3 = false;
                if (PlayerExtensionsKt.getPlayer(it2) == null) continue;
                boolean bl4 = false;
                destination$iv$iv2.add(it$iv$iv);
            }
            Iterable list$iv$iv = (List)destination$iv$iv2;
            CollectionsKt.addAll((Collection)destination$iv$iv, (Iterable)list$iv$iv);
        }
        cobblemonNetwork.sendPacketToPlayers((List)destination$iv$iv, packet);
    }

    public final void sendSplitUpdate(@NotNull BattleActor privateActor, @NotNull NetworkPacket<?> publicPacket, @NotNull NetworkPacket<?> privatePacket) {
        Intrinsics.checkNotNullParameter((Object)privateActor, (String)"privateActor");
        Intrinsics.checkNotNullParameter(publicPacket, (String)"publicPacket");
        Intrinsics.checkNotNullParameter(privatePacket, (String)"privatePacket");
        Iterable<BattleActor> $this$forEach$iv = this.getActors();
        boolean $i$f$forEach = false;
        Iterator<BattleActor> iterator = $this$forEach$iv.iterator();
        while (iterator.hasNext()) {
            BattleActor element$iv;
            BattleActor it = element$iv = iterator.next();
            boolean bl = false;
            it.sendUpdate(Intrinsics.areEqual((Object)it, (Object)privateActor) ? privatePacket : publicPacket);
        }
        this.sendSpectatorUpdate(publicPacket);
    }

    /*
     * WARNING - void declaration
     */
    public final void sendSpectatorUpdate(@NotNull NetworkPacket<?> packet) {
        void $this$mapNotNullTo$iv$iv;
        void $this$mapNotNull$iv;
        Intrinsics.checkNotNullParameter(packet, (String)"packet");
        Iterable iterable = this.spectators;
        CobblemonNetwork cobblemonNetwork = CobblemonNetwork.INSTANCE;
        boolean $i$f$mapNotNull = false;
        void var4_5 = $this$mapNotNull$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$mapNotNullTo = false;
        void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
        boolean $i$f$forEach = false;
        Iterator iterator = $this$forEach$iv$iv$iv.iterator();
        while (iterator.hasNext()) {
            ServerPlayer it$iv$iv;
            Object element$iv$iv$iv;
            Object element$iv$iv = element$iv$iv$iv = iterator.next();
            boolean bl = false;
            UUID it = (UUID)element$iv$iv;
            boolean bl2 = false;
            if (PlayerExtensionsKt.getPlayer(it) == null) continue;
            boolean bl3 = false;
            destination$iv$iv.add(it$iv$iv);
        }
        cobblemonNetwork.sendPacketToPlayers((List)destination$iv$iv, packet);
    }

    public final void dispatch(@NotNull Function0<? extends DispatchResult> dispatcher) {
        Intrinsics.checkNotNullParameter(dispatcher, (String)"dispatcher");
        this.dispatches.add(arg_0 -> PokemonBattle.dispatch$lambda$53(dispatcher, arg_0));
    }

    public final void dispatchToFront(@NotNull Function0<? extends DispatchResult> dispatcher) {
        Intrinsics.checkNotNullParameter(dispatcher, (String)"dispatcher");
        this.dispatches.addFirst(arg_0 -> PokemonBattle.dispatchToFront$lambda$54(dispatcher, arg_0));
    }

    public final void dispatchGo(@NotNull Function0<Unit> dispatcher) {
        Intrinsics.checkNotNullParameter(dispatcher, (String)"dispatcher");
        this.dispatch((Function0<? extends DispatchResult>)((Function0)new Function0<DispatchResult>(dispatcher){
            final /* synthetic */ Function0<Unit> $dispatcher;
            {
                this.$dispatcher = $dispatcher;
                super(0);
            }

            @NotNull
            public final DispatchResult invoke() {
                this.$dispatcher.invoke();
                return DispatchResultKt.getGO();
            }
        }));
    }

    public final void dispatchWaiting(float delaySeconds, @NotNull Function0<Unit> dispatcher) {
        Intrinsics.checkNotNullParameter(dispatcher, (String)"dispatcher");
        this.dispatch((Function0<? extends DispatchResult>)((Function0)new Function0<DispatchResult>(dispatcher, delaySeconds){
            final /* synthetic */ Function0<Unit> $dispatcher;
            final /* synthetic */ float $delaySeconds;
            {
                this.$dispatcher = $dispatcher;
                this.$delaySeconds = $delaySeconds;
                super(0);
            }

            @NotNull
            public final DispatchResult invoke() {
                this.$dispatcher.invoke();
                return new WaitDispatch(this.$delaySeconds);
            }
        }));
    }

    public static /* synthetic */ void dispatchWaiting$default(PokemonBattle pokemonBattle, float f, Function0 function0, int n, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: dispatchWaiting");
        }
        if ((n & 1) != 0) {
            f = 1.0f;
        }
        pokemonBattle.dispatchWaiting(f, (Function0<Unit>)function0);
    }

    public final void dispatchInsert(@NotNull Function0<? extends Iterable<? extends BattleDispatch>> dispatcher) {
        Intrinsics.checkNotNullParameter(dispatcher, (String)"dispatcher");
        this.dispatch((Function0<? extends DispatchResult>)((Function0)new Function0<DispatchResult>(dispatcher, this){
            final /* synthetic */ Function0<Iterable<BattleDispatch>> $dispatcher;
            final /* synthetic */ PokemonBattle this$0;
            {
                this.$dispatcher = $dispatcher;
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final DispatchResult invoke() {
                Iterable newDispatches = (Iterable)this.$dispatcher.invoke();
                List previousDispatches = CollectionsKt.toList((Iterable)this.this$0.getDispatches());
                this.this$0.getDispatches().clear();
                CollectionsKt.addAll((Collection)this.this$0.getDispatches(), (Iterable)newDispatches);
                this.this$0.getDispatches().addAll(previousDispatches);
                return DispatchResultKt.getGO();
            }
        }));
    }

    public final void dispatch(@NotNull BattleDispatch dispatcher) {
        Intrinsics.checkNotNullParameter((Object)dispatcher, (String)"dispatcher");
        this.dispatches.add(dispatcher);
    }

    public final void dispatchToFront(@NotNull BattleDispatch dispatcher) {
        Intrinsics.checkNotNullParameter((Object)dispatcher, (String)"dispatcher");
        this.dispatches.addFirst(dispatcher);
    }

    public final void doWhenClear(@NotNull Function0<Unit> action2) {
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        this.afterDispatches.add(action2);
    }

    /*
     * WARNING - void declaration
     */
    public final void tick() {
        try {
            while (this.dispatchResult.canProceed() && this.dispatches.poll() != null) {
                BattleDispatch dispatch;
                this.dispatchResult = dispatch.invoke(this);
            }
            if (this.dispatches.isEmpty()) {
                Iterable $this$forEach$iv = CollectionsKt.toList((Iterable)this.afterDispatches);
                boolean $i$f$forEach = false;
                for (Object element$iv : $this$forEach$iv) {
                    Function0 it = (Function0)element$iv;
                    boolean bl = false;
                    it.invoke();
                }
                this.afterDispatches.clear();
            }
        }
        catch (Exception e) {
            void $this$filterIsInstanceTo$iv$iv;
            Cobblemon.INSTANCE.getLOGGER().error("Exception while ticking a battle. Saving battle log.", (Throwable)e);
            MutableComponent mutableComponent = LocalizationUtilsKt.battleLang("crash", new Object[0]);
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"battleLang(\"crash\")");
            MutableComponent message = TextKt.red(mutableComponent);
            Iterable<BattleActor> $this$filterIsInstance$iv = this.getActors();
            boolean $i$f$filterIsInstance = false;
            Iterable<BattleActor> it = $this$filterIsInstance$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterIsInstanceTo = false;
            for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
                if (!(element$iv$iv instanceof PlayerBattleActor)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            Iterable $this$forEach$iv = (List)destination$iv$iv;
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                PlayerBattleActor it2 = (PlayerBattleActor)element$iv;
                boolean bl = false;
                ServerPlayer serverPlayer = it2.getEntity();
                if (serverPlayer == null) continue;
                serverPlayer.m_213846_((Component)message);
            }
            this.saveBattleLog();
            this.stop();
            return;
        }
        if (this.started) {
            int n = this.ticks;
            this.setTicks(n + 1);
            if (this.isPvW() && !this.ended && this.dispatches.isEmpty()) {
                this.checkFlee();
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    public void checkFlee() {
        boolean wildPokemonOutOfRange2;
        Object it;
        Object it2;
        Object $this$filterTo$iv$iv;
        boolean $i$f$filter;
        Iterable $this$filter$iv;
        Object element$iv$iv;
        Object $this$filterIsInstanceTo$iv$iv;
        Collection destination$iv$iv;
        Object object;
        boolean $i$f$filterIsInstance;
        Iterable<BattleActor> $this$filterIsInstance$iv;
        block20: {
            $this$filterIsInstance$iv = this.getActors();
            $i$f$filterIsInstance = false;
            object = $this$filterIsInstance$iv;
            destination$iv$iv = new ArrayList();
            boolean $i$f$filterIsInstanceTo = false;
            Iterator iterator = $this$filterIsInstanceTo$iv$iv.iterator();
            while (iterator.hasNext()) {
                element$iv$iv = iterator.next();
                if (!(element$iv$iv instanceof FleeableBattleActor)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            $this$filter$iv = (List)destination$iv$iv;
            $i$f$filter = false;
            $this$filterIsInstanceTo$iv$iv = $this$filter$iv;
            destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            iterator = $this$filterTo$iv$iv.iterator();
            while (iterator.hasNext()) {
                element$iv$iv = iterator.next();
                it2 = (FleeableBattleActor)element$iv$iv;
                boolean bl = false;
                if (!(it2.getWorldAndPosition() != null)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            Iterable $this$none$iv = (List)destination$iv$iv;
            boolean $i$f$none = false;
            if ($this$none$iv instanceof Collection && ((Collection)$this$none$iv).isEmpty()) {
                v0 = true;
            } else {
                for (Object element$iv : $this$none$iv) {
                    boolean bl;
                    FleeableBattleActor pokemonActor = (FleeableBattleActor)element$iv;
                    boolean bl2 = false;
                    if (pokemonActor.getFleeDistance() == -1.0f) {
                        bl = true;
                    } else {
                        Double nearestPlayerActorDistance2;
                        void $this$filterIsInstance$iv2;
                        Intrinsics.checkNotNull(pokemonActor.getWorldAndPosition());
                        ServerLevel world = (ServerLevel)element$iv$iv.component1();
                        Vec3 pos = (Vec3)element$iv$iv.component2();
                        Sequence sequence = SequencesKt.filter((Sequence)CollectionsKt.asSequence(this.getActors()), (Function1)checkFlee.wildPokemonOutOfRange.2.nearestPlayerActorDistance.1.INSTANCE);
                        boolean $i$f$filterIsInstance22 = false;
                        Sequence sequence2 = SequencesKt.filter((Sequence)$this$filterIsInstance$iv2, (Function1)checkFlee$lambda$59$$inlined$filterIsInstance$1.INSTANCE);
                        Intrinsics.checkNotNull((Object)sequence2, (String)"null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
                        Iterator $i$f$filterIsInstance22 = SequencesKt.filter((Sequence)SequencesKt.mapNotNull((Sequence)sequence2, (Function1)checkFlee.wildPokemonOutOfRange.2.nearestPlayerActorDistance.2.INSTANCE), (Function1)((Function1)new Function1<LivingEntity, Boolean>(world){
                            final /* synthetic */ ServerLevel $world;
                            {
                                this.$world = $world;
                                super(1);
                            }

                            @NotNull
                            public final Boolean invoke(@NotNull LivingEntity it) {
                                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                                return Intrinsics.areEqual((Object)it.m_9236_(), (Object)this.$world);
                            }
                        })).iterator();
                        if (!$i$f$filterIsInstance22.hasNext()) {
                            v3 = null;
                        } else {
                            it = (LivingEntity)$i$f$filterIsInstance22.next();
                            boolean bl3 = false;
                            double d = pos.m_82554_(it.m_20182_());
                            while ($i$f$filterIsInstance22.hasNext()) {
                                LivingEntity it3 = (LivingEntity)$i$f$filterIsInstance22.next();
                                $i$a$-minOfOrNull-PokemonBattle$checkFlee$wildPokemonOutOfRange$2$nearestPlayerActorDistance$4 = false;
                                double d2 = pos.m_82554_(it3.m_20182_());
                                d = Math.min(d, d2);
                            }
                            v3 = nearestPlayerActorDistance2 = Double.valueOf(d);
                        }
                        bl = nearestPlayerActorDistance2 != null && nearestPlayerActorDistance2 < (double)pokemonActor.getFleeDistance();
                    }
                    if (!bl) continue;
                    v0 = false;
                    break block20;
                }
                v0 = wildPokemonOutOfRange2 = true;
            }
        }
        if (wildPokemonOutOfRange2) {
            void events$iv;
            void $this$iv;
            boolean bl;
            Object element$iv$iv2;
            Iterable<BattleActor> $this$mapNotNullTo$iv$iv;
            $this$filter$iv = this.getActors();
            $i$f$filter = false;
            $this$filterTo$iv$iv = $this$filter$iv;
            destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            Iterator<Object> bl2 = $this$filterTo$iv$iv.iterator();
            while (bl2.hasNext()) {
                element$iv$iv = bl2.next();
                it2 = (BattleActor)element$iv$iv;
                boolean bl4 = false;
                if (!(((BattleActor)it2).getType() == ActorType.WILD)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            $this$filterIsInstance$iv = (List)destination$iv$iv;
            $i$f$filterIsInstance = false;
            $this$filterTo$iv$iv = $this$filterIsInstance$iv;
            destination$iv$iv = new ArrayList();
            boolean $i$f$filterIsInstanceTo = false;
            bl2 = $this$filterIsInstanceTo$iv$iv.iterator();
            while (bl2.hasNext()) {
                element$iv$iv = bl2.next();
                if (!(element$iv$iv instanceof EntityBackedBattleActor)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            Iterable $this$mapNotNull$iv = (List)destination$iv$iv;
            boolean $i$f$mapNotNull = false;
            $this$filterIsInstanceTo$iv$iv = $this$mapNotNull$iv;
            destination$iv$iv = new ArrayList();
            boolean $i$f$mapNotNullTo = false;
            Iterator $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
            boolean $i$f$forEach = false;
            Iterator iterator = $this$forEach$iv$iv$iv.iterator();
            while (iterator.hasNext()) {
                Object it$iv$iv;
                Object element$iv$iv$iv;
                element$iv$iv2 = element$iv$iv$iv = iterator.next();
                boolean bl5 = false;
                it = (EntityBackedBattleActor)element$iv$iv2;
                boolean bl6 = false;
                if (it.getEntity() == null) continue;
                bl = false;
                destination$iv$iv.add(it$iv$iv);
            }
            $this$filterIsInstance$iv = (List)destination$iv$iv;
            $i$f$filterIsInstance = false;
            $this$mapNotNullTo$iv$iv = $this$filterIsInstance$iv;
            destination$iv$iv = new ArrayList();
            $i$f$filterIsInstanceTo = false;
            $this$forEach$iv$iv$iv = $this$filterIsInstanceTo$iv$iv.iterator();
            while ($this$forEach$iv$iv$iv.hasNext()) {
                Object element$iv$iv3 = $this$forEach$iv$iv$iv.next();
                if (!(element$iv$iv3 instanceof PokemonEntity)) continue;
                destination$iv$iv.add(element$iv$iv3);
            }
            Object $this$forEach$iv = (List)destination$iv$iv;
            boolean $i$f$forEach22 = false;
            $this$filterIsInstanceTo$iv$iv = $this$forEach$iv.iterator();
            while ($this$filterIsInstanceTo$iv$iv.hasNext()) {
                Object element$iv;
                element$iv = $this$filterIsInstanceTo$iv$iv.next();
                PokemonEntity it4 = (PokemonEntity)element$iv;
                boolean bl7 = false;
                it4.getPokemon().heal();
            }
            $this$forEach$iv = CobblemonEvents.BATTLE_FLED;
            BattleFledEvent[] $i$f$forEach22 = new BattleFledEvent[1];
            Object $this$filterIsInstance$iv3 = CollectionsKt.asSequence(this.getActors());
            boolean $i$f$filterIsInstance2 = false;
            Sequence sequence = SequencesKt.filter((Sequence)$this$filterIsInstance$iv3, (Function1)checkFlee$$inlined$filterIsInstance$1.INSTANCE);
            Intrinsics.checkNotNull((Object)sequence, (String)"null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
            $i$f$forEach22[0] = new BattleFledEvent(this, (PlayerBattleActor)sequence.iterator().next());
            boolean $i$f$post = false;
            $this$iv.emit(Arrays.copyOf(events$iv, ((void)events$iv).length));
            void $this$forEach$iv$iv = events$iv;
            boolean $i$f$forEach32 = false;
            for (void element$iv$iv4 : $this$forEach$iv$iv) {
                element$iv$iv2 = element$iv$iv4;
                boolean bl8 = false;
                Object it5 = element$iv$iv2;
            }
            $this$filterIsInstance$iv = this.getActors();
            $i$f$filterIsInstance = false;
            $this$filterIsInstance$iv3 = $this$filterIsInstance$iv;
            destination$iv$iv = new ArrayList();
            $i$f$filterIsInstanceTo = false;
            Iterator $i$f$forEach32 = $this$filterIsInstanceTo$iv$iv.iterator();
            while ($i$f$forEach32.hasNext()) {
                Object element$iv$iv5 = $i$f$forEach32.next();
                if (!(element$iv$iv5 instanceof EntityBackedBattleActor)) continue;
                destination$iv$iv.add(element$iv$iv5);
            }
            $this$mapNotNull$iv = (List)destination$iv$iv;
            $i$f$mapNotNull = false;
            $this$filterIsInstanceTo$iv$iv = $this$mapNotNull$iv;
            destination$iv$iv = new ArrayList();
            $i$f$mapNotNullTo = false;
            Iterable<BattleActor> $this$forEach$iv$iv$iv2 = $this$mapNotNullTo$iv$iv;
            $i$f$forEach = false;
            for (BattleActor element$iv$iv$iv : $this$forEach$iv$iv$iv2) {
                Object it$iv$iv;
                element$iv$iv2 = element$iv$iv$iv;
                boolean bl9 = false;
                it = (EntityBackedBattleActor)element$iv$iv2;
                boolean bl10 = false;
                if (it.getEntity() == null) continue;
                bl = false;
                destination$iv$iv.add(it$iv$iv);
            }
            $this$forEach$iv = (List)destination$iv$iv;
            boolean $i$f$forEach4 = false;
            object = $this$forEach$iv.iterator();
            while (object.hasNext()) {
                Object element$iv = object.next();
                LivingEntity it6 = (LivingEntity)element$iv;
                boolean bl11 = false;
                MutableComponent mutableComponent = LocalizationUtilsKt.battleLang("flee", new Object[0]);
                Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"battleLang(\"flee\")");
                it6.m_213846_((Component)TextKt.yellow(mutableComponent));
            }
            this.stop();
        }
    }

    public final void stop() {
        this.end();
        String[] stringArray = new String[]{">forcetie"};
        this.writeShowdownAction(stringArray);
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void checkForInputDispatch() {
        void $this$filterTo$iv$iv;
        boolean bl;
        BattleActor it;
        boolean bl2;
        if (this.checkForfeit()) {
            return;
        }
        Iterable<BattleActor> $this$any$iv = this.getActors();
        boolean $i$f$any = false;
        if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
            return;
        }
        Iterator<Object> iterator = $this$any$iv.iterator();
        do {
            BattleActor battleActor;
            if (!iterator.hasNext()) return;
            it = battleActor = iterator.next();
            boolean bl3 = false;
            if (!it.getMustChoose() && !((Collection)it.getResponses()).isEmpty()) {
                bl2 = true;
                continue;
            }
            bl2 = false;
        } while (!bl2);
        boolean bl4 = true;
        if (!bl4) return;
        Iterable<BattleActor> $this$none$iv = this.getActors();
        boolean $i$f$none = false;
        if ($this$none$iv instanceof Collection && ((Collection)$this$none$iv).isEmpty()) {
            bl = true;
        } else {
            iterator = $this$none$iv.iterator();
            while (iterator.hasNext()) {
                BattleActor battleActor;
                it = battleActor = iterator.next();
                boolean bl5 = false;
                if (!it.getMustChoose()) continue;
                return;
            }
            bl = true;
        }
        if (!bl) return;
        boolean bl6 = true;
        boolean readyToInput = bl6;
        if (!readyToInput) return;
        if (!this.captureActions.isEmpty()) return;
        Iterable<BattleActor> $this$filter$iv = this.getActors();
        boolean $i$f$filter = false;
        iterator = $this$filter$iv;
        Collection collection = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            BattleActor it2 = (BattleActor)element$iv$iv;
            boolean bl7 = false;
            boolean bl8 = !((Collection)it2.getResponses()).isEmpty();
            if (!bl8) continue;
            collection.add(element$iv$iv);
        }
        Iterable<BattleActor> $this$forEach$iv = (List)collection;
        boolean $i$f$forEach = false;
        for (Object t : $this$forEach$iv) {
            BattleActor it3 = (BattleActor)t;
            boolean bl9 = false;
            it3.writeShowdownResponse();
        }
        $this$forEach$iv = this.getActors();
        $i$f$forEach = false;
        iterator = $this$forEach$iv.iterator();
        while (iterator.hasNext()) {
            Object object = iterator.next();
            BattleActor it4 = (BattleActor)object;
            boolean bl10 = false;
            it4.getResponses().clear();
            it4.setRequest(null);
        }
    }

    private final boolean checkForfeit() {
        boolean bl;
        BattleActor forfeit;
        BattleActor battleActor;
        block7: {
            Iterable<BattleActor> iterable = this.getActors();
            Iterator<BattleActor> iterator = iterable.iterator();
            while (iterator.hasNext()) {
                boolean bl2;
                BattleActor battleActor2;
                block6: {
                    BattleActor it = battleActor2 = iterator.next();
                    boolean bl3 = false;
                    Iterable $this$any$iv = it.getResponses();
                    boolean $i$f$any = false;
                    if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                        bl2 = false;
                    } else {
                        for (Object element$iv : $this$any$iv) {
                            ShowdownActionResponse it2 = (ShowdownActionResponse)element$iv;
                            boolean bl4 = false;
                            if (!(it2 instanceof ForfeitActionResponse)) continue;
                            bl2 = true;
                            break block6;
                        }
                        bl2 = false;
                    }
                }
                if (!bl2) continue;
                battleActor = battleActor2;
                break block7;
            }
            battleActor = null;
        }
        BattleActor battleActor3 = forfeit = (BattleActor)battleActor;
        if (battleActor3 != null) {
            BattleActor it = battleActor3;
            boolean bl5 = false;
            PokemonBattle.dispatchWaiting$default(this, 0.0f, (Function0)new Function0<Unit>(this, it){
                final /* synthetic */ PokemonBattle this$0;
                final /* synthetic */ BattleActor $it;
                {
                    this.this$0 = $receiver;
                    this.$it = $it;
                    super(0);
                }

                public final void invoke() {
                    Object[] objectArray = new Object[]{this.$it.getName()};
                    MutableComponent mutableComponent = LocalizationUtilsKt.battleLang("forfeit", objectArray);
                    Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"battleLang(\"forfeit\", it.getName())");
                    this.this$0.broadcastChatMessage((Component)TextKt.red(mutableComponent));
                }
            }, 1, null);
            String[] stringArray = new String[]{">forcelose " + it.getShowdownId()};
            this.writeShowdownAction(stringArray);
            bl = true;
        } else {
            bl = false;
        }
        return bl;
    }

    @NotNull
    public final Component createUnimplemented$common(@NotNull BattleMessage message) {
        Intrinsics.checkNotNullParameter((Object)message, (String)"message");
        Cobblemon.INSTANCE.getLOGGER().error("Missing interpretation on '{}' action {}", (Object)message.getId(), (Object)message.getRawMessage());
        MutableComponent mutableComponent = Component.m_237113_((String)("Missing interpretation on '" + message.getId() + "' action " + message.getRawMessage()));
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"literal(\"Missing interpr\u2026n ${message.rawMessage}\")");
        return (Component)TextKt.red(mutableComponent);
    }

    @NotNull
    public final Component createUnimplementedSplit$common(@NotNull BattleMessage publicMessage, @NotNull BattleMessage privateMessage) {
        Intrinsics.checkNotNullParameter((Object)publicMessage, (String)"publicMessage");
        Intrinsics.checkNotNullParameter((Object)privateMessage, (String)"privateMessage");
        if (!Intrinsics.areEqual((Object)publicMessage.getId(), (Object)privateMessage.getId())) {
            throw new IllegalArgumentException("Messages do not match");
        }
        Cobblemon.INSTANCE.getLOGGER().error("Missing interpretation on '{}' action: \nPublic \u00bb {}\nPrivate \u00bb {}", (Object)publicMessage.getId(), (Object)publicMessage.getRawMessage(), (Object)privateMessage.getRawMessage());
        MutableComponent mutableComponent = Component.m_237113_((String)("Missing interpretation on '" + publicMessage.getId() + "' action please report to the developers"));
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"literal(\"Missing interpr\u2026eport to the developers\")");
        return (Component)TextKt.red(mutableComponent);
    }

    @NotNull
    public final QueryStruct addQueryFunctions(@NotNull QueryStruct queryStruct) {
        Intrinsics.checkNotNullParameter((Object)queryStruct, (String)"queryStruct");
        queryStruct.addFunction("pvp", arg_0 -> PokemonBattle.addQueryFunctions$lambda$73(this, arg_0));
        queryStruct.addFunction("pvn", arg_0 -> PokemonBattle.addQueryFunctions$lambda$74(this, arg_0));
        queryStruct.addFunction("pvw", arg_0 -> PokemonBattle.addQueryFunctions$lambda$75(this, arg_0));
        queryStruct.addFunction("has_rule", arg_0 -> PokemonBattle.addQueryFunctions$lambda$76(this, arg_0));
        return queryStruct;
    }

    private static final DispatchResult dispatch$lambda$53(Function0 $dispatcher, PokemonBattle it) {
        Intrinsics.checkNotNullParameter((Object)$dispatcher, (String)"$dispatcher");
        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
        return (DispatchResult)$dispatcher.invoke();
    }

    private static final DispatchResult dispatchToFront$lambda$54(Function0 $dispatcher, PokemonBattle it) {
        Intrinsics.checkNotNullParameter((Object)$dispatcher, (String)"$dispatcher");
        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
        return (DispatchResult)$dispatcher.invoke();
    }

    private static final Object addQueryFunctions$lambda$73(PokemonBattle this$0, MoParams it) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        return new DoubleValue(this$0.isPvP());
    }

    private static final Object addQueryFunctions$lambda$74(PokemonBattle this$0, MoParams it) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        return new DoubleValue(this$0.isPvN());
    }

    private static final Object addQueryFunctions$lambda$75(PokemonBattle this$0, MoParams it) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        return new DoubleValue(this$0.isPvW());
    }

    private static final Object addQueryFunctions$lambda$76(PokemonBattle this$0, MoParams params) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        return new DoubleValue(this$0.format.getRuleSet().contains(params.getString(0)));
    }
}

