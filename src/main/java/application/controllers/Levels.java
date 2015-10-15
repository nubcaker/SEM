package application.controllers;

import application.Main;
import application.core.aliens.Alien;
import application.core.Player;
import application.core.projectiles.Projectile;
import application.core.upgrades.Upgrade;
import org.newdawn.slick.Color;
import org.newdawn.slick.Input;
import org.newdawn.slick.Image;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.util.ArrayList;

/**
 * Controller class for Levels.
 * @author Thomas Oomens
 */
@SuppressWarnings({
        "checkstyle:magicnumber",
        "checkstyle:visibilitymodifier",
        "checkstyle:linelength"
})
public class Levels extends BasicGameState {
    protected Main tMain;
    protected int tId;
    protected Image tBackground;
    protected String tBackgroundString = "background.jpg";
    protected boolean pause = false;

    /**
     * Constructor method for this controller.
     * @param id given ID for the controller.
     */
    public Levels(int id) {
        tId = id;
    }

    /**
     * Init method for Levels.
     * @param container GameContainer used by the program.
     * @param game being played at the moment.
     * @throws SlickException
     */
    @Override
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {

        tMain = (Main) game;
        tBackground = new Image("src/main/java/application/images/" + tBackgroundString);

    }

    /**
     * Render method for Levels.
     * @param container GameContainer being used at the moment.
     * @param game being played at the moment.
     * @param g Graphics used by the program.
     * @throws SlickException
     */
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
            throws SlickException {
        container.setPaused(pause);
        if (!pause) {
            for(Player p : tMain.getGame().getPlayerController().getPlayers()) {
                int lives = p.getHealth();
                tBackground.draw(0, 0, container.getWidth(), container.getHeight());
                g.setColor(Color.white);
                //Display Score in top left.
                g.drawString(("SCORE: " + Integer.toString(tMain.getGame().getScore())), 140, 50);
                //Display Lives in top right.
                g.drawString("LIVES: ", container.getWidth() - 500, 50);
                for (int i = 1; i <= lives; i++) {
                    p.getImage().draw(container.getWidth() - 500 + i * 110, 50, p.getWidth(), p.getHeight());
                }

                //Draw all aliens and its upgrades
                for (Alien alien : tMain.getGame().getLevel().getAliens()) {
                    if (!alien.isDead()) {
                        (alien.getImage()).draw(alien.getX(), alien.getY(), alien.getWidth(), alien.getHeight());
                    }
                    drawProjectiles(alien.getProjectiles());
                    drawUpgrades(alien.getUpgrades());
                }

                for (Player x : tMain.getGame().getPlayerController().getPlayers()) {
                    System.out.println(tMain.getGame().getPlayerController().getPlayers().size());
                    //Draw the player
                    x.getImage().draw(x.getX(), x.getY(), x.getWidth(), x.getHeight());
                    drawProjectiles(x.getProjectiles());
                }

                }

        } else {
            g.drawString("PAUSED", container.getWidth() / 2, container.getHeight() / 2);
        }
    }

    /**
     * Update method for Levels.
     * @param container GameContainer used by the program.
     * @param game being played at the moment.
     * @param delta an integer value.
     * @throws SlickException
     */
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta)
            throws SlickException {
        if (!pause) {
            tMain.getGame().update();
            if (tMain.getGame().hasWon()) {
                game.enterState(2, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
            if (tMain.getGame().hasLost()) {
                game.enterState(3, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
            if (tMain.getGame().isNextLevel()) {
                game.enterState(7);
            }
            if (!tMain.getGame().getLevel().getBackground().equals(tBackgroundString)) {
                tBackgroundString = tMain.getGame().getLevel().getBackground();
                tBackground = new Image("src/main/java/application/images/" + tBackgroundString);
            }
        }

    }

    /**
     * Getter method for the ID of the Controller.
     * @return the ID of the Controller.
     */
    @Override
    public int getID() {
        return tId;
    }

    /**
     * Method to check whether a key is pressed.
     * @param key integer value for the key.
     * @param c character value for the key.
     */
    public void keyPressed(int key, char c) {
            switch (key) {
                case Input.KEY_LEFT:
                    tMain.getGame().getPlayerController().getPlayers().get(0).leftArrowPressed(true);
                    break;
                case Input.KEY_RIGHT:
                    tMain.getGame().getPlayerController().getPlayers().get(0).rightArrowPressed(true);
                    break;
                case Input.KEY_UP:
                    tMain.getGame().getPlayerController().getPlayers().get(0).fireButtonPressed(true);
                    break;
                case Input.KEY_ESCAPE:
                    pause = !pause;
                    break;
                default:
                    break;
            }
        if (tMain.getGame().getPlayerController().getPlayers().size() > 1 ) {
            switch (key) {
                case Input.KEY_A:
                    tMain.getGame().getPlayerController().getPlayers().get(1).leftArrowPressed(true);
                    break;
                case Input.KEY_D:
                    tMain.getGame().getPlayerController().getPlayers().get(1).rightArrowPressed(true);
                    break;
                case Input.KEY_W:
                    tMain.getGame().getPlayerController().getPlayers().get(1).fireButtonPressed(true);
                    break;
            }
        }
    }



    /**
     * Method to check whether a key is released.
     * @param key integer value for the key.
     * @param c character value for the key.
     */
    public void keyReleased(int key, char c) {
            switch (key) {
                case Input.KEY_LEFT:
                    tMain.getGame().getPlayerController().getPlayers().get(0).leftArrowPressed(false);
                    break;
                case Input.KEY_RIGHT:
                    tMain.getGame().getPlayerController().getPlayers().get(0).rightArrowPressed(false);
                    break;
                case Input.KEY_UP:
                    tMain.getGame().getPlayerController().getPlayers().get(0).fireButtonPressed(false);
                    break;
                default:
                    break;
            }
        if (tMain.getGame().getPlayerController().getPlayers().size() > 1) {
            switch (key) {
                case Input.KEY_A:
                    tMain.getGame().getPlayerController().getPlayers().get(1).leftArrowPressed(false);
                    break;
                case Input.KEY_D:
                    tMain.getGame().getPlayerController().getPlayers().get(1).rightArrowPressed(false);
                    break;
                case Input.KEY_W:
                    tMain.getGame().getPlayerController().getPlayers().get(1).fireButtonPressed(false);
                    break;
            }
        }


    }

    /**
     * Method to spawn in-game projectiles.
     * @param projectiles to draw in the game.
     */
    public void drawProjectiles(ArrayList<Projectile> projectiles) {
        for (Projectile projectile: projectiles) {
            projectile.getImage().draw(projectile.getX(), projectile.getY(), projectile.getWidth(), projectile.getHeight());
        }
    }

    /**
     * Method to spawn in-game upgrades.
     * @param upgrades to draw in the game.
     */
    private void drawUpgrades(ArrayList<Upgrade> upgrades) {
        for (Upgrade u: upgrades) {
            if (u.toDraw()) {
                (u.getImage()).draw(u.getX(), u.getY(), u.getWidth(), u.getHeight());
            }
        }
    }
}
