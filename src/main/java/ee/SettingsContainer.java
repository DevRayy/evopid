package ee;//import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.text.ParseException;

@Data
@NoArgsConstructor
public class SettingsContainer {

    @JsonProperty(value = "samples")
    private int samples;

    @JsonProperty(value = "time")
    private double time;

    @JsonProperty(value = "populations")
    private int populations;

    @JsonProperty(value = "specimens")
    private int specimens;

    @JsonProperty(value = "risingTimeLow")
    private double risingTimeLow;

    @JsonProperty(value = "risingTimeHigh")
    private double risingTimeHigh;

    @JsonProperty(value = "settlingMargin")
    private double settlingMargin;

    @JsonProperty(value = "overshoot")
    private double overshoot;

    @JsonProperty(value = "risingTime")
    private double risingTime;

    @JsonProperty(value = "settlingTime")
    private double settlingTime;

    @JsonProperty(value = "limitOvershootValue")
    private double limitOvershootValue;

    @JsonProperty(value = "limitOvershootEnabled")
    private boolean limitOvershootEnabled;

    @JsonProperty(value = "A")
    private Double[][] A;           //TODO

    @JsonProperty(value = "B")
    private Double[][] B;           //TODO

    @JsonProperty(value = "C")
    private Double[][] C;           //TODO

    @JsonProperty(value = "D")
    private Double[][] D;           //TODO

    private static SettingsContainer singleton;

    public static SettingsContainer get() {
        if (singleton == null)
            singleton = new SettingsContainer();

        return singleton;
    }

    public static void set(SettingsContainer newSettingsContainer) {
        singleton = newSettingsContainer;
    }

    public int getSamples() {
        return samples;
    }

    public void setSamples(int samples) {
        this.samples = samples;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public int getPopulations() {
        return populations;
    }

    public void setPopulations(int populations) {
        this.populations = populations;
    }

    public int getSpecimens() {
        return specimens;
    }

    public void setSpecimens(int specimens) {
        this.specimens = specimens;
    }

    public double getRisingTimeLow() {
        return risingTimeLow;
    }

    public void setRisingTimeLow(double risingTimeLow) {
        this.risingTimeLow = risingTimeLow;
    }

    public double getRisingTimeHigh() {
        return risingTimeHigh;
    }

    public void setRisingTimeHigh(double risingTimeHigh) {
        this.risingTimeHigh = risingTimeHigh;
    }

    public double getSettlingMargin() {
        return settlingMargin;
    }

    public void setSettlingMargin(double settlingMargin) {
        this.settlingMargin = settlingMargin;
    }

    public double getOvershoot() {
        return overshoot;
    }

    public void setOvershoot(double overshoot) {
        this.overshoot = overshoot;
    }

    public double getRisingTime() {
        return risingTime;
    }

    public void setRisingTime(double risingTime) {
        this.risingTime = risingTime;
    }

    public double getSettlingTime() {
        return settlingTime;
    }

    public void setSettlingTime(double settlingTime) {
        this.settlingTime = settlingTime;
    }

    public double getLimitOvershootValue() {
        return limitOvershootValue;
    }

    public void setLimitOvershootValue(double limitOvershootValue) {
        this.limitOvershootValue = limitOvershootValue;
    }

    public boolean isLimitOvershootEnabled() {
        return limitOvershootEnabled;
    }

    public void setLimitOvershootEnabled(boolean limitOvershootEnabled) {
        this.limitOvershootEnabled = limitOvershootEnabled;
    }

    @JsonIgnore public String getAstring() {
        return parseMatrixToString(A);
    }

    public Double[][] getA() { return A; }

    @JsonIgnore
    public void setAstring(String base) {
        this.A = parseStringToMatrix(base);
    }
    public void setA(Double[][] base) {
        this.A = base;
    }

    @JsonIgnore  public String getBstring() {
        return parseMatrixToString(B);
    }
    public Double[][] getB() { return B; }

    @JsonIgnore public void setBstring(String base) {
        this.B = parseStringToMatrix(base);
    }
    public void setB(Double[][] base) { this.B = base; }

    @JsonIgnore public String getCstring() {
        return parseMatrixToString(C);
    }
    public Double[][] getC() { return C; }

    @JsonIgnore public void setCstring(String base) {
        this.C = parseStringToMatrix(base);
    }
    public void setC(Double[][] base) { this.C = base; }

    @JsonIgnore public String getDstring() {
        return parseMatrixToString(D);
    }
    public Double[][] getD() { return D; }

    @JsonIgnore public void setDstring(String base) {
        this.D = parseStringToMatrix(base);
    }
    public void setD(Double[][] base) { this.D = base; }

    private Double[] stringArrayToDoubleArray(String[] source) {
        Double[] ret = new Double[source.length];
        try {
            for (int i = 0; i < source.length; i++) {
                source[i] = source[i].replace(" ", "").replace("\n", "");
                ret[i] = Double.parseDouble(source[i]);
            }
        } catch (Exception e) {
        }
        return ret;
    }

    private Double[][] parseStringToMatrix(String base) {
        try {
            base.replace(" ", "").replace("\n", "");
            String[] rows = base.split(";");
            int rowsCount = rows.length;
            int columnsCount = rows[0].split(",").length;
            Double[][] ret = new Double[rowsCount][columnsCount];
            for (int i = 0; i < rowsCount; i++) {
                String[] vals = rows[i].split(",");
                ret[i] = stringArrayToDoubleArray(vals);
            }

            return ret;
        } catch (Exception e) {
            ee.Utils.showErrorMessage("Could not parse string to matrix");
        }
        return null;
    }

    private String doubleArrayToString(Double[] array) {
        String ret = "";
        for(Double d : array) {
            ret = ret + d + ", ";
        }
        return ret.substring(0, ret.length()-2);
    }

    private String parseMatrixToString(Double[][] matrix) {
        String ret = "";
        for(Double[] d : matrix) {
            ret = ret + doubleArrayToString(d) + ";\n";
        }
        return ret.substring(0, ret.length()-2);
    }
}
