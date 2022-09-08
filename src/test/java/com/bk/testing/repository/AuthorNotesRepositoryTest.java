package com.bk.testing.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.bk.testing.entity.Author;
import com.bk.testing.entity.Note;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class AuthorNotesRepositoryTest {

  @Autowired
  AuthorRepository authorRepository;
  @Autowired
  NotesRepository notesRepository;

  @Test
  void testAuthorPersistedWithNotes() {
    var author = new Author();
    author.setFirstName("James");
    author.setLastName("Doe");

    List<Note> notes = IntStream.rangeClosed(0, 3)
        .mapToObj(i -> new Note()
            .setTitle("Note title " + i)
            .setBody("Note body " + i)).toList();

    notes.forEach(author::addNote);

    var savedAuthor = authorRepository.save(author);
    assertThat(savedAuthor.getId()).isNotNull();

    var authorNotes = notesRepository.findAllByAuthorId(author.getId());
    assertThat(authorNotes).hasSize(notes.size());
  }

  @Test
  void testNoteTitleUniqueConstraint() {
    var author = new Author();
    author.setFirstName("James");
    author.setLastName("Doe");

    String title = "First note";

    var note = new Note();
    note.setTitle(title);
    note.setBody("My first note");

    var anotherNote = new Note();
    anotherNote.setTitle(title);
    anotherNote.setBody("My second note");

    author.addNote(note);
    author.addNote(anotherNote);

    assertThatThrownBy(() -> authorRepository.saveAndFlush(author))
        .isInstanceOf(DataIntegrityViolationException.class);
  }

  @Sql("/db/data.sql")
  @Test
  void testNotesCreatedBeforeDate() {
    List<Note> notes = notesRepository.findAllByCreatedAtBefore(LocalDateTime.now());
    assertThat(notes).hasSize(3);
  }
}
