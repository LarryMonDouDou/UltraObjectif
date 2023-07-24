package eq.larry.dev.config.json;

import java.util.UUID;

public class ChallengerPlayer {
    private UUID uuid;

    private String reward;

    public ChallengerPlayer(UUID uuid, String reward) {
        this.uuid = uuid;
        this.reward = reward;
    }
}
