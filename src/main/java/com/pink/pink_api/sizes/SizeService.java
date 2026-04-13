package com.pink.pink_api.sizes;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SizeService {
    private final SizeRepository sizeRepository;

    public List<SizeDto> getAllSizes() {
        return sizeRepository
                .findAll()
                .stream()
                .map(s-> new SizeDto(s.getId(), s.getName())).toList();
    }

    public SizeDto addSize(@Valid SizeAddRequest request) {
        var size = new Size();
        size.setName(request.getName());

        sizeRepository.save(size);

        return new SizeDto(size.getId(), size.getName());
    }

    public void updateSize(Integer sizeId, @Valid UpdateSizeRequest request) {
        var size = sizeRepository.findById(sizeId)
                .orElseThrow(()-> new SizeNotFoundException("Size not found"));
        size.setName(request.getName());
        sizeRepository.save(size);
    }
}
