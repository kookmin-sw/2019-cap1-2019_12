package com.capstone.server;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserDTOMongoDBRepository extends MongoRepository<UserDTO,String> {
    public UserDTO findByName(String name);
    //public List<UserDTO> findByPassword(String password);
}
