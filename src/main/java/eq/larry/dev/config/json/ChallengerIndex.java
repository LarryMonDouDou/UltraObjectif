package eq.larry.dev.config.json;

import java.util.UUID;

public class ChallengerIndex extends JSONFileUtils {
    public Object put(String key, Object value) {
        Object k = super.put(key, value);
        String r = (String)value;
        String uuid = key;
        ChallengerPlayer cp = new ChallengerPlayer(UUID.fromString(uuid), r);
        return k;
    }
}
