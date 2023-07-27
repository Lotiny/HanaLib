package me.lotiny.libs.scoreboard;

import lombok.Getter;
import me.lotiny.libs.chat.CC;
import me.lotiny.libs.HanaLib;
import me.lotiny.libs.scoreboard.event.BoardCreateEvent;
import me.lotiny.libs.scoreboard.scoreboard.Board;
import me.lotiny.libs.scoreboard.scoreboard.BoardAdapter;
import me.lotiny.libs.scoreboard.scoreboard.BoardEntry;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static me.lotiny.libs.scoreboard.AetherOptions.defaultOptions;

@Getter
public class Aether {

    private BoardAdapter adapter;
    private final AetherOptions options;

    public Aether(BoardAdapter adapter, AetherOptions options) {
        this.options = options;

        setAdapter(adapter);
        run();
    }

    public Aether(BoardAdapter adapter) {
        this(adapter, defaultOptions());
    }

    public Aether() {
        this(null, defaultOptions());
    }

    private void run() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (adapter == null) {
                    return;
                }

                for (Player player : Bukkit.getOnlinePlayers()) {
                    Board board = Board.getByPlayer(player);
                    if (board != null) {
                        List<String> scores = adapter.getScoreboard(player, board);
                        List<String> translatedScores = new ArrayList<>();
                        if (scores == null) {
                            if (!board.getEntries().isEmpty()) {
                                for (BoardEntry boardEntry : board.getEntries()) {
                                    boardEntry.remove();
                                }
                                board.getEntries().clear();
                            }
                            continue;
                        }
                        for (String line : scores) {
                            translatedScores.add(CC.translate(line));
                        }
                        if (!options.scoreDirectionDown()) {
                            Collections.reverse(scores);
                        }

                        Scoreboard scoreboard = board.getScoreboard();
                        Objective objective = board.getObjective();
                        if (!(objective.getDisplayName().equals(adapter.getTitle(player)))) {
                            objective.setDisplayName(CC.translate(adapter.getTitle(player)));
                        }

                        outer:
                        for (int i = 0; i < scores.size(); i++) {
                            String text = scores.get(i);
                            int position;
                            if (options.scoreDirectionDown()) {
                                position = 15 - i;
                            } else {
                                position = i + 1;
                            }

                            Iterator<BoardEntry> iterator = new ArrayList<>(board.getEntries()).iterator();
                            while (iterator.hasNext()) {
                                BoardEntry boardEntry = iterator.next();
                                Score score = objective.getScore(boardEntry.getKey());

                                if (score != null && boardEntry.getText().equals(CC.translate(text))) {
                                    if (score.getScore() == position) {
                                        continue outer;
                                    }
                                }
                            }

                            int positionToSearch = options.scoreDirectionDown() ? 15 - position : position - 1;

                            iterator = board.getEntries().iterator();
                            while (iterator.hasNext()) {
                                BoardEntry boardEntry = iterator.next();
                                int entryPosition = scoreboard.getObjective(DisplaySlot.SIDEBAR).getScore(boardEntry.getKey()).getScore();

                                if (!options.scoreDirectionDown()) {
                                    if (entryPosition > scores.size()) {
                                        iterator.remove();
                                        boardEntry.remove();
                                    }
                                }
                            }

                            BoardEntry entry = board.getByPosition(positionToSearch);
                            if (entry == null) {
                                new BoardEntry(board, text).send(position);
                            } else {
                                entry.setText(text).setup().send(position);
                            }

                            if (board.getEntries().size() > scores.size()) {
                                iterator = board.getEntries().iterator();
                                while (iterator.hasNext()) {
                                    BoardEntry boardEntry = iterator.next();
                                    if ((!translatedScores.contains(boardEntry.getText())) || Collections.frequency(board.getBoardEntriesFormatted(), boardEntry.getText()) > 1) {
                                        iterator.remove();
                                        boardEntry.remove();
                                    }
                                }
                            }
                        }

                        adapter.onScoreboardUpdate(player, scoreboard);
                        player.setScoreboard(scoreboard);
                    }
                }
            }
        }.runTaskTimerAsynchronously(HanaLib.getInstance(), 20L, 2L);
    }

    public void setAdapter(BoardAdapter adapter) {
        this.adapter = adapter;
        for (Player player : Bukkit.getOnlinePlayers()) {
            Board board = Board.getByPlayer(player);
            if (board != null) Board.getBoards().remove(board);
            Bukkit.getPluginManager().callEvent(new BoardCreateEvent(new Board(player, this, options), player));
        }
    }
}