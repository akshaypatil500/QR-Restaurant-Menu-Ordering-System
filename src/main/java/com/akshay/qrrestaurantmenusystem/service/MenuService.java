package com.akshay.qrrestaurantmenusystem.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.akshay.qrrestaurantmenusystem.entity.Category;
import com.akshay.qrrestaurantmenusystem.entity.Menu;

public interface MenuService {

    Menu saveMenu(Menu menu, MultipartFile imageFile);

    Menu updateMenu(Menu menu, MultipartFile imageFile);

    List<Menu> getAllMenus();

    Menu getMenuById(Long id);

    void deleteMenu(Long id);

    // Customer Menu
    List<Menu> getAllAvailableMenus();
    

    // Customer Module
    List<Menu> getAvailableMenusByCategory(Long categoryId);
}