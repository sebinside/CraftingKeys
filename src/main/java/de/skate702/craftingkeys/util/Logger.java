package de.skate702.craftingkeys.util;

import de.skate702.craftingkeys.CraftingKeys;

/**
 * Simple Logger Class for debug output. Thank you, compiler!
 */
public class Logger {


    /**
     * Static Final Level. Compiler optimizing will kill all other calls!
     */
    private static final LevelOfDetail MODE = LevelOfDetail.INFO;

    /**
     * Standard Output in front of every logger action.
     */
    private static final String StdOutput = "[" + CraftingKeys.NAME + "/";

    private static void print(String method, String message, LevelOfDetail in) {
        System.out.println(StdOutput + in.toString() + "] " + method + ": " + message);
    }

    /**
     * Prints a INFO-Message if allowed.
     *
     * @param method  The calling method / method info
     * @param message The message to print
     */
    public static void info(String method, String message) {
        if (MODE.getLevel() <= 0) {
            print(method, message, LevelOfDetail.INFO);
        }
    }

    /**
     * Prints a DEBUG-Message if allowed.
     *
     * @param method  The calling method / method info
     * @param message The message to print
     */
    public static void debug(String method, String message) {
        if (MODE.getLevel() <= 1) {
            print(method, message, LevelOfDetail.DEBUG);
        }
    }

    /**
     * Prints a WARN-Message if allowed.
     *
     * @param method  The calling method / method info
     * @param message The message to print
     */
    public static void warn(String method, String message) {
        if (MODE.getLevel() <= 2) {
            print(method, message, LevelOfDetail.WARN);
        }
    }

    /**
     * Prints a ERROR-Message if allowed.
     *
     * @param method  The calling method / method info
     * @param message The message to print
     */
    @SuppressWarnings("SameParameterValue")
    public static void error(String method, String message) {
        if (MODE.getLevel() <= 3) {
            print(method, message, LevelOfDetail.ERROR);
        }
    }

    /**
     * Define Level Of Detail with fixed numbers.
     */
    private enum LevelOfDetail {
        INFO(0), DEBUG(1), WARN(2), ERROR(3), NONE(4);

        private final int level;

        LevelOfDetail(int level) {
            this.level = level;
        }

        public int getLevel() {
            return level;
        }
    }
}
