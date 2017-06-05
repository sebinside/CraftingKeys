package de.skate702.craftingkeys.config;


import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

/**
 * Provides all Key Bindings and methods to register and check them.
 */
public class KeyBindings {

    private static final String category = "craftingkeys.binding.cat";

    public static final KeyBinding openGuiBinding = new KeyBinding("craftingkeys.binding.opengui",
            Keyboard.KEY_K, category);

    /**
     * Initializes all key bindings and registers them to the client-side.
     */
    public static void init() {
        ClientRegistry.registerKeyBinding(openGuiBinding);
    }

}
