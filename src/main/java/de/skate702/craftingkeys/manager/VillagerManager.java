package de.skate702.craftingkeys.manager;


import de.skate702.craftingkeys.util.Logger;
import net.minecraft.inventory.Container;

/**
 * Manages a Villager GUI Inventory.
 */
public class VillagerManager extends ContainerManager {

    private static VillagerManager instance = null;

    /**
     * Creates a new Villager Manager with the given container.
     *
     * @param container The container from a crafting GUI
     */
    private VillagerManager(Container container) {
        super(container);
    }

    /**
     * Returns a Villager Manager Instance operating on the given container
     *
     * @param container A container from a GUI
     * @return manager-singleton
     */
    public static VillagerManager getInstance(Container container) {
        if (instance == null) {
            instance = new VillagerManager(container);
        } else {
            instance.container = container;
        }
        return instance;
    }

    @Override
    protected int specificKeyToSlotIndex() {
        return mapKeyToSlot(-1, 0, 1, -1, 0, 1, -1, -1, -1);
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
        return new int[]{0, 1};
    }

    @Override
    protected void interact() {
        clickOnVillagerOutput();
    }

    /**
     * Sends a click on the furnace output
     */
    private void clickOnVillagerOutput() {

        Logger.info("clickOnVillagerOutput()", "Clicked on Villager Output.");
        leftClick(2);

    }
}
