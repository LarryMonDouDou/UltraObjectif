package eq.larry.dev.config.yaml;

import com.google.common.io.Files;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class Utf8YamlConfiguration extends YamlConfiguration {
    public static Charset UTF8_CHARSET = Charset.forName("UTF-8");

    public void load(InputStream stream) throws IOException, InvalidConfigurationException {
        Validate.notNull(stream, "Stream cannot be null");
        InputStreamReader reader = new InputStreamReader(stream, UTF8_CHARSET);
        StringBuilder builder = new StringBuilder();
        BufferedReader input = new BufferedReader(reader);
        try {
            String line;
            while ((line = input.readLine()) != null) {
                builder.append(line);
                builder.append('\n');
            }
        } finally {
            input.close();
        }
        loadFromString(builder.toString());
    }

    public void save(File file) throws IOException {
        Validate.notNull(file, "File cannot be null");
        Files.createParentDirs(file);
        String data = saveToString();
        FileOutputStream stream = new FileOutputStream(file);
        OutputStreamWriter writer = new OutputStreamWriter(stream, UTF8_CHARSET);
        try {
            writer.write(data);
        } finally {
            writer.close();
        }
    }
}

