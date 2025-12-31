/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
public class LocalizationUtils
{
    private Cobblemon MODID;
    public void lang(String subKey, Any objects) {
        "cobblemon.$subKey".asTranslated(objects);
    }

    public void commandLang(String subKey, Any objects ){ 
        lang("command.$subKey", objects);
    }

    public void battleLang(String Key, Any objects) {
        lang("battle.$key", objects);
    }

    public void tooltipLang(String modId, String Key, Any objects) {
        "item.$modId.$key.tooltip".asTranslated(objects);
    }
}