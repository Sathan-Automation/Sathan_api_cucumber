Feature: library


@LibraryWork01
Scenario: As an existing authorized user,I retrieve a list of books available for me in the library.
I will assign a book to myself and later on return it.

  Given As a Registered user I should be able to Generate Token
  And I Should validate authorization
  And I should be able to retrieve all the books in the library
  And I Should assign a book to myself
  When I should delete the book that has been assigned to me
  Then I should view my information to check any books are available in my name
