/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.SharedSuggestionProvider
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.server.permission.PermissionAPI
 *  net.minecraftforge.server.permission.events.PermissionGatherEvent$Nodes
 *  net.minecraftforge.server.permission.nodes.PermissionDynamicContext
 *  net.minecraftforge.server.permission.nodes.PermissionDynamicContextKey
 *  net.minecraftforge.server.permission.nodes.PermissionNode
 *  net.minecraftforge.server.permission.nodes.PermissionTypes
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.mod.forge.permission;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermissions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.Permission;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.PermissionValidator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.server.permission.PermissionAPI;
import net.minecraftforge.server.permission.events.PermissionGatherEvent;
import net.minecraftforge.server.permission.nodes.PermissionDynamicContext;
import net.minecraftforge.server.permission.nodes.PermissionDynamicContextKey;
import net.minecraftforge.server.permission.nodes.PermissionNode;
import net.minecraftforge.server.permission.nodes.PermissionTypes;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u001d\u0010\u0017J#\u0010\u0006\u001a\u0016\u0012\u0012\u0012\u0010\u0012\f\u0012\n \u0005*\u0004\u0018\u00010\u00040\u00040\u00030\u0002H\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0019\u0010\u000b\u001a\u0004\u0018\u00010\n2\u0006\u0010\t\u001a\u00020\bH\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ\u001f\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00032\u0006\u0010\u000e\u001a\u00020\rH\u0002\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u001f\u0010\u0011\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u001f\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0014J\u000f\u0010\u0016\u001a\u00020\u0015H\u0016\u00a2\u0006\u0004\b\u0016\u0010\u0017R<\u0010\u001b\u001a*\u0012\u0004\u0012\u00020\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0018j\u0014\u0012\u0004\u0012\u00020\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u001a8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001b\u0010\u001c\u00a8\u0006\u001e"}, d2={"Lcom/cobblemon/mod/forge/permission/ForgePermissionValidator;", "Lcom/cobblemon/mod/common/api/permission/PermissionValidator;", "", "Lnet/minecraftforge/server/permission/nodes/PermissionNode;", "", "kotlin.jvm.PlatformType", "createNodes", "()Ljava/util/List;", "Lnet/minecraft/commands/SharedSuggestionProvider;", "source", "Lnet/minecraft/server/level/ServerPlayer;", "extractPlayerFromSource", "(Lnet/minecraft/commands/SharedSuggestionProvider;)Lnet/minecraft/server/level/ServerPlayer;", "Lcom/cobblemon/mod/common/api/permission/Permission;", "permission", "findNode", "(Lcom/cobblemon/mod/common/api/permission/Permission;)Lnet/minecraftforge/server/permission/nodes/PermissionNode;", "hasPermission", "(Lnet/minecraft/commands/SharedSuggestionProvider;Lcom/cobblemon/mod/common/api/permission/Permission;)Z", "player", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/api/permission/Permission;)Z", "", "initialize", "()V", "Ljava/util/HashMap;", "Lnet/minecraft/resources/ResourceLocation;", "Lkotlin/collections/HashMap;", "nodes", "Ljava/util/HashMap;", "<init>", "forge"})
@SourceDebugExtension(value={"SMAP\nForgePermissionValidator.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ForgePermissionValidator.kt\ncom/cobblemon/mod/forge/permission/ForgePermissionValidator\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,64:1\n1549#2:65\n1620#2,3:66\n*S KotlinDebug\n*F\n+ 1 ForgePermissionValidator.kt\ncom/cobblemon/mod/forge/permission/ForgePermissionValidator\n*L\n52#1:65\n52#1:66,3\n*E\n"})
public final class ForgePermissionValidator
implements PermissionValidator {
    @NotNull
    public static final ForgePermissionValidator INSTANCE = new ForgePermissionValidator();
    @NotNull
    private static final HashMap<ResourceLocation, PermissionNode<Boolean>> nodes = new HashMap();

    private ForgePermissionValidator() {
    }

    @Override
    public void initialize() {
        Cobblemon.INSTANCE.getLOGGER().info("Booting ForgePermissionApiPermissionValidator, player permissions will be checked using MinecraftForge' PermissionAPI, non player command sources will use Minecraft' permission level system, see https://docs.minecraftforge.net/en/latest/ and https://minecraft.fandom.com/wiki/Permission_level");
    }

    @Override
    public boolean hasPermission(@NotNull ServerPlayer player, @NotNull Permission permission2) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)permission2, (String)"permission");
        PermissionNode<Boolean> permissionNode = this.findNode(permission2);
        if (permissionNode == null) {
            return player.m_20310_(permission2.getLevel().getNumericalValue());
        }
        PermissionNode<Boolean> node = permissionNode;
        Object object = PermissionAPI.getPermission((ServerPlayer)player, node, (PermissionDynamicContext[])new PermissionDynamicContext[0]);
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"getPermission(player, node)");
        return (Boolean)object;
    }

    @Override
    public boolean hasPermission(@NotNull SharedSuggestionProvider source, @NotNull Permission permission2) {
        Intrinsics.checkNotNullParameter((Object)source, (String)"source");
        Intrinsics.checkNotNullParameter((Object)permission2, (String)"permission");
        ServerPlayer serverPlayer = this.extractPlayerFromSource(source);
        if (serverPlayer == null) {
            return source.m_6761_(permission2.getLevel().getNumericalValue());
        }
        ServerPlayer player = serverPlayer;
        PermissionNode<Boolean> permissionNode = this.findNode(permission2);
        if (permissionNode == null) {
            return source.m_6761_(permission2.getLevel().getNumericalValue());
        }
        PermissionNode<Boolean> node = permissionNode;
        Object object = PermissionAPI.getPermission((ServerPlayer)player, node, (PermissionDynamicContext[])new PermissionDynamicContext[0]);
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"getPermission(player, node)");
        return (Boolean)object;
    }

    /*
     * WARNING - void declaration
     */
    private final List<PermissionNode<Boolean>> createNodes() {
        void $this$mapTo$iv$iv;
        Iterable<Permission> $this$map$iv = CobblemonPermissions.INSTANCE.all();
        boolean $i$f$map = false;
        Iterable<Permission> iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void permission2;
            Permission permission3 = (Permission)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            PermissionNode node = new PermissionNode(permission2.getIdentifier(), PermissionTypes.BOOLEAN, (arg_0, arg_1, arg_2) -> ForgePermissionValidator.createNodes$lambda$2$lambda$1((Permission)permission2, arg_0, arg_1, arg_2), new PermissionDynamicContextKey[0]);
            ((Map)nodes).put(permission2.getIdentifier(), node);
            Cobblemon.INSTANCE.getLOGGER().debug("Registered Forge permission node " + node.getNodeName());
            collection.add(node);
        }
        return (List)destination$iv$iv;
    }

    private final PermissionNode<Boolean> findNode(Permission permission2) {
        return nodes.get(permission2.getIdentifier());
    }

    private final ServerPlayer extractPlayerFromSource(SharedSuggestionProvider source) {
        return source instanceof CommandSourceStack ? ((CommandSourceStack)source).m_230896_() : null;
    }

    private static final void _init_$lambda$0(PermissionGatherEvent.Nodes event) {
        Cobblemon.INSTANCE.getLOGGER().info("Starting Forge permission node registry");
        event.addNodes((Iterable)INSTANCE.createNodes());
        Cobblemon.INSTANCE.getLOGGER().debug("Finished Forge permission node registry");
    }

    private static final Boolean createNodes$lambda$2$lambda$1(Permission $permission, ServerPlayer player, UUID uUID, PermissionDynamicContext[] permissionDynamicContextArray) {
        Intrinsics.checkNotNullParameter((Object)$permission, (String)"$permission");
        ServerPlayer serverPlayer = player;
        return serverPlayer != null ? serverPlayer.m_20310_($permission.getLevel().getNumericalValue()) : false;
    }

    static {
        MinecraftForge.EVENT_BUS.addListener(ForgePermissionValidator::_init_$lambda$0);
    }
}

