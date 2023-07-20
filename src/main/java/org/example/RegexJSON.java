package org.example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import org.json.*;


public class RegexJSON implements Runnable{
    String SampleString;
    String regex;
    public int Finder(){
        Pattern pattern = Pattern.compile(regex);
        Matcher matching = pattern.matcher(SampleString);
        int MatchCount = 0;
        while(matching.find()){
            MatchCount++;
        }
        return MatchCount;
    }
    public void JavaObjectToJSON(ExampleJavaObject Obj, Gson gson){
        // Conversion from Java object to JSON string.
        String json = gson.toJson(Obj);
        // Reconverting
        ExampleJavaObject ObjectNew = gson.fromJson(json,ExampleJavaObject.class);
        JSONObject NewObject = new JSONObject(json); // Making a JsonObject from a JSONString
    }
    public RegexJSON(String SampleString,String regex){
        this.SampleString = SampleString;
        this.regex = regex;
    }
    public RegexJSON(){
        this.SampleString = "";
        this.regex = "";
    }

    public  void run() {
        ExampleJavaObject DummyObject = new ExampleJavaObject(); //  Only Object creation for each thread to get no memory porblems
        LargeString LargeString = new LargeString();
        // Object for performing the task of Regex Matching.
        RegexJSON RegexMatching = new RegexJSON(LargeString.getExampleString(),LargeString.getRegexPattern());
        LoggerEvent event = new LoggerEvent();
        int Count = 0;
        Gson gson = new Gson();
        while(true){
            // Logic
            event.begin();
            int TotalMatches = RegexMatching.Finder();
            JavaObjectToJSON(DummyObject,gson);
            event.end();
            event.MatchCount  = TotalMatches;
            event.ThreadNumber = Thread.currentThread().getName();
            event.WhichOperation = "Regex Matching and JSON parsing";
            event.commit(); // Duration wise we  can filter the number of events recorded.
            Count++;
            if(Count%100 == 0){
                Thread.yield();
            }
        }
    }
}
