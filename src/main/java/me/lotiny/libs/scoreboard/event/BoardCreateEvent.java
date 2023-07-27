package me.lotiny.libs.scoreboard.event;

import lombok.Getter;
import me.lotiny.libs.scoreboard.scoreboard.Board;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class BoardCreateEvent extends Event {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    private final Board board;
    private final Player player;

    public BoardCreateEvent(Board board, Player player) {
        this.board = board;
        this.player = player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
