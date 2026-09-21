package com.akshay.qrrestaurantmenusystem.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.akshay.qrrestaurantmenusystem.entity.Menu;
import com.akshay.qrrestaurantmenusystem.service.CategoryService;
import com.akshay.qrrestaurantmenusystem.service.MenuService;

import jakarta.validation.Valid;


@Controller
@RequestMapping("/menu")
public class MenuController {


    // Service layer object for Menu operations
    private final MenuService menuService;


    // Service layer object for Category operations
    private final CategoryService categoryService;



    // Constructor Injection
    public MenuController(MenuService menuService,
                          CategoryService categoryService) {

        this.menuService = menuService;
        this.categoryService = categoryService;
    }



    // ==================================================
    // Show Add Menu Page
    // URL : /menu/add
    // ==================================================

    @GetMapping("/add")
    public String showAddMenuPage(Model model) {


        // Empty Menu object for form binding
        model.addAttribute("menu", new Menu());


        // Send all categories for dropdown
        model.addAttribute(
                "categories",
                categoryService.getAllCategories()
        );


        return "menu/add-menu";
    }


    // ==================================================
    // Save New Menu
    // URL : /menu/save
    // ==================================================

    @PostMapping("/save")
    public String saveMenu(

            @Valid @ModelAttribute("menu") Menu menu,

            BindingResult result,

            @RequestParam("imageFile") MultipartFile imageFile,

            Model model) {

        if (result.hasErrors()) {

            model.addAttribute("categories",
                    categoryService.getAllCategories());

            return "menu/add-menu";
        }

        menuService.saveMenu(menu, imageFile);

        return "redirect:/menu/list";
    }





    // ==================================================
    // Display All Menu Items
    // URL : /menu/list
    // ==================================================

    @GetMapping("/list")
    public String listMenu(Model model) {


        // Fetch all menus from database
        model.addAttribute(
                "menus",
                menuService.getAllMenus()
        );


        return "menu/menu-list";
    }


    // ==================================================
    // Show Edit Menu Page
    // URL : /menu/edit/{id}
    // ==================================================

    @GetMapping("/edit/{id}")
    public String editMenu(@PathVariable Long id, Model model) {

        model.addAttribute("menu", menuService.getMenuById(id));

        model.addAttribute("categories",
                categoryService.getAllCategories());

        return "menu/edit-menu";
    }


    // ==================================================
    // Update Menu
    // URL : /menu/update
    // ==================================================

    @PostMapping("/update")
    public String updateMenu(

            @Valid @ModelAttribute("menu") Menu menu,

            BindingResult result,

            @RequestParam("imageFile") MultipartFile imageFile,

            Model model) {

        if(result.hasErrors()){

            model.addAttribute("categories",
                    categoryService.getAllCategories());

            return "menu/edit-menu";
        }

        menuService.updateMenu(menu,imageFile);

        return "redirect:/menu/list";
    }


    // ==================================================
    // Delete Menu
    // URL : /menu/delete/{id}
    // ==================================================

    @GetMapping("/delete/{id}")
    public String deleteMenu(
            @PathVariable Long id) {


        // Delete menu using service
        menuService.deleteMenu(id);



        return "redirect:/menu/list";
    }

}