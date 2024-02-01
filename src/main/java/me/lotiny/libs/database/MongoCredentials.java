package me.lotiny.libs.database;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Getter
@RequiredArgsConstructor
public class MongoCredentials {

    @NotNull
    private final String uri;

    @NotNull
    private final String databaseName;

    public static MongoCredentials of(String uri, String databaseName) {
        return new MongoCredentials(uri, databaseName);
    }
}
