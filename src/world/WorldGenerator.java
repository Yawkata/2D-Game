package world;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WorldGenerator {
    private final List<List<Integer>> world;

    public WorldGenerator() {
        world = new ArrayList<>();
    }


    public int getWorldWidth() {
        return world.get(0).size();
    }

    public int getWorldHeight() {
        return world.size();
    }

    public int getTileValue(int row, int col) {
        if (row >= 0 && row < getWorldHeight() && col >= 0 && col < getWorldWidth()) {
            return world.get(row).get(col);
        }
        return -1;
    }

    public void loadWorldFromFile(String filePath) {
        try {
            Scanner scanner = new Scanner(new File(filePath));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                if (!line.isEmpty()) {
                    List<Integer> row = new ArrayList<>();
                    String[] values = line.split(",");

                    for (String value : values) {
                        row.add(Integer.parseInt(value.trim()));
                    }

                    world.add(row);
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
