package com.pink.pink_api.sizes;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SizeNotFoundException extends RuntimeException{
    public SizeNotFoundException(String message){
        super(message);
    }
}
