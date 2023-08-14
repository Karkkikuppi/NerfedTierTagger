package com.kevin.tiertagger;

import com.google.gson.JsonPrimitive;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public record TierList(List<List<List<JsonPrimitive>>> rankings, Map<String, PlayerInfo> players) {
    public record PlayerInfo(String name, String region, int points) {

    }

    public Map<UUID, String> getTiers() {
        HashMap<UUID, String> map = new HashMap<>();

        for (int tier = 0; tier < rankings.size(); tier++) {
            List<List<JsonPrimitive>> tierList = rankings.get(tier);
            for (List<JsonPrimitive> player : tierList) {
                UUID uuid = uuid(player.get(0).getAsString());
                boolean high = player.get(1).getAsByte() == 0;
                String tierString = "%cT%d".formatted(high ? 'H' : 'L', tier + 1);
                map.put(uuid, tierString);
            }
        }

        return map;
    }

    private static UUID uuid(String s) {
        long mostSig = Long.parseUnsignedLong(s.substring(0, 16), 16);
        long leastSig = Long.parseUnsignedLong(s.substring(16), 16);
        return new UUID(mostSig, leastSig);
    }
}