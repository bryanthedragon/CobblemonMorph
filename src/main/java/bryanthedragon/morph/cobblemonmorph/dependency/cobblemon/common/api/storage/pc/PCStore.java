/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.IntRange
 *  kotlin.ranges.RangesKt
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.BottomlessStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StoreCoordinates;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCBox;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCPosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.RemoveClientPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.SwapClientPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.InitializePCPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.MoveClientPCPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.SetPCPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0096\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010(\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010#\n\u0002\b\u000e\b\u0016\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0011\b\u0016\u0012\u0006\u0010e\u001a\u00020.\u00a2\u0006\u0004\bi\u00101B\u0017\u0012\u0006\u0010e\u001a\u00020.\u0012\u0006\u0010Y\u001a\u00020X\u00a2\u0006\u0004\bi\u0010jJ\u0015\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J\r\u0010\b\u001a\u00020\u0005\u00a2\u0006\u0004\b\b\u0010\tJ\u001a\u0010\f\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\n\u001a\u00020\u0002H\u0096\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\u0015\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00050\u000eH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0011\u0010\u0011\u001a\u0004\u0018\u00010\u0002H\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0015\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00030\u0013H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u000f\u0010\u0016\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\u0016\u0010\tJ\u0017\u0010\u0018\u001a\u00020\u00172\u0006\u0010\n\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u0016\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000b0\u001aH\u0096\u0002\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\u001d\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u001e\u001a\u00020\u001dH\u0016\u00a2\u0006\u0004\b\u001f\u0010 J\u001d\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\"\u001a\u00020!H\u0016\u00a2\u0006\u0004\b#\u0010$J\u001d\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00020%2\u0006\u0010\"\u001a\u00020!H\u0016\u00a2\u0006\u0004\b&\u0010'J\u0015\u0010)\u001a\u00020\u00052\u0006\u0010(\u001a\u00020\u000b\u00a2\u0006\u0004\b)\u0010*J\u0017\u0010+\u001a\u00020\u00172\u0006\u0010(\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b+\u0010,J\r\u0010-\u001a\u00020\u0005\u00a2\u0006\u0004\b-\u0010\tJ\u0015\u00100\u001a\u00020\u00052\u0006\u0010/\u001a\u00020.\u00a2\u0006\u0004\b0\u00101J5\u00107\u001a\u00020\u00052\u0006\u00103\u001a\u0002022\b\b\u0002\u00104\u001a\u00020\u00172\u0014\b\u0002\u00106\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000505\u00a2\u0006\u0004\b7\u00108J\u001f\u00109\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\"\u001a\u00020!H\u0016\u00a2\u0006\u0004\b9\u0010:J\u0017\u0010;\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001dH\u0016\u00a2\u0006\u0004\b;\u0010<J\u0017\u0010=\u001a\u00020!2\u0006\u0010\"\u001a\u00020!H\u0016\u00a2\u0006\u0004\b=\u0010>J\u0017\u0010?\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b?\u0010\u0007J \u0010@\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010(\u001a\u00020\u000bH\u0096\u0002\u00a2\u0006\u0004\b@\u0010AJ!\u0010B\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u00022\b\u0010(\u001a\u0004\u0018\u00010\u000bH\u0014\u00a2\u0006\u0004\bB\u0010AJ\u001f\u0010E\u001a\u00020\u00052\u0006\u0010C\u001a\u00020\u00022\u0006\u0010D\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\bE\u0010FJ\r\u0010G\u001a\u00020\u0005\u00a2\u0006\u0004\bG\u0010\tR\u0017\u0010I\u001a\u00020H8\u0006\u00a2\u0006\f\n\u0004\bI\u0010J\u001a\u0004\bK\u0010LR\u001d\u0010O\u001a\b\u0012\u0004\u0012\u00020N0M8\u0006\u00a2\u0006\f\n\u0004\bO\u0010P\u001a\u0004\bQ\u0010\u0015R\"\u0010R\u001a\u00020\u00178\u0004@\u0004X\u0084\u000e\u00a2\u0006\u0012\n\u0004\bR\u0010S\u001a\u0004\bT\u0010U\"\u0004\bV\u0010WR\u0017\u0010Y\u001a\u00020X8\u0006\u00a2\u0006\f\n\u0004\bY\u0010Z\u001a\u0004\b[\u0010\\R\u001d\u0010^\u001a\b\u0012\u0004\u0012\u00020.0]8\u0006\u00a2\u0006\f\n\u0004\b^\u0010_\u001a\u0004\b`\u0010aR\u001d\u0010b\u001a\b\u0012\u0004\u0012\u00020\u00050\u000e8\u0006\u00a2\u0006\f\n\u0004\bb\u0010c\u001a\u0004\bd\u0010\u0010R\u0017\u0010e\u001a\u00020.8\u0006\u00a2\u0006\f\n\u0004\be\u0010f\u001a\u0004\bg\u0010h\u00a8\u0006k"}, d2={"Lcom/cobblemon/mod/common/api/storage/pc/PCStore;", "Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "Lcom/cobblemon/mod/common/api/storage/pc/PCPosition;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "addObserver", "(Lnet/minecraft/server/level/ServerPlayer;)V", "clearPC", "()V", "position", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "get", "(Lcom/cobblemon/mod/common/api/storage/pc/PCPosition;)Lcom/cobblemon/mod/common/pokemon/Pokemon;", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "getAnyChangeObservable", "()Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "getFirstAvailablePosition", "()Lcom/cobblemon/mod/common/api/storage/pc/PCPosition;", "", "getObservingPlayers", "()Ljava/util/List;", "initialize", "", "isValidPosition", "(Lcom/cobblemon/mod/common/api/storage/pc/PCPosition;)Z", "", "iterator", "()Ljava/util/Iterator;", "Lcom/google/gson/JsonObject;", "json", "loadFromJSON", "(Lcom/google/gson/JsonObject;)Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "loadFromNBT", "(Lnet/minecraft/nbt/CompoundTag;)Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "Lcom/cobblemon/mod/common/api/storage/StoreCoordinates;", "loadPositionFromNBT", "(Lnet/minecraft/nbt/CompoundTag;)Lcom/cobblemon/mod/common/api/storage/StoreCoordinates;", "pokemon", "relocateEvictedBoxPokemon", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "remove", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "removeDuplicates", "Ljava/util/UUID;", "playerID", "removeObserver", "(Ljava/util/UUID;)V", "", "newSize", "lockNewSize", "Lkotlin/Function1;", "overflowHandler", "resize", "(IZLkotlin/jvm/functions/Function1;)V", "savePositionToNBT", "(Lcom/cobblemon/mod/common/api/storage/pc/PCPosition;Lnet/minecraft/nbt/CompoundTag;)V", "saveToJSON", "(Lcom/google/gson/JsonObject;)Lcom/google/gson/JsonObject;", "saveToNBT", "(Lnet/minecraft/nbt/CompoundTag;)Lnet/minecraft/nbt/CompoundTag;", "sendTo", "set", "(Lcom/cobblemon/mod/common/api/storage/pc/PCPosition;Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "setAtPosition", "position1", "position2", "swap", "(Lcom/cobblemon/mod/common/api/storage/pc/PCPosition;Lcom/cobblemon/mod/common/api/storage/pc/PCPosition;)V", "tryRestoreBackedUpPokemon", "Lcom/cobblemon/mod/common/api/storage/BottomlessStore;", "backupStore", "Lcom/cobblemon/mod/common/api/storage/BottomlessStore;", "getBackupStore", "()Lcom/cobblemon/mod/common/api/storage/BottomlessStore;", "", "Lcom/cobblemon/mod/common/api/storage/pc/PCBox;", "boxes", "Ljava/util/List;", "getBoxes", "lockedSize", "Z", "getLockedSize", "()Z", "setLockedSize", "(Z)V", "Lnet/minecraft/network/chat/MutableComponent;", "name", "Lnet/minecraft/network/chat/MutableComponent;", "getName", "()Lnet/minecraft/network/chat/MutableComponent;", "", "observingUUIDs", "Ljava/util/Set;", "getObservingUUIDs", "()Ljava/util/Set;", "pcChangeObservable", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "getPcChangeObservable", "uuid", "Ljava/util/UUID;", "getUuid", "()Ljava/util/UUID;", "<init>", "(Ljava/util/UUID;Lnet/minecraft/network/chat/MutableComponent;)V", "common"})
@SourceDebugExtension(value={"SMAP\nPCStore.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PCStore.kt\ncom/cobblemon/mod/common/api/storage/pc/PCStore\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,255:1\n1360#2:256\n1446#2,5:257\n1603#2,9:262\n1855#2:271\n1856#2:273\n1612#2:274\n1855#2:275\n1856#2:277\n1855#2,2:278\n1855#2,2:280\n1360#2:282\n1446#2,5:283\n1855#2,2:288\n1864#2,3:290\n1864#2,3:293\n1855#2:296\n1856#2:299\n1#3:272\n1#3:276\n215#4,2:297\n*S KotlinDebug\n*F\n+ 1 PCStore.kt\ncom/cobblemon/mod/common/api/storage/pc/PCStore\n*L\n54#1:256\n54#1:257,5\n55#1:262,9\n55#1:271\n55#1:273\n55#1:274\n67#1:275\n67#1:277\n77#1:278,2\n81#1:280,2\n104#1:282\n104#1:283,5\n104#1:288,2\n129#1:290,3\n171#1:293,3\n249#1:296\n249#1:299\n55#1:272\n250#1:297,2\n*E\n"})
public class PCStore
extends PokemonStore<PCPosition> {
    @NotNull
    private final UUID uuid;
    @NotNull
    private final MutableComponent name;
    @NotNull
    private final List<PCBox> boxes;
    private boolean lockedSize;
    @NotNull
    private final BottomlessStore backupStore;
    @NotNull
    private final Set<UUID> observingUUIDs;
    @NotNull
    private final SimpleObservable<Unit> pcChangeObservable;

    public PCStore(@NotNull UUID uuid2, @NotNull MutableComponent name) {
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        this.uuid = uuid2;
        this.name = name;
        this.boxes = new ArrayList();
        this.backupStore = new BottomlessStore(new UUID(0L, 0L));
        Object[] objectArray = new UUID[]{this.uuid};
        this.observingUUIDs = SetsKt.mutableSetOf((Object[])objectArray);
        this.pcChangeObservable = new SimpleObservable();
    }

    @Override
    @NotNull
    public final UUID getUuid() {
        return this.uuid;
    }

    @NotNull
    public final MutableComponent getName() {
        return this.name;
    }

    public PCStore(@NotNull UUID uuid2) {
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        MutableComponent mutableComponent = LocalizationUtilsKt.lang("your_pc", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"your_pc\")");
        this(uuid2, mutableComponent);
    }

    @NotNull
    public final List<PCBox> getBoxes() {
        return this.boxes;
    }

    protected final boolean getLockedSize() {
        return this.lockedSize;
    }

    protected final void setLockedSize(boolean bl) {
        this.lockedSize = bl;
    }

    @NotNull
    public final BottomlessStore getBackupStore() {
        return this.backupStore;
    }

    @NotNull
    public final Set<UUID> getObservingUUIDs() {
        return this.observingUUIDs;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public Iterator<Pokemon> iterator() {
        void $this$flatMapTo$iv$iv;
        Iterable $this$flatMap$iv = this.boxes;
        boolean $i$f$flatMap = false;
        Iterable iterable = $this$flatMap$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$flatMapTo = false;
        for (Object element$iv$iv : $this$flatMapTo$iv$iv) {
            PCBox it = (PCBox)element$iv$iv;
            boolean bl = false;
            Iterable list$iv$iv = CollectionsKt.toList((Iterable)it);
            CollectionsKt.addAll((Collection)destination$iv$iv, (Iterable)list$iv$iv);
        }
        return ((List)destination$iv$iv).iterator();
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public List<ServerPlayer> getObservingPlayers() {
        void $this$mapNotNullTo$iv$iv;
        Iterable $this$mapNotNull$iv = this.observingUUIDs;
        boolean $i$f$mapNotNull = false;
        Iterable iterable = $this$mapNotNull$iv;
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
        return (List)destination$iv$iv;
    }

    public final void addObserver(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        UUID uUID = player.m_20148_();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"player.uuid");
        this.observingUUIDs.add(uUID);
        this.sendTo(player);
    }

    public final void removeObserver(@NotNull UUID playerID) {
        Intrinsics.checkNotNullParameter((Object)playerID, (String)"playerID");
        this.observingUUIDs.remove(playerID);
    }

    @NotNull
    public final SimpleObservable<Unit> getPcChangeObservable() {
        return this.pcChangeObservable;
    }

    @Override
    @Nullable
    public PCPosition getFirstAvailablePosition() {
        Iterable $this$forEach$iv = this.boxes;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            PCBox it = (PCBox)element$iv;
            boolean bl = false;
            PCPosition pCPosition = it.getFirstAvailablePosition();
            if (pCPosition == null) continue;
            PCPosition it2 = pCPosition;
            boolean bl2 = false;
            return it2;
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean isValidPosition(@NotNull PCPosition position) {
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        int n = this.boxes.size();
        int n2 = position.getBox();
        if (0 > n2) return false;
        if (n2 >= n) return false;
        boolean bl = true;
        if (!bl) return false;
        n = position.getSlot();
        if (0 > n) return false;
        if (n >= 30) return false;
        return true;
    }

    @Override
    public void sendTo(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        new InitializePCPacket(this).sendToPlayer(player);
        Iterable $this$forEach$iv = this.boxes;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            PCBox it = (PCBox)element$iv;
            boolean bl = false;
            it.sendTo(player);
        }
    }

    @Override
    public void initialize() {
        Iterable $this$forEach$iv = this.boxes;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            PCBox it = (PCBox)element$iv;
            boolean bl = false;
            it.initialize();
        }
        this.backupStore.initialize();
    }

    public final void relocateEvictedBoxPokemon(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        PCPosition space = this.getFirstAvailablePosition();
        if (space != null) {
            this.set(space, pokemon);
        } else {
            this.backupStore.add(pokemon);
        }
    }

    /*
     * WARNING - void declaration
     */
    public final void resize(int newSize, boolean lockNewSize, @NotNull Function1<? super Pokemon, Unit> overflowHandler) {
        Intrinsics.checkNotNullParameter(overflowHandler, (String)"overflowHandler");
        if (newSize <= 0) {
            throw new IllegalArgumentException("Invalid box count: Must be greater than zero.");
        }
        this.lockedSize = lockNewSize;
        if (this.boxes.size() > newSize) {
            void $this$forEach$iv;
            void $this$flatMapTo$iv$iv;
            List slicedBoxes = CollectionsKt.slice(this.boxes, (IntRange)RangesKt.until((int)newSize, (int)this.boxes.size()));
            this.boxes.removeAll(slicedBoxes);
            Iterable $this$flatMap$iv = slicedBoxes;
            boolean $i$f$flatMap = false;
            Iterable iterable = $this$flatMap$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$flatMapTo = false;
            for (Object element$iv$iv : $this$flatMapTo$iv$iv) {
                PCBox it = (PCBox)element$iv$iv;
                boolean bl = false;
                Iterable list$iv$iv = it;
                CollectionsKt.addAll((Collection)destination$iv$iv, (Iterable)list$iv$iv);
            }
            $this$flatMap$iv = (List)destination$iv$iv;
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                overflowHandler.invoke(element$iv);
            }
        } else {
            while (this.boxes.size() < newSize) {
                this.boxes.add(new PCBox(this));
            }
            this.tryRestoreBackedUpPokemon();
        }
        Unit[] unitArray = new Unit[]{Unit.INSTANCE};
        this.pcChangeObservable.emit((Unit[])unitArray);
    }

    public static /* synthetic */ void resize$default(PCStore pCStore, int n, boolean bl, Function1 function1, int n2, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: resize");
        }
        if ((n2 & 2) != 0) {
            bl = false;
        }
        if ((n2 & 4) != 0) {
            function1 = (Function1)new Function1<Pokemon, Unit>((Object)pCStore){

                public final void invoke(@NotNull Pokemon p0) {
                    Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                    ((PCStore)this.receiver).relocateEvictedBoxPokemon(p0);
                }
            };
        }
        pCStore.resize(n, bl, (Function1<? super Pokemon, Unit>)function1);
    }

    public final void tryRestoreBackedUpPokemon() {
        PCPosition newPosition = this.getFirstAvailablePosition();
        List backedUpPokemon = CollectionsKt.toMutableList((Collection)this.backupStore.getPokemon());
        while (newPosition != null && !((Collection)backedUpPokemon).isEmpty()) {
            this.set(newPosition, (Pokemon)backedUpPokemon.remove(0));
            newPosition = this.getFirstAvailablePosition();
        }
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public CompoundTag saveToNBT(@NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        nbt.m_128376_("BoxCount", (short)this.boxes.size());
        nbt.m_128379_("BoxCountLocked", this.lockedSize);
        Iterable $this$forEachIndexed$iv = this.boxes;
        boolean $i$f$forEachIndexed = false;
        int index$iv = 0;
        for (Object item$iv : $this$forEachIndexed$iv) {
            void box;
            int n;
            if ((n = index$iv++) < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            PCBox pCBox = (PCBox)item$iv;
            int index = n;
            boolean bl = false;
            nbt.m_128365_("Box" + index, (Tag)box.saveToNBT(new CompoundTag()));
        }
        nbt.m_128365_("BackupStore", (Tag)this.backupStore.saveToNBT(new CompoundTag()));
        return nbt;
    }

    @Override
    @NotNull
    public PokemonStore<PCPosition> loadFromNBT(@NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        int boxCountStored = nbt.m_128448_("BoxCount");
        int n = boxCountStored;
        for (int boxNumber = 0; boxNumber < n; ++boxNumber) {
            PCBox pCBox = new PCBox(this);
            CompoundTag compoundTag = nbt.m_128469_("Box" + boxNumber);
            Intrinsics.checkNotNullExpressionValue((Object)compoundTag, (String)"nbt.getCompound(DataKeys.STORE_BOX + boxNumber)");
            this.boxes.add(pCBox.loadFromNBT(compoundTag));
        }
        this.lockedSize = nbt.m_128471_("BoxCountLocked");
        if (!this.lockedSize && this.boxes.size() != Cobblemon.INSTANCE.getConfig().getDefaultBoxCount()) {
            PCStore.resize$default(this, Cobblemon.INSTANCE.getConfig().getDefaultBoxCount(), false, null, 4, null);
        } else {
            this.tryRestoreBackedUpPokemon();
        }
        this.removeDuplicates();
        return this;
    }

    public final void removeDuplicates() {
        List knownUUIDs = new ArrayList();
        for (PCBox box : this.boxes) {
            for (int i = 0; i < 30; ++i) {
                Pokemon pokemon;
                if (box.get(i) == null) continue;
                if (!knownUUIDs.contains(pokemon.getUuid())) {
                    UUID uUID = pokemon.getUuid();
                    Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"pokemon.uuid");
                    knownUUIDs.add(uUID);
                    continue;
                }
                box.set(i, null);
                Unit[] unitArray = new Unit[]{Unit.INSTANCE};
                this.pcChangeObservable.emit((Unit[])unitArray);
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public JsonObject saveToJSON(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        json.addProperty("BoxCount", (Number)((short)this.boxes.size()));
        json.addProperty("BoxCountLocked", Boolean.valueOf(this.lockedSize));
        Iterable $this$forEachIndexed$iv = this.boxes;
        boolean $i$f$forEachIndexed = false;
        int index$iv = 0;
        for (Object item$iv : $this$forEachIndexed$iv) {
            void box;
            int n;
            if ((n = index$iv++) < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            PCBox pCBox = (PCBox)item$iv;
            int index = n;
            boolean bl = false;
            json.add("Box" + index, (JsonElement)box.saveToJSON(new JsonObject()));
        }
        json.add("BackupStore", (JsonElement)this.backupStore.saveToJSON(new JsonObject()));
        return json;
    }

    @Override
    public void set(@NotNull PCPosition position, @NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        super.set((StorePosition)position, pokemon);
        this.sendPacketToObservers(new SetPCPokemonPacket(this.uuid, position, pokemon));
    }

    @Override
    public boolean remove(@NotNull Pokemon pokemon) {
        boolean bl;
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        if (super.remove(pokemon)) {
            PokemonStore pokemonStore = this;
            UUID uUID = pokemon.getUuid();
            Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"pokemon.uuid");
            this.sendPacketToObservers(new RemoveClientPokemonPacket(pokemonStore, uUID));
            bl = true;
        } else {
            bl = false;
        }
        return bl;
    }

    @Override
    public void swap(@NotNull PCPosition position1, @NotNull PCPosition position2) {
        Intrinsics.checkNotNullParameter((Object)position1, (String)"position1");
        Intrinsics.checkNotNullParameter((Object)position2, (String)"position2");
        Pokemon pokemon1 = this.get(position1);
        Pokemon pokemon2 = this.get(position2);
        super.swap((StorePosition)position1, (StorePosition)position2);
        if (pokemon1 != null && pokemon2 != null) {
            PokemonStore pokemonStore = this;
            UUID uUID = pokemon1.getUuid();
            Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"pokemon1.uuid");
            UUID uUID2 = pokemon2.getUuid();
            Intrinsics.checkNotNullExpressionValue((Object)uUID2, (String)"pokemon2.uuid");
            this.sendPacketToObservers(new SwapClientPokemonPacket(pokemonStore, uUID, uUID2));
        } else if (pokemon1 != null || pokemon2 != null) {
            PCPosition newPosition = pokemon1 == null ? position1 : position2;
            Pokemon pokemon = pokemon1;
            if (pokemon == null) {
                Pokemon pokemon3 = pokemon2;
                pokemon = pokemon3;
                Intrinsics.checkNotNull((Object)pokemon3);
            }
            Pokemon pokemon4 = pokemon;
            UUID uUID = pokemon4.getUuid();
            Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"pokemon.uuid");
            this.sendPacketToObservers(new MoveClientPCPokemonPacket(this.uuid, uUID, newPosition));
        }
    }

    @Override
    @NotNull
    public PokemonStore<PCPosition> loadFromJSON(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        int boxCountStored = json.get("BoxCount").getAsShort();
        int n = boxCountStored;
        for (int boxNumber = 0; boxNumber < n; ++boxNumber) {
            PCBox pCBox = new PCBox(this);
            JsonObject jsonObject = json.getAsJsonObject("Box" + boxNumber);
            Intrinsics.checkNotNullExpressionValue((Object)jsonObject, (String)"json.getAsJsonObject(Dat\u2026ys.STORE_BOX + boxNumber)");
            this.boxes.add(pCBox.loadFromJSON(jsonObject));
        }
        this.lockedSize = json.get("BoxCountLocked").getAsBoolean();
        if (!this.lockedSize && this.boxes.size() != Cobblemon.INSTANCE.getConfig().getDefaultBoxCount()) {
            PCStore.resize$default(this, Cobblemon.INSTANCE.getConfig().getDefaultBoxCount(), false, null, 4, null);
        } else {
            this.tryRestoreBackedUpPokemon();
        }
        this.removeDuplicates();
        return this;
    }

    @Override
    @NotNull
    public StoreCoordinates<PCPosition> loadPositionFromNBT(@NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        return new StoreCoordinates<StorePosition>(this, new PCPosition(nbt.m_128448_("Box"), nbt.m_128445_("Slot")));
    }

    @Override
    public void savePositionToNBT(@NotNull PCPosition position, @NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        nbt.m_128376_("Box", (short)position.getBox());
        nbt.m_128344_("Slot", (byte)position.getSlot());
    }

    @NotNull
    public SimpleObservable<Unit> getAnyChangeObservable() {
        return this.pcChangeObservable;
    }

    @Override
    protected void setAtPosition(@NotNull PCPosition position, @Nullable Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        int n = this.boxes.size();
        int n2 = position.getBox();
        if (!(0 <= n2 ? n2 < n : false)) {
            throw new IllegalArgumentException("Invalid box number " + position.getBox() + ". Should be between 0 and " + this.boxes.size());
        }
        this.boxes.get(position.getBox()).set(position.getSlot(), pokemon);
    }

    @Override
    @Nullable
    public Pokemon get(@NotNull PCPosition position) {
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        int n = this.boxes.size();
        int n2 = position.getBox();
        return !(0 <= n2 ? n2 < n : false) ? null : this.boxes.get(position.getBox()).get(position.getSlot());
    }

    public final void clearPC() {
        Iterable $this$forEach$iv = this.boxes;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            PCBox box = (PCBox)element$iv;
            boolean bl = false;
            Map<Integer, Pokemon> $this$forEach$iv2 = box.getNonEmptySlots();
            boolean $i$f$forEach2 = false;
            Iterator<Map.Entry<Integer, Pokemon>> iterator = $this$forEach$iv2.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Integer, Pokemon> element$iv2;
                Map.Entry<Integer, Pokemon> it = element$iv2 = iterator.next();
                boolean bl2 = false;
                this.remove((StorePosition)new PCPosition(box.getBoxNumber(), ((Number)it.getKey()).intValue()));
            }
        }
    }
}

