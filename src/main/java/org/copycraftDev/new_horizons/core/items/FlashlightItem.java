package org.copycraftDev.new_horizons.core.items;

import org.copycraftDev.new_horizons.core.render.FlashlightRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class FlashlightItem extends Item {

    private final FlashlightRenderer flashlightRenderer;

    public FlashlightItem(Settings settings) {
        super(settings);
        // Initialize the flashlight renderer.
        flashlightRenderer = new FlashlightRenderer();
    }

    /**
     * Called when the player right-clicks with the item.
     * On the client, this toggles the flashlight.
     */
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient) {
            flashlightRenderer.toggle();
            flashlightRenderer.updateFromCamera(MinecraftClient.getInstance());
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    /**
     * Called each tick while the item is in the player's inventory.
     * If the item is selected and the flashlight is on, update its position and orientation.
     */
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.isClient && selected && flashlightRenderer.isOn()) {
            flashlightRenderer.updateFromCamera(MinecraftClient.getInstance());
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
