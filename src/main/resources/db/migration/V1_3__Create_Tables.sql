
CREATE TABLE `Chat`.`users` (
`id` INT NOT NULL AUTO_INCREMENT,
`login` VARCHAR(255) NOT NULL,
`access_token` VARCHAR(255) NOT NULL,
`created_date` DATETIME  NOT NULL DEFAULT NOW(),
`modified_date` TIMESTAMP  NOT NULL,
PRIMARY KEY (`id`),
UNIQUE INDEX `id_UNIQUE` (`id` ASC),
UNIQUE INDEX `login_UNIQUE` (`login` ASC));

CREATE TABLE `messages` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`sender_id` int(11) NOT NULL,
`receiver_id` int(11) NOT NULL,
`send_time` DATETIME NOT NULL DEFAULT NOW(),
`body` text NOT NULL,
`created_date` DATETIME  NOT NULL DEFAULT NOW(),
`modified_date` TIMESTAMP  NOT NULL,
PRIMARY KEY (`id`),
UNIQUE KEY `id_UNIQUE` (`id`)
);

CREATE TABLE `rooms` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`name` varchar(255) NOT NULL,
`created_date` DATETIME  NOT NULL DEFAULT NOW(),
`modified_date` TIMESTAMP  NOT NULL,
PRIMARY KEY (`id`),
UNIQUE KEY `id_UNIQUE` (`id`)
);

CREATE TABLE `Chat`.`roomuser` (
`id` INT NOT NULL AUTO_INCREMENT,
`user_id` INT NULL,
`room_id` INT NULL,
`created_date` DATETIME  NOT NULL DEFAULT NOW(),
`modified_date` TIMESTAMP  NOT NULL,
PRIMARY KEY (`id`),
UNIQUE INDEX `id_UNIQUE` (`id` ASC));