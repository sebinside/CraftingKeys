package de.skate702.craftingkeys.config;

import cpw.mods.fml.client.config.DummyConfigElement;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import de.skate702.craftingkeys.CraftingKeys;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom Config Gui for Mod Options inside of Minecraft.
 */
public class ConfigGui extends GuiConfig {
    public ConfigGui(GuiScreen parent) {
        super(parent, getConfigElements(),
                CraftingKeys.MODID, false, false, CraftingKeys.NAME);
    }

    /**
     * Generates Config Entires based on the main config file.
     *
     * @return A List of IConfigElement-Items, ready to add to the gui.
     */
    private static List<IConfigElement> getConfigElements() {
        List<IConfigElement> list = new ArrayList<IConfigElement>();

        list.add(categoryElement(Config.categoryKeys, Config.categoryKeys, "de.skate702.craftingkeys.config.keys"));
        list.add(categoryElement(Config.categoryOther, Config.categoryOther, "de.skate702.craftingkeys.config.other"));

        return list;
    }

    /**
     * Automatically generates category elements for config categories.
     *
     * @param category    the given category string (from the config file)
     * @param name        the name of the category
     * @param tooltip_key a lang-string, must have tooltip available under .tooltip
     * @return Returns a IConfigElement ready to add to the list of elements
     */
    private static IConfigElement categoryElement(String category, String name, String tooltip_key) {
        return new DummyConfigElement.DummyCategoryElement(name, tooltip_key,
                new ConfigElement<java.util.List<cpw.mods.fml.client.config.IConfigElement>>
                        (Config.configFile.getCategory(category)).getChildElements());
    }
}
