/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.ranges.IntRange
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.ItemDropMethod;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.calculators.CaptureCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.Category;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.LastChangedVersion;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.NodeCategory;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.constraint.IntConstraint;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.catching.calculators.CobblemonCaptureCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.CaptureCalculatorAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IntRangeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Type;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u0006\n\u0002\b\t\n\u0002\u0010\u0007\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b3\n\u0002\u0010\u000e\n\u0002\bA\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b7\u0018\u0000 \u00e6\u00012\u00020\u0001:\u0002\u00e6\u0001B\b\u00a2\u0006\u0005\b\u00e5\u0001\u00108R\"\u0010\u0003\u001a\u00020\u00028\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0012\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\"\u0010\t\u001a\u00020\u00028\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0012\n\u0004\b\t\u0010\u0004\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\"\u0010\r\u001a\u00020\f8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0012\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\"\u0010\u0013\u001a\u00020\u00028\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0012\n\u0004\b\u0013\u0010\u0004\u001a\u0004\b\u0014\u0010\u0006\"\u0004\b\u0015\u0010\bR\"\u0010\u0017\u001a\u00020\u00168\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0012\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\"\u0010\u001d\u001a\u00020\u00028\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0012\n\u0004\b\u001d\u0010\u0004\u001a\u0004\b\u001e\u0010\u0006\"\u0004\b\u001f\u0010\bR\"\u0010!\u001a\u00020 8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0012\n\u0004\b!\u0010\"\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\"\u0010'\u001a\u00020\u00168\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0012\n\u0004\b'\u0010\u0018\u001a\u0004\b(\u0010\u001a\"\u0004\b)\u0010\u001cR\"\u0010+\u001a\u00020*8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0012\n\u0004\b+\u0010,\u001a\u0004\b-\u0010.\"\u0004\b/\u00100R\"\u00101\u001a\u00020 8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0012\n\u0004\b1\u0010\"\u001a\u0004\b2\u0010$\"\u0004\b3\u0010&R(\u00104\u001a\u00020\f8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0018\n\u0004\b4\u0010\u000e\u0012\u0004\b7\u00108\u001a\u0004\b5\u0010\u0010\"\u0004\b6\u0010\u0012R\"\u0010:\u001a\u0002098\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0012\n\u0004\b:\u0010;\u001a\u0004\b<\u0010=\"\u0004\b>\u0010?R\"\u0010@\u001a\u00020\f8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0012\n\u0004\b@\u0010\u000e\u001a\u0004\bA\u0010\u0010\"\u0004\bB\u0010\u0012R\"\u0010C\u001a\u00020 8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0012\n\u0004\bC\u0010\"\u001a\u0004\bD\u0010$\"\u0004\bE\u0010&R\"\u0010F\u001a\u00020\f8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0012\n\u0004\bF\u0010\u000e\u001a\u0004\bG\u0010\u0010\"\u0004\bH\u0010\u0012R\"\u0010I\u001a\u00020\u00028\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0012\n\u0004\bI\u0010\u0004\u001a\u0004\bJ\u0010\u0006\"\u0004\bK\u0010\bR\"\u0010L\u001a\u00020\u00028\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0012\n\u0004\bL\u0010\u0004\u001a\u0004\bM\u0010\u0006\"\u0004\bN\u0010\bR\"\u0010O\u001a\u00020\u00028\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0012\n\u0004\bO\u0010\u0004\u001a\u0004\bP\u0010\u0006\"\u0004\bQ\u0010\bR\"\u0010R\u001a\u00020\u00168\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0012\n\u0004\bR\u0010\u0018\u001a\u0004\bS\u0010\u001a\"\u0004\bT\u0010\u001cR\"\u0010U\u001a\u00020 8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0012\n\u0004\bU\u0010\"\u001a\u0004\bV\u0010$\"\u0004\bW\u0010&R\"\u0010X\u001a\u00020\u00168\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0012\n\u0004\bX\u0010\u0018\u001a\u0004\bY\u0010\u001a\"\u0004\bZ\u0010\u001cR\"\u0010[\u001a\u00020\u00028\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0012\n\u0004\b[\u0010\u0004\u001a\u0004\b\\\u0010\u0006\"\u0004\b]\u0010\bR\"\u0010^\u001a\u00020\u00028\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0012\n\u0004\b^\u0010\u0004\u001a\u0004\b_\u0010\u0006\"\u0004\b`\u0010\bR\"\u0010a\u001a\u00020 8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0012\n\u0004\ba\u0010\"\u001a\u0004\bb\u0010$\"\u0004\bc\u0010&R\"\u0010d\u001a\u00020\u00168\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0012\n\u0004\bd\u0010\u0018\u001a\u0004\be\u0010\u001a\"\u0004\bf\u0010\u001cR\"\u0010g\u001a\u00020\f8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0012\n\u0004\bg\u0010\u000e\u001a\u0004\bh\u0010\u0010\"\u0004\bi\u0010\u0012R\"\u0010j\u001a\u00020\u00028\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0012\n\u0004\bj\u0010\u0004\u001a\u0004\bk\u0010\u0006\"\u0004\bl\u0010\bR\"\u0010n\u001a\u00020m8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bn\u0010o\u001a\u0004\bp\u0010q\"\u0004\br\u0010sR\"\u0010t\u001a\u00020\u00168\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0012\n\u0004\bt\u0010\u0018\u001a\u0004\bu\u0010\u001a\"\u0004\bv\u0010\u001cR(\u0010w\u001a\u00020\f8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0018\n\u0004\bw\u0010\u000e\u0012\u0004\bz\u00108\u001a\u0004\bx\u0010\u0010\"\u0004\by\u0010\u0012R\"\u0010{\u001a\u00020 8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0012\n\u0004\b{\u0010\"\u001a\u0004\b|\u0010$\"\u0004\b}\u0010&R#\u0010~\u001a\u00020\f8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0013\n\u0004\b~\u0010\u000e\u001a\u0004\b\u007f\u0010\u0010\"\u0005\b\u0080\u0001\u0010\u0012R&\u0010\u0081\u0001\u001a\u00020\f8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0015\n\u0005\b\u0081\u0001\u0010\u000e\u001a\u0005\b\u0082\u0001\u0010\u0010\"\u0005\b\u0083\u0001\u0010\u0012R&\u0010\u0084\u0001\u001a\u00020\f8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0015\n\u0005\b\u0084\u0001\u0010\u000e\u001a\u0005\b\u0085\u0001\u0010\u0010\"\u0005\b\u0086\u0001\u0010\u0012R-\u0010\u0087\u0001\u001a\u00020\f8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u001c\n\u0005\b\u0087\u0001\u0010\u000e\u0012\u0005\b\u008a\u0001\u00108\u001a\u0005\b\u0088\u0001\u0010\u0010\"\u0005\b\u0089\u0001\u0010\u0012R-\u0010\u008b\u0001\u001a\u00020\f8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u001c\n\u0005\b\u008b\u0001\u0010\u000e\u0012\u0005\b\u008e\u0001\u00108\u001a\u0005\b\u008c\u0001\u0010\u0010\"\u0005\b\u008d\u0001\u0010\u0012R&\u0010\u008f\u0001\u001a\u00020\f8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0015\n\u0005\b\u008f\u0001\u0010\u000e\u001a\u0005\b\u0090\u0001\u0010\u0010\"\u0005\b\u0091\u0001\u0010\u0012R-\u0010\u0092\u0001\u001a\u00020\f8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u001c\n\u0005\b\u0092\u0001\u0010\u000e\u0012\u0005\b\u0095\u0001\u00108\u001a\u0005\b\u0093\u0001\u0010\u0010\"\u0005\b\u0094\u0001\u0010\u0012R&\u0010\u0096\u0001\u001a\u00020\f8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0015\n\u0005\b\u0096\u0001\u0010\u000e\u001a\u0005\b\u0097\u0001\u0010\u0010\"\u0005\b\u0098\u0001\u0010\u0012R&\u0010\u0099\u0001\u001a\u00020 8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0015\n\u0005\b\u0099\u0001\u0010\"\u001a\u0005\b\u009a\u0001\u0010$\"\u0005\b\u009b\u0001\u0010&R&\u0010\u009c\u0001\u001a\u00020\u00168\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0015\n\u0005\b\u009c\u0001\u0010\u0018\u001a\u0005\b\u009d\u0001\u0010\u001a\"\u0005\b\u009e\u0001\u0010\u001cR-\u0010\u009f\u0001\u001a\u00020\f8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u001c\n\u0005\b\u009f\u0001\u0010\u000e\u0012\u0005\b\u00a2\u0001\u00108\u001a\u0005\b\u00a0\u0001\u0010\u0010\"\u0005\b\u00a1\u0001\u0010\u0012R&\u0010\u00a3\u0001\u001a\u00020 8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0015\n\u0005\b\u00a3\u0001\u0010\"\u001a\u0005\b\u00a4\u0001\u0010$\"\u0005\b\u00a5\u0001\u0010&R&\u0010\u00a6\u0001\u001a\u00020m8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0015\n\u0005\b\u00a6\u0001\u0010o\u001a\u0005\b\u00a7\u0001\u0010q\"\u0005\b\u00a8\u0001\u0010sR&\u0010\u00a9\u0001\u001a\u00020m8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0015\n\u0005\b\u00a9\u0001\u0010o\u001a\u0005\b\u00aa\u0001\u0010q\"\u0005\b\u00ab\u0001\u0010sR&\u0010\u00ac\u0001\u001a\u00020\u00028\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0015\n\u0005\b\u00ac\u0001\u0010\u0004\u001a\u0005\b\u00ad\u0001\u0010\u0006\"\u0005\b\u00ae\u0001\u0010\bR7\u0010\u00b1\u0001\u001a\u0010\u0012\u0004\u0012\u00020m\u0012\u0005\u0012\u00030\u00b0\u00010\u00af\u00018\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0018\n\u0006\b\u00b1\u0001\u0010\u00b2\u0001\u001a\u0006\b\u00b3\u0001\u0010\u00b4\u0001\"\u0006\b\u00b5\u0001\u0010\u00b6\u0001R&\u0010\u00b7\u0001\u001a\u00020\f8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0015\n\u0005\b\u00b7\u0001\u0010\u000e\u001a\u0005\b\u00b8\u0001\u0010\u0010\"\u0005\b\u00b9\u0001\u0010\u0012R&\u0010\u00ba\u0001\u001a\u00020 8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0015\n\u0005\b\u00ba\u0001\u0010\"\u001a\u0005\b\u00bb\u0001\u0010$\"\u0005\b\u00bc\u0001\u0010&R&\u0010\u00bd\u0001\u001a\u00020\f8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0015\n\u0005\b\u00bd\u0001\u0010\u000e\u001a\u0005\b\u00be\u0001\u0010\u0010\"\u0005\b\u00bf\u0001\u0010\u0012R&\u0010\u00c0\u0001\u001a\u00020\u00028\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0015\n\u0005\b\u00c0\u0001\u0010\u0004\u001a\u0005\b\u00c1\u0001\u0010\u0006\"\u0005\b\u00c2\u0001\u0010\bR&\u0010\u00c3\u0001\u001a\u00020 8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0015\n\u0005\b\u00c3\u0001\u0010\"\u001a\u0005\b\u00c4\u0001\u0010$\"\u0005\b\u00c5\u0001\u0010&R-\u0010\u00c6\u0001\u001a\u00020\f8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u001c\n\u0005\b\u00c6\u0001\u0010\u000e\u0012\u0005\b\u00c9\u0001\u00108\u001a\u0005\b\u00c7\u0001\u0010\u0010\"\u0005\b\u00c8\u0001\u0010\u0012R&\u0010\u00ca\u0001\u001a\u00020\u00028\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0015\n\u0005\b\u00ca\u0001\u0010\u0004\u001a\u0005\b\u00cb\u0001\u0010\u0006\"\u0005\b\u00cc\u0001\u0010\bR&\u0010\u00cd\u0001\u001a\u00020\u00028\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0015\n\u0005\b\u00cd\u0001\u0010\u0004\u001a\u0005\b\u00ce\u0001\u0010\u0006\"\u0005\b\u00cf\u0001\u0010\bR&\u0010\u00d0\u0001\u001a\u00020 8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0015\n\u0005\b\u00d0\u0001\u0010\"\u001a\u0005\b\u00d1\u0001\u0010$\"\u0005\b\u00d2\u0001\u0010&R&\u0010\u00d3\u0001\u001a\u00020m8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0015\n\u0005\b\u00d3\u0001\u0010o\u001a\u0005\b\u00d4\u0001\u0010q\"\u0005\b\u00d5\u0001\u0010sR&\u0010\u00d6\u0001\u001a\u00020 8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0015\n\u0005\b\u00d6\u0001\u0010\"\u001a\u0005\b\u00d7\u0001\u0010$\"\u0005\b\u00d8\u0001\u0010&R&\u0010\u00d9\u0001\u001a\u00020 8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0015\n\u0005\b\u00d9\u0001\u0010\"\u001a\u0005\b\u00da\u0001\u0010$\"\u0005\b\u00db\u0001\u0010&R&\u0010\u00dc\u0001\u001a\u00020\u00028\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0015\n\u0005\b\u00dc\u0001\u0010\u0004\u001a\u0005\b\u00dd\u0001\u0010\u0006\"\u0005\b\u00de\u0001\u0010\bR&\u0010\u00df\u0001\u001a\u00020\f8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0015\n\u0005\b\u00df\u0001\u0010\u000e\u001a\u0005\b\u00e0\u0001\u0010\u0010\"\u0005\b\u00e1\u0001\u0010\u0012R&\u0010\u00e2\u0001\u001a\u00020\f8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0015\n\u0005\b\u00e2\u0001\u0010\u000e\u001a\u0005\b\u00e3\u0001\u0010\u0010\"\u0005\b\u00e4\u0001\u0010\u0012\u00a8\u0006\u00e7\u0001"}, d2={"Lcom/cobblemon/mod/common/config/CobblemonConfig;", "", "", "allowExperienceFromPvP", "Z", "getAllowExperienceFromPvP", "()Z", "setAllowExperienceFromPvP", "(Z)V", "allowSpectating", "getAllowSpectating", "setAllowSpectating", "", "ambientPokemonCryTicks", "I", "getAmbientPokemonCryTicks", "()I", "setAmbientPokemonCryTicks", "(I)V", "announceDropItems", "getAnnounceDropItems", "setAnnounceDropItems", "", "appleLeftoversChance", "D", "getAppleLeftoversChance", "()D", "setAppleLeftoversChance", "(D)V", "autoUpdateShowdown", "getAutoUpdateShowdown", "setAutoUpdateShowdown", "", "baseApricornTreeGenerationChance", "F", "getBaseApricornTreeGenerationChance", "()F", "setBaseApricornTreeGenerationChance", "(F)V", "bigRootPropagationChance", "getBigRootPropagationChance", "setBigRootPropagationChance", "Lcom/cobblemon/mod/common/api/pokeball/catching/calculators/CaptureCalculator;", "captureCalculator", "Lcom/cobblemon/mod/common/api/pokeball/catching/calculators/CaptureCalculator;", "getCaptureCalculator", "()Lcom/cobblemon/mod/common/api/pokeball/catching/calculators/CaptureCalculator;", "setCaptureCalculator", "(Lcom/cobblemon/mod/common/api/pokeball/catching/calculators/CaptureCalculator;)V", "chargeGainedPerTick", "getChargeGainedPerTick", "setChargeGainedPerTick", "defaultBoxCount", "getDefaultBoxCount", "setDefaultBoxCount", "getDefaultBoxCount$annotations", "()V", "Lcom/cobblemon/mod/common/api/drop/ItemDropMethod;", "defaultDropItemMethod", "Lcom/cobblemon/mod/common/api/drop/ItemDropMethod;", "getDefaultDropItemMethod", "()Lcom/cobblemon/mod/common/api/drop/ItemDropMethod;", "setDefaultDropItemMethod", "(Lcom/cobblemon/mod/common/api/drop/ItemDropMethod;)V", "defaultFaintTimer", "getDefaultFaintTimer", "setDefaultFaintTimer", "defaultFleeDistance", "getDefaultFleeDistance", "setDefaultFleeDistance", "defaultPasturedPokemonLimit", "getDefaultPasturedPokemonLimit", "setDefaultPasturedPokemonLimit", "displayEntityLevelLabel", "getDisplayEntityLevelLabel", "setDisplayEntityLevelLabel", "enableDebugKeys", "getEnableDebugKeys", "setEnableDebugKeys", "enableSpawning", "getEnableSpawning", "setEnableSpawning", "energyRootChance", "getEnergyRootChance", "setEnergyRootChance", "experienceMultiplier", "getExperienceMultiplier", "setExperienceMultiplier", "experienceShareMultiplier", "getExperienceShareMultiplier", "setExperienceShareMultiplier", "exportSpawnConfig", "getExportSpawnConfig", "setExportSpawnConfig", "exportStarterConfig", "getExportStarterConfig", "setExportStarterConfig", "faintAwakenHealthPercent", "getFaintAwakenHealthPercent", "setFaintAwakenHealthPercent", "healPercent", "getHealPercent", "setHealPercent", "healTimer", "getHealTimer", "setHealTimer", "infiniteHealerCharge", "getInfiniteHealerCharge", "setInfiniteHealerCharge", "", "lastSavedVersion", "Ljava/lang/String;", "getLastSavedVersion", "()Ljava/lang/String;", "setLastSavedVersion", "(Ljava/lang/String;)V", "luckyEggMultiplier", "getLuckyEggMultiplier", "setLuckyEggMultiplier", "maxDynamaxLevel", "getMaxDynamaxLevel", "setMaxDynamaxLevel", "getMaxDynamaxLevel$annotations", "maxHealerCharge", "getMaxHealerCharge", "setMaxHealerCharge", "maxInsertedFossilItems", "getMaxInsertedFossilItems", "setMaxInsertedFossilItems", "maxNearbyBlocksHorizontalRange", "getMaxNearbyBlocksHorizontalRange", "setMaxNearbyBlocksHorizontalRange", "maxNearbyBlocksVerticalRange", "getMaxNearbyBlocksVerticalRange", "setMaxNearbyBlocksVerticalRange", "maxPokemonFriendship", "getMaxPokemonFriendship", "setMaxPokemonFriendship", "getMaxPokemonFriendship$annotations", "maxPokemonLevel", "getMaxPokemonLevel", "setMaxPokemonLevel", "getMaxPokemonLevel$annotations", "maxRootsInArea", "getMaxRootsInArea", "setMaxRootsInArea", "maxVerticalCorrectionBlocks", "getMaxVerticalCorrectionBlocks", "setMaxVerticalCorrectionBlocks", "getMaxVerticalCorrectionBlocks$annotations", "maxVerticalSpace", "getMaxVerticalSpace", "setMaxVerticalSpace", "maximumSliceDistanceFromPlayer", "getMaximumSliceDistanceFromPlayer", "setMaximumSliceDistanceFromPlayer", "minimumDistanceBetweenEntities", "getMinimumDistanceBetweenEntities", "setMinimumDistanceBetweenEntities", "minimumLevelRangeMax", "getMinimumLevelRangeMax", "setMinimumLevelRangeMax", "getMinimumLevelRangeMax$annotations", "minimumSliceDistanceFromPlayer", "getMinimumSliceDistanceFromPlayer", "setMinimumSliceDistanceFromPlayer", "mongoDBConnectionString", "getMongoDBConnectionString", "setMongoDBConnectionString", "mongoDBDatabaseName", "getMongoDBDatabaseName", "setMongoDBDatabaseName", "ninjaskCreatesShedinja", "getNinjaskCreatesShedinja", "setNinjaskCreatesShedinja", "", "Lkotlin/ranges/IntRange;", "passiveStatuses", "Ljava/util/Map;", "getPassiveStatuses", "()Ljava/util/Map;", "setPassiveStatuses", "(Ljava/util/Map;)V", "pastureBlockUpdateTicks", "getPastureBlockUpdateTicks", "setPastureBlockUpdateTicks", "pastureMaxPerChunk", "getPastureMaxPerChunk", "setPastureMaxPerChunk", "pastureMaxWanderDistance", "getPastureMaxWanderDistance", "setPastureMaxWanderDistance", "playerDamagePokemon", "getPlayerDamagePokemon", "setPlayerDamagePokemon", "pokemonPerChunk", "getPokemonPerChunk", "setPokemonPerChunk", "pokemonSaveIntervalSeconds", "getPokemonSaveIntervalSeconds", "setPokemonSaveIntervalSeconds", "getPokemonSaveIntervalSeconds$annotations", "preventCompletePartyDeposit", "getPreventCompletePartyDeposit", "setPreventCompletePartyDeposit", "savePokemonToWorld", "getSavePokemonToWorld", "setSavePokemonToWorld", "shinyRate", "getShinyRate", "setShinyRate", "storageFormat", "getStorageFormat", "setStorageFormat", "teraTypeRate", "getTeraTypeRate", "setTeraTypeRate", "ticksBetweenSpawnAttempts", "getTicksBetweenSpawnAttempts", "setTicksBetweenSpawnAttempts", "walkingInBattleAnimations", "getWalkingInBattleAnimations", "setWalkingInBattleAnimations", "worldSliceDiameter", "getWorldSliceDiameter", "setWorldSliceDiameter", "worldSliceHeight", "getWorldSliceHeight", "setWorldSliceHeight", "<init>", "Companion", "common"})
public final class CobblemonConfig {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private String lastSavedVersion = "0.0.1";
    @NodeCategory(category=Category.Pokemon)
    private int maxPokemonLevel = 100;
    @NodeCategory(category=Category.Pokemon)
    private int maxPokemonFriendship = 255;
    @NodeCategory(category=Category.Pokemon)
    private boolean announceDropItems = true;
    @NodeCategory(category=Category.Pokemon)
    @NotNull
    private ItemDropMethod defaultDropItemMethod = ItemDropMethod.ON_ENTITY;
    @NodeCategory(category=Category.Pokemon)
    @LastChangedVersion(version="1.4.0")
    private int ambientPokemonCryTicks = 1080;
    @NodeCategory(category=Category.Storage)
    private int defaultBoxCount = 30;
    @NodeCategory(category=Category.Storage)
    private int pokemonSaveIntervalSeconds = 30;
    @NodeCategory(category=Category.Storage)
    @NotNull
    private String storageFormat = "nbt";
    @NodeCategory(category=Category.Storage)
    private boolean preventCompletePartyDeposit;
    @NodeCategory(category=Category.Storage)
    @NotNull
    private String mongoDBConnectionString = "mongodb://localhost:27017";
    @NodeCategory(category=Category.Storage)
    @NotNull
    private String mongoDBDatabaseName = "cobblemon";
    @NodeCategory(category=Category.Spawning)
    private int maxVerticalCorrectionBlocks = 64;
    @NodeCategory(category=Category.Spawning)
    private int minimumLevelRangeMax = 10;
    @NodeCategory(category=Category.Spawning)
    private boolean enableSpawning = true;
    @NodeCategory(category=Category.Spawning)
    private double minimumDistanceBetweenEntities = 8.0;
    @NodeCategory(category=Category.Spawning)
    private int maxNearbyBlocksHorizontalRange = 4;
    @NodeCategory(category=Category.Spawning)
    private int maxNearbyBlocksVerticalRange = 2;
    @NodeCategory(category=Category.Spawning)
    private int maxVerticalSpace = 8;
    @NodeCategory(category=Category.Spawning)
    private int worldSliceDiameter = 8;
    @NodeCategory(category=Category.Spawning)
    private int worldSliceHeight = 16;
    @NodeCategory(category=Category.Spawning)
    private float ticksBetweenSpawnAttempts = 20.0f;
    @NodeCategory(category=Category.Spawning)
    private float minimumSliceDistanceFromPlayer = 16.0f;
    @NodeCategory(category=Category.Spawning)
    private float maximumSliceDistanceFromPlayer = 64.0f;
    @NodeCategory(category=Category.Spawning)
    private boolean exportSpawnConfig;
    @NodeCategory(category=Category.Spawning)
    private boolean savePokemonToWorld = true;
    @NodeCategory(category=Category.Starter)
    private boolean exportStarterConfig;
    @NodeCategory(category=Category.Battles)
    private boolean autoUpdateShowdown = true;
    @NodeCategory(category=Category.Battles)
    private float defaultFleeDistance = 32.0f;
    @NodeCategory(category=Category.Battles)
    private boolean allowExperienceFromPvP = true;
    @NodeCategory(category=Category.Battles)
    private double experienceShareMultiplier = 0.5;
    @NodeCategory(category=Category.Battles)
    private double luckyEggMultiplier = 1.5;
    @NodeCategory(category=Category.Battles)
    private boolean allowSpectating = true;
    @NodeCategory(category=Category.Pokemon)
    private float experienceMultiplier = 2.0f;
    @NodeCategory(category=Category.Spawning)
    private float pokemonPerChunk = 1.0f;
    @NodeCategory(category=Category.PassiveStatus)
    @NotNull
    private Map<String, IntRange> passiveStatuses;
    @NodeCategory(category=Category.Healing)
    private boolean infiniteHealerCharge;
    @NodeCategory(category=Category.Healing)
    private float maxHealerCharge;
    @NodeCategory(category=Category.Healing)
    private float chargeGainedPerTick;
    @NodeCategory(category=Category.Healing)
    private int defaultFaintTimer;
    @NodeCategory(category=Category.Healing)
    private float faintAwakenHealthPercent;
    @NodeCategory(category=Category.Healing)
    private double healPercent;
    @NodeCategory(category=Category.Healing)
    private int healTimer;
    @NodeCategory(category=Category.Spawning)
    private float baseApricornTreeGenerationChance;
    @NodeCategory(category=Category.Pokemon)
    private boolean ninjaskCreatesShedinja;
    @NodeCategory(category=Category.Pokemon)
    private boolean displayEntityLevelLabel;
    @NodeCategory(category=Category.Spawning)
    private float shinyRate;
    @NodeCategory(category=Category.Pokemon)
    @NotNull
    private CaptureCalculator captureCalculator;
    @NodeCategory(category=Category.Pokemon)
    private boolean playerDamagePokemon;
    @NodeCategory(category=Category.World)
    private double appleLeftoversChance;
    @NodeCategory(category=Category.World)
    private int maxRootsInArea;
    @NodeCategory(category=Category.World)
    private double bigRootPropagationChance;
    @NodeCategory(category=Category.World)
    private double energyRootChance;
    @NodeCategory(category=Category.Pokemon)
    private int maxDynamaxLevel;
    @NodeCategory(category=Category.Spawning)
    private float teraTypeRate;
    @NodeCategory(category=Category.World)
    private int defaultPasturedPokemonLimit;
    @NodeCategory(category=Category.World)
    private int pastureBlockUpdateTicks;
    @NodeCategory(category=Category.World)
    private int pastureMaxWanderDistance;
    @NodeCategory(category=Category.World)
    private float pastureMaxPerChunk;
    @NodeCategory(category=Category.World)
    private int maxInsertedFossilItems;
    @NodeCategory(category=Category.Battles)
    private boolean walkingInBattleAnimations;
    @NodeCategory(category=Category.Debug)
    private boolean enableDebugKeys;
    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().registerTypeAdapter((Type)((Object)IntRange.class), (Object)IntRangeAdapter.INSTANCE).registerTypeAdapter((Type)((Object)ItemDropMethod.class), ItemDropMethod.Companion.getAdapter()).registerTypeAdapter((Type)((Object)CaptureCalculator.class), (Object)CaptureCalculatorAdapter.INSTANCE).create();

    public CobblemonConfig() {
        Pair[] pairArray = new Pair[]{Statuses.INSTANCE.getPOISON().configEntry(), Statuses.INSTANCE.getPOISON_BADLY().configEntry(), Statuses.INSTANCE.getPARALYSIS().configEntry(), Statuses.INSTANCE.getFROZEN().configEntry(), Statuses.INSTANCE.getSLEEP().configEntry(), Statuses.INSTANCE.getBURN().configEntry()};
        this.passiveStatuses = MapsKt.mutableMapOf((Pair[])pairArray);
        this.maxHealerCharge = 6.0f;
        this.chargeGainedPerTick = 3.33333E-4f;
        this.defaultFaintTimer = 300;
        this.faintAwakenHealthPercent = 0.2f;
        this.healPercent = 0.05;
        this.healTimer = 60;
        this.baseApricornTreeGenerationChance = 0.1f;
        this.ninjaskCreatesShedinja = true;
        this.displayEntityLevelLabel = true;
        this.shinyRate = 8192.0f;
        this.captureCalculator = CobblemonCaptureCalculator.INSTANCE;
        this.playerDamagePokemon = true;
        this.appleLeftoversChance = 0.025;
        this.maxRootsInArea = 5;
        this.bigRootPropagationChance = 0.1;
        this.energyRootChance = 0.25;
        this.maxDynamaxLevel = 10;
        this.teraTypeRate = 20.0f;
        this.defaultPasturedPokemonLimit = 16;
        this.pastureBlockUpdateTicks = 40;
        this.pastureMaxWanderDistance = 64;
        this.pastureMaxPerChunk = 4.0f;
        this.maxInsertedFossilItems = 2;
    }

    @NotNull
    public final String getLastSavedVersion() {
        return this.lastSavedVersion;
    }

    public final void setLastSavedVersion(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.lastSavedVersion = string;
    }

    public final int getMaxPokemonLevel() {
        return this.maxPokemonLevel;
    }

    public final void setMaxPokemonLevel(int n) {
        this.maxPokemonLevel = n;
    }

    @IntConstraint(min=1, max=1000)
    public static /* synthetic */ void getMaxPokemonLevel$annotations() {
    }

    public final int getMaxPokemonFriendship() {
        return this.maxPokemonFriendship;
    }

    public final void setMaxPokemonFriendship(int n) {
        this.maxPokemonFriendship = n;
    }

    @IntConstraint(min=0, max=1000)
    public static /* synthetic */ void getMaxPokemonFriendship$annotations() {
    }

    public final boolean getAnnounceDropItems() {
        return this.announceDropItems;
    }

    public final void setAnnounceDropItems(boolean bl) {
        this.announceDropItems = bl;
    }

    @NotNull
    public final ItemDropMethod getDefaultDropItemMethod() {
        return this.defaultDropItemMethod;
    }

    public final void setDefaultDropItemMethod(@NotNull ItemDropMethod itemDropMethod) {
        Intrinsics.checkNotNullParameter((Object)((Object)itemDropMethod), (String)"<set-?>");
        this.defaultDropItemMethod = itemDropMethod;
    }

    public final int getAmbientPokemonCryTicks() {
        return this.ambientPokemonCryTicks;
    }

    public final void setAmbientPokemonCryTicks(int n) {
        this.ambientPokemonCryTicks = n;
    }

    public final int getDefaultBoxCount() {
        return this.defaultBoxCount;
    }

    public final void setDefaultBoxCount(int n) {
        this.defaultBoxCount = n;
    }

    @IntConstraint(min=1, max=1000)
    public static /* synthetic */ void getDefaultBoxCount$annotations() {
    }

    public final int getPokemonSaveIntervalSeconds() {
        return this.pokemonSaveIntervalSeconds;
    }

    public final void setPokemonSaveIntervalSeconds(int n) {
        this.pokemonSaveIntervalSeconds = n;
    }

    @IntConstraint(min=1, max=120)
    public static /* synthetic */ void getPokemonSaveIntervalSeconds$annotations() {
    }

    @NotNull
    public final String getStorageFormat() {
        return this.storageFormat;
    }

    public final void setStorageFormat(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.storageFormat = string;
    }

    public final boolean getPreventCompletePartyDeposit() {
        return this.preventCompletePartyDeposit;
    }

    public final void setPreventCompletePartyDeposit(boolean bl) {
        this.preventCompletePartyDeposit = bl;
    }

    @NotNull
    public final String getMongoDBConnectionString() {
        return this.mongoDBConnectionString;
    }

    public final void setMongoDBConnectionString(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.mongoDBConnectionString = string;
    }

    @NotNull
    public final String getMongoDBDatabaseName() {
        return this.mongoDBDatabaseName;
    }

    public final void setMongoDBDatabaseName(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.mongoDBDatabaseName = string;
    }

    public final int getMaxVerticalCorrectionBlocks() {
        return this.maxVerticalCorrectionBlocks;
    }

    public final void setMaxVerticalCorrectionBlocks(int n) {
        this.maxVerticalCorrectionBlocks = n;
    }

    @IntConstraint(min=1, max=200)
    public static /* synthetic */ void getMaxVerticalCorrectionBlocks$annotations() {
    }

    public final int getMinimumLevelRangeMax() {
        return this.minimumLevelRangeMax;
    }

    public final void setMinimumLevelRangeMax(int n) {
        this.minimumLevelRangeMax = n;
    }

    @IntConstraint(min=1, max=1000)
    public static /* synthetic */ void getMinimumLevelRangeMax$annotations() {
    }

    public final boolean getEnableSpawning() {
        return this.enableSpawning;
    }

    public final void setEnableSpawning(boolean bl) {
        this.enableSpawning = bl;
    }

    public final double getMinimumDistanceBetweenEntities() {
        return this.minimumDistanceBetweenEntities;
    }

    public final void setMinimumDistanceBetweenEntities(double d) {
        this.minimumDistanceBetweenEntities = d;
    }

    public final int getMaxNearbyBlocksHorizontalRange() {
        return this.maxNearbyBlocksHorizontalRange;
    }

    public final void setMaxNearbyBlocksHorizontalRange(int n) {
        this.maxNearbyBlocksHorizontalRange = n;
    }

    public final int getMaxNearbyBlocksVerticalRange() {
        return this.maxNearbyBlocksVerticalRange;
    }

    public final void setMaxNearbyBlocksVerticalRange(int n) {
        this.maxNearbyBlocksVerticalRange = n;
    }

    public final int getMaxVerticalSpace() {
        return this.maxVerticalSpace;
    }

    public final void setMaxVerticalSpace(int n) {
        this.maxVerticalSpace = n;
    }

    public final int getWorldSliceDiameter() {
        return this.worldSliceDiameter;
    }

    public final void setWorldSliceDiameter(int n) {
        this.worldSliceDiameter = n;
    }

    public final int getWorldSliceHeight() {
        return this.worldSliceHeight;
    }

    public final void setWorldSliceHeight(int n) {
        this.worldSliceHeight = n;
    }

    public final float getTicksBetweenSpawnAttempts() {
        return this.ticksBetweenSpawnAttempts;
    }

    public final void setTicksBetweenSpawnAttempts(float f) {
        this.ticksBetweenSpawnAttempts = f;
    }

    public final float getMinimumSliceDistanceFromPlayer() {
        return this.minimumSliceDistanceFromPlayer;
    }

    public final void setMinimumSliceDistanceFromPlayer(float f) {
        this.minimumSliceDistanceFromPlayer = f;
    }

    public final float getMaximumSliceDistanceFromPlayer() {
        return this.maximumSliceDistanceFromPlayer;
    }

    public final void setMaximumSliceDistanceFromPlayer(float f) {
        this.maximumSliceDistanceFromPlayer = f;
    }

    public final boolean getExportSpawnConfig() {
        return this.exportSpawnConfig;
    }

    public final void setExportSpawnConfig(boolean bl) {
        this.exportSpawnConfig = bl;
    }

    public final boolean getSavePokemonToWorld() {
        return this.savePokemonToWorld;
    }

    public final void setSavePokemonToWorld(boolean bl) {
        this.savePokemonToWorld = bl;
    }

    public final boolean getExportStarterConfig() {
        return this.exportStarterConfig;
    }

    public final void setExportStarterConfig(boolean bl) {
        this.exportStarterConfig = bl;
    }

    public final boolean getAutoUpdateShowdown() {
        return this.autoUpdateShowdown;
    }

    public final void setAutoUpdateShowdown(boolean bl) {
        this.autoUpdateShowdown = bl;
    }

    public final float getDefaultFleeDistance() {
        return this.defaultFleeDistance;
    }

    public final void setDefaultFleeDistance(float f) {
        this.defaultFleeDistance = f;
    }

    public final boolean getAllowExperienceFromPvP() {
        return this.allowExperienceFromPvP;
    }

    public final void setAllowExperienceFromPvP(boolean bl) {
        this.allowExperienceFromPvP = bl;
    }

    public final double getExperienceShareMultiplier() {
        return this.experienceShareMultiplier;
    }

    public final void setExperienceShareMultiplier(double d) {
        this.experienceShareMultiplier = d;
    }

    public final double getLuckyEggMultiplier() {
        return this.luckyEggMultiplier;
    }

    public final void setLuckyEggMultiplier(double d) {
        this.luckyEggMultiplier = d;
    }

    public final boolean getAllowSpectating() {
        return this.allowSpectating;
    }

    public final void setAllowSpectating(boolean bl) {
        this.allowSpectating = bl;
    }

    public final float getExperienceMultiplier() {
        return this.experienceMultiplier;
    }

    public final void setExperienceMultiplier(float f) {
        this.experienceMultiplier = f;
    }

    public final float getPokemonPerChunk() {
        return this.pokemonPerChunk;
    }

    public final void setPokemonPerChunk(float f) {
        this.pokemonPerChunk = f;
    }

    @NotNull
    public final Map<String, IntRange> getPassiveStatuses() {
        return this.passiveStatuses;
    }

    public final void setPassiveStatuses(@NotNull Map<String, IntRange> map) {
        Intrinsics.checkNotNullParameter(map, (String)"<set-?>");
        this.passiveStatuses = map;
    }

    public final boolean getInfiniteHealerCharge() {
        return this.infiniteHealerCharge;
    }

    public final void setInfiniteHealerCharge(boolean bl) {
        this.infiniteHealerCharge = bl;
    }

    public final float getMaxHealerCharge() {
        return this.maxHealerCharge;
    }

    public final void setMaxHealerCharge(float f) {
        this.maxHealerCharge = f;
    }

    public final float getChargeGainedPerTick() {
        return this.chargeGainedPerTick;
    }

    public final void setChargeGainedPerTick(float f) {
        this.chargeGainedPerTick = f;
    }

    public final int getDefaultFaintTimer() {
        return this.defaultFaintTimer;
    }

    public final void setDefaultFaintTimer(int n) {
        this.defaultFaintTimer = n;
    }

    public final float getFaintAwakenHealthPercent() {
        return this.faintAwakenHealthPercent;
    }

    public final void setFaintAwakenHealthPercent(float f) {
        this.faintAwakenHealthPercent = f;
    }

    public final double getHealPercent() {
        return this.healPercent;
    }

    public final void setHealPercent(double d) {
        this.healPercent = d;
    }

    public final int getHealTimer() {
        return this.healTimer;
    }

    public final void setHealTimer(int n) {
        this.healTimer = n;
    }

    public final float getBaseApricornTreeGenerationChance() {
        return this.baseApricornTreeGenerationChance;
    }

    public final void setBaseApricornTreeGenerationChance(float f) {
        this.baseApricornTreeGenerationChance = f;
    }

    public final boolean getNinjaskCreatesShedinja() {
        return this.ninjaskCreatesShedinja;
    }

    public final void setNinjaskCreatesShedinja(boolean bl) {
        this.ninjaskCreatesShedinja = bl;
    }

    public final boolean getDisplayEntityLevelLabel() {
        return this.displayEntityLevelLabel;
    }

    public final void setDisplayEntityLevelLabel(boolean bl) {
        this.displayEntityLevelLabel = bl;
    }

    public final float getShinyRate() {
        return this.shinyRate;
    }

    public final void setShinyRate(float f) {
        this.shinyRate = f;
    }

    @NotNull
    public final CaptureCalculator getCaptureCalculator() {
        return this.captureCalculator;
    }

    public final void setCaptureCalculator(@NotNull CaptureCalculator captureCalculator) {
        Intrinsics.checkNotNullParameter((Object)captureCalculator, (String)"<set-?>");
        this.captureCalculator = captureCalculator;
    }

    public final boolean getPlayerDamagePokemon() {
        return this.playerDamagePokemon;
    }

    public final void setPlayerDamagePokemon(boolean bl) {
        this.playerDamagePokemon = bl;
    }

    public final double getAppleLeftoversChance() {
        return this.appleLeftoversChance;
    }

    public final void setAppleLeftoversChance(double d) {
        this.appleLeftoversChance = d;
    }

    public final int getMaxRootsInArea() {
        return this.maxRootsInArea;
    }

    public final void setMaxRootsInArea(int n) {
        this.maxRootsInArea = n;
    }

    public final double getBigRootPropagationChance() {
        return this.bigRootPropagationChance;
    }

    public final void setBigRootPropagationChance(double d) {
        this.bigRootPropagationChance = d;
    }

    public final double getEnergyRootChance() {
        return this.energyRootChance;
    }

    public final void setEnergyRootChance(double d) {
        this.energyRootChance = d;
    }

    public final int getMaxDynamaxLevel() {
        return this.maxDynamaxLevel;
    }

    public final void setMaxDynamaxLevel(int n) {
        this.maxDynamaxLevel = n;
    }

    @IntConstraint(min=0, max=10)
    public static /* synthetic */ void getMaxDynamaxLevel$annotations() {
    }

    public final float getTeraTypeRate() {
        return this.teraTypeRate;
    }

    public final void setTeraTypeRate(float f) {
        this.teraTypeRate = f;
    }

    public final int getDefaultPasturedPokemonLimit() {
        return this.defaultPasturedPokemonLimit;
    }

    public final void setDefaultPasturedPokemonLimit(int n) {
        this.defaultPasturedPokemonLimit = n;
    }

    public final int getPastureBlockUpdateTicks() {
        return this.pastureBlockUpdateTicks;
    }

    public final void setPastureBlockUpdateTicks(int n) {
        this.pastureBlockUpdateTicks = n;
    }

    public final int getPastureMaxWanderDistance() {
        return this.pastureMaxWanderDistance;
    }

    public final void setPastureMaxWanderDistance(int n) {
        this.pastureMaxWanderDistance = n;
    }

    public final float getPastureMaxPerChunk() {
        return this.pastureMaxPerChunk;
    }

    public final void setPastureMaxPerChunk(float f) {
        this.pastureMaxPerChunk = f;
    }

    public final int getMaxInsertedFossilItems() {
        return this.maxInsertedFossilItems;
    }

    public final void setMaxInsertedFossilItems(int n) {
        this.maxInsertedFossilItems = n;
    }

    public final boolean getWalkingInBattleAnimations() {
        return this.walkingInBattleAnimations;
    }

    public final void setWalkingInBattleAnimations(boolean bl) {
        this.walkingInBattleAnimations = bl;
    }

    public final boolean getEnableDebugKeys() {
        return this.enableDebugKeys;
    }

    public final void setEnableDebugKeys(boolean bl) {
        this.enableDebugKeys = bl;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001f\u0010\u0004\u001a\n \u0003*\u0004\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/config/CobblemonConfig$Companion;", "", "Lcom/google/gson/Gson;", "kotlin.jvm.PlatformType", "GSON", "Lcom/google/gson/Gson;", "getGSON", "()Lcom/google/gson/Gson;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public final Gson getGSON() {
            return GSON;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

