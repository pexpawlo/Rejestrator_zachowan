package bazadanych;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.Date;
import java.util.zip.DataFormatException;

/**
 * Created by Pawel on 28.11.2016.
 */

public class HourFormatter implements IAxisValueFormatter {
    Date startDate;
    int interval;
    String [] values = {"a","b","c","d","e","f"};
    public HourFormatter(int interval)
    {
        this.interval = interval;

    }
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
if (value<0) return "";
else return value*interval+" min";

    }
}
