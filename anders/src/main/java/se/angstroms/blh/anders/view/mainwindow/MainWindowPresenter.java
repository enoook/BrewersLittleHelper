package se.angstroms.blh.anders.view.mainwindow;

import com.airhacks.afterburner.injection.InjectionProvider;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javax.inject.Inject;
import org.blh.core.ingredient.Hop;
import org.blh.core.ingredient.Malt;
import org.blh.core.ingredient.Yeast;
import org.blh.core.recipe.GristPart;
import org.blh.core.recipe.HopAddition;
import org.blh.core.recipe.YeastAddition;
import org.blh.core.uncategorized.BeerType;
import org.blh.core.unit.ExtractPotential;
import org.blh.core.unit.Factor;
import org.blh.core.unit.Percentage;
import org.blh.core.unit.color.Lovibond;
import org.blh.core.unit.gravity.GravityPoints;
import org.blh.core.unit.time.Minutes;
import org.blh.core.unit.volume.Liters;
import org.blh.core.unit.weight.Grams;
import org.blh.core.unit.weight.Kilograms;
import org.blh.core.unit.weight.Lbs;
import se.angstroms.blh.anders.context.FullContext;
import se.angstroms.blh.anders.context.value.findingformulas.FormulaDirectory;
import se.angstroms.blh.anders.context.value.findingformulas.NoDefaultFormulaException;
import se.angstroms.blh.anders.formulas.DefaultFormulaHelper;
import se.angstroms.blh.anders.view.recipe.RecipesTab;

/**
 * The main window.
 *
 * @author Erik Larkö <erik.larko@purplescout.se>
 */
public class MainWindowPresenter implements Initializable {

    @Inject
    private FormulaDirectory formulaDirectory;

    @FXML
    private RecipesTab recipesTab;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        InjectionProvider.registerExistingAndInject(this);

        recipesTab.availableRecipesProperty().set(getDummyRecipeList());
    }

    private ObservableList<FullContext> getDummyRecipeList() {
        List<GristPart> fermentables = new LinkedList<>();
        List<HopAddition> hops = new LinkedList<>();
        List<YeastAddition<?>> yeasts = new LinkedList<>();

        fermentables.add(new GristPart(new Malt("Münich", new Lovibond(1), new ExtractPotential(new GravityPoints(38 / Lbs.CONVERSION_FACTOR)), Malt.TYPE.GRAIN), new Kilograms(1.7)));
        hops.add(new HopAddition(new Hop("Magnum", new Percentage(13.7)), new Minutes(60), new Grams(28.4)));
        //hops.add(new HopAddition(new Hop("Goldings", new Percentage(5.7)), new Minutes(10), new Grams(28.4)));
        yeasts.add(new YeastAddition<>(new Yeast("US-05", "Safale", new Percentage(88)), new Grams(11)));

        try {
            FullContext recipe = new FullContext();
            recipe.nameProperty().set("Dodo IPA");
            recipe.beerTypeProperty().set(BeerType.ALE);
            recipe.getIngredientsList().setFermentables(fermentables);
            recipe.getIngredientsList().setHopAdditions(hops);
            recipe.getIngredientsList().setYeastAdditions(yeasts);

            recipe.getPostBoilVolume().set(new Liters(7));
            recipe.getExtractionEfficiency().set(new Factor(0.7));

            new DefaultFormulaHelper(formulaDirectory).setupDefaultFormulas(recipe);
            return FXCollections.observableArrayList(recipe);

        } catch (NoDefaultFormulaException ex) {
            throw new RuntimeException("Unable to build dummy recipe", ex);
        }
    }
}
