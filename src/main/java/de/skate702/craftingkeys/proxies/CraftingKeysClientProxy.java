package de.skate702.craftingkeys.proxies;

import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Client Proxy, extending Common Proxy.
 * 
 * @author skate702
 *
 */
public class CraftingKeysClientProxy extends CraftingKeysProxy {

	@Override
	public void registerRenderers() {

	}

	@Override
	public void sendSlotClick(PlayerControllerMP controller, int windowId, int slot, int rightClick, int action,
			EntityPlayer player) {

		// TODO: Make this *** multiplayer friendly

		controller.windowClick(windowId, slot, rightClick, action, player);
		// player.openContainer.slotClick(slot, rightClick, action, player);

	}

}
