package com.akshay.qrrestaurantmenusystem.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.akshay.qrrestaurantmenusystem.entity.Menu;
import com.akshay.qrrestaurantmenusystem.repository.MenuRepository;
import com.akshay.qrrestaurantmenusystem.repository.OrderItemRepository;
import com.akshay.qrrestaurantmenusystem.service.FileStorageService;
import com.akshay.qrrestaurantmenusystem.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {


    // Repository object for database operations
    private final MenuRepository menuRepository;


    // Repository object for order history check
    private final OrderItemRepository orderItemRepository;


    // Service object for image upload/delete operations
    private final FileStorageService fileStorageService;



    // Constructor Injection
    public MenuServiceImpl(
            MenuRepository menuRepository,
            OrderItemRepository orderItemRepository,
            FileStorageService fileStorageService) {

        this.menuRepository = menuRepository;
        this.orderItemRepository = orderItemRepository;
        this.fileStorageService = fileStorageService;
    }


    // ==================================================
    // Save New Menu
    // ==================================================

    @Override
    public Menu saveMenu(Menu menu, MultipartFile imageFile) {

        // Check image is uploaded or not
        if (imageFile != null && !imageFile.isEmpty()) {

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
    // ==================================================

    @Override
    public Menu updateMenu(Menu menu, MultipartFile imageFile) {

        Menu oldMenu = menuRepository.findById(menu.getId())
                .orElseThrow(() ->
                        new RuntimeException("Menu Not Found"));


        // New image uploaded
        if (imageFile != null && !imageFile.isEmpty()) {

            // Delete old image
            if (oldMenu.getImageName() != null) {

                fileStorageService.deleteImage(
                        oldMenu.getImageName());
            }

            // Save new image
            String imageName =
                    fileStorageService.saveImage(imageFile);

            menu.setImageName(imageName);

        } else {

            // Keep old image
            menu.setImageName(oldMenu.getImageName());
        }


        return menuRepository.save(menu);
    }


    // ==================================================
    // Get All Menus
    // ==================================================

    @Override
    public List<Menu> getAllMenus() {

        return menuRepository.findAll();
    }


    // ==================================================
    // Get Menu By Id
    // ==================================================

    @Override
    public Menu getMenuById(Long id) {

        return menuRepository.findById(id)
                .orElse(null);
    }


    // ==================================================
    // Delete Menu
    // ==================================================

    @Override
    public void deleteMenu(Long id) {

        Menu menu = menuRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Menu Not Found"));


        // Check whether this menu exists in order history

        if (orderItemRepository.existsByMenuId(id)) {

            throw new RuntimeException(
                    "Cannot delete menu because order history exists for this menu.");
        }


        // Delete menu image

        if (menu.getImageName() != null) {

            fileStorageService.deleteImage(
                    menu.getImageName());
        }


        // Delete menu

        menuRepository.delete(menu);
    }


    // ==========================================
    // GET ALL AVAILABLE MENU ITEMS
    // ==========================================

    @Override
    public List<Menu> getAllAvailableMenus() {

        return menuRepository.findByAvailableTrue();
    }


    // ==========================================
    // GET AVAILABLE MENUS BY CATEGORY
    // ==========================================

    @Override
    public List<Menu> getAvailableMenusByCategory(
            Long categoryId) {

        return menuRepository
                .findByCategoryIdAndAvailableTrue(categoryId);
    }
}

