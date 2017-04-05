
import java.io.Serializable;
import java.sql.*;
import java.util.*;
import org.json.JSONObject;
import org.json.JSONArray;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

@Named(value = "dbconnectionBean")
@SessionScoped
public class ConnectionBean implements Serializable {

    private Connection conn;
    private JSONArray everything = new JSONArray();
    private ArrayList<String> elements = new ArrayList<String>();
    private ArrayList<Element> elementObjs = new ArrayList<Element>();
    private JSONObject currentObj;
    private int currRow = 0;

    public ConnectionBean() {
        connect();
        JSONify();
    }

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://periodictable.cb2vg6rxwemx.us-west-2.rds.amazonaws.com:3306/cecs493ptable", "root", "Coconut12#");
            System.out.println("Database connection established...");
        } catch (Exception e) {
            System.err.println("Connection Error: " + e);
        }
    }

    //Puts all of the elements into JSON objects and places them in a JSON array
    public void JSONify() {
        long before = 0;
        long after = 0;
        long total = 0;
        try {
            Statement select = conn.createStatement();
            ResultSet rs = select.executeQuery("call sp_getallelements()");

            before = System.currentTimeMillis();
            while (rs.next()) {

                String elemName = rs.getString("Elementname");
                String atomSymb = rs.getString("AtomicSymbol");
                String atomNum = rs.getString("AtomicNumber");
                String mass = rs.getString("atomicmass");
                String groupNum = rs.getString("groupnumber");
                String period = rs.getString("Period");
                String state25 = rs.getString("stateofmatter25");
                String valences = rs.getString("valences");
                String elecConf = rs.getString("configuration");
                String density = rs.getString("Density");
                String series = rs.getString("series");
                String xCor = rs.getString("xCoord");
                String yCor = rs.getString("yCoord");

                JSONObject element = new JSONObject();
                element.put("elemName", elemName);
                element.put("atomSymb", atomSymb);
                element.put("atomNum", atomNum);
                element.put("mass", mass);
                element.put("groupNum", groupNum);
                element.put("period", period);
                element.put("state25", state25);
                element.put("valences", valences);
                element.put("elecConf", elecConf);
                element.put("density", density);
                element.put("series", series);
                element.put("xCor", xCor);
                element.put("yCor", yCor);

                everything.put(element);

            }
            for (int i = 1; i <= getLargestY(); i++) {
                for (int j = 1; j <= getLargestX(); j++) {
                    JSONObject json = getElementAt(j, i);
                    if (json != null) {
                        Element el = new Element("visible", json.getString("elemName"), json.getString("atomSymb"),
                                json.getString("atomNum"), json.getString("xCor"), json.getString("yCor"),
                                json.getString("mass"), json.getString("groupNum"), json.getString("period"),
                                json.getString("state25"), json.getString("valences"), json.getString("elecConf"),
                                json.getString("density"), json.getString("series"));
                        elementObjs.add(el);
                    } else {
                        elementObjs.add(new Element("invisible", "", "", "", "", "", "", "", "", "", "", "", "", ""));
                    }
                }
            }
            after = System.currentTimeMillis();
            total = after - before;
            System.out.println("Time: " + total);
        } catch (Exception e) {

        }
        System.out.println("\nData has been JSONified...\n");
    }

    public JSONArray getEverything() {
        return everything;
    }

    public JSONObject getElement(int i) {
        JSONObject elem = new JSONObject();
        try {
            elem = everything.getJSONObject(i);
        } catch (Exception e) {
        }
        return elem;
    }

    public String getName() {
        String value = "";
        try {
            value = currentObj.getString("elemName");
        } catch (Exception e) {
        }
        return value;
    }

    public String getSymbol() {
        String value = "";
        try {
            value = currentObj.getString("atomSymb");
        } catch (Exception e) {
        }
        return value;
    }

    public String getAtomNum() {
        String value = "";
        try {
            value = currentObj.getString("atomNum");
        } catch (Exception e) {
        }
        return value;
    }

    public String getMass(JSONObject jo) {
        String value = "";
        try {
            value = jo.getString("mass");
        } catch (Exception e) {
        }
        return value;
    }

    public String getGroup(JSONObject jo) {
        String value = "";
        try {
            value = jo.getString("groupNum");
        } catch (Exception e) {
        }
        return value;
    }

    public String getPeriod(JSONObject jo) {
        String value = "";
        try {
            value = jo.getString("period");
        } catch (Exception e) {
        }
        return value;
    }

    public String getDensity(JSONObject jo) {
        String value = "";
        try {
            value = jo.getString("density");
        } catch (Exception e) {
        }
        return value;
    }

    public String getSeries(JSONObject jo) {
        String value = "";
        try {
            value = jo.getString("series");
        } catch (Exception e) {
        }
        return value;
    }

    public String getState25(JSONObject jo) {
        String value = "";
        try {
            value = jo.getString("state25");
        } catch (Exception e) {
        }
        return value;
    }

    public String getValences(JSONObject jo) {
        String value = "";
        try {
            value = jo.getString("valences");
        } catch (Exception e) {
        }
        return value;
    }

    public String getElecConf(JSONObject jo) {
        String value = "";
        try {
            value = jo.getString("elecConf");
        } catch (Exception e) {
        }
        return value;
    }

    public int getX(JSONObject jo) {
        String value = "";
        int cor = 0;
        try {
            value = jo.getString("xCor");
        } catch (Exception e) {
        }
        cor = Integer.parseInt(value);
        return cor;
    }

    public int getY(JSONObject jo) {
        String value = "";
        int cor = 0;
        try {
            value = jo.getString("yCor");
        } catch (Exception e) {
        }
        cor = Integer.parseInt(value);
        return cor;
    }

    public boolean LSI(JSONObject jo) {
        boolean indicator = false;
        try {
            String series = jo.getString("series");
            if (series.equalsIgnoreCase("lanthanide")) {
                indicator = true;
            }
        } catch (Exception e) {
        }
        return indicator;
    }

    public boolean ASI(JSONObject jo) {
        boolean indicator = false;
        try {
            String series = jo.getString("series");
            if (series.equalsIgnoreCase("actinide")) {
                indicator = true;
            }
        } catch (Exception e) {
        }
        return indicator;
    }

    public JSONObject getElementAt(int x, int y) {
        try {
            for (int i = 0; i < everything.length(); i++) {
                JSONObject element = everything.getJSONObject(i);
                if ((getX(element) == x) && (getY(element) == y)) {
                    return element;
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    public int getLargestX() {
        int largest = 0;
        try {
            for (int i = 0; i < everything.length(); i++) {
                JSONObject element = everything.getJSONObject(i);
                if ((getX(element) > largest)) {
                    largest = getX(element);
                }
            }
        } catch (Exception e) {
        }
        return largest;
    }

    public int getLargestY() {
        int largest = 0;
        try {
            for (int i = 0; i < everything.length(); i++) {
                JSONObject element = everything.getJSONObject(i);
                if ((getY(element) > largest)) {
                    largest = getY(element);
                }
            }
        } catch (Exception e) {
        }
        return largest;
    }

    public ArrayList<Element> getNextRow() {
        if (currRow == getLargestY()) {
            currRow = 0;
        }
        ArrayList<Element> el = new ArrayList<Element>();
        for (int i = currRow * getLargestX(); i < currRow * getLargestX() + getLargestX(); i++) {
            el.add(elementObjs.get(i));
        }
        currRow++;
        return el;
    }
    public ArrayList<Element> getAllElements() {
        ArrayList<Element> all = new ArrayList<Element>();
        for(Element e: elementObjs) {
            if(!e.getName().equals("")) {
                all.add(e);
            }
        }
        return all;
    }
}
