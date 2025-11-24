/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item.Berry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0003\b\u008d\u0001\n\u0002\u0010!\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u000b\b\u0002\u00a2\u0006\u0006\b\u0095\u0001\u0010\u0096\u0001J\u0017\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0015\u0010\b\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0004\u00a2\u0006\u0004\b\b\u0010\tR\u0017\u0010\n\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\rR\u0017\u0010\u000e\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000b\u001a\u0004\b\u000f\u0010\rR\u0017\u0010\u0010\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\u000b\u001a\u0004\b\u0011\u0010\rR\u0017\u0010\u0012\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u000b\u001a\u0004\b\u0013\u0010\rR\u0017\u0010\u0014\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u000b\u001a\u0004\b\u0015\u0010\rR\u0017\u0010\u0016\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010\u000b\u001a\u0004\b\u0017\u0010\rR\u0017\u0010\u0018\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b\u0018\u0010\u000b\u001a\u0004\b\u0019\u0010\rR\u0017\u0010\u001a\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b\u001a\u0010\u000b\u001a\u0004\b\u001b\u0010\rR\u0017\u0010\u001c\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b\u001c\u0010\u000b\u001a\u0004\b\u001d\u0010\rR\u0017\u0010\u001e\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b\u001e\u0010\u000b\u001a\u0004\b\u001f\u0010\rR\u0017\u0010 \u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b \u0010\u000b\u001a\u0004\b!\u0010\rR\u0017\u0010\"\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b\"\u0010\u000b\u001a\u0004\b#\u0010\rR\u0017\u0010$\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b$\u0010\u000b\u001a\u0004\b%\u0010\rR\u0017\u0010&\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b&\u0010\u000b\u001a\u0004\b'\u0010\rR\u0017\u0010(\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b(\u0010\u000b\u001a\u0004\b)\u0010\rR\u0017\u0010*\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b*\u0010\u000b\u001a\u0004\b+\u0010\rR\u0017\u0010,\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b,\u0010\u000b\u001a\u0004\b-\u0010\rR\u0017\u0010.\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b.\u0010\u000b\u001a\u0004\b/\u0010\rR\u0017\u00100\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b0\u0010\u000b\u001a\u0004\b1\u0010\rR\u0017\u00102\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b2\u0010\u000b\u001a\u0004\b3\u0010\rR\u0017\u00104\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b4\u0010\u000b\u001a\u0004\b5\u0010\rR\u0017\u00106\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b6\u0010\u000b\u001a\u0004\b7\u0010\rR\u0017\u00108\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b8\u0010\u000b\u001a\u0004\b9\u0010\rR\u0017\u0010:\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b:\u0010\u000b\u001a\u0004\b;\u0010\rR\u0017\u0010<\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b<\u0010\u000b\u001a\u0004\b=\u0010\rR\u0017\u0010>\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b>\u0010\u000b\u001a\u0004\b?\u0010\rR\u0017\u0010@\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b@\u0010\u000b\u001a\u0004\bA\u0010\rR\u0017\u0010B\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\bB\u0010\u000b\u001a\u0004\bC\u0010\rR\u0017\u0010D\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\bD\u0010\u000b\u001a\u0004\bE\u0010\rR\u0017\u0010F\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\bF\u0010\u000b\u001a\u0004\bG\u0010\rR\u0017\u0010H\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\bH\u0010\u000b\u001a\u0004\bI\u0010\rR\u0017\u0010J\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\bJ\u0010\u000b\u001a\u0004\bK\u0010\rR\u0017\u0010L\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\bL\u0010\u000b\u001a\u0004\bM\u0010\rR\u0017\u0010N\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\bN\u0010\u000b\u001a\u0004\bO\u0010\rR\u0017\u0010P\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\bP\u0010\u000b\u001a\u0004\bQ\u0010\rR\u0017\u0010R\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\bR\u0010\u000b\u001a\u0004\bS\u0010\rR\u0017\u0010T\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\bT\u0010\u000b\u001a\u0004\bU\u0010\rR\u0017\u0010V\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\bV\u0010\u000b\u001a\u0004\bW\u0010\rR\u0017\u0010X\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\bX\u0010\u000b\u001a\u0004\bY\u0010\rR\u0017\u0010Z\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\bZ\u0010\u000b\u001a\u0004\b[\u0010\rR\u0017\u0010\\\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b\\\u0010\u000b\u001a\u0004\b]\u0010\rR\u0017\u0010^\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b^\u0010\u000b\u001a\u0004\b_\u0010\rR\u0017\u0010`\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b`\u0010\u000b\u001a\u0004\ba\u0010\rR\u0017\u0010b\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\bb\u0010\u000b\u001a\u0004\bc\u0010\rR\u0017\u0010d\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\bd\u0010\u000b\u001a\u0004\be\u0010\rR\u0017\u0010f\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\bf\u0010\u000b\u001a\u0004\bg\u0010\rR\u0017\u0010h\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\bh\u0010\u000b\u001a\u0004\bi\u0010\rR\u0017\u0010j\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\bj\u0010\u000b\u001a\u0004\bk\u0010\rR\u0017\u0010l\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\bl\u0010\u000b\u001a\u0004\bm\u0010\rR\u0017\u0010n\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\bn\u0010\u000b\u001a\u0004\bo\u0010\rR\u0017\u0010p\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\bp\u0010\u000b\u001a\u0004\bq\u0010\rR\u0017\u0010r\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\br\u0010\u000b\u001a\u0004\bs\u0010\rR\u0017\u0010t\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\bt\u0010\u000b\u001a\u0004\bu\u0010\rR\u0017\u0010v\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\bv\u0010\u000b\u001a\u0004\bw\u0010\rR\u0017\u0010x\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\bx\u0010\u000b\u001a\u0004\by\u0010\rR\u0017\u0010z\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\bz\u0010\u000b\u001a\u0004\b{\u0010\rR\u0017\u0010|\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b|\u0010\u000b\u001a\u0004\b}\u0010\rR\u0017\u0010~\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b~\u0010\u000b\u001a\u0004\b\u007f\u0010\rR\u001a\u0010\u0080\u0001\u001a\u00020\u00048\u0006\u00a2\u0006\u000e\n\u0005\b\u0080\u0001\u0010\u000b\u001a\u0005\b\u0081\u0001\u0010\rR\u001a\u0010\u0082\u0001\u001a\u00020\u00048\u0006\u00a2\u0006\u000e\n\u0005\b\u0082\u0001\u0010\u000b\u001a\u0005\b\u0083\u0001\u0010\rR\u001a\u0010\u0084\u0001\u001a\u00020\u00048\u0006\u00a2\u0006\u000e\n\u0005\b\u0084\u0001\u0010\u000b\u001a\u0005\b\u0085\u0001\u0010\rR\u001a\u0010\u0086\u0001\u001a\u00020\u00048\u0006\u00a2\u0006\u000e\n\u0005\b\u0086\u0001\u0010\u000b\u001a\u0005\b\u0087\u0001\u0010\rR\u001a\u0010\u0088\u0001\u001a\u00020\u00048\u0006\u00a2\u0006\u000e\n\u0005\b\u0088\u0001\u0010\u000b\u001a\u0005\b\u0089\u0001\u0010\rR\u001a\u0010\u008a\u0001\u001a\u00020\u00048\u0006\u00a2\u0006\u000e\n\u0005\b\u008a\u0001\u0010\u000b\u001a\u0005\b\u008b\u0001\u0010\rR\u001a\u0010\u008c\u0001\u001a\u00020\u00048\u0006\u00a2\u0006\u000e\n\u0005\b\u008c\u0001\u0010\u000b\u001a\u0005\b\u008d\u0001\u0010\rR\u001a\u0010\u008e\u0001\u001a\u00020\u00048\u0006\u00a2\u0006\u000e\n\u0005\b\u008e\u0001\u0010\u000b\u001a\u0005\b\u008f\u0001\u0010\rR\u001a\u0010\u0090\u0001\u001a\u00020\u00048\u0006\u00a2\u0006\u000e\n\u0005\b\u0090\u0001\u0010\u000b\u001a\u0005\b\u0091\u0001\u0010\rR\u001e\u0010\u0093\u0001\u001a\t\u0012\u0004\u0012\u00020\u00040\u0092\u00018\u0002X\u0082\u0004\u00a2\u0006\b\n\u0006\b\u0093\u0001\u0010\u0094\u0001\u00a8\u0006\u0097\u0001"}, d2={"Lcom/cobblemon/mod/common/api/item/Berries;", "", "Lnet/minecraft/resources/ResourceLocation;", "name", "Lcom/cobblemon/mod/common/api/item/Berry;", "getBerry", "(Lnet/minecraft/resources/ResourceLocation;)Lcom/cobblemon/mod/common/api/item/Berry;", "berry", "registerBerry", "(Lcom/cobblemon/mod/common/api/item/Berry;)Lcom/cobblemon/mod/common/api/item/Berry;", "AGUAV", "Lcom/cobblemon/mod/common/api/item/Berry;", "getAGUAV", "()Lcom/cobblemon/mod/common/api/item/Berry;", "APICOT", "getAPICOT", "ASPEAR", "getASPEAR", "BABIRI", "getBABIRI", "BELUE", "getBELUE", "BLUK", "getBLUK", "CHARTI", "getCHARTI", "CHERI", "getCHERI", "CHESTO", "getCHESTO", "CHILAN", "getCHILAN", "CHOPLE", "getCHOPLE", "COBA", "getCOBA", "COLBUR", "getCOLBUR", "CORNN", "getCORNN", "CUSTAP", "getCUSTAP", "DURIN", "getDURIN", "ENIGMA", "getENIGMA", "FIGY", "getFIGY", "GANLON", "getGANLON", "GREPA", "getGREPA", "HABAN", "getHABAN", "HONDEW", "getHONDEW", "IAPAPA", "getIAPAPA", "JABOCA", "getJABOCA", "KASIB", "getKASIB", "KEBIA", "getKEBIA", "KEE", "getKEE", "KELPSY", "getKELPSY", "LANSAT", "getLANSAT", "LEPPA", "getLEPPA", "LIECHI", "getLIECHI", "LUM", "getLUM", "MAGO", "getMAGO", "MAGOST", "getMAGOST", "MARANGA", "getMARANGA", "MICLE", "getMICLE", "NANAB", "getNANAB", "NOMEL", "getNOMEL", "OCCA", "getOCCA", "ORAN", "getORAN", "PAMTRE", "getPAMTRE", "PASSHO", "getPASSHO", "PAYAPA", "getPAYAPA", "PECHA", "getPECHA", "PERSIM", "getPERSIM", "PETAYA", "getPETAYA", "PINAP", "getPINAP", "POMEG", "getPOMEG", "QUALOT", "getQUALOT", "RABUTA", "getRABUTA", "RAWST", "getRAWST", "RAZZ", "getRAZZ", "RINDO", "getRINDO", "ROSELI", "getROSELI", "ROWAP", "getROWAP", "SALAC", "getSALAC", "SHUCA", "getSHUCA", "SITRUS", "getSITRUS", "SPELON", "getSPELON", "STARF", "getSTARF", "TAMATO", "getTAMATO", "TANGA", "getTANGA", "WACAN", "getWACAN", "WATMEL", "getWATMEL", "WEPEAR", "getWEPEAR", "WIKI", "getWIKI", "YACHE", "getYACHE", "", "allBerries", "Ljava/util/List;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nBerries.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Berries.kt\ncom/cobblemon/mod/common/api/item/Berries\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,107:1\n1#2:108\n*E\n"})
public final class Berries {
    @NotNull
    public static final Berries INSTANCE = new Berries();
    @NotNull
    private static final List<Berry> allBerries = new ArrayList();
    @NotNull
    private static final Berry CHERI = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("cheri"), 10, 0, 0, 0, 0));
    @NotNull
    private static final Berry CHESTO = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("chesto"), 0, 10, 0, 0, 0));
    @NotNull
    private static final Berry PECHA = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("pecha"), 0, 0, 10, 0, 0));
    @NotNull
    private static final Berry RAWST = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("rawst"), 0, 0, 0, 10, 0));
    @NotNull
    private static final Berry ASPEAR = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("aspear"), 0, 0, 0, 0, 10));
    @NotNull
    private static final Berry LEPPA = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("leppa"), 10, 0, 10, 10, 10));
    @NotNull
    private static final Berry ORAN = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("oran"), 10, 10, 0, 10, 10));
    @NotNull
    private static final Berry PERSIM = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("persim"), 10, 10, 10, 0, 10));
    @NotNull
    private static final Berry LUM = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("lum"), 10, 10, 10, 10, 0));
    @NotNull
    private static final Berry SITRUS = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("sitrus"), 0, 10, 10, 10, 10));
    @NotNull
    private static final Berry FIGY = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("figy"), 15, 0, 0, 0, 0));
    @NotNull
    private static final Berry WIKI = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("wiki"), 0, 15, 0, 0, 0));
    @NotNull
    private static final Berry MAGO = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("mago"), 0, 0, 15, 0, 0));
    @NotNull
    private static final Berry AGUAV = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("aguav"), 0, 0, 0, 15, 0));
    @NotNull
    private static final Berry IAPAPA = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("iapapa"), 0, 0, 0, 0, 15));
    @NotNull
    private static final Berry RAZZ = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("razz"), 10, 10, 0, 0, 0));
    @NotNull
    private static final Berry BLUK = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("bluk"), 0, 10, 10, 0, 0));
    @NotNull
    private static final Berry NANAB = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("nanab"), 0, 0, 10, 10, 0));
    @NotNull
    private static final Berry WEPEAR = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("wepear"), 0, 0, 0, 10, 10));
    @NotNull
    private static final Berry PINAP = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("pinap"), 10, 0, 0, 0, 10));
    @NotNull
    private static final Berry POMEG = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("pomeg"), 10, 0, 10, 10, 0));
    @NotNull
    private static final Berry KELPSY = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("kelpsy"), 0, 10, 0, 10, 10));
    @NotNull
    private static final Berry QUALOT = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("qualot"), 10, 0, 10, 0, 10));
    @NotNull
    private static final Berry HONDEW = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("hondew"), 10, 10, 0, 10, 0));
    @NotNull
    private static final Berry GREPA = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("grepa"), 0, 10, 10, 0, 10));
    @NotNull
    private static final Berry TAMATO = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("tamato"), 20, 10, 0, 0, 0));
    @NotNull
    private static final Berry CORNN = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("cornn"), 0, 20, 10, 0, 0));
    @NotNull
    private static final Berry MAGOST = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("magost"), 0, 0, 20, 10, 0));
    @NotNull
    private static final Berry RABUTA = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("rabuta"), 0, 0, 0, 20, 10));
    @NotNull
    private static final Berry NOMEL = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("nomel"), 10, 0, 0, 0, 20));
    @NotNull
    private static final Berry SPELON = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("spelon"), 30, 10, 0, 0, 0));
    @NotNull
    private static final Berry PAMTRE = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("pamtre"), 0, 30, 10, 0, 0));
    @NotNull
    private static final Berry WATMEL = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("watmel"), 0, 0, 30, 10, 0));
    @NotNull
    private static final Berry DURIN = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("durin"), 0, 0, 0, 30, 10));
    @NotNull
    private static final Berry BELUE = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("belue"), 10, 0, 0, 0, 30));
    @NotNull
    private static final Berry OCCA = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("occa"), 15, 0, 10, 0, 0));
    @NotNull
    private static final Berry PASSHO = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("passho"), 0, 15, 0, 10, 0));
    @NotNull
    private static final Berry WACAN = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("wacan"), 0, 0, 15, 0, 10));
    @NotNull
    private static final Berry RINDO = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("rindo"), 10, 0, 0, 15, 0));
    @NotNull
    private static final Berry YACHE = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("yache"), 0, 10, 0, 0, 15));
    @NotNull
    private static final Berry CHOPLE = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("chople"), 15, 0, 0, 10, 0));
    @NotNull
    private static final Berry KEBIA = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("kebia"), 0, 15, 0, 0, 10));
    @NotNull
    private static final Berry SHUCA = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("shuca"), 10, 0, 15, 0, 0));
    @NotNull
    private static final Berry COBA = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("coba"), 0, 10, 0, 15, 0));
    @NotNull
    private static final Berry PAYAPA = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("payapa"), 0, 0, 10, 0, 15));
    @NotNull
    private static final Berry TANGA = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("tanga"), 20, 0, 0, 0, 10));
    @NotNull
    private static final Berry CHARTI = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("charti"), 10, 20, 0, 0, 0));
    @NotNull
    private static final Berry KASIB = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("kasib"), 0, 10, 20, 0, 0));
    @NotNull
    private static final Berry HABAN = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("haban"), 0, 0, 10, 20, 0));
    @NotNull
    private static final Berry COLBUR = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("colbur"), 0, 0, 0, 10, 20));
    @NotNull
    private static final Berry BABIRI = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("babiri"), 25, 10, 0, 0, 0));
    @NotNull
    private static final Berry CHILAN = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("chilan"), 0, 25, 10, 0, 0));
    @NotNull
    private static final Berry LIECHI = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("liechi"), 30, 10, 30, 0, 0));
    @NotNull
    private static final Berry GANLON = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("ganlon"), 0, 30, 10, 30, 0));
    @NotNull
    private static final Berry SALAC = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("salac"), 0, 0, 30, 10, 30));
    @NotNull
    private static final Berry PETAYA = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("petaya"), 30, 0, 0, 30, 10));
    @NotNull
    private static final Berry APICOT = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("apicot"), 10, 30, 0, 0, 30));
    @NotNull
    private static final Berry LANSAT = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("lansat"), 30, 10, 30, 10, 30));
    @NotNull
    private static final Berry STARF = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("starf"), 30, 10, 30, 10, 30));
    @NotNull
    private static final Berry ENIGMA = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("enigma"), 40, 10, 0, 0, 0));
    @NotNull
    private static final Berry MICLE = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("micle"), 0, 40, 10, 0, 0));
    @NotNull
    private static final Berry CUSTAP = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("custap"), 0, 0, 40, 10, 0));
    @NotNull
    private static final Berry JABOCA = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("jaboca"), 0, 0, 0, 40, 10));
    @NotNull
    private static final Berry ROWAP = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("rowap"), 10, 0, 0, 0, 40));
    @NotNull
    private static final Berry ROSELI = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("roseli"), 0, 0, 25, 10, 0));
    @NotNull
    private static final Berry KEE = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("kee"), 30, 30, 10, 10, 10));
    @NotNull
    private static final Berry MARANGA = INSTANCE.registerBerry(new Berry(MiscUtilsKt.cobblemonResource("maranga"), 10, 10, 30, 30, 10));

    private Berries() {
    }

    @NotNull
    public final Berry getCHERI() {
        return CHERI;
    }

    @NotNull
    public final Berry getCHESTO() {
        return CHESTO;
    }

    @NotNull
    public final Berry getPECHA() {
        return PECHA;
    }

    @NotNull
    public final Berry getRAWST() {
        return RAWST;
    }

    @NotNull
    public final Berry getASPEAR() {
        return ASPEAR;
    }

    @NotNull
    public final Berry getLEPPA() {
        return LEPPA;
    }

    @NotNull
    public final Berry getORAN() {
        return ORAN;
    }

    @NotNull
    public final Berry getPERSIM() {
        return PERSIM;
    }

    @NotNull
    public final Berry getLUM() {
        return LUM;
    }

    @NotNull
    public final Berry getSITRUS() {
        return SITRUS;
    }

    @NotNull
    public final Berry getFIGY() {
        return FIGY;
    }

    @NotNull
    public final Berry getWIKI() {
        return WIKI;
    }

    @NotNull
    public final Berry getMAGO() {
        return MAGO;
    }

    @NotNull
    public final Berry getAGUAV() {
        return AGUAV;
    }

    @NotNull
    public final Berry getIAPAPA() {
        return IAPAPA;
    }

    @NotNull
    public final Berry getRAZZ() {
        return RAZZ;
    }

    @NotNull
    public final Berry getBLUK() {
        return BLUK;
    }

    @NotNull
    public final Berry getNANAB() {
        return NANAB;
    }

    @NotNull
    public final Berry getWEPEAR() {
        return WEPEAR;
    }

    @NotNull
    public final Berry getPINAP() {
        return PINAP;
    }

    @NotNull
    public final Berry getPOMEG() {
        return POMEG;
    }

    @NotNull
    public final Berry getKELPSY() {
        return KELPSY;
    }

    @NotNull
    public final Berry getQUALOT() {
        return QUALOT;
    }

    @NotNull
    public final Berry getHONDEW() {
        return HONDEW;
    }

    @NotNull
    public final Berry getGREPA() {
        return GREPA;
    }

    @NotNull
    public final Berry getTAMATO() {
        return TAMATO;
    }

    @NotNull
    public final Berry getCORNN() {
        return CORNN;
    }

    @NotNull
    public final Berry getMAGOST() {
        return MAGOST;
    }

    @NotNull
    public final Berry getRABUTA() {
        return RABUTA;
    }

    @NotNull
    public final Berry getNOMEL() {
        return NOMEL;
    }

    @NotNull
    public final Berry getSPELON() {
        return SPELON;
    }

    @NotNull
    public final Berry getPAMTRE() {
        return PAMTRE;
    }

    @NotNull
    public final Berry getWATMEL() {
        return WATMEL;
    }

    @NotNull
    public final Berry getDURIN() {
        return DURIN;
    }

    @NotNull
    public final Berry getBELUE() {
        return BELUE;
    }

    @NotNull
    public final Berry getOCCA() {
        return OCCA;
    }

    @NotNull
    public final Berry getPASSHO() {
        return PASSHO;
    }

    @NotNull
    public final Berry getWACAN() {
        return WACAN;
    }

    @NotNull
    public final Berry getRINDO() {
        return RINDO;
    }

    @NotNull
    public final Berry getYACHE() {
        return YACHE;
    }

    @NotNull
    public final Berry getCHOPLE() {
        return CHOPLE;
    }

    @NotNull
    public final Berry getKEBIA() {
        return KEBIA;
    }

    @NotNull
    public final Berry getSHUCA() {
        return SHUCA;
    }

    @NotNull
    public final Berry getCOBA() {
        return COBA;
    }

    @NotNull
    public final Berry getPAYAPA() {
        return PAYAPA;
    }

    @NotNull
    public final Berry getTANGA() {
        return TANGA;
    }

    @NotNull
    public final Berry getCHARTI() {
        return CHARTI;
    }

    @NotNull
    public final Berry getKASIB() {
        return KASIB;
    }

    @NotNull
    public final Berry getHABAN() {
        return HABAN;
    }

    @NotNull
    public final Berry getCOLBUR() {
        return COLBUR;
    }

    @NotNull
    public final Berry getBABIRI() {
        return BABIRI;
    }

    @NotNull
    public final Berry getCHILAN() {
        return CHILAN;
    }

    @NotNull
    public final Berry getLIECHI() {
        return LIECHI;
    }

    @NotNull
    public final Berry getGANLON() {
        return GANLON;
    }

    @NotNull
    public final Berry getSALAC() {
        return SALAC;
    }

    @NotNull
    public final Berry getPETAYA() {
        return PETAYA;
    }

    @NotNull
    public final Berry getAPICOT() {
        return APICOT;
    }

    @NotNull
    public final Berry getLANSAT() {
        return LANSAT;
    }

    @NotNull
    public final Berry getSTARF() {
        return STARF;
    }

    @NotNull
    public final Berry getENIGMA() {
        return ENIGMA;
    }

    @NotNull
    public final Berry getMICLE() {
        return MICLE;
    }

    @NotNull
    public final Berry getCUSTAP() {
        return CUSTAP;
    }

    @NotNull
    public final Berry getJABOCA() {
        return JABOCA;
    }

    @NotNull
    public final Berry getROWAP() {
        return ROWAP;
    }

    @NotNull
    public final Berry getROSELI() {
        return ROSELI;
    }

    @NotNull
    public final Berry getKEE() {
        return KEE;
    }

    @NotNull
    public final Berry getMARANGA() {
        return MARANGA;
    }

    @NotNull
    public final Berry registerBerry(@NotNull Berry berry) {
        Intrinsics.checkNotNullParameter((Object)berry, (String)"berry");
        allBerries.add(berry);
        return berry;
    }

    @Nullable
    public final Berry getBerry(@NotNull ResourceLocation name) {
        Object v0;
        block1: {
            Intrinsics.checkNotNullParameter((Object)name, (String)"name");
            Iterable iterable = allBerries;
            for (Object t : iterable) {
                Berry berry = (Berry)t;
                boolean bl = false;
                if (!Intrinsics.areEqual((Object)berry.getName(), (Object)name)) continue;
                v0 = t;
                break block1;
            }
            v0 = null;
        }
        return v0;
    }
}

