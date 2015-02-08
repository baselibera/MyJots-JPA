INSERT INTO `MyJotsDB`.`User` (`idAppUser`, `username`, `name`, `surname`, `email`) VALUES ('1', 'admin', 'Admin', 'Admin', 'admin@myjots.com');
INSERT INTO `MyJotsDB`.`User` (`idAppUser`, `username`, `name`, `surname`, `email`) VALUES ('2', 'editor', 'Editor', 'Editor', 'editor@myjots.com');
INSERT INTO `MyJotsDB`.`User` (`idAppUser`, `username`, `name`, `surname`, `email`) VALUES ('3', 'user', 'User', 'User', 'user@myjots.com');

INSERT INTO `MyJotsDB`.`Role` (`idRole`, `name`) VALUES ('1', 'Admin');
INSERT INTO `MyJotsDB`.`Role` (`idRole`, `name`) VALUES ('2', 'Editor');
INSERT INTO `MyJotsDB`.`Role` (`idRole`, `name`) VALUES ('3', 'Reader');

INSERT INTO `MyJotsDB`.`User_Role` (`idAppUser`, `idRole`) VALUES ('1', '1');
INSERT INTO `MyJotsDB`.`User_Role` (`idAppUser`, `idRole`) VALUES ('2', '2');
INSERT INTO `MyJotsDB`.`User_Role` (`idAppUser`, `idRole`) VALUES ('3', '3');

