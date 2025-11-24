/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.level.Level
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.starter;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.starter.RenderableStarterCategory;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.starter.StarterCategory;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.RenderablePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u00172\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0017B\u0017\b\u0016\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012\u00a2\u0006\u0004\b\u0014\u0010\u0015B\u0017\b\u0000\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\u0004\b\u0014\u0010\u0016J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u001d\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\u000e\u001a\u00020\r8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0018"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/starter/OpenStarterUIPacket;", "Lcom/cobblemon/mod/common/api/net/NetworkPacket;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "encode", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "", "Lcom/cobblemon/mod/common/config/starter/RenderableStarterCategory;", "categories", "Ljava/util/List;", "getCategories", "()Ljava/util/List;", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "", "Lcom/cobblemon/mod/common/config/starter/StarterCategory;", "<init>", "(Ljava/util/Collection;)V", "(Ljava/util/List;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nOpenStarterUIPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 OpenStarterUIPacket.kt\ncom/cobblemon/mod/common/net/messages/client/starter/OpenStarterUIPacket\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,58:1\n1549#2:59\n1620#2,3:60\n1855#2:63\n1855#2,2:64\n1856#2:66\n*S KotlinDebug\n*F\n+ 1 OpenStarterUIPacket.kt\ncom/cobblemon/mod/common/net/messages/client/starter/OpenStarterUIPacket\n*L\n22#1:59\n22#1:60,3\n26#1:63\n30#1:64,2\n26#1:66\n*E\n"})
public final class OpenStarterUIPacket
implements NetworkPacket<OpenStarterUIPacket> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final List<RenderableStarterCategory> categories;
    @NotNull
    private final ResourceLocation id;
    @NotNull
    private static final ResourceLocation ID = MiscUtilsKt.cobblemonResource("open_starter");

    public OpenStarterUIPacket(@NotNull List<RenderableStarterCategory> categories) {
        Intrinsics.checkNotNullParameter(categories, (String)"categories");
        this.categories = categories;
        this.id = ID;
    }

    @NotNull
    public final List<RenderableStarterCategory> getCategories() {
        return this.categories;
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return this.id;
    }

    /*
     * WARNING - void declaration
     */
    public OpenStarterUIPacket(@NotNull Collection<StarterCategory> categories) {
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        Intrinsics.checkNotNullParameter(categories, (String)"categories");
        Iterable iterable = categories;
        OpenStarterUIPacket openStarterUIPacket = this;
        boolean $i$f$map = false;
        void var4_5 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            StarterCategory starterCategory = (StarterCategory)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            collection.add(it.asRenderableStarterCategory());
        }
        openStarterUIPacket((List)destination$iv$iv);
    }

    @Override
    public void encode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.writeInt(this.categories.size());
        Iterable $this$forEach$iv = this.categories;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            RenderableStarterCategory it = (RenderableStarterCategory)element$iv;
            boolean bl = false;
            buffer.m_130070_(it.getName());
            buffer.m_130070_(it.getDisplayName());
            buffer.writeInt(it.getPokemon().size());
            Iterable $this$forEach$iv2 = it.getPokemon();
            boolean $i$f$forEach2 = false;
            for (Object element$iv2 : $this$forEach$iv2) {
                RenderablePokemon it2 = (RenderablePokemon)element$iv2;
                boolean bl2 = false;
                it2.saveToBuffer(buffer);
            }
        }
    }

    @Override
    public void sendToPlayer(@NotNull ServerPlayer player) {
        NetworkPacket.DefaultImpls.sendToPlayer(this, player);
    }

    @Override
    public void sendToPlayers(@NotNull Iterable<? extends ServerPlayer> players2) {
        NetworkPacket.DefaultImpls.sendToPlayers(this, players2);
    }

    @Override
    public void sendToAllPlayers() {
        NetworkPacket.DefaultImpls.sendToAllPlayers(this);
    }

    @Override
    public void sendToServer() {
        NetworkPacket.DefaultImpls.sendToServer(this);
    }

    @Override
    public void sendToPlayersAround(double x, double y, double z, double distance, @NotNull ResourceKey<Level> worldKey, @NotNull Function1<? super ServerPlayer, Boolean> exclusionCondition) {
        NetworkPacket.DefaultImpls.sendToPlayersAround(this, x, y, z, distance, worldKey, exclusionCondition);
    }

    @Override
    @NotNull
    public FriendlyByteBuf toBuffer() {
        return NetworkPacket.DefaultImpls.toBuffer(this);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/starter/OpenStarterUIPacket$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lcom/cobblemon/mod/common/net/messages/client/starter/OpenStarterUIPacket;", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/starter/OpenStarterUIPacket;", "Lnet/minecraft/resources/ResourceLocation;", "ID", "Lnet/minecraft/resources/ResourceLocation;", "getID", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getID() {
            return ID;
        }

        @NotNull
        public final OpenStarterUIPacket decode(@NotNull FriendlyByteBuf buffer) {
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            int numCategories = buffer.readInt();
            ArrayList<RenderableStarterCategory> categories = new ArrayList<RenderableStarterCategory>();
            for (int i = 0; i < numCategories; ++i) {
                String name = buffer.m_130277_();
                String displayName = buffer.m_130277_();
                int numProperties = buffer.readInt();
                List renderablePokemon = new ArrayList();
                int n = 0;
                while (n < numProperties) {
                    int it = n++;
                    boolean bl = false;
                    renderablePokemon.add(RenderablePokemon.Companion.loadFromBuffer(buffer));
                }
                Intrinsics.checkNotNullExpressionValue((Object)name, (String)"name");
                Intrinsics.checkNotNullExpressionValue((Object)displayName, (String)"displayName");
                categories.add(new RenderableStarterCategory(name, displayName, renderablePokemon));
            }
            return new OpenStarterUIPacket((List<RenderableStarterCategory>)categories);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

