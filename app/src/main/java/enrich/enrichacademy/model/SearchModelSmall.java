package enrich.enrichacademy.model;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.Arrays;
import java.util.List;

/**
 * Created by varunbarve on 04/10/17.
 */

public class SearchModelSmall implements Parent<String> {

    public String name;
    public int id;
    public String description;
    public String dataType;
    public String price;

    @Override
    public List<String> getChildList () {
        return Arrays.asList("" + description);
    }

    @Override
    public boolean isInitiallyExpanded () {
        return false;
    }
}
