
import java.io.Serializable;
import java.sql.PreparedStatement;
import javax.inject.Named;
import java.util.Random;
import java.sql.*;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;


@Named(value = "functionalityBean")
@SessionScoped
public class FunctionalityBean implements Serializable {
    
    private String selectedElemName;
    private String selectedIsotopeName;
    private String isotopeAtomNum;

    public String getIsotopeAtomNum() {
        return isotopeAtomNum;
    }

    public String getSelectedIsotopeName() {
        return selectedIsotopeName;
    }

    public void setSelectedIsotopeName(String selectedIsotopeName) {
        this.selectedIsotopeName = selectedIsotopeName;
    }

    public String getSelectedElemName() {
        return selectedElemName;
    }

    public void setSelectedElemName(String selectedElemName) {
        this.selectedElemName = selectedElemName;
    }

    private Connection conn;
    private String elemsubName, elemsubSymbol, elemsubNum, elemsubMass, elemsubGroup, elemsubPeriod, elemsubState, elemsubValence, elemsubConfig, elemsubDensity, elemsubSeries, elemsubConfiguration;
    private String isotopeSymbol, isotopeName, isotopeNumber, isotopeMass, isotopeComposition, isotopeWeight, isotopeAbundance;
    

    public String getIsotopeSymbol() {
        return isotopeSymbol;
    }

    public void setIsotopeSymbol(String isotopeSymbol) {
        this.isotopeSymbol = isotopeSymbol;
    }

    public String getIsotopeName() {
        return isotopeName;
    }

    public void setIsotopeName(String isotopeName) {
        this.isotopeName = isotopeName;
    }

    public String getIsotopeNumber() {
        return isotopeNumber;
    }

    public void setIsotopeNumber(String isotopeNumber) {
        this.isotopeNumber = isotopeNumber;
    }

    public String getIsotopeMass() {
        return isotopeMass;
    }

    public void setIsotopeMass(String isotopeMass) {
        this.isotopeMass = isotopeMass;
    }

    public String getIsotopeComposition() {
        return isotopeComposition;
    }

    public void setIsotopeComposition(String isotopeComposition) {
        this.isotopeComposition = isotopeComposition;
    }

    public String getIsotopeWeight() {
        return isotopeWeight;
    }

    public void setIsotopeWeight(String isotopeWeight) {
        this.isotopeWeight = isotopeWeight;
    }

    public String getIsotopeAbundance() {
        return isotopeAbundance;
    }

    public void setIsotopeAbundance(String isotopeAbundance) {
        this.isotopeAbundance = isotopeAbundance;
    }

    public String getElemsubName() {
        return elemsubName;
    }

    public void setElemsubName(String elemsubName) {
        this.elemsubName = elemsubName;
    }

    public String getElemsubSymbol() {
        return elemsubSymbol;
    }

    public void setElemsubSymbol(String elemsubSymbol) {
        this.elemsubSymbol = elemsubSymbol;
    }

    public String getElemsubNum() {
        return elemsubNum;
    }

    public void setElemsubNum(String elemsubNum) {
        this.elemsubNum = elemsubNum;
    }

    public String getElemsubMass() {
        return elemsubMass;
    }

    public void setElemsubMass(String elemsubMass) {
        this.elemsubMass = elemsubMass;
    }

    public String getElemsubGroup() {
        return elemsubGroup;
    }

    public void setElemsubGroup(String elemsubGroup) {
        this.elemsubGroup = elemsubGroup;
    }

    public String getElemsubPeriod() {
        return elemsubPeriod;
    }

    public void setElemsubPeriod(String elemsubPeriod) {
        this.elemsubPeriod = elemsubPeriod;
    }

    public String getElemsubState() {
        return elemsubState;
    }

    public void setElemsubState(String elemsubState) {
        this.elemsubState = elemsubState;
    }

    public String getElemsubValence() {
        return elemsubValence;
    }

    public void setElemsubValence(String elemsubValence) {
        this.elemsubValence = elemsubValence;
    }

    public String getElemsubConfig() {
        return elemsubConfig;
    }

    public void setElemsubConfig(String elemsubConfig) {
        this.elemsubConfig = elemsubConfig;
    }

    public String getElemsubDensity() {
        return elemsubDensity;
    }

    public void setElemsubDensity(String elemsubDensity) {
        this.elemsubDensity = elemsubDensity;
    }

    public String getElemsubSeries() {
        return elemsubSeries;
    }

    public void setElemsubSeries(String elemsubSeries) {
        this.elemsubSeries = elemsubSeries;
    }

    public String getElemsubConfiguration() {
        return elemsubConfiguration;
    }

    public void setElemsubConfiguration(String elemsubConfiguration) {
        this.elemsubConfiguration = elemsubConfiguration;
    }

    public Integer getElemsubXCoord() {
        return elemsubXCoord;
    }

    public void setElemsubXCoord(Integer elemsubXCoord) {
        this.elemsubXCoord = elemsubXCoord;
    }

    public Integer getElemsubYCoord() {
        return elemsubYCoord;
    }

    public void setElemsubYCoord(Integer elemsubYCoord) {
        this.elemsubYCoord = elemsubYCoord;
    }
    private Integer elemsubXCoord, elemsubYCoord;

    public FunctionalityBean() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://periodictable.cb2vg6rxwemx.us-west-2.rds.amazonaws.com:3306/cecs493ptable", "root", "z_?PvVIr,HcuK_2vU9ldbE_i0");
            System.out.println("Database connection established...");
        } catch (Exception e) {
            System.err.println("Connection Error: " + e);
        }
    }

    public void setConnecction(Connection conn) {
        this.conn = conn;
    }

    //Creates an admin.
    public void createUser(String first, String last, String user, String pass) {
        try {
            String query = "{call sp_createUser(?, ?, ?, ?, ?)}";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, first);
            stmt.setString(2, last);
            stmt.setString(3, user);
            stmt.setString(4, pass);
            stmt.setInt(5, 1);

            stmt.execute();
            //System.out.println("User " + first + " " + last + " was created.");
        } catch (Exception e) {
        }
    }

    //Validates a user login. Returns true if valid, false if not.
    public boolean validateUser(String user, String pass) {
        try {
            String query = "{call sp_validateUser(?, ?)}";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, user);
            stmt.setString(2, pass);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int valid = rs.getInt("count(*)");
                if (valid == 1) {
                    System.out.println("User Validated");
                    return true;
                } else {
                    System.out.println("User Not Validated");
                    return false;
                }
            }
        } catch (SQLException e) {
        }
        return false;
    }

    public void loadElement() {
        String name = selectedElemName;
        ConnectionBean bean = new ConnectionBean(conn);
        Element e = bean.getElementByName(name);
        setElemsubSymbol(e.getSymbol());
        setElemsubConfig(e.getConfiguration());
        setElemsubDensity(e.getDensity());
        setElemsubGroup(e.getGroupNumber());
        setElemsubMass(e.getMass());
        setElemsubName(e.getName());
        setElemsubNum(e.getAtomNum());
        setElemsubPeriod(e.getPeriod());
        setElemsubSeries(e.getSeries());
        setElemsubState(e.getStateOfMatter25());
        setElemsubValence(e.getValences());
        setElemsubXCoord(e.getxCoord());
        setElemsubYCoord(e.getyCoord());
    }
    
    public void loadIsotope() {
        String sym = selectedIsotopeName;
        ConnectionBean bean = new ConnectionBean(conn);
        Isotope i = bean.getIsotopeBySymbol(sym);
        setIsotopeSymbol(i.getSymbol());
        setIsotopeAtomNum("" + i.getAtomNum());
        setIsotopeName(i.getIsoName());
        setIsotopeNumber(i.getIsoNum());
        setIsotopeMass(i.getMass());
        setIsotopeComposition(i.getIsoComp());
        setIsotopeWeight(i.getIsoWeight());
        setIsotopeAbundance(i.getAbundance());
    }
    
    
    //Adds an element to the database.
    public void insertElement() {
        String elemName = getElemsubName();
        String atomSymb = getElemsubSymbol();
        String atomNum = getElemsubNum();
        String mass = getElemsubMass();
        String group = getElemsubGroup();
        int period = Integer.parseInt(getElemsubPeriod());
        String state25 = getElemsubState();
        String valences = getElemsubValence();
        String elecConf = getElemsubConfig();
        String density = getElemsubDensity();
        String series = getElemsubSeries();
        int xCor = getElemsubXCoord();
        int yCor = getElemsubYCoord();
        Random rand = new Random();
        int n = rand.nextInt(999) + 150;
        int atomNumInt = 1000;
        int corDefault = 1;
        if (!atomNum.isEmpty()) {
            atomNumInt = Integer.parseInt(atomNum);
        }
        if (elemName.isEmpty()) {
            elemName = "Element " + atomNumInt;
        }
        if (atomSymb.isEmpty()) {
            atomSymb = "XX";
        }
        if (density.isEmpty()) {
            density = "";
        }
        if (group.isEmpty()) {
            group = "";
        }
        if (xCor == 0) {
            xCor = corDefault;
        }
        if (yCor == 0) {
            yCor = corDefault;
        }

        try {
            String query = "{call sp_insertelement(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, atomNumInt);
            stmt.setString(2, atomSymb);
            stmt.setString(3, elemName);
            stmt.setString(4, mass);
            stmt.setString(5, group);
            stmt.setInt(6, period);
            stmt.setString(7, density);
            stmt.setString(8, series);
            stmt.setString(9, state25);
            stmt.setString(10, valences);
            stmt.setString(11, elecConf);
            stmt.setInt(12, xCor);
            stmt.setInt(13, yCor);

            stmt.execute();
            System.out.println("Element " + elemName + " was added.");
        } catch (Exception e) {
        }
    }

    //Deletes an element given the atomic number.
    public void deleteElement() {
        String atomNum = getElemsubNum();
        int atomNumInt = 0;
        if (!atomNum.isEmpty()) {
            atomNumInt = Integer.parseInt(atomNum);
        }
        try {
            String query = "{call sp_deleteelement_(?)}";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, atomNumInt);
            stmt.execute();
            System.out.println("Element " + atomNum + " was deleted.");
        } catch (Exception e) {
        }
    }

    //Updates an element. Update form must contain ALL data, even unedited data. Use object getters to fill the form upon generation.
    public void updateElement() {
        deleteElement();
        insertElement();
    }

    public void insertIsotope() {
        
        
        String nothing = null;

        

        try {
            String query = "{call sp_insertisotope(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setFloat(1, Float.parseFloat(getIsotopeAtomNum()));
            stmt.setString(2, nothing);
            stmt.setString(3, getIsotopeName());
            stmt.setFloat(4, Float.parseFloat(getIsotopeNumber()));
            stmt.setString(5, getIsotopeSymbol());
            stmt.setString(6, getIsotopeMass());
            stmt.setString(7, getIsotopeComposition());
            stmt.setString(8, getIsotopeWeight());
            stmt.setString(9, nothing);
            stmt.setString(10, getIsotopeAbundance());

            stmt.execute();
            System.out.println("Isotope " + getIsotopeSymbol() + " was added.");
        } catch (Exception e) {
        }
    }

    //Deletes an isotope given the isotopic symbol
    public void deleteIsotope() {
        try {
            String query = "{call sp_deleteisotope(?)}";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, getIsotopeSymbol());
            stmt.execute();
            System.out.println("Isotope " + getIsotopeSymbol() + " was deleted.");
        } catch (Exception e) {
        }
    }

    //Updates an isotope. Update form must contain ALL data, even unedited data. Use object getters to fill the form upon generation.
    public void updateIsotope() {
        deleteIsotope();
        insertIsotope();
        System.out.println("Isotope " + getIsotopeSymbol() + " was updated.");
    }

    public void setIsotopeAtomNum(String isotopeAtomNum) {
        this.isotopeAtomNum = isotopeAtomNum;
    }
}