/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.guntram.mcmod.worldtime;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;

/**
 *
 * @author gbl
 */
public class ConfigKey {
    static KeyMapping configKey;
    
    ConfigKey() {
        if (configKey == null) {
            configKey = new KeyMapping("key.worldtime.config", GLFW.GLFW_KEY_UNKNOWN, "key.worldtime.header");
        }
    }

    @SubscribeEvent
    public void keyPressed(final InputEvent.Key e) {
        if (configKey.consumeClick()) {
            WorldTime.openConfigScreen();
        }
    }    
}
