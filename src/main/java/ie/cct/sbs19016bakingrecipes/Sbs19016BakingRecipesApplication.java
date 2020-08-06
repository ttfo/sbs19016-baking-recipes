package ie.cct.sbs19016bakingrecipes;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ie.cct.model.Recipe;

@SpringBootApplication
public class Sbs19016BakingRecipesApplication {

	public static void main(String[] args) {
		SpringApplication.run(Sbs19016BakingRecipesApplication.class, args);
		
		// Populate with initial recipes
		LocalJsonParser myJP = new LocalJsonParser();
		List<Recipe> myBakingRecipes = myJP.myRecipeBuilder();
		System.out.println(myBakingRecipes.toString());
	}

}
