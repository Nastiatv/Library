package by.runa.lib.api.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(fluent = false, chain=true)
public class DepartmentDto extends ADto {

    private String name;

}
