package me.lotiny.libs.utils;

import lombok.experimental.UtilityClass;
import me.lotiny.libs.HanaLib;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;

@UtilityClass
public class DownloadUtil {

    public void downloadMavenDependency(String repository, String groupId, String artifactId, String version) {
        File pluginFolder = HanaLib.getInstance().getDataFolder();
        File libsFolder = new File(pluginFolder, "libs");
        if (!libsFolder.exists()) {
            libsFolder.mkdir();
        }

        File file = new File(libsFolder, artifactId + "-" + version + ".jar");
        if (file.exists()) {
            loadJARFile(file);
        } else {
            BukkitUtil.log("&aStarting download " + file.getName() + "...");

            try {
                String jarUrl;
                if (repository != null) {
                    jarUrl = repository + "/" + groupId.replace('.', '/') + "/" + artifactId + "/" + version + "/" +
                            artifactId + "-" + version + ".jar";
                } else {
                    jarUrl = "https://repo.maven.apache.org/maven2/" + groupId.replace('.', '/') + "/" + artifactId + "/" + version + "/" +
                            artifactId + "-" + version + ".jar";
                }

                URL url = new URL(jarUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("GET");

                InputStream inputStream = connection.getInputStream();
                File outputFile = new File(libsFolder, artifactId + "-" + version + ".jar");
                OutputStream outputStream = Files.newOutputStream(outputFile.toPath());

                byte[] buffer = new byte[4096];
                int bytesRead;

                long startTime = System.currentTimeMillis();

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                inputStream.close();
                outputStream.close();

                long endTime = System.currentTimeMillis();
                long downloadDuration = endTime - startTime;

                BukkitUtil.log("&aDownload completed! &b" + formatDuration(downloadDuration) + "s");

                loadJARFile(file);
            } catch (IOException e) {
                BukkitUtil.log("&cFailed to download or load the JAR file: " + file.getName());
                e.printStackTrace();
            }
        }
    }

    private String formatDuration(long duration) {
        long millis = duration % 1000;
        long seconds = (duration / 1000) % 60;

        return String.format("%02d:%03d", seconds, millis);
    }

    private void loadJARFile(File file) {
        try {
            URLClassLoader classLoader = (URLClassLoader) DownloadUtil.class.getClassLoader();
            Method addURLMethod = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            addURLMethod.setAccessible(true);
            addURLMethod.invoke(classLoader, file.toURI().toURL());
        } catch (Exception e) {
            BukkitUtil.log("&cFailed to use the JAR file: " + file.getName());
            e.printStackTrace();
        }
    }
}
