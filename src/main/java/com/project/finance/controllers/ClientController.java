package com.project.finance.controllers;

import com.project.finance.clientDBService.ClientService;
import com.project.finance.entities.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class ClientController {

    @Autowired
    ClientService clientService;

    @GetMapping(value = "/registration")
    public String registration(Model model){
        Client client = new Client();
        model.addAttribute("client",client);
        return "registration";
    }

    @GetMapping(value = "/login")
    public String login(Model model){
        return "login";
    }

    @PostMapping(value = "/registration")
    public String addNewClient(@Valid @ModelAttribute("client") Client client, BindingResult bindingResult, Model model){
        Optional<Client> newUser = clientService.findClientByLogin(client.getLogin());
        System.out.println(newUser.isEmpty());
        System.out.println(newUser.isPresent());
        if(newUser.isPresent()){
            bindingResult
                    .rejectValue("login", "error.client",
                            "There is already a client registered with the client name provided");
        }
        if (bindingResult.hasErrors()) {
            System.out.println("--------------------- INSIDE POST ERROR -----------------------");
            bindingResult.getAllErrors().stream().forEach(System.out::println);
            return "registration";
        }else{
            System.out.println("--------------------- INSIDE POST SAVE -----------------------");
            clientService.save(client);
            model.addAttribute("successMessage","client have been registered");
            model.addAttribute("client",new Client());
            return "registration";
        }
    }

    @GetMapping(value = "/home")
    public String home(Model model){
        System.out.println("i'm here");
        model.addAttribute("title","Welcome to the home!");
        return "home";
    }

}
