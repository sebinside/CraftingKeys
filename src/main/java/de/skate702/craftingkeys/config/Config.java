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
public class Config {


    /**
     * Defines the config string for the keys-category.
     */
    private static final String categoryKeys = "keys";

    /**
     * Defines the config string for the other-category.
     */
    private static final String categoryOther = "other";

    /**
     * Standard Return Key if there is a problem reading the config.
     */
    private static final int retDefKey = -1;
    /**
     * Defines all 11 Keys you can use with Crafting Keys.
     */
    static Property keyTopLeft, keyTopCenter, keyTopRight,
            keyCenterLeft, keyCenterCenter, keyCenterRight,
            keyLowerLeft, keyLowerCenter, keyLowerRight,
            keyStack, keyInteract, keyDrop;
    /**
     * Provides the Suggested Config File.
     */
    private static Configuration configFile = null;
    /**
     * Defines, if NumPad is always active for crafting.
     */
    private static Property enableNumPad;

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

    private static boolean isNumPadEnabled() {
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

        keyTopLeft = configFile.get(categoryKeys, "keyTopLeft", Keyboard.KEY_Q);
        keyTopCenter = configFile.get(categoryKeys, "keyTopCenter", Keyboard.KEY_W);
        keyTopRight = configFile.get(categoryKeys, "keyTopRight", Keyboard.KEY_E);

        keyCenterLeft = configFile.get(categoryKeys, "keyCenterLeft", Keyboard.KEY_A);
        keyCenterCenter = configFile.get(categoryKeys, "keyCenterCenter", Keyboard.KEY_S);
        keyCenterRight = configFile.get(categoryKeys, "keyCenterRight", Keyboard.KEY_D);

        keyLowerLeft = configFile.get(categoryKeys, "keyLowerLeft", Keyboard.KEY_Y); // German Keyboard Layout
        keyLowerCenter = configFile.get(categoryKeys, "keyLowerCenter", Keyboard.KEY_X);
        keyLowerRight = configFile.get(categoryKeys, "keyLowerRight", Keyboard.KEY_C);

        // Special Keys

        keyStack = configFile.get(categoryKeys, "keyStack", Keyboard.KEY_LSHIFT);
        keyInteract = configFile.get(categoryKeys, "keyInteract", Keyboard.KEY_LCONTROL);
        keyDrop = configFile.get(categoryKeys, "keyDrop", Keyboard.KEY_SPACE);

        // Other Settings

        enableNumPad = configFile.get(categoryOther, "numPadEnabled", true, "Activates the NumPad for crafting");
    }

}
