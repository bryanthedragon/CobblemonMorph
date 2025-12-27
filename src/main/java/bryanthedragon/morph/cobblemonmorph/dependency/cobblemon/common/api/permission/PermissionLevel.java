package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission

public enum PermissionLevel(numericalValue: Int) {
   NONE(0),
   SPAWN_PROTECTION_BYPASS(1),
   CHEAT_COMMANDS_AND_COMMAND_BLOCKS(2),
   MULTIPLAYER_MANAGEMENT(3),
   ALL_COMMANDS(4)
   public final val numericalValue: Int

   init {
      this.numericalValue = numericalValue;
   }
}
