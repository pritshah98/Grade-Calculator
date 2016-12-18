import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.geometry.Pos;
import java.util.*;
import javafx.util.*;
import javafx.scene.control.Alert.AlertType;

public class GradeCalculator extends Application {

    private ButtonType enter = new ButtonType("ENTER", ButtonData.OK_DONE);
    private VBox layout = new VBox(20);
    private TextInputDialog numGrades = new TextInputDialog();
    private Map<Double, Double> gradeMap = new HashMap<>();
    private TextField finalGrade = new TextField();
    private TextField grade = new TextField();
    private TextField weight = new TextField();
    private Dialog<Pair<String, String>> inputDialog = new Dialog<>();
    private Alert doubleAlert = new Alert(AlertType.INFORMATION);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        window.setTitle("Grade Calculator");
        numGrades.setTitle("Grade Calculator");
        numGrades.setHeaderText("Enter in the number of grades being input.");
        Optional<String> desiredNum = numGrades.showAndWait();
        if (desiredNum.isPresent()) {
            doubleAlert.setTitle("IMPORTANT NOTICE");
            doubleAlert.setHeaderText("Weight must be input as a decimal value (e.g. 15% = 0.15)");
            doubleAlert.showAndWait();
            for (int i = 0; i < Integer.parseInt(desiredNum.get()); i++) {
                enterGrades();
                grade.clear();
                weight.clear();
            }
        }
        getGrade();
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().add(finalGrade);
        Scene scene = new Scene(layout, 375, 100);
        window.setScene(scene);
        window.show();
    }

    public void getGrade() {
        Double overallGrade = 0.0;
        Double totalWeight = 0.0;
        Iterator it = gradeMap.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            overallGrade += (((Double)pair.getKey()) * ((Double)pair.getValue()));
            totalWeight += ((Double)pair.getValue());
        }
        finalGrade.setText("Final Calculated Grade: "
        + String.format("%.4g%n", overallGrade/totalWeight)
        + " with a total weight of " + totalWeight);
    }

    public void enterGrades() {
        inputDialog.setTitle("Grade and Weight");
        inputDialog.setHeaderText("Enter in a grade and its weight");
        inputDialog.getDialogPane().getButtonTypes().addAll(enter);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grade.setPromptText("Enter Grade");
        weight.setPromptText("Enter Weight");
        grid.add(new Label("Grade:"), 0, 0);
        grid.add(grade, 1, 0);
        grid.add(new Label("Weight:"), 0, 1);
        grid.add(weight, 1, 1);
        inputDialog.getDialogPane().setContent(grid);
        Optional<Pair<String, String>> result = inputDialog.showAndWait();
        if (result.isPresent()) {
            gradeMap.put(Double.parseDouble(grade.getText()),
            Double.parseDouble(weight.getText()));
        }
    }
}
