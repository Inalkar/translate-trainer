package com.inalkar.trainer.storage.entity;

import com.inalkar.daf.storage.api.entity.annotation.Column;
import com.inalkar.daf.storage.api.entity.annotation.Table;

@Table("WORD")
public class Word {
    
    private Integer id;
    @Column("group_id") public String groupId;
    public String eng;
    public String rus;
    @Column("rus_alt1") public String rusAlt1;
    @Column("rus_alt2") public String rusAlt2;
    @Column("rus_alt3") public String rusAlt3;

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", groupId='" + groupId + '\'' +
                ", eng='" + eng + '\'' +
                ", rus='" + rus + '\'' +
                ", rusAlt1='" + rusAlt1 + '\'' +
                ", rusAlt2='" + rusAlt2 + '\'' +
                ", rusAlt3='" + rusAlt3 + '\'' +
                '}';
    }
    
}
