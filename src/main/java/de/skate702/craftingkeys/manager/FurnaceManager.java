package de.skate702.craftingkeys.manager;

import de.skate702.craftingkeys.config.Config;
import de.skate702.craftingkeys.util.InputUtil;
import de.skate702.craftingkeys.util.Logger;
import de.skate702.craftingkeys.util.Util;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import org.lwjgl.input.Keyboard;

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
    public void acceptKey() {

        Slot currentHoveredSlot = InputUtil.getSlotAtMousePosition((GuiFurnace) Util.client.currentScreen);

        int slotIndex = specificKeyDownToSlotIndex();

        if (!InputUtil.isSameKey(slotIndex)) {

            // Drop
            if (isDropKeyDown()) {

                Logger.info("acceptKey()", "Drop Key down.");

                for (int i = 0; i < 3; i++) {
                    moveStackToInventory(i);
                }

                // Get from output
            } else if (isInteractionKeyDown()) {

                Logger.info("acceptKey()", "Interaction Key down.");

                // Handles Interaction with items held
                if (Util.isHoldingStack() && (
                        !Util.getHeldStack().isItemEqual(getItemStack(0))
                                || Util.getHeldStack().stackSize + getItemStack(0).stackSize
                                >= getItemStack(0).getMaxStackSize())) {
                    moveStackToInventory(-1);
                }

                if (isStackKeyDown()) {

                    int oldStackSize = -1;
                    clickOnFurnaceOutput();

                    while (Util.isHoldingStack() &&
                            oldStackSize != Util.getHeldStack().stackSize) {

                        oldStackSize = Util.getHeldStack().stackSize;
                        clickOnFurnaceOutput();
                    }

                } else {
                    clickOnFurnaceOutput();
                }

                // Move from hovered slot
            } else if (slotIndex >= 0 && currentHoveredSlot != null &&
                    !Util.isHoldingStack()) {

                Logger.info("acceptKey()", "Key for index " + slotIndex + " down.");

                if (isStackKeyDown()) {
                    moveAll(currentHoveredSlot.slotNumber, slotIndex);
                    moveStackToInventory(-1);
                } else {
                    move(currentHoveredSlot.slotNumber, slotIndex, 1);
                }

                // Handle NumKey-moving and held-moving
            } else if (Util.isHoldingStack()) {

                if (isStackKeyDown()) {
                    moveAll(-1, slotIndex);
                    moveStackToInventory(-1);
                } else {
                    move(-1, slotIndex, 1);
                }

                handleNumKey();
            }
        }

    }

    private void clickOnFurnaceOutput() {

        // Click on furnace output
        Logger.info("clickOnCraftingOutput()", "Clicked on Crafing Output.");
        rightClick(2);

    }

    @Override
    protected int specificKeyDownToSlotIndex() {

        if (Keyboard.isKeyDown(Config.getKeyTopCenter()) ||
                Config.isNumPadEnabled() && Keyboard.isKeyDown(72)) {
            return 0;
        } else if (Keyboard.isKeyDown(Config.getKeyCenterCenter()) ||
                Config.isNumPadEnabled() && Keyboard.isKeyDown(76)) {
            return 1;
        } else if (Keyboard.isKeyDown(Config.getKeyInteract())) {
            return -101;
        } else if (Keyboard.isKeyDown(Config.getKeyDrop())) {
            return -102;
        } else {
            return -1;
        }

    }

    @Override
    protected int getInventoryStartIndex() {
        return 3;
    }
}
