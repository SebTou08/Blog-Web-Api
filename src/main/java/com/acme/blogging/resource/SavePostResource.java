package com.acme.blogging.resource;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SavePostResource {
    @NotNull
    @NotBlank
    @Size(max=100)
    private String title;

    @NotNull
    @NotBlank
    @Size(max=250)
    private String description;

    @NotNull
    @Lob
    private String content;

}
