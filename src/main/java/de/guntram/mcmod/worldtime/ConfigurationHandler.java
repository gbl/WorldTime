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

    private boolean wantGameTime;
    private int offsetLeft;
    private int offsetTop;
    private String prefix;

    private boolean wantRealTime;
    private int offsetRTLeft;
    private int offsetRTTop;
    private String RTFormat;
    private String RTPrefix;
    
    private final String CONF_GT = "Game Time";
    private final String CONF_X = "Game Time X Percent";
    private final String CONF_Y = "Game Time Y Percent";
    private final String PREFIX = "Game Time Prefix";

    private final String CONF_RT = "Real Time";
    private final String CONF_RTX = "Real Time X Percent";
    private final String CONF_RTY = "Real Time Y Percent";
    private final String RTFORMAT = "Real Time Format";
    private final String RTPREFIX = "Real Time Prefix";

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
                case CONF_GT: wantGameTime=(boolean)(Boolean)(event.getNewValue()); break;
                case CONF_X: offsetLeft=(int)(Integer)(event.getNewValue()); break;
                case CONF_Y: offsetTop =(int)(Integer)(event.getNewValue()); break;
                case PREFIX: prefix = (String)(event.getNewValue()); break;
                case CONF_RT: wantRealTime=(boolean)(Boolean)(event.getNewValue()); break;
                case CONF_RTX: offsetRTLeft=(int)(Integer)(event.getNewValue()); break;
                case CONF_RTY: offsetRTTop =(int)(Integer)(event.getNewValue()); break;
                case RTFORMAT: RTFormat = (String)(event.getNewValue()); break;
                case RTPREFIX: RTPrefix = (String)(event.getNewValue()); break;
            }
        }
    }
    */

    private void loadConfig() {
        /* can't do those in old config version
        config.forget("X Percent");
        config.forget("Y Percent");
        config.forget("Prefix");
        */

        wantGameTime=config.getBoolean(CONF_GT, Configuration.CATEGORY_CLIENT, true, "Show ingame time");
        offsetLeft=config.getInt(CONF_X, Configuration.CATEGORY_CLIENT, 0, 0, 100, "Offset from screen left");
        offsetTop=config.getInt(CONF_Y, Configuration.CATEGORY_CLIENT, 5, 0, 100, "Offset from screen top");
        prefix=config.getString(PREFIX, Configuration.CATEGORY_CLIENT, "", "prefix to define colors or similar");
        wantRealTime=config.getBoolean(CONF_RT, Configuration.CATEGORY_CLIENT, false, "Show real time");
        offsetRTLeft=config.getInt(CONF_RTX, Configuration.CATEGORY_CLIENT, 0, 0, 100, "Offset from screen left");
        offsetRTTop=config.getInt(CONF_RTY, Configuration.CATEGORY_CLIENT, 10, 0, 100, "Offset from screen top");
        RTFormat=config.getString(RTFORMAT, Configuration.CATEGORY_CLIENT, "HH:mm:ss", "Real Time Format (please see Java SimpleDateFormat");
        RTPrefix=config.getString(RTPREFIX, Configuration.CATEGORY_CLIENT, "", "prefix to define colors or similar");
        if (config.hasChanged())
            config.save();
    }
    
    @Override
    public Configuration getConfig() {
        return getInstance().config;
    }

    public static boolean wantGameTime()        { return getInstance().wantGameTime; }
    public static int getOffsetLeft()           { return getInstance().offsetLeft; }
    public static int getOffsetTop()            { return getInstance().offsetTop; }
    public static String getPrefix()            { return getInstance().prefix.replace('&', '§'); }

    public static boolean wantRealTime()        { return getInstance().wantRealTime; }
    public static String getRealTimeFormat()    { return getInstance().RTFormat; }
    public static int getRealTimeOffsetLeft()   { return getInstance().offsetRTLeft; }
    public static int getRealTimeOffsetTop()    { return getInstance().offsetRTTop; }
    public static String getRealTimePrefix()    { return getInstance().RTPrefix.replace('&', '§'); }
}
