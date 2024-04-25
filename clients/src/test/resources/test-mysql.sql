CREATE TABLE IF NOT EXISTS `clients` (
    `id` BIGINT AUTO_INCREMENT,
    `name` VARCHAR(255),
    `email` VARCHAR(255),
    `phone` VARCHAR(255),
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `cars` (
    `id` BIGINT,
    `client_id` BIGINT,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`client_id`) REFERENCES `clients`(`id`)
);

BEGIN;
TRUNCATE TABLE clients;

INSERT INTO `clients` (`id`, `name`, `email`, `phone`) VALUES
    (1, 'Julian', 'ju@gmail.com', '182638721'),
    (2, 'Mauricio', 'ma@gmail.com', '450689045'),
    (3, 'Jorge', 'jo@gmail.com', '309850943');

TRUNCATE TABLE cars;

INSERT INTO `cars` (`id`, `client_id`) VALUES
    (1, 1),
    (10, 2),
    (15, 3);

COMMIT;