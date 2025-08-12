package com.base.Resources_Service.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateResourceRequest {
    private String title;
    private String description;
    private String fileUrl;
}