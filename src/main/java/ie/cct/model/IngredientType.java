package ie.cct.model;

/**
 * @author matbe
 * defines common types/categories of ingredients in baking, 
 * as they will undergo different types of calculations
 */
public interface IngredientType {
	
	enum ingredientCategory { liquid, fat, yeast, flour, other; }; // liquid can be water or milk, fat can be oil or butter, etc.
	
	public void setIngredientCategory(IngredientType.ingredientCategory ingredientCategory);

}
