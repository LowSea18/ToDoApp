package com.ToDo.todoApp.model.Dtos.GroupDtos;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class GroupDtoCreateGroup {
    @NotNull
    private String name;

}
