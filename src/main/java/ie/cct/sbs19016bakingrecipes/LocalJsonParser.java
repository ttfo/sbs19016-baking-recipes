package ie.cct.sbs19016bakingrecipes;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import ie.cct.model.Ingredient;
import ie.cct.model.Measure;
import ie.cct.model.Recipe;
import ie.cct.model.Step;

// Helper class to help parse recipes @ src\main\resources\static\recipes_json
// Recipes from https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json
public class LocalJsonParser {
	
	// default constructor
	public LocalJsonParser() {}
	
	
	public List<Recipe> myRecipeBuilder() {
		
		Gson g = new Gson(); // Using Gson library - REF https://github.com/google/gson
		// also REF. https://stackoverflow.com/questions/2591098/how-to-parse-json-in-java

		File recipeBook = null;
		JsonReader reader = null;
		
		List<Recipe> myRecipeBook = new ArrayList<Recipe>();
		
		try {
			recipeBook = new File( this.getClass().getResource( "/static/recipes_json" ).toURI() );
		} catch (URISyntaxException e) {
			// Unhandled exception type URISyntaxException 
			// => REF https://stackoverflow.com/questions/19255821/unhandled-exception-type-urisyntaxexception
			e.printStackTrace();
		}

		try {
			// About JsonReader: https://howtodoinjava.com/gson/jsonreader-streaming-json-parser/#:~:text=The%20JsonReader%20is%20the%20streaming,as%20a%20stream%20of%20tokens.
			reader = new JsonReader(new FileReader(recipeBook));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("recipes not found on server");
			e.printStackTrace();
		}
		
		JsonArray recipesInJson = (JsonArray) com.google.gson.JsonParser.parseReader(reader);
		// System.out.println(recipesInJson.toString()); // testing point
		
		for (JsonElement jEl : recipesInJson) {
			System.out.println(jEl.toString());
			
			JsonObject jObj = jEl.getAsJsonObject();
			Recipe recipe = new Recipe();
			
			recipe.setId(jObj.get("id").getAsInt());
			recipe.setName(jObj.get("name").getAsString());
			recipe.setServings(jObj.get("servings").getAsInt());
			recipe.setImage(jObj.get("image").getAsString());
			
			JsonArray ingredientsJson = jObj.get("ingredients").getAsJsonArray();
			List<Ingredient> ingredients = new ArrayList<Ingredient>();
			
			for (JsonElement jElIngr : ingredientsJson) {
				
				JsonObject jObjIngr = jElIngr.getAsJsonObject();
				Ingredient ingredient = new Ingredient();
				
				Measure measure = new Measure(jObjIngr.get("quantity").getAsDouble(),jObjIngr.get("measure").getAsString());
				
				// alternative way to populate attr. of measure obj
//				measure.setMeasurementUnit(jObjIngr.get("measure").getAsString());
//				measure.setMeasurementValue(jObjIngr.get("quantity").getAsDouble());
				
				ingredient.setMeasure(measure);
				ingredient.setIngredientName(jObjIngr.get("ingredient").getAsString());
				
				ingredients.add(ingredient);
				
			}
			
			recipe.setIngredients(ingredients);
			
			JsonArray stepsJson = jObj.get("steps").getAsJsonArray();
			List<Step> steps = new ArrayList<Step>();
			
			for (JsonElement jElStep : stepsJson) {
				
				JsonObject jObjStep = jElStep.getAsJsonObject();
				Step step = new Step();
				
				step.setId(jObjStep.get("id").getAsInt());
				step.setShortDescription(jObjStep.get("shortDescription").getAsString());
				step.setDescription(jObjStep.get("description").getAsString());
				step.setVideoURL(jObjStep.get("videoURL").getAsString());
				step.setThumbnailURL(jObjStep.get("thumbnailURL").getAsString());
				
				steps.add(step);
								
			}
			
			recipe.setSteps(steps);
			System.out.println(recipe.toString());
			
			myRecipeBook.add(recipe);
			
		}
		
		return myRecipeBook;
	}
	
}
