package main.Images;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * this class represents a collection of puzzle pieces obtained from an image.
 */
public class Pieces extends Images {

    private final int difficulty;

    public Pieces(int num, int difficulty) throws IOException {
        super(num);
        this.difficulty = difficulty;
    }

    /**
     * splits the image into individual puzzle pieces based on the difficulty level
     * returns an array of BufferedImage representing the split puzzle pieces
     */
    public BufferedImage[] splitImage() {
        BufferedImage[] pieces = new BufferedImage[difficulty*difficulty];
        int chunkWidth = img.getWidth() / difficulty;
        int chunkHeight = img.getHeight() / difficulty;
        int count = 0;
        for (int x = 0; x < difficulty; x++)
            for (int y = 0; y < difficulty; y++) {
                pieces[count] = new BufferedImage(chunkWidth, chunkHeight, img.getType());
                Graphics2D gr = pieces[count++].createGraphics();
                gr.drawImage(img, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
                gr.dispose();
            }
        return pieces;
    }
}
