import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SettingsContainer {
    private int samples;
    private double time;
    private int populations;        //TODO
    private int specimens;
    private double risingTimeLow;
    private double risingTimeHigh;
    private double settlingMargin;
    private double overshoot;
    private double risingTime;
    private double settlingTime;
    private Double[][] A;           //TODO
    private Double[][] B;           //TODO
    private Double[][] C;           //TODO
    private Double[][] D;           //TODO

    private static SettingsContainer singleton;

    public static SettingsContainer get() {
        if (singleton == null)
            singleton = new SettingsContainer();

        return singleton;
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

    public Double[][] getA() {
        return A;
    }

    public void setA(String base) {
        this.A = parseStringToMatrix(base);
    }

    public Double[][] getB() {
        return B;
    }

    public void setB(String base) {
        this.B = parseStringToMatrix(base);
    }

    public Double[][] getC() {
        return C;
    }

    public void setC(String base) {
        this.C = parseStringToMatrix(base);
    }

    public Double[][] getD() {
        return D;
    }

    public void setD(String base) {
        this.D = parseStringToMatrix(base);
    }

    private Double[] stringArrayToDoubleArray(String[] source) {
        Double[] ret = new Double[source.length];
        for(int i=0; i< source.length; i++) {
            source[i] = source[i].replace(" ", "").replace("\n", "");
            ret[i] = Double.parseDouble(source[i]);
        }

        return ret;
    }

    private Double[][] parseStringToMatrix(String base) {

        String[] rows = base.split(";");
        int rowsCount = rows.length;
        int columnsCount = rows[0].split(",").length;
        Double[][] ret = new Double[rowsCount][columnsCount];
        for(int i=0; i<rowsCount; i++) {
            String[] vals = rows[i].split(",");
            ret[i] = stringArrayToDoubleArray(vals);
        }

        System.out.println(ret);

        return ret;
    }
}
