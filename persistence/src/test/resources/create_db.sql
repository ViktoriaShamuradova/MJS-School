CREATE TABLE certificates (
    id int NOT NULL AUTO_INCREMENT,
    name varchar(45) NOT NULL,
    description varchar(45) NOT NULL,
    price decimal(8,2) NOT NULL,
    duration int(11) NOT NULL,
    create_date bigint(20) NOT NULL,
    update_last_date bigint(20) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY id (id)
);
CREATE TABLE tags (
    id int(11) NOT NULL AUTO_INCREMENT,
    name varchar(45) NOT NULL
);

CREATE TABLE certificate_tag (
    id_certificate int(11) NOT NULL,
    id_tag int(11) NOT NULL,
    CONSTRAINT id_cert FOREIGN KEY (id_certificate) REFERENCES certificates (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT id_t FOREIGN KEY (id_tag) REFERENCES tags (id) ON UPDATE CASCADE
);



