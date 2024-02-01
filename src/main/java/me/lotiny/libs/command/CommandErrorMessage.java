package me.lotiny.libs.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommandErrorMessage {

    NO_PERM("&cYou don't have permission to execute this command."),
    ONLY_PLAYER("&cOnly player can execute this command.");

    private String message;

    public void setMessage(String message) {
        this.message = message;
    }
}
