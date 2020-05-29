package com.ihusker.spells.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class JsonStorage {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void write(Path path, Object object) {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW)) {
            GSON.toJson(object, bufferedWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <C> C read(Path path, Class<C> clazz) {
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)){
            return GSON.fromJson(reader, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
