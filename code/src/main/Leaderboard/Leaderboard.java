package main.Leaderboard;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Leaderboard {
    private static class Entry {
        String username;
        long time;

        Entry(String username, long time) {
            this.username = username;
            this.time = time;
        }
    }

    private ArrayList<Entry> entries;
    private static final String LEADERBOARD_FILE = "LBData/leaderboard.txt";

    public Leaderboard() {
        entries = new ArrayList<>();
        loadLeaderboard();
    }

    public void addEntry(String username, long time) {
        entries.add(new Entry(username, time));
        Collections.sort(entries, Comparator.comparingLong(e -> e.time));
        saveLeaderboard();
    }

    public String getLeaderboard() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < entries.size(); i++) {
            Entry entry = entries.get(i);
            sb.append((i + 1)).append(". ").append(entry.username).append(" - ").append(entry.time / 1000).append(" seconds\n");
        }
        return sb.toString();
    }

    public void clearLeaderboard() {
        entries.clear();
        saveLeaderboard();
    }

    private void saveLeaderboard() {
        File directory = new File("LBData");
        if (!directory.exists()) {
            directory.mkdir();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LEADERBOARD_FILE))) {
            for (Entry entry : entries) {
                writer.write(entry.username + "," + entry.time);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadLeaderboard() {
        File file = new File(LEADERBOARD_FILE);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(LEADERBOARD_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String username = parts[0];
                    long time = Long.parseLong(parts[1]);
                    entries.add(new Entry(username, time));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}