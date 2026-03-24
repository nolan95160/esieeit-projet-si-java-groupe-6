package com.esieeit.projetsi.config;

import com.esieeit.projetsi.domain.entity.Project;
import com.esieeit.projetsi.domain.entity.Task;
import com.esieeit.projetsi.domain.entity.User;
import com.esieeit.projetsi.domain.enums.ProjectStatus;
import com.esieeit.projetsi.domain.enums.Role;
import com.esieeit.projetsi.domain.enums.TaskPriority;
import com.esieeit.projetsi.domain.enums.TaskStatus;
import com.esieeit.projetsi.repository.ProjectRepository;
import com.esieeit.projetsi.repository.TaskRepository;
import com.esieeit.projetsi.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class DataInitializer {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    public DataInitializer(UserRepository userRepository,
                           ProjectRepository projectRepository,
                           TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    @Bean
    CommandLineRunner initData() {
        return args -> {

            if (userRepository.count() > 0) {
                return;
            }

            // 1) Utilisateurs
            User admin = new User("admin", "admin@esiee.local", "CHANGE_ME_HASH_LATER", Role.ROLE_ADMIN);
            User alice = new User("alice", "alice@esiee.local", "CHANGE_ME_HASH_LATER", Role.ROLE_USER);
            User bob = new User("bob", "bob@esiee.local", "CHANGE_ME_HASH_LATER", Role.ROLE_USER);
            User charlie = new User("charlie", "charlie@esiee.local", "CHANGE_ME_HASH_LATER", Role.ROLE_USER);
            userRepository.saveAll(List.of(admin, alice, bob, charlie));

            // 2) Projets
            Project p1 = new Project("Projet SI - Gestion des tâches",
                    "Plateforme de gestion de projets et tâches", ProjectStatus.ACTIVE, admin);
            Project p2 = new Project("Projet SI - Support interne",
                    "Gestion des tickets de support", ProjectStatus.DRAFT, alice);
            Project p3 = new Project("Projet SI - Documentation",
                    "Rédaction de la documentation technique", ProjectStatus.ACTIVE, bob);
            projectRepository.saveAll(List.of(p1, p2, p3));

            // 3) Tâches liées aux projets
            Task t1 = new Task("Initialiser le repository Git",
                    "Créer le dépôt et les conventions", TaskStatus.DONE, TaskPriority.HIGH, LocalDate.now().minusDays(5));
            t1.setProject(p1);
            t1.setAssignee(admin);

            Task t2 = new Task("Créer les entités JPA",
                    "Mapper User, Project, Task", TaskStatus.DONE, TaskPriority.HIGH, LocalDate.now().minusDays(2));
            t2.setProject(p1);
            t2.setAssignee(alice);

            Task t3 = new Task("Créer les repositories Spring Data",
                    "Ajouter les query methods métier", TaskStatus.IN_PROGRESS, TaskPriority.HIGH, LocalDate.now().plusDays(1));
            t3.setProject(p1);
            t3.setAssignee(bob);

            Task t4 = new Task("Configurer la validation DTO",
                    "Ajouter @Valid et contraintes", TaskStatus.DONE, TaskPriority.MEDIUM, LocalDate.now().minusDays(3));
            t4.setProject(p1);
            t4.setAssignee(alice);

            Task t5 = new Task("Implémenter le DataInitializer",
                    "Seed de données de test", TaskStatus.IN_PROGRESS, TaskPriority.MEDIUM, LocalDate.now());
            t5.setProject(p1);
            t5.setAssignee(charlie);

            Task t6 = new Task("Tester les endpoints REST",
                    "Vérifier CRUD avec Postman", TaskStatus.TODO, TaskPriority.HIGH, LocalDate.now().plusDays(2));
            t6.setProject(p1);

            Task t7 = new Task("Préparer données de démonstration",
                    "Jeu de données pour soutenance", TaskStatus.TODO, TaskPriority.LOW, LocalDate.now().plusDays(5));
            t7.setProject(p2);
            t7.setAssignee(alice);

            Task t8 = new Task("Configurer le système de tickets",
                    "Mettre en place le workflow de support", TaskStatus.TODO, TaskPriority.MEDIUM, LocalDate.now().plusDays(7));
            t8.setProject(p2);

            Task t9 = new Task("Rédiger le guide utilisateur",
                    "Documentation pour les utilisateurs finaux", TaskStatus.TODO, TaskPriority.LOW, LocalDate.now().plusDays(10));
            t9.setProject(p3);
            t9.setAssignee(bob);

            Task t10 = new Task("Documenter l'API REST",
                    "Endpoints, paramètres, exemples curl", TaskStatus.IN_PROGRESS, TaskPriority.HIGH, LocalDate.now().plusDays(3));
            t10.setProject(p3);
            t10.setAssignee(charlie);

            Task t11 = new Task("Créer le diagramme d'architecture",
                    "Schéma technique du projet", TaskStatus.TODO, TaskPriority.MEDIUM, LocalDate.now().plusDays(4));
            t11.setProject(p3);

            Task t12 = new Task("Ajouter la sécurité JWT",
                    "Authentification et autorisation", TaskStatus.TODO, TaskPriority.URGENT, LocalDate.now().plusDays(14));
            t12.setProject(p1);

            taskRepository.saveAll(List.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12));
        };
    }
}
