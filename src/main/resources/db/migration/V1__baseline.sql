DROP FUNCTION IF EXISTS uuid_v4s;
DELIMITER $$
$$
CREATE FUNCTION uuid_v4s()
RETURNS CHAR(36)
NO SQL
BEGIN
	DECLARE h1 CHAR(8);
    DECLARE h2 CHAR(4);
    DECLARE h3 CHAR(4);
    DECLARE h4 CHAR(4);
    DECLARE h5 CHAR(12);

    SET h1 = HEX(RANDOM_BYTES(4));
    SET h2 = HEX(RANDOM_BYTES(2));
    SET h3 = CONCAT('4', SUBSTRING(HEX(RANDOM_BYTES(2)), 2, 3)); 
    SET h4 = CONCAT(HEX(8 + FLOOR(RAND() * 4)), SUBSTRING(HEX(RANDOM_BYTES(2)), 2, 3)); 
    SET h5 = HEX(RANDOM_BYTES(6));

    RETURN LOWER(CONCAT(h1, '-', h2, '-', h3, '-', h4, '-', h5));
END$$
DELIMITER ;

CREATE TABLE `users` (
  `id` char(36) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `enabled` TINYINT(1) DEFAULT 1 NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `created_by_id` char(36) DEFAULT NULL,
  `updated_by_id` char(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8nakkftyppd62ke6tv7oo5a92` (`created_by_id`),
  KEY `FK6nm9u1qpw9xxphk70xr614m7n` (`updated_by_id`)
);

INSERT INTO `users` (
    `id`,
    `created_at`,
    `updated_at`,
    `enabled`,
    `email`,
    `name`,
    `password`,
    `created_by_id`,
    `updated_by_id`
) VALUES (
    uuid_v4s(),
    NOW(),
    NOW(),
    1,
    'admin@localhost',
    'Admin',
    '$2a$10$H7bYjLNilT6LAEk1eD999eyD1N.E.4e42M8PoEQ47F.ZYi5A4hVaa',
    NULL,
    NULL
);