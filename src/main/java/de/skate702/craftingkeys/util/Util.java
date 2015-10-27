package de.skate702.craftingkeys.util;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentTranslation;

/**
 * Provides basic utility methods. For input methods, look at InputUtil.
 */
public class Util {

    /**
     * Current Instance Client, used for a lot of operations.
     */
    public static Minecraft client = FMLClientHandler.instance().getClient();
    /**
     * Only used by isFirstInWorldTick()
     */
    private static boolean firstInWorldTick = true;

    /**
     * Private Constructor. This is a utility class!
     */
    private Util() {
    }

    /**
     * Returns, if this is the first method call in a fresh opened world.
     *
     * @return True, if this is the first tick
     */
    public static boolean isFirstInWorldTick() {
        if (firstInWorldTick && client.theWorld != null) {
            firstInWorldTick = false;
            return true;
        } else if (client.theWorld == null) {
            firstInWorldTick = true;
        }
        return false;
    }

    /**
     * Prints a Message in chat with the given language key from lang-files.
     *
     * @param lang_key key from lang-file
     */
    public static void printMessage(String lang_key) {
        client.thePlayer.addChatMessage(new ChatComponentTranslation(lang_key));
    }

    /**
     * Prints a warning, that this mod is still in alpha-state.
     */
    public static void printWarning() {
        printMessage("de.skate702.craftingkeys.warn.line1");
        printMessage("de.skate702.craftingkeys.warn.line2");
        printMessage("de.skate702.craftingkeys.warn.line3");
    }

}
