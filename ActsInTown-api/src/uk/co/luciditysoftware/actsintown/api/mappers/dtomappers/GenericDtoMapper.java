package uk.co.luciditysoftware.actsintown.api.mappers.dtomappers;

import java.util.List;

public interface GenericDtoMapper {
    public <TTarget> TTarget map(Object source, Class<TTarget> destinationType);
    public <TSource, TTarget> List<TTarget> map(List<TSource> source, Class<TTarget> destinationType);
}
