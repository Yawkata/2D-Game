package world.tile;

import game.GamePanel;
import world.WorldGenerator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TileManager {
    GamePanel gp;
    ArrayList<Tile> tile;

    WorldGenerator worldGenerator;

    public TileManager(GamePanel gp){
        this.gp = gp;
        tile = new ArrayList<>(10);
        worldGenerator = new WorldGenerator();
        getTileImage();
        loadMap(".\\assets\\world\\world_textures_placement.txt");
    }

    public void getTileImage(){
        try {
            tile.add(new Tile());
            tile.get(0).image = new ImageIcon(".\\assets\\world\\nature\\ground\\black.png").getImage();
            tile.add(new Tile());
            tile.get(1).image = new ImageIcon(".\\assets\\world\\nature\\ground\\grass.png").getImage();
            tile.add(new Tile());
            tile.get(2).image = new ImageIcon(".\\assets\\world\\nature\\tree_types\\wide_leaf_tree.png").getImage();
            tile.add(new Tile());
            tile.get(3).image = new ImageIcon(".\\assets\\world\\nature\\ground\\water.png").getImage();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath){
        worldGenerator.loadWorldFromFile(filePath);
    }

    public void draw(Graphics2D g2){

        for (int row = 0; row < gp.SCREEN_ROW; row++) {
            for (int col = 0; col < gp.SCREEN_COL; col++) {
                int tileValue = worldGenerator.getTileValue(row, col);

                // Draw textures based on the tile value
                while (tileValue > 0) {
                    int lastDigit = tileValue % 10;
                    g2.drawImage(tile.get(lastDigit).image, gp.camera.translateX(col * gp.OBJECT_SIZE),
                            gp.camera.translateY(row * gp.OBJECT_SIZE), gp.OBJECT_SIZE, gp.OBJECT_SIZE, null);
                    tileValue /= 10;
                }
            }
        }
    }
}
