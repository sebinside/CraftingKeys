package de.skate702.craftingkeys.manager;

import de.skate702.craftingkeys.util.Logger;
import net.minecraft.inventory.Container;

/**
 * Manages a Brewing Stand GUI Inventory.
 */
public class BrewingManager extends ContainerManager {

    private static BrewingManager instance = null;

    /**
     * Creates a new Brewing Manager with the given container.
     *
     * @param container The container from a crafting GUI
     */
    private BrewingManager(Container container) {
        super(container);
    }

    /**
     * Returns a Brewing Manager Instance operating on the given container
     *
     * @param container A container from a GUI
     * @return manager-singleton
     */
    public static BrewingManager getInstance(Container container) {
        if (instance == null) {
            instance = new BrewingManager(container);
        } else {
            instance.container = container;
        }
        return instance;
    }

    @Override
    protected int specificKeyToSlotIndex() {
        //TODO: check for v1.11
    	return mapKeyToSlot(4, 3, -1, 0, 1, 2, -1, -1, -1);
    }

    @Override
    protected int getInventoryStartIndex() {
        return 4;
    }

    @Override
    protected int getInteractionSlotIndex() {
        return -1;
    }

    @Override
    protected void onInteractionKeyPressed() {
        Logger.info("onInteractionKeyPressed()", "Brewing stands have no interaction!");
    }

    @Override
    protected int[] getDropSlots() {
        return new int[]{0, 1, 2, 3};
    }

    @Override
    protected void interact() {

    }
}
