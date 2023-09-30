import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.TreeMap;

/**
 * Class that creates a calendar that would contain holidays and class times used
 * by all the employees.
 * @param <T>
 */
public class Calendar<T extends Comparable<T>> {
    // This is a dictionary with String key Date and Value as classes
    private TreeMap<String, TreeMap<String, String>> classTimes;

    // This is a dictionary with key Class and value as times
    private TreeMap<String, String> classes;

    // This would be a dictionary with String key Holiday and String value Date
    private TreeMap<String, String> holidays;

    public Calendar(){
        classes = new TreeMap<String, String>();
        classTimes = new TreeMap<String, TreeMap<String, String>>();
        holidays = new TreeMap<String, String>();
    }

    /**
     * Adds classes/labs to the classes dictionary using className
     * as the key and time as the value.
     */
    public void addClasses(String className, String time)
    {

        this.classes.put(className, time);
    }

    public void addHolidays(String file) {

    }

    public static void main(String[] args) {

    }
}