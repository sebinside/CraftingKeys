package de.skate702.craftingkeys.proxies;

import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;

/**
 * Client Proxy, extending Common Proxy.
 *
 * @author skate702
 */
public class CraftingKeysClientProxy extends CraftingKeysProxy {

    @Override
    public void sendSlotClick(PlayerControllerMP controller, int windowId, int slot, int rightClick, int action,
                              EntityPlayer player) {

        controller.windowClick(windowId, slot, rightClick, ClickType.PICKUP, player);
    }

}
