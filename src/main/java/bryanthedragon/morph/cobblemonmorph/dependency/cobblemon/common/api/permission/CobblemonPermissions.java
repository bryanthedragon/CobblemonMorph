package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission

import java.util.ArrayList;

public object CobblemonPermissions {
   public final val BEDROCK_PARTICLE: Permission = INSTANCE.create("command.bedrockparticle", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS)
   public final val CHANGE_SCALE_AND_SIZE: Permission = INSTANCE.create("command.changescaleandsize", PermissionLevel.ALL_COMMANDS)
   public final val CHANGE_WALK_SPEED: Permission = INSTANCE.create("command.changewalkspeed", PermissionLevel.ALL_COMMANDS)
   public final val CHECKSPAWNS: Permission = INSTANCE.create("command.checkspawns", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS)
   public final val CLEAR_PARTY: Permission = INSTANCE.create("command.clearparty", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS)
   public final val CLEAR_PC: Permission = INSTANCE.create("command.clearpc", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS)
   private const val COMMAND_PREFIX: String = "command."
   public final val FRIENDSHIP: Permission = INSTANCE.create("command.friendship", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS)
   public final val GET_NBT: Permission = INSTANCE.create("command.getnbt", PermissionLevel.ALL_COMMANDS)
   public final val GIVE_ALL_POKEMON: Permission = INSTANCE.create("command.giveallpokemon", PermissionLevel.ALL_COMMANDS)
   private const val GIVE_POKEMON_BASE: String = "command.givepokemon"
   public final val GIVE_POKEMON_OTHER: Permission = INSTANCE.create("command.givepokemon.other", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS)
   public final val GIVE_POKEMON_SELF: Permission = INSTANCE.create("command.givepokemon.self", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS)
   private const val HEAL_POKEMON_BASE: String = "command.healpokemon"
   public final val HEAL_POKEMON_OTHER: Permission = INSTANCE.create("command.healpokemon.other", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS)
   public final val HEAL_POKEMON_SELF: Permission = INSTANCE.create("command.healpokemon.self", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS)
   public final val HELD_ITEM: Permission = INSTANCE.create("command.helditem", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS)
   private const val LEVEL_UP_BASE: String = "command.levelup"
   public final val LEVEL_UP_OTHER: Permission = INSTANCE.create("command.levelup.other", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS)
   public final val LEVEL_UP_SELF: Permission = INSTANCE.create("command.levelup.self", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS)
   public final val OPEN_DIALOGUE: Permission = INSTANCE.create("command.opendialogue", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS)
   public final val OPEN_STARTER_SCREEN: Permission = INSTANCE.create("command.openstarterscreen", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS)
   public final val PC: Permission = INSTANCE.create("command.pc", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS)
   public final val POKEBOX: Permission = INSTANCE.create("command.pokebox", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS)
   private const val POKEMON_EDIT_BASE: String = "command.pokemonedit"
   public final val POKEMON_EDIT_OTHER: Permission = INSTANCE.create("command.pokemonedit.other", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS)
   public final val POKEMON_EDIT_SELF: Permission = INSTANCE.create("command.pokemonedit.self", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS)
   public final val QUERY_LEARNSET: Permission = INSTANCE.create("command.querylearnset", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS)
   public final val SPAWN_ALL_POKEMON: Permission = INSTANCE.create("command.spawnallpokemon", PermissionLevel.ALL_COMMANDS)
   public final val SPAWN_POKEMON: Permission = INSTANCE.create("command.spawnpokemon", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS)
   public final val STOP_BATTLE: Permission = INSTANCE.create("command.stopbattle", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS)
   public final val TAKE_POKEMON: Permission = INSTANCE.create("command.takepokemon", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS)
   public final val TEACH: Permission = INSTANCE.create("command.teach.base", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS)
   public final val TEACH_BYPASS_LEARNSET: Permission = INSTANCE.create("command.teach.bypass", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS)
   public final val TEST_PARTY_SLOT: Permission = INSTANCE.create("command.testpartyslot", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS)
   public final val TEST_PC_SLOT: Permission = INSTANCE.create("command.testpcslot", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS)
   public final val TEST_STORE: Permission = INSTANCE.create("command.teststore", PermissionLevel.CHEAT_COMMANDS_AND_COMMAND_BLOCKS)
   private final val permissions: ArrayList<Permission> = new ArrayList()

   public fun all(): Iterable<Permission> {
      return permissions;
   }

   private fun create(node: String, level: PermissionLevel): Permission {
      val permission: CobblemonPermission = new CobblemonPermission(node, level);
      permissions.add(permission);
      return permission;
   }
}
