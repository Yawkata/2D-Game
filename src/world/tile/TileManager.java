package world.tile;

import game.GamePanel;
import world.WorldGenerator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TileManager {
    public GamePanel gp;
    public ArrayList<Tile> tile;

    public WorldGenerator worldGenerator;

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
            tile.get(0).image = new ImageIcon(".\\assets\\world\\nature\\ground\\grass.png").getImage();

            tile.add(new Tile());
            tile.get(1).image = new ImageIcon(".\\assets\\world\\nature\\ground\\wall.png").getImage();
            tile.get(1).collision = true;

            tile.add(new Tile());
            tile.get(2).image = new ImageIcon(".\\assets\\world\\nature\\ground\\water.png").getImage();
            tile.get(2).collision = true;

            tile.add(new Tile());
            tile.get(3).image = new ImageIcon(".\\assets\\world\\nature\\ground\\earth.png").getImage();

            tile.add(new Tile());
            tile.get(4).image = new ImageIcon(".\\assets\\world\\nature\\tree_types\\wide_leaf_tree2.png").getImage();
            tile.get(4).collision = true;

            tile.add(new Tile());
            tile.get(5).image = new ImageIcon(".\\assets\\world\\nature\\ground\\sand.png").getImage();


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath){
        worldGenerator.loadWorldFromFile(filePath);
    }

    public void draw(Graphics2D g2){

        for (int row = 0; row < gp.maxWorldRow; row++) {
            for (int col = 0; col < gp.maxWorldCol; col++) {
                int tileValue = worldGenerator.getTileValue(row, col);

                /*
                if(col * gp.OBJECT_SIZE > gp.player.getX() - gp.camera.getX() &&
                        col * gp.OBJECT_SIZE < gp.player.getX() + gp.camera.getX() &&
                        row * gp.OBJECT_SIZE > gp.player.getY() - gp.camera.getY() &&
                        row * gp.OBJECT_SIZE < gp.player.getY() + gp.camera.getY()){




                 */


                g2.drawImage(tile.get(tileValue).image, gp.camera.translateX(col * gp.OBJECT_SIZE),
                        gp.camera.translateY(row * gp.OBJECT_SIZE), gp.OBJECT_SIZE, gp.OBJECT_SIZE, null);
                //}


            }

        }
    }
}
