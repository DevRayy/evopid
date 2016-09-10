import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SettingsContainer {
    private int samples;
    private double time;
    private int populations;
    private int specimens;
    private double risingTimeLow;
    private double risingTimeHigh;
    private double settlingMargin;
    private double overshoot;
    private double risingTime;
    private double settlingTime;
}
