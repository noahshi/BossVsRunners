package me.sescenti;

import org.bukkit.Bukkit;
import org.bukkit.advancement.Advancement;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class BuffGuyAdv extends JavaPlugin implements Listener {

    public static Game game = new Game();
    public static BuffGuyAdv instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(this, this);
        pm.registerEvents(new Events(), this);
        System.out.println("[Boss vs Runners] Starting Plugin...");
        instance = this;
        game.resetGame();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(label.equals("start")){
            game.startGame();
            return true;
        }
        if(label.equals("list")){
            if(sender instanceof Player){
                for(Advancement a : game.getAdvs()){
                    sender.sendMessage(a.getKey().getKey());
                }
            } else {
                for(Advancement a : game.getAdvs()){
                    System.out.println("[Boss vs Runners] " + a.getKey().getKey());
                }
            }
            return true;
        }
        if(label.equals("setlives")){
            if(args.length == 1){
                try{
                    game.setRunnerLives(Integer.parseInt(args[0]));
                    return true;
                } catch (NumberFormatException e){
                    return false;
                }
            } else {
                return false;
            }
        }
        if(label.equals("lives")){
            if(sender instanceof Player){
                if(game.getRunnerLives() != 1) {
                    sender.sendMessage("You have " + game.getRunnerLives() + " lives left");
                } else {
                    sender.sendMessage("You have 1 life left");
                }
            } else {
                System.out.println("{Boss vs Runners] Runners lives left: " + game.getRunnerLives());
            }
        }
        return false;
    }
}
