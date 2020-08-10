package ie.cct.sbs19016bakingrecipes;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ie.cct.model.Ingredient;
import ie.cct.model.Recipe;
import ie.cct.sbs19016bakingrecipes.LocalJsonParser;


//https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RestController.html
@RestController
public class BakingAppController {
	
	List<Recipe> myBakingRecipes = new ArrayList<>();
	
	public BakingAppController() throws URISyntaxException {
		// Populate with initial recipes
		try {
			LocalJsonParser myJP = new LocalJsonParser();
			myBakingRecipes = myJP.myRecipeBuilder();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public String welcomeMessage() {
	    return "<b>Home Baker App DB - a RESTful web service proof of concept</b><br><br>" +
	    		"Student: Mattia Bergamini<br>" +
	    		"CCT College, Dublin<br><br>" +
	    		"Technologies used:<br>" +
	    		"&emsp;&emsp;&emsp;&emsp;Project: Maven<br>" +
	    		"&emsp;&emsp;&emsp;&emsp;Language: Java 8<br>" +
	    		"&emsp;&emsp;&emsp;&emsp;Packaging: JAR<br>" +
	    		"&emsp;&emsp;&emsp;&emsp;Application framework: Spring Boot<br>" +
	    		"&emsp;&emsp;&emsp;&emsp;Could service: Azure<br><br>" +
	    		"End-points:<br>" +
	    		"🅐 <b>GET</b> /recipes<br>&emsp;&emsp;&emsp;&emsp;<i>=> returns list of recipes in JSON format</i><br>" +
	    		"🅑 <b>GET</b> /recipe?id={recipe-id}<br>&emsp;&emsp;&emsp;&emsp;<i>=> returns individual recipe in JSON format, by recipe id</i><br>" +
	    		"🅒 <b>GET</b> /ingredients?recipe-id={recipe-id}<br>&emsp;&emsp;&emsp;&emsp;<i>=> returns list of ingredients by recipe id in JSON format</i><br>" +
	    		"🅓 <b>GET</b> /recipe-search?name={recipe-name-query}<br>&emsp;&emsp;&emsp;&emsp;<i>=> returns list of recipes that match string in query</i><br>";
	}
	
	/*
	 * END-POINT => "Get my recipes"
	 * Returns full list of recipes in JSON format
	 */	
	@GetMapping("recipes") // http://localhost:8080/recipes
	public List<Recipe> getRecipes() { 
		return myBakingRecipes;
	}
	
	
	/*
	 * END-POINT => recipe?id=int
	 * Returns individual recipe
	 */	
	@GetMapping("recipe") // http://localhost:8080/recipe?id=1
	public ResponseEntity<Recipe> getRecipeById(@RequestParam(name="id", required=true) Integer id) { 
		
		Recipe voidRecipe = null;
		ResponseEntity<Recipe> myRE = new ResponseEntity<Recipe>(voidRecipe, HttpStatus.NO_CONTENT);
		
		for (Recipe recipe : myBakingRecipes) {
			if (recipe.getId() == id) {
				myRE = new ResponseEntity<Recipe>(recipe, HttpStatus.OK);
				return myRE;
			} else {
				myRE = new ResponseEntity<Recipe>(voidRecipe, HttpStatus.NOT_FOUND);
			}
		}
		return myRE;
	}
	
	
	/*
	 * END-POINT => ingredients?recipe-id=1
	 * Returns all ingredients of an individual recipe
	 */	
	@GetMapping("ingredients") // http://localhost:8080/ingredients?recipe-id=1
	public ResponseEntity<List<Ingredient>> getIngredientsByRecipeId(@RequestParam(name="recipe-id", required=true) Integer id) { 
		
		Recipe myRe = new Recipe();
		List<Ingredient> myIngredients = new ArrayList<Ingredient>();
		boolean recipeIsFound = false;
		
		for (Recipe recipe : myBakingRecipes) {
			if (recipe.getId() == id) {
				
				myRe = recipe;
				myIngredients = myRe.getIngredients();
				recipeIsFound = true;
				
			} 
		}
		
		if (recipeIsFound) {
			return new ResponseEntity<List<Ingredient>>(myIngredients, HttpStatus.OK);
		} else {
			myIngredients = null;
			return new ResponseEntity<List<Ingredient>>(myIngredients, HttpStatus.NOT_FOUND);
		}
	}
	
	
	/*
	 * END-POINT => recipe?name=string
	 * Returns individual recipe by name
	 * String needs to be at least 3 characters long
	 */	
	@GetMapping("recipe-search") // http://localhost:8080/recipe-search?name=pizza
	public ResponseEntity<List<Recipe>> getRecipeByName(@RequestParam(name="name", required=true) String recipeNameQuery) { 
		
		List<Recipe> matchedRecipes = new ArrayList<Recipe>();
		ResponseEntity<List<Recipe>> myRE = new ResponseEntity<List<Recipe>>(matchedRecipes, HttpStatus.NO_CONTENT);
		
		if (recipeNameQuery.length() > 2) { // only perform search if query is at least 3 characters long
			for (Recipe recipe : myBakingRecipes) {
				if (recipe.getName().toLowerCase().contains(recipeNameQuery.toLowerCase())) { // make search case insensitive
					matchedRecipes.add(recipe);
				}
			}
		}
		if (matchedRecipes.isEmpty()) {
			myRE = new ResponseEntity<List<Recipe>>(matchedRecipes, HttpStatus.NOT_FOUND);
		} else {
			myRE = new ResponseEntity<List<Recipe>>(matchedRecipes, HttpStatus.OK);
		}
		
		return myRE;
		
	}
	

}
