package it.polimi.ingsw.sagrada.game.base;

import it.polimi.ingsw.sagrada.game.playables.Token;
import it.polimi.ingsw.sagrada.game.playables.Window;
import it.polimi.ingsw.sagrada.game.rules.CellRule;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WindowParser {

    private static WindowParser instance;

    private static final String BASE_PATH = "res/json/window/";
    private static final int WINDOWS_PER_CARD = 2;


    private Iterator<JSONObject> picker;
    private static final Logger logger = Logger.getAnonymousLogger();

    private WindowParser() {
        JSONParser parser;
        parser = new JSONParser();
        JSONArray windowsArray;
        try {
            windowsArray = (JSONArray)parser.parse(new FileReader(BASE_PATH+"Windows.json"));
            picker = new Picker<JSONObject>(windowsArray).pickerIterator();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Something breaks in reading JSON file");
        } catch (ParseException e) {
            logger.log(Level.SEVERE, "JSON parser founds something wrong, check JSON file");
        }
    }

    public static WindowParser getInstance() {
        if(instance==null) instance = new WindowParser();
        return instance;
    }

    public boolean isWindowsLeft() {
        return picker.hasNext();
    }

    public List<Window> generateWindowCard() {
        List<Window> windowCard = new ArrayList<>();
        if(picker.hasNext()) {
            JSONObject card=picker.next();
            for(int i=0; i<WINDOWS_PER_CARD; i++) {
                JSONArray windows=(JSONArray)card.get("windows");
                JSONObject specificWindow=(JSONObject)windows.get(i);
                String name=(String)specificWindow.get("name");
                int numTokens=((Long)specificWindow.get("token")).intValue();
                List<Token> tokens = new ArrayList<>();
                for(int j=0; j<numTokens; j++) {
                    tokens.add(new Token());
                }
                Cell[][] cells=createCellMatrix((JSONArray)specificWindow.get("cells"));

                windowCard.add(new Window(name, cells, tokens));
            }
        }

        return windowCard;
    }

    private Cell[][] createCellMatrix(JSONArray cells) {
        Cell[][] cellMatrix = new Cell[4][5];

        int index=0;
        int numCellWithConstraint=cells.size();
        boolean constraintLeft=true;

        JSONObject singleCell;
        int nextX=-1;
        int nextY=-1;
        String constraint="0";

        if(numCellWithConstraint!=0) {
            singleCell=(JSONObject) cells.get(index);
            nextX=((Long)singleCell.get("x")).intValue();
            nextY=((Long)singleCell.get("y")).intValue();
            constraint=(String)singleCell.get("constraint");
            index++;
        }
        else constraintLeft=false;

        for(int i=0; i<cellMatrix.length; i++) { //row
            for(int j=0; j<cellMatrix[0].length; j++) { //column
                if(constraintLeft&&(i==nextY && j==nextX)) {
                    if(49<=(int)constraint.charAt(0)&&(int)constraint.charAt(0)<=54) { //if string is a number
                        cellMatrix[i][j]=new Cell(CellRule.builder().setNumberConstraint((int)constraint.charAt(0)-48).build());
                    }
                    else {
                        cellMatrix[i][j]=new Cell(CellRule.builder().setColorConstraint(Colors.stringToColor(constraint)).build());
                    }

                    if(index<numCellWithConstraint) {
                        singleCell=(JSONObject) cells.get(index);
                        nextX=((Long)singleCell.get("x")).intValue();
                        nextY=((Long)singleCell.get("y")).intValue();
                        constraint=(String)singleCell.get("constraint");
                        index++;
                    }
                    else constraintLeft=false;
                }
                else {
                    cellMatrix[i][j]=new Cell();
                }
            }
        }

        return cellMatrix;
    }
}
