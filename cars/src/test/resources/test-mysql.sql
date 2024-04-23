CREATE TABLE IF NOT EXISTS `cars` (
    `id` BIGINT AUTO_INCREMENT,
    `brand` VARCHAR(255),
    `model` VARCHAR(255),
    `price` DECIMAL(38,2),
    `year` VARCHAR(255),
    PRIMARY KEY (`id`)
);

BEGIN;
TRUNCATE cars;

INSERT INTO `cars` (`id`, `brand`, `model`, `price`, `year`) VALUES
    (1, 'Audi', 'Q5', 2000000, '2013'),
    (2, 'Toyota', 'Hilux', 3000000, '2019'),
    (3, 'Volkswagen', 'Vento', 3750000, '2020');

COMMIT;