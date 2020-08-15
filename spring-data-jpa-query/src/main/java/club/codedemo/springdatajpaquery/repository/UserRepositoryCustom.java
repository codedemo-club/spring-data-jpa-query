package club.codedemo.springdatajpaquery.repository;

import club.codedemo.springdatajpaquery.entity.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

/**
 * 9.2 Criteria 动态查询 自定义接口
 */
public interface UserRepositoryCustom {
    /**
     * 使用Criteria动态查询
     * 方法作用：根据传入的邮箱，查找邮箱包含在给定集合中的所有用户的列表
     * @return 邮箱包含在集合中的所有用户的列表
     */
    List<User> findUserByEmailsUsingCriteria(Set<String> emails);
}
