package com.inalkar.trainer;

import com.inalkar.daf.storage.api.IStorage;
import com.inalkar.daf.storage.api.StorageConfiguration;
import com.inalkar.daf.storage.jdbc.JDBCStorageConfiguration;
import com.inalkar.saa.h2.H2StorageDriver;
import com.inalkar.trainer.storage.entity.Word;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static IStorage storage; 
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        configureStorage();
        
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("SelectScreenWindow.fxml"));
        primaryStage.setTitle("Translate Trainer");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.getScene().getStylesheets().add(
                getClass().getClassLoader().getResource("darcula.css").toExternalForm()
        );
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        if (storage != null) storage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    private void configureStorage() throws Exception {
        StorageConfiguration storageConfiguration = 
                new JDBCStorageConfiguration("trainer", H2StorageDriver.H2_STORAGE_DRIVER);
        
        storageConfiguration.addParameter(JDBCStorageConfiguration.URL, "jdbc:h2:./db/vocabulary;FILE_LOCK=NO");
        storageConfiguration.addParameter(JDBCStorageConfiguration.USER_NAME, "sa");
        storageConfiguration.addParameter(JDBCStorageConfiguration.PASSWORD, "");
        
        storage = new H2StorageDriver().createStorage(storageConfiguration);
    }
    
}
