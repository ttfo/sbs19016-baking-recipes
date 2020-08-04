package ie.cct.model;

import java.util.List;

public class Recipe {

	private int id;
	private String name;
	private List<Ingredient> ingredients;
	private List<Step> steps;
	private int servings;
	private String image;
	private Author author;
	
	public Recipe(int id, String name, List<Ingredient> ingredients, List<Step> steps, int servings, Author author) {
		super();
		this.id = id;
		this.name = name;
		this.ingredients = ingredients;
		this.steps = steps;
		this.servings = servings;
		this.author = author;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public List<Ingredient> getIngredients() {
		return ingredients;
	}


	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}


	public List<Step> getSteps() {
		return steps;
	}


	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}


	public int getServings() {
		return servings;
	}


	public void setServings(int servings) {
		this.servings = servings;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public Author getAuthor() {
		return author;
	}


	public void setAuthor(Author author) {
		this.author = author;
	}
	
	
}
