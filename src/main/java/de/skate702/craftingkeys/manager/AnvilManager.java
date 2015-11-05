package de.skate702.craftingkeys.manager;

import de.skate702.craftingkeys.util.Logger;
import net.minecraft.inventory.Container;

public class AnvilManager extends ContainerManager {
    //TODO:Get it to work...It's just a copy of the brewingManager
    private static AnvilManager instance = null;

    private AnvilManager(Container container) {
        super(container);
    }

    public static AnvilManager getInstance(Container container) {
        if (instance == null) {
            instance = new AnvilManager(container);
        } else {
            instance.container = container;
        }
        return instance;
    }

    @Override
    protected int specificKeyToSlotIndex() {
        return mapKeyToSlot(-1, 3, -1, 0, 1, 2, -1, -1, -1);
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
