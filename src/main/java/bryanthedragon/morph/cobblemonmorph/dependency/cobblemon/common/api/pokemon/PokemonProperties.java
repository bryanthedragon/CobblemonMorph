/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.mojang.authlib.GameProfile
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.collections.ArraysKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.JvmOverloads
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.random.Random
 *  kotlin.ranges.RangesKt
 *  kotlin.text.Regex
 *  kotlin.text.StringsKt
 *  net.minecraft.ResourceLocationException
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.StringTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.Component$Serializer
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.Level
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.VariableStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.DoubleValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.StringValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.Abilities;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.Ability;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.AbilityTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.PotentialAbility;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.PokeBalls;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.Natures;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.aspect.AspectProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stats;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Status;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonProperty;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonPropertyType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.tera.TeraType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.tera.TeraTypes;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.PokeBall;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.EVs;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Gender;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.IVs;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Nature;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.OriginalTrainerType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.PokemonStats;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.RenderablePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatusContainer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.DistributionUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.StringExtensionsKt;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.Random;
import kotlin.ranges.RangesKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import net.minecraft.ResourceLocationException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u00b0\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0010\"\n\u0002\b\u0006\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u001b\b\u0016\u0018\u0000 \u00a6\u00012\u00020\u0001:\u0002\u00a6\u0001B\b\u00a2\u0006\u0005\b\u00a5\u0001\u00109J\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\u0005\u0010\tJ\r\u0010\u000b\u001a\u00020\n\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0017\u0010\u000f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\r\u00a2\u0006\u0004\b\u000f\u0010\u0010J\r\u0010\u0012\u001a\u00020\u0011\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0017\u0010\u0014\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0007H\u0002\u00a2\u0006\u0004\b\u0014\u0010\tJ\u0017\u0010\u0016\u001a\u00020\u00152\u0006\u0010\b\u001a\u00020\u0007H\u0002\u00a2\u0006\u0004\b\u0016\u0010\u0017J\r\u0010\u0018\u001a\u00020\u0000\u00a2\u0006\u0004\b\u0018\u0010\u0019J\r\u0010\u001a\u001a\u00020\u0007\u00a2\u0006\u0004\b\u001a\u0010\u001bJ!\u0010 \u001a\u0004\u0018\u00010\u001f2\u0006\u0010\u001c\u001a\u00020\r2\u0006\u0010\u001e\u001a\u00020\u001dH\u0002\u00a2\u0006\u0004\b \u0010!J\u0015\u0010$\u001a\u00020\u00022\u0006\u0010#\u001a\u00020\"\u00a2\u0006\u0004\b$\u0010%J\u0015\u0010'\u001a\u00020\u00152\u0006\u0010&\u001a\u00020\u0000\u00a2\u0006\u0004\b'\u0010(J\u0015\u0010+\u001a\u00020\u00002\u0006\u0010*\u001a\u00020)\u00a2\u0006\u0004\b+\u0010,J\u0015\u0010/\u001a\u00020\u00002\u0006\u0010.\u001a\u00020-\u00a2\u0006\u0004\b/\u00100J\u0015\u00101\u001a\u00020\u00152\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b1\u00102J\u0015\u00101\u001a\u00020\u00152\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b1\u0010\u0017J\u0015\u00103\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b3\u0010\tJ\r\u00104\u001a\u00020)\u00a2\u0006\u0004\b4\u00105J\r\u00106\u001a\u00020-\u00a2\u0006\u0004\b6\u00107J\r\u00108\u001a\u00020\u0004\u00a2\u0006\u0004\b8\u00109J\u0013\u0010;\u001a\u00020\u0015*\u00020:H\u0002\u00a2\u0006\u0004\b;\u0010<R$\u0010=\u001a\u0004\u0018\u00010\r8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b=\u0010>\u001a\u0004\b?\u0010@\"\u0004\bA\u0010BR(\u0010D\u001a\b\u0012\u0004\u0012\u00020\r0C8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bD\u0010E\u001a\u0004\bF\u0010G\"\u0004\bH\u0010IR(\u0010L\u001a\b\u0012\u0004\u0012\u00020K0J8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bL\u0010M\u001a\u0004\bN\u0010O\"\u0004\bP\u0010QR$\u0010S\u001a\u0004\u0018\u00010R8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bS\u0010T\u001a\u0004\bU\u0010V\"\u0004\bW\u0010XR$\u0010Z\u001a\u0004\u0018\u00010Y8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bZ\u0010[\u001a\u0004\b\\\u0010]\"\u0004\b^\u0010_R$\u0010\u001e\u001a\u0004\u0018\u00010\r8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001e\u0010>\u001a\u0004\b`\u0010@\"\u0004\ba\u0010BR$\u0010b\u001a\u0004\u0018\u00010R8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bb\u0010T\u001a\u0004\bc\u0010V\"\u0004\bd\u0010XR$\u0010f\u001a\u0004\u0018\u00010e8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bf\u0010g\u001a\u0004\bh\u0010i\"\u0004\bj\u0010kR$\u0010l\u001a\u0004\u0018\u00010\u00158\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bl\u0010m\u001a\u0004\bn\u0010o\"\u0004\bp\u0010qR$\u0010s\u001a\u0004\u0018\u00010r8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bs\u0010t\u001a\u0004\bu\u0010v\"\u0004\bw\u0010xR$\u0010y\u001a\u0004\u0018\u00010R8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\by\u0010T\u001a\u0004\bz\u0010V\"\u0004\b{\u0010XR$\u0010|\u001a\u0004\u0018\u00010\r8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b|\u0010>\u001a\u0004\b}\u0010@\"\u0004\b~\u0010BR+\u0010\u0080\u0001\u001a\u0004\u0018\u00010\u007f8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0018\n\u0006\b\u0080\u0001\u0010\u0081\u0001\u001a\u0006\b\u0082\u0001\u0010\u0083\u0001\"\u0006\b\u0084\u0001\u0010\u0085\u0001R&\u0010\u0086\u0001\u001a\u00020\r8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0015\n\u0005\b\u0086\u0001\u0010>\u001a\u0005\b\u0087\u0001\u0010@\"\u0005\b\u0088\u0001\u0010BR(\u0010\u0089\u0001\u001a\u0004\u0018\u00010\r8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0015\n\u0005\b\u0089\u0001\u0010>\u001a\u0005\b\u008a\u0001\u0010@\"\u0005\b\u008b\u0001\u0010BR,\u0010\u008d\u0001\u001a\u0005\u0018\u00010\u008c\u00018\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0018\n\u0006\b\u008d\u0001\u0010\u008e\u0001\u001a\u0006\b\u008f\u0001\u0010\u0090\u0001\"\u0006\b\u0091\u0001\u0010\u0092\u0001R(\u0010\u0093\u0001\u001a\u0004\u0018\u00010\r8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0015\n\u0005\b\u0093\u0001\u0010>\u001a\u0005\b\u0094\u0001\u0010@\"\u0005\b\u0095\u0001\u0010BR(\u0010\u0096\u0001\u001a\u0004\u0018\u00010\u00158\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0015\n\u0005\b\u0096\u0001\u0010m\u001a\u0005\b\u0097\u0001\u0010o\"\u0005\b\u0098\u0001\u0010qR(\u0010\u0099\u0001\u001a\u0004\u0018\u00010\r8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0015\n\u0005\b\u0099\u0001\u0010>\u001a\u0005\b\u009a\u0001\u0010@\"\u0005\b\u009b\u0001\u0010BR(\u0010\u009c\u0001\u001a\u0004\u0018\u00010\r8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0015\n\u0005\b\u009c\u0001\u0010>\u001a\u0005\b\u009d\u0001\u0010@\"\u0005\b\u009e\u0001\u0010BR(\u0010\u009f\u0001\u001a\u0004\u0018\u00010\r8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0015\n\u0005\b\u009f\u0001\u0010>\u001a\u0005\b\u00a0\u0001\u0010@\"\u0005\b\u00a1\u0001\u0010BR(\u0010\u00a2\u0001\u001a\u0004\u0018\u00010\u00158\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0015\n\u0005\b\u00a2\u0001\u0010m\u001a\u0005\b\u00a3\u0001\u0010o\"\u0005\b\u00a4\u0001\u0010q\u00a8\u0006\u00a7\u0001"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "pokemonEntity", "", "apply", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)V", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "Lcom/cobblemon/mod/common/pokemon/RenderablePokemon;", "asRenderablePokemon", "()Lcom/cobblemon/mod/common/pokemon/RenderablePokemon;", "", "separator", "asString", "(Ljava/lang/String;)Ljava/lang/String;", "Lcom/bedrockk/molang/runtime/struct/VariableStruct;", "asStruct", "()Lcom/bedrockk/molang/runtime/struct/VariableStruct;", "commonApply", "", "commonMatches", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "copy", "()Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "create", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "id", "Lcom/cobblemon/mod/common/pokemon/FormData;", "form", "Lcom/cobblemon/mod/common/api/abilities/Ability;", "createAbility", "(Ljava/lang/String;Lcom/cobblemon/mod/common/pokemon/FormData;)Lcom/cobblemon/mod/common/api/abilities/Ability;", "Lnet/minecraft/world/level/Level;", "world", "createEntity", "(Lnet/minecraft/world/level/Level;)Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "properties", "isSubSetOf", "(Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;)Z", "Lcom/google/gson/JsonObject;", "json", "loadFromJSON", "(Lcom/google/gson/JsonObject;)Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "Lnet/minecraft/nbt/CompoundTag;", "tag", "loadFromNBT", "(Lnet/minecraft/nbt/CompoundTag;)Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "matches", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)Z", "roll", "saveToJSON", "()Lcom/google/gson/JsonObject;", "saveToNBT", "()Lnet/minecraft/nbt/CompoundTag;", "updateAspects", "()V", "", "checkRate", "(F)Z", "ability", "Ljava/lang/String;", "getAbility", "()Ljava/lang/String;", "setAbility", "(Ljava/lang/String;)V", "", "aspects", "Ljava/util/Set;", "getAspects", "()Ljava/util/Set;", "setAspects", "(Ljava/util/Set;)V", "", "Lcom/cobblemon/mod/common/api/properties/CustomPokemonProperty;", "customProperties", "Ljava/util/List;", "getCustomProperties", "()Ljava/util/List;", "setCustomProperties", "(Ljava/util/List;)V", "", "dmaxLevel", "Ljava/lang/Integer;", "getDmaxLevel", "()Ljava/lang/Integer;", "setDmaxLevel", "(Ljava/lang/Integer;)V", "Lcom/cobblemon/mod/common/pokemon/EVs;", "evs", "Lcom/cobblemon/mod/common/pokemon/EVs;", "getEvs", "()Lcom/cobblemon/mod/common/pokemon/EVs;", "setEvs", "(Lcom/cobblemon/mod/common/pokemon/EVs;)V", "getForm", "setForm", "friendship", "getFriendship", "setFriendship", "Lcom/cobblemon/mod/common/pokemon/Gender;", "gender", "Lcom/cobblemon/mod/common/pokemon/Gender;", "getGender", "()Lcom/cobblemon/mod/common/pokemon/Gender;", "setGender", "(Lcom/cobblemon/mod/common/pokemon/Gender;)V", "gmaxFactor", "Ljava/lang/Boolean;", "getGmaxFactor", "()Ljava/lang/Boolean;", "setGmaxFactor", "(Ljava/lang/Boolean;)V", "Lcom/cobblemon/mod/common/pokemon/IVs;", "ivs", "Lcom/cobblemon/mod/common/pokemon/IVs;", "getIvs", "()Lcom/cobblemon/mod/common/pokemon/IVs;", "setIvs", "(Lcom/cobblemon/mod/common/pokemon/IVs;)V", "level", "getLevel", "setLevel", "nature", "getNature", "setNature", "Lnet/minecraft/network/chat/MutableComponent;", "nickname", "Lnet/minecraft/network/chat/MutableComponent;", "getNickname", "()Lnet/minecraft/network/chat/MutableComponent;", "setNickname", "(Lnet/minecraft/network/chat/MutableComponent;)V", "originalString", "getOriginalString", "setOriginalString", "originalTrainer", "getOriginalTrainer", "setOriginalTrainer", "Lcom/cobblemon/mod/common/pokemon/OriginalTrainerType;", "originalTrainerType", "Lcom/cobblemon/mod/common/pokemon/OriginalTrainerType;", "getOriginalTrainerType", "()Lcom/cobblemon/mod/common/pokemon/OriginalTrainerType;", "setOriginalTrainerType", "(Lcom/cobblemon/mod/common/pokemon/OriginalTrainerType;)V", "pokeball", "getPokeball", "setPokeball", "shiny", "getShiny", "setShiny", "species", "getSpecies", "setSpecies", "status", "getStatus", "setStatus", "teraType", "getTeraType", "setTeraType", "tradeable", "getTradeable", "setTradeable", "<init>", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nPokemonProperties.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonProperties.kt\ncom/cobblemon/mod/common/api/pokemon/PokemonProperties\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,675:1\n1855#2,2:676\n1855#2,2:678\n288#2,2:681\n1855#2,2:683\n1855#2,2:685\n1855#2,2:687\n2624#2,3:689\n2624#2,3:692\n1855#2,2:695\n1855#2,2:697\n1855#2,2:699\n1855#2,2:701\n1549#2:703\n1620#2,3:704\n1855#2,2:707\n1855#2,2:709\n1549#2:711\n1620#2,3:712\n1855#2,2:715\n1855#2,2:717\n1855#2,2:719\n1855#2,2:721\n1855#2,2:723\n1855#2,2:725\n288#2,2:727\n1#3:680\n*S KotlinDebug\n*F\n+ 1 PokemonProperties.kt\ncom/cobblemon/mod/common/api/pokemon/PokemonProperties\n*L\n300#1:676,2\n306#1:678,2\n323#1:681,2\n332#1:683,2\n334#1:685,2\n339#1:687,2\n374#1:689,3\n379#1:692,3\n407#1:695,2\n410#1:697,2\n448#1:699,2\n455#1:701,2\n515#1:703\n515#1:704,3\n515#1:707,2\n544#1:709,2\n573#1:711\n573#1:712,3\n573#1:715,2\n603#1:717,2\n621#1:719,2\n624#1:721,2\n633#1:723,2\n650#1:725,2\n670#1:727,2\n*E\n"})
public class PokemonProperties {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private String originalString = "";
    @Nullable
    private String species;
    @Nullable
    private MutableComponent nickname;
    @Nullable
    private String form;
    @Nullable
    private Boolean shiny;
    @Nullable
    private Gender gender;
    @Nullable
    private Integer level;
    @Nullable
    private Integer friendship;
    @Nullable
    private String pokeball;
    @Nullable
    private String nature;
    @Nullable
    private String ability;
    @NotNull
    private Set<String> aspects = SetsKt.emptySet();
    @Nullable
    private String status;
    @Nullable
    private String teraType;
    @Nullable
    private Integer dmaxLevel;
    @Nullable
    private Boolean gmaxFactor;
    @Nullable
    private Boolean tradeable;
    @Nullable
    private OriginalTrainerType originalTrainerType;
    @Nullable
    private String originalTrainer;
    @Nullable
    private IVs ivs;
    @Nullable
    private EVs evs;
    @NotNull
    private List<CustomPokemonProperty> customProperties = new ArrayList();

    @NotNull
    public final String getOriginalString() {
        return this.originalString;
    }

    public final void setOriginalString(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.originalString = string;
    }

    @Nullable
    public final String getSpecies() {
        return this.species;
    }

    public final void setSpecies(@Nullable String string) {
        this.species = string;
    }

    @Nullable
    public final MutableComponent getNickname() {
        return this.nickname;
    }

    public final void setNickname(@Nullable MutableComponent mutableComponent) {
        this.nickname = mutableComponent;
    }

    @Nullable
    public final String getForm() {
        return this.form;
    }

    public final void setForm(@Nullable String string) {
        this.form = string;
    }

    @Nullable
    public final Boolean getShiny() {
        return this.shiny;
    }

    public final void setShiny(@Nullable Boolean bl) {
        this.shiny = bl;
    }

    @Nullable
    public final Gender getGender() {
        return this.gender;
    }

    public final void setGender(@Nullable Gender gender) {
        this.gender = gender;
    }

    @Nullable
    public final Integer getLevel() {
        return this.level;
    }

    public final void setLevel(@Nullable Integer n) {
        this.level = n;
    }

    @Nullable
    public final Integer getFriendship() {
        return this.friendship;
    }

    public final void setFriendship(@Nullable Integer n) {
        this.friendship = n;
    }

    @Nullable
    public final String getPokeball() {
        return this.pokeball;
    }

    public final void setPokeball(@Nullable String string) {
        this.pokeball = string;
    }

    @Nullable
    public final String getNature() {
        return this.nature;
    }

    public final void setNature(@Nullable String string) {
        this.nature = string;
    }

    @Nullable
    public final String getAbility() {
        return this.ability;
    }

    public final void setAbility(@Nullable String string) {
        this.ability = string;
    }

    @NotNull
    public final Set<String> getAspects() {
        return this.aspects;
    }

    public final void setAspects(@NotNull Set<String> set2) {
        Intrinsics.checkNotNullParameter(set2, (String)"<set-?>");
        this.aspects = set2;
    }

    @Nullable
    public final String getStatus() {
        return this.status;
    }

    public final void setStatus(@Nullable String string) {
        this.status = string;
    }

    @Nullable
    public final String getTeraType() {
        return this.teraType;
    }

    public final void setTeraType(@Nullable String string) {
        this.teraType = string;
    }

    @Nullable
    public final Integer getDmaxLevel() {
        return this.dmaxLevel;
    }

    public final void setDmaxLevel(@Nullable Integer n) {
        this.dmaxLevel = n;
    }

    @Nullable
    public final Boolean getGmaxFactor() {
        return this.gmaxFactor;
    }

    public final void setGmaxFactor(@Nullable Boolean bl) {
        this.gmaxFactor = bl;
    }

    @Nullable
    public final Boolean getTradeable() {
        return this.tradeable;
    }

    public final void setTradeable(@Nullable Boolean bl) {
        this.tradeable = bl;
    }

    @Nullable
    public final OriginalTrainerType getOriginalTrainerType() {
        return this.originalTrainerType;
    }

    public final void setOriginalTrainerType(@Nullable OriginalTrainerType originalTrainerType) {
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
    public final IVs getIvs() {
        return this.ivs;
    }

    public final void setIvs(@Nullable IVs iVs) {
        this.ivs = iVs;
    }

    @Nullable
    public final EVs getEvs() {
        return this.evs;
    }

    public final void setEvs(@Nullable EVs eVs) {
        this.evs = eVs;
    }

    @NotNull
    public final List<CustomPokemonProperty> getCustomProperties() {
        return this.customProperties;
    }

    public final void setCustomProperties(@NotNull List<CustomPokemonProperty> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.customProperties = list;
    }

    @NotNull
    public final RenderablePokemon asRenderablePokemon() {
        Object object;
        block5: {
            block4: {
                Species species;
                object = this.species;
                if (object == null) break block4;
                String it = object;
                boolean bl = false;
                try {
                    species = PokemonSpecies.INSTANCE.getByIdentifier(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(it, null, 1, null));
                }
                catch (ResourceLocationException e) {
                    species = PokemonSpecies.INSTANCE.random();
                }
                Species species2 = species;
                object = species2;
                if (species2 != null) break block5;
            }
            object = PokemonSpecies.INSTANCE.random();
        }
        Set<String> set2 = this.aspects;
        Object object2 = object;
        return new RenderablePokemon((Species)object2, set2);
    }

    public final void apply(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Iterable $this$forEach$iv = this.customProperties;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            CustomPokemonProperty it = (CustomPokemonProperty)element$iv;
            boolean bl = false;
            it.apply(pokemon);
        }
        this.commonApply(pokemon);
    }

    public final void apply(@NotNull PokemonEntity pokemonEntity) {
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
        Iterable $this$forEach$iv = this.customProperties;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            CustomPokemonProperty it = (CustomPokemonProperty)element$iv;
            boolean bl = false;
            it.apply(pokemonEntity);
        }
        this.commonApply(pokemonEntity.getPokemon());
    }

    private final void commonApply(Pokemon pokemon) {
        Map.Entry stat;
        Object object;
        String string = this.species;
        if (string != null) {
            Species species;
            String it = string;
            boolean bl = false;
            try {
                species = Intrinsics.areEqual((Object)it, (Object)"random") ? (Species)CollectionsKt.random((Collection)PokemonSpecies.INSTANCE.getImplemented(), (Random)((Random)Random.Default)) : PokemonSpecies.INSTANCE.getByIdentifier(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(it, null, 1, null));
            }
            catch (ResourceLocationException e) {
                species = null;
            }
            Species species2 = species;
            if (species2 != null) {
                it = species2;
                boolean bl2 = false;
                pokemon.setSpecies((Species)((Object)it));
            }
        }
        MutableComponent mutableComponent = this.nickname;
        if (mutableComponent != null) {
            MutableComponent it = mutableComponent;
            boolean bl = false;
            pokemon.setNickname(it);
        }
        if ((object = this.form) != null) {
            Object v2;
            block39: {
                String formID = object;
                boolean bl = false;
                Iterable $this$firstOrNull$iv = pokemon.getSpecies().getForms();
                boolean $i$f$firstOrNull = false;
                for (Object element$iv : $this$firstOrNull$iv) {
                    FormData it = (FormData)element$iv;
                    boolean bl3 = false;
                    if (!StringsKt.equals((String)it.formOnlyShowdownId(), (String)formID, (boolean)true)) continue;
                    v2 = element$iv;
                    break block39;
                }
                v2 = null;
            }
            FormData formData = v2;
            object = formData;
            if (formData != null) {
                Object form2 = object;
                boolean bl = false;
                pokemon.setForm((FormData)form2);
            }
        }
        Boolean bl = this.shiny;
        if (bl != null) {
            boolean it = bl;
            boolean bl4 = false;
            pokemon.setShiny(it);
        }
        Gender gender = this.gender;
        if (gender != null) {
            Gender it = gender;
            boolean bl5 = false;
            pokemon.setGender(it);
        }
        Integer n = this.level;
        if (n != null) {
            int it = ((Number)n).intValue();
            boolean bl6 = false;
            pokemon.setLevel(it);
        }
        Integer n2 = this.friendship;
        if (n2 != null) {
            int it = ((Number)n2).intValue();
            boolean bl7 = false;
            Pokemon.setFriendship$default(pokemon, it, false, 2, null);
        }
        String string2 = this.pokeball;
        if (string2 != null) {
            String it = string2;
            boolean bl8 = false;
            PokeBall pokeBall = PokeBalls.INSTANCE.getPokeBall(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(it, null, 1, null));
            if (pokeBall != null) {
                PokeBall pokeball = pokeBall;
                boolean bl9 = false;
                pokemon.setCaughtBall(pokeball);
            }
        }
        String string3 = this.nature;
        if (string3 != null) {
            String it = string3;
            boolean bl10 = false;
            Nature nature = Natures.INSTANCE.getNature(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(it, null, 1, null));
            if (nature != null) {
                Nature nature2 = nature;
                boolean bl11 = false;
                pokemon.setNature(nature2);
            }
        }
        String string4 = this.ability;
        if (string4 != null) {
            String it = string4;
            boolean bl12 = false;
            Ability ability = this.createAbility(it, pokemon.getForm());
            if (ability != null) {
                Ability p0 = ability;
                boolean bl13 = false;
                pokemon.updateAbility(p0);
            }
        }
        String string5 = this.status;
        if (string5 != null) {
            String it = string5;
            boolean bl14 = false;
            Status status = Statuses.INSTANCE.getStatus(it);
            if (status != null) {
                Status status2 = status;
                boolean bl15 = false;
                if (status2 instanceof PersistentStatus) {
                    pokemon.applyStatus((PersistentStatus)status2);
                }
            }
        }
        Iterable $this$forEach$iv = this.customProperties;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            CustomPokemonProperty it = (CustomPokemonProperty)element$iv;
            boolean bl16 = false;
            it.apply(pokemon);
        }
        IVs iVs = this.ivs;
        if (iVs != null) {
            IVs ivs = iVs;
            boolean bl17 = false;
            Iterable $this$forEach$iv2 = ivs;
            boolean $i$f$forEach2 = false;
            for (Object element$iv : $this$forEach$iv2) {
                stat = (Map.Entry)element$iv;
                boolean bl18 = false;
                pokemon.setIV((Stat)stat.getKey(), ((Number)stat.getValue()).intValue());
            }
        }
        EVs eVs = this.evs;
        if (eVs != null) {
            EVs evs = eVs;
            boolean bl19 = false;
            Iterable $this$forEach$iv3 = evs;
            boolean $i$f$forEach3 = false;
            for (Object element$iv : $this$forEach$iv3) {
                stat = (Map.Entry)element$iv;
                boolean bl20 = false;
                pokemon.setEV((Stat)stat.getKey(), ((Number)stat.getValue()).intValue());
            }
        }
        String string6 = this.teraType;
        if (string6 != null) {
            String it = string6;
            boolean bl21 = false;
            TeraType teraType = TeraTypes.get(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(it, null, 1, null));
            if (teraType != null) {
                TeraType type = teraType;
                boolean bl22 = false;
                pokemon.setTeraType(type);
            }
        }
        Integer n3 = this.dmaxLevel;
        if (n3 != null) {
            int it = ((Number)n3).intValue();
            boolean bl23 = false;
            pokemon.setDmaxLevel(it);
        }
        Boolean bl24 = this.gmaxFactor;
        if (bl24 != null) {
            boolean it = bl24;
            boolean bl25 = false;
            pokemon.setGmaxFactor(it);
        }
        Boolean bl26 = this.tradeable;
        if (bl26 != null) {
            boolean it = bl26;
            boolean bl27 = false;
            pokemon.setTradeable(it);
        }
        OriginalTrainerType originalTrainerType = this.originalTrainerType;
        if (originalTrainerType != null) {
            OriginalTrainerType it = originalTrainerType;
            boolean bl28 = false;
            if (it == OriginalTrainerType.NONE) {
                pokemon.removeOriginalTrainer();
                this.originalTrainer = null;
            }
        }
        String string7 = this.originalTrainer;
        if (string7 != null) {
            String ot = string7;
            boolean bl29 = false;
            OriginalTrainerType originalTrainerType2 = this.originalTrainerType;
            if (originalTrainerType2 == null) {
                originalTrainerType2 = pokemon.getOriginalTrainerType();
            }
            OriginalTrainerType type = originalTrainerType2;
            switch (WhenMappings.$EnumSwitchMapping$0[type.ordinal()]) {
                case 1: {
                    UUID uUID;
                    Object object2;
                    int n4 = ot.length();
                    UUID uUID2 = (3 <= n4 ? n4 < 17 : false) ? ((object2 = DistributionUtilsKt.server()) != null && (object2 = object2.m_129927_()) != null && (object2 = object2.m_10996_(ot)) != null && (object2 = (GameProfile)((Optional)object2).get()) != null ? object2.getId() : null) : (uUID = n4 == 36 ? UUID.fromString(ot) : null);
                    if (uUID == null) break;
                    UUID uuid2 = uUID;
                    boolean bl30 = false;
                    pokemon.setOriginalTrainer(uuid2);
                    break;
                }
                case 2: {
                    pokemon.setOriginalTrainer(ot);
                }
            }
            pokemon.refreshOriginalTrainer();
        }
        pokemon.updateAspects();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean matches(@NotNull Pokemon pokemon) {
        boolean bl;
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        if (!this.commonMatches(pokemon)) return false;
        Iterable $this$none$iv = this.customProperties;
        boolean $i$f$none = false;
        if ($this$none$iv instanceof Collection && ((Collection)$this$none$iv).isEmpty()) {
            return true;
        }
        Iterator iterator = $this$none$iv.iterator();
        do {
            if (!iterator.hasNext()) return true;
            Object element$iv = iterator.next();
            CustomPokemonProperty it = (CustomPokemonProperty)element$iv;
            boolean bl2 = false;
            if (!it.matches(pokemon)) {
                return false;
            }
            bl = false;
        } while (!bl);
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean matches(@NotNull PokemonEntity pokemonEntity) {
        boolean bl;
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
        if (!this.commonMatches(pokemonEntity.getPokemon())) return false;
        Iterable $this$none$iv = this.customProperties;
        boolean $i$f$none = false;
        if ($this$none$iv instanceof Collection && ((Collection)$this$none$iv).isEmpty()) {
            return true;
        }
        Iterator iterator = $this$none$iv.iterator();
        do {
            if (!iterator.hasNext()) return true;
            Object element$iv = iterator.next();
            CustomPokemonProperty it = (CustomPokemonProperty)element$iv;
            boolean bl2 = false;
            if (!it.matches(pokemonEntity)) {
                return false;
            }
            bl = false;
        } while (!bl);
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final boolean commonMatches(Pokemon pokemon) {
        OriginalTrainerType originalTrainerType;
        OriginalTrainerType originalTrainerType2;
        String string;
        Boolean bl;
        Boolean bl2;
        Integer n;
        String string2;
        Map.Entry stat;
        Iterable $this$forEach$iv;
        String string3;
        String string4;
        String string5;
        String string6;
        Integer n2;
        String string7;
        MutableComponent mutableComponent;
        Object object;
        block43: {
            Gender gender;
            Boolean bl3;
            Integer n3 = this.level;
            if (n3 != null) {
                Integer n4 = n3;
                int n5 = ((Number)n4).intValue();
                boolean bl4 = false;
                Integer n6 = object = n5 != pokemon.getLevel() ? n4 : null;
                if (object != null) {
                    int n7 = ((Number)object).intValue();
                    return false;
                }
            }
            if ((bl3 = this.shiny) != null) {
                Boolean bl5 = bl3;
                boolean bl6 = bl5;
                boolean bl7 = false;
                Object object2 = object = bl6 != pokemon.getShiny() ? bl5 : null;
                if (object != null) {
                    boolean bl8 = (Boolean)object;
                    return false;
                }
            }
            if ((gender = this.gender) != null) {
                Gender gender2;
                Gender gender3 = gender2 = gender;
                boolean bl9 = false;
                Object object3 = object = gender3 != pokemon.getGender() ? gender2 : null;
                if (object != null) {
                    Object object4 = object;
                    return false;
                }
            }
            String string8 = this.species;
            if (string8 != null) {
                String string9 = string8;
                boolean bl10 = false;
                try {
                    Species species;
                    if (Intrinsics.areEqual((Object)string9, (Object)"random")) {
                        species = (Species)CollectionsKt.random(PokemonSpecies.INSTANCE.getSpecies(), (Random)((Random)Random.Default));
                    } else {
                        species = PokemonSpecies.INSTANCE.getByIdentifier(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(string9, null, 1, null));
                        if (species == null) break block43;
                    }
                    Species species2 = species;
                    if (!Intrinsics.areEqual((Object)pokemon.getSpecies(), (Object)species2)) {
                        return false;
                    }
                }
                catch (ResourceLocationException e) {
                    return false;
                }
            }
        }
        if ((mutableComponent = this.nickname) != null) {
            MutableComponent mutableComponent2;
            MutableComponent mutableComponent3 = mutableComponent2 = mutableComponent;
            boolean bl11 = false;
            MutableComponent mutableComponent4 = pokemon.getNickname();
            Object object5 = object = !Intrinsics.areEqual((Object)mutableComponent3.getString(), (Object)(mutableComponent4 != null ? mutableComponent4.getString() : null)) ? mutableComponent2 : null;
            if (object != null) {
                Object object6 = object;
                return false;
            }
        }
        if ((string7 = this.form) != null) {
            String string10;
            String string11 = string10 = string7;
            boolean bl12 = false;
            Object object7 = object = !StringsKt.equals((String)string11, (String)pokemon.getForm().getName(), (boolean)true) ? string10 : null;
            if (object != null) {
                Object object8 = object;
                return false;
            }
        }
        if ((n2 = this.friendship) != null) {
            Integer n8 = n2;
            int n9 = ((Number)n8).intValue();
            boolean bl13 = false;
            Integer n10 = object = n9 != pokemon.getFriendship() ? n8 : null;
            if (object != null) {
                int n11 = ((Number)object).intValue();
                return false;
            }
        }
        if ((string6 = this.pokeball) != null) {
            String string12;
            String string13 = string12 = string6;
            boolean bl14 = false;
            Object object9 = object = !Intrinsics.areEqual((Object)string13, (Object)pokemon.getCaughtBall().getName().toString()) ? string12 : null;
            if (object != null) {
                Object object10 = object;
                return false;
            }
        }
        if ((string5 = this.nature) != null) {
            String string14;
            String string15 = string14 = string5;
            boolean bl15 = false;
            Object object11 = object = !Intrinsics.areEqual((Object)string15, (Object)pokemon.getNature().getName().toString()) ? string14 : null;
            if (object != null) {
                Object object12 = object;
                return false;
            }
        }
        if ((string4 = this.ability) != null) {
            String string16;
            String string17 = string16 = string4;
            boolean bl16 = false;
            Object object13 = object = !Intrinsics.areEqual((Object)string17, (Object)pokemon.getAbility().getName()) ? string16 : null;
            if (object != null) {
                Object object14 = object;
                return false;
            }
        }
        if ((string3 = this.status) != null) {
            String string18;
            String string19 = string18 = string3;
            boolean bl17 = false;
            Object object16 = pokemon.getStatus();
            object16 = object = !Intrinsics.areEqual((Object)string19, object16 != null && (object15 = ((PersistentStatusContainer)object16).getStatus()) != null ? ((Status)object16).getShowdownName() : null) ? string18 : null;
            if (object != null) {
                Object object17 = object;
                return false;
            }
        }
        IVs iVs = this.ivs;
        if (iVs != null) {
            $this$forEach$iv = iVs;
            boolean bl18 = false;
            for (Object element$iv : $this$forEach$iv) {
                stat = (Map.Entry)element$iv;
                boolean bl19 = false;
                Integer n12 = pokemon.getIvs().get((Stat)stat.getKey());
                if (n12 == null) {
                    return false;
                }
                if (((Number)stat.getValue()).intValue() == n12.intValue()) continue;
                return false;
            }
        }
        EVs eVs = this.evs;
        if (eVs != null) {
            $this$forEach$iv = eVs;
            boolean bl20 = false;
            for (Object element$iv : $this$forEach$iv) {
                stat = (Map.Entry)element$iv;
                boolean bl21 = false;
                Integer n13 = pokemon.getEvs().get((Stat)stat.getKey());
                if (n13 == null) {
                    return false;
                }
                if (((Number)stat.getValue()).intValue() == n13.intValue()) continue;
                return false;
            }
        }
        if ((string2 = this.teraType) != null) {
            String string20;
            String string21 = string20 = string2;
            boolean bl22 = false;
            Object object18 = object = !Intrinsics.areEqual((Object)ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(string21, null, 1, null), (Object)pokemon.getTeraType().getId()) ? string20 : null;
            if (object != null) {
                Object object19 = object;
                return false;
            }
        }
        if ((n = this.dmaxLevel) != null) {
            Integer n14 = n;
            int n15 = ((Number)n14).intValue();
            boolean bl23 = false;
            Integer n16 = object = n15 != pokemon.getDmaxLevel() ? n14 : null;
            if (object != null) {
                int n17 = ((Number)object).intValue();
                return false;
            }
        }
        if ((bl2 = this.gmaxFactor) != null) {
            Boolean bl24 = bl2;
            boolean bl25 = bl24;
            boolean bl26 = false;
            Object object20 = object = bl25 != pokemon.getGmaxFactor() ? bl24 : null;
            if (object != null) {
                boolean bl27 = (Boolean)object;
                return false;
            }
        }
        if ((bl = this.tradeable) != null) {
            Boolean bl28 = bl;
            boolean bl29 = bl28;
            boolean bl30 = false;
            Object object21 = object = bl29 != pokemon.getTradeable() ? bl28 : null;
            if (object != null) {
                boolean bl31 = (Boolean)object;
                return false;
            }
        }
        if ((string = this.originalTrainer) != null) {
            String string22;
            String string23 = string22 = string;
            boolean bl32 = false;
            Object object22 = object = !Intrinsics.areEqual((Object)string23, (Object)pokemon.getOriginalTrainer()) ? string22 : null;
            if (object != null) {
                Integer n18 = object;
                return false;
            }
        }
        if ((originalTrainerType2 = this.originalTrainerType) == null) return true;
        OriginalTrainerType originalTrainerType3 = originalTrainerType = originalTrainerType2;
        boolean bl33 = false;
        object = originalTrainerType3 != pokemon.getOriginalTrainerType() ? originalTrainerType : null;
        if (object == null) return true;
        Object object23 = object;
        return false;
    }

    public final boolean isSubSetOf(@NotNull PokemonProperties properties2) {
        Map.Entry stat;
        boolean $i$f$forEach;
        Object $this$isSubSetOf_u24lambda_u2476;
        Object object;
        Integer n;
        Object object2;
        block44: {
            int it;
            Intrinsics.checkNotNullParameter((Object)properties2, (String)"properties");
            object2 = this.level;
            if (object2 != null) {
                n = object2;
                it = ((Number)n).intValue();
                boolean bl = false;
                Integer n2 = properties2.level;
                Object object3 = object = n2 == null || it != n2 ? n : null;
                if (object != null) {
                    it = ((Number)object).intValue();
                    boolean bl2 = false;
                    return false;
                }
            }
            if ((object2 = this.shiny) != null) {
                n = object2;
                it = ((Boolean)((Object)n)).booleanValue() ? 1 : 0;
                boolean bl = false;
                Object object4 = object = it != properties2.aspects.contains("shiny") ? n : null;
                if (object != null) {
                    it = ((Boolean)object).booleanValue() ? 1 : 0;
                    boolean bl3 = false;
                    return false;
                }
            }
            if ((object2 = this.gender) != null) {
                Integer it2 = n = object2;
                boolean bl = false;
                Object object5 = object = it2 != properties2.gender ? n : null;
                if (object != null) {
                    it2 = object;
                    boolean bl4 = false;
                    return false;
                }
            }
            String string = this.species;
            if (string != null) {
                $this$isSubSetOf_u24lambda_u2476 = string;
                boolean bl = false;
                try {
                    Species species;
                    if (Intrinsics.areEqual((Object)$this$isSubSetOf_u24lambda_u2476, (Object)"random")) {
                        species = (Species)CollectionsKt.random(PokemonSpecies.INSTANCE.getSpecies(), (Random)((Random)Random.Default));
                    } else {
                        species = PokemonSpecies.INSTANCE.getByIdentifier(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default($this$isSubSetOf_u24lambda_u2476, null, 1, null));
                        if (species == null) break block44;
                    }
                    Species species2 = species;
                    if (!Intrinsics.areEqual((Object)properties2.species, (Object)species2.toString())) {
                        return false;
                    }
                }
                catch (ResourceLocationException species2) {
                }
            }
        }
        if ((object2 = this.nickname) != null) {
            Object it = $this$isSubSetOf_u24lambda_u2476 = object2;
            boolean bl = false;
            MutableComponent mutableComponent = properties2.nickname;
            Object object6 = object = !Intrinsics.areEqual((Object)it.getString(), (Object)(mutableComponent != null ? mutableComponent.getString() : null)) ? $this$isSubSetOf_u24lambda_u2476 : null;
            if (object != null) {
                it = object;
                boolean bl5 = false;
                return false;
            }
        }
        if ((object2 = this.form) != null) {
            Object it = $this$isSubSetOf_u24lambda_u2476 = object2;
            boolean bl = false;
            Object object7 = object = !StringsKt.equals((String)it, (String)properties2.form, (boolean)true) ? $this$isSubSetOf_u24lambda_u2476 : null;
            if (object != null) {
                it = object;
                boolean bl6 = false;
                return false;
            }
        }
        if ((object2 = this.friendship) != null) {
            $this$isSubSetOf_u24lambda_u2476 = object2;
            int it = ((Number)$this$isSubSetOf_u24lambda_u2476).intValue();
            boolean bl = false;
            Integer n3 = properties2.friendship;
            Object object8 = object = n3 == null || it != n3 ? $this$isSubSetOf_u24lambda_u2476 : null;
            if (object != null) {
                it = ((Number)object).intValue();
                boolean bl7 = false;
                return false;
            }
        }
        if ((object2 = this.pokeball) != null) {
            Object it = $this$isSubSetOf_u24lambda_u2476 = object2;
            boolean bl = false;
            Object object9 = object = !Intrinsics.areEqual((Object)it, (Object)properties2.pokeball) ? $this$isSubSetOf_u24lambda_u2476 : null;
            if (object != null) {
                it = object;
                boolean bl8 = false;
                return false;
            }
        }
        if ((object2 = this.nature) != null) {
            Object it = $this$isSubSetOf_u24lambda_u2476 = object2;
            boolean bl = false;
            Object object10 = object = !Intrinsics.areEqual((Object)it, (Object)properties2.nature) ? $this$isSubSetOf_u24lambda_u2476 : null;
            if (object != null) {
                it = object;
                boolean bl9 = false;
                return false;
            }
        }
        if ((object2 = this.ability) != null) {
            Object it = $this$isSubSetOf_u24lambda_u2476 = object2;
            boolean bl = false;
            Object object11 = object = !Intrinsics.areEqual((Object)it, (Object)properties2.ability) ? $this$isSubSetOf_u24lambda_u2476 : null;
            if (object != null) {
                it = object;
                boolean bl10 = false;
                return false;
            }
        }
        if ((object2 = this.status) != null) {
            Object it = $this$isSubSetOf_u24lambda_u2476 = object2;
            boolean bl = false;
            Object object12 = object = !Intrinsics.areEqual((Object)it, (Object)properties2.status) ? $this$isSubSetOf_u24lambda_u2476 : null;
            if (object != null) {
                it = object;
                boolean bl11 = false;
                return false;
            }
        }
        IVs iVs = this.ivs;
        if (iVs != null) {
            IVs ivs = iVs;
            boolean bl = false;
            Iterable $this$forEach$iv = ivs;
            $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                IVs propertiesIVs;
                stat = (Map.Entry)element$iv;
                boolean bl12 = false;
                if (properties2.ivs == null) {
                    return false;
                }
                Integer n4 = propertiesIVs.get((Stat)stat.getKey());
                if (n4 != null && ((Number)stat.getValue()).intValue() == n4.intValue()) continue;
                return false;
            }
        }
        EVs eVs = this.evs;
        if (eVs != null) {
            EVs evs = eVs;
            boolean bl = false;
            Iterable $this$forEach$iv = evs;
            $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                EVs propertiesEVs;
                stat = (Map.Entry)element$iv;
                boolean bl13 = false;
                if (properties2.evs == null) {
                    return false;
                }
                Integer n5 = propertiesEVs.get((Stat)stat.getKey());
                if (n5 != null && ((Number)stat.getValue()).intValue() == n5.intValue()) continue;
                return false;
            }
        }
        if ((object2 = this.teraType) != null) {
            Integer it = n = object2;
            boolean bl = false;
            Object object13 = object = !Intrinsics.areEqual((Object)it, (Object)properties2.teraType) ? n : null;
            if (object != null) {
                it = object;
                boolean bl14 = false;
                return false;
            }
        }
        if ((object2 = this.dmaxLevel) != null) {
            n = object2;
            int it = ((Number)n).intValue();
            boolean bl = false;
            Integer n6 = properties2.dmaxLevel;
            Object object14 = object = n6 == null || it != n6 ? n : null;
            if (object != null) {
                it = ((Number)object).intValue();
                boolean bl15 = false;
                return false;
            }
        }
        if ((object2 = this.gmaxFactor) != null) {
            n = object2;
            boolean it = (Boolean)((Object)n);
            boolean bl = false;
            Object object15 = object = !Intrinsics.areEqual((Object)it, (Object)properties2.gmaxFactor) ? n : null;
            if (object != null) {
                it = (Boolean)object;
                boolean bl16 = false;
                return false;
            }
        }
        if ((object2 = this.tradeable) != null) {
            n = object2;
            boolean it = (Boolean)((Object)n);
            boolean bl = false;
            Object object16 = object = !Intrinsics.areEqual((Object)it, (Object)properties2.tradeable) ? n : null;
            if (object != null) {
                it = (Boolean)object;
                boolean bl17 = false;
                return false;
            }
        }
        if ((object2 = this.originalTrainer) != null) {
            n = object2;
            Object it = n;
            boolean bl = false;
            Object object17 = object = !Intrinsics.areEqual((Object)it, (Object)properties2.originalTrainer) ? n : null;
            if (object != null) {
                it = object;
                boolean bl18 = false;
                return false;
            }
        }
        if ((object2 = this.originalTrainerType) != null) {
            n = object2;
            Object it = n;
            boolean bl = false;
            Object object18 = object = it != properties2.originalTrainerType ? n : null;
            if (object != null) {
                it = object;
                boolean bl19 = false;
                return false;
            }
        }
        return true;
    }

    @NotNull
    public final Pokemon create() {
        Pokemon pokemon = new Pokemon();
        this.apply(pokemon);
        pokemon.initialize();
        this.roll(pokemon);
        return pokemon;
    }

    public final void roll(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        List baseTypes = CollectionsKt.toList(pokemon.getForm().getTypes());
        if (this.shiny == null) {
            pokemon.setShiny(this.checkRate(Cobblemon.INSTANCE.getConfig().getShinyRate()));
        }
        if (this.teraType == null) {
            pokemon.setTeraType(this.checkRate(Cobblemon.INSTANCE.getConfig().getTeraTypeRate()) ? TeraTypes.random(true) : TeraTypes.forElementalType((ElementalType)CollectionsKt.random((Collection)baseTypes, (Random)((Random)Random.Default))));
        }
    }

    @NotNull
    public final PokemonEntity createEntity(@NotNull Level world) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        return new PokemonEntity(world, this.create(), null, 4, null);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final CompoundTag saveToNBT() {
        void $this$mapTo$iv$iv;
        CompoundTag nbt = new CompoundTag();
        String it2 = this.originalString;
        boolean bl = false;
        nbt.m_128359_("OriginalText", it2);
        Integer n = this.level;
        if (n != null) {
            int n2 = ((Number)n).intValue();
            boolean bl2 = false;
            nbt.m_128405_("Level", n2);
        }
        Boolean bl2 = this.shiny;
        if (bl2 != null) {
            boolean bl3 = bl2;
            boolean bl4 = false;
            nbt.m_128379_("Shiny", bl3);
        }
        Gender gender = this.gender;
        if (gender != null) {
            Gender gender2 = gender;
            boolean bl5 = false;
            nbt.m_128359_("Gender", gender2.name());
        }
        String string = this.species;
        if (string != null) {
            String string2 = string;
            boolean bl6 = false;
            nbt.m_128359_("SpeciesText", string2);
        }
        MutableComponent mutableComponent = this.nickname;
        if (mutableComponent != null) {
            MutableComponent mutableComponent2 = mutableComponent;
            boolean bl7 = false;
            nbt.m_128359_("Nickname", Component.Serializer.m_130703_((Component)((Component)mutableComponent2)));
        }
        String string3 = this.form;
        if (string3 != null) {
            String string4 = string3;
            boolean bl8 = false;
            nbt.m_128359_("FormId", string4);
        }
        Integer n3 = this.friendship;
        if (n3 != null) {
            int n4 = ((Number)n3).intValue();
            boolean bl9 = false;
            nbt.m_128405_("Friendship", n4);
        }
        String string5 = this.pokeball;
        if (string5 != null) {
            String string6 = string5;
            boolean bl10 = false;
            nbt.m_128359_("CaughtBall", string6);
        }
        String string7 = this.nature;
        if (string7 != null) {
            String string8 = string7;
            boolean bl11 = false;
            nbt.m_128359_("Nature", string8);
        }
        String string9 = this.ability;
        if (string9 != null) {
            String string10 = string9;
            boolean bl12 = false;
            nbt.m_128359_("Ability", string10);
        }
        String string11 = this.status;
        if (string11 != null) {
            String string12 = string11;
            boolean bl13 = false;
            nbt.m_128359_("StatusName", string12);
        }
        IVs iVs = this.ivs;
        if (iVs != null) {
            IVs iVs2 = iVs;
            boolean bl14 = false;
            nbt.m_128365_("IVs", (Tag)iVs2.saveToNBT(new CompoundTag()));
        }
        EVs eVs = this.evs;
        if (eVs != null) {
            EVs eVs2 = eVs;
            boolean bl15 = false;
            nbt.m_128365_("EVs", (Tag)eVs2.saveToNBT(new CompoundTag()));
        }
        String string13 = this.teraType;
        if (string13 != null) {
            String string14 = string13;
            boolean bl16 = false;
            nbt.m_128359_("TeraType", string14);
        }
        Integer n5 = this.dmaxLevel;
        if (n5 != null) {
            int n6 = ((Number)n5).intValue();
            boolean bl17 = false;
            nbt.m_128405_("DmaxLevel", n6);
        }
        Boolean bl4 = this.gmaxFactor;
        if (bl4 != null) {
            boolean bl5 = bl4;
            boolean bl19 = false;
            nbt.m_128379_("GmaxFactor", bl5);
        }
        Boolean bl6 = this.tradeable;
        if (bl6 != null) {
            boolean bl7 = bl6;
            boolean bl21 = false;
            nbt.m_128379_("Tradeable", bl7);
        }
        OriginalTrainerType originalTrainerType = this.originalTrainerType;
        if (originalTrainerType != null) {
            OriginalTrainerType originalTrainerType2 = originalTrainerType;
            boolean bl22 = false;
            nbt.m_128405_("PokemonOriginalTrainerType", originalTrainerType2.ordinal());
        }
        String string15 = this.originalTrainer;
        if (string15 != null) {
            String string16 = string15;
            boolean bl23 = false;
            nbt.m_128359_("PokemonOriginalTrainer", string16);
        }
        ListTag custom = new ListTag();
        Iterable $this$map$iv = this.customProperties;
        boolean bl8 = false;
        Iterable bl23 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it20;
            CustomPokemonProperty customPokemonProperty = (CustomPokemonProperty)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl24 = false;
            collection.add(StringTag.m_129297_((String)it20.asString()));
        }
        Iterable $this$forEach$iv = (List)destination$iv$iv;
        boolean bl9 = false;
        for (Object element$iv : $this$forEach$iv) {
            StringTag it21 = (StringTag)element$iv;
            boolean bl25 = false;
            custom.add((Object)it21);
        }
        nbt.m_128365_("CustomProperties", (Tag)custom);
        return nbt;
    }

    @NotNull
    public final PokemonProperties loadFromNBT(@NotNull CompoundTag tag) {
        OriginalTrainerType originalTrainerType;
        EVs eVs;
        Gender gender;
        Intrinsics.checkNotNullParameter((Object)tag, (String)"tag");
        String string = tag.m_128461_("OriginalText");
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"tag.getString(DataKeys.P\u2026PROPERTIES_ORIGINAL_TEXT)");
        this.originalString = string;
        this.level = tag.m_128441_("Level") ? Integer.valueOf(tag.m_128451_("Level")) : null;
        Boolean bl = this.shiny = tag.m_128441_("Shiny") ? Boolean.valueOf(tag.m_128471_("Shiny")) : null;
        if (tag.m_128441_("Gender")) {
            String string2 = tag.m_128461_("Gender");
            Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"tag.getString(DataKeys.POKEMON_GENDER)");
            gender = Gender.valueOf(string2);
        } else {
            gender = null;
        }
        this.gender = gender;
        this.species = tag.m_128441_("SpeciesText") ? tag.m_128461_("SpeciesText") : null;
        this.nickname = tag.m_128441_("Nickname") ? Component.Serializer.m_130701_((String)tag.m_128461_("Nickname")) : null;
        this.form = tag.m_128441_("FormId") ? tag.m_128461_("FormId") : null;
        this.friendship = tag.m_128441_("Friendship") ? Integer.valueOf(tag.m_128451_("Friendship")) : null;
        this.pokeball = tag.m_128441_("CaughtBall") ? tag.m_128461_("CaughtBall") : null;
        this.nature = tag.m_128441_("Nature") ? tag.m_128461_("Nature") : null;
        this.ability = tag.m_128441_("Ability") ? tag.m_128461_("Ability") : null;
        String string3 = this.status = tag.m_128441_("StatusName") ? tag.m_128461_("StatusName") : null;
        if (tag.m_128441_("IVs")) {
            PokemonStats pokemonStats;
            IVs iVs = this.ivs;
            if (iVs != null) {
                CompoundTag compoundTag = tag.m_128469_("IVs");
                Intrinsics.checkNotNullExpressionValue((Object)compoundTag, (String)"tag.getCompound(DataKeys.POKEMON_IVS)");
                pokemonStats = iVs.loadFromNBT(compoundTag);
            } else {
                pokemonStats = null;
            }
            v8 = (IVs)pokemonStats;
        } else {
            v8 = this.ivs = null;
        }
        if (tag.m_128441_("EVs")) {
            PokemonStats pokemonStats;
            EVs eVs2 = this.evs;
            if (eVs2 != null) {
                CompoundTag compoundTag = tag.m_128469_("EVs");
                Intrinsics.checkNotNullExpressionValue((Object)compoundTag, (String)"tag.getCompound(DataKeys.POKEMON_EVS)");
                pokemonStats = eVs2.loadFromNBT(compoundTag);
            } else {
                pokemonStats = null;
            }
            eVs = (EVs)pokemonStats;
        } else {
            eVs = null;
        }
        this.evs = eVs;
        this.teraType = tag.m_128441_("TeraType") ? tag.m_128461_("TeraType") : null;
        this.dmaxLevel = tag.m_128441_("DmaxLevel") ? Integer.valueOf(tag.m_128451_("DmaxLevel")) : null;
        this.gmaxFactor = tag.m_128441_("GmaxFactor") ? Boolean.valueOf(tag.m_128471_("GmaxFactor")) : null;
        Boolean bl2 = this.tradeable = tag.m_128441_("Tradeable") ? Boolean.valueOf(tag.m_128471_("Tradeable")) : null;
        if (tag.m_128441_("PokemonOriginalTrainerType")) {
            String string4 = tag.m_128461_("PokemonOriginalTrainerType");
            Intrinsics.checkNotNullExpressionValue((Object)string4, (String)"tag.getString(DataKeys.P\u2026ON_ORIGINAL_TRAINER_TYPE)");
            originalTrainerType = OriginalTrainerType.valueOf(string4);
        } else {
            originalTrainerType = null;
        }
        this.originalTrainerType = originalTrainerType;
        this.originalTrainer = tag.m_128441_("PokemonOriginalTrainer") ? tag.m_128461_("PokemonOriginalTrainer") : null;
        ListTag custom = tag.m_128437_("CustomProperties", 8);
        Intrinsics.checkNotNullExpressionValue((Object)custom, (String)"custom");
        Iterable $this$forEach$iv = (Iterable)custom;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Tag it = (Tag)element$iv;
            boolean bl3 = false;
            String string5 = it.m_7916_();
            Intrinsics.checkNotNullExpressionValue((Object)string5, (String)"it.asString()");
            this.customProperties.addAll((Collection<CustomPokemonProperty>)bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties$Companion.parse$default((Companion)PokemonProperties.Companion, (String)string5, null, null, (int)6, null).customProperties);
        }
        this.updateAspects();
        return this;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final JsonObject saveToJSON() {
        void $this$mapTo$iv$iv;
        JsonObject json = new JsonObject();
        String it2 = this.originalString;
        boolean bl = false;
        json.addProperty("OriginalText", it2);
        Integer n = this.level;
        if (n != null) {
            int n2 = ((Number)n).intValue();
            boolean bl2 = false;
            json.addProperty("Level", (Number)n2);
        }
        Boolean bl2 = this.shiny;
        if (bl2 != null) {
            boolean bl3 = bl2;
            boolean bl4 = false;
            json.addProperty("Shiny", Boolean.valueOf(bl3));
        }
        Gender gender = this.gender;
        if (gender != null) {
            Gender gender2 = gender;
            boolean bl5 = false;
            json.addProperty("Gender", gender2.name());
        }
        String string = this.species;
        if (string != null) {
            String string2 = string;
            boolean bl6 = false;
            json.addProperty("SpeciesText", string2);
        }
        MutableComponent mutableComponent = this.nickname;
        if (mutableComponent != null) {
            MutableComponent mutableComponent2 = mutableComponent;
            boolean bl7 = false;
            json.addProperty("Nickname", Component.Serializer.m_130703_((Component)((Component)mutableComponent2)));
        }
        String string3 = this.form;
        if (string3 != null) {
            String string4 = string3;
            boolean bl8 = false;
            json.addProperty("FormId", string4);
        }
        Integer n3 = this.friendship;
        if (n3 != null) {
            int n4 = ((Number)n3).intValue();
            boolean bl9 = false;
            json.addProperty("Friendship", (Number)n4);
        }
        String string5 = this.pokeball;
        if (string5 != null) {
            String string6 = string5;
            boolean bl10 = false;
            json.addProperty("CaughtBall", string6);
        }
        String string7 = this.nature;
        if (string7 != null) {
            String string8 = string7;
            boolean bl11 = false;
            json.addProperty("Nature", string8);
        }
        String string9 = this.ability;
        if (string9 != null) {
            String string10 = string9;
            boolean bl12 = false;
            json.addProperty("Ability", string10);
        }
        String string11 = this.status;
        if (string11 != null) {
            String string12 = string11;
            boolean bl13 = false;
            json.addProperty("StatusName", string12);
        }
        IVs iVs = this.ivs;
        if (iVs != null) {
            IVs iVs2 = iVs;
            boolean bl14 = false;
            json.add("IVs", (JsonElement)iVs2.saveToJSON(new JsonObject()));
        }
        EVs eVs = this.evs;
        if (eVs != null) {
            EVs eVs2 = eVs;
            boolean bl15 = false;
            json.add("EVs", (JsonElement)eVs2.saveToJSON(new JsonObject()));
        }
        String string13 = this.teraType;
        if (string13 != null) {
            String string14 = string13;
            boolean bl16 = false;
            json.addProperty("TeraType", string14);
        }
        Integer n5 = this.dmaxLevel;
        if (n5 != null) {
            int n6 = ((Number)n5).intValue();
            boolean bl17 = false;
            json.addProperty("DmaxLevel", (Number)n6);
        }
        Boolean bl4 = this.gmaxFactor;
        if (bl4 != null) {
            boolean bl5 = bl4;
            boolean bl19 = false;
            json.addProperty("GmaxFactor", Boolean.valueOf(bl5));
        }
        Boolean bl6 = this.tradeable;
        if (bl6 != null) {
            boolean bl7 = bl6;
            boolean bl21 = false;
            json.addProperty("Tradeable", Boolean.valueOf(bl7));
        }
        OriginalTrainerType originalTrainerType = this.originalTrainerType;
        if (originalTrainerType != null) {
            OriginalTrainerType originalTrainerType2 = originalTrainerType;
            boolean bl22 = false;
            json.addProperty("PokemonOriginalTrainerType", originalTrainerType2.name());
        }
        String string15 = this.originalTrainer;
        if (string15 != null) {
            String string16 = string15;
            boolean bl23 = false;
            json.addProperty("PokemonOriginalTrainer", string16);
        }
        JsonArray custom = new JsonArray();
        Iterable $this$map$iv = this.customProperties;
        boolean bl8 = false;
        Iterable bl23 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it20;
            CustomPokemonProperty customPokemonProperty = (CustomPokemonProperty)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl24 = false;
            collection.add(it20.asString());
        }
        Iterable $this$forEach$iv = (List)destination$iv$iv;
        boolean bl9 = false;
        for (Object element$iv : $this$forEach$iv) {
            String it21 = (String)element$iv;
            boolean bl25 = false;
            custom.add(it21);
        }
        json.add("CustomProperties", (JsonElement)custom);
        return json;
    }

    @NotNull
    public final PokemonProperties loadFromJSON(@NotNull JsonObject json) {
        JsonArray custom;
        OriginalTrainerType originalTrainerType;
        MutableComponent mutableComponent;
        Gender gender;
        Object it;
        PokemonProperties pokemonProperties;
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        JsonElement jsonElement = json.get("OriginalText");
        String string = jsonElement != null ? jsonElement.getAsString() : null;
        if (string == null) {
            string = "";
        }
        this.originalString = string;
        JsonElement jsonElement2 = json.get("Level");
        this.level = jsonElement2 != null ? Integer.valueOf(jsonElement2.getAsInt()) : null;
        JsonElement jsonElement3 = json.get("Shiny");
        this.shiny = jsonElement3 != null ? Boolean.valueOf(jsonElement3.getAsBoolean()) : null;
        PokemonProperties pokemonProperties2 = this;
        Object object = json.get("Gender");
        if (object != null && (object = object.getAsString()) != null) {
            JsonElement jsonElement4 = object;
            pokemonProperties = pokemonProperties2;
            boolean bl = false;
            gender = Gender.valueOf((String)it);
            pokemonProperties2 = pokemonProperties;
        } else {
            gender = null;
        }
        pokemonProperties2.gender = gender;
        JsonElement jsonElement5 = json.get("SpeciesText");
        this.species = jsonElement5 != null ? jsonElement5.getAsString() : null;
        PokemonProperties pokemonProperties3 = this;
        Object object2 = json.get("Nickname");
        if (object2 != null && (object2 = object2.getAsString()) != null) {
            it = object2;
            pokemonProperties = pokemonProperties3;
            boolean bl = false;
            mutableComponent = Component.Serializer.m_130701_((String)it);
            pokemonProperties3 = pokemonProperties;
        } else {
            mutableComponent = null;
        }
        pokemonProperties3.nickname = mutableComponent;
        JsonElement jsonElement6 = json.get("FormId");
        this.form = jsonElement6 != null ? jsonElement6.getAsString() : null;
        JsonElement jsonElement7 = json.get("Friendship");
        this.friendship = jsonElement7 != null ? Integer.valueOf(jsonElement7.getAsInt()) : null;
        JsonElement jsonElement8 = json.get("CaughtBall");
        this.pokeball = jsonElement8 != null ? jsonElement8.getAsString() : null;
        JsonElement jsonElement9 = json.get("Nature");
        this.nature = jsonElement9 != null ? jsonElement9.getAsString() : null;
        JsonElement jsonElement10 = json.get("Ability");
        this.ability = jsonElement10 != null ? jsonElement10.getAsString() : null;
        JsonElement jsonElement11 = json.get("StatusName");
        this.status = jsonElement11 != null ? jsonElement11.getAsString() : null;
        IVs iVs = this.ivs;
        if (iVs != null) {
            JsonObject jsonObject = json.getAsJsonObject("IVs");
            Intrinsics.checkNotNullExpressionValue((Object)jsonObject, (String)"json.getAsJsonObject(DataKeys.POKEMON_IVS)");
            iVs.loadFromJSON(jsonObject);
        }
        EVs eVs = this.evs;
        if (eVs != null) {
            JsonObject jsonObject = json.getAsJsonObject("EVs");
            Intrinsics.checkNotNullExpressionValue((Object)jsonObject, (String)"json.getAsJsonObject(DataKeys.POKEMON_EVS)");
            eVs.loadFromJSON(jsonObject);
        }
        JsonElement jsonElement12 = json.get("TeraType");
        this.teraType = jsonElement12 != null ? jsonElement12.getAsString() : null;
        JsonElement jsonElement13 = json.get("DmaxLevel");
        this.dmaxLevel = jsonElement13 != null ? Integer.valueOf(jsonElement13.getAsInt()) : null;
        JsonElement jsonElement14 = json.get("GmaxFactor");
        this.gmaxFactor = jsonElement14 != null ? Boolean.valueOf(jsonElement14.getAsBoolean()) : null;
        JsonElement jsonElement15 = json.get("Tradeable");
        this.tradeable = jsonElement15 != null ? Boolean.valueOf(jsonElement15.getAsBoolean()) : null;
        PokemonProperties pokemonProperties4 = this;
        Object object3 = json.get("PokemonOriginalTrainerType");
        if (object3 != null && (object3 = object3.getAsString()) != null) {
            it = object3;
            pokemonProperties = pokemonProperties4;
            boolean bl = false;
            originalTrainerType = OriginalTrainerType.valueOf((String)it);
            pokemonProperties4 = pokemonProperties;
        } else {
            originalTrainerType = null;
        }
        pokemonProperties4.originalTrainerType = originalTrainerType;
        JsonElement jsonElement16 = json.get("PokemonOriginalTrainer");
        this.originalTrainer = jsonElement16 != null ? jsonElement16.getAsString() : null;
        JsonElement jsonElement17 = json.get("CustomProperties");
        JsonArray jsonArray = custom = jsonElement17 != null ? jsonElement17.getAsJsonArray() : null;
        if (jsonArray != null) {
            Iterable $this$forEach$iv = (Iterable)jsonArray;
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                JsonElement it2 = (JsonElement)element$iv;
                boolean bl = false;
                String string2 = it2.getAsString();
                Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"it.asString");
                this.customProperties.addAll((Collection<CustomPokemonProperty>)bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties$Companion.parse$default((Companion)PokemonProperties.Companion, (String)string2, null, null, (int)6, null).customProperties);
            }
        }
        this.updateAspects();
        return this;
    }

    @NotNull
    public final String asString(@NotNull String separator) {
        Map.Entry stat;
        Iterable $this$forEach$iv;
        String it;
        Intrinsics.checkNotNullParameter((Object)separator, (String)"separator");
        List pieces = new ArrayList();
        String string = this.species;
        if (string != null) {
            it = string;
            boolean bl = false;
            pieces.add(it);
        }
        MutableComponent mutableComponent = this.nickname;
        if (mutableComponent != null) {
            it = mutableComponent;
            boolean bl = false;
            pieces.add("nickname=$" + it.getString());
        }
        String string2 = this.form;
        if (string2 != null) {
            it = string2;
            boolean bl = false;
            pieces.add("form=" + it);
        }
        Integer n = this.level;
        if (n != null) {
            int it2 = ((Number)n).intValue();
            boolean bl = false;
            pieces.add("level=" + it2);
        }
        Boolean bl = this.shiny;
        if (bl != null) {
            boolean it3 = bl;
            boolean bl2 = false;
            pieces.add("shiny=" + it3);
        }
        Gender gender = this.gender;
        if (gender != null) {
            Gender it4 = gender;
            boolean bl3 = false;
            pieces.add("gender=" + it4);
        }
        Integer n2 = this.friendship;
        if (n2 != null) {
            int it5 = ((Number)n2).intValue();
            boolean bl4 = false;
            pieces.add("friendship=" + it5);
        }
        String string3 = this.pokeball;
        if (string3 != null) {
            String it6 = string3;
            boolean bl5 = false;
            pieces.add("pokeball=" + it6);
        }
        String string4 = this.nature;
        if (string4 != null) {
            String it7 = string4;
            boolean bl6 = false;
            pieces.add("nature=" + it7);
        }
        String string5 = this.ability;
        if (string5 != null) {
            String it8 = string5;
            boolean bl7 = false;
            pieces.add("ability=" + it8);
        }
        String string6 = this.status;
        if (string6 != null) {
            String it9 = string6;
            boolean bl8 = false;
            pieces.add("status=" + it9);
        }
        IVs iVs = this.ivs;
        if (iVs != null) {
            $this$forEach$iv = iVs;
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                stat = (Map.Entry)element$iv;
                boolean bl9 = false;
                pieces.add(stat.getKey() + "_iv=" + stat.getValue());
            }
        }
        EVs eVs = this.evs;
        if (eVs != null) {
            $this$forEach$iv = eVs;
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                stat = (Map.Entry)element$iv;
                boolean bl10 = false;
                pieces.add(stat.getKey() + "_ev=" + stat.getValue());
            }
        }
        String string7 = this.teraType;
        if (string7 != null) {
            String it10 = string7;
            boolean bl11 = false;
            pieces.add("tera_type=" + it10);
        }
        Integer n3 = this.dmaxLevel;
        if (n3 != null) {
            int it11 = ((Number)n3).intValue();
            boolean bl12 = false;
            pieces.add("dmax_level=" + it11);
        }
        Boolean bl13 = this.gmaxFactor;
        if (bl13 != null) {
            boolean it12 = bl13;
            boolean bl14 = false;
            pieces.add("gmax_factor=" + it12);
        }
        Boolean bl15 = this.tradeable;
        if (bl15 != null) {
            boolean it13 = bl15;
            boolean bl16 = false;
            pieces.add("tradeable=" + it13);
        }
        OriginalTrainerType originalTrainerType = this.originalTrainerType;
        if (originalTrainerType != null) {
            OriginalTrainerType it14 = originalTrainerType;
            boolean bl17 = false;
            pieces.add("originaltrainertype=" + it14.name());
        }
        String string8 = this.originalTrainer;
        if (string8 != null) {
            String it15 = string8;
            boolean bl18 = false;
            pieces.add("originaltrainer=" + it15);
        }
        Iterable $this$forEach$iv2 = this.customProperties;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv2) {
            CustomPokemonProperty it16 = (CustomPokemonProperty)element$iv;
            boolean bl19 = false;
            pieces.add(it16.asString());
        }
        return CollectionsKt.joinToString$default((Iterable)pieces, (CharSequence)separator, null, null, (int)0, null, null, (int)62, null);
    }

    public static /* synthetic */ String asString$default(PokemonProperties pokemonProperties, String string, int n, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: asString");
        }
        if ((n & 1) != 0) {
            string = " ";
        }
        return pokemonProperties.asString(string);
    }

    @NotNull
    public final VariableStruct asStruct() {
        VariableStruct struct2;
        block4: {
            struct2 = new VariableStruct();
            String string = this.species;
            if (string != null) {
                String it = string;
                boolean bl = false;
                struct2.setDirectly("species", new StringValue(it));
            }
            Integer n = this.level;
            if (n != null) {
                int it = ((Number)n).intValue();
                boolean bl = false;
                struct2.setDirectly("level", new DoubleValue(it));
            }
            Boolean bl = this.shiny;
            if (bl != null) {
                boolean it = bl;
                boolean bl2 = false;
                struct2.setDirectly("shiny", new DoubleValue(it));
            }
            Gender gender = this.gender;
            if (gender != null) {
                Gender it = gender;
                boolean bl3 = false;
                struct2.setDirectly("gender", new StringValue(it.name()));
            }
            Integer n2 = this.friendship;
            if (n2 == null) break block4;
            int it = ((Number)n2).intValue();
            boolean bl4 = false;
            struct2.setDirectly("friendship", new DoubleValue(it));
        }
        return struct2;
    }

    public final void updateAspects() {
        Set aspects = new LinkedHashSet();
        Iterable $this$forEach$iv = AspectProvider.Companion.getProviders();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            AspectProvider it = (AspectProvider)element$iv;
            boolean bl = false;
            aspects.addAll((Collection)it.provide(this));
        }
        this.aspects = CollectionsKt.toSet((Iterable)aspects);
    }

    @NotNull
    public final PokemonProperties copy() {
        return new PokemonProperties().loadFromJSON(this.saveToJSON());
    }

    private final boolean checkRate(float $this$checkRate) {
        return $this$checkRate >= 1.0f && Random.Default.nextFloat() < 1.0f / $this$checkRate;
    }

    private final Ability createAbility(String id, FormData form2) {
        Object v1;
        AbilityTemplate ability;
        block3: {
            AbilityTemplate abilityTemplate = Abilities.INSTANCE.get(id);
            if (abilityTemplate == null) {
                return null;
            }
            ability = abilityTemplate;
            Iterable $this$firstOrNull$iv = form2.getAbilities();
            boolean $i$f$firstOrNull = false;
            for (Object element$iv : $this$firstOrNull$iv) {
                PotentialAbility potential = (PotentialAbility)element$iv;
                boolean bl = false;
                if (!Intrinsics.areEqual((Object)potential.getTemplate(), (Object)ability)) continue;
                v1 = element$iv;
                break block3;
            }
            v1 = null;
        }
        PotentialAbility potentialAbility = v1;
        if (potentialAbility == null) {
            return ability.create(true);
        }
        PotentialAbility potentialAbility2 = potentialAbility;
        return potentialAbility2.getTemplate().create(false);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u001c\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\n\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b+\u0010,JI\u0010\b\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\u00032\u001a\u0010\u0005\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u00030\u00022\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006H\u0002\u00a2\u0006\u0004\b\b\u0010\tJ+\u0010\u000e\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u00042\b\b\u0002\u0010\u000b\u001a\u00020\u00042\b\b\u0002\u0010\f\u001a\u00020\u0004H\u0007\u00a2\u0006\u0004\b\u000e\u0010\u000fJ;\u0010\u0011\u001a\u0004\u0018\u00010\u00102\u001a\u0010\u0005\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u00030\u00022\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006H\u0002\u00a2\u0006\u0004\b\u0011\u0010\u0012J-\u0010\u0013\u001a\u0004\u0018\u00010\u00042\u001a\u0010\u0005\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u00030\u0002H\u0002\u00a2\u0006\u0004\b\u0013\u0010\u0014JQ\u0010\u001a\u001a\u0004\u0018\u00010\u00042\u001a\u0010\u0005\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u00030\u00022\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00040\u00152\u0014\u0010\u0019\u001a\u0010\u0012\u0004\u0012\u00020\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0017H\u0002\u00a2\u0006\u0004\b\u001a\u0010\u001bJ;\u0010\u001d\u001a\u0004\u0018\u00010\u001c2\u001a\u0010\u0005\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u00030\u00022\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006H\u0002\u00a2\u0006\u0004\b\u001d\u0010\u001eJ;\u0010\u001f\u001a\u0004\u0018\u00010\u00042\u001a\u0010\u0005\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u00030\u00022\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006H\u0002\u00a2\u0006\u0004\b\u001f\u0010 J-\u0010!\u001a\u0004\u0018\u00010\u00042\u001a\u0010\u0005\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u00030\u0002H\u0002\u00a2\u0006\u0004\b!\u0010\u0014JQ\u0010\"\u001a\u0004\u0018\u00010\u00042\u001a\u0010\u0005\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u00030\u00022\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00040\u00152\u0014\u0010\u0019\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0017H\u0002\u00a2\u0006\u0004\b\"\u0010\u001bJ;\u0010$\u001a\u0004\u0018\u00010#2\u001a\u0010\u0005\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u00030\u00022\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006H\u0002\u00a2\u0006\u0004\b$\u0010%Ji\u0010)\u001a\u0004\u0018\u00018\u0000\"\u0004\b\u0000\u0010&*\b\u0012\u0004\u0012\u00028\u00000\u00062\u001a\u0010\u0005\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u00030\u00022\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u00062\b\b\u0002\u0010'\u001a\u00020\u00102\u0012\u0010(\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00040\u0017H\u0002\u00a2\u0006\u0004\b)\u0010*\u00a8\u0006-"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties$Companion;", "", "", "Lkotlin/Pair;", "", "keyPairs", "", "labels", "getMatchedKeyPair", "(Ljava/util/List;Ljava/lang/Iterable;)Lkotlin/Pair;", "string", "delimiter", "assigner", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "parse", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "", "parseBooleanProperty", "(Ljava/util/List;Ljava/lang/Iterable;)Ljava/lang/Boolean;", "parseForm", "(Ljava/util/List;)Ljava/lang/String;", "", "validKeys", "Lkotlin/Function1;", "Lnet/minecraft/resources/ResourceLocation;", "valueFetcher", "parseIdentifierOfRegistry", "(Ljava/util/List;Ljava/util/List;Lkotlin/jvm/functions/Function1;)Ljava/lang/String;", "", "parseIntProperty", "(Ljava/util/List;Ljava/lang/Iterable;)Ljava/lang/Integer;", "parsePlayerProperty", "(Ljava/util/List;Ljava/lang/Iterable;)Ljava/lang/String;", "parseSpeciesIdentifier", "parseStringOfRegistry", "Lnet/minecraft/network/chat/MutableComponent;", "parseText", "(Ljava/util/List;Ljava/lang/Iterable;)Lnet/minecraft/network/chat/MutableComponent;", "T", "labelsOptional", "stringer", "parsePropertyOfCollection", "(Ljava/lang/Iterable;Ljava/util/List;Ljava/lang/Iterable;ZLkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "<init>", "()V", "common"})
    @SourceDebugExtension(value={"SMAP\nPokemonProperties.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonProperties.kt\ncom/cobblemon/mod/common/api/pokemon/PokemonProperties$Companion\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,675:1\n1603#2,9:676\n1855#2:685\n1856#2:688\n1612#2:689\n1855#2,2:690\n288#2:692\n1747#2,3:693\n289#2:696\n223#2,2:697\n1#3:686\n1#3:687\n*S KotlinDebug\n*F\n+ 1 PokemonProperties.kt\ncom/cobblemon/mod/common/api/pokemon/PokemonProperties$Companion\n*L\n62#1:676,9\n62#1:685\n62#1:688\n62#1:689\n103#1:690,2\n252#1:692\n252#1:693,3\n252#1:696\n255#1:697,2\n62#1:687\n*E\n"})
    public static final class Companion {
        private Companion() {
        }

        /*
         * WARNING - void declaration
         */
        @JvmOverloads
        @NotNull
        public final PokemonProperties parse(@NotNull String string, @NotNull String delimiter, @NotNull String assigner) {
            void $this$mapNotNullTo$iv$iv;
            Object[] $this$mapNotNull$iv;
            Intrinsics.checkNotNullParameter((Object)string, (String)"string");
            Intrinsics.checkNotNullParameter((Object)delimiter, (String)"delimiter");
            Intrinsics.checkNotNullParameter((Object)assigner, (String)"assigner");
            PokemonProperties props = new PokemonProperties();
            props.setOriginalString(string);
            List<Pair<String, String>> keyPairs = StringExtensionsKt.splitMap(string, delimiter, assigner);
            Iterable iterable = CustomPokemonProperty.Companion.getProperties();
            PokemonProperties pokemonProperties = props;
            boolean $i$f$mapNotNull22 = false;
            void var8_10 = $this$mapNotNull$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$mapNotNullTo = false;
            void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
            boolean $i$f$forEach = false;
            Iterator iterator = $this$forEach$iv$iv$iv.iterator();
            while (iterator.hasNext()) {
                Object v4;
                Object object;
                CustomPokemonPropertyType property;
                block10: {
                    Object element$iv$iv$iv;
                    Object element$iv$iv = element$iv$iv$iv = iterator.next();
                    boolean bl = false;
                    property = (CustomPokemonPropertyType)element$iv$iv;
                    boolean bl2 = false;
                    Iterable iterable2 = keyPairs;
                    for (Object object2 : iterable2) {
                        Pair it = (Pair)object2;
                        boolean bl3 = false;
                        Iterable<String> iterable3 = property.getKeys();
                        String string2 = ((String)it.getFirst()).toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
                        if (!CollectionsKt.contains(iterable3, (Object)string2)) continue;
                        object = object2;
                        break block10;
                    }
                    object = null;
                }
                Pair matchedKeyPair = (Pair)object;
                if (matchedKeyPair == null) {
                    if (!property.getNeedsKey()) {
                        Object v3;
                        Object savedProperty;
                        block11: {
                            Object object2;
                            savedProperty = null;
                            object2 = keyPairs;
                            Iterator iterator2 = object2.iterator();
                            while (iterator2.hasNext()) {
                                Object t = iterator2.next();
                                Pair keyPair = (Pair)t;
                                boolean bl = false;
                                savedProperty = property.fromString((String)keyPair.getSecond());
                                if (!(savedProperty != null)) continue;
                                v3 = t;
                                break block11;
                            }
                            v3 = null;
                        }
                        Pair keyPair = v3;
                        if (keyPair != null) {
                            keyPairs.remove(keyPair);
                        }
                        v4 = savedProperty;
                    } else {
                        v4 = null;
                    }
                } else {
                    keyPairs.remove(matchedKeyPair);
                    v4 = property.fromString((String)matchedKeyPair.getSecond());
                }
                if (v4 == null) continue;
                Object it$iv$iv = v4;
                boolean bl = false;
                destination$iv$iv.add(it$iv$iv);
            }
            pokemonProperties.setCustomProperties(CollectionsKt.toMutableList((Collection)((List)destination$iv$iv)));
            props.setGender((Gender)((Object)this.parsePropertyOfCollection(ArraysKt.toList((Object[])Gender.values()), keyPairs, CollectionsKt.listOf((Object)"gender"), true, parse.2.INSTANCE)));
            Object[] $i$f$mapNotNull22 = new String[]{"level", "lvl", "l"};
            Integer n = this.parseIntProperty(keyPairs, CollectionsKt.listOf((Object[])$i$f$mapNotNull22));
            props.setLevel(n != null ? Integer.valueOf(RangesKt.coerceIn((int)n, (int)1, (int)Cobblemon.INSTANCE.getConfig().getMaxPokemonLevel())) : null);
            $this$mapNotNull$iv = new String[]{"shiny", "s"};
            props.setShiny(this.parseBooleanProperty(keyPairs, CollectionsKt.listOf((Object[])$this$mapNotNull$iv)));
            props.setSpecies(this.parseSpeciesIdentifier(keyPairs));
            props.setForm(this.parseForm(keyPairs));
            Integer n2 = this.parseIntProperty(keyPairs, CollectionsKt.listOf((Object)"friendship"));
            props.setFriendship(n2 != null ? Integer.valueOf(RangesKt.coerceIn((int)n2, (int)0, (int)Cobblemon.INSTANCE.getConfig().getMaxPokemonFriendship())) : null);
            props.setPokeball(this.parseIdentifierOfRegistry(keyPairs, CollectionsKt.listOf((Object)"pokeball"), (Function1<? super ResourceLocation, String>)((Function1)parse.3.INSTANCE)));
            props.setNature(this.parseIdentifierOfRegistry(keyPairs, CollectionsKt.listOf((Object)"nature"), (Function1<? super ResourceLocation, String>)((Function1)parse.4.INSTANCE)));
            props.setAbility(this.parseStringOfRegistry(keyPairs, CollectionsKt.listOf((Object)"ability"), (Function1<? super String, String>)((Function1)parse.5.INSTANCE)));
            props.setStatus(this.parseStringOfRegistry(keyPairs, CollectionsKt.listOf((Object)"status"), (Function1<? super String, String>)((Function1)parse.6.INSTANCE)));
            $this$mapNotNull$iv = new String[]{"nickname", "nick"};
            props.setNickname(this.parseText(keyPairs, CollectionsKt.listOf((Object[])$this$mapNotNull$iv)));
            $this$mapNotNull$iv = new String[]{"tera_type", "tera"};
            props.setTeraType(this.parseStringOfRegistry(keyPairs, CollectionsKt.listOf((Object[])$this$mapNotNull$iv), (Function1<? super String, String>)((Function1)parse.7.INSTANCE)));
            $i$f$mapNotNull22 = new String[]{"dmax_level", "dmax"};
            Integer n3 = this.parseIntProperty(keyPairs, CollectionsKt.listOf((Object[])$i$f$mapNotNull22));
            props.setDmaxLevel(n3 != null ? Integer.valueOf(RangesKt.coerceIn((int)n3, (int)0, (int)Cobblemon.INSTANCE.getConfig().getMaxDynamaxLevel())) : null);
            $this$mapNotNull$iv = new String[]{"gmax_factor", "gmax"};
            props.setGmaxFactor(this.parseBooleanProperty(keyPairs, CollectionsKt.listOf((Object[])$this$mapNotNull$iv)));
            $this$mapNotNull$iv = new String[]{"tradeable", "tradable"};
            props.setTradeable(this.parseBooleanProperty(keyPairs, CollectionsKt.listOf((Object[])$this$mapNotNull$iv)));
            $this$mapNotNull$iv = new String[]{"originaltrainertype", "ottype"};
            props.setOriginalTrainerType((OriginalTrainerType)((Object)this.parsePropertyOfCollection(ArraysKt.toList((Object[])OriginalTrainerType.values()), keyPairs, CollectionsKt.listOf((Object[])$this$mapNotNull$iv), true, parse.8.INSTANCE)));
            $this$mapNotNull$iv = new String[]{"originaltrainer", "ot"};
            props.setOriginalTrainer(this.parsePlayerProperty(keyPairs, CollectionsKt.listOf((Object[])$this$mapNotNull$iv)));
            IVs maybeIVs = new IVs();
            EVs maybeEVs = new EVs();
            Iterable $this$forEach$iv = Stats.Companion.getPERMANENT();
            boolean $i$f$forEach2 = false;
            for (Object element$iv : $this$forEach$iv) {
                int it;
                String statName;
                Stat stat = (Stat)element$iv;
                boolean bl = false;
                Intrinsics.checkNotNullExpressionValue((Object)stat.toString().toLowerCase(Locale.ROOT), (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
                Integer n4 = Companion.parseIntProperty(keyPairs, CollectionsKt.listOf((Object)(statName + "_iv")));
                if (n4 != null) {
                    it = RangesKt.coerceIn((int)n4, (int)0, (int)31);
                    boolean bl4 = false;
                    maybeIVs.set(stat, it);
                }
                Integer n5 = Companion.parseIntProperty(keyPairs, CollectionsKt.listOf((Object)(statName + "_ev")));
                if (n5 == null) continue;
                it = RangesKt.coerceIn((int)n5, (int)0, (int)252);
                boolean bl5 = false;
                maybeEVs.set(stat, it);
            }
            props.setIvs(maybeIVs);
            props.setEvs(maybeEVs);
            props.updateAspects();
            return props;
        }

        public /* synthetic */ PokemonProperties parse$default(Companion companion, String string, String string2, String string3, int n, Object object) {
            if ((n & 2) != 0) {
                string2 = " ";
            }
            if ((n & 4) != 0) {
                string3 = "=";
            }
            return companion.parse(string, string2, string3);
        }

        private final Pair<String, String> getMatchedKeyPair(List<Pair<String, String>> keyPairs, Iterable<String> labels) {
            Pair<String, String> pair;
            block1: {
                List<Pair<String, String>> list = keyPairs;
                ListIterator<Pair<String, String>> listIterator = list.listIterator(list.size());
                while (listIterator.hasPrevious()) {
                    Pair<String, String> pair2;
                    Pair<String, String> it = pair2 = listIterator.previous();
                    boolean bl = false;
                    if (!CollectionsKt.contains(labels, (Object)it.getFirst())) continue;
                    pair = pair2;
                    break block1;
                }
                pair = null;
            }
            return (Pair)pair;
        }

        private final MutableComponent parseText(List<Pair<String, String>> keyPairs, Iterable<String> labels) {
            Pair<String, String> pair = this.getMatchedKeyPair(keyPairs, labels);
            if (pair == null) {
                return null;
            }
            Pair<String, String> matchingKeyPair = pair;
            String value2 = (String)matchingKeyPair.getSecond();
            CharSequence charSequence = value2;
            return charSequence == null || StringsKt.isBlank((CharSequence)charSequence) ? null : Component.m_237115_((String)value2);
        }

        private final Integer parseIntProperty(List<Pair<String, String>> keyPairs, Iterable<String> labels) {
            Pair<String, String> pair = this.getMatchedKeyPair(keyPairs, labels);
            if (pair == null) {
                return null;
            }
            Pair<String, String> matchingKeyPair = pair;
            String value2 = (String)matchingKeyPair.getSecond();
            return value2 == null || !MiscUtils.isInt(value2) ? null : Integer.valueOf(Integer.parseInt(value2));
        }

        private final String parseIdentifierOfRegistry(List<Pair<String, String>> keyPairs, List<String> validKeys, Function1<? super ResourceLocation, String> valueFetcher) {
            String string;
            String string2;
            block7: {
                block6: {
                    Pair<String, String> pair = this.getMatchedKeyPair(keyPairs, (Iterable<String>)validKeys);
                    if (pair == null) {
                        return null;
                    }
                    Pair<String, String> matched = pair;
                    string2 = (String)matched.getSecond();
                    if (string2 == null) break block6;
                    String string3 = string2.toLowerCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
                    string2 = string3;
                    if (string3 != null) break block7;
                }
                return null;
            }
            String value2 = string2;
            try {
                ResourceLocation identifier = ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(value2, null, 1, null);
                string = (String)valueFetcher.invoke((Object)identifier);
            }
            catch (ResourceLocationException resourceLocationException) {
                string = null;
            }
            return string;
        }

        private final String parseStringOfRegistry(List<Pair<String, String>> keyPairs, List<String> validKeys, Function1<? super String, String> valueFetcher) {
            String string;
            String string2;
            block7: {
                block6: {
                    Pair<String, String> pair = this.getMatchedKeyPair(keyPairs, (Iterable<String>)validKeys);
                    if (pair == null) {
                        return null;
                    }
                    Pair<String, String> matched = pair;
                    string2 = (String)matched.getSecond();
                    if (string2 == null) break block6;
                    String string3 = string2.toLowerCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
                    string2 = string3;
                    if (string3 != null) break block7;
                }
                return null;
            }
            String value2 = string2;
            try {
                string = (String)valueFetcher.invoke((Object)value2);
            }
            catch (ResourceLocationException resourceLocationException) {
                string = null;
            }
            return string;
        }

        /*
         * Unable to fully structure code
         */
        private final String parseSpeciesIdentifier(List<Pair<String, String>> keyPairs) {
            block16: {
                block17: {
                    block19: {
                        block18: {
                            matched = this.getMatchedKeyPair(keyPairs, CollectionsKt.listOf((Object)"species"));
                            if (matched == null) break block17;
                            v0 = (String)matched.getSecond();
                            if (v0 == null) break block18;
                            p0 = v0;
                            $i$a$-let-PokemonProperties$Companion$parseSpeciesIdentifier$value$1 = false;
                            v1 = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties$Companion.parseSpeciesIdentifier$cleanSpeciesName(p0);
                            v0 = v1;
                            if (v1 != null) break block19;
                        }
                        return null;
                    }
                    value = v0;
                    v2 = value.toLowerCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue((Object)v2, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
                    if (!Intrinsics.areEqual((Object)v2, (Object)"random")) {
                        try {
                            v3 = PokemonSpecies.INSTANCE.getByIdentifier(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(value, null, 1, null));
                            if (v3 == null) {
                                return null;
                            }
                            species = v3;
                            return Intrinsics.areEqual((Object)species.getResourceIdentifier().m_135827_(), (Object)"cobblemon") != false ? species.getResourceIdentifier().m_135815_() : species.getResourceIdentifier().toString();
                        }
                        catch (ResourceLocationException e) {
                            return null;
                        }
                    }
                    return "random";
                }
                species = null;
                var6_12 = keyPairs;
                for (T var8_6 : var6_12) {
                    block15: {
                        pair = (Pair)var8_6;
                        $i$a$-find-PokemonProperties$Companion$parseSpeciesIdentifier$keyPair$1 = false;
                        if (pair.getSecond() != null) ** GOTO lbl-1000
                        v4 = ((String)pair.getFirst()).toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue((Object)v4, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
                        if (Intrinsics.areEqual((Object)v4, (Object)"random")) {
                            v5 = "random";
                        } else lbl-1000:
                        // 2 sources

                        {
                            try {
                                identifier = ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties$Companion.parseSpeciesIdentifier$cleanSpeciesName((String)pair.getFirst()), null, 1, null);
                                if (PokemonSpecies.INSTANCE.getByIdentifier(identifier) == null) {
                                    v6 = false;
                                    break block15;
                                }
                                var11_15 = Intrinsics.areEqual((Object)found.getResourceIdentifier().m_135827_(), (Object)"cobblemon") != false ? found.getResourceIdentifier().m_135815_() : found.getResourceIdentifier().toString();
                            }
                            catch (ResourceLocationException e) {
                                v6 = false;
                                break block15;
                            }
                            v5 = var11_15;
                        }
                        species = v5;
                        v6 = species != null;
                    }
                    if (!v6) continue;
                    v7 = var8_6;
                    break block16;
                }
                v7 = null;
            }
            keyPair = v7;
            if (keyPair != null) {
                keyPairs.remove(keyPair);
            }
            return species;
        }

        private final String parseForm(List<Pair<String, String>> keyPairs) {
            Pair<String, String> pair = this.getMatchedKeyPair(keyPairs, CollectionsKt.listOf((Object)"form"));
            if (pair == null) {
                return null;
            }
            Pair<String, String> matchingKeyPair = pair;
            keyPairs.remove(matchingKeyPair);
            return (String)matchingKeyPair.getSecond();
        }

        /*
         * Unable to fully structure code
         */
        private final Boolean parseBooleanProperty(List<Pair<String, String>> keyPairs, Iterable<String> labels) {
            block12: {
                block11: {
                    v0 = this.getMatchedKeyPair(keyPairs, labels);
                    if (v0 == null) {
                        return null;
                    }
                    matchingKeyPair = v0;
                    keyPairs.remove(matchingKeyPair);
                    v1 = (String)matchingKeyPair.getSecond();
                    if (v1 != null) {
                        v2 = v1.toLowerCase(Locale.ROOT);
                        v3 = v2;
                        Intrinsics.checkNotNullExpressionValue((Object)v2, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
                    } else {
                        v3 = null;
                    }
                    v4 = var4_4 = v3;
                    if (v4 == null) ** GOTO lbl31
                    switch (v4.hashCode()) {
                        case 3521: {
                            if (!var4_4.equals("no")) {
                                ** break;
                            }
                            break block11;
                        }
                        case 119527: {
                            if (var4_4.equals("yes")) break;
                            ** break;
                        }
                        case 3569038: {
                            if (var4_4.equals("true")) break;
                            ** break;
                        }
                        case 97196323: {
                            if (!var4_4.equals("false")) {
                                ** break;
                            }
                            break block11;
                        }
lbl31:
                        // 1 sources

                        v5 = true;
                        break block12;
                    }
                    v5 = true;
                    break block12;
                }
                v5 = false;
                break block12;
lbl38:
                // 5 sources

                v5 = null;
            }
            return v5;
        }

        private final String parsePlayerProperty(List<Pair<String, String>> keyPairs, Iterable<String> labels) {
            Pair<String, String> pair = this.getMatchedKeyPair(keyPairs, labels);
            if (pair == null) {
                return null;
            }
            Pair<String, String> matchingKeyPair = pair;
            keyPairs.remove(matchingKeyPair);
            if (matchingKeyPair.getSecond() == null) {
                return null;
            }
            Object object = matchingKeyPair.getSecond();
            Intrinsics.checkNotNull((Object)object);
            String string = (String)object;
            int n = string.length();
            return (3 <= n ? n < 17 : false) || string.length() == 36 && MiscUtils.isUuid(string) ? string : null;
        }

        private final <T> T parsePropertyOfCollection(Iterable<? extends T> $this$parsePropertyOfCollection, List<Pair<String, String>> keyPairs, Iterable<String> labels, boolean labelsOptional, Function1<? super T, String> stringer) {
            Pair<String, String> matchingKeyPair = this.getMatchedKeyPair(keyPairs, labels);
            if (matchingKeyPair != null) {
                Object v3;
                String value2;
                String string = (String)matchingKeyPair.getSecond();
                if (string != null) {
                    String string2 = string.toLowerCase(Locale.ROOT);
                    v2 = string2;
                    Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
                } else {
                    v2 = value2 = null;
                }
                if (value2 == null) {
                    v3 = null;
                } else {
                    Object matched;
                    block14: {
                        Object v5;
                        Iterable<T> iterable = $this$parsePropertyOfCollection;
                        Iterator<T> iterator = iterable.iterator();
                        while (iterator.hasNext()) {
                            T t;
                            T it = t = iterator.next();
                            boolean bl = false;
                            String string3 = ((String)stringer.invoke(it)).toLowerCase(Locale.ROOT);
                            Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
                            if (!Intrinsics.areEqual((Object)string3, (Object)value2)) continue;
                            v5 = t;
                            break block14;
                        }
                        v5 = matched = null;
                    }
                    if (matched != null) {
                        keyPairs.remove(matchingKeyPair);
                    }
                    v3 = matched;
                }
                return v3;
            }
            if (labelsOptional) {
                Object v8;
                block16: {
                    Iterable $this$firstOrNull$iv = keyPairs;
                    boolean $i$f$firstOrNull = false;
                    for (Object element$iv : $this$firstOrNull$iv) {
                        boolean bl;
                        block15: {
                            Pair pair = (Pair)element$iv;
                            boolean bl2 = false;
                            Iterable<T> $this$any$iv = $this$parsePropertyOfCollection;
                            boolean $i$f$any = false;
                            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                                bl = false;
                            } else {
                                Iterator<T> iterator = $this$any$iv.iterator();
                                while (iterator.hasNext()) {
                                    T element$iv2;
                                    T it = element$iv2 = iterator.next();
                                    boolean bl3 = false;
                                    String string = ((String)stringer.invoke(it)).toLowerCase(Locale.ROOT);
                                    Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
                                    if (!Intrinsics.areEqual((Object)string, (Object)pair.getFirst())) continue;
                                    bl = true;
                                    break block15;
                                }
                                bl = false;
                            }
                        }
                        if (!bl) continue;
                        v8 = element$iv;
                        break block16;
                    }
                    v8 = null;
                }
                Pair keyPair = v8;
                if (keyPair != null) {
                    Object element$iv;
                    block17: {
                        keyPairs.remove(keyPair);
                        Iterable<T> $this$first$iv = $this$parsePropertyOfCollection;
                        boolean $i$f$first = false;
                        Iterator<Object> iterator = $this$first$iv.iterator();
                        while (iterator.hasNext()) {
                            Object it = element$iv = iterator.next();
                            boolean bl = false;
                            String string = ((String)stringer.invoke(it)).toLowerCase(Locale.ROOT);
                            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
                            if (!Intrinsics.areEqual((Object)string, (Object)keyPair.getFirst())) continue;
                            break block17;
                        }
                        throw new NoSuchElementException("Collection contains no element matching the predicate.");
                    }
                    return element$iv;
                }
            }
            return null;
        }

        static /* synthetic */ Object parsePropertyOfCollection$default(Companion companion, Iterable iterable, List list, Iterable iterable2, boolean bl, Function1 function1, int n, Object object) {
            if ((n & 4) != 0) {
                bl = false;
            }
            return companion.parsePropertyOfCollection(iterable, list, iterable2, bl, function1);
        }

        @JvmOverloads
        @NotNull
        public final PokemonProperties parse(@NotNull String string, @NotNull String delimiter) {
            Intrinsics.checkNotNullParameter((Object)string, (String)"string");
            Intrinsics.checkNotNullParameter((Object)delimiter, (String)"delimiter");
            return bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties$Companion.parse$default(this, string, delimiter, null, 4, null);
        }

        @JvmOverloads
        @NotNull
        public final PokemonProperties parse(@NotNull String string) {
            Intrinsics.checkNotNullParameter((Object)string, (String)"string");
            return bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties$Companion.parse$default(this, string, null, null, 6, null);
        }

        private static final String parseSpeciesIdentifier$cleanSpeciesName(String string) {
            String string2 = string.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
            CharSequence charSequence = string2;
            Regex regex = new Regex("[^a-z0-9_:]");
            String string3 = "";
            return regex.replace(charSequence, string3);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[OriginalTrainerType.values().length];
            try {
                nArray[OriginalTrainerType.PLAYER.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[OriginalTrainerType.NPC.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

