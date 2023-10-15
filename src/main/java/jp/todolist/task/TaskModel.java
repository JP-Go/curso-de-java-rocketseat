package jp.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import jp.todolist.errors.DomainRuleException;

@Data
@Entity(name = "tb_tasks")
public class TaskModel {

    /**
     * Id
     * User (user_id)
     * Título
     * Descrição
     * Data de início
     * Data de término
     * Prioridade
     */

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(length = 50)
    private String title;
    private String description;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String priority;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private UUID idUser;

    public void setTitle(String title) throws RuntimeException{
        if (title.length() > 50){
            throw new DomainRuleException("O campo de título deve conter no máximo 50 caracteres");
        }
        this.title = title;
    }
}
