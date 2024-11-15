package com.sparta.harmony.menu.controller;

import com.sparta.harmony.menu.dto.MenuCreateRequestDto;
import com.sparta.harmony.menu.dto.MenuCreateResponseDto;
import com.sparta.harmony.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/{storeId}/menus")
    public ResponseEntity<MenuCreateResponseDto> create(@PathVariable String storeId, @RequestBody MenuCreateRequestDto menuCreateRequestDto) {
        MenuCreateResponseDto result = menuService.createMenu(UUID.fromString(storeId), menuCreateRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(result);
    }

    @PostMapping("/{storeId}/menus/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // todo: AWS S3 주소로 바꿔야 됨
            String currentDirectory = System.getProperty("user.dir");
            Path directoryPath = Paths.get(currentDirectory + "/src/main/resources/image");
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath); // Create directory if it does not exist
            }

            Path filePath = directoryPath.resolve(file.getOriginalFilename());
            file.transferTo(filePath.toFile()); // Save the file

            return "Image uploaded successfully: " + filePath.toAbsolutePath();
        } catch (IOException e) {
            return "Failed to upload image: " + e.getMessage();
        }
    }

}
