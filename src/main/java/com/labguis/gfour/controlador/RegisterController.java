/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labguis.gfour.controlador;

import com.labguis.gfour.interfaceService.IUsuarioService;
import com.labguis.gfour.interfaceService.InterfaceWhiteList;
import com.labguis.gfour.modelo.User;
import com.labguis.gfour.modelo.WhiteList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class RegisterController {

    @Autowired
    private IUsuarioService service;
    @Autowired
    private InterfaceWhiteList iwls;

    @PostMapping("/register")
    public String register(@Validated User user, @RequestParam("password_2") String pass2, Model model) {
        user.setName(user.getEmail()); // the name is the same email
        //user.setPassword(passwordEncoder.encode(user.getPassword()));
        int can_pass = checkRegister(user.getName(), user.getPassword(), pass2, user.getEmail());
        switch (can_pass) {
            case 1:
                model.addAttribute("error", "Longitud del nombre incorrecta");
                break;
            case 2:
                model.addAttribute("error", "Longitud de la contraseña incorrecta");
                break;
            case 3:
                model.addAttribute("error", "Las contraseñas no coinciden");
                break;
            case 4:
                model.addAttribute("error", "El usuario con correo " + user.getEmail() + " ya existe");
                break;
            case 5:
                model.addAttribute("error", "El correo  " + user.getEmail() + " no esta autorizado");
                break;
            default:
                // All ok
                user.setPassword(service.hashPassword(pass2));
                int reg = service.save(user); // Save the user in DB      
                if (reg != 0) {
                    return "redirect:/login?status=Gracias por registrarse";
                }
                break;
        }
        return "registro";
    }

    public int checkRegister(String nombre, String pass1, String pass2, String email) {
        if (service.findByEmail(email) != null) {
            return 4;
        }
        if (!checkName(nombre)) {
            return 1;
        }
        if (!checkPass(pass1)) {
            return 2;
        }
        if (!checkPasswords(pass1, pass2)) {
            return 3;
        }
        if (!service.checkWhiteList(email)) {
            return 5;
        }
        return 0; // All ok
    }

    @GetMapping("/register")
    public String register(Model model, HttpServletRequest request) {
        if (service.isUserLogged(request)) {
            return "redirect:/equipos";
        }
        model.addAttribute("usuario", new User());
        return "registro";
    }

    @PostMapping("/autorizar")
    public String autorizar(@Validated WhiteList w, Model model, HttpServletRequest request) {
        if (!service.isUserAdmin(request)) {
            return "redirect:/autorizar";
        }
        WhiteList wl = iwls.findByEmail(w.getEmail());
        if(wl == null) {
            iwls.save(w);
        }
        return "redirect:/autorizar";
    }

    @GetMapping("/autorizar")
    public String autorizar(Model model, HttpServletRequest request) {
        if (!service.isUserAdmin(request)) {
            return "redirect:/equipos";
        }
        List<WhiteList> wl = iwls.listar();
        model.addAttribute("whitelists", wl);
        model.addAttribute("newwl", new WhiteList());
        return "autorizar";
    }

    @GetMapping("/deleteWL/{id}")
    public String eliminar(@PathVariable int id, Model model) {
        iwls.delete(id);
        return "redirect:/listar";
    }

    public boolean checkPasswords(String pass1, String pass2) {
        return pass1.equals(pass2);
    }

    public boolean checkName(String nombre) {
        return (nombre.length() > 1 && nombre.length() <= 64);
    }

    public boolean checkPass(String pass) {
        return (pass.length() > 8 && pass.length() <= 16);
    }

}
