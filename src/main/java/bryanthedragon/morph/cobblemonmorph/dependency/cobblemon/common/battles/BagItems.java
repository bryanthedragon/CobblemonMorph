/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.io.CloseableKt
 *  kotlin.io.FilesKt
 *  kotlin.io.TextStreamsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.Charsets
 *  kotlin.text.StringsKt
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.server.packs.PackType
 *  net.minecraft.server.packs.resources.Resource
 *  net.minecraft.server.packs.resources.ResourceManager
 *  net.minecraft.world.item.ItemStack
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.PrioritizedList;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.DataRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle.BagItemConvertible;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b*\u0010+J\u0017\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\n\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0017\u0010\u000e\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fR\u001d\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00040\u00108\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014R&\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00160\u00158\u0000X\u0080\u0004\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001c\u001a\u00020\u001b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001fR \u0010!\u001a\b\u0012\u0004\u0012\u00020\u00000 8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b!\u0010\"\u001a\u0004\b#\u0010$R\u001a\u0010&\u001a\u00020%8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b&\u0010'\u001a\u0004\b(\u0010)\u00a8\u0006,"}, d2={"Lcom/cobblemon/mod/common/battles/BagItems;", "Lcom/cobblemon/mod/common/api/data/DataRegistry;", "Lnet/minecraft/world/item/ItemStack;", "stack", "Lcom/cobblemon/mod/common/item/battle/BagItemConvertible;", "getConvertibleForStack", "(Lnet/minecraft/world/item/ItemStack;)Lcom/cobblemon/mod/common/item/battle/BagItemConvertible;", "Lnet/minecraft/server/packs/resources/ResourceManager;", "manager", "", "reload", "(Lnet/minecraft/server/packs/resources/ResourceManager;)V", "Lnet/minecraft/server/level/ServerPlayer;", "player", "sync", "(Lnet/minecraft/server/level/ServerPlayer;)V", "Lcom/cobblemon/mod/common/api/PrioritizedList;", "bagItems", "Lcom/cobblemon/mod/common/api/PrioritizedList;", "getBagItems", "()Lcom/cobblemon/mod/common/api/PrioritizedList;", "", "", "bagItemsScripts", "Ljava/util/Map;", "getBagItemsScripts$common", "()Ljava/util/Map;", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "observable", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "getObservable", "()Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "Lnet/minecraft/server/packs/PackType;", "type", "Lnet/minecraft/server/packs/PackType;", "getType", "()Lnet/minecraft/server/packs/PackType;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nBagItems.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BagItems.kt\ncom/cobblemon/mod/common/battles/BagItems\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,64:1\n288#2,2:65\n215#3,2:67\n*S KotlinDebug\n*F\n+ 1 BagItems.kt\ncom/cobblemon/mod/common/battles/BagItems\n*L\n49#1:65,2\n53#1:67,2\n*E\n"})
public final class BagItems
implements DataRegistry {
    @NotNull
    public static final BagItems INSTANCE = new BagItems();
    @NotNull
    private static final ResourceLocation id = MiscUtilsKt.cobblemonResource("bag_items");
    @NotNull
    private static final PackType type = PackType.SERVER_DATA;
    @NotNull
    private static final SimpleObservable<BagItems> observable = new SimpleObservable();
    @NotNull
    private static final PrioritizedList<BagItemConvertible> bagItems = new PrioritizedList();
    @NotNull
    private static final Map<String, String> bagItemsScripts = new LinkedHashMap();

    private BagItems() {
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return id;
    }

    @Override
    @NotNull
    public PackType getType() {
        return type;
    }

    @NotNull
    public SimpleObservable<BagItems> getObservable() {
        return observable;
    }

    @Override
    public void sync(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
    }

    @NotNull
    public final PrioritizedList<BagItemConvertible> getBagItems() {
        return bagItems;
    }

    @NotNull
    public final Map<String, String> getBagItemsScripts$common() {
        return bagItemsScripts;
    }

    @Nullable
    public final BagItemConvertible getConvertibleForStack(@NotNull ItemStack stack) {
        Object v0;
        block1: {
            Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
            Iterable $this$firstOrNull$iv = bagItems;
            boolean $i$f$firstOrNull = false;
            for (Object element$iv : $this$firstOrNull$iv) {
                BagItemConvertible it = (BagItemConvertible)element$iv;
                boolean bl = false;
                if (!(it.getBagItem(stack) != null)) continue;
                v0 = element$iv;
                break block1;
            }
            v0 = null;
        }
        return v0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void reload(@NotNull ResourceManager manager) {
        Intrinsics.checkNotNullParameter((Object)manager, (String)"manager");
        Map map = manager.m_214159_("bag_items", BagItems::reload$lambda$1);
        Intrinsics.checkNotNullExpressionValue((Object)map, (String)"manager.findResources(\"b\u2026it.path.endsWith(\".js\") }");
        Map $this$forEach$iv = map;
        boolean $i$f$forEach = false;
        Iterator iterator = $this$forEach$iv.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry element$iv;
            Map.Entry entry = element$iv = iterator.next();
            boolean bl = false;
            ResourceLocation identifier = (ResourceLocation)entry.getKey();
            Resource resource = (Resource)entry.getValue();
            Closeable closeable = resource.m_215507_();
            Throwable throwable = null;
            try {
                InputStream stream = (InputStream)closeable;
                boolean bl2 = false;
                Intrinsics.checkNotNullExpressionValue((Object)stream, (String)"stream");
                Closeable closeable2 = stream;
                Object object = Charsets.UTF_8;
                Reader reader = new InputStreamReader((InputStream)closeable2, (Charset)object);
                int n = 8192;
                closeable2 = reader instanceof BufferedReader ? (BufferedReader)reader : new BufferedReader(reader, n);
                object = null;
                try {
                    BufferedReader reader2 = (BufferedReader)closeable2;
                    boolean bl3 = false;
                    ResourceLocation resolvedIdentifier = new ResourceLocation(identifier.m_135827_(), FilesKt.getNameWithoutExtension((File)new File(identifier.m_135815_())));
                    String js = TextStreamsKt.readText((Reader)reader2);
                    Map<String, String> map2 = bagItemsScripts;
                    String string = resolvedIdentifier.m_135815_();
                    Intrinsics.checkNotNullExpressionValue((Object)string, (String)"resolvedIdentifier.path");
                    map2.put(string, js);
                    reader = Unit.INSTANCE;
                }
                catch (Throwable throwable2) {
                    object = throwable2;
                    throw throwable2;
                }
                finally {
                    CloseableKt.closeFinally((Closeable)closeable2, (Throwable)object);
                }
                Unit unit = Unit.INSTANCE;
            }
            catch (Throwable throwable3) {
                throwable = throwable3;
                throw throwable3;
            }
            finally {
                CloseableKt.closeFinally((Closeable)closeable, (Throwable)throwable);
            }
        }
        BagItems[] bagItemsArray = new BagItems[]{this};
        this.getObservable().emit((BagItems[])bagItemsArray);
    }

    private static final boolean reload$lambda$1(ResourceLocation it) {
        String string = it.m_135815_();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"it.path");
        return StringsKt.endsWith$default((String)string, (String)".js", (boolean)false, (int)2, null);
    }

    static {
        Observable.DefaultImpls.subscribe$default(INSTANCE.getObservable(), null, 1.INSTANCE, 1, null);
    }
}

