-- Module 6 Assignment - RDBMS
-- SQL Exercises (1-7)
-- @author Tim Ning

-- 1. How many copies of the book titled The Lost Tribe are owned by the library
-- branch whose name is "Sharpstown"?
SELECT bc.noOfCopies
FROM tbl_book_copies bc
INNER JOIN tbl_library_branch lb ON bc.branchId = lb.branchId
INNER JOIN tbl_book b ON b.bookId = bc.bookId
WHERE title = 'The Lost Tribe' AND branchName = 'Sharpstown'
;

-- 2. How many copies of the book titled The Lost Tribe are owned by each
-- library branch?
SELECT lb.branchName, bc.noOfCopies
FROM tbl_book_copies bc
INNER JOIN tbl_library_branch lb ON bc.branchId = lb.branchId
INNER JOIN tbl_book b ON b.bookId = bc.bookId
WHERE b.title = 'The Lost Tribe'
;

-- 3. Retrieve the names of all borrowers who do not have any books checked out.
SELECT a.name
FROM tbl_borrower a
LEFT JOIN tbl_book_loans b ON a.cardNo = b.cardNo
WHERE b.cardNo IS NULL
;

-- 4. For each book that is loaned out from the "Sharpstown" branch and whose DueDate
-- is today, retrieve the book title, the borrower's name, and the borrower's address.
SELECT b.title, bo.name, bo.address
FROM tbl_book_loans bl
INNER JOIN tbl_borrower bo ON bl.cardNo = bo.cardNo
INNER JOIN tbl_book b ON bl.bookId = b.bookId
INNER JOIN tbl_library_branch lb ON lb.branchId = bl.branchId
WHERE branchName = 'Sharpstown' AND dueDate LIKE '2018-10-07%'
;

-- 5. For each library branch, retrieve the branch name and the total number of
-- books loaned out from that branch.
SELECT lb.branchName, COUNT(*) AS 'count'
FROM tbl_library_branch lb
INNER JOIN tbl_book_loans bl ON bl.branchId = lb.branchId
GROUP BY branchName
;

-- 6. Retrieve the names, addresses, and number of books checked out for all
-- borrowers who have more than five books checked out.
SELECT bo.name, bo.address, COUNT(*) AS 'count'
FROM tbl_book_loans bl
INNER JOIN tbl_borrower bo ON bl.cardNo = bo.cardNo
INNER JOIN tbl_book b ON bl.bookId = b.bookId
INNER JOIN tbl_library_branch lb ON lb.branchId = bl.branchId
GROUP BY name
HAVING count > 5
;

-- 7. For each book authored (or co-authored) by "Stephen King", retrieve the title
-- and the number of copies owned by the library branch whose name is "Central".
SELECT b.title, bc.noOfCopies
FROM tbl_book_copies bc
INNER JOIN tbl_library_branch lb ON bc.branchId = lb.branchId
INNER JOIN tbl_book b ON b.bookId = bc.bookId
INNER JOIN tbl_author a ON b.authId = a.authorId
WHERE authorName = 'Stephen King' AND branchName = 'Central'
;

