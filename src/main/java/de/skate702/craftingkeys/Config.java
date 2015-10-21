package de.skate702.craftingkeys;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import org.lwjgl.input.Keyboard;

/**
 * Configuration Management for Keys, etc.
 */
public class Config {


    private static final String categoryKeys = "Keys";
    private static final String categoryOther = "Other";
    /**
     * Defines all 11 Keys you can use with Crafting Keys.
     */
    public static Property keyTopLeft, keyTopCenter, keyTopRight,
            keyCenterLeft, keyCenterCenter, keyCenterRight,
            keyLowerLeft, keyLowerCenter, keyLowerRight,
            keyStack, keyInteract;
    /**
     * Defines, if NumPad is always active for crafting
     */
    public static Property enableNumPad;

    /**
     * Initializes the config Files, loads all values (or sets them to default).
     *
     * @param event PreInitEvent from Main Class
     */
    public static void loadConfig(FMLPreInitializationEvent event) {

        Configuration config = new Configuration(event.getSuggestedConfigurationFile());

        config.load();

        genComments(config);

        getProperties(config);

        config.save();

    }

    /**
     * Generates comments for easier understanding of the categories.
     *
     * @param config Mod related Configuration File
     */
    private static void genComments(Configuration config) {

        config.addCustomCategoryComment(categoryKeys, "Keyboard codes based on http://minecraft.gamepedia.com/Key_codes");
        config.addCustomCategoryComment(categoryOther, "Other settings which have effects @ crafting keys");

    }

    /**
     * Loads all properties from the config file.
     *
     * @param config Mod related Configuration File
     */
    private static void getProperties(Configuration config) {

        keyTopLeft = config.get(categoryKeys, "1_keyTopLeft", Keyboard.KEY_Q);
        keyTopCenter = config.get(categoryKeys, "2_keyTopCenter", Keyboard.KEY_W);
        keyTopRight = config.get(categoryKeys, "3_keyTopRight", Keyboard.KEY_E);

        keyCenterLeft = config.get(categoryKeys, "4_keyCenterLeft", Keyboard.KEY_A);
        keyCenterCenter = config.get(categoryKeys, "5_keyCenterCenter", Keyboard.KEY_S);
        keyCenterRight = config.get(categoryKeys, "6_keyCenterRight", Keyboard.KEY_D);

        keyLowerLeft = config.get(categoryKeys, "7_keyLowerLeft", Keyboard.KEY_Y); // German Keyboard Layout
        keyLowerCenter = config.get(categoryKeys, "8_keyLowerCenter", Keyboard.KEY_X);
        keyLowerRight = config.get(categoryKeys, "9_keyLowerRight", Keyboard.KEY_C);

        keyStack = config.get(categoryKeys, "keyStack", Keyboard.KEY_LSHIFT, "Key to move stacks instead of single items");
        keyInteract = config.get(categoryKeys, "keyInteract", Keyboard.KEY_LCONTROL, "Key to interact with e.g. crafting output");

        enableNumPad = config.get(categoryOther, "enableNumPad", true, "Activates the NumPad for crafting");

    }

}
