package de.skate702.craftingkeys.config;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

/**
 * Provides all Key Bindings and methods to register and check them.
 */
@Deprecated
public class KeyBindings {

    // WARNING: This does NOT work! Have to use own Option Dialog OR Settings file. Bindings are exclusive. Shit!


    private static final String category = "key.categories.craftingkeys";

    private static KeyBinding[] bindings = { // Inspired by CSS Position Properties
            new KeyBinding("key.topleft", Keyboard.KEY_Q, category),
            new KeyBinding("key.topcenter", Keyboard.KEY_W, category),
            new KeyBinding("key.topright", Keyboard.KEY_E, category),

            new KeyBinding("key.centerleft", Keyboard.KEY_A, category),
            new KeyBinding("key.centercenter", Keyboard.KEY_S, category),
            new KeyBinding("key.centerright", Keyboard.KEY_D, category),

            new KeyBinding("key.lowerleft", Keyboard.KEY_Y, category), // GERMAN Keyboard layout
            new KeyBinding("key.lowercenter", Keyboard.KEY_X, category),
            new KeyBinding("key.lowerright", Keyboard.KEY_C, category),

            new KeyBinding("key.stack", Keyboard.KEY_LSHIFT, category),
            new KeyBinding("key.interact", Keyboard.KEY_LCONTROL, category),
    };

    /**
     * Initializes all key bindings and registers them to the client-side. Bindings are exclusive.
     * Do NOT use until there is a solution for this!
     */
    @Deprecated
    public static void init() {
        for (KeyBinding binding : bindings) {
            ClientRegistry.registerKeyBinding(binding);
        }
        ClientRegistry.registerKeyBinding(Minecraft.getMinecraft().gameSettings.keyBindRight);
        //Minecraft.getMinecraft().gameSettings.keybind
    }

}
