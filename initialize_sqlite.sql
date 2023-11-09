-- User table
CREATE TABLE User (
    u_userid INTEGER PRIMARY KEY AUTOINCREMENT,
    u_username TEXT NOT NULL UNIQUE,
    u_usertype TEXT NOT NULL
    -- potential constraints, e.g., CHECK(u_usertype IN ('Admin', 'Regular'))
);

-- Comment table
CREATE TABLE Comment (
    c_commentid INTEGER PRIMARY KEY AUTOINCREMENT,
    c_userid INTEGER,
    c_lawid INTEGER,
    c_commenttext TEXT NOT NULL,
    c_timestamp DATE NOT NULL,
    FOREIGN KEY (c_userid) REFERENCES User(u_userid),
    FOREIGN KEY (c_lawid) REFERENCES Law(l_lawid)
);

-- Tag table
CREATE TABLE Tag (
    t_tagid INTEGER PRIMARY KEY AUTOINCREMENT,
    t_tagname TEXT NOT NULL UNIQUE
);

-- LawTag table (associative table for many-to-many relation between Law and Tag)
CREATE TABLE LawTag (
    l_lawtagid INTEGER PRIMARY KEY AUTOINCREMENT,
    l_lawid INTEGER,
    l_tagid INTEGER,
    FOREIGN KEY (l_lawid) REFERENCES Law(l_lawid),
    FOREIGN KEY (l_tagid) REFERENCES Tag(t_tagid)
);

-- Category table
CREATE TABLE Category (
    c_categoryid INTEGER PRIMARY KEY AUTOINCREMENT,
    c_categoryname TEXT NOT NULL UNIQUE
);

-- Law table
CREATE TABLE Law (
    l_lawid INTEGER PRIMARY KEY AUTOINCREMENT,
    l_title TEXT NOT NULL,
    l_description TEXT,
    l_dateenacted DATE NOT NULL,
    l_categoryid INTEGER,
    FOREIGN KEY (l_categoryid) REFERENCES Category(c_categoryid) --keep asking why we need this if we also have assocaiteave 
    --This suggests a many-to-one relationship (many laws can belong to one category). It means that each law is associated with one primary category directly.
    --This can be useful in real-world scenarios where a law might primarily be categorized under "Transportation" but also relate to "Environment" and "Public Safety"
);

-- AuditLog table
CREATE TABLE AuditLog (
    a_logid INTEGER PRIMARY KEY AUTOINCREMENT,
    a_adminid INTEGER,
    a_timestamp DATE NOT NULL,
    FOREIGN KEY (a_adminid) REFERENCES User(u_userid)
);

-- LawCategory associative table (many-to-many relation between Law and Category)
CREATE TABLE LawCategory (
    lc_lawid INTEGER,
    lc_categoryid INTEGER,
    PRIMARY KEY (lc_lawid, lc_categoryid),
    FOREIGN KEY (lc_lawid) REFERENCES Law(l_lawid),
    FOREIGN KEY (lc_categoryid) REFERENCES Category(c_categoryid)
);
