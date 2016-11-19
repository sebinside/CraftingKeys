package de.skate702.craftingkeys.util;

//NEW_1_9: net.minecraft.util.text.translation.I18n;
//NEW:1.9.4: import static net.minecraft.client.resources.I18n.format;
import static net.minecraft.client.resources.I18n.format;

public class LanguageLocalizer {
    /**
     * If another language than de_DE or en_US is selected the English one is used...
     *
     * @param unloc The unlocalized string which is in the definded in the language file
     * @return localized The localized String
     */
    public static String localize(String unloc) {
        //NEW_1_9 return I18n.translateToLocal(unloc);
        //NEW_1_9.4: return I18n.format(unloc);
        return format(unloc);
    }
}
