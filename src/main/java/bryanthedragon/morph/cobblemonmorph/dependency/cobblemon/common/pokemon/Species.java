/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  kotlin.Lazy
 *  kotlin.LazyKt
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.RangesKt
 *  kotlin.text.Regex
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.EntityDimensions
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.AbilityPool;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.ClientDataSynchronizer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.ShowdownIdentifiable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.DropTable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.effect.ShoulderEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.egg.EggGroup;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.PreEvolution;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.ExperienceGroup;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.ExperienceGroups;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.moves.Learnset;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.StatProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalTypes;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai.PokemonBehaviour;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.lighthing.LightingData;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import kotlin.text.Regex;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u00ec\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010!\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001c\n\u0002\b\b\u0018\u0000 \u00cb\u00012\b\u0012\u0004\u0012\u00020\u00000\u00012\u00020\u0002:\u0002\u00cb\u0001B\b\u00a2\u0006\u0005\b\u00ca\u0001\u0010\u001dJ\r\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0017\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\u0004\b\t\u0010\nJ\u0017\u0010\u000e\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0017\u0010\u0010\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\u0010\u0010\u000fJ\u0015\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0012\u001a\u00020\u0011\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u001b\u0010\u001a\u001a\u00020\u00192\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\r\u0010\u001c\u001a\u00020\r\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u000f\u0010\u001f\u001a\u00020\rH\u0000\u00a2\u0006\u0004\b\u001e\u0010\u001dJ\u0019\u0010 \u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0012\u001a\u00020\u0011H\u0002\u00a2\u0006\u0004\b \u0010!J\u0017\u0010#\u001a\u00020\u00032\u0006\u0010\"\u001a\u00020\u0000H\u0016\u00a2\u0006\u0004\b#\u0010$J\u000f\u0010%\u001a\u00020\u0017H\u0016\u00a2\u0006\u0004\b%\u0010&J\u000f\u0010'\u001a\u00020\u0017H\u0016\u00a2\u0006\u0004\b'\u0010&J\u000f\u0010(\u001a\u00020\u0017H\u0002\u00a2\u0006\u0004\b(\u0010&R$\u0010+\u001a\u00020)2\u0006\u0010*\u001a\u00020)8\u0006@BX\u0086\u000e\u00a2\u0006\f\n\u0004\b+\u0010,\u001a\u0004\b-\u0010.R\"\u0010/\u001a\u00020\u00068\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b/\u00100\u001a\u0004\b1\u00102\"\u0004\b3\u00104R\"\u00105\u001a\u00020\u00068\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b5\u00100\u001a\u0004\b6\u00102\"\u0004\b7\u00104R\"\u00108\u001a\u00020\u00138\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b8\u00109\u001a\u0004\b:\u0010;\"\u0004\b<\u0010=R\\\u0010A\u001a\u001e\u0012\u0004\u0012\u00020?\u0012\u0004\u0012\u00020\u00060>j\u000e\u0012\u0004\u0012\u00020?\u0012\u0004\u0012\u00020\u0006`@2\"\u0010*\u001a\u001e\u0012\u0004\u0012\u00020?\u0012\u0004\u0012\u00020\u00060>j\u000e\u0012\u0004\u0012\u00020?\u0012\u0004\u0012\u00020\u0006`@8\u0006@BX\u0086\u000e\u00a2\u0006\f\n\u0004\bA\u0010B\u001a\u0004\bC\u0010DR\"\u0010F\u001a\u00020E8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bF\u0010G\u001a\u0004\bH\u0010I\"\u0004\bJ\u0010KR$\u0010M\u001a\u00020L2\u0006\u0010*\u001a\u00020L8\u0006@BX\u0086\u000e\u00a2\u0006\f\n\u0004\bM\u0010N\u001a\u0004\bO\u0010PR$\u0010Q\u001a\u00020\u00062\u0006\u0010*\u001a\u00020\u00068\u0006@BX\u0086\u000e\u00a2\u0006\f\n\u0004\bQ\u00100\u001a\u0004\bR\u00102R$\u0010T\u001a\u00020S2\u0006\u0010*\u001a\u00020S8\u0006@BX\u0086\u000e\u00a2\u0006\f\n\u0004\bT\u0010U\u001a\u0004\bV\u0010WR\"\u0010X\u001a\u00020\u00038\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bX\u0010Y\u001a\u0004\bZ\u0010\u0005\"\u0004\b[\u0010\\R$\u0010]\u001a\u00020\u00062\u0006\u0010*\u001a\u00020\u00068\u0006@BX\u0086\u000e\u00a2\u0006\f\n\u0004\b]\u00100\u001a\u0004\b^\u00102RD\u0010b\u001a\u0012\u0012\u0004\u0012\u00020`0_j\b\u0012\u0004\u0012\u00020``a2\u0016\u0010*\u001a\u0012\u0012\u0004\u0012\u00020`0_j\b\u0012\u0004\u0012\u00020``a8\u0006@BX\u0086\u000e\u00a2\u0006\f\n\u0004\bb\u0010c\u001a\u0004\bd\u0010eR\\\u0010f\u001a\u001e\u0012\u0004\u0012\u00020?\u0012\u0004\u0012\u00020\u00060>j\u000e\u0012\u0004\u0012\u00020?\u0012\u0004\u0012\u00020\u0006`@2\"\u0010*\u001a\u001e\u0012\u0004\u0012\u00020?\u0012\u0004\u0012\u00020\u00060>j\u000e\u0012\u0004\u0012\u00020?\u0012\u0004\u0012\u00020\u0006`@8\u0006@BX\u0086\u000e\u00a2\u0006\f\n\u0004\bf\u0010B\u001a\u0004\bg\u0010DR0\u0010j\u001a\b\u0012\u0004\u0012\u00020i0h2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020i0h8\u0006@BX\u0086\u000e\u00a2\u0006\f\n\u0004\bj\u0010k\u001a\u0004\bl\u0010mR\"\u0010o\u001a\u00020n8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bo\u0010p\u001a\u0004\bq\u0010r\"\u0004\bs\u0010tR0\u0010u\u001a\b\u0012\u0004\u0012\u00020\u00170h2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00170h8\u0006@BX\u0086\u000e\u00a2\u0006\f\n\u0004\bu\u0010k\u001a\u0004\bv\u0010mR\u0018\u0010w\u001a\u0004\u0018\u00010\u00138\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\bw\u0010xR0\u0010z\u001a\b\u0012\u0004\u0012\u00020\u00190y2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00190y8\u0006@BX\u0086\u000e\u00a2\u0006\f\n\u0004\bz\u0010{\u001a\u0004\b|\u0010}R$\u0010~\u001a\u00020\u00132\u0006\u0010*\u001a\u00020\u00138\u0006@BX\u0086\u000e\u00a2\u0006\f\n\u0004\b~\u00109\u001a\u0004\b\u007f\u0010;R*\u0010\u0081\u0001\u001a\u00030\u0080\u00018\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0018\n\u0006\b\u0081\u0001\u0010\u0082\u0001\u001a\u0006\b\u0083\u0001\u0010\u0084\u0001\"\u0006\b\u0085\u0001\u0010\u0086\u0001R&\u0010\u0087\u0001\u001a\u00020\u00038\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0015\n\u0005\b\u0087\u0001\u0010Y\u001a\u0005\b\u0088\u0001\u0010\u0005\"\u0005\b\u0089\u0001\u0010\\RG\u0010\u008a\u0001\u001a\u0012\u0012\u0004\u0012\u00020\u00170_j\b\u0012\u0004\u0012\u00020\u0017`a2\u0016\u0010*\u001a\u0012\u0012\u0004\u0012\u00020\u00170_j\b\u0012\u0004\u0012\u00020\u0017`a8\u0006@BX\u0086\u000e\u00a2\u0006\u000e\n\u0005\b\u008a\u0001\u0010c\u001a\u0005\b\u008b\u0001\u0010eR/\u0010\u008d\u0001\u001a\u0005\u0018\u00010\u008c\u00012\t\u0010*\u001a\u0005\u0018\u00010\u008c\u00018\u0006@BX\u0086\u000e\u00a2\u0006\u0010\n\u0006\b\u008d\u0001\u0010\u008e\u0001\u001a\u0006\b\u008f\u0001\u0010\u0090\u0001R'\u0010\u0091\u0001\u001a\u00020\u00132\u0006\u0010*\u001a\u00020\u00138\u0006@BX\u0086\u000e\u00a2\u0006\u000e\n\u0005\b\u0091\u0001\u00109\u001a\u0005\b\u0092\u0001\u0010;R+\u0010\u0094\u0001\u001a\u00030\u0093\u00012\u0007\u0010*\u001a\u00030\u0093\u00018\u0006@BX\u0086\u000e\u00a2\u0006\u0010\n\u0006\b\u0094\u0001\u0010\u0095\u0001\u001a\u0006\b\u0096\u0001\u0010\u0097\u0001R(\u0010\u0098\u0001\u001a\u00020\u00178\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0017\n\u0006\b\u0098\u0001\u0010\u0099\u0001\u001a\u0005\b\u009a\u0001\u0010&\"\u0006\b\u009b\u0001\u0010\u009c\u0001R&\u0010\u009d\u0001\u001a\u00020\u00068\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0015\n\u0005\b\u009d\u0001\u00100\u001a\u0005\b\u009e\u0001\u00102\"\u0005\b\u009f\u0001\u00104R3\u0010\u00a0\u0001\u001a\b\u0012\u0004\u0012\u00020\u00170y2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00170y8\u0006@BX\u0086\u000e\u00a2\u0006\u000e\n\u0005\b\u00a0\u0001\u0010{\u001a\u0005\b\u00a1\u0001\u0010}R/\u0010\u00a3\u0001\u001a\u0005\u0018\u00010\u00a2\u00012\t\u0010*\u001a\u0005\u0018\u00010\u00a2\u00018\u0006@BX\u0086\u000e\u00a2\u0006\u0010\n\u0006\b\u00a3\u0001\u0010\u00a4\u0001\u001a\u0006\b\u00a5\u0001\u0010\u00a6\u0001R3\u0010\u00a8\u0001\u001a\u00030\u00a7\u00012\u0007\u0010*\u001a\u00030\u00a7\u00018\u0006@@X\u0086\u000e\u00a2\u0006\u0018\n\u0006\b\u00a8\u0001\u0010\u00a9\u0001\u001a\u0006\b\u00aa\u0001\u0010\u00ab\u0001\"\u0006\b\u00ac\u0001\u0010\u00ad\u0001R&\u0010\u00ae\u0001\u001a\u00020E8\u0006@\u0006X\u0086.\u00a2\u0006\u0015\n\u0005\b\u00ae\u0001\u0010G\u001a\u0005\b\u00af\u0001\u0010I\"\u0005\b\u00b0\u0001\u0010KR7\u0010\u00b1\u0001\u001a\u0005\u0018\u00010\u00a7\u00012\t\u0010*\u001a\u0005\u0018\u00010\u00a7\u00018\u0006@@X\u0086\u000e\u00a2\u0006\u0018\n\u0006\b\u00b1\u0001\u0010\u00a9\u0001\u001a\u0006\b\u00b2\u0001\u0010\u00ab\u0001\"\u0006\b\u00b3\u0001\u0010\u00ad\u0001R5\u0010\u00b5\u0001\u001a\t\u0012\u0005\u0012\u00030\u00b4\u00010y2\r\u0010*\u001a\t\u0012\u0005\u0012\u00030\u00b4\u00010y8\u0006@BX\u0086\u000e\u00a2\u0006\u000e\n\u0005\b\u00b5\u0001\u0010{\u001a\u0005\b\u00b6\u0001\u0010}R'\u0010\u00b7\u0001\u001a\u00020\u00032\u0006\u0010*\u001a\u00020\u00038\u0006@BX\u0086\u000e\u00a2\u0006\u000e\n\u0005\b\u00b7\u0001\u0010Y\u001a\u0005\b\u00b8\u0001\u0010\u0005R \u0010\u00bd\u0001\u001a\u00020\u00198FX\u0086\u0084\u0002\u00a2\u0006\u0010\n\u0006\b\u00b9\u0001\u0010\u00ba\u0001\u001a\u0006\b\u00bb\u0001\u0010\u00bc\u0001R\u001a\u0010\u00be\u0001\u001a\u0004\u0018\u00010\u00138\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0007\n\u0005\b\u00be\u0001\u0010xR\u001a\u0010\u00bf\u0001\u001a\u0004\u0018\u00010\u00138\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0007\n\u0005\b\u00bf\u0001\u0010xR\u0015\u0010\u00c3\u0001\u001a\u00030\u00c0\u00018F\u00a2\u0006\b\u001a\u0006\b\u00c1\u0001\u0010\u00c2\u0001R\u001c\u0010\u00c7\u0001\u001a\n\u0012\u0005\u0012\u00030\u00a7\u00010\u00c4\u00018F\u00a2\u0006\b\u001a\u0006\b\u00c5\u0001\u0010\u00c6\u0001R'\u0010\u00c8\u0001\u001a\u00020\u00132\u0006\u0010*\u001a\u00020\u00138\u0006@BX\u0086\u000e\u00a2\u0006\u000e\n\u0005\b\u00c8\u0001\u00109\u001a\u0005\b\u00c9\u0001\u0010;\u00a8\u0006\u00cc\u0001"}, d2={"Lcom/cobblemon/mod/common/pokemon/Species;", "Lcom/cobblemon/mod/common/api/data/ClientDataSynchronizer;", "Lcom/cobblemon/mod/common/api/data/ShowdownIdentifiable;", "", "canGmax", "()Z", "", "level", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "create", "(I)Lcom/cobblemon/mod/common/pokemon/Pokemon;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "encode", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "entity", "", "eyeHeight", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)F", "", "", "aspects", "Lcom/cobblemon/mod/common/pokemon/FormData;", "getForm", "(Ljava/util/Set;)Lcom/cobblemon/mod/common/pokemon/FormData;", "initialize", "()V", "resolveEvolutionMoves$common", "resolveEvolutionMoves", "resolveEyeHeight", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)Ljava/lang/Float;", "other", "shouldSynchronize", "(Lcom/cobblemon/mod/common/pokemon/Species;)Z", "showdownId", "()Ljava/lang/String;", "toString", "unformattedShowdownId", "Lcom/cobblemon/mod/common/api/abilities/AbilityPool;", "<set-?>", "abilities", "Lcom/cobblemon/mod/common/api/abilities/AbilityPool;", "getAbilities", "()Lcom/cobblemon/mod/common/api/abilities/AbilityPool;", "baseExperienceYield", "I", "getBaseExperienceYield", "()I", "setBaseExperienceYield", "(I)V", "baseFriendship", "getBaseFriendship", "setBaseFriendship", "baseScale", "F", "getBaseScale", "()F", "setBaseScale", "(F)V", "Ljava/util/HashMap;", "Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;", "Lkotlin/collections/HashMap;", "baseStats", "Ljava/util/HashMap;", "getBaseStats", "()Ljava/util/HashMap;", "Lnet/minecraft/resources/ResourceLocation;", "battleTheme", "Lnet/minecraft/resources/ResourceLocation;", "getBattleTheme", "()Lnet/minecraft/resources/ResourceLocation;", "setBattleTheme", "(Lnet/minecraft/resources/ResourceLocation;)V", "Lcom/cobblemon/mod/common/pokemon/ai/PokemonBehaviour;", "behaviour", "Lcom/cobblemon/mod/common/pokemon/ai/PokemonBehaviour;", "getBehaviour", "()Lcom/cobblemon/mod/common/pokemon/ai/PokemonBehaviour;", "catchRate", "getCatchRate", "Lcom/cobblemon/mod/common/api/drop/DropTable;", "drops", "Lcom/cobblemon/mod/common/api/drop/DropTable;", "getDrops", "()Lcom/cobblemon/mod/common/api/drop/DropTable;", "dynamaxBlocked", "Z", "getDynamaxBlocked", "setDynamaxBlocked", "(Z)V", "eggCycles", "getEggCycles", "Ljava/util/HashSet;", "Lcom/cobblemon/mod/common/api/pokemon/egg/EggGroup;", "Lkotlin/collections/HashSet;", "eggGroups", "Ljava/util/HashSet;", "getEggGroups", "()Ljava/util/HashSet;", "evYield", "getEvYield", "", "Lcom/cobblemon/mod/common/api/pokemon/evolution/Evolution;", "evolutions", "Ljava/util/Set;", "getEvolutions", "()Ljava/util/Set;", "Lcom/cobblemon/mod/common/api/pokemon/experience/ExperienceGroup;", "experienceGroup", "Lcom/cobblemon/mod/common/api/pokemon/experience/ExperienceGroup;", "getExperienceGroup", "()Lcom/cobblemon/mod/common/api/pokemon/experience/ExperienceGroup;", "setExperienceGroup", "(Lcom/cobblemon/mod/common/api/pokemon/experience/ExperienceGroup;)V", "features", "getFeatures", "flyingEyeHeight", "Ljava/lang/Float;", "", "forms", "Ljava/util/List;", "getForms", "()Ljava/util/List;", "height", "getHeight", "Lnet/minecraft/world/entity/EntityDimensions;", "hitbox", "Lnet/minecraft/world/entity/EntityDimensions;", "getHitbox", "()Lnet/minecraft/world/entity/EntityDimensions;", "setHitbox", "(Lnet/minecraft/world/entity/EntityDimensions;)V", "implemented", "getImplemented", "setImplemented", "labels", "getLabels", "Lcom/cobblemon/mod/common/pokemon/lighthing/LightingData;", "lightingData", "Lcom/cobblemon/mod/common/pokemon/lighthing/LightingData;", "getLightingData", "()Lcom/cobblemon/mod/common/pokemon/lighthing/LightingData;", "maleRatio", "getMaleRatio", "Lcom/cobblemon/mod/common/api/pokemon/moves/Learnset;", "moves", "Lcom/cobblemon/mod/common/api/pokemon/moves/Learnset;", "getMoves", "()Lcom/cobblemon/mod/common/api/pokemon/moves/Learnset;", "name", "Ljava/lang/String;", "getName", "setName", "(Ljava/lang/String;)V", "nationalPokedexNumber", "getNationalPokedexNumber", "setNationalPokedexNumber", "pokedex", "getPokedex", "Lcom/cobblemon/mod/common/api/pokemon/evolution/PreEvolution;", "preEvolution", "Lcom/cobblemon/mod/common/api/pokemon/evolution/PreEvolution;", "getPreEvolution", "()Lcom/cobblemon/mod/common/api/pokemon/evolution/PreEvolution;", "Lcom/cobblemon/mod/common/api/types/ElementalType;", "primaryType", "Lcom/cobblemon/mod/common/api/types/ElementalType;", "getPrimaryType", "()Lcom/cobblemon/mod/common/api/types/ElementalType;", "setPrimaryType$common", "(Lcom/cobblemon/mod/common/api/types/ElementalType;)V", "resourceIdentifier", "getResourceIdentifier", "setResourceIdentifier", "secondaryType", "getSecondaryType", "setSecondaryType$common", "Lcom/cobblemon/mod/common/api/pokemon/effect/ShoulderEffect;", "shoulderEffects", "getShoulderEffects", "shoulderMountable", "getShoulderMountable", "standardForm$delegate", "Lkotlin/Lazy;", "getStandardForm", "()Lcom/cobblemon/mod/common/pokemon/FormData;", "standardForm", "standingEyeHeight", "swimmingEyeHeight", "Lnet/minecraft/network/chat/MutableComponent;", "getTranslatedName", "()Lnet/minecraft/network/chat/MutableComponent;", "translatedName", "", "getTypes", "()Ljava/lang/Iterable;", "types", "weight", "getWeight", "<init>", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nSpecies.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Species.kt\ncom/cobblemon/mod/common/pokemon/Species\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,287:1\n1#2:288\n1855#3,2:289\n2624#3,3:291\n1855#3,2:294\n1855#3,2:296\n533#3,4:298\n1726#3,3:302\n538#3:305\n*S KotlinDebug\n*F\n+ 1 Species.kt\ncom/cobblemon/mod/common/pokemon/Species\n*L\n139#1:289,2\n140#1:291,3\n152#1:294,2\n158#1:296,2\n163#1:298,4\n163#1:302,3\n163#1:305\n*E\n"})
public final class Species
implements ClientDataSynchronizer<Species>,
ShowdownIdentifiable {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private String name = "Bulbasaur";
    private int nationalPokedexNumber = 1;
    @NotNull
    private HashMap<Stat, Integer> baseStats = new HashMap();
    private float maleRatio = 0.5f;
    private int catchRate = 45;
    private float baseScale = 1.0f;
    private int baseExperienceYield = 10;
    private int baseFriendship;
    @NotNull
    private HashMap<Stat, Integer> evYield = new HashMap();
    @NotNull
    private ExperienceGroup experienceGroup = (ExperienceGroup)CollectionsKt.first((Iterable)ExperienceGroups.INSTANCE);
    @NotNull
    private EntityDimensions hitbox = new EntityDimensions(1.0f, 1.0f, false);
    @NotNull
    private ElementalType primaryType = ElementalTypes.INSTANCE.getGRASS();
    @Nullable
    private ElementalType secondaryType;
    @NotNull
    private AbilityPool abilities = new AbilityPool();
    private boolean shoulderMountable;
    @NotNull
    private List<ShoulderEffect> shoulderEffects = new ArrayList();
    @NotNull
    private Learnset moves = new Learnset();
    @NotNull
    private Set<String> features = new LinkedHashSet();
    @Nullable
    private Float standingEyeHeight;
    @Nullable
    private Float swimmingEyeHeight;
    @Nullable
    private Float flyingEyeHeight;
    @NotNull
    private PokemonBehaviour behaviour = new PokemonBehaviour();
    @NotNull
    private List<String> pokedex = new ArrayList();
    @NotNull
    private DropTable drops = new DropTable();
    private int eggCycles = 120;
    @NotNull
    private HashSet<EggGroup> eggGroups = new HashSet();
    private boolean dynamaxBlocked;
    private boolean implemented;
    private float height = 1.0f;
    private float weight = 1.0f;
    @NotNull
    private List<FormData> forms = new ArrayList();
    @NotNull
    private final Lazy standardForm$delegate = LazyKt.lazy((Function0)((Function0)new Function0<FormData>(this){
        final /* synthetic */ Species this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        @NotNull
        public final FormData invoke() {
            return new FormData(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, this.this$0.getEvolutions(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, -32769, 3, null).initialize(this.this$0);
        }
    }));
    @NotNull
    private HashSet<String> labels = new HashSet();
    @NotNull
    private Set<Evolution> evolutions = new HashSet();
    @Nullable
    private PreEvolution preEvolution;
    public transient ResourceLocation resourceIdentifier;
    @NotNull
    private ResourceLocation battleTheme;
    @Nullable
    private LightingData lightingData;
    private static final float VANILLA_DEFAULT_EYE_HEIGHT = 0.85f;

    public Species() {
        ResourceLocation resourceLocation = CobblemonSounds.PVW_BATTLE.m_11660_();
        Intrinsics.checkNotNullExpressionValue((Object)resourceLocation, (String)"PVW_BATTLE.id");
        this.battleTheme = resourceLocation;
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    public final void setName(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.name = string;
    }

    @NotNull
    public final MutableComponent getTranslatedName() {
        MutableComponent mutableComponent = Component.m_237115_((String)(this.getResourceIdentifier().m_135827_() + ".species." + this.unformattedShowdownId() + ".name"));
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"translatable(\"${this.res\u2026attedShowdownId()}.name\")");
        return mutableComponent;
    }

    public final int getNationalPokedexNumber() {
        return this.nationalPokedexNumber;
    }

    public final void setNationalPokedexNumber(int n) {
        this.nationalPokedexNumber = n;
    }

    @NotNull
    public final HashMap<Stat, Integer> getBaseStats() {
        return this.baseStats;
    }

    public final float getMaleRatio() {
        return this.maleRatio;
    }

    public final int getCatchRate() {
        return this.catchRate;
    }

    public final float getBaseScale() {
        return this.baseScale;
    }

    public final void setBaseScale(float f) {
        this.baseScale = f;
    }

    public final int getBaseExperienceYield() {
        return this.baseExperienceYield;
    }

    public final void setBaseExperienceYield(int n) {
        this.baseExperienceYield = n;
    }

    public final int getBaseFriendship() {
        return this.baseFriendship;
    }

    public final void setBaseFriendship(int n) {
        this.baseFriendship = n;
    }

    @NotNull
    public final HashMap<Stat, Integer> getEvYield() {
        return this.evYield;
    }

    @NotNull
    public final ExperienceGroup getExperienceGroup() {
        return this.experienceGroup;
    }

    public final void setExperienceGroup(@NotNull ExperienceGroup experienceGroup) {
        Intrinsics.checkNotNullParameter((Object)experienceGroup, (String)"<set-?>");
        this.experienceGroup = experienceGroup;
    }

    @NotNull
    public final EntityDimensions getHitbox() {
        return this.hitbox;
    }

    public final void setHitbox(@NotNull EntityDimensions entityDimensions) {
        Intrinsics.checkNotNullParameter((Object)entityDimensions, (String)"<set-?>");
        this.hitbox = entityDimensions;
    }

    @NotNull
    public final ElementalType getPrimaryType() {
        return this.primaryType;
    }

    public final void setPrimaryType$common(@NotNull ElementalType elementalType) {
        Intrinsics.checkNotNullParameter((Object)elementalType, (String)"<set-?>");
        this.primaryType = elementalType;
    }

    @Nullable
    public final ElementalType getSecondaryType() {
        return this.secondaryType;
    }

    public final void setSecondaryType$common(@Nullable ElementalType elementalType) {
        this.secondaryType = elementalType;
    }

    @NotNull
    public final AbilityPool getAbilities() {
        return this.abilities;
    }

    public final boolean getShoulderMountable() {
        return this.shoulderMountable;
    }

    @NotNull
    public final List<ShoulderEffect> getShoulderEffects() {
        return this.shoulderEffects;
    }

    @NotNull
    public final Learnset getMoves() {
        return this.moves;
    }

    @NotNull
    public final Set<String> getFeatures() {
        return this.features;
    }

    @NotNull
    public final PokemonBehaviour getBehaviour() {
        return this.behaviour;
    }

    @NotNull
    public final List<String> getPokedex() {
        return this.pokedex;
    }

    @NotNull
    public final DropTable getDrops() {
        return this.drops;
    }

    public final int getEggCycles() {
        return this.eggCycles;
    }

    @NotNull
    public final HashSet<EggGroup> getEggGroups() {
        return this.eggGroups;
    }

    public final boolean getDynamaxBlocked() {
        return this.dynamaxBlocked;
    }

    public final void setDynamaxBlocked(boolean bl) {
        this.dynamaxBlocked = bl;
    }

    public final boolean getImplemented() {
        return this.implemented;
    }

    public final void setImplemented(boolean bl) {
        this.implemented = bl;
    }

    public final float getHeight() {
        return this.height;
    }

    public final float getWeight() {
        return this.weight;
    }

    @NotNull
    public final List<FormData> getForms() {
        return this.forms;
    }

    @NotNull
    public final FormData getStandardForm() {
        Lazy lazy = this.standardForm$delegate;
        return (FormData)lazy.getValue();
    }

    @NotNull
    public final HashSet<String> getLabels() {
        return this.labels;
    }

    @NotNull
    public final Set<Evolution> getEvolutions() {
        return this.evolutions;
    }

    @Nullable
    public final PreEvolution getPreEvolution() {
        return this.preEvolution;
    }

    @NotNull
    public final ResourceLocation getResourceIdentifier() {
        ResourceLocation resourceLocation = this.resourceIdentifier;
        if (resourceLocation != null) {
            return resourceLocation;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"resourceIdentifier");
        return null;
    }

    public final void setResourceIdentifier(@NotNull ResourceLocation resourceLocation) {
        Intrinsics.checkNotNullParameter((Object)resourceLocation, (String)"<set-?>");
        this.resourceIdentifier = resourceLocation;
    }

    /*
     * Enabled aggressive block sorting
     */
    @NotNull
    public final Iterable<ElementalType> getTypes() {
        Iterable iterable;
        Object object = this.secondaryType;
        if (object != null) {
            ElementalType it = object;
            boolean bl = false;
            Object[] objectArray = new ElementalType[]{this.primaryType, it};
            List list = CollectionsKt.listOf((Object[])objectArray);
            object = list;
            if (list != null) {
                iterable = (Iterable)object;
                return iterable;
            }
        }
        iterable = CollectionsKt.listOf((Object)this.primaryType);
        return iterable;
    }

    @NotNull
    public final ResourceLocation getBattleTheme() {
        return this.battleTheme;
    }

    public final void setBattleTheme(@NotNull ResourceLocation resourceLocation) {
        Intrinsics.checkNotNullParameter((Object)resourceLocation, (String)"<set-?>");
        this.battleTheme = resourceLocation;
    }

    @Nullable
    public final LightingData getLightingData() {
        return this.lightingData;
    }

    public final void initialize() {
        FormData it;
        Cobblemon.INSTANCE.getStatProvider().provide(this);
        Iterable $this$forEach$iv = this.forms;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            it = (FormData)element$iv;
            boolean bl = false;
            it.initialize(this);
        }
        if (!((Collection)this.forms).isEmpty()) {
            boolean bl;
            block9: {
                Iterable $this$none$iv = this.forms;
                boolean $i$f$none = false;
                if ($this$none$iv instanceof Collection && ((Collection)$this$none$iv).isEmpty()) {
                    bl = true;
                } else {
                    for (Object element$iv : $this$none$iv) {
                        it = (FormData)element$iv;
                        boolean bl2 = false;
                        if (!Intrinsics.areEqual((Object)it, (Object)this.getStandardForm())) continue;
                        bl = false;
                        break block9;
                    }
                    bl = true;
                }
            }
            if (bl) {
                this.forms.add(0, this.getStandardForm());
            }
        }
        LightingData lightingData = this.lightingData;
        if (lightingData != null) {
            LightingData it2 = lightingData;
            boolean bl = false;
            this.lightingData = LightingData.copy$default(it2, RangesKt.coerceIn((int)it2.getLightLevel(), (int)0, (int)15), null, 2, null);
        }
        PreEvolution preEvolution = this.preEvolution;
        if (preEvolution != null) {
            preEvolution.getSpecies();
        }
        PreEvolution preEvolution2 = this.preEvolution;
        if (preEvolution2 != null) {
            preEvolution2.getForm();
        }
        this.evolutions.size();
    }

    public final void resolveEvolutionMoves$common() {
        Iterable $this$forEach$iv = this.evolutions;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Evolution evolution = (Evolution)element$iv;
            boolean bl = false;
            if (!(!((Collection)evolution.getLearnableMoves()).isEmpty()) || evolution.getResult().getSpecies() == null) continue;
            Pokemon pokemon = evolution.getResult().create();
            CollectionsKt.addAll((Collection)pokemon.getForm().getMoves().getEvolutionMoves(), (Iterable)evolution.getLearnableMoves());
        }
        $this$forEach$iv = this.forms;
        $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            FormData p0 = (FormData)element$iv;
            boolean bl = false;
            p0.resolveEvolutionMoves$common();
        }
    }

    @NotNull
    public final Pokemon create(int level) {
        return PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "species=\"" + this.name + "\" level=" + level, null, null, 6, null).create();
    }

    public static /* synthetic */ Pokemon create$default(Species species, int n, int n2, Object object) {
        if ((n2 & 1) != 0) {
            n = 10;
        }
        return species.create(n);
    }

    @NotNull
    public final FormData getForm(@NotNull Set<String> aspects) {
        FormData formData;
        FormData formData2;
        block6: {
            Intrinsics.checkNotNullParameter(aspects, (String)"aspects");
            List<FormData> $this$lastOrNull$iv = this.forms;
            boolean $i$f$lastOrNull = false;
            ListIterator<FormData> iterator$iv = $this$lastOrNull$iv.listIterator($this$lastOrNull$iv.size());
            while (iterator$iv.hasPrevious()) {
                boolean bl;
                FormData element$iv;
                block5: {
                    FormData it = element$iv = iterator$iv.previous();
                    boolean bl2 = false;
                    Iterable $this$all$iv = it.getAspects();
                    boolean $i$f$all = false;
                    if ($this$all$iv instanceof Collection && ((Collection)$this$all$iv).isEmpty()) {
                        bl = true;
                    } else {
                        for (Object element$iv2 : $this$all$iv) {
                            String it2 = (String)element$iv2;
                            boolean bl3 = false;
                            if (aspects.contains(it2)) continue;
                            bl = false;
                            break block5;
                        }
                        bl = true;
                    }
                }
                if (!bl) continue;
                formData2 = element$iv;
                break block6;
            }
            formData2 = null;
        }
        if ((formData = (FormData)formData2) == null) {
            formData = this.getStandardForm();
        }
        return formData;
    }

    public final float eyeHeight(@NotNull PokemonEntity entity2) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        Float f = this.resolveEyeHeight(entity2);
        float multiplier = f != null ? f.floatValue() : 0.85f;
        return entity2.m_20206_() * multiplier;
    }

    private final Float resolveEyeHeight(PokemonEntity entity2) {
        Float f;
        if (PoseType.Companion.getSWIMMING_POSES().contains((Object)entity2.getCurrentPoseType())) {
            f = this.swimmingEyeHeight;
            if (f == null) {
                f = this.standingEyeHeight;
            }
        } else if (PoseType.Companion.getFLYING_POSES().contains((Object)entity2.getCurrentPoseType())) {
            f = this.flyingEyeHeight;
            if (f == null) {
                f = this.standingEyeHeight;
            }
        } else {
            f = this.standingEyeHeight;
        }
        return f;
    }

    public final boolean canGmax() {
        Object v0;
        block1: {
            Iterable iterable = this.forms;
            for (Object t : iterable) {
                FormData it = (FormData)t;
                boolean bl = false;
                if (!Intrinsics.areEqual((Object)it.formOnlyShowdownId(), (Object)"gmax")) continue;
                v0 = t;
                break block1;
            }
            v0 = null;
        }
        return v0 != null;
    }

    @Override
    public void encode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.writeBoolean(this.implemented);
        buffer.m_130070_(this.name);
        buffer.writeInt(this.nationalPokedexNumber);
        buffer.m_236831_((Map)this.baseStats, Species::encode$lambda$8, Species::encode$lambda$9);
        buffer.m_130070_(this.primaryType.getName());
        buffer.m_236821_((Object)this.secondaryType, Species::encode$lambda$10);
        buffer.m_130070_(this.experienceGroup.getName());
        buffer.writeFloat(this.height);
        buffer.writeFloat(this.weight);
        buffer.writeFloat(this.baseScale);
        buffer.writeFloat(this.hitbox.f_20377_);
        buffer.writeFloat(this.hitbox.f_20378_);
        buffer.writeBoolean(this.hitbox.f_20379_);
        this.moves.encode(buffer);
        buffer.m_236828_((Collection)this.pokedex, Species::encode$lambda$11);
        buffer.m_236828_((Collection)this.forms, Species::encode$lambda$12);
        buffer.m_130085_(this.battleTheme);
        buffer.m_236828_((Collection)this.features, Species::encode$lambda$13);
        buffer.m_236821_((Object)this.lightingData, Species::encode$lambda$14);
    }

    @Override
    public void decode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        this.implemented = buffer.readBoolean();
        String string = buffer.m_130277_();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"buffer.readString()");
        this.name = string;
        this.nationalPokedexNumber = buffer.readInt();
        this.baseStats.putAll(buffer.m_236847_(Species::decode$lambda$15, Species::decode$lambda$16));
        String string2 = buffer.m_130277_();
        Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"buffer.readString()");
        this.primaryType = ElementalTypes.INSTANCE.getOrException(string2);
        this.secondaryType = (ElementalType)buffer.m_236868_(Species::decode$lambda$17);
        String string3 = buffer.m_130277_();
        Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"buffer.readString()");
        ExperienceGroup experienceGroup = ExperienceGroups.INSTANCE.findByName(string3);
        Intrinsics.checkNotNull((Object)experienceGroup);
        this.experienceGroup = experienceGroup;
        this.height = buffer.readFloat();
        this.weight = buffer.readFloat();
        this.baseScale = buffer.readFloat();
        this.hitbox = new EntityDimensions(buffer.readFloat(), buffer.readFloat(), buffer.readBoolean());
        this.moves.decode(buffer);
        this.pokedex.clear();
        Collection collection = this.pokedex;
        List list = buffer.m_236845_(Species::decode$lambda$18);
        Intrinsics.checkNotNullExpressionValue((Object)list, (String)"buffer.readList { pb -> pb.readString() }");
        CollectionsKt.addAll((Collection)collection, (Iterable)list);
        this.forms.clear();
        collection = this.forms;
        List list2 = buffer.m_236845_(Species::decode$lambda$20);
        Intrinsics.checkNotNullExpressionValue((Object)list2, (String)"buffer.readList{ pb -> F\u2026().apply { decode(pb) } }");
        CollectionsKt.addAll((Collection)collection, (Iterable)CollectionsKt.filterNotNull((Iterable)list2));
        ResourceLocation resourceLocation = buffer.m_130281_();
        Intrinsics.checkNotNullExpressionValue((Object)resourceLocation, (String)"buffer.readIdentifier()");
        this.battleTheme = resourceLocation;
        this.features.clear();
        collection = this.features;
        List list3 = buffer.m_236845_(Species::decode$lambda$21);
        Intrinsics.checkNotNullExpressionValue((Object)list3, (String)"buffer.readList { pb -> pb.readString() }");
        CollectionsKt.addAll((Collection)collection, (Iterable)list3);
        this.lightingData = (LightingData)buffer.m_236868_(Species::decode$lambda$22);
        this.initialize();
    }

    @Override
    public boolean shouldSynchronize(@NotNull Species other) {
        Intrinsics.checkNotNullParameter((Object)other, (String)"other");
        if (!Intrinsics.areEqual((Object)other.getResourceIdentifier().toString(), (Object)other.getResourceIdentifier().toString())) {
            return false;
        }
        return !Intrinsics.areEqual((Object)other.showdownId(), (Object)this.showdownId()) || other.nationalPokedexNumber != this.nationalPokedexNumber || !Intrinsics.areEqual(other.baseStats, this.baseStats) || !Intrinsics.areEqual((Object)other.hitbox, (Object)this.hitbox) || !Intrinsics.areEqual((Object)other.primaryType, (Object)this.primaryType) || !Intrinsics.areEqual((Object)other.secondaryType, (Object)this.secondaryType) || !Intrinsics.areEqual((Float)other.standingEyeHeight, (Float)this.standingEyeHeight) || !Intrinsics.areEqual((Float)other.swimmingEyeHeight, (Float)this.swimmingEyeHeight) || !Intrinsics.areEqual((Float)other.flyingEyeHeight, (Float)this.flyingEyeHeight) || other.dynamaxBlocked != this.dynamaxBlocked || !Intrinsics.areEqual(other.pokedex, this.pokedex) || !Intrinsics.areEqual(other.forms, this.forms) || this.moves.shouldSynchronize(other.moves) || !Intrinsics.areEqual((Object)other.battleTheme, (Object)this.battleTheme) || !Intrinsics.areEqual(other.features, this.features);
    }

    @Override
    @NotNull
    public String showdownId() {
        String id = this.unformattedShowdownId();
        if (Intrinsics.areEqual((Object)this.getResourceIdentifier().m_135827_(), (Object)"cobblemon")) {
            return id;
        }
        return this.getResourceIdentifier().m_135827_() + id;
    }

    private final String unformattedShowdownId() {
        Regex regex = ShowdownIdentifiable.Companion.getREGEX$common();
        String string = this.name.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
        return regex.replace((CharSequence)string, "");
    }

    @NotNull
    public String toString() {
        return this.showdownId();
    }

    private static final void encode$lambda$8(FriendlyByteBuf keyBuffer, Stat stat) {
        StatProvider statProvider = Cobblemon.INSTANCE.getStatProvider();
        Intrinsics.checkNotNullExpressionValue((Object)keyBuffer, (String)"keyBuffer");
        Intrinsics.checkNotNullExpressionValue((Object)stat, (String)"stat");
        statProvider.encode(keyBuffer, stat);
    }

    private static final void encode$lambda$9(FriendlyByteBuf valueBuffer, Integer value2) {
        Intrinsics.checkNotNullExpressionValue((Object)valueBuffer, (String)"valueBuffer");
        ByteBuf byteBuf = (ByteBuf)valueBuffer;
        Intrinsics.checkNotNullExpressionValue((Object)value2, (String)"value");
        NetExtensionsKt.writeSizedInt(byteBuf, IntSize.U_SHORT, value2);
    }

    private static final void encode$lambda$10(FriendlyByteBuf pb, ElementalType type) {
        pb.m_130070_(type.getName());
    }

    private static final void encode$lambda$11(FriendlyByteBuf pb, String line) {
        pb.m_130070_(line);
    }

    private static final void encode$lambda$12(FriendlyByteBuf pb, FormData form2) {
        Intrinsics.checkNotNullExpressionValue((Object)pb, (String)"pb");
        form2.encode(pb);
    }

    private static final void encode$lambda$13(FriendlyByteBuf pb, String feature) {
        pb.m_130070_(feature);
    }

    private static final void encode$lambda$14(FriendlyByteBuf pb, LightingData data) {
        pb.writeInt(data.getLightLevel());
        pb.m_130068_((Enum)data.getLiquidGlowMode());
    }

    private static final Stat decode$lambda$15(FriendlyByteBuf keyBuffer) {
        StatProvider statProvider = Cobblemon.INSTANCE.getStatProvider();
        Intrinsics.checkNotNullExpressionValue((Object)keyBuffer, (String)"keyBuffer");
        return statProvider.decode(keyBuffer);
    }

    private static final Integer decode$lambda$16(FriendlyByteBuf valueBuffer) {
        Intrinsics.checkNotNullExpressionValue((Object)valueBuffer, (String)"valueBuffer");
        return NetExtensionsKt.readSizedInt((ByteBuf)valueBuffer, IntSize.U_SHORT);
    }

    private static final ElementalType decode$lambda$17(FriendlyByteBuf pb) {
        String string = pb.m_130277_();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"pb.readString()");
        return ElementalTypes.INSTANCE.getOrException(string);
    }

    private static final String decode$lambda$18(FriendlyByteBuf pb) {
        return pb.m_130277_();
    }

    private static final FormData decode$lambda$20(FriendlyByteBuf pb) {
        FormData formData;
        FormData $this$decode_u24lambda_u2420_u24lambda_u2419 = formData = new FormData(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, -1, 3, null);
        boolean bl = false;
        Intrinsics.checkNotNullExpressionValue((Object)pb, (String)"pb");
        $this$decode_u24lambda_u2420_u24lambda_u2419.decode(pb);
        return formData;
    }

    private static final String decode$lambda$21(FriendlyByteBuf pb) {
        return pb.m_130277_();
    }

    private static final LightingData decode$lambda$22(FriendlyByteBuf pb) {
        int n = pb.readInt();
        Enum enum_ = pb.m_130066_(LightingData.LiquidGlowMode.class);
        Intrinsics.checkNotNullExpressionValue((Object)enum_, (String)"pb.readEnumConstant(Ligh\u2026quidGlowMode::class.java)");
        return new LightingData(n, (LightingData.LiquidGlowMode)enum_);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0007\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0007"}, d2={"Lcom/cobblemon/mod/common/pokemon/Species$Companion;", "", "", "VANILLA_DEFAULT_EYE_HEIGHT", "F", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

