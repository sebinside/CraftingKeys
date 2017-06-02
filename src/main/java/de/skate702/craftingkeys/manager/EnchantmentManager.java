package de.skate702.craftingkeys.manager;

import de.skate702.craftingkeys.util.Logger;
import net.minecraft.inventory.Container;

/**
 * Manages a Enchantment Table GUI Inventory.
 */
public class EnchantmentManager extends ContainerManager {

    private static EnchantmentManager instance = null;

    /**
     * Creates a new Enchantment Manager with the given container.
     *
     * @param container The container from a crafting GUI
     */
    private EnchantmentManager(Container container) {
        super(container);
    }

    /**
     * Returns a Enchantment Manager Instance operating on the given container
     *
     * @param container A container from a GUI
     * @return manager-singleton
     */
    public static EnchantmentManager getInstance(Container container) {
        if (instance == null) {
            instance = new EnchantmentManager(container);
        } else {
            instance.container = container;
        }
        return instance;
    }

    @Override
    protected int specificKeyToSlotIndex() {
        //TODO: check for v1.11
    	return mapKeyToSlot(-1, 0, 1, -1, 0, 1, -1, -1, -1);

    }

    @Override
    protected int getInventoryStartIndex() {
        // TODO: check for v1.11
    	return 2;
    }

    @Override
    protected int getInteractionSlotIndex() {
        return 0;
    }

    @Override
    protected int[] getDropSlots() {
        //TODO: check for v1.11
        return new int[] {0, 1};
    }

    @Override
    protected void interact() {
        clickOnItem();
    }

    /**
     * Sends a click on the enchanted item
     */
    private void clickOnItem() {

        Logger.info("clickOnCraftingOutput()", "Clicked on Crafing Output.");
        leftClick(0);

    }
}
