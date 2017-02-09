package com.inalkar.trainer.view.selectscreen.entity;

import com.inalkar.trainer.storage.entity.Group;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;

public class GroupViewEntity {
    
    public Group group;
    public StringProperty groupNameProperty = new SimpleStringProperty();
    public CheckBox groupCheckBox = new CheckBox();

    public GroupViewEntity(Group group) {
        this.group = group;
        groupNameProperty.setValue(group.name);
        groupCheckBox.textProperty().bind(groupNameProperty);
        groupCheckBox.setSelected(true);
    }
    
}
