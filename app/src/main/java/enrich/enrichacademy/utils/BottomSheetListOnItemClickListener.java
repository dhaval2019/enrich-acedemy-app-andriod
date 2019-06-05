package enrich.enrichacademy.utils;

import java.io.Serializable;

import enrich.enrichacademy.model.TimeSlotModel;

/**
 * Created by Admin on 22-Feb-17.
 */

public interface BottomSheetListOnItemClickListener extends Serializable {

    void onListItemSelected(int dateId,String slotDate, TimeSlotModel timeSlotModel);

}
