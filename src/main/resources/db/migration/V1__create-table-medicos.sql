
CREATE TABLE medicos (
    id_medico BINARY(16) NOT NULL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL unique,
    documento VARCHAR(255) NOT NULL,
    especialidad ENUM('CARDIOLOGIA', 'GINECOLOGIA', 'ORTOPEDIA', 'PEDIATRIA') NOT NULL,
    calle VARCHAR(255),
    ciudad VARCHAR(255),
    complemento VARCHAR(255),
    distrito VARCHAR(255),
    numero INTEGER
);