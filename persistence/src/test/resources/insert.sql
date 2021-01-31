INSERT INTO certificates
(id, name, description, price, duration, create_date, update_last_date)
VALUES (1, 'Certificate 1', 'Description 1', 100, 10, 1612018316227,1612018555878);
INSERT INTO certificates
(id, name, description, price, duration, create_date, update_last_date)
VALUES (2, 'Certificate 2', 'Description 2', 200, 20, 1612018444447,1612018666668);
INSERT INTO certificates
(id, name, description, price, duration, create_date, update_last_date)
VALUES (3, 'Certificate 3', 'Description 3', 300, 30, 1612018433333,1612018644444);

INSERT INTO tags (id, name) VALUES (1, 'AA');
INSERT INTO tags (id, name) VALUES (2, 'AB');
INSERT INTO tags (id, name) VALUES (3, 'AC');
INSERT INTO tags (id, name) VALUES (4, 'AD');

INSERT INTO certificate_tag (id_certificate, id_tag) VALUES (1,1);
INSERT INTO certificate_tag (id_certificate, id_tag) VALUES (1,2);
INSERT INTO certificate_tag (id_certificate, id_tag) VALUES (2,3);
INSERT INTO certificate_tag (id_certificate, id_tag) VALUES (3,1);





