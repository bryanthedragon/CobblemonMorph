package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.molang

import com.bedrockk.molang.runtime.struct.VariableStruct
import java.util.UUID

public interface MoLangDataStoreFactory {
   public abstract fun markDirty(uuid: UUID) {
   }

   public abstract fun load(uuid: UUID): VariableStruct {
   }
}
