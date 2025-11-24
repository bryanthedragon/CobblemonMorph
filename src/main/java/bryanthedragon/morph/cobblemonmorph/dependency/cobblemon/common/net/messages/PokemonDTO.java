/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.Unpooled
 *  kotlin.Metadata
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.Abilities;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.AbilityTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.BenchedMoves;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Move;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveSet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Decodable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Encodable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.PokeBalls;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.Natures;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeature;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatureProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatures;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SynchronizedSpeciesFeature;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SynchronizedSpeciesFeatureProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Status;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.tera.TeraType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.tera.TeraTypes;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.PokeBall;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.EVs;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Gender;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.IVs;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Nature;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.OriginalTrainerType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate.PokemonState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatusContainer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u00a2\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\"\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0015\u0018\u00002\u00020\u00012\u00020\u0002B\u000b\b\u0016\u00a2\u0006\u0006\b\u00b5\u0001\u0010\u00b6\u0001B\u001d\b\u0016\u0012\u0007\u0010\u00b7\u0001\u001a\u00020\u0003\u0012\u0007\u0010\u00ab\u0001\u001a\u00020Q\u00a2\u0006\u0006\b\u00b5\u0001\u0010\u00b8\u0001J\r\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0017\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ\u0017\u0010\u000b\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\u000b\u0010\nR\"\u0010\r\u001a\u00020\f8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R(\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\f0\u00138\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\"\u0010\u001b\u001a\u00020\u001a8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\"\u0010\"\u001a\u00020!8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R\"\u0010)\u001a\u00020(8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b)\u0010*\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R\"\u0010/\u001a\u00020(8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b/\u0010*\u001a\u0004\b0\u0010,\"\u0004\b1\u0010.R\"\u00102\u001a\u00020\u00068\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b2\u00103\u001a\u0004\b4\u00105\"\u0004\b6\u0010\nR\"\u00108\u001a\u0002078\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b8\u00109\u001a\u0004\b:\u0010;\"\u0004\b<\u0010=R\"\u0010>\u001a\u00020(8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b>\u0010*\u001a\u0004\b?\u0010,\"\u0004\b@\u0010.R\"\u0010A\u001a\u00020\u00068\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bA\u00103\u001a\u0004\bB\u00105\"\u0004\bC\u0010\nR\"\u0010D\u001a\u00020\f8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bD\u0010\u000e\u001a\u0004\bE\u0010\u0010\"\u0004\bF\u0010\u0012R\"\u0010G\u001a\u00020(8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bG\u0010*\u001a\u0004\bH\u0010,\"\u0004\bI\u0010.R\"\u0010K\u001a\u00020J8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bK\u0010L\u001a\u0004\bM\u0010N\"\u0004\bO\u0010PR\"\u0010R\u001a\u00020Q8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bR\u0010S\u001a\u0004\bT\u0010U\"\u0004\bV\u0010WR\"\u0010Y\u001a\u00020X8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bY\u0010Z\u001a\u0004\b[\u0010\\\"\u0004\b]\u0010^R\"\u0010`\u001a\u00020_8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b`\u0010a\u001a\u0004\bb\u0010c\"\u0004\bd\u0010eR\"\u0010f\u001a\u00020(8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bf\u0010*\u001a\u0004\bg\u0010,\"\u0004\bh\u0010.R$\u0010i\u001a\u0004\u0018\u00010!8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bi\u0010#\u001a\u0004\bj\u0010%\"\u0004\bk\u0010'R\"\u0010m\u001a\u00020l8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bm\u0010n\u001a\u0004\bo\u0010p\"\u0004\bq\u0010rR\"\u0010s\u001a\u00020!8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bs\u0010#\u001a\u0004\bt\u0010%\"\u0004\bu\u0010'R$\u0010w\u001a\u0004\u0018\u00010v8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bw\u0010x\u001a\u0004\by\u0010z\"\u0004\b{\u0010|R$\u0010}\u001a\u0004\u0018\u00010\f8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b}\u0010\u000e\u001a\u0004\b~\u0010\u0010\"\u0004\b\u007f\u0010\u0012R(\u0010\u0080\u0001\u001a\u0004\u0018\u00010\f8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0015\n\u0005\b\u0080\u0001\u0010\u000e\u001a\u0005\b\u0081\u0001\u0010\u0010\"\u0005\b\u0082\u0001\u0010\u0012R*\u0010\u0084\u0001\u001a\u00030\u0083\u00018\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0018\n\u0006\b\u0084\u0001\u0010\u0085\u0001\u001a\u0006\b\u0086\u0001\u0010\u0087\u0001\"\u0006\b\u0088\u0001\u0010\u0089\u0001R*\u0010\u008b\u0001\u001a\u00030\u008a\u00018\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0018\n\u0006\b\u008b\u0001\u0010\u008c\u0001\u001a\u0006\b\u008d\u0001\u0010\u008e\u0001\"\u0006\b\u008f\u0001\u0010\u0090\u0001R&\u0010\u0091\u0001\u001a\u00020Q8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0015\n\u0005\b\u0091\u0001\u0010S\u001a\u0005\b\u0092\u0001\u0010U\"\u0005\b\u0093\u0001\u0010WR&\u0010\u0094\u0001\u001a\u00020!8\u0006@\u0006X\u0086.\u00a2\u0006\u0015\n\u0005\b\u0094\u0001\u0010#\u001a\u0005\b\u0095\u0001\u0010%\"\u0005\b\u0096\u0001\u0010'R*\u0010\u0098\u0001\u001a\u00030\u0097\u00018\u0006@\u0006X\u0086.\u00a2\u0006\u0018\n\u0006\b\u0098\u0001\u0010\u0099\u0001\u001a\u0006\b\u009a\u0001\u0010\u009b\u0001\"\u0006\b\u009c\u0001\u0010\u009d\u0001R(\u0010\u009e\u0001\u001a\u0004\u0018\u00010!8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0015\n\u0005\b\u009e\u0001\u0010#\u001a\u0005\b\u009f\u0001\u0010%\"\u0005\b\u00a0\u0001\u0010'R&\u0010\u00a1\u0001\u001a\u00020\f8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0015\n\u0005\b\u00a1\u0001\u0010\u000e\u001a\u0005\b\u00a2\u0001\u0010\u0010\"\u0005\b\u00a3\u0001\u0010\u0012R,\u0010\u00a5\u0001\u001a\u0005\u0018\u00010\u00a4\u00018\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0018\n\u0006\b\u00a5\u0001\u0010\u00a6\u0001\u001a\u0006\b\u00a7\u0001\u0010\u00a8\u0001\"\u0006\b\u00a9\u0001\u0010\u00aa\u0001R&\u0010\u00ab\u0001\u001a\u00020Q8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0015\n\u0005\b\u00ab\u0001\u0010S\u001a\u0005\b\u00ac\u0001\u0010U\"\u0005\b\u00ad\u0001\u0010WR&\u0010\u00ae\u0001\u001a\u00020Q8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0015\n\u0005\b\u00ae\u0001\u0010S\u001a\u0005\b\u00af\u0001\u0010U\"\u0005\b\u00b0\u0001\u0010WR4\u0010\u00b2\u0001\u001a\r \u00b1\u0001*\u0005\u0018\u00010\u00a4\u00010\u00a4\u00018\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0018\n\u0006\b\u00b2\u0001\u0010\u00a6\u0001\u001a\u0006\b\u00b3\u0001\u0010\u00a8\u0001\"\u0006\b\u00b4\u0001\u0010\u00aa\u0001\u00a8\u0006\u00b9\u0001"}, d2={"Lcom/cobblemon/mod/common/net/messages/PokemonDTO;", "Lcom/cobblemon/mod/common/api/net/Encodable;", "Lcom/cobblemon/mod/common/api/net/Decodable;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "create", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "encode", "", "ability", "Ljava/lang/String;", "getAbility", "()Ljava/lang/String;", "setAbility", "(Ljava/lang/String;)V", "", "aspects", "Ljava/util/Set;", "getAspects", "()Ljava/util/Set;", "setAspects", "(Ljava/util/Set;)V", "Lcom/cobblemon/mod/common/api/moves/BenchedMoves;", "benchedMoves", "Lcom/cobblemon/mod/common/api/moves/BenchedMoves;", "getBenchedMoves", "()Lcom/cobblemon/mod/common/api/moves/BenchedMoves;", "setBenchedMoves", "(Lcom/cobblemon/mod/common/api/moves/BenchedMoves;)V", "Lnet/minecraft/resources/ResourceLocation;", "caughtBall", "Lnet/minecraft/resources/ResourceLocation;", "getCaughtBall", "()Lnet/minecraft/resources/ResourceLocation;", "setCaughtBall", "(Lnet/minecraft/resources/ResourceLocation;)V", "", "currentHealth", "I", "getCurrentHealth", "()I", "setCurrentHealth", "(I)V", "dmaxLevel", "getDmaxLevel", "setDmaxLevel", "evolutionBuffer", "Lnet/minecraft/network/FriendlyByteBuf;", "getEvolutionBuffer", "()Lnet/minecraft/network/FriendlyByteBuf;", "setEvolutionBuffer", "Lcom/cobblemon/mod/common/pokemon/EVs;", "evs", "Lcom/cobblemon/mod/common/pokemon/EVs;", "getEvs", "()Lcom/cobblemon/mod/common/pokemon/EVs;", "setEvs", "(Lcom/cobblemon/mod/common/pokemon/EVs;)V", "experience", "getExperience", "setExperience", "featuresBuffer", "getFeaturesBuffer", "setFeaturesBuffer", "form", "getForm", "setForm", "friendship", "getFriendship", "setFriendship", "Lcom/cobblemon/mod/common/pokemon/Gender;", "gender", "Lcom/cobblemon/mod/common/pokemon/Gender;", "getGender", "()Lcom/cobblemon/mod/common/pokemon/Gender;", "setGender", "(Lcom/cobblemon/mod/common/pokemon/Gender;)V", "", "gmaxFactor", "Z", "getGmaxFactor", "()Z", "setGmaxFactor", "(Z)V", "Lnet/minecraft/world/item/ItemStack;", "heldItem", "Lnet/minecraft/world/item/ItemStack;", "getHeldItem", "()Lnet/minecraft/world/item/ItemStack;", "setHeldItem", "(Lnet/minecraft/world/item/ItemStack;)V", "Lcom/cobblemon/mod/common/pokemon/IVs;", "ivs", "Lcom/cobblemon/mod/common/pokemon/IVs;", "getIvs", "()Lcom/cobblemon/mod/common/pokemon/IVs;", "setIvs", "(Lcom/cobblemon/mod/common/pokemon/IVs;)V", "level", "getLevel", "setLevel", "mintNature", "getMintNature", "setMintNature", "Lcom/cobblemon/mod/common/api/moves/MoveSet;", "moveSet", "Lcom/cobblemon/mod/common/api/moves/MoveSet;", "getMoveSet", "()Lcom/cobblemon/mod/common/api/moves/MoveSet;", "setMoveSet", "(Lcom/cobblemon/mod/common/api/moves/MoveSet;)V", "nature", "getNature", "setNature", "Lnet/minecraft/network/chat/MutableComponent;", "nickname", "Lnet/minecraft/network/chat/MutableComponent;", "getNickname", "()Lnet/minecraft/network/chat/MutableComponent;", "setNickname", "(Lnet/minecraft/network/chat/MutableComponent;)V", "originalTrainer", "getOriginalTrainer", "setOriginalTrainer", "originalTrainerName", "getOriginalTrainerName", "setOriginalTrainerName", "Lcom/cobblemon/mod/common/pokemon/OriginalTrainerType;", "originalTrainerType", "Lcom/cobblemon/mod/common/pokemon/OriginalTrainerType;", "getOriginalTrainerType", "()Lcom/cobblemon/mod/common/pokemon/OriginalTrainerType;", "setOriginalTrainerType", "(Lcom/cobblemon/mod/common/pokemon/OriginalTrainerType;)V", "", "scaleModifier", "F", "getScaleModifier", "()F", "setScaleModifier", "(F)V", "shiny", "getShiny", "setShiny", "species", "getSpecies", "setSpecies", "Lcom/cobblemon/mod/common/pokemon/activestate/PokemonState;", "state", "Lcom/cobblemon/mod/common/pokemon/activestate/PokemonState;", "getState", "()Lcom/cobblemon/mod/common/pokemon/activestate/PokemonState;", "setState", "(Lcom/cobblemon/mod/common/pokemon/activestate/PokemonState;)V", "status", "getStatus", "setStatus", "teraType", "getTeraType", "setTeraType", "Ljava/util/UUID;", "tetheringId", "Ljava/util/UUID;", "getTetheringId", "()Ljava/util/UUID;", "setTetheringId", "(Ljava/util/UUID;)V", "toClient", "getToClient", "setToClient", "tradeable", "getTradeable", "setTradeable", "kotlin.jvm.PlatformType", "uuid", "getUuid", "setUuid", "<init>", "()V", "pokemon", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Z)V", "common"})
@SourceDebugExtension(value={"SMAP\nPokemonDTO.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonDTO.kt\ncom/cobblemon/mod/common/net/messages/PokemonDTO\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,299:1\n800#2,11:300\n766#2:311\n857#2,2:312\n1855#2,2:314\n1855#2,2:317\n1855#2,2:319\n800#2,11:321\n1#3:316\n*S KotlinDebug\n*F\n+ 1 PokemonDTO.kt\ncom/cobblemon/mod/common/net/messages/PokemonDTO\n*L\n119#1:300,11\n120#1:311\n120#1:312,2\n153#1:314,2\n230#1:317,2\n233#1:319,2\n271#1:321,11\n*E\n"})
public final class PokemonDTO
implements Encodable,
Decodable {
    private boolean toClient;
    private UUID uuid;
    public ResourceLocation species;
    @Nullable
    private MutableComponent nickname;
    @NotNull
    private String form;
    private int level;
    private int experience;
    private int friendship;
    private int currentHealth;
    @NotNull
    private Gender gender;
    @NotNull
    private IVs ivs;
    @NotNull
    private EVs evs;
    @NotNull
    private MoveSet moveSet;
    private float scaleModifier;
    @NotNull
    private String ability;
    private boolean shiny;
    @Nullable
    private ResourceLocation status;
    public PokemonState state;
    public ResourceLocation caughtBall;
    @NotNull
    private BenchedMoves benchedMoves;
    @NotNull
    private Set<String> aspects;
    public FriendlyByteBuf evolutionBuffer;
    public ResourceLocation nature;
    @Nullable
    private ResourceLocation mintNature;
    @NotNull
    private ItemStack heldItem;
    @Nullable
    private UUID tetheringId;
    @NotNull
    private String teraType;
    private int dmaxLevel;
    private boolean gmaxFactor;
    private boolean tradeable;
    public FriendlyByteBuf featuresBuffer;
    @NotNull
    private OriginalTrainerType originalTrainerType;
    @Nullable
    private String originalTrainer;
    @Nullable
    private String originalTrainerName;

    public final boolean getToClient() {
        return this.toClient;
    }

    public final void setToClient(boolean bl) {
        this.toClient = bl;
    }

    public final UUID getUuid() {
        return this.uuid;
    }

    public final void setUuid(UUID uUID) {
        this.uuid = uUID;
    }

    @NotNull
    public final ResourceLocation getSpecies() {
        ResourceLocation resourceLocation = this.species;
        if (resourceLocation != null) {
            return resourceLocation;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"species");
        return null;
    }

    public final void setSpecies(@NotNull ResourceLocation resourceLocation) {
        Intrinsics.checkNotNullParameter((Object)resourceLocation, (String)"<set-?>");
        this.species = resourceLocation;
    }

    @Nullable
    public final MutableComponent getNickname() {
        return this.nickname;
    }

    public final void setNickname(@Nullable MutableComponent mutableComponent) {
        this.nickname = mutableComponent;
    }

    @NotNull
    public final String getForm() {
        return this.form;
    }

    public final void setForm(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.form = string;
    }

    public final int getLevel() {
        return this.level;
    }

    public final void setLevel(int n) {
        this.level = n;
    }

    public final int getExperience() {
        return this.experience;
    }

    public final void setExperience(int n) {
        this.experience = n;
    }

    public final int getFriendship() {
        return this.friendship;
    }

    public final void setFriendship(int n) {
        this.friendship = n;
    }

    public final int getCurrentHealth() {
        return this.currentHealth;
    }

    public final void setCurrentHealth(int n) {
        this.currentHealth = n;
    }

    @NotNull
    public final Gender getGender() {
        return this.gender;
    }

    public final void setGender(@NotNull Gender gender) {
        Intrinsics.checkNotNullParameter((Object)((Object)gender), (String)"<set-?>");
        this.gender = gender;
    }

    @NotNull
    public final IVs getIvs() {
        return this.ivs;
    }

    public final void setIvs(@NotNull IVs iVs) {
        Intrinsics.checkNotNullParameter((Object)iVs, (String)"<set-?>");
        this.ivs = iVs;
    }

    @NotNull
    public final EVs getEvs() {
        return this.evs;
    }

    public final void setEvs(@NotNull EVs eVs) {
        Intrinsics.checkNotNullParameter((Object)eVs, (String)"<set-?>");
        this.evs = eVs;
    }

    @NotNull
    public final MoveSet getMoveSet() {
        return this.moveSet;
    }

    public final void setMoveSet(@NotNull MoveSet moveSet) {
        Intrinsics.checkNotNullParameter((Object)moveSet, (String)"<set-?>");
        this.moveSet = moveSet;
    }

    public final float getScaleModifier() {
        return this.scaleModifier;
    }

    public final void setScaleModifier(float f) {
        this.scaleModifier = f;
    }

    @NotNull
    public final String getAbility() {
        return this.ability;
    }

    public final void setAbility(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.ability = string;
    }

    public final boolean getShiny() {
        return this.shiny;
    }

    public final void setShiny(boolean bl) {
        this.shiny = bl;
    }

    @Nullable
    public final ResourceLocation getStatus() {
        return this.status;
    }

    public final void setStatus(@Nullable ResourceLocation resourceLocation) {
        this.status = resourceLocation;
    }

    @NotNull
    public final PokemonState getState() {
        PokemonState pokemonState = this.state;
        if (pokemonState != null) {
            return pokemonState;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"state");
        return null;
    }

    public final void setState(@NotNull PokemonState pokemonState) {
        Intrinsics.checkNotNullParameter((Object)pokemonState, (String)"<set-?>");
        this.state = pokemonState;
    }

    @NotNull
    public final ResourceLocation getCaughtBall() {
        ResourceLocation resourceLocation = this.caughtBall;
        if (resourceLocation != null) {
            return resourceLocation;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"caughtBall");
        return null;
    }

    public final void setCaughtBall(@NotNull ResourceLocation resourceLocation) {
        Intrinsics.checkNotNullParameter((Object)resourceLocation, (String)"<set-?>");
        this.caughtBall = resourceLocation;
    }

    @NotNull
    public final BenchedMoves getBenchedMoves() {
        return this.benchedMoves;
    }

    public final void setBenchedMoves(@NotNull BenchedMoves benchedMoves) {
        Intrinsics.checkNotNullParameter((Object)benchedMoves, (String)"<set-?>");
        this.benchedMoves = benchedMoves;
    }

    @NotNull
    public final Set<String> getAspects() {
        return this.aspects;
    }

    public final void setAspects(@NotNull Set<String> set2) {
        Intrinsics.checkNotNullParameter(set2, (String)"<set-?>");
        this.aspects = set2;
    }

    @NotNull
    public final FriendlyByteBuf getEvolutionBuffer() {
        FriendlyByteBuf friendlyByteBuf = this.evolutionBuffer;
        if (friendlyByteBuf != null) {
            return friendlyByteBuf;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"evolutionBuffer");
        return null;
    }

    public final void setEvolutionBuffer(@NotNull FriendlyByteBuf friendlyByteBuf) {
        Intrinsics.checkNotNullParameter((Object)friendlyByteBuf, (String)"<set-?>");
        this.evolutionBuffer = friendlyByteBuf;
    }

    @NotNull
    public final ResourceLocation getNature() {
        ResourceLocation resourceLocation = this.nature;
        if (resourceLocation != null) {
            return resourceLocation;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"nature");
        return null;
    }

    public final void setNature(@NotNull ResourceLocation resourceLocation) {
        Intrinsics.checkNotNullParameter((Object)resourceLocation, (String)"<set-?>");
        this.nature = resourceLocation;
    }

    @Nullable
    public final ResourceLocation getMintNature() {
        return this.mintNature;
    }

    public final void setMintNature(@Nullable ResourceLocation resourceLocation) {
        this.mintNature = resourceLocation;
    }

    @NotNull
    public final ItemStack getHeldItem() {
        return this.heldItem;
    }

    public final void setHeldItem(@NotNull ItemStack itemStack) {
        Intrinsics.checkNotNullParameter((Object)itemStack, (String)"<set-?>");
        this.heldItem = itemStack;
    }

    @Nullable
    public final UUID getTetheringId() {
        return this.tetheringId;
    }

    public final void setTetheringId(@Nullable UUID uUID) {
        this.tetheringId = uUID;
    }

    @NotNull
    public final String getTeraType() {
        return this.teraType;
    }

    public final void setTeraType(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.teraType = string;
    }

    public final int getDmaxLevel() {
        return this.dmaxLevel;
    }

    public final void setDmaxLevel(int n) {
        this.dmaxLevel = n;
    }

    public final boolean getGmaxFactor() {
        return this.gmaxFactor;
    }

    public final void setGmaxFactor(boolean bl) {
        this.gmaxFactor = bl;
    }

    public final boolean getTradeable() {
        return this.tradeable;
    }

    public final void setTradeable(boolean bl) {
        this.tradeable = bl;
    }

    @NotNull
    public final FriendlyByteBuf getFeaturesBuffer() {
        FriendlyByteBuf friendlyByteBuf = this.featuresBuffer;
        if (friendlyByteBuf != null) {
            return friendlyByteBuf;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"featuresBuffer");
        return null;
    }

    public final void setFeaturesBuffer(@NotNull FriendlyByteBuf friendlyByteBuf) {
        Intrinsics.checkNotNullParameter((Object)friendlyByteBuf, (String)"<set-?>");
        this.featuresBuffer = friendlyByteBuf;
    }

    @NotNull
    public final OriginalTrainerType getOriginalTrainerType() {
        return this.originalTrainerType;
    }

    public final void setOriginalTrainerType(@NotNull OriginalTrainerType originalTrainerType) {
        Intrinsics.checkNotNullParameter((Object)((Object)originalTrainerType), (String)"<set-?>");
        this.originalTrainerType = originalTrainerType;
    }

    @Nullable
    public final String getOriginalTrainer() {
        return this.originalTrainer;
    }

    public final void setOriginalTrainer(@Nullable String string) {
        this.originalTrainer = string;
    }

    @Nullable
    public final String getOriginalTrainerName() {
        return this.originalTrainerName;
    }

    public final void setOriginalTrainerName(@Nullable String string) {
        this.originalTrainerName = string;
    }

    public PokemonDTO() {
        this.uuid = UUID.randomUUID();
        this.form = "";
        this.level = 1;
        this.experience = 1;
        this.gender = Gender.MALE;
        this.ivs = new IVs();
        this.evs = new EVs();
        this.moveSet = new MoveSet();
        this.ability = "";
        this.benchedMoves = new BenchedMoves();
        this.aspects = SetsKt.emptySet();
        ItemStack itemStack = ItemStack.f_41583_;
        Intrinsics.checkNotNullExpressionValue((Object)itemStack, (String)"EMPTY");
        this.heldItem = itemStack;
        this.teraType = "";
        this.tradeable = true;
        this.originalTrainerType = OriginalTrainerType.NONE;
    }

    /*
     * WARNING - void declaration
     */
    public PokemonDTO(@NotNull Pokemon pokemon, boolean toClient) {
        void $this$filterTo$iv$iv;
        void $this$filter$iv;
        void $this$filterIsInstanceTo$iv$iv;
        Iterable $this$filterIsInstance$iv;
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        this.uuid = UUID.randomUUID();
        this.form = "";
        this.level = 1;
        this.experience = 1;
        this.gender = Gender.MALE;
        this.ivs = new IVs();
        this.evs = new EVs();
        this.moveSet = new MoveSet();
        this.ability = "";
        this.benchedMoves = new BenchedMoves();
        this.aspects = SetsKt.emptySet();
        ItemStack itemStack = ItemStack.f_41583_;
        Intrinsics.checkNotNullExpressionValue((Object)itemStack, (String)"EMPTY");
        this.heldItem = itemStack;
        this.teraType = "";
        this.tradeable = true;
        this.originalTrainerType = OriginalTrainerType.NONE;
        this.toClient = toClient;
        this.uuid = pokemon.getUuid();
        this.setSpecies(pokemon.getSpecies().getResourceIdentifier());
        this.nickname = pokemon.getNickname();
        this.form = pokemon.getForm().getName();
        this.level = pokemon.getLevel();
        this.experience = pokemon.getExperience();
        this.friendship = pokemon.getFriendship();
        this.currentHealth = pokemon.getCurrentHealth();
        this.gender = pokemon.getGender();
        this.ivs = pokemon.getIvs();
        this.evs = pokemon.getEvs();
        this.moveSet = pokemon.getMoveSet();
        this.scaleModifier = pokemon.getScaleModifier();
        this.ability = pokemon.getAbility().getName();
        this.shiny = pokemon.getShiny();
        Object object = pokemon.getStatus();
        this.status = object != null && (object = ((PersistentStatusContainer)object).getStatus()) != null ? ((Status)object).getName() : null;
        this.setState(pokemon.getState());
        this.setCaughtBall(pokemon.getCaughtBall().getName());
        this.benchedMoves = pokemon.getBenchedMoves();
        this.aspects = pokemon.getAspects();
        this.setEvolutionBuffer(new FriendlyByteBuf(Unpooled.buffer()));
        pokemon.getEvolutionProxy().saveToBuffer(this.getEvolutionBuffer(), toClient);
        this.setNature(pokemon.getNature().getName());
        Nature nature = pokemon.getMintedNature();
        this.mintNature = nature != null ? nature.getName() : null;
        this.heldItem = pokemon.heldItemNoCopy$common();
        this.tetheringId = pokemon.getTetheringId();
        String string = pokemon.getTeraType().getId().toString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"pokemon.teraType.id.toString()");
        this.teraType = string;
        this.dmaxLevel = pokemon.getDmaxLevel();
        this.gmaxFactor = pokemon.getGmaxFactor();
        this.tradeable = pokemon.getTradeable();
        this.setFeaturesBuffer(new FriendlyByteBuf(Unpooled.buffer()));
        Iterable iterable = pokemon.getFeatures();
        boolean $i$f$filterIsInstance = false;
        void var6_5 = $this$filterIsInstance$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterIsInstanceTo = false;
        for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
            if (!(element$iv$iv instanceof SynchronizedSpeciesFeature)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        $this$filterIsInstance$iv = (List)destination$iv$iv;
        boolean $i$f$filter = false;
        $this$filterIsInstanceTo$iv$iv = $this$filter$iv;
        destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            SynchronizedSpeciesFeature it = (SynchronizedSpeciesFeature)element$iv$iv;
            boolean bl = false;
            SpeciesFeatureProvider<? extends SpeciesFeature> speciesFeatureProvider = SpeciesFeatures.INSTANCE.getFeature(it.getName());
            SynchronizedSpeciesFeatureProvider synchronizedSpeciesFeatureProvider = speciesFeatureProvider instanceof SynchronizedSpeciesFeatureProvider ? (SynchronizedSpeciesFeatureProvider)speciesFeatureProvider : null;
            boolean bl2 = synchronizedSpeciesFeatureProvider != null ? synchronizedSpeciesFeatureProvider.getVisible() : false;
            if (!bl2) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        List visibleFeatures = (List)destination$iv$iv;
        this.getFeaturesBuffer().m_236828_((Collection)visibleFeatures, (arg_0, arg_1) -> PokemonDTO._init_$lambda$1(this, arg_0, arg_1));
        this.originalTrainerType = pokemon.getOriginalTrainerType();
        this.originalTrainer = pokemon.getOriginalTrainer();
        this.originalTrainerName = pokemon.getOriginalTrainerName();
    }

    @Override
    public void encode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.writeBoolean(this.toClient);
        buffer.m_130077_(this.uuid);
        buffer.m_130085_(this.getSpecies());
        buffer.m_236821_((Object)this.nickname, (arg_0, arg_1) -> PokemonDTO.encode$lambda$2(buffer, arg_0, arg_1));
        buffer.m_130070_(this.form);
        buffer.writeInt(this.experience);
        NetExtensionsKt.writeSizedInt((ByteBuf)buffer, IntSize.U_SHORT, this.level);
        buffer.writeShort(this.friendship);
        buffer.writeShort(this.currentHealth);
        NetExtensionsKt.writeSizedInt((ByteBuf)buffer, IntSize.U_BYTE, this.gender.ordinal());
        this.ivs.saveToBuffer(buffer);
        this.evs.saveToBuffer(buffer);
        this.moveSet.saveToBuffer(buffer);
        buffer.writeFloat(this.scaleModifier);
        buffer.m_130070_(this.ability);
        buffer.writeBoolean(this.shiny);
        this.getState().writeToBuffer(buffer);
        buffer.m_236821_((Object)this.status, (arg_0, arg_1) -> PokemonDTO.encode$lambda$3(buffer, arg_0, arg_1));
        buffer.m_130085_(this.getCaughtBall());
        this.benchedMoves.saveToBuffer(buffer);
        NetExtensionsKt.writeSizedInt((ByteBuf)buffer, IntSize.U_BYTE, this.aspects.size());
        Iterable $this$forEach$iv = this.aspects;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            String it = (String)element$iv;
            boolean bl = false;
            buffer.m_130070_(it);
        }
        int byteCount = this.getEvolutionBuffer().readableBytes();
        NetExtensionsKt.writeSizedInt((ByteBuf)buffer, IntSize.U_SHORT, byteCount);
        buffer.writeBytes((ByteBuf)this.getEvolutionBuffer());
        this.getEvolutionBuffer().release();
        buffer.m_130085_(this.getNature());
        buffer.m_236821_((Object)this.mintNature, (arg_0, arg_1) -> PokemonDTO.encode$lambda$5(buffer, arg_0, arg_1));
        buffer.m_130055_(this.heldItem);
        buffer.m_236821_((Object)this.tetheringId, (arg_0, arg_1) -> PokemonDTO.encode$lambda$6(buffer, arg_0, arg_1));
        buffer.m_130070_(this.teraType);
        buffer.writeInt(this.dmaxLevel);
        buffer.writeBoolean(this.gmaxFactor);
        buffer.writeBoolean(this.tradeable);
        int featureByteCount = this.getFeaturesBuffer().readableBytes();
        NetExtensionsKt.writeSizedInt((ByteBuf)buffer, IntSize.U_SHORT, featureByteCount);
        buffer.writeBytes((ByteBuf)this.getFeaturesBuffer());
        this.getFeaturesBuffer().release();
        buffer.m_130070_(this.originalTrainerType.name());
        buffer.m_236821_((Object)this.originalTrainer, (arg_0, arg_1) -> PokemonDTO.encode$lambda$7(buffer, arg_0, arg_1));
        buffer.m_236821_((Object)this.originalTrainerName, (arg_0, arg_1) -> PokemonDTO.encode$lambda$8(buffer, arg_0, arg_1));
    }

    @Override
    public void decode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        this.toClient = buffer.readBoolean();
        this.uuid = buffer.m_130259_();
        ResourceLocation resourceLocation = buffer.m_130281_();
        Intrinsics.checkNotNullExpressionValue((Object)resourceLocation, (String)"buffer.readIdentifier()");
        this.setSpecies(resourceLocation);
        this.nickname = (MutableComponent)buffer.m_236868_(arg_0 -> PokemonDTO.decode$lambda$9(buffer, arg_0));
        String string = buffer.m_130277_();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"buffer.readString()");
        this.form = string;
        this.experience = buffer.readInt();
        this.level = NetExtensionsKt.readSizedInt((ByteBuf)buffer, IntSize.U_SHORT);
        this.friendship = buffer.readUnsignedShort();
        this.currentHealth = buffer.readUnsignedShort();
        this.gender = Gender.values()[NetExtensionsKt.readSizedInt((ByteBuf)buffer, IntSize.U_BYTE)];
        this.ivs.loadFromBuffer(buffer);
        this.evs.loadFromBuffer(buffer);
        this.moveSet.loadFromBuffer(buffer);
        this.scaleModifier = buffer.readFloat();
        String string2 = buffer.m_130277_();
        Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"buffer.readString()");
        this.ability = string2;
        this.shiny = buffer.readBoolean();
        this.setState(PokemonState.Companion.fromBuffer(buffer));
        this.status = (ResourceLocation)buffer.m_236868_(arg_0 -> PokemonDTO.decode$lambda$10(buffer, arg_0));
        ResourceLocation resourceLocation2 = buffer.m_130281_();
        Intrinsics.checkNotNullExpressionValue((Object)resourceLocation2, (String)"buffer.readIdentifier()");
        this.setCaughtBall(resourceLocation2);
        this.benchedMoves.loadFromBuffer(buffer);
        Set aspects = new LinkedHashSet();
        int n = NetExtensionsKt.readSizedInt((ByteBuf)buffer, IntSize.U_BYTE);
        int n2 = 0;
        while (n2 < n) {
            int it = n2++;
            boolean bl = false;
            String string3 = buffer.m_130277_();
            Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"buffer.readString()");
            aspects.add(string3);
        }
        this.aspects = aspects;
        int bytesToRead = NetExtensionsKt.readSizedInt((ByteBuf)buffer, IntSize.U_SHORT);
        this.setEvolutionBuffer(new FriendlyByteBuf(buffer.readBytes(bytesToRead)));
        ResourceLocation resourceLocation3 = buffer.m_130281_();
        Intrinsics.checkNotNullExpressionValue((Object)resourceLocation3, (String)"buffer.readIdentifier()");
        this.setNature(resourceLocation3);
        this.mintNature = (ResourceLocation)buffer.m_236868_(arg_0 -> PokemonDTO.decode$lambda$12(buffer, arg_0));
        ItemStack itemStack = buffer.m_130267_();
        Intrinsics.checkNotNullExpressionValue((Object)itemStack, (String)"buffer.readItemStack()");
        this.heldItem = itemStack;
        this.tetheringId = (UUID)buffer.m_236868_(arg_0 -> PokemonDTO.decode$lambda$13(buffer, arg_0));
        String string4 = buffer.m_130277_();
        Intrinsics.checkNotNullExpressionValue((Object)string4, (String)"buffer.readString()");
        this.teraType = string4;
        this.dmaxLevel = buffer.readInt();
        this.gmaxFactor = buffer.readBoolean();
        this.tradeable = buffer.readBoolean();
        int featureBytesToRead = NetExtensionsKt.readSizedInt((ByteBuf)buffer, IntSize.U_SHORT);
        this.setFeaturesBuffer(new FriendlyByteBuf(buffer.readBytes(featureBytesToRead)));
        String string5 = buffer.m_130277_();
        Intrinsics.checkNotNullExpressionValue((Object)string5, (String)"buffer.readString()");
        this.originalTrainerType = OriginalTrainerType.valueOf(string5);
        this.originalTrainer = (String)buffer.m_236868_(arg_0 -> PokemonDTO.decode$lambda$14(buffer, arg_0));
        this.originalTrainerName = (String)buffer.m_236868_(arg_0 -> PokemonDTO.decode$lambda$15(buffer, arg_0));
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final Pokemon create() {
        Nature nature;
        PersistentStatusContainer persistentStatusContainer;
        ResourceLocation id;
        Map.Entry stat;
        FormData formData;
        Object v1;
        ResourceLocation resourceLocation;
        Pokemon pokemon;
        Pokemon it;
        Pokemon pokemon2;
        block21: {
            it = pokemon2 = new Pokemon();
            boolean bl = false;
            it.setClient$common(this.toClient);
            it.setUuid(this.uuid);
            Species species = PokemonSpecies.INSTANCE.getByIdentifier(this.getSpecies());
            Intrinsics.checkNotNull((Object)species);
            it.setSpecies(species);
            it.setNickname(this.nickname);
            Iterable iterable = it.getSpecies().getForms();
            pokemon = it;
            resourceLocation = iterable;
            for (Object t : resourceLocation) {
                FormData it2 = (FormData)t;
                boolean bl2 = false;
                if (!Intrinsics.areEqual((Object)it2.getName(), (Object)this.form)) continue;
                v1 = t;
                break block21;
            }
            v1 = null;
        }
        if ((formData = (FormData)v1) == null) {
            formData = it.getSpecies().getStandardForm();
        }
        pokemon.setForm(formData);
        it.setExperience$common(this.experience);
        it.setLevel(this.level);
        Pokemon.setFriendship$default(it, this.friendship, false, 2, null);
        it.setGender(this.gender);
        Iterable $this$forEach$iv = this.ivs;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            stat = (Map.Entry)element$iv;
            boolean bl = false;
            it.setIV((Stat)stat.getKey(), ((Number)stat.getValue()).intValue());
        }
        $this$forEach$iv = this.evs;
        $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            stat = (Map.Entry)element$iv;
            boolean bl = false;
            it.setEV((Stat)stat.getKey(), ((Number)stat.getValue()).intValue());
        }
        it.setCurrentHealth(this.currentHealth);
        it.getMoveSet().clear();
        for (Move move : this.moveSet) {
            it.getMoveSet().add(move);
        }
        it.setScaleModifier(this.scaleModifier);
        it.setAbility$common(AbilityTemplate.create$default(Abilities.INSTANCE.getOrException(this.ability), false, 1, null));
        it.setShiny(this.shiny);
        it.setState(this.getState());
        Pokemon pokemon3 = it;
        ResourceLocation resourceLocation2 = this.status;
        if (resourceLocation2 != null) {
            resourceLocation = resourceLocation2;
            pokemon = pokemon3;
            boolean bl = false;
            Status statusType = Statuses.INSTANCE.getStatus(id);
            persistentStatusContainer = statusType instanceof PersistentStatus ? new PersistentStatusContainer((PersistentStatus)statusType, 0) : null;
            pokemon3 = pokemon;
        } else {
            persistentStatusContainer = null;
        }
        pokemon3.setStatus(persistentStatusContainer);
        PokeBall pokeBall = PokeBalls.INSTANCE.getPokeBall(this.getCaughtBall());
        Intrinsics.checkNotNull((Object)pokeBall);
        it.setCaughtBall(pokeBall);
        it.getBenchedMoves().addAll(this.benchedMoves);
        it.setAspects(this.aspects);
        it.getEvolutionProxy().loadFromBuffer(this.getEvolutionBuffer());
        this.getEvolutionBuffer().release();
        Nature nature2 = Natures.INSTANCE.getNature(this.getNature());
        Intrinsics.checkNotNull((Object)nature2);
        it.setNature(nature2);
        Pokemon pokemon4 = it;
        ResourceLocation resourceLocation3 = this.mintNature;
        if (resourceLocation3 != null) {
            id = resourceLocation3;
            pokemon = pokemon4;
            boolean bl = false;
            Nature nature3 = Natures.INSTANCE.getNature(id);
            Intrinsics.checkNotNull((Object)nature3);
            nature = nature3;
            pokemon4 = pokemon;
        } else {
            nature = null;
        }
        pokemon4.setMintedNature(nature);
        it.swapHeldItem(this.heldItem, false);
        it.setTetheringId(this.tetheringId);
        TeraType teraType = TeraTypes.get(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(this.teraType, null, 1, null));
        Intrinsics.checkNotNull((Object)teraType);
        it.setTeraType(teraType);
        it.setDmaxLevel(this.dmaxLevel);
        it.setGmaxFactor(this.gmaxFactor);
        it.setTradeable(this.tradeable);
        int n = NetExtensionsKt.readSizedInt((ByteBuf)this.getFeaturesBuffer(), IntSize.U_BYTE);
        for (int i = 0; i < n; ++i) {
            SynchronizedSpeciesFeature synchronizedSpeciesFeature;
            block22: {
                void $this$filterIsInstanceTo$iv$iv;
                void $this$filterIsInstance$iv;
                Species species;
                boolean bl = false;
                Intrinsics.checkNotNull((Object)PokemonSpecies.INSTANCE.getByIdentifier(this.getSpecies()));
                String speciesFeatureName = this.getFeaturesBuffer().m_130277_();
                Iterable bl2 = SpeciesFeatures.INSTANCE.getFeaturesFor(species);
                boolean $i$f$filterIsInstance = false;
                void var13_27 = $this$filterIsInstance$iv;
                Collection destination$iv$iv = new ArrayList();
                boolean $i$f$filterIsInstanceTo = false;
                for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
                    if (!(element$iv$iv instanceof SynchronizedSpeciesFeatureProvider)) continue;
                    destination$iv$iv.add(element$iv$iv);
                }
                List featureProviders = (List)destination$iv$iv;
                for (SynchronizedSpeciesFeatureProvider it3 : (Iterable)featureProviders) {
                    boolean bl3 = false;
                    FriendlyByteBuf friendlyByteBuf = this.getFeaturesBuffer();
                    Intrinsics.checkNotNullExpressionValue((Object)speciesFeatureName, (String)"speciesFeatureName");
                    Object t = it3.invoke(friendlyByteBuf, speciesFeatureName);
                    if (t == null) continue;
                    synchronizedSpeciesFeature = (SynchronizedSpeciesFeature)t;
                    break block22;
                }
                synchronizedSpeciesFeature = null;
            }
            if (synchronizedSpeciesFeature == null) {
                throw new IllegalArgumentException("Couldn't find a feature provider to deserialize this feature. Something's wrong.");
            }
            SynchronizedSpeciesFeature feature = synchronizedSpeciesFeature;
            it.getFeatures().removeIf(arg_0 -> PokemonDTO.create$lambda$27$lambda$23$lambda$22((Function1)new Function1<SpeciesFeature, Boolean>(feature){
                final /* synthetic */ SynchronizedSpeciesFeature $feature;
                {
                    this.$feature = $feature;
                    super(1);
                }

                @NotNull
                public final Boolean invoke(@NotNull SpeciesFeature it) {
                    Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                    return Intrinsics.areEqual((Object)it.getName(), (Object)this.$feature.getName());
                }
            }, arg_0));
            it.getFeatures().add(feature);
        }
        switch (WhenMappings.$EnumSwitchMapping$0[this.originalTrainerType.ordinal()]) {
            case 1: {
                it.removeOriginalTrainer();
                break;
            }
            case 2: {
                String ot;
                String string = this.originalTrainer;
                if (string != null) {
                    UUID uUID;
                    ot = string;
                    boolean bl = false;
                    UUID uUID2 = uUID = UUID.fromString(ot);
                    if (uUID2 != null) {
                        Intrinsics.checkNotNullExpressionValue((Object)uUID2, (String)"fromString(ot)");
                        UUID uuid2 = uUID;
                        boolean bl4 = false;
                        it.setOriginalTrainer(uuid2);
                    }
                }
                break;
            }
            case 3: {
                String ot;
                String string = this.originalTrainer;
                if (string != null) {
                    ot = string;
                    boolean bl = false;
                    it.setOriginalTrainer(ot);
                }
                break;
            }
        }
        it.setOriginalTrainerName(this.originalTrainerName);
        return pokemon2;
    }

    private static final void _init_$lambda$1(PokemonDTO this$0, FriendlyByteBuf friendlyByteBuf, SynchronizedSpeciesFeature value2) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        this$0.getFeaturesBuffer().m_130070_(value2.getName());
        value2.encode(this$0.getFeaturesBuffer());
    }

    private static final void encode$lambda$2(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, MutableComponent v) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        $buffer.m_130083_((Component)v);
    }

    private static final void encode$lambda$3(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, ResourceLocation v) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        $buffer.m_130085_(v);
    }

    private static final void encode$lambda$5(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, ResourceLocation v) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        $buffer.m_130085_(v);
    }

    private static final void encode$lambda$6(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, UUID v) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        $buffer.m_130077_(v);
    }

    private static final void encode$lambda$7(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, String v) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        $buffer.m_130070_(v);
    }

    private static final void encode$lambda$8(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, String v) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        $buffer.m_130070_(v);
    }

    private static final MutableComponent decode$lambda$9(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        return $buffer.m_130238_().m_6881_();
    }

    private static final ResourceLocation decode$lambda$10(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        return $buffer.m_130281_();
    }

    private static final ResourceLocation decode$lambda$12(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        return $buffer.m_130281_();
    }

    private static final UUID decode$lambda$13(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        return $buffer.m_130259_();
    }

    private static final String decode$lambda$14(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        return $buffer.m_130277_();
    }

    private static final String decode$lambda$15(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        return $buffer.m_130277_();
    }

    private static final boolean create$lambda$27$lambda$23$lambda$22(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Boolean)$tmp0.invoke(p0);
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[OriginalTrainerType.values().length];
            try {
                nArray[OriginalTrainerType.NONE.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[OriginalTrainerType.PLAYER.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[OriginalTrainerType.NPC.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

