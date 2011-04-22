package com.herocraftonline.dev.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.NetServerHandler;
import net.minecraft.server.NetworkManager;
import net.minecraft.server.Packet101CloseWindow;
import net.minecraft.server.Packet102WindowClick;
import net.minecraft.server.Packet106Transaction;
import net.minecraft.server.Slot;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.api.InventoryChangeEvent;
import com.herocraftonline.dev.heroes.api.InventoryCloseEvent;

public class HNetServerHandler extends NetServerHandler {

    private Map n = new HashMap();

    public HNetServerHandler(MinecraftServer minecraftserver, NetworkManager networkmanager, EntityPlayer entityplayer) {
        super(minecraftserver, networkmanager, entityplayer);
    }

    @Override
    public void a(Packet101CloseWindow packet101closewindow) {
        Bukkit.getServer().getPluginManager().callEvent(new InventoryCloseEvent((Player) this.player.getBukkitEntity()));
        this.player.z();
    }

    @Override
    public void a(Packet102WindowClick packet102windowclick) {
        //System.out.print("1 - Click");
        if (this.player.activeContainer.f == packet102windowclick.a && this.player.activeContainer.c(this.player)) {
            //System.out.print("2 - Active Container");
            //System.out.print("Location - " + packet102windowclick.b);
            //System.out.print(packet102windowclick.c);

            int pos = packet102windowclick.b;
            Player p = (Player) this.player.getBukkitEntity();
            InventoryChangeEvent event = new InventoryChangeEvent(p, pos);
            Bukkit.getServer().getPluginManager().callEvent(event);

            if(event.isCancelled()){
                p.updateInventory();
                return;
            }

            ItemStack itemstack = this.player.activeContainer.a(packet102windowclick.b, packet102windowclick.c, packet102windowclick.f, this.player);

            if (ItemStack.equals(packet102windowclick.e, itemstack)) {
                //System.out.print("3 - Both Items are same.");
                //System.out.print(packet102windowclick.a);
                //System.out.print("Click Amount - " + packet102windowclick.d);

                this.player.netServerHandler.sendPacket(new Packet106Transaction(packet102windowclick.a, packet102windowclick.d, true));
                this.player.h = true;
                this.player.activeContainer.a();
                this.player.y();
                this.player.h = false;
            } else {
                System.out.print("4 - Different Items.");
                this.n.put(Integer.valueOf(this.player.activeContainer.f), Short.valueOf(packet102windowclick.d));
                this.player.netServerHandler.sendPacket(new Packet106Transaction(packet102windowclick.a, packet102windowclick.d, false));
                this.player.activeContainer.a(this.player, false);
                ArrayList arraylist = new ArrayList();

                for (int i = 0; i < this.player.activeContainer.e.size(); ++i) {
                    System.out.print("5 " + i);
                    arraylist.add(((Slot) this.player.activeContainer.e.get(i)).getItem());
                }

                this.player.a(this.player.activeContainer, arraylist);
            }
            //System.out.print("---");
        }
    }
}
