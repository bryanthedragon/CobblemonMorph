
package org.graalvm.nativeimage;

import org.graalvm.nativeimage.c.struct.CStruct;
import org.graalvm.word.PointerBase;

@CStruct(value="graal_isolate_t", isIncomplete=true)
public interface Isolate
extends PointerBase {
}

