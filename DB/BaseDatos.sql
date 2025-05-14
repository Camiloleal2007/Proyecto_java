drop database GestionCita;
Create database GestionCita;
use GestionCita;


CREATE TABLE Usuario (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100),
    documento VARCHAR(20) unique ,
    telefono VARCHAR(20),
    especialidad VARCHAR(100), 
    correo VARCHAR(100),
    fecha_nacimiento DATE,
    contraseña VARCHAR(50) NOT NULL,
    Rol Enum('Medico','Paciente','Admin')
);
INSERT INTO Usuario (nombre, documento, telefono, especialidad, correo, fecha_nacimiento, contraseña, Rol)
VALUES ('CamiloLeal', '1082911004', '3011946015', NULL, 'Camiloandreslealospino@gmail.com', '2007-02-20', 'camiloleal2007', 'Admin');
INSERT INTO Usuario (nombre, documento, telefono, especialidad, correo, fecha_nacimiento, contraseña, Rol)
VALUES ('Paciente1', '123456789', '3011946015', NULL, 'Camiloandreslealospino@gmail.com', '2007-02-20', 'Paciente1', 'Paciente');
INSERT INTO Usuario (nombre, documento, telefono, especialidad, correo, fecha_nacimiento, contraseña, Rol)
VALUES ('Paciente2', '12345', '3011946015', NULL, 'Camiloandreslealospino@gmail.com', '2007-02-20', 'Paciente2', 'Paciente');
INSERT INTO Usuario (nombre, documento, telefono, especialidad, correo, fecha_nacimiento, contraseña, Rol) 
VALUES 
    ('MedicoGeneral1', '10001', '3001111111', 'General', 'medicogeneral1@example.com', '1980-05-15', 'MedicoGeneral1', 'Medico'),
    ('MedicoGeneral2', '10002', '3002222222', 'General', 'medicogeneral2@example.com', '1985-07-20', 'MedicoGeneral2', 'Medico'),
    ('MedicoGeneral3', '10003', '3003333333', 'General', 'medicogeneral3@example.com', '1990-02-10', 'MedicoGeneral3', 'Medico');

INSERT INTO Usuario (nombre, documento, telefono, especialidad, correo, fecha_nacimiento, contraseña, Rol) 
VALUES 
    ('Odontologo1', '20001', '3101111111', 'Odontología', 'odontologo1@example.com', '1982-09-25', 'Odontologo1', 'Medico'),
    ('Odontologo2', '20002', '3102222222', 'Odontología', 'odontologo2@example.com', '1988-11-30', 'Odontologo2', 'Medico'),
    ('Odontologo3', '20003', '3103333333', 'Odontología', 'odontologo3@example.com', '1995-06-05', 'Odontologo3', 'Medico');

INSERT INTO Usuario (nombre, documento, telefono, especialidad, correo, fecha_nacimiento, contraseña, Rol) 
VALUES 
    ('Pediatra1', '30001', '3201111111', 'Pediatría', 'pediatra1@example.com', '1983-04-12', 'Pediatra1', 'Medico'),
    ('Pediatra2', '30002', '3202222222', 'Pediatría', 'pediatra2@example.com', '1987-08-18', 'Pediatra2', 'Medico'),
    ('Pediatra3', '30003', '3203333333', 'Pediatría', 'pediatra3@example.com', '1992-01-25', 'Pediatra3', 'Medico');
select * from Usuario;

create table CitasActivas(
    id INT AUTO_INCREMENT PRIMARY KEY,
    paciente_nombre VARCHAR(100),
    medico_nombre VARCHAR(100),
    fecha DATE,
    hora VARCHAR(20),
    tipo_cita VARCHAR(100)
);

create table CitasAtendidas(
    id INT AUTO_INCREMENT PRIMARY KEY,
    paciente_nombre VARCHAR(100),
    medico_nombre VARCHAR(100),
    fecha DATE,
    hora VARCHAR(20),
    tipo_cita VARCHAR(100),
    diagnostico varchar(500),
    solucion varchar(5000)
);

create table Horario(
horario varchar(100),
lunes varchar(50),
martes varchar(50),
miercoles varchar(50),
jueves varchar(50),
viernes varchar(50),
sabado varchar(50),
domingo varchar(50)
);
INSERT INTO Horario (horario, lunes, martes, miercoles, jueves, viernes, sabado, domingo) 
VALUES 
('7:00 AM - 7:45 AM', 'Disponible', 'Disponible', 'Disponible', 'Disponible', 'Disponible', 'Disponible', 'Disponible'),
('8:00 AM - 8:45 AM', 'Disponible', 'Disponible', 'Disponible', 'Disponible', 'Disponible', 'Disponible', 'Disponible'),
('9:00 AM - 9:45 AM', 'Disponible', 'Disponible', 'Disponible', 'Disponible', 'Disponible', 'Disponible', 'Disponible'),
('10:00 AM - 10:45 AM', 'Disponible', 'Disponible', 'Disponible', 'Disponible', 'Disponible', 'Disponible', 'Disponible'),
('11:00 AM - 11:45 AM', 'Disponible', 'Disponible', 'Disponible', 'Disponible', 'Disponible', 'Disponible', 'Disponible'),
('11:50 AM - 12:55 PM', 'No Disponible', 'No Disponible', 'No Disponible', 'No Disponible', 'No Disponible', 'No Disponible', 'No Disponible'),
('1:00 PM - 1:45 PM', 'Disponible', 'Disponible', 'Disponible', 'Disponible', 'Disponible', 'Disponible', 'Disponible'),
('2:00 PM - 2:45 PM', 'Disponible', 'Disponible', 'Disponible', 'Disponible', 'Disponible', 'Disponible', 'Disponible'),
('3:00 PM - 3:45 PM', 'Disponible', 'Disponible', 'Disponible', 'Disponible', 'Disponible', 'Disponible', 'Disponible'),
('4:00 PM - 4:45 PM', 'Disponible', 'Disponible', 'Disponible', 'Disponible', 'Disponible', 'Disponible', 'Disponible'),
('5:00 PM - 5:45 PM', 'Disponible', 'Disponible', 'Disponible', 'Disponible', 'Disponible', 'Disponible', 'Disponible');

select * from Horario;
select * from CitasActivas;
select * from CitasAtendidas;
DELIMITER //
CREATE PROCEDURE validar_login( in p_documento VARCHAR(20), in p_contraseña VARCHAR(50),OUT rol_usuario VARCHAR(20)
)
BEGIN
    SELECT Rol INTO rol_usuario
    FROM Usuario
    WHERE documento = p_documento AND contraseña = p_contraseña;
    
    IF rol_usuario IS NULL THEN
        SET rol_usuario = 'No encontrado';
    END IF;
END //
DELIMITER ;


DELIMITER //
create procedure Procedimientos (numero int, id int) begin 
if numero=1 then
    select nombre from Usuario where documento=id;
end if;
if numero=2 then
    select correo from Usuario where documento=id;
end if;
if numero=3 then
	select telefono from Usuario where documento=id;
end if;
end//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE CRUD_Usuario(accion ENUM('INSERT', 'UPDATE', 'DELETE'), p_documento VARCHAR(20), p_nombre VARCHAR(100), p_telefono VARCHAR(20), 
p_especialidad VARCHAR(100), p_correo VARCHAR(100), p_fecha_nacimiento DATE, p_contraseña VARCHAR(50),p_Rol ENUM('Medico','Paciente','Admin'),OUT resultado TINYINT
)
BEGIN
    CASE accion
        WHEN 'INSERT' THEN
            IF NOT EXISTS (SELECT 1 FROM Usuario WHERE documento = p_documento) THEN
                INSERT INTO Usuario (documento, nombre, telefono, especialidad, correo, fecha_nacimiento, contraseña, Rol)
                VALUES (p_documento, p_nombre, p_telefono, p_especialidad, p_correo, p_fecha_nacimiento, p_contraseña, p_Rol);
                SET resultado = 1;
            ELSE
                SET resultado = 0;
            END IF;
        WHEN 'UPDATE' THEN
            UPDATE Usuario 
            SET nombre = p_nombre, telefono = p_telefono, especialidad = p_especialidad, 
                correo = p_correo, fecha_nacimiento = p_fecha_nacimiento, 
                contraseña = p_contraseña, Rol = p_Rol
            WHERE documento = p_documento;
        WHEN 'DELETE' THEN
            DELETE FROM Usuario WHERE documento = p_documento;
    END CASE;
END //
DELIMITER ;

DELIMITER //

CREATE PROCEDURE EliminarCita( cita_fecha DATE, cita_hora VARCHAR(20))
BEGIN
    DELETE FROM CitasActivas WHERE fecha = cita_fecha AND hora = cita_hora;
END //

DELIMITER ;

DELIMITER //
CREATE FUNCTION obtenerMedicoPorEspecialidad(especialidadBuscada VARCHAR(100))
RETURNS VARCHAR(100)
DETERMINISTIC
READS SQL DATA
BEGIN
    DECLARE nombreMedico VARCHAR(100);

    SELECT nombre
    INTO nombreMedico
    FROM Usuario
    WHERE Rol= 'Medico'
      AND especialidad = especialidadBuscada
    ORDER BY RAND()
    LIMIT 1;

    RETURN nombreMedico;
END //
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE GestionarCitaMedica ( modo INT, p_paciente_nombre VARCHAR(100),p_medico_nombre VARCHAR(100),p_fecha DATE,p_hora VARCHAR(20),p_tipo_cita VARCHAR(100),OUT mensaje VARCHAR(255)
)
BEGIN
    DECLARE existe INT;

    IF modo = 1 THEN 
         SELECT COUNT(*) INTO existe FROM CitasActivas WHERE medico_nombre = p_medico_nombre AND fecha = p_fecha AND hora = p_hora AND tipo_cita = p_tipo_cita;

	     IF existe > 0 THEN
		     SET mensaje = 'La cita médica ya existe para ese horario.';
		ELSE
			INSERT INTO CitasActivas (paciente_nombre,medico_nombre,fecha,hora,tipo_cita)
			VALUES (p_paciente_nombre,p_medico_nombre,p_fecha,p_hora,p_tipo_cita);
            SET mensaje = 'agregada';
		END IF;

    ELSEIF modo = 0 THEN
        SELECT COUNT(*) INTO existe 
        FROM CitasActivas 
        WHERE paciente_nombre = p_paciente_nombre 
          AND medico_nombre = p_medico_nombre 
          AND fecha = p_fecha 
          AND hora = p_hora 
          AND tipo_cita = p_tipo_cita;

        IF existe = 0 THEN
            SET mensaje = 'No se encontró la cita para eliminar.';
        ELSE
            DELETE FROM CitasActivas 
            WHERE paciente_nombre = p_paciente_nombre 
              AND medico_nombre = p_medico_nombre 
              AND fecha = p_fecha 
              AND tipo_cita = p_tipo_cita;
            SET mensaje = 'Cita médica eliminada correctamente.';
        END IF;

    ELSE
        SET mensaje = 'Modo inválido. Use 1 para agregar o 0 para eliminar.';
    END IF;
END $$
DELIMITER ;

DELIMITER //
CREATE PROCEDURE obtenerCitasMedicas(IN paciente_noombre VARCHAR(255))
BEGIN
    SELECT fecha, hora, medico_nombre, tipo_cita 
    FROM CitasActivas 
    WHERE paciente_nombre = paciente_noombre;
END//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE obtenerCitasMedicasPorMedico(IN medico_nombre_param VARCHAR(255))
BEGIN
    SELECT fecha, hora, paciente_nombre,medico_nombre, tipo_cita 
    FROM CitasActivas 
    WHERE medico_nombre = medico_nombre_param;
END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE obtenerCitasAtendidas(IN paciente_noombre VARCHAR(255))
BEGIN
    SELECT fecha, hora, paciente_nombre,medico_nombre,tipo_cita, diagnostico, solucion
    FROM CitasAtendidas
    WHERE medico_nombre = paciente_noombre or paciente_nombre = paciente_noombre;
END //
DELIMITER ;

DELIMITER //

CREATE PROCEDURE AtenderCita(
    p_paciente_nombre VARCHAR(100),
    p_medico_nombre VARCHAR(100),
	p_fecha DATE,
    p_hora VARCHAR(20),
    p_tipo_cita VARCHAR(100),
    p_diagnostico VARCHAR(500),
    p_solucion VARCHAR(5000),
    OUT mensaje VARCHAR(255)
)
BEGIN
        INSERT INTO CitasAtendidas (paciente_nombre, medico_nombre, fecha, hora, tipo_cita, diagnostico, solucion)
        VALUES (p_paciente_nombre, p_medico_nombre, p_fecha, p_hora, p_tipo_cita, p_diagnostico, p_solucion);
       
       DELETE FROM CitasActivas WHERE paciente_nombre = p_paciente_nombre AND medico_nombre = p_medico_nombre AND fecha = p_fecha AND hora = p_hora AND tipo_cita = p_tipo_cita;

        SET mensaje = 'Cita atendida correctamente y guardada en CitasAtendidas.';
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE EliminarCitaActiva(
    IN p_paciente_nombre VARCHAR(100),
    IN p_medico_nombre VARCHAR(100),
    IN p_fecha DATE,
    IN p_hora VARCHAR(20),
    IN p_tipo_cita VARCHAR(100)
)
BEGIN
    DELETE FROM CitasActivas
    WHERE paciente_nombre = p_paciente_nombre
      AND medico_nombre = p_medico_nombre
      AND fecha = p_fecha
      AND hora = p_hora
      AND tipo_cita = p_tipo_cita;
END //

DELIMITER ;


DELIMITER //
CREATE PROCEDURE obtenerDatosCitasRecurrentes()
BEGIN
    SELECT 
        paciente_nombre,
        COUNT(*) AS cantidad_citas, 
        (SELECT hora
         FROM CitasActivas
         WHERE paciente_nombre = c.paciente_nombre
         GROUP BY hora
         ORDER BY COUNT(*) DESC
         LIMIT 1) AS hora_mas_recurrente,
        (SELECT tipo_cita
         FROM CitasActivas
         WHERE paciente_nombre = c.paciente_nombre
         GROUP BY tipo_cita
         ORDER BY COUNT(*) DESC
         LIMIT 1) AS tipo_cita_mas_recurrente,
        (SELECT medico_nombre
         FROM CitasActivas
         WHERE paciente_nombre = c.paciente_nombre
         GROUP BY medico_nombre
         ORDER BY COUNT(*) DESC
         LIMIT 1) AS medico_mas_recurrente
    FROM CitasActivas c
    GROUP BY paciente_nombre
    HAVING COUNT(*) > 1; 
END //

DELIMITER ;
DELIMITER //

CREATE PROCEDURE ReporteCitas (
    IN modo VARCHAR(10), -- 'dia', 'semana', 'mes'
    IN fecha_base DATE
)
BEGIN
    etiqueta_bloque: BEGIN
        DECLARE fecha_inicio DATE;
        DECLARE fecha_fin DATE;
        DECLARE activas INT DEFAULT 0;
        DECLARE atendidas INT DEFAULT 0;
        DECLARE total INT DEFAULT 0;

        -- Calcular fechas según modo
        IF modo = 'dia' THEN
            SET fecha_inicio = fecha_base;
            SET fecha_fin = fecha_base;
        ELSEIF modo = 'semana' THEN
            SET fecha_inicio = DATE_SUB(fecha_base, INTERVAL WEEKDAY(fecha_base) DAY);
            SET fecha_fin = DATE_ADD(fecha_inicio, INTERVAL 6 DAY);
        ELSEIF modo = 'mes' THEN
            SET fecha_inicio = DATE_FORMAT(fecha_base, '%Y-%m-01');
            SET fecha_fin = LAST_DAY(fecha_base);
        ELSE
            LEAVE etiqueta_bloque;
        END IF;

        -- Contar activas y atendidas en el rango
        SELECT COUNT(*) INTO activas 
        FROM CitasActivas 
        WHERE fecha BETWEEN fecha_inicio AND fecha_fin;

        SELECT COUNT(*) INTO atendidas 
        FROM CitasAtendidas 
        WHERE fecha BETWEEN fecha_inicio AND fecha_fin;

        -- Contar total sin importar fecha
        SELECT 
            (SELECT COUNT(*) FROM CitasActivas) + 
            (SELECT COUNT(*) FROM CitasAtendidas)
        INTO total;

        -- Mostrar resultados
        SELECT 
            activas AS citas_activas,
            atendidas AS citas_atendidas,
            total AS total_citas;

    END etiqueta_bloque;
END //

DELIMITER ;



select * from CitasActivas;
select * from CitasAtendidas;
CALL ReporteCitas('mes', '2025-06-03');


DELIMITER //

CREATE PROCEDURE TotalCitasPorEspecialidad()
BEGIN
    SELECT tipo_cita, 
           (SELECT COUNT(*) FROM CitasActivas WHERE tipo_cita = CA.tipo_cita) +
           (SELECT COUNT(*) FROM CitasAtendidas WHERE tipo_cita = CA.tipo_cita) AS total_citas
    FROM CitasActivas CA
    GROUP BY tipo_cita
    UNION
    SELECT tipo_cita,
           (SELECT COUNT(*) FROM CitasActivas WHERE tipo_cita = CA.tipo_cita) +
           (SELECT COUNT(*) FROM CitasAtendidas WHERE tipo_cita = CA.tipo_cita) AS total_citas
    FROM CitasAtendidas CA
    GROUP BY tipo_cita;
END //

DELIMITER ;

call TotalCitasPorEspecialidad();

DELIMITER $$

CREATE PROCEDURE ObtenerHorario()
BEGIN
    SELECT * FROM Horario;
END$$

DELIMITER ;

