package me.sescenti;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import static me.sescenti.BuffGuyAdv.game;

public class Events implements Listener {

    @EventHandler
    public void advComplete(PlayerAdvancementDoneEvent e){
        if(game.getBoss() == null || e.getPlayer().getDisplayName().equals(game.getBoss().getDisplayName()))
            return;
        for(int i = 0; i < game.getAdvs().length; i++) {
            if(e.getAdvancement().equals(game.getAdvs()[i]) && !game.getCompleted()[i] && !e.getPlayer().getUniqueId().equals(game.getBoss().getUniqueId())){
                game.setCompleted(i);

                if(i < 4){
                    System.out.println("The runners have completed the easy advancement " + game.getAdvs()[i].getKey().getKey());
                    for(Player p : Bukkit.getOnlinePlayers()){
                        p.sendMessage("The runners have completed the easy advancement " + game.getAdvs()[i].getKey().getKey());
                    }
                    game.easyAdvNerf();
                } else if(i < 7){
                    System.out.println("The runners have completed the medium advancement " + game.getAdvs()[i].getKey().getKey());
                    for(Player p : Bukkit.getOnlinePlayers()){
                        p.sendMessage("The runners have completed the medium advancement " + game.getAdvs()[i].getKey().getKey());
                    }
                    game.medAdvNerf();
                } else if(i < 9){
                    System.out.println("The runners have completed the hard advancement " + game.getAdvs()[i].getKey().getKey());
                    for(Player p : Bukkit.getOnlinePlayers()){
                        p.sendMessage("The runners have completed the hard advancement " + game.getAdvs()[i].getKey().getKey());
                    }
                    game.hardAdvNerf();
                } else {
                    System.out.println("The runners have completed the expert advancement " + game.getAdvs()[i].getKey().getKey());
                    for(Player p : Bukkit.getOnlinePlayers()){
                        p.sendMessage("The runners have completed the expert advancement " + game.getAdvs()[i].getKey().getKey());
                    }
                    game.expertAdvNerf();
                }
                game.displayBossEffects();
                game.completedAdv();
            }
        }
    }

    @EventHandler
    private void playerDeath(PlayerDeathEvent e){
        if(game.getBoss() == null)
            return;
        if(e.getEntity().getUniqueId().equals(game.getBoss().getUniqueId())){
            for(Player p : Bukkit.getOnlinePlayers()){
                p.sendMessage("The boss, " + game.getBoss().getDisplayName() + ", has died. \nRunners win.");
            }
            System.out.println("The boss, " + game.getBoss().getDisplayName() + ", has died. \nRunners win.");
            game.resetGame();
        } else {
            game.playerDeathBuff(e.getEntity());
        }
    }
}
