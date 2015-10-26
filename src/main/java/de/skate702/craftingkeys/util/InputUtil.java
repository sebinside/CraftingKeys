package de.skate702.craftingkeys.util;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import org.lwjgl.input.Mouse;

/**
 * This class provides methods to interact with the Mouse and key input.
 */
public class InputUtil {

    /**
     * Saves the last Key that was Pressed
     */
    private static int lastKeyDown = -1;

    /**
     * Private Constructor, Utility Class.
     */
    private InputUtil() {
    }

    /**
     * Returns the Slot at the current Mouse Position. [FROM GUICONTAINER]
     *
     * @param guiContainer The (Inventory) Container to work with.
     * @return Returns the slot (or null)
     */
    public static Slot getSlotAtMousePosition(GuiContainer guiContainer) {
        if (guiContainer != null) {
            Container container = guiContainer.inventorySlots;

            int x = getMouseX(guiContainer);
            int y = getMouseY(guiContainer);
            for (int k = 0; k < container.inventorySlots.size(); k++) {
                Slot slot = (Slot) container.inventorySlots.get(k);
                if (getIsMouseOverSlot(guiContainer, slot, x, y)) {
                    return slot;
                }
            }
            return null;
        } else {
            Logger.debug("getSlotAtMousePosition(gui)", "guiContainer == null");
            return null;
        }
    }

    /**
     * Returns, if the mouse cursor is over a specified slot [FROM GUICONTAINER]
     *
     * @param guiContainer the GuiContainer to work with
     * @param slot         The spcified slot
     * @param x            The Mouse x-Position
     * @param y            The Mouse y-Position
     * @return True, if the mouse is positioned over this slot. Otherwise false
     */
    private static boolean getIsMouseOverSlot(GuiContainer guiContainer, Slot slot, int x, int y) {
        if (guiContainer != null) {

            // Constants from Minecraft Source Code
            x -= (guiContainer.width - 176) / 2;
            y -= (guiContainer.height - 166) / 2;
            return x >= slot.xDisplayPosition - 1 && x < slot.xDisplayPosition + 16 + 1
                    && y >= slot.yDisplayPosition - 1 && y < slot.yDisplayPosition + 16 + 1;
        } else {
            return false;
        }
    }

    /**
     * Returns a calculated Mouse Position for getSlotAtMousePosition().
     *
     * @param guiContainer the GuiContainer to work with
     * @return The relative Mouse-Position
     */
    private static int getMouseX(GuiContainer guiContainer) {
        return (Mouse.getEventX() * guiContainer.width) / Util.client.displayWidth;
    }

    /**
     * Returns a calculated Mouse Position for getSlotAtMousePosition().
     *
     * @param guiContainer the GuiContainer to work with
     * @return The relative Mouse-Position
     */
    private static int getMouseY(GuiContainer guiContainer) {
        return guiContainer.height - (Mouse.getEventY() * guiContainer.height) / Util.client.displayHeight - 1;
    }

    /**
     * Returns, if the current KeyValue is the same
     *
     * @param currentKey the new input value, saved in the method
     * @return True, if these are the same keys
     */
    public static boolean isSameKey(int currentKey) {

        boolean returnValue = false;

        if (lastKeyDown == currentKey && currentKey != -1) {
            returnValue = true;
        }

        lastKeyDown = currentKey;
        return returnValue;

    }
}
