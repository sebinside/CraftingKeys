package de.skate702.craftingkeys.util;


import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;

/**
 * Provides basic utility methods. For input methods, look at InputUtil.
 */
public class Util {

    /**
     * Current Instance Client, used for a lot of operations.
     */
    public static final Minecraft client = FMLClientHandler.instance().getClient();
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
     * Returns the current held item stack.
     *
     * @return A item stack
     */
    public static ItemStack getHeldStack() {
        return client.player.inventory.getItemStack();
    }

    /**
     * Returns if the current player is helding a item stack.
     *
     * @return True, if held stack != null
     */
    public static boolean isHoldingStack() {  
    	return (getHeldStack().getCount() > 0);
    }

    /**
     * Returns, if this is the first method call in a fresh opened world.
     *
     * @return True, if this is the first tick
     */
    public static boolean isFirstInWorldTick() {
        if (firstInWorldTick && client.world != null) {
            firstInWorldTick = false;
            return true;
        } else if (client.world == null) {
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
    	String translated = (new TextComponentTranslation(lang_key)).getUnformattedComponentText();
        client.player.sendChatMessage(translated);
    }

}
