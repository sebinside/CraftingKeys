package de.skate702.craftingkeys.proxies;

// NEW_1_9 import net.minecraft.inventory.ClickType;

import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Client Proxy, extending Common Proxy.
 *
 * @author skate702
 */
public class CraftingKeysClientProxy extends CraftingKeysProxy {

    @Override
    public void sendSlotClick(PlayerControllerMP controller, int windowId, int slot, int rightClick, int action,
                              EntityPlayer player) {

        controller.windowClick(windowId, slot, rightClick, action, player);
        // NEW_1_9 controller.func_187098_a(windowId, slot, rightClick, ClickType.PICKUP, player);
    }

}