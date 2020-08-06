/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.guntram.mcmod.worldtime;

import de.guntram.mcmod.GBForgetools.ConfigurationProvider;
import de.guntram.mcmod.GBForgetools.GuiModOptions;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("worldtime")
public class WorldTime {
    
    public static final String MODID = "worldtime";
    public static final String MODNAME = "World Time";
    public static final String VERSION = "1.0";
    
    public static WorldTime instance;
    private static ConfigurationHandler confHandler;
    
    public WorldTime() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::init);
    }

    public void init(final FMLCommonSetupEvent event) {
        confHandler = ConfigurationHandler.getInstance();
        confHandler.load(ConfigurationProvider.getSuggestedFile(MODID));
        MinecraftForge.EVENT_BUS.register(new ConfigKey());
        MinecraftForge.EVENT_BUS.register(new GuiWorldTime());
    }
    
    public static void openConfigScreen() {
        Minecraft.getInstance().displayGuiScreen(new GuiModOptions(null, MODNAME, confHandler));
    }
}
