package ie.cct.sbs19016bakingrecipes;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	
	/*
	 * END-POINT => "Get my recipes"
	 * Returns full list of recipes in JSON format
	 */	
	@GetMapping("recipes") // http://localhost:8080/get-recipes
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
	

}
