package de.guntram.mcmod.worldtime;

import de.guntram.mcmod.fabrictools.ConfigChangedEvent;
import de.guntram.mcmod.fabrictools.Configuration;
import de.guntram.mcmod.fabrictools.ModConfigurationHandler;
import java.io.File;

public class ConfigurationHandler implements ModConfigurationHandler
{
    private static ConfigurationHandler instance;

    private Configuration config;
    private String configFileName;

    private int offsetLeft;
    private int offsetTop;
    private String prefix;
    
    private final String CONF_X = "X Percent";
    private final String CONF_Y = "Y Percent";
    private final String PREFIX = "Prefix";

    public static ConfigurationHandler getInstance() {
        if (instance==null) {
            instance=new ConfigurationHandler();
        }
        return instance;
    }
    
    public void load(final File configFile) {
        if (config == null) {
            config = new Configuration(configFile);
            configFileName=configFile.getPath();
            loadConfig();
        }
    }
    
    public static String getConfigFileName() {
        return getInstance().configFileName;
    }

    @Override
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(WorldTime.MODNAME)) {
            loadConfig();
        }
    }

/* Not supported in version 1.0.x of GBFabricTools, but I don't want to update
 * as I would have to do that in ALL mods - 1.0 still has command interpreter and key handler.

    @Override
    public void onConfigChanging(ConfigChangedEvent.OnConfigChangingEvent event) {
        if (event.getModID().equals(WorldTime.MODNAME)) {
            switch (event.getItem()) {
                case CONF_X: offsetLeft=(int)(Integer)(event.getNewValue()); break;
                case CONF_Y: offsetTop =(int)(Integer)(event.getNewValue()); break;
                case PREFIX: prefix = (String)(event.getNewValue()); break;
            }
        }
    }
    */

    private void loadConfig() {
        offsetLeft=config.getInt(CONF_X, Configuration.CATEGORY_CLIENT, 0, 0, 100, "Offset from screen left");
        offsetTop=config.getInt(CONF_Y, Configuration.CATEGORY_CLIENT, 5, 0, 100, "Offset from screen top");
        prefix=config.getString(PREFIX, Configuration.CATEGORY_CLIENT, "", "prefix to define colors or similar");
        if (config.hasChanged())
            config.save();
    }
    
    @Override
    public Configuration getConfig() {
        return getInstance().config;
    }

    public static int getOffsetLeft() {
        return getInstance().offsetLeft;
    }
    
    public static int getOffsetTop() {
        return getInstance().offsetTop;
    }
    
    public static String getPrefix() {
        return getInstance().prefix.replace('&', '§');
    }
}
