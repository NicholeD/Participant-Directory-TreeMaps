package com.kenzie.trees.library;

import com.kenzie.trees.library.treestructure.BookNode;

/**
 * Application to test traversing Binary Trees and Binary Search Trees.
 *
 * Represents a Library with a collection of books.
 */
public class LibraryService {

    /**
     * The root node of our tree of Books.
     * Assume this tree is sorted by ISBN.
     * The tree and its nodes should not be modified by our application.
    */
    private final BookNode books;

    /**
     * Constructs our library with a default tree of books.
     * Assume this tree is sorted by ISBN.
     *
     * @param books The root node of a tree of Books
     */
    public LibraryService(final BookNode books) {
        this.books = books;
    }

    /**
     * Determines whether or not a book is in the library
     * by searching our tree for a book with the given ISBN.
     *
     * @param isbn A given ISBN to search our library for
     * @return True if a book with the given ISBN is in our library and
     *         false otherwise
     */
    public boolean isBookInLibraryByIsbn(String isbn) {
        if (isbn == null || isbn.isEmpty()) {
            return false;
        } else {
            return binarySearch(books, isbn);
        }
    }

    /**
     * Determines whether or not a book is in the library
     * by searching our tree for a book with the given Title and Author.
     *
     * @param title A given title to search our library for, alongside an author's name
     * @param author The name of a given author to search our library for, alongside a title
     * @return True if a book with the given title and author is in our library, and
     *         false otherwise
     */
    public boolean isBookInLibraryByTitleAndAuthor(String title, String author) {
        if (title == null || author == null) {
            return false;
        }
        return recursiveDepthFirstSearch(books, title, author);
    }

    public boolean binarySearch(BookNode node, String isbn) {
        int comparison = node.getBook().getIsbn().compareTo(isbn);

        if (comparison == 0) {
            return true;
        } else if (comparison > 0 && node.getLeft() != null) {
            return binarySearch(node.getLeft(), isbn);
        } else if (comparison < 0 && node.getRight() != null) {
            return binarySearch(node.getRight(), isbn);
        } else {
            return false;
        }
    }

    public boolean recursiveDepthFirstSearch(BookNode node, String title, String author) {
        if (node == null) {
            return false;
        }

        return  (node.getBook().getAuthor() == author)
                && (node.getBook().getTitle() == title)
                || recursiveDepthFirstSearch(node.getLeft(), title, author)
                || recursiveDepthFirstSearch(node.getRight(), title, author);
    }
}
