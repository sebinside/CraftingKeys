package de.skate702.craftingkeys.util;

import org.lwjgl.input.Keyboard;

/**
 * This helper class provides some static helping Methods and Constants.
 *
 * @author sebastian
 */
public class Helper {

    /**
     * If true, there are Debug-Output-Prints from debugPrint(msg).
     */
    public static final boolean DEBUG = true;


    /**
     * Saves the times strg was pressed before reseting
     */
    private static int strgTimesDown = 0;

    /**
     * Standart Output for Debug-Messages - Depends on DEBUG-Constant.
     *
     * @param message The Debug-Message. Syntax: "methodname(): Message"
     */
    @Deprecated
    public static void debugPrint(String message) {

        if (DEBUG) {
            System.out.println("CK-DEBUG: " + message);
        }

    }

    /**
     * Reads the current Keyboard-Input and converts it to a Inventory-Slot.
     *
     * @return A Inventory-Slot (based on CraftingGUI), -1 for wrong input, -2
     * for space
     */
    @Deprecated
    public static int craftingKeyDownToSlotNumber() {

        // TODO: Make this dynamic! // Use Settings
        // TODO: This has to be the job of the manager

        int returnValue = -1;

        if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
            returnValue = 1;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            returnValue = 2;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
            returnValue = 3;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            returnValue = 4;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            returnValue = 5;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            returnValue = 6;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_Y)) {
            returnValue = 7;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_X)) {
            returnValue = 8;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
            returnValue = 9;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            returnValue = -2;
        }

        return returnValue;
    }

    /**
     * Return the times Strg was pressed before reseting
     *
     * @param strgDown false, if reset
     * @return A number of tick strg was down before
     */
    @Deprecated
    public static int getStrgTimesDown(boolean strgDown) {

        if (!strgDown)
            strgTimesDown = 0;

        return ++strgTimesDown;

    }
}