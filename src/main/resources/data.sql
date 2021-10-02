INSERT INTO user(id, email, first_name, last_name, password, image_link) VALUES (1,'jon','jon','Admin','$2a$12$UA3deYUynO5LMwxnlRq6uOVvSUMHWdQ0QyCqcD9ycoAlheaes5utq',NULL),
(2,'test@test.com','Khushi','Test','$2a$12$UA3deYUynO5LMwxnlRq6uOVvSUMHWdQ0QyCqcD9ycoAlheaes5utq',NULL);
INSERT INTO role(id, name) VALUES (1,'ROLE_USER'),(2,'ROLE_ADMIN');
INSERT INTO users_roles( user_id, role_id) VALUES (1,2),(2,1);
INSERT INTO breed(id, name) VALUES (1,'Labrador Retrievers'),(2, 'German Shepherd'),(3, 'Golden Retrievers'),(4, 'French Bulldogs'), (5,'Bulldogs, Beagles'),
 (6,'Poodles'), (7,'Rottweilers'), (8,'Yorkshire Terriers'), (9,'Boxers'), (10,'Dachshunds'), (11,'Great Danes');