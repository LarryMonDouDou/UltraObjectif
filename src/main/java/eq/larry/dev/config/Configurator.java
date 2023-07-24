package eq.larry.dev.config;

import eq.larry.dev.UltraObjectif;
import eq.larry.dev.color.ColorUtils;
import eq.larry.dev.config.yaml.YamlUtils;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

public class Configurator {
    public static String win;
    public static String begin;
    public static String end;
    public final YamlUtils yamlUtils = new YamlUtils(UltraObjectif.getInstance(), "config.yml");

    public void loadRewards() {
        this.yamlUtils.save();
        ConfigurationSection section = this.yamlUtils.getConfig().getConfigurationSection("rewards");
        if (section != null) {
            section.getKeys(false).forEach((key) -> {
                Reward reward = new Reward(key);
                reward.setCommand(this.yamlUtils.getConfig().getString("rewards." + key + ".command"));
            });
        }
    }

    public void loadObjectifs() {
        win = ColorUtils.translateColorCodes(this.yamlUtils.getConfig().getString("messages.win"));
        begin = ColorUtils.translateColorCodes(this.yamlUtils.getConfig().getString("messages.begin"));
        end = ColorUtils.translateColorCodes(this.yamlUtils.getConfig().getString("messages.end"));
        ConfigurationSection section = this.yamlUtils.getConfig().getConfigurationSection("objectifs");
        if (section != null) {
            section.getKeys(false).forEach((key) -> {
                int data = -1;
                String materialName = this.yamlUtils.getConfig().getString("objectifs." + key + ".material");
                Material material;
                if (materialName.contains(":")) {
                    String[] split = materialName.split(":");
                    material = Material.getMaterial(split[0]);
                    data = Integer.parseInt(split[1]);
                } else {
                    material = Material.getMaterial(materialName);
                }

                int amount = this.yamlUtils.getConfig().getInt("objectifs." + key + ".amount");
                String name = this.yamlUtils.getConfig().getString("objectifs." + key + ".name");
                String description = ColorUtils.translateColorCodes(this.yamlUtils.getConfig().getString("objectifs." + key + ".description"));
                Reward reward = (Reward) Reward.getRewards().get(this.yamlUtils.getConfig().getString("objectifs." + key + ".reward"));
                String bossBarName = ColorUtils.translateColorCodes(this.yamlUtils.getConfig().getString("objectifs." + key + ".bossbar"));
                Integer seconds = null;
                if (this.yamlUtils.getConfig().contains("objectifs." + key + ".seconds")) {
                    seconds = this.yamlUtils.getConfig().getInt("objectifs." + key + ".seconds");
                }

                Objectif objectif = new Objectif(material, data, amount, name, reward, bossBarName, description, seconds);
                Objectif.getObjectifs().put(objectif.name, objectif);
            });
        }
        if (this.yamlUtils.getConfig().contains("currentObjectif")) {
            String currentObjectif = this.yamlUtils.getConfig().getString("currentObjectif");
            if (Objectif.getObjectifs().containsKey(currentObjectif)) {
                UltraObjectif.getInstance().setCurrentObjectif((Objectif)Objectif.getObjectifs().get(currentObjectif));
            }
        }

    }
}
