package ie.cct.sbs19016bakingrecipes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import ie.cct.model.Recipe;


//https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RestController.html
@RestController
public class BakingAppController {
	
	private List<Recipe> myBakingRecipes = new ArrayList<Recipe>();
	
	public BakingAppController() {
		
		// Populate with initial recipes
		LocalJsonParser myJP = new LocalJsonParser();
		//myBakingRecipes = myJP.myRecipeBuilder();
		
	}
	

}
