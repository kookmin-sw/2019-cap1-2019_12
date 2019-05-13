package com.capstone.server.repository;

import com.capstone.server.user.UserInfo;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@EnableScan
public interface UserInfoRepository extends CrudRepository<UserInfo, String> {
    Optional<UserInfo> findById(String id);
}