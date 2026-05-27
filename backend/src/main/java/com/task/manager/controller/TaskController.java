package com.task.manager.controller;

import com.task.manager.entity.Task;
import com.task.manager.service.FileStorageService;
import com.task.manager.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final FileStorageService fileStorageService;

    // Construtor único recebendo os dois serviços
    public TaskController(TaskService taskService, FileStorageService fileStorageService) {
        this.taskService = taskService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping
    public List<Task> list() {
        return taskService.findAll();
    }

    @PostMapping
    public Task create(@RequestBody Task task) {
        return taskService.save(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        Task updateTask = taskService.updateTask(id, task);
        return ResponseEntity.ok(updateTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deletetask(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{id}/attachment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAttachment(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        // 1. Validação básica de segurança e integridade (Critério de Aceite)
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("O arquivo não pode estar vazio.");
        }

        // Bloqueio de arquivos executáveis por extensão para segurança do servidor
        String contentType = file.getContentType();
        if (contentType != null && (contentType.equals("application/x-msdownload") ||
                file.getOriginalFilename().endsWith(".exe") ||
                file.getOriginalFilename().endsWith(".bat"))) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                    .body("Tipo de arquivo não permitido (Executáveis são bloqueados).");
        }

        try {
            // 2. Salva o arquivo físico no diretório local configurado
            String nomeArquivoGerado = fileStorageService.armazenarArquivo(file);

            // 3. Vínculo com o banco de dados
            // Na arquitetura real, o nome ou o caminho do arquivo seria passado para o
            // serviço
            // da tarefa para atualizar o registro correspondente:
            // taskService.adicionarAnexo(id, nomeArquivoGerado);

            String mensagemSucesso = String.format("Arquivo '%s' enviado com sucesso para a tarefa %d!",
                    nomeArquivoGerado, id);

            return ResponseEntity.ok(mensagemSucesso);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro ao processar o upload: " + e.getMessage());
        }
    }

}
