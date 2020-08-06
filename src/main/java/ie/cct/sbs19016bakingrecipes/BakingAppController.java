package ie.cct.sbs19016bakingrecipes;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ie.cct.model.Author;
import ie.cct.model.Recipe;
import ie.cct.sbs19016bakingrecipes.LocalJsonParser;


//https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RestController.html
@RestController
public class BakingAppController {
	
	
	// Populate with initial recipes
	LocalJsonParser myJP = new LocalJsonParser();
	List<Recipe> myBakingRecipes = myJP.myRecipeBuilder();
	
	
	public BakingAppController() {}
	
	
	/*
	 * END-POINT => "Get my recipes"
	 * Returns list of recipes in JSON format
	 */	
	@GetMapping("get-recipes") // http://localhost:8080/get-recipes
	public List<Recipe> getRecipes() { 
		return myBakingRecipes;
	}
	

}
