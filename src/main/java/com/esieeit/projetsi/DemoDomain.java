package com.esieeit.projetsi;

import com.esieeit.projetsi.domain.model.Project;
import com.esieeit.projetsi.domain.model.Task;
import com.esieeit.projetsi.domain.model.User;

public class DemoDomain {

    public static void main(String[] args) {
        // 1) Création d'un utilisateur
        User alice = new User(1L, "alice@mail.com", "hash123", "Alice");

        // 2) Création d'un groupe (Project = Group)
        Project foyer = new Project(10L, "Foyer", alice.getId());

        // 3) Création d'une tâche dans le groupe
        Task task = new Task(100L, foyer.getId(), "Faire les courses", "Acheter lait et pâtes", alice.getId());

        System.out.println("Utilisateur: " + alice.getDisplayName() + " (" + alice.getEmail() + ")");
        System.out.println("Groupe: " + foyer.getName());
        System.out.println("Tâche: " + task.getTitle() + " | statut=" + task.getStatus());

        // 4) Workflow de statut
        task.start();
        System.out.println("Après start(): statut=" + task.getStatus());

        task.complete();
        System.out.println("Après complete(): statut=" + task.getStatus());

        // 5) Renommage du groupe par le owner
        foyer.rename("Foyer (maison)", alice.getId());
        System.out.println("Groupe renommé: " + foyer.getName());
    }
}