package de.guntram.mcmod.worldtime;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.minecraft.client.MainWindow;
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
        if (minecraft == null  || minecraft.player == null || minecraft.player.world == null)
            return;

        MatrixStack stack = event.getMatrixStack();
        
        if (ConfigurationHandler.wantGameTime()) {
            int hours=(int) (minecraft.player.world.getDayTime()/1000+6)%24;
            int minutes = (int) ((minecraft.player.world.getDayTime()%1000)*60/1000);
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
                        .replace("{X}", String.format("%.1f", minecraft.player.getPosX()))
                        .replace("{Y}", String.format("%.1f", minecraft.player.getPosY()))
                        .replace("{Z}", String.format("%.1f", minecraft.player.getPosZ()));
            displayStringAtPercentages(stack, coords, ConfigurationHandler.getCoordsOffsetLeft(), ConfigurationHandler.getCoordsOffsetTop());
        }
    }
    
    private void displayStringAtPercentages(MatrixStack stack, String string, int xperc, int yperc) {
        MainWindow mainWindow = minecraft.getMainWindow();
        int xneed = minecraft.fontRenderer.getStringWidth(string);
        int yneed = minecraft.fontRenderer.FONT_HEIGHT;

        int xpos = (mainWindow.getScaledWidth()-xneed)*xperc/100;
        int ypos = (mainWindow.getScaledHeight()-yneed)*yperc/100;

        minecraft.fontRenderer.func_238405_a_(stack, string, xpos, ypos, 0xffffff);
    }
}
