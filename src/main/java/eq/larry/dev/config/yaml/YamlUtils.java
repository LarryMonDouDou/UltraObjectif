package eq.larry.dev.config.yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class YamlUtils {
    private Plugin plugin;

    private File confDataFile;

    private YamlConfiguration confDataConfig;

    private Utf8YamlConfiguration utf8;

    private String pathName = null;

    public static Charset UTF8_CHARSET = Charset.forName("UTF-8");

    public YamlUtils(Plugin plugin, String name, String pathName) {
        this.pathName = pathName;
        this.plugin = plugin;
        this.confDataFile = new File(plugin.getDataFolder(), name);
        this.confDataConfig = YamlConfiguration.loadConfiguration(this.confDataFile);
        this.utf8 = new Utf8YamlConfiguration();
        loadYml(false);
    }

    public YamlUtils(Plugin plugin, String name, String pathName, boolean saveRessource) {
        this.plugin = plugin;
        this.confDataFile = new File(plugin.getDataFolder(), name);
        this.confDataConfig = YamlConfiguration.loadConfiguration(this.confDataFile);
        this.utf8 = new Utf8YamlConfiguration();
        loadYml(saveRessource);
    }

    public YamlUtils(Plugin plugin, String name) {
        this.plugin = plugin;
        this.confDataFile = new File(plugin.getDataFolder(), name);
        this.confDataConfig = YamlConfiguration.loadConfiguration(this.confDataFile);
        this.utf8 = new Utf8YamlConfiguration();
        loadYml(false);
    }

    public YamlUtils(Plugin plugin, String name, boolean saveRessource) {
        this.plugin = plugin;
        this.confDataFile = new File(plugin.getDataFolder(), name);
        this.confDataConfig = YamlConfiguration.loadConfiguration(this.confDataFile);
        this.utf8 = new Utf8YamlConfiguration();
        loadYml(saveRessource);
    }

    public FileConfiguration getConfig() {
        return (FileConfiguration)this.confDataConfig;
    }

    public File getFile() {
        return this.confDataFile;
    }

    public void save() {
        try {
            this.confDataConfig.save(this.confDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadYml(boolean saveRessource) {
        if (!this.confDataFile.exists())
            if (this.pathName != null) {
                if (!(new File(this.plugin.getDataFolder() + this.pathName)).exists()) {
                    this.confDataFile.getParentFile().mkdirs();
                    if (saveRessource)
                        this.plugin.saveResource(this.confDataFile.getName(), false);
                }
            } else {
                this.confDataFile.getParentFile().mkdirs();
                if (saveRessource)
                    this.plugin.saveResource(this.confDataFile.getName(), false);
            }
        this.confDataConfig = new YamlConfiguration();
        try {
            if (this.pathName != null) {
                this.confDataFile.renameTo(new File(this.plugin.getDataFolder() + this.pathName));
                this.confDataFile.delete();
                this.confDataFile = new File(this.plugin.getDataFolder() + this.pathName);
            }
            if (this.confDataFile.exists()) {
                InputStream inputstream = new FileInputStream(this.confDataFile);
                this.utf8.load(inputstream);
                this.confDataConfig = this.utf8;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR LOADING FILE");
        }
    }
}