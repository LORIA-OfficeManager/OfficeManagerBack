--
-- Dumping data for table `department`
--

INSERT INTO `department` (`department_id`, `name`) VALUES
(23803, 'Informatique'),
(19798, 'Loria'),
(23805, 'Math'),
(23796, 'Office Manager');

-- --------------------------------------------------------

--
-- Dumping data for table `status`
--

INSERT INTO `status` (`status_id`, `name`, `size`) VALUES
(0, 'Enseignant-chercheur', 1),
(1, 'Doctorant', 0.66),
(2, 'Stagiaire', 0);

-- --------------------------------------------------------

--
-- Dumping data for table `team`
--

INSERT INTO `team` (`team_id`, `name`, `department_department_id`) VALUES
(19799, 'Loria', 19798),
(23797, 'Office Manager', 23796),
(23798, 'Front', 23796),
(23799, 'Back', 23796),
(23800, 'Scrum master', 23796),
(23804, 'Informatique', 23803),
(23806, 'Math', 23805),
(23807, 'kyshlo ren', 23803),
(23808, 'heineken skywalker', 23803),
(23809, 'Pythagore', 23805),
(23810, 'Thales', 23805);

--
-- Dumping data for table `user` password OFF|c3m@n@g3r
--

INSERT INTO `user` (`user_id`, `password`, `role`, `username`) VALUES
(33382, '$2a$10$h51di.r0c68xsQTEOYMGye4jZl.sbKzzrr7lCN6ob6xDo2AZmAAsm', 'admin', 'officemanager.admin@loria.fr')

