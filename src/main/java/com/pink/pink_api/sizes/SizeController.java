package com.pink.pink_api.sizes;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sizes")
public class SizeController {
    private final SizeService sizeService;

    @GetMapping
    public List<SizeDto> getAllSizes(){
        return sizeService.getAllSizes();
    }

    @PostMapping
    public ResponseEntity<SizeDto> addSize(@RequestBody @Valid SizeAddRequest request){
        SizeDto sizeDto = sizeService.addSize(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(sizeDto);
    }

    @PutMapping("/{sizeId}")
    public ResponseEntity<?> updateSize(@PathVariable(name = "sizeId") Integer sizeId,
                                           @RequestBody @Valid UpdateSizeRequest request){
        sizeService.updateSize(sizeId, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
