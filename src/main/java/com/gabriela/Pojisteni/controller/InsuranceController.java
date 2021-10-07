package com.gabriela.Pojisteni.controller;

import com.gabriela.Pojisteni.model.Insurance;
import com.gabriela.Pojisteni.model.User;
import com.gabriela.Pojisteni.service.InsuranceService;
import com.gabriela.Pojisteni.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class InsuranceController {
    @Autowired
    private InsuranceService service;

    @Autowired
    private UserService userService;

    /* Seznam pojištění doplněný o email pojištěnce
        (nemůže se opakovat a je přehlednější než id) */
    @GetMapping("/insurances")
    public String showInsuranceList(Model model) {
        List<Insurance> listInsurance = service.listAll();
        List<User> listUsers = userService.listAll();
        model.addAttribute("listInsurance", listInsurance);
        model.addAttribute("listUsers", listUsers);
        return "insurances";
    }

    // Vytvoření formuláře pro přidání nového pojištění
    @GetMapping("/insurance/new")
    public String showNewInsuranceForm(Model model) {
        List<User> listUsers = userService.listAll();
        model.addAttribute("insurance", new Insurance());
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("pageTitle", "Vytvoř nové pojištění");
        return "insurance_form";
    }

    // Uložení pojištění s oznámením
    @PostMapping("/insurance/save")
    public String saveInsurance(Insurance insurance, RedirectAttributes ra) {
        service.save(insurance);
        ra.addFlashAttribute("message", "Záznam byl úspěšně uložen.");
        return "redirect:/users";
    }

    // Formulář pro editaci existujícího pojištění s možností výběru pojištěnce (podle emailu)
    @GetMapping("/insurance/edit/{insurance_id}")
    public String showEditInsuranceForm(@PathVariable("insurance_id") Integer insurance_id, Model model, RedirectAttributes ra) {
        try {
            Insurance insurance = service.get(insurance_id);
            List<User> listUsers = userService.listAll();
            model.addAttribute("insurance", insurance);
            model.addAttribute("listUsers", listUsers);
            model.addAttribute("pageTitle", "Uprav záznam (ID: " + insurance_id + ")");
            return "insurance_form";
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/insurances";
        }
    }

    // Smazání pojištění
    @GetMapping("/insurance/delete/{insurance_id}")
    public String deleteInsurance(@PathVariable("insurance_id") Integer insurance_id, RedirectAttributes ra) {
        try {
            service.delete(insurance_id);
            ra.addFlashAttribute("message", "Záznam s ID " + insurance_id + " byl smazán.");
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/insurances";
    }
}