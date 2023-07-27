package me.lotiny.libs.scoreboard.scoreboard;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.List;

public interface BoardAdapter {

    String getTitle(Player player);

    List<String> getScoreboard(Player player, Board board);

    void onScoreboardUpdate(Player player, Scoreboard board);
}
