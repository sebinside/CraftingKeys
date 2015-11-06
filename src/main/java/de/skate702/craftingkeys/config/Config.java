package de.skate702.craftingkeys.config;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import de.skate702.craftingkeys.CraftingKeys;
import de.skate702.craftingkeys.util.Logger;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import org.lwjgl.input.Keyboard;

/**
 * Configuration Management for Keys, etc.
 */
@Deprecated
public class Config {


    /**
     * Defines the config string for the keys-category.
     */
    protected static final String categoryKeys = "keys";

    /**
     * Defines the config string for the other-category.
     */
    protected static final String categoryOther = "other";

    /**
     * Standard Return Key if there is a problem reading the config.
     */
    private static final int retDefKey = -1;

    /**
     * Provides the Suggested Config File.
     */
    protected static Configuration configFile = null;

    /**
     * Defines all 11 Keys you can use with Crafting Keys.
     */
    protected static Property keyTopLeft, keyTopCenter, keyTopRight,
            keyCenterLeft, keyCenterCenter, keyCenterRight,
            keyLowerLeft, keyLowerCenter, keyLowerRight,
            keyStack, keyInteract, keyDrop;

    /**
     * Defines, if NumPad is always active for crafting.
     */
    protected static Property enableNumPad;

    public static boolean isKeyTopLeftPressed() {
        return Keyboard.isKeyDown(keyTopLeft.getInt(retDefKey)) || isNumPadEnabled() && Keyboard.isKeyDown(71);
    }

    public static boolean isKeyTopCenterPressed() {
        return Keyboard.isKeyDown(keyTopCenter.getInt(retDefKey)) || isNumPadEnabled() && Keyboard.isKeyDown(72);
    }

    public static boolean isKeyTopRightPressed() {
        return Keyboard.isKeyDown(keyTopRight.getInt(retDefKey)) || isNumPadEnabled() && Keyboard.isKeyDown(73);
    }

    public static boolean isKeyCenterLeftPressed() {
        return Keyboard.isKeyDown(keyCenterLeft.getInt(retDefKey)) || isNumPadEnabled() && Keyboard.isKeyDown(75);
    }

    public static boolean isKeyCenterCenterPressed() {
        return Keyboard.isKeyDown(keyCenterCenter.getInt(retDefKey)) || isNumPadEnabled() && Keyboard.isKeyDown(76);
    }

    public static boolean isKeyCenterRightPressed() {
        return Keyboard.isKeyDown(keyCenterRight.getInt(retDefKey)) || isNumPadEnabled() && Keyboard.isKeyDown(77);
    }

    public static boolean isKeyLowerLeftPressed() {
        return Keyboard.isKeyDown(keyLowerLeft.getInt(retDefKey)) || isNumPadEnabled() && Keyboard.isKeyDown(79);
    }

    public static boolean isKeyLowerCenterPressed() {
        return Keyboard.isKeyDown(keyLowerCenter.getInt(retDefKey)) || isNumPadEnabled() && Keyboard.isKeyDown(80);
    }

    public static boolean isKeyLowerRightPressed() {
        return Keyboard.isKeyDown(keyLowerRight.getInt(retDefKey)) || isNumPadEnabled() && Keyboard.isKeyDown(81);
    }

    public static boolean isKeyStackPressed() {
        return Keyboard.isKeyDown(keyStack.getInt(retDefKey));
    }

    public static boolean isKeyInteractPressed() {
        return Keyboard.isKeyDown(keyInteract.getInt(retDefKey));
    }

    public static boolean isKeyDropPressed() {
        return Keyboard.isKeyDown(keyDrop.getInt(retDefKey));
    }

    public static boolean isNumPadEnabled() {
        return enableNumPad.getBoolean(true);
    }

    /**
     * Initializes the configFile Files, loads all values (or sets them to default).
     *
     * @param event PreInitEvent from Main Class
     */
    public static void loadConfig(FMLPreInitializationEvent event) {

        configFile = new Configuration(event.getSuggestedConfigurationFile(), CraftingKeys.VERSION);

        configFile.load();

        genComments();

        syncConfig();

    }

    /**
     * Syncs and saves the configFile file.
     */
    public static void syncConfig() {

        if (configFile == null) {
            Logger.error("syncConfig()", "Unable to read config file!");
            return;
        }

        syncProperties();

        if (configFile.hasChanged())
            configFile.save();

    }

    /**
     * Generates comments for easier understanding of the categories.
     */
    private static void genComments() {
        configFile.addCustomCategoryComment(categoryKeys, "Keyboard codes based on http://minecraft.gamepedia.com/Key_codes");
        configFile.addCustomCategoryComment(categoryOther, "Other settings which have effects @ crafting keys");
    }

    /**
     * Loads all properties from the configFile file.
     */
    private static void syncProperties() {
        // Standard Keys

        keyTopLeft = configFile.get(categoryKeys, "1.0-keyTopLeft", Keyboard.KEY_Q, "Top Left crafting key (key value)");
        keyTopLeft.setLanguageKey("key.topleft");
        keyTopCenter = configFile.get(categoryKeys, "1.1-2keyTopCenter", Keyboard.KEY_W, "Top Center crafting key (key value)");
        keyTopCenter.setLanguageKey("key.topcenter");
        keyTopRight = configFile.get(categoryKeys, "1.2-keyTopRight", Keyboard.KEY_E, "Top Right crafting key (key value)");
        keyTopRight.setLanguageKey("key.topright");

        keyCenterLeft = configFile.get(categoryKeys, "2.0-keyCenterLeft", Keyboard.KEY_A, "Center Left crafting key (key value)");
        keyCenterLeft.setLanguageKey("key.centerleft");
        keyCenterCenter = configFile.get(categoryKeys, "2.1-keyCenterCenter", Keyboard.KEY_S, "Center Center crafting key (key value)");
        keyCenterCenter.setLanguageKey("key.centercenter");
        keyCenterRight = configFile.get(categoryKeys, "2.2-keyCenterRight", Keyboard.KEY_D, "Center Right crafting key (key value)");
        keyCenterRight.setLanguageKey("key.centerright");

        keyLowerLeft = configFile.get(categoryKeys, "3.0-keyLowerLeft", Keyboard.KEY_Y, "Lower Left crafting key (key value)"); // German Keyboard Layout
        keyLowerLeft.setLanguageKey("key.lowerleft");
        keyLowerCenter = configFile.get(categoryKeys, "3.1-keyLowerCenter", Keyboard.KEY_X, "Lower Center crafting key (key value)");
        keyLowerCenter.setLanguageKey("key.lowercenter");
        keyLowerRight = configFile.get(categoryKeys, "3.2-keyLowerRight", Keyboard.KEY_C, "Lower Right crafting key (key value)");
        keyLowerRight.setLanguageKey("key.lowerright");

        // Special Keys

        keyStack = configFile.get(categoryKeys, "4.0-keyStack", Keyboard.KEY_LSHIFT, "Key to move stacks instead of single items (key value)");
        keyStack.setLanguageKey("key.stack");
        keyInteract = configFile.get(categoryKeys, "4.1-keyInteract", Keyboard.KEY_LCONTROL, "Key to interact with e.g. crafting output (key value)");
        keyInteract.setLanguageKey("key.interact");
        keyDrop = configFile.get(categoryKeys, "4.2-keyDrop", Keyboard.KEY_SPACE, "Key to drop all items from crafting (key value)");
        keyDrop.setLanguageKey("key.drop");

        // Other Settings

        enableNumPad = configFile.get(categoryOther, "numPadEnabled", true, "Activates the NumPad for crafting");
        enableNumPad.setLanguageKey("options.numpadEnabled");
    }

}
