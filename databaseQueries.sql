--insert into the User table (Admin, Regular)
INSERT INTO User (u_username, u_usertype) VALUES ('AdminUser', 'Admin');
INSERT INTO User (u_username, u_usertype) VALUES ('RegularUser', 'Regular');
INSERT INTO User (u_username, u_usertype) VALUES ('RegularUser2', 'Regular');
INSERT INTO User (u_username, u_usertype) VALUES ('AdminUser2', 'Admin');
INSERT INTO User (u_username, u_usertype) VALUES ('AdminUser3', 'Admin');


--insert  categories
INSERT INTO Category (c_categoryname) VALUES ('Agriculture');
INSERT INTO Category (c_categoryname) VALUES ('Transportation');

--insert  laws
INSERT INTO Law (l_title, l_description, l_dateenacted) VALUES ('Agriculture Law A', 'Description about this law', '2023-10-25');
INSERT INTO Law (l_title, l_description, l_dateenacted) VALUES ('Transportation Law B', 'Description about another law', '2023-08-12');
INSERT INTO Law (l_title, l_description, l_dateenacted) VALUES ('Some other Law C', 'Description about this law', '2023-10-26'); --to show the many to many on the tags side. this law and law b will share the safety
--tag making the many to many on the tag side 'Law B' and 'Law C'
INSERT INTO Law (l_title, l_description, l_dateenacted) VALUES ('Environmental Law D', 'Description about environmental regulations', '2023-11-01');
INSERT INTO Law (l_title, l_description, l_dateenacted) VALUES ('New Law E', 'Description for new law without comments, categories, or tags.', '2023-11-03');


--insert  into LawCategory
INSERT INTO LawCategory (lc_lawid, lc_categoryid) VALUES (1, 1); -- Assuming IDs generated are sequential
INSERT INTO LawCategory (lc_lawid, lc_categoryid) VALUES (2, 2);
INSERT INTO LawCategory (lc_lawid, lc_categoryid) VALUES (4, 1);
INSERT INTO LawCategory (lc_lawid, lc_categoryid) VALUES (3, 2);

--insert into Tag
INSERT INTO Tag (t_tagname) VALUES ('AG'); -- aka Environment
INSERT INTO Tag (t_tagname) VALUES ('Safety'); -- aka Public Safety
INSERT INTO Tag (t_tagname) VALUES ('farm'); -- many to many w /Env
INSERT INTO Tag (t_tagname) VALUES ('Env');

--insert into the LawTag
INSERT INTO LawTag (l_lawid, l_tagid) VALUES (1, 1); -- this lawtag shows the many to many from the Law side .. this law

-- is associated with two tags AG an farm
INSERT INTO LawTag (l_lawid, l_tagid) VALUES (2, 2);
INSERT INTO LawTag (l_lawid, l_tagid) VALUES (1, 3);
INSERT INTO LawTag (l_lawid, l_tagid) VALUES (3, 2); -- this lawtag shows the many to many from the Tag side.. this tag
--(2) 'Safety' is associated with two laws ()
INSERT INTO LawTag (l_lawid, l_tagid) VALUES (4, 4);



--insert into AuditLog:
INSERT INTO AuditLog (a_adminid, a_timestamp) VALUES (1, '2023-10-27');
INSERT INTO AuditLog (a_adminid, a_timestamp) VALUES (4, '2023-11-02');
INSERT INTO AuditLog (a_adminid, a_timestamp) VALUES (5, '2023-11-02');


--insert into comment
INSERT INTO Comment (c_userid, c_lawid, c_commenttext, c_timestamp) VALUES (1, 1, 'This is a comment on Agriculture Law A.', '2023-10-27');
INSERT INTO Comment (c_userid, c_lawid, c_commenttext, c_timestamp) VALUES (1, 3, 'This is a comment on a Transportation Law.', '2023-11-01');
INSERT INTO Comment (c_userid, c_lawid, c_commenttext, c_timestamp) VALUES (2, 4, 'This is a comment on a Environmental Law D.', '2023-11-01');
INSERT INTO Comment (c_userid, c_lawid, c_commenttext, c_timestamp) VALUES (3, 2, 'This law is pivotal for modern transportation.', '2023-11-02');
INSERT INTO Comment (c_userid, c_lawid, c_commenttext, c_timestamp) VALUES (4, 2, 'This comment is to support query 3', '2023-11-02');
INSERT INTO Comment (c_userid, c_lawid, c_commenttext, c_timestamp) VALUES (5, 2, 'This comment is to support query 3', '2023-11-02');
INSERT INTO Comment (c_userid, c_lawid, c_commenttext, c_timestamp) VALUES (5, 3, 'This comment is to support query 3', '2023-10-02');


-- CUSTOM QUERIES

--1 Done
--find all users who have commented on a law that falls under both 'Agriculture' and 'Transportation' categories
SELECT DISTINCT u.u_username 
FROM User u
JOIN Comment c ON u.u_userid = c.c_userid
JOIN Law l ON c.c_lawid = l.l_lawid
JOIN LawCategory lc ON l.l_lawid = lc.lc_lawid
JOIN Category cat ON lc.lc_categoryid = cat.c_categoryid
WHERE cat.c_categoryname IN ('Agriculture', 'Transportation')
GROUP BY u.u_username
HAVING COUNT(DISTINCT cat.c_categoryname) = 2; --makes sure only returns users who commented on BOTH not or the categories

--Done
--2
--Get the count of laws in each category along with the average number of comments per law in that category:
SELECT cat.c_categoryname, COUNT(DISTINCT l.l_lawid) AS NumberOfLaws, AVG(CommentCount) AS AverageCommentsPerLaw
FROM Category cat
JOIN LawCategory lc ON cat.c_categoryid = lc.lc_categoryid
JOIN Law l ON lc.lc_lawid = l.l_lawid
LEFT JOIN (
    SELECT c.c_lawid, COUNT(c.c_commenttext) AS CommentCount
    FROM Comment c
    GROUP BY c.c_lawid
) AS CommentData ON l.l_lawid = CommentData.c_lawid
GROUP BY cat.c_categoryname;
-- will return result of 
--Agriculture|1|1.0
--Transportation|2|2.5 there is two laws with category Transportation 


--Done
-- 2
--Lists the titles of laws along with their associated tags and the username of the user who commented on them
SELECT l.l_title, GROUP_CONCAT(DISTINCT t.t_tagname) AS Tags, u.u_username AS CommentedBy
FROM Law l
LEFT JOIN (
  SELECT l.l_lawid, t.t_tagname
  FROM Law l
  LEFT JOIN LawTag lt ON l.l_lawid = lt.l_lawid
  LEFT JOIN Tag t ON lt.l_tagid = t.t_tagid
) t ON l.l_lawid = t.l_lawid
LEFT JOIN Comment c ON l.l_lawid = c.c_lawid
LEFT JOIN User u ON c.c_userid = u.u_userid
GROUP BY l.l_title, u.u_username
ORDER BY l.l_title;

--Done
--3
--Retrieve laws with the most comments, ordered by the count of comments:
SELECT l.l_title, COUNT(c.c_commenttext) AS CommentCount
FROM Law l
LEFT JOIN Comment c ON l.l_lawid = c.c_lawid
GROUP BY l.l_title
ORDER BY CommentCount DESC
LIMIT 3;

--returns the law/laws associated with the category 'Agriculture'
SELECT l.l_title, l.l_description
FROM Law l
JOIN LawCategory lc ON l.l_lawid = lc.lc_lawid
JOIN Category c ON lc.lc_categoryid = c.c_categoryid
WHERE c.c_categoryname = 'Agriculture';

-- this query we can see for example which tags is 'AdminUser' which tags are his comments falling under.. showing his expertise in the area 
--result = Ag,Farm, Safety
SELECT u.u_username, GROUP_CONCAT(DISTINCT t.t_tagname) AS Tags
FROM User u
JOIN Comment c ON u.u_userid = c.c_userid
JOIN Law l ON c.c_lawid = l.l_lawid
JOIN LawTag lt ON l.l_lawid = lt.l_lawid
JOIN Tag t ON lt.l_tagid = t.t_tagid
WHERE u.u_username = 'AdminUser' -- replace with actual username
GROUP BY u.u_username;



--Done
--Find users who have commented on laws enacted after a certain date and list the comments:
SELECT u.u_username, l.l_title, c.c_commenttext
FROM Comment c
JOIN Law l ON c.c_lawid = l.l_lawid
JOIN User u ON c.c_userid = u.u_userid
WHERE l.l_dateenacted > '2023-01-01'
ORDER BY l.l_dateenacted;



--Done
--List users and the titles of laws they have commented on, along with the count of their comments, 
--for users who have commented on more than one law
SELECT u.u_username, GROUP_CONCAT(DISTINCT l.l_title) AS LawTitles, COUNT(c.c_commenttext) AS TotalComments
FROM User u
JOIN Comment c ON u.u_userid = c.c_userid
JOIN Law l ON c.c_lawid = l.l_lawid
GROUP BY u.u_username
HAVING COUNT(DISTINCT l.l_lawid) > 1;

--Done
-- laws that have not been commented on and also have not been associated with any categories or tags, 
SELECT l.l_title
FROM Law l
LEFT JOIN LawCategory lc ON l.l_lawid = lc.lc_lawid
LEFT JOIN LawTag lt ON l.l_lawid = lt.l_lawid
WHERE l.l_lawid NOT IN (SELECT c_lawid FROM Comment)
AND lc.lc_categoryid IS NULL
AND lt.l_tagid IS NULL;

--returns the username of the user/users who commented on the 'Agriculture' Law
SELECT DISTINCT u.u_username 
FROM User u 
JOIN Comment c ON u.u_userid = c.c_userid 
JOIN Law l ON c.c_lawid = l.l_lawid 
WHERE l.l_title = 'Agriculture Law A';

-- returns the tags associated with teh first Law .. will return AG , farm
SELECT t.t_tagname 
FROM Tag t 
JOIN LawTag lt ON t.t_tagid = lt.l_tagid 
WHERE lt.l_lawid = 1;


--will return the log history of users
SELECT u.u_username, a.a_timestamp FROM User u JOIN AuditLog a ON u.u_userid = a.a_adminid; 

-- now lets say we delete all three laws .. we would have to also delete the lawTag and LawCategory entries also 
--why ? because these associative entities hold the many to many relationships therefore if 

-- will delete the comment tha mentions "Agriculture"
DELETE FROM Comment 
WHERE c_commenttext LIKE '%Agriculture%';

--update the Comment that was inserted
UPDATE Comment SET c_commenttext = 'Comment updated.' WHERE c_userid = 1 AND c_lawid = 1;
