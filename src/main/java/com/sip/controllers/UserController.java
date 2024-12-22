package com.sip.controllers;

import com.sip.entities.User;
import com.sip.repositories.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private userRepository userRepo;

    /**
     * Afficher la liste des utilisateurs
     * URL : /users
     */
    @GetMapping
    public String listUsers(Model model) {
        List<User> users = userRepo.findAll(); // Récupère tous les utilisateurs
        model.addAttribute("users", users);
        return "users/list";
        // Renvoie vers la vue Thymeleaf users/list.html (à créer dans src/main/resources/templates/users/list.html)
    }

    /**
     * Afficher un formulaire pour ajouter un nouvel utilisateur
     * URL : /users/add
     */
    @GetMapping("/add")
    public String addUserForm(Model model) {
        // On passe un objet user vide pour que le formulaire puisse le remplir
        model.addAttribute("user", new User());
        return "users/add";
        // Renvoie vers la vue Thymeleaf users/add.html (à créer)
    }

    /**
     * Traiter la soumission du formulaire d'ajout
     * URL : /users (méthode POST)
     */
    @PostMapping
    public String addUser(@ModelAttribute("user") User u) {
        userRepo.save(u); // Création (INSERT) en base
        return "redirect:/users";
        // Redirection vers l'affichage de la liste (méthode GET /users)
    }

    /**
     * Afficher un formulaire de modification pour un user (UPDATE)
     * URL : /users/edit/{id}
     */
    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable("id") Long id, Model model) {
        // On récupère l'utilisateur en base pour pré-remplir le formulaire
        User existingUser = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", existingUser);
        return "users/edit";
        // Renvoie vers la vue Thymeleaf users/edit.html (à créer)
    }

    /**
     * Traiter la soumission du formulaire de modification
     * URL : /users/update (méthode POST)
     */
    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User u) {
        userRepo.save(u); // S'il existe déjà, JPA fera un UPDATE
        return "redirect:/users";
    }

    /**
     * Supprimer un utilisateur (DELETE)
     * URL : /users/delete/{id}
     */
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userRepo.deleteById(id);
        return "redirect:/users";
    }
}
