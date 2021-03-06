package application.core;

import application.Main;
import application.controllers.PlayerController;
import org.w3c.dom.NodeList;
import application.core.aliens.BigAlien;
import application.core.aliens.SmallAlien;
import application.core.aliens.MothershipAlien;
import application.core.aliens.MiniAlien;
import application.core.aliens.FinalBoss;
import application.core.aliens.Alien;
import application.core.aliens.DaphnalienBoss;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

/**
 * Class for LevelFactory.
 * @author Thomas Oomens
 */
@SuppressWarnings({
        "checkstyle:visibilitymodifier",
        "checkstyle:methodlength",
        "linelength"
})
public final class LevelFactory {
    protected NodeList tLevels;
    private static LevelFactory tUniqueFactory;
    protected static int tScreenWidth;
    protected static int tScreenHeight;

    /**
     * Constructor for the LevelFactory.
     * @param width width of the game.
     * @param height height of the game.
     */
    private  LevelFactory(int width, int height) {
        try {
            tScreenWidth = width;
            tScreenHeight = height;
            URL fileURL = Main.class.getResource("/levels/levels.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder;
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fileURL.toURI().toString());
            doc.getDocumentElement().normalize();

            //Start xml parsing
            tLevels = doc.getElementsByTagName("level");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the LevelFactory belonging to the level.
     * @return the LevelFactory.
     */
    public static synchronized LevelFactory getFactory() {
        if (tUniqueFactory == null) {
            tUniqueFactory = new LevelFactory(tScreenWidth, tScreenHeight);
        }
        return tUniqueFactory;
    }

    /**
     * Build the parsed Level.
     * @param levelNumber the number belonging to the Level.
     * @param players the amount of players.
     * @param controller the PlayerController.
     * @return the built Level.
     */
    public Level buildLevel(int levelNumber, int players, PlayerController controller) {
        Level level = new Level(players, controller);

        Element levelName = (Element) tLevels.item(levelNumber);
        try {
            URL fileURL = Main.class.getResource("/levels/" + levelName.getTextContent() + ".xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder;
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fileURL.toURI().toString());
            doc.getDocumentElement().normalize();
            Element levelXml = doc.getDocumentElement();

            //Set aliens
            ArrayList<Alien> aliens = loadAliens(levelXml);
            level.addAliens(aliens);
            //Set background music
            if (levelXml.getElementsByTagName("background").getLength() != 0) {
                level.setBackground(levelXml.getElementsByTagName("background").item(0).getTextContent());
            }

            //Set Story
            if (levelXml.getElementsByTagName("story").getLength() != 0) {
                level.setStoryLine(levelXml.getElementsByTagName("story").item(0).getTextContent());
            }

            //Set Music
            if (levelXml.getElementsByTagName("music").getLength() != 0) {
                level.setMusic(levelXml.getElementsByTagName("music").item(0).getTextContent());
            }

            //Set Theme
            if (levelXml.getElementsByTagName("theme").getLength() != 0) {
                level.setTheme(levelXml.getElementsByTagName("theme").item(0).getTextContent());
            }

            //Set level time
            if (levelXml.getElementsByTagName("time").getLength() != 0) {
                level.setTime(Integer.parseInt(levelXml.getElementsByTagName("time").item(0).getTextContent()));
                System.out.println(levelXml.getElementsByTagName("time").item(0).getTextContent());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return level;
    }

    /**
     * Load all Aliens beloning to a Level.
     * @param level the read XMl for the Aliens in the Level.
     * @return an ArrayList of Aliens.
     */
    public ArrayList<Alien> loadAliens(Element level) {
        ArrayList<Alien> aliens = new ArrayList<Alien>();
        NodeList alienList = level.getElementsByTagName("alien");
        Alien alien = new Alien();
        for (int temp = 0; temp < alienList.getLength(); temp++) {
            Node node = alienList.item(temp);
            Element eElement = (Element) node;
            switch (eElement.getElementsByTagName("type").item(0).getTextContent()) {
                case "small":
                    alien = new SmallAlien();
                    break;
                case "mother":
                    alien = new MothershipAlien();
                    break;
                case "big":
                    alien = new BigAlien();
                    break;
                case "mini":
                    alien = new MiniAlien();
                    break;
                case "boss":
                    alien = new FinalBoss();
                    break;
                case "daphne":
                    alien = new DaphnalienBoss();
                    break;
                default: break;
            }
            alien.readXml(eElement);
            aliens.add(alien);
        }
        return aliens;
    }

    /**
     * Check whether a Level exists.
     * @param levelNumber the number belonging to the Level.
     * @return the boolean value.
     */
    public boolean levelExists(int levelNumber) {
        return (levelNumber < (tLevels.getLength()));
    }
}
