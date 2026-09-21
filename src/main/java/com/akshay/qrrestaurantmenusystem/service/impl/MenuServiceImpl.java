package com.akshay.qrrestaurantmenusystem.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.akshay.qrrestaurantmenusystem.entity.Category;
import com.akshay.qrrestaurantmenusystem.entity.Menu;
import com.akshay.qrrestaurantmenusystem.repository.MenuRepository;
import com.akshay.qrrestaurantmenusystem.service.FileStorageService;
import com.akshay.qrrestaurantmenusystem.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {


    // Repository object for database operations
    private final MenuRepository menuRepository;


    // Service object for image upload/delete operations
    private final FileStorageService fileStorageService;



    // Constructor Injection
    public MenuServiceImpl(MenuRepository menuRepository,
                           FileStorageService fileStorageService) {

        this.menuRepository = menuRepository;
        this.fileStorageService = fileStorageService;
    }	



    // ==================================================
    // Save New Menu
    // Purpose : Add new menu item into database
    // ==================================================

    @Override
    public Menu saveMenu(Menu menu, MultipartFile imageFile) {


        // Check image is uploaded or not
        if(imageFile != null && !imageFile.isEmpty()) {


            // Save image and get generated file name
            String imageName =
                    fileStorageService.saveImage(imageFile);


            // Store image name in Menu object
            menu.setImageName(imageName);
        }


        // Save menu data into database
        return menuRepository.save(menu);
    }





    // ==================================================
    // Update Existing Menu
    // Purpose : Update menu details and handle image
    // ==================================================
    @Override
    public Menu updateMenu(Menu menu, MultipartFile imageFile) {

        Menu oldMenu = menuRepository.findById(menu.getId())
                .orElseThrow(() -> new RuntimeException("Menu Not Found"));

        // New image uploaded
        if (imageFile != null && !imageFile.isEmpty()) {

            // Delete old image
            if (oldMenu.getImageName() != null) {
                fileStorageService.deleteImage(oldMenu.getImageName());
            }

            // Save new image
            String imageName = fileStorageService.saveImage(imageFile);
            menu.setImageName(imageName);

        } else {

            // Keep old image
            menu.setImageName(oldMenu.getImageName());

        }

        return menuRepository.save(menu);
    }





    // ==================================================
    // Get All Menus
    // Purpose : Display all menu items
    // ==================================================

    @Override
    public List<Menu> getAllMenus() {

        return menuRepository.findAll();
    }





    // ==================================================
    // Get Menu By Id
    // Purpose : Find single menu record
    // ==================================================

    @Override
    public Menu getMenuById(Long id) {

        return menuRepository.findById(id)
                .orElse(null);
    }


    // ==================================================
    // Delete Menu
    // Purpose : Delete image and database record
    // ==================================================

    @Override
    public void deleteMenu(Long id) {

        Menu menu = menuRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Menu Not Found"));

        if(menu.getImageName()!=null){

            fileStorageService.deleteImage(menu.getImageName());

        }

        menuRepository.delete(menu);

    }
    
    
 // ==========================================
 // GET ALL AVAILABLE MENU ITEMS
 // ==========================================

 @Override
 public List<Menu> getAllAvailableMenus() {

     return menuRepository.findByAvailableTrue();

 }
 
 @Override
 public List<Menu> getAvailableMenusByCategory(Long categoryId) {

     return menuRepository.findByCategoryIdAndAvailableTrue(categoryId);

 }
 
 
 
 

}