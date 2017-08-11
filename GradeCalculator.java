//Created by Prit Shah, pshah334@gatech.edu

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import java.util.*;
import javafx.util.*;
import javafx.scene.control.Alert.AlertType;

public class GradeCalculator extends Application {

    private Scene scene;
    private ButtonType enter = new ButtonType("ENTER", ButtonData.OK_DONE);
    private VBox layout = new VBox(20);
    private TextInputDialog numGrades = new TextInputDialog();
    private List<Double> gradeList = new ArrayList<>();
    private List<Double> weightList = new ArrayList<>();
    private Text finalGrade = new Text();
    private TextField grade = new TextField();
    private TextField weight = new TextField();
    private Text feedback = new Text();
    private Dialog<Pair<String, String>> inputDialog = new Dialog<>();
    private Double overallGrade = 0.0;
    private Double totalWeight = 0.0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        window.setTitle("Grade Calculator");
        numGrades.setTitle("Grade Calculator");
        numGrades.setHeaderText("Enter in the number of grades to input.");
        Optional<String> desiredNum = numGrades.showAndWait();
        if (desiredNum.isPresent()) {
            for (int i = 0; i < Integer.parseInt(desiredNum.get()); i++) {
                enterGrades();
                grade.clear();
                weight.clear();
            }
        }
        getGrade();
        finalGrade.setFont(Font.font(20));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().add(finalGrade);
        window.setScene(getScene());
        window.show();
    }

    public void getGrade() {
        for(int i = 0; i < gradeList.size(); i++) {
            overallGrade += (gradeList.get(i) * weightList.get(i));
            totalWeight += weightList.get(i);
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
            gradeList.add(Double.parseDouble(grade.getText()));
            weightList.add(Double.parseDouble(weight.getText()) / 100.0);
        }
    }

    public Scene getScene() {
        Double returnGrade = overallGrade/totalWeight;
        if (returnGrade >= 90) {
            Image smile = new Image("http://images.clipartpanda.com/laughing-smiley-face-clip-art-k0152710.jpg");
            ImageView smileView = new ImageView();
            smileView.setImage(smile);
            feedback.setText("GREAT WORK! KEEP IT UP!");
            feedback.setFont(Font.font(16));
            layout.getChildren().addAll(smileView, feedback);
            scene = new Scene(layout, 500, 300);
        } else if (returnGrade >= 80 && returnGrade < 90) {
            Image smirk = new Image("https://cdn4.iconfinder.com/data/icons/mood-smiles/80/mood-16-128.png");
            ImageView smirkView = new ImageView();
            smirkView.setImage(smirk);
            feedback.setText("NOT BAD!");
            feedback.setFont(Font.font(16));
            layout.getChildren().addAll(smirkView, feedback);
            scene = new Scene(layout, 500, 450);
        } else if (returnGrade >= 75 && returnGrade < 80){
            Image eh = new Image("https://image.flaticon.com/icons/png/128/42/42890.png");
            ImageView ehView = new ImageView();
            ehView.setImage(eh);
            feedback.setText("I CAN TELL YOU'VE BEEN SLACKING!");
            feedback.setFont(Font.font(16));
            layout.getChildren().addAll(ehView, feedback);
            scene = new Scene(layout, 500, 500);
        } else {
            Image unhappy = new Image("http://findicons.com/files/icons/350/aqua_smiles/128/sad.png");
            ImageView unhappyView = new ImageView();
            unhappyView.setImage(unhappy);
            feedback.setText("THAT'S NOT GOOD...");
            feedback.setFont(Font.font(16));
            layout.getChildren().addAll(unhappyView, feedback);
            scene = new Scene(layout, 500, 400);
        }
        return scene;
    }
}
