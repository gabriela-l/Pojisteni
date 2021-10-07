package com.gabriela.Pojisteni.controller;

import com.gabriela.Pojisteni.model.Insurance;
import com.gabriela.Pojisteni.model.Role;
import com.gabriela.Pojisteni.model.User;
import com.gabriela.Pojisteni.repository.RoleRepository;
import com.gabriela.Pojisteni.service.InsuranceService;
import com.gabriela.Pojisteni.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {
    // UserService rozšiřuje UserRepository (které rozšiřuje JpaRepository):
    @Autowired
    private UserService service;
    @Autowired
    private InsuranceService insuranceService;
    @Autowired
    private RoleRepository roleRepository;

    public UserController(UserService service) {
        this.service = service;
    }

    // Formulář pro zaregistrování nového uživatele, potažmo pojištěnce
    @GetMapping("/register")
    public String showSignUpForm(Model model) {
        // do modelu přidám formulář pro vytvoření uživatele "user"
        model.addAttribute("user", new User());
        // výběr z rolí user/admin:
        List<Role> listRoles = roleRepository.findAll();
        model.addAttribute("listRoles", listRoles);
        // vrátí register.html
        return "register";
    }


    /* Uloží nového uživatele/pojištěnce a zašifruje jeho heslo */
    @PostMapping("/process_register")
    public String processRegistration(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setEnabled(true);
        service.save(user);
        // po úspěšném uložení uživatele a jeho hesla se uživatel může přihlásit
        return "register_success";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // po přihlášení je uživatel přesměrován na users.html, viz WebSecurityConfig (configure)

    // Úvodní stránka se seznamem všech pojištěnců, admin navíc může data editovat či mazat
    @GetMapping("/users")
    public String showUsersList(Model model) {
        List<User> listUsers = service.listAll();
        model.addAttribute("listUsers", listUsers);
        return "users";
    }

    // Přidání nového pojištěnce
    @GetMapping("/users/new")
    public String showUserNewForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("pageTitle", "Nový pojištěnec");
        List<Role> listRoles = roleRepository.findAll();
        model.addAttribute("listRoles", listRoles);
        return "user_form";
    }

    // Uložení pojištěnce a zašifrování hesla
    @PostMapping("/users/save")
    public String saveUser(User user, RedirectAttributes ra) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // heslo vložené uživatelem bude v DB uloženo zašifrované
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setEnabled(true);
        service.save(user);
        ra.addFlashAttribute("message", "Záznam byl úspěšně uložen.");
        return "redirect:/users";
    }

    // Úprava detailů uživatelů - možné jen pro Adminy
    @GetMapping("/users/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            User user = service.get(id);
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Uprav záznam (ID: " + id + ")");
            List<Role> listRoles = roleRepository.findAll();
            model.addAttribute("listRoles", listRoles);
            return "user_form";
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/users";
        }
    }
    // Mazání uživatelů - možné jen pro Adminy
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("message", "Záznam s ID " + id + " byl smazán.");
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/users";

    }

    // Zobrazení detailu uživatele na nové stránce spolu s výpisem všech jeho pojištění (existují li)
    @GetMapping("/users/detail/{id}")
    public String showDetailUser(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            User user = service.get(id);
            List<Insurance> insuranceList = user.getInsurances();
            model.addAttribute(user);
            model.addAttribute(insuranceList);
            if (insuranceList.isEmpty()) {
                model.addAttribute("noInsurance", "Žádné pojištění není evidováno");
            }
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "detail";
    }

    // Chybová stránka oznamující nedostatčná oprávnění uživatele
    @GetMapping("/403")
    public String error403() {
        return "403";
    }
}

