package com.bk.testing.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Table(name = "authors")
@Entity
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(exclude = "notes")
public class Author {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String firstName;
  @Column(nullable = false)
  private String lastName;
  @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,
      CascadeType.MERGE}, orphanRemoval = true)
  @Setter(AccessLevel.PRIVATE)
  private List<Note> notes = new ArrayList<>();


  public void addNote(Note note) {
    note.setAuthor(this);
    notes.add(note);
  }
}
