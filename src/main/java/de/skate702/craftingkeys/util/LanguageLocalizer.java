package de.skate702.craftingkeys.util;

import net.minecraft.util.StatCollector;

public class LanguageLocalizer {
    /**
     * If another language than de_DE or en_US is selected the English one is used...
     *
     * @param unloc The unlocalized string which is in the definded in the language file
     * @return localized The localized String
     */
    public static String localize(String unloc) {
        return StatCollector.translateToLocal(unloc);
    }
}
