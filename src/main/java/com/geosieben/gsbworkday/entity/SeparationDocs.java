package com.geosieben.gsbworkday.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "separationdocs")
public class SeparationDocs {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int docId;
private String document;
@ManyToOne
@JoinColumn(name="SeparationId")
private Separation separation;
public int getDocId() {
    return docId;
}
public void setDocId(int docId) {
    this.docId = docId;
}
public String getDocument() {
    return document;
}
public void setDocument(String document) {
    this.document = document;
}
public Separation getSeparation() {
    return separation;
}
public void setSeparation(Separation separation) {
    this.separation = separation;
}
public SeparationDocs(int docId, String document, Separation separation) {
    this.docId = docId;
    this.document = document;
    this.separation = separation;
}
public SeparationDocs() {
}


}
