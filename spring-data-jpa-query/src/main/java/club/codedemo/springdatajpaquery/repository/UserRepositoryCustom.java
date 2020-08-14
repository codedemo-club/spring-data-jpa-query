package club.codedemo.springdatajpaquery.repository;

import club.codedemo.springdatajpaquery.entity.User;

import java.util.List;
import java.util.Set;

/**
 * 9.2 Criteria 动态查询 自定义接口
 */
public interface UserRepositoryCustom {
    List<User> findUserByEmailsUsingCriteria(Set<String> emails);
}
