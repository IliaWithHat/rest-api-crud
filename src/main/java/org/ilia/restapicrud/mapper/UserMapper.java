package org.ilia.restapicrud.mapper;

import org.ilia.restapicrud.dto.UserDto;
import org.ilia.restapicrud.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    User toUser(UserDto userDto);

    User copyUserDtoToUser(UserDto userDto, @MappingTarget User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User copyUserDtoToUserIgnoreNull(UserDto userDto, @MappingTarget User user);
}
