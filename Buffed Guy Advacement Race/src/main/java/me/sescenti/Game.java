package me.sescenti;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.advancement.Advancement;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.Objects;

public class Game {

    private Player boss;
    private double regenLvl = 5;
    private double strengthLvl = 4;
    private double hpLvl = 20;
    private double hasteLvl = 3;
    private double speedLvl = 4;
    private double hp = 20;
    private double atk = 1;
    private double spd = 0.1;
    private int runnerLives = 10;

    private boolean endAdv = false;
    private boolean witherAdv = false;

    private final String[] easyAdvs = {"story/mine_stone", "story/upgrade_tools", "story/smelt_iron", "story/obtain_armor", "story/lava_bucket",
            "story/iron_tools", "story/deflect_arrow", "story/form_obsidian", "story/mine_diamond", "story/enter_the_nether", "nether/return_to_sender",
            "nether/find_bastion", "nether/find_fortress", "nether/obtain_crying_obsidian", "nether/distract_piglin", "nether/ride_strider",
            "nether/obtain_blaze_rod", "nether/charge_respawn_anchor", "nether/brew_potion", "adventure/voluntary_exile", "adventure/spyglass_at_parrot",
            "adventure/kill_a_mob", "adventure/trade", "adventure/ol_betsy", "adventure/salvage_sherd", "adventure/avoid_vibration", "adventure/sleep_in_bed",
            "adventure/shoot_arrow", "adventure/summon_iron_golem", "adventure/whos_the_pillager_now", "adventure/walk_on_powder_snow_with_leather_boots",
            "husbandry/safely_harvest_honey", "husbandry/breed_an_animal", "husbandry/ride_a_boat_with_a_goat", "husbandry/tame_an_animal",
            "husbandry/make_a_sign_glow", "husbandry/fishy_business", "husbandry/tadpole_in_a_bucket", "husbandry/plant_seed", "husbandry/wax_on",
            "husbandry/tactical_fishing", "husbandry/wax_off", "husbandry/axolotl_in_a_bucket"};

    private final String[] medAdvs = {"story/shiny_gear", "story/enchant_item", "story/cure_zombie_villager","story/follow_ender_eye",
            "story/enter_the_end", "nether/obtain_ancient_debris", "nether/fast_travel", "nether/uneasy_alliance", "nether/get_wither_skull", "nether/ride_strider_in_overworld_lava",
            "nether/explore_nether", "adventure/honey_block_slide", "adventure/sniper_duel", "husbandry/silk_touch_nest", "adventure/read_power_of_chiseled_bookshelf",
            "adventure/trim_with_any_armor_pattern", "adventure/fall_from_world_height", "adventure/spyglass_at_ghast", "adventure/trade_at_world_height",
            "adventure/craft_decorated_pot_using_only_sherds", "adventure/play_jukebox_in_meadows", "adventure/bullseye", "husbandry/allay_deliver_item_to_player",
            "husbandry/silk_touch_nest", "husbandry/leash_all_frog_variants", "husbandry/kill_axolotl_target"};

    private final String[] hardAdvs = {"nether/summon_wither", "nether/create_beacon", "nether/all_potions", "end/kill_dragon", "end/dragon_egg", "end/enter_end_gateway",
            "end/elytra", "end/levitate" ,"adventure/hero_of_the_village", "adventure/totem_of_undying", "adventure/two_birds_one_arrow", "husbandry/obtain_netherite_hoe",
            "adventure/lightning_rod_with_villager_no_fire", "adventure/throw_trident", "adventure/kill_mob_near_sculk_catalyst", "adventure/trim_with_all_exclusive_armor_patterns",
        "adventure/spyglass_at_dragon", "adventure/very_very_frightening", "husbandry/allay_deliver_cake_to_note_block", "husbandry/froglights"};

    private final String[] expertAdvs = {"nether/netherite_armor", "nether/create_full_beacon", "nether/all_effects", "adventure/kill_all_mobs", "adventure/arbalistic",
            "adventure/adventuring_time", "husbandry/bred_all_animals", "husbandry/complete_catalogue", "husbandry/balanced_diet"};
    private Advancement[] advs = new Advancement[10];
    private boolean[] completed = new boolean[10];

    public void startGame(){
        if(boss == null) {
            for (int i = 0; i < 4; i++) {
                advs[i] = newEasyAdv();
            }
            for (int i = 4; i < 7; i++) {
                advs[i] = newMedAdv();
            }
            for (int i = 7; i < 9; i++) {
                advs[i] = newHardAdv();
            }
            for (int i = 9; i < 10; i++) {
                advs[i] = newExpertAdv();
            }

            //0 min
            boss = (Player) Bukkit.getOnlinePlayers().toArray()[(int) (Math.random() * Bukkit.getOnlinePlayers().size())];
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage("The boss is " + boss.getDisplayName());
            }
            System.out.println("[Boss vs Runners] The boss is " + boss.getDisplayName());

            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage("Your Advancements are:");
                for (Advancement a : advs) {
                    p.sendMessage(a.getKey().getKey());
                }
            }
            for (Advancement a : advs) {
                System.out.println("[Boss vs Runners] " + a.getKey().getKey());
            }
            //0 min
            System.out.println("0 minutes has passed");
            System.out.println(boss.getDisplayName() + " currently has:\nResistance V\nFire Res I");
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage("0 minutes has passed");
                p.sendMessage(boss.getDisplayName() + " currently has:\nResistance V\nFire Res I");
            }
            boss.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1200, 4, false, false));
            boss.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1000000, 0, false, false));
            //1 min
            Bukkit.getScheduler().runTaskLater(BuffGuyAdv.instance, () -> {
                System.out.println("1 minute has passed");
                System.out.println(boss.getDisplayName() + " currently has:\nResistance IV\nFire Res I\nHeath Boost IV\nStrength I\nSpeed I\nHaste I\nRegen I");
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage("1 minute has passed");
                    p.sendMessage(boss.getDisplayName() + " currently has:\nResistance IV\nFire Res I\nHeath Boost IV\nStrength I\nSpeed I\nHaste I\nRegen I");
                }
                boss.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
                boss.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1200, 3, false, false));
                Objects.requireNonNull(boss.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(hp + 4 * hpLvl / 5);
                Objects.requireNonNull(boss.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE)).setBaseValue(atk + 3 * strengthLvl / 4);
                Objects.requireNonNull(boss.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).setBaseValue(spd * Math.pow(1.1, speedLvl / 4));
                boss.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 2400, (int) (hasteLvl / 3 - 1), false, false));
                boss.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 1200, (int) (regenLvl / 5 - 1), false, false));
                //2 min
                Bukkit.getScheduler().runTaskLater(BuffGuyAdv.instance, () -> {
                    System.out.println("2 minutes has passed");
                    System.out.println(boss.getDisplayName() + " currently has:\nResistance III\nFire Res I\nHeath Boost VIII\nStrength I\nSpeed II\nHaste I\nRegen II");
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.sendMessage("2 minutes has passed");
                        p.sendMessage(boss.getDisplayName() + " currently has:\nResistance III\nFire Res I\nHeath Boost VIII\nStrength I\nSpeed II\nHaste I\nRegen II");
                    }
                    boss.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
                    boss.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1200, 2, false, false));
                    Objects.requireNonNull(boss.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(hp + 4 * 2 * hpLvl / 5);
                    Objects.requireNonNull(boss.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).setBaseValue(spd * Math.pow(1.1, 2 * speedLvl / 4));
                    boss.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 1200, (int) (2 * regenLvl / 5 - 1), false, false));
                    //3 min
                    Bukkit.getScheduler().runTaskLater(BuffGuyAdv.instance, () -> {
                        System.out.println("3 minutes has passed");
                        System.out.println(boss.getDisplayName() + " currently has:\nResistance II\nFire Res I\nHeath Boost XII\nStrength II\nSpeed II\nHaste II\nRegen III");
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.sendMessage("3 minutes has passed");
                            p.sendMessage(boss.getDisplayName() + " currently has:\nResistance II\nFire Res I\nHeath Boost XII\nStrength II\nSpeed II\nHaste II\nRegen III");
                        }
                        boss.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
                        boss.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1200, 1, false, false));
                        Objects.requireNonNull(boss.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(hp + 4 * 3 * hpLvl / 5);
                        Objects.requireNonNull(boss.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE)).setBaseValue(atk + 3 * 2 * strengthLvl / 4);
                        boss.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 2400, (int) (2 * hasteLvl / 3 - 1), false, false));
                        boss.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 1200, (int) (3 * regenLvl / 5 - 1), false, false));
                        //4 min
                        Bukkit.getScheduler().runTaskLater(BuffGuyAdv.instance, () -> {
                            System.out.println("4 minutes has passed");
                            System.out.println(boss.getDisplayName() + " currently has:\nResistance I\nFire Res I\nHeath Boost XVI\nStrength III\nSpeed III\nHaste II\nRegen IV");
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                p.sendMessage("4 minutes has passed");
                                p.sendMessage(boss.getDisplayName() + " currently has:\nResistance I\nFire Res I\nHeath Boost XVI\nStrength III\nSpeed III\nHaste II\nRegen IV");
                            }
                            boss.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
                            boss.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1200, 0, false, false));
                            Objects.requireNonNull(boss.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(hp + 4 * 4 * hpLvl / 5);
                            Objects.requireNonNull(boss.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE)).setBaseValue(atk + 3 * 3 * strengthLvl / 4);
                            Objects.requireNonNull(boss.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).setBaseValue(spd * Math.pow(1.1, 3 * speedLvl / 4));
                            boss.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 1200, (int) (4 * regenLvl / 5 - 1), false, false));
                            //5 min
                            Bukkit.getScheduler().runTaskLater(BuffGuyAdv.instance, () -> {
                                System.out.println("5 minutes has passed");
                                System.out.println(boss.getDisplayName() + " currently has:\nFire Res I\nHeath Boost XX\nStrength IV\nSpeed IV\nHaste III\nRegen V");
                                for (Player p : Bukkit.getOnlinePlayers()) {
                                    p.sendMessage("5 minutes has passed");
                                    p.sendMessage(boss.getDisplayName() + " currently has:\nFire Res I\nHeath Boost XX\nStrength IV\nSpeed IV\nHaste III\nRegen V");
                                }
                                boss.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
                                Objects.requireNonNull(boss.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(hp + 4 * hpLvl);
                                Objects.requireNonNull(boss.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE)).setBaseValue(atk + 3 * strengthLvl);
                                Objects.requireNonNull(boss.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).setBaseValue(spd * Math.pow(1.1, speedLvl));
                                boss.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 1000000, (int) (hasteLvl - 1), false, false));
                                boss.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 1000000, (int) (regenLvl - 1), false, false));
                            }, 1200);
                        }, 1200);
                    }, 1200);
                }, 1200);
            }, 1200);
        }
    }

    public void resetGame(){
        boss = null;
        regenLvl = 5;
        strengthLvl = 4;
        hpLvl = 10;
        hasteLvl = 3;
        speedLvl = 3;
        hp = 20;
        atk = 1;
        spd = 0.1;
        runnerLives = 10;
        Arrays.fill(advs, null);
        Arrays.fill(completed, false);
        for (Player p : Bukkit.getOnlinePlayers()) {
            for(PotionEffect e : p.getActivePotionEffects()){
                p.removePotionEffect(e.getType());
            }
            Objects.requireNonNull(p.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(hp);
            Objects.requireNonNull(p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE)).setBaseValue(atk);
            Objects.requireNonNull(p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).setBaseValue(spd);
        }
    }
    public void easyAdvNerf(){
        hpLvl -= 2;
        updateBossEffects();
    }

    public void medAdvNerf(){
        hpLvl -= 2;
        strengthLvl -= 0.5;
        updateBossEffects();
    }

    public void hardAdvNerf(){
        hpLvl -= 2;
        strengthLvl -= 1;
        regenLvl -= 1;
        speedLvl -= 1;
        hasteLvl -= 1;

        updateBossEffects();
    }

    public void expertAdvNerf(){
        hpLvl -= 2;
        strengthLvl -= 2;
        regenLvl -= 2;
        speedLvl -= 2;
        hasteLvl -= 1;
        updateBossEffects();
    }

    public Player getBoss(){
        return boss;
    }

    public Advancement[] getAdvs(){
        return advs;
    }

    public boolean[] getCompleted(){
        return completed;
    }

    public void setCompleted(int i){
        if(i < completed.length){
            completed[i] = true;
        }
    }

    public Advancement newEasyAdv(){
        int random = (int) (Math.random() * easyAdvs.length);
        Advancement temp = Bukkit.getAdvancement(NamespacedKey.minecraft(easyAdvs[random]));
        for (Advancement a : advs) {
            if(!(a == null)) {
                if (a.equals(temp)){
                    return newEasyAdv();
                }
            }
        }
        return temp;
    }

    public Advancement newMedAdv(){
        int random = (int) (Math.random() * medAdvs.length);
        Advancement temp = Bukkit.getAdvancement(NamespacedKey.minecraft(medAdvs[random]));
        for (Advancement a : advs) {
            if(!(a == null)) {
                if (a.equals(temp) ||
                        (endAdv &&
                                (Objects.equals(temp, Bukkit.getAdvancement(NamespacedKey.minecraft("story/follow_ender_eye"))) ||
                                        Objects.equals(temp, Bukkit.getAdvancement(NamespacedKey.minecraft("story/enter_the_end")))))) {
                    return newMedAdv();
                }
            }
        }
        if((Objects.equals(temp, Bukkit.getAdvancement(NamespacedKey.minecraft("story/follow_ender_eye"))) ||
                Objects.equals(temp, Bukkit.getAdvancement(NamespacedKey.minecraft("story/enter_the_end")))) && !endAdv){
            endAdv = true;
        }

        return temp;
    }

    public Advancement newHardAdv(){
        int random = (int) (Math.random() * hardAdvs.length);
        Advancement temp = Bukkit.getAdvancement(NamespacedKey.minecraft(hardAdvs[random]));
        for (Advancement a : advs) {
            if(a != null) {
                if (a.equals(temp) ||
                        (witherAdv &&
                                (Objects.equals(temp, Bukkit.getAdvancement(NamespacedKey.minecraft("nether/summon_wither"))) ||
                                        Objects.equals(temp, Bukkit.getAdvancement(NamespacedKey.minecraft("nether/create_beacon")))))) {
                    return newHardAdv();
                }
            }
        }
        assert temp != null;
        if((temp.equals(Bukkit.getAdvancement(NamespacedKey.minecraft("nether/summon_wither"))) ||
                temp.equals(Bukkit.getAdvancement(NamespacedKey.minecraft("nether/create_beacon")))) && !witherAdv){
            witherAdv = true;
        }
        return temp;
    }

    public Advancement newExpertAdv(){
        int random = (int) (Math.random() * expertAdvs.length);
        Advancement temp = Bukkit.getAdvancement(NamespacedKey.minecraft(expertAdvs[random]));
        for (Advancement a : advs) {
            if(!(a == null)) {
                if (a.equals(temp)){
                    return newExpertAdv();
                }
            }
        }
        return temp;
    }

    public int getRunnerLives(){
        return runnerLives;
    }
    public void setRunnerLives(int l){
        runnerLives = l;
    }

    public void completedAdv(){
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage("PLAYER COORDS REVEALED");
            if(boss.getLocation().getWorld().equals(p.getLocation().getWorld())) {
                boss.sendMessage("PLAYER " + p.getDisplayName() + " is " + distCalc(boss.getLocation(), p.getLocation()) + "m away");
            } else {
                switch(p.getLocation().getWorld().getEnvironment()){
                    case NORMAL:
                        boss.sendMessage("PLAYER" + p.getDisplayName() + " is in the OVERWORLD");
                        break;
                    case NETHER:
                        boss.sendMessage("PLAYER" + p.getDisplayName() + " is in the NETHER");
                        break;
                    case THE_END:
                        boss.sendMessage("PLAYER" + p.getDisplayName() + " is in the END");
                        break;
                }

            }
        }
    }

    private double distCalc(Location one, Location two){
        return Math.sqrt((two.getX() - one.getX()) * (two.getX() - one.getX()) + (two.getY() - one.getY()) * (two.getY() - one.getY()) + (two.getZ() - one.getZ()) * (two.getZ() - one.getZ()));
    }

    public void updateBossEffects(){
        for(PotionEffect p : boss.getActivePotionEffects()) {
            boss.removePotionEffect(p.getType());
        }
        Objects.requireNonNull(boss.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(hp + 4 * hpLvl);
        Objects.requireNonNull(boss.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE)).setBaseValue(atk + 3 * strengthLvl);
        Objects.requireNonNull(boss.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).setBaseValue(spd * Math.pow(1.1, speedLvl));
        boss.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 1000000, (int)(hasteLvl - 1), false, false));
        boss.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 1000000, (int)(regenLvl - 1), false, false));
    }
    public void displayBossEffects(){
        for(Player p : Bukkit.getOnlinePlayers()){
            p.sendMessage(boss.getDisplayName() + " currently has:\nFire Res I\nHeath Boost " + IntegerToRomanNumeral((int) hpLvl)
                    + "\nStrength " + IntegerToRomanNumeral((int) strengthLvl) + "\nSpeed " + IntegerToRomanNumeral((int) speedLvl) +
                    "\nHaste " + IntegerToRomanNumeral((int) hasteLvl) + "\nRegen " + IntegerToRomanNumeral((int) regenLvl) );
        }
    }

    public void playerDeathBuff(Player d){
        runnerLives --;

        hp += 2;
        atk ++;
        spd *= 1.03;
        updateBossEffects();

        boss.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 200, 1, false, false));

        for(Player p : Bukkit.getOnlinePlayers()){
            p.sendMessage("A player, " + d.getDisplayName() + ", has died.");
            if(runnerLives == 1){
                p.sendMessage("1 Runner Life left");
            }else if (runnerLives == 0){
                p.sendMessage("0 Runner Lives left \nThe boss has won.");
            } else {
                p.sendMessage(runnerLives + " Runner Lives left");
            }
        }

        System.out.println("A player, " + d.getDisplayName() + ", has died.");
        if(runnerLives == 1){
            System.out.println("1 Runner Life left");
        }else if (runnerLives == 0){
            System.out.println("0 Runner Lives left \nThe boss has won.");
        } else {
            System.out.println(runnerLives + " Runner Lives left");
        }
    }

    public String IntegerToRomanNumeral(int input) {
        if(input == 0){
            return "0";
        }

        if (input < 1 || input > 3999)
            return "Invalid Roman Number Value";
        StringBuilder s = new StringBuilder();
        while (input >= 1000) {
            s.append("M");
            input -= 1000;        }
        while (input >= 900) {
            s.append("CM");
            input -= 900;
        }
        while (input >= 500) {
            s.append("D");
            input -= 500;
        }
        while (input >= 400) {
            s.append("CD");
            input -= 400;
        }
        while (input >= 100) {
            s.append("C");
            input -= 100;
        }
        while (input >= 90) {
            s.append("XC");
            input -= 90;
        }
        while (input >= 50) {
            s.append("L");
            input -= 50;
        }
        while (input >= 40) {
            s.append("XL");
            input -= 40;
        }
        while (input >= 10) {
            s.append("X");
            input -= 10;
        }
        while (input >= 9) {
            s.append("IX");
            input -= 9;
        }
        while (input >= 5) {
            s.append("V");
            input -= 5;
        }
        while (input >= 4) {
            s.append("IV");
            input -= 4;
        }
        while (input >= 1) {
            s.append("I");
            input -= 1;
        }
        return s.toString();
    }
}
