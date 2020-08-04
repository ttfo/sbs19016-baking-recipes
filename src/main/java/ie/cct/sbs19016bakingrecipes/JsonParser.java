package ie.cct.sbs19016bakingrecipes;
import com.google.gson.Gson;

// Helper class to help parse recipes @ src\main\resources\static\recipes_json
// Recipes from https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json
public class JsonParser {
	
	// default constructor
	public JsonParser() {}
	
	Gson g = new Gson(); // Using Gson library - REF https://github.com/google/gson
							// also REF. https://stackoverflow.com/questions/2591098/how-to-parse-json-in-java

	Person person = g.fromJson("{\"name\": \"John\"}", Person.class);
	System.out.println(person.name); //John

	System.out.println(g.toJson(person)); // {"name":"John"}

}
