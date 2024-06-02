package main.DrawPuzzle;

import main.Images.Images;
import main.Images.Pieces;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * the puzzle class manages the logic for the puzzle game,
 * including initializing the puzzle pieces, handling movements,
 * and checking for a win condition
 */
public class Puzzle {
    private Images[] imgs;
    public BufferedImage[][] pieces;
    private int numImages;
    private int difficulty;
    private int difficultyMenor;
    private int level;
    public int noPiece;
    public ArrayList<Integer> orderPieces;
    public int[][] positionPieces;
    public int width;
    public int height;
    public boolean move = false;
    public int direction = -1;
    public int pieceMove = -1;
    public int Ym = 0;
    public int Xm = 0;

    /**
     * constructor for the puzzle class
     * initializes the puzzle with the specified difficulty
     */
    public Puzzle(int difficulty) {
        Images.existDirec();
        this.numImages = Images.getNumImages();
        this.difficulty = difficulty * difficulty;
        this.difficultyMenor = difficulty;
        this.level = 0;
        this.imgs = new Images[this.numImages];
        this.positionPieces = new int[this.difficulty][4];
        this.pieces = new BufferedImage[this.numImages][this.difficulty];
        this.orderPieces = new ArrayList<>();
        this.noPiece = (int)(Math.random() * (double)this.difficulty);
        this.setOrderPiece();
        this.setImages();
        this.width = this.pieces[this.level][0].getWidth();
        this.height = this.pieces[this.level][0].getHeight();
    }

    /**
     * gets the current difficulty of the puzzle
     */
    public int getDifficulty() {
        return this.difficulty;
    }

    /**
     * gets the current minor difficulty of the puzzle
     */
    public int getDifficultyMenor() {
        return this.difficultyMenor;
    }

    /**
     * sets the difficulty of the puzzle and reinitializes the puzzle pieces
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty * difficulty;
        this.difficultyMenor = difficulty;
        this.numImages = Images.getNumImages();
        this.imgs = new Images[this.numImages];
        this.pieces = new BufferedImage[this.numImages][this.difficulty];
        this.noPiece = (int)(Math.random() * (double)this.difficulty);
        this.positionPieces = new int[this.difficulty][4];
        this.resetImage();
        this.setImages();
        this.width = this.pieces[this.level][0].getWidth();
        this.height = this.pieces[this.level][0].getHeight();
    }

    /**
     * sets the current level (image) of the puzzle.
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * resets the image by randomizing the order of the pieces
     */
    public void resetImage() {
        this.noPiece = (int)(Math.random() * (double)this.difficulty);
        this.orderPieces.clear();
        this.setOrderPiece();
    }

    /**
     * solves the puzzle by arranging the pieces in the correct order
     */
    public void solve() {
        for(int i = 0; i < this.orderPieces.size(); ++i) {
            this.orderPieces.set(i, i);
        }
    }

    /**
     * sets the order of the pieces randomly
     */
    private void setOrderPiece() {
        while(this.orderPieces.size() < this.difficulty) {
            int posTemp = (int)(Math.random() * (double)this.difficulty);
            if (!this.existNum(posTemp)) {
                this.orderPieces.add(posTemp);
            }
        }
    }

    /**
     * checks if a number exists in the orderPieces list
     */
    private boolean existNum(int num) {
        Iterator<Integer> var2 = this.orderPieces.iterator();

        Integer orderPiece;
        do {
            if (!var2.hasNext()) {
                return false;
            }

            orderPiece = var2.next();
        } while(orderPiece != num);

        return true;
    }

    /**
     * draws the puzzle pieces on the given Graphics object
     */
    public void drawPieces(Graphics g) {
        int space = 5;
        int x = 20;
        int y = 50;
        int contX = 0;
        int contY = 0;

        for(int i = 0; i < this.difficulty; ++i) {
            if (contX > 0) {
                x += this.width + space;
                ++contX;
            } else {
                ++contX;
            }

            if (contY > this.difficultyMenor - 1) {
                y += this.height + space;
                x = 20;
                contX = 1;
                contY = 1;
            } else {
                ++contY;
            }

            if (i != this.noPiece) {
                if (this.move && i == this.pieceMove) {
                    int calPos;
                    switch (this.direction) {
                        case 0:
                            calPos = this.positionPieces[i][2] - this.Ym;
                            if (calPos < -50) {
                                this.Ym -= 50;
                                g.drawImage(this.pieces[this.level][this.orderPieces.get(i)], x, this.Ym, null);
                            } else {
                                g.drawImage(this.pieces[this.level][this.orderPieces.get(i)], x, y, null);
                                this.move = false;
                                this.direction = -1;
                                this.pieceMove = -1;
                                this.Ym = 0;
                            }
                            break;
                        case 1:
                            calPos = this.positionPieces[i][0] - this.Xm;
                            if (calPos > 50) {
                                this.Xm += 50;
                                g.drawImage(this.pieces[this.level][this.orderPieces.get(i)], this.Xm, y, null);
                            } else {
                                g.drawImage(this.pieces[this.level][this.orderPieces.get(i)], x, y, null);
                                this.move = false;
                                this.direction = -1;
                                this.pieceMove = -1;
                                this.Xm = 0;
                            }
                            break;
                        case 2:
                            calPos = this.positionPieces[i][2] - this.Ym;
                            if (calPos > 50) {
                                this.Ym += 50;
                                g.drawImage(this.pieces[this.level][this.orderPieces.get(i)], x, this.Ym, null);
                            } else {
                                g.drawImage(this.pieces[this.level][this.orderPieces.get(i)], x, y, null);
                                this.move = false;
                                this.direction = -1;
                                this.pieceMove = -1;
                                this.Ym = 0;
                            }
                            break;
                        case 3:
                            calPos = this.positionPieces[i][0] - this.Xm;
                            if (calPos < -50) {
                                this.Xm -= 50;
                                g.drawImage(this.pieces[this.level][this.orderPieces.get(i)], this.Xm, y, null);
                            } else {
                                g.drawImage(this.pieces[this.level][this.orderPieces.get(i)], x, y, null);
                                this.move = false;
                                this.direction = -1;
                                this.pieceMove = -1;
                                this.Xm = 0;
                            }
                    }
                } else {
                    g.drawImage(this.pieces[this.level][this.orderPieces.get(i)], x, y, null);
                }
            }

            this.positionPieces[i][0] = x;
            this.positionPieces[i][1] = x + this.width;
            this.positionPieces[i][2] = y;
            this.positionPieces[i][3] = y + this.height;
        }

    }

    /**
     * checks if the puzzle is in a win state
     */
    public boolean isWin() {
        boolean win = true;

        for(int i = 0; i < this.difficulty; ++i) {
            if (i != this.orderPieces.get(i)) {
                win = false;
                break;
            }
        }

        return win;
    }

    /**
     * initializes the images used in the puzzle
     */
    private void setImages() {
        for(int i = 0; i < this.numImages; ++i) {
            try {
                this.imgs[i] = new Pieces(i, this.difficultyMenor);
                BufferedImage[] imgsTemp = this.imgs[i].splitImage();

                for(int j = 0; j < this.difficulty; ++j) {
                    this.pieces[i][j] = imgsTemp[j];
                }
            } catch (IOException var4) {
                IOException ex = var4;
                System.err.println(ex.getMessage());
            }
        }
    }
}