package eq.larry.dev.config.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;

public class JSONFileUtils extends LinkedHashMap<String, Object> {
    private File indexFile;

    public void refreshFile() {
        Gson gson = (new GsonBuilder()).disableHtmlEscaping().create();

        try {
            FileWriter f = new FileWriter(this.indexFile);
            gson.toJson(this, f);
            f.flush();
            f.close();
        } catch (JsonIOException var3) {
            var3.printStackTrace();
        } catch (IOException var4) {
            var4.printStackTrace();
        }

    }

    public File getIndexFile() {
        return this.indexFile;
    }

    public void setIndexFile(File indexFile) {
        this.indexFile = indexFile;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof JSONFileUtils)) {
            return false;
        } else {
            JSONFileUtils other = (JSONFileUtils)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$indexFile = this.getIndexFile();
                Object other$indexFile = other.getIndexFile();
                if (this$indexFile == null) {
                    if (other$indexFile != null) {
                        return false;
                    }
                } else if (!this$indexFile.equals(other$indexFile)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof JSONFileUtils;
    }

    public int hashCode() {
        int result = 1;
        Object $indexFile = this.getIndexFile();
        result = result * 59 + ($indexFile == null ? 43 : $indexFile.hashCode());
        return result;
    }

    public String toString() {
        return "JSONFileUtils(indexFile=" + this.getIndexFile() + ")";
    }
}
