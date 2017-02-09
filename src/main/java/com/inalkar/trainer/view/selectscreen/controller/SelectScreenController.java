package com.inalkar.trainer.view.selectscreen.controller;

import com.inalkar.trainer.storage.entity.Group;
import com.inalkar.trainer.view.selectscreen.entity.GroupViewEntity;
import com.inalkar.trainer.view.trainer.controller.TrainerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static com.inalkar.trainer.Main.storage;

public class SelectScreenController implements Initializable {
    
    private List<GroupViewEntity> groups = new ArrayList<>();
    
    @FXML
    private CheckBox chkRusToEng;

    @FXML
    private FlowPane groupsFlowPannel;

    @FXML
    private CheckBox chkEngToRus;

    @FXML
    void start(ActionEvent event) {
        List<Group> selectedGroup = groups.stream()
                .filter(g -> g.groupCheckBox.isSelected())
                .map(g -> g.group)
                .collect(Collectors.toList());
        
        if (selectedGroup.size() != 0) {
            try {
                TrainerController.openTrainerController(selectedGroup, chkRusToEng.isSelected(), chkEngToRus.isSelected());
            } catch (IOException e) {
                System.out.println("Error opening trainer screen.");
                e.printStackTrace();
            }
        }
    }
    
    @FXML
    void selectAllGroups(ActionEvent event) {
        groups.forEach(groupViewEntity -> groupViewEntity.groupCheckBox.setSelected(true));
    }

    @FXML
    void unselectAllGroups(ActionEvent event) {
        groups.forEach(groupViewEntity -> groupViewEntity.groupCheckBox.setSelected(false));
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        storage.select(Group.class).all().stream().forEach(
                group -> {
                    GroupViewEntity groupViewEntity = new GroupViewEntity(group);
                    groups.add(groupViewEntity);
                    groupsFlowPannel.getChildren().add(groupViewEntity.groupCheckBox);
                }
        );
    }
    
}
