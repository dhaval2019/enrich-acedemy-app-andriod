package enrich.enrichacademy.utils;

import java.util.Comparator;

import enrich.enrichacademy.model.TimeSlotModel;

/**
 * Created by varunbarve on 27/07/17.
 */

public class TimeSlotComparator implements Comparator<TimeSlotModel> {
    @Override
    public int compare (TimeSlotModel o1, TimeSlotModel o2) {
        return o1.Start.compareTo(o2.Start);
    }
}
