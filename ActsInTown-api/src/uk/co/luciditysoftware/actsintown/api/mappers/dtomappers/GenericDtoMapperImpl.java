package uk.co.luciditysoftware.actsintown.api.mappers.dtomappers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenericDtoMapperImpl implements GenericDtoMapper {

    @Autowired
    private ModelMapper modelMapper;
    
    public <TTarget> TTarget map(Object source, Class<TTarget> destinationType) {
        TTarget target = modelMapper.map(source, destinationType);
        return target;
    }

    public <TSource extends Object, TTarget> List<TTarget> map(List<TSource> source, Class<TTarget> destinationType) {
        List<TTarget> target = source
            .stream()
            .map(element -> map(element, destinationType))
            .collect(Collectors.toList());
        
        return target;
    }
}
