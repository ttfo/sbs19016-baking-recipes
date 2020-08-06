package ie.cct.sbs19016bakingrecipes;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import ie.cct.model.Author;
import ie.cct.model.Ingredient;
import ie.cct.model.Measure;
import ie.cct.model.Recipe;
import ie.cct.model.Step;

// Helper class to help parse recipes @ src\main\resources\static\recipes_json
// Recipes from https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json
public class LocalJsonParser {
	
	// default constructor
	public LocalJsonParser() {}
	
	
	public List<Recipe> myRecipeBuilder() throws IOException, URISyntaxException {
		
		//Gson g = new Gson(); // Using Gson library - REF https://github.com/google/gson
		// also REF. https://stackoverflow.com/questions/2591098/how-to-parse-json-in-java

		File recipeBook = null;
		JsonReader reader = null;
		
		List<Recipe> myRecipeBook = new ArrayList<Recipe>();
		

		// Snippet below is from https://stackoverflow.com/questions/18055189/why-is-my-uri-not-hierarchical
		// file name is BOOT-INF/classes/static/recipes_json
		// Path : C:\Users\matbe\OneDrive\CCT_College_CompScience(SpringBoard)\_FINAL_PROJECT\sbs19016-baking-recipes\BOOT-INF\classes\static\recipes_json
//		File jarFile = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
//        String actualFile = jarFile.getParentFile().getAbsolutePath()+File.separator+"sbs19016-baking-recipes-0.0.1-SNAPSHOT.jar";
//        System.out.println("jarFile is : "+jarFile.getAbsolutePath());
//        System.out.println("actulaFilePath is : "+actualFile);
//        final JarFile jar = new JarFile(actualFile);
//        final Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
//        System.out.println("Reading entries in jar file ");
//        while(entries.hasMoreElements()) {
//            JarEntry jarEntry = entries.nextElement();
//            final String name = jarEntry.getName();
//            if (name.startsWith("")) { //filter according to the path
//                System.out.println("file name is "+name);
//                System.out.println("is directory : "+jarEntry.isDirectory());
//                File scriptsFile  = new File(name);
//                System.out.println("file names are : "+scriptsFile.getAbsolutePath());
//
//            }
//        }
//        jar.close();
		
        // Also check https://stackoverflow.com/questions/20389255/reading-a-resource-file-from-within-jar
		// String path = File.separator + "static"+ File.separator + "recipes_json";
        InputStream in = getClass().getResourceAsStream("/static/recipes_json"); 
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        
        // Create a Reader from String
        Reader stringReader = new StringReader(sb.toString());
        
        System.out.println("Retrieving recipes via InputStream: " + sb);
        
        JsonObject json = new JsonObject();

		// About JsonReader: https://howtodoinjava.com/gson/jsonreader-streaming-json-parser/#:~:text=The%20JsonReader%20is%20the%20streaming,as%20a%20stream%20of%20tokens.
		reader = new JsonReader(stringReader);
		
		JsonArray recipesInJson = (JsonArray) com.google.gson.JsonParser.parseReader(reader);
		// System.out.println(recipesInJson.toString()); // testing point
		
		for (JsonElement jEl : recipesInJson) {
			System.out.println(jEl.toString());
			
			JsonObject jObj = jEl.getAsJsonObject();
			Recipe recipe = new Recipe();
			
			recipe.setId(jObj.get("id").getAsInt());
			recipe.setName(jObj.get("name").getAsString());
			recipe.setType(jObj.get("type").getAsString());
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
				ingredient.setIngredientCategory(ie.cct.model.IngredientType.ingredientCategory.valueOf(jObjIngr.get("type").getAsString()));
				
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
			
			if (jObj.get("author") != null) {
				
				JsonArray authorsJson = jObj.get("author").getAsJsonArray();
				List<Author> authors = new ArrayList<Author>();
				
				for (JsonElement jElAut : authorsJson) {
					
					JsonObject jObjAut = jElAut.getAsJsonObject();
					Author author = new Author();
					
					author.setName(jObjAut.get("name").getAsString());
					author.setWebsite(jObjAut.get("website").getAsString());
					
					authors.add(author);
									
				}
				
				recipe.setAuthors(authors);		
			}
			
			System.out.println(recipe.toString());
			
			myRecipeBook.add(recipe);
			
		}
		
		return myRecipeBook;
	}
	
}
