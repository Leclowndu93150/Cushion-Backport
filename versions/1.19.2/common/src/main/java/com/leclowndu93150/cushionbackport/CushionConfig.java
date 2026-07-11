package com.leclowndu93150.cushionbackport;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CushionConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = Paths.get("config", "cushionbackport.json");
    private static CushionConfig instance;

    public boolean preventCushionToCushionMovement = false;

    public static CushionConfig get() {
        if (instance == null) {
            instance = load();
        }
        return instance;
    }

    private static CushionConfig load() {
        if (Files.exists(CONFIG_PATH)) {
            try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
                CushionConfig config = GSON.fromJson(reader, CushionConfig.class);
                if (config != null) {
                    return config;
                }
            } catch (IOException exception) {
                Cushionbackport.LOGGER.error("Failed to read config, using defaults", exception);
            }
        }

        CushionConfig config = new CushionConfig();
        config.save();
        return config;
    }

    public void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
                GSON.toJson(this, writer);
            }
        } catch (IOException exception) {
            Cushionbackport.LOGGER.error("Failed to write config", exception);
        }
    }
}
