package br.com.ciaf.admin.services;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Optional;

import br.com.ciaf.admin.config.FileStorageConfig;
import br.com.ciaf.admin.entities.Emitente;
import br.com.ciaf.admin.exception.FileStorageException;
import br.com.ciaf.admin.exception.MyFileNotFoundException;
import br.com.ciaf.admin.repositories.EmitenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    private final EmitenteRepository repository;

    @Autowired
    private FileStorageConfig fileStorageConfig;

    @Autowired
    public FileStorageService(FileStorageConfig fileStorageConfig, EmitenteRepository repository) {

        this.fileStorageLocation = Paths.get(fileStorageConfig.getUploadDir())
                .toAbsolutePath().normalize();
        this.repository = repository;
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored", e);
        }
    }

    public String storeFile(MultipartFile file, Long emitente_id) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalida path sequence " + fileName);
            }
            Optional<Emitente> emitente = repository.findById(emitente_id);
            assert emitente.isPresent();
            emitente.get().setImage(file.getBytes());
            fileName = "logo_"+ emitente_id + "." + fileName.substring(fileName.length() -3);
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            saveImage(targetLocation.toString(), emitente);

            return fileName;
        } catch (Exception e) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", e);
        }
    }
    public Emitente saveImage(String imagePath, Optional<Emitente> optionalEmitente) throws IOException {
        // Read the image as a byte array
        byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
        Emitente emitente = null;
        if(optionalEmitente.isPresent()){
             emitente = optionalEmitente.get();
        }

        // Set the byte array to the entity
        assert emitente != null;
        emitente.setImage(imageBytes);

        // Save the entity to the database (assuming you are using a repository)
        return repository.save(emitente); // Replace 'notasFiscaisRepository' with your repository instance
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (Exception e) {
            throw new MyFileNotFoundException("File not found " + fileName, e);
        }

    }

}
