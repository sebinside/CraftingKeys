package de.skate702.craftingkeys.manager;

import de.skate702.craftingkeys.util.Logger;
import net.minecraft.inventory.Container;

/**
 * Manages a Furnace GUI Inventory.
 */
public class FurnaceManager extends ContainerManager {

    private static FurnaceManager instance = null;

    /**
     * Creates a new Furnace Manager with the given container.
     *
     * @param container The container from a crafting GUI
     */
    private FurnaceManager(Container container) {
        super(container);
    }

    /**
     * Returns a Furnace Manager Instance operating on the given container
     *
     * @param container A container from a GUI
     * @return manager-singleton
     */
    public static FurnaceManager getInstance(Container container) {
        if (instance == null) {
            instance = new FurnaceManager(container);
        } else {
            instance.container = container;
        }
        return instance;
    }

    @Override
    protected int specificKeyToSlotIndex() {

        return mapKeyToSlot(-1, 0, -1, -1, 1, -1, -1, -1, -1);

    }

    @Override
    protected int getInventoryStartIndex() {
        return 3;
    }

    @Override
    protected int getInteractionSlotIndex() {
        return 2;
    }

    @Override
    protected int[] getDropSlots() {
        return new int[]{0, 1, 2};
    }

    @Override
    protected void interact() {
        clickOnFurnaceOutput();
    }

    /**
     * Sends a click on the furnace output
     */
    private void clickOnFurnaceOutput() {

        Logger.info("clickOnFurnaceOutput()", "Clicked on Crafing Output.");
        rightClick(2);

    }
}
