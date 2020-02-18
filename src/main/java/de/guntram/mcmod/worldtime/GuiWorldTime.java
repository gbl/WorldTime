package de.guntram.mcmod.worldtime;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;

public class GuiWorldTime {
    
    public GuiWorldTime() {

    }
    
    public void onRenderGameOverlayPost(float partialticks) {
        MinecraftClient minecraft = MinecraftClient.getInstance();
        if (minecraft == null  || minecraft.player == null || minecraft.player.world == null)
            return;
        Window mainWindow = minecraft.getWindow();
        
        int hours=(int) (minecraft.player.world.getTimeOfDay()/1000+6)%24;
        int minutes = (int) ((minecraft.player.world.getTimeOfDay()%1000)*60/1000);
        String clock=ConfigurationHandler.getPrefix()+String.format("%02d:%02d", hours, minutes);

        int xneed = minecraft.textRenderer.getStringWidth(clock);
        int yneed = minecraft.textRenderer.fontHeight;
        
        int xpos = (mainWindow.getScaledWidth()-xneed)*ConfigurationHandler.getOffsetLeft()/100;
        int ypos = (mainWindow.getScaledHeight()-yneed)*ConfigurationHandler.getOffsetTop()/100;

        minecraft.textRenderer.draw(clock, xpos, ypos, 0xffffff);
    }
}
