package org.dci.bookhaven.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "liked_books")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LikedBook {

    @EmbeddedId
    private LikedBookId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    private Book book;

}