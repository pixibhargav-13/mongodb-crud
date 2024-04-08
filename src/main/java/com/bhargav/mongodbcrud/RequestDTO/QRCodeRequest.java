package com.bhargav.mongodbcrud.RequestDTO;

import com.mongodb.annotations.Sealed;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QRCodeRequest {
    private String url;
    private String imageType;
    private String fileName;

}


