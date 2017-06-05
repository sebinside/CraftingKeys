package de.skate702.craftingkeys.util;

import net.minecraft.client.resources.I18n;

public class LanguageLocalizer {
    /**
     * If another language than de_DE or en_US is selected the English one is used...
     *
     * @param unloc The unlocalized string which is in the definded in the language file
     * @return localized The localized String
     */
    public static String localize(String unloc) {
        return I18n.format(unloc);
    }
}
