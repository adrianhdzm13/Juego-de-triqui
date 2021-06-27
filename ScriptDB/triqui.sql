
--
-- Base de datos: `triqui`
--
DROP DATABASE IF EXISTS triqui;
CREATE DATABASE triqui;
USE triqui;
-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `juego`
--

CREATE TABLE `juego` (
  `id` int(11) NOT NULL,
  `jugador1` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,
  `jugador2` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,
  `dataTime` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `juego`
--

INSERT INTO `juego` (`id`, `jugador1`, `jugador2`, `dataTime`) VALUES
(2, 'juank', 'Daniela', '2021-06-27 05:27:16');

--
-- Disparadores `juego`
--
DELIMITER $$
CREATE TRIGGER `ELIMINAR` AFTER DELETE ON `juego` FOR EACH ROW BEGIN
    		INSERT INTO triguer(id,jugador1,jugador2,dataTime,sentencia,usuario)
        	VALUES(OLD.id,OLD.jugador1,OLD.jugador2, NOW(), 'DELETE', USER() );
   		END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `INSERTAR` AFTER INSERT ON `juego` FOR EACH ROW BEGIN
    		INSERT INTO triguer(id,jugador1,jugador2,dataTime,sentencia,usuario)
        	VALUES(NEW.id,NEW.jugador1,NEW.jugador2,NOW(),'INSERT',USER());
   		END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `MODIFICAR` AFTER UPDATE ON `juego` FOR EACH ROW BEGIN
    		INSERT INTO triguer(id,jugador1,jugador2,dataTime,sentencia,usuario)
        	VALUES(NEW.id,NEW.jugador1,NEW.jugador2,NOW(),'UPDATE',USER());
   		END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `triguer`
--

CREATE TABLE `triguer` (
  `id` int(11) NOT NULL,
  `jugador1` varchar(50) NOT NULL,
  `jugador2` varchar(50) NOT NULL,
  `dataTime` datetime NOT NULL,
  `sentencia` varchar(20) NOT NULL,
  `usuario` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `triguer`
--

INSERT INTO `triguer` (`id`, `jugador1`, `jugador2`, `dataTime`, `sentencia`, `usuario`) VALUES
(2, 'jjjkk', 'lopp', '2021-06-27 05:27:16', 'INSERT', 'adrian@localhost'),
(2, 'juank', 'Daniela', '2021-06-27 05:43:28', 'UPDATE', 'root@localhost'),
(1, 'gato', 'gato2', '2021-06-27 05:44:09', 'DELETE', 'root@localhost');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `juego`
--
ALTER TABLE `juego`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `juego`
--
ALTER TABLE `juego`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
