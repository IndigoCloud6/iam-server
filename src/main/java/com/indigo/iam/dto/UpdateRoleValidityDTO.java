package com.indigo.iam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 更新角色有效期DTO
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@Data
public class UpdateRoleValidityDTO {

    /**
     * 用户角色关联ID
     */
    @NotNull(message = "关联ID不能为空")
    private Long id;

    /**
     * 生效时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime effectiveFrom;

    /**
     * 失效时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime effectiveTo;
}
