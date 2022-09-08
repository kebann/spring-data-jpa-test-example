package com.bk.testing.repository;

import com.bk.testing.entity.Note;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesRepository extends JpaRepository<Note, Long> {

  List<Note> findAllByCreatedAtBefore(LocalDateTime creationDate);

  List<Note> findAllByAuthorId(Long authorId);
}
