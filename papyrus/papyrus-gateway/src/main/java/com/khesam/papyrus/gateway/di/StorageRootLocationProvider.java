package com.khesam.papyrus.gateway.di;

import com.khesam.papyrus.gateway.config.StorageProperties;
import com.khesam.papyrus.gateway.exception.StorageException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class StorageRootLocationProvider {

    @Bean
    @Qualifier("file-storage-root-location")
    Path fileStorageRootLocationProvider(
            StorageProperties storageProperties
    ) {
        if (storageProperties.location().trim().isEmpty()) {
            throw new StorageException.EmptyRootLocationStorageException("File upload location can not be Empty.");
        }

        return Paths.get(storageProperties.location());
    }
}
