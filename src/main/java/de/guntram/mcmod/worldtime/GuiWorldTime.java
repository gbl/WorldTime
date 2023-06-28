package de.guntram.mcmod.worldtime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;

public class GuiWorldTime {

    MinecraftClient minecraft;

    public GuiWorldTime() {
        minecraft = MinecraftClient.getInstance();
    }
    
    public void onRenderGameOverlayPost(DrawContext context, float partialticks) {
        if (minecraft == null  || minecraft.player == null || minecraft.player.getWorld() == null)
            return;

        if (ConfigurationHandler.wantGameTime()) {
            long daytime = minecraft.player.getWorld().getTimeOfDay()+6000;
            
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
            
            
            displayStringAtPercentages(context, clock, ConfigurationHandler.getOffsetLeft(), ConfigurationHandler.getOffsetTop());
        }

        if (ConfigurationHandler.wantRealTime()) {
            String clock;
            try {
            	DateFormat dateFormat = new SimpleDateFormat(ConfigurationHandler.getRealTimeFormat());
                clock = ConfigurationHandler.getRealTimePrefix()+dateFormat.format(new Date(new Date().getTime()+ConfigurationHandler.getOffsetMinutes()*60*1000));
            } catch (IllegalArgumentException ex) {
                clock = "illegal clock format; google for Java SimpleDateFormat";
            }
            displayStringAtPercentages(context, clock, ConfigurationHandler.getRealTimeOffsetLeft(), ConfigurationHandler.getRealTimeOffsetTop());
        }
        
        if (ConfigurationHandler.wantCoords()) {
            String coords = ConfigurationHandler.getCoordsPrefix()+
                    ConfigurationHandler.getCoordsFormat()
                        .replace("{X}", String.format("%.1f", minecraft.player.getPos().x))
                        .replace("{Y}", String.format("%.1f", minecraft.player.getPos().y))
                        .replace("{Z}", String.format("%.1f", minecraft.player.getPos().z));
            displayStringAtPercentages(context, coords, ConfigurationHandler.getCoordsOffsetLeft(), ConfigurationHandler.getCoordsOffsetTop());
        }
    }
    
    private void displayStringAtPercentages(DrawContext context, String string, int xperc, int yperc) {
        Window mainWindow = minecraft.getWindow();
        int xneed = minecraft.textRenderer.getWidth(string);
        int yneed = minecraft.textRenderer.fontHeight;

        int xpos = (mainWindow.getScaledWidth()-xneed)*xperc/100;
        int ypos = (mainWindow.getScaledHeight()-yneed)*yperc/100;

        context.drawTextWithShadow(minecraft.textRenderer, string, xpos, ypos, 0xffffff);
    }
}
