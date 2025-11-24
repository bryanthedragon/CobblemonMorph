/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.Regex
 *  kotlin.text.RegexOption
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.NbtIo
 *  net.minecraft.nbt.Tag
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.conversions;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.Abilities;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.AbilityTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.PotentialAbility;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Moves;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.PokeBalls;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.Natures;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.SidemodExperienceSource;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stats;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.conversions.CobblemonConverter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.conversions.ReforgedConversion;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.PokeBall;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.EVs;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Gender;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.IVs;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Nature;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.Regex;
import kotlin.text.RegexOption;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0002&'B\u000f\u0012\u0006\u0010!\u001a\u00020\u001b\u00a2\u0006\u0004\b$\u0010%J5\u0010\t\u001a\u0004\u0018\u00018\u0000\"\u0004\b\u0000\u0010\u00032\u0006\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00052\u000e\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00018\u00000\u0007\u00a2\u0006\u0004\b\t\u0010\nJA\u0010\u0012\u001a\u0004\u0018\u00018\u0001\"\b\b\u0000\u0010\f*\u00020\u000b\"\u000e\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u00028\u00000\r2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00010\u000e2\u0006\u0010\u0011\u001a\u00020\u0010H\u0016\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u001f\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0004\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u001f\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0004\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\u000f\u0010\u001c\u001a\u00020\u001bH\u0016\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u0017\u0010\u001f\u001a\u00020\u001e2\u0006\u0010\u0004\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u001f\u0010 R\u0017\u0010!\u001a\u00020\u001b8\u0006\u00a2\u0006\f\n\u0004\b!\u0010\"\u001a\u0004\b#\u0010\u001d\u00a8\u0006("}, d2={"Lcom/cobblemon/mod/common/api/storage/adapter/conversions/ReforgedConversion;", "Lcom/cobblemon/mod/common/api/storage/adapter/conversions/CobblemonConverter;", "Lnet/minecraft/nbt/CompoundTag;", "T", "nbt", "", "key", "Lcom/cobblemon/mod/common/api/storage/adapter/conversions/ReforgedConversion$Translator;", "translator", "find", "(Lnet/minecraft/nbt/CompoundTag;Ljava/lang/String;Lcom/cobblemon/mod/common/api/storage/adapter/conversions/ReforgedConversion$Translator;)Ljava/lang/Object;", "Lcom/cobblemon/mod/common/api/storage/StorePosition;", "E", "Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "Ljava/lang/Class;", "storeClass", "Ljava/util/UUID;", "uuid", "load", "(Ljava/lang/Class;Ljava/util/UUID;)Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "user", "Lcom/cobblemon/mod/common/api/storage/party/PlayerPartyStore;", "party", "(Ljava/util/UUID;Lnet/minecraft/nbt/CompoundTag;)Lcom/cobblemon/mod/common/api/storage/party/PlayerPartyStore;", "Lcom/cobblemon/mod/common/api/storage/pc/PCStore;", "pc", "(Ljava/util/UUID;Lnet/minecraft/nbt/CompoundTag;)Lcom/cobblemon/mod/common/api/storage/pc/PCStore;", "Ljava/nio/file/Path;", "root", "()Ljava/nio/file/Path;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "translate", "(Lnet/minecraft/nbt/CompoundTag;)Lcom/cobblemon/mod/common/pokemon/Pokemon;", "base", "Ljava/nio/file/Path;", "getBase", "<init>", "(Ljava/nio/file/Path;)V", "ReforgedNatures", "Translator", "common"})
@SourceDebugExtension(value={"SMAP\nReforgedConversion.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ReforgedConversion.kt\ncom/cobblemon/mod/common/api/storage/adapter/conversions/ReforgedConversion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,192:1\n1#2:193\n2624#3,3:194\n1855#3,2:197\n1855#3,2:199\n*S KotlinDebug\n*F\n+ 1 ReforgedConversion.kt\ncom/cobblemon/mod/common/api/storage/adapter/conversions/ReforgedConversion\n*L\n103#1:194,3\n126#1:197,2\n129#1:199,2\n*E\n"})
public final class ReforgedConversion
implements CobblemonConverter<CompoundTag> {
    @NotNull
    private final Path base;

    public ReforgedConversion(@NotNull Path base) {
        Intrinsics.checkNotNullParameter((Object)base, (String)"base");
        this.base = base;
    }

    @NotNull
    public final Path getBase() {
        return this.base;
    }

    @Override
    @NotNull
    public Path root() {
        Path path = this.base.resolve("data").resolve("pokemon");
        Intrinsics.checkNotNullExpressionValue((Object)path, (String)"this.base.resolve(\"data\").resolve(\"pokemon\")");
        return path;
    }

    @Override
    @Nullable
    public <E extends StorePosition, T extends PokemonStore<E>> T load(@NotNull Class<T> storeClass, @NotNull UUID uuid2) {
        Intrinsics.checkNotNullParameter(storeClass, (String)"storeClass");
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        String string = storeClass.getSimpleName();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"storeClass.simpleName");
        String string2 = string.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
        String extension = Intrinsics.areEqual((Object)string2, (Object)"playerpartystore") ? "pk" : "comp";
        Path target = this.root().resolve(uuid2 + "." + extension);
        Intrinsics.checkNotNullExpressionValue((Object)target, (String)"target");
        if (!this.exists(target)) {
            return null;
        }
        CompoundTag nbt = NbtIo.m_128953_((File)target.toFile());
        if (nbt != null) {
            PokemonStore pokemonStore = Intrinsics.areEqual((Object)extension, (Object)"pk") ? (PokemonStore)this.party(uuid2, nbt) : (PokemonStore)this.pc(uuid2, nbt);
            Intrinsics.checkNotNull((Object)pokemonStore, (String)"null cannot be cast to non-null type T of bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.conversions.ReforgedConversion.load");
            return (T)pokemonStore;
        }
        return null;
    }

    @Override
    @NotNull
    public PlayerPartyStore party(@NotNull UUID user, @NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)user, (String)"user");
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        PlayerPartyStore result = new PlayerPartyStore(user);
        for (int x = 0; x < 6; ++x) {
            String key = "party" + x;
            if (!nbt.m_128441_(key)) continue;
            CompoundTag compoundTag = nbt.m_128469_(key);
            Intrinsics.checkNotNullExpressionValue((Object)compoundTag, (String)"nbt.getCompound(key)");
            result.add(this.translate(compoundTag));
        }
        return result;
    }

    @Override
    @NotNull
    public PCStore pc(@NotNull UUID user, @NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)user, (String)"user");
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        PCStore result = new PCStore(user);
        int box = 0;
        while (nbt.m_128441_("BoxNumber" + box)) {
            CompoundTag storage = nbt.m_128469_("BoxNumber" + box);
            for (int x = 0; x < 30; ++x) {
                if (!storage.m_128441_("pc" + x)) continue;
                CompoundTag compoundTag = storage.m_128469_("pc" + x);
                Intrinsics.checkNotNullExpressionValue((Object)compoundTag, (String)"storage.getCompound(\"pc$x\")");
                Pokemon pokemon = this.translate(compoundTag);
                if (result.add(pokemon)) continue;
                result.getBackupStore().add(pokemon);
            }
            ++box;
        }
        result.tryRestoreBackedUpPokemon();
        return result;
    }

    @Override
    @NotNull
    public Pokemon translate(@NotNull CompoundTag nbt) {
        PokeBall pokeBall;
        Object element$iv;
        Object object;
        Iterator $this$none$iv;
        boolean bl;
        FormData formData;
        Object v1;
        Object it;
        PokemonProperties.Companion companion;
        Pokemon result;
        block16: {
            Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
            result = new Pokemon();
            result.setUuid(nbt.m_128342_("UUID"));
            Species species = PokemonSpecies.getByPokedexNumber$default(PokemonSpecies.INSTANCE, nbt.m_128451_("ndex"), null, 2, null);
            if (species == null) {
                throw new IllegalStateException("Failed to read a species with pokedex identifier " + nbt.m_128451_("ndex"));
            }
            result.setSpecies(species);
            Iterable iterable = result.getSpecies().getForms();
            companion = PokemonProperties.Companion;
            Iterable iterable2 = iterable;
            for (Object object2 : iterable2) {
                it = (FormData)object2;
                boolean bl2 = false;
                if (!Intrinsics.areEqual((Object)((FormData)it).getName(), (Object)nbt.m_128461_("Variant"))) continue;
                v1 = object2;
                break block16;
            }
            v1 = null;
        }
        if ((formData = (FormData)v1) == null) {
            formData = result.getSpecies().getStandardForm();
        }
        PokemonProperties.Companion.parse$default(companion, formData.getName(), null, null, 6, null).apply(result);
        result.setGender(Gender.values()[nbt.m_128451_("Gender")]);
        Boolean bl3 = (Boolean)this.find(nbt, "IsShiny", translate.2.INSTANCE);
        if (bl3 != null) {
            bl = bl3;
        } else {
            String string = (String)this.find(nbt, "palette", translate.3.INSTANCE);
            Boolean bl4 = string != null ? Boolean.valueOf(string.equals("shiny")) : null;
            bl = bl4 != null ? bl4 : false;
        }
        result.setShiny(bl);
        result.setLevel(nbt.m_128451_("Level"));
        result.addExperience(new SidemodExperienceSource("Reforged"), nbt.m_128451_("EXP"));
        Pokemon.setFriendship$default(result, nbt.m_128451_("Friendship"), false, 2, null);
        String string = nbt.m_128461_("Ability");
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"nbt.getString(\"Ability\")");
        AbilityTemplate abilityTemplate = Abilities.INSTANCE.get(string);
        if (abilityTemplate != null) {
            boolean bl5;
            Pokemon bl2;
            block17: {
                Object object2;
                AbilityTemplate template = abilityTemplate;
                boolean bl6 = false;
                object2 = result.getForm().getAbilities();
                it = template;
                bl2 = result;
                boolean $i$f$none = false;
                if ($this$none$iv instanceof Collection && ((Collection)((Object)$this$none$iv)).isEmpty()) {
                    bl5 = true;
                } else {
                    object = $this$none$iv.iterator();
                    while (object.hasNext()) {
                        Object element$iv2 = object.next();
                        PotentialAbility it2 = (PotentialAbility)element$iv2;
                        boolean bl7 = false;
                        if (!Intrinsics.areEqual((Object)it2.getTemplate(), (Object)template)) continue;
                        bl5 = false;
                        break block17;
                    }
                    bl5 = true;
                }
            }
            boolean bl8 = bl5;
            bl2.updateAbility(((AbilityTemplate)it).create(bl8));
        }
        String string2 = ReforgedNatures.values()[nbt.m_128451_("Nature")].name().toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
        Nature nature = Natures.INSTANCE.getNature(new ResourceLocation(string2));
        if (nature == null) {
            nature = Natures.INSTANCE.getRandomNature();
        }
        result.setNature(nature);
        String string3 = ReforgedNatures.values()[nbt.m_128451_("MintNature")].name().toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
        result.setMintedNature(Natures.INSTANCE.getNature(new ResourceLocation(string3)));
        result.setCurrentHealth(nbt.m_128451_("Health"));
        IVs ivs = new IVs();
        ivs.set(Stats.HP, nbt.m_128451_("IVHP"));
        ivs.set(Stats.ATTACK, nbt.m_128451_("IVAttack"));
        ivs.set(Stats.DEFENCE, nbt.m_128451_("IVDefense"));
        ivs.set(Stats.SPECIAL_ATTACK, nbt.m_128451_("IVSpAtt"));
        ivs.set(Stats.SPECIAL_DEFENCE, nbt.m_128451_("IVSpDef"));
        ivs.set(Stats.SPEED, nbt.m_128451_("IVSpeed"));
        EVs evs = new EVs();
        evs.set(Stats.HP, nbt.m_128451_("EVHP"));
        evs.set(Stats.ATTACK, nbt.m_128451_("EVAttack"));
        evs.set(Stats.DEFENCE, nbt.m_128451_("EVDefense"));
        evs.set(Stats.SPECIAL_ATTACK, nbt.m_128451_("EVSpecialAttack"));
        evs.set(Stats.SPECIAL_DEFENCE, nbt.m_128451_("EVSpecialDefense"));
        evs.set(Stats.SPEED, nbt.m_128451_("EVSpeed"));
        Object $this$forEach$iv = ivs;
        boolean $i$f$forEach = false;
        $this$none$iv = $this$forEach$iv.iterator();
        while ($this$none$iv.hasNext()) {
            element$iv = $this$none$iv.next();
            Map.Entry stat = (Map.Entry)element$iv;
            boolean bl9 = false;
            result.setIV((Stat)stat.getKey(), ((Number)stat.getValue()).intValue());
        }
        $this$forEach$iv = evs;
        $i$f$forEach = false;
        $this$none$iv = $this$forEach$iv.iterator();
        while ($this$none$iv.hasNext()) {
            element$iv = $this$none$iv.next();
            Map.Entry stat = (Map.Entry)element$iv;
            boolean bl10 = false;
            result.setEV((Stat)stat.getKey(), ((Number)stat.getValue()).intValue());
        }
        for (Tag move : nbt.m_128437_("Moveset", 10)) {
            Intrinsics.checkNotNull((Object)move, (String)"null cannot be cast to non-null type net.minecraft.nbt.NbtCompound");
            CompoundTag compound = (CompoundTag)move;
            String string4 = compound.m_128461_("MoveID");
            Intrinsics.checkNotNullExpressionValue((Object)string4, (String)"compound.getString(\"MoveID\")");
            CharSequence stat = string4;
            Regex bl10 = new Regex("[-\\s]", RegexOption.IGNORE_CASE);
            object = "";
            String id = bl10.replace(stat, (String)object);
            int pp = compound.m_128451_("MovePP");
            int level = compound.m_128451_("MovePPLevel");
            String string5 = id.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue((Object)string5, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
            MoveTemplate template = Moves.INSTANCE.getByNameOrDummy(string5);
            result.getMoveSet().add(template.create(pp, level));
        }
        String ball2 = (String)this.find(nbt, "CaughtBall", translate.ball.1.INSTANCE);
        if (ball2 != null) {
            pokeBall = PokeBalls.INSTANCE.getPokeBall(new ResourceLocation(ball2));
            if (pokeBall == null) {
                pokeBall = PokeBalls.INSTANCE.getPOKE_BALL();
            }
        } else {
            pokeBall = PokeBalls.INSTANCE.getPOKE_BALL();
        }
        result.setCaughtBall(pokeBall);
        return result;
    }

    @Nullable
    public final <T> T find(@NotNull CompoundTag nbt, @NotNull String key, @NotNull Translator<? extends T> translator) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        Intrinsics.checkNotNullParameter((Object)key, (String)"key");
        Intrinsics.checkNotNullParameter(translator, (String)"translator");
        if (nbt.m_128441_(key)) {
            return translator.from(nbt, key);
        }
        return null;
    }

    @Override
    public boolean exists(@NotNull Path target) {
        return CobblemonConverter.DefaultImpls.exists(this, target);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u001c\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001aj\u0002\b\u001bj\u0002\b\u001c\u00a8\u0006\u001d"}, d2={"Lcom/cobblemon/mod/common/api/storage/adapter/conversions/ReforgedConversion$ReforgedNatures;", "", "<init>", "(Ljava/lang/String;I)V", "HARDY", "SERIOUS", "DOCILE", "BASHFUL", "QUIRKY", "LONELY", "BRAVE", "ADAMANT", "NAUGHTY", "BOLD", "RELAXED", "IMPISH", "LAX", "TIMID", "HASTY", "JOLLY", "NAIVE", "MODEST", "MILD", "QUIET", "RASH", "CALM", "GENTLE", "SASSY", "CAREFUL", "common"})
    public static final class ReforgedNatures
    extends Enum<ReforgedNatures> {
        public static final /* enum */ ReforgedNatures HARDY = new ReforgedNatures();
        public static final /* enum */ ReforgedNatures SERIOUS = new ReforgedNatures();
        public static final /* enum */ ReforgedNatures DOCILE = new ReforgedNatures();
        public static final /* enum */ ReforgedNatures BASHFUL = new ReforgedNatures();
        public static final /* enum */ ReforgedNatures QUIRKY = new ReforgedNatures();
        public static final /* enum */ ReforgedNatures LONELY = new ReforgedNatures();
        public static final /* enum */ ReforgedNatures BRAVE = new ReforgedNatures();
        public static final /* enum */ ReforgedNatures ADAMANT = new ReforgedNatures();
        public static final /* enum */ ReforgedNatures NAUGHTY = new ReforgedNatures();
        public static final /* enum */ ReforgedNatures BOLD = new ReforgedNatures();
        public static final /* enum */ ReforgedNatures RELAXED = new ReforgedNatures();
        public static final /* enum */ ReforgedNatures IMPISH = new ReforgedNatures();
        public static final /* enum */ ReforgedNatures LAX = new ReforgedNatures();
        public static final /* enum */ ReforgedNatures TIMID = new ReforgedNatures();
        public static final /* enum */ ReforgedNatures HASTY = new ReforgedNatures();
        public static final /* enum */ ReforgedNatures JOLLY = new ReforgedNatures();
        public static final /* enum */ ReforgedNatures NAIVE = new ReforgedNatures();
        public static final /* enum */ ReforgedNatures MODEST = new ReforgedNatures();
        public static final /* enum */ ReforgedNatures MILD = new ReforgedNatures();
        public static final /* enum */ ReforgedNatures QUIET = new ReforgedNatures();
        public static final /* enum */ ReforgedNatures RASH = new ReforgedNatures();
        public static final /* enum */ ReforgedNatures CALM = new ReforgedNatures();
        public static final /* enum */ ReforgedNatures GENTLE = new ReforgedNatures();
        public static final /* enum */ ReforgedNatures SASSY = new ReforgedNatures();
        public static final /* enum */ ReforgedNatures CAREFUL = new ReforgedNatures();
        private static final /* synthetic */ ReforgedNatures[] $VALUES;

        public static ReforgedNatures[] values() {
            return (ReforgedNatures[])$VALUES.clone();
        }

        public static ReforgedNatures valueOf(String value2) {
            return Enum.valueOf(ReforgedNatures.class, value2);
        }

        static {
            $VALUES = reforgedNaturesArray = new ReforgedNatures[]{ReforgedNatures.HARDY, ReforgedNatures.SERIOUS, ReforgedNatures.DOCILE, ReforgedNatures.BASHFUL, ReforgedNatures.QUIRKY, ReforgedNatures.LONELY, ReforgedNatures.BRAVE, ReforgedNatures.ADAMANT, ReforgedNatures.NAUGHTY, ReforgedNatures.BOLD, ReforgedNatures.RELAXED, ReforgedNatures.IMPISH, ReforgedNatures.LAX, ReforgedNatures.TIMID, ReforgedNatures.HASTY, ReforgedNatures.JOLLY, ReforgedNatures.NAIVE, ReforgedNatures.MODEST, ReforgedNatures.MILD, ReforgedNatures.QUIET, ReforgedNatures.RASH, ReforgedNatures.CALM, ReforgedNatures.GENTLE, ReforgedNatures.SASSY, ReforgedNatures.CAREFUL};
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u00e6\u0080\u0001\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\u00020\u0002J!\u0010\u0007\u001a\u0004\u0018\u00018\u00002\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0005H&\u00a2\u0006\u0004\b\u0007\u0010\b\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/api/storage/adapter/conversions/ReforgedConversion$Translator;", "R", "", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "", "key", "from", "(Lnet/minecraft/nbt/CompoundTag;Ljava/lang/String;)Ljava/lang/Object;", "common"})
    public static interface Translator<R> {
        @Nullable
        public R from(@NotNull CompoundTag var1, @NotNull String var2);
    }
}

