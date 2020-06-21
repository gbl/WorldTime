package de.guntram.mcmod.worldtime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;

public class GuiWorldTime {

    MinecraftClient minecraft;

    public GuiWorldTime() {
        minecraft = MinecraftClient.getInstance();
    }
    
    public void onRenderGameOverlayPost(MatrixStack stack, float partialticks) {
        if (minecraft == null  || minecraft.player == null || minecraft.player.world == null)
            return;

        if (ConfigurationHandler.wantGameTime()) {
            int hours=(int) (minecraft.player.world.getTimeOfDay()/1000+6)%24;
            int minutes = (int) ((minecraft.player.world.getTimeOfDay()%1000)*60/1000);
            String clock=ConfigurationHandler.getPrefix()+String.format("%02d:%02d", hours, minutes);
            
            displayStringAtPercentages(stack, clock, ConfigurationHandler.getOffsetLeft(), ConfigurationHandler.getOffsetTop());
        }

        if (ConfigurationHandler.wantRealTime()) {
            String clock;
            try {
            	DateFormat dateFormat = new SimpleDateFormat(ConfigurationHandler.getRealTimeFormat());
                clock = ConfigurationHandler.getRealTimePrefix()+dateFormat.format(new Date());
            } catch (IllegalArgumentException ex) {
                clock = "illegal clock format; google for Java SimpleDateFormat";
            }
            displayStringAtPercentages(stack, clock, ConfigurationHandler.getRealTimeOffsetLeft(), ConfigurationHandler.getRealTimeOffsetTop());
        }
        
        if (ConfigurationHandler.wantCoords()) {
            String coords = ConfigurationHandler.getCoordsPrefix()+
                    ConfigurationHandler.getCoordsFormat()
                        .replace("{X}", String.format("%.1f", minecraft.player.getPos().x))
                        .replace("{Y}", String.format("%.1f", minecraft.player.getPos().y))
                        .replace("{Z}", String.format("%.1f", minecraft.player.getPos().z));
            displayStringAtPercentages(stack, coords, ConfigurationHandler.getCoordsOffsetLeft(), ConfigurationHandler.getCoordsOffsetTop());
        }
    }
    
    private void displayStringAtPercentages(MatrixStack stack, String string, int xperc, int yperc) {
        Window mainWindow = minecraft.getWindow();
        int xneed = minecraft.textRenderer.getWidth(string);
        int yneed = minecraft.textRenderer.fontHeight;

        int xpos = (mainWindow.getScaledWidth()-xneed)*xperc/100;
        int ypos = (mainWindow.getScaledHeight()-yneed)*yperc/100;

        minecraft.textRenderer.draw(stack, string, xpos, ypos, 0xffffff);
    }
}
