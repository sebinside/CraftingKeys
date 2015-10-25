package de.skate702.craftingkeys.config;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
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
    private static Property keyTopLeft, keyTopCenter, keyTopRight,
            keyCenterLeft, keyCenterCenter, keyCenterRight,
            keyLowerLeft, keyLowerCenter, keyLowerRight,
            keyStack, keyInteract, keyDrop;

    /**
     * Defines, if NumPad is always active for crafting.
     */
    private static Property enableNumPad;

    public static int getKeyTopLeft() {
        return keyTopLeft.getInt(retDefKey);
    }

    public static int getkeyTopCenter() {
        return keyTopCenter.getInt(retDefKey);
    }

    public static int getKeyTopRight() {
        return keyTopRight.getInt(retDefKey);
    }

    public static int getKeyCenterLeft() {
        return keyCenterLeft.getInt(retDefKey);
    }

    public static int getKeyCenterCenter() {
        return keyCenterCenter.getInt(retDefKey);
    }

    public static int getKeyCenterRight() {
        return keyCenterRight.getInt(retDefKey);
    }

    public static int getKeyLowerLeft() {
        return keyLowerLeft.getInt(retDefKey);
    }

    public static int getKeyLowerCenter() {
        return keyLowerCenter.getInt(retDefKey);
    }

    public static int getKeyLowerRight() {
        return keyLowerRight.getInt(retDefKey);
    }

    public static int getKeyStack() {
        return keyStack.getInt(retDefKey);
    }

    public static int getKeyInteract() {
        return keyInteract.getInt(retDefKey);
    }

    public static int getKeyDrop() {
        return keyDrop.getInt(retDefKey);
    }

    public static boolean getEnableNumPad() {
        return enableNumPad.getBoolean(true);
    }

    /**
     * Initializes the configFile Files, loads all values (or sets them to default).
     *
     * @param event PreInitEvent from Main Class
     */
    public static void loadConfig(FMLPreInitializationEvent event) {

        configFile = new Configuration(event.getSuggestedConfigurationFile());

        configFile.load();

        genComments();

        syncConfig();

    }

    /**
     * Syncs and saves the configFile file.
     */
    public static void syncConfig() {

        if (configFile == null) {
            // TODO: Throw Error!
            return;
        }

        syncProperties(); // TODO: Why here?

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

        keyTopLeft = configFile.get(categoryKeys, "1. Top Left", Keyboard.KEY_Q, "Top Left crafting key (key value)");
        keyTopCenter = configFile.get(categoryKeys, "2. Top Center", Keyboard.KEY_W, "Top Center crafting key (key value)");
        keyTopRight = configFile.get(categoryKeys, "3. Top Right", Keyboard.KEY_E, "Top Right crafting key (key value)");

        keyCenterLeft = configFile.get(categoryKeys, "4. Center Left", Keyboard.KEY_A, "Center Left crafting key (key value)");
        keyCenterCenter = configFile.get(categoryKeys, "5. Center Center", Keyboard.KEY_S, "Center Center crafting key (key value)");
        keyCenterRight = configFile.get(categoryKeys, "6. Center Right", Keyboard.KEY_D, "Center Right crafting key (key value)");

        keyLowerLeft = configFile.get(categoryKeys, "7. Lower Left", Keyboard.KEY_Y, "Lower Left crafting key (key value)"); // German Keyboard Layout
        keyLowerCenter = configFile.get(categoryKeys, "8. Lower Center", Keyboard.KEY_X, "Lower Center crafting key (key value)");
        keyLowerRight = configFile.get(categoryKeys, "9. Lower Right", Keyboard.KEY_C, "Lower Right crafting key (key value)");

        // Special Keys

        keyStack = configFile.get(categoryKeys, "Stack Key", Keyboard.KEY_LSHIFT, "Key to move stacks instead of single items (key value)");
        keyInteract = configFile.get(categoryKeys, "Interaction Key", Keyboard.KEY_LCONTROL, "Key to interact with e.g. crafting output (key value)");
        keyDrop = configFile.get(categoryKeys, "Drop Key", Keyboard.KEY_SPACE, "Key to drop all items from crafting (key value)");

        // Other Settings

        enableNumPad = configFile.get(categoryOther, "Enable Num Pad", true, "Activates the NumPad for crafting");

    }

}
