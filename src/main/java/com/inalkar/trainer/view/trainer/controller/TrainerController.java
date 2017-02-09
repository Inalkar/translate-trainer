package com.inalkar.trainer.view.trainer.controller;

import com.inalkar.trainer.storage.entity.Group;
import com.inalkar.trainer.storage.entity.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.inalkar.daf.storage.impl.WhereColumn.columnExpr;
import static com.inalkar.trainer.Main.storage;

public class TrainerController {
    
    private static boolean rusToEng;
    private static boolean engToRus;
    private static List<Word> words;
    private boolean isCurrentEng;
    private Word currentWord;
    private Stage stage;
    
    @FXML
    private Label lblWord;

    @FXML
    private Label lblError;

    @FXML
    private TextField txtTranslation;

    @FXML
    void finish(ActionEvent event) {
        stage.close();
    }

    @FXML
    void nextWord(ActionEvent event) {
        if (currentWord != null) {
            if (!validateWord()) {
                lblError.setText("Not valid translation");
                return;
            }
        }
        lblError.setText("");
        txtTranslation.setText("");
        
        int wordIndex = ThreadLocalRandom.current().nextInt(0, words.size());
        Word word = words.get(wordIndex);
        currentWord = word;

        if (rusToEng && engToRus) {
            int random = ThreadLocalRandom.current().nextInt(0, 2);
            isCurrentEng = random == 0;
            lblWord.setText(isCurrentEng ? word.eng : word.rus);
        } else if (rusToEng) {
            isCurrentEng = false;
            lblWord.setText(word.rus);
        } else if (engToRus) {
            isCurrentEng = true;
            lblWord.setText(word.eng);
        }
    }
    
    @FXML
    void skip(ActionEvent event) {
        currentWord = null;
        nextWord(null);
    }
    
    public static void openTrainerController(final List<Group> groups, final boolean rusToEng, final boolean engToRus) 
            throws IOException 
    {
        TrainerController.rusToEng = rusToEng;
        TrainerController.engToRus = engToRus;
        TrainerController.words = storage.select(Word.class)
                .where(
                        columnExpr("group_id")
                                .in(groups.stream()
                                        .map(g -> (Integer) g.id)
                                        .toArray())
                )
                .all()
                .stream().collect(Collectors.toList());

        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(
                TrainerController.class.getClassLoader().getResource("TrainerWindow.fxml")
        );
        Parent root = fxmlLoader.load();
        TrainerController controller = fxmlLoader.getController();
        stage.setTitle("Translate Trainer");
        stage.setScene(new Scene(root, 300, 170));
        stage.getScene().getStylesheets().add(
                TrainerController.class.getClassLoader().getResource("darcula.css").toExternalForm()
        );
        stage.setMinHeight(170);
        stage.setMaxHeight(170);
        stage.show();
        
        controller.nextWord(null);
        controller.stage = stage;
    }
    
    private boolean validateWord() {
        String translation = txtTranslation.getText(); 
        return isCurrentEng 
                ? translation.equalsIgnoreCase(currentWord.rus) 
                    || translation.equalsIgnoreCase(currentWord.rusAlt1)
                    || translation.equalsIgnoreCase(currentWord.rusAlt2)
                    || translation.equalsIgnoreCase(currentWord.rusAlt3)
                : translation.equalsIgnoreCase(currentWord.eng);
    }
    
}
