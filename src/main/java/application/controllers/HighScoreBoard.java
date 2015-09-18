package application.controllers;

import application.Main;
import org.newdawn.slick.*;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;

/**
 * Created by Daphne van Tetering on 17-9-2015.
 */
public class HighScoreBoard extends BasicGameState {
    protected Main tMain;
    protected int tId;
    protected Image tBackground;
    protected String tBackgroundString = "moving.jpg";
    protected boolean tPause = false;


    public HighScoreBoard(int id) {
        tId = id;
    }



    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        tMain = (Main) stateBasedGame;
        tBackground = new Image("src/main/java/application/images/"+ tBackgroundString);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        gameContainer.setPaused(tPause);
        if(!tPause) {
            tBackground.draw(0, 0, gameContainer.getWidth(), gameContainer.getHeight());

            graphics.setColor(Color.white);
            graphics.drawString("HIGH SCORE BOARD", 600, 100);

            ArrayList<Integer> list = tMain.getGame().getHighScoreManager().getScores();
            int x = 400;
            int y = 300;
            int j = 1;

            for (int i = 0; i < list.size() - 1; i++ ) {
                    graphics.drawString(j+ ". " + list.get(i).toString() + "\n", x,y);
                    y = y + 25;
                    j = j + 1;

                }

            }

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }

    @Override
    public int getID() {
        return tId;
    }
}
