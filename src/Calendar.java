import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.TreeMap;

public class Calendar<T extends Comparable<T>> {
    // This is a dictionary with String key Date and Value as classes
    private TreeMap<String, TreeMap<String, String>> classTimes;

    // This is a dictionary with key Class and value as times
    private TreeMap<String, String> classes;

    // This would be a dictionary with String key Holiday and String value Date
    private TreeMap<String, String> holidays;

    public Calendar(){
        classes = new TreeMap<String, String>;
        classTimes = new TreeMap<String, TreeMap<String, String>>;
        holidays = new Treemap<String, String>
    }

    public void addClasses(String className, String time)
    {
        /**
         * Puts className as a key in class and time as the value
         */
        this.classes.put(className, time);
    }

    public void addHolidays(String file) {

    }

    public static void main(String[] args) {

    }
}