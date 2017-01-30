CREATE TABLE `users` (
`id_user` INT NOT NULL AUTO_INCREMENT,
`login` VARCHAR(255) NOT NULL,
`access_token` VARCHAR(255) NOT NULL,
`date_time` DATETIME NULL,
`time_stamp` TIMESTAMP(6) NULL,
PRIMARY KEY (`id_user`),
UNIQUE INDEX `id_user_UNIQUE` (`id_user` ASC),
UNIQUE INDEX `login_UNIQUE` (`login` ASC));

CREATE TABLE `messages` (
`id_message` INT NOT NULL AUTO_INCREMENT,
`id_sender` VARCHAR(255) NOT NULL,
`id_receiver` VARCHAR(255) NOT NULL,
`send_time` DATETIME NOT NULL,
`body` TEXT(255) NOT NULL,
`date_time` DATETIME NULL,
`time_stamp` TIMESTAMP(6) NULL,
PRIMARY KEY (`id_message`),
UNIQUE INDEX `id_message_UNIQUE` (`id_message` ASC));