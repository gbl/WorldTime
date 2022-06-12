package de.guntram.mcmod.worldtime;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class GuiWorldTime {

    Minecraft minecraft;

    public GuiWorldTime() {
        minecraft = Minecraft.getInstance();
    }
    
    @SubscribeEvent(priority = EventPriority.NORMAL)    
    public void onRenderGameOverlayPost(RenderGameOverlayEvent.Text event) {
        if (minecraft == null  || minecraft.player == null || minecraft.player.level == null)
            return;

        PoseStack stack = event.getPoseStack();
        
        if (ConfigurationHandler.wantGameTime()) {
            long daytime = minecraft.player.clientLevel.getDayTime() +6000;       // 0 is 6 in the morning,,,
            
            int hours=(int) (daytime / 1000)%24;
            int minutes = (int) ((daytime % 1000)*60/1000);
            int day = (int) daytime / 1000 / 24;
            String clock;
            try {
                String strDateFormat = ConfigurationHandler.getGameTimeFormat().replace("J", Integer.toString(day));
                DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
                Calendar calendar = new GregorianCalendar();
                calendar.set(2000, 0, day+1, hours, minutes, 0);
                
                clock = ConfigurationHandler.getPrefix()+dateFormat.format(calendar.getTimeInMillis());
            } catch (IllegalArgumentException ex) {
                clock = "illegal clock format; google for Java SimpleDateFormat";
            }
            
            displayStringAtPercentages(stack, clock, ConfigurationHandler.getOffsetLeft(), ConfigurationHandler.getOffsetTop());
        }

        if (ConfigurationHandler.wantRealTime()) {
            String clock;
            try {
            	DateFormat dateFormat = new SimpleDateFormat(ConfigurationHandler.getRealTimeFormat());
                clock = ConfigurationHandler.getRealTimePrefix()+dateFormat.format(new Date(new Date().getTime()+ConfigurationHandler.getOffsetMinutes()*60*1000));
            } catch (IllegalArgumentException ex) {
                clock = "illegal clock format; google for Java SimpleDateFormat";
            }
            displayStringAtPercentages(stack, clock, ConfigurationHandler.getRealTimeOffsetLeft(), ConfigurationHandler.getRealTimeOffsetTop());
        }
        
        if (ConfigurationHandler.wantCoords()) {
            String coords = ConfigurationHandler.getCoordsPrefix()+
                    ConfigurationHandler.getCoordsFormat()
                        .replace("{X}", String.format("%.1f", minecraft.player.getX()))
                        .replace("{Y}", String.format("%.1f", minecraft.player.getY()))
                        .replace("{Z}", String.format("%.1f", minecraft.player.getZ()));
            displayStringAtPercentages(stack, coords, ConfigurationHandler.getCoordsOffsetLeft(), ConfigurationHandler.getCoordsOffsetTop());
        }
    }
    
    private void displayStringAtPercentages(PoseStack stack, String string, int xperc, int yperc) {
        Window mainWindow = minecraft.getWindow();
        int xneed = minecraft.font.width(string);
        int yneed = minecraft.font.lineHeight;

        int xpos = (mainWindow.getGuiScaledWidth()-xneed)*xperc/100;
        int ypos = (mainWindow.getGuiScaledHeight()-yneed)*yperc/100;

        minecraft.font.draw(stack, string, xpos, ypos, 0xffffff);
    }
}
