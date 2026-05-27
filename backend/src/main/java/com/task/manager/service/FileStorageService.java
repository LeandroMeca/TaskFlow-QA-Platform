package com.task.manager.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    // O construtor lê o caminho do application.properties e já cria a pasta se não
    // existir
    public FileStorageService(@Value("${file.upload-dir}") String uploadDir) {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Não foi possível criar o diretório onde os arquivos serão armazenados.", ex);
        }
    }

    public String armazenarArquivo(MultipartFile arquivo) {
        // Limpa o nome do arquivo e gera um UUID para evitar nomes duplicados
        String nomeOriginal = StringUtils.cleanPath(arquivo.getOriginalFilename());
        String nomeArquivoUnico = UUID.randomUUID().toString() + "_" + nomeOriginal;

        try {
            // Verifica se o arquivo não contém caracteres inválidos no caminho (Segurança)
            if (nomeArquivoUnico.contains("..")) {
                throw new RuntimeException(
                        "Desculpe! O nome do arquivo contém uma sequência de caminho inválida: " + nomeArquivoUnico);
            }

            // Copia o arquivo para o diretório alvo (Substituindo caso exista um com o
            // mesmo nome exato)
            Path targetLocation = this.fileStorageLocation.resolve(nomeArquivoUnico);
            Files.copy(arquivo.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return nomeArquivoUnico; // Retornamos o nome para salvar no banco de dados da Tarefa

        } catch (IOException ex) {
            throw new RuntimeException(
                    "Não foi possível armazenar o arquivo " + nomeArquivoUnico + ". Por favor, tente novamente!", ex);
        }
    }
}