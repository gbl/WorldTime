/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.guntram.mcmod.worldtime;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

/**
 *
 * @author gbl
 */
public class ConfigKey {
    static KeyBinding configKey;
    
    ConfigKey() {
        if (configKey == null) {
            ClientRegistry.registerKeyBinding(configKey = new KeyBinding("key.worldtime.config", GLFW.GLFW_KEY_UNKNOWN, "key.worldtime.header"));
        }
    }

    @SubscribeEvent
    public void keyPressed(final InputEvent.KeyInputEvent e) {
        if (configKey.isPressed()) {
            WorldTime.openConfigScreen();
        }
    }    
}
