/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.guntram.mcmod.worldtime;

import de.guntram.mcmod.fabrictools.ConfigurationProvider;
import net.fabricmc.api.ClientModInitializer;

/**
 *
 * @author gbl
 */
public class WorldTime implements ClientModInitializer {
    
    public static final String MODID = "worldtime";
    public static final String MODNAME = "World Time";
    public static final String VERSION = "1.0";
    
    public static WorldTime instance;

    @Override
    public void onInitializeClient() {
        ConfigurationHandler confHandler = ConfigurationHandler.getInstance();
        ConfigurationProvider.register(MODNAME, confHandler);
        confHandler.load(ConfigurationProvider.getSuggestedFile(MODID));
    }

}
