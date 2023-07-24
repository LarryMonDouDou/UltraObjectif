package eq.larry.dev;

import eq.larry.dev.config.json.ChallengerPlayer;

import java.util.HashMap;
import java.util.UUID;

public class ChallengerManager {
    private final HashMap<UUID, ChallengerPlayer> challengerPlayers = new HashMap<>();

    public HashMap<UUID, ChallengerPlayer> getChallengerPlayers() {
        return this.challengerPlayers;
    }
}
