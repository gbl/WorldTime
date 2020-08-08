package de.guntram.mcmod.worldtime;

import de.guntram.mcmod.GBForgetools.ConfigChangedEvent;
import de.guntram.mcmod.GBForgetools.Configuration;
import de.guntram.mcmod.GBForgetools.ModConfigurationHandler;
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
    
    private boolean wantCoords;
    private int offsetCOLeft, offsetCOTop;
    private String COFormat, COPrefix;
    
    private final String CONF_GT = "worldtime.config.gametime";
    private final String CONF_GTX = "worldtime.config.gametimex";
    private final String CONF_GTY = "worldtime.config.gametimey";
    private final String CONF_GTPREFIX = "worldtime.config.gametimeprefix";

    private final String CONF_RT = "worldtime.config.realtime";
    private final String CONF_RTX = "worldtime.config.realtimex";
    private final String CONF_RTY = "worldtime.config.realtimey";
    private final String CONF_RTFORMAT = "worldtime.config.realtimeformat";
    private final String CONF_RTPREFIX = "worldtime.config.realtimeprefix";
    
    private final String CONF_CO =     "worldtime.config.coords";
    private final String CONF_COX =     "worldtime.config.coordsx";
    private final String CONF_COY =     "worldtime.config.coordsy";
    private final String CONF_COPREFIX ="worldtime.config.coordsprefix";
    private final String CONF_COFORMAT ="worldtime.config.coordsformat";

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
    
    @Override
    public void onConfigChanging(ConfigChangedEvent.OnConfigChangingEvent event) {
        if (event.getModID().equals(WorldTime.MODNAME)) {
            switch (event.getItem()) {
                case CONF_GT:       wantGameTime=(boolean)(Boolean)(event.getNewValue()); break;
                case CONF_GTX:      offsetLeft  =(int)(Integer)(event.getNewValue()); break;
                case CONF_GTY:      offsetTop   =(int)(Integer)(event.getNewValue()); break;
                case CONF_GTPREFIX: prefix      = (String)(event.getNewValue()); break;
                
                case CONF_RT:       wantRealTime=(boolean)(Boolean)(event.getNewValue()); break;
                case CONF_RTX:      offsetRTLeft=(int)(Integer)(event.getNewValue()); break;
                case CONF_RTY:      offsetRTTop =(int)(Integer)(event.getNewValue()); break;
                case CONF_RTFORMAT: RTFormat    = (String)(event.getNewValue()); break;
                case CONF_RTPREFIX: RTPrefix    = (String)(event.getNewValue()); break;
                
                case CONF_CO:       wantCoords  =(boolean)(Boolean)(event.getNewValue()); break;
                case CONF_COX:      offsetCOLeft=(int)(Integer)(event.getNewValue()); break;
                case CONF_COY:      offsetCOTop =(int)(Integer)(event.getNewValue()); break;
                case CONF_COFORMAT: COFormat    = (String)(event.getNewValue()); break;
                case CONF_COPREFIX: COPrefix    = (String)(event.getNewValue()); break;
            }
        }
    }
    
    private void loadConfig() {
        config.migrate("X Percent", CONF_GTX);
        config.migrate("Y Percent", CONF_GTY);
        config.migrate("Prefix",    CONF_GTPREFIX);

        config.migrate("Game Time", CONF_GT);
        config.migrate("Game Time X Percent", CONF_GTX);
        config.migrate("Game Time Y Percent", CONF_GTY);
        config.migrate("Game Time Prefix", CONF_GTPREFIX);

        config.migrate("Real Time", CONF_RT);
        config.migrate("Real Time X Percent", CONF_RTX);
        config.migrate("Real Time Y Percent", CONF_RTY);
        config.migrate("Real Time Prefix", CONF_RTPREFIX);
        config.migrate("Real Time Format", CONF_RTFORMAT);
        
        wantGameTime=config.getBoolean(CONF_GT, Configuration.CATEGORY_CLIENT, true, "worldtime.config.tt.gametime");
        offsetLeft=config.getInt(CONF_GTX, Configuration.CATEGORY_CLIENT, 0, 0, 100, "worldtime.config.tt.offsetleft");
        offsetTop=config.getInt(CONF_GTY, Configuration.CATEGORY_CLIENT, 5, 0, 100, "worldtime.config.tt.offsettop");
        prefix=config.getString(CONF_GTPREFIX, Configuration.CATEGORY_CLIENT, "", "worldtime.config.tt.prefix");
        
        wantRealTime=config.getBoolean(CONF_RT, Configuration.CATEGORY_CLIENT, false, "worldtime.config.tt.realtime");
        offsetRTLeft=config.getInt(CONF_RTX, Configuration.CATEGORY_CLIENT, 0, 0, 100, "worldtime.config.tt.offsetleft");
        offsetRTTop=config.getInt(CONF_RTY, Configuration.CATEGORY_CLIENT, 10, 0, 100, "worldtime.config.tt.offsettop");
        RTFormat=config.getString(CONF_RTFORMAT, Configuration.CATEGORY_CLIENT, "HH:mm:ss", "worldtime.config.tt.realtimeformat");
        RTPrefix=config.getString(CONF_RTPREFIX, Configuration.CATEGORY_CLIENT, "", "worldtime.config.tt.prefix");

        wantCoords=config.getBoolean(CONF_CO, Configuration.CATEGORY_CLIENT, false, "worldtime.config.tt.coords");
        offsetCOLeft=config.getInt(CONF_COX, Configuration.CATEGORY_CLIENT, 0, 0, 100, "worldtime.config.tt.offsetleft");
        offsetCOTop=config.getInt(CONF_COY, Configuration.CATEGORY_CLIENT, 10, 0, 100, "worldtime.config.tt.offsettop");
        COFormat=config.getString(CONF_COFORMAT, Configuration.CATEGORY_CLIENT, "Position {X}/{Z} Height {Y}", "worldtime.config.tt.coordformat");
        COPrefix=config.getString(CONF_COPREFIX, Configuration.CATEGORY_CLIENT, "", "worldtime.config.tt.prefix");

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

    public static boolean wantCoords()          { return getInstance().wantCoords; }
    public static String getCoordsFormat()      { return getInstance().COFormat.replace('&', '§'); }
    public static int getCoordsOffsetLeft()     { return getInstance().offsetCOLeft; }
    public static int getCoordsOffsetTop()      { return getInstance().offsetCOTop; }
    public static String getCoordsPrefix()      { return getInstance().COPrefix.replace('&', '§'); }
}
