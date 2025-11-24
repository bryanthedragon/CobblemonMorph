/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.SerializedName
 *  io.netty.buffer.ByteBuf
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.RangesKt
 *  kotlin.text.Regex
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.EntityDimensions
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.AbilityPool;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.ShowdownIdentifiable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.DropTable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Decodable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Encodable;
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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai.FormPokemonBehaviour;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.lighthing.LightingData;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt;
import com.google.gson.annotations.SerializedName;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import kotlin.text.Regex;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u00f4\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010$\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0002\b!\n\u0002\u0010 \n\u0002\b\u0011\n\u0002\u0010\u001c\n\u0002\b\b\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u00e5\u0003\u0012\t\b\u0002\u0010\u00a4\u0001\u001a\u00020\u0014\u0012\u0016\b\u0002\u0010.\u001a\u0010\u0012\u0004\u0012\u00020-\u0012\u0004\u0012\u00020\u0017\u0018\u00010,\u0012\n\b\u0002\u0010P\u001a\u0004\u0018\u00010\u0011\u0012\n\b\u0002\u0010*\u001a\u0004\u0018\u00010\u0011\u0012\n\b\u0002\u0010J\u001a\u0004\u0018\u00010I\u0012\n\b\u0002\u00107\u001a\u0004\u0018\u00010\u0017\u0012\n\b\u0002\u0010F\u001a\u0004\u0018\u00010E\u0012\n\b\u0002\u0010'\u001a\u0004\u0018\u00010\u0017\u0012\n\b\u0002\u0010)\u001a\u0004\u0018\u00010\u0017\u0012\u0016\b\u0002\u0010A\u001a\u0010\u0012\u0004\u0012\u00020-\u0012\u0004\u0012\u00020\u0017\u0018\u00010,\u0012\n\b\u0002\u0010[\u001a\u0004\u0018\u00010Z\u0012\n\b\u0002\u0010]\u001a\u0004\u0018\u00010Z\u0012\n\b\u0002\u0010`\u001a\u0004\u0018\u00010\f\u0012\u0010\b\u0002\u0010_\u001a\n\u0012\u0004\u0012\u00020^\u0018\u00010T\u0012\n\b\u0002\u0010R\u001a\u0004\u0018\u00010Q\u0012\u0010\b\u0002\u0010D\u001a\n\u0012\u0004\u0012\u00020C\u0018\u00010B\u0012\n\b\u0002\u0010%\u001a\u0004\u0018\u00010$\u0012\n\b\u0002\u00109\u001a\u0004\u0018\u000108\u0012\u0010\b\u0002\u0010U\u001a\n\u0012\u0004\u0012\u00020\u0014\u0018\u00010T\u0012\n\b\u0002\u0010X\u001a\u0004\u0018\u00010W\u0012\u000b\b\u0002\u0010\u00c1\u0001\u001a\u0004\u0018\u00010\u0011\u0012\u000b\b\u0002\u0010\u00c2\u0001\u001a\u0004\u0018\u00010\u0011\u0012\u000b\b\u0002\u0010\u008e\u0001\u001a\u0004\u0018\u00010\u0011\u0012\u0010\b\u0002\u0010L\u001a\n\u0012\u0004\u0012\u00020\u0014\u0018\u00010=\u0012\n\b\u0002\u0010;\u001a\u0004\u0018\u00010\f\u0012\u0010\b\u0002\u0010?\u001a\n\u0012\u0004\u0012\u00020>\u0018\u00010=\u0012\n\b\u0002\u0010H\u001a\u0004\u0018\u00010\u0011\u0012\n\b\u0002\u0010a\u001a\u0004\u0018\u00010\u0011\u0012\u000b\b\u0002\u0010\u00b4\u0001\u001a\u0004\u0018\u00010\u0014\u0012\u000b\b\u0002\u0010\u00af\u0001\u001a\u0004\u0018\u00010\u0014\u0012\u0012\b\u0002\u0010\u00b2\u0001\u001a\u000b\u0012\u0004\u0012\u00020\u0014\u0018\u00010\u00b1\u0001\u0012\f\b\u0002\u0010\u0090\u0001\u001a\u0005\u0018\u00010\u008f\u0001\u0012\n\b\u0002\u00105\u001a\u0004\u0018\u000104\u0012\n\b\u0002\u0010N\u001a\u0004\u0018\u00010M\u00a2\u0006\u0006\b\u00c9\u0001\u0010\u00ca\u0001J\u0017\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0017\u0010\t\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\t\u0010\bJ\u001a\u0010\r\u001a\u00020\f2\b\u0010\u000b\u001a\u0004\u0018\u00010\nH\u0096\u0002\u00a2\u0006\u0004\b\r\u0010\u000eJ\u0015\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0010\u001a\u00020\u000f\u00a2\u0006\u0004\b\u0012\u0010\u0013J\r\u0010\u0015\u001a\u00020\u0014\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u000f\u0010\u0018\u001a\u00020\u0017H\u0016\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u0015\u0010\u001c\u001a\u00020\u00002\u0006\u0010\u001b\u001a\u00020\u001a\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u000f\u0010 \u001a\u00020\u0006H\u0000\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u0019\u0010!\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0010\u001a\u00020\u000fH\u0002\u00a2\u0006\u0004\b!\u0010\"J\u000f\u0010#\u001a\u00020\u0014H\u0016\u00a2\u0006\u0004\b#\u0010\u0016R\u0016\u0010%\u001a\u0004\u0018\u00010$8\u0002X\u0083\u0004\u00a2\u0006\u0006\n\u0004\b%\u0010&R\u0018\u0010'\u001a\u0004\u0018\u00010\u00178\u0002@\u0002X\u0083\u000e\u00a2\u0006\u0006\n\u0004\b'\u0010(R\u0018\u0010)\u001a\u0004\u0018\u00010\u00178\u0002@\u0002X\u0083\u000e\u00a2\u0006\u0006\n\u0004\b)\u0010(R\u0018\u0010*\u001a\u0004\u0018\u00010\u00118\u0002@\u0002X\u0083\u000e\u00a2\u0006\u0006\n\u0004\b*\u0010+R0\u0010.\u001a\u0010\u0012\u0004\u0012\u00020-\u0012\u0004\u0012\u00020\u0017\u0018\u00010,8\u0000@\u0000X\u0081\u000e\u00a2\u0006\u0012\n\u0004\b.\u0010/\u001a\u0004\b0\u00101\"\u0004\b2\u00103R\u0018\u00105\u001a\u0004\u0018\u0001048\u0002@\u0002X\u0083\u000e\u00a2\u0006\u0006\n\u0004\b5\u00106R\u0018\u00107\u001a\u0004\u0018\u00010\u00178\u0002@\u0002X\u0083\u000e\u00a2\u0006\u0006\n\u0004\b7\u0010(R\u0016\u00109\u001a\u0004\u0018\u0001088\u0002X\u0083\u0004\u00a2\u0006\u0006\n\u0004\b9\u0010:R\u0018\u0010;\u001a\u0004\u0018\u00010\f8\u0002@\u0002X\u0083\u000e\u00a2\u0006\u0006\n\u0004\b;\u0010<R\u001c\u0010?\u001a\n\u0012\u0004\u0012\u00020>\u0018\u00010=8\u0002X\u0083\u0004\u00a2\u0006\u0006\n\u0004\b?\u0010@R$\u0010A\u001a\u0010\u0012\u0004\u0012\u00020-\u0012\u0004\u0012\u00020\u0017\u0018\u00010,8\u0002@\u0002X\u0083\u000e\u00a2\u0006\u0006\n\u0004\bA\u0010/R\u001c\u0010D\u001a\n\u0012\u0004\u0012\u00020C\u0018\u00010B8\u0002X\u0083\u0004\u00a2\u0006\u0006\n\u0004\bD\u0010@R\u0018\u0010F\u001a\u0004\u0018\u00010E8\u0002@\u0002X\u0083\u000e\u00a2\u0006\u0006\n\u0004\bF\u0010GR\u0018\u0010H\u001a\u0004\u0018\u00010\u00118\u0002@\u0002X\u0083\u000e\u00a2\u0006\u0006\n\u0004\bH\u0010+R\u0018\u0010J\u001a\u0004\u0018\u00010I8\u0002@\u0002X\u0083\u000e\u00a2\u0006\u0006\n\u0004\bJ\u0010KR\u001c\u0010L\u001a\n\u0012\u0004\u0012\u00020\u0014\u0018\u00010=8\u0002X\u0083\u0004\u00a2\u0006\u0006\n\u0004\bL\u0010@R\u0018\u0010N\u001a\u0004\u0018\u00010M8\u0002@\u0002X\u0083\u000e\u00a2\u0006\u0006\n\u0004\bN\u0010OR\u0016\u0010P\u001a\u0004\u0018\u00010\u00118\u0002X\u0083\u0004\u00a2\u0006\u0006\n\u0004\bP\u0010+R\u0018\u0010R\u001a\u0004\u0018\u00010Q8\u0002@\u0002X\u0083\u000e\u00a2\u0006\u0006\n\u0004\bR\u0010SR\u001e\u0010U\u001a\n\u0012\u0004\u0012\u00020\u0014\u0018\u00010T8\u0002@\u0002X\u0083\u000e\u00a2\u0006\u0006\n\u0004\bU\u0010VR\u0016\u0010X\u001a\u0004\u0018\u00010W8\u0002X\u0083\u0004\u00a2\u0006\u0006\n\u0004\bX\u0010YR\u0018\u0010[\u001a\u0004\u0018\u00010Z8\u0002@\u0002X\u0083\u000e\u00a2\u0006\u0006\n\u0004\b[\u0010\\R\u0018\u0010]\u001a\u0004\u0018\u00010Z8\u0002@\u0002X\u0083\u000e\u00a2\u0006\u0006\n\u0004\b]\u0010\\R\u001c\u0010_\u001a\n\u0012\u0004\u0012\u00020^\u0018\u00010T8\u0002X\u0083\u0004\u00a2\u0006\u0006\n\u0004\b_\u0010VR\u0016\u0010`\u001a\u0004\u0018\u00010\f8\u0002X\u0083\u0004\u00a2\u0006\u0006\n\u0004\b`\u0010<R\u0018\u0010a\u001a\u0004\u0018\u00010\u00118\u0002@\u0002X\u0083\u000e\u00a2\u0006\u0006\n\u0004\ba\u0010+R\u0011\u0010d\u001a\u00020$8F\u00a2\u0006\u0006\u001a\u0004\bb\u0010cR(\u0010e\u001a\b\u0012\u0004\u0012\u00020\u00140T8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\be\u0010V\u001a\u0004\bf\u0010g\"\u0004\bh\u0010iR\u0011\u0010k\u001a\u00020\u00178F\u00a2\u0006\u0006\u001a\u0004\bj\u0010\u0019R\u0011\u0010m\u001a\u00020\u00178F\u00a2\u0006\u0006\u001a\u0004\bl\u0010\u0019R\u0011\u0010p\u001a\u00020\u00118F\u00a2\u0006\u0006\u001a\u0004\bn\u0010oR\u001d\u0010s\u001a\u000e\u0012\u0004\u0012\u00020-\u0012\u0004\u0012\u00020\u00170q8F\u00a2\u0006\u0006\u001a\u0004\br\u00101R\u0011\u0010v\u001a\u0002048F\u00a2\u0006\u0006\u001a\u0004\bt\u0010uR\u0017\u0010x\u001a\u00020w8\u0006\u00a2\u0006\f\n\u0004\bx\u0010y\u001a\u0004\bz\u0010{R\u0011\u0010}\u001a\u00020\u00178F\u00a2\u0006\u0006\u001a\u0004\b|\u0010\u0019R\u0012\u0010\u0080\u0001\u001a\u0002088F\u00a2\u0006\u0006\u001a\u0004\b~\u0010\u007fR\u0014\u0010\u0083\u0001\u001a\u00020\f8F\u00a2\u0006\b\u001a\u0006\b\u0081\u0001\u0010\u0082\u0001R\u001a\u0010\u0086\u0001\u001a\b\u0012\u0004\u0012\u00020>0=8F\u00a2\u0006\b\u001a\u0006\b\u0084\u0001\u0010\u0085\u0001R\u001f\u0010\u0088\u0001\u001a\u000e\u0012\u0004\u0012\u00020-\u0012\u0004\u0012\u00020\u00170q8F\u00a2\u0006\u0007\u001a\u0005\b\u0087\u0001\u00101R\u001a\u0010\u008a\u0001\u001a\b\u0012\u0004\u0012\u00020C0B8F\u00a2\u0006\b\u001a\u0006\b\u0089\u0001\u0010\u0085\u0001R\u0014\u0010\u008d\u0001\u001a\u00020E8F\u00a2\u0006\b\u001a\u0006\b\u008b\u0001\u0010\u008c\u0001R\u001a\u0010\u008e\u0001\u001a\u0004\u0018\u00010\u00118\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0007\n\u0005\b\u008e\u0001\u0010+R\u001f\u0010\u0090\u0001\u001a\u0005\u0018\u00010\u008f\u00018\u0006\u00a2\u0006\u0010\n\u0006\b\u0090\u0001\u0010\u0091\u0001\u001a\u0006\b\u0092\u0001\u0010\u0093\u0001R\u0013\u0010\u0095\u0001\u001a\u00020\u00118F\u00a2\u0006\u0007\u001a\u0005\b\u0094\u0001\u0010oR\u0014\u0010\u0098\u0001\u001a\u00020I8F\u00a2\u0006\b\u001a\u0006\b\u0096\u0001\u0010\u0097\u0001R\u001a\u0010\u009a\u0001\u001a\b\u0012\u0004\u0012\u00020\u00140=8F\u00a2\u0006\b\u001a\u0006\b\u0099\u0001\u0010\u0085\u0001R\u0016\u0010\u009d\u0001\u001a\u0004\u0018\u00010M8F\u00a2\u0006\b\u001a\u0006\b\u009b\u0001\u0010\u009c\u0001R\u0013\u0010\u009f\u0001\u001a\u00020\u00118F\u00a2\u0006\u0007\u001a\u0005\b\u009e\u0001\u0010oR\u0014\u0010\u00a2\u0001\u001a\u00020Q8F\u00a2\u0006\b\u001a\u0006\b\u00a0\u0001\u0010\u00a1\u0001R)\u0010\u00a4\u0001\u001a\u00020\u00142\u0007\u0010\u00a3\u0001\u001a\u00020\u00148\u0006@BX\u0087\u000e\u00a2\u0006\u000f\n\u0006\b\u00a4\u0001\u0010\u00a5\u0001\u001a\u0005\b\u00a6\u0001\u0010\u0016R\u0019\u0010\u00a8\u0001\u001a\b\u0012\u0004\u0012\u00020\u00140T8F\u00a2\u0006\u0007\u001a\u0005\b\u00a7\u0001\u0010gR\u0016\u0010\u00ab\u0001\u001a\u0004\u0018\u00010W8F\u00a2\u0006\b\u001a\u0006\b\u00a9\u0001\u0010\u00aa\u0001R\u0014\u0010\u00ae\u0001\u001a\u00020Z8F\u00a2\u0006\b\u001a\u0006\b\u00ac\u0001\u0010\u00ad\u0001R\u001d\u0010\u00af\u0001\u001a\u0004\u0018\u00010\u00148\u0006\u00a2\u0006\u000f\n\u0006\b\u00af\u0001\u0010\u00a5\u0001\u001a\u0005\b\u00b0\u0001\u0010\u0016R#\u0010\u00b2\u0001\u001a\u000b\u0012\u0004\u0012\u00020\u0014\u0018\u00010\u00b1\u00018\u0006\u00a2\u0006\u000e\n\u0005\b\u00b2\u0001\u0010V\u001a\u0005\b\u00b3\u0001\u0010gR\u001d\u0010\u00b4\u0001\u001a\u0004\u0018\u00010\u00148\u0006\u00a2\u0006\u000f\n\u0006\b\u00b4\u0001\u0010\u00a5\u0001\u001a\u0005\b\u00b5\u0001\u0010\u0016R\u0016\u0010\u00b7\u0001\u001a\u0004\u0018\u00010Z8F\u00a2\u0006\b\u001a\u0006\b\u00b6\u0001\u0010\u00ad\u0001R\u0019\u0010\u00b9\u0001\u001a\b\u0012\u0004\u0012\u00020^0T8F\u00a2\u0006\u0007\u001a\u0005\b\u00b8\u0001\u0010gR\u0014\u0010\u00bb\u0001\u001a\u00020\f8F\u00a2\u0006\b\u001a\u0006\b\u00ba\u0001\u0010\u0082\u0001R'\u0010\u001b\u001a\u00020\u001a8\u0006@\u0006X\u0086.\u00a2\u0006\u0017\n\u0005\b\u001b\u0010\u00bc\u0001\u001a\u0006\b\u00bd\u0001\u0010\u00be\u0001\"\u0006\b\u00bf\u0001\u0010\u00c0\u0001R\u001a\u0010\u00c1\u0001\u001a\u0004\u0018\u00010\u00118\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0007\n\u0005\b\u00c1\u0001\u0010+R\u001a\u0010\u00c2\u0001\u001a\u0004\u0018\u00010\u00118\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0007\n\u0005\b\u00c2\u0001\u0010+R\u001b\u0010\u00c6\u0001\u001a\t\u0012\u0004\u0012\u00020Z0\u00c3\u00018F\u00a2\u0006\b\u001a\u0006\b\u00c4\u0001\u0010\u00c5\u0001R\u0013\u0010\u00c8\u0001\u001a\u00020\u00118F\u00a2\u0006\u0007\u001a\u0005\b\u00c7\u0001\u0010o\u00a8\u0006\u00cb\u0001"}, d2={"Lcom/cobblemon/mod/common/pokemon/FormData;", "Lcom/cobblemon/mod/common/api/net/Decodable;", "Lcom/cobblemon/mod/common/api/net/Encodable;", "Lcom/cobblemon/mod/common/api/data/ShowdownIdentifiable;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "encode", "", "other", "", "equals", "(Ljava/lang/Object;)Z", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "entity", "", "eyeHeight", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)F", "", "formOnlyShowdownId", "()Ljava/lang/String;", "", "hashCode", "()I", "Lcom/cobblemon/mod/common/pokemon/Species;", "species", "initialize", "(Lcom/cobblemon/mod/common/pokemon/Species;)Lcom/cobblemon/mod/common/pokemon/FormData;", "resolveEvolutionMoves$common", "()V", "resolveEvolutionMoves", "resolveEyeHeight", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)Ljava/lang/Float;", "showdownId", "Lcom/cobblemon/mod/common/api/abilities/AbilityPool;", "_abilities", "Lcom/cobblemon/mod/common/api/abilities/AbilityPool;", "_baseExperienceYield", "Ljava/lang/Integer;", "_baseFriendship", "_baseScale", "Ljava/lang/Float;", "", "Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;", "_baseStats", "Ljava/util/Map;", "get_baseStats$common", "()Ljava/util/Map;", "set_baseStats$common", "(Ljava/util/Map;)V", "Lnet/minecraft/resources/ResourceLocation;", "_battleTheme", "Lnet/minecraft/resources/ResourceLocation;", "_catchRate", "Lcom/cobblemon/mod/common/api/drop/DropTable;", "_drops", "Lcom/cobblemon/mod/common/api/drop/DropTable;", "_dynamaxBlocked", "Ljava/lang/Boolean;", "", "Lcom/cobblemon/mod/common/api/pokemon/egg/EggGroup;", "_eggGroups", "Ljava/util/Set;", "_evYield", "", "Lcom/cobblemon/mod/common/api/pokemon/evolution/Evolution;", "_evolutions", "Lcom/cobblemon/mod/common/api/pokemon/experience/ExperienceGroup;", "_experienceGroup", "Lcom/cobblemon/mod/common/api/pokemon/experience/ExperienceGroup;", "_height", "Lnet/minecraft/world/entity/EntityDimensions;", "_hitbox", "Lnet/minecraft/world/entity/EntityDimensions;", "_labels", "Lcom/cobblemon/mod/common/pokemon/lighthing/LightingData;", "_lightingData", "Lcom/cobblemon/mod/common/pokemon/lighthing/LightingData;", "_maleRatio", "Lcom/cobblemon/mod/common/api/pokemon/moves/Learnset;", "_moves", "Lcom/cobblemon/mod/common/api/pokemon/moves/Learnset;", "", "_pokedex", "Ljava/util/List;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/PreEvolution;", "_preEvolution", "Lcom/cobblemon/mod/common/api/pokemon/evolution/PreEvolution;", "Lcom/cobblemon/mod/common/api/types/ElementalType;", "_primaryType", "Lcom/cobblemon/mod/common/api/types/ElementalType;", "_secondaryType", "Lcom/cobblemon/mod/common/api/pokemon/effect/ShoulderEffect;", "_shoulderEffects", "_shoulderMountable", "_weight", "getAbilities", "()Lcom/cobblemon/mod/common/api/abilities/AbilityPool;", "abilities", "aspects", "getAspects", "()Ljava/util/List;", "setAspects", "(Ljava/util/List;)V", "getBaseExperienceYield", "baseExperienceYield", "getBaseFriendship", "baseFriendship", "getBaseScale", "()F", "baseScale", "", "getBaseStats", "baseStats", "getBattleTheme", "()Lnet/minecraft/resources/ResourceLocation;", "battleTheme", "Lcom/cobblemon/mod/common/pokemon/ai/FormPokemonBehaviour;", "behaviour", "Lcom/cobblemon/mod/common/pokemon/ai/FormPokemonBehaviour;", "getBehaviour", "()Lcom/cobblemon/mod/common/pokemon/ai/FormPokemonBehaviour;", "getCatchRate", "catchRate", "getDrops", "()Lcom/cobblemon/mod/common/api/drop/DropTable;", "drops", "getDynamaxBlocked", "()Z", "dynamaxBlocked", "getEggGroups", "()Ljava/util/Set;", "eggGroups", "getEvYield", "evYield", "getEvolutions", "evolutions", "getExperienceGroup", "()Lcom/cobblemon/mod/common/api/pokemon/experience/ExperienceGroup;", "experienceGroup", "flyingEyeHeight", "Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "gigantamaxMove", "Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "getGigantamaxMove", "()Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "getHeight", "height", "getHitbox", "()Lnet/minecraft/world/entity/EntityDimensions;", "hitbox", "getLabels", "labels", "getLightingData", "()Lcom/cobblemon/mod/common/pokemon/lighthing/LightingData;", "lightingData", "getMaleRatio", "maleRatio", "getMoves", "()Lcom/cobblemon/mod/common/api/pokemon/moves/Learnset;", "moves", "<set-?>", "name", "Ljava/lang/String;", "getName", "getPokedex", "pokedex", "getPreEvolution", "()Lcom/cobblemon/mod/common/api/pokemon/evolution/PreEvolution;", "preEvolution", "getPrimaryType", "()Lcom/cobblemon/mod/common/api/types/ElementalType;", "primaryType", "requiredItem", "getRequiredItem", "", "requiredItems", "getRequiredItems", "requiredMove", "getRequiredMove", "getSecondaryType", "secondaryType", "getShoulderEffects", "shoulderEffects", "getShoulderMountable", "shoulderMountable", "Lcom/cobblemon/mod/common/pokemon/Species;", "getSpecies", "()Lcom/cobblemon/mod/common/pokemon/Species;", "setSpecies", "(Lcom/cobblemon/mod/common/pokemon/Species;)V", "standingEyeHeight", "swimmingEyeHeight", "", "getTypes", "()Ljava/lang/Iterable;", "types", "getWeight", "weight", "<init>", "(Ljava/lang/String;Ljava/util/Map;Ljava/lang/Float;Ljava/lang/Float;Lnet/minecraft/world/entity/EntityDimensions;Ljava/lang/Integer;Lcom/cobblemon/mod/common/api/pokemon/experience/ExperienceGroup;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/util/Map;Lcom/cobblemon/mod/common/api/types/ElementalType;Lcom/cobblemon/mod/common/api/types/ElementalType;Ljava/lang/Boolean;Ljava/util/List;Lcom/cobblemon/mod/common/api/pokemon/moves/Learnset;Ljava/util/Set;Lcom/cobblemon/mod/common/api/abilities/AbilityPool;Lcom/cobblemon/mod/common/api/drop/DropTable;Ljava/util/List;Lcom/cobblemon/mod/common/api/pokemon/evolution/PreEvolution;Ljava/lang/Float;Ljava/lang/Float;Ljava/lang/Float;Ljava/util/Set;Ljava/lang/Boolean;Ljava/util/Set;Ljava/lang/Float;Ljava/lang/Float;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Lcom/cobblemon/mod/common/api/moves/MoveTemplate;Lnet/minecraft/resources/ResourceLocation;Lcom/cobblemon/mod/common/pokemon/lighthing/LightingData;)V", "common"})
@SourceDebugExtension(value={"SMAP\nFormData.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FormData.kt\ncom/cobblemon/mod/common/pokemon/FormData\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,315:1\n1#2:316\n1855#3,2:317\n*S KotlinDebug\n*F\n+ 1 FormData.kt\ncom/cobblemon/mod/common/pokemon/FormData\n*L\n234#1:317,2\n*E\n"})
public final class FormData
implements Decodable,
Encodable,
ShowdownIdentifiable {
    @SerializedName(value="baseStats")
    @Nullable
    private Map<Stat, Integer> _baseStats;
    @SerializedName(value="maleRatio")
    @Nullable
    private final Float _maleRatio;
    @SerializedName(value="baseScale")
    @Nullable
    private Float _baseScale;
    @SerializedName(value="hitbox")
    @Nullable
    private EntityDimensions _hitbox;
    @SerializedName(value="catchRate")
    @Nullable
    private Integer _catchRate;
    @SerializedName(value="experienceGroup")
    @Nullable
    private ExperienceGroup _experienceGroup;
    @SerializedName(value="baseExperienceYield")
    @Nullable
    private Integer _baseExperienceYield;
    @SerializedName(value="_baseFriendship")
    @Nullable
    private Integer _baseFriendship;
    @SerializedName(value="evYield")
    @Nullable
    private Map<Stat, Integer> _evYield;
    @SerializedName(value="primaryType")
    @Nullable
    private ElementalType _primaryType;
    @SerializedName(value="secondaryType")
    @Nullable
    private ElementalType _secondaryType;
    @SerializedName(value="shoulderMountable")
    @Nullable
    private final Boolean _shoulderMountable;
    @SerializedName(value="shoulderEffects")
    @Nullable
    private final List<ShoulderEffect> _shoulderEffects;
    @SerializedName(value="moves")
    @Nullable
    private Learnset _moves;
    @SerializedName(value="evolutions")
    @Nullable
    private final Set<Evolution> _evolutions;
    @SerializedName(value="abilities")
    @Nullable
    private final AbilityPool _abilities;
    @SerializedName(value="drops")
    @Nullable
    private final DropTable _drops;
    @SerializedName(value="pokedex")
    @Nullable
    private List<String> _pokedex;
    @SerializedName(value="preEvolution")
    @Nullable
    private final PreEvolution _preEvolution;
    @Nullable
    private Float standingEyeHeight;
    @Nullable
    private Float swimmingEyeHeight;
    @Nullable
    private Float flyingEyeHeight;
    @SerializedName(value="labels")
    @Nullable
    private final Set<String> _labels;
    @SerializedName(value="dynamaxBlocked")
    @Nullable
    private Boolean _dynamaxBlocked;
    @SerializedName(value="eggGroups")
    @Nullable
    private final Set<EggGroup> _eggGroups;
    @SerializedName(value="height")
    @Nullable
    private Float _height;
    @SerializedName(value="weight")
    @Nullable
    private Float _weight;
    @Nullable
    private final String requiredMove;
    @Nullable
    private final String requiredItem;
    @Nullable
    private final List<String> requiredItems;
    @Nullable
    private final MoveTemplate gigantamaxMove;
    @SerializedName(value="battleTheme")
    @Nullable
    private ResourceLocation _battleTheme;
    @SerializedName(value="lightingData")
    @Nullable
    private LightingData _lightingData;
    @SerializedName(value="name")
    @NotNull
    private String name;
    @NotNull
    private List<String> aspects;
    @NotNull
    private final FormPokemonBehaviour behaviour;
    public transient Species species;

    public FormData(@NotNull String name, @Nullable Map<Stat, Integer> _baseStats, @Nullable Float _maleRatio, @Nullable Float _baseScale, @Nullable EntityDimensions _hitbox, @Nullable Integer _catchRate, @Nullable ExperienceGroup _experienceGroup, @Nullable Integer _baseExperienceYield, @Nullable Integer _baseFriendship, @Nullable Map<Stat, Integer> _evYield, @Nullable ElementalType _primaryType, @Nullable ElementalType _secondaryType, @Nullable Boolean _shoulderMountable, @Nullable List<ShoulderEffect> _shoulderEffects, @Nullable Learnset _moves, @Nullable Set<Evolution> _evolutions, @Nullable AbilityPool _abilities, @Nullable DropTable _drops, @Nullable List<String> _pokedex, @Nullable PreEvolution _preEvolution, @Nullable Float standingEyeHeight, @Nullable Float swimmingEyeHeight, @Nullable Float flyingEyeHeight, @Nullable Set<String> _labels, @Nullable Boolean _dynamaxBlocked, @Nullable Set<? extends EggGroup> _eggGroups, @Nullable Float _height, @Nullable Float _weight, @Nullable String requiredMove, @Nullable String requiredItem, @Nullable List<String> requiredItems, @Nullable MoveTemplate gigantamaxMove, @Nullable ResourceLocation _battleTheme, @Nullable LightingData _lightingData) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        this._baseStats = _baseStats;
        this._maleRatio = _maleRatio;
        this._baseScale = _baseScale;
        this._hitbox = _hitbox;
        this._catchRate = _catchRate;
        this._experienceGroup = _experienceGroup;
        this._baseExperienceYield = _baseExperienceYield;
        this._baseFriendship = _baseFriendship;
        this._evYield = _evYield;
        this._primaryType = _primaryType;
        this._secondaryType = _secondaryType;
        this._shoulderMountable = _shoulderMountable;
        this._shoulderEffects = _shoulderEffects;
        this._moves = _moves;
        this._evolutions = _evolutions;
        this._abilities = _abilities;
        this._drops = _drops;
        this._pokedex = _pokedex;
        this._preEvolution = _preEvolution;
        this.standingEyeHeight = standingEyeHeight;
        this.swimmingEyeHeight = swimmingEyeHeight;
        this.flyingEyeHeight = flyingEyeHeight;
        this._labels = _labels;
        this._dynamaxBlocked = _dynamaxBlocked;
        this._eggGroups = _eggGroups;
        this._height = _height;
        this._weight = _weight;
        this.requiredMove = requiredMove;
        this.requiredItem = requiredItem;
        this.requiredItems = requiredItems;
        this.gigantamaxMove = gigantamaxMove;
        this._battleTheme = _battleTheme;
        this._lightingData = _lightingData;
        this.name = name;
        this.aspects = new ArrayList();
        this.behaviour = new FormPokemonBehaviour();
    }

    public /* synthetic */ FormData(String string, Map map, Float f, Float f2, EntityDimensions entityDimensions, Integer n, ExperienceGroup experienceGroup, Integer n2, Integer n3, Map map2, ElementalType elementalType, ElementalType elementalType2, Boolean bl, List list, Learnset learnset, Set set2, AbilityPool abilityPool, DropTable dropTable, List list2, PreEvolution preEvolution, Float f3, Float f4, Float f5, Set set3, Boolean bl2, Set set4, Float f6, Float f7, String string2, String string3, List list3, MoveTemplate moveTemplate, ResourceLocation resourceLocation, LightingData lightingData, int n4, int n5, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n4 & 1) != 0) {
            string = "Normal";
        }
        if ((n4 & 2) != 0) {
            map = null;
        }
        if ((n4 & 4) != 0) {
            f = null;
        }
        if ((n4 & 8) != 0) {
            f2 = null;
        }
        if ((n4 & 0x10) != 0) {
            entityDimensions = null;
        }
        if ((n4 & 0x20) != 0) {
            n = null;
        }
        if ((n4 & 0x40) != 0) {
            experienceGroup = null;
        }
        if ((n4 & 0x80) != 0) {
            n2 = null;
        }
        if ((n4 & 0x100) != 0) {
            n3 = null;
        }
        if ((n4 & 0x200) != 0) {
            map2 = null;
        }
        if ((n4 & 0x400) != 0) {
            elementalType = null;
        }
        if ((n4 & 0x800) != 0) {
            elementalType2 = null;
        }
        if ((n4 & 0x1000) != 0) {
            bl = null;
        }
        if ((n4 & 0x2000) != 0) {
            list = null;
        }
        if ((n4 & 0x4000) != 0) {
            learnset = null;
        }
        if ((n4 & 0x8000) != 0) {
            set2 = null;
        }
        if ((n4 & 0x10000) != 0) {
            abilityPool = null;
        }
        if ((n4 & 0x20000) != 0) {
            dropTable = null;
        }
        if ((n4 & 0x40000) != 0) {
            list2 = null;
        }
        if ((n4 & 0x80000) != 0) {
            preEvolution = null;
        }
        if ((n4 & 0x100000) != 0) {
            f3 = null;
        }
        if ((n4 & 0x200000) != 0) {
            f4 = null;
        }
        if ((n4 & 0x400000) != 0) {
            f5 = null;
        }
        if ((n4 & 0x800000) != 0) {
            set3 = null;
        }
        if ((n4 & 0x1000000) != 0) {
            bl2 = null;
        }
        if ((n4 & 0x2000000) != 0) {
            set4 = null;
        }
        if ((n4 & 0x4000000) != 0) {
            f6 = null;
        }
        if ((n4 & 0x8000000) != 0) {
            f7 = null;
        }
        if ((n4 & 0x10000000) != 0) {
            string2 = null;
        }
        if ((n4 & 0x20000000) != 0) {
            string3 = null;
        }
        if ((n4 & 0x40000000) != 0) {
            list3 = null;
        }
        if ((n4 & Integer.MIN_VALUE) != 0) {
            moveTemplate = null;
        }
        if ((n5 & 1) != 0) {
            resourceLocation = null;
        }
        if ((n5 & 2) != 0) {
            lightingData = null;
        }
        this(string, map, f, f2, entityDimensions, n, experienceGroup, n2, n3, map2, elementalType, elementalType2, bl, list, learnset, set2, abilityPool, dropTable, list2, preEvolution, f3, f4, f5, set3, bl2, set4, f6, f7, string2, string3, list3, moveTemplate, resourceLocation, lightingData);
    }

    @Nullable
    public final Map<Stat, Integer> get_baseStats$common() {
        return this._baseStats;
    }

    public final void set_baseStats$common(@Nullable Map<Stat, Integer> map) {
        this._baseStats = map;
    }

    @Nullable
    public final String getRequiredMove() {
        return this.requiredMove;
    }

    @Nullable
    public final String getRequiredItem() {
        return this.requiredItem;
    }

    @Nullable
    public final List<String> getRequiredItems() {
        return this.requiredItems;
    }

    @Nullable
    public final MoveTemplate getGigantamaxMove() {
        return this.gigantamaxMove;
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    @NotNull
    public final Map<Stat, Integer> getBaseStats() {
        Map map = this._baseStats;
        if (map == null) {
            map = this.getSpecies().getBaseStats();
        }
        return map;
    }

    public final float getMaleRatio() {
        Float f = this._maleRatio;
        return f != null ? f.floatValue() : this.getSpecies().getMaleRatio();
    }

    public final float getBaseScale() {
        Float f = this._baseScale;
        return f != null ? f.floatValue() : this.getSpecies().getBaseScale();
    }

    @NotNull
    public final EntityDimensions getHitbox() {
        EntityDimensions entityDimensions = this._hitbox;
        if (entityDimensions == null) {
            entityDimensions = this.getSpecies().getHitbox();
        }
        return entityDimensions;
    }

    public final int getCatchRate() {
        Integer n = this._catchRate;
        return n != null ? n.intValue() : this.getSpecies().getCatchRate();
    }

    @NotNull
    public final ExperienceGroup getExperienceGroup() {
        ExperienceGroup experienceGroup = this._experienceGroup;
        if (experienceGroup == null) {
            experienceGroup = this.getSpecies().getExperienceGroup();
        }
        return experienceGroup;
    }

    public final int getBaseExperienceYield() {
        Integer n = this._baseExperienceYield;
        return n != null ? n.intValue() : this.getSpecies().getBaseExperienceYield();
    }

    public final int getBaseFriendship() {
        Integer n = this._baseFriendship;
        return n != null ? n.intValue() : this.getSpecies().getBaseFriendship();
    }

    @NotNull
    public final Map<Stat, Integer> getEvYield() {
        Map map = this._evYield;
        if (map == null) {
            map = this.getSpecies().getEvYield();
        }
        return map;
    }

    @NotNull
    public final ElementalType getPrimaryType() {
        ElementalType elementalType = this._primaryType;
        if (elementalType == null) {
            elementalType = this.getSpecies().getPrimaryType();
        }
        return elementalType;
    }

    @Nullable
    public final ElementalType getSecondaryType() {
        return this._secondaryType == null && this._primaryType == null ? this.getSpecies().getSecondaryType() : this._secondaryType;
    }

    public final boolean getShoulderMountable() {
        Boolean bl = this._shoulderMountable;
        return bl != null ? bl.booleanValue() : this.getSpecies().getShoulderMountable();
    }

    @NotNull
    public final List<ShoulderEffect> getShoulderEffects() {
        List<ShoulderEffect> list = this._shoulderEffects;
        if (list == null) {
            list = this.getSpecies().getShoulderEffects();
        }
        return list;
    }

    @NotNull
    public final List<String> getPokedex() {
        List<String> list = this._pokedex;
        if (list == null) {
            list = this.getSpecies().getPokedex();
        }
        return list;
    }

    @NotNull
    public final Learnset getMoves() {
        Learnset learnset = this._moves;
        if (learnset == null) {
            learnset = this.getSpecies().getMoves();
        }
        return learnset;
    }

    /*
     * Enabled aggressive block sorting
     */
    @NotNull
    public final Iterable<ElementalType> getTypes() {
        Iterable iterable;
        Object object = this.getSecondaryType();
        if (object != null) {
            ElementalType it = object;
            boolean bl = false;
            Object[] objectArray = new ElementalType[]{this.getPrimaryType(), it};
            List list = CollectionsKt.listOf((Object[])objectArray);
            object = list;
            if (list != null) {
                iterable = (Iterable)object;
                return iterable;
            }
        }
        iterable = CollectionsKt.listOf((Object)this.getPrimaryType());
        return iterable;
    }

    @NotNull
    public final AbilityPool getAbilities() {
        AbilityPool abilityPool = this._abilities;
        if (abilityPool == null) {
            abilityPool = this.getSpecies().getAbilities();
        }
        return abilityPool;
    }

    @NotNull
    public final DropTable getDrops() {
        DropTable dropTable = this._drops;
        if (dropTable == null) {
            dropTable = this.getSpecies().getDrops();
        }
        return dropTable;
    }

    @NotNull
    public final List<String> getAspects() {
        return this.aspects;
    }

    public final void setAspects(@NotNull List<String> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.aspects = list;
    }

    @Nullable
    public final PreEvolution getPreEvolution() {
        PreEvolution preEvolution = this._preEvolution;
        if (preEvolution == null) {
            preEvolution = this.getSpecies().getPreEvolution();
        }
        return preEvolution;
    }

    @NotNull
    public final FormPokemonBehaviour getBehaviour() {
        return this.behaviour;
    }

    public final boolean getDynamaxBlocked() {
        Boolean bl = this._dynamaxBlocked;
        return bl != null ? bl.booleanValue() : this.getSpecies().getDynamaxBlocked();
    }

    @NotNull
    public final Set<EggGroup> getEggGroups() {
        Set set2 = this._eggGroups;
        if (set2 == null) {
            set2 = this.getSpecies().getEggGroups();
        }
        return set2;
    }

    public final float getHeight() {
        Float f = this._height;
        return f != null ? f.floatValue() : this.getSpecies().getHeight();
    }

    public final float getWeight() {
        Float f = this._weight;
        return f != null ? f.floatValue() : this.getSpecies().getWeight();
    }

    @NotNull
    public final Set<String> getLabels() {
        Set set2 = this._labels;
        if (set2 == null) {
            set2 = this.getSpecies().getLabels();
        }
        return set2;
    }

    @NotNull
    public final Set<Evolution> getEvolutions() {
        Set set2 = this._evolutions;
        if (set2 == null) {
            set2 = new LinkedHashSet();
        }
        return set2;
    }

    @NotNull
    public final ResourceLocation getBattleTheme() {
        ResourceLocation resourceLocation = this._battleTheme;
        if (resourceLocation == null) {
            resourceLocation = this.getSpecies().getBattleTheme();
        }
        return resourceLocation;
    }

    @Nullable
    public final LightingData getLightingData() {
        if (Intrinsics.areEqual((Object)this.getSpecies().getStandardForm(), (Object)this)) {
            return this.getSpecies().getLightingData();
        }
        return this._lightingData;
    }

    public final float eyeHeight(@NotNull PokemonEntity entity2) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        Float f = this.resolveEyeHeight(entity2);
        if (f == null) {
            return this.getSpecies().eyeHeight(entity2);
        }
        float multiplier = f.floatValue();
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

    @NotNull
    public final Species getSpecies() {
        Species species = this.species;
        if (species != null) {
            return species;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"species");
        return null;
    }

    public final void setSpecies(@NotNull Species species) {
        Intrinsics.checkNotNullParameter((Object)species, (String)"<set-?>");
        this.species = species;
    }

    @NotNull
    public final FormData initialize(@NotNull Species species) {
        block2: {
            Intrinsics.checkNotNullParameter((Object)species, (String)"species");
            this.setSpecies(species);
            this.behaviour.setParent(species.getBehaviour());
            Cobblemon.INSTANCE.getStatProvider().provide(this);
            PreEvolution preEvolution = this.getPreEvolution();
            if (preEvolution != null) {
                preEvolution.getSpecies();
            }
            PreEvolution preEvolution2 = this.getPreEvolution();
            if (preEvolution2 != null) {
                preEvolution2.getForm();
            }
            this.getEvolutions().size();
            LightingData lightingData = this._lightingData;
            if (lightingData == null) break block2;
            LightingData it = lightingData;
            boolean bl = false;
            this._lightingData = LightingData.copy$default(it, RangesKt.coerceIn((int)it.getLightLevel(), (int)0, (int)15), null, 2, null);
        }
        return this;
    }

    public final void resolveEvolutionMoves$common() {
        Iterable $this$forEach$iv = this.getEvolutions();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Evolution evolution = (Evolution)element$iv;
            boolean bl = false;
            if (!(!((Collection)evolution.getLearnableMoves()).isEmpty()) || evolution.getResult().getSpecies() == null) continue;
            Pokemon pokemon = evolution.getResult().create();
            CollectionsKt.addAll((Collection)pokemon.getForm().getMoves().getEvolutionMoves(), (Iterable)evolution.getLearnableMoves());
        }
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof FormData && Intrinsics.areEqual((Object)((FormData)other).showdownId(), (Object)this.showdownId());
    }

    public int hashCode() {
        return this.showdownId().hashCode();
    }

    @Override
    public void encode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130070_(this.name);
        buffer.m_236828_((Collection)this.aspects, FormData::encode$lambda$3);
        buffer.m_236821_(this._baseStats, FormData::encode$lambda$6);
        buffer.m_236821_((Object)this._primaryType, FormData::encode$lambda$7);
        buffer.m_236821_((Object)this._secondaryType, FormData::encode$lambda$8);
        buffer.m_236821_((Object)this._experienceGroup, FormData::encode$lambda$9);
        buffer.m_236821_((Object)this._height, FormData::encode$lambda$10);
        buffer.m_236821_((Object)this._weight, FormData::encode$lambda$11);
        buffer.m_236821_((Object)this._baseScale, FormData::encode$lambda$12);
        buffer.m_236821_((Object)this._hitbox, FormData::encode$lambda$13);
        buffer.m_236821_((Object)this._moves, FormData::encode$lambda$14);
        buffer.m_236821_(this._pokedex, FormData::encode$lambda$16);
        buffer.m_236821_((Object)this.getLightingData(), FormData::encode$lambda$17);
    }

    @Override
    public void decode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        String string = buffer.m_130277_();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"buffer.readString()");
        this.name = string;
        List list = buffer.m_236845_(arg_0 -> FormData.decode$lambda$18(buffer, arg_0));
        Intrinsics.checkNotNullExpressionValue((Object)list, (String)"buffer.readList { buffer.readString() }");
        this.aspects = CollectionsKt.toMutableList((Collection)list);
        buffer.m_236868_(arg_0 -> FormData.decode$lambda$21(this, arg_0));
        this._primaryType = (ElementalType)buffer.m_236868_(FormData::decode$lambda$22);
        this._secondaryType = (ElementalType)buffer.m_236868_(FormData::decode$lambda$23);
        this._experienceGroup = (ExperienceGroup)buffer.m_236868_(FormData::decode$lambda$24);
        this._height = (Float)buffer.m_236868_(FormData::decode$lambda$25);
        this._weight = (Float)buffer.m_236868_(FormData::decode$lambda$26);
        this._baseScale = (Float)buffer.m_236868_(FormData::decode$lambda$27);
        this._hitbox = (EntityDimensions)buffer.m_236868_(FormData::decode$lambda$28);
        this._moves = (Learnset)buffer.m_236868_(FormData::decode$lambda$30);
        this._pokedex = (List)buffer.m_236868_(FormData::decode$lambda$32);
        this._lightingData = (LightingData)buffer.m_236868_(FormData::decode$lambda$33);
    }

    @Override
    @NotNull
    public String showdownId() {
        return this.getSpecies().showdownId() + this.formOnlyShowdownId();
    }

    @NotNull
    public final String formOnlyShowdownId() {
        Regex regex = ShowdownIdentifiable.Companion.getREGEX$common();
        String string = this.name.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
        return regex.replace((CharSequence)string, "");
    }

    private static final void encode$lambda$3(FriendlyByteBuf pb, String aspect) {
        pb.m_130070_(aspect);
    }

    private static final void encode$lambda$6$lambda$4(FriendlyByteBuf keyBuffer, Stat stat) {
        StatProvider statProvider = Cobblemon.INSTANCE.getStatProvider();
        Intrinsics.checkNotNullExpressionValue((Object)keyBuffer, (String)"keyBuffer");
        Intrinsics.checkNotNullExpressionValue((Object)stat, (String)"stat");
        statProvider.encode(keyBuffer, stat);
    }

    private static final void encode$lambda$6$lambda$5(FriendlyByteBuf valueBuffer, Integer value2) {
        Intrinsics.checkNotNullExpressionValue((Object)valueBuffer, (String)"valueBuffer");
        ByteBuf byteBuf = (ByteBuf)valueBuffer;
        Intrinsics.checkNotNullExpressionValue((Object)value2, (String)"value");
        NetExtensionsKt.writeSizedInt(byteBuf, IntSize.U_SHORT, value2);
    }

    private static final void encode$lambda$6(FriendlyByteBuf statsBuffer, Map map) {
        statsBuffer.m_236831_(map, FormData::encode$lambda$6$lambda$4, FormData::encode$lambda$6$lambda$5);
    }

    private static final void encode$lambda$7(FriendlyByteBuf pb, ElementalType type) {
        pb.m_130070_(type.getName());
    }

    private static final void encode$lambda$8(FriendlyByteBuf pb, ElementalType type) {
        pb.m_130070_(type.getName());
    }

    private static final void encode$lambda$9(FriendlyByteBuf pb, ExperienceGroup value2) {
        pb.m_130070_(value2.getName());
    }

    private static final void encode$lambda$10(FriendlyByteBuf pb, Float height) {
        Intrinsics.checkNotNullExpressionValue((Object)height, (String)"height");
        pb.writeFloat(height.floatValue());
    }

    private static final void encode$lambda$11(FriendlyByteBuf pb, Float weight) {
        Intrinsics.checkNotNullExpressionValue((Object)weight, (String)"weight");
        pb.writeFloat(weight.floatValue());
    }

    private static final void encode$lambda$12(FriendlyByteBuf buf, Float fl) {
        Intrinsics.checkNotNullExpressionValue((Object)fl, (String)"fl");
        buf.writeFloat(fl.floatValue());
    }

    private static final void encode$lambda$13(FriendlyByteBuf pb, EntityDimensions hitbox) {
        pb.writeFloat(hitbox.f_20377_);
        pb.writeFloat(hitbox.f_20378_);
        pb.writeBoolean(hitbox.f_20379_);
    }

    private static final void encode$lambda$14(FriendlyByteBuf buf, Learnset moves) {
        Intrinsics.checkNotNullExpressionValue((Object)buf, (String)"buf");
        moves.encode(buf);
    }

    private static final void encode$lambda$16$lambda$15(FriendlyByteBuf pb2, String line) {
        pb2.m_130070_(line);
    }

    private static final void encode$lambda$16(FriendlyByteBuf pb1, List pokedex) {
        pb1.m_236828_((Collection)pokedex, FormData::encode$lambda$16$lambda$15);
    }

    private static final void encode$lambda$17(FriendlyByteBuf pb, LightingData data) {
        pb.writeInt(data.getLightLevel());
        pb.m_130068_((Enum)data.getLiquidGlowMode());
    }

    private static final String decode$lambda$18(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        return $buffer.m_130277_();
    }

    private static final Stat decode$lambda$21$lambda$19(FriendlyByteBuf keyBuffer) {
        StatProvider statProvider = Cobblemon.INSTANCE.getStatProvider();
        Intrinsics.checkNotNullExpressionValue((Object)keyBuffer, (String)"keyBuffer");
        return statProvider.decode(keyBuffer);
    }

    private static final Integer decode$lambda$21$lambda$20(FriendlyByteBuf valueBuffer) {
        Intrinsics.checkNotNullExpressionValue((Object)valueBuffer, (String)"valueBuffer");
        return NetExtensionsKt.readSizedInt((ByteBuf)valueBuffer, IntSize.U_SHORT);
    }

    private static final Unit decode$lambda$21(FormData this$0, FriendlyByteBuf mapBuffer) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        this$0._baseStats = mapBuffer.m_236847_(FormData::decode$lambda$21$lambda$19, FormData::decode$lambda$21$lambda$20);
        return Unit.INSTANCE;
    }

    private static final ElementalType decode$lambda$22(FriendlyByteBuf pb) {
        String string = pb.m_130277_();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"pb.readString()");
        return ElementalTypes.INSTANCE.get(string);
    }

    private static final ElementalType decode$lambda$23(FriendlyByteBuf pb) {
        String string = pb.m_130277_();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"pb.readString()");
        return ElementalTypes.INSTANCE.get(string);
    }

    private static final ExperienceGroup decode$lambda$24(FriendlyByteBuf pb) {
        String string = pb.m_130277_();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"pb.readString()");
        return ExperienceGroups.INSTANCE.findByName(string);
    }

    private static final Float decode$lambda$25(FriendlyByteBuf pb) {
        return Float.valueOf(pb.readFloat());
    }

    private static final Float decode$lambda$26(FriendlyByteBuf pb) {
        return Float.valueOf(pb.readFloat());
    }

    private static final Float decode$lambda$27(FriendlyByteBuf pb) {
        return Float.valueOf(pb.readFloat());
    }

    private static final EntityDimensions decode$lambda$28(FriendlyByteBuf pb) {
        return new EntityDimensions(pb.readFloat(), pb.readFloat(), pb.readBoolean());
    }

    private static final Learnset decode$lambda$30(FriendlyByteBuf pb) {
        Learnset learnset;
        Learnset $this$decode_u24lambda_u2430_u24lambda_u2429 = learnset = new Learnset();
        boolean bl = false;
        Intrinsics.checkNotNullExpressionValue((Object)pb, (String)"pb");
        $this$decode_u24lambda_u2430_u24lambda_u2429.decode(pb);
        return learnset;
    }

    private static final String decode$lambda$32$lambda$31(FriendlyByteBuf it) {
        return it.m_130277_();
    }

    private static final List decode$lambda$32(FriendlyByteBuf pb) {
        return pb.m_236845_(FormData::decode$lambda$32$lambda$31);
    }

    private static final LightingData decode$lambda$33(FriendlyByteBuf pb) {
        int n = pb.readInt();
        Enum enum_ = pb.m_130066_(LightingData.LiquidGlowMode.class);
        Intrinsics.checkNotNullExpressionValue((Object)enum_, (String)"pb.readEnumConstant(Ligh\u2026quidGlowMode::class.java)");
        return new LightingData(n, (LightingData.LiquidGlowMode)enum_);
    }

    public FormData() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, -1, 3, null);
    }
}

