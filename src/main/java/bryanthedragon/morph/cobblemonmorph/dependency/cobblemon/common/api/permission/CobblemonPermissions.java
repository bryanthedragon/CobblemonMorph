/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.CobblemonPermission;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.Permission;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.PermissionLevel;
import java.util.ArrayList;
import java.util.Collection;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\bK\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\bX\u0010YJ\u0013\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u001f\u0010\n\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\bH\u0002\u00a2\u0006\u0004\b\n\u0010\u000bR\u0017\u0010\f\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0010\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\r\u001a\u0004\b\u0011\u0010\u000fR\u0017\u0010\u0012\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\r\u001a\u0004\b\u0013\u0010\u000fR\u0017\u0010\u0014\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\r\u001a\u0004\b\u0015\u0010\u000fR\u0017\u0010\u0016\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010\r\u001a\u0004\b\u0017\u0010\u000fR\u0017\u0010\u0018\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b\u0018\u0010\r\u001a\u0004\b\u0019\u0010\u000fR\u0014\u0010\u001a\u001a\u00020\u00068\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u001a\u0010\u001bR\u0017\u0010\u001c\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b\u001c\u0010\r\u001a\u0004\b\u001d\u0010\u000fR\u0017\u0010\u001e\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b\u001e\u0010\r\u001a\u0004\b\u001f\u0010\u000fR\u0017\u0010 \u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b \u0010\r\u001a\u0004\b!\u0010\u000fR\u0014\u0010\"\u001a\u00020\u00068\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\"\u0010\u001bR\u0017\u0010#\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b#\u0010\r\u001a\u0004\b$\u0010\u000fR\u0017\u0010%\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b%\u0010\r\u001a\u0004\b&\u0010\u000fR\u0014\u0010'\u001a\u00020\u00068\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b'\u0010\u001bR\u0017\u0010(\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b(\u0010\r\u001a\u0004\b)\u0010\u000fR\u0017\u0010*\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b*\u0010\r\u001a\u0004\b+\u0010\u000fR\u0017\u0010,\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b,\u0010\r\u001a\u0004\b-\u0010\u000fR\u0014\u0010.\u001a\u00020\u00068\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b.\u0010\u001bR\u0017\u0010/\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b/\u0010\r\u001a\u0004\b0\u0010\u000fR\u0017\u00101\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b1\u0010\r\u001a\u0004\b2\u0010\u000fR\u0017\u00103\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b3\u0010\r\u001a\u0004\b4\u0010\u000fR\u0017\u00105\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b5\u0010\r\u001a\u0004\b6\u0010\u000fR\u0017\u00107\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b7\u0010\r\u001a\u0004\b8\u0010\u000fR\u0017\u00109\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b9\u0010\r\u001a\u0004\b:\u0010\u000fR\u0014\u0010;\u001a\u00020\u00068\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b;\u0010\u001bR\u0017\u0010<\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b<\u0010\r\u001a\u0004\b=\u0010\u000fR\u0017\u0010>\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b>\u0010\r\u001a\u0004\b?\u0010\u000fR\u0017\u0010@\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b@\u0010\r\u001a\u0004\bA\u0010\u000fR\u0017\u0010B\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\bB\u0010\r\u001a\u0004\bC\u0010\u000fR\u0017\u0010D\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\bD\u0010\r\u001a\u0004\bE\u0010\u000fR\u0017\u0010F\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\bF\u0010\r\u001a\u0004\bG\u0010\u000fR\u0017\u0010H\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\bH\u0010\r\u001a\u0004\bI\u0010\u000fR\u0017\u0010J\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\bJ\u0010\r\u001a\u0004\bK\u0010\u000fR\u0017\u0010L\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\bL\u0010\r\u001a\u0004\bM\u0010\u000fR\u0017\u0010N\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\bN\u0010\r\u001a\u0004\bO\u0010\u000fR\u0017\u0010P\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\bP\u0010\r\u001a\u0004\bQ\u0010\u000fR\u0017\u0010R\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\bR\u0010\r\u001a\u0004\bS\u0010\u000fR$\u0010V\u001a\u0012\u0012\u0004\u0012\u00020\u00030Tj\b\u0012\u0004\u0012\u00020\u0003`U8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\bV\u0010W\u00a8\u0006Z"}, d2={"Lcom/cobblemon/mod/common/api/permission/CobblemonPermissions;", "", "", "Lcom/cobblemon/mod/common/api/permission/Permission;", "all", "()Ljava/lang/Iterable;", "", "node", "Lcom/cobblemon/mod/common/api/permission/PermissionLevel;", "level", "create", "(Ljava/lang/String;Lcom/cobblemon/mod/common/api/permission/PermissionLevel;)Lcom/cobblemon/mod/common/api/permission/Permission;", "BEDROCK_PARTICLE", "Lcom/cobblemon/mod/common/api/permission/Permission;", "getBEDROCK_PARTICLE", "()Lcom/cobblemon/mod/common/api/permission/Permission;", "CHANGE_SCALE_AND_SIZE", "getCHANGE_SCALE_AND_SIZE", "CHANGE_WALK_SPEED", "getCHANGE_WALK_SPEED", "CHECKSPAWNS", "getCHECKSPAWNS", "CLEAR_PARTY", "getCLEAR_PARTY", "CLEAR_PC", "getCLEAR_PC", "COMMAND_PREFIX", "Ljava/lang/String;", "FRIENDSHIP", "getFRIENDSHIP", "GET_NBT", "getGET_NBT", "GIVE_ALL_POKEMON", "getGIVE_ALL_POKEMON", "GIVE_POKEMON_BASE", "GIVE_POKEMON_OTHER", "getGIVE_POKEMON_OTHER", "GIVE_POKEMON_SELF", "getGIVE_POKEMON_SELF", "HEAL_POKEMON_BASE", "HEAL_POKEMON_OTHER", "getHEAL_POKEMON_OTHER", "HEAL_POKEMON_SELF", "getHEAL_POKEMON_SELF", "HELD_ITEM", "getHELD_ITEM", "LEVEL_UP_BASE", "LEVEL_UP_OTHER", "getLEVEL_UP_OTHER", "LEVEL_UP_SELF", "getLEVEL_UP_SELF", "OPEN_DIALOGUE", "getOPEN_DIALOGUE", "OPEN_STARTER_SCREEN", "getOPEN_STARTER_SCREEN", "PC", "getPC", "POKEBOX", "getPOKEBOX", "POKEMON_EDIT_BASE", "POKEMON_EDIT_OTHER", "getPOKEMON_EDIT_OTHER", "POKEMON_EDIT_SELF", "getPOKEMON_EDIT_SELF", "QUERY_LEARNSET", "getQUERY_LEARNSET", "SPAWN_ALL_POKEMON", "getSPAWN_ALL_POKEMON", "SPAWN_POKEMON", "getSPAWN_POKEMON", "STOP_BATTLE", "getSTOP_BATTLE", "TAKE_POKEMON", "getTAKE_POKEMON", "TEACH", "getTEACH", "TEACH_BYPASS_LEARNSET", "getTEACH_BYPASS_LEARNSET", "TEST_PARTY_SLOT", "getTEST_PARTY_SLOT", "TEST_PC_SLOT", "getTEST_PC_SLOT", "TEST_STORE", "getTEST_STORE", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "permissions", "Ljava/util/ArrayList;", "<init>", "()V", "common"})
public final class CobblemonPermissions {
    @NotNull
    public static final CobblemonPermissions INSTANCE = new CobblemonPermissions();
    @NotNull
    private static final String COMMAND_PREFIX = "command.";
    @NotNull
    private static final ArrayList<Permission> permissions = new ArrayList();
    @NotNull
    private static final Permission CHANGE_SCALE_AND_SIZE = INSTANCE.create("command.changescaleandsize", PermissionLevel.ALL_COMMANDS);
    @NotNull
    private static final Permission CHANGE_WALK_SPEED = INSTANCE.create("command.changewalkspeed", PermissionLevel.ALL_COMMANDS);
    @NotNull
    private static final Permission CHECKSPAWNS = INSTANCE.create("command.checkspawns", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS);
    @NotNull
    private static final Permission GET_NBT = INSTANCE.create("command.getnbt", PermissionLevel.ALL_COMMANDS);
    @NotNull
    private static final String GIVE_POKEMON_BASE = "command.givepokemon";
    @NotNull
    private static final Permission GIVE_POKEMON_SELF = INSTANCE.create("command.givepokemon.self", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS);
    @NotNull
    private static final Permission GIVE_POKEMON_OTHER = INSTANCE.create("command.givepokemon.other", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS);
    @NotNull
    private static final String HEAL_POKEMON_BASE = "command.healpokemon";
    @NotNull
    private static final Permission HEAL_POKEMON_SELF = INSTANCE.create("command.healpokemon.self", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS);
    @NotNull
    private static final Permission HEAL_POKEMON_OTHER = INSTANCE.create("command.healpokemon.other", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS);
    @NotNull
    private static final String LEVEL_UP_BASE = "command.levelup";
    @NotNull
    private static final Permission LEVEL_UP_SELF = INSTANCE.create("command.levelup.self", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS);
    @NotNull
    private static final Permission LEVEL_UP_OTHER = INSTANCE.create("command.levelup.other", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS);
    @NotNull
    private static final Permission OPEN_STARTER_SCREEN = INSTANCE.create("command.openstarterscreen", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS);
    @NotNull
    private static final Permission BEDROCK_PARTICLE = INSTANCE.create("command.bedrockparticle", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS);
    @NotNull
    private static final Permission OPEN_DIALOGUE = INSTANCE.create("command.opendialogue", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS);
    @NotNull
    private static final String POKEMON_EDIT_BASE = "command.pokemonedit";
    @NotNull
    private static final Permission POKEMON_EDIT_SELF = INSTANCE.create("command.pokemonedit.self", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS);
    @NotNull
    private static final Permission POKEMON_EDIT_OTHER = INSTANCE.create("command.pokemonedit.other", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS);
    @NotNull
    private static final Permission SPAWN_ALL_POKEMON = INSTANCE.create("command.spawnallpokemon", PermissionLevel.ALL_COMMANDS);
    @NotNull
    private static final Permission GIVE_ALL_POKEMON = INSTANCE.create("command.giveallpokemon", PermissionLevel.ALL_COMMANDS);
    @NotNull
    private static final Permission SPAWN_POKEMON = INSTANCE.create("command.spawnpokemon", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS);
    @NotNull
    private static final Permission STOP_BATTLE = INSTANCE.create("command.stopbattle", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS);
    @NotNull
    private static final Permission TAKE_POKEMON = INSTANCE.create("command.takepokemon", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS);
    @NotNull
    private static final Permission TEACH = INSTANCE.create("command.teach.base", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS);
    @NotNull
    private static final Permission TEACH_BYPASS_LEARNSET = INSTANCE.create("command.teach.bypass", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS);
    @NotNull
    private static final Permission FRIENDSHIP = INSTANCE.create("command.friendship", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS);
    @NotNull
    private static final Permission HELD_ITEM = INSTANCE.create("command.helditem", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS);
    @NotNull
    private static final Permission PC = INSTANCE.create("command.pc", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS);
    @NotNull
    private static final Permission POKEBOX = INSTANCE.create("command.pokebox", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS);
    @NotNull
    private static final Permission TEST_STORE = INSTANCE.create("command.teststore", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS);
    @NotNull
    private static final Permission QUERY_LEARNSET = INSTANCE.create("command.querylearnset", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS);
    @NotNull
    private static final Permission TEST_PC_SLOT = INSTANCE.create("command.testpcslot", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS);
    @NotNull
    private static final Permission TEST_PARTY_SLOT = INSTANCE.create("command.testpartyslot", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS);
    @NotNull
    private static final Permission CLEAR_PARTY = INSTANCE.create("command.clearparty", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS);
    @NotNull
    private static final Permission CLEAR_PC = INSTANCE.create("command.clearpc", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS);

    private CobblemonPermissions() {
    }

    @NotNull
    public final Permission getCHANGE_SCALE_AND_SIZE() {
        return CHANGE_SCALE_AND_SIZE;
    }

    @NotNull
    public final Permission getCHANGE_WALK_SPEED() {
        return CHANGE_WALK_SPEED;
    }

    @NotNull
    public final Permission getCHECKSPAWNS() {
        return CHECKSPAWNS;
    }

    @NotNull
    public final Permission getGET_NBT() {
        return GET_NBT;
    }

    @NotNull
    public final Permission getGIVE_POKEMON_SELF() {
        return GIVE_POKEMON_SELF;
    }

    @NotNull
    public final Permission getGIVE_POKEMON_OTHER() {
        return GIVE_POKEMON_OTHER;
    }

    @NotNull
    public final Permission getHEAL_POKEMON_SELF() {
        return HEAL_POKEMON_SELF;
    }

    @NotNull
    public final Permission getHEAL_POKEMON_OTHER() {
        return HEAL_POKEMON_OTHER;
    }

    @NotNull
    public final Permission getLEVEL_UP_SELF() {
        return LEVEL_UP_SELF;
    }

    @NotNull
    public final Permission getLEVEL_UP_OTHER() {
        return LEVEL_UP_OTHER;
    }

    @NotNull
    public final Permission getOPEN_STARTER_SCREEN() {
        return OPEN_STARTER_SCREEN;
    }

    @NotNull
    public final Permission getBEDROCK_PARTICLE() {
        return BEDROCK_PARTICLE;
    }

    @NotNull
    public final Permission getOPEN_DIALOGUE() {
        return OPEN_DIALOGUE;
    }

    @NotNull
    public final Permission getPOKEMON_EDIT_SELF() {
        return POKEMON_EDIT_SELF;
    }

    @NotNull
    public final Permission getPOKEMON_EDIT_OTHER() {
        return POKEMON_EDIT_OTHER;
    }

    @NotNull
    public final Permission getSPAWN_ALL_POKEMON() {
        return SPAWN_ALL_POKEMON;
    }

    @NotNull
    public final Permission getGIVE_ALL_POKEMON() {
        return GIVE_ALL_POKEMON;
    }

    @NotNull
    public final Permission getSPAWN_POKEMON() {
        return SPAWN_POKEMON;
    }

    @NotNull
    public final Permission getSTOP_BATTLE() {
        return STOP_BATTLE;
    }

    @NotNull
    public final Permission getTAKE_POKEMON() {
        return TAKE_POKEMON;
    }

    @NotNull
    public final Permission getTEACH() {
        return TEACH;
    }

    @NotNull
    public final Permission getTEACH_BYPASS_LEARNSET() {
        return TEACH_BYPASS_LEARNSET;
    }

    @NotNull
    public final Permission getFRIENDSHIP() {
        return FRIENDSHIP;
    }

    @NotNull
    public final Permission getHELD_ITEM() {
        return HELD_ITEM;
    }

    @NotNull
    public final Permission getPC() {
        return PC;
    }

    @NotNull
    public final Permission getPOKEBOX() {
        return POKEBOX;
    }

    @NotNull
    public final Permission getTEST_STORE() {
        return TEST_STORE;
    }

    @NotNull
    public final Permission getQUERY_LEARNSET() {
        return QUERY_LEARNSET;
    }

    @NotNull
    public final Permission getTEST_PC_SLOT() {
        return TEST_PC_SLOT;
    }

    @NotNull
    public final Permission getTEST_PARTY_SLOT() {
        return TEST_PARTY_SLOT;
    }

    @NotNull
    public final Permission getCLEAR_PARTY() {
        return CLEAR_PARTY;
    }

    @NotNull
    public final Permission getCLEAR_PC() {
        return CLEAR_PC;
    }

    @NotNull
    public final Iterable<Permission> all() {
        return permissions;
    }

    private final Permission create(String node, PermissionLevel level) {
        CobblemonPermission permission2 = new CobblemonPermission(node, level);
        ((Collection)permissions).add(permission2);
        return permission2;
    }
}

