package de.skate702.craftingkeys.util;

import net.minecraft.util.StatCollector;

public class LanguageLocalizer {
    //TODO: Finish localizer
    public static String localizeIfPossible(String unloc){
        String localized = "";
        if(StatCollector.canTranslate(unloc)){
            localized = StatCollector.translateToLocal(unloc);
        }
        return localized;
    }
}
