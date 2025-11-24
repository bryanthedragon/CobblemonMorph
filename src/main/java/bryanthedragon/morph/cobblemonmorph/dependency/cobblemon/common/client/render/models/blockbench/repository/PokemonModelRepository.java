/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.JsonPokemonPoseableModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.PokemonModelRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.VaryingModelRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0011\b\u00c6\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\t\b\u0002\u00a2\u0006\u0004\b'\u0010\fJ#\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00030\u00062\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ\u000f\u0010\u000b\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fR \u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00040\r8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0013\u001a\u00020\u00128\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0018\u001a\u00020\u00178\u0016X\u0096D\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u0018\u0010\u001aR \u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00040\r8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001b\u0010\u000f\u001a\u0004\b\u001c\u0010\u0011R \u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00040\r8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001d\u0010\u000f\u001a\u0004\b\u001e\u0010\u0011R\u001a\u0010\u001f\u001a\u00020\u00048\u0016X\u0096D\u00a2\u0006\f\n\u0004\b\u001f\u0010 \u001a\u0004\b!\u0010\"R\u001a\u0010#\u001a\u00020\u00048\u0016X\u0096D\u00a2\u0006\f\n\u0004\b#\u0010 \u001a\u0004\b$\u0010\"R \u0010%\u001a\b\u0012\u0004\u0012\u00020\u00040\r8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b%\u0010\u000f\u001a\u0004\b&\u0010\u0011\u00a8\u0006("}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/repository/PokemonModelRepository;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/repository/VaryingModelRepository;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPoseableModel;", "", "json", "Lkotlin/Function1;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "loadJsonPoser", "(Ljava/lang/String;)Lkotlin/jvm/functions/Function1;", "", "registerInBuiltPosers", "()V", "", "animationDirectories", "Ljava/util/List;", "getAnimationDirectories", "()Ljava/util/List;", "Lnet/minecraft/resources/ResourceLocation;", "fallback", "Lnet/minecraft/resources/ResourceLocation;", "getFallback", "()Lnet/minecraft/resources/ResourceLocation;", "", "isForLivingEntityRenderer", "Z", "()Z", "modelDirectories", "getModelDirectories", "poserDirectories", "getPoserDirectories", "title", "Ljava/lang/String;", "getTitle", "()Ljava/lang/String;", "type", "getType", "variationDirectories", "getVariationDirectories", "<init>", "common"})
public final class PokemonModelRepository
extends VaryingModelRepository<PokemonEntity, PokemonPoseableModel> {
    @NotNull
    public static final PokemonModelRepository INSTANCE = new PokemonModelRepository();
    @NotNull
    private static final String title = "Pok\u00e9mon";
    @NotNull
    private static final String type = "pokemon";
    @NotNull
    private static final List<String> variationDirectories;
    @NotNull
    private static final List<String> poserDirectories;
    @NotNull
    private static final List<String> modelDirectories;
    @NotNull
    private static final List<String> animationDirectories;
    private static final boolean isForLivingEntityRenderer;
    @NotNull
    private static final ResourceLocation fallback;

    private PokemonModelRepository() {
    }

    @Override
    @NotNull
    public String getTitle() {
        return title;
    }

    @Override
    @NotNull
    public String getType() {
        return type;
    }

    @Override
    @NotNull
    public List<String> getVariationDirectories() {
        return variationDirectories;
    }

    @Override
    @NotNull
    public List<String> getPoserDirectories() {
        return poserDirectories;
    }

    @Override
    @NotNull
    public List<String> getModelDirectories() {
        return modelDirectories;
    }

    @Override
    @NotNull
    public List<String> getAnimationDirectories() {
        return animationDirectories;
    }

    @Override
    public boolean isForLivingEntityRenderer() {
        return isForLivingEntityRenderer;
    }

    @Override
    @NotNull
    public ResourceLocation getFallback() {
        return fallback;
    }

    @Override
    public void registerInBuiltPosers() {
        this.inbuilt("bulbasaur", registerInBuiltPosers.1.INSTANCE);
        this.inbuilt("ivysaur", registerInBuiltPosers.2.INSTANCE);
        this.inbuilt("venusaur", registerInBuiltPosers.3.INSTANCE);
        this.inbuilt("charmander", registerInBuiltPosers.4.INSTANCE);
        this.inbuilt("charmeleon", registerInBuiltPosers.5.INSTANCE);
        this.inbuilt("charizard", registerInBuiltPosers.6.INSTANCE);
        this.inbuilt("squirtle", registerInBuiltPosers.7.INSTANCE);
        this.inbuilt("wartortle", registerInBuiltPosers.8.INSTANCE);
        this.inbuilt("caterpie", registerInBuiltPosers.9.INSTANCE);
        this.inbuilt("metapod", registerInBuiltPosers.10.INSTANCE);
        this.inbuilt("butterfree", registerInBuiltPosers.11.INSTANCE);
        this.inbuilt("weedle", registerInBuiltPosers.12.INSTANCE);
        this.inbuilt("kakuna", registerInBuiltPosers.13.INSTANCE);
        this.inbuilt("beedrill", registerInBuiltPosers.14.INSTANCE);
        this.inbuilt("rattata", registerInBuiltPosers.15.INSTANCE);
        this.inbuilt("raticate", registerInBuiltPosers.16.INSTANCE);
        this.inbuilt("rattata_alolan", registerInBuiltPosers.17.INSTANCE);
        this.inbuilt("raticate_alolan", registerInBuiltPosers.18.INSTANCE);
        this.inbuilt("eevee", registerInBuiltPosers.19.INSTANCE);
        this.inbuilt("magikarp", registerInBuiltPosers.20.INSTANCE);
        this.inbuilt("gyarados", registerInBuiltPosers.21.INSTANCE);
        this.inbuilt("pidgey", registerInBuiltPosers.22.INSTANCE);
        this.inbuilt("pidgeotto", registerInBuiltPosers.23.INSTANCE);
        this.inbuilt("pidgeot", registerInBuiltPosers.24.INSTANCE);
        this.inbuilt("diglett", registerInBuiltPosers.25.INSTANCE);
        this.inbuilt("dugtrio", registerInBuiltPosers.26.INSTANCE);
        this.inbuilt("zubat", registerInBuiltPosers.27.INSTANCE);
        this.inbuilt("cleffa", registerInBuiltPosers.28.INSTANCE);
        this.inbuilt("clefable", registerInBuiltPosers.29.INSTANCE);
        this.inbuilt("clefairy", registerInBuiltPosers.30.INSTANCE);
        this.inbuilt("krabby", registerInBuiltPosers.31.INSTANCE);
        this.inbuilt("paras", registerInBuiltPosers.32.INSTANCE);
        this.inbuilt("parasect", registerInBuiltPosers.33.INSTANCE);
        this.inbuilt("mankey", registerInBuiltPosers.34.INSTANCE);
        this.inbuilt("primeape", registerInBuiltPosers.35.INSTANCE);
        this.inbuilt("oddish", registerInBuiltPosers.36.INSTANCE);
        this.inbuilt("gloom", registerInBuiltPosers.37.INSTANCE);
        this.inbuilt("vileplume", registerInBuiltPosers.38.INSTANCE);
        this.inbuilt("bellossom", registerInBuiltPosers.39.INSTANCE);
        this.inbuilt("voltorb", registerInBuiltPosers.40.INSTANCE);
        this.inbuilt("electrode", registerInBuiltPosers.41.INSTANCE);
        this.inbuilt("lapras", registerInBuiltPosers.42.INSTANCE);
        this.inbuilt("ekans", registerInBuiltPosers.43.INSTANCE);
        this.inbuilt("machop", registerInBuiltPosers.44.INSTANCE);
        this.inbuilt("machoke", registerInBuiltPosers.45.INSTANCE);
        this.inbuilt("machamp", registerInBuiltPosers.46.INSTANCE);
        this.inbuilt("abra", registerInBuiltPosers.47.INSTANCE);
        this.inbuilt("aerodactyl", registerInBuiltPosers.48.INSTANCE);
        this.inbuilt("alakazam", registerInBuiltPosers.49.INSTANCE);
        this.inbuilt("arbok", registerInBuiltPosers.50.INSTANCE);
        this.inbuilt("arcanine", registerInBuiltPosers.51.INSTANCE);
        this.inbuilt("articuno", registerInBuiltPosers.52.INSTANCE);
        this.inbuilt("bellsprout", registerInBuiltPosers.53.INSTANCE);
        this.inbuilt("chansey", registerInBuiltPosers.54.INSTANCE);
        this.inbuilt("cloyster", registerInBuiltPosers.55.INSTANCE);
        this.inbuilt("crobat", registerInBuiltPosers.56.INSTANCE);
        this.inbuilt("cubone", registerInBuiltPosers.57.INSTANCE);
        this.inbuilt("dewgong", registerInBuiltPosers.58.INSTANCE);
        this.inbuilt("ditto", registerInBuiltPosers.59.INSTANCE);
        this.inbuilt("dodrio", registerInBuiltPosers.60.INSTANCE);
        this.inbuilt("doduo", registerInBuiltPosers.61.INSTANCE);
        this.inbuilt("dragonair", registerInBuiltPosers.62.INSTANCE);
        this.inbuilt("dragonite", registerInBuiltPosers.63.INSTANCE);
        this.inbuilt("dratini", registerInBuiltPosers.64.INSTANCE);
        this.inbuilt("drowzee", registerInBuiltPosers.65.INSTANCE);
        this.inbuilt("electabuzz", registerInBuiltPosers.66.INSTANCE);
        this.inbuilt("exeggcute", registerInBuiltPosers.67.INSTANCE);
        this.inbuilt("exeggutor", registerInBuiltPosers.68.INSTANCE);
        this.inbuilt("exeggutor_alolan", registerInBuiltPosers.69.INSTANCE);
        this.inbuilt("farfetchd", registerInBuiltPosers.70.INSTANCE);
        this.inbuilt("farfetchd_galarian", registerInBuiltPosers.71.INSTANCE);
        this.inbuilt("fearow", registerInBuiltPosers.72.INSTANCE);
        this.inbuilt("flareon", registerInBuiltPosers.73.INSTANCE);
        this.inbuilt("gastly", registerInBuiltPosers.74.INSTANCE);
        this.inbuilt("gastly_shiny", registerInBuiltPosers.75.INSTANCE);
        this.inbuilt("gengar", registerInBuiltPosers.76.INSTANCE);
        this.inbuilt("geodude", registerInBuiltPosers.77.INSTANCE);
        this.inbuilt("golbat", registerInBuiltPosers.78.INSTANCE);
        this.inbuilt("goldeen", registerInBuiltPosers.79.INSTANCE);
        this.inbuilt("golduck", registerInBuiltPosers.80.INSTANCE);
        this.inbuilt("golem", registerInBuiltPosers.81.INSTANCE);
        this.inbuilt("graveler", registerInBuiltPosers.82.INSTANCE);
        this.inbuilt("grimer", registerInBuiltPosers.83.INSTANCE);
        this.inbuilt("growlithe", registerInBuiltPosers.84.INSTANCE);
        this.inbuilt("haunter", registerInBuiltPosers.85.INSTANCE);
        this.inbuilt("hitmonchan", registerInBuiltPosers.86.INSTANCE);
        this.inbuilt("hitmonlee", registerInBuiltPosers.87.INSTANCE);
        this.inbuilt("horsea", registerInBuiltPosers.88.INSTANCE);
        this.inbuilt("hypno", registerInBuiltPosers.89.INSTANCE);
        this.inbuilt("jigglypuff", registerInBuiltPosers.90.INSTANCE);
        this.inbuilt("jolteon", registerInBuiltPosers.91.INSTANCE);
        this.inbuilt("jynx", registerInBuiltPosers.92.INSTANCE);
        this.inbuilt("kabuto", registerInBuiltPosers.93.INSTANCE);
        this.inbuilt("kabutops", registerInBuiltPosers.94.INSTANCE);
        this.inbuilt("kadabra", registerInBuiltPosers.95.INSTANCE);
        this.inbuilt("kangaskhan", registerInBuiltPosers.96.INSTANCE);
        this.inbuilt("kingler", registerInBuiltPosers.97.INSTANCE);
        this.inbuilt("koffing", registerInBuiltPosers.98.INSTANCE);
        this.inbuilt("krabby", registerInBuiltPosers.99.INSTANCE);
        this.inbuilt("lickitung", registerInBuiltPosers.100.INSTANCE);
        this.inbuilt("magmar", registerInBuiltPosers.101.INSTANCE);
        this.inbuilt("magnemite", registerInBuiltPosers.102.INSTANCE);
        this.inbuilt("magneton", registerInBuiltPosers.103.INSTANCE);
        this.inbuilt("marowak", registerInBuiltPosers.104.INSTANCE);
        this.inbuilt("meowth", registerInBuiltPosers.105.INSTANCE);
        this.inbuilt("mew", registerInBuiltPosers.106.INSTANCE);
        this.inbuilt("mewtwo", registerInBuiltPosers.107.INSTANCE);
        this.inbuilt("moltres", registerInBuiltPosers.108.INSTANCE);
        this.inbuilt("mrmime", registerInBuiltPosers.109.INSTANCE);
        this.inbuilt("muk", registerInBuiltPosers.110.INSTANCE);
        this.inbuilt("nidoking", registerInBuiltPosers.111.INSTANCE);
        this.inbuilt("nidoqueen", registerInBuiltPosers.112.INSTANCE);
        this.inbuilt("nidoranf", registerInBuiltPosers.113.INSTANCE);
        this.inbuilt("nidoranm", registerInBuiltPosers.114.INSTANCE);
        this.inbuilt("nidorina", registerInBuiltPosers.115.INSTANCE);
        this.inbuilt("nidorino", registerInBuiltPosers.116.INSTANCE);
        this.inbuilt("ninetales", registerInBuiltPosers.117.INSTANCE);
        this.inbuilt("omanyte", registerInBuiltPosers.118.INSTANCE);
        this.inbuilt("omastar", registerInBuiltPosers.119.INSTANCE);
        this.inbuilt("onix", registerInBuiltPosers.120.INSTANCE);
        this.inbuilt("persian", registerInBuiltPosers.121.INSTANCE);
        this.inbuilt("pikachu", registerInBuiltPosers.122.INSTANCE);
        this.inbuilt("pinsir", registerInBuiltPosers.123.INSTANCE);
        this.inbuilt("poliwag", registerInBuiltPosers.124.INSTANCE);
        this.inbuilt("poliwhirl", registerInBuiltPosers.125.INSTANCE);
        this.inbuilt("poliwrath", registerInBuiltPosers.126.INSTANCE);
        this.inbuilt("politoed", registerInBuiltPosers.127.INSTANCE);
        this.inbuilt("ponyta", registerInBuiltPosers.128.INSTANCE);
        this.inbuilt("porygon", registerInBuiltPosers.129.INSTANCE);
        this.inbuilt("psyduck", registerInBuiltPosers.130.INSTANCE);
        this.inbuilt("raichu", registerInBuiltPosers.131.INSTANCE);
        this.inbuilt("raichu_alolan", registerInBuiltPosers.132.INSTANCE);
        this.inbuilt("rapidash", registerInBuiltPosers.133.INSTANCE);
        this.inbuilt("rhydon", registerInBuiltPosers.134.INSTANCE);
        this.inbuilt("rhyhorn", registerInBuiltPosers.135.INSTANCE);
        this.inbuilt("sandshrew", registerInBuiltPosers.136.INSTANCE);
        this.inbuilt("sandslash", registerInBuiltPosers.137.INSTANCE);
        this.inbuilt("scyther", registerInBuiltPosers.138.INSTANCE);
        this.inbuilt("seadra", registerInBuiltPosers.139.INSTANCE);
        this.inbuilt("seaking", registerInBuiltPosers.140.INSTANCE);
        this.inbuilt("seel", registerInBuiltPosers.141.INSTANCE);
        this.inbuilt("shellder", registerInBuiltPosers.142.INSTANCE);
        this.inbuilt("slowbro", registerInBuiltPosers.143.INSTANCE);
        this.inbuilt("slowpoke", registerInBuiltPosers.144.INSTANCE);
        this.inbuilt("snorlax", registerInBuiltPosers.145.INSTANCE);
        this.inbuilt("spearow", registerInBuiltPosers.146.INSTANCE);
        this.inbuilt("starmie", registerInBuiltPosers.147.INSTANCE);
        this.inbuilt("staryu", registerInBuiltPosers.148.INSTANCE);
        this.inbuilt("steelix", registerInBuiltPosers.149.INSTANCE);
        this.inbuilt("tangela", registerInBuiltPosers.150.INSTANCE);
        this.inbuilt("tauros", registerInBuiltPosers.151.INSTANCE);
        this.inbuilt("tentacool", registerInBuiltPosers.152.INSTANCE);
        this.inbuilt("tentacruel", registerInBuiltPosers.153.INSTANCE);
        this.inbuilt("vaporeon", registerInBuiltPosers.154.INSTANCE);
        this.inbuilt("venomoth", registerInBuiltPosers.155.INSTANCE);
        this.inbuilt("venonat", registerInBuiltPosers.156.INSTANCE);
        this.inbuilt("victreebel", registerInBuiltPosers.157.INSTANCE);
        this.inbuilt("vulpix", registerInBuiltPosers.158.INSTANCE);
        this.inbuilt("weepinbell", registerInBuiltPosers.159.INSTANCE);
        this.inbuilt("weezing", registerInBuiltPosers.160.INSTANCE);
        this.inbuilt("wigglytuff", registerInBuiltPosers.161.INSTANCE);
        this.inbuilt("zapdos", registerInBuiltPosers.162.INSTANCE);
        this.inbuilt("elekid", registerInBuiltPosers.163.INSTANCE);
        this.inbuilt("igglybuff", registerInBuiltPosers.164.INSTANCE);
        this.inbuilt("magby", registerInBuiltPosers.165.INSTANCE);
        this.inbuilt("pichu", registerInBuiltPosers.166.INSTANCE);
        this.inbuilt("smoochum", registerInBuiltPosers.167.INSTANCE);
        this.inbuilt("tyrogue", registerInBuiltPosers.168.INSTANCE);
        this.inbuilt("hitmontop", registerInBuiltPosers.169.INSTANCE);
        this.inbuilt("electivire", registerInBuiltPosers.170.INSTANCE);
        this.inbuilt("glaceon", registerInBuiltPosers.171.INSTANCE);
        this.inbuilt("happiny", registerInBuiltPosers.172.INSTANCE);
        this.inbuilt("leafeon", registerInBuiltPosers.173.INSTANCE);
        this.inbuilt("lickilicky", registerInBuiltPosers.174.INSTANCE);
        this.inbuilt("magmortar", registerInBuiltPosers.175.INSTANCE);
        this.inbuilt("magnezone", registerInBuiltPosers.176.INSTANCE);
        this.inbuilt("mimejr", registerInBuiltPosers.177.INSTANCE);
        this.inbuilt("munchlax", registerInBuiltPosers.178.INSTANCE);
        this.inbuilt("porygon2", registerInBuiltPosers.179.INSTANCE);
        this.inbuilt("porygonz", registerInBuiltPosers.180.INSTANCE);
        this.inbuilt("rhyperior", registerInBuiltPosers.181.INSTANCE);
        this.inbuilt("scizor", registerInBuiltPosers.182.INSTANCE);
        this.inbuilt("tangrowth", registerInBuiltPosers.183.INSTANCE);
        this.inbuilt("sylveon", registerInBuiltPosers.184.INSTANCE);
        this.inbuilt("umbreon", registerInBuiltPosers.185.INSTANCE);
        this.inbuilt("espeon", registerInBuiltPosers.186.INSTANCE);
        this.inbuilt("blissey", registerInBuiltPosers.187.INSTANCE);
        this.inbuilt("kingdra", registerInBuiltPosers.188.INSTANCE);
        this.inbuilt("piloswine", registerInBuiltPosers.189.INSTANCE);
        this.inbuilt("quagsire", registerInBuiltPosers.190.INSTANCE);
        this.inbuilt("slowking", registerInBuiltPosers.191.INSTANCE);
        this.inbuilt("swinub", registerInBuiltPosers.192.INSTANCE);
        this.inbuilt("wooper", registerInBuiltPosers.193.INSTANCE);
        this.inbuilt("wooper_paldean", registerInBuiltPosers.194.INSTANCE);
        this.inbuilt("yanma", registerInBuiltPosers.195.INSTANCE);
        this.inbuilt("blaziken", registerInBuiltPosers.196.INSTANCE);
        this.inbuilt("combusken", registerInBuiltPosers.197.INSTANCE);
        this.inbuilt("marshtomp", registerInBuiltPosers.198.INSTANCE);
        this.inbuilt("minun", registerInBuiltPosers.199.INSTANCE);
        this.inbuilt("mudkip", registerInBuiltPosers.200.INSTANCE);
        this.inbuilt("plusle", registerInBuiltPosers.201.INSTANCE);
        this.inbuilt("rayquaza", registerInBuiltPosers.202.INSTANCE);
        this.inbuilt("swampert", registerInBuiltPosers.203.INSTANCE);
        this.inbuilt("torchic", registerInBuiltPosers.204.INSTANCE);
        this.inbuilt("bibarel", registerInBuiltPosers.205.INSTANCE);
        this.inbuilt("bidoof", registerInBuiltPosers.206.INSTANCE);
        this.inbuilt("buneary", registerInBuiltPosers.207.INSTANCE);
        this.inbuilt("empoleon", registerInBuiltPosers.208.INSTANCE);
        this.inbuilt("lopunny", registerInBuiltPosers.209.INSTANCE);
        this.inbuilt("mamoswine", registerInBuiltPosers.210.INSTANCE);
        this.inbuilt("pachirisu", registerInBuiltPosers.211.INSTANCE);
        this.inbuilt("piplup", registerInBuiltPosers.212.INSTANCE);
        this.inbuilt("prinplup", registerInBuiltPosers.213.INSTANCE);
        this.inbuilt("yanmega", registerInBuiltPosers.214.INSTANCE);
        this.inbuilt("basculin", registerInBuiltPosers.215.INSTANCE);
        this.inbuilt("crustle", registerInBuiltPosers.216.INSTANCE);
        this.inbuilt("dwebble", registerInBuiltPosers.217.INSTANCE);
        this.inbuilt("emolga", registerInBuiltPosers.218.INSTANCE);
        this.inbuilt("maractus", registerInBuiltPosers.219.INSTANCE);
        this.inbuilt("bounsweet", registerInBuiltPosers.220.INSTANCE);
        this.inbuilt("dartrix", registerInBuiltPosers.221.INSTANCE);
        this.inbuilt("decidueye", registerInBuiltPosers.222.INSTANCE);
        this.inbuilt("incineroar", registerInBuiltPosers.223.INSTANCE);
        this.inbuilt("litten", registerInBuiltPosers.224.INSTANCE);
        this.inbuilt("mimikyu", registerInBuiltPosers.225.INSTANCE);
        this.inbuilt("naganadel", registerInBuiltPosers.226.INSTANCE);
        this.inbuilt("poipole", registerInBuiltPosers.227.INSTANCE);
        this.inbuilt("rowlet", registerInBuiltPosers.228.INSTANCE);
        this.inbuilt("steenee", registerInBuiltPosers.229.INSTANCE);
        this.inbuilt("torracat", registerInBuiltPosers.230.INSTANCE);
        this.inbuilt("tsareena", registerInBuiltPosers.231.INSTANCE);
        this.inbuilt("centiskorch", registerInBuiltPosers.232.INSTANCE);
        this.inbuilt("sizzlipede", registerInBuiltPosers.233.INSTANCE);
        this.inbuilt("kleavor", registerInBuiltPosers.234.INSTANCE);
        this.inbuilt("pyukumuku", registerInBuiltPosers.235.INSTANCE);
        this.inbuilt("deerling", registerInBuiltPosers.236.INSTANCE);
        this.inbuilt("sawsbuck", registerInBuiltPosers.237.INSTANCE);
        this.inbuilt("sableye", registerInBuiltPosers.238.INSTANCE);
        this.inbuilt("natu", registerInBuiltPosers.239.INSTANCE);
        this.inbuilt("xatu", registerInBuiltPosers.240.INSTANCE);
        this.inbuilt("wailmer", registerInBuiltPosers.241.INSTANCE);
        this.inbuilt("wailord", registerInBuiltPosers.242.INSTANCE);
        this.inbuilt("murkrow", registerInBuiltPosers.243.INSTANCE);
        this.inbuilt("honchkrow", registerInBuiltPosers.244.INSTANCE);
        this.inbuilt("nacli", registerInBuiltPosers.245.INSTANCE);
        this.inbuilt("naclstack", registerInBuiltPosers.246.INSTANCE);
        this.inbuilt("garganacl", registerInBuiltPosers.247.INSTANCE);
        this.inbuilt("dhelmise", registerInBuiltPosers.248.INSTANCE);
        this.inbuilt("alcremie", registerInBuiltPosers.249.INSTANCE);
        this.inbuilt("milcery", registerInBuiltPosers.250.INSTANCE);
        this.inbuilt("turtwig", registerInBuiltPosers.251.INSTANCE);
        this.inbuilt("grotle", registerInBuiltPosers.252.INSTANCE);
        this.inbuilt("torterra", registerInBuiltPosers.253.INSTANCE);
        this.inbuilt("torterra_cherry", registerInBuiltPosers.254.INSTANCE);
        this.inbuilt("xerneas", registerInBuiltPosers.255.INSTANCE);
        this.inbuilt("klink", registerInBuiltPosers.256.INSTANCE);
        this.inbuilt("klang", registerInBuiltPosers.257.INSTANCE);
        this.inbuilt("klinklang", registerInBuiltPosers.258.INSTANCE);
        this.inbuilt("morelull", registerInBuiltPosers.259.INSTANCE);
        this.inbuilt("shiinotic", registerInBuiltPosers.260.INSTANCE);
        this.inbuilt("joltik", registerInBuiltPosers.261.INSTANCE);
        this.inbuilt("galvantula", registerInBuiltPosers.262.INSTANCE);
        this.inbuilt("riolu", registerInBuiltPosers.263.INSTANCE);
        this.inbuilt("lucario", registerInBuiltPosers.264.INSTANCE);
        this.inbuilt("treecko", registerInBuiltPosers.265.INSTANCE);
        this.inbuilt("grovyle", registerInBuiltPosers.266.INSTANCE);
        this.inbuilt("sceptile", registerInBuiltPosers.267.INSTANCE);
        this.inbuilt("honedge", registerInBuiltPosers.268.INSTANCE);
        this.inbuilt("spiritomb", registerInBuiltPosers.269.INSTANCE);
        this.inbuilt("baltoy", registerInBuiltPosers.270.INSTANCE);
        this.inbuilt("claydol", registerInBuiltPosers.271.INSTANCE);
        this.inbuilt("chespin", registerInBuiltPosers.272.INSTANCE);
        this.inbuilt("quilladin", registerInBuiltPosers.273.INSTANCE);
        this.inbuilt("chesnaught", registerInBuiltPosers.274.INSTANCE);
        this.inbuilt("elgyem", registerInBuiltPosers.275.INSTANCE);
        this.inbuilt("beheeyem", registerInBuiltPosers.276.INSTANCE);
        this.inbuilt("gible", registerInBuiltPosers.277.INSTANCE);
        this.inbuilt("gabite", registerInBuiltPosers.278.INSTANCE);
        this.inbuilt("garchomp", registerInBuiltPosers.279.INSTANCE);
        this.inbuilt("pineco", registerInBuiltPosers.280.INSTANCE);
        this.inbuilt("forretress", registerInBuiltPosers.281.INSTANCE);
        this.inbuilt("doublade", registerInBuiltPosers.282.INSTANCE);
        this.inbuilt("aegislash", registerInBuiltPosers.283.INSTANCE);
        this.inbuilt("lotad", registerInBuiltPosers.284.INSTANCE);
        this.inbuilt("lombre", registerInBuiltPosers.285.INSTANCE);
        this.inbuilt("ludicolo", registerInBuiltPosers.286.INSTANCE);
        this.inbuilt("golett", registerInBuiltPosers.287.INSTANCE);
        this.inbuilt("golurk", registerInBuiltPosers.288.INSTANCE);
        this.inbuilt("stantler", registerInBuiltPosers.289.INSTANCE);
        this.inbuilt("wyrdeer", registerInBuiltPosers.290.INSTANCE);
        this.inbuilt("sneasel", registerInBuiltPosers.291.INSTANCE);
        this.inbuilt("weavile", registerInBuiltPosers.292.INSTANCE);
        this.inbuilt("bergmite", registerInBuiltPosers.293.INSTANCE);
        this.inbuilt("avalugg", registerInBuiltPosers.294.INSTANCE);
        this.inbuilt("misdreavus", registerInBuiltPosers.295.INSTANCE);
        this.inbuilt("mismagius", registerInBuiltPosers.296.INSTANCE);
        this.inbuilt("whismur", registerInBuiltPosers.297.INSTANCE);
        this.inbuilt("loudred", registerInBuiltPosers.298.INSTANCE);
        this.inbuilt("exploud", registerInBuiltPosers.299.INSTANCE);
        this.inbuilt("luvdisc", registerInBuiltPosers.300.INSTANCE);
        this.inbuilt("cryogonal", registerInBuiltPosers.301.INSTANCE);
        this.inbuilt("sigilyph", registerInBuiltPosers.302.INSTANCE);
        this.inbuilt("pumpkaboo", registerInBuiltPosers.303.INSTANCE);
        this.inbuilt("gourgeist", registerInBuiltPosers.304.INSTANCE);
        this.inbuilt("eiscue", registerInBuiltPosers.305.INSTANCE);
        this.inbuilt("tatsugiri", registerInBuiltPosers.306.INSTANCE);
        this.inbuilt("wooloo", registerInBuiltPosers.307.INSTANCE);
        this.inbuilt("dubwool", registerInBuiltPosers.308.INSTANCE);
        this.inbuilt("chimchar", registerInBuiltPosers.309.INSTANCE);
        this.inbuilt("monferno", registerInBuiltPosers.310.INSTANCE);
        this.inbuilt("infernape", registerInBuiltPosers.311.INSTANCE);
        this.inbuilt("popplio", registerInBuiltPosers.312.INSTANCE);
        this.inbuilt("brionne", registerInBuiltPosers.313.INSTANCE);
        this.inbuilt("primarina", registerInBuiltPosers.314.INSTANCE);
        this.inbuilt("spinda", registerInBuiltPosers.315.INSTANCE);
        this.inbuilt("seedot", registerInBuiltPosers.316.INSTANCE);
        this.inbuilt("nuzleaf", registerInBuiltPosers.317.INSTANCE);
        this.inbuilt("shiftry", registerInBuiltPosers.318.INSTANCE);
        this.inbuilt("kricketot", registerInBuiltPosers.319.INSTANCE);
        this.inbuilt("kricketune", registerInBuiltPosers.320.INSTANCE);
        this.inbuilt("heatmor", registerInBuiltPosers.321.INSTANCE);
        this.inbuilt("durant", registerInBuiltPosers.322.INSTANCE);
        this.inbuilt("carvanha", registerInBuiltPosers.323.INSTANCE);
        this.inbuilt("sharpedo", registerInBuiltPosers.324.INSTANCE);
        this.inbuilt("mawile", registerInBuiltPosers.325.INSTANCE);
        this.inbuilt("walkingwake", registerInBuiltPosers.326.INSTANCE);
        this.inbuilt("ironleaves", registerInBuiltPosers.327.INSTANCE);
        this.inbuilt("miltank", registerInBuiltPosers.328.INSTANCE);
        this.inbuilt("torkoal", registerInBuiltPosers.329.INSTANCE);
        this.inbuilt("fennekin", registerInBuiltPosers.330.INSTANCE);
        this.inbuilt("braixen", registerInBuiltPosers.331.INSTANCE);
        this.inbuilt("delphox", registerInBuiltPosers.332.INSTANCE);
        this.inbuilt("froakie", registerInBuiltPosers.333.INSTANCE);
        this.inbuilt("frogadier", registerInBuiltPosers.334.INSTANCE);
        this.inbuilt("greninja", registerInBuiltPosers.335.INSTANCE);
        this.inbuilt("tepig", registerInBuiltPosers.336.INSTANCE);
        this.inbuilt("pignite", registerInBuiltPosers.337.INSTANCE);
        this.inbuilt("emboar", registerInBuiltPosers.338.INSTANCE);
        this.inbuilt("grookey", registerInBuiltPosers.339.INSTANCE);
        this.inbuilt("thwackey", registerInBuiltPosers.340.INSTANCE);
        this.inbuilt("rillaboom", registerInBuiltPosers.341.INSTANCE);
        this.inbuilt("scorbunny", registerInBuiltPosers.342.INSTANCE);
        this.inbuilt("raboot", registerInBuiltPosers.343.INSTANCE);
        this.inbuilt("cinderace", registerInBuiltPosers.344.INSTANCE);
        this.inbuilt("sobble", registerInBuiltPosers.345.INSTANCE);
        this.inbuilt("drizzile", registerInBuiltPosers.346.INSTANCE);
        this.inbuilt("inteleon", registerInBuiltPosers.347.INSTANCE);
        this.inbuilt("oshawott", registerInBuiltPosers.348.INSTANCE);
        this.inbuilt("dewott", registerInBuiltPosers.349.INSTANCE);
        this.inbuilt("samurott", registerInBuiltPosers.350.INSTANCE);
        this.inbuilt("snivy", registerInBuiltPosers.351.INSTANCE);
        this.inbuilt("servine", registerInBuiltPosers.352.INSTANCE);
        this.inbuilt("serperior", registerInBuiltPosers.353.INSTANCE);
        this.inbuilt("slugma", registerInBuiltPosers.354.INSTANCE);
        this.inbuilt("magcargo", registerInBuiltPosers.355.INSTANCE);
        this.inbuilt("slugma_shiny", registerInBuiltPosers.356.INSTANCE);
        this.inbuilt("magcargo_shiny", registerInBuiltPosers.357.INSTANCE);
        this.inbuilt("nosepass", registerInBuiltPosers.358.INSTANCE);
        this.inbuilt("probopass", registerInBuiltPosers.359.INSTANCE);
        this.inbuilt("chinchou", registerInBuiltPosers.360.INSTANCE);
        this.inbuilt("clamperl", registerInBuiltPosers.361.INSTANCE);
        this.inbuilt("huntail", registerInBuiltPosers.362.INSTANCE);
        this.inbuilt("gorebyss", registerInBuiltPosers.363.INSTANCE);
        this.inbuilt("spinarak", registerInBuiltPosers.364.INSTANCE);
        this.inbuilt("ariados", registerInBuiltPosers.365.INSTANCE);
        this.inbuilt("shuckle", registerInBuiltPosers.366.INSTANCE);
        this.inbuilt("taillow", registerInBuiltPosers.367.INSTANCE);
        this.inbuilt("swellow", registerInBuiltPosers.368.INSTANCE);
        this.inbuilt("relicanth", registerInBuiltPosers.369.INSTANCE);
        this.inbuilt("mudbray", registerInBuiltPosers.370.INSTANCE);
        this.inbuilt("mudsdale", registerInBuiltPosers.371.INSTANCE);
        this.inbuilt("comfey", registerInBuiltPosers.372.INSTANCE);
        this.inbuilt("tandemaus", registerInBuiltPosers.373.INSTANCE);
        this.inbuilt("maushold", registerInBuiltPosers.374.INSTANCE);
        this.inbuilt("mausholdfour", registerInBuiltPosers.375.INSTANCE);
        this.inbuilt("varoom", registerInBuiltPosers.376.INSTANCE);
        this.inbuilt("revavroom", registerInBuiltPosers.377.INSTANCE);
        this.inbuilt("lanturn", registerInBuiltPosers.378.INSTANCE);
        this.inbuilt("chingling", registerInBuiltPosers.379.INSTANCE);
        this.inbuilt("chimecho", registerInBuiltPosers.380.INSTANCE);
        this.inbuilt("fidough", registerInBuiltPosers.381.INSTANCE);
        this.inbuilt("dachsbun", registerInBuiltPosers.382.INSTANCE);
        this.inbuilt("chatot", registerInBuiltPosers.383.INSTANCE);
        this.inbuilt("gligar", registerInBuiltPosers.384.INSTANCE);
        this.inbuilt("gliscor", registerInBuiltPosers.385.INSTANCE);
        this.inbuilt("poochyena", registerInBuiltPosers.386.INSTANCE);
        this.inbuilt("mightyena", registerInBuiltPosers.387.INSTANCE);
        this.inbuilt("sprigatito", registerInBuiltPosers.388.INSTANCE);
        this.inbuilt("floragato", registerInBuiltPosers.389.INSTANCE);
        this.inbuilt("meowscarada", registerInBuiltPosers.390.INSTANCE);
        this.inbuilt("shroomish", registerInBuiltPosers.391.INSTANCE);
        this.inbuilt("breloom", registerInBuiltPosers.392.INSTANCE);
        this.inbuilt("charcadet", registerInBuiltPosers.393.INSTANCE);
        this.inbuilt("armarouge", registerInBuiltPosers.394.INSTANCE);
        this.inbuilt("ceruledge", registerInBuiltPosers.395.INSTANCE);
        this.inbuilt("flittle", registerInBuiltPosers.396.INSTANCE);
        this.inbuilt("espathra", registerInBuiltPosers.397.INSTANCE);
        this.inbuilt("surskit", registerInBuiltPosers.398.INSTANCE);
        this.inbuilt("masquerain", registerInBuiltPosers.399.INSTANCE);
        this.inbuilt("cutiefly", registerInBuiltPosers.400.INSTANCE);
        this.inbuilt("ribombee", registerInBuiltPosers.401.INSTANCE);
        this.inbuilt("carnivine", registerInBuiltPosers.402.INSTANCE);
        this.inbuilt("falinks", registerInBuiltPosers.403.INSTANCE);
        this.inbuilt("stufful", registerInBuiltPosers.404.INSTANCE);
        this.inbuilt("bewear", registerInBuiltPosers.405.INSTANCE);
        this.inbuilt("scatterbug", registerInBuiltPosers.406.INSTANCE);
        this.inbuilt("spewpa", registerInBuiltPosers.407.INSTANCE);
        this.inbuilt("vivillon", registerInBuiltPosers.408.INSTANCE);
        this.inbuilt("barboach", registerInBuiltPosers.409.INSTANCE);
        this.inbuilt("whiscash", registerInBuiltPosers.410.INSTANCE);
        this.inbuilt("combee", registerInBuiltPosers.411.INSTANCE);
        this.inbuilt("vespiquen", registerInBuiltPosers.412.INSTANCE);
        this.inbuilt("lillipup", registerInBuiltPosers.413.INSTANCE);
        this.inbuilt("herdier", registerInBuiltPosers.414.INSTANCE);
        this.inbuilt("stoutland", registerInBuiltPosers.415.INSTANCE);
        this.inbuilt("sirfetchd", registerInBuiltPosers.416.INSTANCE);
        this.inbuilt("rookidee", registerInBuiltPosers.417.INSTANCE);
        this.inbuilt("corvisquire", registerInBuiltPosers.418.INSTANCE);
        this.inbuilt("corviknight", registerInBuiltPosers.419.INSTANCE);
        this.inbuilt("duskull", registerInBuiltPosers.420.INSTANCE);
        this.inbuilt("dusclops", registerInBuiltPosers.421.INSTANCE);
        this.inbuilt("dusknoir", registerInBuiltPosers.422.INSTANCE);
        this.inbuilt("nickit", registerInBuiltPosers.423.INSTANCE);
        this.inbuilt("thievul", registerInBuiltPosers.424.INSTANCE);
        this.inbuilt("cacnea", registerInBuiltPosers.425.INSTANCE);
        this.inbuilt("cacturne", registerInBuiltPosers.426.INSTANCE);
        this.inbuilt("glimmet", registerInBuiltPosers.427.INSTANCE);
        this.inbuilt("glimmora", registerInBuiltPosers.428.INSTANCE);
        this.inbuilt("bonsly", registerInBuiltPosers.429.INSTANCE);
        this.inbuilt("sudowoodo", registerInBuiltPosers.430.INSTANCE);
        this.inbuilt("bouffalant", registerInBuiltPosers.431.INSTANCE);
        this.inbuilt("cetoddle", registerInBuiltPosers.432.INSTANCE);
        this.inbuilt("cetitan", registerInBuiltPosers.433.INSTANCE);
        this.inbuilt("venipede", registerInBuiltPosers.434.INSTANCE);
        this.inbuilt("whirlipede", registerInBuiltPosers.435.INSTANCE);
        this.inbuilt("scolipede", registerInBuiltPosers.436.INSTANCE);
        this.inbuilt("aipom", registerInBuiltPosers.437.INSTANCE);
        this.inbuilt("ambipom", registerInBuiltPosers.438.INSTANCE);
        this.inbuilt("hoothoot", registerInBuiltPosers.439.INSTANCE);
        this.inbuilt("noctowl", registerInBuiltPosers.440.INSTANCE);
        this.inbuilt("wingull", registerInBuiltPosers.441.INSTANCE);
        this.inbuilt("pelipper", registerInBuiltPosers.442.INSTANCE);
        this.inbuilt("shinx", registerInBuiltPosers.443.INSTANCE);
        this.inbuilt("luxio", registerInBuiltPosers.444.INSTANCE);
        this.inbuilt("luxray", registerInBuiltPosers.445.INSTANCE);
        this.inbuilt("numel", registerInBuiltPosers.446.INSTANCE);
        this.inbuilt("camerupt", registerInBuiltPosers.447.INSTANCE);
        this.inbuilt("vulpix_alolan", registerInBuiltPosers.448.INSTANCE);
        this.inbuilt("ninetales_alolan", registerInBuiltPosers.449.INSTANCE);
        this.inbuilt("roggenrola", registerInBuiltPosers.450.INSTANCE);
        this.inbuilt("boldore", registerInBuiltPosers.451.INSTANCE);
        this.inbuilt("gigalith", registerInBuiltPosers.452.INSTANCE);
        this.inbuilt("yamask", registerInBuiltPosers.453.INSTANCE);
        this.inbuilt("cofagrigus", registerInBuiltPosers.454.INSTANCE);
        this.inbuilt("mareep", registerInBuiltPosers.455.INSTANCE);
        this.inbuilt("flaaffy", registerInBuiltPosers.456.INSTANCE);
        this.inbuilt("ampharos", registerInBuiltPosers.457.INSTANCE);
        this.inbuilt("patrat", registerInBuiltPosers.458.INSTANCE);
        this.inbuilt("watchog", registerInBuiltPosers.459.INSTANCE);
        this.inbuilt("skrelp", registerInBuiltPosers.460.INSTANCE);
        this.inbuilt("dragalge", registerInBuiltPosers.461.INSTANCE);
        this.inbuilt("bunnelby", registerInBuiltPosers.462.INSTANCE);
        this.inbuilt("diggersby", registerInBuiltPosers.463.INSTANCE);
        this.inbuilt("arrokuda", registerInBuiltPosers.464.INSTANCE);
        this.inbuilt("barraskewda", registerInBuiltPosers.465.INSTANCE);
        this.inbuilt("shroodle", registerInBuiltPosers.466.INSTANCE);
        this.inbuilt("grafaiai", registerInBuiltPosers.467.INSTANCE);
        this.inbuilt("squawkabilly", registerInBuiltPosers.468.INSTANCE);
        this.inbuilt("annihilape", registerInBuiltPosers.469.INSTANCE);
        this.inbuilt("ponyta_galarian", registerInBuiltPosers.470.INSTANCE);
        this.inbuilt("rapidash_galarian", registerInBuiltPosers.471.INSTANCE);
        this.inbuilt("volbeat", registerInBuiltPosers.472.INSTANCE);
        this.inbuilt("illumise", registerInBuiltPosers.473.INSTANCE);
        this.inbuilt("yamper", registerInBuiltPosers.474.INSTANCE);
        this.inbuilt("boltund", registerInBuiltPosers.475.INSTANCE);
        this.inbuilt("tinkatink", registerInBuiltPosers.476.INSTANCE);
        this.inbuilt("tinkatuff", registerInBuiltPosers.477.INSTANCE);
        this.inbuilt("tinkaton", registerInBuiltPosers.478.INSTANCE);
        this.inbuilt("fuecoco", registerInBuiltPosers.479.INSTANCE);
        this.inbuilt("crocalor", registerInBuiltPosers.480.INSTANCE);
        this.inbuilt("skeledirge", registerInBuiltPosers.481.INSTANCE);
        this.inbuilt("quaxly", registerInBuiltPosers.482.INSTANCE);
        this.inbuilt("quaxwell", registerInBuiltPosers.483.INSTANCE);
        this.inbuilt("quaquaval", registerInBuiltPosers.484.INSTANCE);
        this.inbuilt("snubbull", registerInBuiltPosers.485.INSTANCE);
        this.inbuilt("granbull", registerInBuiltPosers.486.INSTANCE);
        this.inbuilt("maschiff", registerInBuiltPosers.487.INSTANCE);
        this.inbuilt("mabosstiff", registerInBuiltPosers.488.INSTANCE);
        this.inbuilt("phanpy", registerInBuiltPosers.489.INSTANCE);
        this.inbuilt("donphan", registerInBuiltPosers.490.INSTANCE);
        this.inbuilt("buizel", registerInBuiltPosers.491.INSTANCE);
        this.inbuilt("floatzel", registerInBuiltPosers.492.INSTANCE);
        this.inbuilt("zigzagoon", registerInBuiltPosers.493.INSTANCE);
        this.inbuilt("linoone", registerInBuiltPosers.494.INSTANCE);
        this.inbuilt("zigzagoon_galarian", registerInBuiltPosers.495.INSTANCE);
        this.inbuilt("linoone_galarian", registerInBuiltPosers.496.INSTANCE);
        this.inbuilt("obstagoon", registerInBuiltPosers.497.INSTANCE);
        this.inbuilt("cottonee", registerInBuiltPosers.498.INSTANCE);
        this.inbuilt("whimsicott", registerInBuiltPosers.499.INSTANCE);
        this.inbuilt("wishiwashi_solo", registerInBuiltPosers.500.INSTANCE);
        this.inbuilt("wishiwashi_schooling", registerInBuiltPosers.501.INSTANCE);
        this.inbuilt("meowth_alolan", registerInBuiltPosers.502.INSTANCE);
        this.inbuilt("meowth_galarian", registerInBuiltPosers.503.INSTANCE);
        this.inbuilt("persian_alolan", registerInBuiltPosers.504.INSTANCE);
        this.inbuilt("perrserker", registerInBuiltPosers.505.INSTANCE);
        this.inbuilt("starly", registerInBuiltPosers.506.INSTANCE);
        this.inbuilt("staravia", registerInBuiltPosers.507.INSTANCE);
        this.inbuilt("staraptor", registerInBuiltPosers.508.INSTANCE);
        this.inbuilt("komala", registerInBuiltPosers.509.INSTANCE);
        this.inbuilt("phantump", registerInBuiltPosers.510.INSTANCE);
        this.inbuilt("trevenant", registerInBuiltPosers.511.INSTANCE);
        this.inbuilt("totodile", registerInBuiltPosers.512.INSTANCE);
        this.inbuilt("croconaw", registerInBuiltPosers.513.INSTANCE);
        this.inbuilt("feraligatr", registerInBuiltPosers.514.INSTANCE);
        this.inbuilt("cyndaquil", registerInBuiltPosers.515.INSTANCE);
        this.inbuilt("quilava", registerInBuiltPosers.516.INSTANCE);
        this.inbuilt("typhlosion", registerInBuiltPosers.517.INSTANCE);
        this.inbuilt("chikorita", registerInBuiltPosers.518.INSTANCE);
        this.inbuilt("bayleef", registerInBuiltPosers.519.INSTANCE);
        this.inbuilt("meganium", registerInBuiltPosers.520.INSTANCE);
        this.inbuilt("fletchling", registerInBuiltPosers.521.INSTANCE);
        this.inbuilt("fletchinder", registerInBuiltPosers.522.INSTANCE);
        this.inbuilt("talonflame", registerInBuiltPosers.523.INSTANCE);
        this.inbuilt("crabrawler", registerInBuiltPosers.524.INSTANCE);
        this.inbuilt("crabominable", registerInBuiltPosers.525.INSTANCE);
        this.inbuilt("wimpod", registerInBuiltPosers.526.INSTANCE);
        this.inbuilt("golisopod", registerInBuiltPosers.527.INSTANCE);
        this.inbuilt("nincada", registerInBuiltPosers.528.INSTANCE);
        this.inbuilt("ninjask", registerInBuiltPosers.529.INSTANCE);
        this.inbuilt("shedinja", registerInBuiltPosers.530.INSTANCE);
        this.inbuilt("ralts", registerInBuiltPosers.531.INSTANCE);
        this.inbuilt("kirlia", registerInBuiltPosers.532.INSTANCE);
        this.inbuilt("gardevoir", registerInBuiltPosers.533.INSTANCE);
        this.inbuilt("gallade", registerInBuiltPosers.534.INSTANCE);
        this.inbuilt("beldum", registerInBuiltPosers.535.INSTANCE);
        this.inbuilt("metang", registerInBuiltPosers.536.INSTANCE);
        this.inbuilt("metagross", registerInBuiltPosers.537.INSTANCE);
        this.inbuilt("ursaluna", registerInBuiltPosers.538.INSTANCE);
        this.inbuilt("lechonk", registerInBuiltPosers.539.INSTANCE);
        this.inbuilt("oinkologne_male", registerInBuiltPosers.540.INSTANCE);
        this.inbuilt("oinkologne_female", registerInBuiltPosers.541.INSTANCE);
        this.inbuilt("pidove", registerInBuiltPosers.542.INSTANCE);
        this.inbuilt("tranquill", registerInBuiltPosers.543.INSTANCE);
        this.inbuilt("unfezant", registerInBuiltPosers.544.INSTANCE);
        this.inbuilt("timburr", registerInBuiltPosers.545.INSTANCE);
        this.inbuilt("gurdurr", registerInBuiltPosers.546.INSTANCE);
        this.inbuilt("conkeldurr", registerInBuiltPosers.547.INSTANCE);
        this.inbuilt("clodsire", registerInBuiltPosers.548.INSTANCE);
        this.inbuilt("teddiursa", registerInBuiltPosers.549.INSTANCE);
        this.inbuilt("ursaring", registerInBuiltPosers.550.INSTANCE);
        this.inbuilt("litwick", registerInBuiltPosers.551.INSTANCE);
        this.inbuilt("lampent", registerInBuiltPosers.552.INSTANCE);
        this.inbuilt("chandelure", registerInBuiltPosers.553.INSTANCE);
        this.inbuilt("gimmighoulroaming", registerInBuiltPosers.554.INSTANCE);
        this.inbuilt("gimmighoulchest", registerInBuiltPosers.555.INSTANCE);
        this.inbuilt("gholdengo", registerInBuiltPosers.556.INSTANCE);
        this.inbuilt("drifloon", registerInBuiltPosers.557.INSTANCE);
        this.inbuilt("drifblim", registerInBuiltPosers.558.INSTANCE);
        this.inbuilt("lileep", registerInBuiltPosers.559.INSTANCE);
        this.inbuilt("cradily", registerInBuiltPosers.560.INSTANCE);
        this.inbuilt("tirtouga", registerInBuiltPosers.561.INSTANCE);
        this.inbuilt("carracosta", registerInBuiltPosers.562.INSTANCE);
        this.inbuilt("arctovish", registerInBuiltPosers.563.INSTANCE);
        this.inbuilt("dracovish", registerInBuiltPosers.564.INSTANCE);
        this.inbuilt("arctozolt", registerInBuiltPosers.565.INSTANCE);
        this.inbuilt("dracozolt", registerInBuiltPosers.566.INSTANCE);
        this.inbuilt("shieldon", registerInBuiltPosers.567.INSTANCE);
        this.inbuilt("bastiodon", registerInBuiltPosers.568.INSTANCE);
        this.inbuilt("cranidos", registerInBuiltPosers.569.INSTANCE);
        this.inbuilt("rampardos", registerInBuiltPosers.570.INSTANCE);
        this.inbuilt("basculegion", registerInBuiltPosers.571.INSTANCE);
        this.inbuilt("tyrunt", registerInBuiltPosers.572.INSTANCE);
        this.inbuilt("tyrantrum", registerInBuiltPosers.573.INSTANCE);
        this.inbuilt("anorith", registerInBuiltPosers.574.INSTANCE);
        this.inbuilt("armaldo", registerInBuiltPosers.575.INSTANCE);
        this.inbuilt("archen", registerInBuiltPosers.576.INSTANCE);
        this.inbuilt("archeops", registerInBuiltPosers.577.INSTANCE);
        this.inbuilt("aron", registerInBuiltPosers.578.INSTANCE);
        this.inbuilt("lairon", registerInBuiltPosers.579.INSTANCE);
        this.inbuilt("aggron", registerInBuiltPosers.580.INSTANCE);
        this.inbuilt("hippopotas", registerInBuiltPosers.581.INSTANCE);
        this.inbuilt("hippowdon", registerInBuiltPosers.582.INSTANCE);
        this.inbuilt("zorua", registerInBuiltPosers.583.INSTANCE);
        this.inbuilt("zorua_hisuian", registerInBuiltPosers.584.INSTANCE);
        this.inbuilt("zoroark", registerInBuiltPosers.585.INSTANCE);
        this.inbuilt("zoroark_hisuian", registerInBuiltPosers.586.INSTANCE);
        this.inbuilt("gossifleur", registerInBuiltPosers.587.INSTANCE);
        this.inbuilt("eldegoss", registerInBuiltPosers.588.INSTANCE);
        this.inbuilt("amaura", registerInBuiltPosers.589.INSTANCE);
        this.inbuilt("aurorus", registerInBuiltPosers.590.INSTANCE);
        this.inbuilt("voltorb_hisuian", registerInBuiltPosers.591.INSTANCE);
        this.inbuilt("electrode_hisuian", registerInBuiltPosers.592.INSTANCE);
        this.inbuilt("sentret", registerInBuiltPosers.593.INSTANCE);
        this.inbuilt("furret", registerInBuiltPosers.594.INSTANCE);
        this.inbuilt("qwilfish", registerInBuiltPosers.595.INSTANCE);
        this.inbuilt("qwilfish_hisuian", registerInBuiltPosers.596.INSTANCE);
        this.inbuilt("overqwil", registerInBuiltPosers.597.INSTANCE);
        this.inbuilt("sneasel_hisuian", registerInBuiltPosers.598.INSTANCE);
        this.inbuilt("sneasler", registerInBuiltPosers.599.INSTANCE);
        this.inbuilt("tropius", registerInBuiltPosers.600.INSTANCE);
        this.inbuilt("petilil", registerInBuiltPosers.601.INSTANCE);
        this.inbuilt("lilligant", registerInBuiltPosers.602.INSTANCE);
        this.inbuilt("petilil_hisui_bias", registerInBuiltPosers.603.INSTANCE);
        this.inbuilt("lilligant_hisuian", registerInBuiltPosers.604.INSTANCE);
        this.inbuilt("darumaka", registerInBuiltPosers.605.INSTANCE);
        this.inbuilt("darmanitan", registerInBuiltPosers.606.INSTANCE);
        this.inbuilt("darmanitan_zen", registerInBuiltPosers.607.INSTANCE);
        this.inbuilt("turtonator", registerInBuiltPosers.608.INSTANCE);
        this.inbuilt("stonjourner", registerInBuiltPosers.609.INSTANCE);
        this.inbuilt("cufant", registerInBuiltPosers.610.INSTANCE);
        this.inbuilt("copperajah", registerInBuiltPosers.611.INSTANCE);
        this.inbuilt("budew", registerInBuiltPosers.612.INSTANCE);
        this.inbuilt("roselia", registerInBuiltPosers.613.INSTANCE);
        this.inbuilt("roserade", registerInBuiltPosers.614.INSTANCE);
        this.inbuilt("solrock", registerInBuiltPosers.615.INSTANCE);
        this.inbuilt("lunatone", registerInBuiltPosers.616.INSTANCE);
        this.inbuilt("woobat", registerInBuiltPosers.617.INSTANCE);
        this.inbuilt("swoobat", registerInBuiltPosers.618.INSTANCE);
        this.inbuilt("sandile", registerInBuiltPosers.619.INSTANCE);
        this.inbuilt("krokorok", registerInBuiltPosers.620.INSTANCE);
        this.inbuilt("krookodile", registerInBuiltPosers.621.INSTANCE);
        this.inbuilt("frillish", registerInBuiltPosers.622.INSTANCE);
        this.inbuilt("jellicent", registerInBuiltPosers.623.INSTANCE);
        this.inbuilt("cubchoo", registerInBuiltPosers.624.INSTANCE);
        this.inbuilt("beartic", registerInBuiltPosers.625.INSTANCE);
        this.inbuilt("deino", registerInBuiltPosers.626.INSTANCE);
        this.inbuilt("zweilous", registerInBuiltPosers.627.INSTANCE);
        this.inbuilt("hydreigon", registerInBuiltPosers.628.INSTANCE);
        this.inbuilt("larvesta", registerInBuiltPosers.629.INSTANCE);
        this.inbuilt("volcarona", registerInBuiltPosers.630.INSTANCE);
        this.inbuilt("fomantis", registerInBuiltPosers.631.INSTANCE);
        this.inbuilt("lurantis", registerInBuiltPosers.632.INSTANCE);
        this.inbuilt("dreepy", registerInBuiltPosers.633.INSTANCE);
        this.inbuilt("drakloak", registerInBuiltPosers.634.INSTANCE);
        this.inbuilt("dragapult", registerInBuiltPosers.635.INSTANCE);
        this.inbuilt("diglett_alolan", registerInBuiltPosers.636.INSTANCE);
        this.inbuilt("dugtrio_alolan", registerInBuiltPosers.637.INSTANCE);
        this.inbuilt("makuhita", registerInBuiltPosers.638.INSTANCE);
        this.inbuilt("hariyama", registerInBuiltPosers.639.INSTANCE);
        this.inbuilt("alomomola", registerInBuiltPosers.640.INSTANCE);
        this.inbuilt("ferroseed", registerInBuiltPosers.641.INSTANCE);
        this.inbuilt("ferrothorn", registerInBuiltPosers.642.INSTANCE);
        this.inbuilt("flabebe", registerInBuiltPosers.643.INSTANCE);
        this.inbuilt("floette", registerInBuiltPosers.644.INSTANCE);
        this.inbuilt("florges", registerInBuiltPosers.645.INSTANCE);
        this.inbuilt("carbink", registerInBuiltPosers.646.INSTANCE);
        this.inbuilt("goomy", registerInBuiltPosers.647.INSTANCE);
        this.inbuilt("goomy_hisui_bias", registerInBuiltPosers.648.INSTANCE);
        this.inbuilt("sliggoo", registerInBuiltPosers.649.INSTANCE);
        this.inbuilt("sliggoo_hisuian", registerInBuiltPosers.650.INSTANCE);
        this.inbuilt("goodra", registerInBuiltPosers.651.INSTANCE);
        this.inbuilt("goodra_hisuian", registerInBuiltPosers.652.INSTANCE);
        this.inbuilt("heracross", registerInBuiltPosers.653.INSTANCE);
        this.inbuilt("skarmory", registerInBuiltPosers.654.INSTANCE);
        this.inbuilt("salandit", registerInBuiltPosers.655.INSTANCE);
        this.inbuilt("salazzle", registerInBuiltPosers.656.INSTANCE);
        this.inbuilt("jangmo-o", registerInBuiltPosers.657.INSTANCE);
        this.inbuilt("hakamo-o", registerInBuiltPosers.658.INSTANCE);
        this.inbuilt("kommo-o", registerInBuiltPosers.659.INSTANCE);
        this.inbuilt("trapinch", registerInBuiltPosers.660.INSTANCE);
        this.inbuilt("vibrava", registerInBuiltPosers.661.INSTANCE);
        this.inbuilt("flygon", registerInBuiltPosers.662.INSTANCE);
        this.inbuilt("larvitar", registerInBuiltPosers.663.INSTANCE);
        this.inbuilt("pupitar", registerInBuiltPosers.664.INSTANCE);
        this.inbuilt("tyranitar", registerInBuiltPosers.665.INSTANCE);
        this.inbuilt("impidimp", registerInBuiltPosers.666.INSTANCE);
        this.inbuilt("morgrem", registerInBuiltPosers.667.INSTANCE);
        this.inbuilt("grimmsnarl", registerInBuiltPosers.668.INSTANCE);
        this.inbuilt("klefki", registerInBuiltPosers.669.INSTANCE);
        this.inbuilt("oshawott_hisui_bias", registerInBuiltPosers.670.INSTANCE);
        this.inbuilt("dewott_hisui_bias", registerInBuiltPosers.671.INSTANCE);
        this.inbuilt("samurott_hisuian", registerInBuiltPosers.672.INSTANCE);
        this.inbuilt("cyndaquil_hisui_bias", registerInBuiltPosers.673.INSTANCE);
        this.inbuilt("quilava_hisui_bias", registerInBuiltPosers.674.INSTANCE);
        this.inbuilt("typhlosion_hisuian", registerInBuiltPosers.675.INSTANCE);
        this.inbuilt("rowlet_hisui_bias", registerInBuiltPosers.676.INSTANCE);
        this.inbuilt("dartrix_hisui_bias", registerInBuiltPosers.677.INSTANCE);
        this.inbuilt("decidueye_hisuian", registerInBuiltPosers.678.INSTANCE);
        this.inbuilt("smeargle", registerInBuiltPosers.679.INSTANCE);
    }

    @Override
    @NotNull
    public Function1<Bone, PokemonPoseableModel> loadJsonPoser(@NotNull String json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        JsonObject jsonObject = (JsonObject)JsonPokemonPoseableModel.Companion.getGson().fromJson(json, JsonObject.class);
        return (Function1)new Function1<Bone, JsonPokemonPoseableModel>(jsonObject){
            final /* synthetic */ JsonObject $jsonObject;
            {
                this.$jsonObject = $jsonObject;
                super(1);
            }

            public final JsonPokemonPoseableModel invoke(@NotNull Bone it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                JsonPokemonPoseableModel.JsonPokemonPoseableModelAdapter.INSTANCE.setModelPart(it);
                Object object = JsonPokemonPoseableModel.Companion.getGson().fromJson((JsonElement)this.$jsonObject, JsonPokemonPoseableModel.class);
                JsonPokemonPoseableModel it2 = (JsonPokemonPoseableModel)object;
                boolean bl = false;
                it2.getPoses().forEach((arg_0, arg_1) -> loadJsonPoser.1.invoke$lambda$1$lambda$0(loadJsonPoser.1.1.INSTANCE, arg_0, arg_1));
                Object object2 = object;
                Intrinsics.checkNotNullExpressionValue((Object)object2, (String)"JsonPokemonPoseableModel\u2026 poseName }\n            }");
                return (JsonPokemonPoseableModel)object2;
            }

            private static final void invoke$lambda$1$lambda$0(Function2 $tmp0, Object p0, Object p1) {
                Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
                $tmp0.invoke(p0, p1);
            }
        };
    }

    static {
        Object[] objectArray = new String[]{"bedrock/" + INSTANCE.getType() + "/resolvers", "bedrock/species"};
        variationDirectories = CollectionsKt.listOf((Object[])objectArray);
        objectArray = new String[]{"bedrock/" + INSTANCE.getType() + "/posers", "bedrock/posers"};
        poserDirectories = CollectionsKt.listOf((Object[])objectArray);
        objectArray = new String[]{"bedrock/" + INSTANCE.getType() + "/models", "bedrock/models"};
        modelDirectories = CollectionsKt.listOf((Object[])objectArray);
        objectArray = new String[]{"bedrock/animations", "bedrock/" + INSTANCE.getType() + "/animations"};
        animationDirectories = CollectionsKt.listOf((Object[])objectArray);
        isForLivingEntityRenderer = true;
        fallback = MiscUtilsKt.cobblemonResource("substitute");
    }
}

